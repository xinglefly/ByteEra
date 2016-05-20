package com.zhicai.byteera.activity.shouyi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.event.JpushEvent;
import com.zhicai.byteera.activity.shouyi.view.ButtonView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


@SuppressWarnings("unused")
public class LiCaiShouYiFragment extends Fragment {

    @Bind(R.id.button_view) ButtonView buttonView;
    private InComeFragment mTab01;
    private BidFragment mTab02;
    private int type = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shouyi_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        setSelect(type);
        progressLogic();
    }

    public void progressLogic(){
        buttonView.setButtonViewCheckedListener(new ButtonView.ButtonViewCheckedListener() {
            @Override
            public void checkedIncome() {
                setSelect(0);
            }

            @Override
            public void checkedBid() {
                setSelect(1);
            }
        });
    }


    private void setSelect(int i) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new InComeFragment();
                }
                transaction.replace(R.id.rl_container, mTab01);
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new BidFragment();
                }
                transaction.replace(R.id.rl_container, mTab02);
                break;
        }
        transaction.commit();
    }


    public void onEventMainThread(JpushEvent event) {
        Log.d("LiCaiShouYiFragment", "接收onEventMainThread信息 JpushEvent-->" + event.getType());
//        type = event.getType();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
