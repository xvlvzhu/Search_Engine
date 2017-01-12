/**
 * 提供了数据库连接
 */
package com.zxl.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class jdbcUtils {
	
	public static void releaseConnection(Connection connection){
		try {
			if (connection!=null) {
				connection.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static DataSource dataSource= null;
	
	//数据源只能创建一次
	static{
		dataSource = new ComboPooledDataSource("MVCApp");
	}
	
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}

	
}
 