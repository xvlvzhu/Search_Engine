package com.zxl.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.runners.Parameterized;

/**
 * 封装了基本的CRUD方法，供子类使用
 * 所有的添删改查操作（不变）
 * @author zxl
 *
 */
public class DAO<T> {

	private QueryRunner queryRunner = new QueryRunner();

	private Class<T> clazz;

	public DAO() {
		Type superClass = getClass().getGenericSuperclass();

		if (superClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) superClass;

			Type[] typeArgs = parameterizedType.getActualTypeArguments();
			if (typeArgs[0] instanceof Class) {
				clazz = (Class<T>) typeArgs[0];
			}
		}
	}

	public <E> E getForValue(String sql, Object... args) {
		Connection connection = null;
		try {
			connection = jdbcUtils.getConnection();
			return (E) queryRunner.query(connection, sql, new ScalarHandler(), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConnection(connection);
		}
		return null;

	}

	public T get(String sql, Object... args) {
		Connection connection = null;
		try {
			connection = jdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConnection(connection);
		}
		return null; //????

	}
	
	public List<T> getForList(String sql, Object... args){
		Connection connection = null;
		try {
			connection = jdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConnection(connection);
		}
		return null;
	}

	public void update(String sql, Object... args) {

		Connection connection = null;

		try {
			connection = jdbcUtils.getConnection();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConnection(connection);
		}
	}
}
