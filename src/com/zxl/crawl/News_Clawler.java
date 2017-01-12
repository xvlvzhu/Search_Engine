package com.zxl.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

//import org.apache.commons.httpclient.HttpMethod;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.zxl.dao.SearchDAO;
import com.zxl.dao.SearchDAOJdbcImpl;
import com.zxl.javaBean.UrlData;

public class News_Clawler {
	// 初始化种子url
	private static String[] seeds = new String[] { "http://news.sina.com.cn/", "http://www.sohu.com/",
			"http://news.163.com/", "http://www.ifeng.com/", "http://news.qq.com/", "http://www.xinhuanet.com/",
			"http://www.people.com.cn/", "http://www.chinanews.com/", "http://www.huanqiu.com/",
			"http://news.cctv.com/" };
	
	private static String[] seeds_user_defined;
	// 初始化选择器
	private final static String[] selectors = new String[] { "div.main-nav", "div.index-nav", "div.N-nav-channel",
			"div.NavM", "div.channelNavPart", "div.navCont", "div.w1000", "div.nav_navcon", "div.navMain",
			"div.top_nav" };
	// 初始类别集合(网址，中英类别)
	private static HashMap<String, String> url_seeds = new HashMap<>();
	// 线程数
	public final static int threadCount = 10;
	// 待爬取队列
	ArrayList<String> notCrawlurlSet = new ArrayList<String>();
	// 已爬取队列
	ArrayList<String> allurlSet = new ArrayList<String>();

	private static int i = 0;

	private static int exit = 0;// 程序终止标志位

	private static News_Clawler news_Clawler = new News_Clawler();

	private News_Clawler() {}

	public static News_Clawler getInstance() {
		return news_Clawler;
	}

//	public static void main(String[] args) {
//		News_Clawler news_Clawler = new News_Clawler();
//
//		new Thread(new Runnable() {
//			public void run() {
//				stop_crawler();
//			};
//		}, "thread-exit");
//
//		try {
//			news_Clawler.seed_url(news_Clawler);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		news_Clawler.begin();
//		try {
//			Thread.sleep(5500);
//		} catch (Exception e) {
//		}
//		new Thread(new Runnable() {
//			public void run() {
//				stop_crawler();
//			};
//		}, "thread-exit").start();
//	}

