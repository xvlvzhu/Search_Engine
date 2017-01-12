package com.zxl.dao;

import java.util.List;

import com.zxl.dao.SearchDAO;
import com.zxl.dao.DAO;
import com.zxl.javaBean.UrlData;

public class SearchDAOJdbcImpl extends DAO<UrlData> implements SearchDAO {


	@Override
	public void save(UrlData urlData) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO Search_Engine_Test_ZXL(URL,HTML,TYPE,URLList) VALUES (?,?,?,?)";
		update(sql, urlData.getUrl(),urlData.getHtml(),urlData.getSource(),urlData.getUrl_link());
	}


}
