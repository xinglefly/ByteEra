package com.zhicai.byteera.activity.product;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JumpWebActivity extends BaseActivity {

    @Bind(R.id.head_title) HeadViewMain mHeadTitle;
    @Bind(R.id.web_view) WebView mWebView;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.jump_web_activity);
        ButterKnife.bind(this);
    }


    @Override protected void initData() {
        Bundle extras = getIntent().getExtras();
        mHeadTitle.setTitleName(extras.getString("name"));
        initWebView(extras.getString("url"));
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mHeadTitle.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }


    private void initWebView(final String url) {
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setSupportZoom(true);
        setting.setBuiltInZoomControls(true);
        setting.setDefaultTextEncodingName("utf-8");
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.loadUrl(url);
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
}
