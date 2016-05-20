package com.zhicai.byteera.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.UIUtils;

/**
 * Created by bing on 2015/5/14.
 */
public class TabItemView extends LinearLayout {

    private ImageView img;
    private TextView textView;

    public TabItemView(Context context) {
        this(context, null);
    }

    public TabItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        this.setOrientation(VERTICAL);
        this.setPadding(UIUtils.dip2px(getContext(), 2), UIUtils.dip2px(getContext(), 2), UIUtils.dip2px(getContext(), 2), UIUtils.dip2px(getContext(), 2));
        this.setGravity(Gravity.CENTER);
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.TabItemView);
        Drawable buttonImg = attributes.getDrawable(R.styleable.TabItemView_ButtonImage);
        String text = attributes.getString(R.styleable.TabItemView_text);

        LayoutInflater.from(getContext()).inflate(R.layout.tab_item_view, this, true);
        img = (ImageView) findViewById(R.id.imageview);
        img.setImageDrawable(buttonImg);
        textView = (TextView) findViewById(R.id.textview);
        textView.setText(text);
    }

    public void select() {
        img.setSelected(true);
        textView.setSelected(true);
        textView.setTextColor(Color.parseColor("#30d0df"));
    }
    public void unSelect(){
        img.setSelected(false);
        textView.setSelected(false);
        textView.setTextColor(Color.parseColor("#c0c9ca"));
    }
}
