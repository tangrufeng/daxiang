package com.dx.entity;

/**
 * Created by Tom on 16/6/22.
 */
public class StoreRateBean {

    private String id="";
    private String buy="";
    private String sell="";
    private String rate="";
    private String sId="";
    private String sName="";
    private String createtime="";
    private String supplierId="";

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "StoreRateBean{" +
                "id='" + id + '\'' +
                ", buy='" + buy + '\'' +
                ", sell='" + sell + '\'' +
                ", rate='" + rate + '\'' +
                ", sId='" + sId + '\'' +
                ", sName='" + sName + '\'' +
                ", createtime='" + createtime + '\'' +
                ", supplierId='" + supplierId + '\'' +
                '}';
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
