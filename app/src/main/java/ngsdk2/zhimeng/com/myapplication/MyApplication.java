package ngsdk2.zhimeng.com.myapplication;

import android.app.Application;

import com.yj.sdk.YJManage;

/**
 * Created by Frank on 2017/5/22 0022.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YJManage.getInstance().init(this,"a123456");
        YJManage.getInstance().registerTencentAppid("wxc36a32b56aaf9b94","1106088295");
    }

}
