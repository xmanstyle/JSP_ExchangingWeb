package com.xiaoming.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;
import com.xiaoming.model.UserBean;
import com.xiaoming.util.MysqlCon;

/**
 * ���ǽ���<�ҷ�����>ҳ���д��ݹ��������ݣ�
 * ��Ҫ����Ϊ���ڵļ�¼���д���
 * @author Xman
 *
 */

public class HandleChangePost extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		//���ж��Ƿ���Session��û��Session����������ҳ��
		HttpSession session = request.getSession();
		UserBean loginUser = null;
		loginUser = (UserBean)session.getAttribute("loginUser");
		if(loginUser == null){
			String err = java.net.URLEncoder.encode("������������ڻػ�!","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
			return;
		}
		
		//�����ӱ��д��ݹ�������
		//���Բ�ͬ����(�������񵽣���ʧ)�ļ�¼���д���
		Enumeration<String> fields = request.getParameterNames();
		String cur = null;
		ArrayList<String> chs = new ArrayList<String>();//��ŵ���change��¼��id
		ArrayList<String> los = new ArrayList<String>();//��Ŷ�ʧlost ��¼��id
		ArrayList<String> mes = new ArrayList<String>();//��ż�meet��¼��id
		while(fields.hasMoreElements()){
			cur = fields.nextElement();
			if(cur.startsWith("ch")){
				chs.add(cur.substring(3));
			}else if(cur.startsWith("lo")){
				los.add(cur.substring(3));
			}else{
				mes.add(cur.substring(3));
			}
		}
		
		//��ʼ�������ݿ��е�����
		MysqlCon mysql = new MysqlCon();
		Connection con = mysql.getCon();
		
		String upCh = "update tab_change set ispass='yes' where id=?";
		String upLo = "update tab_lost set ispass='yes' where id=?";
		String upMe = "update tab_meet set ispass='yes' where id=?";
		
		//�������ݿ��еĵ�����Ʒ�ı� tab_change
		PreparedStatement chPre = null;
		try{
			chPre = (PreparedStatement)con.prepareStatement(upCh);
			for(int i=0 ; i<chs.size() ; i++){
				chPre.setString(1, chs.get(i));
				chPre.addBatch();//��ӵ���������
			}
			chPre.executeBatch();//ִ��������
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(chPre != null){
				try {
					chPre.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//�������ݿ��еĶ�ʧ��Ʒ�ı� tab_lost
		PreparedStatement loPre = null;
		try{
			loPre = (PreparedStatement)con.prepareStatement(upLo);
			for(int i=0 ; i<los.size() ; i++){
				loPre.setString(1, los.get(i));
				loPre.addBatch();
			}
			loPre.executeBatch();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(loPre != null){
				try {
					loPre.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//�������ݿ��еļ���Ʒ�ı� tab_meet
		PreparedStatement mePre = null;
		try{
			mePre = (PreparedStatement)con.prepareStatement(upMe);
			for(int i=0 ; i<mes.size() ; i++){
				mePre.setString(1, mes.get(i));
				mePre.addBatch();
			}
			mePre.executeBatch();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(mePre != null){
				try {
					mePre.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			mysql.closeCon();
		}
		
		//�������Ժ���ת����ҳ
		request.getRequestDispatcher("/WEB-INF/MainPage.jsp").forward(request, response);
	}
}
