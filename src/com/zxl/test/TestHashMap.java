package com.zxl.test;

import java.util.HashMap;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

public class TestHashMap {
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
		System.out.println(urlMap.get("2313"));
		System.out.println(urlMap.get("mil"));
	}
}
