// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.shouyi;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class InComeFragment$$ViewBinder<T extends com.zhicai.byteera.activity.shouyi.InComeFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427573, "field 'mListView' and method 'onItemClickListener'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClickListener(p0, p2);
        }
      });
    view = finder.findRequiredView(source, 2131427892, "field 'etCoin' and method 'onAfterTextChanged'");
    target.etCoin = finder.castView(view, 2131427892, "field 'etCoin'");
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
          target.onAfterTextChanged();
        }
      });
    view = finder.findRequiredView(source, 2131427893, "field 'mSeekbar'");
    target.mSeekbar = finder.castView(view, 2131427893, "field 'mSeekbar'");
    view = finder.findRequiredView(source, 2131427888, "field 'tvTime' and method 'clickListener'");
    target.tvTime = finder.castView(view, 2131427888, "field 'tvTime'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427889, "field 'tvRate' and method 'clickListener'");
    target.tvRate = finder.castView(view, 2131427889, "field 'tvRate'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427890, "field 'tvBank' and method 'clickListener'");
    target.tvBank = finder.castView(view, 2131427890, "field 'tvBank'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427891, "field 'tvP2p' and method 'clickListener'");
    target.tvP2p = finder.castView(view, 2131427891, "field 'tvP2p'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427894, "field 'tvCntMoney'");
    target.tvCntMoney = finder.castView(view, 2131427894, "field 'tvCntMoney'");
    view = finder.findRequiredView(source, 2131427622, "method 'clickListener'");
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
    target.mListView = null;
    target.etCoin = null;
    target.mSeekbar = null;
    target.tvTime = null;
    target.tvRate = null;
    target.tvBank = null;
    target.tvP2p = null;
    target.tvCntMoney = null;
  }
}
