package com.zhicai.byteera.activity.knowwealth.view;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Bind;

/** Created by bing on 2015/5/28. */
public class WebViewActivity extends BaseActivity {
    @Bind(R.id.web_view) WebView mWebView;
    private String url;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.web_view_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("url");
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new HelloWebViewClient());
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {

    }

    private class HelloWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //loadingPage.hidePage();
        }
    }
}
