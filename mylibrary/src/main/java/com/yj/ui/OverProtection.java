package com.yj.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.entity.SecretData;
import com.yj.sdk.YouaiAppService;
import com.yj.util.ActivityService;
import com.yj.util.BitmapCache;
import com.yj.util.CheckAccount;
import com.yj.util.DimensionUtil;
import com.yj.util.UserClickService;
import com.yj.util.Utils;

/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class OverProtection extends AbstractLayoutTow implements
		View.OnClickListener {
	private ActivityService activityService;
	private Button button3;

	private int dynamicTime = 60;
	private int time = dynamicTime;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (time == dynamicTime) {
				// 获取验证码
				CheckAccount checkAccount = new CheckAccount(mContext);
				UserClickService clickService = new UserClickService(mContext,
						checkAccount,activityService);
				clickService.alterSecretCommit(overProtection,
						Constants.A_PHONE_VERIFICATION_CODE,mContext);
			}
			time--;
			button3.setText(time + "秒");
			if (time > 0) {
				handler.removeCallbacks(runnable);
				handler.postDelayed(runnable, 1000);
			} else {
				button3.setFocusable(true);
				button3.setText("获取验证");
				time = dynamicTime;
			}

		}
	};

	// 下拉事件
	private OnClickListener optionListener;

	public RelativeLayout newQuestionEditLayout;

	public TextView newQuestionEdit;

	private EditText oldAnserEdit, newAnserEdit;

	public Activity mContext;

	private OnClickListener mConfirmListener;

	private SecretData[] secretData;

	private LinearLayout oldAnswerEditLayout;

	private LinearLayout newAnswerEditLayout;

	
	public void setActivity(Activity mContext){
		this.mContext = mContext;
	}
	/**
	 * 获取旧答案
	 * 
	 * @return
	 */
	public String getOldAnserText() {
		return oldAnserEdit.getText().toString();
	}

	/**
	 * 获取新答案
	 * 
	 * @return
	 */
	public String getNewAnserText() {
		return newAnserEdit.getText().toString();
	}

	private static OverProtection overProtection;

	public static OverProtection getOverProtection(Activity mContext,
                                                       SecretData[] secretData, ActivityService activityService) {
		if(overProtection == null)
		overProtection = new OverProtection(mContext, secretData, activityService);
		return overProtection;
	}

	public OverProtection(Activity mContext, SecretData[] secretData, ActivityService activityService) {
		super(mContext);
		this.activityService = activityService;
		this.secretData = secretData;
		initUI(mContext);
	}

	@SuppressWarnings("ResourceType")
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
//		fanhui.setId(Constants.ID_BACK);
		fanhui.setOnClickListener(this);
		rlp = new RelativeLayout.LayoutParams(-2, -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT
                            | RelativeLayout.CENTER_VERTICAL);
		rlp.leftMargin = DimensionUtil.dip2px(mContext, 5);
		title.addView(fanhui, rlp);

		/* 关闭 */
		ImageView close = LayoutUtil.getCloseImage(mContext, this);
		rlp = LayoutUtil.getRelativeParams(mContext);
		title.addView(close, rlp);

		TextView textView = new TextView(mContext);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		rlp = new RelativeLayout.LayoutParams(-1, -2);
		textView.setText("修改密保");
		textView.setTextColor(0xff000000);
		textView.setTextSize(22);
		title.addView(textView, rlp);

		// -----------------------------------------------------

		// layout.addView(qq, lp); // 5399账号
		LinearLayout wrap1 = new LinearLayout(mContext);
		wrap1.setOrientation(LinearLayout.HORIZONTAL);
		wrap1.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams lpid = new LinearLayout.LayoutParams(-1,
                                                                               DimensionUtil.dip2px(mContext, 37));
		lpid.gravity = Gravity.CENTER_HORIZONTAL;
		lpid.topMargin = DimensionUtil.dip2px(mContext, 5);
		content.addView(wrap1, lpid);

		ImageView userLogo = new ImageView(mContext);
		userLogo.setBackgroundDrawable(BitmapCache.getDrawable(mActivity,
				Constants.ASSETS_RES_PATH + "user_logo.png"));
		LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-2, -2);
		lp3.setMargins(DimensionUtil.dip2px(mContext, 35), 0,
                               DimensionUtil.dip2px(mContext, 10), 0);
		wrap1.addView(userLogo, lp3);

		TextView mRegistUserId = new TextView(mContext);
		mRegistUserId.setSingleLine(true);
		mRegistUserId.setBackgroundDrawable(null);
		mRegistUserId.setText(Html.fromHtml(Constants.COMPANY_NAME
                                                    + "帐号: <font color = '#000000'> "
                                                    + YouaiAppService.mSession.userAccount + "</font>"));
		mRegistUserId.setTextColor(0xff435153);
		mRegistUserId.setTextSize(15);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		lp3.weight = 1;
		wrap1.addView(mRegistUserId, lp3);

		/* 横线 */
		View topLine = new View(mContext);
		topLine.setBackgroundColor(0xff999999);
		LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(-1, 1);
		lp4.leftMargin = Utils.getBorderMargin(mContext, 20);
		lp4.rightMargin = Utils.getBorderMargin(mContext, 20);
		// lp4.topMargin = DimensionUtil.dip2px(mContext,8);
		lp4.bottomMargin = DimensionUtil.dip2px(mContext, 5);
		lp4.topMargin = DimensionUtil.dip2px(mContext, 5);
		// wrap1.addView(topLine,lp4);
		content.addView(topLine, lp4);

		LinearLayout layout1 = new LinearLayout(mContext);
		LinearLayout.LayoutParams oldParams = new LinearLayout.LayoutParams(-1,
                                                                                    -2);
		layout1.setOrientation(LinearLayout.VERTICAL);
		// layout1.setBackgroundDrawableColor(0xffffffff);
		content.addView(layout1, oldParams);

		/*************************/

		/* 原密保问题 */
		LinearLayout oldQuestionlayout = new LinearLayout(mContext);
		oldQuestionlayout.setOrientation(LinearLayout.HORIZONTAL);
		oldQuestionlayout.setGravity(Gravity.CENTER_VERTICAL);
		oldParams = new LinearLayout.LayoutParams(-1, -2);
		oldParams.topMargin = DimensionUtil.dip2px(mActivity, 5);
		oldParams.leftMargin = DimensionUtil.dip2px(mActivity, 15);
		oldParams.rightMargin = DimensionUtil.dip2px(mActivity, 15);
		layout1.addView(oldQuestionlayout, oldParams);

		TextView oldQuestionText = new TextView(mContext);
		try {
			// oldQuestionText.setText("原问题：" +
			// this.secretData[Session.secretId-1].secretName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		oldQuestionText.setTextSize(15);
		oldQuestionText.setTextColor(0xff767B7F);
		oldQuestionlayout.addView(oldQuestionText, -2, -2);

		/*--------------------------------------------------------------------*/
		// 原答案
		LinearLayout oldAnswerLayout = new LinearLayout(mContext);
		oldAnswerLayout.setOrientation(LinearLayout.HORIZONTAL);
		oldAnswerLayout.setGravity(Gravity.CENTER_VERTICAL);
		oldParams = new LinearLayout.LayoutParams(-1, -2);
		oldParams.topMargin = DimensionUtil.dip2px(mContext, 14);
		oldParams.leftMargin = DimensionUtil.dip2px(mContext, 15);
		oldParams.rightMargin = DimensionUtil.dip2px(mContext, 15);
		layout1.addView(oldAnswerLayout, oldParams);

		TextView oldAnserText = new TextView(mContext);
		oldAnserText.setText("绑定号码：");
		oldAnserText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		oldAnserText.setTextColor(0xff767B7F);
		oldParams = new LinearLayout.LayoutParams(-2, -2);
		oldAnswerLayout.addView(oldAnserText, oldParams);

		oldAnswerEditLayout = new LinearLayout(mActivity);
		oldParams = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(
				mContext, 40));
		oldAnswerEditLayout.setGravity(Gravity.CENTER_VERTICAL);
		oldAnswerEditLayout.setBackgroundDrawable(BitmapCache
				.getNinePatchDrawable(mContext, "input_no.9.png"));

		oldAnswerLayout.addView(oldAnswerEditLayout, oldParams);

		oldAnserEdit = new EditText(mContext);
		oldAnserEdit.setPadding(DimensionUtil.dip2px(mActivity, 5), 0, 0, 0);
		oldAnserEdit.setBackgroundDrawable(null);
		// oldAnserEdit.setHint("请输入2-20个字符");
