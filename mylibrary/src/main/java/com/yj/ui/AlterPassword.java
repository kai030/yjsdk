package com.yj.ui;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
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
import com.yj.util.LoginService;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月28日
 * @copyright 游鹏科技
 */
@SuppressWarnings("ResourceType")
public class AlterPassword extends AbstractLayoutTow implements OnClickListener {

	private Activity mContext;
	
	//返回事件
		private OnClickListener mConfirmListener;
		/** 原密码 */
		public EditText answerEdit;
		/** 新密码 */
		public EditText inputPas;
		/*输入框*/
		private LinearLayout answerEditLayout,inputPasLayout;
		
	
	public AlterPassword(Activity context) {
		super(context);
		this.mContext = context;
		initUI();
	}
	
	private void initUI() {
		//-----------------title-----------------------------------
		
		RelativeLayout title = new RelativeLayout(mContext);
		title.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity, "title.9.png"));
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1, -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		title.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0, DimensionUtil.dip2px(mContext, 7));
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-1, -2);
		llp.leftMargin = 2;
		llp.rightMargin = 2;
		llp.topMargin = 2;
		content.addView(title, llp);
		/*返回*/
		ImageView back = new ImageView(mContext);
		back.setImageDrawable(BitmapCache.getDrawable(mContext, Constants.ASSETS_RES_PATH + "fanhui.png"));
		back.setId(Constants.ID_BACK);
		back.setOnClickListener(this);
		rlp = new RelativeLayout.LayoutParams(-2, -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
		rlp.leftMargin = DimensionUtil.dip2px(mContext, 5);
		title.addView(back,rlp);
		/*关闭*/
		ImageView close = LayoutUtil.getCloseImage(mContext, this);
		rlp = LayoutUtil.getRelativeParams(mContext);
		title.addView(close,rlp);

		TextView textView = new TextView(mContext);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		rlp = new RelativeLayout.LayoutParams(-1, -2);
		textView.setText("修改密码");
		textView.setTextColor(0xff000000);
		textView.setTextSize(22);
		title.addView(textView, rlp);
		
		//-----------------------------------------------------
		
		// 5399账号
		LinearLayout wrap1 = new LinearLayout(mContext);
		/*wrap1.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				mActivity, "titil_user.9.png"));*/
		wrap1.setOrientation(LinearLayout.HORIZONTAL);
		wrap1.setGravity(Gravity.CENTER_VERTICAL);

		LinearLayout.LayoutParams lpid = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(mContext, 35));
		lpid.gravity = Gravity.CENTER_HORIZONTAL;
		lpid.topMargin = DimensionUtil.dip2px(mContext, 8);
		content.addView(wrap1, lpid);

		ImageView userLogo = new ImageView(mContext);
		userLogo.setBackgroundDrawable(BitmapCache.getDrawable(mActivity,
				Constants.ASSETS_RES_PATH + "user_logo.png"));
		LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-2, -2);
		lp3.setMargins(Constants.BORDER_MARGIN + DimensionUtil.dip2px(mContext, 26), 0, DimensionUtil.dip2px(mContext, 10), 0);
		wrap1.addView(userLogo,lp3);
		
		TextView mRegistUserId = new TextView(mContext);
		mRegistUserId.setSingleLine(true);
		mRegistUserId.setBackgroundDrawable(null);
		try {
			mRegistUserId.setText(
                            Html.fromHtml(Constants.COMPANY_NAME + "帐号: <font color = '#000000'> " + YouaiAppService.mSession.userAccount + "</font>"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		mRegistUserId.setTextColor(0xff435153);
		mRegistUserId.setTextSize(16);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		lp3.weight = 1;
		wrap1.addView(mRegistUserId, lp3);
		
		/*横线*/
		View topLine = new View(mContext);
		topLine.setBackgroundColor(0xff999999);
		LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(-1, 1);
		lp4.leftMargin = Utils.getBorderMargin(mContext, Constants.BORDER_MARGIN);
		lp4.rightMargin = Utils.getBorderMargin(mContext, Constants.BORDER_MARGIN);
		lp4.topMargin = DimensionUtil.dip2px(mContext, 10);
		lp4.bottomMargin = DimensionUtil.dip2px(mContext, 18);
//		wrap1.addView(topLine,lp4);
		content.addView(topLine, lp4);
		
		
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
		mAllLayout.setPadding(DimensionUtil.dip2px(mContext, 25), DimensionUtil.dip2px(mContext, 5), DimensionUtil
                    .dip2px(mContext, 25), 0);
	    lp4 = new LinearLayout.LayoutParams(-1, -1);
		lp4.gravity = Gravity.CENTER_VERTICAL;
		scroll.addView(mAllLayout,lp4);
				
//--------------------原密码-----------------------------------------
		LinearLayout wrap4 = new LinearLayout(mContext);
		wrap4.setGravity(Gravity.CENTER_VERTICAL);
		wrap4.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mActivity, 10);
		mAllLayout.addView(wrap4, lp);

		TextView answer = new TextView(mContext);
		answer.setSingleLine(true);
		answer.setHint("原密码 : ");
		answer.setTextSize(16);
		answer.setTextColor(0xff58687b);
		wrap4.addView(answer);
		
		answerEditLayout = new LinearLayout(mContext);
		answerEditLayout.setGravity(Gravity.CENTER_VERTICAL);
		answerEditLayout.setOrientation(LinearLayout.HORIZONTAL);
		answerEditLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity, "input.9.png"));
		
		answerEdit = new EditText(mActivity);
		answerEdit.setId(0x11090);
		answerEdit.setInputType(InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		answerEdit.setHint("请输入原密码");
		answerEdit.setPadding(DimensionUtil.dip2px(mActivity, 10), 0, 0, 0);
		answerEdit.setTextColor(0xff343434);
		answerEdit.setBackgroundDrawable(null);
		answerEdit.setTextSize(14);
		answerEdit.setSingleLine();
		answerEdit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
		answerEdit.setText("");
		Utils.setKeyboard(mContext);
		Utils.getEditTextFocus(answerEdit);

		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(-1, -1);
		lp2.leftMargin = DimensionUtil.dip2px(mActivity, 5);
		answerEditLayout.addView(answerEdit,lp2);
		
		lp = new LinearLayout.LayoutParams(-2, DimensionUtil.dip2px(mActivity, 36));
		lp.weight = 1;
		lp.leftMargin = DimensionUtil.dip2px(mActivity, 10);
		wrap4.addView(answerEditLayout,lp);

		
//-----------------------新密码--------------------------------------------
		
		LinearLayout newPassLayout = new LinearLayout(mContext);
		newPassLayout.setGravity(Gravity.CENTER_VERTICAL);
		newPassLayout.setOrientation(LinearLayout.HORIZONTAL);
		lp = new LinearLayout.LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mActivity, 25);
		mAllLayout.addView(newPassLayout, lp);

		TextView newPass = new TextView(mContext);
		newPass.setSingleLine(true);
		newPass.setHint("新密码 : ");
		newPass.setTextSize(16);
		newPass.setTextColor(0xff58687b);
		newPassLayout.addView(newPass);
		
		inputPasLayout = new LinearLayout(mContext);
		inputPasLayout.setGravity(Gravity.CENTER_VERTICAL);
		inputPasLayout.setOrientation(LinearLayout.HORIZONTAL);
		inputPasLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity, "input_no.9.png"));
		
		inputPas = new EditText(mActivity);
		inputPas.setHint("请输入" + YouaiAppService.min + "-" + YouaiAppService.max + "位新密码");
