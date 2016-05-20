package com.zhicai.byteera.activity.community.dynamic.presenter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.activity.community.dynamic.activity.DynamicCommentDetailActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.ImagePagerActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.OrganizationInfoActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.PublishDynamicActivity;
import com.zhicai.byteera.activity.community.dynamic.activity.UserFansActivity;
import com.zhicai.byteera.activity.community.dynamic.entity.DynamicEntity;
import com.zhicai.byteera.activity.community.dynamic.entity.ImgEntity;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.OrganizationDynamicItemIV;
import com.zhicai.byteera.activity.community.dynamic.interfaceview.OrganizationHomeIV;
import com.zhicai.byteera.activity.initialize.LoginActivity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.dynamic.Dynamic;
import com.zhicai.byteera.service.dynamic.InstitutionAttribute;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;

import org.litepal.crud.DataSupport;

import java.util.List;

/** Created by bing on 2015/6/29. */
public class OrganizationHomePre {
    private static final String TAG = OrganizationHomePre.class.getSimpleName();

    private Activity mContext;
    private OrganizationHomeIV organizationHomeIV;
    private SparseArray<OrganizationDynamicItemIV> organizationDynamicItemIVs = new SparseArray<>();

    private String other_user_id;
    private int list_position; //当点击关注的，刷新listview position数据
    private int currentPage;
    private String lastMsgId = "";
    //评分&粉丝数
    private int fansCnt;
    private int score;
    private int riskScore;
    private int expScore;
    private int incomeScore;
    private boolean isAttention;


    public OrganizationHomePre(OrganizationHomeIV organizationHomeIV) {
        this.organizationHomeIV = organizationHomeIV;
        this.mContext = organizationHomeIV.getContext();
    }

    public void addOrganizationDynamicItemIV(int position, OrganizationDynamicItemIV organizationDynamicItemIV) {
        this.organizationDynamicItemIVs.append(position, organizationDynamicItemIV);
    }

    public void getDataFromBefore() {
        other_user_id = mContext.getIntent().getStringExtra("other_user_id");
        list_position = mContext.getIntent().getIntExtra("position",0);
        Log.d(TAG, "organiztion_id" + other_user_id + "," + list_position);
    }
    public String getUserId(){
        return other_user_id;
    }




