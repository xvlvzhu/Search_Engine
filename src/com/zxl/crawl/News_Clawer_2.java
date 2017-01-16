package com.zxl.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.net.ssl.SSLException;

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
import com.zxl.javaBean.Queue;
import com.zxl.javaBean.UrlData;

/**
 * 1 在html页面中提取文本 提取新闻长度小于20的新闻 2 将新闻头页面放入列表
 * 
 * @author zxl
 *
 */
public class News_Clawer_2 {
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

	private final static String[] news_selectors = new String[] { "div#artibody", "div#contentText", "div#endText",
			"div#main_content", "div.qq_article", "div#content", "div#rwb_zw", "div.content", "div#text", "div.cnt_bd" };

	// 初始类别集合(网址，中英类别)
	private static HashMap<String, String> url_seeds = new HashMap<>();
	// 线程数
	public final static int threadCount = 10;
	// 待爬取队列
//	ArrayList<String> notCrawlurlSet = new ArrayList<String>();
	LinkedHashMap<String,String> notCrawlurlSet = new LinkedHashMap<>();
	private static Queue[] q = new Queue[seeds.length];

	static {
		for (int i = 0; i < q.length; i++) {
			q[i] = new Queue();
			q[i].arrayList.add(seeds[i]);
		}
	}

	// 已爬取队列
	ArrayList<String> allurlSet = new ArrayList<String>();

	private static int i = 0;

	private static int exit = 0;// 程序终止标志位

	private static News_Clawer_2 news_Clawler = new News_Clawer_2();

	private News_Clawer_2() {
	}

	public static News_Clawer_2 getInstance() {
		return news_Clawler;
	}

	// public static void main(String[] args) {
	// News_Clawler news_Clawler = new News_Clawler();
	//
	// new Thread(new Runnable() {
	// public void run() {
	// stop_crawler();
	// };
	// }, "thread-exit");
	//
	// try {
	// news_Clawler.seed_url(news_Clawler);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// news_Clawler.begin();
	// try {
	// Thread.sleep(5500);
	// } catch (Exception e) {
	// }
	// new Thread(new Runnable() {
	// public void run() {
	// stop_crawler();
	// };
	// }, "thread-exit").start();
	// }

	public void start_crawler() {
		// News_Clawler news_Clawler = new News_Clawler();
		exit = 0;
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
		Scanner input = new Scanner(System.in);
		System.out.println("请输入您想搜索的网站个数：");
		int n = input.nextInt();
		seeds_user_defined = new String[n];
		for (int i = 0; i < n; i++) {
			System.out.println("请输入您想搜索的网站：");
			Scanner input1 = new Scanner(System.in);
			String url = "http://" + input1.nextLine().trim();
			seeds_user_defined[i] = url;
			notCrawlurlSet.put(url,"用户自定义网站");
		}
		seeds = seeds_user_defined;
		start_crawler();
	}

	/**
	 * 获取urlseed页面
	 * 
	 * @param newCl
	 * @return
	 * @throws IOException
	 */
	private HashMap<String, String> seed_url(News_Clawer_2 newCl) throws IOException {
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
				if (url_Filter(linkUrl) != "") {

					// notCrawlurlSet.add(linkUrl);
					q[i].arrayList.add(linkUrl);
				}
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
						String tmp = getAUrl(j);
						if (tmp != null) {
							try {
								Document html_tmp = sendRequest(tmp);
								UrlData uData = parseHtml(html_tmp, tmp, j);
								if (uData!=null) {
									
									searchDAO.save1(uData, "Search_Engine_Test_ZXL_2");
								}

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

	private synchronized String getAUrl(int j) {
		if (q[j].arrayList.isEmpty())
			return null;
		String tmpAUrl;
		// synchronized(notCrawlurlSet){
		tmpAUrl = q[j].arrayList.get(0);
		q[j].arrayList.remove(0);
		// }
		return tmpAUrl;
	}

//	private synchronized void addUrl(String url, int j) {
//		q[j].linkedHashMap.;
//		allurlSet.add(url);
//		// depth.put(url, d);
//	}

	/**
	 * 请求html页面
	 * 
	 * @return
	 */
	private Document sendRequest(String url) {
		try {
			return Jsoup.connect(url).timeout(30000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("网络有延迟，请耐心等待！");
		}
		return null;
	}

	/**
	 * 解析html页面
	 * 
	 * @param html_tmp
	 */
	private UrlData parseHtml(Document html_tmp,String url, int j) {
		// Document doc = Jsoup.parse(html_tmp);
		Elements href = html_tmp.select("a[href]");
		String title = html_tmp.select("h1").eq(0).text();
		
		
		UrlData urlData = new UrlData();
		
		String text = html_tmp.select(news_selectors[j]+" p").text(); 
	    text=text.replace(Jsoup.parse(" ").text(), "");
	    text=text.replace(Jsoup.parse("a[href]").text(), "");
//	    System.out.println(text);
	    for (Element hre : href) {
			String linkUrl = hre.attr("abs:href");// 获取网页的绝对地址
			if (url_Filter(linkUrl) != "") {
				// allurlSet.add(linkUrl);
				q[j].arrayList.add(linkUrl);
				urlData.addUrl_link(linkUrl);
			}
	    }
		if (text.length()>20 && title.length()>5) {
			urlData.setUrl(url);
			urlData.setHtml(text);
			urlData.setTitle(title);
			String tp = url_Filter(url);
			urlData.setSource(tp);
		}else{
			return null;
		}
		allurlSet.add(url);
//		 System.out.println(urlData);
		System.out.println("写入第" + i++ + "条记录" + "    网址：" + url + "    来源：" + urlData.getSource());
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
