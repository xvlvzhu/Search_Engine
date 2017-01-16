package com.zxl.test;

import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestJsoup {
public static void main(String[] args) throws IOException {
	
	
	
//	Document doc = Jsoup.parse(readTxtFile("news"));
////	System.out.println(doc);
//	String text = doc.select("div.qq_article p").text();//.text(); 
//    text=text.replace(Jsoup.parse(" ").text(), "");
//    text=text.replace(Jsoup.parse("a[href]").text(), "");
//    System.out.println(text);
	
	Document doc = Jsoup.parse(readTxtFile("news2"));
//	System.out.println(doc);
	String text = doc.select("h1").eq(0).text();//.text(); 
    text=text.replace(Jsoup.parse(" ").text(), "");
    text=text.replace(Jsoup.parse("a[href]").text(), "");
    System.out.println(text);
	
}

public static String readTxtFile(String fileName) throws IOException{
	File file = new File(fileName);
	  
	  BufferedReader bf = new BufferedReader(new FileReader(file));
	  
	  String content = "";
	  StringBuilder sb = new StringBuilder();
	  
	  while(content != null){
	   content = bf.readLine();
	   
	   if(content == null){
	    break;
	   }
	   
	   sb.append(content.trim());
	  }
	  
	bf.close();
	  return sb.toString();
	 }

}
