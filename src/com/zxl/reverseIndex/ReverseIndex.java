package com.zxl.reverseIndex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import com.zxl.dao.SearchDAO;
import com.zxl.dao.SearchDAOJdbcImpl;
import com.zxl.javaBean.UrlData;

public class ReverseIndex {

	public static void main(String[] args) {
		  
		    createIndex("D:\\test\\reverseIndex");
//		    search(text, indexDirctory);
		 
//		try {
//			search(text, FSDirectory.open(Paths.get(dir)));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 
	 * @param dir
	 * @param b 是否更新索引
	 */
	public static ArrayList<UrlData> reverseIndex(String dir,String text,boolean b){
		File file=new File(dir);
		if (file.exists() && b==true) {
			delAllFile(dir);
			createIndex(dir);
		}
		if(!file.exists())    
		{    
		    createIndex(dir);    
		}  
		return search(text, dir);
//			search(text, FSDirectory.open(Paths.get(dir)));
	}

	public static void createIndex(String dir) {
		Date date1 = new Date();
		SearchDAO searchDAO = new SearchDAOJdbcImpl();
		List<UrlData> ua = searchDAO.getAll();

		try {
			// IndexWriter writer = new IndexWriter(idx, iwc);
			// Document doc = new Document();

			// analyzer = new StandardAnalyzer();
			SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
			// 内存存储
//			idx = new RAMDirectory();
			// 本地存储
			Directory idx = FSDirectory.open(Paths.get(dir));
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(idx, iwc);
			for (UrlData urlData : ua) {
				Document doc = new Document();

				doc.add(new Field("url", urlData.getUrl(), TextField.TYPE_STORED));
				doc.add(new Field("html", urlData.getHtml(), TextField.TYPE_STORED));
				doc.add(new Field("title", urlData.getTitle(), TextField.TYPE_STORED));
				writer.addDocument(doc);
			}
			writer.commit();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date2 = new Date();
		System.out.println("创建索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
		// return doc;
	}

	public static ArrayList<UrlData> search(String text, String indexDirctory) {
		ArrayList<UrlData> queryResult = new ArrayList<>();
		try {
			Directory directory = FSDirectory.open(Paths.get(indexDirctory));
			IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(directory));
			// TopDocs topdoc = searcher.search(new QueryParser("html",
			// analyzer).parse("陕西"), 10);
			TopDocs topdoc = searcher.search(new QueryParser("title", new SmartChineseAnalyzer()).parse(text), 100);
			TopDocs topdoc_1 = searcher.search(new QueryParser("html", new SmartChineseAnalyzer()).parse(text), 100);
			System.out.println("命中个数:" + (topdoc.totalHits+topdoc_1.totalHits));
			ScoreDoc[] hits = topdoc.scoreDocs;
			ScoreDoc[] hits_1 = topdoc_1.scoreDocs;

			if (hits != null && hits.length > 0) {
				for (int i = 0; i < hits.length; i++) {
					UrlData urlData= new UrlData();
					Document hitDoc = searcher.doc(hits[i].doc);
					urlData.setUrl(hitDoc.get("url"));
					urlData.setTitle(hitDoc.get("title"));
					urlData.setHtml(hitDoc.get("html"));
					queryResult.add(urlData);
					System.out.println(hitDoc.get("url"));
					System.out.println(hitDoc.get("title"));
 				    System.out.println(hitDoc.get("html"));
				}
			}
			
			if (hits_1 != null && hits_1.length > 0) {
				for (int i = 0; i < hits_1.length; i++) {
					UrlData urlData= new UrlData();
					Document hitDoc = searcher.doc(hits_1[i].doc);
					urlData.setUrl(hitDoc.get("url"));
					urlData.setTitle(hitDoc.get("title"));
					urlData.setHtml(hitDoc.get("html"));
					queryResult.add(urlData);
					System.out.println(hitDoc.get("url"));
					System.out.println(hitDoc.get("title"));
 				    System.out.println(hitDoc.get("html"));
				}
			}

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queryResult;
	}
	
	public static boolean delAllFile(String path) {  
        boolean flag = false;  
        File file = new File(path);  
        if (!file.exists()) {  
            return flag;  
        }  
        if (!file.isDirectory()) {  
            return flag;  
        }  
        String[] tempList = file.list();  
        File temp = null;  
        for (int i = 0; i < tempList.length; i++) {  
            if (path.endsWith(File.separator)) {  
                temp = new File(path + tempList[i]);  
            } else {  
                temp = new File(path + File.separator + tempList[i]);  
            }  
            if (temp.isFile()) {  
                temp.delete();  
            }  
            if (temp.isDirectory()) {  
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件  
//              delFolder(path + "/" + tempList[i]);// 再删除空文件夹  
                flag = true;  
            }  
        }  
        return flag;  
    }  

}
