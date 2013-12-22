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
	Map<Integer, Outcome> outcomeMap;
	
	public EventManager(){
		eventsMap = new ConcurrentHashMap<Integer, Event>();
		outcomeMap = new ConcurrentHashMap<Integer, Outcome>();
	}
	
	@Override
	public Event addEvent(Event newEvent) {
		return eventsMap.put(newEvent.getEventId(), newEvent);
	}
	
	/**
	 * Добавляет новый исход в маркет и в общий контейнер.
	 * @param newOutcome
	 * @param eventId
	 * @param marketId
	 * @return - true - если всё корректно добавлено
	 */
	@Override 
	 public boolean addOutcome(Outcome newOutcome, int eventId, int marketId){
	    // проверка корректности запроса
	    if (!containsEvent(eventId)){
		return false;
	    }
	    
	    if (!getEvent(eventId).containsMarket(marketId)){
		return false;
	    }
	    
	    // добавляет новый исход в общий контейнер и в маркет
	    getEvent(eventId).getMarket(marketId).addOutcome(newOutcome);
	    outcomeMap.put(newOutcome.getOutcomeId(), newOutcome);
	    
	    // задаём маркет исходу
	    Market curMarket = getEvent(eventId).getMarket(marketId);
	    newOutcome.setMarket(curMarket);
	    
	    return true;
	}
	
	@Override
	public boolean containsOutcome(int outcomeId){
	    return outcomeMap.containsKey(outcomeId);
	}
	
	@Override
	public Outcome getOutcome(int outcomeId){
	    return outcomeMap.get(outcomeId);
	}

	@Override
	public Event getEvent(int eventId) {
		return eventsMap.get(eventId);
	}

	@Override
	public Event removeEvent(int eventId) {
		return eventsMap.remove(eventId);
	}
	
	/**
	 * тестирование 
	 */
	public void testEventManager(){
		Event newEvent = new Event(1, 1555555L, "test evest");
		
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
        
        @Override
        public boolean containsEvent(int eventId){
            return eventsMap.containsKey(eventId);
        }
        
        
	
	/**
	 * для тестов
	 * @param args
	 */
	public static void main(String[] args){
		EventManager manager = new EventManager();
	//	manager.testEventManager();
	}
        
	public String toString() {
		return outcomeMap.keySet().toString();
		
	}
	
}
