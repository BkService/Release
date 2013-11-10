<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="juniors.server.core.data.users.User" %>
<%@ page import="juniors.server.core.logic.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Its working</title>
	<link rel="stylesheet" type="text/css" href="styles/index.css"/>
	<script src="js/index.js" type="text/javascript"></script>
	<script src="http://code.jquery.com/jquery-git2.js" type="text/javascript"></script>
</head>
<body>
	<h2>
		Welcome to <s>hell</s> Simple Server
	</h2>
	<%
		User user = (User)request.getSession().getAttribute("user");
		String msg = (String) request.getSession().getAttribute("msg");
		msg = (msg == null) ? "" : msg;
		if(user != null) {
			request.getRequestDispatcher("/SwitchHandler").forward(request, response);
		} else {
	%>	
		<div id="signin" class="visible">
			<h4>Sign in, please:</h4>
			<form id="signform" action="/SimpleServer/LoginHandler" method="post" 
							onsubmit="">
				<input id="signuname" name="uname" type="text" placeholder="input login"/><br>
				<input id="signpasswd" name="passwd" type="password" placeholder="input password"/><br>
				<input type="submit" value="SignIn" onclick="return validatorLogin();"/>
			</form>	
			<br>
			<div class="action" onClick="replaceShow();">Registrations</div>
		</div>
		<div id="registration" class="none">
			<br>Input data to this fields and press OK<br><br>
			<jsp:include page="include/registration_form.html"></jsp:include>
			<br><br>
			If you have account of <s>hell</s> Simple Server,
			<div class="action" onClick="replaceShow();">Sign in here</div>
		</div>
	<%	}  %>
	<br>
	<div class="message"><%= msg %></div>
	<%
		request.getSession().setAttribute("msg", null);
	%>
</body>
</html>