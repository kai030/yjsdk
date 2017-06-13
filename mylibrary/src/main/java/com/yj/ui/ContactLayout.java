package com.yj.ui;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.sdk.YouaiAppService;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.GetDataImpl;
import com.yj.util.UserService;

/**
 * @author lufengkai 
 * @date 2015年5月28日
 * @copyright 游鹏科技
 */
public class ContactLayout extends AbstractLayoutTow implements
		View.OnClickListener {

	// private static final int ID_BACK = 0x006;
	public Activity mContext;
	private OnClickListener mConfirmListener;
	private String phoneNub, QQ;
	private final int phoneNubId = 1;
	private final int QQId = 2;

	public ContactLayout(Activity mContext) {
		super(mContext);
		this.mContext = mContext;
		initUI(mContext);
	}

	public void initUI(Activity mContext) {

		// -----------------title-----------------------------------

		RelativeLayout title = new RelativeLayout(mContext);
		title.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity,
                                                                             "title.9.png"));
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1,
                                                                                  -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		title.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
				DimensionUtil.dip2px(mContext, 7));
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-1, -2);
		llp.leftMargin = 2;
		llp.rightMargin = 2;
		llp.topMargin = 2;
		content.addView(title, llp);

		ImageView fanhui = new ImageView(mContext);
		fanhui.setImageDrawable(BitmapCache.getDrawable(mContext,
				Constants.ASSETS_RES_PATH + "fanhui.png"));
		fanhui.setId(Constants.ID_BACK);
		fanhui.setOnClickListener(this);
		rlp = new RelativeLayout.LayoutParams(-2, -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT
                            | RelativeLayout.CENTER_VERTICAL);
		rlp.leftMargin = DimensionUtil.dip2px(mContext, 5);
		title.addView(fanhui, rlp);

		ImageView close = LayoutUtil.getCloseImage(mContext, this);
		rlp = LayoutUtil.getRelativeParams(mContext);
		title.addView(close,rlp);

		TextView textView = new TextView(mContext);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		rlp = new RelativeLayout.LayoutParams(-1, -2);
		textView.setText("联系客服");
		textView.setTextColor(0xff000000);
		textView.setTextSize(22);
		title.addView(textView, rlp);

		// -----------------------------------------------------

		// ScrollView scroll = new ScrollView(mContext);
		// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -1);
		// lp.gravity = Gravity.CENTER;
		// content.addView(scroll, lp);

		LinearLayout mSubject = new LinearLayout(mContext);
		/*mSubject.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity,
				"no_title_bg.9.png"));*/
		// mSubject.setPadding(0, 0, 0,DimensionUtil.dip2px(mContext,38));
		mSubject.setOrientation(LinearLayout.VERTICAL);
		content.addView(mSubject, -1, -1);

		LinearLayout ollLayout = new LinearLayout(mContext);
		ollLayout.setOrientation(LinearLayout.VERTICAL);
		mSubject.addView(ollLayout);
		// ----------------客服热线---------------------------
		LinearLayout phoneLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -1);
		lp.leftMargin = DimensionUtil.dip2px(mContext, 20);
		lp.rightMargin = DimensionUtil.dip2px(mContext, 20);
		lp.topMargin = DimensionUtil.dip2px(mContext, 20);
		lp.bottomMargin = DimensionUtil.dip2px(mContext, 10);
		phoneLayout.setOrientation(LinearLayout.HORIZONTAL);
		ollLayout.addView(phoneLayout, lp);

		ImageView imagePhone = new ImageView(mContext);
		imagePhone.setImageDrawable(BitmapCache.getDrawable(mContext,
				Constants.ASSETS_RES_PATH + "phone.png"));
		phoneLayout.addView(imagePhone);
		
		TextView phoneText = new TextView(mActivity);
		phoneText.setPadding(DimensionUtil.dip2px(mContext, 5), 0, 0, 0);
		phoneText.setTextColor(0xff58687b);
		phoneText.setTextSize(18);
		phoneText.setText("客服热线：");
		phoneLayout.addView(phoneText);
		
		if (YouaiAppService.basicDate == null) {
			YouaiAppService.basicDate = GetDataImpl.getInstance(mContext)
					.online();
		}
		
		if (YouaiAppService.basicDate == null)
			return;
		TextView phone = new TextView(mActivity);
		String phoneNum = YouaiAppService.basicDate.serviceTel;
		/*if(phoneNum == null || "0".equals(phoneNum)){
			phoneNum = "";
//			phoneText.setVisibility(View.INVISIBLE);
//			imagePhone.setVisibility(View.INVISIBLE);
			phoneLayout.setVisibility(View.GONE);
		}*/
		phone.setText(Html.fromHtml("<font color=#000000><a href=''>" + phoneNum + "</a></font>"));
		/*"客服热线：<font color = '#ffff0066'> "
				+ YouaiAppService.basicDate.serviceTel + "</font>"));*/
		phone.setId(phoneNubId);
		phone.setOnClickListener(this);
		phoneNub = YouaiAppService.basicDate.serviceTel;
