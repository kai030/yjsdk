package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.Selection;
import android.text.Spannable;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.sdk.YouaiAppService;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.LoginClick;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
@SuppressWarnings("ResourceType")
public class ForgetPassword extends AbstractLayoutTow
    implements OnClickListener {

	private boolean falg = false;
	private static ForgetPassword forgetPassword;
	private LoginClick loginClick;
	public Context mContext;
	private String userName;
	private Button mBtConfirm;
	public EditText loginName;
//	private static final int ID_BACK = 0x006;   loginClick.forgerPasswodToAccount(LoginActivity.this);
	//返回事件
	private OnClickListener mConfirmListener;
	//提交按钮
	private OnClickListener forgetPasswordListener;
	
	
	private int dynamicTime = 60;
	private int time = dynamicTime;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (time == dynamicTime) {
				// 发送密码
				loginClick.forgerPasswodToAccount();
				/*CheckAccount checkAccount = new CheckAccount(mActivity);
				UserClickService clickService = new UserClickService(mActivity,checkAccount);
				clickService.alterSecretCommit(overProtection,Constants.A_PHONE_VERIFICATION_CODE);*/
			}
			time--;
//			mBtConfirm.setText(time + "秒");
			if (time > 0) {
				handler.removeCallbacks(runnable);
				handler.postDelayed(runnable, 1000);
			} else {
				mBtConfirm.setFocusable(true);
//				mBtConfirm.setText("重置密码");
				time = dynamicTime;
			}

		}
	};
	
	public static ForgetPassword getForgetPassword(Activity context){
		if(forgetPassword == null) {
			forgetPassword = new ForgetPassword(context);
		}
		return forgetPassword;
	}

	public OnClickListener getForgetPasswordListener() {
		return forgetPasswordListener;
	}

	public void setForgetPasswordListener(OnClickListener forgetPasswordListener) {
		this.forgetPasswordListener = forgetPasswordListener;
	}

	public LinearLayout wrap2;
	public TextView questionEdit;
	
	public void init(String userName, LoginClick loginClick){
		this.loginClick = loginClick;
		this.userName = userName;
		if(!falg)
		initUI();
	}
	
	public ForgetPassword(Activity context) {
		super(context);
		this.mContext = context;
	}

	/**
	 */
	private void initUI() {
		falg = true;
		//-----------------title-----------------------------------
		
		RelativeLayout title = new RelativeLayout(mContext);
		title.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity, "title.9.png"));
//		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1,-2);
//		rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//		title.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,DimensionUtil.dip2px(mContext, 7));
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(mContext, 35));
		llp.topMargin = 2;
		llp.leftMargin = 2;
		llp.rightMargin = 2;
		content.addView(title, llp);
		
		ImageView fanhui = new ImageView(mContext);
		fanhui.setImageDrawable(BitmapCache.getDrawable(mContext,Constants.ASSETS_RES_PATH + "fanhui.png"));
		fanhui.setId(Constants.ID_BACK);
		fanhui.setOnClickListener(this);
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-2, -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
		rlp.leftMargin = DimensionUtil.dip2px(mContext, 5);
		title.addView(fanhui,rlp);
		
		TextView textView = new TextView(mContext);
		textView.setGravity(Gravity.CENTER);
		rlp = new RelativeLayout.LayoutParams(-1, -2);
		textView.setText("找回密码");
		textView.setTextColor(0xff000000);
		textView.setTextSize(20);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		title.addView(textView, rlp);
		
		/*关闭*/
	/*	ImageView close = LayoutUtil.getCloseImage(mContext, this);
		rlp = LayoutUtil.getRelativeParams(mContext);
		title.addView(close,rlp);*/
		
		
		//-----------密保问题，密保代码，新密码------------------------------------------------
		LinearLayout mSubject = new LinearLayout(mContext);
//		mSubject.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity, "no_title_bg.9.png"));
		mSubject.setOrientation(LinearLayout.VERTICAL);
		mSubject.setGravity(Gravity.CENTER_HORIZONTAL);
		content.addView(mSubject,-1,-1);

		ScrollView scroll = new ScrollView(mContext);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -1);
		lp.gravity = Gravity.CENTER;
		mSubject.addView(scroll, lp);
		
		LinearLayout mAllLayout = new LinearLayout(mContext);
		mAllLayout.setOrientation(LinearLayout.VERTICAL);
		mAllLayout.setPadding(DimensionUtil.dip2px(mContext, 15), DimensionUtil.dip2px(mContext, 5), DimensionUtil.dip2px(mContext, 15), 0);
		LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(-1, -1);
		lp4.gravity = Gravity.CENTER_HORIZONTAL;
		scroll.addView(mAllLayout,lp4);
		
		// 用来放置帐号信息的布局
		wrap2 = new LinearLayout(mContext);
		wrap2.setGravity(Gravity.CENTER_VERTICAL);