//		oldAnserEdit.setText(YouaiAppService.mSession.phoneNum);
		oldAnserEdit.setTextColor(0xff001100);
		oldAnserEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		oldAnserEdit.setSingleLine();
		oldAnserEdit.setEnabled(false);
		oldAnserEdit
				.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
		oldAnswerEditLayout.addView(oldAnserEdit, -1, -1);

		// 新密保问题
		LinearLayout newQuestionLayout = new LinearLayout(mContext);
		newQuestionLayout.setOrientation(LinearLayout.HORIZONTAL);
		// newQuestionLayout.setBackgroundDrawableColor(0xffffffff);
		newQuestionLayout.setGravity(Gravity.CENTER_VERTICAL);
		oldParams = new LinearLayout.LayoutParams(-1, -2);
		oldParams.topMargin = DimensionUtil.dip2px(mContext, 14);
		oldParams.leftMargin = DimensionUtil.dip2px(mContext, 15);
		oldParams.rightMargin = DimensionUtil.dip2px(mContext, 15);
		// layout1.addView(newQuestionLayout, oldParams);

		TextView newQuestionText = new TextView(mContext);
		newQuestionText.setText("新问题：");
		newQuestionText.setTextSize(15);
		newQuestionText.setTextColor(0xff767B7F);
		newQuestionLayout.addView(newQuestionText, -2, -2);

		newQuestionEditLayout = new RelativeLayout(mActivity);
		oldParams = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(
				mContext, 40));
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    -1, DimensionUtil.dip2px(mContext, 40));
		newQuestionEditLayout.setGravity(Gravity.CENTER_VERTICAL);
		newQuestionEditLayout.setBackgroundDrawable(BitmapCache
				.getNinePatchDrawable(mContext, "input_no.9.png"));
