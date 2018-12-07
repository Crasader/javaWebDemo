package org.smart4j.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	//数据库配置
	private static final String driver="com.mysql.jdbc.Driver";
	
	private static final String url="jdbc:mysql://localhost:3306/shiro";
	
	private static final String username="root";
	
	private static final String password="root";
	
	private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();
	
	
	
	public static ThreadLocal<Connection> getConnContainer() {
		return connContainer;
	}

	public static Connection getConnection() {
		Connection conn = connContainer.get();
		try {
		 if(conn == null) {
				conn = DriverManager.getConnection(url, username, password);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connContainer.set(conn);
		}
		return conn;
	}
	
	public static void closeConnection() {
		Connection conn = connContainer.get();
		try {
		 if(conn != null) {
			conn.close();
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connContainer.remove();
		}
	}
	
	
}
