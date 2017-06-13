package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.entity.ChargeData;
import com.yj.entity.Constants;
import com.yj.entity.Flag;
import com.yj.entity.PayChannel;
import com.yj.sdk.YouaiAppService;
import com.yj.ui.ChargeRightAbstractLayout.ChagerLayout;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.Utils;

/**
 * 
 * @author lufengkai
 *
 */
public class CharhePaymentListLayoutVertical extends ChargeAbstractLayout
    implements OnClickListener {

	private ViewPager viewPager;
	// 所有title标题
	private ChagerListLayout[] title;
	public static final int payChannelCount = 2;
	private PayChannel[] payChannel;
	// 所有支付页面
	private ChargeRightAbstractLayout[] views;
	// title页面选中标记
	private int type;
	// 无渠道显示信息
	private TextView mErr;
	private ChargeData mCharge;

	private OnClickListener yibaoOnClickListener;
	private OnClickListener zhifubaoOnClickListener;

	public CardCharger cardCharger;
	public AlipayChanger alipayChanger;
	// 是否锁定充值
//	private boolean isLockInput;
	
	// 当前显示页面
	public ChargeRightAbstractLayout rightLayout;
	// 当前支付方式
	public PayChannel mPayChannel;

	public CharhePaymentListLayoutVertical(Activity activity,
                                               PayChannel[] payChannel, ChargeData charge) {
		super(activity);
		this.payChannel = payChannel;
		this.mCharge = charge;
		
		if(payChannel == null || payChannel.length <= 0 || "".equals(payChannel))
		{
			Utils.toastInfo(activity, "获取充值界面失败！");
			return;
		}
		title = new ChagerListLayout[payChannelCount];
		this.mActivity = activity;
		initUI(activity);//
	}

	@Override
	protected void initUI(Activity activity) {
		super.initUI(activity);
			LinearLayout layout = new LinearLayout(activity);
			layout.setOrientation(LinearLayout.VERTICAL);
			mContent.addView(layout);

			// -------------------初始化页面-------------------------------------
			viewPager = new ViewPager(activity);
			views = new ChargeRightAbstractLayout[payChannelCount];
			for (int i = 0; i < views.length; i++) {
				switch (payChannel[i].paymentId) {
				case 1:
					// 支付宝
					alipayChanger = new AlipayChanger(activity, payChannel[i],
							mCharge);
					alipayChanger.setOnItemClickListener(onItemClickListener);
					// 初始化内容
					views[i] = alipayChanger;
					mPayChannel = payChannel[i];
					
					break;
				case 2:
				case 3:
					// 卡类
//					Flag.fastOrCard = 2;
					cardCharger = new CardCharger(activity, payChannel[i],
							mCharge);
//					cardCharger.setOnItemClickListener(onItemClickListener);
					views[i] = cardCharger;
					mPayChannel = payChannel[i];
					break;
				default:
					break;
				}
				views[i].setButtonClickListener(this);
			}
			// -------------------title---------------------------------
			HorizontalScrollView scroll = new HorizontalScrollView(mActivity);
			scroll.setHorizontalScrollBarEnabled(false);
			scroll.setBackgroundColor(0xffe6e6e6);
			layout.addView(scroll, -1, -1);

			LinearLayout layout2 = new LinearLayout(activity);
			layout2.setOrientation(LinearLayout.HORIZONTAL);
			layout2.setBackgroundColor(0xffdadddd);
			for (int i = 0; i < 2; i++) { 
				title[i] = new ChagerListLayout(activity);
				title[i].setBackgroundDrawable(BitmapCache
						.getNinePatchDrawable(mActivity, "list_bg.9.png"));
				if (i == 0) {
					title[i].setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(mActivity,
									"pro_list_btn.9.png"));
					title[i].textView.setTextColor(0xfff46d10);
					rightLayout = views[i];
				}
				title[i].setId(i);
				title[i].setOnClickListener(listClickListener);
//				title[i].textView.setText(payChannel[i].paymentName);
				if(i == 0){
					title[i].textView.setText("快捷支付");
				}else if(i == 1){
					title[1].textView.setText("充值卡支付");
				}
				
//				
				layout2.addView(title[i], -2, -2);
			}
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.leftMargin = DimensionUtil.dip2px(mActivity, 10);
			scroll.addView(layout2, lp);

			// -------------------------------------------------------

			/** ViewPager的数据适配器 */
			PagerAdapter pagerAdapter = new PagerAdapter() {
				@Override
				public boolean isViewFromObject(View arg0, Object arg1) {
					return arg0.equals(arg1);
				}

				/** 选项的总数量 */
				@Override
				public int getCount() {
					return payChannelCount;
				}

				/** 初始化分页组件中一个选项 */
				@Override
				public Object instantiateItem(ViewGroup container, int position) {
					container.addView(views[position]);
					return views[position];
				}

				/** 原始的选项 */
				@Override
				public void setPrimaryItem(ViewGroup container, int position,
                                                           Object object) {
					// 加载服务器端的数据

				}

				/** 消毁分页视图组件中一个选项 */
				@Override
				public void destroyItem(ViewGroup container, int position,
                                                        Object object) {
					container.removeView(views[position]);
				}
			};
			viewPager.setAdapter(pagerAdapter);

			layout.addView(viewPager, -1, -2);

			viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				public void onPageSelected(int index) {
					if(index == 0){
//						Flag.fastOrCard = 1;
						Utils.setDescr(YouaiAppService.mPayChannelsFast.get(Flag.positionFast).paymentId);
                                          Flag.payTypeFlag = Flag.payTypeFlagAlipay;
					}else if(index == 1){
//						Flag.fastOrCard = 2;
                                          Flag.payTypeFlag = Flag.payTypeFlagCard;
							if(!Flag.isFirstCard){
                                                          Flag.payTypeFlag = 3;
                                                          Flag.isFirstCard = true;
								Utils.setDescr(YouaiAppService.mPayChannelsCard.get(0).paymentId);
								ChargeCardRightAbstractLayout.marquee.setText(Flag.notice);
								ChargeCardRightAbstractLayout.marquee.init(-1);// width通常就是屏幕宽！
							}else{
								Utils.setDescr(YouaiAppService.mPayChannelsCard.get(Flag.positionCard).paymentId);
							}
					}
					
					title[type].setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(mActivity, "list_bg.9.png"));
					title[type].textView.setTextColor(0xff384359);
					title[index].setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(mActivity,
									"pro_list_btn.9.png"));
					title[index].textView.setTextColor(0xfff46d10);
					type = index;
					rightLayout = views[index];
					mPayChannel = payChannel[index];
				}

				public void onPageScrolled(int arg0, float arg1, int arg2) {

				}

				public void onPageScrollStateChanged(int arg0) {

				}
			});
	}

	public void showPayList(int visibility) {
		switch (visibility) {
		case View.GONE:
			mSubject.setVisibility(View.GONE);
			mErr.setVisibility(View.VISIBLE);
			break;
		case View.VISIBLE:
			mSubject.setVisibility(View.VISIBLE);
			mErr.setVisibility(View.GONE);
			break;
		}

	}

	@Override
	public void setButtonClickListener(OnClickListener listener) {
		super.setButtonClickListener(listener);
	}

	@Override
	public ChargeData getChargeEntity() {
		return null;
	}

	public class ChagerListLayout extends LinearLayout {
//		ImageView imageView;
		public TextView textView;

		public ChagerListLayout(Context context) {
			super(context);
			initUI(context);
		}

		public void initUI(Context context) {
			this.setClickable(false);
			this.setOrientation(LinearLayout.HORIZONTAL);
			// this.setBackgroundDrawableDrawable(BitmapCache.getNinePatchDrawable(
			// mActivity, "login_bg.9.png"));
			textView = new TextView(mActivity);
			textView.setPadding(DimensionUtil.dip2px(mActivity, 15),
					DimensionUtil.dip2px(mActivity, 10),
					DimensionUtil.dip2px(mActivity, 15),
					DimensionUtil.dip2px(mActivity, 10));
			textView.setTextSize(18);
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
			textView.setTextColor(0xff4c4c4c);
			addView(textView);
		}

	}

	// 将所有的数字、字母及标点全部转为全角字符
	public String ToDBC(String input) {
		if (null == input)
			return null;
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public void setYibaoOnClickListener(OnClickListener yibaoOnClickListener) {
		this.yibaoOnClickListener = yibaoOnClickListener;
	}

	public void setZhifubaoOnClickListener(
			OnClickListener zhifubaoOnClickListener) {
		this.zhifubaoOnClickListener = zhifubaoOnClickListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case AlipayChanger.ID_ALIPAY:
			if (zhifubaoOnClickListener != null) {
				zhifubaoOnClickListener.onClick(v);
			}
			break;
		case CardCharger.ID_YIBO:
			if (yibaoOnClickListener != null) {
				yibaoOnClickListener.onClick(v);
			}
			break;
		case Constants.ID_BACK:
			this.mActivity.finish();
			break;
		}
	}
	
	private OnClickListener listClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// isClickPice = 0;
			title[type].setBackgroundDrawable(BitmapCache
					.getNinePatchDrawable(mActivity,
							"list_bg.9.png"));
			title[type].textView.setTextColor(0xff384359);
			title[v.getId()].setBackgroundDrawable(BitmapCache
					.getNinePatchDrawable(mActivity,
							"pro_list_btn.9.png"));
			title[v.getId()].textView.setTextColor(0xfff46d10);
			type = v.getId();
			viewPager.setCurrentItem(v.getId());
		}
	};

	// 网格
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adpater, View view,
                                        int position, long arg3) {
			// 如果禁止输入金额，直接禁止金额选中事件
//			if (!isLockInput) {
                  Flag.position = position;
				for (int i = 0; i < adpater.getCount(); i++) {
					if (rightLayout == null) {
						rightLayout = views[0];
						mPayChannel = payChannel[0];
					}
					ChagerLayout layout = (ChagerLayout) adpater.getChildAt(i);
					if (position == i) {// 当前选中的Item改变背景颜色
						layout.setBackgroundDrawable(BitmapCache
								.getNinePatchDrawable(mActivity,
										"grid_click_it.9.png"));
						layout.setPadding(0,DimensionUtil.dip2px(mActivity, 7),0,DimensionUtil.dip2px(mActivity, 7));
						layout.yuan.setTextColor(0xffffffff);
						layout.textView.setTextColor(0xffffffff);
						rightLayout.setInput(layout.textView.getText()
								.toString());
						
						AlipayChanger.layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mActivity, "input_no.9.png"));
						Utils.clearEditTextFocus(ChargeRightAbstractLayout.input);
					} else {
						layout.yuan.setTextColor(0xff758ba6);
						layout.textView.setTextColor(0xff758ba6);
						layout.setBackgroundDrawable(BitmapCache
								.getNinePatchDrawable(mActivity,
										"grid_click.9.png"));
						layout.setPadding(0,DimensionUtil.dip2px(mActivity, 7),0,DimensionUtil.dip2px(mActivity, 7));
					}
					
				/*	layout.setPadding(DimensionUtil.dip2px(mActivity, 10),
							DimensionUtil.dip2px(mActivity, 7),
							DimensionUtil.dip2px(mActivity, 10),
							DimensionUtil.dip2px(mActivity, 7));*/
				}
				Utils.getFastPayMoneyHint(ChargeRightAbstractLayout.amount,Utils.getFastMoney());
//			}
		}
	};
}
