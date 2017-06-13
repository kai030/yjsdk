package com.yj.listener;

import android.app.Activity;
import android.content.Context;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yj.entity.Session;
import com.yj.sdk.YJManage;
import com.yj.util.TWXLoginTask;
import com.yj.util.ToastUtils;
import com.yj.util.Utils;

/**
 * Created by Frank on 2017/5/25 0025.
 */

public class WXEventHandler implements IWXAPIEventHandler {
    private Context mContext;
    public WXEventHandler(Context context){
        mContext = context;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        String code = "";
        String result = "";
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                code = ((SendAuth.Resp) resp).code;
                Session.getInstance().code = code;
                new TWXLoginTask(mContext).execute();
//                ToastUtils.toastShow(YJManage.getApplication(),"授权成功");
                result = "成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if(mContext != null) {
                    ((Activity) mContext).finish();
                }
                result = "取消";
                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                result = "ERR_AUTH_DENIED";
//                break;
//            case BaseResp.ErrCode.ERR_UNSUPPORT:
//                result = "ERR_UNSUPPORT";
//                break;
            default:
                if(mContext != null) {
                    ((Activity) mContext).finish();
                }
                ToastUtils.toastShow(YJManage.getApplication(),"授权失败");
                result = "default";
                break;
        }
        Utils.fengLog("onResp onResp result: "+result);
        Utils.fengLog("onResp:"+resp.toString()
                + "   openid:"+resp.openId
                + "   transaction:"+resp.transaction
                + "   type:" + resp.getType()
                + "    code:"+code
        );
    }
}
