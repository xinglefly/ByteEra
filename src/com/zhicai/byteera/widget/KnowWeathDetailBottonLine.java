package com.zhicai.byteera.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhicai.byteera.R;

import butterknife.ButterKnife;

/**
 * Created by bing on 2015/4/15.
 */
public class KnowWeathDetailBottonLine extends RelativeLayout implements View.OnClickListener {
    private ButtonLineClickListener mListener;
    private ImageView praises;
    private TextView tv_comment_num;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                mListener.onBackClick();
                break;
            case R.id.rl_prise_container:
                mListener.onCommentClick();
                break;
            case R.id.praises:
                mListener.onPraiseClick();
                break;
            case R.id.transpond:
                mListener.onTranspondClick();
                break;
            case R.id.share:
                mListener.onShareClick();
                break;
        }
    }

    public interface ButtonLineClickListener {
        void onBackClick();

        void onCommentClick();

        void onPraiseClick();

        void onTranspondClick();

        void onShareClick();
    }


    public void setButtonLineClickListener(ButtonLineClickListener mListener) {
        this.mListener = mListener;
    }

    private Context mContext;

    public KnowWeathDetailBottonLine(Context context) {
        this(context, null);
    }

    public KnowWeathDetailBottonLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KnowWeathDetailBottonLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.know_wealth_botton_line, this, true);
        ImageView back = ButterKnife.findById(view, R.id.back);
        RelativeLayout rlPraiseContainer = ButterKnife.findById(view, R.id.rl_prise_container);
        praises = ButterKnife.findById(view, R.id.praises);
        ImageView transpond = ButterKnife.findById(view, R.id.transpond);
        ImageView share = ButterKnife.findById(view, R.id.share);
        tv_comment_num = ButterKnife.findById(view, R.id.tv_comment_num);

        back.setOnClickListener(this);
        rlPraiseContainer.setOnClickListener(this);
        praises.setOnClickListener(this);
        transpond.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    public ImageView getCollectionButtom() {
        return praises;
    }

    public void setConmmentNum(String num) {
        tv_comment_num.setText(num);
    }
}
