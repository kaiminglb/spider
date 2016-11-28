package com.km.spider.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TextUtil
{
	public static  boolean isEmpty(String str)
	{
		if(str == null || str.trim().length() == 0)
		{
			return true ;
		}
		return false ;
	}
	
	//去除 node文本中的占位符
	public static String escapeSpace(String str){
		str = str.replace("&nbsp;","");
		Document doc = Jsoup.parse(str);
		return doc.body().text();
	}
}
