package com.zhicai.byteera.activity.knowwealth.view;

import android.app.Activity;
import android.content.Intent;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.activity.knowwealth.presenter.KnowWealthDetailPre;
import com.zhicai.byteera.activity.knowwealth.viewinterface.KnowWealthDeatailIV;
import com.zhicai.byteera.activity.myhome.widget.ShareInnerDialog;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.LogUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.information.InformationSecondary;
import com.zhicai.byteera.widget.KnowWeathDetailBottonLine;
import com.zhicai.byteera.widget.LoadingPage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemLongClick;

/** Created by bing on 2015/4/15. */
@SuppressWarnings("unused")
public class KnowWealthDetailActivity extends BaseActivity implements KnowWealthDeatailIV,IWXAPIEventHandler {

    @Bind(R.id.list_view) KnowWealthDetailListView listView;
    @Bind(R.id.loading_page) LoadingPage loadingPage;
    @Bind(R.id.botton_line) KnowWeathDetailBottonLine bottonLine;

    public static final int FRESH_ALL = 2;
    public static final int FRESH_LIST = 3;
    private KnowWealthDetailAdapter mAdapter;
    private KnowWealthDetailPre knowWealthDetailPre;
    private ShareInnerDialog shareInnerDialog;


    protected void loadViewLayout() {
        setContentView(R.layout.activity_know_weath_detail);
        ButterKnife.bind(this);
        knowWealthDetailPre = new KnowWealthDetailPre(this);
        shareInnerDialog = new ShareInnerDialog(this);
        MobclickAgent.onEventValue(baseContext, "KnowWealthDetailActivity", null, 4);
    }

    protected void initData() {
        loadingPage.showPage(Constants.STATE_LOADING);
        knowWealthDetailPre.getIntentData();
        knowWealthDetailPre.getDetailData(FRESH_ALL);
    }

    protected void updateUI() {

    }

    @OnClick(R.id.iv_back)
    public void clickListener(){
        ActivityUtil.finishActivity(baseContext);
    }

    @OnItemLongClick(R.id.list_view)
    public boolean onItemLongClick(int position){
        position -= 1;  //不知道 为什么  反正是要减去1
        if (mAdapter.getItem(position) instanceof DynamicCommentEntity) {
            if (((DynamicCommentEntity) mAdapter.getItem(position)).getNickName().equals(zcUser.getNickname())) {
                knowWealthDetailPre.showDeleteDialog(position);
            }
        }
        return false;
    }

    protected void processLogic() {

        listView.setLoadMoreDataListener(new KnowWealthDetailListView.LoadMoreDataListener() {
            @Override
            public void loadMore() {
                knowWealthDetailPre.loadMoreListView();
            }
        });

        bottonLine.setButtonLineClickListener(new KnowWeathDetailBottonLine.ButtonLineClickListener() {

            public void onBackClick() {
                ActivityUtil.finishActivity(baseContext);
            }

            /*底部点击跳转到评论界面*/
            public void onCommentClick() {
                if (!isStartLoginActivity()) {
                    knowWealthDetailPre.intentToDetailCommentActivity();
                }
            }

            /*点击进行收藏*/
            public void onPraiseClick() {
                knowWealthDetailPre.changCollect();
            }

            /*分享内部链接*/
            public void onTranspondClick() {
                shareInnerDialog.show();
            }

            /*分享*/
            public void onShareClick() {
                knowWealthDetailPre.ShareAPI(qqShareListener);
            }
        });

        shareInnerDialog.setOkClickListener(new ShareInnerDialog.OnOkClickListener() {
            @Override
            public void onOk(String text) {
                knowWealthDetailPre.share(text);
            }
        });
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void showErrorPage() {
        loadingPage.showPage(Constants.STATE_ERROR);
    }

    @Override public void setComment(int commentCount) {
        listView.setComment(commentCount);
        bottonLine.setConmmentNum(commentCount + ""); //底部button显示的评论条数更新

    }

    @Override public void freshListView(List<DynamicCommentEntity> commentList) {
        mAdapter.setData(commentList);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void initListView(InformationSecondary.DetailsPage page, final List<DynamicCommentEntity> commentItemEntities) {
        boolean isCollect = page.getIsCollect();
        String detailUrl = page.getDetailUrl();
        int commentCount = page.getTotalCommentCnt();
        if (isCollect) {
            bottonLine.getCollectionButtom().setImageResource(R.drawable.know_xihuan);
        } else {
            bottonLine.getCollectionButtom().setImageResource(R.drawable.nolike);
        }
        listView.setWebViewLoadCompleteListener(detailUrl, new KnowWealthDetailListView.WebViewLoadCompleteListener() {
            @Override public void loadComplete() {
                MyApp.getMainThreadHandler().postDelayed(new Runnable() {
                    @Override public void run() {
                        mAdapter = new KnowWealthDetailAdapter(baseContext);
                        if (commentItemEntities != null) {
                            mAdapter.addAllItem(commentItemEntities);
                            listView.setAdapter(mAdapter);
                            loadingPage.hidePage();
                        }
                    }
                }, 200);
            }
        });
        listView.setRatingBar();
        listView.setComment(commentCount);
        bottonLine.setConmmentNum(commentCount + "");
    }


    @Override public int getCommentIndex(int position) {
        return ((DynamicCommentEntity) mAdapter.getItem(position)).getCommentIndex();
    }

    @Override public void removeItemAtPosition(int position) {
        mAdapter.removeItemAtPosition(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void loadMoreListView(List<DynamicCommentEntity> commentItemEntities) {
        mAdapter.addAllItem(commentItemEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void loadComplete() {
        listView.loadComplete();
    }

    @Override public void loadError() {
        listView.loadError();
    }

    @Override public boolean startLoginActivity() {
        return isStartLoginActivity();
    }

    @Override public void changeCollectionButomUnCollect() {
        bottonLine.getCollectionButtom().setImageResource(R.drawable.nolike);
    }

    @Override public void changeCollectionButomCollect() {
        bottonLine.getCollectionButtom().setImageResource(R.drawable.know_xihuan);
    }

    @Override public void dismissShareDialog() {
        shareInnerDialog.dismiss();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.KNOW_WEALTH_DETAIL && resultCode == Constants.RESULT_SUCCESS)
            knowWealthDetailPre.getDetailData(FRESH_LIST);
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_QZONE_SHARE || requestCode == com.tencent.connect.common.Constants.REQUEST_QQ_SHARE)
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);

    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.d("resp errCode : %s",baseReq.getType());
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                ToastUtil.showLongToastText("get数据");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                ToastUtil.showLongToastText("show数据");
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.d("resp errCode : %s",baseResp.errCode);
        String result;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "发送成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }
        ToastUtil.showLongToastText(result);
    }

    IUiListener qqShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            LogUtil.d("onCancle");
            ToastUtil.showLongToastText("取消分享！");
        }
        @Override
        public void onComplete(Object response) {
            LogUtil.d("onComplete:%s", response.toString());
            dialog.show();
            dialog.setResultStatusDrawable(true, "成功分享！");
        }
        @Override
        public void onError(UiError e) {
            LogUtil.e("onError : %s", e.errorMessage);
            ToastUtil.showLongToastText("分享失败！！");
        }
    };

}
