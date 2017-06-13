package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.yj.entity.DeviceProperties;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月28日
 * @copyright 游鹏科技
 */
public class ControllerView extends FrameLayout {

	/**
	 * 状态栏高度
	 **/
	public static int tool_bar_high = 0;

	/** 跟随监听 **/
	private LayoutChangeListener layoutListener;

	private Context mContext;
	private WindowManager.LayoutParams params = new WindowManager.LayoutParams();
	private int startX = 0;
	private int startY = 0;
	WindowManager wm;
	public float x;
	public float y;

	/** 屏幕宽 **/
	private int window_width;

	/**按下去的时间*/
	public static long downTime;
	/** View宽 **/
//	private int width;

	private boolean isShow = false;

	public ControllerView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		wm = (WindowManager) paramContext.getSystemService("window");
		init();
	}

	public ControllerView(Context context) {
		super(context);
		this.mContext = context;
		wm = (WindowManager) context.getSystemService("window");
		init();
	}

	private void init() {
//		setLeft(true);
		getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
//				width = ControllerView.this.getWidth();
				DisplayMetrics displayMetrics = new DisplayMetrics();
				wm.getDefaultDisplay().getMetrics(displayMetrics);
				window_width = displayMetrics.widthPixels;
				return true;
			}
		});
	}

	public void addToWindow(int paramIntX, int paramIntY) {
		params.width = -2;
		params.height = -2;
		params.type = 2002;
		params.flags = 40;
		// params.gravity = 51;
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.format = 1;
		params.y = paramIntY;
		
		DeviceProperties device = DeviceProperties.getInstance(mContext);
		
		Utils.youaiLog("paramIntX -->" + paramIntX + "   device.displayScreenWidth / 2-->"+ device.displayScreenWidth / 2);
		if (paramIntX > device.displayScreenWidth / 2) {
			//右边
//			MyLog.d("右边！");
			params.x = device.displayScreenWidth;
		} else {
		//drawable_left
			//左边
//			MyLog.d("左边！");
			params.x = 0;
		}
//		MyLog.d("params.x -->" + params.x + "   params.y-->"+ params.y);
		wm.addView(this, this.params);
	}

	public void removeControllerView() {
		wm.removeView(this);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (!isShow) {
			this.x = event.getRawX();
			this.y = (event.getRawY() - tool_bar_high);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = (int) event.getX();
				startY = (int) event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				updatePosition(false);
				break;
			case MotionEvent.ACTION_UP:
				//记录最后一次退出的位置
				updatePosition(true);
				updateView(params.x ,params.y);
				SharedPreferences saveXY = mContext.getSharedPreferences("savaXY", Context.MODE_PRIVATE);
				Editor editor = saveXY.edit();
				if(this.x != 0 && this.y != 0){
					editor.putInt("y", params.y);
					editor.putInt("x", params.x);
//					MyLog.d("退出时保存的坐标位置为： --x:" + params.x  + "--y:" + params.y);
					editor.commit();
				}
				this.startY = 0;
				this.startX = 0;
				break;
			}
		}
		downTime = event.getEventTime() - event.getDownTime() ;
		return super.onInterceptTouchEvent(event);
	}

	public void setLayoutChangeListener(
			LayoutChangeListener paramLayoutChangeListener) {
		this.layoutListener = paramLayoutChangeListener;
	}

	public void updatePosition(boolean up) {
	
		params.x = (int) (this.x - this.startX);
		// }
		params.y = (int) (this.y - this.startY);
		this.wm.updateViewLayout(this, this.params);
		if (this.layoutListener != null)
			this.layoutListener.layout(this, this.params.x, this.params.y);
	}

	// public int getParamsY() {
	// return params.y;
	// }
	public WindowManager.LayoutParams getWMParams() {
		return params;
	}
	public void setWMParams(WindowManager.LayoutParams layoutParams) {
		params = layoutParams;
		wm.updateViewLayout(this, params);
	}

	public static abstract interface LayoutChangeListener {
		public abstract void layout(ControllerView paramVG_ControllerView, int paramInt1, int paramInt2);
	}

	public void initStatusBaHeight(Context context) {
		if (context instanceof Activity) {
			int flag = ((Activity) context).getWindow().getAttributes().flags;

			if ((flag & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
				tool_bar_high = 0;
			} else {
				try {
					Class<?> class1 = Class.forName("com.android.internal.R$dimen");
					Object obj = class1.newInstance();
					int j = Integer.parseInt(class1.getField("status_bar_height").get(obj).toString());
					tool_bar_high = context.getResources().getDimensionPixelSize(j);
				} catch (Exception e) {
				}
			}
		}

	}

	public void updateView(int paramX ,int paramY) {
		
//		WindowManager.LayoutParams layoutParams = this.getWMParams();
		if (paramX > this.getWindow_width() / 2) {
			//右边
			params.x = this.getWindow_width();
		} else {
		//drawable_left
			//左边
			params.x = 0;
		}
		params.y = (int)paramY;
		wm.updateViewLayout(this, params);
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public boolean isShow() {
		return isShow;
	}

	public int getWindow_width() {
		return window_width;
	}
}