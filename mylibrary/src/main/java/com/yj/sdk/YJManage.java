package com.yj.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.fanwei.jubaosdk.shell.FWPay;
import com.yj.entity.ChargeData;
import com.yj.entity.Constants;
import com.yj.entity.DeviceProperties;
import com.yj.entity.Session;
import com.yj.ui.LoginLayout;
import com.yj.util.LogLoginTask;
import com.yj.util.LoginService;
import com.yj.util.LoginTask;
import com.yj.util.NetworkImpl;
import com.yj.util.PayService;
import com.yj.util.SharedPreferencesUtil;
import com.yj.util.ToastUtils;
import com.yj.util.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author lufengkai
 * @date 2015年5月27日
 * @copyright
 */

public class YJManage {

    private static Context mContext;


    private static YJManage instance = null;


    public static Context getApplication() {
        return mContext;
    }

    // public YJResultListen resultListen;
    // private Context context = null;

//	private Context context;
//
//	public Context getContext() {
//		return this.context;
//	}

    /**
     * 实例
     */
    private YJManage() {
    }

    /**
     * 单例
     */
    public static YJManage getInstance() {
        if (instance == null) {
            instance = new YJManage();
        }
        return instance;
    }


    /**
     * 显示登录界面
     */
    public void yjLogin(Context context, YJResultListen resultListen) {

        if (context == null) {
            Utils.fengLog("context不能为空");
            return;
        }
        if (resultListen == null) {
            ToastUtils.toastShow(context, "登录监听器不能为空");
            return;
        }
        YouaiAppService.resultListen = resultListen;
        //登录处理
        LoginService loginService = LoginService.getInit(context);
            /* 获取本机登录记录 */
        Session session = loginService.getLoginRecord(context);


//			loginLayout = new LoginLayout(this);// 添加登录界面布局
//			loginLayout.setStartGameListener(this);// 传点击监听
        String autolLogin = SharedPreferencesUtil.getXmlAutoLogin(context);
        if (session.password != null
                && session.password.length() >= YouaiAppService.min
                && Constants.YES.equals(autolLogin)) {//自动登陆
            new LoginTask(context, session.userAccount, session.password).execute();
        } else {
            Utils.fengLog("显示登录界面");
            LoginService.getInit(context).openActivity(
                    YJLoginActivity.class);
        }

		/* 网络不可用 */
        if (!NetworkImpl.isNetworkConnected(context)) {
            Utils.toastInfo(context, Constants.NETWORK_FAILURE);
        }
    }

    public void init(Context context, String appid) {
        mContext = context;
        FWPay.init(context, "51370112", true);
        Session.getInstance().gameName = appid;
    }

    public void registerTencentAppid(String wxAppid,String qqAppid){
        Constants.WX_APP_ID = wxAppid;
        Constants.QQ_APP_ID = qqAppid;
    }


    /**
     * 打开新的Activity
     */
    public void openActivity(Context context, Class<?> className) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, className);
        context.startActivity(intent);
    }


    public void sendServerId(Context context,String serverId){
        Session.getInstance().serverName = serverId;
        new LogLoginTask(context).execute();
    }

    /**
     *
     * @param context 上下文
     * @param money 金额
     * @param cpOrderId cp订单号
     * @param serverId 服务器id
     * @param playerId 角色id
     * @param playerName 角色名称
     * @param goodsId 商品id
     * @param goodsName 商品名称
     * @param vipLevel vip等级
     * @param callBackInfo 自定义
     */
    public void yjPayment(Context context, final double money,String cpOrderId,
                          String serverId, String playerId,String playerName, String goodsId,final String goodsName,
                          String vipLevel,final String callBackInfo) {
        Session.getInstance().money = money + "";
        Session.getInstance().cpOrderId = cpOrderId;
        Session.getInstance().serverName = serverId;
        Session.getInstance().playerId = playerId;
        Session.getInstance().tradeId = goodsId;
        Session.getInstance().vipLevel = vipLevel;
        try {
            Session.getInstance().playerName = URLEncoder.encode(playerName, "utf-8");
            Session.getInstance().tradeName = URLEncoder.encode(goodsName, "utf-8");
            Session.getInstance().commodiyName = URLEncoder.encode(callBackInfo, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PayActivity.setPaymentParam();
//		setChargeData(serverId, money, callBackInfo, tradeName);
        PayService.getExample(context).showPaymentView(context);

    }

    /**
     * 保存数据
     */
    private void setChargeData(String serverId, final double money,
                               final String callBackInfo, final String tradeName) {

        Utils.youaiLog("serverName:" + serverId + "    money:" + money + "  "
                + "    callBackInfo:" + callBackInfo + "   tradeName:"
                + tradeName);

        ChargeData chargeData = ChargeData.getChargeData();
        chargeData.telephoneNumber = Utils.getPhoneNum(mContext);
        chargeData.serverName = serverId;
        chargeData.money = money + "";
        chargeData.tradeId = callBackInfo.split("-")[0];
        // chargeData.roleLevel = roleLevel;
        chargeData.tradeName = tradeName;
        chargeData.cardDenomination = money + "";// 面额
        chargeData.gameName = Utils.getApplicationName(mContext);
    }

    /**
     * 显示浮动窗口
     *
     * @param context
     */
    public void showYJFloatView(Context context) {
        Utils.fengLog("(YouaiAppService.isLogin:" + YouaiAppService.isLogin);
        if (YouaiAppService.isLogin && YouaiAppService.resultListen != null) {
            MyWindowManager.createSmallWindow(context, 0, 300);
        }
    }

    /**
     * 隐藏浮动框
     */
    public void removeYJFloatView(Context context) {
        Utils.youaiLog("removeFloatView");
        MyWindowManager.removeSmallWindow();
    }

    /**
     * 退出登录
     *
     * @return
     */
    public boolean outLogin() {
        SharedPreferencesUtil.cleanXmlPwd(mContext);
        YouaiAppService.isLogin = false;
        MyWindowManager.removeSmallWindow();
        return !YouaiAppService.isLogin;
    }

    public void destroy() {
        YouaiAppService.isLogin = false;
        MyWindowManager.destroy();
    }
/**
 ..     [1, 2, 3, 4],
 ...     [5, 6, 7, 8],
 ...     [9, 10, 11, 12],
 ... ]
 以下实例将3X4的矩阵列表转换为4X3列表：
 >>> [[row[i] for row in matrix] for i in range(4)]
 [[1, 5, 9], [2, 6, 10], [3, 7, 11], [4, 8, 12]]* >>> matrix = [
 .
 */

}
