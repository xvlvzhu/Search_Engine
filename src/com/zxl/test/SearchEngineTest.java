package com.zxl.test;

import java.util.Scanner;

import com.zxl.crawl.Balanced_Crawler;
import com.zxl.crawl.News_Clawer_2;
import com.zxl.crawl.News_Clawler;

public class SearchEngineTest {
	public static void main(String[] args) {
		News_Clawer_2 news_Clawler = News_Clawer_2.getInstance();
		news_Clawler.start_crawler();
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		news_Clawler.stop_crawler();
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(Thread.activeCount());
//		news_Clawler.start_crawler();
	}
	
//	public static void main(String[] args) {
//		News_Clawler news_Clawler = News_Clawler.getInstance();
//		news_Clawler.user_defined_url();
//	}
}
