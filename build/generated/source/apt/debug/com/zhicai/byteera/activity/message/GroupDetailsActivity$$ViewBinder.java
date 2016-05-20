// Generated code from Butter Knife. Do not modify!
package com.zhicai.byteera.activity.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GroupDetailsActivity$$ViewBinder<T extends com.zhicai.byteera.activity.message.GroupDetailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427563, "field 'userGridview'");
    target.userGridview = finder.castView(view, 2131427563, "field 'userGridview'");
    view = finder.findRequiredView(source, 2131427561, "field 'loadingPB'");
    target.loadingPB = finder.castView(view, 2131427561, "field 'loadingPB'");
    view = finder.findRequiredView(source, 2131427570, "field 'exitBtn'");
    target.exitBtn = finder.castView(view, 2131427570, "field 'exitBtn'");
    view = finder.findRequiredView(source, 2131427571, "field 'deleteBtn'");
    target.deleteBtn = finder.castView(view, 2131427571, "field 'deleteBtn'");
    view = finder.findRequiredView(source, 2131427566, "field 'rl_switch_block_groupmsg'");
    target.rl_switch_block_groupmsg = finder.castView(view, 2131427566, "field 'rl_switch_block_groupmsg'");
    view = finder.findRequiredView(source, 2131427567, "field 'iv_switch_block_groupmsg'");
    target.iv_switch_block_groupmsg = finder.castView(view, 2131427567, "field 'iv_switch_block_groupmsg'");
    view = finder.findRequiredView(source, 2131427568, "field 'iv_switch_unblock_groupmsg'");
    target.iv_switch_unblock_groupmsg = finder.castView(view, 2131427568, "field 'iv_switch_unblock_groupmsg'");
    view = finder.findRequiredView(source, 2131427569, "field 'clearAllHistory'");
    target.clearAllHistory = finder.castView(view, 2131427569, "field 'clearAllHistory'");
    view = finder.findRequiredView(source, 2131427565, "field 'blacklistLayout'");
    target.blacklistLayout = finder.castView(view, 2131427565, "field 'blacklistLayout'");
    view = finder.findRequiredView(source, 2131427564, "field 'changeGroupNameLayout'");
    target.changeGroupNameLayout = finder.castView(view, 2131427564, "field 'changeGroupNameLayout'");
    view = finder.findRequiredView(source, 2131427562, "field 'homeImageView'");
    target.homeImageView = finder.castView(view, 2131427562, "field 'homeImageView'");
  }

  @Override public void unbind(T target) {
    target.userGridview = null;
    target.loadingPB = null;
    target.exitBtn = null;
    target.deleteBtn = null;
    target.rl_switch_block_groupmsg = null;
    target.iv_switch_block_groupmsg = null;
    target.iv_switch_unblock_groupmsg = null;
    target.clearAllHistory = null;
    target.blacklistLayout = null;
    target.changeGroupNameLayout = null;
    target.homeImageView = null;
  }
}
