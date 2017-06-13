package com.yj.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author lufengkai 
 */
public class NetworkImpl {
	
	/** 没有网络或其他网络 */
	private static final String NETWORKTYPE_INVALID = "6";
	
	/** wifi网络 */
	private static final String NETWORKTYPE_WIFI ="1";
	
	/** 2.5G网络 */
	private static final String NETWORKTYPE_WAP ="2";
	/** 2G网络 */
	private static final String NETWORKTYPE_2G = "3";
	/** 3G和3G以上网络，或统称为快速网络 */
	private static final String NETWORKTYPE_3G ="4";
	
	
	
//	public static HttpClient getHttpClient(Context ctx) {
//		String networkTypeName = getNetworkTypeName(ctx);
//		if (networkTypeName == null) {
//			return null;
//		}
//		HttpClient client = null;
//		if (isCmwapType(ctx)) {
//			HttpParams httpParams = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(httpParams, 30 * 1000);
//			HttpConnectionParams.setSoTimeout(httpParams, 30 * 1000);
//			HttpConnectionParams.setSocketBufferSize(httpParams, 100 * 1024);
//			HttpClientParams.setRedirecting(httpParams, true);
//			HttpHost host = new HttpHost("10.0.0.172", 80);
//			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
//			client = new DefaultHttpClient(httpParams);
//		} else {
//			client = new DefaultHttpClient();
//			HttpParams httpParams = client.getParams();
//			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
//					30 * 1000);
//			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);
//		}
//		return client;
//	}
	
	private static boolean isCmwapType(Context ctx) {
		ConnectivityManager mgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = mgr.getActiveNetworkInfo();
		String extraInfo = activeNetworkInfo.getExtraInfo();
		if (extraInfo == null) {
			return false;
		}
		return "cmwap".equalsIgnoreCase(extraInfo)
				|| "3gwap".equalsIgnoreCase(extraInfo)
				|| "uniwap".equalsIgnoreCase(extraInfo);
	}
	
	/**
	 * 获取网络名称
	 * @param ctx
	 * @return
	 */
	private static String getNetworkTypeName(Context ctx) {
		ConnectivityManager mgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = mgr.getActiveNetworkInfo();
		if (activeNetworkInfo == null) {
			return null;
		}
		String extraInfo = activeNetworkInfo.getExtraInfo();
		if (extraInfo != null && extraInfo.length() > 0) {
			return extraInfo;
		}
		return activeNetworkInfo.getTypeName();
	}

	
	
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		Log.i("feng", "context:" + context);
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	/**
	 * 获取网络类型
	 * @return
	 */
	public static String getNetworkType(Context context){
		
		String net = NETWORKTYPE_INVALID;
		
		if(!isNetworkConnected(context)){//假如无网络连接 6
			return net;
		}
		
		try {
			ConnectivityManager conMan = (ConnectivityManager) context
					 .getSystemService(Context.CONNECTIVITY_SERVICE);
			 NetworkInfo info = conMan.getActiveNetworkInfo();

			 if(isNetworkConnected(context)){
				 if (info.getType() == ConnectivityManager.TYPE_WIFI) {
					 
					 net = NETWORKTYPE_WIFI;//wifi
					 
					} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//手机卡网络
						
						int mobtile = info.getSubtype();
						if(mobtile == TelephonyManager.NETWORK_TYPE_GPRS
								|| mobtile == TelephonyManager.NETWORK_TYPE_CDMA){
							
							net = NETWORKTYPE_2G;//2g
							
					}else if(mobtile == TelephonyManager.NETWORK_TYPE_EDGE){
						net = NETWORKTYPE_WAP;//2.5g
						
					}else{
						net = NETWORKTYPE_3G;//3g
					}
				 
			 }else{
				 net = "6";//其他
			 }
			 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	     /* if(info !=null && info.getType() ==  ConnectivityManager.TYPE_MOBILE)
	      {*/

	       // NETWORK_TYPE_EVDO_A是电信3G

	      //NETWORK_TYPE_EVDO_A是中国电信3G的getNetworkType

	      //NETWORK_TYPE_CDMA电信2G是CDMA

	      //移动2G卡 + CMCC + 2//type = NETWORK_TYPE_EDGE

	      //联通的2G经过测试 China Unicom   1 NETWORK_TYPE_GPRS

     return net;
	}
	
	/*public static HttpClient getHttpClient(Context ctx) {
		String networkTypeName = getNetworkTypeName(ctx);
		if (networkTypeName == null) {
			return null;
		}
		HttpClient client = null;
		if (isCmwapType(ctx)) {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 30 * 1000);
			HttpConnectionParams.setSoTimeout(httpParams, 30 * 1000);
			HttpConnectionParams.setSocketBufferSize(httpParams, 100 * 1024);
			HttpClientParams.setRedirecting(httpParams, true);
			HttpHost host = new HttpHost("10.0.0.172", 80);
			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
			client = new DefaultHttpClient(httpParams);
		} else {
			client = new DefaultHttpClient();
			HttpParams httpParams = client.getParams();
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
					30 * 1000);
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);
		}
		return client;
	}*/
	
	public static int checkNetworkAvailableType(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null)
			return -1;

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return -1;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						NetworkInfo netWorkInfo = info[i];
						if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
							return 1;
						} else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
							return 2;
						}

					}
				}
			}
			/*NetworkInfo info1 = connectivityManager.getActiveNetworkInfo();
			if (info1 != null && info1.isAvailable()) {
				return 3;
			}*/
		}
		return -1;
	}

}
