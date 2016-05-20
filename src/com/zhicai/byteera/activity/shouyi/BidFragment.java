package com.zhicai.byteera.activity.shouyi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.community.group.GroupEntity;
import com.zhicai.byteera.activity.community.topic.entity.FinancingCompanyEntity;
import com.zhicai.byteera.activity.event.JpushEvent;
import com.zhicai.byteera.activity.product.ProductDetailActivity;
import com.zhicai.byteera.activity.shouyi.adapter.BidPushAdapter;
import com.zhicai.byteera.activity.shouyi.entity.CompaniesIdList;
import com.zhicai.byteera.activity.shouyi.eventbus.CompaniesEvent;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.jpush.JpushProductEntity;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.SwitchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;


/**
 * Created by chenzhenxing on 15/11/18.
 */
@SuppressWarnings("unused")
public class BidFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_open) TextView tvOpen;
    @Bind(R.id.switch_open) SwitchView switchOpen;
    @Bind(R.id.tv_switch_show) TextView tvSwitchShow;
    @Bind(R.id.list_view) ListView mListView;
    @Bind(R.id.rl_remind_openbg) RelativeLayout rl_remind_openbg;
    @Bind(R.id.rl_nodata) RelativeLayout rlNoData;

    private BidPushAdapter mAdapter;
    private List<GroupEntity> groupList;
    private PopupWindow ppw;
    private Drawable imgOpen,imgClose;
    private boolean isOpen;
    private boolean result_toogle_state;
    private TextView tv8;
    private TextView tv12;
    private TextView tv16;
    private TextView tv17;
    private TextView tvNew;
    private TextView tv30;
    private TextView tv60;
    private TextView tv90;
    private TextView tv180;
    private TextView tv190;
    private TextView tvSave;
    private TextView tvCompanies;
    private RelativeLayout rlCompanies;
    private String tvCompaniesValue = "";
    private String tvCompaniesIdValue = "";
    private int avgIncomeType=0;
    private int avgLimitType=0;
    private List<String> companiesList;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bid_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initListView();
        initLocalDB();
        setSwitchListener();
        showPopWindow();
    }



    private void setSwitchListener() {
        switchOpen.setOnSwitchStateChangedListener(new SwitchView.OnSwitchStateChangedListener() {
            @Override
            public void onStateChanged(int state) {
                setToggleState(isOpen);
            }
        });
    }


    public void setToggleState(boolean getState){
        if (DetermineIsLogin()) return;
        if (getState){
            switchOpen.setState(true);
            tvSwitchShow.setText("已开启提醒");
            isOpen = false;
            setToogleSwitchState(true);
        }else{
            switchOpen.setState(false);
            tvSwitchShow.setText("已关闭提醒");
            isOpen = true;
            setToogleSwitchState(false);
        }
    }


    private void initListView() {
        mAdapter = new BidPushAdapter(getActivity());
        mListView.setAdapter(mAdapter);
    }

    private void initLocalDB() {
        getNotificationStatus();
        getDBJpushProduct();
    }

    private void getDBCompaniesIdList(){
        try {
            if (MyApp.getInstance().db.tableIsExist(CompaniesIdList.class)){
                clearTvCompaniesValue();
                List<CompaniesIdList> companiesIdLists = MyApp.getInstance().db.findAll(CompaniesIdList.class);
                if (companiesIdLists.size()>0){
                    for (CompaniesIdList companiesId:companiesIdLists){
                        if (!TextUtils.isEmpty(companiesId.getCompany_name())){tvCompaniesValue +=companiesId.getCompany_name()+",";}
                        if (!TextUtils.isEmpty(companiesId.getCompany_id())){tvCompaniesIdValue +=companiesId.getCompany_id()+",";}
                    }
                }else clearTvCompaniesValue();
                updateTvCompniesValue();
                Log.d("BidFragment","-value->"+tvCompaniesValue+",id-->"+tvCompaniesIdValue);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public void getDBJpushProduct(){
        MyApp.getInstance().executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!MyApp.getInstance().db.tableIsExist(JpushProductEntity.class)) {
                        rlNoData.setVisibility(View.VISIBLE);
                    } else {
                        List<JpushProductEntity> jpushProductEntityList = MyApp.getInstance().db.findAll(Selector.from(JpushProductEntity.class).orderBy("product_time", true));
                        refreshDBProduct(jpushProductEntityList);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshDBProduct(final List<JpushProductEntity> jpushProductEntityList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (jpushProductEntityList.size()>0) {
                    mListView.setVisibility(View.VISIBLE);
                    mAdapter.setData(jpushProductEntityList);
                } else {
                    rlNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnItemClick(R.id.list_view)
    void onItemClick(AdapterView<?> parent,int position){
        JpushProductEntity productEntity = (JpushProductEntity) parent.getAdapter().getItem(position);
        if (!TextUtils.isEmpty(productEntity.getProduct_id()))
            ActivityUtil.startActivity(getActivity(), new Intent(getActivity(),ProductDetailActivity.class).putExtra("productId",productEntity.getProduct_id()));
    }

    @OnClick({R.id.tv_open,R.id.tv_clear})
    public void clickListener(View view){
        switch (view.getId()){
            case R.id.tv_open:
                showPopWindow();
                imgClose = getResources().getDrawable(R.drawable.close);
                imgClose.setBounds(0, 0, imgClose.getMinimumWidth(), imgClose.getMinimumHeight());
                tvOpen.setCompoundDrawables(imgClose, null, null, null);
                tvOpen.setText("收起");
                break;
            case R.id.tv_clear:
                try {
                    if (MyApp.getInstance().db.tableIsExist(JpushProductEntity.class)) {
                        MyApp.getInstance().db.deleteAll(JpushProductEntity.class);
                        initListView();
                        mListView.setVisibility(View.GONE);
                        rlNoData.setVisibility(View.VISIBLE);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void showPopWindow() {
        if (ppw == null) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.pop_bit_condition, null);
            ppw = new PopupWindow(view, AbsoluteLayout.LayoutParams.MATCH_PARENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT);
            initPopView(view);
        }
        rl_remind_openbg.setBackgroundColor(Color.parseColor("#ced4d4"));
        ppw.setFocusable(true);
        ppw.setOutsideTouchable(true);
        ppw.setBackgroundDrawable(new BitmapDrawable());
        ppw.setAnimationStyle(R.style.PopupAnimation);
        ppw.update();
        ppw.showAsDropDown(tvOpen);
        ppw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imgOpen = getResources().getDrawable(R.drawable.open);
                imgOpen.setBounds(0, 0, imgOpen.getMinimumWidth(), imgOpen.getMinimumHeight());
                tvOpen.setCompoundDrawables(imgOpen, null, null, null);
                tvOpen.setText("展开");
                rl_remind_openbg.setBackgroundResource(R.color.white);
            }
        });
    }



    private void initPopView(View view) {
        tv8 = ButterKnife.findById(view, R.id.tv_8);
        tv12 = ButterKnife.findById(view, R.id.tv_12);
        tv16 = ButterKnife.findById(view, R.id.tv_16);
        tv17 = ButterKnife.findById(view, R.id.tv_17);
        tvNew = ButterKnife.findById(view, R.id.tv_new);
        tv30 = ButterKnife.findById(view, R.id.tv_30);
        tv60 = ButterKnife.findById(view, R.id.tv_60);
        tv90 = ButterKnife.findById(view, R.id.tv_90);
        tv180 = ButterKnife.findById(view, R.id.tv_180);
        tv190 = ButterKnife.findById(view, R.id.tv_190);
        tvSave = ButterKnife.findById(view, R.id.tv_save);
        tvCompanies = ButterKnife.findById(view, R.id.tv_companies);
        rlCompanies = ButterKnife.findById(view, R.id.rl_companies);
        clearTvCompaniesValue();
        tv8.setText("<8%");
        tv17.setText(">16%");
        tv30.setText("<30");
        tv190.setText(">180");
        tv8.setOnClickListener(this);
        tv12.setOnClickListener(this);
        tv16.setOnClickListener(this);
        tv17.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        tv30.setOnClickListener(this);
        tv60.setOnClickListener(this);
        tv90.setOnClickListener(this);
        tv180.setOnClickListener(this);
        tv190.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        rlCompanies.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_8:
                checkTv8();
                break;
            case R.id.tv_12:
                checkTv12();
                break;
            case R.id.tv_16:
                checkTv16();
                break;
            case R.id.tv_17:
                checkTv17();
                break;
            case R.id.tv_new:
                checkTvNew();
                break;
            case R.id.tv_30:
                checkTv30();
                break;
            case R.id.tv_60:
                checkTv60();
                break;
            case R.id.tv_90:
                checkTv90();
                break;
            case R.id.tv_180:
                checkTv180();
                break;
            case R.id.tv_190:
                checkTv190();
                break;
            case R.id.tv_save:
                doConfirm(avgIncomeType,avgLimitType);
                break;
            case R.id.rl_companies:
                ActivityUtil.startActivity(getActivity(),new Intent(getActivity(),CompaniesListActivity.class));
                break;
        }
    }

    private void checkTv190() {
        avgLimitType = 6;
        tvNew.setBackgroundResource(R.drawable.textview_corner_bid);
        tv30.setBackgroundResource(R.drawable.textview_corner_bid);
        tv60.setBackgroundResource(R.drawable.textview_corner_bid);
        tv90.setBackgroundResource(R.drawable.textview_corner_bid);
        tv180.setBackgroundResource(R.drawable.textview_corner_bid);
        tv190.setBackgroundResource(R.drawable.textview_corner_bid_check);
    }

    private void checkTv180() {
        avgLimitType = 5;
        tvNew.setBackgroundResource(R.drawable.textview_corner_bid);
        tv30.setBackgroundResource(R.drawable.textview_corner_bid);
        tv60.setBackgroundResource(R.drawable.textview_corner_bid);
        tv90.setBackgroundResource(R.drawable.textview_corner_bid);
        tv180.setBackgroundResource(R.drawable.textview_corner_bid_check);
        tv190.setBackgroundResource(R.drawable.textview_corner_bid);
    }

    private void checkTv90() {
        avgLimitType = 4;
        tvNew.setBackgroundResource(R.drawable.textview_corner_bid);
        tv30.setBackgroundResource(R.drawable.textview_corner_bid);
        tv60.setBackgroundResource(R.drawable.textview_corner_bid);
        tv90.setBackgroundResource(R.drawable.textview_corner_bid_check);
        tv180.setBackgroundResource(R.drawable.textview_corner_bid);
        tv190.setBackgroundResource(R.drawable.textview_corner_bid);
    }

    private void checkTv60() {
        avgLimitType = 3;
        tvNew.setBackgroundResource(R.drawable.textview_corner_bid);
        tv30.setBackgroundResource(R.drawable.textview_corner_bid);
        tv60.setBackgroundResource(R.drawable.textview_corner_bid_check);
        tv90.setBackgroundResource(R.drawable.textview_corner_bid);
        tv180.setBackgroundResource(R.drawable.textview_corner_bid);
        tv190.setBackgroundResource(R.drawable.textview_corner_bid);
    }

    private void checkTv30() {
        avgLimitType = 2;
        tvNew.setBackgroundResource(R.drawable.textview_corner_bid);
        tv30.setBackgroundResource(R.drawable.textview_corner_bid_check);
        tv60.setBackgroundResource(R.drawable.textview_corner_bid);
        tv90.setBackgroundResource(R.drawable.textview_corner_bid);
        tv180.setBackgroundResource(R.drawable.textview_corner_bid);
        tv190.setBackgroundResource(R.drawable.textview_corner_bid);
    }

    private void checkTvNew() {
        avgLimitType = 1;
        tvNew.setBackgroundResource(R.drawable.textview_corner_bid_check);
        tv30.setBackgroundResource(R.drawable.textview_corner_bid);
        tv60.setBackgroundResource(R.drawable.textview_corner_bid);
        tv90.setBackgroundResource(R.drawable.textview_corner_bid);
        tv180.setBackgroundResource(R.drawable.textview_corner_bid);
        tv190.setBackgroundResource(R.drawable.textview_corner_bid);
    }

    private void checkTv17() {
        avgIncomeType = 4;
        tv8.setBackgroundResource(R.drawable.textview_corner_bid);
        tv12.setBackgroundResource(R.drawable.textview_corner_bid);
        tv16.setBackgroundResource(R.drawable.textview_corner_bid);
        tv17.setBackgroundResource(R.drawable.textview_corner_bid_check);
    }

    private void checkTv16() {
        avgIncomeType = 3;
        tv8.setBackgroundResource(R.drawable.textview_corner_bid);
        tv12.setBackgroundResource(R.drawable.textview_corner_bid);
        tv16.setBackgroundResource(R.drawable.textview_corner_bid_check);
        tv17.setBackgroundResource(R.drawable.textview_corner_bid);
    }

    private void checkTv12() {
        avgIncomeType = 2;
        tv8.setBackgroundResource(R.drawable.textview_corner_bid);
        tv12.setBackgroundResource(R.drawable.textview_corner_bid_check);
        tv16.setBackgroundResource(R.drawable.textview_corner_bid);
        tv17.setBackgroundResource(R.drawable.textview_corner_bid);
    }

    private void checkTv8() {
        avgIncomeType = 1;
        tv8.setBackgroundResource(R.drawable.textview_corner_bid_check);
        tv12.setBackgroundResource(R.drawable.textview_corner_bid);
        tv16.setBackgroundResource(R.drawable.textview_corner_bid);
        tv17.setBackgroundResource(R.drawable.textview_corner_bid);
    }




    private void doConfirm(int avgIncomeType, int avgLimitType) {
        if (DetermineIsLogin()) return;
        if (avgIncomeType==0){
            ToastUtil.showToastText("请选择收益");
            return;
        }
        if (avgLimitType==0){
            ToastUtil.showToastText("请选择期限");
            return;
        }

        if (switchOpen.getState()==1) setToggleState(true);

        FinancingProduct.SetFinancingNotificationConditionReq.Builder req = FinancingProduct.SetFinancingNotificationConditionReq.newBuilder();
        FinancingProduct.NotificationIncomeType incomeType = FinancingProduct.NotificationIncomeType.valueOf(avgIncomeType);
        FinancingProduct.NotificationLimitType limitType = FinancingProduct.NotificationLimitType.valueOf(avgLimitType);

        //TODO 返回平台
        if (!TextUtils.isEmpty(tvCompaniesIdValue)){
            req.setCondition(FinancingProduct.NotificationCondition.newBuilder().setIncomeType(incomeType).setLimitType(limitType).setCompanies(tvCompaniesIdValue.substring(0,tvCompaniesIdValue.length()-1)));
        }else{
            req.setCondition(FinancingProduct.NotificationCondition.newBuilder().setIncomeType(incomeType).setLimitType(limitType));
        }
        req.setUserId(MyApp.getInstance().getUserId());
        final FinancingProduct.SetFinancingNotificationConditionReq build = req.build();
        Log.d("BidFragment", "type-->" + build.toString());
        TiangongClient.instance().send("chronos", "set_financing_notification_condition", build.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final Common.CommonResponse response = Common.CommonResponse.parseFrom(buffer);
                    Log.d("BidFragment", "response-->" + response.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                ToastUtil.showToastText("保存成功");
                                ppw.dismiss();
                                rl_remind_openbg.setBackgroundResource(R.color.white);
                            } else {
                                ToastUtil.showToastText("服务器异常");
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void getNotificationStatus() {
        if (DetermineIsLogin()) return;
        FinancingProduct.GetFinancingNotificationConditionReq req = FinancingProduct.GetFinancingNotificationConditionReq.newBuilder().setUserId(MyApp.getInstance().getUserId()).build();
        TiangongClient.instance().send("chronos", "get_financing_notification_condition", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final FinancingProduct.GetFinancingNotificationConditionResponse response = FinancingProduct.GetFinancingNotificationConditionResponse.parseFrom(buffer);
                    Log.d("BidFragment", "init status-->" + response.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                final FinancingProduct.NotificationIncomeType incomeType = response.getCondition().getIncomeType();
                                final FinancingProduct.NotificationLimitType limitType = response.getCondition().getLimitType();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateAvgIncome(incomeType);
                                        updateAvgLimit(limitType);
                                        if (response.getCondition().getCompaniesList().size() > 0)
                                            updateTvCompanies(response.getCondition().getCompaniesList());
                                        else clearTvCompaniesValue();
                                        setToggleState(response.getIsOpen());
                                    }
                                });
                            } else {
                                ToastUtil.showToastText("获取失败!");
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private boolean setToogleSwitchState(boolean open) {

        FinancingProduct.SetFinancingNotificationSwitchStateReq req = FinancingProduct.SetFinancingNotificationSwitchStateReq.newBuilder().setUserId(MyApp.getInstance().getUserId()).setIsOpen(open).build();
        TiangongClient.instance().send("chronos", "set_financing_notification_switch_state", req.toByteArray(), new BaseHandlerClass() {
            @Override
            public void onSuccess(byte[] buffer) {
                try {
                    final Common.CommonResponse response = Common.CommonResponse.parseFrom(buffer);
                    Log.d("BidFragment", "toogle state res-->" + response.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getErrorno() == 0) {
                                result_toogle_state = true;
                            } else {
                                result_toogle_state = false;
                                Log.d("BidFragment", "err-->" + response.getErrorDescription());
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
        return result_toogle_state;
    }

    private void updateTvCompanies(List<Common.FinancingCompany> companiesList) {
        clearTvCompaniesValue();
        final List<CompaniesIdList> companiesIdLists = new ArrayList();

        try {
            if (MyApp.getInstance().db.tableIsExist(CompaniesIdList.class)) {
                List<CompaniesIdList> dbCompaniesList = MyApp.getInstance().db.findAll(CompaniesIdList.class);
                for (CompaniesIdList companiesId:dbCompaniesList){
                    FinancingCompanyEntity updateCompany = new FinancingCompanyEntity(companiesId.getId(),companiesId.getCompany_id(),false);
                    MyApp.getInstance().db.update(updateCompany, "id", "company_id", "isChecked");
                    Log.d("BidFragment","db update all false -->"+updateCompany.toString());
                }
                MyApp.getInstance().db.deleteAll(CompaniesIdList.class);
                Log.d("BidFragment","db delete all "+dbCompaniesList.size());
            }

            for (Common.FinancingCompany company:companiesList){
                Log.d("BidFragment","server "+company.toString());
                tvCompaniesValue+= company.getCompanyName()+",";
                companiesIdLists.add(new CompaniesIdList(company.getCompanyId(), company.getCompanyName()));
                FinancingCompanyEntity company_id = MyApp.getInstance().db.findFirst(Selector.from(FinancingCompanyEntity.class).where("company_id", "=", company.getCompanyId()));
                if (company_id!=null){
                    FinancingCompanyEntity updateCompany = new FinancingCompanyEntity(company_id.getId(),company_id.getCompany_id(),true);
                    MyApp.getInstance().db.update(updateCompany, "id", "company_id", "isChecked");
                    Log.d("BidFragment", "db update set true -->" + updateCompany.toString());
                }
            }
            updateTvCompniesValue();
            MyApp.getInstance().db.saveBindingIdAll(companiesIdLists);
            Log.d("BidFragment", "db save CompaniesIdList-->" + companiesIdLists.size());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }




    private void updateAvgIncome(FinancingProduct.NotificationIncomeType incomeType) {
        switch (incomeType){
            case lt8:
                checkTv8();
                break;
            case gte8lt12:
                checkTv12();
                break;
            case gte12lt16:
                checkTv16();
                break;
            case gte16:
                checkTv17();
                break;
        }
    }

    private void updateAvgLimit(FinancingProduct.NotificationLimitType limitType) {
        switch (limitType){
            case beginner:
                checkTvNew();
                break;
            case lte30:
                checkTv30();
                break;
            case gt30lte60:
                checkTv60();
                break;
            case gt60lte90:
                checkTv90();
                break;
            case gt90lte180:
                checkTv180();
                break;
            case gt180:
                checkTv190();
                break;

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(CompaniesEvent event) {
        Log.d("BidFragment", "接收onEventMainThread信息 CompaniesEvent-->" + event.isRefresh());
        getDBCompaniesIdList();
    }

    public void onEventMainThread(JpushEvent event) {
        Log.d("BidFragment", "接收onEventMainThread信息 JpushEvent-->" + event.isRefreshUI());
    }

    private void updateTvCompniesValue() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(tvCompaniesValue))
                    tvCompanies.setText(tvCompaniesValue.substring(0, tvCompaniesValue.length() - 1));
                else tvCompanies.setText("指定平台（可选填）");
            }
        });
    }

    private void clearTvCompaniesValue(){
        tvCompaniesValue = "";
        tvCompaniesIdValue = "";
    }


}
