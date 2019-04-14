package com.xiaoming.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 连接Mysql的工具类
 * @author Xman
 *
 */
public class MysqlCon {
	String driver = "com.mysql.jdbc.Driver";//驱动
	String url = "jdbc:mysql://127.0.0.1:3306/exchange";//使用本地数据库时，数据库的连接语句
	//String url = "jdbc:mysql://olpf1128.imwork.net:35117/exchange";//使用外网映射时，数据库的连接语句
	String user = "root";//数据库的登录名
	String pass = "root";//a数据库的连接密码
	private Connection con;

	public MysqlCon(){
		con = null;
		try {
			Class.forName(driver);//加载驱动
			con = DriverManager.getConnection(url, user, pass);//连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getCon(){
		return this.con;
	}
	
	//关闭数据库连接
	public void closeCon(){
		try{
			if(con != null){
				con.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
