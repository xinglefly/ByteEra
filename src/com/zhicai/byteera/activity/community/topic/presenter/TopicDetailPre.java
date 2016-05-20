package com.zhicai.byteera.activity.community.topic.presenter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.topic.actiivty.MoreTopicActivity;
import com.zhicai.byteera.activity.community.topic.actiivty.PublishOpinionActivity;
import com.zhicai.byteera.activity.community.topic.entity.OpinionEntity;
import com.zhicai.byteera.activity.community.topic.entity.TopicEntity;
import com.zhicai.byteera.activity.community.topic.entity.TopicItemTitleEntity;
import com.zhicai.byteera.activity.community.topic.viewinterface.TopicDetailIV;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.service.topic.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Created by bing on 2015/6/28. */
public class TopicDetailPre {
    private static final String TAG = TopicDetailPre.class.getSimpleName();

    private Activity mContext;
    private TopicDetailIV topicDetailIV;
    private ArrayList<TopicEntity> topicList;
    private int totalNum;
    private int[] titles;
    private int selectedInt;
    private TopicEntity topicEntity;
    private boolean shouqi = false; //是否收起了热度推荐
    private List<Topic.OpinionItem> itemList;
    private List<Topic.OpinionItem> recommendItemList;

    public TopicDetailPre(TopicDetailIV topicDetailIV) {
        this.topicDetailIV = topicDetailIV;
        mContext = topicDetailIV.getContext();
    }

    public void initData() {
        topicList = mContext.getIntent().getParcelableArrayListExtra("topicList");
        totalNum = mContext.getIntent().getIntExtra("totalNum", 0);
        titles = new int[totalNum];
        Collections.reverse(topicList);
        for (int i = 0; i < totalNum; i++) {
            titles[i] = topicList.get(i).getTopicNum();
        }
        selectedInt = mContext.getIntent().getIntExtra("position", 0);
        topicDetailIV.initListView();
        setTitleText();
    }

    private void setTitleText() {
        if (selectedInt == titles[0]) { //如果滑到了最左边
            topicDetailIV.setLeftTextGone();
            topicDetailIV.setMiddleText(formatInt(selectedInt));
            topicDetailIV.setRIghtText(formatInt(selectedInt + 1));

        } else if (selectedInt == titles[totalNum - 1]) {   //如果进来是最右边
            topicDetailIV.setLeftText(formatInt(selectedInt - 1));
            topicDetailIV.setMiddleText(formatInt(selectedInt));
            topicDetailIV.setRightTextGone();
        } else {
            topicDetailIV.setLeftTextVisible();
            topicDetailIV.setRightTextVisible();
            topicDetailIV.setLeftText(formatInt(selectedInt - 1));
            topicDetailIV.setMiddleText(formatInt(selectedInt));
            topicDetailIV.setRIghtText(formatInt(selectedInt + 1));
        }
    }


    public String formatInt(int num) {
        if (num <= 9) {
            return String.format("0%02d期", num);
        } else {
            return String.format("0%d期", num);
        }
    }


