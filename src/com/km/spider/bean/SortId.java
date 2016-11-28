/** 

* @Title: SortId.java

* @Package com.km.spider.bean

* @Description: TODO(用一句话描述该文件做什么)

* @author hulikaimen@gmail.com

* @date 2016-11-21 上午11:46:49

* @version V1.0 

*/ 
package com.km.spider.bean;
/**
 * @author PipiLu
 * @version 创建时间：2016-11-21 上午11:46:49
 * 类说明   取出最大 newid,sortNum
 */
public class SortId {
	int newid;
	int sortNum;
	/**
	 * @return the newid
	 */
	public int getNewid() {
		return newid;
	}
	/**
	 * @param newid the newid to set
	 */
	public void setNewid(int newid) {
		this.newid = newid;
	}
	/**
	 * @return the sortNum
	 */
	public int getSortNum() {
		return sortNum;
	}
	/**
	 * @param sortNum the sortNum to set
	 */
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	
	
	public void addOne(){
		this.setNewid(this.newid + 1 );
		this.setSortNum(this.sortNum + 1);
	}
	
}
