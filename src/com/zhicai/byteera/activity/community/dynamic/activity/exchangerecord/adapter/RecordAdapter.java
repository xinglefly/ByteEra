package com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.community.dynamic.entity.ExchangeRecordEntity;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenzhenxing on 15/12/14.
 */
public class RecordAdapter extends BaseAdapter {
    private Context context;
    private int res;
    private List<ExchangeRecordEntity> entityList;

    public RecordAdapter(Context context, int textViewResourceId, List<ExchangeRecordEntity> entityList) {
        this.context = context;
        this.res = textViewResourceId;
        this.entityList = entityList;
    }

    @Override
    public int getCount() {
        return entityList.size();
    }

    @Override
    public Object getItem(int position) {
        return entityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {convertView = LayoutInflater.from(context).inflate(res, null);}
        ImageView ivShoppingPic = ViewHolder.get(convertView, R.id.iv_shopping_pic);
        TextView ivShoppingName = ViewHolder.get(convertView, R.id.tv_shopping_name);
        TextView ivShoppingTime = ViewHolder.get(convertView, R.id.tv_shopping_time);
        TextView tvDeliverCompany = ViewHolder.get(convertView, R.id.tv_deliver_company);
        TextView tvDeliverSN = ViewHolder.get(convertView, R.id.tv_deliver_sn);
        LinearLayout llXuni = ViewHolder.get(convertView, R.id.ll_xuni);
        TextView tvXuniCode = ViewHolder.get(convertView, R.id.tv_xuni_code);
        TextView tvXuniCopy = ViewHolder.get(convertView, R.id.tv_xuni_copy);
        TextView tvShow = ViewHolder.get(convertView, R.id.tv_show);


        tvShow.getBackground().setAlpha(200);
        final ExchangeRecordEntity exchangeRecordEntity = entityList.get(position);
        ImageLoader.getInstance().displayImage(exchangeRecordEntity.getItem_image(), ivShoppingPic);
        ivShoppingName.setText(exchangeRecordEntity.getItem_name());
        ivShoppingTime.setText("兑换时间: " + checkTime(exchangeRecordEntity.getCreate_time() * 1000));
        if (exchangeRecordEntity.getItem_type()==1){
            tvShow.setText("实物商品");
            llXuni.setVisibility(View.GONE);
            tvDeliverCompany.setVisibility(View.VISIBLE);
            tvDeliverSN.setVisibility(View.VISIBLE);
            tvDeliverCompany.setText("快递:" + exchangeRecordEntity.getDeliver_company());
            tvDeliverSN.setText("单号:"+exchangeRecordEntity.getDeliver_sn());
        }else if(exchangeRecordEntity.getItem_type()==2){
            tvShow.setText("虚拟道具");
            llXuni.setVisibility(View.VISIBLE);
            tvDeliverCompany.setVisibility(View.GONE);
            tvDeliverSN.setVisibility(View.GONE);
            tvXuniCode.setText("兑换码:" + exchangeRecordEntity.getExchange_item_content());
            tvXuniCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToastText("复制成功!");
                    ClipboardManager cm =(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(exchangeRecordEntity.getExchange_item_content());
                }
            });
        }
        return convertView;
    }



    public  String checkTime(long time) {
        time += 8*60*60*1000;
        Date date = new Date(time);
        Calendar now = Calendar.getInstance();
        int currentDayOfYear = now.get(Calendar.DAY_OF_YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        return format.format(time);
    }
}