package com.yj.util;

import static com.yj.sdk.YouaiAppService.mSession;

import android.app.Activity;
import android.os.Message;
import android.view.View;

import com.yj.entity.Constants;
import com.yj.entity.Result;
import com.yj.sdk.LoginCallbackInfo;
import com.yj.sdk.YouaiAppService;

import java.util.Stack;

/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class ActivityService {

	private Activity activity;
	private Stack<View> mViewStack = new Stack<View>();

	public ActivityService(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 弹出旧ui
	 * 
	 * @return
	 */
	public View popViewFromStack() {
		if (mViewStack.size() > 1) {
			// 弹出旧ui
			View pop = mViewStack.pop();
			pop.clearFocus();
			pop = null;

			View peek = mViewStack.peek();
			this.activity.setContentView(peek);
			peek.requestFocus();
			// peek.startAnimation(mAnimLeftIn);
			return peek;
		} else {
			return null;
		}
	}

	/**
	 * 添加新UI
	 * 
	 * @param newView
	 */
	public void pushView2Stack(View newView) {
		if (mViewStack.size() > 0) {
			View peek = mViewStack.peek();
			peek.clearFocus();
		}
	/*	if(mViewStack.contains(newView)){
			mViewStack.remove(newView);
		}
		mViewStack.*/
		mViewStack.push(newView);
		this.activity.setContentView(newView);
		newView.requestFocus();
	}

	/**
	 * 监听返回键
	 */
	public View listenerBack(int flag) {// 系统返回键
		View view = popViewFromStack();
		if (view == null) {
			if (flag == Constants.BACK_LOGIN) {
				closeLogin();
				this.activity.finish();
			}else if(flag == Constants.BACK_PAY){
				
			}else{
				// 用户退出主界面
				closeLogin();
				this.activity.finish();
			}

		} else {
			this.activity.setContentView(view);
			view.requestFocus();
		}
		return view;
	}

	/*	*//**
	 * 监听用户中心返回键
	 */
	/*
	 * public void listenerUserCenterBack() {//系统返回键 View view =
	 * popViewFromStack(); if (view == null) { // 用户退出登陆 // closeLogin();
	 * this.activity.finish(); } else { this.activity.setContentView(view);
	 * view.requestFocus(); } }
	 */
	/**
	 * 监听支付返回键
	 */
	/*
	 * public void listenerPayBack() {//系统返回键 View view = popViewFromStack(); if
	 * (view == null) { // 用户退出登陆 // closeLogin(); this.activity.finish(); }
	 * else { this.activity.setContentView(view); view.requestFocus(); } }
	 */

	/**
	 * 用户按back键退出页面 反馈失败消息
	 */
	public void closeLogin() {
		Utils.youaiLog("close login-------------");
		if (YouaiAppService.callbackHandler != null) {
			LoginCallbackInfo lbi = new LoginCallbackInfo();
			lbi.statusCode = LoginCallbackInfo.STATUS_CLOSE_VIEW;
			lbi.desc = "用户关闭登录界面";
			Message message = Message.obtain();
			message.obj = lbi;
			message.what = YouaiAppService.what;
			YouaiAppService.callbackHandler.sendMessage(message);
		}
	}

	/**
	 * 发送回调handle
	 * 
	 * @param lbi
	 */
	private void sendCallbackInfo(LoginCallbackInfo lbi) {
		if (lbi != null && YouaiAppService.callbackHandler != null) {
			Message message = Message.obtain();
			message.obj = lbi;
			message.what = YouaiAppService.what;
			YouaiAppService.callbackHandler.sendMessage(message);
		} else {
			closeLogin();
		}
	}

	/**
	 * 登陆成功 或 注册成功 或 修改密码成功后 反馈第三方
	 */
	public void onPostLogin(final Result result, Activity activity) {
		if (YouaiAppService.callbackHandler != null && result != null
				&& result.resultCode == 0 && mSession != null) {
			LoginCallbackInfo lbi = new LoginCallbackInfo();
			lbi.statusCode = LoginCallbackInfo.STATUS_SUCCESS;
			// lbi.userName = mSession.userAccount;
//			lbi.loginTime = mSession.loginTime;
			lbi.desc = result.resultDescr;
//			lbi.userId = mSession.userId;
//			lbi.sign = mSession.sign;
			Utils.calc(lbi.userId + "&" + lbi.loginTime + "&"
					+ "f419c116cb5d6e15b59e3d9c263bb6ab");
			sendCallbackInfo(lbi);
			activity.finish();

		}
	}
}
