package com.zxl.dao;
/**
 * �ṩSearchEngineDAO�����Ľӿڣ���SearchEngineimplʵ�ָýӿ�
 */
import java.util.List;

import com.zxl.javaBean.UrlData;

public interface SearchDAO {

	public void save(UrlData urlData);
	
	public void save(UrlData urlData,String table);
	
	public void save1(UrlData urlData,String table);
	
	public void update1(String url,String classified,String table);
	
	public List<UrlData> getAll();
	
	
}
