package com.zhicai.byteera.activity.community.group;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.GroupHomeActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.service.dynamic.DynamicGroup;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.ZhiCaiLRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;

/** Created by bing on 2015/6/1. */
@SuppressWarnings("unused")
public class AllGroupActivity extends BaseActivity {
    @Bind(R.id.list_view) ZhiCaiLRefreshListView mListView;
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    private MyGroupAdapter mAdapter;
    private List<GroupEntity> list;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.all_group_activity);
        ButterKnife.bind(this);
        mAdapter = new MyGroupAdapter(baseContext);
        mListView.getListView().setAdapter(mAdapter);
    }

    @Override protected void initData() {
        String userId = PreferenceUtils.getInstance(baseContext).readUserInfo().getUser_id();
        DynamicGroup.GetAllGroupReq req;
        if (TextUtils.isEmpty(userId)) {
            req = DynamicGroup.GetAllGroupReq.newBuilder().build();
        } else {
            req = DynamicGroup.GetAllGroupReq.newBuilder().setUserId(userId).build();
        }
        TiangongClient.instance().send("chronos", "licaiquan_get_all_group", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    DynamicGroup.GetAllGroupResponse response = DynamicGroup.GetAllGroupResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        /*list = ModelParseUtil.GroupEntityParse(response.getGroupList());
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                mAdapter.setData(list);
                                mAdapter.notifyDataSetChanged();
                            }
                        });*/
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnItemClick(R.id.list_view)
    public void onItemClickListener(int position){
        Intent itent = new Intent(baseContext, GroupHomeActivity.class);
        itent.putExtra("group", list.get(position));
        ActivityUtil.startActivity(baseContext, itent);
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
        mHeadView.setRightTextClickListener(new HeadViewMain.RightTextClickListener() {
            @Override
            public void onRightTextClick() {
                Intent intent = new Intent(baseContext, SelelctGroupActivity.class);
                ActivityUtil.startActivity(baseContext, intent);
            }
        });
        mListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListView.refreshFinish();
            }
        });

    }


}
