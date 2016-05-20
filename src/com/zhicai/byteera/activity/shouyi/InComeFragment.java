package com.zhicai.byteera.activity.shouyi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseFragment;
import com.zhicai.byteera.activity.event.NetWorkEvent;
import com.zhicai.byteera.activity.product.ProductDetailActivity;
import com.zhicai.byteera.activity.product.entity.ProductEntity;
import com.zhicai.byteera.activity.shouyi.adapter.InComeAdapter;
import com.zhicai.byteera.activity.shouyi.eventbus.InComeEvents;
import com.zhicai.byteera.activity.shouyi.view.IncomeView;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.LogUtil;
import com.zhicai.byteera.commonutil.ModelParseUtil;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.UIUtils;
import com.zhicai.byteera.commonutil.Utils;
import com.zhicai.byteera.service.Common;
import com.zhicai.byteera.service.product.FinancingProduct;
import com.zhicai.byteera.service.serversdk.BaseHandlerClass;
import com.zhicai.byteera.service.serversdk.TiangongClient;
import com.zhicai.byteera.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;


@SuppressWarnings("unused")
public class InComeFragment extends BaseFragment implements IncomeView{
    private static final String TAG = InComeFragment.class.getSimpleName();

    @Bind(R.id.list_view) LoadMoreListView mListView;
    @Bind(R.id.et_coin) EditText etCoin;
    @Bind(R.id.seekbar) SeekBar mSeekbar;
    @Bind(R.id.tv_condition_time) TextView tvTime;
    @Bind(R.id.tv_condition_rate) TextView tvRate;
    @Bind(R.id.tv_condition_bank) TextView tvBank;
    @Bind(R.id.tv_condition_p2p) TextView tvP2p;
    @Bind(R.id.tv_cnt_money) TextView tvCntMoney;

