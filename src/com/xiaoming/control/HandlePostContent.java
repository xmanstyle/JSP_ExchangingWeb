package com.xiaoming.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.xiaoming.model.UserBean;
import com.xiaoming.util.MysqlCon;
import com.xiaoming.util.ToUtf8Str;

/**
 * �����û��������������Ϣ
 * @author Xman
 *
 */

public class HandlePostContent extends HttpServlet {
	
	private static String fileName = null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		//���ж��û��ķ�������(change������meet�񵽣�lost��ʧ)
		String type = null;
		type = request.getParameter("type");
		if(type == null){
			//��������Ϊ��,���Ϸ�����ת������ҳ��
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg=request error!^n^").forward(request, response);
			return;
		}
		//�ж��Ƿ�����û�Session������������ת������ҳ��
		HttpSession session = request.getSession();
		UserBean loginUser = null;
		loginUser = (UserBean)session.getAttribute("loginUser");
		if(loginUser == null){
			String err = java.net.URLEncoder.encode("������������ڻػ�!","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
			return;
		}
		
		//���������е�ͼƬ�ļ�
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		factory.setSizeThreshold(1024*1024*10);//����ͼƬ��С 10M
		List items = null;
		try{
			items = upload.parseRequest(request);//��ȡ���е�������
		}catch(FileUploadException e){
			e.printStackTrace();
		}
		
		Iterator iterator = items.iterator();//�����������ڱ������е�������
		//ͼƬ�ϴ���Ĵ洢Ŀ¼
		String imgUploadPath = getServletContext().getRealPath("./")+"uploadImgs";
		File uploadImgDir = new File(imgUploadPath);
		//����ϴ�ͼƬ��Ŀ¼������, �򴴽�Ŀ¼
		if(!uploadImgDir.exists()){
			uploadImgDir.mkdir();
		}
		
		List<String> fieldList = new ArrayList<String>();
		int imgId = getCurImgId(type);
		while(iterator.hasNext()){
			FileItem item = (FileItem)iterator.next();
			//�������ֶΣ���ͼƬ�����б���ͼƬ�Ĳ���
			if(!item.isFormField()){
				//ȷ��ͼƬ����ʱ������
				fileName = type+"_"+imgId+".jpg";
				//ȷ��ͼƬ�����Ŀ¼
				String imgPath = imgUploadPath+File.separatorChar+fileName;
				
				File imgFile = new File(imgPath);
				InputStream inputStream = item.getInputStream();
				FileOutputStream outputStream = new FileOutputStream(imgFile);
				byte[] bytes = new byte[1024*1024];
				while((inputStream.read(bytes)) != -1){
					outputStream.write(bytes);
				}
				outputStream.flush();
				outputStream.close();
				inputStream.close();
			}
			//��ͨ�ֶΣ�������������Ϣ���绰�ȵ�
			else{
				String value = item.getString();
				value = new String(value.getBytes("iso-8859-1"),"utf-8");
				if((!value.equals(""))&&(value!=null))
					fieldList.add(value);
			}
		}
		
		//�����ݿ��в����¼
		String schoolId = loginUser.schoolId;//��ȡSession�е��û���Ϣ����ʹ��ѧ���ֶθ���
		String info = null;//������Ϣ
		String tel = null;//�绰
		String isPass = "no";//��¼�Ƿ���ڵı�־��noû���ڣ�yes���ڣ�
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curTime = format.format(date);//��ǰ������ʱ��
		
		if(fieldList.size() == 0){
			//����˵�����е����ǿյģ��û�û��������Ϣ�ͷ�������ת������ҳ��
			String errString = java.net.URLEncoder.encode("������Ϣû������","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+errString).forward(request, response);
		}else{
			System.out.println("fieldList:"+fieldList);
			info = fieldList.get(0);
			tel = fieldList.get(1);
			MysqlCon mysqlCon = new MysqlCon();
			Connection connection = mysqlCon.getCon();
			String insertStr = "insert into tab_"+type+" values(?,?,?,?,?,?)";
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = (PreparedStatement) connection.prepareStatement(insertStr);
				preparedStatement.setInt(1, imgId);
				preparedStatement.setString(2, schoolId);
				preparedStatement.setString(3, tel);
				preparedStatement.setString(4, info);
				preparedStatement.setString(5, curTime);
				preparedStatement.setString(6, isPass);
				preparedStatement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(preparedStatement != null){
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try{
					connection.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				mysqlCon.closeCon();
			}
		}
		//���������ת����ҳ
		request.getRequestDispatcher("/WEB-INF/MainPage.jsp").forward(request, response);
	}
	
	//��ȡ���ݿ��У���Ӧ�����������¼id���Ӷ��ж���һ����¼��id ֵ
	public int getCurImgId(String type){
		MysqlCon mysqlCon = new MysqlCon();
		Connection connection = mysqlCon.getCon();
		String selectStr = "select id from tab_"+type+" order by id desc limit 1";
		int curId = 0;
		Statement statement = null;
		ResultSet set = null;
		try {
			statement = (Statement) connection.createStatement();
			set = statement.executeQuery(selectStr);
			while(set.next()){
				curId = set.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(set != null){set.close();}
				if(statement != null){statement.close();}
				connection.close();
				mysqlCon.closeCon();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		return curId+1;
	}
}
