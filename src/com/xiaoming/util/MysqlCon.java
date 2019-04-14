package com.xiaoming.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * ����Mysql�Ĺ�����
 * @author Xman
 *
 */
public class MysqlCon {
	String driver = "com.mysql.jdbc.Driver";//����
	String url = "jdbc:mysql://127.0.0.1:3306/exchange";//ʹ�ñ������ݿ�ʱ�����ݿ���������
	//String url = "jdbc:mysql://olpf1128.imwork.net:35117/exchange";//ʹ������ӳ��ʱ�����ݿ���������
	String user = "root";//���ݿ�ĵ�¼��
	String pass = "root";//a���ݿ����������
	private Connection con;

	public MysqlCon(){
		con = null;
		try {
			Class.forName(driver);//��������
			con = DriverManager.getConnection(url, user, pass);//����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getCon(){
		return this.con;
	}
	
	//�ر����ݿ�����
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