//		inputPas.setHint("请输入新密码");
		inputPas.setPadding(DimensionUtil.dip2px(mActivity, 10), 0, 0, 0);
		inputPas.setBackgroundDrawable(null);
		inputPas.setTextColor(0xff343434);
		inputPas.setTextSize(14);
		inputPas.setSingleLine();
		inputPas.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
		inputPas.setGravity(Gravity.CENTER_VERTICAL);
		inputPasLayout.addView(inputPas,lp2);
		
		lp = new LinearLayout.LayoutParams(-2, DimensionUtil.dip2px(mActivity, 36));
		lp.weight = 1;
		lp.leftMargin = DimensionUtil.dip2px(mActivity, 10);
		newPassLayout.addView(inputPasLayout,lp);
		
		/*answerEditLayout.setBackgroundDrawable(BitmapCache
					.getNinePatchDrawable(mContext, "key_not_down.9.png"));
		inputPasLayout.setBackgroundDrawable(BitmapCache
					.getNinePatchDrawable(mContext, "key_not_down.9.png"));*/

		LoginService.getInit(mContext).select(inputPasLayout, answerEditLayout, inputPas, answerEdit, null, null);
		
		LinearLayout layout1 = new LinearLayout(mActivity);
//		layout1.setPadding(DimensionUtil.dip2px(mContext, 10), 0, 0, 0);
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(-2, -2);
		lp1.gravity = Gravity.LEFT;
		layout1.setGravity(Gravity.CENTER);
		mAllLayout.addView(layout1, lp1);
		layout1.setGravity(Gravity.CENTER);
		
	
		// 用来放确认和返回按钮的子布局
		LinearLayout wrap3 = new LinearLayout(mContext);
		wrap3.setOrientation(LinearLayout.HORIZONTAL);
		wrap3.setGravity(Gravity.CENTER);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		lp3.topMargin = DimensionUtil.dip2px(mContext, 10);
		mAllLayout.addView(wrap3, lp3);

		// 确认按钮
		LinearLayout layout = new LinearLayout(mContext);
		layout.setGravity(Gravity.CENTER);
		lp3 = new LinearLayout.LayoutParams(-1, -2);
		wrap3.addView(layout, lp3);
		
		Button mBtConfirm = new Button(mContext);
		mBtConfirm.setBackgroundDrawable(
                    Utils.getStateListtNinePatchDrawable(mContext, "btn_blue.9.png", "btn_blue_down.9.png"));
		mBtConfirm.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0, DimensionUtil.dip2px(mContext, 7));
		mBtConfirm.setGravity(Gravity.CENTER);
		mBtConfirm.setId(Constants.ID_ALTERPASSWORD_COMMIT);
		mBtConfirm.setOnClickListener(this);
		mBtConfirm.setText("确   认");
		mBtConfirm.setTextColor(Color.WHITE);
		mBtConfirm.setTextSize(22);
		mBtConfirm.setSingleLine();
		mBtConfirm.setOnClickListener(this);
		lp4 = new LinearLayout.LayoutParams(-1, -2);
		lp4.topMargin = DimensionUtil.dip2px(mActivity, 15);
		lp4.bottomMargin = DimensionUtil.dip2px(mActivity, 25);
		layout.addView(mBtConfirm, lp4);
		
	}

	@Override
	public void setButtonClickListener(OnClickListener listener) {
		this.mConfirmListener = listener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case Constants.CLOSE_ID:
		case Constants.ID_ALTERPASSWORD_COMMIT:
		case Constants.ID_BACK:
			if(this.mConfirmListener != null)
				this.mConfirmListener.onClick(v);
			break;
			

		default:
			break;
		}
		
	}
	
	/**
	 * 获取旧密码
	 * @return
	 */
	public String getOldPassword(){
		if(answerEdit != null) return answerEdit.getText().toString();
		return null;
	}
	
	/**
	 * 获取新密码
	 * @param context
	 */
	public String getNewPassword(){
		if(answerEdit != null) return inputPas.getText().toString();
		return null;
	}
	
	
}
