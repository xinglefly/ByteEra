package com.zhicai.byteera.activity.knowwealth.presenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.protobuf.InvalidProtocolBufferException;
import com.tencent.tauth.IUiListener;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.ShareEntity;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailActivity;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailCommentActivity;
import com.zhicai.byteera.activity.knowwealth.viewinterface.KnowWealthDeatailIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.information.InformationSecondary;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.PopupWindowManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.drakeet.materialdialog.MaterialDialog;

/** Created by bing on 2015/6/27. */
public class KnowWealthDetailPre {
    private static final String TAG = KnowWealthDetailPre.class.getSimpleName();

    private KnowWealthDeatailIV knowWealthDeatailIV;
    private Activity mContext;
    private String zixun_id;
    private InformationSecondary.DetailsPage firstPage; //添加第一次加载时的界面数据主要是为了 跳转到评论页面的时候不用再请求网络拿到所有的评论
    private String title;
    private String imageUrl;
    private boolean isCollect;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private InformationSecondary.DetailsPage page;
    private int commentIndex;

    public KnowWealthDetailPre(KnowWealthDeatailIV knowWealthDeatailIV) {
        this.knowWealthDeatailIV = knowWealthDeatailIV;
        this.mContext = knowWealthDeatailIV.getContext();
    }

    public void getIntentData() {
        zixun_id = mContext.getIntent().getStringExtra("zixun_id");
        title = mContext.getIntent().getStringExtra("title");
        imageUrl = mContext.getIntent().getStringExtra("imgUrl");
    }

    public void getDetailData(final int fresh) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        InformationSecondary.DetailsPageReq sec;
        if (!TextUtils.isEmpty(userId)) {
            sec = InformationSecondary.DetailsPageReq.newBuilder().setZixunId(zixun_id).setUserId(userId).build();
        } else {
            sec = InformationSecondary.DetailsPageReq.newBuilder().setZixunId(zixun_id).build();
        }
        TiangongClient.instance().send("chronos", "get_info", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    page = InformationSecondary.DetailsPage.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            if (page.getErrorno() == 0) {
                                int commentCount = 0;
                                if (page.getTotalCommentCnt() > 0) {
                                    commentIndex = page.getComment(page.getCommentCount() - 1).getCommentIndex();
                                }
                                isCollect = page.getIsCollect();
                                commentCount = page.getTotalCommentCnt();
                                List<DynamicCommentEntity> dynamicCommentEntities = ModelParseUtil.DynamicCommentEntityParse(page.getCommentList());
                                if (fresh == KnowWealthDetailActivity.FRESH_ALL) {
                                    firstPage = page;
                                    knowWealthDeatailIV.initListView(page, dynamicCommentEntities);
                                } else if (fresh == KnowWealthDetailActivity.FRESH_LIST) {
                                    firstPage = page;
                                    knowWealthDeatailIV.setComment(commentCount);
                                    knowWealthDeatailIV.freshListView(dynamicCommentEntities);
                                }
                            } else {
                                knowWealthDeatailIV.showErrorPage();
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void ShareAPI(final IUiListener qqShareListener) {
        executorService.execute(new Runnable() {
            public void run() {
                MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                    public void run() {
                        new PopupWindowManager(new ShareEntity(mContext,title, imageUrl, page.getDetailUrl()+"?is_share=123",qqShareListener));
                    }
                });
            }
        });
    }

