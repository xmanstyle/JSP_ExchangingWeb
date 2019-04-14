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
 * ��ת����ҳ
 * @author Xman
 *
 */

public class HandleIntoMain extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		//���жϷ��ʵĺϷ��ԣ����������Session������ת������ҳ��
		HttpSession session = request.getSession();
		UserBean loginUser = null;
		loginUser = (UserBean)session.getAttribute("loginUser");
		if(loginUser == null){
			String err = java.net.URLEncoder.encode("������������ڻػ�!","utf-8");
			//���Ϸ�����ת������ҳ��
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
			return;
		}		
		
		//�Ϸ�����ת����ҳ
		request.getRequestDispatcher("/WEB-INF/MainPage.jsp").forward(request, response);
	}

}
