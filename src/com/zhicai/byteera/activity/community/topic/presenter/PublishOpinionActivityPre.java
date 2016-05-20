package com.zhicai.byteera.activity.community.topic.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.community.topic.entity.InstitutionUserEntity;
import com.zhicai.byteera.activity.community.topic.viewinterface.PublishOpinionActivityIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.service.topic.Topic;

/** Created by bing on 2015/6/30. */
public class PublishOpinionActivityPre {
    private static final String TAG = PublishOpinionActivityPre.class.getSimpleName();

    private PublishOpinionActivityIV publishOpinionActivityIV;
    private Activity mContext;
    private String topicId;
    private InstitutionUserEntity institutionItem;
    private FinancingCompanyEntity institutionCompanyItem;

    public PublishOpinionActivityPre(PublishOpinionActivityIV publishOpinionActivityIV) {
        this.publishOpinionActivityIV = publishOpinionActivityIV;
        this.mContext = publishOpinionActivityIV.getContext();
    }

    public void getIntentData() {
        topicId = mContext.getIntent().getStringExtra("topic_id");
    }

    public void pushOpinion() {
        String userId = PreferenceUtils.getInstance(mContext).getUserId();
        String content = publishOpinionActivityIV.getContent();
        if (!publishOpinionActivityIV.isStartLoginActivity()) {
            publishOpinionActivityIV.setSendEnabled(false);
            Topic.PublishOpinionReq req;
            if (institutionCompanyItem == null) {
                req = Topic.PublishOpinionReq.newBuilder().setUserId(userId).setContent(content).setTopicId(topicId).build();
            } else {
                req = Topic.PublishOpinionReq.newBuilder().setUserId(userId).setContent(content).setTopicId(topicId).setCompanyId(institutionCompanyItem.getCompany_id()).build();
            }
            TiangongClient.instance().send("chronos", "publish_opinion", req.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        final Topic.PublishOpinionResponse response = Topic.PublishOpinionResponse.parseFrom(buffer);
                        Log.d(TAG,"res-->"+response.toString());
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                publishOpinionActivityIV.setSendEnabled(true);
                                if (response.getErrorno() == 0) {
                                    ToastUtil.showToastText("发布成功");
                                    UIUtils.hideKeyboard(mContext);
                                    //动态发布完成之后返回到上一个界面,并使页面得到更新
                                    returnToLastUI(response);
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

    private void returnToLastUI(Topic.PublishOpinionResponse response) {
        //自己创建一个opinionEntity对象 用来返回到上一个页面  这里先不做了 因为还没有加上分页加载
//        Topic.OpinionItem item = response.getItem();
//        OpinionEntity opinionEntity = new OpinionEntity();
//        opinionEntity.setUserId(item.getUserId());
//        opinionEntity.setContent(item.getContent());
//        opinionEntity.setCommentNum(item.getCommentNum());
//        opinionEntity.set
        mContext.setResult(Constants.RESULT_SUCCESS);
        ActivityUtil.finishActivity(mContext);
    }

    public void setOnActivityResult(Intent data) {
        institutionCompanyItem = data.getParcelableExtra("institutionCompanyItem");
        //显示机构
        publishOpinionActivityIV.showInstitutionCompany(institutionCompanyItem);
    }
}
