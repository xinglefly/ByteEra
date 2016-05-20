package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;

public class UserHomeCategoryView extends FrameLayout implements View.OnClickListener {
    private TextView tv_dynamic;
    private TextView tv_collect;

    public interface CategoryItemClick {
        void dynamicClick();

        void collectClick();
    }

    private CategoryItemClick listener;

    public void setOnCategoryItemClickListener(CategoryItemClick listener) {
        this.listener = listener;
    }

    private Context mContext;

    public UserHomeCategoryView(Context context) {
        this(context, null);
    }

    public UserHomeCategoryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserHomeCategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.user_home_category, this, true);

        findViewById(R.id.ll_dynamic).setOnClickListener(this);
        findViewById(R.id.ll_collect).setOnClickListener(this);

        tv_dynamic = (TextView) findViewById(R.id.tv_dynamic);
        tv_collect = (TextView) findViewById(R.id.tv_collect);

        tv_dynamic.setSelected(true);
        tv_dynamic.setTextColor(Color.parseColor("#ffffff"));


    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            switch (v.getId()) {
                case R.id.ll_dynamic:
                    tv_dynamic.setSelected(true);
                    tv_collect.setSelected(false);
                    listener.dynamicClick();
                    recetColor();
                    tv_dynamic.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case R.id.ll_collect:
                    tv_dynamic.setSelected(false);
                    tv_collect.setSelected(true);
                    listener.collectClick();
                    recetColor();
                    tv_collect.setTextColor(Color.parseColor("#ffffff"));
                    break;
            }
        }
    }

    private void recetColor() {
        tv_dynamic.setTextColor(Color.parseColor("#666666"));

        tv_collect.setTextColor(Color.parseColor("#666666"));
    }
}
