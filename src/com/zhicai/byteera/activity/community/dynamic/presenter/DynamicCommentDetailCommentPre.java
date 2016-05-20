package com.zhicai.byteera.activity.community.dynamic.presenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicCommentEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.DynamicCommentDetailIV;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/** Created by bing on 2015/6/27. */
public class DynamicCommentDetailCommentPre {
    private DynamicCommentDetailIV dynamicCommentDetailIV;
    private Activity mContext;
    private DynamicEntity dynamicEntity;
    private List<DynamicCommentEntity> commmentItemList;
    private int dynamicIntemPosition;

    public DynamicCommentDetailCommentPre(DynamicCommentDetailIV dynamicCommentDetailIV) {
        this.dynamicCommentDetailIV = dynamicCommentDetailIV;
        this.mContext = dynamicCommentDetailIV.getContext();
    }

    public void getIntentData() {
        dynamicEntity = (DynamicEntity) mContext.getIntent().getSerializableExtra("entity");
        dynamicIntemPosition = mContext.getIntent().getIntExtra("position", 0);
//        int id = dynamicEntity.getId();
//        //从数据库中得到所有的评论
//        commmentItemList = dynamicEntity.getComments(id);
        commmentItemList = dynamicEntity.getCommmentItemList();
        dynamicCommentDetailIV.initListView();
        if (this.commmentItemList != null && this.commmentItemList.size() > 0) {
            dynamicCommentDetailIV.goneEmptyComment();
            dynamicCommentDetailIV.showListView();
            dynamicCommentDetailIV.setData(this.commmentItemList);
        } else {
            dynamicCommentDetailIV.goneListView();
            dynamicCommentDetailIV.showEmptyComment();
        }
    }

    public void toastEmptyComment() {
        dynamicCommentDetailIV.toastEmptyComment();
    }

    public void returnToLastUI() {
        UIUtils.hideKeyboard(mContext);
        Intent intent = new Intent();
        intent.putExtra("dynamic_entity", dynamicEntity);
        intent.putExtra("position", dynamicIntemPosition);
        mContext.setResult(Constants.RESULT_SUCCESS, intent);
        ActivityUtil.finishActivity(mContext);
    }

    /** wangle动态对象中添加一条评论 */
    private void updateDynamicEntity(Dynamic.DoCommentResponse response) {
        Common.Comment comment = response.getComment();
        dynamicEntity.setCommentNum(dynamicEntity.getCommentNum() + 1);
        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
        dynamicCommentEntity.setAvatarUrl(comment.getHeadPortrait());
        dynamicCommentEntity.setNickName(comment.getNickname());
        dynamicCommentEntity.setContent(comment.getContent());
        dynamicCommentEntity.setCommentIndex(comment.getCommentIndex());
        dynamicCommentEntity.setPublishTime(comment.getPublishTime());
        dynamicCommentEntity.setUserId(comment.getUserId());
        commmentItemList.add(dynamicCommentEntity);
        dynamicEntity.setCommmentItemList(commmentItemList);
    }
//    private void freshView(Common.Comment comment) {
//        final DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
//        dynamicCommentEntity.setPublishTime(comment.getPublishTime());
//        dynamicCommentEntity.setUserId(comment.getUserId());
//        dynamicCommentEntity.setAvatarUrl(comment.getHeadPortrait());
//        dynamicCommentEntity.setNickName(comment.getNickname());
//        dynamicCommentEntity.setContent(comment.getContent());
//        dynamicCommentEntity.setCommentIndex(comment.getCommentIndex());
//        //评论完成 往数据库中添加一条评论
//        int id = dynamicEntity.getId();
//        dynamicCommentEntity.setDynamicitementity_id(id);     //评论绑定到动态id上面
//        dynamicCommentEntity.save();
//        //重新查询数据库 并且更新界面
//        List<DynamicCommentEntity> commmentItemList = dynamicEntity.getComments(id);
//        dynamicEntity.setCommentNum(commmentItemList.size()); //数据库中评论数加1
//        dynamicEntity.update(id);
//        UIUtils.hideKeyboard(mContext);
//        mContext.setResult(mContext.RESULT_OK);
//        ActivityUtil.finishActivity(mContext);
//    }


