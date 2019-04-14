<%@page import="java.sql.ResultSet"%>
<%@page import="com.mysql.jdbc.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xiaoming.util.MysqlCon"%>
<%@page import="com.xiaoming.model.UserBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>我发布的</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="imgs/ico.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="csses/mypost.css">
  </head>
  
  <body>
     <div id="mainDiv">
  	   <a href="servlet/HandleIntoMain">主页</a>
  	 </div>
    <%
    	UserBean loginUser = (UserBean)session.getAttribute("loginUser");
    	MysqlCon mysql = new MysqlCon();
    	Connection con = mysql.getCon();
     %>
     <center>
     <div id="container">
     	<form action="servlet/HandleChangePost" method="post">
     		<div id="div_change">
     			<table id="tab_change">
     				<tr><td colspan="4" id="ch_title">调换</td></tr>
     				<tr><td>发布时间</td><td>描述信息</td><td>是否过期？</td><td>设置过期</td></tr>
     				<%
     				//<<<<<<<<<<<<<<<<<<<< 设置页面中我发布的《交换》 显示 >>>>>>>>>>>>>>>
     				String changeSelect = "select * from tab_change where studentid=? order by id desc";
     				PreparedStatement pre = null;
     				try{
     					pre = (PreparedStatement)con.prepareStatement(changeSelect);
     					pre.setString(1,loginUser.schoolId);
     					ResultSet changeSet = pre.executeQuery();
     					String ispass = null;
     					while(changeSet.next()){
     						ispass = changeSet.getString("ispass");
     					%>
     						<tr><td><%=changeSet.getString("time")%></td><td><%=changeSet.getString("info")%></td><td><%=ispass%></td>
     						<td><input type="radio" <%if(ispass.equals("yes")){%>disabled="disabled"<%}%> name="<%=("ch_"+changeSet.getString("id"))%>"/></td></tr>
     					<%	
     					}
     					changeSet.close();
     					pre.close();
     				}catch(Exception e){
     					e.printStackTrace();
     				}
     				 %>
     			</table>
     		</div>
     		<div id="div_lost">
     			<table id="tab_lost">
     				<tr><td colspan="4" id="lo_title">丢失</td></tr>
     				<tr><td>发布时间</td><td>描述信息</td><td>是否过期？</td><td>设置过期</td></tr>
     				<%
     				//<<<<<<<<<<<<<<<<<<<< 设置页面中我发布的《丢失》 显示 >>>>>>>>>>>>>>>
     				String lostSelect = "select * from tab_lost where studentid=? order by id desc";
     				PreparedStatement lostPre = null;
     				try{
     					lostPre = (PreparedStatement)con.prepareStatement(lostSelect);
     					lostPre.setString(1,loginUser.schoolId);
     					ResultSet lostSet = lostPre.executeQuery();
     					String ispass = null;
     					while(lostSet.next()){
     						ispass = lostSet.getString("ispass");
     					%>
     						<tr><td><%=lostSet.getString("time")%></td><td><%=lostSet.getString("info")%></td><td><%=ispass%></td>
     						<td><input type="radio" <%if(ispass.equals("yes")){%>disabled="disabled"<%}%> name="<%=("lo_"+lostSet.getString("id"))%>"/></td></tr>
     					<%	
     					}
     					lostSet.close();
     					lostPre.close();
     				}catch(Exception e){
     					e.printStackTrace();
     				}
     				 %>
     			</table>
     		</div>
     		<div id="div_meet">
     			<table id="tab_meet">
     				<tr><td colspan="4" id="me_title">拾得</td></tr>
     				<tr><td>发布时间</td><td>描述信息</td><td>是否过期？</td><td>设置过期</td></tr>
     				<%
     				//<<<<<<<<<<<<<<<<<<<< 设置页面中我发布的《捡到》 显示 >>>>>>>>>>>>>>>
     				String meetSelect = "select * from tab_meet where studentid=? order by id desc";
     				PreparedStatement meetPre = null;
     				try{
     					meetPre = (PreparedStatement)con.prepareStatement(meetSelect);
     					meetPre.setString(1,loginUser.schoolId);
     					ResultSet meetSet = meetPre.executeQuery();
     					String ispass = null;
     					while(meetSet.next()){
     						ispass = meetSet.getString("ispass");
     					%>
     						<tr><td><%=meetSet.getString("time")%></td><td><%=meetSet.getString("info")%></td><td><%=ispass%></td>
     						<td><input type="radio" <%if(ispass.equals("yes")){%>disabled="disabled"<%}%> name="<%=("me_"+meetSet.getString("id"))%>"/></td></tr>
     					<%	
     					}
     					meetSet.close();
     					meetPre.close();
     					con.close();
     					mysql.closeCon();
     				}catch(Exception e){
     					e.printStackTrace();
     				}
     				 %>
     			</table>
     		</div>
     		<input type="submit" id="submitBt" value="提交"/>
     	</form>
     </div>
     </center>
  </body>
</html>
