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
 * �����û�ע�ἰ�û�������Ϣ�Ĳ�����ע��͸��ģ�
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
		
		//���ж��������ͣ�ע��͸��ģ�
		String type = request.getParameter("type");
		System.out.println(type);
		
		//��ȡ����ı��е���Ϣ
		String realName = ToUtf8Str.func(request.getParameter("realName"));
		String schoolId = request.getParameter("schoolId");
		String pass = request.getParameter("pass");
		String passAgain = request.getParameter("passagain");
		String telphone = request.getParameter("telphone");
		String unit = ToUtf8Str.func(request.getParameter("unit"));
				
		MysqlCon mysql = new MysqlCon();
		Connection con = mysql.getCon();
		if(con == null){
			String err = java.net.URLEncoder.encode("���ݿ�����ʧ��","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request,response);
			return;
		}else{
			//����type���в�ͬ�Ĵ���
			//������ע������
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
			//�����Ǹ������� 
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
				//�������Ժ����������û�Session
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
		//ע�����Ժ�ֱ����ת����վ˵������
		request.getRequestDispatcher("/WEB-INF/info.jsp").forward(request, response);
	}

}
