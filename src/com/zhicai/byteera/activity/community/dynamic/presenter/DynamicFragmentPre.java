package com.zhicai.byteera.activity.community.dynamic.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.dynamic.activity.DynamicCommentDetailActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.GroupHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.ImagePagerActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.OrganizationHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.UserHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.ImgEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.DynamicFragmentIV;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.DynamicFragmentItemIV;
import com.zhicai.byteera.activity.community.group.AllGroupActivity;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.activity.community.group.MyGroupActivity;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.drakeet.materialdialog.MaterialDialog;

/** Created by bing on 2015/6/29. */
public class DynamicFragmentPre {
    private Activity mContext;
    private DynamicFragmentIV dynamicFragmentIV;
    private SparseArray<DynamicFragmentItemIV> dynamicFragmentItemIVs = new SparseArray<>();
    private Fragment fragment;

    Executor exec = Executors.newSingleThreadExecutor();
    private String msgId;

    public void addDynamicFragmentItemIV(DynamicFragmentItemIV dynamicFragmentItemIV, int position) {
        this.dynamicFragmentItemIVs.append(position, dynamicFragmentItemIV);
    }

    public DynamicFragmentPre(DynamicFragmentIV dynamicFragmentIV) {
        this.dynamicFragmentIV = dynamicFragmentIV;
    }

