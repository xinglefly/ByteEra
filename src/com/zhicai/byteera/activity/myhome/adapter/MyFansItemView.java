package com.zhicai.byteera.activity.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.classroom.view.BaseHolder;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;

/**
 * Created by bing on 2015/5/16.
 */
public class MyFansItemView extends BaseHolder {

    private ImageView ivAvatar;
    private TextView tvTitle;
    private ImageView ivStatus;

    public MyFansItemView(Context context) {
        super(context);
    }

    public MyFansItemView(Context context, Object mData) {
        super(context, mData);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_fans_item, null);
        ivAvatar = (ImageView) inflate.findViewById(R.id.iv_avatar);
        tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) inflate.findViewById(R.id.tv_content);
        ivStatus = (ImageView) inflate.findViewById(R.id.iv_status);
        return inflate;
    }

    @Override
    public void refreshView() {
        UserInfoEntity data = (UserInfoEntity) getData();
        ImageLoader.getInstance().displayImage( data.getAvatarUrl(), ivAvatar);

        tvTitle.setText(data.getNickName());
        // tvContent.setText(data.getContent());
        if (data.getmStatus() == UserInfoEntity.Status.ADD_ATTENTION) {
            ivStatus.setImageResource(R.drawable.card_icon_addattention);
        } else {
            ivStatus.setImageResource(R.drawable.card_icon_arrow);
        }

    }
}