//		wrap2.setBackgroundDrawableDrawable(BitmapCache.getNinePatchDrawable(mActivity, "key_not_down.9.png"));
		wrap2.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(mContext, 36));
		lp.topMargin = DimensionUtil.dip2px(mContext, 25);
		mAllLayout.addView(wrap2, lp);

		TextView question = new TextView(mContext);
		question.setSingleLine(true);
		question.setHint("账   号 : ");
		question.setTextSize(16);
		question.setTextColor(0xff58687b);
		wrap2.addView(question);
		
		final LinearLayout questionLayout= new LinearLayout(mContext);
		questionLayout.setGravity(Gravity.CENTER_VERTICAL);
		questionLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity, "input_no.9.png"));
		questionLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		
		wrap2.addView(questionLayout,-1,DimensionUtil.dip2px(mContext, 36));
		
		loginName = new EditText(mActivity);
		loginName.setId(0x110);
		loginName.setHint("请输入"+YouaiAppService.min+"-"+YouaiAppService.max+"位帐号");
		if(userName!=null)	
			loginName.setText(userName);
		loginName.setPadding(DimensionUtil.dip2px(mContext, 10), 0, 0, 0);
		loginName.setTextColor(0xff333333);
		loginName.setTextSize(16);
		loginName.setSingleLine();
		loginName.setBackgroundDrawable(null);
		loginName.setGravity(Gravity.CENTER_VERTICAL);
		lp = new LinearLayout.LayoutParams(-1, -2);
//		lp.weight = 1;
		questionLayout.addView(loginName,lp);
		
		loginName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				 if(hasFocus){
					 questionLayout.setBackgroundDrawable(BitmapCache
								.getNinePatchDrawable(mContext, "input.9.png"));
						
						CharSequence text = loginName.getText();
						if(loginName != null && text != null){
							if (text instanceof Spannable) {
						    Spannable spanText = (Spannable)text;
						    Selection.setSelection(spanText, text.length());
						}
						}
						
				}
			}
		});
		
		
		LinearLayout layout1 = new LinearLayout(mActivity);
//		layout1.setPadding(DimensionUtil.dip2px(mContext, 10), 0, 0, 0);
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(-2, -2);
		lp1.gravity = Gravity.LEFT;
		layout1.setGravity(Gravity.CENTER);
		mAllLayout.addView(layout1, lp1);
		layout1.setGravity(Gravity.CENTER);
		
		lp1 = new LinearLayout.LayoutParams(-2, -2);
		lp1.leftMargin = DimensionUtil.dip2px(mContext, 6);
		lp1.topMargin = DimensionUtil.dip2px(mContext, 10);
		TextView reminder = new TextView(mActivity);
		reminder.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		reminder.setTextColor(0xffee0000);
		reminder.setText("注意：点击重置密码后，将会把修改后密码发送到密保手机");
		
		
		mAllLayout.addView(reminder ,lp1);
	
		// 用来放确认和返回按钮的子布局
		LinearLayout wrap3 = new LinearLayout(mContext);
		wrap3.setOrientation(LinearLayout.HORIZONTAL);
		wrap3.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-1, -2);
		lp3.topMargin = DimensionUtil.dip2px(mContext, 10);
		mAllLayout.addView(wrap3, lp3);

		// 确认按钮
		LinearLayout layout = new LinearLayout(mContext);
		layout.setGravity(Gravity.CENTER);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		wrap3.addView(layout, lp3);
		
	    mBtConfirm = new Button(mContext);
		mBtConfirm.setBackgroundDrawable(
                    Utils.getStateListtNinePatchDrawable(mContext, "btn_blue.9.png", "btn_blue_down.9.png"));
		mBtConfirm.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0, DimensionUtil.dip2px(mContext, 7));
		mBtConfirm.setGravity(Gravity.CENTER);
		mBtConfirm.setText("重置密码");
		mBtConfirm.setTextColor(Color.WHITE);
		mBtConfirm.setTextSize(22);
		mBtConfirm.setId(Constants.FORGER_PASSWORD);
		mBtConfirm.setSingleLine();
		mBtConfirm.setOnClickListener(this);
		lp4 = new LinearLayout.LayoutParams(-1, -2);
		lp4.topMargin = DimensionUtil.dip2px(mActivity, 15);
		lp4.bottomMargin = DimensionUtil.dip2px(mActivity, 15);
		layout.addView(mBtConfirm, lp4);
		
		
		LinearLayout layout2 = new LinearLayout(mActivity);
