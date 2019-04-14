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
 * �����û��ĵ�¼����
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
		
		//�Ȼ�ȡ���������ѧ�ţ�����
		String schoolId = request.getParameter("schoolId");
		String password = request.getParameter("password");
		System.out.println("id:"+schoolId+"\npass:"+password);
		
		//��ȡ���ݿ����û����ѧ�ź�����
        MysqlCon mysqlCon = new MysqlCon();
        Connection con = mysqlCon.getCon();
  		String selectStr = "select * from tab_user";
  		ArrayList<String> schoolIds = new ArrayList();//����ѧ��
  		ArrayList<String> passes = new ArrayList();//��������
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
  		//�����¼���е�ѧ�ź����룬������ѧ�ź����������У����û��Ϸ�
		if(schoolIds.contains(schoolId) && passes.contains(password)){
			UserBean loginUser = new UserBean(schoolId);
			if(loginUser.isUsed){
				//�û��Ϸ��������û���Session
				HttpSession userSession = request.getSession(true);
				userSession.setAttribute("loginUser", loginUser);
				request.getRequestDispatcher("/WEB-INF/info.jsp").forward(request, response);
			}else{
				//����
				String err = java.net.URLEncoder.encode("�ػ���ʼ��ʧ��!","utf-8");
				request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
			}
		}else{
			//����
			String err = java.net.URLEncoder.encode("ѧ�Ż����벻��ȷ!","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
		}
	}

}
