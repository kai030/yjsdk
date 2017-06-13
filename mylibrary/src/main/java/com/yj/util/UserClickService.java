package com.yj.util;

import android.app.Activity;

import com.yj.entity.Constants;
import com.yj.ui.AlterPassword;
import com.yj.ui.OverProtection;
import com.yj.ui.ResettingProtection;

public class UserClickService {

	private Activity activity;
	private CheckAccount checkAccount;
	private ActivityService activityService;

	public UserClickService(Activity activity, CheckAccount checkAccount, ActivityService activityService) {
		this.activity = activity;
		this.checkAccount = checkAccount;
		this.activityService = activityService;
	}

	/**
	 * 修改密码提交
	 */

	public void alterPassword(AlterPassword alterPassword) {
		if (!NetworkImpl.isNetworkConnected(activity)) {
			Utils.toastInfo(activity, Constants.NETWORK_FAILURE);
			return;
		}

		if (alterPassword != null) {
			String newPassword = alterPassword.getNewPassword();
			String oldPassword = alterPassword.getOldPassword();

			/* 检查密码是否合法 */
			boolean flagBoolean = checkAccount
					.isPasswordLegal(oldPassword, "原");
			if (!flagBoolean)
				return;
			flagBoolean = checkAccount.isPasswordLegal(newPassword, "新");
			if (!flagBoolean)
				return;
			if (newPassword.equals(oldPassword)) {
				Utils.toastInfo(activity, "新密码不能与旧密码相同");
				return;
			}
			/* 提交密码 */
			new ModificTask(activity, oldPassword, newPassword).execute();

		}
	}

	/**
	 * 密保设置提交
	 */
	public void setSecretCommit(ResettingProtection resettingProtection,
                                    String flagRequestId, Activity activity) {

		if (!Utils.isInteger(resettingProtection.answerEdit.getText()
				.toString())) {
			ToastUtils.toastShow(activity, "请输入正确的手机号码");
			return;
		} else if (resettingProtection.inputPas.getText().toString() == null
				|| "".equals(resettingProtection.inputPas.getText().toString()
						.trim())) {
			if (flagRequestId != Constants.A_PHONE_VERIFICATION_CODE) {
				ToastUtils.toastShow(activity, "请输入验证码");
				return;
			}

		}
		if (resettingProtection != null) {
			if (!NetworkImpl.isNetworkConnected(activity)) {
				Utils.toastInfo(activity, Constants.NETWORK_FAILURE);
				return;
			} else {
				// int secretId = resettingProtection.questionEdit.getId();//
				// 问题id
				String secretAnswer = resettingProtection.answerEdit.getText()
						.toString();// 问题答案
				Utils.youaiLog("手机号码：" + secretAnswer);
				String password = resettingProtection.inputPas.getText()
						.toString();// 登录密码
				Utils.youaiLog("手机验证码：" + password);

				/*
				 * boolean flagBoolean = checkAccount.isPasswordLegal(password,
				 * ""); if (!flagBoolean) return;
				 * 
				 * flagBoolean = checkAccount.isSecretAnswer(secretAnswer); if
				 * (!flagBoolean) return;
				 */

				new ModiSubmitTask(activity, password, secretAnswer,
						flagRequestId,this.activityService).execute();
			}
		}
	}

	/**
	 * 修改密保提交
	 */
	public void alterSecretCommit(OverProtection overProtection,
                                      String flagRequestId, Activity activity) {
		if (!NetworkImpl.isNetworkConnected(activity)) {
			Utils.toastInfo(activity, "网络连接失败，请检查网络设置");
			return;
		}

		String oldSecretAnswer = overProtection.getOldAnserText();// 已绑定手机号码
		String newSecretAnswer = overProtection.getNewAnserText();// 验证码
		if (!Utils.isInteger(oldSecretAnswer)) {
			ToastUtils.toastShow(activity, "请输入正确的手机号码");
			return;
		} else if (newSecretAnswer == null || "".equals(newSecretAnswer.trim())) {
			if (flagRequestId != Constants.A_PHONE_VERIFICATION_CODE) {
				ToastUtils.toastShow(activity, "请输入验证码");
				return;
			}

		}

		/* 检测问题规范 */
		/*
		 * if (!checkAccount.isSecretAnswer(oldSecretAnswer)) { return; } if
		 * (!checkAccount.isSecretAnswer(newSecretAnswer)) { return; }
		 */

		// int newsecretId = overProtection.newQuestionEdit.getId()

		new AlterMiBaoSubmitTask(activity, oldSecretAnswer, newSecretAnswer,
                                         flagRequestId).execute();

	
	}

}
