package com.spire.base.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import spire.commons.userservice.bean.LoginResponseBean;

public class LoginResponseBean2 extends LoginResponseBean {

	@JsonIgnoreProperties(ignoreUnknown = true)
	String isFirstLogin;

	public String getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(String isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

}
