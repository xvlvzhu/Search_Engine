package com.zxl.classifiy;

import java.util.HashMap;
import java.util.List;

import com.zxl.dao.SearchDAO;
import com.zxl.dao.SearchDAOJdbcImpl;
import com.zxl.javaBean.UrlData;

public class Classified_News {
	
	private static HashMap<String, String> urlMap = new HashMap<String, String>() {
	    {
	        put("house", "�Ҿ�");  
	        put("home", "�Ҿ�");
	        put("money", "�ƾ�");
	        put("finance", "�ƾ�");
	        put("cj", "�ƾ�");
	        put("mil", "����");
	        put("ent", "����");
	        put("baby", "Ӥ��");
	        put("baobao", "Ӥ��");
	        put("cul", "�Ļ�");
	        put("culture", "�Ļ�");
	        put("book", "����");
	        put("game", "��Ϸ");
	        put("sports", "����");
	        put("auto", "��ͨ");
	        put("edu", "����");
	        put("health", "����");
	        put("art", "����");
	        
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
				searchDAO.update1(url, "����", "Search_Engine_Test_ZXL_2");
				System.out.println("����");
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
