package com.yj.ui;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月28日
 * @copyright 游鹏科技
 */
public class AbstractLayout extends RelativeLayout {

	private Context context;
	protected LinearLayout layout;
	
	public AbstractLayout(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	private void init(){
//		this.setBackgroundColor(0x0aefefef);
		LayoutParams params = new LayoutParams(Utils.compatibleToWidth(context), -2);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
//		layout = new LinearLayout(context);
		addView(layout,params);
		
//		layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
//				context, "login_bg.9.png"));
//		layout.setOrientation(LinearLayout.VERTICAL);
	}

}
