/** 

* @Title: SpiderTimer.java

* @Package com.km.spider.timer

* @Description: TODO(用一句话描述该文件做什么)

* @author hulikaimen@gmail.com

* @date 2016-11-21 下午8:27:56

* @version V1.0 

*/ 
package com.km.spider.timer;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.dbutils.QueryRunner;

import com.km.spider.bean.GGSDInfo;
import com.km.spider.bean.SortId;
import com.km.spider.dao.GGSDInfoDao;
import com.km.spider.rule.Rule;
import com.km.spider.rule.SpiderConstant;
import com.km.spider.service.ExtractService;
import com.km.spider.util.TextUtil;

/**
 * @author PipiLu
 * @version 创建时间：2016-11-21 下午8:27:56
 * 类说明
 */
public class SpiderTimer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SpiderTimer.class);

	/**     
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param args    设定文件   
	 */
	public static void main(String[] args) {
		 Timer timer = new Timer();  
	     timer.schedule(new MyTask(), 1000, 3600000);
//		 timer.schedule(new MyTask(), 1000, 600000);
	}
}

class MyTask extends TimerTask {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MyTask.class);
  
	private ExtractService es = new ExtractService();
	private QueryRunner qr = new QueryRunner();
	private GGSDInfoDao dao = new GGSDInfoDao(qr);
	
	
	//爬取目标页面，存数据库  
    @Override  
    public void run() {
		if (logger.isInfoEnabled()) {
			logger.info("start-------------------"+ new Date().toString());
		}
    	long startTime=System.currentTimeMillis();
          //执行方法
          
    	int pages;
    	//存新发表的公告
    	Map<String,GGSDInfo> newInfoMap = new LinkedHashMap<String,GGSDInfo>();
    	newInfoMap.clear();
    	
    	Rule baseRule = new Rule(SpiderConstant.URLBASE,//
				new String[] { "fydm","page" }, new String[] { SpiderConstant.FYDM ,"1" },//
				"div.r_wenben", Rule.SELECTION, Rule.POST);
    	pages = es.getTotalPages(baseRule);
    	System.out.println("total:" + pages + "页");
    	
    	List<GGSDInfo> pageList = new ArrayList<GGSDInfo>();
    	int count = 0;//累计连续多少条，数据库有记录
    	//多页循环
    	for(int i=1 ; i<=pages && count < SpiderConstant.COUNT; i++){//所有页循环
    		System.out.println("第" + i + "页 ");
    		Rule rule = new Rule(SpiderConstant.URLBASE,//
    				new String[] { "fydm","page" }, new String[] { SpiderConstant.FYDM ,String.valueOf(i) },//
    				"div.r_wenben", Rule.SELECTION, Rule.POST);
    		pageList = es.getDatasGGSDInfoByPage(rule);
    		//单页循环
    		for(GGSDInfo info: pageList ){
    			String id = info.getId();
    			Boolean existed = Boolean.FALSE;
    			
    			try {
					existed = dao.existGGSDById(info.getId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
    			
    			if(existed){
    				System.out.println(id + ":" + info.getLitigant() + "  存在 ");
    				++count;
    			}else{
    				count = 0 ;//累计清零
    				newInfoMap.put(info.getId(), info);
    				System.out.println(id + ":" + info.getLitigant() + "  待bao存 ");
    			}
    			
    		}
    		
    	}//结束多页循环
    	
    	if (logger.isInfoEnabled()) {
			logger.info("total saving-------" + newInfoMap.size());
		}
    	//新发表记录 保存
		for(Map.Entry<String, GGSDInfo> m : newInfoMap.entrySet()){
			try {
				saveNewGGSDInfo(m.getValue());
			} catch (SQLException e) {
				throw new RuntimeException("数据库保存失败");
			}
		}
		
		long endTime=System.currentTimeMillis();
        float excTime=(float)(endTime-startTime)/1000;
        if (logger.isInfoEnabled()) {
			logger.info("执行时间："+excTime+"s");
		}
    }


	/**     
	* @Description: TODO(这里用一句话描述这个方法的作用)   
	* @throws SQLException    设定文件   
	*/  
	private void saveNewGGSDInfo(GGSDInfo info) throws SQLException {
		SortId sortId = dao.findMaxSortId();
		sortId.addOne();
		info.setBody(es.extracatDisplayPart(SpiderConstant.URLPRE + info.getId()));
		if(!TextUtil.isEmpty(info.getBody())){
			dao.saveGGSDInfo(info, sortId);
		}
	}  
  
}  
