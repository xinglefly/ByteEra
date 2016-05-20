package com.zhicai.byteera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;

/**
 * Created by Administrator on 2015/7/2 0002.
 */
public class OrganizationLinearLayout extends LinearLayout {

    public OrganizationLinearLayout(Context context) {
        this(context, null);
    }

    public OrganizationLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    private void initView() {
        this.setOrientation(VERTICAL);
    }

    public void addItem(String name, String content) {
        View item = LayoutInflater.from(getContext()).inflate(R.layout.linear_item, null);
        TextView tvName = (TextView) item.findViewById(R.id.tv_name);
        TextView tvContent = (TextView) item.findViewById(R.id.tv_content);
        tvName.setText(name);
        tvContent.setText(content);
        this.addView(item);
    }
}
