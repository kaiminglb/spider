/** 

* @Title: gGSDInfoDao.java

* @Package com.km.spider.dao

* @Description: TODO(用一句话描述该文件做什么)

* @author hulikaimen@gmail.com

* @date 2016-11-21 上午11:45:30

* @version V1.0 

*/ 
package com.km.spider.dao;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.km.spider.bean.GGSDInfo;
import com.km.spider.bean.SortId;
import com.km.spider.rule.SpiderConstant;
import com.km.spider.util.TransationManager;

/**
 * @author PipiLu
 * @version 创建时间：2016-11-21 上午11:45:30
 * 类说明
 */
public class GGSDInfoDao {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GGSDInfoDao.class);

	private QueryRunner qr;
	
	public GGSDInfoDao(QueryRunner qr) {
		super();
		this.qr = qr;
	}

	//返回 最大 id
	public SortId findMaxSortId() throws SQLException {
			return qr.query(TransationManager.getConnection(),//
					"SELECT max(newsid) as newid ,max(SortNum) as sortNum from News_News", //
					new BeanHandler<SortId>(SortId.class));
	}
	
	/**
	 * 
	* @Description:根据id查询字段 TempCate,若查到结果返回true
	* @param id
	* @return Ture 存在；false 数据库没记录
	* @throws SQLException    
	 */
	public Boolean existGGSDById(String id) throws SQLException{
		Boolean bool = Boolean.FALSE;
		List<Object[]> list = qr.query(TransationManager.getConnection(), //
				"SELECT TempCate from News_News where TempCate = ?", //
				new ArrayListHandler(), //
				id);
		if (list.size() > 0) {
			bool = Boolean.TRUE;
		}
		return bool;
	}
	
	/**
	 * 
	* @Description: 新增ggsd   
	* @param info
	* @param sortId	新增记录 id,SortNum
	* @throws SQLException    设定文件
	 */
	//TempCate : 公告id，Dep : 公告案号，Title:（类型:当事人）
	//AddedDate : 公告日期， zuozhe : 系统网管，
	
	public void saveGGSDInfo(GGSDInfo info,SortId sortId){
		Object params[] = { sortId.getNewid(),sortId.getSortNum(),//
				info.getId(),info.getCaseID(),//id(目标页面一条记录对应ID),案号
				info.getType()+":"+info.getLitigant(),//Title  5th
				new java.sql.Date(info.getAnnouncementDate().getTime()),//还要自己转时间
				SpiderConstant.AUTHOR,//作者
//				service.extracatDisplayPart(SpiderConstant.URLPRE + info.getId()),//
				info.getBody(),SpiderConstant.CATEGORYID, 1 , 0 //body  8th param,送达类型 4 ,Approved(正常显示) 1, gd:0
			};
		//插入9个字段
		String sql = "insert into News_News (NewsID,SortNum,TempCate,Dep,Title,AddedDate,zuozhe,body,CategoryID,Approved,gd) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		try {
			TransationManager.startTransaction();
			qr.update(TransationManager.getConnection(),sql,params);
			TransationManager.commit();

			if (logger.isInfoEnabled()) {
				logger.info("saveGGSDInfo " + sortId.getNewid() + ":" +info.getType()+":"//
						+info.getLitigant() +":"+ info.getId());
			}
			
		} catch (SQLException e) {
			TransationManager.rollback();
			throw new RuntimeException(e);
		}finally{
			TransationManager.release();
		}
	}
	
	/**
	 * 
	* @Description: 根据传入的ids，设置显示的info，对应字段approved为1。过期设为4 
	* @param ids    需显示的info的id集合
	 */
	public void updateApproved(String[] ids){
		
		// 1、所有 approved=1&&CategoryID=4的 update更新  approved=4(过期)
		String sql = "update News_News SET approved=4 WHERE approved=1 AND CategoryID=4";
		String sqlUpdate = "update News_News SET approved=1 WHERE TempCate = ?";
		// 2、循环ids，更新approved=1
		for(String id : ids){
			
		}
	}
}
