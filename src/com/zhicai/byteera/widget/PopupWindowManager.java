package com.zhicai.byteera.widget;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.bean.ShareEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class PopupWindowManager implements OnClickListener{

    private ShareEntity shareEntity;
    private PopupWindow mPopupWindow;
    private View view;
    private static final int THUMB_SIZE = 150;

    public PopupWindowManager(ShareEntity shareEntity) {
        this.shareEntity = shareEntity;
        initView();
        settingPupOption();
    }



    private void initView() {
        view = LayoutInflater.from(shareEntity.getContext()).inflate( R.layout.share_layout, null);
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setAnimationStyle(R.style.AnimBottom);
            ButterKnife.findById(view, R.id.tv_wx).setOnClickListener(this);
            ButterKnife.findById(view, R.id.tv_wxfriend).setOnClickListener(this);
            ButterKnife.findById(view, R.id.tv_qq).setOnClickListener(this);
            ButterKnife.findById(view, R.id.tv_qzone).setOnClickListener(this);
        }
    }

    private void settingPupOption() {
        ColorDrawable drawable = new ColorDrawable(0x00000000);
        mPopupWindow.setBackgroundDrawable(drawable);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(shareEntity.getBtn(), Gravity.BOTTOM, 0, 0);
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.share_bottom_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP && y < height) popupWindowDismiss();
                return true;
            }
        });
    }


    @Override
    public void onClick(View v) {
        popupWindowDismiss();
        final Bundle params = new Bundle();
        switch (v.getId()){
            case R.id.tv_wx:
                WXShare(false);
                break;
            case R.id.tv_wxfriend:
                WXShare(true);
                break;
            case R.id.tv_qq:
                qqShare(params);
                break;
            case R.id.tv_qzone:
                qzoneShare(params);
                break;
        }
    }

    public void popupWindowDismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    private void WXShare(boolean isPublishFriend) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareEntity.getUrl();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareEntity.getTitle();

        Bitmap bitmap = Bitmap.createScaledBitmap(ImageLoader.getInstance().loadImageSync(shareEntity.getImageUrl()), THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = Util.bmpToByteArray(bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isPublishFriend?SendMessageToWX.Req.WXSceneTimeline:SendMessageToWX.Req.WXSceneSession;
        MyApp.getInstance().api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void qzoneShare(Bundle params) {
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareEntity.getTitle());
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareEntity.getUrl());
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, new ArrayList<String>());
        MyApp.getInstance().mTencent.shareToQzone(shareEntity.getContext(), params, shareEntity.getQqShareListener());
    }

    private void qqShare(Bundle params) {
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareEntity.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareEntity.getUrl());
        MyApp.getInstance().mTencent.shareToQQ(shareEntity.getContext(), params, shareEntity.getQqShareListener());
    }




}
