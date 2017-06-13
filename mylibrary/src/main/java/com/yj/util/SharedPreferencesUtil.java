package com.yj.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yj.entity.Constants;

/**
 * Created by Frank on 2017/4/21 0021.
 */

public class SharedPreferencesUtil {

    private static SharedPreferences userPreferences;

    public static SharedPreferences getUserSharedPreferences(Context context){
        if(userPreferences == null){
            userPreferences = context.getApplicationContext().getSharedPreferences(Constants.USER_XML, Context.MODE_PRIVATE);
        }
        return userPreferences;
    }

    public static void saveAccountToXml(Context context, String account){
        SharedPreferences preferences = getUserSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USER_ACCOUNTS,account);
        editor.apply();
    }

    public static void savePwdToXml(Context context, String pwd){
        SharedPreferences preferences = getUserSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USER_PASSWORD,pwd);
        editor.apply();
    }

    public static void saveUidToXml(Context context, String uid){
        SharedPreferences preferences = getUserSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USER_UID,uid);
        editor.apply();
    }

    public static void saveAutoLoginToXml(Context context, String flag){
        SharedPreferences preferences = getUserSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USER_AUTOL_LOGIN,flag);
        editor.apply();
    }

    public static String getXmlAccount(Context context){
        SharedPreferences preferences = getUserSharedPreferences(context);
        return preferences.getString(Constants.USER_ACCOUNTS,"");
    }

    public static String getXmlAutoLogin(Context context){
        SharedPreferences preferences = getUserSharedPreferences(context);
        return preferences.getString(Constants.USER_AUTOL_LOGIN,Constants.YES);
    }

    public static String getXmlPwd(Context context){
        SharedPreferences preferences = getUserSharedPreferences(context);
        return Utils.decode(preferences.getString(Constants.USER_PASSWORD,""));
    }

    public static void cleanXmlPwd(Context context){
        SharedPreferences preferences = getUserSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Constants.USER_PASSWORD);
        editor.apply();
    }

    public static String getXmlUid(Context context){
        SharedPreferences preferences = getUserSharedPreferences(context);
        return preferences.getString(Constants.USER_UID,"");
    }

}
