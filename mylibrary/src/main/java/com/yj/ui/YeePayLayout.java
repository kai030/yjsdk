/**
 * 
 */
package com.yj.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import com.yj.entity.ChargeData;
import com.yj.entity.Constants;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * @author lufengkai
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
@SuppressWarnings("ResourceType")
public class YeePayLayout extends PayLayout {

	/**
	 * @param context
	 * @param flag
	 */
	private ImageView mobileBg, unicomBg, telecomBg;
	private ImageView[] operatorBg = new ImageView[3];
	public static EditText cardDenominationEdit;
	private EditText cardPwdEdit,cardNumEdit;
	
    public EditText getCardPwdEdit(){
		return cardPwdEdit;
    }
    public EditText getCardNumEdit(){
  		return cardNumEdit;
      }
	public ImageView[] getOperatorBg() {
		return operatorBg;
	}

	public YeePayLayout(Context context, int flag) {
		super(context, flag);

		LinearLayout operatorsImageOut = new LinearLayout(context);
		operatorsImageOut.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
		params.topMargin = DimensionUtil.dip2px(context, 5);
		operatorsLayout.addView(operatorsImageOut, params);
		operatorsImageOut.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout operatorsImage = new LinearLayout(context);
		operatorsImageOut.setOrientation(LinearLayout.HORIZONTAL);
		operatorsImageOut.addView(operatorsImage, -2, -2);

		Drawable drawable = BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "mobile_btn.png");
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		
		int widthOut = Utils.compatibleToWidth(context) / 3 - DimensionUtil.dip2px(context, 8);
		

