package com.zhicai.byteera.activity.community.dynamic.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;

/**
 * 小组：取消关注
 */
public class ShowGroupWindow extends PopupWindow {
    public ShowGroupWindow(Activity activitys, View.OnClickListener itemOnclick) {
        super(activitys);
        Activity activity = activitys;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.item_group_popupwindows, null);
        LinearLayout ll_popup = (LinearLayout) contentView.findViewById(R.id.ll_popup);
        RelativeLayout parent = (RelativeLayout) contentView.findViewById(R.id.parent);
        TextView tv_cancle_joined = (TextView) contentView.findViewById(R.id.tv_cancle_joined);
        TextView tv_cancle = (TextView) contentView.findViewById(R.id.tv_cancle);


        //取消按钮
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //点击事件
        tv_cancle_joined.setOnClickListener(itemOnclick);

        //设置属性
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.ShowSelectPopAnimation);
        this.setContentView(contentView);



    }
}
