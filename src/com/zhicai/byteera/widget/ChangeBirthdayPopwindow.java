package com.zhicai.byteera.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupWindow;

import com.zhicai.byteera.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Created by lieeber on 15/8/7. */
public class ChangeBirthdayPopwindow {

    private final DatePicker mDatePicker;
    private PopupWindow changeBirthPop;

    public void setBirthday(String[] date) {
        if (date.length == 3) {
            mDatePicker.init(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), new DatePicker.OnDateChangedListener() {
                @Override public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

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

    public String getDate() {
        return String.valueOf(mDatePicker.getYear()) + "-" + String.valueOf(mDatePicker.getMonth() + 1) + "-" + String.valueOf(mDatePicker.getDayOfMonth());

    }

    public interface ChangeOkListener {
        void onOkClick();
    }

    private ChangeOkListener changeOkListener;


    public void setChangeOkListener(ChangeOkListener changeOkListener) {
        this.changeOkListener = changeOkListener;
    }

    public void showAtLocation(View locationView) {
        changeBirthPop.setFocusable(true);
        changeBirthPop.setOutsideTouchable(true);
        changeBirthPop.setBackgroundDrawable(new BitmapDrawable());
        changeBirthPop.setAnimationStyle(R.style.modifyUserInfoPop);
        changeBirthPop.update();
        changeBirthPop.showAtLocation(locationView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public ChangeBirthdayPopwindow(Activity mContext) {
        View view = mContext.getLayoutInflater().inflate(R.layout.select_birthday_dialog, null);
        changeBirthPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeBirthPop.dismiss();
            }
        });
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeBirthPop.dismiss();
                if (changeOkListener != null) {
                    changeOkListener.onOkClick();
                }
            }
        });
        mDatePicker = (DatePicker) view.findViewById(R.id.date_picker);
    }
}
