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
 * 这是接收<我发布的>页面中传递过来的数据，
 * 对要设置为过期的记录进行处理
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
		//先判断是否有Session，没有Session则跳到错误页面
		HttpSession session = request.getSession();
		UserBean loginUser = null;
		loginUser = (UserBean)session.getAttribute("loginUser");
		if(loginUser == null){
			String err = java.net.URLEncoder.encode("与服务器不存在回话!","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
			return;
		}
		
		//遍历从表单中传递过来的项
		//并对不同类型(调换，捡到，丢失)的记录进行处理
		Enumeration<String> fields = request.getParameterNames();
		String cur = null;
		ArrayList<String> chs = new ArrayList<String>();//存放调换change记录的id
		ArrayList<String> los = new ArrayList<String>();//存放丢失lost 记录的id
		ArrayList<String> mes = new ArrayList<String>();//存放捡到meet记录的id
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
		
		//开始更改数据库中的数据
		MysqlCon mysql = new MysqlCon();
		Connection con = mysql.getCon();
		
		String upCh = "update tab_change set ispass='yes' where id=?";
		String upLo = "update tab_lost set ispass='yes' where id=?";
		String upMe = "update tab_meet set ispass='yes' where id=?";
		
		//更新数据库中的调换物品的表 tab_change
		PreparedStatement chPre = null;
		try{
			chPre = (PreparedStatement)con.prepareStatement(upCh);
			for(int i=0 ; i<chs.size() ; i++){
				chPre.setString(1, chs.get(i));
				chPre.addBatch();//添加到批处理中
			}
			chPre.executeBatch();//执行批处理
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
		
		//更新数据库中的丢失物品的表 tab_lost
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
		
		//更新数据库中的捡到物品的表 tab_meet
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
		
		//更新完以后跳转到主页
		request.getRequestDispatcher("/WEB-INF/MainPage.jsp").forward(request, response);
	}
}
