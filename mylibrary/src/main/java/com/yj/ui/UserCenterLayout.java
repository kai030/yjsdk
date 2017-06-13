package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yj.dao.UserCenterBean;
import com.yj.entity.Constants;
import com.yj.entity.Session;
import com.yj.util.BitmapCache;
import com.yj.util.DimensionUtil;
import com.yj.util.SharedPreferencesUtil;
import com.yj.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class UserCenterLayout extends AbstractLayoutTow implements OnClickListener, OnItemClickListener {

	private Context mContext;
	private NumGridView item;
	private List<UserCenterBean> listUserCenterBean;
	private OnClickListener clickListener;
	private OnItemClickListener OnItemclick;

	public UserCenterLayout(final Activity context) {
		super(context);
		this.mContext = context;


		/**
		 * 顶部布局
		 */
		RelativeLayout title = new RelativeLayout(mContext);
		
		title.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mContext,
                                                                             "title.9.png"));
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(-1,
                                                                                  -2);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		title.setPadding(0, DimensionUtil.dip2px(mContext, 7), 0,
                                 DimensionUtil.dip2px(mContext, 7));
		rlp.leftMargin = 100;
		LinearLayout.LayoutParams layout12 = new LinearLayout.LayoutParams(-1, -2);
		layout12.leftMargin = 2;
		layout12.rightMargin = 2;
		layout12.topMargin = 2;
		content.addView(title, layout12);

		LinearLayout baseLayout = new LinearLayout(mContext);
		baseLayout.setOrientation(LinearLayout.VERTICAL);
		baseLayout.setGravity(Gravity.CENTER_VERTICAL);
		rlp = new RelativeLayout.LayoutParams(-2, -2);
		rlp.addRule(RelativeLayout.ALIGN_RIGHT);
		rlp.rightMargin = DimensionUtil.dip2px(mContext, 10);
		rlp.alignWithParent = true;
		title.addView(baseLayout, rlp);
		/* 返回--关闭 */
		ImageView back = new ImageView(mContext);
		back.setImageDrawable(BitmapCache.getDrawable(mContext,
				Constants.ASSETS_RES_PATH + "close.png"));
		baseLayout.setId(Constants.ID_BACK);
		baseLayout.setOnClickListener(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
		lp.bottomMargin = DimensionUtil.dip2px(mContext, 5);
		lp.leftMargin = DimensionUtil.dip2px(mContext, 10);
		lp.topMargin = DimensionUtil.dip2px(mContext, 5);
		lp.rightMargin = DimensionUtil.dip2px(mContext, 10);
		baseLayout.addView(back, lp);
		/* 标题 */
		TextView textView = new TextView(mContext);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		rlp = new RelativeLayout.LayoutParams(-1, -2);
		textView.setText("个人中心");
		textView.setTextColor(0xff222222);
		textView.setTextSize(22);
		title.addView(textView, rlp);

		// 5399账号
		LinearLayout wrap1 = new LinearLayout(mContext);
		/*wrap1.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mContext,
				"titil_user.9.png"));*/
	/*	wrap1.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mContext,
				"no_title_bg.9.png"));*/
		wrap1.setOrientation(LinearLayout.HORIZONTAL);
		wrap1.setGravity(Gravity.CENTER_VERTICAL);

		LinearLayout.LayoutParams lpid = new LinearLayout.LayoutParams(-1,
                                                                               DimensionUtil.dip2px(mContext, 35));
		lpid.gravity = Gravity.CENTER_HORIZONTAL;
		lpid.topMargin = DimensionUtil.dip2px(context, 8);
		content.addView(wrap1, lpid);

		ImageView userLogo = new ImageView(mContext);
		userLogo.setBackgroundDrawable(BitmapCache.getDrawable(mContext,
				Constants.ASSETS_RES_PATH + "user_logo.png"));
		LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(-2, -2);
		lp3.setMargins(Constants.BORDER_MARGIN + DimensionUtil.dip2px(mContext, 25), 0,
                               DimensionUtil.dip2px(mContext, 10), 0);
		wrap1.addView(userLogo, lp3);

		TextView mRegistUserId = new TextView(mContext);
		mRegistUserId.setSingleLine(true);
		mRegistUserId.setBackgroundDrawable(null);
		mRegistUserId.setText(Html.fromHtml(Constants.COMPANY_NAME + "帐号: <font color = '#000000'> "
                                                    + Session.getInstance().userAccount + "</font>"));
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
		lp4.topMargin = DimensionUtil.dip2px(mContext, 8);
		lp4.bottomMargin = DimensionUtil.dip2px(mContext, 18);
//		wrap1.addView(topLine,lp4);
		content.addView(topLine, lp4);

		initList();

		LinearLayout mSubject = new LinearLayout(mContext);
		/*mSubject.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(mContext,
				"no_title_bg.9.png"));*/
		mSubject.setOrientation(LinearLayout.VERTICAL);
		content.addView(mSubject, -1, -1);

		ScrollView scroll = new ScrollView(mContext);
		lp = new LinearLayout.LayoutParams(-1, -2);
		lp.topMargin = DimensionUtil.dip2px(mContext, 15);
		lp.gravity = Gravity.CENTER;
		mSubject.addView(scroll, lp);

		item = new NumGridView(mContext);
		item.setHorizontalSpacing(DimensionUtil.dip2px(mContext, 10));
		item.setVerticalSpacing(DimensionUtil.dip2px(mContext, 8));
		item.setNumColumns(3);
		item.setBackgroundDrawable(null);
		item.setSelector(android.R.color.transparent);
		lp = new LinearLayout.LayoutParams(-1, -1);
		lp.topMargin = DimensionUtil.dip2px(mContext, 10);
		lp.leftMargin = DimensionUtil.dip2px(mContext, 10);
		lp.rightMargin = DimensionUtil.dip2px(mContext, 10);
		scroll.addView(item, lp);

		item.setAdapter(new UserListAdapter(context, listUserCenterBean));
		item.setOnItemClickListener(this);
		
		LinearLayout automaticLogin = new LinearLayout(context);
		automaticLogin.setOrientation(LinearLayout.HORIZONTAL);
		automaticLogin.setGravity(Gravity.CENTER_VERTICAL);
		TextView automaticLoginTxt = new TextView(context);
		automaticLoginTxt.setText("自动登录");
		automaticLoginTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
		automaticLoginTxt.setTextColor(0xff222222);
		automaticLogin.addView(automaticLoginTxt, -2,-2);
		lp = new LinearLayout.LayoutParams(-1, -2);
		lp.leftMargin = DimensionUtil.px2dip(context, 30);
		lp.topMargin = DimensionUtil.px2dip(context, 25);
		
		final ImageView autoIcon = new ImageView(context);

		String autolLogin = SharedPreferencesUtil.getXmlAutoLogin(context);
		
		if (Constants.YES.equals(autolLogin)) {// 假如标志位为yes则自动登录
			autoIcon.setBackgroundDrawable(BitmapCache.getDrawable(context,
					Constants.ASSETS_RES_PATH + "auto_login_on.png"));
			/*LoginTask.autoLoginFlag = true;
			btnStartGame.performClick();*/
		}else{
			autoIcon.setBackgroundDrawable(BitmapCache.getDrawable(context,
					Constants.ASSETS_RES_PATH + "auto_login_off.png"));
		}
		
		autoIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(SharedPreferencesUtil.getXmlAutoLogin(context).equals(Constants.YES)){
					autoIcon.setBackgroundDrawable(BitmapCache.getDrawable(context,
							Constants.ASSETS_RES_PATH + "auto_login_off.png"));
					SharedPreferencesUtil.saveAutoLoginToXml(context, Constants.NO);
				}else{
					autoIcon.setBackgroundDrawable(BitmapCache.getDrawable(context,
							Constants.ASSETS_RES_PATH + "auto_login_on.png"));
					SharedPreferencesUtil.saveAutoLoginToXml(context, Constants.YES);
				}
			}
		});
		Drawable icon = BitmapCache.getDrawable(context,
				Constants.ASSETS_RES_PATH + "auto_login_on.png");
