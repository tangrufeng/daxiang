package com.dx.entity;


public class ResultBean {

	int errCode=0;
	
	String errMsg="";
	

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	public String toString() {
		return "ResultBean [errCode=" + errCode + ", errMsg=" + errMsg + "]";
	}

	
}
