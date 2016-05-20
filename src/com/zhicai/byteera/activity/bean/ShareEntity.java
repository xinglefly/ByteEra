package com.zhicai.byteera.activity.bean;

import android.app.Activity;
import android.widget.Button;

import com.tencent.tauth.IUiListener;

import java.io.Serializable;

/**
 * Created by chenzhenxing on 16/1/21.
 */

@SuppressWarnings("unused")
public class ShareEntity implements Serializable {
    private Activity context;
    private String title;
    private String imageUrl;
    private String url;
    private Button btn;
    private IUiListener qqShareListener;


    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public IUiListener getQqShareListener() {
        return qqShareListener;
    }

    public void setQqShareListener(IUiListener qqShareListener) {
        this.qqShareListener = qqShareListener;
    }

    @Override
    public String toString() {
        return "ShareEntity{" +
                "context=" + context +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public ShareEntity(Activity context, String title, String imageUrl, String url,IUiListener qqShareListener) {
        this.btn = new Button(context);
        this.context = context;
        this.title = title;
        this.imageUrl = imageUrl;
        this.url = url;
        this.qqShareListener = qqShareListener;
    }
}
