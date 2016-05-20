// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.dynamic.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ImageDetailFragment$$ViewBinder<T extends com.zhicai.byteera.activity.community.dynamic.view.ImageDetailFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427400, "field 'mImageView'");
    target.mImageView = finder.castView(view, 2131427400, "field 'mImageView'");
    view = finder.findRequiredView(source, 2131427848, "field 'progressBar'");
    target.progressBar = finder.castView(view, 2131427848, "field 'progressBar'");
  }

  @Override public void unbind(T target) {
    target.mImageView = null;
    target.progressBar = null;
  }
}