//		mSubject.addView(automaticLogin,lp);
		int width = icon.getIntrinsicWidth();
		int height = icon.getIntrinsicHeight();
		lp = new LinearLayout.LayoutParams(width * 17 / 10, height * 17 / 10);
		lp.leftMargin = DimensionUtil.px2dip(context, 10);
//		lp.topMargin = DimensionUtil.px2dip(context, 10);
		automaticLogin.addView(autoIcon,lp);
		
		
		
		TextView textViewLast = new TextView(mContext);
		textViewLast.setVisibility(View.INVISIBLE);
		mSubject.addView(textViewLast, -1, DimensionUtil.dip2px(mContext, 100));

	}
	
	/**
	 * 点击事件
	 * @param clickListener
	 */
	public void setOnclick(OnClickListener clickListener){
		this.clickListener = clickListener;
	}
	
	/**
	 * item点击事件
	 */
	public void setOnItemclick(OnItemClickListener OnItemclick){
		this.OnItemclick = OnItemclick;
	}

	private void initList() {
		if (listUserCenterBean == null)
			listUserCenterBean = new ArrayList<UserCenterBean>();

		String[] name = {"修改密码", "密保管理", "联系客服" };
		String[] icon = {"key.png", "mibao.png", "contact.png" };

		for (int i = 0; i < icon.length; i++) {
			UserCenterBean bean = new UserCenterBean();
			bean.setIconPath(icon[i]);
			bean.setName(name[i]);
			listUserCenterBean.add(bean);
		}

	}


	@Override
	public void onClick(View v) {
          switch (v.getId()) {
		case Constants.ID_BACK:
			if(this.clickListener != null){
				this.clickListener.onClick(v);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
		switch (position) {
		case Constants.ALTER_PASS_ITEM:
        case Constants.SECRET_ITEM:
        case Constants.SERVICE_ITEM:
     	   if(this.OnItemclick != null){
     		   this.OnItemclick.onItemClick(parent, view, position, id);
     	   }
 	      break;
        
		default:
			break;
		}
	}

	@Override
	public void setButtonClickListener(OnClickListener listener) {
		
	}
}
