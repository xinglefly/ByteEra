package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.adapter.DynamicCommentDetailAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.DynamicCommentDetailIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.DynamicCommentDetailCommentPre;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.widget.HasHeadLoadMoreListView;
import com.zhicai.byteera.widget.HeadViewMain;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemLongClick;

/** Created by bing on 2015/5/5 */
public class DynamicCommentDetailActivity extends BaseActivity implements DynamicCommentDetailIV {
    @Bind(R.id.list_view) HasHeadLoadMoreListView mListView;
    @Bind(R.id.et_comment) EditText etComment;
    @Bind(R.id.tv_empty_comment) TextView tvEmptyComment;
    @Bind(R.id.head_title) HeadViewMain mHeadTitle;
    private DynamicCommentDetailAdapter mAdapter;
    private DynamicCommentDetailCommentPre dynamicCommentDetailCommentPre;
    private boolean hasDeleteComment = false;

    protected void loadViewLayout() {
        setContentView(R.layout.dynamic_comment_detail_activity);
        ButterKnife.bind(this);
        dynamicCommentDetailCommentPre = new DynamicCommentDetailCommentPre(this);
        mListView.setHeaderView(LayoutInflater.from(baseContext).inflate(R.layout.blank_view, null));
    }

    protected void initData() {
        dynamicCommentDetailCommentPre.getIntentData();
    }

    @Override public void initListView() {
        mAdapter = new DynamicCommentDetailAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @Override public void setData(List<DynamicCommentEntity> commmentItemList) {
        mAdapter.setData(commmentItemList);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void setHasDeleteComent(boolean hadDeleteComment) {
        hasDeleteComment = hadDeleteComment;
    }

    @Override public DynamicCommentEntity getItem(int position) {
        return mAdapter.getItem(position);
    }

    @OnItemLongClick(R.id.list_view)
    public boolean onItemLongClickListener(int position){
        position -= 1;
        dynamicCommentDetailCommentPre.showDeleteDialog(position);
        return false;
    }

    protected void updateUI() {

    }
    protected void processLogic() {
        mHeadTitle.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override public void onRightTextClick() {
             dynamicCommentDetailCommentPre.publishComent(etComment.getText().toString());
            }
        });

        mHeadTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                if (hasDeleteComment) {
                    dynamicCommentDetailCommentPre.returnToLastUI();
                }else{
                    UIUtils.hideKeyboard(baseContext);
                    ActivityUtil.finishActivity(baseContext);
                }
            }
        });

        mListView.setLoadMoreDataListener(new HasHeadLoadMoreListView.LoadMoreDataListener() {
            @Override public void loadMore() {
//                dynamicCommentDetailCommentPre.loadMoreListView();
            }
        });

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
        mHeadTitle.getRightTextView().setEnabled(b);
    }

    @Override public void toastErrorComment() {
       ToastUtil.showToastText("评论失败，请重试");
    }

    @Override public void setMoreList(List<DynamicCommentEntity> commentItemEntities) {
//        mAdapter.addAllItem(commentItemEntities);
//        mAdapter.notifyDataSetChanged();
    }

    @Override public void loadComplete() {
        mListView.loadComplete();
    }

    @Override public void onBackPressed() {
        UIUtils.hideKeyboard(baseContext);
        if (hasDeleteComment) {
            dynamicCommentDetailCommentPre.returnToLastUI();
        }
        super.onBackPressed();
    }
}
