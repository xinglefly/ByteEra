package com.zhicai.byteera.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.zhicai.byteera.R;

public class EditTextPwd extends EditText {
	private Context context;
	private Drawable showpwd;
	private Drawable pwd;
	private int status=1;
	public EditTextPwd(Context context){
		super(context);
		this.context=context;
		init();
	}

	public EditTextPwd(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
	}
	private void init(){
		showpwd = context.getResources().getDrawable(R.drawable.showpwd);
		pwd = context.getResources().getDrawable(R.drawable.pwd);
		setDrawable();
	}
	private void setDrawable() {
		if (status == 0) {
			this.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//			setCompoundDrawablesWithIntrinsicBounds(null, null, zoomDrawable(showpwd, 200, 200), null);
			setCompoundDrawablesWithIntrinsicBounds(null, null, showpwd, null);
			status = 1;
		} else {
			this.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
//			setCompoundDrawablesWithIntrinsicBounds(null, null, zoomDrawable(pwd, 200, 200), null);
			setCompoundDrawablesWithIntrinsicBounds(null, null, pwd, null);
			status = 0;
		}
		Editable etable = getText();
        Selection.setSelection(etable, etable.length());
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(pwd!=null&&event.getAction()==MotionEvent.ACTION_UP){
			int eventx=(int) event.getRawX();
			int eventy=(int) event.getRawY();
			Rect rect=new Rect();
			getGlobalVisibleRect(rect);
			rect.left=rect.right-90;
			if(rect.contains(eventx,eventy)){
				setDrawable();
			}
		}
		return super.onTouchEvent(event);
		
	}
	static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成 bitmap   
    {  
              int width = drawable.getIntrinsicWidth();   // 取 drawable 的长宽   
              int height = drawable.getIntrinsicHeight();  
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;         // 取 drawable 的颜色格式   
              Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 建立对应 bitmap   
              Canvas canvas = new Canvas(bitmap);         // 建立对应 bitmap 的画布   
              drawable.setBounds(0, 0, width, height);  
              drawable.draw(canvas);      // 把 drawable 内容画到画布中   
              return bitmap;  
    } 
	static Drawable zoomDrawable(Drawable drawable, int w, int h)  
	    {  
	              int width = drawable.getIntrinsicWidth();  
	              int height= drawable.getIntrinsicHeight();  
	              Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap   
	              Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象   
	              float scaleWidth = ((float)w / width);   // 计算缩放比例   
	              float scaleHeight = ((float)h / height);  
	              matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例   
	              Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);// 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图   
	              return new BitmapDrawable(newbmp);       // 把 bitmap 转换成 drawable 并返回   
	    } 
}
