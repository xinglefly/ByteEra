// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.myhome.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SelectCityAcitivity$$ViewBinder<T extends com.zhicai.byteera.activity.myhome.activity.SelectCityAcitivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
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
    view = finder.findRequiredView(source, 2131427725, "field 'mSearchListView' and method 'onItemSearchListLisetner'");
    target.mSearchListView = finder.castView(view, 2131427725, "field 'mSearchListView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemSearchListLisetner(p2);
        }
      });
    view = finder.findRequiredView(source, 2131427504, "field 'mListView', method 'onItemClickListener', and method 'onTouchListener'");
    target.mListView = finder.castView(view, 2131427504, "field 'mListView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClickListener(p2);
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
    target.etMsgSearch = null;
    target.mRightLetter = null;
    target.tvMidLetter = null;
    target.mCityContainer = null;
    target.mSearchContainer = null;
    target.mSearchListView = null;
    target.mListView = null;
  }
}
