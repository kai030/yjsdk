package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yj.entity.ChargeData;
import com.yj.entity.Constants;
import com.yj.entity.Flag;
import com.yj.entity.PayChannel;
import com.yj.sdk.YouaiAppService;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

public abstract class ChargeRightAbstractLayout extends LinearLayout {
	
    public AutoScrollTextView marquee;
	protected static final int MAXAMOUNT = 10000;
	public NumGridView mPaymentNum,mPaymentNum1;
	private Activity mContext;
	protected LinearLayout subLayout;
	public static int money = 10;
	/** 充值说明 */
	public static final int CHARGE_EXPLAIN = 0x1517;
	private TextView changerExplain;
	/** 充值金额  */
	public static EditText input;
	public abstract void setInput(String input);
	
	/** 充值可选金额  */
	public String[] num;
	
	/** 充值类型信息 */
	private PayChannel channelMessage;
	/** 充值卡号 */
	protected EditText cardNumber;
	/** 充值密码 */
	protected EditText inputPsw;
	/** 是否锁定充值  */
//	boolean isLockInput;
	/** charge信息  */
	ChargeData charge;
	// 充值金额
	public static int isClickPice;
	/** 是否首次进入  */
	private boolean isfrist = false;
	
	public static TextView amount;
	
	public abstract ChargeData getChargeEntity();
	public ChargeRightAbstractLayout(Activity context){
		super(context);
	}
	public ChargeRightAbstractLayout(Activity context, PayChannel channelMessage, ChargeData charge) {
		super(context);
		this.amount = new TextView(context);
		this.charge = charge;
//		this.isLockInput = isLockInput;
		this.channelMessage = channelMessage;
		this.mContext = context;
		num = YouaiAppService.mPayChannelsFast.get(0).selectMoney.split(",");
		init();
	}

	private void init() {
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundColor(0xfffafafa);
		this.setClickable(false);
		this.setFocusable(false);

		/* 设置滚动 */
		LinearLayout content = new LinearLayout(mContext);
		content.setOrientation(LinearLayout.VERTICAL);

		LayoutParams lp = new LayoutParams(-1, -1);
		lp.leftMargin = DimensionUtil.dip2px(mContext, 20);
		lp.rightMargin = DimensionUtil.dip2px(mContext, 20);
		lp.topMargin = DimensionUtil.dip2px(mContext, 10);
//		lp.bottomMargin = DimensionUtil.dip2px(mContext, 10);
		ScrollView scroll = new ScrollView(mContext);
		scroll.addView(content, lp);
//		lp = new LinearLayout.LayoutParams(-1, -1);
		this.addView(scroll, lp);


		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);

		String userName = Constants.COMPANY_NAME + "帐号：" + YouaiAppService.mSession.userAccount;
		if(userName !=null  && metrics.widthPixels == 320 && YouaiAppService.mSession.userAccount.length() > 8)
		{
			userName =Constants.COMPANY_NAME+"帐号："+userName.substring(0, 2) +"..."+ userName.substring(userName.length()-3, userName.length()-1);
		};

		TextView user = new TextView(mContext);
		user.setTextColor(0xff323232);
		user.setTextSize(16);
		user.setText(userName);
		lp = new LayoutParams(-2, -2);
		lp.leftMargin = DimensionUtil.dip2px(mContext, 5);
		content.addView(user,-2,-2);

		LinearLayout UserName = new LinearLayout(mContext);

