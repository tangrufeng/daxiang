package com.dx.entity;

public class StoreBean {
	private String id       ="";
	private String sid      ="";
	private String supplier ="";
	private String name     ="";
	private String address  ="";
	private String openingHours ="";
	private String max      ="";
	private String min      ="";
	private String linkman  ="";
	private String phone    ="";
	private String city     ="";
	private String province ="";
	private String area     ="";
	private String ahead    ="";
	private String rate ="";

	public StoreBean(){

	}

	@Override
	public String toString() {
		return "StoreBean{" +
				"id='" + id + '\'' +
				", sid='" + sid + '\'' +
				", supplier='" + supplier + '\'' +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", openingHours='" + openingHours + '\'' +
				", max='" + max + '\'' +
				", min='" + min + '\'' +
				", linkman='" + linkman + '\'' +
				", phone='" + phone + '\'' +
				", city='" + city + '\'' +
				", province='" + province + '\'' +
				", area='" + area + '\'' +
				", ahead='" + ahead + '\'' +
				", rate='" + rate + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAhead() {
		return ahead;
	}

	public void setAhead(String ahead) {
		this.ahead = ahead;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}