//		phone.setPadding(DimensionUtil.dip2px(mContext, 5), 0, 0, 0);
		phone.setTextColor(0xff58687b);
		phone.setTextSize(18);
		// phone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		phoneLayout.addView(phone);

		// ---------------------分割虚线--------------------------------

		TextView gapLine = new TextView(mContext);
		lp = new LinearLayout.LayoutParams(-1, -1);
		lp.leftMargin = DimensionUtil.dip2px(mContext, 20);
		lp.rightMargin = DimensionUtil.dip2px(mContext, 20);
		GradientDrawable gradientDrawable = new GradientDrawable();
		gradientDrawable.setShape(GradientDrawable.LINE);
		gradientDrawable.setStroke(DimensionUtil.dip2px(mContext, 1),
				0xff8aafd3, DimensionUtil.dip2px(mContext, 5),
				DimensionUtil.dip2px(mContext, 2));
		gapLine.setBackgroundDrawable(gradientDrawable);
		// addView(gapLine,lp);
		ollLayout.addView(gapLine, lp);
		
//		TextView phone = new TextView(mActivity);
//		String phoneNum = YouaiAppService.basicDate.serviceTel;
		if(phoneNum == null || "0".equals(phoneNum)){
			phoneNum = "";
//			phoneText.setVisibility(View.INVISIBLE);
//			imagePhone.setVisibility(View.INVISIBLE);
			phoneLayout.setVisibility(View.GONE);
			gapLine.setVisibility(View.GONE);
		}

		// ---------------------客服QQ---------------------------------
		LinearLayout qqLayout = new LinearLayout(mContext);
		qqLayout.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, -1);
		lp.leftMargin = DimensionUtil.dip2px(mContext, 20);
		lp.rightMargin = DimensionUtil.dip2px(mContext, 20);
		lp.topMargin = DimensionUtil.dip2px(mContext, 10);
		ollLayout.addView(qqLayout, lp);

		ImageView qqPhone = new ImageView(mContext);
		qqPhone.setImageDrawable(BitmapCache.getDrawable(mContext,
				Constants.ASSETS_RES_PATH + "qq.png"));
		qqLayout.addView(qqPhone);
		
		TextView qqText = new TextView(mActivity);
		qqText.setPadding(DimensionUtil.dip2px(mContext, 5), 0, 0, 0);
		qqText.setTextColor(0xff58687b);
		qqText.setTextSize(18);
		qqText.setText("客服QQ：");
		qqLayout.addView(qqText);

		TextView qq = new TextView(mActivity);
		qq.setOnClickListener(this);
		lp = new LinearLayout.LayoutParams(-2, -2);
		qq.setText(Html.fromHtml("<font color=#000000><a href=''>" + YouaiAppService.basicDate.serviceQQ + "</a></font>"));
		qq.setId(QQId);
		QQ = YouaiAppService.basicDate.serviceQQ;
		qq.setPadding(DimensionUtil.dip2px(mContext, 5), 0, 0, 0);
		qq.setTextColor(0xff58687b);
		qq.setTextSize(18);
		lp.bottomMargin = DimensionUtil.dip2px(mContext, 150);
		qqLayout.addView(qq, lp);

	}

	public OnClickListener getmConfirmListener() {
		return mConfirmListener;
	}

	public void setConfirmListener(OnClickListener mConfirmListener) {
		this.mConfirmListener = mConfirmListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case Constants.CLOSE_ID:
		case Constants.ID_BACK:
			if (mConfirmListener != null) {
				mConfirmListener.onClick(v);
			}
			break;

		case phoneNubId:
			 UserService.getInit().serverPhone(mActivity, phoneNub);
			break;
			
		case QQId:
			if (QQ != null) {
				
				/*Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(Constants.QQ_URL);
				intent.setData(content_url);
				mActivity.startActivity(intent);*/

			}
			break;

		}
	}

	@Override
	public void setButtonClickListener(OnClickListener listener) {
		this.setConfirmListener(listener);
	}
}
