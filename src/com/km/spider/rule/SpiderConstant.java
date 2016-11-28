/** 

* @Title: SpiderConstant.java

* @Package com.km.spider.rule

* @Description: TODO(用一句话描述该文件做什么)

* @author hulikaimen@gmail.com

* @date 2016-11-20 下午9:20:59

* @version V1.0 

*/ 
package com.km.spider.rule;
/**
 * @author PipiLu
 * @version 创建时间：2016-11-20 下午9:20:59
 * 类说明	抓取  常量设置
 */
public class SpiderConstant {
	// 开始抓取网页
	public final static String	URLBASE = "http://www.cqfygzfw.gov.cn/ggsd/ggxtFront/ggxtList.shtml?sfsx=1";
	//抓取内容网页 前缀
	public final static String	URLPRE = "http://www.cqfygzfw.gov.cn/ggsd/ggxtFront/showHtml.shtml?id=";
	public final static Integer COUNT = 20;//若连续20条数据，数据库有值。则停止抓取
	public final static String FYDM = "M1D";
	
	public final static String AUTHOR = "系统网管";
	public final static Integer CATEGORYID = 4;
	public final static Integer LOOP = 5;
	
	//抓取间隔
//	public final static Integer LOOP = 5;//分
	//下载页面
//	public final static String	ALINK = "http://www.cqfygzfw.gov.cn/ggsd/ggxtFront/showGgxx.shtml?id=";
//	public final static String;
}