    private InComeAdapter adapter;
    private List<ProductEntity> productEntities = new ArrayList();
    private String lastMsgId;
    private int conditionTime;
    private int conditionRate;
    private boolean conditionBank = true;
    private boolean conditionP2p = true;
    private boolean isClick = false;
    private boolean isClickBank = true;
    private boolean isClickP2p = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.income_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initView();
        initListView();
        setListener();
    }

    private void initView() {
        if (!isClick) {
            tvTime.setText("周期<=90天");
            tvRate.setText("利率<=18%");
            conditionBank = true;
            conditionP2p = true;
            tvCntMoney.setText(10000 + "");
        }
        UIUtils.hideKeyboard(getActivity(), etCoin);
    }

    private void initListView() {
        getDynamicFromNet(true);
        adapter = new InComeAdapter(getActivity(), productEntities);
        mListView.setAdapter(adapter);
        mListView.setLoadMoreDataListener(new LoadMoreListView.LoadMoreDataListener() {
            @Override
            public void loadMore() {
                getDynamicFromNet(false);
            }
        });
    }


    private void setListener() {
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int p = seekBar.getProgress();
                etCoin.setText(transformInteger(p) + "");
                tvCntMoney.setText(transformInteger(p) + "");
                adapter.updateView(productEntities, seekBar.getProgress());
            }
        });
    }


    private int transformInteger(int p) {
        int i;
        i = p % 100;
        p -= i;
        if (i > 50) p += 100;
        return p;
    }

    @OnItemClick(R.id.list_view)
    public void onItemClickListener(AdapterView<?> parent, int position) {
        ProductEntity productEntity = (ProductEntity) parent.getAdapter().getItem(position);
        if (productEntity!=null && !TextUtils.isEmpty(productEntity.getProductId()))
            ActivityUtil.startActivity(getActivity(), new Intent(getActivity(),ProductDetailActivity.class).putExtra("productId",productEntity.getProductId()));
    }

    @OnTextChanged(value = R.id.et_coin, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onAfterTextChanged() {
        String etCoinValue = etCoin.getText().toString().equals("") ? "0" : etCoin.getText().toString();
        if ((Integer.valueOf(etCoinValue) % 100) != 0) {
            ToastUtil.showToastText("请输入100的整数倍");
        }
        if (etCoinValue.length() > 6) {
            setEtCoinInitValue();
        }
        if (Integer.valueOf(etCoinValue) >= 100050) {
            ToastUtil.showToastText("不能超出总金额,重新输入");
            setEtCoinInitValue();
        } else {
            tvCntMoney.setText(etCoinValue);
            mSeekbar.setProgress(Integer.valueOf(etCoinValue));
            adapter.updateView(productEntities, Integer.valueOf(etCoinValue));
        }
    }

    private void setEtCoinInitValue() {
        etCoin.setText(10000 + "");
        tvCntMoney.setText(10000 + "");
        mSeekbar.setProgress(10000);
    }

    @OnClick({R.id.tv_jump, R.id.tv_condition_time, R.id.tv_condition_rate, R.id.tv_condition_bank, R.id.tv_condition_p2p})
    public void clickListener(View v) {
        switch (v.getId()) {
            case R.id.tv_condition_time:
                tvTime.setVisibility(View.GONE);
                conditionTime = 90;
                updateData();
                break;
            case R.id.tv_condition_rate:
                tvRate.setVisibility(View.GONE);
                conditionRate = 30;
                updateData();
                break;
            case R.id.tv_condition_bank:
                tvBank.setVisibility(View.GONE);
                isClickBank = false;
                conditionBank = false;
                updateData();
                break;
            case R.id.tv_condition_p2p:
                tvP2p.setVisibility(View.GONE);
                isClickP2p = false;
                conditionP2p = false;
                updateData();
                break;
            case R.id.tv_jump:
                Intent intent = new Intent(getActivity(), InComeConditionActivity.class);
                if (tvBank.getText().equals("直销银行")) intent.putExtra("conditionBank", isClickBank);
                if (tvP2p.getText().equals("p2p")) intent.putExtra("conditionP2p", isClickP2p);
                intent.putExtra("conditionTime", conditionTime);
                intent.putExtra("conditionRate", conditionRate);
                ActivityUtil.startActivity(getActivity(), intent);
                break;
        }
    }


    public void onEventMainThread(InComeEvents event) {
        LogUtil.d("接收onEventMainThread信息 InComeEvents：%s",event.getConditionTime());
        conditionTime = event.getConditionTime();
        conditionRate = event.getConditionRate();
        conditionBank = event.isConditionBank();
        conditionP2p = event.isConditionP2p();
        tvTime.setText("周期<=" + (conditionTime == 0 ? 90 : conditionTime) + "天");
        tvRate.setText("利率<=" + (conditionRate == 0 ? 18 : conditionRate) + "%");
        isClick = event.isClick();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateUI();
            }
        });
    }


    public void onEventMainThread(NetWorkEvent event) {
        Log.d("KnowWealthFragment", "接收onEventMainThread信息 NetWorkEvent-->" + event.isNetwork());
        getDynamicFromNet(true);
    }



    private void updateUI() {
        productEntities.clear();
        getDynamicFromNet(true);
        if (conditionTime > 0) tvTime.setVisibility(View.VISIBLE);
        if (conditionRate > 0) tvRate.setVisibility(View.VISIBLE);

        if (conditionBank && conditionP2p) {
            tvBank.setVisibility(View.VISIBLE);
            tvP2p.setVisibility(View.VISIBLE);
            isClickBank = true;
            isClickP2p = true;
        } else {
            tvBank.setVisibility(View.GONE);
            tvP2p.setVisibility(View.GONE);
            isClickBank = false;
            isClickP2p = false;
        }

        if (conditionBank) {
            tvBank.setVisibility(View.VISIBLE);
            isClickBank = true;
        } else {
            tvBank.setVisibility(View.GONE);
            isClickBank = false;
        }

        if (conditionP2p) {
            tvP2p.setVisibility(View.VISIBLE);
            isClickP2p = true;
        } else {
            tvP2p.setVisibility(View.GONE);
            isClickP2p = false;
        }
    }

    public void updateData() {
        productEntities.clear();
        getDynamicFromNet(true);
    }


    public void getDynamicFromNet(final boolean isFirst) {
        FinancingProduct.IncomeComparisionGetReq.Builder req = FinancingProduct.IncomeComparisionGetReq.newBuilder();
        StringBuilder sb = new StringBuilder();
        int p2p = Common.ProductType.zhiCai_p2p_VALUE;
        int bank = Common.ProductType.zhiCai_zxyh_VALUE;
        if (isFirst) {
            if (conditionTime != 0)
                req.setCondLimit(FinancingProduct.IncomeComparisionGetCondLimit.newBuilder().setMax(conditionTime));
            else
                req.setCondLimit(FinancingProduct.IncomeComparisionGetCondLimit.newBuilder().setMax(90));
            if (conditionRate != 0)
                req.setCondIncome(FinancingProduct.IncomeComparisionGetCondIncome.newBuilder().setMax(((float) conditionRate) / 100));
            else
                req.setCondIncome(FinancingProduct.IncomeComparisionGetCondIncome.newBuilder().setMax(0.18f));
            if (conditionBank) sb.append(bank).append(",");
            if (conditionP2p) sb.append(p2p).append(",");
            if (!TextUtils.isEmpty(sb.toString())) {
                String types = sb.toString().substring(0, sb.toString().lastIndexOf(","));
                req.setTypes(types);
            }
            req.setIsafter(false);
        } else {
            if (!TextUtils.isEmpty(lastMsgId)) req.setProductId(lastMsgId);
            req.setIsafter(false);
        }
        final FinancingProduct.IncomeComparisionGetReq builder = req.build();
        LogUtil.d("isfirst : %s, builder: %s", isFirst, builder.toString());
        TiangongClient.instance().send("chronos", "income_comparision_get", builder.toByteArray(), new BaseHandlerClass() {
            public void onSuccess(byte[] buffer) {
                try {
                    final FinancingProduct.IncomeComparisionGetResponse response = FinancingProduct.IncomeComparisionGetResponse.parseFrom(buffer);
                    LogUtil.d("groupHome: %s", response.toString());
                    MyApp.getMainThreadHandler().postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            if (response.getErrorno() == 0 && response.getProductsCount() > 0) {
                                productEntities = ModelParseUtil.ProductEntityParse(response.getProductsList());
                                if (productEntities.size() > 0) {
                                    lastMsgId = productEntities.get(productEntities.size() - 1).getProductId();
                                    loadData(isFirst);
                                }
                            }
                        }
                    });
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadData(boolean isFirst) {
        if (isFirst) {
            adapter.setData(productEntities);
        } else {
            adapter.addAllItem(productEntities);
        }
        mListView.loadComplete();
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.setingNetWork(getActivity());
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void loadComplete() {
        mListView.loadComplete();
    }

    @Override
    public void setData(List<ProductEntity> productEntities) {
        adapter.setData(productEntities);
    }

    @Override
    public void addAllItem(List<ProductEntity> productEntities) {
        adapter.addAllItem(productEntities);
    }
}
