// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ExchangeActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.exchangerecord.ExchangeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427520, "field 'iv_change_pic'");
    target.iv_change_pic = finder.castView(view, 2131427520, "field 'iv_change_pic'");
    view = finder.findRequiredView(source, 2131427521, "field 'iv_change_cancel' and method 'clickListener'");
    target.iv_change_cancel = finder.castView(view, 2131427521, "field 'iv_change_cancel'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427522, "field 'iv_change_accomplish'");
    target.iv_change_accomplish = finder.castView(view, 2131427522, "field 'iv_change_accomplish'");
    view = finder.findRequiredView(source, 2131427523, "field 'll_exchange'");
    target.ll_exchange = finder.castView(view, 2131427523, "field 'll_exchange'");
    view = finder.findRequiredView(source, 2131427524, "field 'rl_exchange'");
    target.rl_exchange = finder.castView(view, 2131427524, "field 'rl_exchange'");
    view = finder.findRequiredView(source, 2131427525, "field 'tv_change_name'");
    target.tv_change_name = finder.castView(view, 2131427525, "field 'tv_change_name'");
    view = finder.findRequiredView(source, 2131427526, "field 'tv_change_number'");
    target.tv_change_number = finder.castView(view, 2131427526, "field 'tv_change_number'");
    view = finder.findRequiredView(source, 2131427527, "field 'tv_change_coin'");
    target.tv_change_coin = finder.castView(view, 2131427527, "field 'tv_change_coin'");
    view = finder.findRequiredView(source, 2131427529, "field 'rl_confirm_exchange'");
    target.rl_confirm_exchange = finder.castView(view, 2131427529, "field 'rl_confirm_exchange'");
    view = finder.findRequiredView(source, 2131427531, "field 'tv_exchange_cancle' and method 'clickListener'");
    target.tv_exchange_cancle = finder.castView(view, 2131427531, "field 'tv_exchange_cancle'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427532, "field 'tv_exchange_confirm' and method 'clickListener'");
    target.tv_exchange_confirm = finder.castView(view, 2131427532, "field 'tv_exchange_confirm'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427530, "field 'tv_exchange_des'");
    target.tv_exchange_des = finder.castView(view, 2131427530, "field 'tv_exchange_des'");
    view = finder.findRequiredView(source, 2131427533, "field 'll_exchange_des'");
    target.ll_exchange_des = finder.castView(view, 2131427533, "field 'll_exchange_des'");
    view = finder.findRequiredView(source, 2131427528, "field 'tv_exchange' and method 'clickListener'");
    target.tv_exchange = finder.castView(view, 2131427528, "field 'tv_exchange'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427535, "field 'tv_change_description'");
    target.tv_change_description = finder.castView(view, 2131427535, "field 'tv_change_description'");
    view = finder.findRequiredView(source, 2131427536, "field 'rl_exchange_address'");
    target.rl_exchange_address = finder.castView(view, 2131427536, "field 'rl_exchange_address'");
    view = finder.findRequiredView(source, 2131427539, "field 'et_add_name'");
    target.et_add_name = finder.castView(view, 2131427539, "field 'et_add_name'");
    view = finder.findRequiredView(source, 2131427542, "field 'et_add_phone'");
    target.et_add_phone = finder.castView(view, 2131427542, "field 'et_add_phone'");
    view = finder.findRequiredView(source, 2131427545, "field 'et_address'");
    target.et_address = finder.castView(view, 2131427545, "field 'et_address'");
    view = finder.findRequiredView(source, 2131427548, "field 'et_zip'");
    target.et_zip = finder.castView(view, 2131427548, "field 'et_zip'");
    view = finder.findRequiredView(source, 2131427550, "field 'tv_add_confirm' and method 'clickListener'");
    target.tv_add_confirm = finder.castView(view, 2131427550, "field 'tv_add_confirm'");
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
    target.iv_change_pic = null;
    target.iv_change_cancel = null;
    target.iv_change_accomplish = null;
    target.ll_exchange = null;
    target.rl_exchange = null;
    target.tv_change_name = null;
    target.tv_change_number = null;
    target.tv_change_coin = null;
    target.rl_confirm_exchange = null;
    target.tv_exchange_cancle = null;
    target.tv_exchange_confirm = null;
    target.tv_exchange_des = null;
    target.ll_exchange_des = null;
    target.tv_exchange = null;
    target.tv_change_description = null;
    target.rl_exchange_address = null;
    target.et_add_name = null;
    target.et_add_phone = null;
    target.et_address = null;
    target.et_zip = null;
    target.tv_add_confirm = null;
  }
}
