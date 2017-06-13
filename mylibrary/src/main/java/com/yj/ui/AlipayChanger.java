package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yj.entity.ChargeData;
import com.yj.entity.Flag;
import com.yj.entity.PayChannel;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月28日
 * @copyright 游鹏科技
 */
public class AlipayChanger extends ChargeRightAbstractLayout {

	public static LinearLayout layout;
	private Context mContext;
	private ChargeData charge;
//	private boolean isLockInput;
	public PayChannel channelMessage;
	private Button mBtConfirm;
	
//	protected static int ID_PAY;
	/** 支付宝充值 */
	public static final int ID_ALIPAY = 0x200014;
	public static final int ID_CAIFUTONG = 0x200015;
	
	public AlipayChanger(Activity context,
                             PayChannel channelMessage, ChargeData charge) {
		super(context,channelMessage,charge);
		
		this.mContext = context;
//		this.isLockInput = isLockInput;
		this.charge = charge;
		this.channelMessage = channelMessage;
//		setID_PAY(channelMessage.paymentType);
		initUI();
	}
	
/*	public void setID_PAY(int id) {
		switch (id) {
		// 支付宝
		case PaymentActivity.INDEX_CHARGE_ZHIFUBAO:
			ID_PAY = ID_ALIPAY;
			break;
		case PaymentActivity.INDEX_CHARGE_CAIFUTONG:
			ID_PAY = ID_CAIFUTONG;
		break;
		}
	}*/

	private void initUI()
	{
	    layout = new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mContext, "input_no.9.png"));
		
		amount.setText("对应" + Utils.getCardPayMoneyHint(null));
		amount.setTextColor(0xff6a6a6a);
		amount.setTextSize(16);
		amount.setPadding(DimensionUtil.dip2px(mContext, 0),
				DimensionUtil.dip2px(mContext, 0),
				DimensionUtil.dip2px(mContext, 0),
				DimensionUtil.dip2px(mContext, 5));
		subLayout.addView(amount);
		
		input = new EditText(mContext);
		input.setBackgroundDrawable(null);
		input.setPadding(DimensionUtil.dip2px(mContext, 10), DimensionUtil.dip2px(mContext, 8), DimensionUtil.dip2px(mContext, 10), DimensionUtil.dip2px(mContext, 8));
		input.setGravity(Gravity.CENTER_VERTICAL);
		input.setHint("请选择或输入充值金额");
		input.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
		input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(5)});
		input.setHintTextColor(0xff758ba6);
		input.setTextSize(16);
		input.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Utils.getEditTextFocus(ChargeRightAbstractLayout.input);
				CharSequence text = input.getText();
				if (text instanceof Spannable) {
				    Spannable spanText = (Spannable)text;
				    Selection.setSelection(spanText, text.length());
				}
				layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mContext, "input.9.png"));
                          Flag.flag = true;
                          Flag.position = 100001;
				setChannelMessages();
				return false;
			}
		});
		input.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				Log.i("feng", "onTextChanged");  
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                                      int after) {
//				Log.i("feng", "beforeTextChanged");
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				try {
					Utils.getFastPayMoneyHint(ChargeRightAbstractLayout.amount, Integer.valueOf(s.toString()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
			}
		});
		layout.addView(input,-1,-1);
		subLayout.addView(layout);
		
		/*if(charge.money <= 0 || charge.money > 9999 )
		{
			input.setText("10");
		}else
		{
			input.setText(10+"");
		}*/
	/*	if(isLockInput)
		{
			input.setText(charge.money+"");
			input.setFocusable(false);
		}*/
		
		mBtConfirm = new Button(mContext);
		mBtConfirm.setBackgroundDrawable(Utils.getStateListtNinePatchDrawable(mContext, "btn_blue.9.png", "btn_blue_down.9.png"));
//		mBtConfirm.setBackgroundDrawableDrawable(Utils.getGradientCornerListDrawable(mContext, 0xffe7e7e7, 0xff007aff, 7));
		mBtConfirm.setGravity(Gravity.CENTER);
		mBtConfirm.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0, DimensionUtil.dip2px(mContext, 7));
		mBtConfirm.setText("立即充值");
		mBtConfirm.setTextColor(Color.WHITE);
		mBtConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		mBtConfirm.setId(ID_ALIPAY);
		mBtConfirm.setSingleLine();
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mContext, 15);
		lp.bottomMargin = DimensionUtil.dip2px(mContext, 25);
		subLayout.addView(mBtConfirm, lp);
		
	}
	
	@Override
	public void setButtonClickListener(View.OnClickListener listener) {
		super.setButtonClickListener(listener);
		mBtConfirm.setOnClickListener(listener);
	}

	@Override
	public ChargeData getChargeEntity() {
		if("".equals(getInputMoney()) || null ==getInputMoney())
		{
			Utils.toastInfo(mContext, "请输入充值金额");
			return null;
		};
		if(Integer.valueOf(getInputMoney()) <= 0)
		{
			Utils.toastInfo(mContext, "充值金额不能为0");
			return null;
		}
//		charge.money = (int) Double.parseDouble(getInputMoney());
//		charge.paymentId = channelMessage.paymentId;
		return charge;
	}

	@Override
	public String getInputMoney() {
		return input.getText().toString().trim();
	}

	@Override
	public void setInput(String input) {
		this.input.setText(input);
	}
	
}
