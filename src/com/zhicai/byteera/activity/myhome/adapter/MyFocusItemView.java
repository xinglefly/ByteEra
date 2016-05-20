package com.zhicai.byteera.activity.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.classroom.view.BaseHolder;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;

/**
 * Created by bing on 2015/5/16.
 */
public class MyFocusItemView extends BaseHolder {

    private ImageView ivAvatar;
    private TextView tvTitle;
    private ImageView ivStatus;

    public MyFocusItemView(Context context) {
        super(context);
    }

    public MyFocusItemView(Context context, Object mData) {
        super(context, mData);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_focus_item, null);
        ivAvatar = (ImageView) inflate.findViewById(R.id.iv_avatar);
        tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) inflate.findViewById(R.id.tv_content);
        ivStatus = (ImageView) inflate.findViewById(R.id.iv_status);
        return inflate;
    }

    @Override
    public void refreshView() {
        UserInfoEntity data = (UserInfoEntity) getData();

    }
}