    public void showDeleteDialog(final int position) {
        DynamicCommentEntity commentEntity = dynamicCommentDetailIV.getItem(position);
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if (commentEntity.getUserId().equals(userId)) { //如果是自己发表的评论就能够长按删除
            final MaterialDialog materialDialog = new MaterialDialog(mContext);
            materialDialog.setTitle("删除评论").setMessage("您确定要删除这条评论吗?").setPositiveButton("确定", new View.OnClickListener() {
                @Override public void onClick(View v) {
                    deleteCommment(position, materialDialog);
                }
            }).setNegativeButton("取消", new View.OnClickListener() {
                @Override public void onClick(View v) {
                    materialDialog.dismiss();
                }
            }).show();
        }
    }

    private void deleteCommment(final int position, final MaterialDialog dialog) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        final DynamicCommentEntity dynamicCommentEntity = commmentItemList.get(position);
        Dynamic.UndoComment sec = Dynamic.UndoComment.newBuilder().setUserId(userId)
                .setCommentIndex(dynamicCommentEntity.getCommentIndex()).setMsgId(dynamicEntity.getMsgId()).build();
        TiangongClient.instance().send("chronos", "licaiquan_undocomment", sec.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final Dynamic.UndoCommentResponse response = Dynamic.UndoCommentResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override public void run() {
                            if (response.getErrorno() == 0) {
                               ToastUtil.showToastText("删除评论成功");
                                dialog.dismiss();
                                UIUtils.hideKeyboard(mContext);
//                                //从数据库中删除该条评论
//                                DataSupport.delete(DynamicCommentEntity.class, dynamicCommentEntity.getId());
//                                //重新查询数据库 并且更新界面
//                                commmentItemList = dynamicEntity.getComments(id);
//                                dynamicEntity.setCommentNum(commmentItemList.size());
//                                dynamicEntity.update(id); //更新数据库中评论的条数
                                commmentItemList.remove(position);
                                dynamicEntity.setCommmentItemList(commmentItemList);
                                dynamicEntity.setCommentNum(commmentItemList.size());//评论的条目数也要相应的改变
                                dynamicCommentDetailIV.setData(commmentItemList);
                                dynamicCommentDetailIV.setHasDeleteComent(true);
                            } else {
                               ToastUtil.showToastText("删除评论失败");
                                dialog.dismiss();
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void publishComent(String comment) {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if (TextUtils.isEmpty(userId)) {
            ActivityUtil.showLoginDialog(mContext, -1); //-1说明不需要数据回传进行界面更新
        } else {
            if (TextUtils.isEmpty(comment)) {
                toastEmptyComment();
            } else {
                dynamicCommentDetailIV.setHerViewRightTextViewEnabled(false);
                if (!dynamicCommentDetailIV.startLoginActivity()) {
                    Dynamic.DoComment doComment = Dynamic.DoComment.newBuilder().setUserId(userId).setMsgId(dynamicEntity.getMsgId()).setContent(comment).build();
                    TiangongClient.instance().send("chronos", "licaiquan_docomment", doComment.toByteArray(), new BaseHandlerClass() {
                        public void onSuccess(byte[] buffer) {
                            try {
                                Dynamic.DoCommentResponse response = Dynamic.DoCommentResponse.parseFrom(buffer);
                                if (response.getErrorno() == 0) {
                                   ToastUtil.showToastText("评论成功");
                                    //TODO 每日任务，评论一条动态

                                    //通知上一个界面更新
                                    updateDynamicEntity(response);
                                    returnToLastUI();
                                }
                            } catch (InvalidProtocolBufferException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }
}
