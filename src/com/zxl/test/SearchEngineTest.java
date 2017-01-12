package com.zxl.test;

import java.util.Scanner;

import com.zxl.crawl.News_Clawler;

public class SearchEngineTest {
//	public static void main(String[] args) {
//		News_Clawler news_Clawler = News_Clawler.getInstance();
//		news_Clawler.start_crawler();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		news_Clawler.stop_crawler();
//	}
	
	public static void main(String[] args) {
		News_Clawler news_Clawler = News_Clawler.getInstance();
		news_Clawler.user_defined_url();
	}
}
