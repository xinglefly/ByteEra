package com.zhicai.byteera.activity.initialize;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.widget.HeadViewMain;

import butterknife.Bind;
import butterknife.ButterKnife;

/** Created by bing on 2015/5/6. */
@SuppressWarnings("unused")
public class AboutUSActivity extends BaseActivity {

    @Bind(R.id.head_title) HeadViewMain headView;
    @Bind(R.id.tv_version) TextView tvVersion;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.aboutus_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        try {
            tvVersion.setText("Android "+getVersionName()+"版");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        headView.setLeftImgClickListener(new HeadViewMain.LeftImgClickListner() {
            @Override
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
    }


    private String getVersionName() throws Exception
    {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }
}
