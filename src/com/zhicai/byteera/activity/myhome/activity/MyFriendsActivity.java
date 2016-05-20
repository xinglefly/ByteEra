package com.zhicai.byteera.activity.myhome.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.UserHomeActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.UserInfoEntity;
import com.zhicai.byteera.activity.myhome.adapter.MyFriendAdapter;
import com.zhicai.byteera.activity.myhome.adapter.SearchUserAdapter;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.UserAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.ClearEditText;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.MyLetterSortView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;
import butterknife.OnTouch;

/** Created by bing on 2015/5/15. */
@SuppressWarnings("unused")
public class MyFriendsActivity extends BaseActivity {
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.et_msg_search) ClearEditText etMsgSearch;
    @Bind(R.id.right_letter) MyLetterSortView mRightLetter;
    @Bind(R.id.tv_mid_letter) TextView tvMidLetter;
    @Bind(R.id.city_content_container) View mCityContainer;
    @Bind(R.id.search_content_container) FrameLayout mSearchContainer;
    @Bind(R.id.search_list) ListView mSearchListView;
    @Bind(R.id.list) ListView mListView;

    private List<UserInfoEntity> mlist = new ArrayList<>();
    private MyFriendAdapter mAdapter;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.my_friends_activity);
        ButterKnife.bind(this);
    }

    @Override protected void initData() {
        getData();
        mAdapter = new MyFriendAdapter(this);
        mRightLetter.setTextView(tvMidLetter);
        mSearchListView.setEmptyView(findViewById(R.id.search_empty));
        mSearchContainer.setVisibility(View.GONE);
        mListView.setEmptyView(findViewById(R.id.citys_list_load));
        mListView.setAdapter(mAdapter);
    }

    private void getData() {
        String user_id = PreferenceUtils.getInstance(baseContext).readUserInfo().getUser_id();
        UserAttribute.GetUserFriendReq sec = UserAttribute.GetUserFriendReq.newBuilder().setUserId(user_id).build();
        TiangongClient.instance().send("chronos", "get_user_friend_user", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final UserAttribute.GetUserFriendResponse response = UserAttribute.GetUserFriendResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            if (response.getErrorno() == 0) {
                                mlist = ModelParseUtil.UserInfoEntityParse2(response.getUserList());
                                Collections.sort(mlist);
                                MyApp.getMainThreadHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.updateListView(mlist);
                                    }
                                });
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override protected void updateUI() {
    }

    @OnItemClick(R.id.list)
    public void itemClickListener(int position){
        //跳转到我关注的人的个人页
        Intent intent = new Intent(baseContext, UserHomeActivity.class);
        intent.putExtra("other_user_id", mlist.get(position).getUserId());
        ActivityUtil.startActivity(baseContext, intent);
    }

    @OnTouch(R.id.list)
    public boolean onTouchListener(){
        UIUtils.hideKeyboard(baseContext);
        return false;
    }

    @Override protected void processLogic() {
        mHeadView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });


        // 设置右侧触摸监听
        mRightLetter.setOnTouchingLetterChangedListener(new MyLetterSortView.OnTouchingLetterChangedListener() {
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }
            }
        });
        // 根据输入框输入值的改变来过滤搜索
        etMsgSearch.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData2(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSearchListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent mIntent = new Intent();
//                mIntent.putExtra("city", mAdapter.getItem(position).toString());
//                setResult(RESULT_OK, mIntent);
//                ActivityUtil.finishActivity(MyFriendsActivity.this);
            }
        });
    }

    private void filterData2(String filterStr) {
        SearchUserAdapter mSearchUserAdapter = new SearchUserAdapter(this, mlist);
        mSearchListView.setAdapter(mSearchUserAdapter);
        mSearchListView.setTextFilterEnabled(true);
        if (mlist.size() < 1 || TextUtils.isEmpty(filterStr)) {
            mCityContainer.setVisibility(View.VISIBLE);
            mSearchContainer.setVisibility(View.INVISIBLE);

        } else {

            mCityContainer.setVisibility(View.INVISIBLE);
            mSearchContainer.setVisibility(View.VISIBLE);
            mSearchUserAdapter.getFilter().filter(filterStr);
        }
    }
}
