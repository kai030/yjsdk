package com.yj.sdk;

import android.app.Application;

import com.fanwei.jubaosdk.shell.FWPay;

/**
 * Created by Frank on 2017/5/18 0018.
 */

public class YjApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();//wx1cb7e61eb868bea3
        FWPay.init(this,"14081714462317168447",true);
//        FWPay.get
    }
}
