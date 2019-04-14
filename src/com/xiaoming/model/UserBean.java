package com.xiaoming.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.instrumentation.ToStringHelper;

import com.mysql.jdbc.PreparedStatement;
import com.xiaoming.util.MysqlCon;

/**
 * 从数据库中把学生信息的每条记录映射成代码中的一个类
 * @author Xman
 *
 */
public class UserBean {
	public String id;
	public String userName;
	public String userPass;
	public String schoolId;
	public String telphone;
	public String unit;
	public boolean isUsed = false;
	public UserBean(){}
	public UserBean(String schoolId){
		MysqlCon mysqlCon = new MysqlCon();
		Connection connection = mysqlCon.getCon();
		String sql = "select * from tab_user where studentid=?";
		PreparedStatement preStatement = null;
		ResultSet set = null;
		try {
			preStatement = (PreparedStatement) connection.prepareStatement(sql);
			preStatement.setString(1, schoolId);
			set = preStatement.executeQuery();
			set.next();
			this.id = set.getString("id");
			this.userName = set.getString("username");
			this.userPass = set.getString("userpass");
			this.schoolId = set.getString("studentid");
			this.telphone = set.getString("telphone");
			this.unit = set.getString("unit");
			this.isUsed = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(set != null){set.close();}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(preStatement != null){preStatement.close();}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			mysqlCon.closeCon();
		}
	}
	@Override
	public String toString(){
		return "<<user>>\nname:"+userName+" \nschoolId:"+schoolId+" \ntelphone:"+
				telphone+" \nunit:"+unit+" \nisUsed:"+isUsed;
	}
}
