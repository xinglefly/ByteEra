package com.zhicai.byteera.activity.community.dynamic.presenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.Consult;
import com.zhicai.byteera.activity.community.dynamic.activity.DynamicCommentDetailActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.ImagePagerActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.ImgEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.UserHOmeDynamicItemIV;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.UserHomeActivityIV;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.UserHomeProductItemIV;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.information.InformationSecondary;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.List;

/** Created by bing on 2015/6/29. */
public class UserHomeActivityPre {
    private UserHomeActivityIV userHomeActivityIV;
    private Activity mContext;
    private String other_user_id;
    private int currentPage;
    private String msgId;
    private SparseArray<UserHomeProductItemIV> userHomeProductItemIVs = new SparseArray<>();

    private SparseArray<UserHOmeDynamicItemIV> userHOmeDynamicItemIVs = new SparseArray<>();
    private boolean isFocus;

    public UserHomeActivityPre(UserHomeActivityIV userHomeActivityIV) {
        this.userHomeActivityIV = userHomeActivityIV;
        this.mContext = userHomeActivityIV.getContext();
    }

    public void addUserHomeDynamicItemIV(UserHOmeDynamicItemIV userHOmeDynamicItemIV, int position) {
        this.userHOmeDynamicItemIVs.append(position, userHOmeDynamicItemIV);
    }

    public void addUserHomeProductIV(UserHomeProductItemIV userHomeProductItemIV, int position) {
        this.userHomeProductItemIVs.append(position, userHomeProductItemIV);
    }

    public void getDataBefore() {
        other_user_id = mContext.getIntent().getStringExtra("other_user_id");
    }

