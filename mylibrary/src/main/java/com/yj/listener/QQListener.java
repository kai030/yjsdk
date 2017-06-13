package com.yj.listener;

import android.content.Context;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.yj.entity.Session;
import com.yj.util.TQQLoginTask;
import com.yj.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Frank on 17/5/13.
 */

public class QQListener implements IUiListener {
  private Context context;

  public QQListener(Context context){
    this.context = context;
  }

  @Override
  public void onComplete(Object o) {
    Utils.fengLog("onComplete onComplete : " + o.toString());
//
    try {
      JSONObject jsonObject = new JSONObject(o.toString());
      Session.getInstance().access_token = jsonObject.getString("access_token");
//      expires_in = jsonObject.getString("expires_in");
      Session.getInstance().openId = jsonObject.getString("openid");

      new TQQLoginTask(context).execute();
//      YJLoginActivity.mTencent.setOpenId(openId);
//      YJLoginActivity.mTencent.setAccessToken(access_token,expires_in);
    } catch (JSONException e) {
      e.printStackTrace();
    }
////    getUserInfoInThread();
//    UserInfo userInfo = new UserInfo(context, YJLoginActivity.mTencent.getQQToken());
//    userInfo.getUserInfo(new QQInfoListener(context));
//    Utils.fengLog("onComplete UserInfo  : " + userInfo.toString());
  }

  @Override
  public void onError(UiError uiError) {
    Utils.fengLog("UiError");
    Utils.fengLog("onError onError 3 : " + uiError.toString());
  }

  @Override
  public void onCancel() {
    Utils.fengLog("onCancel");
  }

//  public void getUserInfoInThread()
//  {
//    new Thread(){
//      @Override
//      public void run() {
//        JSONObject json = null;
//        try {Constants.gra
//          json = YJLoginActivity.mTencent.request("get_simple_userinfo", null, Constants.HTTP_GET);
//        } catch (IOException e) {
//          e.printStackTrace();
//        } catch (JSONException e) {
//          e.printStackTrace();
//        } catch (HttpUtils.NetworkUnavailableException e) {
//          e.printStackTrace();
//        } catch (HttpUtils.HttpStatusException e) {
//          e.printStackTrace();
//        }
//        Utils.fengLog("getUserInfoInThread:"+json.toString());
//        System.out.println(json);
//      }
//    }.start();
//  }
}
