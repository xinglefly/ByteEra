// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AlertDialogActivity$$ViewBinder<T extends com.zhicai.byteera.activity.message.AlertDialogActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427404, "field 'mTextView'");
    target.mTextView = finder.castView(view, 2131427404, "field 'mTextView'");
    view = finder.findRequiredView(source, 2131427468, "field 'mButton'");
    target.mButton = finder.castView(view, 2131427468, "field 'mButton'");
    view = finder.findRequiredView(source, 2131427400, "field 'imageView'");
    target.imageView = finder.castView(view, 2131427400, "field 'imageView'");
    view = finder.findRequiredView(source, 2131427467, "field 'editText'");
    target.editText = finder.castView(view, 2131427467, "field 'editText'");
  }

  @Override public void unbind(T target) {
    target.mTextView = null;
    target.mButton = null;
    target.imageView = null;
    target.editText = null;
  }
}
