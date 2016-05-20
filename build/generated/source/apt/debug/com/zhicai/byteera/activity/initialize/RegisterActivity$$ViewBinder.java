// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.initialize;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends com.zhicai.byteera.activity.initialize.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427554, "field 'etVerifyNum' and method 'onAfterVerifynumTextChanged'");
    target.etVerifyNum = finder.castView(view, 2131427554, "field 'etVerifyNum'");
    ((android.widget.TextView) view).addTextChangedListener(
      new android.text.TextWatcher() {
        @Override public void onTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void beforeTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void afterTextChanged(
          android.text.Editable p0
        ) {
          target.onAfterVerifynumTextChanged();
        }
      });
    view = finder.findRequiredView(source, 2131427639, "field 'etPhone' and method 'onAfterPhoneTextChanged'");
    target.etPhone = finder.castView(view, 2131427639, "field 'etPhone'");
    ((android.widget.TextView) view).addTextChangedListener(
      new android.text.TextWatcher() {
        @Override public void onTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void beforeTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void afterTextChanged(
          android.text.Editable p0
        ) {
          target.onAfterPhoneTextChanged();
        }
      });
    view = finder.findRequiredView(source, 2131427628, "field 'etPassword' and method 'onAfterPasswordTextChanged'");
    target.etPassword = finder.castView(view, 2131427628, "field 'etPassword'");
    ((android.widget.TextView) view).addTextChangedListener(
      new android.text.TextWatcher() {
        @Override public void onTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void beforeTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void afterTextChanged(
          android.text.Editable p0
        ) {
          target.onAfterPasswordTextChanged();
        }
      });
    view = finder.findRequiredView(source, 2131427643, "field 'tvProto' and method 'onclickView'");
    target.tvProto = finder.castView(view, 2131427643, "field 'tvProto'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onclickView(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427640, "field 'tvSendVerify' and method 'onclickView'");
    target.tvSendVerify = finder.castView(view, 2131427640, "field 'tvSendVerify'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onclickView(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427642, "field 'tvRegister' and method 'onclickView'");
    target.tvRegister = finder.castView(view, 2131427642, "field 'tvRegister'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onclickView(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427641, "field 'etRecommend'");
    target.etRecommend = finder.castView(view, 2131427641, "field 'etRecommend'");
  }

  @Override public void unbind(T target) {
    target.mHeadView = null;
    target.etVerifyNum = null;
    target.etPhone = null;
    target.etPassword = null;
    target.tvProto = null;
    target.tvSendVerify = null;
    target.tvRegister = null;
    target.etRecommend = null;
  }
}
