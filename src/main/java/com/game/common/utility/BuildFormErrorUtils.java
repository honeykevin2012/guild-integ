package com.game.common.utility;

import com.game.common.basics.Error;

public class BuildFormErrorUtils {
	public static Error buildFormError(String errorMsg) {
		Error error = new Error();
		String[] formMsg = errorMsg.split("#");
		if(formMsg.length == 2){
			error.setState(formMsg[0]);
			error.setMsg(formMsg[1]);
		}else{
			error.setState("error");
			error.setMsg(errorMsg);
		}
		return error;
	}
}
