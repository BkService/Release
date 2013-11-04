<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Error</title>
<link rel="stylesheet" type="text\css" href="styles/error.css" />
</head>
<body>
	<br>
	<h2 class="err">
		Error
		<%=request.getAttribute("javax.servlet.error.status_code")
					.toString()%><br>
		_________________________________________________________________
	</h2>
		
	<h1 class="err">
		<br>
		<br> Не ошибается тот, <br> кто ничего не делает!<br>
		Мы же работаем и делаем все возможное,<br> чтобы устранить эту
		ошибку!<br>
		<br> Спасибо за понимание
	</h1>
	<br>
	<br>
	<script type="text/javascript">
		var delay = 7000;
		setTimeout("window.location.href='/SimpleServer/index.jsp'", delay);
	</script>
	<h4 style="margin-left: 80px;">
		Через 7 секунд вы будуте перенаправлены на начальную страницу.<br>
		Если перенаправление не началось нажмите на <a
			href="/SimpleServer/index.jsp">эту ссылку</a>
	</h4>
</body>
</html>