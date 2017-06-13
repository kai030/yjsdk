package com.yj.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.fanwei.jubaosdk.common.util.ToastUtil;
import com.fanwei.jubaosdk.shell.FWPay;
import com.fanwei.jubaosdk.shell.PayOrder;
import com.yj.entity.Constants;
import com.yj.entity.PaySession;
import com.yj.entity.Session;
import com.yj.sdk.FWPayResult;

import com.yj.util.JbTask;
import com.yj.util.ToastUtils;
import com.yj.util.Utils;
import com.yj.util.YJPayTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;


/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public class PayChoiceLayout extends ParentLayout implements OnClickListener {

    private PayOrder payOrder;
    private Context mContext;
//    public static int chargeType;
    public static final int WX = 1;
    public static final int ALIPAY = 2;
    public static final int JB = 3;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ToastUtils.toastShow(mContext, "网络错误");
                    break;
                case 1:
                    try {
                        String channelType = (String) msg.obj;
                        Utils.fengLog("channelType handleMessage:"+channelType);
                        if ("[]".equals(channelType)) {
                            ToastUtils.toastShow(mContext, "未开通支付通道");
                            return;
                        }
                        JSONArray array = new JSONArray(channelType);
                        // 在这里只取json数组的第一位，用户可自定义使用相对应的值
                        int index = array.getInt(0);
//                        Utils.fengLog(" handleMessage index:"+chargeType);
                        // 主线程中调用 FWPay.pay 方法
                        FWPay.pay((Activity) mContext, payOrder, Session.getInstance().chargeType, new FWPayResult());
                        ( (Activity) mContext).finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtil.showToast(mContext, "支付通道配置错误", Toast.LENGTH_SHORT);
                    }
                    break;
            }
        }
    };

    public PayChoiceLayout(Activity context) {
        super(context, MResource.getIdByName(context, Constants.LAYOUT,"pay_choice_layout"));
        mContext = context;
        initUi();
    }

    private void initUi(){
        View backView = view.findViewById(MResource.getIdByName(context, Constants.ID,"view_back"));
        backView.setOnClickListener(this);

        ImageView ivAlipay = (ImageView) view.findViewById(MResource.getIdByName(context, Constants.ID,"pay_choice_icon_alipay"));
        ivAlipay.setOnClickListener(this);

        ImageView ivWx = (ImageView) view.findViewById(MResource.getIdByName(context, Constants.ID,"pay_choice_icon_wx"));
        ivWx.setOnClickListener(this);

        ImageView ivJb = (ImageView) view.findViewById(MResource.getIdByName(context, Constants.ID,"pay_choice_icon_jb"));
        ivJb.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int i = v.getId();
        if (i == MResource.getIdByName(context, Constants.ID,"pay_choice_icon_alipay")) {
//            chargeType = ALIPAY;
            Session.getInstance().chargeType = ALIPAY;
            setPayInfo();
//            ToastUtils.toastShow(context, "pay_choice_icon_alipay");

        } else if (i == MResource.getIdByName(context, Constants.ID,"pay_choice_icon_wx")) {
//            chargeType = WX;
            Session.getInstance().chargeType = WX;
            setPayInfo();
//            ToastUtils.toastShow(context, "pay_choice_icon_wx");

        } else if (i == MResource.getIdByName(context, Constants.ID,"pay_choice_icon_jb")) {
            JbTask jbTask = new JbTask(context);
            jbTask.execute();

        } else if (i == MResource.getIdByName(context, Constants.ID,"view_back")) {
            ((Activity) context).finish();
        } else {}

        new YJPayTask(context).execute();
    }

  private void setPayInfo(){
      String amount = Session.getInstance().money;
      Float fAmount = Float.parseFloat(amount);
      DecimalFormat df = new DecimalFormat("0.00");
      amount = df.format(fAmount);
      Utils.fengLog("amount4:"+amount);
      payOrder = new PayOrder();
      payOrder.setAmount(amount);
//        payOrder.setChannelId("b");
      //商户订单号(必需)
      String orderCode = System.currentTimeMillis()+Session.getInstance().serverName+Session.getInstance().playerId;
      Session.getInstance().payId = orderCode;
      payOrder.setPayId(orderCode);
      payOrder.setGoodsName(Session.getInstance().tradeName);
      //玩家Id(必需)
      payOrder.setPlayerId(Session.getInstance().playerId);
      PaySession paySession = new PaySession(Session.getInstance().gameName
                                                     ,Session.getInstance().serverName,Session.getInstance().userAccount
                                                              ,Session.getInstance().uid);
      payOrder.setRemark(paySession.buildJson().toString());

      new Thread(new Runnable() {
          @Override
          public void run() {
              String channelType = FWPay.getChannelType(payOrder);
              Message message = Message.obtain();
              message.obj = channelType;
              message.what = 1;
              mHandler.sendMessage(message);
          }
      }).start();
  }


}
