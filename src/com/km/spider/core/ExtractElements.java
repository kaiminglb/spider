package com.km.spider.core;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.km.spider.rule.Rule;
import com.km.spider.rule.RuleException;
import com.km.spider.util.TextUtil;

/**
 *  抓取 指定 元素
 */
public class ExtractElements
{
	/**
	 * @param rule 规则
	 * @return
	 */
	public static Elements extract(Rule rule)
	{

		// 进行对rule的必要校验
		validateRule(rule);

		Elements results = null;
		try
		{
			/**
			 * 解析rule
			 */
			String url = rule.getUrl();
			String[] params = rule.getParams();
			String[] values = rule.getValues();
			String resultTagName = rule.getResultTagName();
			int type = rule.getType();
			int requestType = rule.getRequestMoethod();
			
//			try {
//				System.out.println("pause 1 second");
//				Thread.currentThread().sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			Connection conn = Jsoup.connect(url);
			// 设置查询参数

			if (params != null)
			{
				for (int i = 0; i < params.length; i++)
				{
					conn.data(params[i], values[i]);
				}
			}

			// 设置请求类型
			Document doc = null;
			switch (requestType)
			{
			case Rule.GET:
				doc = conn.timeout(8000).get();
				break;
			case Rule.POST:
				doc = conn.timeout(8000).post();
				break;
			}

			//处理返回数据
			
			
			
			
			results = new Elements();
			
			switch (type)
			{
			case Rule.CLASS:
				results = doc.getElementsByClass(resultTagName);
				break;
			case Rule.ID:
				Element result = doc.getElementById(resultTagName);
				results.add(result);
				break;
			case Rule.SELECTION:
				results = doc.select(resultTagName);
				break;
			default:
				//当resultTagName为空时默认取body标签
				if (TextUtil.isEmpty(resultTagName))
				{
					results = doc.getElementsByTag("body");
				}
			}

		} catch (IOException e)
		{
			e.printStackTrace();
//			throw new RuntimeException("超时，网络异常");
		}

		return results;
	}
	
	/**
	 * 
	* @param rule
	* @param num 循环次数
	 */
	
	public static Elements extract(Rule rule , int num){
		Elements results = new Elements();
		for(int i = 0; i < num ; i++){
			 Random rand = new Random();
		     int randNum = rand.nextInt(2)+1;
			try {
				Thread.currentThread().sleep(randNum*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(null == results || results.size() == 0){
				results = extract(rule);
			}
			if(null != results)
				break;
		}
		return results;
	}
	

	/**
	 * 对传入的参数进行必要的校验
	 */
	private static void validateRule(Rule rule)
	{
		String url = rule.getUrl();
		if (TextUtil.isEmpty(url))
		{
			throw new RuleException("url不能为空！");
		}
		if (!url.startsWith("http://"))
		{
			throw new RuleException("url的格式不正确！");
		}

		if (rule.getParams() != null && rule.getValues() != null)
		{
			if (rule.getParams().length != rule.getValues().length)
			{
				throw new RuleException("参数的键值对个数不匹配！");
			}
		}
	}

}
