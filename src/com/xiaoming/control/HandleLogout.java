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
 * �����û��˳�
 * @author Xman
 *
 */
public class HandleLogout extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		//�ж��û��Ƿ����Session������������ת������ҳ��
		HttpSession session = request.getSession();
		UserBean loginUser = null;
		loginUser = (UserBean)session.getAttribute("loginUser");
		if(loginUser == null){
			String err = java.net.URLEncoder.encode("������������ڻػ�!","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
			return;
		}else{
			//ɾ���û���Session,�ﵽ�˳���Ŀ��
			session.removeAttribute("loginUser");
		}
		//��ת�����յ��˳�ҳ��
		request.getRequestDispatcher("/WEB-INF/Logout.jsp").forward(request, response);
	}

}
