package com.zhicai.byteera.activity.community.topic.actiivty;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.topic.adapter.TopicDetailAdapter;
import com.zhicai.byteera.activity.community.topic.presenter.TopicDetailPre;
import com.zhicai.byteera.activity.community.topic.view.CommentView;
import com.zhicai.byteera.activity.community.topic.viewinterface.TopicDetailIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.widget.LoadingPage;
import com.zhicai.byteera.widget.MyCircleStrokeView;
import com.zhicai.byteera.widget.ZhiCaiLRefreshListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopicDetailActivity extends BaseActivity implements CommentView.OnCommentFinishListener, TopicDetailIV {

    @Bind(R.id.list_view) ZhiCaiLRefreshListView mListView;
    @Bind(R.id.tv_left) MyCircleStrokeView mTvLeft;
    @Bind(R.id.tv_middle) TextView mTvMiddle;
    @Bind(R.id.tv_right) MyCircleStrokeView mTvRight;
    @Bind(R.id.rl_dianping) RelativeLayout rlDianPin;
    @Bind(R.id.loading_page) LoadingPage loadingPage;
    @Bind(R.id.ll_topic_title) LinearLayout llTopicTitle;
    @Bind(R.id.comment_view) CommentView mCommentView;
    @Bind(R.id.top_head) View topHead;
    @Bind(R.id.rl_container) RelativeLayout rl_container;

    private boolean showBottom = true;
    private TopicDetailAdapter mAdapter;
    private float downY;
    private TopicDetailPre topicDetailPre;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.activity_topic_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rl_container.getLayoutParams();
            params.setMargins(0, -UIUtils.getStatusHeight(this), 0, 0);
            rl_container.setLayoutParams(params);
        } else {
            topHead.setVisibility(View.GONE);
        }
        topicDetailPre = new TopicDetailPre(this);
        llTopicTitle.bringToFront();
        initLoadingPage();
        mCommentView.setOnCommentFinishListener(this);
        mListView.setDivider(null);
    }

    private void initLoadingPage() {
        loadingPage.showPage(Constants.STATE_LOADING);
        loadingPage.setOnRetryClickListener(new LoadingPage.RetryClickListener() {
            @Override public void retryClick() {
                loadingPage.showPage(Constants.STATE_LOADING);
                topicDetailPre.getDataFromNet();
            }
        });
    }

    @Override protected void initData() {
        topicDetailPre.initData();
        topicDetailPre.getDataFromNet();   //获得话题下面的所有意见
    }

    @Override protected void updateUI() {
    }

    @Override protected void processLogic() {
        mListView.setOnScrollListener(new ZhiCaiLRefreshListView.OnScrollListener() {
            @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    showBottom = true;
                    topicDetailPre.showBottom(rlDianPin);
                } else {
                    if (showBottom) {
                        showBottom = false;
                        topicDetailPre.hideBottom(rlDianPin);
                    }
                }
            }

            @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        mListView.setLoadMoreDataListener(new ZhiCaiLRefreshListView.LoadMoreDataListener() {
            @Override public void loadMore() {
                mListView.loadComplete();
            }
        });
        mListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                topicDetailPre.getDataFromNet();
            }
        });
    }



    @OnClick({R.id.tv_left, R.id.tv_right, R.id.tv_more, R.id.img_back, R.id.img_share, R.id.rl_dianping})
    public void clickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                topicDetailPre.changText(-1);
                break;
            case R.id.tv_more:
                topicDetailPre.intentToMoreTopic();
                break;
            case R.id.tv_right:
                topicDetailPre.changText(1);
                break;
            case R.id.img_back:
                ActivityUtil.finishActivity(baseContext);
                break;
            case R.id.img_share:
               ToastUtil.showToastText("分享");
                break;
            case R.id.rl_dianping://跳转到点评界面
                topicDetailPre.intentToPublishOpinion();
                break;
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_TOPIC_DETAIL && resultCode == Constants.RESULT_SUCCESS) {
            topicDetailPre.getDataFromNet();   //重新请求网络
        }
        if (requestCode == 11 && resultCode == 11) {
            topicDetailPre.setData(data);
        }
    }

    @Override public void commentFinish(Common.Comment comment, int position) {
        //topicDetailPre.getDataFromNet();    //评论完成之后 最好不要重新请求网络
        mAdapter.addComment(comment, position);
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void refreshFinish() {
        mListView.refreshFinish();
    }

    @Override public void setTitleButtonEnable(boolean flag) {
        mTvMiddle.setEnabled(flag);
        mTvRight.setEnabled(flag);
        mTvLeft.setEnabled(flag);
    }

    @Override public void hidePage() {
        loadingPage.hidePage();
    }

    @Override public void initListView() {
        mAdapter = new TopicDetailAdapter(this, topicDetailPre);
        mListView.getListView().setAdapter(mAdapter);
    }

    @Override public void setLeftText(String s) {
        mTvLeft.setText(s);
    }

    @Override public void setMiddleText(String s) {
        mTvMiddle.setText(s);
    }

    @Override public void setRIghtText(String s) {
        mTvRight.setText(s);
    }

    @Override public void removeAllViews() {
        mAdapter.removeAllViews();
    }

    @Override public void addItem(Object item) {
        mAdapter.addItem(item);
    }

    @Override public void addAllItem(List items) {
        mAdapter.addAllItem(items);
    }

    @Override public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override public boolean titleButtonIsEnabled() {
        return mTvMiddle.isEnabled();
    }

    @Override public void showPage(int state) {
        loadingPage.showPage(state);
    }

    @Override public void openEditCommentView() {
        mCommentView.setVisibility(View.VISIBLE);
    }

    @Override public void setComment(String topicId, String opinionId, String user_id, String toUserId, int position, String nickName) {
        mCommentView.setComment(topicId, opinionId, user_id, toUserId, position, nickName);
        mCommentView.getFocus();
    }

    @Override public void setZaning(boolean zaning, int position) {
        mAdapter.setZaninig(zaning, position);
    }

    @Override public void setRightTextGone() {
        mTvRight.setVisibility(View.INVISIBLE);
    }

    @Override public void setLeftTextVisible() {
        mTvLeft.setVisibility(View.VISIBLE);
    }

    @Override public void setRightTextVisible() {
        mTvRight.setVisibility(View.VISIBLE);
    }

    @Override public void setLeftTextGone() {
        mTvLeft.setVisibility(View.INVISIBLE);
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
            case MotionEvent.ACTION_MOVE:
                float y = ev.getY();
                float dy = Math.abs(y - downY);
                if (!CommentView.keyBoardIsHide && dy > 5) {
                    UIUtils.hideKeyboard(baseContext);
                    mCommentView.quitFocus();
                }
        }
        return super.dispatchTouchEvent(ev);
    }
}
