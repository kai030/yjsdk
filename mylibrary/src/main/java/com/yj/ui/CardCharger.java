package com.yj.ui;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yj.entity.ChargeData;
import com.yj.entity.PayChannel;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * 充值卡
 * 
 * @author lufengkai
 * 
 */
public class CardCharger extends ChargeCardRightAbstractLayout{

	private Activity mContext;
	private Button mBtConfirm;
	private ChargeData mcharge;
//	private PayChannel channelMessage;
	public static final int ID_YIBO = 0x56875;
//	boolean isLockInput;

	public CardCharger(Activity context, PayChannel channelMessage, ChargeData mcharge) {
		super(context, channelMessage,mcharge);
		this.mContext = context;
//		this.isLockInput = isLockInput;
//		this.channelMessage = channelMessage;
		this.mcharge = mcharge;
		initUI();
	}

	private void initUI() {
		LinearLayout layout = new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mContext,
				"card_charge.9.png"));
		
		amount.setText("对应" + Utils.getCardPayMoneyHint(null));
		amount.setTextColor(0xff6a6a6a);
		amount.setTextSize(16);
		amount.setPadding(DimensionUtil.dip2px(mContext, 0),
				DimensionUtil.dip2px(mContext, 0),
				DimensionUtil.dip2px(mContext, 0),
				DimensionUtil.dip2px(mContext, 5));
		subLayout.addView(amount);

		cardNumber = new EditText(mContext);
		cardNumber.setBackgroundDrawable(null);
		cardNumber.setPadding(DimensionUtil.dip2px(mContext, 10),
				DimensionUtil.dip2px(mContext, 8),
				DimensionUtil.dip2px(mContext, 10),
				DimensionUtil.dip2px(mContext, 8));
		cardNumber.setGravity(Gravity.CENTER_VERTICAL);
		cardNumber.setHint("请输入充值卡号");
		cardNumber.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
		cardNumber.setHintTextColor(0xff758ba6);
		cardNumber.setTextSize(16);
		layout.addView(cardNumber);

		inputPsw = new EditText(mContext);
		inputPsw.setBackgroundDrawable(null);
		inputPsw.setPadding(DimensionUtil.dip2px(mContext, 10),
				DimensionUtil.dip2px(mContext, 8),
				DimensionUtil.dip2px(mContext, 10),
				DimensionUtil.dip2px(mContext, 8));
		inputPsw.setGravity(Gravity.CENTER_VERTICAL);
		inputPsw.setHint("请输入充值卡密");
		inputPsw.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
		inputPsw.setHintTextColor(0xff758ba6);
		inputPsw.setTextSize(16);
		layout.addView(inputPsw);

		subLayout.addView(layout);

		mBtConfirm = new Button(mContext);
		mBtConfirm.setBackgroundDrawable(Utils.getStateListtNinePatchDrawable(
				mContext, "btn_blue.9.png", "btn_blue_down.9.png"));
		mBtConfirm.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
				DimensionUtil.dip2px(mContext, 7));
		mBtConfirm.setGravity(Gravity.CENTER);
		mBtConfirm.setText("立即充值");
		mBtConfirm.setId(CardCharger.ID_YIBO);
		mBtConfirm.setTextColor(Color.WHITE);
		mBtConfirm.setTextSize(22);
		mBtConfirm.setSingleLine();
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mContext, 15);
		lp.bottomMargin = DimensionUtil.dip2px(mContext, 25);
		subLayout.addView(mBtConfirm, lp);
		
		input = new EditText(mContext);
		input.setText(mcharge.money + "");

	}

	public ChargeData getChargeEntity() {
		
		if(getInputMoney() == null || "".equals(getInputMoney()))
		{
			Utils.toastInfo(mContext, "请选择充值金额！");
			return null;
		}
		/* mcharge.money = (int) Double.parseDouble(getInputMoney());
		 mcharge.paymentId = channelMessage.paymentId;
		 mcharge.yeepayAccount = cardNumber.getText().toString();
	     mcharge.yeepayPwd = inputPsw.getText().toString();*/
		 return mcharge;
	}

	@Override
	public void setButtonClickListener(View.OnClickListener listener) {
		super.setButtonClickListener(listener);
		mBtConfirm.setOnClickListener(listener);
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