package com.yj.sdk;

import com.fanwei.jubaosdk.common.core.OnPayResultListener;
import com.yj.entity.Session;
import com.yj.ui.PayChoiceLayout;
import com.yj.util.Utils;

/**
 * Created by Frank on 2017/5/18 0018.
 */

public class FWPayResult implements OnPayResultListener {
    public final int PAY_SUCCESS = 0;
    public final int PAY_FAILED = 1;
    public final int PAY_CANCEL = 2;



    @Override
    public void onSuccess(Integer code, String message, String payId) {
        Utils.fengLog("FWPayResult onSuccess ： " + Session.getInstance().chargeType + "   message: "+message
         + "   code: " + code  +  "  payId  : "+payId);
        switch (code){
            case PAY_SUCCESS:
                //成功
                break;
            case PAY_FAILED:
                //失败
                break;
            case PAY_CANCEL:
                //取消
                break;
        }
    }

    @Override
    public void onFailed(Integer code, String message, String payId) {
        Utils.fengLog("FWPayResult onFailed ： "+ Session.getInstance().chargeType+ "  message: "+message
                + "   code: " + code  +  "  payId  : "+payId);
    }

}
