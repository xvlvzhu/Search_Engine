package com.zxl.test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.zxl.dao.SearchDAO;
import com.zxl.dao.SearchDAOJdbcImpl;
import com.zxl.javaBean.UrlData;
public class HttpClientTest {
	public static void main(String[] args) throws IOException, Exception {
//		HttpClient httpClient  = new HttpClient();
//		HttpMethod method  = new GetMethod("http://www.ifeng.com/");
//		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
//		httpClient.executeMethod(method);
//		String aString =method.getResponseBodyAsString();
//		System.out.println(aString);
//		method.releaseConnection();
//		SearchDAO sDao = new SearchDAOJdbcImpl();
//		sDao.save(new UrlData("www",aString,"www","www",new ArrayList<String>(Arrays.asList("Buenos Aires", "C¨®rdoba", "La Plata"))));
		System.out.println(Jsoup.connect("http://www.ifeng.com/").get());
	
	}
	
	@Test
	public void name() throws IOException {
		System.out.println(Jsoup.connect("http://www.ifeng.com/").get());
	}
}
