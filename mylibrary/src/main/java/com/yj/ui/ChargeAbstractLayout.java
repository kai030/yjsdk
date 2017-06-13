package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.entity.ChargeData;
import com.yj.entity.Constants;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;

/**
 * 
 * @author lufengkai 
 *
 */
@SuppressWarnings("ResourceType")
public abstract class ChargeAbstractLayout extends LinearLayout {
	
	public static final int ID_EXIT = 40001;
	public static final int ID_CANCEL = 40002;
	protected static final int MAXAMOUNT = 10000;

	protected LinearLayout mContent;
	protected Activity mActivity;
	protected TextView mTileType;
	protected ImageView mExit;
	protected LinearLayout mSubject;
	public Bitmap bitmap1, bitmap2, bitmap3;
	protected int weight1;
	
	//title
	protected TextView logo;
	

	public ChargeAbstractLayout(Context context) {
		super(context);
		
		
	}
	
	protected void initUI(Activity activity) {
		mActivity = activity;
		
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundColor(0xffffffff);
		setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(-1, -1);
		setLayoutParams(lp);
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
//		int densityDpi = metrics.densityDpi;

		
		mContent = new LinearLayout(activity);
		lp = new LayoutParams(-1, -1);
		mContent.setOrientation(LinearLayout.VERTICAL);
		addView(mContent, lp);

		// 标题
		RelativeLayout rl = new RelativeLayout(activity);
		rl.setId(2);
		rl.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity,
				"title.9.png"));
//		rl.setBackgroundDrawableColor(0xff313b45);
//		rl.setPadding(0, DimensionUtil.dip2px(activity, 10), 0,DimensionUtil.dip2px(activity, 10));
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1, -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		mContent.addView(rl, rlp);
		
		LinearLayout ll = new LinearLayout(activity);
		ll.setGravity(Gravity.CENTER);
		ll.setPadding(0, DimensionUtil.dip2px(activity, 5), 0,DimensionUtil.dip2px(activity, 5));
		
		LinearLayout logoLayout = new LinearLayout(activity);
		logoLayout.setGravity(Gravity.CENTER);

		logo = new TextView(activity);
		logo.setText("充 值");
		logo.setTextColor(0xff000000);
		logo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		lp = new LayoutParams(-2, -2);
		logoLayout.addView(logo, lp);
		ll.addView(logoLayout);

		rlp = new RelativeLayout.LayoutParams(-2, -2);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		// rlp.bottomMargin=DimensionUtil.dip2px(context, 20);
		rl.addView(ll, rlp);

		// 返回键
		mExit = new ImageView(activity);
		rlp = new RelativeLayout.LayoutParams(-2, -2);
		mExit.setImageDrawable(BitmapCache.getDrawable(mActivity,
				Constants.ASSETS_RES_PATH + "fanhui.png"));
		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
		rlp.leftMargin = DimensionUtil.dip2px(activity, 15);
		mExit.setId(Constants.ID_REGISTER_BACK_BTN);
//		mExit.setOnClickListener((OnClickListener) activity);
		rl.addView(mExit, rlp);
	}
	
	public void setButtonClickListener(OnClickListener listener) {
		mExit.setOnClickListener(listener);
	}
	
	public abstract ChargeData getChargeEntity();

}
