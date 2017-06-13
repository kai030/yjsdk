package com.yj.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.Selection;
import android.text.Spannable;

import com.yj.entity.Constants;
import com.yj.entity.Result;
import com.yj.entity.Session;
import com.yj.ui.RegisterLayout;

/**
 * 
 * @author lufengkai
 *  自动获取帐号
 */
public class ModifyTask extends AsyncTask<Void, Void, Result> {
	private RegisterLayout registerLayout;
	private Context ctx;
	public  static Session session;

	public ModifyTask(Context context, RegisterLayout registerLayout) {
		this.ctx = context;
		this.registerLayout = registerLayout;
	}

	@Override
	protected void onPreExecute() {
		// 显示进度条
	}

	@Override
	protected Result doInBackground(Void... params) {
		// 拿到注册用户名
		String json = GetDataImpl.getInstance(ctx).registerUserAndPasswrod();
		if(json == null) return null;
//		json = "{b:{\"a\":156,\"b\":lufengkai,\"d\":\"20150533\"}}";
		session = (Session) JsonUtil.parseJSonObject(Session.class, json);
		if(session == null){
			return null;
		}
		
		Utils.youaiLog("userAccount:" + session.userAccount + "     password:" + session.password + "    ssssssxxxx:" + Session
                    .getInstance().userAccount);
//		Log.i("feng", "userId:"+session.userId+ "      userAccount:"+session.userAccount + "     loginTime:"+session.loginTime);
		Result result = (Result) JsonUtil.parseJSonObject(Result.class,
                                                                  json);
		return result;
	}

	@Override
	protected void onPostExecute(Result result) {
		// 获取用户名密码的值
		if (null != session && result != null && result.resultCode == 0) {
			
			/*把默认帐号缓存到本地*/
			if(session.userAccount != null && !session.userAccount.equals("") && session.userAccount.length() >= 6
					&& registerLayout != null && registerLayout.mRegistUserId != null){
				registerLayout.mRegistUserId.setText(session.userAccount);//设置帐号
				registerLayout.mRegistUserPwd.setText(session.password);
			SharedPreferences preferences = Utils.getXmlShared(ctx, Constants.USER_XML);
			Utils.commitSharedString(preferences, Constants.DEFAULT_REGISTER, session.userAccount);
			}
			
			CharSequence text = registerLayout.mRegistUserId.getText();
			if (text instanceof Spannable) {
			    Spannable spanText = (Spannable)text;
			    Selection.setSelection(spanText, text.length());
			}
			
		}
	}

}
