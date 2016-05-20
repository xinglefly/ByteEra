// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.myhome.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyFriendsActivity$$ViewBinder<T extends com.zhicai.byteera.activity.myhome.activity.MyFriendsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427718, "field 'etMsgSearch'");
    target.etMsgSearch = finder.castView(view, 2131427718, "field 'etMsgSearch'");
    view = finder.findRequiredView(source, 2131427723, "field 'mRightLetter'");
    target.mRightLetter = finder.castView(view, 2131427723, "field 'mRightLetter'");
    view = finder.findRequiredView(source, 2131427721, "field 'tvMidLetter'");
    target.tvMidLetter = finder.castView(view, 2131427721, "field 'tvMidLetter'");
    view = finder.findRequiredView(source, 2131427720, "field 'mCityContainer'");
    target.mCityContainer = view;
    view = finder.findRequiredView(source, 2131427724, "field 'mSearchContainer'");
    target.mSearchContainer = finder.castView(view, 2131427724, "field 'mSearchContainer'");
    view = finder.findRequiredView(source, 2131427725, "field 'mSearchListView'");
    target.mSearchListView = finder.castView(view, 2131427725, "field 'mSearchListView'");
    view = finder.findRequiredView(source, 2131427504, "field 'mListView', method 'itemClickListener', and method 'onTouchListener'");
    target.mListView = finder.castView(view, 2131427504, "field 'mListView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.itemClickListener(p2);
        }
      });
    view.setOnTouchListener(
      new android.view.View.OnTouchListener() {
        @Override public boolean onTouch(
          android.view.View p0,
          android.view.MotionEvent p1
        ) {
          return target.onTouchListener();
        }
      });
  }

  @Override public void unbind(T target) {
    target.mHeadView = null;
    target.etMsgSearch = null;
    target.mRightLetter = null;
    target.tvMidLetter = null;
    target.mCityContainer = null;
    target.mSearchContainer = null;
    target.mSearchListView = null;
    target.mListView = null;
  }
}
