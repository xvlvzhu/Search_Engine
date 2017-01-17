package com.zxl.test;

import java.util.ArrayList;

import com.zxl.javaBean.UrlData;
import com.zxl.reverseIndex.ReverseIndex;

public class TestIndex {
	public static void main(String[] args) {
		ArrayList<UrlData> queryResult = ReverseIndex.reverseIndex("D:\\test\\reverseIndex","¼¦µ°",false);
//		ReverseIndex.reverseIndex("D:\\test", "ÉÂÎ÷", true);
//		System.out.println(Double.parseDouble((1.653657347+"")));
	}
}
