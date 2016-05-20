// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.topic.actiivty;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TopicActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.topic.actiivty.TopicActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427439, "field 'headTitle'");
    target.headTitle = finder.castView(view, 2131427439, "field 'headTitle'");
    view = finder.findRequiredView(source, 2131427573, "field 'mListVIew' and method 'onItemClick'");
    target.mListVIew = finder.castView(view, 2131427573, "field 'mListVIew'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p2);
        }
      });
    view = finder.findRequiredView(source, 2131427800, "field 'mSwipeLayout'");
    target.mSwipeLayout = finder.castView(view, 2131427800, "field 'mSwipeLayout'");
  }

  @Override public void unbind(T target) {
    target.headTitle = null;
    target.mListVIew = null;
    target.mSwipeLayout = null;
  }
}
