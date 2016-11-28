package com.km.spider.util;

import java.sql.Connection;
import java.sql.SQLException;
//事务管理器
public class TransationManager {
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	public static Connection getConnection(){
		Connection conn = tl.get();//从当前线程上获得链接
		if(conn==null){
			conn = DBCPUtil.getConnection();
			tl.set(conn);//把链接绑定到当前线程上
		}
		return conn;
	}
	public static void startTransaction(){
		Connection conn = getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void commit(){
		Connection conn = getConnection();
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(){
		Connection conn = getConnection();
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void release(){
		Connection conn = getConnection();
		try {
			conn.close();
			tl.remove();//与线程池有关，解除连接与当前线程关系
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}