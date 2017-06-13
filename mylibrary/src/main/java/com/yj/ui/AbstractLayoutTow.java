package com.yj.ui;

import android.app.Activity;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yj.util.BitmapCache;
import com.yj.util.Utils;


/**
 * @author lufengkai 
 * @date 2015年5月28日
 * @copyright 游鹏科技
 */
public abstract class AbstractLayoutTow extends RelativeLayout {
	
	protected LinearLayout content;
	protected int widthPixels;
	protected int heightPixels;
	protected Activity mActivity;

	public AbstractLayoutTow(Activity mActivity) {
		super(mActivity);
		this.mActivity = mActivity;
		init();
	}
	
	private void init(){
		
//		this.setBackgroundDrawableColor(0x0aefefef);
//		this.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(this.mActivity, "login_bg.9.png"));
		LayoutParams rp = new LayoutParams(Utils.compatibleToWidth(mActivity),
                                                   -2);
		rp.addRule(RelativeLayout.CENTER_IN_PARENT);
		FrameLayout fragment = new FrameLayout(mActivity);
//		fragment.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(this.mActivity, "login_bg.9.png"));
		
		content = new LinearLayout(mActivity);
		addView(fragment, rp);
		content.setOrientation(LinearLayout.VERTICAL);
		content.setGravity(Gravity.CENTER_HORIZONTAL);
		content.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(this.mActivity, "login_bg.9.png"));
//		mSubject.setBackgroundDrawableDrawable(BitmapCache.getNinePatchDrawable(mActivity, "no_title_bg.9.png"));
		fragment.addView(content, -1,-1);
		
	}
	
	public abstract void setButtonClickListener(OnClickListener listener);

}
