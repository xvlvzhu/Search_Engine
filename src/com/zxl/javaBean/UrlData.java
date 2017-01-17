package com.zxl.javaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 网页信息类
 * 
 * @author zxl
 *
 */
public class UrlData implements Comparable<UrlData>{
	private String url; // 网址
	private String title; // 标题
	private String html; // html
	private String type; // 分类
	private String source ; // 来源
	private Double rank;
	private ArrayList<String> url_link = new ArrayList<>(); // 指向的url链接

	public UrlData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

//	public UrlData(String url, String html) {
//		super();
//		this.url = url;
//		this.html = html;
//	}
//	
//	



	public UrlData(String url, String html, String title) {
		super();
		this.url = url;
		this.title = title;
		this.html = html;
	}
	
	



	public UrlData(String url, String html,String title,  Double rank) {
	super();
	this.url = url;
	this.title = title;
	this.html = html;
	this.rank = rank;
}



	public UrlData(String url, String title, String html, String type, String source, ArrayList<String> url_link) {
		super();
		this.url = url;
		this.title = title;
		this.html = html;
		this.type = type;
		this.source = source;
		this.url_link = url_link;
	}

	
	public Double getRank() {
		return rank;
	}



	public void setRank(Double rank) {
		this.rank = rank;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl_link() {
		String string = "";
		for (String s : url_link) {
			if (string != "") {
				string = string + ";" + s;
			} else{
				string = s;
			}
		}
		return string;
	}

	public void addUrl_link(String link) {
		url_link.add(link);
	}

	@Override
	public String toString() {
		return "UrlData [url=" + url + ", html=" + html + ", type=" + type + ", source=" + source + "]";
	}



	@Override
	public int compareTo(UrlData o) {
//		if (this.getRank()>o.getRank()) {
//			return -1;
//		}
//		if (this.getRank()==o.getRank()) {
//			return 0;
//		}
//		return 1;
//		
		if(this == null && o == null) {  
		    return 0;  
		}  
		if(this == null) {  
		    return -1;  
		}  
		if(o == null) {  
		    return 1;  
		}  
		if(this.getRank() > o.getRank()) {  
		    return -1;  
		}  
		if(o.getRank() > this.getRank()) {  
		    return 1;  
		}  
		return 0; 
	}

}