    public void getDynamicData(final boolean isFirst) {
        if (isFirst) {
            userHomeActivityIV.removeAllViews();
        }
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        Dynamic.GetUserMsg req;
        if (isFirst) {
            req = Dynamic.GetUserMsg.newBuilder().setUserId(other_user_id).setIsafter(false).build();
        } else {
            req = Dynamic.GetUserMsg.newBuilder().setUserId(other_user_id).setIsafter(false).setMsgId(msgId).build();
        }
        TiangongClient.instance().send("chronos", "licaiquan_get_usermsg", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Dynamic.GetUserMsgResponse response = Dynamic.GetUserMsgResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            if (response.getErrorno() == 0 && response.getItemCount() > 0) {
                                List<DynamicEntity> dynamicItemList = ModelParseUtil.DynamicEntityParse(response, userId);
                                if (isFirst) {
                                    userHomeActivityIV.setData(dynamicItemList);
                                } else {
                                    userHomeActivityIV.addAllItem(dynamicItemList);
                                }
                                msgId = dynamicItemList.get(dynamicItemList.size() - 1).getMsgId();
                            }
                            userHomeActivityIV.loadComplete();
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAttributeFromNet() {
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        UserAttribute.GetUserAttrReq1 sec = UserAttribute.GetUserAttrReq1.newBuilder().setUserId2(userId).setUserId(other_user_id).build();
        TiangongClient.instance().send("chronos", "get_user_attr1", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final UserAttribute.GetUserAttrResponse response = UserAttribute.GetUserAttrResponse.parseFrom(buffer);
                    //保存数据数据库
//                    saveData(response);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            userHomeActivityIV.setCoinNum(response.getCoin() + "");
                            userHomeActivityIV.SetFansNum(response.getFansUserCnt() + "");
                            userHomeActivityIV.setFocusNum(response.getWatchUserCnt() + "");
                            userHomeActivityIV.setHeadImage(response.getHeadPortrait());
                            userHomeActivityIV.setUserName(response.getNickname());
                            userHomeActivityIV.setUerId(response.getUserId());
                            isFocus = response.getWatched();
                            userHomeActivityIV.changeFocus(isFocus);
                            setSelect(0);
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setSelect(int position) {
        currentPage = position;
        switch (position) {
            case 0:
                getDynamicData(true);
                break;
            case 1:
                getCollectData(true);
                break;
        }
    }



    private void getCollectData(final boolean ifFirst) {
        if (ifFirst) {
            userHomeActivityIV.removeAllViews();
        }
        InformationSecondary.GetUserCollectReq req = InformationSecondary.GetUserCollectReq.newBuilder().setUserId(other_user_id).build();
        TiangongClient.instance().send("chronos", "zixun_get_user_collect", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final InformationSecondary.GetUserCollectResponse response = InformationSecondary.GetUserCollectResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                List<Consult> consults = ModelParseUtil.ZixunParse(response.getZixunList());
                                if (ifFirst) {
                                    userHomeActivityIV.setData(consults);
                                } else {
//                                    userHomeActivityIV.addAllItem(productEntities1);
                                }
                                userHomeActivityIV.loadComplete();
                            }
                        });
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void doFocus() {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if (TextUtils.isEmpty(userId)) {
            ActivityUtil.startActivity(mContext, new Intent(mContext, LoginActivity.class));
            return;
        }
        if (!isFocus) {
            UserAttribute.WatchUserReq sec = UserAttribute.WatchUserReq.newBuilder().setUserId(userId).setOtherUserId(other_user_id).build();
            TiangongClient.instance().send("chronos", "watch_user", sec.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        final UserAttribute.WatchUserResponse watchUserResponse = UserAttribute.WatchUserResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                if (watchUserResponse.getErrorno() == 0) {
                                    ToastUtil.showToastText("关注成功");
                                    isFocus = true;
                                    userHomeActivityIV.changeFocus(isFocus);
                                } else {
                                }
                            }
                        });
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            UserAttribute.DeWatchUserReq sec = UserAttribute.DeWatchUserReq.newBuilder().setUserId(userId).setOtherUserId(other_user_id).build();
            TiangongClient.instance().send("chronos", "dewatch_user", sec.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        final UserAttribute.DeWatchUserResponse deWatchUserResponse = UserAttribute.DeWatchUserResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                if (deWatchUserResponse.getErrorno() == 0) {
                                    ToastUtil.showToastText("成功取消关注");
                                    isFocus = false;
                                    userHomeActivityIV.changeFocus(isFocus);
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

    public void doChat() {
        ToastUtil.showToastText("聊天");
    }

    public void loadMore() {
        switch (currentPage) {
            case 0:
                getDynamicData(false);
                break;
            case 1:
                getCollectData(false);
                break;
        }
    }

    public void dianZan(final DynamicEntity entity, final int position) {
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if (TextUtils.isEmpty(userId)) {
            ActivityUtil.showLoginDialog(mContext, -1);
            return;
        }

        boolean hasZan = entity.isHasZan();
        if (!hasZan) {
            //点赞
            Dynamic.DoZan msg = Dynamic.DoZan.newBuilder().setUserId(userId).setMsgId(entity.getMsgId()).build();
            TiangongClient.instance().send("chronos", "licaiquan_dozan", msg.toByteArray(), new BaseHandlerClass() {
                @Override public void onSuccess(byte[] buffer) {
                    try {
                        final Dynamic.DoZanResponse response = Dynamic.DoZanResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                if (response.getErrorno() == 0) {
                                    //TODO 每日任务  点赞一次

                                    userHOmeDynamicItemIVs.get(position).setPraiseNum(userHOmeDynamicItemIVs.get(position).getPraiseNum() + 1);
                                    userHOmeDynamicItemIVs.get(position).setIsPraise();
                                    //自己封装一个赞的人的集合，然后点赞的时候就把自己加进去
                                    entity.setHasZan(true);
                                    entity.setZanCount(entity.getZanCount() + 1);
                                    userHomeActivityIV.notifyDataSetChanged();
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
            Dynamic.UndoZan msg = Dynamic.UndoZan.newBuilder().setUserId(userId).setMsgId(entity.getMsgId()).build();
            TiangongClient.instance().send("chronos", "licaiquan_undozan", msg.toByteArray(), new BaseHandlerClass() {
                @Override public void onSuccess(byte[] buffer) {
                    try {
                        final Dynamic.UndoZanResponse response = Dynamic.UndoZanResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                if (response.getErrorno() == 0) {
                                    userHOmeDynamicItemIVs.get(position).setPraiseNum(userHOmeDynamicItemIVs.get(position).getPraiseNum() - 1);
                                    userHOmeDynamicItemIVs.get(position).setUnPraise();
                                    entity.setHasZan(false);
                                    entity.setZanCount(entity.getZanCount() - 1);
                                    userHomeActivityIV.notifyDataSetChanged();
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

    public void intentToDynamicCommentDetail(int position, DynamicEntity entity) {
        Intent intent = new Intent(mContext, DynamicCommentDetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("entity", entity);
        ActivityUtil.startActivityForResult(mContext, intent, Constants.USER_HOME_DYNAMIC);
    }

    public void intentToKnowWealthDetail(DynamicEntity entity) {
        Intent intent = new Intent(mContext, KnowWealthDetailActivity.class);
        intent.putExtra("zixun_id", entity.getZiXunId());
        ActivityUtil.startActivity((Activity) mContext, intent);
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
}
