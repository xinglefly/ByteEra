package com.zhicai.byteera.activity.knowwealth.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.bean.BannerEntity;
import com.zhicai.byteera.activity.bean.Consult;
import com.zhicai.byteera.activity.knowwealth.presenter.KnowWealthPre;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/** Created by bing on 2015/6/9. */
public class KnowWealthAdpater extends BaseAdapter {
    private static final int BANNER_VIEW = 0;
    private static final int ITEM_LIST = 1;
    private Context mContext;
    private KnowWealthPre knowWealthPre;
    private List mList;


    public KnowWealthAdpater(Context mContext, KnowWealthPre knowWealthPre) {
        this.mContext = mContext;
        this.knowWealthPre = knowWealthPre;
        mList = new ArrayList();
    }


    public void setData(List list) {
        this.mList = list;
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public Object getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            if (type == BANNER_VIEW) convertView = new BannerView(mContext, knowWealthPre);
            if (type == ITEM_LIST) convertView = LayoutInflater.from(mContext).inflate(R.layout.knowwealth_info_item, null);
        }

        if (type == BANNER_VIEW) convertView = setBannerView(position, convertView);
        if (type == ITEM_LIST) convertView = setItemView(position, convertView);

        return convertView;
    }

    private View setBannerView(int position, View convertView) {
        List<BannerEntity> bannerList = (List<BannerEntity>) mList.get(position);
        ((BannerView) convertView).refreshView(bannerList);
        return convertView;
    }


    private View setItemView(int position, View convertView) {
        ImageView imgInfo = ViewHolder.get(convertView, R.id.img_info);
        TextView tvInfoTitle = ViewHolder.get(convertView, R.id.tv_info_title);
        TextView tvInfoTime = ViewHolder.get(convertView, R.id.tv_info_time);
        TextView tvRevertCount = ViewHolder.get(convertView, R.id.tv_revert_count);
        TextView tvType = ViewHolder.get(convertView, R.id.tv_type);

        final Consult consult = (Consult) mList.get(position);
        tvInfoTitle.setText(consult.getTitle());
        tvInfoTime.setText(StringUtil.checkTime(consult.getPublishTime() * 1000));
        tvRevertCount.setText(consult.getCommentNum() + "");
        ImageLoader.getInstance().displayImage(consult.getAvatarUrl(), imgInfo);

        switch (consult.getProdutType()){
            case 1:
                tvType.setText("全部");
                break;
            case 2:
                tvType.setText("P2P");
                break;
            case 3:
                tvType.setText("众筹");
                break;
            case 5:
                tvType.setText("直销银行");
                break;
            case 7:
                tvType.setText("互联网理财");
                break;
            case 9:
                tvType.setText("消费金融");
                break;
        }

        tvRevertCount.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                knowWealthPre.intenttoComment(consult);
            }
        });
        return convertView;
    }

    public void removeAllViews() {
        this.mList.clear();
    }

    public void addItem(Object item) {
        this.mList.add(item);
    }

    @Override public int getViewTypeCount() {
        return 2;
    }

    @Override public int getItemViewType(int position) {
        if (mList.get(position) instanceof List) return BANNER_VIEW;
        else return ITEM_LIST;
    }
}
