package com.zxl.javaBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Queue {
	 public ArrayList<String> arrayList = new ArrayList<>();
//	public LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();

	public Queue() {
		super();
	}
	
	public Queue(ArrayList<String> arrayList) {
		super();
		this.arrayList = arrayList;
	}

	public void addUrlLink(String url,String title) {
		arrayList.add(url);
	}
}
