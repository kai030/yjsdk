package com.yj.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public class SecretData extends JsonParseInterface implements Serializable {
	private static final long serialVersionUID = -484341736305531036L;

	private static final String u_secretId = "a";
	private static final String u_secretName = "b";
	
	/**密保问题id */
	public static int secretId; 		
	/**密保问题 */
	public String secretName;
	
	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			setInt(json, u_secretId, secretId);
			setString(json, u_secretName, secretName);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void parseJson(JSONObject json) {
		if (json == null)
			return;
		try {
			secretId = getInt(json, u_secretId);
			secretName = getString(json, u_secretName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getShortName() {
		return ShortName.SECRETDATA;
	}

	@Override
	public String toString() {
		return "SecretData [secretId=" + secretId + ", secretName="
				+ secretName + "]";
	}

}
