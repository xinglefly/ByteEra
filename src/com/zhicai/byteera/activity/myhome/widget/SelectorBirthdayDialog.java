package com.zhicai.byteera.activity.myhome.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.UIUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Created by bing on 2015/5/8. */
public class SelectorBirthdayDialog extends AlertDialog {
    private Context mContext;
    private DatePicker mDatePicker;

    public interface OnOkClickListener {
        void onOk();
    }

    private OnOkClickListener okClickListener;

    public void setOkClickListener(OnOkClickListener listener) {
        this.okClickListener = listener;
    }

    public SelectorBirthdayDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public SelectorBirthdayDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_birthday_dialog);
        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.width = UIUtils.dip2px(mContext, 300);
        this.getWindow().setAttributes(attributes);
        mDatePicker = (DatePicker) findViewById(R.id.date_picker);
        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClickListener.onOk();
                dismiss();
            }
        });
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public String getYear() {
        return String.valueOf(mDatePicker.getYear());
    }

    public String getMonth() {
        return String.valueOf(mDatePicker.getMonth()) + 1;
    }

    public String getDay() {
        return String.valueOf(mDatePicker.getDayOfMonth());
    }

    public String getDate() {
        return String.valueOf(mDatePicker.getYear()) + "-" + String.valueOf(mDatePicker.getMonth() + 1) + "-" + String.valueOf(mDatePicker.getDayOfMonth());
    }

    public void setBirthday(String... date) {
        if (date.length == 3) {
            mDatePicker.init(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                }
            });
        } else {
            Date current = new Date();
            long time = current.getTime();
            Format format = new SimpleDateFormat("yyyy-MM-dd");
            String format1 = format.format(time);
            String[] split = format1.split("-");
            mDatePicker.init(Integer.parseInt(split[0]), Integer.parseInt(split[1]) - 1, Integer.parseInt(split[2]), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                }
            });
        }

    }

}
