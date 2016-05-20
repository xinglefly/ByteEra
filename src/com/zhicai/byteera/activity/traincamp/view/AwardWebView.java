package com.zhicai.byteera.activity.traincamp.view;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.SDLog;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by zhenxing on 2015/5/12.
 */
@SuppressWarnings("unused")
public class AwardWebView extends BaseActivity {

    private static final String TAG = AwardWebView.class.getSimpleName();

    @Bind(R.id.web_award) WebView webView;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.awardwebview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("jumpurl");
        SDLog.d(TAG, "-web url-" + url);
        initWebView("https://"+ url);
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {

    }

    private void initWebView(String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }


}
