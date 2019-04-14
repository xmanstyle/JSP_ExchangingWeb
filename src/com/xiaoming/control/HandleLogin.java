package com.xiaoming.control;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.*;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoming.util.MysqlCon;

/**
 * 处理用户的登录请求
 * @author Xman
 *
 */

public class HandleLogin extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
response.setContentType("text/html;charset=utf-8");
		
		//先获取表单中输入的学号，密码
		String schoolId = request.getParameter("schoolId");
		String password = request.getParameter("password");
		System.out.println("id:"+schoolId+"\npass:"+password);
		
		//获取数据库中用户表的学号和密码
        MysqlCon mysqlCon = new MysqlCon();
        Connection con = mysqlCon.getCon();
  		String selectStr = "select * from tab_user";
  		ArrayList<String> schoolIds = new ArrayList();//所有学号
  		ArrayList<String> passes = new ArrayList();//所有密码
		try {
			Statement statement = (Statement) con.createStatement();
			ResultSet set = statement.executeQuery(selectStr);
			int i = 0;
			while(set.next()){
				schoolIds.add(set.getString(4));
				passes.add(set.getString(3));
			}
			statement.close();
			set.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		mysqlCon.closeCon();
  		//如果登录表单中的学号和密码，在所有学号和所有密码中，则用户合法
		if(schoolIds.contains(schoolId) && passes.contains(password)){
			UserBean loginUser = new UserBean(schoolId);
			if(loginUser.isUsed){
				//用户合法，保存用户的Session
				HttpSession userSession = request.getSession(true);
				userSession.setAttribute("loginUser", loginUser);
				request.getRequestDispatcher("/WEB-INF/info.jsp").forward(request, response);
			}else{
				//出错
				String err = java.net.URLEncoder.encode("回话初始化失败!","utf-8");
				request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
			}
		}else{
			//出错
			String err = java.net.URLEncoder.encode("学号或密码不正确!","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
		}
	}

}
