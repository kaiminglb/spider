package com.km.spider.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import com.km.spider.bean.GGSDInfo;
import com.km.spider.dao.GGSDInfoDao;
import com.km.spider.rule.Rule;
import com.km.spider.rule.SpiderConstant;
import com.km.spider.service.ExtractService;


/**
 * @author PipiLu
 * @version 创建时间：2016-11-20 下午9:56:19
 * 类说明
 */
public class TestExtractService {
	ExtractService service = new ExtractService();
	
	@org.junit.Test
	public void testExtracatDisplayPart(){
		String url = //
				"http://www.cqfygzfw.gov.cn/ggsd/ggxtFront/showHtml.shtml?id=M1D87abee60-fa55-4d09-a4b6-541568c893c0";
		String rs  = service.extracatDisplayPart(url);
		System.out.println(rs);
	}
	
	//抓取单页信息
	@org.junit.Test
	public void testGetDatasGGSDInfoByPage(){
		String url = SpiderConstant.URLBASE;
		Rule rule = new Rule(url,//
				new String[] { "fydm","page" }, new String[] { "M1D","1" },
				"div.r_wenben", Rule.SELECTION, Rule.POST);
		List<GGSDInfo> list = service.getDatasGGSDInfoByPage(rule);
		for(GGSDInfo info : list){
			System.out.println(info);
		}
	}
	
	
	@org.junit.Test
	public void testGetAll(){
		QueryRunner qr = new QueryRunner();
		GGSDInfoDao dao = new GGSDInfoDao(qr);
		ExtractService es = new ExtractService();
		
		int pages;
    	//存新发表的公告
    	Map<String,GGSDInfo> newInfoMap = new HashMap<String,GGSDInfo>();
    	
    	
    	Rule baseRule = new Rule(SpiderConstant.URLBASE,//
				new String[] { "fydm","page" }, new String[] { SpiderConstant.FYDM ,"1" },//
				"div.r_wenben", Rule.SELECTION, Rule.POST);
    	pages = es.getTotalPages(baseRule);
    	System.out.println("total:" + pages + "页");
    	
    	List<GGSDInfo> pageList = new ArrayList<GGSDInfo>();
    	int count = 0;//累计连续多少条，数据库有记录
    	for(int i=1 ; i<=pages && count < SpiderConstant.COUNT; i++){//所有页循环
    		System.out.println("第" + i + "页 ");
    		Rule rule = new Rule(SpiderConstant.URLBASE,//
    				new String[] { "fydm","page" }, new String[] { SpiderConstant.FYDM ,String.valueOf(i) },//
    				"div.r_wenben", Rule.SELECTION, Rule.POST);
    		pageList = es.getDatasGGSDInfoByPage(rule);
    		//每页循环
    		for(GGSDInfo info: pageList ){
    			String id = info.getId();
    			Boolean existed = Boolean.FALSE;
    			
    			try {
					existed = dao.existGGSDById(info.getId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
    			
    			if(existed){
    				System.out.println(id + "  存在 ");
    				++count;
    			}else{
    				count = 0 ;//累计清零
    				newInfoMap.put(info.getId(), info);
    				System.out.println(id + "  待存 ");
    			}
    		}
    	}
	}
}
