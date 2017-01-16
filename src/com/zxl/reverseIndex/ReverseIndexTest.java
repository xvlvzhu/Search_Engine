package com.zxl.reverseIndex;

import java.io.IOException;
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
import org.apache.lucene.store.RAMDirectory;

import com.zxl.dao.SearchDAO;
import com.zxl.dao.SearchDAOJdbcImpl;
import com.zxl.javaBean.UrlData;

public class ReverseIndexTest {
	static Analyzer analyzer=null;
	static Directory idx =null;
	static IndexWriter writer=null;
	
	public static void main(String[] args) {
		Date date1 = new Date();
//		SearchDAO searchDAO = new SearchDAOJdbcImpl();
//		List<UrlData> ua=searchDAO.getAll();
//		Analyzer analyzer = new StandardAnalyzer();
//		// 内存存储
//		Directory idx = new RAMDirectory();
//		// 本地存储
////		Directory directory = FSDirectory.open("/tmp/testindex");
//		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		
		try {
//			IndexWriter writer = new IndexWriter(idx, iwc);
//			Document doc = new Document();
				
//				 analyzer = new StandardAnalyzer();
				 analyzer = new SmartChineseAnalyzer();
				// 内存存储
				idx = new RAMDirectory();
				// 本地存储
//				Directory directory = FSDirectory.open("/tmp/testindex");
				IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
				writer = new IndexWriter(idx, iwc);
				for(int i=0;i<5;i++){
					Document doc = new Document();
				
				doc.add(new Field("url", "man,hello,woman",TextField.TYPE_STORED));
				doc.add(new Field("html",2*i+"zxl,yzk,jjj",TextField.TYPE_STORED));
				writer.addDocument(doc);
				}
				for(int i=0;i<5;i++){
					Document doc = new Document();
					doc.add(new Field("url", "man,hello,woman",TextField.TYPE_STORED));
					doc.add(new Field("html",(2*i+1)+"zxl,yzk,lg",TextField.TYPE_STORED));
					writer.addDocument(doc);
					}
				writer.commit();
				writer.close();
//			writer.addDocument(doc);
//			writer.commit();
//			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date2 = new Date();
		System.out.println("创建索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
//		return doc;
		
		
		try {
			IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(idx));
			TopDocs topdoc = searcher.search(new QueryParser("html", analyzer).parse("yzk"), 1000);
			System.out.println("命中个数:" + topdoc.totalHits);
			ScoreDoc[] hits = topdoc.scoreDocs;
			System.out.println();

			if (hits != null && hits.length > 0) {
				for (int i = 0; i < hits.length; i++) {
					Document hitDoc = searcher.doc(hits[i].doc);
					System.out.println(hitDoc.get("url"));

					System.out.println(hitDoc.get("html"));
				}
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
