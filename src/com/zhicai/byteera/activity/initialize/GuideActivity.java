package com.zhicai.byteera.activity.initialize;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.DbException;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.MainActivity;
import com.zhicai.byteera.activity.bean.GuessDb;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.PreferenceUtils;
import com.zhicai.byteera.commonutil.SDLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnPageChange;

@SuppressWarnings("unused")
public class GuideActivity extends BaseActivity {

	@Bind(R.id.viewpager) ViewPager viewpager;
	@Bind(R.id.tv_jump) TextView tvJump;
	@Bind({R.id.iv_1,R.id.iv_2,R.id.iv_3}) ImageView[] dots;
	private final List<View> views = new ArrayList();
	private View view3;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_guide);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.tv_jump)
	void onClick(){
		startApp();
	}


	@OnPageChange(value = R.id.viewpager,callback = OnPageChange.Callback.PAGE_SELECTED)
	public void onPageSelected(int position){
		for (int i=0;i<dots.length;i++)
			dots[i].setImageResource(position==i?R.drawable.point_selected:R.drawable.point_unselected);
	}

	@Override
	protected void initData() {
		views.add(getLayoutInflater().inflate(R.layout.item_guide_01, null));
		views.add(getLayoutInflater().inflate(R.layout.item_guide_02, null));
		view3 = getLayoutInflater().inflate(R.layout.item_guide_03, null);
		views.add(view3);
		viewpager.setAdapter(new vpAdapter());
	}

	@Override
	protected void updateUI() {
	}

	@Override
	protected void processLogic() {
		ButterKnife.findById(view3,R.id.mstart).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startApp();
			}
		});
	}

	private void startApp() {
		PreferenceUtils.getInstance(baseContext).setStartGuide(true);
		createGuessDb();
		goHome();
	}

	protected void goHome() {
		ActivityUtil.startActivity(this, new Intent(this, MainActivity.class));
		ActivityUtil.finishActivity(this);
	}

	public void createGuessDb(){
		try {
			for (int i=0;i<8;i++)
				for (int j=1;j<4;j++) db.save(new GuessDb(i+1,j,1));
			SDLog.d("GuideActivity","guessdb create is ok!");
		} catch (DbException e) {
			e.printStackTrace();
		}
	}


	private class vpAdapter extends PagerAdapter {
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}


		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

}
