package com.zhicai.byteera.activity.knowwealth.view;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.activity.knowwealth.presenter.KnowWealthDetailCommentPre;
import com.zhicai.byteera.activity.knowwealth.viewinterface.KnowWealthDetailCommentIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.widget.HasHeadLoadMoreListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/** Created by bing on 2015/5/5. */
public class KnowWealthDetailCommentActivity extends BaseActivity implements KnowWealthDetailCommentIV {
    @Bind(R.id.list_view) HasHeadLoadMoreListView mListView;
    @Bind(R.id.et_comment) EditText etComment;
    @Bind(R.id.rl_comment) LinearLayout rlComment;
    @Bind(R.id.tv_finish) TextView tvFinish;
    @Bind(R.id.tv_empty_comment) TextView tvEmptyComment;
    @Bind(R.id.top_head) View topHead;
    @Bind(R.id.rl_container) RelativeLayout rl_container;
    private KnowWealthDetailAdapter mAdapter;
    private KnowWealthDetailCommentPre knowWealthDetailCommentPre;

    protected void loadViewLayout() {
        setContentView(R.layout.know_wealth_detail_comment_activity);
        ButterKnife.bind(this);
        //去除头部的任务栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rl_container.getLayoutParams();
            params.setMargins(0, -UIUtils.getStatusHeight(this), 0, 0);
            rl_container.setLayoutParams(params);
        } else {
            topHead.setVisibility(View.GONE);
        }
        knowWealthDetailCommentPre = new KnowWealthDetailCommentPre(this);
        initListView();
    }

    private void initListView() {
        mAdapter = new KnowWealthDetailAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    protected void initData() {
        knowWealthDetailCommentPre.initData();
    }

    protected void updateUI() {

    }

    @OnClick({R.id.iv_back, R.id.iv_edit})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(Constants.RESULT_OK);
                ActivityUtil.finishActivity(baseContext);
                break;
            case R.id.iv_edit:
                openComment();
                break;
        }
    }


    protected void processLogic() {
        mListView.setLoadMoreDataListener(new HasHeadLoadMoreListView.LoadMoreDataListener() {
            @Override public void loadMore() {
                knowWealthDetailCommentPre.loadMoreListView();
            }
        });
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (TextUtils.isEmpty(etComment.getText().toString())) {
                    knowWealthDetailCommentPre.toastEmptyComment();
                } else {
                    knowWealthDetailCommentPre.sendComment(etComment.getText().toString());
                    rlComment.setVisibility(View.GONE);
                    UIUtils.hideKeyboard(baseContext);
                }
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    UIUtils.hideKeyboard(baseContext);
                    rlComment.setVisibility(View.GONE);
                }
                return false;
            }
        });
        tvEmptyComment.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    UIUtils.hideKeyboard(baseContext);
                    rlComment.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    private void openComment() {
        rlComment.setVisibility(View.VISIBLE);
        etComment.requestFocus();
        UIUtils.showKeyboard(baseContext, etComment);
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void showEmptyComment() {
        tvEmptyComment.setVisibility(View.VISIBLE);
    }

    @Override public void goneListView() {
        mListView.setVisibility(View.GONE);
    }

    @Override public void showListView() {
        mListView.setVisibility(View.VISIBLE);
    }

    @Override public void goneEmptyComment() {
        tvEmptyComment.setVisibility(View.GONE);
    }

    @Override public void refreshListView(List<DynamicCommentEntity> commentList) {
        mAdapter.setData(commentList);
        mAdapter.notifyDataSetChanged();

    }

    @Override public void toastEmptyComment() {
        ToastUtil.showToastText("评论的内容不能为空");
    }


    @Override public boolean startLoginActivity() {
        return isStartLoginActivity();
    }

    @Override public void setHerViewRightTextViewEnabled(boolean b) {
        //  mHeadTitle.getRightTextView().setEnabled(b);
    }

    @Override public void toastErrorComment() {
        ToastUtil.showToastText("评论失败，请重试");
    }

    @Override public void setMoreList(List<DynamicCommentEntity> commentItemEntities) {
        mAdapter.addAllItem(commentItemEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void loadComplete() {
        mListView.loadComplete();
    }
}
