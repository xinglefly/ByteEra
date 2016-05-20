package com.zhicai.byteera.activity.myhome.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.HeadViewMain.LeftImgClickListner;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by bing on 2015/5/21.
 */
public class MyTixianActivity extends BaseActivity {
    @Bind(R.id.list_view) ListView mListView;
    @Bind(R.id.head_view) HeadViewMain mHeadView;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.my_tixian_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initListView();
    }

    private void initListView() {
        TixianAdapter mAdapter = new TixianAdapter(baseContext, R.layout.my_tixian_item);
        mAdapter.add(new TixianEntity("03-21", "￥3340", "工行", 0));
        mAdapter.add(new TixianEntity("01-22", "￥310", "建行", 1));
        mAdapter.add(new TixianEntity("11-21", "￥2450", "农行", 0));
        mAdapter.add(new TixianEntity("03-11", "￥340", "交行", 1));
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mHeadView.setLeftImgClickListener(new LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    private class TixianAdapter extends ArrayAdapter<TixianEntity> {
        private int resource;

        public TixianAdapter(Context context, int resource) {
            super(context, resource);
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TixianEntity entity = getItem(position);
            View view = LayoutInflater.from(baseContext).inflate(resource, null);
            TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
            TextView tvMoney = (TextView) view.findViewById(R.id.tv_money);
            TextView tvAccount = (TextView) view.findViewById(R.id.tv_account);
            TextView tvStatus = (TextView) view.findViewById(R.id.tv_status);

            tvAccount.setText(entity.getAccount());
            tvDate.setText(entity.getDate());
            tvMoney.setText(entity.getMoney());
            if (entity.getStatus() == 0) {
                tvStatus.setText("完成");
            } else {
                tvStatus.setText("未完成");
            }
            return view;
        }
    }
}
