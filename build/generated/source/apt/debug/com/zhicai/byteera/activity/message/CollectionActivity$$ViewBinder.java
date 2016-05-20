// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CollectionActivity$$ViewBinder<T extends com.zhicai.byteera.activity.message.CollectionActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427655, "field 'back'");
    target.back = finder.castView(view, 2131427655, "field 'back'");
    view = finder.findRequiredView(source, 2131427835, "field 'titleName'");
    target.titleName = finder.castView(view, 2131427835, "field 'titleName'");
    view = finder.findRequiredView(source, 2131427841, "field 'search'");
    target.search = finder.castView(view, 2131427841, "field 'search'");
    view = finder.findRequiredView(source, 2131427842, "field 'plus'");
    target.plus = finder.castView(view, 2131427842, "field 'plus'");
    view = finder.findRequiredView(source, 2131427513, "field 'listView'");
    target.listView = finder.castView(view, 2131427513, "field 'listView'");
  }

  @Override public void unbind(T target) {
    target.back = null;
    target.titleName = null;
    target.search = null;
    target.plus = null;
    target.listView = null;
  }
}
