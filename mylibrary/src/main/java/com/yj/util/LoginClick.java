package com.yj.util;

import android.app.Activity;
import android.util.Log;

import com.yj.ui.ForgetPassword;
import com.yj.ui.LoginLayout;
import com.yj.ui.RegisterLayout;
import com.yj.ui.ResettingPassword;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class LoginClick {
	
	private Activity context;
	private CheckAccount checkAccount;
	private LoginLayout loginLayout;
	private ForgetPassword forgetlayout;
	
	public LoginClick(Activity context, CheckAccount checkAccount, LoginLayout loginLayout, ForgetPassword forgetlayout){
		this.context = context;
		this.checkAccount = checkAccount;
		this.loginLayout = loginLayout;
		this.forgetlayout = forgetlayout;
	}
	
	/**
	 * 登录游戏
	 */
	public void login(){
		/* 获取帐号密码 */
		String account = loginLayout.getAccount();
		String password = loginLayout.getPassWord();
		boolean isLegal = checkAccount.checkAccount2PasswordLegal(account, password);
		if (!isLegal)
			return;
		/* 登录 */
		new LoginTask(this.context, account, password).execute();
	}
	
	/**
	 *  进入游戏 ---》注册按钮
	 */
	public void register(RegisterLayout registerLayout){
		if (registerLayout == null) {
			Utils.toastInfo(context, "初始化失败，请重新启动游戏");
			return;
		}
		// 获取帐号密码
		String user = registerLayout.getInputUserName();// 帐号
		String pw = registerLayout.getInputUserPwd();// 密码
		Log.i("feng", "user:" + user + "   pw:" + pw);
        /*检测帐号密码*/
		boolean isLegal = checkAccount.checkAccount2PasswordLegal(user, pw);
		if (!isLegal){
			return;
		}
		if(!registerLayout.isCheck()){
			ToastUtils.toastShow(context,"同意用户协议才能下一步操作");
			return;
		}
        /*注册帐号*/
		new RegisterTask(context, user, pw, loginLayout).execute();
	}
	
	/**
	 * 忘记密码提交帐号
	 */
	public void forgerPasswodToAccount(){
		if(forgetlayout == null) return;
		String userName = forgetlayout.getAccount();
		boolean isLegal = checkAccount.isAccountLegal(userName);
		if (!isLegal)
			return;
         new GetMiBaoToUserTask(context, userName, forgetlayout).execute();
	}
	
	/**
	 * 忘记密码提交新密码
	 */
	public void forgerPasswodToPassword(ResettingPassword resettingPassword){
		if(resettingPassword == null || resettingPassword.answerEdit == null || resettingPassword.answerEdit.getText() == null){
			return;
		}
		String answer = resettingPassword.answerEdit.getText().toString();
		String newPw = resettingPassword.inputPas.getText().toString();
        /*检测问题答案格式*/
		if (answer == null || "".equals(answer) || answer.length() > 20) {
			Utils.toastInfo(context, "请填写正确的密保答案");
			return;
		}
		/*检测帐号密码规范*/
		boolean isLegal = checkAccount.isAccountLegal(forgetlayout.getAccount());
		if (!isLegal)
			return;
		isLegal = checkAccount.isPasswordLegal(newPw,"");
		if (!isLegal)
			return;
       /*提交*/
		new MiBaoSubmitTask(context, forgetlayout.getAccount(), newPw, answer)
				.execute();
	}

}
