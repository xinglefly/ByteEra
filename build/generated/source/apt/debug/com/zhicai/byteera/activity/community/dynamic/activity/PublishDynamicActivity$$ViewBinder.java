// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PublishDynamicActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.PublishDynamicActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131428110, "field 'noScrollgridview' and method 'onItemClick'");
    target.noScrollgridview = finder.castView(view, 2131428110, "field 'noScrollgridview'");
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
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427706, "field 'etComment'");
    target.etComment = finder.castView(view, 2131427706, "field 'etComment'");
    view = finder.findRequiredView(source, 2131428109, "field 'popParent'");
    target.popParent = view;
    view = finder.findRequiredView(source, 2131427660, "field 'tvSend'");
    target.tvSend = view;
    view = finder.findRequiredView(source, 2131428111, "field 'rlOptions'");
    target.rlOptions = finder.castView(view, 2131428111, "field 'rlOptions'");
    view = finder.findRequiredView(source, 2131427513, "field 'mListView' and method 'itemClickListener'");
    target.mListView = finder.castView(view, 2131427513, "field 'mListView'");
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
    view = finder.findRequiredView(source, 2131428113, "field 'tvSelect' and method 'clickListener'");
    target.tvSelect = finder.castView(view, 2131428113, "field 'tvSelect'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428114, "field 'tvOptions' and method 'clickListener'");
    target.tvOptions = finder.castView(view, 2131428114, "field 'tvOptions'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.noScrollgridview = null;
    target.mHeadView = null;
    target.etComment = null;
    target.popParent = null;
    target.tvSend = null;
    target.rlOptions = null;
    target.mListView = null;
    target.tvSelect = null;
    target.tvOptions = null;
  }
}
