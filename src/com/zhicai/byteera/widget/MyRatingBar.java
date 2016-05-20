package com.zhicai.byteera.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.UIUtils;

/**
 * Created by lieeber on 15/8/11.
 */
public class MyRatingBar extends FrameLayout {
    private ImageView rating1;
    private ImageView rating2;
    private ImageView rating3;
    private ImageView rating4;
    private ImageView rating5;

    private int num;

    public MyRatingBar(Context context) {
        this(context, null);
    }

    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }
    public interface OnClickRatingListener {
        void clickRating(int num);

    }

    private OnClickRatingListener onClickRatingListener;

    public void setOnClickRatingListener(OnClickRatingListener onClickRatingListener) {
        this.onClickRatingListener = onClickRatingListener;
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.rating_bar, this, true);
        rating1 = (ImageView) findViewById(R.id.rating_1);
        rating2 = (ImageView) findViewById(R.id.rating_2);
        rating3 = (ImageView) findViewById(R.id.rating_3);
        rating4 = (ImageView) findViewById(R.id.rating_4);
        rating5 = (ImageView) findViewById(R.id.rating_5);

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyRatingBar);
        boolean isInDicator = attributes.getBoolean(R.styleable.MyRatingBar_isindicator, false);
        float dimension = attributes.getDimension(R.styleable.MyRatingBar_size, 0);
        float margin = attributes.getDimension(R.styleable.MyRatingBar_margin, UIUtils.dip2px(getContext(), 10));
        attributes.recycle();
        if (!isInDicator) {   //如果是indicator的话就是不可以点击的
            rating1.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    //改变星星的状态
                    setRating(1);
                    if (onClickRatingListener != null) {
                        onClickRatingListener.clickRating(1);
                    }
                }
            });
            rating2.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    setRating(2);
                    if (onClickRatingListener != null) {
                        onClickRatingListener.clickRating(2);
                    }
                }
            });
            rating3.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    setRating(3);
                    if (onClickRatingListener != null) {
                        onClickRatingListener.clickRating(3);
                    }
                }
            });
            rating4.setOnClickListener(new OnClickListener() {

                @Override public void onClick(View v) {
                    setRating(4);
                    if (onClickRatingListener != null) {
                        onClickRatingListener.clickRating(4);
                    }
                }
            });
            rating5.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    setRating(5);
                    if (onClickRatingListener != null) {
                        onClickRatingListener.clickRating(5);
                    }
                }
            });
        }
        if (dimension != 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) dimension, (int) dimension);
            layoutParams.setMargins(0, 0, (int) margin, 0);
            rating1.setLayoutParams(layoutParams);
            rating2.setLayoutParams(layoutParams);
            rating3.setLayoutParams(layoutParams);
            rating4.setLayoutParams(layoutParams);
            rating5.setLayoutParams(layoutParams);
        }
    }

    public void setRating(int num) {
        switch (num) {
            case 1:
                rating1.setImageResource(R.drawable.pingfen);
                rating2.setImageResource(R.drawable.pingfenxingxingbankesvg);
                rating3.setImageResource(R.drawable.pingfenxingxingbankesvg);
                rating4.setImageResource(R.drawable.pingfenxingxingbankesvg);
                rating5.setImageResource(R.drawable.pingfenxingxingbankesvg);
                this.num = 1;
                break;
            case 2:
                rating1.setImageResource(R.drawable.pingfen);
                rating2.setImageResource(R.drawable.pingfen);
                rating3.setImageResource(R.drawable.pingfenxingxingbankesvg);
                rating4.setImageResource(R.drawable.pingfenxingxingbankesvg);
                rating5.setImageResource(R.drawable.pingfenxingxingbankesvg);
                this.num = 2;
                break;
            case 3:
                rating1.setImageResource(R.drawable.pingfen);
                rating2.setImageResource(R.drawable.pingfen);
                rating3.setImageResource(R.drawable.pingfen);
                rating4.setImageResource(R.drawable.pingfenxingxingbankesvg);
                rating5.setImageResource(R.drawable.pingfenxingxingbankesvg);
                this.num = 3;
                break;
            case 4:
                rating1.setImageResource(R.drawable.pingfen);
                rating2.setImageResource(R.drawable.pingfen);
                rating3.setImageResource(R.drawable.pingfen);
                rating4.setImageResource(R.drawable.pingfen);
                rating5.setImageResource(R.drawable.pingfenxingxingbankesvg);
                this.num = 4;
                break;
            case 5:
                rating1.setImageResource(R.drawable.pingfen);
                rating2.setImageResource(R.drawable.pingfen);
                rating3.setImageResource(R.drawable.pingfen);
                rating4.setImageResource(R.drawable.pingfen);
                rating5.setImageResource(R.drawable.pingfen);
                this.num = 5;
                break;
        }
    }

    public int getNum() {
        return this.num;
    }
}
