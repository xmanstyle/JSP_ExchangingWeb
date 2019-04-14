<%@page import="com.xiaoming.model.UserBean"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>关于</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="imgs/ico.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="csses/intro.css">
  </head>
  <%
  		UserBean loginUser = (UserBean)session.getAttribute("loginUser");
  		if(loginUser == null){
  			String err = java.net.URLEncoder.encode("服务器与您还未建立回话!","utf-8");
  			request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
  			return;
  		}
   %>
  <body>
  	<center>
    <div id="title">
    	服务说明
    </div>
    <div id="welcome">
    	&nbsp;&nbsp;欢迎使用武警警官学院物资互助系统
    </div>
    <div id="content">
&nbsp;&nbsp;注：请用户仔细阅读，以便您更好的使用本网站
	此网站的开发主要是解决战友们被装型号不合适的问题，让各位战友们可以更快的更换到适合自己的物品，更换的物品可以是衣服、鞋子等。此网站还有失物招领的功能，战友们可以在上面发布自己捡到的或者自己丢失的物品。发布时尽可能把物品描述准确清楚，方便战友们快速搜索和浏览。
    	如果大家对此网站有什么好的意见或者建议，可以联系我们，谢谢！	
	联系我们    电话：************
    </div>
    <div id="link"> 
    	<a href="servlet/HandleIntoMain">进入主页</a>
    </div>
    </center>
  </body>
</html>
