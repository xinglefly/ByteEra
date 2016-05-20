package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;

/**
 * Created by bing on 2015/4/21.
 */
public class MyHomeHeadView extends FrameLayout {
    private Context mContext;
    private ImageView ivHead;
    private TextView tvName;

    public interface IconClickListener {
        void iconClick();
    }

    private IconClickListener iconClickListener;

    public MyHomeHeadView(Context context) {
        this(context, null);
    }

    public MyHomeHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHomeHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.my_home_head, this, true);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        tvName = (TextView) findViewById(R.id.tv_name);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.top_head).setVisibility(View.VISIBLE);
        }
        ivHead.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iconClickListener.iconClick();
            }
        });
    }

    public void setIcon(String url) {
        if (!TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, ivHead);
        }
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setIconClickListener(IconClickListener iconClickListener) {
        this.iconClickListener = iconClickListener;
    }
}
