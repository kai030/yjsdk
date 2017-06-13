package com.yj.sdk;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.yj.ui.FloatWindowView;

/**
 * @author lufengkai 
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
public class MyWindowManager {

	private static Context mContext;

	/**
	 * 小悬浮窗View的实例
	 */
	private static FloatWindowView smallWindow;

	/**
	 * 小悬浮窗View的参数
	 */
	private static LayoutParams smallWindowParams;

	/**
	 * 用于控制在屏幕上添加或移除悬浮窗
	 */
	private static WindowManager mWindowManager;


	/**-
	 * 
	 * 创建浮窗
	 * @param context
	 */
	public static void createSmallWindow(Context context, int locationX, int locationY) {
		mContext = context;
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		
		/*假如位置超出屏幕范围则默认为0*/
		if(locationX > screenWidth){
			locationX = 0;
		}
		if(locationY > screenHeight) locationY = 0;
		
		/*创建窗口*/
		if (smallWindow == null) {
			smallWindow = new FloatWindowView(context);
			if (smallWindowParams == null) {
				smallWindowParams = new LayoutParams();
				smallWindowParams.width = -2;
				smallWindowParams.height = -2;
//				smallWindowParams.type = 2002;WindowManager
				smallWindowParams.type = LayoutParams.TYPE_APPLICATION;
				smallWindowParams.flags = 40;
				// params.gravity = 51;
				smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				smallWindowParams.format = 1;
//				params.y = paramIntY;
				
				
			/*	smallWindowParams = new LayoutParams();
				smallWindowParams.type = LayoutParams.TYPE_PHONE;
				smallWindowParams.format = PixelFormat.RGBA_8888;
				smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
						| LayoutParams.FLAG_NOT_FOCUSABLE;
				smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				smallWindowParams.width = FloatWindowView.viewWidth;
				smallWindowParams.height = FloatWindowView.viewHeight;*/
				smallWindowParams.x = locationX;
				smallWindowParams.y = locationY;
			}
			smallWindow.setParams(smallWindowParams);

			windowManager.addView(smallWindow, smallWindowParams);
		}
	}

	public static void destroy(){
		mWindowManager = null;
		smallWindow = null;
		smallWindowParams = null;
		mWindowManager = null;
		mContext = null;
	}

	/**
	 * 将小悬浮窗从屏幕上移除。
	 * 
	 *            必须为应用程序的Context.
	 */
	public static void removeSmallWindow() {
		if (smallWindow != null) {
			WindowManager windowManager = getWindowManager(mContext);
			windowManager.removeView(smallWindow);
			smallWindow = null;
//			mWindowManager = null;
		}
	}




	/**
	 * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
	 * 
	 * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
	 */
	public static boolean isWindowShowing() {
		return smallWindow != null ;
	}

	/**
	 * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
	 */
	private static WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}


}
