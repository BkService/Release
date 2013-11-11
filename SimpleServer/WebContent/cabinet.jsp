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
	<script type="text/javascript" src="js/jsa.js"></script>
</head>
<body>

<%
	User user = (User)request.getSession().getAttribute("user");
	String namePage = (String) request.getSession().getAttribute("pagetype");
	if(namePage == null || namePage.isEmpty())
		namePage = "main";
	if(user == null) {
		/* stub. Надо сделать так, чтоб пользователи сразу 
		переходили в кабинет (но добавить панель входа для тех кто не вошел) */
		request.getSession().setAttribute("msg", "Limit of time wait finished");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
%>
<input type="hidden" id="loginOnPage" value="<%= user.getLogin() %>" />
<div id="aboutPanel" class="naboutPanel">Version 2.0.0 Beta<br>Authors: <br>Vadim, Alexey, Dmitrii and Yakov</div>	
<div id="formMakeBet" class="defaultl"></div>
<div id="f" class="nforma">
	<div class="headline">Form for make bets
		<div class="buttonClose" onclick="hideFormMakeBet();">&#215</div>
	</div>
	<br>
	<div id="wait"><img /></div>
	<div id="formContent"><pre>
  Information<br>
  	<span id="descr"></span>
  	Balance: <span id="ubalance"></span>
  	<span id="bets" style="color: red;">Bet: <input id="bet" style="border:solid 1px green;" 
  											value="0"><button onclick="checkAndSend();">Make bet</button></span>
	</pre></div>
</div>
	<div class="header">
		<img src="imgs/ava.jpg" class="avatar"/>
		<div class="account">
			Name: 			<%= " " + user.getName() %><br>
			Surname:   		<%= " " + user.getSurname() %><br>
	  		Balance: <span id="money"><%= user.getBalance() %></span><br>
			Bank account:	<%= " " + user.getBankAccount() %>
		</div>
		<div class="logout" onclick="window.location.replace('/SimpleServer/LogoutHandler');">Logout</div>
		<div class="about" onclick="showAbout();">About</div>
	</div>
	
	<div>
		<div class="blockmenu">
			<span class="labelmenu">Menu</span>
			<table>
				<tr><td id="main" class="itemmenu" style="cursor: pointer;" onclick="switchPage(this);">Main</td></tr>
				<tr><td id="events" style="cursor: pointer;" onclick="switchPage(this);" class="itemmenu">Upcoming events</td></tr>
				<tr><td class="itemmenu" id="history" style="cursor: pointer;" onclick="switchPage(this);">My history of bets</td></tr>
				<tr><td class="itemmenu" id="options" style="cursor: pointer;" onclick="switchPage(this);">Options</td></tr>
			</table>
		</div>
		<%
			namePage = "include/" + namePage + ".jsp";
		%>
		<div class="workspace">
			<jsp:include page="<%=namePage%>"></jsp:include>
		</div>
	</div>
	<br>
</body>
</html>