package com.yj.ui;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yj.entity.Constants;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class LayoutUtil {
	
	public static RelativeLayout.LayoutParams getRelativeParams(Context mContext){
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-2, -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		rlp.rightMargin = DimensionUtil.dip2px(mContext, 10);
		return rlp;
	}
	
	public static ImageView getCloseImage(Context mContext, android.view.View.OnClickListener clickListener){
		ImageView close = new ImageView(mContext);
		close.setImageDrawable(BitmapCache.getDrawable(mContext, Constants.ASSETS_RES_PATH + "close.png"));
		close.setId(Constants.CLOSE_ID);
		close.setOnClickListener(clickListener);
		return close;
	}

}
