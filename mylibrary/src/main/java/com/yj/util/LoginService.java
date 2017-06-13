package com.yj.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yj.entity.Constants;
import com.yj.entity.Result;
import com.yj.entity.Session;
import com.yj.entity.UserAction;
import com.yj.sdk.YouaiAppService;
import com.yj.ui.MResource;
import com.yj.ui.RegisterLayout;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class LoginService {

	private static LoginService loginService;
	private Context context;

	private LoginService(Context context) {
		this.context = context;
	}

	public static LoginService getInit(Context context) {
		if (loginService == null) {
			loginService = new LoginService(context);
		}
		return loginService;

	}

	/*
	 * public void select(final LinearLayout key_layout,final LinearLayout
	 * editLayout,final EditText mInputPsW, final EditText mInputUser){
	 * 
	 * }
	 */

	/**
	 * 输入框背景切换
	 */
	public void select(final LinearLayout key_layout,
                           final LinearLayout editLayout, final EditText mInputPsW,
                           final EditText mInputUser, final ImageView user,
                           final ImageView password) {

		mInputUser.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					key_layout.setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(context, "input_no.9.png"));
					editLayout.setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(context, "input.9.png"));

					CharSequence text = mInputUser.getText();
					if (mInputUser != null && text != null) {
						if (text instanceof Spannable) {
							Spannable spanText = (Spannable) text;
							Selection.setSelection(spanText, text.length());
						}
					}
					if (user != null && password != null) {
						user.setBackgroundDrawable(BitmapCache.getDrawable(
								context, Constants.ASSETS_RES_PATH
										+ "user_on.png"));
						password.setBackgroundDrawable(BitmapCache.getDrawable(
								context, Constants.ASSETS_RES_PATH
										+ "password_off.png"));
					}

				}
			}
		});

		mInputPsW.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					key_layout.setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(context, "input.9.png"));
					editLayout.setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(context, "input_no.9.png"));

					CharSequence text = mInputPsW.getText();
					if (mInputPsW != null && text != null) {
						if (text instanceof Spannable) {
							Spannable spanText = (Spannable) text;
							Selection.setSelection(spanText, text.length());
						}
					}
					if (user != null && password != null) {
						user.setBackgroundDrawable(BitmapCache.getDrawable(
								context, Constants.ASSETS_RES_PATH
										+ "user_off.png"));
						password.setBackgroundDrawable(BitmapCache.getDrawable(
								context, Constants.ASSETS_RES_PATH
										+ "password_on.png"));
					}

				}
			}
		});

	}

	/**
	 * 输入框背景切换
	 */
	public void selectInput(final LinearLayout key_layout,
                                final LinearLayout editLayout, EditText mInputPsW,
                                EditText mInputUser) {

		mInputUser.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					key_layout.setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(context, "input_no.9.png"));
					editLayout.setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(context, "input.9.png"));
				}
			}
		});

		mInputPsW.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					key_layout.setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(context, "input.9.png"));
					editLayout.setBackgroundDrawable(BitmapCache
							.getNinePatchDrawable(context, "input_no.9.png"));
				}
			}
		});

	}

	private LinearLayout key_layout, editLayout;
	private RelativeLayout anser;
	private String input_no = "input_no.9.png";
	private String input = "input.9.png";

	/**
	 * 输入框背景切换
	 */
	public void selectInputMb(final LinearLayout key_layout,
                                  final LinearLayout editLayout, final EditText mInputPsw,
                                  final EditText mInputUser, final RelativeLayout anser) {

		this.key_layout = key_layout;
		this.editLayout = editLayout;
		this.anser = anser;
		mbSetOnTouchListener(mInputUser, input_no, input, input_no);// 帐号框点击
		mbSetOnTouchListener(mInputPsw, input, input_no, input_no);// 密码框点击
		mbSetOnFocusChangeListener(mInputUser, input_no, input, input_no);// 帐号框焦点
		mbSetOnFocusChangeListener(mInputPsw, input, input_no, input_no);// 密码框焦点

	}

	private void mbSetOnTouchListener(final EditText mInput, final String str1,
                                          final String str2, final String str3) {
		mInput.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Utils.getEditTextFocus(mInput);// 获取焦点
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					YouaiAppService.keyboard = false;
					mbSetBackground(str1, str2, str3);
				}

				return false;
			}
		});
	}

	/**
	 * 对话框焦点
	 * 
	 * @param str1
	 * @param str2
	 * @param str3
	 */
	private void mbSetOnFocusChangeListener(final EditText mInput,
                                                final String str1, final String str2, final String str3) {
		mInput.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mbSetBackground(str1, str2, str3);
				}
			}
		});
	}

	/**
	 * 设置密保对话框背景
	 */
	public void mbSetBackground(String key_layout_str, String editLayout_str,
                                    String anser_str) {

		if (key_layout != null && key_layout_str != null)
			key_layout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
					context, key_layout_str));

		if (editLayout != null && editLayout_str != null)
			editLayout.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
					context, editLayout_str));

		if (anser != null && anser_str != null)
			anser.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
					context, anser_str));

	}

	/**
	 * 初始化
	 */
	public void initialize() {

		/* youaiId、serverId 赋值 */
		// DeviceProperties.youpengId = youaiId;

		/* 回调参数 */
		/*
		 * YouaiAppService.what = what; YouaiAppService.callbackHandler =
		 * callbackHandler;
		 */

		new Thread(new Runnable() {
			@Override
			public void run() {

//				BaseData baseData = GetDataImpl.getInstance(context).online();// 登入请求
				/* 发送行为 */
				/*
				 * while (true) { long userActionLogIntervalTime = 0;//
				 * 每次发送行为的时间 if (baseData != null && baseData.userActionTime !=
				 * null) { try { userActionLogIntervalTime = Long
				 * .valueOf(baseData.userActionTime); } catch (Exception e) {
				 * Utils.youaiLog("行为时间有误"); } } else {
				 * Utils.youaiLog("行为时间有误"); }
				 * 
				 * try { 定义发送请求的时间 if (userActionLogIntervalTime < 10000l ||
				 * userActionLogIntervalTime > 360000l) {
				 * userActionLogIntervalTime = 180000l; }
				 * Thread.sleep(userActionLogIntervalTime);
				 * Utils.youaiLog("发送行为"); 发送行为 sendUserAction(); } catch
				 * (Exception e) { e.printStackTrace(); } }
				 */

			}
		}).start();
	}

	/**
	 * 发送用户行为次数
	 */
	private void sendUserAction() {

		/* 获取点击记录的点击次数 */
		SharedPreferences sharedPreferences = context.getSharedPreferences(
                    Constants.SAVESETTING, Context.MODE_PRIVATE);
		int actions = sharedPreferences.getInt(Constants.ACTIONS, 0);// 点击小窗口次数
		int aotocancel = sharedPreferences.getInt(Constants.AOTOCANCEL, 0);// 点击自动登录按钮次数

		/* 假如是0就不发送 */
		/*
		 * if (actions == 0 && aotocancel == 0) { return; }
		 */

		Utils.youaiLog("actions  count--->" + actions);
		Utils.youaiLog("aotocancel  count--->" + aotocancel);

		/* 赋值封装json */
		UserAction useraction = new UserAction();
		useraction.isCancelLogin = aotocancel;
		useraction.isClieckIcon = actions;

		/* 发送完清0 */
		Editor editor = sharedPreferences.edit();
		editor.putInt(Constants.ACTIONS, 0);
		editor.putInt(Constants.AOTOCANCEL, 0);
		editor.commit();
		/* 发送行为次数 */
		GetDataImpl.getInstance(context).userAction(useraction);
	}

	/**
	 * 打开新的Activity
	 */
	public void openActivity(Class<?> className) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, className);
		context.startActivity(intent);
	}

	/**
	 * 获取本机登录记录
	 */
	public Session getLoginRecord(Context context) {
		Session session = Session.getInstance();
//		String[] str = Utils.getAccountFromSDcard();// 获取SD卡帐号记录
//		if (str != null && str.length == 2) {
//			session.userAccount = str[0];
//			session.password = str[1];
//		} else {// 获取xml帐号记录
//			SharedPreferences preferences = Utils.getXmlShared(context,
//					Constants.LOGIN_XML_NAME);
			session.userAccount = SharedPreferencesUtil.getXmlAccount(context);
			session.password = SharedPreferencesUtil.getXmlPwd(context);
//		}
		return session;
	}

	/**
	 * 自动登录
	 * 
	 * @param btnStartGame
	 */
	public void autolLogin(Button btnStartGame) {

		String autolLogin = SharedPreferencesUtil.getXmlAutoLogin(context);

		if (Constants.YES.equals(autolLogin) && btnStartGame != null) {// 假如标志位为yes则自动登录
			LoginTask.autoLoginFlag = true;
			btnStartGame.performClick();
		}

		else if (!Constants.NO.equals(autolLogin)) {// 假如标志位为空则自动登录
												// 只有标志为no的时候不自动登录
			SharedPreferencesUtil.saveAutoLoginToXml(context,Constants.YES);
			if (btnStartGame != null) {
				btnStartGame.performClick();
				LoginTask.autoLoginFlag = true;
			}
		}
	}

	/**
	 * 判断是不是第一次注册
	 * 
	 * @param registerLayout
	 */
	public void getRegisterRecord(RegisterLayout registerLayout) {
		new ModifyTask(context, registerLayout).execute();
		/* 判断记录 */
		/*
		 * SharedPreferences preferences = Utils.getXmlShared(context,
		 * Constants.LOGIN_XML_NAME); String accounts =
		 * preferences.getString(Constants.LOGIN_ACCOUNTS_XML, "NULL"); if
		 * ("NULL".equals(accounts)) {// 无本地登录记录 String defaultAccount =
		 * preferences.getString( Constants.DEFAULT_REGISTER, "NULL"); if
		 * (!"NULL".equals(defaultAccount)) {
		 * registerLayout.mRegistUserId.setText(defaultAccount); } else { //
		 * 没获取默认帐号记录 ----》自动获取帐号 new ModifyTask(context,
		 * registerLayout).execute(); } }
		 */
	}

	/**
	 * 发送serverId
	 */
	public void sendServerId(Handler handler, int what) {
		Result result = GetDataImpl.getInstance(context).sendServerId();
		if (result == null) {
			Utils.toastInfo(context, "连接服务器失败");
			return;
		}
		if (result.resultCode == 0) {
			Message message = Message.obtain();
			message.what = what;
			message.obj = "succeed";
			handler.sendMessage(message);
			Utils.toastInfo(context, "连接成功");
		} else {
			Utils.toastInfo(context, result.resultDescr);
		}
	}

	/**
	 * 自动登录按钮
	 */
	public void setAutol(ImageView autoImage) {
		String auto = SharedPreferencesUtil.getXmlAutoLogin(context);
		if (Constants.YES.equals(auto)) {
			SharedPreferencesUtil.saveAutoLoginToXml(context,Constants.NO);
			autoImage.setImageDrawable
					(BitmapCache.getDrawable(context, MResource.getIdByName(context,Constants.DRAWABLE,"bth_gouxuan"),19,19));
		} else {
			SharedPreferencesUtil.saveAutoLoginToXml(context,Constants.YES);
			autoImage.setImageDrawable
					(BitmapCache.getDrawable(context, MResource.getIdByName(context,Constants.DRAWABLE,"bth_gouxuan_sel"),19,19));
		}
	}

	public void setAutoLoginIvState(ImageView autoImage){
		String auto = SharedPreferencesUtil.getXmlAutoLogin(context);
		if (Constants.NO.equals(auto)) {
			autoImage.setImageDrawable
					(BitmapCache.getDrawable(context, MResource.getIdByName(context,Constants.DRAWABLE,"bth_gouxuan"),19,19));
		} else {
			autoImage.setImageDrawable
					(BitmapCache.getDrawable(context, MResource.getIdByName(context,Constants.DRAWABLE,"bth_gouxuan_sel"),19,19));
		}
	}

	/**
	 * 修改自动登录参数
	 * 
	 * @param preferences
	 * @param flag
	 * @param iconName
	 */
//	private void setAutoLogin(SharedPreferences preferences, String flag,
//			String iconName, ImageView imageView) {
//		Editor editor = preferences.edit();
//		editor.putString(Constants.AUTOL_LOGIN_XML, flag);
//		editor.commit();
//		imageView.setBackgroundDrawable(BitmapCache.getDrawable(context,
//				Constants.ASSETS_RES_PATH + iconName));
//	}

	/**
	 * 设置行为次数
	 * 
	 * @param key
	 */
	public void setActions(String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
                    Constants.SAVESETTING, Context.MODE_PRIVATE);
		int actions = sharedPreferences.getInt(key, 0);// 点击自动登录选项次数
		Editor editorAction = sharedPreferences.edit();
		editorAction.putInt(key, actions + 1);
		editorAction.commit();
	}

}
