<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="juniors.server.core.data.users.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>My private cabinet</title>
	<link rel="stylesheet" type="text/css" href="styles/cabinet.css"/>
	<link rel="stylesheet" type="text/css" href="styles/events.css"/>
	<script type="text/javascript" src="js/jsRequests.js"></script>
</head>
<body>
<%
	//это убрать отсюда нафиг. это очень плохая заглушка =)
	User user = (User)request.getSession().getAttribute("user");
	String namePage = (String) request.getSession().getAttribute("pagetype");
	if(namePage == null || namePage.isEmpty())
		namePage = "main";
%>
	<div class="header">
		<img src="imgs/ava.jpg" class="avatar"/>
		<div class="account">
			<!-- get info about user from connectionManager -->
			Name: 			<%= " " + user.getName() %><br>
			Surname:   		<%= " " + user.getSurname() %><br>
			Bank account:	<%= " " + user.getBankAccount() %>
		</div>
		<div class="logout"><a href="/SimpleServer/LogoutHandler">Logout</a></div>
	</div>
	
	<div>
		<div class="blockmenu">
			<span class="labelmenu">Menu</span>
			<table>
				<tr><td class="itemmenu"><a id="main" style="cursor: pointer;" 
										onclick="switchPage(this);">Main page</a></td></tr>
				<tr><td class="itemmenu"><a id="events" style="cursor: pointer;" 
										onclick="switchPage(this);">Upcoming events</a></td></tr>
				<tr><td class="itemmenu"><a id="history" style="cursor: pointer;" 
										onclick="switchPage(this);">My history of bets</a></td></tr>
				<tr><td class="itemmenu"><a id="options" style="cursor: pointer;" 
										onclick="switchPage(this);">Options</a></td></tr>
			</table>
		</div>
		<%
			namePage = "include/" + namePage + ".jsp";
		%>
		<div class="workspace">
			<jsp:include page="<%=namePage%>"></jsp:include>
		</div>
	</div>
</body>
</html>