package com.zxl.dao;
/**
 * 提供SearchEngineDAO方法的接口，由SearchEngineimpl实现该接口
 */
import java.util.List;

import com.zxl.javaBean.UrlData;

public interface SearchDAO {

	public void save(UrlData urlData);
	
	
}
