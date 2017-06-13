/**
 * 
 */
package com.yj.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class PayExitLayout extends RelativeLayout implements OnClickListener {

	private OnClickListener clickListener;
	protected ImageView titleIcon;
	protected TextView titleText;
	protected Button cancelBtn;
	protected TextView txtPrompt;
	protected Button confirmBtn;
	
	public void setOnClickListener(OnClickListener clickListener){
		this.clickListener = clickListener;
	}
	
	public PayExitLayout(Context context) {
		super(context);
		this.setBackgroundColor(0x0aefefef);
		int width =  (int) ((int) Utils.compatibleToWidth(context) * 0.8);
		int height = width*4/6;
		LinearLayout layout = new LinearLayout(context);
		layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
                                                                              "pay_home_bg.9.png"));
		layout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams params = new LayoutParams(
				width, height);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		this.addView(layout, params);
		
		
//		this.addView(layout,width,hetght);
		RelativeLayout titleLayout = new RelativeLayout(context);
		
		LayoutParams layoutParams = new LayoutParams(
				-2, -2);
		layoutParams.rightMargin = DimensionUtil.dip2px(context, 6);
		layoutParams.topMargin = DimensionUtil.dip2px(context, 7);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		ImageView close = new ImageView(context);
		close.setId(Constants.ID_EXIT_CLOSE);
		close.setOnClickListener(this);
		close.setImageDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "pay_home_close.png"));
		titleLayout.addView(close, layoutParams);
		
		
		layout.addView(titleLayout,-1,DimensionUtil.dip2px(context, 35));
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(context, 35));
		LinearLayout titleLayoutOut = new LinearLayout(context);
		titleLayoutOut.setGravity(Gravity.CENTER);
		titleLayoutOut.setOrientation(LinearLayout.HORIZONTAL);
		titleLayout.addView(titleLayoutOut,params1);
		LinearLayout titleLayoutMid = new LinearLayout(context);
		titleLayoutMid.setOrientation(LinearLayout.HORIZONTAL);
		titleLayoutOut.addView(titleLayoutMid,-2,-2);
		
		titleIcon = new ImageView(context);
		titleLayoutOut.addView(titleIcon,-2,-2);
		
		titleText = new TextView(context);
		titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		titleText.setTextColor(0xdd565555);
		titleText.setText("退出购买");
		titleLayoutOut.addView(titleText,-2,-2);
		
		View divideTitle = new View(context);
		divideTitle.setBackgroundColor(0xffaaaaaa);
		LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
				-1, DimensionUtil.dip2px(context, 1));
		layout.addView(divideTitle, layoutParams2);
		
		LinearLayout txtPromptLayout = new LinearLayout(context);//height
		txtPromptLayout.setGravity(Gravity.CENTER);
		params1 = new LinearLayout.LayoutParams(-1, height / 3);
		params1.topMargin = DimensionUtil.dip2px(context, 10);
		layout.addView(txtPromptLayout, params1);
		
	    txtPrompt = new TextView(context);
		txtPrompt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		txtPrompt.setTextColor(0xee565555);
		txtPrompt.setText("反对反对法反对反对法");
		txtPromptLayout.addView(txtPrompt,-2,-2);	
		
		LinearLayout btnLayoutOut = new LinearLayout(context);
		btnLayoutOut.setGravity(Gravity.CENTER);
		layout.addView(btnLayoutOut,-1,-2);
		LinearLayout btnLayout = new LinearLayout(context);
		btnLayout.setOrientation(LinearLayout.HORIZONTAL);
		btnLayoutOut.addView(btnLayout,-2,-2);
		
	    confirmBtn = new Button(context);
//		confirmBtn.setId(Constants.ID_EXIT_CONFIRM);
		confirmBtn.setOnClickListener(this);
		confirmBtn.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
                                                                                  "exit_confirm_btn.9.png"));
		confirmBtn.setText("确认");
		params1 = new LinearLayout.LayoutParams(width * 5 / 12, DimensionUtil.dip2px(context, 30));
		btnLayout.addView(confirmBtn,params1);
		
		
	    cancelBtn = new Button(context);
//		cancelBtn.setId(Constants.ID_EXIT_CANCEL);
		cancelBtn.setOnClickListener(this);
		cancelBtn.setText("取消");
		cancelBtn.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
                                                                                 "exit_cancel_btn.9.png"));
		params1 = new LinearLayout.LayoutParams(width * 5 / 12, DimensionUtil.dip2px(context, 30));
		params1.leftMargin = width/15;
		btnLayout.addView(cancelBtn,params1);
		
		
		
		
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case Constants.ID_EXIT_CLOSE:
		case Constants.ID_EXIT_CONFIRM:
		case Constants.ID_PAY_FAILURE:
		case Constants.ID_EXIT_CANCEL:
		case Constants.ID_PAY_SUCCEED:
			if(this.clickListener != null){
				this.clickListener.onClick(v);
			}
			break;

		default:
			break;
		}
		
	}

}
