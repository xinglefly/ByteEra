package com.zhicai.byteera.activity.knowwealth.presenter;

import android.app.Activity;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.bean.ZCUser;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.activity.knowwealth.viewinterface.KnowWealthDetailCommentIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.information.InformationSecondary;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/6/27. */
public class KnowWealthDetailCommentPre {
    private KnowWealthDetailCommentIV knowWealthDetailCommentIV;
    private Activity mContext;
    private String zixunId;
    private int commentIndex;
    private ZCUser userInfo;

    public KnowWealthDetailCommentPre(KnowWealthDetailCommentIV knowWealthDetailCommentIV) {
        this.knowWealthDetailCommentIV = knowWealthDetailCommentIV;
        this.mContext = knowWealthDetailCommentIV.getContext();
    }

    public void initData() {
        //第一页数据是从上一个界面带过来的  但是 之后的加载更多的数据需要
        userInfo = PreferenceUtils.getInstance(mContext).readUserInfo();
        zixunId = mContext.getIntent().getStringExtra("zixun_id");
        InformationSecondary.DetailsPage detail = (InformationSecondary.DetailsPage) mContext.getIntent().getSerializableExtra("detail");
        if (detail != null) {
            List<Common.Comment> commentList = detail.getCommentList();
            List<DynamicCommentEntity> dynamicCommentEntities = ModelParseUtil.DynamicCommentEntityParse(detail.getCommentList());
            if (commentList.size() > 0) {
                commentIndex = commentList.get(dynamicCommentEntities.size() - 1).getCommentIndex();
            }
            if (detail.getCommentCount() == 0) {
                knowWealthDetailCommentIV.showEmptyComment();
                knowWealthDetailCommentIV.goneListView();
            } else {
                knowWealthDetailCommentIV.showListView();
                knowWealthDetailCommentIV.goneEmptyComment();
            }
            knowWealthDetailCommentIV.refreshListView(dynamicCommentEntities);
        }

    }

    public void toastEmptyComment() {
        knowWealthDetailCommentIV.toastEmptyComment();
    }

    public void sendComment(String comment) {
        knowWealthDetailCommentIV.setHerViewRightTextViewEnabled(false);
        if (!knowWealthDetailCommentIV.startLoginActivity()) {
            InformationSecondary.Z_DoComment sec = InformationSecondary.Z_DoComment.newBuilder().setZixunId(zixunId).setUserId(userInfo.getUser_id()).setContent(comment)
                    .build();
            TiangongClient.instance().send("chronos", "zixun_do_comment", sec.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        final InformationSecondary.Z_DoCommentResponse response = InformationSecondary.Z_DoCommentResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                knowWealthDetailCommentIV.setHerViewRightTextViewEnabled(true);
                                if (response.getErrorno() == 0) {
                                    ToastUtil.showToastText( "评论成功");
                                    //TODO 每日任务，评论一篇文章
                                    UIUtils.hideKeyboard(mContext);
                                    mContext.setResult(Constants.RESULT_SUCCESS);
                                    ActivityUtil.finishActivity(mContext);
                                } else {
                                    knowWealthDetailCommentIV.toastErrorComment();
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

    public void loadMoreListView() {
        InformationSecondary.DetailsPageReq sec = InformationSecondary.DetailsPageReq.newBuilder()
                .setZixunId(zixunId).setUserId(userInfo.getUser_id()).setIsafter(0).setCommentIndex(commentIndex).build();
        TiangongClient.instance().send("chronos", "get_info", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final InformationSecondary.DetailsPage detailsPage = InformationSecondary.DetailsPage.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (detailsPage.getErrorno() == 0) {
                                if (detailsPage.getCommentCount() > 0) {
                                    commentIndex = detailsPage.getComment(detailsPage.getCommentCount() - 1).getCommentIndex();
                                    List<Common.Comment> commentList = detailsPage.getCommentList();
                                    List<DynamicCommentEntity> commentItemEntities = new ArrayList<DynamicCommentEntity>();
                                    for (int i = 0; i < commentList.size(); i++) {
                                        Common.Comment comment = commentList.get(i);
                                        DynamicCommentEntity itemEntity = new DynamicCommentEntity();
                                        itemEntity.setContent(comment.getContent());
                                        itemEntity.setAvatarUrl(comment.getHeadPortrait());
                                        itemEntity.setNickName(comment.getNickname());
                                        itemEntity.setUserId(comment.getUserId());
                                        commentItemEntities.add(itemEntity);
                                    }
                                    knowWealthDetailCommentIV.setMoreList(commentItemEntities);
                                }
                                knowWealthDetailCommentIV.loadComplete();

                            } else {
                                ToastUtil.showToastText("加载更多失败，请稍后重试");
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
