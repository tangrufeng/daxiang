package com.dx.entity;

import java.util.ArrayList;
import java.util.List;

public class ResultListBean extends ResultBean{
	
	private int cnt=0;

	private List<Object> list=new ArrayList<Object>();

	public int getCnt() {
		return cnt;
	}


	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
		if(list!=null){
			this.cnt=list.size();
		}
	}


	public void setCnt(int cnt) {
		this.cnt = cnt;
	}


	@Override
	public String toString() {
		return "ResultListBean [cnt=" + cnt + ", list=" + list + ", errcode="
				+ errCode + ", errmsg=" + errMsg + "]";
	}


	
	
}
