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
		if(events == null || events.length == 0){
		%>
			<br><p style="font-size: x-large; color: blue;">No events</p>		
		<%
			return;
		}
		for(int i = 0; i < events.length; ++i) {
			/* print event */
			%>
				<br><div class="eventItem"><%= events[i].getDescription() %></div>
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
<br><br>
</div>
<br><br>


