package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.service.information.InformationSecondary;

/** Created by bing on 2015/5/3. */
public class MyCollectItem extends FrameLayout {
    private ImageView ivHead;
    private TextView tvContent;
    private TextView tvTime;

    public MyCollectItem(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.user_home_collect_item, this, true);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvTime = (TextView) findViewById(R.id.tv_time);
    }

    public void freshView(InformationSecondary.TZixun tZixun) {
        ImageLoader.getInstance().displayImage( tZixun.getImageUrl(), ivHead);
        tvContent.setText(tZixun.getTitle());
        tvTime.setText(StringUtil.checkTime((long)tZixun.getPublishTime() * 1000));
    }
}
