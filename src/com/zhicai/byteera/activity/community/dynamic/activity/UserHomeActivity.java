package com.zhicai.byteera.activity.community.dynamic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.bean.Consult;
import com.zhicai.byteera.activity.community.dynamic.adapter.UserHomeAdapter;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.UserHomeActivityIV;
import com.zhicai.byteera.activity.community.dynamic.presenter.UserHomeActivityPre;
import com.zhicai.byteera.activity.community.dynamic.view.UserHomeCategoryView;
import com.zhicai.byteera.activity.community.dynamic.view.UserHomeHeadView;
import com.zhicai.byteera.activity.community.dynamic.view.UserHomeTitleView;
import com.zhicai.byteera.activity.knowwealth.view.KnowWealthDetailActivity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.widget.HasHeadLoadMoreListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class UserHomeActivity extends BaseActivity implements UserHomeActivityIV {

    @Bind(R.id.title) UserHomeTitleView mHomeTitle;
    @Bind(R.id.user_home_head) UserHomeHeadView mHeadView;
    @Bind(R.id.list_view) HasHeadLoadMoreListView mListView;

    private UserHomeActivityPre userHomeActivityPre;
    private UserHomeAdapter mAdapter;

    @Override protected void loadViewLayout() {
        setContentView(R.layout.activity_user_home);
        ButterKnife.bind(this);
        userHomeActivityPre = new UserHomeActivityPre(this);
        initListView();
        initCategory();
    }

    private void initListView() {
        mListView.setHeaderView(LayoutInflater.from(baseContext).inflate(R.layout.user_home_header_layout, null));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.top_head).setVisibility(View.VISIBLE);
        }
        mAdapter = new UserHomeAdapter(baseContext, userHomeActivityPre);
        mListView.setAdapter(mAdapter);
        mListView.setLoadMoreDataListener(new HasHeadLoadMoreListView.LoadMoreDataListener() {
            @Override
            public void loadMore() {
                userHomeActivityPre.loadMore();
            }
        });
    }

    @Override protected void initData() {
        userHomeActivityPre.getDataBefore();
        userHomeActivityPre.getAttributeFromNet();
    }

    @Override protected void updateUI() {
    }

    @Override protected void processLogic() {
        mHomeTitle.getLeftImg().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ActivityUtil.finishActivity(baseContext);
            }
        });
        mHeadView.getDoFocusView().setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                userHomeActivityPre.doFocus();
            }
        });
    }

    @OnItemClick(R.id.list_view)
    public void OnItemClick(int position){
        position -= 1;
        if (mAdapter.getCount() > position) {
            Object item = mAdapter.getItem(position);
            if (item instanceof ProductEntity) {
                /*Intent intent = new Intent(baseContext, IncomeAccessActivity.class);
                ProductEntity productEntity = (ProductEntity) item;
                boolean productWatch = productEntity.isProductWatch();
                intent.putExtra("product", productEntity);
                intent.putExtra("hasFocus", productWatch);
                ActivityUtil.startActivity(baseContext, intent);*/
            } else if (item instanceof Consult) {
                Intent intent = new Intent(baseContext, KnowWealthDetailActivity.class);
                Consult consult = (Consult) item;
                intent.putExtra("zixun_id", consult.getZiXunId());
                intent.putExtra("title", consult.getTitle());
                intent.putExtra("imgUrl", consult.getAvatarUrl());
                ActivityUtil.startActivity(baseContext, intent);
            }
        }
    }


    private void initCategory() {
        UserHomeCategoryView mCategory = (UserHomeCategoryView) findViewById(R.id.category);
        mCategory.setOnCategoryItemClickListener(new UserHomeCategoryView.CategoryItemClick() {
            @Override public void dynamicClick() {
                userHomeActivityPre.setSelect(0);
            }

            @Override public void collectClick() {
                userHomeActivityPre.setSelect(1);
            }

        });
    }

    @Override public Activity getContext() {
        return this;
    }

    @Override public void setCoinNum(String coinNum) {
        mHeadView.setCoinNum(coinNum);
    }

    @Override public void SetFansNum(String fansNum) {
        mHeadView.setFansNum(fansNum);
    }

    @Override public void setFocusNum(String focusNum) {
        mHeadView.setFocusNum(focusNum);
    }

    @Override public void setHeadImage(String headPortrait) {
        mHeadView.setIheadImage(headPortrait);
    }

    @Override public void setUserName(String nickname) {
        mHomeTitle.setUserName(nickname);
    }

    @Override public void setUerId(String userId) {
        mHeadView.setUserId(userId);
    }

    @Override public void changeFocus(boolean isFocus) {
        if (isFocus) {
            mHeadView.getDoFocusView().setImageResource(R.drawable.user_home_unfocus);
        } else {
            mHeadView.getDoFocusView().setImageResource(R.drawable.user_home_focus);
        }
    }

    @Override public void setData(List list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void addAllItem(List list) {
        mAdapter.addAllItem(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override public void loadComplete() {
        mListView.loadComplete();
    }

    @Override public void removeAllViews() {
        mAdapter.removeAllView();
        mAdapter.notifyDataSetChanged();
    }

    @Override public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.USER_HOME_DYNAMIC && resultCode == Constants.RESULT_SUCCESS) {
            userHomeActivityPre.setSelect(0);   //评论完成之后,刷新动态
        }
    }
}
