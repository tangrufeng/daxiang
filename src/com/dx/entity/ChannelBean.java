package com.dx.entity;

/**
 * Created by Tom on 16/6/24.
 */
public class ChannelBean {
    private String id="";
    private String name="";
    private String qrcode ="";
    private String qrimage ="";
    private String status="";
    private String createtime="";
    private String updatetime="";
    private String sId="0";
    private String linkman="";
    private String phone="";

    @Override
    public String toString() {
        return "ChannelBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", qrimage='" + qrimage + '\'' +
                ", status='" + status + '\'' +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", sId='" + sId + '\'' +
                ", linkman='" + linkman + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrimage() {
        return qrimage;
    }

    public void setQrimage(String qrimage) {
        this.qrimage = qrimage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
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
}
