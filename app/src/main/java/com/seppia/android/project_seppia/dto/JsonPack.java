package com.seppia.android.project_seppia.dto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * json包装对象
 * @author mr.z
 *
 */
public class JsonPack {
	//请求是否成功     约定  200：成功  其他：异常
	private int code = -1;
	private boolean status = true;
	private String tag = "";
	//异常信息
	private String err_msg = "";
	//对象 可以为null
	private JSONObject result = null;
	
	public JsonPack() {
		
	}
	
	public JsonPack(int resu, String msg) {
		this.code = resu;
		this.err_msg = msg;
	}
	
	public JsonPack(int resu, String msg, JSONObject data) {
		this.code = resu;
		this.err_msg = msg;
		this.result = data;
	}
	public JsonPack(boolean st, String tag, String err, JSONObject data){
		this.status = st;
		this.tag = tag;
		this.err_msg = err;
		this.result = data;
	}
	
	//get,set-------------------------------------------------------------------
	
	public JSONObject getResult() {
		return result;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public static JsonPack toBean(String jsonStr) {
		JsonPack jp = null;
		try {
			JSONObject jObj = new JSONObject(jsonStr);
			jp = new JsonPack();
			if (jObj.has("code")) {
				jp.setCode(jObj.getInt("code"));
			}
			if (jObj.has("tag")) {
				jp.setTag(jObj.getString("tag"));
			}
			if (jObj.has("status")) {
				jp.setStatus(jObj.getBoolean("status"));
			}
			if (jObj.has("err_msg")) {
				jp.setErr_msg(jObj.getString("err_msg"));
			}
			if (jObj.has("result")) {
				jp.setResult(new JSONObject(jObj.getString("result")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jp;
	}
}
