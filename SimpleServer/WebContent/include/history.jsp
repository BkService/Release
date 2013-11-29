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
			<th>State</th>
		</tr>
	 <%
	for(Bet bet : user.getBets().values()) {
		boolean finish = bet.getOutcome().getFinish();
		%>
			<tr class="element">
				<td><%= bet.getOutcome().getDescription() %></td>
				<td><%= bet.getCoefficient() %></td>
				<td><%= bet.getSum() %></td>
				<td><%= finish ? (bet.getOutcome().getWin()?"Yes":"No") : "Wait" %></td>
				<td><%= finish ? "Finished" : "Actual" %></td>
			</tr>
		<% 
	}
	%> </table> <%
%>