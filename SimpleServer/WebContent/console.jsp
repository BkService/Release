<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Simple server console</title>
	<link rel="stylesheet" type="text/css" href="styles/console.css" />
	<script type="text/javascript" src="js/jsRequests.js"></script>
</head>
<body onload="document.getElementById('commandline').focus();">
<div class="main">
	<div class="head">
		__simple server console__ WebShell v 1.1.0
		<div class="close">
			<a href="/SimpleServer/LogoutHandler">X</a>
		</div>
	</div>
	<br>
	<%
		String startLine = "admin@simpleserver ~ $";
		String history = (String)request.getSession().getAttribute("shell");
		if(history == null) {
			history = "";
			request.getSession().setAttribute("shell", "");
		}
	%>
	<%= history.equals("null") ? "" : history %>
	<form id="cmdfrm" action="/SimpleServer/xshell" method="post">
		<%= startLine %> <input autocomplete="off" id="commandline" type="text" name="command" class="xshell" onkeypress="send(event);"/> 
	</form>
</div>
</body>
</html>