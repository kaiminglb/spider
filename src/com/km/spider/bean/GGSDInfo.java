/** 

* @Title: GGSDInfo.java

* @Package com.zhy.spider.bean

* @Description: TODO(用一句话描述该文件做什么)

* @author hulikaimen@gmail.com

* @date 2016-11-18 上午6:48:31

* @version V1.0 

*/ 
package com.km.spider.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author PipiLu
 * @version 创建时间：2016-11-18 上午6:48:31
 * 类说明 公告送达信息实体类
 */
public class GGSDInfo {
	private String id;//页面跳转的id
	private String type;//公告类型
	private String caseID;//案号
	private String court;
	private String announcemenPerson;//公告人
	private String litigant;//当事人
	private Date AnnouncementDate;//公告日期
	
	private  String body;
	
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the caseID
	 */
	public String getCaseID() {
		return caseID;
	}
	/**
	 * @param caseID the caseID to set
	 */
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	/**
	 * @return the court
	 */
	public String getCourt() {
		return court;
	}
	/**
	 * @param court the court to set
	 */
	public void setCourt(String court) {
		this.court = court;
	}
	/**
	 * @return the announcemenPerson
	 */
	public String getAnnouncemenPerson() {
		return announcemenPerson;
	}
	/**
	 * @param announcemenPerson the announcemenPerson to set
	 */
	public void setAnnouncemenPerson(String announcemenPerson) {
		this.announcemenPerson = announcemenPerson;
	}
	/**
	 * @return the litigant
	 */
	public String getLitigant() {
		return litigant;
	}
	/**
	 * @param litigant the litigant to set
	 */
	public void setLitigant(String litigant) {
		this.litigant = litigant;
	}
	/**
	 * @return the announcementDate
	 */
	public Date getAnnouncementDate() {
		return AnnouncementDate;
	}
	/**
	 * @param announcementDate the announcementDate to set
	 */
	public void setAnnouncementDate(Date announcementDate) {
		AnnouncementDate = announcementDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return "GGSDInfo [id=" + id + ", type=" + type + ", caseID=" + caseID
				+ ", litigant=" + litigant
				+ ", AnnouncementDate=" + df.format(AnnouncementDate) + "]";
	}
	
	
	
}
