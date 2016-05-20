package com.zhicai.byteera.activity.initialize;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.LoadingPage;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by bing on 2015/5/14.
 */
public class UserProtocolActivity extends BaseActivity {
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.web_view) WebView mWebView;
    @Bind(R.id.loading_page) LoadingPage mLoadingPage;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.user_protocol_activity);
        ButterKnife.bind(this);
        mLoadingPage.showPage(Constants.STATE_LOADING);
    }

    private void initWebView() {
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDefaultTextEncodingName("utf-8");//设置字符编码
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
        mWebView.loadUrl("http://119.254.108.59:9000//system/agreement/1/preview/");
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
                if (progress == 100) {
                    mLoadingPage.hidePage();
                }
                super.onProgressChanged(view, progress);
            }
        });
    }

    @Override
    protected void initData() {
        initWebView();
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }
}
