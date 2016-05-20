package com.zhicai.byteera.activity.knowwealth.view;

import android.os.Build;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
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
import com.zhicai.byteera.widget.HasHeadLoadMoreListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** Created by bing on 2015/5/5. */
public class KnowWealthDetailCommentActivity2 extends BaseActivity {
    @Bind(R.id.et_comment) EditText etComment;
    @Bind(R.id.list_view) HasHeadLoadMoreListView mListView;
    @Bind(R.id.tv_empty_comment) TextView tvEmptyComment;
    @Bind(R.id.rl_comment) LinearLayout rlComment;
    @Bind(R.id.tv_finish) TextView tvFinish;
    @Bind(R.id.top_head) View topHead;
    @Bind(R.id.rl_container) RelativeLayout rl_container;
    private KnowWealthDetailAdapter mAdapter;
    private int commentIndex;
    private String zixunId;
    private List<DynamicCommentEntity> dynamicCommentEntities;
    private boolean isSend;


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
        initListView();
    }

    private void initListView() {
        mAdapter = new KnowWealthDetailAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @OnClick({R.id.iv_back, R.id.iv_edit})
    public void clickListener(View view) {
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

    protected void initData() {
        String userId = PreferenceUtils.getInstance(this).readUserInfo().getUser_id();
        zixunId = this.getIntent().getStringExtra("zixun_id");
        InformationSecondary.DetailsPageReq sec;
        if (!TextUtils.isEmpty(userId)) {
            sec = InformationSecondary.DetailsPageReq.newBuilder().setZixunId(zixunId).setUserId(userId).build();
        } else {
            sec = InformationSecondary.DetailsPageReq.newBuilder().setZixunId(zixunId).build();
        }
        TiangongClient.instance().send("chronos", "get_info", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final InformationSecondary.DetailsPage page = InformationSecondary.DetailsPage.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            if (page.getErrorno() == 0) {
                                if (page.getTotalCommentCnt() > 0) {
                                    commentIndex = page.getComment(page.getCommentCount() - 1).getCommentIndex();
                                    mListView.setVisibility(View.VISIBLE);
                                    tvEmptyComment.setVisibility(View.GONE);
                                    dynamicCommentEntities = ModelParseUtil.DynamicCommentEntityParse(page.getCommentList());
                                    mAdapter.setData(dynamicCommentEntities);
                                } else {
                                    tvEmptyComment.setVisibility(View.VISIBLE);
                                    mListView.setVisibility(View.GONE);
                                }
                            } else {
                                ToastUtil.showToastText("数据获取失败...");
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void updateUI() {

    }

    protected void processLogic() {
        mListView.setLoadMoreDataListener(new HasHeadLoadMoreListView.LoadMoreDataListener() {
            @Override public void loadMore() {
            }
        });
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (TextUtils.isEmpty(etComment.getText().toString())) {
                    ToastUtil.showToastText("评论的内容不能为空");
                } else {
                    if (!isSend) {
                        sendComment();
                        rlComment.setVisibility(View.GONE);
                        UIUtils.hideKeyboard(baseContext);
                    }

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

    private void sendComment() {
        isSend = true;
        if (!isStartLoginActivity()) {
            InformationSecondary.Z_DoComment sec = InformationSecondary.Z_DoComment.newBuilder().setZixunId(zixunId).setUserId(userId).setContent(etComment.getText().toString()).build();
            TiangongClient.instance().send("chronos", "zixun_do_comment", sec.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        final InformationSecondary.Z_DoCommentResponse response = InformationSecondary.Z_DoCommentResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            public void run() {
                                isSend = false;
                                if (response.getErrorno() == 0) {
                                    ToastUtil.showToastText("评论成功");
                                    //TODO 每日任务，评论一篇文章
                                    UIUtils.hideKeyboard(baseContext);
                                    etComment.setText("");
                                    localRefresh(response);
                                } else {
                                    ToastUtil.showToastText("评论失败，请重试");
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

    private void localRefresh(InformationSecondary.Z_DoCommentResponse response) {
        Common.Comment comment = response.getComment();
        DynamicCommentEntity entity = new DynamicCommentEntity();
        entity.setContent(comment.getContent());
        entity.setAvatarUrl(comment.getHeadPortrait());
        entity.setCommentIndex(comment.getCommentIndex());
        entity.setNickName(comment.getNickname());
        entity.setUserId(comment.getUserId());
        entity.setPublishTime(comment.getPublishTime());
        dynamicCommentEntities.add(0, entity);
        mAdapter.notifyDataSetChanged();
    }

    private void openComment() {
        etComment.requestFocus();
        UIUtils.showKeyboard(baseContext, etComment);
        rlComment.setVisibility(View.VISIBLE);
    }

}