//		newQuestionEditLayout.setId(Constants.ID_NEW_SECRET_QUESTION);
		newQuestionLayout.addView(newQuestionEditLayout, layoutParams);
		newQuestionEditLayout.setOnClickListener(this);
		newQuestionEdit = new TextView(mContext);
		newQuestionEdit.setGravity(Gravity.CENTER_VERTICAL);
		newQuestionEdit.setPadding(DimensionUtil.dip2px(mActivity, 5), 0, 0, 0);
		newQuestionEdit.setId(1);
		newQuestionEdit.setBackgroundDrawable(null);
		// newQuestionEdit.setHint("请选择问题");
		newQuestionEdit.setText(secretData[0].secretName);
		newQuestionEdit.setTextColor(0xff767B7F);
		newQuestionEdit.setTextSize(15);
		newQuestionEdit.setSingleLine();
		newQuestionEdit
				.setFilters(new InputFilter[] {new InputFilter.LengthFilter(15) });// 内容长度
		newQuestionEditLayout.addView(newQuestionEdit, -1, -1);

		ImageView rightImage = new ImageView(mActivity);
		rightImage.setBackgroundDrawable(BitmapCache.getDrawable(mActivity,
				Constants.ASSETS_RES_PATH + "down_bk.png"));
		layoutParams = new RelativeLayout.LayoutParams(-2, -2);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layoutParams.rightMargin = DimensionUtil.dip2px(mContext, 5);
		layoutParams.topMargin = DimensionUtil.dip2px(mContext, 15);
		newQuestionEditLayout.addView(rightImage, layoutParams);

		// 新密保答案
		LinearLayout newAnswerLayout = new LinearLayout(mContext);
		newAnswerLayout.setGravity(Gravity.CENTER_VERTICAL);
		newAnswerLayout.setOrientation(LinearLayout.HORIZONTAL);
		oldParams = new LinearLayout.LayoutParams(-1, -2);
		oldParams.topMargin = DimensionUtil.dip2px(mContext, 14);
		oldParams.leftMargin = DimensionUtil.dip2px(mContext, 15);
		oldParams.rightMargin = DimensionUtil.dip2px(mContext, 15);
		layout1.addView(newAnswerLayout, oldParams);

		TextView newAnserText = new TextView(mContext);
		newAnserText.setText("手机验证：");
		newAnserText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		newAnserText.setTextColor(0xff767B7F);
		newAnswerLayout.addView(newAnserText, -2, -2);

		newAnswerEditLayout = new LinearLayout(mActivity);
		newAnswerEditLayout.setOrientation(LinearLayout.HORIZONTAL);
		oldParams = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(
				mContext, 40));
		newAnswerEditLayout.setGravity(Gravity.CENTER_VERTICAL);
		newAnswerEditLayout.setBackgroundColor(0x00000000);
		newAnswerLayout.addView(newAnswerEditLayout, oldParams);

		LinearLayout verificationLayout = new LinearLayout(mActivity);
		verificationLayout.setBackgroundDrawable(BitmapCache
				.getNinePatchDrawable(mContext, "input_no.9.png"));
		oldParams = new LinearLayout.LayoutParams(DimensionUtil.dip2px(
				mActivity, 80), DimensionUtil.dip2px(mActivity, 36));
		newAnswerEditLayout.addView(verificationLayout, oldParams);

		newAnserEdit = new EditText(mContext);
		newAnserEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
		newAnserEdit.setPadding(DimensionUtil.dip2px(mActivity, 5), 0, 0, 0);
		newAnserEdit.setId(0x115);
		newAnserEdit.setBackgroundDrawable(null);
		// newAnserEdit.setHint("请输入2-20个字符");
		newAnserEdit.setTextColor(0xff767B7F);
		newAnserEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		newAnserEdit.setSingleLine();
		newAnserEdit
				.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
		oldParams = new LinearLayout.LayoutParams(-1, -1);
		verificationLayout.addView(newAnserEdit, oldParams);

		button3 = new Button(mContext);
		button3.setText("获取验证");
		button3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		oldParams = new LinearLayout.LayoutParams(DimensionUtil.dip2px(
				mActivity, 80), DimensionUtil.dip2px(mActivity, 40));
		oldParams.leftMargin = DimensionUtil.dip2px(mActivity, 10);
		newAnswerEditLayout.addView(button3, oldParams);

		/*
		 * LoginService.getInit(mContext).selectInputMb(oldAnswerEditLayout,
		 * newAnswerEditLayout, oldAnserEdit, newAnserEdit,
		 * newQuestionEditLayout);
		 */// 设置输入框背景

		// 确认按钮
		LinearLayout buttonlayout = new LinearLayout(mContext);
		// buttonlayout.setGravity(Gravity.CENTER);
		oldParams = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(
				mContext, 50));
		oldParams.bottomMargin = DimensionUtil.dip2px(mActivity, 3);
		oldParams.topMargin = DimensionUtil.dip2px(mActivity, 14);
		layout1.addView(buttonlayout, oldParams);

		Button mBtConfirm = new Button(mContext);
		mBtConfirm.setBackgroundDrawable(Utils.getStateListtNinePatchDrawable(
				mContext, "btn_blue.9.png", "btn_blue_down.9.png"));
		mBtConfirm.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
                                      DimensionUtil.dip2px(mContext, 7));
		mBtConfirm.setGravity(Gravity.CENTER);