		RelativeLayout mobileImageLayout = new RelativeLayout(context);
		ImageView mobileImage = new ImageView(context);
		mobileImage.setId(Constants.ID_PAY_MOBILE);
		mobileImage.setOnClickListener(this);
		mobileImage.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "mobile_btn.png"));// mobile_btn.png
		operatorsImage.addView(mobileImageLayout, widthOut, height*widthOut/width);
		mobileImageLayout.addView(mobileImage, widthOut, height*widthOut/width);

		mobileBg = new ImageView(context);
		mobileBg.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				context, "operators_on_bj.9.png"));
		operatorBg[0] = mobileBg;
		mobileImageLayout.addView(mobileBg, widthOut, height*widthOut/width);

		RelativeLayout unicomImageLayout = new RelativeLayout(context);
		ImageView unicomImage = new ImageView(context);
		unicomImage.setId(Constants.ID_PAY_UNICOM);
		unicomImage.setOnClickListener(this);
		unicomImage.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "unicom_btn.png"));// mobile_btn.png
		operatorsImage.addView(unicomImageLayout, widthOut, height*widthOut/width);
		unicomImageLayout.addView(unicomImage, widthOut, height*widthOut/width);
		unicomBg = new ImageView(context);
		unicomBg.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				context, "operators_on_bj.9.png"));
		unicomBg.setVisibility(View.GONE);
		operatorBg[1] = unicomBg;
		unicomImageLayout.addView(unicomBg, widthOut, height*widthOut/width);

		RelativeLayout telecomImageLayout = new RelativeLayout(context);
		ImageView telecomImage = new ImageView(context);
		telecomImage.setId(Constants.ID_PAY_TELECOM);
		telecomImage.setOnClickListener(this);
		telecomImage.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "telecom_btn.png"));// mobile_btn.png
		operatorsImage.addView(telecomImageLayout, widthOut, height*widthOut/width);

		telecomImageLayout.addView(telecomImage, widthOut, height*widthOut/width);

		telecomBg = new ImageView(context);
		telecomBg.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				context, "operators_on_bj.9.png"));
		telecomBg.setVisibility(View.GONE);
		operatorBg[2] = telecomBg;
		telecomImageLayout.addView(telecomBg, widthOut, height*widthOut/width);
		
		LinearLayout inputLayout = new LinearLayout(context);
		inputLayout.setOrientation(LinearLayout.VERTICAL);
		params = new LinearLayout.LayoutParams(-1, -2);
		operatorsLayout.addView(inputLayout,params);
		
		/*卡号*/
		LinearLayout inputCardNumLayout = new LinearLayout(context);
		inputCardNumLayout.setOrientation(LinearLayout.HORIZONTAL);
		params = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(context, 35));
		params.topMargin = DimensionUtil.dip2px(context, 10);
		params.leftMargin = DimensionUtil.dip2px(context, 10);
		params.rightMargin = DimensionUtil.dip2px(context, 16);
		inputLayout.addView(inputCardNumLayout,params);
		
		TextView cardNum = new TextView(context);
		cardNum.setText("卡号：");
		cardNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		cardNum.setTextColor(0xff123521);
		inputCardNumLayout.addView(cardNum,-2,-2);
		
	    cardNumEdit = new EditText(context);
		cardNumEdit.setGravity(Gravity.CENTER_VERTICAL);
		cardNumEdit.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				context, "card_input_bg.9.png"));
		cardNumEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
		cardNumEdit.setHint("请输入充值卡号");
		cardNumEdit.setSingleLine(true);
		cardNumEdit.setId(100);
		cardNumEdit.setTextColor(0xff123521);
		cardNumEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		cardNumEdit.setHintTextColor(0x7758687a);
		cardNumEdit
		.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
	/*	if(Session.getInstance() != null && Session.getInstance().userAccount != null && Session.getInstance().userAccount.length() >= YouaiAppService.min){
			cardNumEdit.setText(Session.getInstance().userAccount);
			CharSequence text = cardNumEdit.getText();
			if (text instanceof Spannable) {
			    Spannable spanText = (Spannable)text;
			    Selection.setSelection(spanText, text.length());
			}
		}*/
		inputCardNumLayout.addView(cardNumEdit,-1,-1);
		
		/*卡密*/
		LinearLayout inputCardcardPwdLayout = new LinearLayout(context);
		inputCardcardPwdLayout.setOrientation(LinearLayout.HORIZONTAL);
		params = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(context, 35));
		params.topMargin = DimensionUtil.dip2px(context, 6);
		params.leftMargin = DimensionUtil.dip2px(context, 10);
		params.rightMargin = DimensionUtil.dip2px(context, 16);
		inputLayout.addView(inputCardcardPwdLayout,params);
		
		TextView cardPwd = new TextView(context);
		cardPwd.setText("卡密：");
		cardPwd.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		cardPwd.setTextColor(0xff123521);
		inputCardcardPwdLayout.addView(cardPwd,-2,-2);
		
	    cardPwdEdit = new EditText(context);
		cardPwdEdit.setGravity(Gravity.CENTER_VERTICAL);
		cardPwdEdit.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				context, "card_input_bg.9.png"));
		cardPwdEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
		cardPwdEdit.setHint("请输入充值卡密");
		cardPwdEdit.setSingleLine(true);
		cardPwdEdit.setId(100);
		cardPwdEdit.setTextColor(0xff123521);
		cardPwdEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		cardPwdEdit.setHintTextColor(0x7758687a);
		cardPwdEdit
		.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
	/*	if(Session.getInstance() != null && Session.getInstance().userAccount != null && Session.getInstance().userAccount.length() >= YouaiAppService.min){
			cardNumEdit.setText(Session.getInstance().userAccount);
			CharSequence text = cardNumEdit.getText();
			if (text instanceof Spannable) {
			    Spannable spanText = (Spannable)text;
			    Selection.setSelection(spanText, text.length());
			}
		}*/
		inputCardcardPwdLayout.addView(cardPwdEdit,-1,-1);
		

		/*面额*/
		LinearLayout inputCardcardDenominationLayout = new LinearLayout(context);
		inputCardcardDenominationLayout.setOrientation(LinearLayout.HORIZONTAL);
		params = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(context, 35));
		params.topMargin = DimensionUtil.dip2px(context, 6);
		params.leftMargin = DimensionUtil.dip2px(context, 10);
		params.rightMargin = DimensionUtil.dip2px(context, 16);
		inputLayout.addView(inputCardcardDenominationLayout,params);
		
		TextView cardDenomination= new TextView(context);
		cardDenomination.setText("面额：");
		cardDenomination.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		cardDenomination.setTextColor(0xff123521);
		inputCardcardDenominationLayout.addView(cardDenomination,-2,-2);
		
	    cardDenominationEdit = new EditText(context);
		cardDenominationEdit.setOnClickListener(this);
		cardDenominationEdit.setGravity(Gravity.CENTER_VERTICAL);
		cardDenominationEdit.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				context, "card_input_bg.9.png"));
