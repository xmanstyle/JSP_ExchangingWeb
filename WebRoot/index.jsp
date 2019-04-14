<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>小明的换物系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="imgs/ico.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="csses/body.css">
	<script language="javascript" type="text/javascript" src="jses/body.js">
	</script>
  </head>
  
  <body>
  <center id="center1">
    <form action="servlet/HandleLogin" id="form1" method="post">
    <table border="4px">
    	<tr align="center"><td colspan="2">登入系统</td></tr>
    	<tr ><td>学&nbsp;号:</td><td><input name="schoolId" id="schoolId" /></td></tr>
    	<tr ><td>密&nbsp;码:</td><td><input type="password" name="password" id="password" /></td></tr>
    	<tr align="center">
    	<td colspan="2">
    	<input id="loginbt" type="submit" onclick="return checkInput()" value="登录"/>
    		&nbsp;<a id="signlink" href="servlet/JumpToSign">注册?</a>
    	</td>
    	</tr>
    </table>
    </form>
  </center>
  </body>
</html>