//		mBtConfirm.setId(Constants.ID_NEW_SECRET_COMMIT);
		mBtConfirm.setText("解除绑定");
		mBtConfirm.setTextColor(Color.WHITE);
		mBtConfirm.setTextSize(20);
		mBtConfirm.setSingleLine();
		mBtConfirm.setOnClickListener(this);
		LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
				-1, -2);
		layoutParams2.leftMargin = DimensionUtil.dip2px(mContext, 10);
		layoutParams2.rightMargin = DimensionUtil.dip2px(mContext, 10);
		// layoutParams2.bottomMargin = DimensionUtil.dip2px(mContext,30);
		buttonlayout.addView(mBtConfirm, layoutParams2);

		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * if(!Utils.isInteger(answerEdit.getText().toString())){
				 * ToastUtils.toastShow(mContext, "请正确输入手机号码"); return; }
				 */
				if (time == dynamicTime) {
					newAnserEdit.setText("");
					button3.setText(dynamicTime + "秒");
					handler.postDelayed(runnable, 1000);
				}

			}
		});

	}

	public void setOptionListener(OnClickListener optionListener) {
		this.optionListener = optionListener;
	}

	public void setConfirmListener(OnClickListener mConfirmListener) {
		this.mConfirmListener = mConfirmListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case Constants.ID_NEW_SECRET_QUESTION:

			/* 设置输入框背景 */
			/*
			 * newQuestionEditLayout.setBackgroundDrawable(BitmapCache
			 * .getNinePatchDrawable(mActivity, "input.9.png"));
			 * oldAnswerEditLayout.setBackgroundDrawable(BitmapCache
			 * .getNinePatchDrawable(mActivity, "input_no.9.png"));
			 * newAnswerEditLayout.setBackgroundDrawable(BitmapCache
			 * .getNinePatchDrawable(mActivity, "input_no.9.png"));
			 */

			// 密保下拉事件
			if (optionListener != null) {
				optionListener.onClick(v);
			}
			break;
		case Constants.ID_NEW_SECRET_COMMIT:
		case Constants.CLOSE_ID:
		case com.yj.entity.Constants.ID_BACK:
			if (mConfirmListener != null) {
				mConfirmListener.onClick(v);
			}
			break;
		}

	}

	@Override
	public void setButtonClickListener(OnClickListener listener) {
		this.setConfirmListener(listener);
	}
}
