package com.xiaoming.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.xiaoming.model.UserBean;
import com.xiaoming.util.MysqlCon;
import com.xiaoming.util.ToUtf8Str;

/**
 * 处理用户注册及用户更改信息的操作（注册和更改）
 * @author Xman
 *
 */

public class HandleSign extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		//先判断请求类型（注册和更改）
		String type = request.getParameter("type");
		System.out.println(type);
		
		//获取请求的表单中的信息
		String realName = ToUtf8Str.func(request.getParameter("realName"));
		String schoolId = request.getParameter("schoolId");
		String pass = request.getParameter("pass");
		String passAgain = request.getParameter("passagain");
		String telphone = request.getParameter("telphone");
		String unit = ToUtf8Str.func(request.getParameter("unit"));
				
		MysqlCon mysql = new MysqlCon();
		Connection con = mysql.getCon();
		if(con == null){
			String err = java.net.URLEncoder.encode("数据库连接失败","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request,response);
			return;
		}else{
			//根据type进行不同的处理
			//这里是注册请求
			if(type.equals("sign")){
				String insertStr = "insert into tab_user values(null,?,?,?,?,?)";
				try {
					PreparedStatement preStatement = (PreparedStatement) con.prepareStatement(insertStr);
					preStatement.setString(1,realName);
					preStatement.setString(2,pass);
					preStatement.setString(3,schoolId);
					preStatement.setString(4,telphone);
					preStatement.setString(5,unit);
					preStatement.execute();
					preStatement.close();
					System.out.println("sign success!");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				UserBean loginUser = new UserBean(schoolId);
				HttpSession userSession = request.getSession(true);
				userSession.setAttribute("loginUser", loginUser);
			}
			//这里是更新请求 
			else{//update
				String id = request.getParameter("id");
				System.out.println(id);
				String updateStr = "update tab_user set username=?,userpass=?,studentid=?,"+
				"telphone=?,unit=? where id=?";
				try{
					PreparedStatement preStatement = (PreparedStatement) con.prepareStatement(updateStr);
					preStatement.setString(1,realName);
					preStatement.setString(2,pass);
					preStatement.setString(3,schoolId);
					preStatement.setString(4,telphone);
					preStatement.setString(5,unit);
					preStatement.setString(6, id);
					preStatement.execute();
					preStatement.close();
					System.out.println("update success!");
				}catch(Exception e){
					e.printStackTrace();
				}
				//更新完以后重新设置用户Session
				UserBean loginUser = new UserBean(schoolId);
				HttpSession userSession = request.getSession(true);
				userSession.setAttribute("loginUser", loginUser);
			}
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mysql.closeCon();
		//注册完以后直接跳转到网站说明界面
		request.getRequestDispatcher("/WEB-INF/info.jsp").forward(request, response);
	}

}
