package com.zhicai.byteera.activity.myhome.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.adapter.MyDynamicAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.myhome.interfaceview.MyDynamicActivityIV;
import com.zhicai.byteera.activity.myhome.presenter.MyDynamicActivityPre;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.HeadViewMain.LeftImgClickListner;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemLongClick;

/** Created by bing on 2015/5/11. */
@SuppressWarnings("unused")
public class MyDynamicActivity extends BaseActivity implements MyDynamicActivityIV {
    @Bind(R.id.list_view) ListView mListView;
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    private MyDynamicAdapter mAdapter;
    private MyDynamicActivityPre myDynamicActivityPre;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.my_dynamic_activity);
        ButterKnife.bind(this);
        this.myDynamicActivityPre = new MyDynamicActivityPre(this);
    }

    @Override
    protected void initData() {
        mAdapter = new MyDynamicAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setDividerHeight(0);
        myDynamicActivityPre.getDataFromNet();
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

    @OnItemLongClick(R.id.list_view)
    public boolean itemLongClickListener(int position){
        myDynamicActivityPre.showDeleteDialog(position);
        return false;
    }


    @Override public Activity getContext() {
        return this;
    }

    @Override public void setData(List<DynamicEntity> dynamicItemList) {
        mAdapter.setData(dynamicItemList);
        mAdapter.notifyDataSetChanged();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_DYNAMIC_FRAGMENT && resultCode == Constants.RESULT_SUCCESS) {
            int position = data.getIntExtra("position", 0);
            DynamicEntity entity = (DynamicEntity) data.getSerializableExtra("dynamic_entity");
            mAdapter.refreshItem(position, entity);
        }
    }
}
