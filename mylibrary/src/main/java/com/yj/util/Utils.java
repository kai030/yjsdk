package com.yj.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yj.entity.Constants;
import com.yj.entity.Flag;
import com.yj.entity.PayChannel;
import com.yj.sdk.YJTisActivity;
import com.yj.sdk.YouaiAppService;
import com.yj.webview.CommonWebViewActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *@author lufengkai  
 */
@SuppressWarnings("WrongConstant")
public class Utils {
	public static boolean request = true;
	private final static Pattern PATTERN = Pattern.compile("\\d+");
	private final static String KEY = "www.youai.com";
	private static InputMethodManager imm;


	public static void opneWebvieAvtivity(Context context,String title,String url){
		Intent intent = new Intent(context, CommonWebViewActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(CommonWebViewActivity.WEBVIEW_TITLE,title);
		bundle.putString(CommonWebViewActivity.WEBVIEW_URL,url);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}



	/**
	 * 字体加粗
	 * @param textView
	 */
	public static void setBold(TextView textView){
		TextPaint paint = textView.getPaint();
		paint.setFakeBoldText(true);
	}

	public static void openWebpage(String url, Context context){
		  Uri uri = Uri.parse(url);
		   Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		   context.startActivity(intent);

	}
	/**
	 * xml中保存youaiid的键
	 */
	private static final String KEY_DOU_ID = "yd";
	
	/**
	 * xml 文件名 ，记录imsi douid...
	 */
	private static final String XML_YOUAI_DQ = "ya";
	
	/**
	 * 
	 * @param value ： 手机号码
	 * @return
	 */
	 public static boolean isInteger(String value) {
		  try {
			  if(value == null || value.length() != 11){
				  return false;
			  }
		   Long.parseLong(value);
		   return true;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }

	
	/**
	 * @author lufengkai 2014-7-15
	 * @param plainText
	 * @return   MD5加密
	 */
	public final static String calc(final String plainText ) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("utf-8"));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if(i<0) i+= 256;
				if(i<16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			md.reset();
			return buf.toString().toLowerCase();//32位的加密
		} catch (NoSuchAlgorithmException e) {
			youaiLog("系统加密异常" + e);
		} catch (UnsupportedEncodingException e) {
			youaiLog("系统加密异常编码" + e);
		}
		return null;
	}
	
