package com.yj.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.yj.entity.Constants;


public class CustomProgressDialog extends ProgressDialog {

  private Context mContext;
  private TextView tvLoad;
  private String mLoadContent;

  public CustomProgressDialog(Context context) {
    super(context, MResource.getIdByName(context,"style","CustomDialog"));
    this.mContext = context;
  }

  public CustomProgressDialog(Context context,String loadContent) {
    super(context, MResource.getIdByName(context,"style","CustomDialog"));
    this.mContext = context;
    mLoadContent = loadContent;
  }

  public CustomProgressDialog(Context context, int theme) {
    super(context, theme);
    this.mContext = context;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
  }

//  public void setCancelable(boolean cancelable){
//    //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
//    setCancelable(cancelable);
//  }
//
//  public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside){
//    //返回键
//    setCanceledOnTouchOutside(canceledOnTouchOutside);
//  }

  private void init() {

    setCancelable(false);
    setCanceledOnTouchOutside(false);
    setContentView(MResource.getIdByName(getContext(), Constants.LAYOUT,"view_tips_loading"));
    WindowManager.LayoutParams params = getWindow().getAttributes();
    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    getWindow().setAttributes(params);

    tvLoad = (TextView) findViewById(MResource.getIdByName(getContext(),Constants.ID,"tv_load_dialog"));
      if(!TextUtils.isEmpty(mLoadContent)){
        tvLoad.setText(mLoadContent);
      }
  }

  @Override
  public void show() {
    super.show();
  }
}