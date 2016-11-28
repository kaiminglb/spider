package com.km.spider.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;

import com.km.spider.bean.GGSDInfo;
import com.km.spider.core.ExtractElements;
import com.km.spider.rule.Rule;
import com.km.spider.util.TextUtil;

public class Test
{
	private Elements elements = null;
	
	@Before
	public void setUp(){
		Rule rule = new Rule("http://www.cqfygzfw.gov.cn/ggsd/ggxtFront/ggxtList.shtml;jsessionid=E7CF6A56743ED13F1EDE4D5C4782BC84?sfsx=1",
				new String[] { "fydm","page" }, new String[] { "M1D","1" },
				"div.r_wenben", Rule.SELECTION, Rule.POST);//根据标签选择
		elements = ExtractElements.extract(rule);
	}
	
	
	
	
//	@org.junit.Test
//	public void getDatasByClass()
//	{
//		Rule rule = new Rule(
//				"http://www1.sxcredit.gov.cn/public/infocomquery.do?method=publicIndexQuery",
//		new String[] { "query.enterprisename","query.registationnumber" }, new String[] { "兴网","" },
//				"cont_right", Rule.CLASS, Rule.POST);//根据class
//		List<LinkTypeData> extracts = ExtractService.extract(rule);
//		printf(extracts);
//	}

	@org.junit.Test
	public void getTotalPages()
	{
		
		//String str = eles.get(0).outerHtml();
		if(null != elements){
			String str = elements.get(0).select(".gg_fanye").text();
			int start = str.indexOf("/");
			int end = str.indexOf("页",start+1);
			String page = str.substring(start+1, end);
			System.out.println(Integer.parseInt(page));
		}
		
	}
	
	
	@org.junit.Test
	public void getDatasGGSDInfoByPage()
	{
//		Map<String,GGSDInfo> map = new HashMap<String,GGSDInfo>();
		List<GGSDInfo> list = new ArrayList<GGSDInfo>();
		if(null != elements){
			Elements trs = elements.get(0).getElementsByTag("tr");
//			System.out.println(trs.size());
			for(int i = 1 ; i < trs.size(); i++){
				Element e = trs.get(i);//tr标签
				GGSDInfo info = new GGSDInfo();
				
				System.out.println(i + "-------------");
				
				Elements tds = e.getElementsByTag("td");
				String s0,s1,s2,s3,s4,s5;
				//type
				Element e_A = tds.get(0).getElementsByTag("a").get(0);
				String type = e_A.text();
				System.out.println(type);
				//id
				s0 = e_A.attr("onclick");
				int start = s0.indexOf("'");
				String id = s0.substring(start+1, s0.lastIndexOf("'"));
				System.out.println(id);
				//caseID
				s1 = TextUtil.escapeSpace(tds.get(1).html());
				System.out.println(s1);
				
//				s2 = tds.get(2).text().trim();
				s2 = TextUtil.escapeSpace(tds.get(2).html());
				System.out.println(s2);
				
				s3 = TextUtil.escapeSpace(tds.get(3).html());
				System.out.println(s3);
				s4 = tds.get(4).text().trim();
				System.out.println(s4);
				
				//时间
				SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
				s5 = tds.get(5).text();
				System.out.println("original:" + s5);
				Date date = new Date();
				try {
					date = dateFormat.parse(s5);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日");
				String day = df.format(date);
				System.out.println(day);
				
				
				
//			System.out.println(trs.get(i).outerHtml());
			}
		}
	}
	
	
	
	
	
//	@org.junit.Test
//	public void getDatasByCssQuery()
//	{
//		Rule rule = new Rule("http://www.11315.com/search",
//				new String[] { "name" }, new String[] { "兴网" },
//				"div.g-mn div.con-model", Rule.SELECTION, Rule.GET);//根据标签选择器
//		List<LinkTypeData> extracts = ExtractService.extract(rule);
//		printf(extracts);
//	}
//	
	

//	public void printf(List<LinkTypeData> datas)
//	{
//		for (LinkTypeData data : datas)
//		{
//			System.out.println(data.getLinkText());
//			System.out.println(data.getLinkHref());
//			System.out.println("***********************************");
//		}
//
//	}
}
