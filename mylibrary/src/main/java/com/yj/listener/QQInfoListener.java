package com.yj.listener;

import android.content.Context;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.yj.util.Utils;

/**
 * Created by Frank on 17/5/14.
 */

public class QQInfoListener implements IUiListener {

  private Context context;

  public QQInfoListener(Context context){
    this.context = context;
  }

  @Override
  public void onComplete(Object o) {
    Utils.fengLog("QQInfoListener onComplete:"+o.toString());
  }

  @Override
  public void onError(UiError uiError) {

  }

  @Override
  public void onCancel() {

  }
}
