// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.traincamp.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GuessGameActivity$$ViewBinder<T extends com.zhicai.byteera.activity.traincamp.activity.GuessGameActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427655, "field 'back' and method 'clickListener'");
    target.back = finder.castView(view, 2131427655, "field 'back'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427837, "field 'question'");
    target.question = finder.castView(view, 2131427837, "field 'question'");
    view = finder.findRequiredView(source, 2131427593, "field 'tv_game_coin'");
    target.tv_game_coin = finder.castView(view, 2131427593, "field 'tv_game_coin'");
    view = finder.findRequiredView(source, 2131427594, "field 'lay_tips' and method 'clickListener'");
    target.lay_tips = finder.castView(view, 2131427594, "field 'lay_tips'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427595, "field 'img_tips'");
    target.img_tips = finder.castView(view, 2131427595, "field 'img_tips'");
    view = finder.findRequiredView(source, 2131427596, "field 'tv_showtips'");
    target.tv_showtips = finder.castView(view, 2131427596, "field 'tv_showtips'");
    view = finder.findRequiredView(source, 2131427597, "field 'lay_helper' and method 'clickListener'");
    target.lay_helper = finder.castView(view, 2131427597, "field 'lay_helper'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427599, "field 'lay_right_answer' and method 'clickListener'");
    target.lay_right_answer = finder.castView(view, 2131427599, "field 'lay_right_answer'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427601, "field 'tv_answer_coin'");
    target.tv_answer_coin = finder.castView(view, 2131427601, "field 'tv_answer_coin'");
    view = finder.findRequiredView(source, 2131427600, "field 'img_right_answer'");
    target.img_right_answer = finder.castView(view, 2131427600, "field 'img_right_answer'");
    view = finder.findRequiredView(source, 2131427602, "field 'lay_pass' and method 'clickListener'");
    target.lay_pass = finder.castView(view, 2131427602, "field 'lay_pass'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clickListener(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427604, "field 'tv_pass_coin'");
    target.tv_pass_coin = finder.castView(view, 2131427604, "field 'tv_pass_coin'");
    view = finder.findRequiredView(source, 2131427603, "field 'img_pass'");
    target.img_pass = finder.castView(view, 2131427603, "field 'img_pass'");
    view = finder.findRequiredView(source, 2131427598, "field 'img_gamepic'");
    target.img_gamepic = finder.castView(view, 2131427598, "field 'img_gamepic'");
    view = finder.findRequiredView(source, 2131427605, "field 'gv_answer'");
    target.gv_answer = finder.castView(view, 2131427605, "field 'gv_answer'");
    view = finder.findRequiredView(source, 2131427606, "field 'gv_options'");
    target.gv_options = finder.castView(view, 2131427606, "field 'gv_options'");
  }

  @Override public void unbind(T target) {
    target.back = null;
    target.question = null;
    target.tv_game_coin = null;
    target.lay_tips = null;
    target.img_tips = null;
    target.tv_showtips = null;
    target.lay_helper = null;
    target.lay_right_answer = null;
    target.tv_answer_coin = null;
    target.img_right_answer = null;
    target.lay_pass = null;
    target.tv_pass_coin = null;
    target.img_pass = null;
    target.img_gamepic = null;
    target.gv_answer = null;
    target.gv_options = null;
  }
}