//		cardDenominationEdit.setHint("请输入充值卡密");
		cardDenominationEdit.setSingleLine(true);
		cardDenominationEdit.setFocusable(false);
		cardDenominationEdit.setId(Constants.ID_PAY_DENOMINATION);
		cardDenominationEdit.setTextColor(0xff123521);
		cardDenominationEdit.setText(ChargeData.getChargeData().money + "");
		cardDenominationEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//		cardDenominationEdit.setHintTextColor(0x7758687a);
		cardDenominationEdit
		.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20) });// 内容长度
	/*	if(Session.getInstance() != null && Session.getInstance().userAccount != null && Session.getInstance().userAccount.length() >= YouaiAppService.min){
			cardNumEdit.setText(Session.getInstance().userAccount);
			CharSequence text = cardNumEdit.getText();
			if (text instanceof Spannable) {
			    Spannable spanText = (Spannable)text;
			    Selection.setSelection(spanText, text.length());
			}
		}*/
		inputCardcardDenominationLayout.addView(cardDenominationEdit,-1,-1);
		// input_no.9.png operatorsLayout

		/*
		 * LinearLayout accountLayout = new LinearLayout(context);
		 * accountLayout.setOrientation(LinearLayout.HORIZONTAL); params = new
		 * LinearLayout.LayoutParams(-2, -2); params.topMargin =
		 * DimensionUtil.dip2px(context, 5); params.leftMargin =
		 * DimensionUtil.dip2px(context, 5);
		 * operatorsLayout.addView(accountLayout,params); TextView cardNumber =
		 * new TextView(context); cardNumber.setText("卡号:");
		 * cardNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
		 * cardNumber.setTextColor(0xff333333);
		 * accountLayout.addView(cardNumber, params);
		 */

	/*	LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
				"card_charge.9.png"));
		
		 * TextView amount = new TextView(context); amount.setText("对应" +
		 * Utils.getCardPayMoneyHint(null)); amount.setTextColor(0xff6a6a6a);
		 * amount.setTextSize(16);
		 * amount.setPadding(DimensionUtil.dip2px(context, 0),
		 * DimensionUtil.dip2px(context, 0), DimensionUtil.dip2px(context, 0),
		 * DimensionUtil.dip2px(context, 5)); subLayout.addView(amount);
		 

		EditText cardNumber = new EditText(context);
		cardNumber.setBackgroundDrawable(null);
		cardNumber.setPadding(DimensionUtil.dip2px(context, 10),
				DimensionUtil.dip2px(context, 8),
				DimensionUtil.dip2px(context, 10),
				DimensionUtil.dip2px(context, 8));
		cardNumber.setGravity(Gravity.CENTER_VERTICAL);
		cardNumber.setHint("请输入充值卡号");
		cardNumber.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
		cardNumber.setHintTextColor(0x66758ba6);// cardNumber.setHintTextColor(0xff758ba6);
		cardNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		layout.addView(cardNumber);

		EditText inputPsw = new EditText(context);
		inputPsw.setBackgroundDrawable(null);
		inputPsw.setPadding(DimensionUtil.dip2px(context, 10),
				DimensionUtil.dip2px(context, 8),
				DimensionUtil.dip2px(context, 10),
				DimensionUtil.dip2px(context, 8));
		inputPsw.setGravity(Gravity.CENTER_VERTICAL);
		inputPsw.setHint("请输入充值卡密");
		inputPsw.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
		inputPsw.setHintTextColor(0x66758ba6);
		inputPsw.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		layout.addView(inputPsw);
		params = new LinearLayout.LayoutParams(-1, -2);
		params.topMargin = DimensionUtil.dip2px(context, 0);
		params.leftMargin = DimensionUtil.dip2px(context, 13);
		params.rightMargin = DimensionUtil.dip2px(context, 13);
		operatorsLayout.addView(layout, params);*/

		LinearLayout submitLayout = new LinearLayout(context);
		submitLayout.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams(-1, DimensionUtil.dip2px(
				context, 60));
		params.topMargin = DimensionUtil.dip2px(context, 8);
		params.bottomMargin = DimensionUtil.dip2px(context, 8);
		operatorsLayout.addView(submitLayout, params);

		Button submitBtn = new Button(context);// yellow_btn.9.png
		submitBtn.setId(Constants.ID_PAY_CARD_PAYBTN);
		submitBtn.setOnClickListener(this);
		submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		submitBtn.setText("立即支付"); //
		submitBtn.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				context, "yellow_btn.9.png"));
		submitBtn.setBackgroundDrawable(BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "jk.png"));
		submitBtn.setBackgroundDrawable(Utils.getStateListDrawable(context,
                                                                           "pay_btn_on.png", "jk.png"));
		params = new LinearLayout.LayoutParams(
                    Utils.compatibleToWidth(context) * 5 / 6, -1);
		submitLayout.addView(submitBtn, params);
		/*
		 * Button mBtConfirm = new Button(context);
		 * mBtConfirm.setBackgroundDrawable
		 * (Utils.getStateListtNinePatchDrawable( context, "btn_blue.9.png",
		 * "btn_blue_down.9.png")); mBtConfirm.setPadding(0,
		 * DimensionUtil.dip2px(context, 7), 0, DimensionUtil.dip2px(context,
		 * 7)); mBtConfirm.setGravity(Gravity.CENTER);
		 * mBtConfirm.setText("立即充值"); mBtConfirm.setId(CardCharger.ID_YIBO);
		 * mBtConfirm.setTextColor(Color.WHITE);
		 * mBtConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
		 * mBtConfirm.setSingleLine(); params = new
		 * LinearLayout.LayoutParams(-2, -2);
		 * operatorsLayout.addView(mBtConfirm,params);
		 */

	}
 
	
}
