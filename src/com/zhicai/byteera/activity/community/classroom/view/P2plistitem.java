package com.zhicai.byteera.activity.community.classroom.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.classroom.entity.P2pListEntity;

/**
 * Created by bing on 2015/4/19.
 */
public class P2plistitem extends BaseHolder {

    private TextView title;
    private TextView content;
    private ImageView img;

    public P2plistitem(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.p2p_item, null);
        title = (TextView) view.findViewById(R.id.tv_p2p_title);
        content = (TextView) view.findViewById(R.id.tv_p2p_explain);
        img = (ImageView) view.findViewById(R.id.img_p2p_head);
        return view;
    }

    @Override
    public void refreshView() {
        P2pListEntity entity = (P2pListEntity) getData();
        title.setText(entity.getTitle());
        content.setText(entity.getContent());
        img.setImageResource(entity.getImgId());
    }
}
