/** 

* @Title: ResultSetHandlerDemo.java

* @Package com.km.spider.test

* @Description: TODO(用一句话描述该文件做什么)

* @author hulikaimen@gmail.com

* @date 2016-11-21 上午10:41:32

* @version V1.0 

*/ 
package com.km.spider.test;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Before;

import com.km.spider.bean.GGSDInfo;
import com.km.spider.bean.SortId;
import com.km.spider.dao.GGSDInfoDao;
import com.km.spider.rule.SpiderConstant;
import com.km.spider.service.ExtractService;
import com.km.spider.util.TransationManager;

/**
 * @author PipiLu
 * @version 创建时间：2016-11-21 上午10:41:32
 * 类说明 DBUtils测试
 */
public class ResultSetHandlerDemo {
	private QueryRunner qr ;
	private GGSDInfoDao dao ;
	private ExtractService service;
	@Before
	public void setUp(){
		qr = new QueryRunner();
		dao = new GGSDInfoDao(qr);
		service = new ExtractService();
	}
	
	
	//ArrayHandler:适合结果只有一条的情况。把记录中的每个字段的值封装到了一个Object[]中。
	//查询 最大newid,sortNum
	@org.junit.Test
	public void test1() throws SQLException{
//		Object[] objs = qr.query(TransationManager.getConnection(),//
//				"SELECT max(newsid) as max ,max(SortNum) as sortNum from News_News",//
//				new ArrayHandler());
//		for(Object obj:objs)//每个元素就是列的值
//			System.out.println(obj);
		SortId sortId = dao.findMaxSortId();
		System.out.println(sortId);
	}
	
	
	@org.junit.Test
	
	public void testExistGGSDById() throws SQLException{
		Boolean b = dao.existGGSDById("M1Dfcfae2a8-0fbb-49dc-99cf-1671e5232153");
		System.out.print(b);
	}
	
	@org.junit.Test
	public void testSaveGGSDInfo() throws SQLException{
		SortId sortId = dao.findMaxSortId();
		sortId.addOne();
		GGSDInfo info = new GGSDInfo();
		info.setId("M1Dfefffae7-c131-431a-940c-2fc7e3eae488");
		info.setCaseID("(2016)渝0112民初19034号");
		info.setType("变更诉讼请求申请书、开庭传票");
		info.setLitigant("李强");
		info.setAnnouncementDate(new Date());
		
		Object params[] = { sortId.getNewid(),sortId.getSortNum(),//
				info.getId(),info.getCaseID(),//id,案号
				info.getType()+":"+info.getLitigant(),//Title
				new java.sql.Date(info.getAnnouncementDate().getTime()),//还要自己转时间
				SpiderConstant.AUTHOR,//作者
				service.extracatDisplayPart(SpiderConstant.URLPRE + info.getId()),//body
				SpiderConstant.CATEGORYID//送达类型
			};
		
		String sql = "insert into News_News (NewsID,SortNum,TempCate,Dep,Title,AddedDate,zuozhe,body,CategoryID) VALUES (?,?,?,?,?,?,?,?,?)";
		qr.update(TransationManager.getConnection(),sql,params);
	}
	
}