	public void start_crawler() {
		// News_Clawler news_Clawler = new News_Clawler();

		try {
			news_Clawler.seed_url(news_Clawler);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		news_Clawler.begin();
	}

	public void stop_crawler() {
		exit = 1;
		System.out.println("停止爬虫.......");
	}
	
	/**
	 * 用户自定义输入
	 */
	public void user_defined_url() {
		Scanner input=new Scanner(System.in);
		System.out.println("请输入您想搜索的网站个数：");
		int n = input.nextInt();
		seeds_user_defined = new String[n];
		for(int i=0;i<n;i++){
			System.out.println("请输入您想搜索的网站：");
			Scanner input1=new Scanner(System.in);
			String url = "http://"+input1.nextLine().trim();
			seeds_user_defined[i]=url;
			notCrawlurlSet.add(url);
		}
		seeds=seeds_user_defined;
		start_crawler();
	}

	/**
	 * 获取urlseed页面
	 * 
	 * @param newCl
	 * @return
	 * @throws IOException
	 */
	private HashMap<String, String> seed_url(News_Clawler newCl) throws IOException {
		// 获取所有种子页面
		Document[] documents = new Document[seeds.length];
		// 获取网页中的所有url地址，并保存在linkurls链表中
		Elements[] href = new Elements[seeds.length];
		for (int i = 0; i < documents.length; i++) {

			documents[i] = Jsoup.connect(seeds[i]).timeout(300000).get();

			href[i] = documents[i].select(selectors[i]).select("a[href]");

			for (Element hre : href[i]) {
				String linkUrl = hre.attr("abs:href");// 获取网页的绝对地址
				// String type = linkUrl.split("\\.")[0].substring(7);
				url_seeds.put(linkUrl, hre.text());
				// System.out.println(linkUrl);
				notCrawlurlSet.add(linkUrl);
			}

		}

		for (Entry<String, String> u : url_seeds.entrySet()) {
			System.out.println(u.getValue() + "-------" + u.getKey());
		}
		return null;
	}

	// /**
	// * 获取urlseed页面中的分类标签url及分类标签名
	// *
	// * @param doc
	// * @throws IOException
	// */
	// public void getclassurl(Document[] doc) throws IOException {
	// // 获取网页中的所有url地址，并保存在linkurls链表中
	// Elements[] href = new Elements[10];
	// href[0] = doc[0].select("div.main-nav").select("a[href]");
	// href[1] = doc[1].select("div.index-nav").select("a[href]");
	// href[2] = doc[2].select("div.N-nav-channel").select("a[href]");
	// href[3] = doc[3].select("div.NavM").select("a[href]");
	// href[4] = doc[4].select("div.channelNavPart").select("a[href]");
	// href[5] = doc[5].select("div.navCont").select("a[href]");
	// href[6] = doc[6].select("div.w1000").select("a[href]");
	// href[7] = doc[7].select("div.nav_navcon").select("a[href]");
	// href[8] = doc[8].select("div.navMain").select("a[href]");
	// href[9] = doc[9].select("div.top_nav").select("a[href]");
	// // String regex = "[a-zA-Z]+://[/w+][.]/huanqiu.com/[^/s]*";
	// for (int i = 0; i < href.length; i++) {
	// for (Element hre : href[i]) {
	// String linkUrl = hre.attr("abs:href");// 获取网页的绝对地址
	// String type = linkUrl.split("\\.")[0].substring(7);
	// url_seeds.put(linkUrl, hre.text());
	// // System.out.println(linkUrl);
	// }
	// }
	// // return linkurls;
	// }
	private void begin() {
		for (int i = 0; i < threadCount; i++) {
			final int j = i;
			new Thread(new Runnable() {
				public void run() {
					SearchDAO searchDAO = new SearchDAOJdbcImpl();
					while (true) {
						if (exit == 1) {
							System.out.println("停止" + j + "号爬虫");
							break;
						}
						// System.out.println("当前进入"+Thread.currentThread().getName());
						String tmp = getAUrl();
						if (tmp != null) {
							try {
								Document html_tmp = sendRequest(tmp);
								UrlData uData = parseHtml(html_tmp, tmp);
								searchDAO.save(uData);

							} catch (Exception e) {
								// e.printStackTrace();
								// System.out.println("抛弃问题页面");
								continue;
							}
						} else {
							break;
						}
					}

				}
			}, "thread-" + i).start();
		}
	}

	private synchronized String getAUrl() {
		if (notCrawlurlSet.isEmpty())
			return null;
		String tmpAUrl;
		// synchronized(notCrawlurlSet){
		tmpAUrl = notCrawlurlSet.get(0);
		notCrawlurlSet.remove(0);
		// }
		return tmpAUrl;
	}

	private synchronized void addUrl(String url, int d) {
		notCrawlurlSet.add(url);
		allurlSet.add(url);
		// depth.put(url, d);
	}

	/**
	 * 请求html页面
	 * 
	 * @return
	 */
	private Document sendRequest(String url) {
		// HttpClient client = new HttpClient();
		// // 设置代理服务器地址和端口
		// //
		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
		// // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
		// HttpMethod method = new GetMethod(url);
		// method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
		// // 使用POST方法
		// // HttpMethod method = new PostMethod("http://java.sun.com");
		// try {
		// client.executeMethod(method);
		// return method.getResponseBodyAsString();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// // 释放连接
		// method.releaseConnection();

		try {
			return Jsoup.connect(url).timeout(30000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("网络有延迟，请耐心等待！");
		}

		// 打印服务器返回的状态
		// System.out.println(method.getStatusLine());
		// 打印返回的信息
		// try {
		// return method.getResponseBodyAsString();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// // 释放连接
		// method.releaseConnection();
		// }
		return null;
	}

	/**
	 * 解析html页面
	 * 
	 * @param html_tmp
	 */
	private UrlData parseHtml(Document html_tmp, String url) {
		// Document doc = Jsoup.parse(html_tmp);
		Elements href = html_tmp.select("a[href]");
		UrlData urlData = new UrlData();
		urlData.setUrl(url);
		urlData.setHtml(html_tmp.toString());
		String tp = url_Filter(url);
		urlData.setSource(tp);

		for (Element hre : href) {
			String linkUrl = hre.attr("abs:href");// 获取网页的绝对地址
			if (url_Filter(linkUrl) != "") {
				// allurlSet.add(linkUrl);
				notCrawlurlSet.add(linkUrl);
				urlData.addUrl_link(linkUrl);
			}
			// if (!urlMap.get(type).contains(linkUrl)) {
			// if (linkUrl.contains("http://" + temp + ".163.com")) {
			// urlMap.get(type).add(linkUrl);
			// UrlData urlData = new UrlData(type, linkUrl, hre.text());
			// urlDatas.add(urlData);
			// // System.out.println(linkUrl);
			// }
			// }
		}
		allurlSet.add(url);
		// System.out.println(urlData);
		System.out.println("写入第" + i++ + "条记录" + "    网址：" + url + "    来源：" + tp);
		return urlData;

	}

	private String url_Filter(String linkurl) {
		for (int i = 0; i < seeds.length; i++) {
			String type = seeds[i].split("\\.")[1];
			if (linkurl.contains(type)) {
				return seeds[i];
			}
		}
		return "";
	}

}
