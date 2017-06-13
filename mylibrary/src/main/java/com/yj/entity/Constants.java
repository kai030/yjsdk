package com.yj.entity;

/**
 * @author lufengkai
 * @date 2015年5月26日
 * @copyright
 */
public class Constants {

    public static final String URL = "http://sdk.5qx.com/login/reg/";

    public static final String PATH = "http://sdk.5qx.com/sdk/";
    public static final String URL_LOGIN = PATH + "login";
    public static final String URL_LOGIN_REG = PATH + "login/yjreg";
    public static final String URL_REGISTER = PATH + "login/reg";
    public static final String QQ_LOGING_URL = PATH + "login/qqopen";
    public static final String WX_LOGING_URL = PATH + "login/wxopen";
    public static final String URL_PAY_RESULT = PATH + "pay/payfirm";
    public static final String URL_PAY_JB = PATH + "ptb";
    public static final String URL_USER_GIFT_LIST = PATH + "card/card_list";
    public static final String URL_USER_GIFT = PATH + "card/card_hq";
    public static final String URL_CUSTOMER_SERVICE = PATH + "Config";
    public static final String URL_USER_NEWS= PATH + "article/news_list";
    public static final String URL_LOG = PATH+"login/game_log";
    public static final String URL_PAY = "http://sdk.zytcgame.com/sdkRechargeInterface";


    public static final String ID = "id";
    public static final String LAYOUT = "layout";
    public static final String DRAWABLE = "drawable";

    public static String WX_APP_ID = "";

    public static String QQ_APP_ID = "";
    public static final String QQ_APP_KEY = "gpCAbabb35ccFPHi";


    public static final String ZHIMENG_APPKEY = "6193b5d35cd41851619b4f7899687d7a";
    public static final String ZHIMENG_SECRET = "281e97b73c8c26f8cddc2c6fd95a84be";

    /**
     * id
     */
    public static final int ID_PAY_LAYOUT = 1001;
    public static final int ID_PAY_ALIPAY = 1002;
    public static final int ID_PAY_YEEPAY = 1003;
    public static final int ID_PAY_UNIONPAY = 1004;
    public static final int ID_PAY_MOBILE = 1005;
    public static final int ID_PAY_UNICOM = 1006;
    public static final int ID_PAY_TELECOM = 1007;
    public static final int ID_PAY_CLOSE = 1008;//退出支付关闭按钮
    public static final int ID_PAY_DENOMINATION = 1009;//面额选择
    public static final int ID_EXIT_CLOSE = 1010;//退出框关闭按钮
    public static final int ID_EXIT_CONFIRM = 1011;//确认退出支付按钮
    public static final int ID_EXIT_CANCEL = 1012;//取消退出支付按钮
    public static final int ID_PAY_FAILURE = 1013;//重新支付按钮
    public static final int ID_PAY_SUCCEED = 1014;//支付成功按钮
    public static final int ID_PAY_CARD_PAYBTN = 1015;//充值卡确认提交


    public static final int BACK_LOGIN = 1;
    public static final int BACK_USER = 2;
    public static final int BACK_PAY = 3;

    public static final String A_INIT = "1";
    public static final String A_LOGIN = "3";
    public static final String A_REGISTER = "2";
    public static final String A_CHANGE_PWD = "4";
    public static final String A_MODI = "5";
    public static final String A_DEFAULT_REGISTER = "6";
    public static final String A_PHONE_VERIFICATION_CODE = "7";
    public static final String A_ALTER_MODI = "8";
    public static final String A_FORGOT_PASSWORD = "9";
    public static final String A_PAY_YEEPAY = "10";

    public static final String A_PAY = "1";


    public static final int getPhoneverificationcode = 1;
    public static final int bindPhone = 2;


    /**
     * 连接
     */
    /* 登录 */
	/*test*/
//	public static final String URL = "http://192.168.1.64:8080/sdk/sdkInterface";
//	public static final String URL_PAY = "http://192.168.1.64:8080/sdk/sdkRechargeInterface";
	/**/
	


	/* 域名 */
//	public static final String TEST_URL = "http://api.sdk.9133.com/api/";
    public static final String TEST_URL = "http://sdk.m.5399.com/api/";
    //	public static final String TEST_URL = "http://172.16.1.100/api/";
    // public static final String TEST_URL =
    // "http://172.16.1.5/9133/php/branches/1.0/api.sdk.9133.com/api/";
    public static final String YOUAI_HTML = "http://sdk.zytcgame.com";

    /**
     * old
     * 充值
     */
//	public static final String CHARGE_DATA_URL = "http://pay.9133.com/payInterface";
	

	/* 初始化 */
    public static final String INIT_URL = TEST_URL + "init.php";

