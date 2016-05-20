package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.UserFansActivityIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.UserFansActivityPre;
import com.zhicai.byteera.activity.myhome.adapter.MyFriendAdapter;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.HeadViewMain.LeftImgClickListner;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;

/** Created by bing on 2015/5/16. */
@SuppressWarnings("unused")
public class UserFansActivity extends BaseActivity implements UserFansActivityIV {
    @Bind(R.id.list_view) ListView mListView;
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.rl_empty) RelativeLayout mRlEmpty;
    private MyFriendAdapter mAdapter;
    private UserFansActivityPre userFansActivityPre;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.my_fans_activity);
        ButterKnife.bind(this);
        userFansActivityPre = new UserFansActivityPre(this);
        mHeadView.setTitleName("他的粉丝");
    }

    @Override protected void initData() {
        userFansActivityPre.getIntentData();
        mAdapter = new MyFriendAdapter(this);
        mListView.setAdapter(mAdapter);
        userFansActivityPre.getDataFromNet();
    }


    @OnItemClick(R.id.list_view)
    public void onItemClickListener(int position){
        //跳转到用户关注的人的个人页
        userFansActivityPre.intentToUserHome(((UserInfoEntity) mAdapter.getItem(position)).getUserId());
    }

    @Override protected void updateUI() {

    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void showEmpty() {
        mRlEmpty.setVisibility(View.VISIBLE);
    }

    @Override public void goneEmpty() {
        mRlEmpty.setVisibility(View.GONE);
    }

    @Override public void setData(List<UserInfoEntity> userInfoEntities) {
        mAdapter.updateListView(userInfoEntities);
    }
}