    /**内部分享**/
    public void share(String content) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if(TextUtils.isEmpty(userId)){
            ActivityUtil.startActivity(mContext,new Intent(mContext, LoginActivity.class));
            return;
        }
        Dynamic.PublishShareInternalMsg sec = Dynamic.PublishShareInternalMsg.newBuilder().setSharedZixunId(zixun_id).setUserId(userId).setContent(content).build();
        TiangongClient.instance().send("chronos", "licaiquan_pub_int_msg", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    Dynamic.PublishShareInternalMsgResponse res = Dynamic.PublishShareInternalMsgResponse.parseFrom(buffer);
                    if (res.getErrorno() == 0) {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                ToastUtil.showToastText( "分享成功");
                                knowWealthDeatailIV.dismissShareDialog();
                            }
                        });
                    } else {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                ToastUtil.showToastText( "分享失败,请稍后再试");
                            }
                        });
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showDeleteDialog(final int position) {
        final MaterialDialog materialDialog = new MaterialDialog(mContext);
        materialDialog.setTitle("删除评论").setMessage("您确定要删除这条评论吗?").setPositiveButton("确定", new View.OnClickListener() {
            @Override public void onClick(View v) {
                deleteCommment(position, materialDialog);
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override public void onClick(View v) {
                materialDialog.dismiss();
            }
        }).show();
    }

    private void deleteCommment(final int position, final MaterialDialog dialog) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        InformationSecondary.Z_UndoComment sec = InformationSecondary.Z_UndoComment.newBuilder().setUserId(userId)
                .setCommentIndex(knowWealthDeatailIV.getCommentIndex(position)).setZixunId(zixun_id).build();
        Log.e("zixunId", zixun_id);
        TiangongClient.instance().send("chronos", "zixun_undo_comment", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final InformationSecondary.Z_UndoCommentResponse response = InformationSecondary.Z_UndoCommentResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            if (response.getErrorno() == 0) {
                                ToastUtil.showToastText( "删除评论成功,请稍后再试");
                                dialog.dismiss();
                                //最好不要重新请求网络，不然又会回到第一页
                                knowWealthDeatailIV.removeItemAtPosition(position);
                                //更新评论条数的显示
                                knowWealthDeatailIV.setComment(response.getTotalCommentCnt());
                                //firstPage中也要删除该条评论 在进入下一个界面的时候才能够一致.当然，只有在第一页的时候才需要这么做，因为后面的分页到跳转到评论页的时候已经是从网上获取的了
                                if (position < firstPage.getCommentCount()) {
                                    firstPage = firstPage.toBuilder().removeComment(position).build();
                                }
                            } else {
                                ToastUtil.showToastText( "删除评论失败");
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void loadMoreListView() {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        InformationSecondary.DetailsPageReq sec;
        if (TextUtils.isEmpty(userId)) {
            sec = InformationSecondary.DetailsPageReq.newBuilder().setZixunId(zixun_id).setIsafter(0).setCommentIndex(commentIndex).build();
        } else {
            sec = InformationSecondary.DetailsPageReq.newBuilder().setZixunId(zixun_id).setUserId(userId).setIsafter(0).setCommentIndex(commentIndex).build();
        }
        TiangongClient.instance().send("chronos", "get_info", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    page = InformationSecondary.DetailsPage.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (page.getErrorno() == 0) {
                                if (page.getCommentCount() > 0) {
                                    List<DynamicCommentEntity> dynamicCommentEntities = ModelParseUtil.DynamicCommentEntityParse(page.getCommentList());
                                    knowWealthDeatailIV.loadMoreListView(dynamicCommentEntities);
                                    commentIndex = page.getComment(page.getCommentCount() - 1).getCommentIndex();
                                }
                                knowWealthDeatailIV.loadComplete();
                            } else {
                                knowWealthDeatailIV.loadError();
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void intentToDetailCommentActivity() {
        if (!knowWealthDeatailIV.startLoginActivity()) {
            Intent intent = new Intent(mContext, KnowWealthDetailCommentActivity.class);
            intent.putExtra("detail", firstPage);
            intent.putExtra("zixun_id", zixun_id);
            ActivityUtil.startActivityForResult(mContext, intent, Constants.KNOW_WEALTH_DETAIL);
        }
    }

    public void changCollect() {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if (isCollect) {
            //如果已经收藏，点击就为取消收藏
            InformationSecondary.UndoCollectReq req = InformationSecondary.UndoCollectReq.newBuilder().setUserId(userId).setZixunId(zixun_id).build();
            TiangongClient.instance().send("chronos", "zixun_undo_collect", req.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        InformationSecondary.UndoCollectResponse response = InformationSecondary.UndoCollectResponse.parseFrom(buffer);
                        if (response.getErrorno() == 0) {
                            //取消收藏成功
                            MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                                public void run() {
                                    knowWealthDeatailIV.changeCollectionButomUnCollect();
                                    isCollect = false;
                                    ToastUtil.showToastText( "成功取消收藏");
                                    //修改sharedPreference中的值
                                    ZCUser zcUser = PreferenceUtils.getInstance(mContext).readUserInfo();
                                    zcUser.setCollectCnt(zcUser.getCollectCnt() - 1);
                                    PreferenceUtils.getInstance(mContext).setUserInfo(zcUser);
                                }
                            });
                        } else {
                            //取消收藏失败
                        }
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            //如果还没有收藏，点击为添加收藏
            if (!knowWealthDeatailIV.startLoginActivity()) {
                InformationSecondary.DoCollectReq req = InformationSecondary.DoCollectReq.newBuilder().setUserId(userId).setZixunId(zixun_id).build();
                TiangongClient.instance().send("chronos", "zixun_do_collect", req.toByteArray(), new BaseHandlerClass() {
                    public void onSuccess(byte[] buffer) {
                        try {
                            InformationSecondary.DoCollectResponse page = InformationSecondary.DoCollectResponse.parseFrom(buffer);
                            if (page.getErrorno() == 0) {
                                //收藏成功
                                MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                                    public void run() {
                                        knowWealthDeatailIV.changeCollectionButomCollect();
                                        ToastUtil.showToastText( "收藏成功");
                                        //修改sharedPreference中的值
                                        ZCUser zcUser = PreferenceUtils.getInstance(mContext).readUserInfo();
                                        zcUser.setCollectCnt(zcUser.getCollectCnt() + 1);
                                        PreferenceUtils.getInstance(mContext).setUserInfo(zcUser);
                                        isCollect = true;
                                    }
                                });
                            } else {
                                //收藏失败
                            }
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
