package com.yj.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yj.entity.Constants;
import com.yj.entity.SecretData;
import com.yj.ui.OptionsAdapter;
import com.yj.ui.OptionsAdapter.OptionView;
import com.yj.ui.OverProtection;
import com.yj.ui.ResettingProtection;

public class UserService {

	private static UserService mService;
//	private Context activity;
	private PopupWindow selectPopupWindow;
	private ResettingProtection resettingProtection;
	private OverProtection overProtection;
	private Context contexts;

	private UserService() {
//		this.activity = activity;
	}

	public static UserService getInit() {
		if (mService == null) {
			mService = new UserService();
		}
		return mService;
	}

	

	/**
	 * 初始化密保问题数组
	 * 
	 * @return
	 */
	public SecretData[] initSecretDataArray() {
		SecretData[] secretData = new SecretData[Constants.SECRET.length];
		for (int i = 0; i < Constants.SECRET.length; i++) {
			SecretData data = new SecretData();
                  SecretData.secretId = i + 1;
			data.secretName = Constants.SECRET[i];
			secretData[i] = data;
		}
		return secretData;
	}

	public void initPopuWindow(Context contexts, SecretData[] secretData,
                                   final ResettingProtection resettingProtection,
                                   final OverProtection overProtection) {

		this.resettingProtection = resettingProtection;
		this.overProtection = overProtection;

		ListView listView = new ListView(contexts);
		listView.setDivider(BitmapCache.getDrawable(contexts,
				Constants.ASSETS_RES_PATH + "listview_divide.png"));
		listView.setCacheColorHint(0x00000000);
		listView.setSelector(android.R.color.transparent);
		OptionsAdapter adapter = new OptionsAdapter(contexts, secretData);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
				OptionView optionView = (OptionView) view;
				// 获取密保问题下拉组件
			/*	if (Session.secret != Constants.SECRET_ON) {
					setSecretQuestion(resettingProtection.questionEdit,
							optionView, position);
				} else {
					setSecretQuestion(overProtection.newQuestionEdit,
							optionView, position);
				}*/
				dismiss();

			}
		});
		selectPopupWindow = new PopupWindow(listView, DimensionUtil.dip2px(
				contexts, 200), -2, true);
		selectPopupWindow.setBackgroundDrawable(Utils.getNormalColorList(
				contexts, 0xffadadad, 0xfff6f6f6));
	}

	private void setSecretQuestion(TextView questionText,
			OptionView optionView, int position) {
		questionText.setText(optionView.account.getText().toString());
		questionText.setId(position + 1);
	}

	/**
	 * 显示问题列表
	 */
	public void popupWindwShowing(Context activity, int flag) {
		if (flag == Constants.SECRET_OFF) {
			selectPopupWindow.showAsDropDown(
					resettingProtection.questionLayout,
					DimensionUtil.dip2px(activity, 5), -3);
		}
		if (flag == Constants.SECRET_ON)
			selectPopupWindow.showAsDropDown(
					overProtection.newQuestionEditLayout,
					DimensionUtil.dip2px(activity, 5), -3);
	}

	/**
	 * PopupWindow消失
	 */
	public void dismiss() {
		if (null != selectPopupWindow && selectPopupWindow.isShowing())
			selectPopupWindow.dismiss();
	}
	
	/**
	 * 拨打客服电话
	 */
	public void serverPhone(final Context context, final String phoneNub){
		if (phoneNub != null) {
			
			 LinearLayout view = new LinearLayout(context);
			 view.setOrientation(LinearLayout.VERTICAL);
			 view.setGravity(Gravity.CENTER);
			 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
			 TextView textView = new TextView(context);
			 textView.setText(phoneNub+"");
			 textView.setTextColor(0xffffffff);
			 textView.setTextSize(18);
			 params.topMargin = 20;
			 params.bottomMargin = 20;
			 view.addView(textView,params);
			 AlertDialog.Builder builder = new AlertDialog.Builder(context);
			 builder.setView(view);
//			 builder.setTitle("拨打电话");
			 builder.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.CALL");
					intent.setData(Uri.parse("tel:" + phoneNub));
					context.startActivity(intent);// 方法内部会自动为Intent添加类别：android.intent.category.DEFAULT
				}
			});
			 builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			 builder.create().show();
			
		}
	}
	
}