    /* 登录 */
    public static final String LOGIN_URL = TEST_URL + "login.php";

    /* 发送行为次数 */
    public static final String ACTION_URL = TEST_URL + "action.php";

    /* 提交服务器id */
    public static final String SERVER_ID_URL = TEST_URL + "logingameserver.php";

    /* 注册 -自动获取帐号 */
    public static final String GET_ACCOUNTS = TEST_URL + "generate.php";

    /* 注册 */
    public static final String REGISTER_URL = TEST_URL + "reg.php";

    /* 修改密码 */
    public static final String PWD_URL = TEST_URL + "pwd.php";

    /* 设置密保 - 修改密保 */
    public static final String SECRET_URL = TEST_URL + "security.php";

    /* 记录安装数 */
    public static final String INSTALL_URL = TEST_URL + "install.php";
    /* 记录卸载数 */
    public static final String UNINSTALL_URL = TEST_URL + "uninstall.php";

    /* 忘记密码 ---- 根据帐号获取密保 */
    public static final String GET_SECRET_URL = TEST_URL + "pwdforget.php";
	
	/* 注册协议 */
//	public static final String YOUAI_HTML = "http://m.5399.com/agreement.html";


    /* 充值 */
    public static final String PAY_URL = "http://pay.m.5399.com/sdk/";
    /* 支付列表 - 本地 */
    //public static final String PAY_LIST_URL = "http://172.16.1.100/9133/php/branches/1.0/pay.9133.com/sdk/PayLists.php";
    public static final String PAY_LIST_URL = PAY_URL + "PayLists.php";

    /* 提交支付 - 本地 */
    //public static final String PAY_COMMIT_URL = "http://172.16.1.100/9133/php/branches/1.0/pay.9133.com/sdk/CreateOrder.php";
    public static final String PAY_COMMIT_URL = PAY_URL + "CreateOrder.php";

    /* 客服QQ */
    public static final String QQ = "客服QQ：5625414782";
    /*客服电话*/
    public static final String TEL = "客服电话：020-65985632";
    /**
     * 网络请求参数
     */
	/* 请求参数名 */
    public static final String REQUESTID = "requestId";
    /* 初始化 */
    public static final byte ID_ONLINE = 0x01;
    /* 获取基础数据 */
    public static final byte ID_BASIC_DATA = 0x02;
    /* 获取注册用户名和密码 */
    public static final byte ID_REGISTER_SESSION = 0x03;
    /* 注册 */
    public static final byte ID_REGISTER = 0x04;
    /* 登录 */
    public static final byte ID_LOGIN = 0x05;
    /* 用户退出 */
    public static final byte ID_LOGOUT = 0x06;
    /* 修改密码 */
    public static final byte ID_MODIFY_PASSWORD = 0x07;
    /* 获取密保列表 */
    public static final byte ID_GET_MOBAO_LIST = 0x08;
    /* 设置密保提交 */
    public static final byte ID_MOBAO_SUBMIT = 0x09;
    /* 根据用户名获取密保 */
    public static final byte ID_MIBAO_IN_USER = 0x0a;
    /* 密保重置密码 */
    public static final byte ID_UPDATE_PASSWORD_TO_MIBAO = 0x0b;
    /* 用户行为接口 */
    public static final byte ID_USERACTION = 0x0c;

    /**
     * 支付
     */
	/* 支付列表 */
    public static final byte ID_PAYMENT_LIST = 0x01;
    /* 支付列表 */
    public static final byte ID_CHARGE = 0x02;
    /* 返回充值结果 */
    public static final byte ID_PAYRESULT = 0x03;

    /**
     * 行为次数
     */
	/* 点击次数记录xml名 */
    public static final String SAVESETTING = "SaveSetting";
    /* 点击自动登录按钮次数 */
    public static final String AOTOCANCEL = "aotoCancel";
    /* 点击窗口图标次数 */
    public static final String ACTIONS = "actions";

    /**
     * 数据库
     */
	/* 数据库名 */
    public static final String DBNAME = "youai_sdk_db";
    /* 表名 */
    public static final String TABLE_NAME_SESSION = "session";
    /* 用户id */
    public static final String USERID = "user_id";
    /* 用户名 */
    public static final String USERNAME = "user_name";
    /* 用户密码 */
    public static final String PASSWORD = "password";
    /* 用户邮箱 */
    public static final String EMAIL = "email";
    /* 充值金额 */
    public static final String MONEY = "money";
    /* 自动登录 */
    public static final String AUTOLOGIN = "auto_login";
    /* 最后登录世间 */
    public static final String LASTLOGINTIME = "last_login_time";

