package com.dx.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBean implements Serializable{

	private String id="";
	
	private String openId="";
	
	private String buy="";
	
	private String sell="";
	
	private String takeDate="";
	
	private int cityId=0;
	
	private int storeId=0;
	
	private int sum=0;
	
	private String changer="";
	
	private String idType="";
	
	private String idNo="";
	
	private String referenceID ="";
	
	private String mobileNo ="";
	
	private int status=0;
	
	private String createTime;
	
	private String updateTime;
	
	private double rate=0;
	
	private int areaId=0;
	

	
	public OrderBean() {
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}



	public void setOpenId(String openId) {
		this.openId = openId;
	}



	public String getBuy() {
		return buy;
	}



	public void setBuy(String buy) {
		this.buy = buy;
	}



	public String getSell() {
		return sell;
	}



	public void setSell(String sell) {
		this.sell = sell;
	}



	public String getTakeDate() {
		return takeDate;
	}



	public void setTakeDate(String takeDate) {
		this.takeDate = takeDate;
	}



	public int getCityId() {
		return cityId;
	}



	public void setCityId(int cityId) {
		this.cityId = cityId;
	}



	public int getStoreId() {
		return storeId;
	}



	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}



	public int getSum() {
		return sum;
	}



	public void setSum(int sum) {
		this.sum = sum;
	}



	public String getChanger() {
		return changer;
	}



	public void setChanger(String changer) {
		this.changer = changer;
	}



	public String getIdType() {
		return idType;
	}



	public void setIdType(String idType) {
		this.idType = idType;
	}



	public String getIdNo() {
		return idNo;
	}



	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}



	public String getReferenceID() {
		return referenceID;
	}



	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}


	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getAreaId() {
		return areaId;
	}



	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}



	public String getMobileNo() {
		return mobileNo;
	}



	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}



	@Override
	public String toString() {
		return "OrderBean [id=" + id + ", openId=" + openId + ", buy=" + buy
				+ ", sell=" + sell + ", takeDate=" + takeDate + ", cityId="
				+ cityId + ", storeId=" + storeId + ", sum=" + sum
				+ ", changer=" + changer + ", idType=" + idType + ", idNo="
				+ idNo + ", referenceID=" + referenceID + ", mobileNo="
				+ mobileNo + ", status=" + status + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", rate=" + rate
				+ ", areaId=" + areaId + "]";
	}



	
}