    public void getDataFromNet() {
        topicEntity = topicList.get(selectedInt - 1);
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        Topic.getTopicOpinionReq req;
        if (TextUtils.isEmpty(userId)) {
            req = Topic.getTopicOpinionReq.newBuilder().setTopicId(topicEntity.getTopicId()).build();
        } else {
            req = Topic.getTopicOpinionReq.newBuilder().setTopicId(topicEntity.getTopicId()).setUserId(userId).build();
        }
        TiangongClient.instance().send("chronos", "get_topic_opinion", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Topic.getTopicOpinionResponse response = Topic.getTopicOpinionResponse.parseFrom(buffer);
//                    Log.d(TAG,"res -->"+response.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            topicDetailIV.setTitleButtonEnable(true);
                            topicDetailIV.refreshFinish();
                            topicDetailIV.removeAllViews();
                            topicDetailIV.addItem(topicEntity); //话题内容
                            if (response.getErrorno() == 0) {
                                recommendItemList = response.getRecommendItemList();
                                itemList = response.getItemList();
                                if (recommendItemList.size() > 0) {
                                    //先添加一条热度推荐的标题
                                    addHotTitle();
                                    setListData(recommendItemList, 0);
                                }
                                if (itemList.size() > 0) {
                                    addNewTitle();
                                    setListData(itemList, 1);
                                }
                                topicDetailIV.notifyDataSetChanged();
                            } else {
//                                loadingPage.showPage(Constants.STATE_ERROR);
                            }
                            topicDetailIV.hidePage();
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addNewTitle() {
        TopicItemTitleEntity topicItemTitleEntity = new TopicItemTitleEntity();
        topicItemTitleEntity.setItemType(1);
        topicDetailIV.addItem(topicItemTitleEntity);
    }

    private void addHotTitle() {
        TopicItemTitleEntity topicItemTitleEntity = new TopicItemTitleEntity();
        topicItemTitleEntity.setItemType(0);
        topicDetailIV.addItem(topicItemTitleEntity);
    }

    private void setListData(List<Topic.OpinionItem> itemList, int type) {
        List<OpinionEntity> opinionEntities = ModelParseUtil.OpioionEntityParse(itemList, type);
        topicDetailIV.addAllItem(opinionEntities);
    }

    public void changText(int i) {
        //只有按钮可用的时候点击才有效
        if (titleButtonIsEnable()) {
            if (i == -1) {
                if (selectedInt == titles[0]) {      //刚好左边是最后一个
                    //没有任何反应
                    return;
                } else {    //三个按钮的数字都减小一
                    selectedInt -= 1;    //数字减一
                    setTitleText();
                }
            } else if (i == 1) {        //点击的是右边按钮
                if (selectedInt == titles[totalNum - 1]) {
                    //没有任何反应
                    return;
                } else {
                    selectedInt += 1;
                    setTitleText();
                }
            }
            //刷新界面
            topicDetailIV.showPage(Constants.STATE_LOADING);
            getDataFromNet();
            topicDetailIV.setTitleButtonEnable(false);
        }
    }

    private boolean titleButtonIsEnable() {
        return topicDetailIV.titleButtonIsEnabled();
    }

    public void intentToPublishOpinion() {
        if (TextUtils.isEmpty(MyApp.getInstance().getUserId())) {
            ActivityUtil.startActivity(mContext, new Intent(mContext, LoginActivity.class));
        } else {
            Intent intent = new Intent(mContext, PublishOpinionActivity.class);
            intent.putExtra("topic_id", topicList.get(selectedInt - 1).getTopicId());    //传入topic_id
            ActivityUtil.startActivityForResult(mContext, intent, Constants.REQUEST_TOPIC_DETAIL);
        }
    }

    /** 打开评论板 */
    public void openEditCommentView(String opinionId, String toUserId, int position, String nickName) {
        String user_id = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        //只有登录的时候才能够发表评论 没有登录就要跳转到登录页面
        if (TextUtils.isEmpty(user_id)) {
            ActivityUtil.showLoginDialog(mContext, Constants.REQUEST_TOPIC_DETAIL);
        } else {
            topicDetailIV.openEditCommentView();
            String topicId = topicEntity.getTopicId();
            topicDetailIV.setComment(topicId, opinionId, user_id, toUserId, position, nickName);
        }
    }

    public void intentToMoreTopic() {
        Intent intent = new Intent(mContext, MoreTopicActivity.class);
        intent.putExtra("num", selectedInt);
        ActivityUtil.startActivityForResult(mContext, intent, 11);
    }

    public void dianZan(String opinionId, final int position) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if (TextUtils.isEmpty(userId)) {
            ActivityUtil.showLoginDialog(mContext, Constants.REQUEST_TOPIC_DETAIL);
        } else {
            Topic.TopicDozan req = Topic.TopicDozan.newBuilder().setUserId(userId).setOpinionId(opinionId).build();
            TiangongClient.instance().send("chronos", "topic_dozan", req.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        final Topic.TopicDozanResponse response = Topic.TopicDozanResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
//                            boolean zaning = response.getZaning();
                                // getDataFromNet();  //直接刷新本地数据  不要重新请求网络
                                topicDetailIV.setZaning(response.getZaning(), position);

                            }
                        });
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

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

    public void freshView() {
        topicDetailIV.notifyDataSetChanged();
    }

    public boolean IsShouqi() {
        return shouqi;
    }

    public void setShouqi(boolean shouqi) {
        this.shouqi = shouqi;
        topicDetailIV.removeAllViews();
        topicDetailIV.addItem(topicEntity); //话题内容
        if (shouqi) {
            addHotTitle();
        } else {
            if (recommendItemList.size() > 0) {
                //先添加一条热度推荐的标题
                addHotTitle();
                setListData(recommendItemList, 0);
            }
        }
        if (itemList.size() > 0) {
            addNewTitle();
            setListData(itemList, 1);
        }
        topicDetailIV.notifyDataSetChanged();
    }

    public void setData(Intent data) {  //点击查看更多后返回界面后重新进行界面的更新
        topicList = data.getParcelableArrayListExtra("topicList");
        totalNum = data.getIntExtra("totalNum", 0);
        titles = new int[totalNum];
        Collections.reverse(topicList);
        for (int i = 0; i < totalNum; i++) {
            titles[i] = topicList.get(i).getTopicNum();
        }
        selectedInt = data.getIntExtra("position", 0);
        setTitleText();
        getDataFromNet();
    }
}
