package com.xiaoming.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xiaoming.model.UserBean;

/**
 * 根据个人的学号，跳转到显示详细信息的界面
 * @author Xman
 *
 */
public class HandleToPerson extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		
		//判断是否是合法的访问
		HttpSession session = request.getSession();
		UserBean loginUser = null;
		loginUser = (UserBean)session.getAttribute("loginUser");
		if(loginUser == null){
			String err = java.net.URLEncoder.encode("与服务器不存在回话!","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
			return;
		}
		
		//跳转
		request.getRequestDispatcher("/WEB-INF/person.jsp").forward(request, response);
	}

}
