// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.community.topic.actiivty;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TopicDetailActivity$$ViewBinder<T extends com.zhicai.byteera.activity.community.topic.actiivty.TopicDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427573, "field 'mListView'");
    target.mListView = finder.castView(view, 2131427573, "field 'mListView'");
    view = finder.findRequiredView(source, 2131427659, "field 'mTvLeft' and method 'clickListener'");
    target.mTvLeft = finder.castView(view, 2131427659, "field 'mTvLeft'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427584, "field 'mTvMiddle'");
    target.mTvMiddle = finder.castView(view, 2131427584, "field 'mTvMiddle'");
    view = finder.findRequiredView(source, 2131427660, "field 'mTvRight' and method 'clickListener'");
    target.mTvRight = finder.castView(view, 2131427660, "field 'mTvRight'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427575, "field 'rlDianPin' and method 'clickListener'");
    target.rlDianPin = finder.castView(view, 2131427575, "field 'rlDianPin'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427623, "field 'loadingPage'");
    target.loadingPage = finder.castView(view, 2131427623, "field 'loadingPage'");
    view = finder.findRequiredView(source, 2131427658, "field 'llTopicTitle'");
    target.llTopicTitle = finder.castView(view, 2131427658, "field 'llTopicTitle'");
    view = finder.findRequiredView(source, 2131427661, "field 'mCommentView'");
    target.mCommentView = finder.castView(view, 2131427661, "field 'mCommentView'");
    view = finder.findRequiredView(source, 2131427653, "field 'topHead'");
    target.topHead = view;
    view = finder.findRequiredView(source, 2131427512, "field 'rl_container'");
    target.rl_container = finder.castView(view, 2131427512, "field 'rl_container'");
    view = finder.findRequiredView(source, 2131427657, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427655, "method 'clickListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427656, "method 'clickListener'");
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
    target.mTvLeft = null;
    target.mTvMiddle = null;
    target.mTvRight = null;
    target.rlDianPin = null;
    target.loadingPage = null;
    target.llTopicTitle = null;
    target.mCommentView = null;
    target.topHead = null;
    target.rl_container = null;
  }
}
