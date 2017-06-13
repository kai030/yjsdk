package com.yj.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	
	private static Toast toast;
	
	public static void toastShow(Context context, String content){
		if(context == null){
			return;
		}
		if(toast == null){
			toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_LONG);
		}else{
			toast.setText(content);
		}
		toast.show();
	}

}
