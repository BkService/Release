<%@page import="java.util.regex.Pattern"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="juniors.server.core.data.users.User" %>
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
		<u style="margin-left: 10px;">Web Terminal v 1.2.0</u>
		<div class="close">
			<a href="/SimpleServer/LogoutHandler">X</a>
		</div>
	</div>
	<br>
	<%
		HttpSession ses = request.getSession(true);
		Boolean flag = (Boolean)ses.getAttribute("adm");
		if(flag == null || !flag) {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		String startLine = "admin@simpleserver ~ $";
		String history = (String)ses.getAttribute("shell");
	    Boolean br = (Boolean)ses.getAttribute("br");
	    if(br == null) br = true;
		if(history == null || history.isEmpty() || Pattern.matches("\\s+", history)) {
			history = "";
			ses.setAttribute("shell", "");
			br = false;
		}
	%>
	<%= (history == null || history.isEmpty()) ? "" : history %>
	<%= br ? "<br>" : "" %>
	<%= startLine %> <input autocomplete="off" id="commandline" type="text" name="command" class="xshell" onkeypress="send(event);"/> 
							
	
</div>
</body>
</html>
