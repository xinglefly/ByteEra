package com.zhicai.byteera.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.zhicai.byteera.R;
import com.zhicai.byteera.commonutil.UIUtils;

/** Created by bing on 2015/6/19. */
public class MyCircleStrokeView extends View {

    private Paint mPaint;
    private int startColorStroke;
    private int endColorStroke;
    private float strokeWidth;
    private String circleText;
    private TextPaint textPaint;
    private int circleType;
    private Paint fillPaint;
    private int startColorFill;
    private int endColorFill;
    private int textColor;

    public MyCircleStrokeView(Context context) {
        this(context, null);
    }

    public MyCircleStrokeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleStrokeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyCircleStrokeView);
        circleType = typedArray.getInt(R.styleable.MyCircleStrokeView_circle_type, 0);
        startColorStroke = typedArray.getColor(R.styleable.MyCircleStrokeView_start_color_stroke, Color.RED);
        endColorStroke = typedArray.getColor(R.styleable.MyCircleStrokeView_end_color_stroke, Color.BLUE);
        startColorFill = typedArray.getColor(R.styleable.MyCircleStrokeView_start_color_fill, Color.BLUE);
        endColorFill = typedArray.getColor(R.styleable.MyCircleStrokeView_end_color_fill, Color.BLUE);
        strokeWidth = typedArray.getDimension(R.styleable.MyCircleStrokeView_stroke_width, 5);
        circleText = typedArray.getString(R.styleable.MyCircleStrokeView_circle_text);
        textColor = typedArray.getColor(R.styleable.MyCircleStrokeView_circle_text_color, Color.BLACK);
        float textSize = typedArray.getDimension(R.styleable.MyCircleStrokeView_circle_text_width, 20);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);

        /**绘制文字**********/
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setTypeface(Typeface.SERIF);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /**外环的颜色渐变***********/
        mPaint.setShader(new LinearGradient(this.getLeft() + this.getWidth() / 2.0f, this.getTop(),
                this.getLeft() + this.getWidth() / 2.0f, this.getTop() + this.getWidth(),
                new int[]{startColorStroke, endColorStroke}, new float[]{0, 1},
                Shader.TileMode.CLAMP));
        /****内圆的颜色渐变*******/
        if (circleType == 1) {
            fillPaint.setShader(new LinearGradient(this.getLeft() + this.getWidth() / 2.0f, this.getTop(),
                    this.getLeft() + this.getWidth() / 2.0f, this.getTop() + this.getWidth(),
                    new int[]{startColorFill, endColorFill}, new float[]{0, 1},
                    Shader.TileMode.CLAMP));
        }
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(this.getWidth() / 2.0f, this.getHeight() / 2.0f, this.getWidth() / 2.0f - strokeWidth, mPaint);
        float baseX = (this.getWidth() / 2.0f - textPaint.measureText(circleText) / 2);
        float baseY = ((this.getHeight() / 2.0f) - ((textPaint.descent() + textPaint.ascent()) / 2));

        if (circleType == 1) {
            canvas.drawCircle(this.getWidth() / 2.0f, this.getHeight() / 2.0f, this.getWidth() / 2.0f - strokeWidth - UIUtils.dip2px(getContext(), 2), fillPaint);
        }
        canvas.drawText(circleText, baseX, baseY, textPaint);   //需要垂直居中和水平居中
    }

    public void setText(String text) {
        circleText = text;
        postInvalidate();
    }

    public void setCircleType(int type) {
        circleType = type;
        if (circleType == 1) {
            fillPaint.setShader(new LinearGradient(this.getLeft() + this.getWidth() / 2.0f, this.getTop(),
                    this.getLeft() + this.getWidth() / 2.0f, this.getTop() + this.getWidth(),
                    new int[]{startColorFill, endColorFill}, new float[]{0, 1},
                    Shader.TileMode.CLAMP));
        }
        postInvalidate();
    }

    public int getCircleType() {
        return circleType;
    }

    public void setTextColor(int color) {
        this.textColor = color;
        textPaint.setColor(textColor);
        postInvalidate();
    }
}