	    lp = new LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mContext, 7);
		lp.bottomMargin = DimensionUtil.dip2px(mContext, 10);

		/*温馨提示*/
		LinearLayout marqueeLyout = new LinearLayout(mContext);
		marqueeLyout.setOrientation(LinearLayout.HORIZONTAL);
		marqueeLyout.setBackgroundColor(0xffe6e6e6);
		marqueeLyout.setGravity(Gravity.CENTER_VERTICAL);


		TextView text = new TextView(mContext);
		text.setTextSize(16);
		text.setTextColor(0xff2f4051);
		text.setText("温馨提示：");
		text.setGravity(Gravity.LEFT);
		lp = new LayoutParams(-2, -2);
		lp.leftMargin = DimensionUtil.dip2px(mContext, 5);
		marqueeLyout.addView(text, lp);

		lp = new LayoutParams(-1, DimensionUtil.dip2px(mContext,
                                                                            25));
		lp.topMargin = DimensionUtil.dip2px(mContext, 7);
		 lp.bottomMargin = DimensionUtil.dip2px(mContext, 3);
		content.addView(marqueeLyout, lp);
		//自定义滚动条
		 marquee = new AutoScrollTextView(mContext);
		if(Flag.descr != null){
//			marquee.setText(channelMessage.notice);
			marquee.setText(Flag.notice);
		}else{
			marquee.setText("");
		}

		marquee.setSpeed(1.0f);
		marquee.setTextColor(0xff2f4051);
		marquee.init(-1);// width通常就是屏幕宽！
		marquee.startScroll();
		marquee.setTextSize(16);
		marquee.setTextColor(0xff2f4051);

		lp = new LayoutParams(-2, -2);
		lp.gravity = Gravity.CENTER_VERTICAL;
		lp.topMargin = DimensionUtil.dip2px(mContext, 3);
		lp.leftMargin = DimensionUtil.dip2px(mContext, 5);
		lp.rightMargin = DimensionUtil.dip2px(mContext, 5);
		lp.bottomMargin = DimensionUtil.dip2px(mContext, 3);
		marqueeLyout.addView(marquee, lp);

		TextView selectPay = new TextView(mContext);
		selectPay.setTextColor(0xff6a6a6a);
		selectPay.setTextSize(16);
		selectPay.setText("请选择支付方式：");

		mPaymentNum1 = new NumGridView(mContext);
		mPaymentNum1.setHorizontalSpacing(DimensionUtil.dip2px(mContext, 15));
		mPaymentNum1.setVerticalSpacing(DimensionUtil.dip2px(mContext, 8));
		mPaymentNum1.setNumColumns(3);
		mPaymentNum1.setSelector(android.R.color.transparent);
		mPaymentNum1.setAdapter(new PaymentListAdapterS(YouaiAppService.mPayChannelsFast, mContext));

		mPaymentNum1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                          Flag.flag = false;
                          Flag.positionFast = position;
                          Flag.payTypeFlag = YouaiAppService.mPayChannelsFast.get(position).paymentId;//支付类型id
			for (int i = 0; i < parent.getCount(); i++) {
					PaymentListAdapterS.ChagerLayout layout = (PaymentListAdapterS.ChagerLayout) parent.getChildAt(i);
					if (position == i) {// 当前选中的Item改变背景颜色
						layout.setBackgroundDrawable(BitmapCache
								.getNinePatchDrawable(mContext,
										"grid_click_it.9.png"));
						layout.setPadding(0,DimensionUtil.dip2px(mContext, 7),0,DimensionUtil.dip2px(mContext, 7));
//						layout.yuan.setTextColor(0xffffffff);
						layout.textView.setTextColor(0xffffffff);
						/*rightLayout.setInput(layout.textView.getText()
								.toString());*/
					} else {
//						layout.yuan.setTextColor(0xff758ba6);
						layout.textView.setTextColor(0xff758ba6);
						layout.setBackgroundDrawable(BitmapCache
								.getNinePatchDrawable(mContext,
										"grid_click.9.png"));
						layout.setPadding(0,DimensionUtil.dip2px(mContext, 7),0,DimensionUtil.dip2px(mContext, 7));
					}
				}
			        num = YouaiAppService.mPayChannelsFast.get(position).selectMoney.split(",");
			        int paymentId =  YouaiAppService.mPayChannelsFast.get(position).paymentId;
                          Flag.payTypeFlag = paymentId;
                          Flag.payTypeFlagAlipay = paymentId;
					Utils.setDescr(paymentId);
					marquee.setText(Flag.notice);
					marquee.init(-1);// width通常就是屏幕宽！
