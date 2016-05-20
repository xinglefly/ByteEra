// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AlbumActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.AlbumActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131428061, "field 'gridView'");
    target.gridView = finder.castView(view, 2131428061, "field 'gridView'");
    view = finder.findRequiredView(source, 2131428059, "field 'preview'");
    target.preview = finder.castView(view, 2131428059, "field 'preview'");
    view = finder.findRequiredView(source, 2131428060, "field 'okButton'");
    target.okButton = finder.castView(view, 2131428060, "field 'okButton'");
    view = finder.findRequiredView(source, 2131428062, "field 'tv'");
    target.tv = finder.castView(view, 2131428062, "field 'tv'");
  }

  @Override public void unbind(T target) {
    target.mHeadView = null;
    target.gridView = null;
    target.preview = null;
    target.okButton = null;
    target.tv = null;
  }
}
