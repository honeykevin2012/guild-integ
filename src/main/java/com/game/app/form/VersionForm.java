package com.game.app.form;

import javax.validation.constraints.NotNull;

import com.game.common.BaseForm;



public class VersionForm extends BaseForm {

	//版本号
	@NotNull(message = "error#app版本号为空.")
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	} 

	
}
