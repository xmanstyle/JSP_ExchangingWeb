<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xiaoming.util.MysqlCon"%>
<%@page import="com.mysql.jdbc.PreparedStatement"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>主页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="imgs/ico.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="csses/main.css">
	<script type="text/javascript" language="javascript" src="jses/main.js">
	</script>
  </head>
  	
  <body>
  <center id="center">
    <div id="container">
    	<div id="name">
    		小明的换物网站
    		<div id="mine">
    			<a href="servlet/HandleToChangeInfo">个人中心</a>
    			<div id="out">
    				<a href="servlet/HandleLogout" onclick="return sureOut()"><font color=white>退出</font></a>
    			</div>
    		</div>
    	</div>
    	<hr color="red"/>
    	<div id="title">
    	<ul id="list">
    		<li onmouseover="listOver('li1')" onmouseout="listOut('li1')" 
    		id="li1"><a href="servlet/HandlePost?type=lost">发布失物</a></li>
    		<li onmouseover="listOver('li2')" onmouseout="listOut('li2')" 
    		id="li2"><a href="servlet/HandlePost?type=meet">发布捡物</a></li>
    		<li onmouseover="listOver('li3')" onmouseout="listOut('li3')" 
    		id="li3"><a href="servlet/HandlePost?type=change">发布调换</a></li>
    		<li onmouseover="listOver('li4')" onmouseout="listOut('li4')" 
    		id="li4"><a href="servlet/HandleToMyPost">我发布的</a></li>
    	</ul>
    	</div>
    	<hr color="red"/> 
    	<div id="content1"> 
    		<%
    		MysqlCon mysql = new MysqlCon();
    		Connection con = mysql.getCon();
    		
    		int topPageNow = 1;//当前页
    		String topPageNowStr = (String)request.getParameter("topPageNow");
    		if(topPageNowStr != null){
    			topPageNow = Integer.parseInt(topPageNowStr);
    		}
    		int leftPageNow = 1;//当前页
    		String leftPageNowStr = (String)request.getParameter("leftPageNow");
    		if(leftPageNowStr != null){
    			leftPageNow = Integer.parseInt(leftPageNowStr);
    		}
    		int rightPageNow = 1;//当前页
    		String rightPageNowStr = (String)request.getParameter("rightPageNow");
    		if(rightPageNowStr != null){
    			rightPageNow = Integer.parseInt(rightPageNowStr);
    		}
    		
    		
    		//<<<<<<<<<<<<<<<<<<<< 设置页面中最上面一栏 《调换信息》显示 >>>>>>>>>>>>>>>
    		int topPageSize = 5;//每页显示多上条记录
    		int topPageCount = 0;//页数
    		int topRowCount = 0;//数据库中的记录数
    		int topMax = 8;
    		int topShowed = 0;
    		boolean topFlg = false;
    		try{
    			PreparedStatement topPreparedStatement = (PreparedStatement)con.prepareStatement("select count(*) from tab_change");
    			ResultSet topSet = topPreparedStatement.executeQuery();
    			topSet.next();
    			topRowCount = topSet.getInt(1);
    			topSet.close();
    			topPreparedStatement.close();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		//计算共有多少页
    		topPageCount = (topRowCount-1)/topPageSize+1;
    		try{
    			int topM = (topPageNow-1)*topPageSize;//limit start
    			String topSelect = "select * from tab_change order by id desc limit ?,?";
    			PreparedStatement topPreparedStatement2 = (PreparedStatement)con.prepareStatement(topSelect);
    			topPreparedStatement2.setInt(1, topM);
    			topPreparedStatement2.setInt(2,topPageSize);
    			ResultSet topSet2 = topPreparedStatement2.executeQuery();
    			String topId = null,topStudentId = null,topTelphone = null;
    			String topInfo = null,topTime = null,topIsPass = null;
    			while(topSet2.next()){
    				topId = topSet2.getString("id");
    				topStudentId = topSet2.getString("studentid");
    				topTelphone = topSet2.getString("telphone");
    				topInfo = topSet2.getString("info");
    				topTime = topSet2.getString("time");
    				topIsPass = topSet2.getString("ispass");
    			%>
    				<div class="item" 
    				<%
    				if(topIsPass.equals("yes")){
    					%>
    					style="background-color:gray;"
    					<%
    				}
    				 %>
    				>
    				<img src="uploadImgs/change_<%=topId %>.jpg"/><br/>
    				<a href="servlet/HandleToPerson?studentId=<%=topStudentId%>"><%=topStudentId%></a><br/>
    				<font color="red">电话:</font><%=topTelphone %><br/>
    				<font color="red">描述:</font><%=topInfo %><br/>
    				<font color="red">时间:</font><%=topTime %>
    				</div>
    			<%
    			}
    			%><div id="page1"><%
    			//<<<<<<<<<<<<<<<<<<<< 设置页面中最上面一栏 《调换信息》 的分页显示 >>>>>>>>>>>>>>>
    			if(topPageNow-2 > 0){
            		%>
            		<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topPageNow-1)%>'>&lt;&lt;</a>
            		<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topPageNow-2)%>'><%=(topPageNow-2)%></a>
            		<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topPageNow-1)%>'><%=(topPageNow-1)%></a>
            		<%
            		topFlg = true;
            		topShowed = 2;
        		}
        		else{
					if(topPageNow > 1){
						%>
						<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topPageNow-1)%>'>&lt;&lt;</a>
						<%
					}
					int topCur = 1;
					while((topShowed<=topMax)&&(topCur<=topPageCount)){
						if(topCur==topPageNow)
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topPageNow)%>'><font color=white>[<%=topPageNow%>]</font></a>
						<%}
						else
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topCur)%>'><%=topCur%></a>
						<%}
						topCur++;
						topShowed++;
					}
					if(topPageNow < topCur-1){
						%>
						<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topPageNow+1)%>'>&gt;&gt;</a>
						<%
					}
				}
				if(topFlg){
					int topCur = topPageNow;
					while((topShowed<=topMax)&&(topCur<=topPageCount)){
						if(topCur==topPageNow)
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topCur)%>'><font color=white>[<%=topCur%>]</font></a>
						<%}
						else
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topCur)%>'><%=topCur%></a>
						<%}
						topCur++;
						topShowed++;
					}
					if(topPageNow < topCur-1){
						%>
						<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topPageNow+1)%>'>&gt;&gt;</a>
						<%
					}
				}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		%>	                                                                                    
    		</div>
    	</div>
    	<hr color="red"/>
    	<div id="content2">
    	<div id="left">
    		<div id="lefttitle">
    		<<<失物>>>
    		</div>
    		<%
    		//<<<<<<<<<<<<<<<<<<<< 设置页面中最上面一栏 《失物信息》显示 >>>>>>>>>>>>>>>
    		int leftPageSize = 2;//每页显示多上条记录
    		int leftPageCount = 0;//页数
    		int leftRowCount = 0;//数据库中的记录数
    		int leftMax = 4;
    		int leftShowed = 0;
    		boolean leftFlg = false;
    		try{
    			PreparedStatement leftPreparedStatement = (PreparedStatement)con.prepareStatement("select count(*) from tab_lost");
    			ResultSet leftSet = leftPreparedStatement.executeQuery();
    			leftSet.next();
    			leftRowCount = leftSet.getInt(1);
    			leftSet.close();
    			leftPreparedStatement.close();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		leftPageCount = (leftRowCount-1)/leftPageSize+1;
    		try{
    			int leftM = (leftPageNow-1)*leftPageSize;//limit start
    			String leftSelect = "select * from tab_lost order by id desc limit ?,?";
    			PreparedStatement leftPreparedStatement2 = (PreparedStatement)con.prepareStatement(leftSelect);
    			leftPreparedStatement2.setInt(1, leftM);
    			leftPreparedStatement2.setInt(2,leftPageSize);
    			ResultSet leftSet2 = leftPreparedStatement2.executeQuery();
    			String leftId = null,leftStudentId = null,leftTelphone = null;
    			String leftInfo = null,leftTime = null,leftIsPass = null;
    			while(leftSet2.next()){
    				leftId = leftSet2.getString("id");
    				leftStudentId = leftSet2.getString("studentid");
    				leftTelphone = leftSet2.getString("telphone");
    				leftInfo = leftSet2.getString("info");
    				leftTime = leftSet2.getString("time");
    				leftIsPass = leftSet2.getString("ispass");
    			%>
    				<div class="leftitem"
    				<%
    				if(leftIsPass.equals("yes")){
    					%>
    					style="background-color:gray;"
    					<%
    				}
    				 %>
    				>
    				<img src="uploadImgs/lost_<%=leftId %>.jpg"/><br/>
    				<a href="servlet/HandleToPerson?studentId=<%=leftStudentId%>"><%=leftStudentId%></a><br/>
    				<font color="red">电话:</font><%=leftTelphone %><br/>
    				<font color="red">描述:</font><%=leftInfo %><br/>
    				<font color="red">时间:</font><%=leftTime %>
    				</div>
    			<%
    			}
    			%><div id="pageleft"><%
    			//<<<<<<<<<<<<<<<<<<<< 设置页面中最上面一栏 《失物信息》 分页显示 >>>>>>>>>>>>>>>
    			if(leftPageNow-2 > 0){
            		%>
            		<a href='servlet/HandleIntoMain?leftPageNow=<%=(leftPageNow-1)%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=topPageNow%>'>&lt;&lt;</a>
            		<a href='servlet/HandleIntoMain?leftPageNow=<%=(leftPageNow-2)%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=topPageNow%>'><%=(leftPageNow-2)%></a>
            		<a href='servlet/HandleIntoMain?leftPageNow=<%=(leftPageNow-1)%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=topPageNow%>'><%=(leftPageNow-1)%></a>
            		<%
            		leftFlg = true;
            		leftShowed = 2;
        		}
        		else{
					if(leftPageNow > 1){
						%>
						<a href='servlet/HandleIntoMain?leftPageNow=<%=(leftPageNow-1)%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=topPageNow%>'>&lt;&lt;</a>
						<%
					}
					int leftCur = 1;
					while((leftShowed<=leftMax)&&(leftCur<=leftPageCount)){
						if(leftCur==leftPageNow)
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topPageNow)%>'><font color=white>[<%=leftPageNow%>]</font></a>
						<%}
						else
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftCur%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=topPageNow%>'><%=leftCur%></a>
						<%}
						leftCur++;
						leftShowed++;
					}
					if(leftPageNow < leftCur-1){
						%>
						<a href='servlet/HandleIntoMain?leftPageNow=<%=(leftPageNow+1)%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=topPageNow%>'>&gt;&gt;</a>
						<%
					}
				}
				if(leftFlg){
					int leftCur = leftPageNow;
					while((leftShowed<=leftMax)&&(leftCur<=leftPageCount)){
						if(leftCur==leftPageNow)
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftCur%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=topPageNow%>'><font color=white>[<%=leftCur%>]</font></a>
						<%}
						else
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftCur%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=topPageNow%>'><%=leftCur%></a>
						<%}
						leftCur++;
						leftShowed++;
					}
					if(leftPageNow < leftCur-1){
						%>
						<a href='servlet/HandleIntoMain?leftPageNow=<%=(leftPageNow+1)%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=topPageNow%>'>&gt;&gt;</a>
						<%
					}
				}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		%>
    		</div>
    	</div>
    	<div id="right">
    		<div id="righttitle">
    		<<<捡物>>>
    		</div>
    		<%
    		//<<<<<<<<<<<<<<<<<<<< 设置页面中最上面一栏 《捡到信息》 显示 >>>>>>>>>>>>>>>
    		int rightPageSize = 2;//每页显示多上条记录
    		int rightPageCount = 0;//页数
    		int rightRowCount = 0;//数据库中的记录数
    		int rightMax = 4;
    		int rightShowed = 0;
    		boolean rightFlg = false;
    		try{
    			PreparedStatement rightPreparedStatement = (PreparedStatement)con.prepareStatement("select count(*) from tab_meet");
    			ResultSet rightSet = rightPreparedStatement.executeQuery();
    			rightSet.next();
    			rightRowCount = rightSet.getInt(1);
    			rightSet.close();
    			rightPreparedStatement.close();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		rightPageCount = (rightRowCount-1)/rightPageSize+1;
    		try{
    			int rightM = (rightPageNow-1)*rightPageSize;//limit start
    			String rightSelect = "select * from tab_meet order by id desc limit ?,?";
    			PreparedStatement rightPreparedStatement2 = (PreparedStatement)con.prepareStatement(rightSelect);
    			rightPreparedStatement2.setInt(1, rightM);
    			rightPreparedStatement2.setInt(2,rightPageSize);
    			ResultSet rightSet2 = rightPreparedStatement2.executeQuery();
    			String rightId = null,rightStudentId = null,rightTelphone = null;
    			String rightInfo = null,rightTime = null,rightIsPass = null;
    			while(rightSet2.next()){
    				rightId = rightSet2.getString("id");
    				rightStudentId = rightSet2.getString("studentid");
    				rightTelphone = rightSet2.getString("telphone");
    				rightInfo = rightSet2.getString("info");
    				rightTime = rightSet2.getString("time");
    				rightIsPass = rightSet2.getString("ispass");
    			%>
    				<div class="rightitem"
    				<%
    				if(rightIsPass.equals("yes")){
    					%>
    					style="background-color:gray;"
    					<%
    				}
    				 %>
    				>
    				<img src="uploadImgs/meet_<%=rightId %>.jpg"/><br/>
    				<a href="servlet/HandleToPerson?studentId=<%=rightStudentId%>"><%=rightStudentId%></a><br/>
    				<font color="red">电话:</font><%=rightTelphone %><br/>
    				<font color="red">描述:</font><%=rightInfo %><br/>
    				<font color="red">时间:</font><%=rightTime %>
    				</div>
    			<%
    			}
    			%><div id="pageright"><% 
    			//<<<<<<<<<<<<<<<<<<<< 设置页面中最上面一栏 《捡到信息》 分页显示 >>>>>>>>>>>>>>>
    			if(rightPageNow-2 > 0){
            		//分页显示
            		%>
            		<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=(rightPageNow-1)%>&topPageNow=<%=topPageNow%>'>&lt;&lt;</a>
            		<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=(rightPageNow-2)%>&topPageNow=<%=topPageNow%>'><%=(rightPageNow-2)%></a>
            		<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=(rightPageNow-1)%>&topPageNow=<%=topPageNow%>'><%=(rightPageNow-1)%></a>
            		<%
            		rightFlg = true;
            		rightShowed = 2;
        		}
        		else{
					if(rightPageNow > 1){
						%>
						<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=(rightPageNow-1)%>&topPageNow=<%=topPageNow%>'>&lt;&lt;</a>
						<%
					}
					int rightCur = 1;
					while((rightShowed<=rightMax)&&(rightCur<=rightPageCount)){
						if(rightCur==rightPageNow)
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightPageNow%>&topPageNow=<%=(topPageNow)%>'><font color=white>[<%=rightPageNow%>]</font></a>
						<%}
						else
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightCur%>&topPageNow=<%=topPageNow%>'><%=rightCur%></a>
						<%}
						rightCur++;
						rightShowed++;
					}
					if(rightPageNow < rightCur-1){
						%>
						<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=(rightPageNow+1)%>&topPageNow=<%=topPageNow%>'>&gt;&gt;</a>
						<%
					}
				}
				if(rightFlg){
					int rightCur = rightPageNow;
					while((rightShowed<=rightMax)&&(rightCur<=rightPageCount)){
						if(rightCur==rightPageNow)
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightCur%>&topPageNow=<%=topPageNow%>'><font color=white>[<%=rightCur%>]</font></a>
						<%}
						else
						{%>
							<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=rightCur%>&topPageNow=<%=topPageNow%>'><%=rightCur%></a>
						<%}
						rightCur++;
						rightShowed++;
					}
					if(rightPageNow < rightCur-1){
						%>
						<a href='servlet/HandleIntoMain?leftPageNow=<%=leftPageNow%>&rightPageNow=<%=(rightPageNow+1)%>&topPageNow=<%=topPageNow%>'>&gt;&gt;</a>
						<%
					}
				}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		 %>
    		</div>
    	</div>
    	</div>
    </div>
    </center>
  </body>
</html>
