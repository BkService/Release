package juniors.server.core.data.events;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;

/**
 * Реализация интерфейса EventManagerInterface
 * @author kovalev
 *
 */
public class EventManager implements EventManagerInterface {
	Map<Integer, Event> eventsMap;	
	
	public EventManager(){
		eventsMap = new ConcurrentHashMap<Integer, Event>();
	}
	
	@Override
	public Event addEvent(Event newEvent) {
		return eventsMap.put(newEvent.getEventId(), newEvent);
	}

	@Override
	public Event getEvent(int eventId) {
		return eventsMap.get(eventId);
	}

	@Override
	public Event removeEvent(int eventId) {
		return eventsMap.remove(eventId);
	}
	
	public void testEventManager(){
		Event newEvent = new Event(1, "test evest");
		
		this.addEvent(newEvent);
		
		Event testEvent = this.getEvent(1);
		
		Market newMarket = new Market(1, "test market");
		
		testEvent.addMarket(newMarket);
		
		Market testMarket = testEvent.getMarket(1);
		Outcome newOutcome = new Outcome(1, 1.0);
		newOutcome.setDescription("test Outcome");
		testMarket.addOutcome(newOutcome);
		
		
	}
	
	@Override
	public Map<Integer, Event> getEventsMap() {
		return eventsMap;
	}

	@Override
	public Collection<Event> getEventsCollection() {
		return eventsMap.values();
	}
}
