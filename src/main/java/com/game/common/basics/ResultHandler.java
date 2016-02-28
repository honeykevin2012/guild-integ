package com.game.common.basics;

import java.util.HashMap;
import java.util.Map;

public class ResultHandler {
	public static Error bindError(String msg) {
		Error error = new Error();
		String[] formMsg = msg.split("#");
		if (formMsg.length == 2) {
			error.setState(formMsg[0]);
			error.setMsg(formMsg[1]);
		}
		return error;
	}

	public static Result bindResult(Result result, String msg) {
		String[] formMsg = msg.split("#");
		if (formMsg.length == 2) {
			result.setState(formMsg[0]);
			result.setMsg(formMsg[1]);
		}
		return result;
	}
	
	public static JsonResult jsonResult(JsonResult result, String msg) {
		String[] formMsg = msg.split("#");
		if (formMsg.length == 2) {
			result.setState(formMsg[0]);
			result.setMsg(formMsg[1]);
		}
		return result;
	}

	public static Result bindResult(String msg) {
		Result result = new Result();
		String[] formMsg = msg.split("#");
		if (formMsg.length == 2) {
			result.setState(formMsg[0]);
			result.setMsg(formMsg[1]);
		}
		return result;
	}
	
	public static JsonResult jsonResult(String msg) {
		JsonResult result = new JsonResult();
		String[] formMsg = msg.split("#");
		if (formMsg.length == 2) {
			result.setState(formMsg[0]);
			result.setMsg(formMsg[1]);
		}
		return result;
	}

	public static Map<String, Object> bindMap(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] message = msg.split("#");
		if (message.length == 2) {
			map.put("state", message[0]);
			map.put("msg", message[1]);
		}
		return map;
	}
}
