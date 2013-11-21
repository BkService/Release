<%@page import="juniors.server.core.data.bets.Bet"%>
<%@page import="juniors.server.core.data.users.User"%>
<%@page import="juniors.server.core.logic.services.AccountsService"%>
<%@page import="juniors.server.core.logic.ServerFacade"%>
<br>
<% 
	AccountsService as = ServerFacade.getInstance().getServices().getAccountsService();
	User user = (User)request.getSession().getAttribute("user");
	if(user == null) {
		response.getWriter().write("fail");
		return;
	} else {
		%><%
	}
	%> <table class="c">
		<tr>
			<th>Outcome description</th>
			<th>Coefficient</th>
			<th>Summ of bet</th>
			<th>Is win</th>
			<th>Finished</th>
		</tr>
	 <%
	for(Bet bet : user.getBets()) {
		%>
			<tr class="element">
				<td><%= bet.getOutcome().getDescription() %></td>
				<td><%= bet.getCoefficient() %></td>
				<td><%= bet.getSum() %></td>
				<td><%= bet.getOutcome().getWin() ? "Yes" : "No" %></td>
				<td><%= bet.getOutcome().getFinish() ? "Yes" : "No" %></td>
			</tr>
		<% 
	}
	%> </table> <%
%>