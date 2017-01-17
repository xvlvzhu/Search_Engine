package com.zxl.classifiy;

import java.util.HashMap;
import java.util.List;

import com.zxl.dao.SearchDAO;
import com.zxl.dao.SearchDAOJdbcImpl;
import com.zxl.javaBean.UrlData;

public class Classified_News {
	
	private static HashMap<String, String> urlMap = new HashMap<String, String>() {
	    {
	        put("house", "家居");  
	        put("home", "家居");
	        put("money", "财经");
	        put("finance", "财经");
	        put("cj", "财经");
	        put("mil", "军事");
	        put("ent", "娱乐");
	        put("baby", "婴幼");
	        put("baobao", "婴幼");
	        put("cul", "文化");
	        put("culture", "文化");
	        put("book", "读书");
	        put("game", "游戏");
	        put("sports", "体育");
	        put("auto", "交通");
	        put("edu", "教育");
	        put("health", "健康");
	        put("art", "艺术");
	        
	    }
	};
	
	public static void main(String[] args) {
		SearchDAO searchDAO = new SearchDAOJdbcImpl();
		List<UrlData> urlDatas = searchDAO.getAll();
		for(UrlData u:urlDatas){
			String url = u.getUrl();
			String type = class_ectraction(url);
			String cnType=urlMap.get(type);
			if (cnType!=null) {
				searchDAO.update1(url, cnType, "Search_Engine_Test_ZXL_2");
				System.out.println(type);
			}else{
				searchDAO.update1(url, "其他", "Search_Engine_Test_ZXL_2");
				System.out.println("其他");
			}
		}
//		System.out.println(class_ectraction("http://china.huanqiu.com/article/2016-11/9652699.html"));
	}
	
	public static String class_ectraction(String s) {
		String string  = s.split("\\.")[0];
		if (string.length()>7) {
			
			return string.substring(7);
		}else{
			return "";
		}
	}
}
