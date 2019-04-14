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
 * 处理用户真正发布后的信息
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
		//先判断用户的发布类型(change交换，meet捡到，lost丢失)
		String type = null;
		type = request.getParameter("type");
		if(type == null){
			//发布类型为空,不合法，跳转到出错页面
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg=request error!^n^").forward(request, response);
			return;
		}
		//判断是否存在用户Session，不存在则跳转到出错页面
		HttpSession session = request.getSession();
		UserBean loginUser = null;
		loginUser = (UserBean)session.getAttribute("loginUser");
		if(loginUser == null){
			String err = java.net.URLEncoder.encode("与服务器不存在回话!","utf-8");
			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
			return;
		}
		
		//处理发布表单中的图片文件
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		factory.setSizeThreshold(1024*1024*10);//最大的图片大小 10M
		List items = null;
		try{
			items = upload.parseRequest(request);//获取表单中的所有项
		}catch(FileUploadException e){
			e.printStackTrace();
		}
		
		Iterator iterator = items.iterator();//遍历器，用于遍历表单中的所有项
		//图片上传后的存储目录
		String imgUploadPath = getServletContext().getRealPath("./")+"uploadImgs";
		File uploadImgDir = new File(imgUploadPath);
		//如果上传图片的目录不存在, 则创建目录
		if(!uploadImgDir.exists()){
			uploadImgDir.mkdir();
		}
		
		List<String> fieldList = new ArrayList<String>();
		int imgId = getCurImgId(type);
		while(iterator.hasNext()){
			FileItem item = (FileItem)iterator.next();
			//二进制字段，是图片，进行保存图片的操作
			if(!item.isFormField()){
				//确定图片保存时的名字
				fileName = type+"_"+imgId+".jpg";
				//确定图片保存的目录
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
			//普通字段，发布的描述信息，电话等等
			else{
				String value = item.getString();
				value = new String(value.getBytes("iso-8859-1"),"utf-8");
				if((!value.equals(""))&&(value!=null))
					fieldList.add(value);
			}
		}
		
		//向数据库中插入记录
		String schoolId = loginUser.schoolId;//获取Session中的用户信息，并使用学号字段复制
		String info = null;//描述信息
		String tel = null;//电话
		String isPass = "no";//记录是否过期的标志（no没过期，yes过期）
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curTime = format.format(date);//当前发布的时间
		
		if(fieldList.size() == 0){
			//这里说明表单中的项是空的，用户没填完整信息就发布，跳转到出错页面
			String errString = java.net.URLEncoder.encode("发布信息没填完整","utf-8");
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
		//发布完后跳转到主页
		request.getRequestDispatcher("/WEB-INF/MainPage.jsp").forward(request, response);
	}
	
	//获取数据库中，对应发布表的最大记录id，从而判断下一条记录的id 值
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
