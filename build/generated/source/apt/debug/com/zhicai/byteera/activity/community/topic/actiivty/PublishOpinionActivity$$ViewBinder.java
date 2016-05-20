// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.topic.actiivty;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PublishOpinionActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.topic.actiivty.PublishOpinionActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427706, "field 'etComment'");
    target.etComment = finder.castView(view, 2131427706, "field 'etComment'");
    view = finder.findRequiredView(source, 2131427660, "field 'tvSend'");
    target.tvSend = view;
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131428117, "field 'llInstitution'");
    target.llInstitution = finder.castView(view, 2131428117, "field 'llInstitution'");
    view = finder.findRequiredView(source, 2131427743, "field 'ivInstitution'");
    target.ivInstitution = finder.castView(view, 2131427743, "field 'ivInstitution'");
    view = finder.findRequiredView(source, 2131427744, "field 'tvInstitution'");
    target.tvInstitution = finder.castView(view, 2131427744, "field 'tvInstitution'");
    view = finder.findRequiredView(source, 2131428116, "field 'tvAddInstitution'");
    target.tvAddInstitution = finder.castView(view, 2131428116, "field 'tvAddInstitution'");
    view = finder.findRequiredView(source, 2131428115, "method 'clickListener'");
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
    target.etComment = null;
    target.tvSend = null;
    target.mHeadView = null;
    target.llInstitution = null;
    target.ivInstitution = null;
    target.tvInstitution = null;
    target.tvAddInstitution = null;
  }
}
