package com.yj.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.yj.entity.BaseData;
import com.yj.entity.Constants;
import com.yj.entity.SecretData;
import com.yj.ui.AlterPassword;
import com.yj.ui.ContactLayout;
import com.yj.ui.OverProtection;
import com.yj.ui.ResettingProtection;
import com.yj.ui.UserCenterLayout;
import com.yj.util.ActivityService;
import com.yj.util.CheckAccount;
import com.yj.util.GetDataImpl;
import com.yj.util.NetworkImpl;
import com.yj.util.UserClickService;
import com.yj.util.UserService;
import com.yj.util.Utils;

/**
 * @author lufengkai
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
public class ZhimengUserCenterActivity extends Activity implements OnClickListener, OnItemClickListener {

	private AlterPassword alterPassword;
	private SecretData[] secretData = new SecretData[Constants.SECRET.length];
	private OverProtection overProtection;
	public static ResettingProtection resettingProtection;
	// 下拉框依附组件宽度，也将作为下拉框的宽度
	private ActivityService activityService;
	private UserService userService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		activityService = new ActivityService(this);
		userService = UserService.getInit();

		UserCenterLayout relativeLayout = new UserCenterLayout(
				ZhimengUserCenterActivity.this);
		relativeLayout.setOnclick(this);
		relativeLayout.setOnItemclick(this);
		activityService.pushView2Stack(relativeLayout);
		this.setContentView(relativeLayout);
	}

	/**
	 * 功能按钮
	 */
	@Override
	public void onClick(View v) {

		CheckAccount checkAccount = new CheckAccount(ZhimengUserCenterActivity.this);
		UserClickService clickService = new UserClickService(this, checkAccount, activityService);

		switch (v.getId()) {
		case Constants.ID_BACK:// 返回键
			onBackPressed();
			break;
		case Constants.CLOSE_ID:// 关闭
			onBackPressed();
			finish();
			break;

		case Constants.ID_ALTERPASSWORD_COMMIT: // 修改密码
			clickService.alterPassword(alterPassword);
			break;

		case Constants.ID_SECRET_QUESTION:// 设置密保问题列表
			userService.initPopuWindow(this, secretData, resettingProtection,
					overProtection);
			userService.popupWindwShowing(this, Constants.SECRET_OFF);
			break;

		case Constants.ID_SECRET_COMMIT:// 设置密保提交
			clickService.setSecretCommit(resettingProtection, Constants.A_MODI, this);
			break;

		case Constants.ID_NEW_SECRET_QUESTION:// 修改密保问题列表
			userService.initPopuWindow(this, secretData, resettingProtection,
					overProtection);
			userService.popupWindwShowing(this, Constants.SECRET_ON);
			break;

		case Constants.ID_NEW_SECRET_COMMIT:// 修改密保提交
			clickService.alterSecretCommit(overProtection, Constants.A_ALTER_MODI, this);
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// finish();
		activityService.listenerBack(Constants.BACK_USER);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
		switch (position) {
		case Constants.ALTER_PASS_ITEM:// 修改密码
			alterPassword = new AlterPassword(ZhimengUserCenterActivity.this);
			alterPassword.setButtonClickListener(this);
			activityService.pushView2Stack(alterPassword);
			break;

		case Constants.SECRET_ITEM:// 密保
			clickSecret();
			break;

		case Constants.SERVICE_ITEM:// 联系客服
			contactService();
			break;

		default:
			break;
		}
	}

	/**
	 * 密保
	 */
	private void clickSecret() {
		if (!NetworkImpl.isNetworkConnected(ZhimengUserCenterActivity.this)) {
			Utils.toastInfo(ZhimengUserCenterActivity.this, "网络连接失败，请检查网络设置");
			return;
		}

		/* 初始化密保数组 */
		secretData = userService.initSecretDataArray();
//		if ("true".equals(YouaiAppService.mSession.bindMObile)) {// 设置过密保
//			/*overProtection = new OverProtection(UserCenterActivity.this,
//					this.secretData);*/
//			overProtection = OverProtection.getOverProtection(ZhimengUserCenterActivity.this,
//					this.secretData,activityService);
//			overProtection.setActivity(this);
//			overProtection.setConfirmListener(ZhimengUserCenterActivity.this);
//			overProtection.setOptionListener(ZhimengUserCenterActivity.this);
//			activityService.pushView2Stack(overProtection);
//		} else {
//			resettingProtection = ResettingProtection.getResettingProtection(
//					ZhimengUserCenterActivity.this, secretData,activityService);
//			resettingProtection.setActivity(this);
//			resettingProtection.setButtonClickListener(ZhimengUserCenterActivity.this);
//			activityService.pushView2Stack(resettingProtection);
//		}
	}

	/**
	 * 客服
	 */
	private BaseData contactService() {
		BaseData baseDate = null;
		if (YouaiAppService.basicDate == null) {
			// 重新获取基础数据
			GetDataImpl data_impl = GetDataImpl
					.getInstance(ZhimengUserCenterActivity.this);
			baseDate = data_impl.online();
			if (baseDate == null) {
				Utils.toastInfo(ZhimengUserCenterActivity.this, "初始化数据失败！");
				return null;
			}

		}

		ContactLayout contact = new ContactLayout(ZhimengUserCenterActivity.this);
		contact.setConfirmListener(ZhimengUserCenterActivity.this);
		activityService.pushView2Stack(contact);
		return baseDate;
	}

}
