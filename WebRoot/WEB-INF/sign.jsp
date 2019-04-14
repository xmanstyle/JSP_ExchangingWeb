<%@page import="java.sql.Connection"%>
<%@page import="com.mysql.jdbc.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.xiaoming.util.MysqlCon"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="imgs/ico.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="csses/sign.css">
	<script language="javascript" type="text/javascript" src="jses/sign.js">
	</script>
  </head>
  
  <%
        MysqlCon mysqlCon = new MysqlCon();
        Connection con = mysqlCon.getCon();
  		String selectStr = "select * from tab_user";
  		ArrayList<String> schoolIds = new ArrayList();
  		ArrayList<String> telphones = new ArrayList();
		try {
			Statement statement = (Statement) con.createStatement();
			ResultSet set = statement.executeQuery(selectStr);
			int i = 0;
			while(set.next()){
				schoolIds.add(set.getString(4));
				telphones.add(set.getString(5));
			}
			System.out.println(schoolIds.toString());
			System.out.println(telphones.toString());
			statement.close();
			set.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  		mysqlCon.closeCon();
   %>
  <body>
    <center>
    	<form action="servlet/HandleSign?type=sign" id="signForm" method="post">
    		<table border="4px">
    			<tr align="center"><td colspan="2">用户注册</td></tr>
				<tr><td>真实姓名</td><td><input name="realName" id="realName"/></td></tr>
				<tr><td>学&nbsp;&nbsp;号</td><td><input name="schoolId" id="schoolId"/></td></tr>
				<tr><td>密&nbsp;&nbsp;码</td><td><input type="password" name="pass" id="pass"/></td></tr>
				<tr><td>确认密码</td><td><input type="password" name="passagain" id="passagain"/></td></tr>
				<tr><td>电&nbsp;&nbsp;话</td><td><input name="telphone" id="telphone"/></td></tr>
				<tr>
				<td>单&nbsp;&nbsp;位</td>
				<td align="center">
					<select name="unit" id="unit">
						<%
						for(int i=1 ; i<=24 ; i++){
							for(int j=1 ; j<=3 ; j++){
								StringBuffer sb = new StringBuffer(i+"大队"+"-"+j+"中队");
								%>
								<option value=<%=sb.toString()%>><%=sb.toString()%></option>
								<%
							}
						}
						 %>
					</select>
				</td>
				</tr>
				<tr align="center">
				<td colspan="2">
				<input type="submit" value="注册" id="signBt" onclick="return checkSign()"/>
				</td>
				</tr>
    		</table>
    	</form>
    </center>
  </body>
</html>
