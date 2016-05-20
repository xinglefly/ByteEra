package com.zhicai.byteera.activity.community.dynamic.presenter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.dynamic.activity.DynamicCommentDetailActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.GroupHomeSecondActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.ImagePagerActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.PublishDynamicActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.UserFansActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.ImgEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.GroupDynamicItemIV;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.GroupHomeActivityIV;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.dynamic.DynamicGroupV2;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.List;

/** Created by bing on 2015/6/29. */
public class GroupHomeActivityPre {
    private Activity mContext;
    private GroupHomeActivityIV groupHomeActivityIV;
    private String lastMsgId;
    private SparseArray<GroupDynamicItemIV> groupDynamicItemIVs = new SparseArray<>();
    public GroupEntity groupEntity;
    public boolean isAttention;
    boolean position_des;


    public GroupHomeActivityPre(GroupHomeActivityIV groupHomeActivityIV) {
        this.groupHomeActivityIV = groupHomeActivityIV;
        this.mContext = groupHomeActivityIV.getContext();
    }


    public void addGroupDynamicItemIVs(GroupDynamicItemIV groupDynamicItemIV, int position) {
        this.groupDynamicItemIVs.append(position, groupDynamicItemIV);
    }


    public void getDynamicFromNet(final boolean isFirst) {
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        Dynamic.GetGroupMsgReq req;
        if (isFirst) {
            req = Dynamic.GetGroupMsgReq.newBuilder().setGroupId(groupEntity.getGroupId()).setIsafter(false).build();
        } else {
            req = Dynamic.GetGroupMsgReq.newBuilder().setGroupId(groupEntity.getGroupId()).setMsgId(lastMsgId).setIsafter(false).build();
        }
        TiangongClient.instance().send("chronos", "licaiquan_get_group_msg", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Dynamic.GetGroupMsgResponse response = Dynamic.GetGroupMsgResponse.parseFrom(buffer);
                    Log.d("groupHome", "groupHome-->" + response.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            if (response.getErrorno() == 0 && response.getItemCount() > 0) {
                                List<DynamicEntity> dynamicEntities = ModelParseUtil.GroupDynamicEntityParse(response, userId);
                                lastMsgId = dynamicEntities.get(dynamicEntities.size() - 1).getMsgId(); //获取最后一条数据的msgId
                                if (isFirst) {
                                    groupHomeActivityIV.setData(dynamicEntities);
                                } else {
                                    groupHomeActivityIV.addAllItem(dynamicEntities);
                                }
                            }
                            groupHomeActivityIV.loadComplete();
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getIntentData() {
        groupEntity = (GroupEntity) mContext.getIntent().getSerializableExtra("group");
        position_des = mContext.getIntent().getBooleanExtra("position_des", false);
    }

    public void setGroupAttributes() {
        groupHomeActivityIV.setTitleName(groupEntity.getName());
        groupHomeActivityIV.setAvatar(groupEntity.getAvatarUrl());
        groupHomeActivityIV.setDescrition(groupEntity.getDescription());
        groupHomeActivityIV.setFansCount(groupEntity.getPeopleCnt());
        groupHomeActivityIV.setJoined(groupEntity.isJoined());
        isAttention = groupEntity.isJoined();
    }


    public void imageBrower(int position, List<ImgEntity> imgList) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        String images[] = new String[imgList.size()];
        for (int i = 0; i < imgList.size(); i++) {
            images[i] = imgList.get(i).getImgUrl();
        }
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, images);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        ActivityUtil.startActivity((Activity) mContext, intent);
    }

    public void dianZan(final DynamicEntity entity, final int position) {
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        boolean hasZan = entity.isHasZan();
        final String msgId = entity.getMsgId();
        if (!hasZan) {
            //点赞
            Dynamic.DoZan msg = Dynamic.DoZan.newBuilder().setUserId(userId).setMsgId(msgId).build();
            TiangongClient.instance().send("chronos", "licaiquan_dozan", msg.toByteArray(), new BaseHandlerClass() {
                @Override public void onSuccess(byte[] buffer) {
                    try {
                        final Dynamic.DoZanResponse response = Dynamic.DoZanResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                if (response.getErrorno() == 0) {
                                    //TODO 每日任务  点赞一次

                                    groupDynamicItemIVs.get(position).setPraiseCount(String.valueOf(Integer.parseInt(groupDynamicItemIVs.get(position).getPraiseCount()) + 1));
                                    groupDynamicItemIVs.get(position).setIsPraise();
                                    //自己封装一个赞的人的集合，然后点赞的时候就把自己加进去
                                    entity.setHasZan(true);
                                    entity.setZanCount(entity.getZanCount() + 1);
                                    //更新数据库
//                                    ContentValues values = new ContentValues();
//                                    values.put("zancount", entity.getZanCount());
//                                    values.put("haszan", true);
//                                    DataSupport.updateAll(DynamicEntity.class, values, "msgid = ?", entity.getMsgId());
                                }
                            }
                        });
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            //取消点赞
            Dynamic.UndoZan msg = Dynamic.UndoZan.newBuilder().setUserId(userId).setMsgId(msgId).build();
            TiangongClient.instance().send("chronos", "licaiquan_undozan", msg.toByteArray(), new BaseHandlerClass() {
                @Override public void onSuccess(byte[] buffer) {
                    try {
                        final Dynamic.UndoZanResponse response = Dynamic.UndoZanResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override
                            public void run() {
                                if (response.getErrorno() == 0) {
                                    groupDynamicItemIVs.get(position).setPraiseCount(String.valueOf(Integer.parseInt(groupDynamicItemIVs.get(position).getPraiseCount()) - 1));
                                    groupDynamicItemIVs.get(position).setUnPraise();
                                    entity.setHasZan(false);
                                    entity.setZanCount(entity.getZanCount() - 1);
                                    //更新数据库
//                                    ContentValues values = new ContentValues();
//                                    values.put("zancount", entity.getZanCount());
//                                    values.put("haszan", false);
//                                    DataSupport.updateAll(DynamicEntity.class, values, "msgid = ?", entity.getMsgId());
                                }
                            }
                        });

                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void intentToDynamicCommentDetial(int position, DynamicEntity entity) {
        Intent intent = new Intent(mContext, DynamicCommentDetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("entity", entity);
        ActivityUtil.startActivityForResult(mContext, intent, Constants.REQUEST_ORGANIZATION_HOME);
    }


    public void intentToFansActivity() {
        Intent itent = new Intent(mContext, UserFansActivity.class);
        itent.putExtra("user_id",groupEntity.getGroupId());
        ActivityUtil.startActivity(mContext, itent);
    }

    /**发布小组动态**/
    public void intentToPublish() {
        if (TextUtils.isEmpty(MyApp.getInstance().getUserId())) {
            ActivityUtil.startActivity(mContext, new Intent(mContext, LoginActivity.class));
        } else if(!isAttention){
            ToastUtil.showToastText("请先关注该小组才能发表点评");
        }else{
            ActivityUtil.startActivity(mContext,new Intent(mContext,PublishDynamicActivity.class).putExtra("group_id",groupEntity.getGroupId()));
        }
    }



    public void intentToSecondActivity() {
        Intent itent = new Intent(mContext, GroupHomeSecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("group", groupEntity);
        itent.putExtras(bundle);
        itent.putExtra("isAttetion",isAttention);
        ActivityUtil.startActivity(mContext, itent);
    }

    public void showBottom(RelativeLayout rlDianPin) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlDianPin, "translationY", rlDianPin.getMeasuredHeight(), 0f);
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public void hideBottom(RelativeLayout rlDianPin) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlDianPin, "translationY", 0f, rlDianPin.getMeasuredHeight());
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }




    /**加入小组**/
    public void toJoined() {
        if (TextUtils.isEmpty(MyApp.getInstance().getUserId())) {
            ActivityUtil.startActivity(mContext, new Intent(mContext, LoginActivity.class));
        }
        final DynamicGroupV2.LicaiquanGroupJoinReq req = DynamicGroupV2.LicaiquanGroupJoinReq.newBuilder().setMyUserId(MyApp.getInstance().getUserId()).setLicaiquanGroupId(groupEntity.getGroupId()).build();
        TiangongClient.instance().send("chronos", "licaiquan_group_join", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final DynamicGroupV2.LicaiquanGroupJoinResponse response = DynamicGroupV2.LicaiquanGroupJoinResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToastText("关注成功");
                                groupHomeActivityIV.setJoined(true);
                                mContext.sendBroadcast(new Intent(Constants.GROUPACTION));
                                isAttention=true;
                            }
                        });

                    } else {
                        ToastUtil.showToastText("关注失败");
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**获取某个理财小组信息**/
    public void getPositionData() {
        if (!position_des) return;
        DynamicGroupV2.LicaiquanGroupGetReq sec = DynamicGroupV2.LicaiquanGroupGetReq.newBuilder().setUserId(MyApp.getInstance().getUserId()).setLicaiquanGroupId(groupEntity.getGroupId()).build();
        TiangongClient.instance().send("chronos", "licaiquan_group_get", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final DynamicGroupV2.LicaiquanGroupGetResponse response = DynamicGroupV2.LicaiquanGroupGetResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                final DynamicGroupV2.LicaiquanGroupEx group = response.getGroup();
                                groupHomeActivityIV.setTitleName(group.getName());
                                groupHomeActivityIV.setAvatar(group.getImage());
                                groupHomeActivityIV.setFansCount(group.getMemberCount());
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
