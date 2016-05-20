package com.zhicai.byteera.activity.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.util.DateUtils;
import com.zhicai.byteera.R;
import com.zhicai.byteera.activity.BaseActivity;
import com.zhicai.byteera.commonutil.Constants;
import com.zhicai.byteera.commonutil.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SystemMessageActivity extends BaseActivity {

    @Bind(R.id.img_back) ImageView back;
    @Bind(R.id.tv_titlename) TextView titleName;
    @Bind(R.id.img_settings) ImageView settings;
    @Bind(R.id.list_system_message) ListView listView;
    private List<EMMessage> messages;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_system_message);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        titleName.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        settings.setVisibility(View.VISIBLE);

        titleName.setText("财讯精选");

        EMConversation conversation = EMChatManager.getInstance().getConversation(Constants.SYSTEM_MESSAGE_ACCOUNT);
        messages = new ArrayList<EMMessage>();
        for (EMMessage message : conversation.getAllMessages()) {
            messages.add(message);
        }

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void updateUI() {
        if (messages.size() > 0) {
            SystemMessageAdapter adapter = new SystemMessageAdapter();
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void processLogic() {

    }

    private class SystemMessageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(SystemMessageActivity.this).inflate(R.layout.system_message_item, null);
                holder.timeStamp = (TextView) convertView.findViewById(R.id.tv_timestamp);
                holder.messageImage = (ImageView) convertView.findViewById(R.id.iv_message_image);
                holder.messageTitle1 = (TextView) convertView.findViewById(R.id.tv_message_title1);
                holder.smallMessageImage = (ImageView) convertView.findViewById(R.id.iv_small_message_image);
                holder.messageTitle2 = (TextView) convertView.findViewById(R.id.tv_message_title2);
                convertView.setTag(UIUtils.getScreenHeight(baseContext));
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.timeStamp.setText(DateUtils.getTimestampString(new Date(messages.get(position).getMsgTime())));
            return convertView;
        }

    }

    private class ViewHolder {
        TextView timeStamp;
        ImageView messageImage;
        TextView messageTitle1;
        ImageView smallMessageImage;
        TextView messageTitle2;

    }

}
