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
    
    <title>个人信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="imgs/ico.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="csses/person.css">

  </head>
  
  <body>
     <div id="mainDiv">
  	   <a href="servlet/HandleIntoMain">主页</a>
  	 </div>
    <%
    	boolean isSelf = false;
    	String personSchoolId = request.getParameter("studentId");
    	UserBean loginUser = (UserBean)session.getAttribute("loginUser");
    	UserBean handleUser = null;
    	if(personSchoolId == null){
    		String err = java.net.URLEncoder.encode("学号不正确!","utf-8");
    		request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
    		return;
    	}
    	if(personSchoolId.equals(loginUser.schoolId)){
    		isSelf = true;
    		handleUser = loginUser;
    		System.out.println("is self!");
    		System.out.println(handleUser);
    	}else{
    		handleUser = new UserBean(personSchoolId);
    		System.out.println("not is self");
    		System.out.println(handleUser);
    	}
     %>
     
     <center>
     <div id="center">
     	<form action="#" method="post">
     		<table border="4px">
     			<tr align="center"><td colspan="2">个人信息</td></tr>
     			<tr><td>姓&nbsp;&nbsp;名</td><td><input disabled="disabled" value="<%=handleUser.userName%>"/></td></tr>
     			<tr><td>学&nbsp;&nbsp;号</td><td><input disabled="disabled" value="<%=handleUser.schoolId%>"/></td></tr>
     			<tr><td>电&nbsp;&nbsp;话</td><td><input disabled="disabled" value="<%=handleUser.telphone%>"/></td></tr>
     			<tr><td>所在单位</td><td><input disabled="disabled" value="<%=handleUser.unit%>"/></td></tr>
     		</table>
     	</form>
     </div>
     </center>
  </body>
</html>
