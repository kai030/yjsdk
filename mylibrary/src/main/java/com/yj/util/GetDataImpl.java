package com.yj.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.yj.entity.BaseData;
import com.yj.entity.ChargeData;
import com.yj.entity.Constants;
import com.yj.entity.DeviceProperties;
import com.yj.entity.Flag;
import com.yj.entity.Helper;
import com.yj.entity.PayChannel;
import com.yj.entity.PayResult;
import com.yj.entity.Result;
import com.yj.entity.SecretData;
import com.yj.entity.Session;
import com.yj.entity.UserAction;
import com.yj.sdk.YJTisActivity;
import com.yj.sdk.YouaiAppService;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public class GetDataImpl {

    private static GetDataImpl mInstance;
    private Context context;
    private DeviceProperties deviceProperties;
    private static Session mSession = null;

    private GetDataImpl(Context context) {
        this.context = context;
        deviceProperties = DeviceProperties.getInstance(context);
        mSession = Session.getInstance();
    }

    /**
     * 单例
     *
     * @param context
     * @return
     */
    public static GetDataImpl getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new GetDataImpl(context);
        }
        return mInstance;
    }

    /**
     * POST请求操作
     *
     * @param pathUrl
     * @param parmsStr
     */
    public InputStream doRequest(String pathUrl, String parmsStr) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(pathUrl);
            connection = (HttpURLConnection) url.openConnection();


            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(1000 * 3000);
            connection.setReadTimeout(1000 * 3000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Connection", "Keep-Alive");

            connection.setRequestProperty("User-Agent", "BDNuomiAppAndroid");
            connection.setUseCaches(false);

            // 设置请求方式
            connection.setRequestMethod("POST");

            // 设置编码格式
//            connection.setRequestProperty("Charset", "UTF-8");
            // 传递自定义参数
            Log.i("ttttttttttttt", "parmsStr:" + parmsStr);
//            connection.setRequestProperty("Content-type", "text/html");
//			connection.setRequestProperty("MyProperty", parmsStr);
//			connection.addRequestProperty("MyProperty", parmsStr);
            // 设置容许输出
//            connection.setDoInput(true); // 读取数据
//            connection.setDoOutput(true);

            //DataOutputStream流
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            //要上传的参数
            String content = parmsStr;
            //将要上传的内容写入流中
            out.writeBytes(content);
            //刷新、关闭
            out.flush();
            out.close();

            // 上传一张图片
//			FileInputStream file = new FileInputStream(Environment.getExternalStorageDirectory().getPath()
//					+ "/Pictures/Screenshots/Screenshot_2015-12-19-08-40-18.png");
//			OutputStream os = connection.getOutputStream();
//			int count = 0;
////			while((count=file.read()) != -1){
//				os.write(parmsStr.getBytes());
////			}
//			os.flush();
//			os.close();
            Utils.fengLog("connection.getResponseCode:" + connection.getResponseCode());
            // 获取返回数据
            if (connection.getResponseCode() != 1200) {
                InputStream is = connection.getInputStream();
                Utils.fengLog("connection.is:" + is);
                return is;
                //        创建字节输出流对象
//				ByteArrayOutputStream os = new ByteArrayOutputStream();
//				// 定义读取的长度
//				int len = 0;
//				// 定义缓冲区
//				byte buffer[] = new byte[1024];
//				// 按照缓冲区的大小，循环读取
//				while ((len = is.read(buffer)) != -1) {
//					// 根据读取的长度写入到os对象中
//					os.write(buffer, 0, len);
//				}
//				// 释放资源
//				is.close();
//				os.close();
//				// 返回字符串
//				String result = new String(os.toByteArray());
//
//				result = StringStreamUtil.inputStreamToString(is);

//				Message msg = Message.obtain();
//				msg.what = 0;
//				postHandler.sendMessage(msg);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Utils.fengLog("Exception:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * 平台登入 表示用户打开软件
     *
     * @return
     */
    public BaseData online() {

        Utils.youaiLog("初始化参数:   "
                + getSessionJson(Constants.A_INIT).toString());
        InputStream in = doRequest(Constants.URL,
                Kode1.e(getSessionJson(Constants.A_INIT).toString()));

        if (in == null) {
            Utils.youaiLog("初始化失败 ");
            return null;
        }

        String json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("初始化返回结果:   " + json);
        if (json == null) {
            Utils.youaiLog("初始化失败");
            return null;
        }

        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);// 结果码

        if (null != result && result.resultCode == 0) {
            Utils.youaiLog("初始化成功");
            BaseData basicDate = (BaseData) JsonUtil.parseJSonObject(
                    BaseData.class, json);// 引用字段
            if (null != basicDate) {
                Utils.youaiLog("describevt--->" + basicDate.describevt);
                Utils.youaiLog("QQ--->" + basicDate.serviceQQ);
                Utils.youaiLog("serviceTel--->" + basicDate.serviceTel);
                YouaiAppService.basicDate = basicDate;
                // 用户行为定时发送qij
                return basicDate;
            }
        } else {
            Utils.youaiLog("初始化失败");
        }
        return null;
    }

    /**
     * 发送行为
     *
     * @param useraction
     * @return
     */
    public Result userAction(UserAction useraction) {

        // HashMap<String, String> params = new HashMap<String, String>();
        // params.put(Constants.REQUESTID, "" + Constants.ID_USERACTION);
        // String url = getUrl(params);
        JSONObject jsonObject = getSessionAndDevicesPropertiesJson();
        try {
            jsonObject.put(useraction.getShortName(), useraction.buildJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Utils.youaiLog("发送行为参数 -> " + jsonObject.toString());
        InputStream in = doRequest(Constants.ACTION_URL, Kode1.e(jsonObject.toString()));
        if (in == null)
            return null;
        String json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("发送行为结果 -> " + json);
        if (json == null)
            return null;
        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        return result;

    }

    /**
     * 获取注册时的用户名和密码
     *
     * @return
     */
    public String registerUserAndPasswrod() {

        InputStream in = doRequest(Constants.URL,
                Kode1.e(getSessionJson(Constants.A_DEFAULT_REGISTER).toString()));
        if (in == null)
            return null;
        Utils.youaiLog("获取默认注册帐号参数："
                + getSessionJson(Constants.A_DEFAULT_REGISTER).toString());
        String json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("获取默认注册帐号结果：" + json);
        Utils.youaiLog("registerUserAndPasswrod json --> " + json);
        if (json == null)
            return null;

        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        Session session = (Session) JsonUtil.parseJSonObject(Session.class,
                json);
        if (result == null || session == null) {
            Log.i("feng", "获取注册帐号密码失败");
            return null;
        }
        if (result.resultCode == 0) {
            Log.i("feng", "获取注册帐号密码成功");
        }

        return json;
    }

    /**
     * 用户注册
     *
     * @return 注册
     */
    public Result register(final String userName, final String password) {

        YouaiAppService.isLogin = false;
        // mSession = new Session();
        Session.getInstance().userAccount = userName;
        Session.getInstance().password = Utils.calc(password.toLowerCase());
        Utils.youaiLog("注册参数： "
                + getSessionJson(Constants.A_REGISTER).toString());
        Utils.fengLog("注册参数： "
                + getSessionJson(Constants.A_REGISTER).toString());
//        InputStream in = doRequest(Constants.URL_REGISTER,
//                getSessionJson(Constants.A_REGISTER).toString());
        String json = loginByHttpClientPost(Constants.URL_REGISTER,
                getSessionJson(Constants.A_REGISTER).toString());
//        String json = readJsonMy(in);
        Utils.fengLog("注册结果：" + json);
//		json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("注册返回： " + json);
        if (json == null) {
            return null;
        }
//		Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        Result result = JsonUtil.getResult(json);
        Session session = Session.getInstance();
        if (result == null) {
            return null;
        }
        if (session != null && (result.resultCode == Constants.REQUEST_SUCCESS)) {
            // mSession = session;
            session.password = Utils.calc(password);
            session.uid = result.uid + "";
            YouaiAppService.mSession = Session.getInstance();
            YouaiAppService.isLogin = true;
            Utils.writeAccountXml(context, userName, password, session.uid);
//			syncSession(session);
        }
        return result;
    }

    /**
     * 用户登录
     *
     * @return true表示登录成功 false表示登录失败
     */
    public Result login(final String userName, final String password) {

        YouaiAppService.isLogin = false;

        Session.getInstance().userAccount = userName;
        Session.getInstance().password = Utils.calc(password.toLowerCase());

        Utils.fengLog("登录参数：" + getSessionJson(Constants.A_LOGIN).toString());
//        InputStream in = doRequest(Constants.URL_LOGIN,
//                getSessionJson(Constants.A_LOGIN).toString());
        String json = loginByHttpClientPost(Constants.URL_LOGIN,
                getSessionJson(Constants.A_LOGIN).toString());
//        String json = readJsonMy(in);
        Utils.fengLog("登录结果：" + json);
        if (json == null) {
            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }
        // YouaiAppService.isLogin = true;
        Result result = JsonUtil.getResult(json);
        if (result == null) {
            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }

        Session session = Session.getInstance();


        if (session != null && result.resultCode == Constants.REQUEST_SUCCESS) {
            session.password = password;
            session.uid = result.uid + "";
            Utils.writeAccountXml(context, userName, password, session.uid);

//			syncSession(session);
        }
        return result;

    }

    /**
     * pay
     *
     * @return
     */
    public Result pay() {
        Session.getInstance().password = "";
        Utils.fengLog("支付通知参数：" + getSessionJson(Constants.A_LOGIN).toString());
//        InputStream in = doRequest(Constants.URL_PAY_RESULT,
//                getSessionJson(Constants.A_LOGIN).toString());
//        String json = readJsonMy(in);
        String json = loginByHttpClientPost(Constants.URL_PAY_RESULT,
                getSessionJson(Constants.A_LOGIN).toString());
        Utils.fengLog("支付通知结果：" + json);
        if (json == null) {
//            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }
        // YouaiAppService.isLogin = true;
        Result result = JsonUtil.getResult(json);
        if (result == null) {
//            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }


        if (result.resultCode == Constants.REQUEST_SUCCESS) {

        }
        return result;

    }

    /**
     * jb兑换来气h5
     *
     * @return
     */
    public String payJb() {
        Utils.fengLog("jb兑换参数：" + getSessionJson(Constants.A_LOGIN).toString());
        String json = loginByHttpClientPost(Constants.URL_PAY_JB,
                getSessionJson(Constants.A_LOGIN).toString());
        Utils.fengLog("jb兑换结果：" + json);
        return json;
    }

    /**
     * 礼包列表
     *
     * @return
     */
    public String giftList() {
        Utils.fengLog("礼包列表参数：" + getSessionJson(Constants.A_LOGIN).toString());
//        InputStream in = doRequest(Constants.URL_PAY_RESULT,
//                getSessionJson(Constants.A_LOGIN).toString());
//        String json = readJsonMy(in);
        String json = loginByHttpClientPost(Constants.URL_USER_GIFT_LIST,
                getSessionJson(Constants.A_LOGIN).toString());
        Utils.fengLog("礼包列表结果：" + json);
        return json;
    }

    /**
     * 获取礼包
     *
     * @return
     */
    public String giftGet() {
        Utils.fengLog("获取礼包参数：" + getSessionJson(Constants.A_LOGIN).toString());
        String json = loginByHttpClientPost(Constants.URL_USER_GIFT,
                getSessionJson(Constants.A_LOGIN).toString());
        Utils.fengLog("获取礼包结果：" + json);
        return json;
    }

    /**
     * 新闻列表
     *
     * @return
     */
    public String getNews() {
        Utils.fengLog("新闻列表参数：" + getSessionJson(Constants.A_LOGIN).toString());
//        InputStream in = doRequest(Constants.URL_PAY_RESULT,
//                getSessionJson(Constants.A_LOGIN).toString());
//        String json = readJsonMy(in);
        String json = loginByHttpClientPost(Constants.URL_USER_NEWS,
                getSessionJson(Constants.A_LOGIN).toString());
        Utils.fengLog("新闻列表结果：" + json);
        return json;
    }

    /**
     * 客服中心
     *
     * @return
     */
    public String customerService() {
        Utils.fengLog("客服中心参数：" + getSessionJson(Constants.A_LOGIN).toString());
//        InputStream in = doRequest(Constants.URL_PAY_RESULT,
//                getSessionJson(Constants.A_LOGIN).toString());
//        String json = readJsonMy(in);
        String json = loginByHttpClientPost(Constants.URL_CUSTOMER_SERVICE,
                getSessionJson(Constants.A_LOGIN).toString());
        Utils.fengLog("客服中心结果：" + json);
        return json;
    }

    /**
     * 通知服务器id
     *
     * @return
     */
    public Result serverId(String url) {
        Utils.fengLog("服务器id参数：" + getSessionJson(Constants.A_LOGIN).toString());
//        InputStream in = doRequest(Constants.URL_PAY_RESULT,
//                getSessionJson(Constants.A_LOGIN).toString());
//        String json = readJsonMy(in);
        String json = loginByHttpClientPost(url,
                getSessionJson(Constants.A_LOGIN).toString());
        Utils.fengLog("服务器id结果：" + json);
        if (json == null) {
//            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }
        // YouaiAppService.isLogin = true;
        Result result = JsonUtil.getResult(json);
        if (result == null) {
//            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }


        if (result.resultCode == Constants.REQUEST_SUCCESS) {

        }
        return result;

    }

    /**
     * 第三方登录
     *
     * @return
     */
    public Result tQQLogin(String url) {

        if (TextUtils.isEmpty(url)) {
            return null;
        }

//        Session.getInstance().password = "";

        JSONObject jsonObject = getSessionJson(Constants.A_LOGIN);
        Utils.fengLog("第三方登录参数：" + jsonObject.toString());

//        InputStream in = doRequest(url,
//                jsonObject.toString());
        String json = loginByHttpClientPost(url,
                jsonObject.toString());
//        String json = readJsonMy(in);
        Utils.fengLog("第三方登录结果：" + json);
        if (json == null) {
//            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }
        // YouaiAppService.isLogin = true;
        Result result = JsonUtil.getResult(json);
        if (result == null) {
//            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }


        if (result.resultCode == Constants.REQUEST_SUCCESS) {
            Session session = Session.getInstance();
            session.userAccount = result.username;
            session.uid = "" + result.uid;
            Utils.writeAccountXml(context, result.username, result.password, result.uid + "");
        }
        return result;

    }

    private static String changeInputStream(InputStream inputStream,
                                            String encode) {

        // 内存流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = null;
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
                result = new String(byteArrayOutputStream.toByteArray(), encode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String loginByHttpClientPost(String url, String param) {
        String result = "";
        //1.创建 HttpClient 的实例
        HttpClient client = new DefaultHttpClient();
        //2. 创建某种连接方法的实例，在这里是HttpPost。在 HttpPost 的构造函数中传入待连接的地址
        String uri = url;
        HttpPost httpPost = new HttpPost(uri);
        try {
            //封装传递参数的集合
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            //往这个集合中添加你要传递的参数
            parameters.add(new BasicNameValuePair("param", param));
//            parameters.add(new BasicNameValuePair("userpass", userPass));
            //创建传递参数封装 实体对象
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");//设置传递参数的编码
            //把实体对象存入到httpPost对象中
            httpPost.setEntity(entity);
            //3. 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例
            HttpResponse response = client.execute(httpPost); //HttpUriRequest的后代对象 //在浏览器中敲一下回车
            //4. 读 response
            if (response.getStatusLine().getStatusCode() == 200) {//判断状态码
                InputStream is = response.getEntity().getContent();//获取内容
                result = changeInputStream(is, "utf-8"); // 通过工具类转换文本

//                LoginActivity.this.runOnUiThread(new Runnable() {   //通过runOnUiThread方法处理
//                    @Override
//                    public void run() {
//                        //设置控件的内容(此内容是从服务器端获取的)
//                        tv_result.setText(result);
//                    }
//                });
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //6. 释放连接。无论执行方法是否成功，都必须释放连接
            client.getConnectionManager().shutdown();
        }
        return result;
    }


    /**
     * 一键登录
     *
     * @return
     */
    public Result goLogin() {

        YouaiAppService.isLogin = false;

        Utils.fengLog("一键登录参数：" + getSessionJson(Constants.A_LOGIN).toString());
//        InputStream in = doRequest(Constants.URL_LOGIN_REG,
//                getSessionJson(Constants.A_LOGIN).toString());
//        InputStream in  = loginByHttpClientPost(Constants.URL_LOGIN_REG,
//                getSessionJson(Constants.A_LOGIN).toString());
        String json = loginByHttpClientPost(Constants.URL_LOGIN_REG,
                getSessionJson(Constants.A_LOGIN).toString());
//        String json = readJsonMy(in);
        Utils.fengLog("一键登录结果：" + json);
        if (json == null) {
            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }
        // YouaiAppService.isLogin = true;
        Result result = JsonUtil.getResult(json);
        if (result == null) {
            YouaiAppService.resultListen.loginFailureResult();
            return null;
        }


        Session session = Session.getInstance();

        if (session != null && result.resultCode == Constants.REQUEST_SUCCESS) {
            session.userAccount = result.username;
//            session.password = password;
            session.uid = result.uid + "";
            Utils.writeAccountXml(context, result.username, result.password, result.uid + "");

//			syncSession(session);
        }
        return result;

    }

    /**
     * 发送服务器id
     *
     * @return true表示登录成功 false表示登录失败
     */
    public Result sendServerId() {

        // YouaiAppService.isLogin = false;

        Utils.youaiLog("发送服务器id参数："
                + getSessionJson(Constants.A_LOGIN).toString());
        InputStream in = doRequest(Constants.SERVER_ID_URL,
                getSessionJson(Constants.A_LOGIN).toString());
        String json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("发送服务器id结果：" + json);
        if (json == null) {
            return null;
        }

        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        if (result == null) {
            return null;
        }

		/*
         * Session session = (Session) JsonUtil.parseJSonObject(Session.class,
		 * json);
		 */
        return result;

    }


    /**
     * 根据账号获取密保
     *
     * @param userName
     * @return
     */
    public Result getMibaoToUser(String userName) {
        // mSession = new Session();
        Session.getInstance().userAccount = userName;

        Utils.youaiLog("根据账号获取密保参数:"
                + getSessionJson(Constants.A_FORGOT_PASSWORD).toString());
        InputStream in = doRequest(Constants.URL,
                Kode1.e(getSessionJson(Constants.A_FORGOT_PASSWORD).toString()));
        String json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("根据账号获取密保结果:" + json);
        if (json == null) {
            return null;
        }
        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        JsonUtil.parseJSonObject(SecretData.class, json);

        Session session = (Session) JsonUtil.parseJSonObject(Session.class,
                json);

        if (result == null) {
            return null;
        }
        if (session != null && result.resultCode == 0) {
            YouaiAppService.mSession = session;
        }
        return result;
    }


    /**
     * 银联支付
     *
     * @return
     */
    public Result unionPaySubmit() {

        Utils.youaiLog("银联参数" + Constants.A_PAY + ":"
                + getPaySessionJson(Constants.A_PAY).toString());
        InputStream in = doRequest(Constants.URL_PAY,
                Kode1.e(getPaySessionJson(Constants.A_PAY).toString()));
        String json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("银联支付结果：  " + json);
        if (json == null) {
//			YouaiAppService.resultListen.paymentFailureResult(YJResultCode.PAYMENT_Failure);
            return null;
        }
        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        if (result == null) {
//			YouaiAppService.resultListen.paymentFailureResult(YJResultCode.PAYMENT_Failure);
            return null;
        }

        if (result.resultCode == 0) {
            /*YouaiAppService.mSession.bindMObile = "true";
            YouaiAppService.mSession.phoneNum = answer;*/
            // Session.secret = 1;//
            // Session.secretId = secretId;
        }
        return result;
    }


    /**
     * 支付宝支付
     *
     * @return
     */
    public Result alipaySubmit() {
	/*	Session mSession = Session.getInstance();
		mSession.securityCode = pasword;
	    mSession.phoneNum = answer;
		mSession.password = YouaiAppService.mSession.password;
		mSession.userAccount = YouaiAppService.mSession.userAccount;*/
        // mSession.newSecretId = secretId;
        // mSession.newAnswer = answer;

        // Session.secretId = SecretData.secretId;
        Utils.youaiLog("支付宝支付参数" + Constants.A_PAY + ":"
                + getPaySessionJson(Constants.A_PAY).toString());
        InputStream in = doRequest(Constants.URL_PAY,
                Kode1.e(getPaySessionJson(Constants.A_PAY).toString()));
        String json = Kode1.d(readJsonMy(in));
//		Utils.youaiLog("支付宝支付结果：  " + json);
        if (json == null) {
//			YouaiAppService.resultListen.paymentFailureResult(YJResultCode.PAYMENT_Failure);
            return null;
        }
        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        if (result == null) {
//			YouaiAppService.resultListen.paymentFailureResult(YJResultCode.PAYMENT_Failure);
            return null;
        }

        if (result.resultCode == 0) {
			/*YouaiAppService.mSession.bindMObile = "true";
			YouaiAppService.mSession.phoneNum = answer;*/
            // Session.secret = 1;//
            // Session.secretId = secretId;
        }
        return result;
    }

    /**
     * 易宝支付
     *
     * @return
     */
    public Result yeepaySubmit() {
        Utils.youaiLog("易宝支付参数" + Constants.A_PAY + ":"
                + getPaySessionJson(Constants.A_PAY).toString());
        InputStream in = doRequest(Constants.URL_PAY,
                Kode1.e(getPaySessionJson(Constants.A_PAY).toString()));
        String json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("易宝支付结果：  " + json);
        if (json == null) {
//			YouaiAppService.resultListen.paymentFailureResult(YJResultCode.PAYMENT_Failure);
            return null;
        }
        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        if (result == null) {
//			YouaiAppService.resultListen.paymentFailureResult(YJResultCode.PAYMENT_Failure);
            return null;
        }

        if (result.resultCode == 0) {
        }
        return result;
    }

    /**
     * 获取支付列表
     *
     * @return
     */
    public PayChannel[] getPaymentList(ChargeData charge) {

        JSONObject jsonObject = getSessionAndDevicesPropertiesJson();
        try {
            jsonObject.put(charge.getShortName(), charge.buildJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Utils.youaiLog("获取支付列表参数：" + jsonObject.toString());
        InputStream in = doRequest(Constants.PAY_LIST_URL,
                Kode1.e(jsonObject.toString()));
        if (in == null) {
            // Utils.toastInfo(context, "获取列表失败");
            return null;
        }

        String json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("获取支付列表返回结果：" + json);
        if (json == null) {
            // Utils.toastInfo(context, "获取列表失败");
            return null;
        }

        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        PayChannel[] channelMessages = (PayChannel[]) JsonUtil.parseJSonArray(
                PayChannel.class, json);
        if (null == result || 0 != result.resultCode || channelMessages == null) {
            Utils.fengLog("payment list channelMessages ----> "
                    + channelMessages);
            return null;
        }
        // 设置全局静态数据
        YouaiAppService.mPayChannelsFast.clear();
        YouaiAppService.mPayChannelsCard.clear();
        for (int i = 0; i < channelMessages.length; i++) {
            if (channelMessages[i].paymentType == 1) {
                YouaiAppService.mPayChannelsFast.add(channelMessages[i]);
            } else if (channelMessages[i].paymentType == 2) {
                YouaiAppService.mPayChannelsCard.add(channelMessages[i]);
            }
        }
        YouaiAppService.mPayChannels = channelMessages;
        Flag.notice = YouaiAppService.mPayChannels[0].notice;// 滚动
        Flag.descr = YouaiAppService.mPayChannels[0].descr;// 说明
		/*
		 * for (int i = 0; i < YouaiAppService.mPayChannelsCard.size(); i++) {
		 * Log.i("feng", "channelMessages  length:" + channelMessages.length +
		 * "  " + YouaiAppService.mPayChannelsCard.get(i).paymentName); }
		 */

        return channelMessages;
    }

    /**
     * 提交支付信息
     *
     * @param charge
     * @return
     */
/*	public String charge(ChargeData charge) {
		Log.i("feng", " 提交支付信息 提交支付信息 :金额" + charge.money + " ---------- 支付id:"
				+ Flag.payTypeFlag);
		ChargeData chargeDate4Req = charge.clone();
		chargeDate4Req.money = charge.money * 100;

		chargeDate4Req.paymentId = Flag.payTypeFlag;
		mSession = YouaiAppService.mSession;
		JSONObject jsonObject = getSessionAndDevicesPropertiesJson();
		try {
			jsonObject.put(chargeDate4Req.getShortName(),
					chargeDate4Req.buildJson());
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		Utils.youaiLog("支付参数：" + jsonObject.toString());
		InputStream in = doRequest(Constants.PAY_COMMIT_URL,
				jsonObject.toString());
		if (in == null) {
			return null;
		}
		String json = readJsonMy(in);
		Utils.youaiLog("支付结果：" + json);
		if (json == null)
			return null;
		return json;
	}*/

    /**
     * 充值后返回
     *
     * @param payResult
     * @return
     */
    public Result payResult(PayResult payResult) {

        JSONObject jsonObject = getSessionAndDevicesPropertiesJson();
        try {
            jsonObject.put(payResult.getShortName(), payResult.buildJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Utils.youaiLog("充值后返回参数：" + jsonObject.toString());
        InputStream in = doRequest("", Kode1.e(jsonObject.toString()));
        if (in == null)
            return null;

        String json = Kode1.d(readJsonMy(in));
        Utils.youaiLog("充值后返回结果：" + json);
        if (json == null)
            return null;
        Result result = (Result) JsonUtil.parseJSonObject(Result.class, json);
        return result;
    }

    /**
     * 同步用户基本信息到数据库和SD卡
     *
     * @return
     */
    private void syncSession(Session session) {
//		TSession t = TSession.getInstance(context);
//		// 将用户名保存到sdcard
//		Utils.writeAccount2SDcard(session.userAccount, session.password);
//		// 将用户名保存到xml
//		Utils.writeAccountXml(context, session.userAccount,session.password);
//		// 将用户名保存到数据库
//		return t.update(session);
    }

    /**
     * post参数
     *
     * @return
     */

    private JSONObject getSessionJson(String interfaceFlag) {
        JSONObject json = new JSONObject();
        try {
//			json.put("a", interfaceFlag);
            json.put(Session.getInstance().getShortName(), Session
                    .getInstance().buildJson());
			/*
			 * json.put(deviceProperties.getShortName(),
			 * deviceProperties.buildJson());
			 */
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return json;
    }

    protected void setString(JSONObject json, String key, String value) throws Exception {
        if (!Helper.isNullOrEmpty(key) && !Helper.isNullOrEmpty(value)) {
            json.put(key, value);
        }
    }

    private JSONObject getPaySessionJson(String interfaceFlag) {
        JSONObject json = new JSONObject();
        try {
            json.put("a", interfaceFlag);
            json.put(Session.getInstance().getShortName(), Session
                    .getInstance().buildJson());
            json.put(ChargeData.getChargeData().getShortName(), ChargeData.getChargeData().buildJson());
			/*
			 * json.put(deviceProperties.getShortName(),
			 * deviceProperties.buildJson());
			 */
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return json;
    }

    private JSONObject getSessionAndDevicesPropertiesJson() {
        JSONObject json = new JSONObject();
        try {
            json.put(mSession.getShortName(), mSession.buildJson());
            json.put(deviceProperties.getShortName(),
                    deviceProperties.buildJson());
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return json;
    }

    private JSONObject getSessionAndDevicesPropertiesJsonInit() {
        JSONObject json = new JSONObject();
        try {
            // json.put(mSession.getShortName(), mSession.buildJson());
            json.put(deviceProperties.getShortName(),
                    deviceProperties.buildJson());
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return json;
    }

    /**
     * 拼接url
     *
     * @param params
     * @return
     */
    private String getUrl(HashMap<String, String> params) {
        String url = Constants.URL;
        params.put("a", "1");
        params.put("b", "1");
        // 添加url参数
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            url += sb.toString();
        }
        return url;
    }

    /**
     * 请求c/s数据
     *
     * @param params
     *            url参数
     * @param bytes
     *            post内容
     * @return 返回内容
     */
//	private InputStream doRequest(String url, String str) {
//
//		HttpClient client = NetworkImpl.getHttpClient(this.context);
//
//		if (client == null) {
//			return null;
//		}
//
//		// System.out.println("加密后----->" + Encrypt.encode(str));
//		// 添加post内容
//		HttpPost httpPost = new HttpPost(url);
//		httpPost.setHeader("content-type", "text/html");
//		if (str != null) {
//
//			HttpEntity entity;
//			try {
//
//				/*
//				 * entity = new ByteArrayEntity(compress(Encrypt2.encode(str,
//				 * "1") .getBytes()));
//				 */
//				// str = "{"+"a:"+interfaceFlag+str.substring(1,
//				// str.length())+"}";
//				entity = new ByteArrayEntity(str.getBytes());
//				httpPost.setEntity(entity);
//				/*
//				 * List<NameValuePair> formParam = new
//				 * ArrayList<NameValuePair>(); formParam.add(new
//				 * BasicNameValuePair("a", "1")); // formParam.add(new
//				 * BasicNameValuePair("b", str));
//				 *
//				 * UrlEncodedFormEntity urlEncodedFormEntity = new
//				 * UrlEncodedFormEntity(formParam, "UTF-8");
//				 * httpPost.setEntity(urlEncodedFormEntity);
//				 */
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//
//		HttpResponse response = null;
//		int reconnectCount = 0;
//		while (reconnectCount < 2) {
//			try {
//				response = client.execute(httpPost);
//				int status = response.getStatusLine().getStatusCode();
//				Utils.youaiLog("status == " + status);
//				if (status == HttpStatus.SC_OK) {
//					return response.getEntity().getContent();
//				}
//			} catch (ClientProtocolException e) {
//				Utils.youaiLog(e.getMessage());
//			} catch (IOException e) {
//				Utils.youaiLog(e.getMessage());
//			}
//			reconnectCount++;
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//			}
//		}
//		return null;
//	}

    /**
     * 数据压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] compress(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 压缩
        compress(bais, baos);

        byte[] output = baos.toByteArray();

        baos.flush();
        baos.close();
        bais.close();

        return output;
    }

    /**
     * 数据压缩
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static void compress(InputStream is, OutputStream os)
            throws Exception {

        GZIPOutputStream gos = new GZIPOutputStream(os);

        int count;
        byte data[] = new byte[1024];
        while ((count = is.read(data, 0, data.length)) != -1) {
            gos.write(data, 0, count);
        }

        // gos.flush();
        gos.finish();

        gos.close();
    }

    /**
     * 数据解压
     */
    public static byte[] unzip(InputStream in) {
        // Open the compressed stream
        GZIPInputStream gin;
        try {
            if (in == null) {
                return null;
            }
            gin = new GZIPInputStream(in);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // Transfer bytes from the compressed stream to the output stream
            byte[] buf = new byte[1024];
            int len;
            while ((len = gin.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            // Close the file and stream
            gin.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
/*	private String readJsonData(byte[] data) {
		if (data == null || data.length <= 0)
			return null;
		String tmp = null;
		tmp = new String(data);
		try {
			return Encrypt2.decode(tmp, "1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

    /**
     * 不解压不加密
     *
     * @param in
     * @return
     */
    public static String readJsonMy(InputStream in) {
        if (in == null)
            return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        try {
            while ((i = in.read()) != -1) {
                baos.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = baos.toString();
        Utils.fengLog("fengLogfengLog6666666666666666:" + str);

        String tmp = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            try {
                while ((tmp = reader.readLine()) != null) {
                    sb.append(tmp);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return sb.toString();
        } catch (Exception e) {
            // HandlerService.handler.sendEmptyMessage(UtilDown.RESTARTACTIVITY);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 解压
     */
    public static String unZip(String json) {
        return json;
    }

}
