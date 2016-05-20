package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.UserFocusActivityIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.UserFocusActivityPre;
import com.zhicai.byteera.activity.myhome.adapter.MyFriendAdapter;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.HeadViewMain.LeftImgClickListner;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;

/** Created by bing on 2015/5/16. */
public class UserFocusActivity extends BaseActivity implements UserFocusActivityIV {
    @Bind(R.id.list_view) ListView mListView;
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.rl_empty) RelativeLayout mRlEmpty;
    private MyFriendAdapter mAdapter;
    private UserFocusActivityPre userFocusActivityPre;
    private String hisUserId;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.my_focus_activity);
        ButterKnife.bind(this);
        userFocusActivityPre = new UserFocusActivityPre(this);
        mHeadView.setTitleName("他的关注");
    }

    @Override
    protected void initData() {
        hisUserId = getIntent().getStringExtra("user_id");
        userFocusActivityPre.getDataFromNet(hisUserId);
        mAdapter = new MyFriendAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @OnItemClick(R.id.list_view)
    public void onItemClickListener(int position){
        UserInfoEntity userInfoEntity = mAdapter.getItem(position);
        if (userInfoEntity.getUserType() == 1) {
            Intent intent = new Intent(baseContext, UserHomeActivity.class);
            intent.putExtra("other_user_id", userInfoEntity.getUserId());
            ActivityUtil.startActivity(baseContext, intent);
        } else if (userInfoEntity.getUserType() == 2) {
            Intent intent = new Intent(baseContext, OrganizationHomeActivity.class);
            intent.putExtra("other_user_id", userInfoEntity.getUserId());
            ActivityUtil.startActivity(baseContext, intent);
        }
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mHeadView.setLeftImgClickListener(new LeftImgClickListner() {
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

    @Override public void hideEmpty() {
        mRlEmpty.setVisibility(View.GONE);
    }

    @Override public void setData(List<UserInfoEntity> userInfoEntities) {
        mAdapter.updateListView(userInfoEntities);
    }
}
