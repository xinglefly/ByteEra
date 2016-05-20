package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.adapter.GroupHomeAdapter;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.GroupHomeActivityIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.GroupHomeActivityPre;
import com.zhicai.byteera.activity.community.eventbus.GroupEvent;
import com.zhicai.byteera.activity.community.eventbus.PublishGroupDynamicEvent;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.widget.HasHeadLoadMoreListView;
import com.zhicai.byteera.widget.HeadViewMain;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

@SuppressWarnings("unused")
public class GroupHomeActivity extends BaseActivity implements GroupHomeActivityIV {
    private static final String TAG = GroupHomeActivity.class.getSimpleName();

    @Bind(R.id.list_view) HasHeadLoadMoreListView mListView;
    @Bind(R.id.title_view) HeadViewMain mtitleView;
    @Bind(R.id.iv_head) ImageView mAvatar;
    @Bind(R.id.tv_des) TextView tvDes;
    @Bind(R.id.tv_fans_num) TextView tvFansNum;
    @Bind(R.id.tv_focus) TextView tvFocus;
    @Bind(R.id.iv_right) ImageView ivRight;
    @Bind(R.id.rl_dianping) RelativeLayout rlDianPin;
    @Bind(R.id.scroll_group) ScrollView scrollGroup;


    private GroupHomeActivityPre groupHomeActivityPre;
    private GroupHomeAdapter mAdapter;

    private boolean showBottom = true;


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_group_home);
        ButterKnife.bind(this);
        this.groupHomeActivityPre = new GroupHomeActivityPre(this);
        initListView();

        EventBus.getDefault().register(this);
    }


    private void initListView() {
//        mListView.setHeaderView(LayoutInflater.from(baseContext).inflate(R.layout.group_listview_header, null));
        mAdapter = new GroupHomeAdapter(this,groupHomeActivityPre);
        mListView.setAdapter(mAdapter);
        setListViewHeightBasedOnChildren(mListView);
    }

    @Override
    protected void initData() {
        groupHomeActivityPre.getIntentData();
        groupHomeActivityPre.setGroupAttributes();
        groupHomeActivityPre.getDynamicFromNet(true);
        groupHomeActivityPre.getPositionData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        groupHomeActivityPre.getDynamicFromNet(true);
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {

        mListView.setOnScrollListener(new HasHeadLoadMoreListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    showBottom = true;
                    groupHomeActivityPre.showBottom(rlDianPin);
                } else {
                    if (showBottom) {
                        showBottom = false;
                        groupHomeActivityPre.hideBottom(rlDianPin);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mtitleView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }

    @OnClick({R.id.tv_focus,R.id.iv_right,R.id.tv_fans_num,R.id.rl_dianping})
    public void clickListener(View view){
        switch (view.getId()){
            case R.id.tv_focus:
                groupHomeActivityPre.toJoined();
                break;
            case R.id.iv_right:
                groupHomeActivityPre.intentToSecondActivity();
                break;
            case R.id.tv_fans_num:
                //TODO 点击查看小组的粉丝
                break;
            case R.id.rl_dianping: //发表动态
                groupHomeActivityPre.intentToPublish();
                break;
        }
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void setData(List<DynamicEntity> dynamicEntities) {
        mAdapter.setData(dynamicEntities);
    }

    @Override public void addAllItem(List<DynamicEntity> dynamicEntities) {
        mAdapter.addAllItem(dynamicEntities);
    }

    @Override public void loadComplete() {
        mListView.loadComplete();
    }

    @Override public void setTitleName(String name) {
        mtitleView.setTitleName(name);
    }


    @Override public void setAvatar(String avatarUrl) {
        ImageLoader.getInstance().displayImage(avatarUrl, mAvatar);
    }

    @Override
    public void setDescrition(String descrition) {
        tvDes.setText(descrition);
    }

    @Override
    public void setFansCount(int fansCount) {
        tvFansNum.setText(fansCount + "");
    }


    @Override
    public void setJoined(boolean joined) {
        if (joined){
            tvFocus.setVisibility(View.GONE);
        }else{
            tvFocus.setVisibility(View.VISIBLE);
            tvFocus.setText("关注");
        }
    }


    /*** scrollview嵌套listview显示不全解决*/
    public static void setListViewHeightBasedOnChildren(HasHeadLoadMoreListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public void onEventMainThread(GroupEvent event) {
        Log.d(TAG, "接收onEventMainThread信息 GroupEvent-->" + event.isAttention());
        boolean attention = event.isAttention();
        setJoined(attention);
        sendBroadcast(new Intent(Constants.GROUPACTION));
    }

    public void onEvent(PublishGroupDynamicEvent event){
        Log.d(TAG, "接收onEvent信息 PublishDynamicEvent-->" + event.isRefresh());
        if (event.isRefresh()){
            groupHomeActivityPre.getDynamicFromNet(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
