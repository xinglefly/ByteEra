// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.myhome.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChangeNickNameActivity$$ViewBinder<T extends com.zhicai.byteera.activity.myhome.activity.ChangeNickNameActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427703, "field 'etNickName'");
    target.etNickName = finder.castView(view, 2131427703, "field 'etNickName'");
    view = finder.findRequiredView(source, 2131427704, "field 'iv_clear'");
    target.iv_clear = finder.castView(view, 2131427704, "field 'iv_clear'");
  }

  @Override public void unbind(T target) {
    target.mHeadView = null;
    target.etNickName = null;
    target.iv_clear = null;
  }
}
