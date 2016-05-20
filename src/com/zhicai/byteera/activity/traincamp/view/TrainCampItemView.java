package com.zhicai.byteera.activity.traincamp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhicai.byteera.R;

/**
 * Created by bing on 2015/4/27.
 */
public class TrainCampItemView extends FrameLayout {

    public interface  OnButtonOnclickListener{
        void  onButtonClick(View v);
    }
    private OnButtonOnclickListener linster;

    public void setOnButtonClickListener(OnButtonOnclickListener listener) {
        this.linster = listener;
    }
    private Context mContext;
    public TrainCampItemView(Context context) {
        this(context, null);
    }

    public TrainCampItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrainCampItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;

        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.TrainCampItem);
        String primaryText = attributes.getString(R.styleable.TrainCampItem_primaryText);
        String secondText = attributes.getString(R.styleable.TrainCampItem_secondText);
        String buttonText = attributes.getString(R.styleable.TrainCampItem_buttonText);
        int buttonColor = attributes.getColor(R.styleable.TrainCampItem_buttonColor,getResources().getColor(R.color.orange));
        Drawable leftImage = attributes.getDrawable(R.styleable.TrainCampItem_leftImage);

        LayoutInflater.from(mContext).inflate(R.layout.train_camp_item, this, true);

        this.findViewById(R.id.tv_button).setBackgroundColor(buttonColor);
        ((ImageView) this.findViewById(R.id.iv_left)).setImageDrawable(leftImage);
        ((TextView) this.findViewById(R.id.tv_primary)).setText(primaryText);
        ((TextView) this.findViewById(R.id.tv_second)).setText(secondText);
        ((TextView) this.findViewById(R.id.tv_button)).setText(buttonText);

        this.findViewById(R.id.tv_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                linster.onButtonClick((View) (v.getParent().getParent()));
            }
        });
    }

    public void setButtonBackground(int resource) {
        this.findViewById(R.id.tv_button).setBackgroundColor(resource);
    }

    public void setLeftImageResource(int resource){
        ((ImageView) this.findViewById(R.id.iv_left)).setImageResource(resource);
    }

    public void setPrimaryText(String text){
        ((TextView) this.findViewById(R.id.tv_primary)).setText(text);
    }
    public void setPrimaryText(int text){
        ((TextView) this.findViewById(R.id.tv_primary)).setText(text);
    }
    public void setSecondText(String text){
        ((TextView) this.findViewById(R.id.tv_second)).setText(text);
    }
    public void setSecondText(int text){
        ((TextView) this.findViewById(R.id.tv_second)).setText(text);
    }
}
