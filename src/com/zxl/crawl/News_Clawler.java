package com.zxl.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

//import org.apache.commons.httpclient.HttpMethod;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zxl.dao.SearchDAO;
import com.zxl.dao.SearchDAOJdbcImpl;
import com.zxl.javaBean.UrlData;

public class News_Clawler {
	// ��ʼ������url
	private final static String[] seeds = new String[] { "http://news.sina.com.cn/", "http://www.sohu.com/",
			"http://news.163.com/", "http://www.ifeng.com/", "http://news.qq.com/", "http://www.xinhuanet.com/",
			"http://www.people.com.cn/", "http://www.chinanews.com/", "http://www.huanqiu.com/",
			"http://news.cctv.com/" };
	// ��ʼ��ѡ����
	private final static String[] selectors = new String[] { "div.main-nav", "div.index-nav", "div.N-nav-channel",
			"div.NavM", "div.channelNavPart", "div.navCont", "div.w1000", "div.nav_navcon", "div.navMain",
			"div.top_nav" };
	// ��ʼ��𼯺�(��ַ����Ӣ���)
	private static HashMap<String, String> url_seeds = new HashMap<>();
	// �߳���
	public final static int threadCount = 10;
	// ����ȡ����
	ArrayList<String> notCrawlurlSet = new ArrayList<String>();
	// ����ȡ����
	ArrayList<String> allurlSet = new ArrayList<String>();
	
	private static int i=0; 
	
	public static void main(String[] args) {
		News_Clawler news_Clawler = new News_Clawler();
		try {
			news_Clawler.seed_url(news_Clawler);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		news_Clawler.begin();

	}

	/**
	 * ��ȡurlseedҳ��
	 * 
	 * @param newCl
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, String> seed_url(News_Clawler newCl) throws IOException {
		// ��ȡ��������ҳ��
		Document[] documents = new Document[10];
		// ��ȡ��ҳ�е�����url��ַ����������linkurls������
		Elements[] href = new Elements[10];
		for (int i = 0; i < documents.length; i++) {

			documents[i] = Jsoup.connect(seeds[i]).timeout(300000).get();

			href[i] = documents[i].select(selectors[i]).select("a[href]");

			for (Element hre : href[i]) {
				String linkUrl = hre.attr("abs:href");// ��ȡ��ҳ�ľ��Ե�ַ
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
			new Thread(new Runnable() {
				public void run() {
					SearchDAO searchDAO = new SearchDAOJdbcImpl();
					while (true) {
						// System.out.println("��ǰ����"+Thread.currentThread().getName());
						String tmp = getAUrl();
						if (tmp != null) {
							try {
								Document html_tmp = sendRequest(tmp);
								UrlData uData  = parseHtml(html_tmp, tmp);
								searchDAO.save(uData);
								
							} catch (Exception e) {
								e.printStackTrace();
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

	public synchronized String getAUrl() {
		if (notCrawlurlSet.isEmpty())
			return null;
		String tmpAUrl;
		// synchronized(notCrawlurlSet){
		tmpAUrl = notCrawlurlSet.get(0);
		notCrawlurlSet.remove(0);
		// }
		return tmpAUrl;
	}

	public synchronized void addUrl(String url, int d) {
		notCrawlurlSet.add(url);
		allurlSet.add(url);
		// depth.put(url, d);
	}

	/**
	 * ����htmlҳ��
	 * 
	 * @return
	 */
	public Document sendRequest(String url) {
//		HttpClient client = new HttpClient();
//		// ���ô����������ַ�Ͷ˿�
//		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
//		// ʹ�� GET ���� �������������Ҫͨ�� HTTPS ���ӣ���ֻ��Ҫ������ URL �е� http ���� https
//		HttpMethod method = new GetMethod(url);
//		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
//		// ʹ��POST����
//		// HttpMethod method = new PostMethod("http://java.sun.com");
//		try {
//			client.executeMethod(method);
//			return method.getResponseBodyAsString();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			// �ͷ�����
//			method.releaseConnection();
		
		try {
			return Jsoup.connect(url).timeout(30000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("�������ӳ٣������ĵȴ���");
		}
			
		

		// ��ӡ���������ص�״̬
		// System.out.println(method.getStatusLine());
		// ��ӡ���ص���Ϣ
//		try {
//			return method.getResponseBodyAsString();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			// �ͷ�����
//			method.releaseConnection();
//		}
		return null;
	}

	/**
	 * ����htmlҳ��
	 * 
	 * @param html_tmp
	 */
	public UrlData parseHtml(Document html_tmp, String url) {
//		Document doc = Jsoup.parse(html_tmp);
		Elements href =  html_tmp.select("a[href]");
		UrlData urlData = new UrlData();
		urlData.setUrl(url);
		urlData.setHtml(html_tmp.toString());
		String tp = url_Filter(url);
		urlData.setSource(tp);

		for (Element hre : href) {
			String linkUrl = hre.attr("abs:href");// ��ȡ��ҳ�ľ��Ե�ַ
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
//		System.out.println(urlData);
		System.out.println("д���"+ i++ +"����¼"+"    ��ַ��"+url + "    ��Դ��"+tp);
		return urlData;
		
	}

	public String url_Filter(String linkurl) {
		for (int i = 0; i < seeds.length; i++) {
			String type = seeds[i].split("\\.")[1];
			if (linkurl.contains(type)) {
				return seeds[i];
			}
		}
		return "";
	}

}
