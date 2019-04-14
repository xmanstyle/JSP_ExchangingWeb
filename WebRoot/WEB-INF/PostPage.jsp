<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>发布消息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="csses/post.css">
	<link rel="shortcut icon" href="imgs/ico.png" type="image/x-icon" />
	<script type="text/javascript" language="javascript" src="jses/post.js">
	</script>
  </head>
  
  <body>
        <div id="mainDiv">
  	   <a href="servlet/HandleIntoMain">主页</a>
  	 </div>
    <center>
    <div id="container">
    	<div id="title">
    		<%
    			String type = null;
    			String title = null;
    			type = request.getParameter("type");
    			if(type == null){
    			String err = java.net.URLEncoder.encode("请求错误!","utf-8");
    				request.getRequestDispatcher("/WEB-INF/error.jsp?errMsg="+err).forward(request, response);
    			}else{
    				if(type.equals("lost")){title="<<发布失物>>";}
    				else if(type.equals("meet")){title="<<发布捡物>>";}
    				else {title="<<发布调换>>";}
    			}
    		 %>
    		 <%=title %>
    	</div>
    	<div id="formdiv">
    		<form action="servlet/HandlePostContent?type=<%=type %>" 
    		method="post" enctype="multipart/form-data">
    			<table id="posttable" border="4px">
    				<tr>
    					<td>描述信息</td>
    					<td><textarea name="info" rows="3" cols="30"></textarea></td>
    				</tr>
    				<tr>
    					<td>联系方式</td>
    					<td><input name="tel"/></td>
    				</tr>
    				<tr>
    					<td>图&nbsp;&nbsp;片</td>
    					<td><input type="file" name="img"/></td>
    				</tr>
    				<tr align="center">
    					<td colspan="2">
    					<input class="redBt" type="submit" value="发布" onclick="return checkBlank()"/>
    					</td>
    				</tr>
    			</table>
    		</form>
    	</div>
    </div>
    </center>
  </body>
</html>