	public static boolean formatMoney(String money) {

		if (null == money || "".equals(money))
			return false;
		String format = "^(?!0(\\d|\\.0+$|$))\\d+(\\.\\d{1,2})?$";
		if (money.matches(format)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static String getYouAiId(Context ctx) {
		PackageManager pm = ctx.getPackageManager();
		String douId = null;

		File file = null;
		// 读取SDcard
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// sdcard上保存的路径
			file = new File(Environment.getExternalStorageDirectory(),
					"/Android/youai/data/code/" + ctx.getPackageName() + "/YID.DAT");
			if (file.exists()) {
				FileInputStream fis = null;
				BufferedReader reader = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(file);
					isr = new InputStreamReader(fis);
					reader = new BufferedReader(isr);
					String temp = null;
					if ((temp = reader.readLine()) != null) {
						douId = decode(temp);
						writeDouId2xml(ctx, douId);
						return douId;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (isr != null) {
						try {
							isr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
//		 读取程序本地缓存
		SharedPreferences prefs = ctx.getSharedPreferences(XML_YOUAI_DQ,
                                                                   Context.MODE_PRIVATE);
		String tmp = prefs.getString(KEY_DOU_ID, null);
		if (tmp != null && !"".equals(tmp)) {
			douId = decode(tmp);
			if(!"0".equals(douId))
			writeDouId2File(ctx, file, douId);
			return douId;
		}

		try {
			douId = reflectDouId(ctx);
			if (StringUtils.isEmpty(douId)) {
				ApplicationInfo appInfo = pm.getApplicationInfo(
                                    ctx.getPackageName(), PackageManager.GET_META_DATA);
				Bundle metaData = appInfo.metaData;
				douId = "" + metaData.getInt("YOUAI_ID");
			}

			if (!StringUtils.isEmpty(douId) && !"0".equals(douId)) {
				writeDouId2File(ctx, file, douId);
				writeDouId2xml(ctx, douId);
				return douId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		return "-1";
	}
	
	/**
	 * 保存帐号密码到SD卡
	 * @param user
	 * @param pw
	 */
	public static void writeAccount2SDcard(String user, String pw) {
		youaiLog("writeAccount2SDcard");
		if (user == null || pw == null) {
			return;
		}
		// 账号与密码用||分开
		String data = user + "||" + pw;
		String encodeData = encode(data);
		File dir = new File(Environment.getExternalStorageDirectory(),
                                    Constants.ACCOUNT_PASSWORD_FILE.substring(0,
						Constants.ACCOUNT_PASSWORD_FILE.lastIndexOf("/")));
		if (!dir.exists() || dir.isFile()) {
			if (!dir.mkdirs()) {
				return;
			}
		}
		File file = new File(dir, Constants.ACCOUNT_PASSWORD_FILE.substring(
				Constants.ACCOUNT_PASSWORD_FILE.lastIndexOf("/") + 1,
				Constants.ACCOUNT_PASSWORD_FILE.length()));
		if (file.exists() && file.isFile()) {
			// 将原文件删除
			file.delete();
		}
		OutputStream out = null;
		try {
		    out = new FileOutputStream(file);
			out.write(encodeData.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(out != null)
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 调用tis界面
	 */
	public static void showTisView(Context context,String content){
		Intent intent = new Intent(context, YJTisActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(YJTisActivity.TIS_CONTENT,content);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	
	/**
	 * 把帐号密码保存到xml
	 */
	public static void writeAccountXml(Context context, String account, String password,String uid){
		SharedPreferencesUtil.saveAccountToXml(context,account);
		SharedPreferencesUtil.savePwdToXml(context,encode(password));
		SharedPreferencesUtil.saveUidToXml(context,uid);
//		commitSharedString(preferences, Constants.AUTOL_LOGIN_XML, "yes");
	}
	
	/**
	 * 保存youaiID到SD卡
	 * @param ctx
	 * @param file
	 * @param douId
	 */
	private static void writeDouId2File(Context ctx, File file, String douId) {
		if (file == null || douId == null)
			return;
		// 把imsi写到sdcard
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter writer = null;
		try {
			File parent = file.getParentFile();
			if (!parent.exists())
				parent.mkdirs();
			file.createNewFile();
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);
			writer = new BufferedWriter(osw);
			writer.write(encode(douId));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将douId加密写到xml文件
	 * 
	 * @param ctx
	 * @param douId
	 */
	private static void writeDouId2xml(Context ctx, String douId) {
		if (douId == null) {
			return;
		}
		SharedPreferences prefs = ctx.getSharedPreferences(XML_YOUAI_DQ,
                                                                   Context.MODE_PRIVATE);
		prefs.edit().putString(KEY_DOU_ID, encode(douId)).commit();
	}
	
	/**
	 * 加密
	 * @param src
	 * @return
	 */
	public static String encode(String src) {
		try {
			byte[] data = src.getBytes("utf-8");
			byte[] keys = KEY.getBytes();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < data.length; i++) {
				int n = (0xff & data[i]) + (0xff & keys[i % keys.length]);
				sb.append("%" + n);
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return src;
	}
	
	
	/**
	 * 解密
	 * @param src
	 * @return
	 */
	public static String decode(String src) {
		if (src == null || src.length() == 0) {
			return src;
		}
		Matcher m = PATTERN.matcher(src);
		List<Integer> list = new ArrayList<Integer>();
		while (m.find()) {
			try {
				String group = m.group();
				list.add(Integer.valueOf(group));
			} catch (Exception e) {
				e.printStackTrace();
				return src;
			}
		}

		if (list.size() > 0) {
			try {
				byte[] data = new byte[list.size()];
				byte[] keys = KEY.getBytes();

				for (int i = 0; i < data.length; i++) {
					data[i] = (byte) (list.get(i) - (0xff & keys[i
							% keys.length]));
				}
				return new String(data, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return src;
		} else {
			return src;
		}
	}
	
	/**
	 * 反射DouGameID
	 * 
	 * @param ctx
	 * @return
	 */
	public static String reflectDouId(Context ctx) {
		try {
			Class<?> c = Class.forName("com.youai.sdk.YouaiConfig");
			Method method = c.getDeclaredMethod("getYouaiID", Context.class);
			method.setAccessible(true);
			Object o = c.newInstance();
			Object douId = method.invoke(o, ctx);
			if (douId != null && douId instanceof String) {
				return (String) douId;
			}
			return null;
		} catch (Exception e) {
			Log.d("youai_sdk", e.getClass().getName());
		}
		return null;
	}

	/**
	 * 获取 SharedPreferences
	 * @param context
	 * @return
	 */
	public static SharedPreferences getXmlShared(Context context, String xmlName){
		return context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
	}

	/**
	 * 提交String参数
	 */
	public static void commitSharedString(SharedPreferences preferences, String key, String value){
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 屏幕宽度
	 */
	public static int getScreenWidth(Context context){
		return context.getResources().getDisplayMetrics().widthPixels;
	}


	/**
	 * 屏幕高度
	 */
	public static int getScreenheight(Context context){
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static int getBorderMargin(Context context, int margin){
		int wid = getScreenWidth(context);
		int hei = getScreenheight(context);
		if(wid < hei){
			return wid/margin;
		}else{
			return hei/margin;
		}
	}

	/**
	 * 设置登录、注册框的大小
	 * @param context
	 * @return
	 */
	public static int compatibleToWidth(Context context) {

		int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
		int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
		int widDip = DimensionUtil.px2dip(context, widthPixels);
		int heiDip = DimensionUtil.px2dip(context, heightPixels);
		if (widthPixels > heightPixels) {
//			if(heiDip > 550){
//				heightPixels = (int)(heightPixels*((float)500/heiDip));
//				return (int)((float)heightPixels * 0.9f);
//			}else{
//				return (int)((float)heightPixels * 0.9f);
//			}
			return DimensionUtil.dip2px(context,385);

		} else {

			if(widDip > 550){
				widthPixels = (int)(widthPixels*((float)500/widDip));
				return (int)((float)widthPixels * 0.9f);
			}else{
				return (int)((float)widthPixels * 0.9f);

			}
		}

	}

	/**
	 *
	 * @param context
	 * @param width
	 *            宽
	 * @return
	 */
	public static int compatibleToWidth(Context context, int width) {
		// int w = width;
		int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
		int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
		if (widthPixels > heightPixels) {
			return heightPixels * width / 480;
		} else {

			return widthPixels * width / 480;
		}

	}

	/**
	 * 判断SDK是否存在
	 * @return
	 */
	public static boolean isSDExist(){
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}
	
	/**
	 * 从SD卡获取帐号密码
	 */
	public static String[] getAccountFromSDcard() {
		if(isSDExist()){
			File dir = new File(Environment.getExternalStorageDirectory(),
                                            Constants.ACCOUNT_PASSWORD_FILE.substring(0,
							Constants.ACCOUNT_PASSWORD_FILE.lastIndexOf("/")));
			File file = new File(dir, Constants.ACCOUNT_PASSWORD_FILE.substring(
					Constants.ACCOUNT_PASSWORD_FILE.lastIndexOf("/") + 1,
					Constants.ACCOUNT_PASSWORD_FILE.length()));
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				int length = (int) file.length();
				if (length == 0) {
					return null;
				}
				byte[] buf = new byte[length];
				in.read(buf);
				String data = new String(buf);
				String decodeData = decode(data);
				String[] split = decodeData.split("\\|\\|");
				if (split != null && split.length == 2) {
					return split;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 浮动窗口图片
	 * @param context
	 * @param picPressed
	 * @param picNormal
	 * @return
	 */
	public static StateListDrawable getStateListDrawable(Context context,
                                                             String picPressed, String picNormal) {
		StateListDrawable listDrawable = new StateListDrawable();
		listDrawable.addState(
				new int[] { android.R.attr.state_pressed },
				BitmapCache.getDrawable(context, Constants.ASSETS_RES_PATH
						+ picPressed));
		listDrawable.addState(
				new int[] { android.R.attr.state_selected },
				BitmapCache.getDrawable(context, Constants.ASSETS_RES_PATH
						+ picPressed));
		listDrawable.addState(
				new int[] { android.R.attr.state_enabled },
				BitmapCache.getDrawable(context, Constants.ASSETS_RES_PATH
						+ picNormal));
		return listDrawable;
	}
	
	public static StateListDrawable getStateListtNinePatchDrawable(
            Context context, String picPressed, String picNormal) {
		StateListDrawable listDrawable = new StateListDrawable();
		listDrawable.addState(new int[] { android.R.attr.state_pressed },
				BitmapCache.getNinePatchDrawable(context, picPressed));
		listDrawable.addState(new int[] { android.R.attr.state_selected },
				BitmapCache.getNinePatchDrawable(context, picPressed));
		listDrawable.addState(new int[] { android.R.attr.state_enabled },
				BitmapCache.getNinePatchDrawable(context, picNormal));
		return listDrawable;
	}
	
	public static StateListDrawable getNormalColorList(Context context,
                                                           int color, int colorNormal) {
		StateListDrawable listDrawable = new StateListDrawable();
		Drawable drawable = new ColorDrawable(color);
		Drawable drawableNormal = new ColorDrawable(colorNormal);

		listDrawable.addState(new int[] { android.R.attr.state_pressed },
				drawable);
		listDrawable.addState(new int[] { android.R.attr.state_selected },
				drawable);
		listDrawable.addState(new int[] { android.R.attr.state_enabled },
				drawableNormal);

		return listDrawable;

	}
	
	/**
	 * 打开新的Activity
	 */
	public static void openActivity(Class<?> className, Context context){
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, className); 
		context.startActivity(intent);
	}
	
	/**
	 * Toast
	 * @param context
	 * @param text
	 */
	public static void toastInfo(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void 	youaiLog(String msg){
		if(true)
		Log.i("youai", msg + "");
	}
	public static void 	fengLog(String msg){
		if(true)
		Log.i("feng", msg + "");
	}
	
	/**
	 * EditText获取焦点
	 */
	public static void getEditTextFocus(EditText mInput){
		mInput.setFocusable(true);
		mInput.setFocusableInTouchMode(true);
		mInput.requestFocus();
	}
	/**
	 * EditText失去焦点
	 */
	public static void clearEditTextFocus(EditText mInput){
		mInput.setFocusable(false);   
		mInput.setFocusableInTouchMode(false);   
		mInput.clearFocus(); 
	}
	
	/**
	 * 显示键盘
	 */
   public static void ggddfg(Context mContext){
	   InputMethodManager imm = getInputMethodManager(mContext);
	   imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	/**
	 * 隐藏/显示 键盘
	 */
	public static void setKeyboard(Context mContext){
		 InputMethodManager imm = getInputMethodManager(mContext);
		 imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	/**
	 * 键盘管理类
	 * @param mContext
	 * @return
	 */
	public static InputMethodManager getInputMethodManager(Context mContext){
		if(imm == null)
		  imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		return imm; 
	}
	
	
	/**
	 * 设置提示语
	 */
	public static void setDescr(int id){
		if(id > 0 && id <= YouaiAppService.mPayChannels.length){
                  Flag.notice = YouaiAppService.mPayChannels[id - 1].notice;//滚动
                  Flag.descr =  YouaiAppService.mPayChannels[id - 1].descr;//说明
		}
		
	}
	
	/**
	 * 卡类支付金额提示
	 */
	public static String getCardPayMoneyHint(TextView amount){
		PayChannel channel = YouaiAppService.mPayChannelsCard.get(Flag.positionCard);
		int money = 10;
		try {
			 money = Integer.valueOf(channel.selectMoney.split(",")[Flag.positionCardList]);
             if(amount != null)
			 amount.setText("对应" + Flag.moneyType + "数量：" + ((money * Flag.gameMoneyDouble * channel.payMoneyDouble) / 100) + "    " + "[兑换比例1:" + ((channel.payMoneyDouble * Flag.gameMoneyDouble) / 100) + "]");
		} catch (Exception e) {
		}
		
		return "对应" + Flag.moneyType + "数量：" + ((money * Flag.gameMoneyDouble * channel.payMoneyDouble) / 100) + "    " + "[兑换比例1:" + ((channel.payMoneyDouble * Flag.gameMoneyDouble) / 100) + "]";
		
	}
	/**
	 * 快捷支付金额提示
	 */
	public static String getFastPayMoneyHint(TextView amount, int money){
		PayChannel channel = YouaiAppService.mPayChannelsFast.get(Flag.positionFast);
		try {
//			 money = Integer.valueOf(channel.selectMoney.split(",")[Flag.position]);
             if(amount != null)
			 amount.setText("对应" + Flag.moneyType + "数量：" + ((money * Flag.gameMoneyDouble * channel.payMoneyDouble) / 100) + "    " + "[兑换比例1:" + ((channel.payMoneyDouble * Flag.gameMoneyDouble) / 100) + "]");
		} catch (Exception e) {
			// TODO: handle exception                                                                                                                                                                                                    
		}
		
		return "对应" + Flag.moneyType + "数量：" + ((money * Flag.gameMoneyDouble * channel.payMoneyDouble) / 100) + "    " + "[兑换比例1:" + ((channel.payMoneyDouble * Flag.gameMoneyDouble) / 100) + "]";
		
	}
	/*快捷支付按钮选择的金额*/
	public static int getFastMoney(){
		int money = Integer.valueOf(YouaiAppService.mPayChannelsFast.get(Flag.positionFast).selectMoney.split(",")[Flag.position]);
		if(money == 0) money = 10;
		return money;
	}
	
	/**
	 * 
	 * @param context
	 * @return macAddress
	 */
	public static String getMacAddress(Context context) {
		String macAddress = "";
		if (tm == null) {
			tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
		}
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifi ? null : wifi.getConnectionInfo());
		if (null != info) {
			macAddress = info.getMacAddress();
		}

		return macAddress;
	}
	private static TelephonyManager tm;

	/**
	 * 获取应用名称
	 * @param context
	 * @return
	 */
	public static String getApplicationName(Context context) {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getApplicationContext()
					.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}
	
	
	/**
	 * 
	 * @param context
	 * @return imei
	 */
	public static String getImei(Context context) {
		if (tm == null) {
			tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
		}
        if(isPermissionGranted((Activity) context, Manifest.permission.READ_PHONE_STATE, 0)){
            return tm.getDeviceId();
        }else {
            return "";
        }

	}

    public static boolean isPermissionGranted(Activity context , String permissionName, int questCode){
        //6.0以下系统，取消请求权限  Manifest.permission.RECORD_AUDIO
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        //判断是否需要请求允许权限，否则请求用户授权
        int hasPermision = context.checkSelfPermission(permissionName);
        if(hasPermision != PackageManager.PERMISSION_GRANTED) {
            context.requestPermissions(new String[] {permissionName}, questCode);
            return false;
        }
        return true;
    }

    /**
	 * 
	 * @param context
	 * @return imsi
	 */
	public static String getImsi(Context context) {
        if(!isPermissionGranted((Activity) context, Manifest.permission.READ_PHONE_STATE, 0)){
            return "";
        }
		String imsi = "";
		try {
			// 普通方法获取imsi
			if (tm == null) {
				tm = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
			}
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = tm.getSubscriberId();// 获取imsi

			if (imsi == null || "".equals(imsi)) {
				// 获取运营商
				imsi = tm.getSimOperator();
			}

			// 联发科
			Class<?>[] resources = new Class<?>[] {int.class };
			if (imsi == null || "".equals(imsi)) {
				try {
					// 利用反射获取 MTK手机
					Method addMethod = tm.getClass().getDeclaredMethod(
							"getSubscriberIdGemini", resources);
					addMethod.setAccessible(true);
					imsi = (String) addMethod.invoke(tm, 1); // 卡2
					// imsi = (String) addMethod.invoke(tm, 0); //卡1
				} catch (Exception e) {
					imsi = null;
				}
			}

			// 展讯
			if (imsi == null || "".equals(imsi)) {
				try {
					// 利用反射获取 展讯手机
					Class<?> c = Class
							.forName("com.android.internal.telephony.PhoneFactory");
					Method m = c.getMethod("getServiceName", String.class,
                                                               int.class);
					// String spreadTmService = (String) m.invoke(c,
					// Context.TELEPHONY_SERVICE, 0); //卡1
					String spreadTmService = (String) m.invoke(c,
                                                                                   Context.TELEPHONY_SERVICE, 1); // 卡2
					TelephonyManager tm1 = (TelephonyManager) context
							.getSystemService(spreadTmService);
					imsi = tm1.getSubscriberId();
				} catch (Exception e) {
					imsi = null;
				}
			}

			// 高通
			if (imsi == null || "".equals(imsi)) {
				try {
					// 利用反射获取 高通手机
					Class<?> cx = Class
							.forName("android.telephony.MSimTelephonyManager");
					Object obj = context.getSystemService("phone_msim");
					Method mx = cx.getMethod("getDataState");
					Method ms = cx.getMethod("getSubscriberId", int.class);
					// String imei_1 = (String) ms.invoke(obj, 0); //卡1
					imsi = (String) ms.invoke(obj, 1); // 卡2

				} catch (Exception e) {
					imsi = null;
				}
			}

			if (imsi == null) {
				imsi = "";
			}
			return imsi;
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 网络类型  wifi或移动网
	 */
	public static int getNetType(Context context){
        if(!isPermissionGranted((Activity) context, Manifest.permission.ACCESS_WIFI_STATE, 0)){
            return 1;
        }
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
				
				NetworkInfo ni = connectivity.getActiveNetworkInfo();
				if(ni != null){
					if(ni.isConnectedOrConnecting()){
						return 2;
					}
				}
			}
			
		return -1;
	}
	
	/**
	 * 当前运营商
	 * 
	 * @param context
	 * @return
	 */
	public static String checkOperator(Context context) {
		String operatorName = "";
		/*TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		String operator = telManager.getSimOperator();*/
		String operator = "";
		try {
			String imsi = getImsi(context);
			if(imsi != null && imsi.length() > 4){
				 operator = imsi.substring(0,5);
				 Log.i("feng", "operator:" + operator);
			}
		} catch (Exception e) {
		}
		
//		
		// ToastUtils.toastShow(context, "当前"+operator);
		if (operator != null && !operator.trim().equals("")) {
			if (operator.equals("46001")) {
				return "2";
			} else if (operator.equals("46003")) {
				return "3";
			} else {
				return "1";
			}
		}
		return operatorName;
	}
	
	
	/**
	 * 定时发送请求
	 */
	
	public static void requestSession(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while (request) {
					try {
						Thread.sleep(60 * 5 * 1000);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
	
	/**
	 * 读取渠道文件
	 */
	public static String getChannel(Context context) {
		ApplicationInfo appInfo = null;
		String channel = "";
		try {
			appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
			channel = appInfo.metaData.getString("UMENG_CHANNEL");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return channel;
//		String[] channels = new String[2];
//		try {
//			InputStream in = context.getAssets().open(
//					"zhimeng/zhimeng_channel.txt");
//			if (in == null)
//				return null;
//			String tmp = null;
//			StringBuilder sb = new StringBuilder();
//			BufferedReader reader = null;
//			try {
//				reader = new BufferedReader(new InputStreamReader(in));
//				try {
//					while ((tmp = reader.readLine()) != null) {
//						sb.append(tmp);
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//                if(!"".equals(""+sb.toString())){
//                	channels = sb.toString().split(";");
//                }
//				return channels;
//			} catch (Exception e) {
//				// HandlerService.handler.sendEmptyMessage(UtilDown.RESTARTACTIVITY);
//			} finally {
//				if (reader != null) {
//					try {
//						reader.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				if (in != null) {
//					try {
//						in.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return null;
	}
	
	/**
	 * 获取本机手机号
	 */
	public static String getPhoneNum(Context context){
        if(!isPermissionGranted((Activity) context, Manifest.permission.READ_PHONE_STATE, 0)){
            return "";
        }
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		  String te1  = tm.getLine1Number();//获取本机号码
		return te1;
	}
}
