package com.zhicai.byteera.activity.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhicai.byteera.MyApp;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.activity.message.entity.ContactInfo;
import com.zhicai.byteera.commonutil.ActivityUtil;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.ToastUtil;
import com.zhicai.byteera.commonutil.StringUtil;
import com.zhicai.byteera.commonutil.ViewHolder;
import com.zhicai.byteera.widget.HeadViewMain;
import com.zhicai.byteera.widget.HeadViewMain.LeftImgClickListner;
import com.zhicai.byteera.widget.HeadViewMain.RightTextClickListener;
import com.zhicai.byteera.widget.LoadingPage;
import com.zhicai.byteera.widget.MyLetterSortView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/** Created by bing on 2015/5/25. */
public class SelectAddressActivity extends BaseActivity {
    @Bind(R.id.head_view) HeadViewMain mHeadView;
    @Bind(R.id.list_view) ListView mListView;
    @Bind(R.id.loading_page) LoadingPage mLoadingPage;
    @Bind(R.id.rl_bottom) RelativeLayout rlBottom;
    @Bind(R.id.right_letter) MyLetterSortView mRightLetter;
    @Bind(R.id.tv_mid_letter) TextView tvMidLetter;
    private List<ContactInfo> infos;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private boolean single = true;
    private ContactsAdapter mAdapter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.select_address_activity);
        ButterKnife.bind(this);
        mRightLetter.setTextView(tvMidLetter);
    }

    @Override
    protected void initData() {
        mLoadingPage.showPage(Constants.STATE_LOADING);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                infos = ContactInfoParser.findAll(baseContext);
                Collections.sort(infos);
                MyApp.getMainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingPage.hidePage();
                        if (mAdapter == null) {
                            mAdapter = new ContactsAdapter();
                            mListView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetInvalidated();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void processLogic() {
        mHeadView.setLeftImgClickListener(new LeftImgClickListner() {
            public void onLeftImgClick() {
                ActivityUtil.finishActivity(baseContext);
            }
        });
        mHeadView.setRightTextClickListener(new RightTextClickListener() {
            @Override
            public void onRightTextClick() {
                initData();
            }
        });
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!single) {
                    infos.get(position).setIsCheck(!infos.get(position).isCheck()); //改变状态
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        // 设置右侧触摸监听
        mRightLetter.setOnTouchingLetterChangedListener(new MyLetterSortView.OnTouchingLetterChangedListener() {
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }
            }
        });
    }

    @OnClick({R.id.tv_quit, R.id.tv_finish, R.id.tv_adds})
    public void clickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_quit:
                //调整listView的状态
                single = true;
                for (int i = 0; i < infos.size(); i++) {
                    if (infos.get(i).isCheck()) {
                        infos.get(i).setIsCheck(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
                rlBottom.setVisibility(View.GONE);
                break;
            case R.id.tv_finish:
                //跳转到发送短信界面，并且携带多个联系人
                List<String> mList = new ArrayList<String>();
                for (int i = 0; i < infos.size(); i++) {
                    if (infos.get(i).isCheck()) {
                        mList.add(infos.get(i).getPhone());
                    }
                }
                if (mList.size() <= 0) {
                    ToastUtil.showToastText( "请至少选择一位联系人");

                } else {
//                    ShareUtils.shareSms(baseContext, getResources().getString(R.string.share_context), mList.toArray(new String[mList.size()]));

                }
                break;
            case R.id.tv_adds:
                //改变状态
                single = !single;
                mAdapter.notifyDataSetChanged();
                if (!single) {
                    //底部弹出对话框，用于多选完成
                    rlBottom.setVisibility(View.VISIBLE);
                } else {
                    rlBottom.setVisibility(View.GONE);
                }
                //请出所有已选择的联系人
                for (int i = 0; i < infos.size(); i++) {
                    if (infos.get(i).isCheck()) {
                        infos.get(i).setIsCheck(false);
                    }
                }
                break;
            default:
                break;
        }
    }

    private class ContactsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ContactInfo user = infos.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(baseContext).inflate(R.layout.item_contact, parent, false);
            }
            TextView tv_name = ViewHolder.get(convertView, R.id.tv_item_name);
            CheckBox cb_check = ViewHolder.get(convertView, R.id.cb_check);
            TextView tv_yaoqing = ViewHolder.get(convertView, R.id.tv_yaoqing);
            TextView group_title = ViewHolder.get(convertView, R.id.group_title);
            tv_name.setText(user.getName());
            if (!single) {  //多选联系人状态
                cb_check.setVisibility(View.VISIBLE);
                tv_yaoqing.setVisibility(View.GONE);
                if (user.isCheck()) {
                    cb_check.setChecked(true);
                } else {
                    cb_check.setChecked(false);
                }

            } else {    //单选联系人状态
                cb_check.setVisibility(View.GONE);
                tv_yaoqing.setVisibility(View.VISIBLE);
                tv_yaoqing.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //邀请指定的联系人
//                        ShareUtils.shareSms(baseContext, getResources().getString(R.string.share_context), user.getPhone());
                    }
                });
            }
            //显示中间的字母
            int section = getSectionForPosition(position);
            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                group_title.setVisibility(View.VISIBLE);
                group_title.setText(StringUtil.getPinYinHeadChar(user.getName()).substring(0, 1));
            } else {
                group_title.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public int getSectionForPosition(int position) {
            return StringUtil.getPinYinHeadChar(infos.get(position).getName()).charAt(0);
        }

        public int getPositionForSection(int section) {
            for (int i = 0; i < getCount(); i++) {
                char firstChar = StringUtil.getPinYinHeadChar(infos.get(i).getName()).charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
            return -1;
        }
    }
}
