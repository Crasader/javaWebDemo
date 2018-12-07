package org.smart4j.framework.helper;

import java.sql.Connection;

import org.smart4j.framework.util.DBUtil;

public final class DataBaseHelper {

	private static ThreadLocal<Connection> CONNECTION_HOLDER = DBUtil.getConnContainer();
	
	/**
	 * 开启事务
	 */
	public static void beginTransaction() {
		Connection conn = DBUtil.getConnection();
		try {
		if(conn != null) {
			conn.setAutoCommit(false);
		}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}finally {
			CONNECTION_HOLDER.set(conn);
		}
	}
	
	public static void commitTransaction() {
		Connection conn = DBUtil.getConnection();
		try {
		if(conn != null) {
			conn.commit();
			conn.close();
		}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}finally {
			CONNECTION_HOLDER.remove();
		}
	}
	
	public static void rollbackTransaction() {

		Connection conn = DBUtil.getConnection();
		try {
		if(conn != null) {
			conn.rollback();
			conn.close();
		}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}finally {
			CONNECTION_HOLDER.remove();
		}
	
	}
	
}
