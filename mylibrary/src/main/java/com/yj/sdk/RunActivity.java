package com.yj.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * @author lufengkai
 */
public class RunActivity extends Activity implements OnClickListener {

	private final int INIT_WHAT = 0;
	private final int LOGIN_ID = 1;// 登录
	private final int SERVICE_ID = 2;// 服务器id
	private final int PAY_ID = 3;// 支付
	private YJManage manage;// 接口管理
	private EditText editText;

	@SuppressWarnings("ResourceType")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				     WindowManager.LayoutParams.FLAG_FULLSCREEN);
		LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundColor(0xffdddddd);
		layout.setOrientation(LinearLayout.VERTICAL);
		this.setContentView(layout);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);

		/* 登录 */
		params = new LinearLayout.LayoutParams(-1, -2);
		Button loginBtn = new Button(this);
		loginBtn.setText("登录");
		loginBtn.setId(LOGIN_ID);
		loginBtn.setOnClickListener(this);
		layout.addView(loginBtn, params);
		
	    editText = new EditText(this);
	    editText.setHint("请输入充值金额");
	    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		layout.addView(editText,-1,-2);

		/* 发送server */
		/*
		 * params = new LinearLayout.LayoutParams(-1, -2); Button severBtn = new
		 * Button(this); severBtn.setText("severId");
		 * severBtn.setId(SERVICE_ID); severBtn.setOnClickListener(this);
		 * layout.addView(severBtn, params);
		 */

		/* 充值 */
		Button payBtn = new Button(this);
		payBtn.setText("充值");
		payBtn.setId(PAY_ID);
		payBtn.setOnClickListener(this);
		layout.addView(payBtn, params);

		// 初始化接口管理类
		manage = YJManage.getInstance();

		/**/
		 resultListen = new YJResultListen() {
			
			@Override
			public void loginSucceedResult(String uid) {
				//参数说明：account为登录帐号
				//登录成功  
				Log.i("feng", "登录成功aaaaaaaaaaaaaa:" + uid);
			}
			
			@Override
			public void loginFailureResult() {
				//登录失败
				Log.i("feng", "登录失败aaaaaaaaaaaaaaaaaaaaaaa");
			}
		};

//		YJManage.getInstance().initYJSDK(this,"lrcq_zm");

	}

	private YJResultListen resultListen;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case LOGIN_ID:
			/*登录*/
			YJManage.getInstance().yjLogin(this,resultListen);
			break;

		case PAY_ID:
			// 金额、角色名字、角色等级
			// manage.showPaymentView(19, "封印", 72, "自定义回调", handler, PAY_ID);
           String serverId = "1002";//服务器id
           double money = 0.1;
           try {
        	   money = Integer.valueOf(editText.getText() + "");
		} catch (Exception e) {
			// TODO: handle exception
		}
            
           if(money == 0) money = 0.1;
//           String roleName = "漫步者";
//           double roleLevel = 70;
           String tradeName = "金币";
           String callBackInfo = "自定义参数" ;
           YJManage.getInstance().yjPayment(this,money, "665655444",serverId,"95","战斗","5369",tradeName, "8",callBackInfo);
           
			break;

		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		manage.showYJFloatView(this);

	}

	@Override
	protected void onPause() {
		super.onPause();
		manage.removeYJFloatView(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		YJManage.getInstance().outLogin();
	}

}
