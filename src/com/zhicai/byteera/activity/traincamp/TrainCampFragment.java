package com.zhicai.byteera.activity.traincamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.traincamp.activity.GuessPicActivity;
import com.zhicai.byteera.activity.traincamp.activity.WealthTaskActivity;
import com.zhicai.byteera.activity.traincamp.view.TrainCampItemView;
import com.zhicai.byteera.commonutil.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TrainCampFragment extends Fragment implements TrainCampItemView.OnButtonOnclickListener{

	@Bind(R.id.wealth_task) TrainCampItemView mWealthTask;
	@Bind(R.id.guss_financial) TrainCampItemView mGussFinancial;
	@Bind(R.id.guss_change) TrainCampItemView mGussChange;
	@Bind(R.id.buy_money) TrainCampItemView mBuyMoney;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.train_camp_fragment, container,false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ButterKnife.bind(this,getView());
		initItem();
	}

	private void initItem() {
		mWealthTask.setOnButtonClickListener(this);
		mGussFinancial.setOnButtonClickListener(this);
		mGussChange.setOnButtonClickListener(this);
		mBuyMoney.setOnButtonClickListener(this);
	}


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
	public void onButtonClick(View view) {
		switch (view.getId()){
			case R.id.wealth_task:
				startActivity(new Intent(getActivity(), WealthTaskActivity.class));
				break;
			case R.id.guss_financial:
				startActivity(new Intent(getActivity(), GuessPicActivity.class));
				break;
			case R.id.guss_change:
				ToastUtil.showToastText( "猜大盘暂未做");

				break;
			case R.id.buy_money:
				ToastUtil.showToastText( "第四条");
				break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
}
