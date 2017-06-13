package com.yj.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yj.entity.Constants;
import com.yj.sdk.FloatActivity;
import com.yj.sdk.YJLoginActivity;
import com.yj.sdk.YouaiAppService;
import com.yj.sdk.ZhimengUserCenterActivity;
import com.yj.util.BitmapCache;

import java.lang.reflect.Field;


/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright
 */
public class FloatWindowView extends LinearLayout {
	
	/**
	 * 右边图标的宽度
	 */
	public static int directionRightWidth;
	/**
	 * 记录小悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录小悬浮窗的高度
	 */
	public static int viewHeight;

	/**
	 * 记录系统状态栏的高度
	 */
	 private static int statusBarHeight;

	/**
	 * 用于更新小悬浮窗的位置
	 */
	private WindowManager windowManager;

	/**
	 * 小悬浮窗的参数
	 */
	private WindowManager.LayoutParams mParams;

	/**
	 * 记录当前手指位置在屏幕上的横坐标值
	 */
	private float xInScreen;

	/**
	 * 记录当前手指位置在屏幕上的纵坐标值
	 */
	private float yInScreen;

	/**
	 * 记录手指按下时在屏幕上的横坐标的值
	 */
	private float xDownInScreen;

	/**
	 * 记录手指按下时在屏幕上的纵坐标的值
	 */
	private float yDownInScreen;

	/**
	 * 记录手指按下时在小悬浮窗的View上的横坐标的值
	 */
	private float xInView;

	/**
	 * 记录手指按下时在小悬浮窗的View上的纵坐标的值
	 */
	private float yInView;

	private ImageView floatImage;
	
	private Context context;
	public FloatWindowView(Context context) {
		super(context);
		try {
			
			
			this.context = context;
			
			windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			
			floatImage = new ImageView(context);
//			Drawable drawable = Utils.getDrawable(context, "youai_res/"+"float_icon.png");
			Drawable drawable = BitmapCache.getDrawable(context, "youai_res/" + "float_icon2.png");
			floatImage.setBackgroundDrawable(drawable);
			this.addView(floatImage,-2,-2);
			
			Drawable direction = BitmapCache.getDrawable(context, "youai_res/" + "menu_right.png");
			directionRightWidth = direction.getIntrinsicWidth();
			
			viewWidth = drawable.getIntrinsicWidth();
			viewHeight = drawable.getIntrinsicHeight();
			handler.postDelayed(runnable, 3000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		handler.postDelayed(runnable, 3000);
	/*	LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
		View view = findViewById(R.id.small_window_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height*/;
	}
	
	private int direction = 1;
	private boolean changeFlag = true;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			if(changeFlag){
				if(direction == 2){
					floatImage.setBackgroundDrawable(
                                            BitmapCache.getDrawable(FloatWindowView.this.context, "youai_res/" + "menu_right.png"));
					//updateViewPositionUp();
				}else{
					floatImage.setBackgroundDrawable(
                                            BitmapCache.getDrawable(FloatWindowView.this.context, "youai_res/" + "menu_left.png"));
				}
				
			}
			handler.removeCallbacks(runnable);
			handler.postDelayed(runnable, 3000);
			
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			changeFlag = false;
			floatImage.setBackgroundDrawable(BitmapCache.getDrawable(this.context, "youai_res/" + "float_icon.png"));
			
			// 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
			xInView = event.getX();
			yInView = event.getY();
			xDownInScreen = event.getRawX();
			yDownInScreen = event.getRawY() - getStatusBarHeight();
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			changeFlag = false;
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			// 手指移动的时候更新小悬浮窗的位置
			updateViewPositionMove();
			break;
		case MotionEvent.ACTION_UP:
			changeFlag = true;
			updateViewPositionUp();
			floatImage.setBackgroundDrawable(BitmapCache.getDrawable(this.context, "youai_res/" + "float_icon2.png"));
			// 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
//			if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {//点击
				if(xDownInScreen -10< xInScreen && xInScreen< xDownInScreen +10 && yDownInScreen - 10 < yInScreen && yInScreen < yDownInScreen+10){
					
//				}
			 	SharedPreferences sharedPreferences = context.getSharedPreferences(
                                    Constants.SAVESETTING, Context.MODE_PRIVATE);
	        	int aotocancel = sharedPreferences.getInt(Constants.AOTOCANCEL, 0);//点击小窗口次数
	        	Editor editorAction = sharedPreferences.edit();
	        	editorAction.putInt(Constants.AOTOCANCEL, aotocancel+1);
	        	editorAction.commit();
				openBigWindow();
			}
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
	 * 
	 * @param params
	 *            小悬浮窗的参数
	 */
	public void setParams(WindowManager.LayoutParams params) {
		mParams = params;
		int wid = windowManager.getDefaultDisplay().getWidth();
		if(params != null && wid/2 < params.x){
			direction = 2;
		}
	}

	/**
	 * 更新小悬浮窗在屏幕中的位置。  移动过程
	 */
	private void updateViewPositionMove() {
		mParams.x = (int) (xInScreen - xInView);
		mParams.y = (int) (yInScreen - yInView);
		windowManager.updateViewLayout(this, mParams);
	}
	
	/***
	 * 手指离开屏幕
	 */
	private void updateViewPositionUp(){
		int wid = windowManager.getDefaultDisplay().getWidth();
		if(wid/2 > xInScreen){
			direction = 1;
			mParams.x = 0;
		}else{
			direction = 2;
			mParams.x = wid;
		}
		windowManager.updateViewLayout(this, mParams);
	}

	/**
	 * 点击事件
	 */
	private void openBigWindow() {
		if(YouaiAppService.isLogin){
			Intent intent = new Intent();
		intent.setClass(context, FloatActivity.class);
		context.startActivity(intent);
		}else{
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(context, YJLoginActivity.class);
			context.startActivity(intent);
		}
		
//		MyWindowManager.createBigWindow(getContext());
//		MyWindowManager.removeSmallWindow(getContext());
	}

	/**
	 * 用于获取状态栏的高度。
	 * 
	 * @return 返回状态栏高度的像素值。
	 */
	private int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}


}
