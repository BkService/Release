<%@ page import="juniors.server.core.data.events.*"%>
<%@ page import="juniors.server.core.data.markets.*"%>
<%@ page import="juniors.server.core.logic.*"%>
<%@ page import="juniors.server.core.logic.services.*"%>
<%@ page import="java.util.*"%>
<br>
<div style="font-size: xx-large; color: rgb(255, 100, 100);">
	<p>Actual events</p>
</div>

<div>

	<%
		EventService srv = ServerFacade.getInstance().getServices().getEventService();
		Event[] events = new Event[srv.getEventsMap().size()];
		events = srv.getEventsMap().values().toArray(events);
		if(events == null){
			response.getWriter().println("No events");
			return;
		}
		for(int i = 0; i < events.length; ++i) {
			/* print event */
			%>
				<div class="eventItem"><%= events[i].getDescription() %></div><br><br>
			<%
			Market[] markets = new Market[events[i].getMarkets().size()];
			markets = events[i].getMarkets().values().toArray(markets);
			if(markets == null) {
				continue;
			}
			for(int j = 0; j < markets.length; ++j) {
				/* print markets */
				%>
					<div class="itemMarketView"> <%= markets[j].getDescription() %> </div><br>
					<table border="1" class="c">
					<tr>
						<td>Result</td>
						<td>Coefficient</td>
					</tr>
				<%
				Outcome[] results = new Outcome[markets[j].getOutcomeMap().size()];
				results = markets[j].getOutcomeMap().values().toArray(results);
				if(results == null) {
					continue;
				}
				for(int k = 0; k < results.length; ++k) {
					%>

						<tr>
							<td><%= results[k].getDescription() %></td>
							<td><%= results[k].getCoefficient() %></td>
						</tr>
	
					<%
				}
				%>
					</table>
				<%
			}
		}
	
	%>

</div>

<div id="all"></div>