//		layout2.setPadding(DimensionUtil.dip2px(mContext, 10), 0, 0, 0);
		lp1 = new LinearLayout.LayoutParams(-2, -2);
		lp1.topMargin = DimensionUtil.dip2px(mContext, 10);
		lp1.gravity = Gravity.LEFT;
		mAllLayout.addView(layout2, lp1);
		
		TextView tishi = new TextView(mActivity);
		tishi.setText("提示:若您没有设置密保，请通过客服找回您的密码");
		tishi.setTextColor(0xff58687a);
		tishi.setTextSize(12);
		tishi.setSingleLine();
		layout2.addView(tishi);
		
//--------------------客服和QQ------------------------------------
		LinearLayout layout3 = new LinearLayout(mContext);
		layout3.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-2, -2);
		lp.bottomMargin = DimensionUtil.dip2px(mContext, 16);
		mAllLayout.addView(layout3,lp);
		
		LinearLayout servicerLayout = new LinearLayout(mContext);
		servicerLayout.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-2, -2);
		layout3.addView(servicerLayout, lp);

		TextView phoneTextView = new TextView(mContext);
		phoneTextView.setTextSize(12);
		phoneTextView.setText("客服热线：");
		phoneTextView.setTextColor(0xff58687a);
		servicerLayout.addView(phoneTextView);
		
		TextView servicerPhone = new TextView(mContext);
		servicerPhone.setTextSize(12);
		servicerPhone.setAutoLinkMask(Linkify.PHONE_NUMBERS);
		servicerLayout.addView(servicerPhone);

//---------------------------------------------------------
	
		LinearLayout servicerQQLayout = new LinearLayout(mContext);
		servicerQQLayout.setOrientation(LinearLayout.HORIZONTAL);
		layout3.addView(servicerQQLayout, lp);

		TextView servicer = new TextView(mContext);
		servicer.setTextSize(12);
		servicer.setPadding(DimensionUtil.dip2px(mContext, 8), 0, 0,0);
		servicer.setText("客服QQ：");
		servicer.setTextColor(0xff58687a);
		servicerQQLayout.addView(servicer);

		TextView servicerQQ = new TextView(mContext);
		servicerQQ.setTextSize(11);
		servicerQQ.setTextColor(0xff5251f7);
		servicerQQLayout.addView(servicerQQ);
		String servicerPhoneNum = "";


//		if(YouaiAppService.basicDate == null){
//			YouaiAppService.basicDate = GetDataImpl.getInstance(mContext).online();
//			if(YouaiAppService.basicDate != null){
//				 servicerPhoneNum = YouaiAppService.basicDate.serviceTel;
//				if(servicerPhoneNum == null || "0".equals(servicerPhoneNum) || "".equals(servicerPhoneNum)){
//					servicerPhoneNum = "f";
//					phoneTextView.setVisibility(View.GONE);
//				}
//				servicerPhone.setText(servicerPhoneNum);
//				servicerQQ.setText(YouaiAppService.basicDate.serviceQQ);
//			}
//
//		}else{
//			servicerPhoneNum = YouaiAppService.basicDate.serviceTel;
//			if(servicerPhoneNum == null || "0".equals(servicerPhoneNum) || "".equals(servicerPhoneNum)){
//				servicerPhoneNum = "";
//				phoneTextView.setVisibility(View.GONE);
//				servicer.setPadding(DimensionUtil.dip2px(mContext, 0), 0, 0,0);
//			}
//			servicerPhone.setText(servicerPhoneNum);
//			servicerQQ.setText(YouaiAppService.basicDate.serviceQQ);
//		}
//
//		mBtConfirm.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (time == dynamicTime) {
////					newAnserEdit.setText("");
////					mBtConfirm.setText(dynamicTime+"秒");
//					handler.postDelayed(runnable, 1000);
//				}else{
//					ToastUtils.toastShow(mActivity, "一分钟内请勿重复提交");
//				}
//			}
//		});
		
	}

	/**
	 * 获取帐号
	 * 
	 * @return
	 */
	public String getAccount() {
		return loginName.getText().toString();
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
//		case Constants.CLOSE_ID:
		case Constants.ID_BACK:
			if (mConfirmListener != null) {
				mConfirmListener.onClick(v);
			}
			break;
		/*case Constants.FORGER_PASSWORD:
			if ( forgetPasswordListener!= null) {
				forgetPasswordListener.onClick(v);
			}
			break;*/
		}
	}

	@Override
	public void setButtonClickListener(OnClickListener listener) {
		this.setConfirmListener(listener);
		this.setForgetPasswordListener(listener);
	}
	
}
