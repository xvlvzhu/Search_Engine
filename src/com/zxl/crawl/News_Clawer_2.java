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
 * 1 ��htmlҳ������ȡ�ı� ��ȡ���ų���С��20������ 2 ������ͷҳ������б�
 * 
 * @author zxl
 *
 */
public class News_Clawer_2 {
	// ��ʼ������url
	private static String[] seeds = new String[] { "http://news.sina.com.cn/", "http://www.sohu.com/",
			"http://news.163.com/", "http://www.ifeng.com/", "http://news.qq.com/", "http://www.xinhuanet.com/",
			"http://www.people.com.cn/", "http://www.chinanews.com/", "http://www.huanqiu.com/",
			"http://news.cctv.com/" };

	private static String[] seeds_user_defined;
	// ��ʼ��ѡ����
	private final static String[] selectors = new String[] { "div.main-nav", "div.index-nav", "div.N-nav-channel",
			"div.NavM", "div.channelNavPart", "div.navCont", "div.w1000", "div.nav_navcon", "div.navMain",
			"div.top_nav" };

	private final static String[] news_selectors = new String[] { "div#artibody", "div#contentText", "div#endText",
			"div#main_content", "div.qq_article", "div#content", "div#rwb_zw", "div.content", "div#text", "div.cnt_bd" };

	// ��ʼ��𼯺�(��ַ����Ӣ���)
	private static HashMap<String, String> url_seeds = new HashMap<>();
	// �߳���
	public final static int threadCount = 10;
	// ����ȡ����
//	ArrayList<String> notCrawlurlSet = new ArrayList<String>();
	LinkedHashMap<String,String> notCrawlurlSet = new LinkedHashMap<>();
	private static Queue[] q = new Queue[seeds.length];

	static {
		for (int i = 0; i < q.length; i++) {
			q[i] = new Queue();
			q[i].arrayList.add(seeds[i]);
		}
	}

	// ����ȡ����
	ArrayList<String> allurlSet = new ArrayList<String>();

	private static int i = 0;

	private static int exit = 0;// ������ֹ��־λ

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
		System.out.println("ֹͣ����.......");
	}

	/**
	 * �û��Զ�������
	 */
	public void user_defined_url() {
		Scanner input = new Scanner(System.in);
		System.out.println("������������������վ������");
		int n = input.nextInt();
		seeds_user_defined = new String[n];
		for (int i = 0; i < n; i++) {
			System.out.println("������������������վ��");
			Scanner input1 = new Scanner(System.in);
			String url = "http://" + input1.nextLine().trim();
			seeds_user_defined[i] = url;
			notCrawlurlSet.put(url,"�û��Զ�����վ");
		}
		seeds = seeds_user_defined;
		start_crawler();
	}

	/**
	 * ��ȡurlseedҳ��
	 * 
	 * @param newCl
	 * @return
	 * @throws IOException
	 */
	private HashMap<String, String> seed_url(News_Clawer_2 newCl) throws IOException {
		// ��ȡ��������ҳ��
		Document[] documents = new Document[seeds.length];
		// ��ȡ��ҳ�е�����url��ַ����������linkurls������
		Elements[] href = new Elements[seeds.length];
		for (int i = 0; i < documents.length; i++) {

			documents[i] = Jsoup.connect(seeds[i]).timeout(300000).get();

			href[i] = documents[i].select(selectors[i]).select("a[href]");

			for (Element hre : href[i]) {
				String linkUrl = hre.attr("abs:href");// ��ȡ��ҳ�ľ��Ե�ַ
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
	// * ��ȡurlseedҳ���еķ����ǩurl�������ǩ��
	// *
	// * @param doc
	// * @throws IOException
	// */
	// public void getclassurl(Document[] doc) throws IOException {
	// // ��ȡ��ҳ�е�����url��ַ����������linkurls������
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
	// String linkUrl = hre.attr("abs:href");// ��ȡ��ҳ�ľ��Ե�ַ
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
							System.out.println("ֹͣ" + j + "������");
							break;
						}
						// System.out.println("��ǰ����"+Thread.currentThread().getName());
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
								// System.out.println("��������ҳ��");
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
	 * ����htmlҳ��
	 * 
	 * @return
	 */
	private Document sendRequest(String url) {
		try {
			return Jsoup.connect(url).timeout(30000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("�������ӳ٣������ĵȴ���");
		}
		return null;
	}

	/**
	 * ����htmlҳ��
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
			String linkUrl = hre.attr("abs:href");// ��ȡ��ҳ�ľ��Ե�ַ
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
		System.out.println("д���" + i++ + "����¼" + "    ��ַ��" + url + "    ��Դ��" + urlData.getSource());
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
