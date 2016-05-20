package com.zhicai.byteera.activity.myhome.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.myhome.adapter.ItemBeanAdapter;
import com.zhicai.byteera.activity.myhome.adapter.SearchCityAdapter;
import com.zhicai.byteera.activity.myhome.db.City;
import com.zhicai.byteera.activity.myhome.db.MyCityDBHelper;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.ClearEditText;
import com.zhicai.byteera.widget.MyLetterSortView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;
import butterknife.OnTouch;

/** Created by bing on 2015/5/8. */
public class SelectCityAcitivity extends BaseActivity {

    @Bind(R.id.et_msg_search) ClearEditText etMsgSearch;
    @Bind(R.id.right_letter) MyLetterSortView mRightLetter;
    @Bind(R.id.tv_mid_letter) TextView tvMidLetter;
    @Bind(R.id.city_content_container) View mCityContainer;
    @Bind(R.id.search_content_container) FrameLayout mSearchContainer;
    @Bind(R.id.search_list) ListView mSearchListView;
    @Bind(R.id.list) ListView mListView;

    private InputMethodManager inputMethodManager;
    private List<City> mlist = new ArrayList<City>();
    private ItemBeanAdapter mAdapter;
    private SearchCityAdapter mSearchCityAdapter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.city_activity);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.top_head).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        getDataCity();
        mAdapter = new ItemBeanAdapter(this, mlist);
        inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        mRightLetter.setTextView(tvMidLetter);
        mSearchListView.setEmptyView(findViewById(R.id.search_empty));
        mSearchContainer.setVisibility(View.GONE);

        mListView.setEmptyView(findViewById(R.id.citys_list_load));
        mListView.setAdapter(mAdapter);
    }


    @OnItemClick(R.id.list)
    public void onItemClickListener(int position){
        Intent mIntent = new Intent();
        mIntent.putExtra("city", mAdapter.getItem(position).toString());
        setResult(RESULT_OK, mIntent);
        ActivityUtil.finishActivity(baseContext);
    }

    @OnTouch(R.id.list)
    public boolean onTouchListener(){
        // 隐藏软键盘
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    @OnItemClick(R.id.search_list)
    public void onItemSearchListLisetner(int position){
        Intent mIntent = new Intent();
        mIntent.putExtra("city", mSearchCityAdapter.getItem(position).toString());
        setResult(RESULT_OK, mIntent);
        ActivityUtil.finishActivity(baseContext);
    }

    @Override
    protected void updateUI() {
    }

    @Override
    protected void processLogic() {

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
    }

    private void filterData2(String filterStr) {
        mSearchCityAdapter = new SearchCityAdapter(baseContext, mlist);
        mSearchListView.setAdapter(mSearchCityAdapter);
        mSearchListView.setTextFilterEnabled(true);
        if (mlist.size() < 1 || TextUtils.isEmpty(filterStr)) {
            mCityContainer.setVisibility(View.VISIBLE);
            mSearchContainer.setVisibility(View.INVISIBLE);

        } else {

            mCityContainer.setVisibility(View.INVISIBLE);
            mSearchContainer.setVisibility(View.VISIBLE);
            mSearchCityAdapter.getFilter().filter(filterStr);
        }
    }

    public void getDataCity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyCityDBHelper myCityDBHelper = new MyCityDBHelper(baseContext);
                mlist = myCityDBHelper.getCityDB().getAllCity();
                MyApp.getMainThreadHandler().post(new Runnable() {
                    @Override public void run() {
                        mAdapter.updateListView(mlist);
                    }
                });
            }
        }).start();
    }
}
