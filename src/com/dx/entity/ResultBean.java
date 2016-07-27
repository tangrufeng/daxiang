package com.dx.entity;


public class ResultBean {

	int errCode=0;
	
	String errMsg="";

	String isExist="";

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

	public String getIsExist() {
		return isExist;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}

	@Override
	public String toString() {
		return "ResultBean [errCode=" + errCode + ", errMsg=" + errMsg + "]";
	}

	
}
