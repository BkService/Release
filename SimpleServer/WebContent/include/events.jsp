<%@ page import="juniors.server.core.data.events.*"%>
<%@ page import="juniors.server.core.data.markets.*"%>
<%@ page import="juniors.server.core.logic.*"%>
<%@ page import="juniors.server.core.logic.services.*"%>
<%@ page import="java.util.*"%>
<div style="font-size: xx-large; color: rgb(255, 100, 100);">
	Actual events
</div>
<div>
	<%
		EventService srv = ServerFacade.getInstance().getServices().getEventService();
		Event[] events = new Event[srv.getEventsMap().size()];
		events = srv.getEventsMap().toArray(events);
		String currenPage = request.getParameter("numpage");
		int numpage = 0;
		if(currenPage == null || currenPage.isEmpty())
			numpage = 1;
		else {
			try {
				numpage = Integer.parseInt(currenPage);
			} catch (Exception e) {
				numpage = 1;
			}
		}
		int all = events.length;
		int maxOnPage = 5;
		int onPage = (all <= maxOnPage) ? all : maxOnPage;
		int coutPages = all / onPage;
		if(all % maxOnPage > 0) coutPages++;
		if(numpage > coutPages)
			numpage = 1;
		if(coutPages == numpage)
			onPage = all - (numpage - 1)*maxOnPage;

		if(events == null || all == 0){
		%>
			<br><p style="font-size: x-large; color: blue;">No events</p>		
		<%
			return;
		}
		for(int i = (numpage - 1)*maxOnPage; i < (numpage - 1)*maxOnPage + onPage; ++i) {
			if(new Date().getTime() > events[i].getStartTime())
				continue;
			/* print event */
			%>
				<br><div class="eventItem"><%= events[i].getDescription() %><br>
				<%= events[i].getStartTimeToString() %></div><br>
			<%
			Market[] markets = new Market[events[i].getMarketsCollection().size()];
			markets = events[i].getMarketsCollection().toArray(markets);
			if(markets == null) {
				continue;
			}
			for(int j = 0; j < markets.length; ++j) {
				/* print markets */
				%>
					<div class="itemMarketView"> <%= markets[j].getDescription() %> </div>
					<table class="c">
					<tr>
						<th>Result</th>
						<th>Coefficient</th>
					</tr>
				<%
				Outcome[] results = new Outcome[markets[j].getOutcomeMap().size()];
				results = markets[j].getOutcomeMap().values().toArray(results);
				if(results == null) {
					continue;
				}
				for(int k = 0; k < results.length; ++k) {
					%>
						<tr class="element" id="<%= results[k].getOutcomeId() %>" onclick="showFormMakeBet(this);">
							<td><%= results[k].getDescription() %></td>
							<td width="5%"><%= results[k].getCoefficient() %></td>
						</tr>
					<%
				}
				%>
					</table>
				<%
			}
		}
	%>
<br><br><div style="margin-left: 200px;">
	<%
		for(int i = 1; i <= coutPages; ++i) {
			String num = (i == numpage) ? ("<font size=\"8\">" + i + "</font>") : ("" + i);
			%>
				_ <a href="/SimpleServer/cabinet.jsp?numpage=<%= i %>"><%= num %></a> _
			<%
		}
	%></div>
	<br><br>
</div>
<br><br>


