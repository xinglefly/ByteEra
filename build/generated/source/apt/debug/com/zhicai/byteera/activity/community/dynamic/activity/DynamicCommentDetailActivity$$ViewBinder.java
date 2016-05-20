// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DynamicCommentDetailActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.DynamicCommentDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427573, "field 'mListView' and method 'onItemLongClickListener'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
    ((android.widget.AdapterView<?>) view).setOnItemLongClickListener(
      new android.widget.AdapterView.OnItemLongClickListener() {
        @Override public boolean onItemLongClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          return target.onItemLongClickListener(p2);
        }
      });
    view = finder.findRequiredView(source, 2131427706, "field 'etComment'");
    target.etComment = finder.castView(view, 2131427706, "field 'etComment'");
    view = finder.findRequiredView(source, 2131427786, "field 'tvEmptyComment'");
    target.tvEmptyComment = finder.castView(view, 2131427786, "field 'tvEmptyComment'");
    view = finder.findRequiredView(source, 2131427439, "field 'mHeadTitle'");
    target.mHeadTitle = finder.castView(view, 2131427439, "field 'mHeadTitle'");
  }

  @Override public void unbind(T target) {
    target.mListView = null;
    target.etComment = null;
    target.tvEmptyComment = null;
    target.mHeadTitle = null;
  }
}
