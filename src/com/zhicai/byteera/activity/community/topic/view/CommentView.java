package com.zhicai.byteera.activity.community.topic.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.service.topic.Topic;
import com.zhicai.byteera.widget.ImeEditText;

/** Created by bing on 2015/6/25. */
public class CommentView extends FrameLayout implements View.OnClickListener, ImeEditText.OnImeHideListener {
    public static boolean keyBoardIsHide = true;
    private ImeEditText etComment;
    private String topic_id;
    private String opinion_id;
    private String user_id;
    private String to_user_id;
    private int position;

    @Override public void imeHide() {
        quitFocus();
    }

    public interface OnCommentFinishListener {
        void commentFinish(Common.Comment comment, int position);
    }

    private OnCommentFinishListener onCommentFinishListener;

    public void setOnCommentFinishListener(OnCommentFinishListener listener) {
        this.onCommentFinishListener = listener;
    }

    public CommentView(Context context) {
        this(context, null);
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.comment_view, this, true);
        etComment = (ImeEditText) findViewById(R.id.et_comment);
        findViewById(R.id.btn).setOnClickListener(this);
        etComment.setOnImeHideListener(this);
    }

    public void getFocus() {
        keyBoardIsHide = false;
        etComment.setFocusable(true);
        etComment.setFocusableInTouchMode(true);
        etComment.requestFocus();
        UIUtils.showKeyboard((Activity) getContext(), etComment);
    }

    public void quitFocus() {
        etComment.setText("");
        UIUtils.hideKeyboard((Activity) getContext(), etComment);
        this.setVisibility(GONE);
        keyBoardIsHide = true;
    }

    public void setComment(String topic_id, String opinion_id, String user_id, String to_user_id, int position, String nickName) {
        this.topic_id = topic_id;
        this.opinion_id = opinion_id;
        this.user_id = user_id;
        this.to_user_id = to_user_id;
        this.position = position;
        etComment.setHint("@"+nickName);
    }

    @Override public void onClick(View v) {
        final Topic.PublishCommentReq req;
        String comment = etComment.getText().toString();
        if (TextUtils.isEmpty(comment)) {
           ToastUtil.showToastText("内容不能为空");
        } else {
            if (TextUtils.isEmpty(to_user_id)) {    //评论某人
                req = Topic.PublishCommentReq.newBuilder().setTopicId(topic_id).setOpinionId(opinion_id).setUserId(user_id)
                        .setContent(etComment.getText().toString()).build();
            } else {        //回复某人
                req = Topic.PublishCommentReq.newBuilder().setTopicId(topic_id).setOpinionId(opinion_id).setUserId(user_id)
                        .setContent(etComment.getText().toString()).setToUserId(to_user_id).build();
            }
            Log.e("req", req.toString());
            TiangongClient.instance().send("chronos", "publish_comment", req.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        final Topic.PublishCommentResponse response = Topic.PublishCommentResponse.parseFrom(buffer);
                        Log.e("getNickName", response.getComment().getNickname());
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                               ToastUtil.showToastText("评论成功");
                                quitFocus();
                                //TODO 通过重新刷新页面的方式来添加评论
                                if (onCommentFinishListener != null) {
                                    Common.Comment comment = response.getComment();
                                    onCommentFinishListener.commentFinish(comment, position);
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
}
