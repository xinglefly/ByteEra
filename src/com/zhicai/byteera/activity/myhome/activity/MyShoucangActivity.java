package com.zhicai.byteera.activity.myhome.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailActivity;
import com.zhicai.byteera.activity.myhome.adapter.MyCollectAdapter;
import com.zhicai.byteera.activity.myhome.interfaceview.MyShoucangActivityIV;
import com.zhicai.byteera.activity.myhome.presenter.MyShoucangActivityPre;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.service.information.InformationSecondary;
import com.zhicai.byteera.widget.HeadViewMain;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

/** Created by bing on 2015/5/11. */
@SuppressWarnings("unused")
public class MyShoucangActivity extends BaseActivity implements MyShoucangActivityIV {
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.list_view) ListView mListView;
    private MyCollectAdapter mAdapter;
    private MyShoucangActivityPre myShoucangActivityPre;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.shou_cang_acitivity);
        ButterKnife.bind(this);
        myShoucangActivityPre = new MyShoucangActivityPre(this);
    }

    @Override protected void initData() {
        mAdapter = new MyCollectAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setDividerHeight(0);
        myShoucangActivityPre.getDataFromNet();
    }

    @OnItemClick(R.id.list_view)
    public void onItemClickListener(int position){
        Intent intent = new Intent(baseContext, KnowWealthDetailActivity.class);
        InformationSecondary.TZixun item = mAdapter.getItem(position);
        intent.putExtra("zixun_id", item.getZixunId());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("imgUrl", item.getImageUrl());
        ActivityUtil.startActivity(baseContext, intent);
    }

    @OnItemLongClick(R.id.list_view)
    public boolean onItemLongClickListener(int position){
        myShoucangActivityPre.showDeleteDialog(position);
        return true;
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void setData(List<InformationSecondary.TZixun> zixunList) {
        mAdapter.setData(zixunList);
        mAdapter.notifyDataSetChanged();
    }
}