    /**
     * 保存登录信息 xml
     */
	/* xml名 */
    public static final String USER_XML = "user_xml";
    /* 帐号 */
    public static final String USER_ACCOUNTS = "user_accounts";
    /* uid */
    public static final String USER_UID = "user_uid";
    /* 密码 */
    public static final String USER_PASSWORD = "user_password";
    /* 自动登录 */
    public static final String USER_AUTOL_LOGIN = "user_autol_login";
    /* 标志自动登录 */
    public static final String YES = "yes";
    public static final String NO = "no";
    /* 默认注册帐号 */
    public static final String DEFAULT_REGISTER = "default_register";

    /**
     * 资料文件夹
     **/
    public static final String ASSETS_RES_PATH = "youai_res/";
    /**
     * 保存帐号与密码到sdcard（加密保存）
     */
    public static final String ACCOUNT_PASSWORD_FILE = "/youai/data/code/youai/ZM.DAT";

    /**
     * ID
     */
	/* 一秒注册按钮 */
    public static final int ID_JUMP_REGISTER_BTN = 1;
    /* 登录按钮 */
    public static final int ID_LOGIS_BTN = 2;
    /* 进入游戏按钮 */
    public static final int ID_EBTER_GAME_BTN = 3;
    /* 忘记密码 */
    public static final int ID_FORGET_PASSWORD = 4;
    /* 注册界面返回键 */
    public static final int ID_REGISTER_BACK_BTN = 5;
    /* 注册界面帐号输入框 */
    public static final int ID_REGISTER_ACCOUNTS_EDIT = 6;
    /* 注册界面密码输入框 */
    public static final int ID_REGISTER_PASSWORD_EDIT = 7;
    /* 注册界面进入游戏按钮 */
    public static final int ID_REGISTER_STARTGAME_BTN = 8;
    /* 注册协议 */
    public static final int ID_REGISTER_AGREEMENT = 9;
    /* 用户中心返回键 */
    public static final int ID_BACK = 10;
    /* 修改密码提交 */
    public static final int ID_ALTERPASSWORD_COMMIT = 11;
    /* 密保问题 */
    public static final int ID_SECRET_QUESTION = 12;
    /* 密保 */
    public static final int ID_SECRET_COMMIT = 13;
    /* 新密保问题 */
    public static final int ID_NEW_SECRET_QUESTION = 14;
    /* 新密保问题 */
    public static final int ID_NEW_SECRET_COMMIT = 15;
    /* 忘记密码提交账号 */
    public static final int FORGER_PASSWORD = 16;
    /* 忘记密码提交新密码 */
    public static final int FORGER_PASSWORD_NEW = 17;
    /* 自动登录 */
    public static final int AUTO_LOGIN_ID = 18;
    /* 关闭 */
    public static final int CLOSE_ID = 19;

    /**
     * 文字
     */
    public static final String NETWORK_FAILURE = "网络连接失败，请检查网络设置";

    /* 密保问题 */
    public static final String[] SECRET = {"您母亲的姓名?", "您母亲的生日?", "您父亲的姓名?",
            "您父亲的生日?", "您配偶的姓名?", "您配偶的生日?", "您的学号（或工号）?", "您小学班主任的名字?",
            "您初中班主任的名字是?", "您高中班主任的名字是?", "您最熟悉的童年好友名字?", "您最熟悉的学校宿舍室友名字?",
            "对您影响最大的人名字?"};

    public static final String[] PAY = {"10", "20", "30", "50", "100", "200", "300", "500", "1000", "1500", "2000", "3000",};

    /**
     * 支付接口字段名
     */
	/* 角色id */
    public static final String EXTRA_PRICE = "price";
    /*角色等级*/
    public static final String EXTRA_ROLELEVEL = "rolelevel";
    /* 角色名 */
    public static final String EXTRA_ROLE = "role";
    /* 回调验证参数 */
    public static final String EXTRA_CALLBACKINFO = "callBackInfo";

    /**
     * 密码
     */
	/* 没设置密保 */
    public static final int SECRET_OFF = 0;
    /* 已经设置密保 */
    public static final int SECRET_ON = 1;

    /**
     * 用户中心item
     */
	/* 修改密码 */
    public static final int ALTER_PASS_ITEM = 0;
    /* 密保管理 */
    public static final int SECRET_ITEM = 1;
    /* 联系客服 */
    public static final int SERVICE_ITEM = 2;
	/* 已经设置密保 */

    /* 边距 */
    public static final int BORDER_MARGIN = 14;

    /**
     * 支付id
     */
    public static final int ALIPAY = 1;
    public static final int YEEPAY = 2;

    public static final String COMPANY_NAME = "游戏";

    public static final int REQUEST_SUCCESS = 200;

}
