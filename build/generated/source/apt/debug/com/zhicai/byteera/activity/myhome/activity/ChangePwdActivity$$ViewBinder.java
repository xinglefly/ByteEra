// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.myhome.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChangePwdActivity$$ViewBinder<T extends com.zhicai.byteera.activity.myhome.activity.ChangePwdActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427708, "field 'etOldPwd'");
    target.etOldPwd = finder.castView(view, 2131427708, "field 'etOldPwd'");
    view = finder.findRequiredView(source, 2131427710, "field 'etNewPwd'");
    target.etNewPwd = finder.castView(view, 2131427710, "field 'etNewPwd'");
    view = finder.findRequiredView(source, 2131427709, "field 'tbOldPwd'");
    target.tbOldPwd = finder.castView(view, 2131427709, "field 'tbOldPwd'");
    view = finder.findRequiredView(source, 2131427711, "field 'tbNewPwd'");
    target.tbNewPwd = finder.castView(view, 2131427711, "field 'tbNewPwd'");
  }

  @Override public void unbind(T target) {
    target.mHeadView = null;
    target.etOldPwd = null;
    target.etNewPwd = null;
    target.tbOldPwd = null;
    target.tbNewPwd = null;
  }
}
