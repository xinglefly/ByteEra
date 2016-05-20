// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OrganizationInfoActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.activity.OrganizationInfoActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427936, "field 'myRatingBar'");
    target.myRatingBar = finder.castView(view, 2131427936, "field 'myRatingBar'");
    view = finder.findRequiredView(source, 2131428040, "field 'tvRating'");
    target.tvRating = finder.castView(view, 2131428040, "field 'tvRating'");
    view = finder.findRequiredView(source, 2131428041, "field 'ivRatingRight' and method 'clickListener'");
    target.ivRatingRight = finder.castView(view, 2131428041, "field 'ivRatingRight'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427824, "field 'tvFansCnt'");
    target.tvFansCnt = finder.castView(view, 2131427824, "field 'tvFansCnt'");
    view = finder.findRequiredView(source, 2131428043, "field 'ivFansRight' and method 'clickListener'");
    target.ivFansRight = finder.castView(view, 2131428043, "field 'ivFansRight'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131428044, "field 'tvTitle1'");
    target.tvTitle1 = finder.castView(view, 2131428044, "field 'tvTitle1'");
    view = finder.findRequiredView(source, 2131428046, "field 'tvTitle2'");
    target.tvTitle2 = finder.castView(view, 2131428046, "field 'tvTitle2'");
    view = finder.findRequiredView(source, 2131428048, "field 'tvTitle3'");
    target.tvTitle3 = finder.castView(view, 2131428048, "field 'tvTitle3'");
    view = finder.findRequiredView(source, 2131427619, "field 'iv1'");
    target.iv1 = finder.castView(view, 2131427619, "field 'iv1'");
    view = finder.findRequiredView(source, 2131427620, "field 'iv2'");
    target.iv2 = finder.castView(view, 2131427620, "field 'iv2'");
    view = finder.findRequiredView(source, 2131427621, "field 'iv3'");
    target.iv3 = finder.castView(view, 2131427621, "field 'iv3'");
    view = finder.findRequiredView(source, 2131428045, "field 'ol1'");
    target.ol1 = finder.castView(view, 2131428045, "field 'ol1'");
    view = finder.findRequiredView(source, 2131428047, "field 'ol2'");
    target.ol2 = finder.castView(view, 2131428047, "field 'ol2'");
    view = finder.findRequiredView(source, 2131428049, "field 'ol3'");
    target.ol3 = finder.castView(view, 2131428049, "field 'ol3'");
    view = finder.findRequiredView(source, 2131427512, "field 'rlContainer'");
    target.rlContainer = finder.castView(view, 2131427512, "field 'rlContainer'");
    view = finder.findRequiredView(source, 2131427460, "field 'mHeadView'");
    target.mHeadView = finder.castView(view, 2131427460, "field 'mHeadView'");
    view = finder.findRequiredView(source, 2131427731, "field 'line'");
    target.line = view;
    view = finder.findRequiredView(source, 2131428038, "field 'rlOrganization'");
    target.rlOrganization = finder.castView(view, 2131428038, "field 'rlOrganization'");
  }

  @Override public void unbind(T target) {
    target.myRatingBar = null;
    target.tvRating = null;
    target.ivRatingRight = null;
    target.tvFansCnt = null;
    target.ivFansRight = null;
    target.tvTitle1 = null;
    target.tvTitle2 = null;
    target.tvTitle3 = null;
    target.iv1 = null;
    target.iv2 = null;
    target.iv3 = null;
    target.ol1 = null;
    target.ol2 = null;
    target.ol3 = null;
    target.rlContainer = null;
    target.mHeadView = null;
    target.line = null;
    target.rlOrganization = null;
  }
}
