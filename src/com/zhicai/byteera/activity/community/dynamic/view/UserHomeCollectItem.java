package com.zhicai.byteera.activity.community.dynamic.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.bean.Consult;
import com.zhicai.byteera.activity.community.dynamic.presenter.UserHomeActivityPre;
import com.zhicai.byteera.commonutil.StringUtil;

/**
 * Created by bing on 2015/5/3.
 */
public class UserHomeCollectItem extends FrameLayout {
    private ImageView ivHead;
    private TextView tvContent;
    private TextView tvTime;

    public UserHomeCollectItem(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.user_home_collect_item, this, true);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvTime = (TextView) findViewById(R.id.tv_time);
    }

    public void refreshView(int position, Consult consult, UserHomeActivityPre userHomeActivityPre) {
        ImageLoader.getInstance().displayImage(consult.getAvatarUrl(), ivHead);
        tvContent.setText(consult.getTitle());
        tvTime.setText(StringUtil.checkTime(consult.getPublishTime() * 1000));
    }
}