    /** 获取机构的动态数据 */
    public void getDynamicData(final boolean isFirst) {
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        Dynamic.GetInstituteReq req;
        if (isFirst) {
            req = Dynamic.GetInstituteReq.newBuilder().setInstituteUserId(other_user_id).build();
        } else {
            req = Dynamic.GetInstituteReq.newBuilder().setInstituteUserId(other_user_id).setIsafter(false).build();
        }
        TiangongClient.instance().send("chronos", "licaiquan_get_institute_msg", req.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final Dynamic.GetInstituteResponse response = Dynamic.GetInstituteResponse.parseFrom(buffer);
                    Log.d(TAG,"res-->"+response.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            if (response.getErrorno() == 0 && response.getItemCount() > 0) {
                                List<DynamicEntity> dynamicItemList = ModelParseUtil.OrganizationDynamicEntityParse(response, userId);
                                lastMsgId = dynamicItemList.get(dynamicItemList.size() - 1).getMsgId(); //获取最后一条数据的msgId
                                if (isFirst) {
                                    organizationHomeIV.setData(dynamicItemList);
                                } else {
                                    organizationHomeIV.addAllItem(dynamicItemList);
                                }
                            }
                            organizationHomeIV.loadComplete();
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void getAttributeFromNet() {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        InstitutionAttribute.GetInstitutionAttrReq sec;
        if (!TextUtils.isEmpty(userId)) {
            sec = InstitutionAttribute.GetInstitutionAttrReq.newBuilder().setUserId2(userId).setUserId(other_user_id).build();
        } else {
            sec = InstitutionAttribute.GetInstitutionAttrReq.newBuilder().setUserId(other_user_id).build();
        }
        TiangongClient.instance().send("chronos", "get_institution_attr", sec.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.GetInstitutionAttrResponse response = InstitutionAttribute.GetInstitutionAttrResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "-organization->" + response.toString());
                            organizationHomeIV.setHead(response.getHeadPortrait());
                            organizationHomeIV.setTitleName(response.getInstitutionName());

                            fansCnt = response.getFansUserCnt();
                            organizationHomeIV.setFansCount(fansCnt + "");

                            if (response.hasLatestAmount()) {
                                organizationHomeIV.setMoneyNum(String.valueOf(response.getLatestAmount()));
                            } else {
                                organizationHomeIV.setMoneyNum("0");
                            }
                            if (response.hasLatestInvestors()) {
                                organizationHomeIV.setPeopleNum(response.getLatestInvestors() + "");
                            } else {
                                organizationHomeIV.setPeopleNum("0");
                            }
//                            optional uint32 score = 10;           // 平均分数
//                            optional uint32 risk_score = 11;      // 风险分数
//                            optional uint32 exp_score = 12;       // 体验分数
//                            optional uint32 income_score = 13;    // 收益分数

                            score = response.getScore();
                            riskScore = response.getRiskScore();
                            expScore = response.getExpScore();
                            incomeScore = response.getIncomeScore();
                            isAttention = response.getWatched();
                            changeFocus(isAttention);
                            setSelect(0);
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeFocus(boolean isFocus) {
        if (isFocus) {
            organizationHomeIV.setHeadIsFocus();
        } else {
            organizationHomeIV.setHeadUnFocus();
        }
    }

    public void setSelect(int position) {
        currentPage = position;
        organizationHomeIV.removeAllViews();

        switch (position) {
            case 0:
                organizationHomeIV.setDynamicButtomChecked();
                getDynamicData(true);
                break;
            case 1:
                organizationHomeIV.setProductButtomChecked();
                getProductData();
                break;
        }
    }

    public void getFans() {
        Intent intent = new Intent(mContext, UserFansActivity.class);
        intent.putExtra("user_id", other_user_id);
        ActivityUtil.startActivity(mContext, intent);
    }

    public void doChat() {
       ToastUtil.showToastText("聊天");
    }

    public void doFocus() {
        String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        if(TextUtils.isEmpty(userId)){
            ActivityUtil.startActivity(mContext,new Intent(mContext, LoginActivity.class));
            return;
        }
        final InstitutionAttribute.WatchInstitutionReq req = InstitutionAttribute.WatchInstitutionReq.newBuilder().setUserId(other_user_id).setOtherUserId(userId).build();
        TiangongClient.instance().send("chronos", "watch_institution", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.WatchInstitutionResponse response = InstitutionAttribute.WatchInstitutionResponse.parseFrom(buffer);
                    if (response.getErrorno() == 0) {
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override
                            public void run() {
                                boolean isFocus = response.getWatched();
                                isAttention = true;
                                changeFocus(isFocus);
                                organizationHomeIV.setFansCount(isFocus ? String.valueOf(Integer.parseInt(organizationHomeIV.getFansCount()) + 1)
                                        : String.valueOf(Integer.parseInt(organizationHomeIV.getFansCount()) - 1));
                                sendBroadCast(list_position, isFocus);
                            }
                        });
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**发送广播刷新机构列表页的一条数据**/
    public void sendBroadCast(int position,boolean isFocus) {
        mContext.sendBroadcast(new Intent(Constants.ORGANIZATIONACTION).putExtra("position", position).putExtra("isFocus", isFocus));
    }


    public void getProductData() {
        String user_id = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        InstitutionAttribute.GetInstituteProductReq req;
        if (TextUtils.isEmpty(user_id)) {
            req = InstitutionAttribute.GetInstituteProductReq.newBuilder().setInstUserId(other_user_id).build();
        } else {
            req = InstitutionAttribute.GetInstituteProductReq.newBuilder().setInstUserId(other_user_id).setNormalUserId(user_id).build();
        }
        TiangongClient.instance().send("chronos", "get_institute_product", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final InstitutionAttribute.GetInstituteProductResponse response = InstitutionAttribute.GetInstituteProductResponse.parseFrom(buffer);
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            List<Common.LicaiProduct> productList = response.getProductList();
                            List<ProductEntity> productEntities = ModelParseUtil.ProductEntityParse(productList);
                            organizationHomeIV.setData(productEntities);
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadMore() {
        switch (currentPage) {
            case 0:
                getDynamicData(false);
                break;
            case 1:
                getProductData();
                break;
        }
    }


    public void dianZan(final DynamicEntity entity, final int position) {
        final String userId = PreferenceUtils.getInstance(mContext).readUserInfo().getUser_id();
        boolean hasZan = entity.isHasZan();
        final String msgId = entity.getMsgId();
        if (!hasZan) {
            //点赞
            Dynamic.DoZan msg = Dynamic.DoZan.newBuilder().setUserId(userId).setMsgId(msgId).build();
            TiangongClient.instance().send("chronos", "licaiquan_dozan", msg.toByteArray(), new BaseHandlerClass() {
                @Override public void onSuccess(byte[] buffer) {
                    try {
                        final Dynamic.DoZanResponse response = Dynamic.DoZanResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override public void run() {
                                if (response.getErrorno() == 0) {
                                    //TODO 每日任务  点赞一次

                                    organizationDynamicItemIVs.get(position).setPraiseCount(String.valueOf(Integer.parseInt(organizationDynamicItemIVs.get(position).getPraiseCount()) + 1));
                                    organizationDynamicItemIVs.get(position).setIsPraise();
                                    //自己封装一个赞的人的集合，然后点赞的时候就把自己加进去
                                    entity.setHasZan(true);
                                    entity.setZanCount(entity.getZanCount() + 1);
                                    //更新数据库
                                    ContentValues values = new ContentValues();
                                    values.put("zancount", entity.getZanCount());
                                    values.put("haszan", true);
                                    DataSupport.updateAll(DynamicEntity.class, values, "msgid = ?", entity.getMsgId());
                                }
                            }
                        });
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            //取消点赞
            Dynamic.UndoZan msg = Dynamic.UndoZan.newBuilder().setUserId(userId).setMsgId(msgId).build();
            TiangongClient.instance().send("chronos", "licaiquan_undozan", msg.toByteArray(), new BaseHandlerClass() {
                @Override public void onSuccess(byte[] buffer) {
                    try {
                        final Dynamic.UndoZanResponse response = Dynamic.UndoZanResponse.parseFrom(buffer);
                        MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                            @Override
                            public void run() {
                                if (response.getErrorno() == 0) {
                                    organizationDynamicItemIVs.get(position).setPraiseCount(String.valueOf(Integer.parseInt(organizationDynamicItemIVs.get(position).getPraiseCount()) - 1));
                                    organizationDynamicItemIVs.get(position).setUnPraise();
                                    entity.setHasZan(false);
                                    entity.setZanCount(entity.getZanCount() - 1);
                                    //更新数据库
                                    ContentValues values = new ContentValues();
                                    values.put("zancount", entity.getZanCount());
                                    values.put("haszan", false);
                                    DataSupport.updateAll(DynamicEntity.class, values, "msgid = ?", entity.getMsgId());
                                }
                            }
                        });

                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void intentToDynamicCommentDetial(int position, DynamicEntity entity) {
        Intent intent = new Intent(mContext, DynamicCommentDetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("entity", entity);
        ActivityUtil.startActivityForResult(mContext, intent, Constants.REQUEST_ORGANIZATION_HOME);
    }



    public void intentToOrganizationInf(boolean isAttention) {
        Intent intent = new Intent(mContext, OrganizationInfoActivity.class);
        intent.putExtra("user_id", other_user_id);
        intent.putExtra("position",list_position);
        intent.putExtra("fansCnt",fansCnt);
        intent.putExtra("score", score);
        intent.putExtra("riskScore", riskScore);
        intent.putExtra("expScore", expScore);
        intent.putExtra("incomeScore", incomeScore);
        intent.putExtra("isAttention",isAttention);
        ActivityUtil.startActivity(mContext, intent);
    }

    public void imageBrower(int position, List<ImgEntity> imgList) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        String images[] = new String[imgList.size()];
        for (int i = 0; i < imgList.size(); i++) {
            images[i] = imgList.get(i).getImgUrl();
        }
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, images);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        ActivityUtil.startActivity((Activity) mContext, intent);
    }


    public void showBottom(RelativeLayout rlDianPin) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlDianPin, "translationY", rlDianPin.getMeasuredHeight(), 0f);
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public void hideBottom(RelativeLayout rlDianPin) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlDianPin, "translationY", 0f, rlDianPin.getMeasuredHeight());
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    /**发布机构动态**/
    public void intentToPublish() {
        if (TextUtils.isEmpty(MyApp.getInstance().getUserId())) {
            ActivityUtil.startActivity(mContext, new Intent(mContext, LoginActivity.class));
        } else if(!isAttention){
            ToastUtil.showToastText("请先关注该机构才能发表点评");
        }else {
            ActivityUtil.startActivity(mContext,new Intent(mContext,PublishDynamicActivity.class).putExtra("organization_id",other_user_id));
        }
    }
}
