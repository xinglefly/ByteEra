package com.zhicai.byteera.activity.community.group;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.GroupHomeActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.dynamic.DynamicGroup;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.ZhiCaiLRefreshListView;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;
import me.drakeet.materialdialog.MaterialDialog;


/** Created by bing on 2015/6/10. */
@SuppressWarnings("unused")
public class MyGroupActivity extends BaseActivity {
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.list_view) ZhiCaiLRefreshListView mListView;
    private MyGroupAdapter mAdapter;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.my_group_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {
        mAdapter = new MyGroupAdapter(this);
        mListView.getListView().setAdapter(mAdapter);
        getDataFromNet();
    }

    private void getDataFromNet() {
        String userId = PreferenceUtils.getInstance(baseContext).readUserInfo().getUser_id();
        if (TextUtils.isEmpty(userId)) {
            final MaterialDialog materialDialog = new MaterialDialog(baseContext);
            materialDialog.setTitle("登录").setMessage("您还没有登录，是否登录?").setPositiveButton("确定", new View.OnClickListener() {
                @Override public void onClick(View v) {
                    ActivityUtil.startLoginActivityForResult(baseContext, Constants.REQUEST_MY_GROUP);
                    materialDialog.dismiss();
                }
            }).setNegativeButton("取消", new View.OnClickListener() {
                @Override public void onClick(View v) {
                    materialDialog.dismiss();
                    finish();
                }
            }).show();
        } else {
            DynamicGroup.GetUserGroupReq req = DynamicGroup.GetUserGroupReq.newBuilder().setUserId(userId).build();
            TiangongClient.instance().send("chronos", "licaiquan_get_user_group", req.toByteArray(), new BaseHandlerClass() {
                public void onSuccess(byte[] buffer) {
                    try {
                        final DynamicGroup.GetUserGroupResponse response = DynamicGroup.GetUserGroupResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                if (response.getErrorno() == 0) {
//                                    List<GroupEntity> groupEntities = ModelParseUtil.GroupEntityParse(response.getGroupList());
//                                    mAdapter.setData(groupEntities);
//                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override protected void updateUI() {

    }

    @Override protected void onResume() {
        super.onResume();

    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });

        mListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListView.refreshFinish();
            }
        });

    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_MY_GROUP) {
            getDataFromNet();
        }
    }

    @OnItemClick(R.id.list_view)
    public void onItemClickListener(int position){
        Intent intent = new Intent(baseContext, GroupHomeActivity.class);
        GroupEntity entity = mAdapter.getItem(position);
        intent.putExtra("group", entity);
        ActivityUtil.startActivity(baseContext, intent);
    }
}
