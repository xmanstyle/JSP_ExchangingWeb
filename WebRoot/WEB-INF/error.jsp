<%@page import="com.xiaoming.util.ToUtf8Str"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>错误页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="imgs/ico.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="csses/err.css"/>
  </head>
  
  <body>
    <%
    	//获取请求的错误参数并显示
    	String errMsg = java.net.URLDecoder.decode(request.getParameter("errMsg"),"utf-8");
    	System.out.println("err:"+errMsg);
     %>
     <center>
     <div id="errDiv">
     	<%=errMsg %>
     </div>
     <br/>
     <br/>
     </center>
  </body>
</html>
