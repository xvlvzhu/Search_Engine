package com.zxl.test;

import java.util.HashMap;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

public class TestHashMap {
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
		System.out.println(urlMap.get("2313"));
		System.out.println(urlMap.get("mil"));
	}
}
