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
    
    <title>个人中心</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="imgs/ico.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="csses/change.css">
	<script language="javascript" type="text/javascript" src="jses/change.js">
	</script>
  </head>
  
  <body>
      <div id="mainDiv">
  	   <a href="servlet/HandleIntoMain">主页</a>
  	 </div>
  <%
  	UserBean loginUser = (UserBean)session.getAttribute("loginUser");
   %>
    <center>
    	<div id="center">
    		<form action="servlet/HandleSign?type=update&id=<%=loginUser.id%>" method="post" id="signForm">
    		<table border="4px">
    			<tr align="center"><td colspan="2">更改信息</td></tr>
				<tr><td>真实姓名</td><td><input name="realName" id="realName" value="<%=loginUser.userName%>"/></td></tr>
				<tr><td>学&nbsp;&nbsp;号</td><td><input name="schoolId" id="schoolId" value="<%=loginUser.schoolId%>"/></td></tr>
				<tr><td>密&nbsp;&nbsp;码</td><td><input type="password" name="pass" id="pass" value="<%=loginUser.userPass%>"/></td></tr>
				<tr><td>确认密码</td><td><input type="password" name="passagain" id="passagain" value="<%=loginUser.userPass%>"/></td></tr>
				<tr><td>电&nbsp;&nbsp;话</td><td><input name="telphone" id="telphone" value="<%=loginUser.telphone%>"/></td></tr>
				<tr>
				<td>单&nbsp;&nbsp;位</td>
				<td align="center">
					<select name="unit" id="unit">
						<%
						//设置表单中单位一栏下拉框的显示
						boolean flg;
						for(int i=1 ; i<=24 ; i++){
							for(int j=1 ; j<=3 ; j++){
								StringBuffer sb = new StringBuffer(i+"大队"+"-"+j+"中队");
								flg = false;
								if(sb.toString().equals(loginUser.unit)){
									flg = true;
									System.out.println("true->"+sb.toString());
								}
								%>
								<option value=<%=sb.toString()%> 
								<%
								if(flg){%>selected="selected"<%}
								%>
								>
								<%=sb.toString()%>
								</option>
								<%
							}
						}
						 %>
					</select>
				</td>
				</tr>
				<tr align="center">
				<td colspan="2">
				<input type="submit" value="修改" id="signBt" onclick="return checkSign()"/>
				</td>
				</tr>
    		</table>
    		</form>
    	</div>
    </center>
  </body>
</html>