//					Utils.getCardPayMoneyHint(amount);
					setChannelMessages();

			}
		});

		lp = new LayoutParams(-2, -2);
		lp.gravity = Gravity.CENTER_VERTICAL;
//		lp.topMargin = DimensionUtil.dip2px(mContext, 3);
//		lp.leftMargin = DimensionUtil.dip2px(mContext, 5);
		lp.rightMargin = DimensionUtil.dip2px(mContext, 5);
		lp.bottomMargin = DimensionUtil.dip2px(mContext, 3);
		content.addView(selectPay,lp);
		content.addView(mPaymentNum1,lp);
		/*充值金额*/
		lp = new LayoutParams(-1, -2);
		content.addView(UserName,lp);

		TextView tishi = new TextView(mContext);
		tishi.setTextColor(0xff6a6a6a);
		tishi.setTextSize(16);
		tishi.setText("请选择充值金额：");
		UserName.addView(tishi);

		LinearLayout baseLayout = new LinearLayout(mContext);
		baseLayout.setOrientation(LinearLayout.HORIZONTAL);
		baseLayout.setGravity(Gravity.RIGHT);
		LayoutParams params = new LayoutParams(-1, -1);
		params.leftMargin = DimensionUtil.dip2px(mContext, 8);
		UserName.addView(baseLayout, params);

		changerExplain = new TextView(mContext);
		changerExplain.setId(CHARGE_EXPLAIN);
		changerExplain.setTextColor(0xff6a6a6a);
		changerExplain .getPaint().setAntiAlias(true);
		changerExplain.setText(Html.fromHtml("<u>充值说明?</u>"));
		changerExplain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Flag.descr != null){
					DialogHelper.showPaySMDialog(mContext, Flag.descr);
				}else{
					DialogHelper.showPaySMDialog(mContext, "");
				}

			}
		});
		changerExplain.setTextSize(16);
		params = new LayoutParams(-2, -2);
		baseLayout.addView(changerExplain,params);


		mPaymentNum = new NumGridView(mContext);
		mPaymentNum.setHorizontalSpacing(DimensionUtil.dip2px(mContext, 15));
		mPaymentNum.setVerticalSpacing(DimensionUtil.dip2px(mContext, 8));
		mPaymentNum.setNumColumns(4);
		mPaymentNum.setSelector(android.R.color.transparent);
		num = channelMessage.selectMoney.split(",");
		setChannelMessages();
		lp = new LayoutParams(-1, -2);
//		lp.leftMargin = DimensionUtil.dip2px(mContext, 20);
//		lp.rightMargin = DimensionUtil.dip2px(mContext, 20);
		lp.topMargin = DimensionUtil.dip2px(mContext,5);
		lp.bottomMargin = DimensionUtil.dip2px(mContext,3);
		lp.gravity = Gravity.CENTER_HORIZONTAL;
		content.addView(mPaymentNum, lp);

		subLayout = new LinearLayout(mContext);
		subLayout.setOrientation(LinearLayout.VERTICAL);
		lp = new LayoutParams(-1, -2);
