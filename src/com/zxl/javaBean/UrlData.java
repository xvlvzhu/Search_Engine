package com.zxl.javaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ҳ��Ϣ��
 * 
 * @author zxl
 *
 */
public class UrlData {
	private String url; // ��ַ
	private String html; // html
	private String type; // ��ҳ����
	private String source; // ��Դ
	private ArrayList<String> url_link = new ArrayList<>(); // ָ���url����

	public UrlData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UrlData(String url, String html, String type, String source, ArrayList<String> url_link) {
		super();
		this.url = url;
		this.html = html;
		this.type = type;
		this.source = source;
		this.url_link = url_link;
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

}
