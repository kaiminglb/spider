/** 

 * @Title: ExtractService.java

 * @Package com.km.spider.service

 * @Description: TODO(用一句话描述该文件做什么)

 * @author hulikaimen@gmail.com

 * @date 2016-11-20 下午9:31:16

 * @version V1.0 

 */
package com.km.spider.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.km.spider.bean.GGSDInfo;
import com.km.spider.core.ExtractElements;
import com.km.spider.rule.Rule;
import com.km.spider.rule.SpiderConstant;
import com.km.spider.util.TextUtil;

/**
 * @author PipiLu
 * @version 创建时间：2016-11-20 下午9:31:16 类说明
 */
public class ExtractService {

	/**
	 * 
	* @Description: 抓取目标网页的显示部分，包括<style> 和 <body> 
	* @param url	目标 网页
	* @return    
	 */
	public  String  extracatDisplayPart(String url){
		
		System.out.println("BODY:" + url);
		Elements es = new Elements();
		
		Rule rule = new Rule(url,new String[]{}, new String[]{},//
				"html", Rule.SELECTION, Rule.GET);//根据标签选择
		es = ExtractElements.extract(rule,SpiderConstant.LOOP);
		
		//样式
//		es.get(0).getElementsByTag("style").outerHtml();
		//内容 移除 <img>
		if(es != null && es.size() > 0){
			es.get(0).getElementsByTag("body").select("img").remove();
			
			return es.get(0).getElementsByTag("style").outerHtml() //
					+ es.get(0).getElementsByTag("body").html();
		}else{
			return "";
		}
		
	}
	
	
	/**
	 * 
	* @Description: 获取总页数   
	* @param rule
	* @return    
	 */
	public int getTotalPages(Rule rule){
		Elements elements = new Elements();
		int pageNum = 0;
		elements = ExtractElements.extract(rule,SpiderConstant.LOOP);
		
		
		if(null != elements && elements.size() > 0){
			String str = elements.get(0).select(".gg_fanye").text();
			int start = str.indexOf("/");
			int end = str.indexOf("页",start+1);
			pageNum = Integer.parseInt(str.substring(start+1, end));
		}
		return pageNum;
	}

	
	
	/**
	 * 
	 * @Description: 获取某页的公告信息
	 * @param page
	 * @return 设定文件
	 */
	public List<GGSDInfo> getDatasGGSDInfoByPage(Rule rule) {

		Elements elements = new Elements();
		elements = ExtractElements.extract(rule , SpiderConstant.LOOP);

		List<GGSDInfo> list = new ArrayList<GGSDInfo>();

		if (null != elements && elements.size() > 0) {
			Elements trs = elements.get(0).getElementsByTag("tr");
			// System.out.println(trs.size());
			for (int i = 1; i < trs.size(); i++) {
				Element e = trs.get(i);// tr标签
				list.add(getGGSDInfoFromTR(e));
			}
		}
		
		return list;
	}

	/**     
	* @Description: 从tr标签 得到 公告送达对象   
	* @param e
	* @return    设定文件   
	*/  
	private GGSDInfo getGGSDInfoFromTR(Element e) {
		GGSDInfo info = new GGSDInfo();
		Elements tds = e.getElementsByTag("td");
		String type, sID, caseID, s2, s3, litigant, s5;
		// type
		Element e_A = tds.get(0).getElementsByTag("a").get(0);
		type = e_A.text();
		info.setType(type);

		// id
		sID = e_A.attr("onclick");
		int start = sID.indexOf("'");
		String id = sID.substring(start + 1, sID.lastIndexOf("'"));
		info.setId(id);
		// caseID
		caseID = TextUtil.escapeSpace(tds.get(1).html());
		info.setCaseID(caseID);

		// //法院
		s2 = TextUtil.escapeSpace(tds.get(2).html());
		info.setCourt(s2);
		// 公告人
		s3 = TextUtil.escapeSpace(tds.get(3).html());
		info.setAnnouncemenPerson(s3);
		// 当事人
		litigant = tds.get(4).text();
		info.setLitigant(litigant);

		// 公告日期
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MMM d, yyyy", Locale.ENGLISH);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		s5 = tds.get(5).text();
		Date date = new Date();
		try {
			date = dateFormat.parse(s5);
			//date = df.parse(df.format(date));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		info.setAnnouncementDate(date);
		return info;
	}
}