//		lp.leftMargin = DimensionUtil.dip2px(mContext, 20);
//		lp.rightMargin = DimensionUtil.dip2px(mContext, 20);
		content.addView(subLayout,lp);
	}

	public void setChannelMessages() {
		mPaymentNum.setAdapter(new PaymentListAdapter(num));
	}

	public void setOnItemClickListener(
			OnItemClickListener onItemClickListener) {
		mPaymentNum.setOnItemClickListener(onItemClickListener);
	}

	class NumGridView extends GridView {

		public NumGridView(Context context) {
			super(context);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			int expandSpec = MeasureSpec.makeMeasureSpec(
                            Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);

		}
	}

	class PaymentListAdapter extends BaseAdapter {

		private String[] channelMessages;
		private ChagerLayout chagerLayout;

		public PaymentListAdapter(String[] num) {
			this.channelMessages = num;
		}

		@Override
		public int getCount() {
			return channelMessages.length;
		}

		@Override
		public Object getItem(int position) {
			return channelMessages[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			chagerLayout = (ChagerLayout) convertView;
			if (chagerLayout == null) {
				chagerLayout = new ChagerLayout(mContext);
			}

			if(Flag.position == position && !Flag.flag){
				chagerLayout.setBackgroundDrawable(
                                    BitmapCache.getNinePatchDrawable(mContext, "grid_click_it.9.png"));
				chagerLayout.setPadding(0,DimensionUtil.dip2px(mContext, 7),0,DimensionUtil.dip2px(mContext, 7));
				chagerLayout.yuan.setTextColor(0xffffffff);
				chagerLayout.textView.setTextColor(0xffffffff);
				}

			chagerLayout.textView.setText(channelMessages[position]);
			if(0== position && !isfrist && Flag.position == 0 && !Flag.flag)
			{
				isfrist = true;
				chagerLayout.setBackgroundDrawable(
                                    BitmapCache.getNinePatchDrawable(mContext, "grid_click_it.9.png"));
				chagerLayout.setPadding(0,DimensionUtil.dip2px(mContext, 7),0,DimensionUtil.dip2px(mContext, 7));
				chagerLayout.yuan.setTextColor(0xffffffff);
				chagerLayout.textView.setTextColor(0xffffffff);
			}



			return chagerLayout;

		}
	}

	public class ChagerLayout extends LinearLayout {
		public TextView textView;
		public TextView yuan;

		public ChagerLayout(Context context) {
			super(context);
			initUI(context);
		}

		public void initUI(Context context) {
			this.setOrientation(LinearLayout.HORIZONTAL);
			this.setGravity(Gravity.CENTER_HORIZONTAL);
			this.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mContext, "grid_click.9.png"));
			this.setPadding(0,DimensionUtil.dip2px(mContext, 7),0,DimensionUtil.dip2px(mContext, 7));

			textView = new TextView(context);
			textView.setTextSize(15);
//			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(0xff758ba6);
			LayoutParams lp = new LayoutParams(-2, -1);
			lp.gravity = Gravity.CENTER;
			lp.topMargin = 2;
			addView(textView, lp);
			
			yuan = new TextView(context);
			yuan.setTextSize(18);
			yuan.setGravity(Gravity.CENTER);
			yuan.setTextColor(0xff758ba6);
			yuan.setText("元");
//			yuan.setText("");
			addView(yuan);
		}
	}

	public void setButtonClickListener(OnClickListener listener) {
	}

	public boolean checkMoney() {
		if(getInputMoney() == null || "".equals(getInputMoney()))
		{
			Utils.toastInfo(mContext, "请选择充值金额！");
			return false;
		}
		if("".equals(getCardNumber())|| null == getCardNumber() )
		{
			Utils.toastInfo(mContext, "请输入充值卡号！");
			return false;
		}
		if("".equals(getInputPsw() )|| null == getInputPsw())
		{
			Utils.toastInfo(mContext, "请输入充值卡密！");
			return false;
		}
		
		return Utils.formatMoney(getInputMoney())
                       && Double.parseDouble(getInputMoney()) < MAXAMOUNT;
	}
	
	public abstract String getInputMoney();
	
	/** 获取充值卡号 */
	public String getCardNumber() {
		return cardNumber.getText().toString();
	}
	
	/** 获取充值卡密码 */
	public String getInputPsw() {
		return inputPsw.getText().toString();
	}
	/** 支付过滤  */
	public boolean checkNum() {
		// 检测金额是否正确
		return input.getText().toString() != null && !"".equals(input.getText().toString());

	}

	
}