    public void setContext() {
        this.mContext = dynamicFragmentIV.getContext();
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void intentToMyGroup() {
        Intent intent = new Intent(mContext, MyGroupActivity.class);
        ActivityUtil.startActivity((Activity) mContext, intent);
    }

    public void setFragment() {
        this.fragment = dynamicFragmentIV.getFragment();
    }

    public void intentToAllGroup() {
        Intent intent = new Intent(mContext, AllGroupActivity.class);
        ActivityUtil.startActivity((Activity) mContext, intent);
    }


    public void getDataFromNet(final boolean isFirst) {
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        Dynamic.GetMsg msg;
        if (isFirst) {
            msg = Dynamic.GetMsg.newBuilder().setIsafter(false).build();
        } else {
            msg = Dynamic.GetMsg.newBuilder().setIsafter(false).setMsgId(msgId).build();
        }

        TiangongClient.instance().send("chronos", "licaiquan_get_msg", msg.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Dynamic.GetMsgResponse response = Dynamic.GetMsgResponse.parseFrom(buffer);
                    Log.d("dynamicEntity","res-->"+response.toString());
                    dynamicFragmentIV.refreshFinish();
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            if (response.getErrorno() == 0) {
                                if (response.getItemCount() > 0) {
                                    final List<DynamicEntity> dynamicItemList = ModelParseUtil.DynamicFragmentItemEntityParse(response, userId);
                                    if (isFirst) {
                                        //如果是刷新或者是 第一次加载  就要把头给加上
                                        dynamicFragmentIV.removeAllViews();
                                        dynamicFragmentIV.addItem("");
                                    }
                                    dynamicFragmentIV.addAllItem(dynamicItemList);
                                    msgId = dynamicItemList.get(dynamicItemList.size() - 1).getMsgId();
                                    if (isFirst) {
                                        dynamicFragmentIV.notifyDataSetChanged();
                                    } else {
                                        dynamicFragmentIV.notifyDataSetChanged();
                                    }
                                }
                                dynamicFragmentIV.hidePage();
                                dynamicFragmentIV.loadComplete();
                                dynamicFragmentIV.refreshFinish();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void showDeleteDialog(final int position) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if (dynamicFragmentIV.getItem(position) instanceof DynamicEntity) {
            DynamicEntity dynamicEntity = (DynamicEntity) dynamicFragmentIV.getItem(position);
            if (dynamicEntity.getUserId().equals(userId)) {
                final MaterialDialog materialDialog = new MaterialDialog(mContext);
                materialDialog.setTitle("删除动态").setMessage("您确定要删除这条动态吗?").setPositiveButton("确定", new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        deleteCommment(position, materialDialog);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        materialDialog.dismiss();
                    }
                }).show();
            }
        }
    }

    private void deleteCommment(final int position, final MaterialDialog dialog) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        final DynamicEntity dynamicEntity = (DynamicEntity) dynamicFragmentIV.getItem(position);
        Dynamic.DeleteMyMsg sec = Dynamic.DeleteMyMsg.newBuilder().setUserId(userId).setMsgId(dynamicEntity.getMsgId()).build();
        TiangongClient.instance().send("chronos", "licaiquan_delete_mymsg", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Dynamic.DeleteMyMsgResponse response = Dynamic.DeleteMyMsgResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().post(new Runnable() {
                        public void run() {
                            if (response.getErrorno() == 0) {
                                ToastUtil.showToastText("删除动态成功");
                                dialog.dismiss();
                                dynamicFragmentIV.removeItemAtPosition(position);
                                dynamicFragmentIV.notifyDataSetChanged();
                                //从数据库中删除该条记录
                                // DataSupport.delete(DynamicEntity.class, dynamicEntity.getId());
                            } else {
                                ToastUtil.showToastText("删除动态失败");
                                dialog.dismiss();
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void intentToKnowWealthDetail(DynamicEntity entity) {
        Intent intent = new Intent(mContext, KnowWealthDetailActivity.class);
        intent.putExtra("zixun_id", entity.getZiXunId());
        ActivityUtil.startActivity((Activity) mContext, intent);
    }

//    public void intentToMyHome() {
//        Intent intent = new Intent(mContext, MyHomeActivity.class);
//        ActivityUtil.startActivity((Activity) mContext, intent);
//    }

    public void intentToUserHome(DynamicEntity entity) {
        Intent intent = new Intent(mContext, UserHomeActivity.class);
        intent.putExtra("other_user_id", entity.getUserId());
        ActivityUtil.startActivity((Activity) mContext, intent);

    }

    public void intentToOrganizationHome(DynamicEntity entity) {
        Intent intent = new Intent(mContext, OrganizationHomeActivity.class);
        intent.putExtra("other_user_id", entity.getUserId());
        ActivityUtil.startActivity((Activity) mContext, intent);
    }

    public void intentToGroupHome(String groupId) {
        DbUtils db = DbUtils.create(MyApp.getInstance(), Constants.BYTEERA_DB);
        GroupEntity groupEntity = null;
        try {
            groupEntity = db.findFirst(Selector.from(GroupEntity.class).where("groupId", "=", groupId));
//            Log.d("GroupEntity","groupEntity-->"+groupEntity.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (groupEntity!=null){
            Intent intent = new Intent(mContext, GroupHomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("group", groupEntity);
            intent.putExtras(bundle);
            ActivityUtil.startActivity(mContext, intent);
        }
    }

    public void clickHeadItent(DynamicEntity entity) {
        //点击头像 跳转到个人页 如果该条动态是自己发的  就跳转到我的主页
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        int userType = entity.getUserType();
        if (entity.getUserId().equals(userId)) {
//            intentToMyHome();
        } else {
            if (userType == 1) {
                intentToUserHome(entity);
            } else if (userType == 2) {
                intentToOrganizationHome(entity);
            }
        }
    }

    public void dianZan(final DynamicEntity entity, final int position) {
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if (TextUtils.isEmpty(userId)) {
            ActivityUtil.showLoginDialog((Activity) mContext, Constants.REQUEST_FRESH_DYNAMIC_FRAGMENT);
            return;
        }
        boolean hasZan = entity.isHasZan();
        if (!hasZan) {

            Dynamic.DoZan msg = Dynamic.DoZan.newBuilder().setUserId(userId).setMsgId(entity.getMsgId()).build();
            TiangongClient.instance().send("chronos", "licaiquan_dozan", msg.toByteArray(), new BaseHandlerClass() {
                @Override public void onSuccess(byte[] buffer) {
                    try {
                        final Dynamic.DoZanResponse response = Dynamic.DoZanResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                if (response.getErrorno() == 0) {
                                    //TODO 每日任务  点赞一次

                                    dynamicFragmentItemIVs.get(position).setPraiseNum(dynamicFragmentItemIVs.get(position).getPraiseNum() + 1);
                                    dynamicFragmentItemIVs.get(position).setIsPraise();
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
            Dynamic.UndoZan msg = Dynamic.UndoZan.newBuilder().setUserId(userId).setMsgId(entity.getMsgId()).build();
            TiangongClient.instance().send("chronos", "licaiquan_undozan", msg.toByteArray(), new BaseHandlerClass() {
                @Override public void onSuccess(byte[] buffer) {
                    try {
                        final Dynamic.UndoZanResponse response = Dynamic.UndoZanResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                if (response.getErrorno() == 0) {
                                    dynamicFragmentItemIVs.get(position).setPraiseNum(dynamicFragmentItemIVs.get(position).getPraiseNum() - 1);
                                    dynamicFragmentItemIVs.get(position).setUnPraise();
                                    entity.setHasZan(false);
                                    entity.setZanCount(entity.getZanCount() - 1);
//                                    //更新数据库
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

    public void imageBrower(View view, int position, List<ImgEntity> imgList) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        String images[] = new String[imgList.size()];
        for (int i = 0; i < imgList.size(); i++) {
            images[i] = imgList.get(i).getImgUrl();
        }
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, images);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        ActivityCompat.startActivity(mContext, intent, options.toBundle());
    }

    public void intentToDynamicCommentDetail(int position, DynamicEntity entity) {
        String user_id = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if (TextUtils.isEmpty(user_id)) {
            ActivityUtil.showLoginDialog(mContext);
        } else {
            Intent intent = new Intent(mContext, DynamicCommentDetailActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("entity", entity);
            ActivityUtil.startActivityForResult((Activity) mContext, getFragment(), intent, Constants.REQUEST_DYNAMIC_FRAGMENT);
        }
    }
}
