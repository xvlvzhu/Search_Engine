package com.zxl.util;

import java.util.Comparator;

import com.zxl.javaBean.UrlData;

public class SortByRank implements Comparator<UrlData>{

	@Override
	public int compare(UrlData o1, UrlData o2) {
		// TODO Auto-generated method stub
		if (o1.getRank()!=o2.getRank()) {
			return (int) ((o1.getRank()-o2.getRank())*1000);
		}
		return 0;
	}
}
