package com.yj.util;

import android.content.Context;

import com.yj.sdk.YouaiAppService;

import java.util.regex.Pattern;

public class CheckAccount {
	
	private Context context;
	
	public CheckAccount(Context context){
		this.context = context;
	}
	
	/**
	 * 检查登录帐号密码是否合法
	 * @param account
	 * @param password
	 */
	public boolean checkAccount2PasswordLegal(String account, String password) {
		/*检测合法*/
		boolean isUser = isAccountLegal(account);
		if(!isUser){
			return false;
		}
		boolean isPw = isPasswordLegal(password,"");
		if (!isPw){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断密码是否合法
	 */
	public boolean isPasswordLegal(String password, String flag){
		if(password == null || "".equals(password)){
			ToastUtils.toastShow(context, flag + "密码不能为空");
			return false;
	  }else if ( password.length() < YouaiAppService.min || password.length() > YouaiAppService.max) {
			ToastUtils.toastShow(context, "有效" + flag + "密码为" + YouaiAppService.min + "-" + YouaiAppService.max + "位");
			return false;
		}else if(getChinese(password)){
			ToastUtils.toastShow(context, flag + "密码不能包含中文");
			return false;
		}else if(password.lastIndexOf(" ") > 0){
			ToastUtils.toastShow(context, flag + "密码不能包含空格");
			return false;
		}
		return true;
	}
	
	/**
	 * 判断账户是否合法
	 * @return
	 */
	public boolean isAccountLegal(String account){
		if(account == null || "".equals(account)){
			Utils.toastInfo(context, "帐号不能为空");
			return false;
	  }else if ( account.length() < YouaiAppService.min || account.length() > YouaiAppService.max) {
			Utils.toastInfo(context, "有效" + "帐号为" + YouaiAppService.min + "-" + YouaiAppService.max + "位");
			return false;
		}else if(getChinese(account)){
			Utils.toastInfo(context, "帐号不能包含中文");
			return false;
		}else if(account.lastIndexOf(" ") > 0){
			Utils.toastInfo(context, "帐号不能包含空格");
			return false;
		}else if(!matchesABC123(account)){
			Utils.toastInfo(context, "帐号支持数字、字母、-、@、_、.字符");
			return false;
		}else if(!matchesAdmin(context, account)){
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 不能包含特殊字符
	 */
	public  boolean matchesAdmin(Context context, String str){
		// 'admin','gm','GM','ｇｍ','ＧＭ','ＡＤＭＩＮ','ａｄｍｉｎ'
		 String[] strAdminArray = {"admin", "gm", "GM", "ｇｍ", "ＧＭ", "ＡＤＭＩＮ", "ａｄｍｉｎ"};
         for (int i = 0; i < strAdminArray.length; i++) {
			if(str.contains(strAdminArray[i])){
				Utils.toastInfo(context, "帐号不能包含:" + strAdminArray[i]);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 检测数字字母组合
	 */
	 public  boolean matchesABC123(String str){
		 if(str == null) return false;
		 return str.matches("^[a-zA-Z0-9-_.@]+$");
	 }
	
	
	/**
	 * @param str
	 * @return true表示包含有中文
	 */
	public  boolean getChinese(String str) {
		boolean HasChinese = false;
		if (str == null || "".equals(str.trim())) {
			return false;
		}
		char[] pwd = str.toCharArray();
		for (int i = 0; i < pwd.length; i++) {
			char c = pwd[i];
			if (Pattern.matches("[\u4e00-\u9fa5]", String.valueOf(c))) {
				HasChinese = true;
				break;
			}
		}
		return HasChinese;
	}

	/**
	 * 判断密保答案
	 */
	public boolean isSecretAnswer(String anser){
		if(anser == null){
			return false;
		}else if(anser.length() < YouaiAppService.minM || anser.length() > YouaiAppService.maxM){
			Utils.toastInfo(context, "密保答案为" + YouaiAppService.minM + "-" + YouaiAppService.maxM + "个字符");
			return false;
		}
		return true;
	}

}
