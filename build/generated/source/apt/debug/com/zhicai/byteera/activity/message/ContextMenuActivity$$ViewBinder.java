// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContextMenuActivity$$ViewBinder<T extends com.zhicai.byteera.activity.message.ContextMenuActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427514, "field 'copyTextView'");
    target.copyTextView = finder.castView(view, 2131427514, "field 'copyTextView'");
    view = finder.findRequiredView(source, 2131427515, "field 'copyLineView'");
    target.copyLineView = view;
    view = finder.findRequiredView(source, 2131427516, "field 'deleteTextView'");
    target.deleteTextView = finder.castView(view, 2131427516, "field 'deleteTextView'");
    view = finder.findRequiredView(source, 2131427517, "field 'deleteLineView'");
    target.deleteLineView = view;
    view = finder.findRequiredView(source, 2131427518, "field 'forwardTextView'");
    target.forwardTextView = finder.castView(view, 2131427518, "field 'forwardTextView'");
    view = finder.findRequiredView(source, 2131427519, "field 'forwardLineView'");
    target.forwardLineView = view;
  }

  @Override public void unbind(T target) {
    target.copyTextView = null;
    target.copyLineView = null;
    target.deleteTextView = null;
    target.deleteLineView = null;
    target.forwardTextView = null;
    target.forwardLineView = null;
  }
}
