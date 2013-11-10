package juniors.server.core.data.events;

import java.util.Collection;
import java.util.Map;

/**
 * Описывает взаимодействие с событиями. 
 * @author kovalev
 *
 */
public interface EventManagerInterface {
	/**
	 * Создаётся и добавляется событие
	 * @param new_event
	 * @return ссылка на созданное событие
	 */
	public Event addEvent(Event newEvent);
	
	/**
	 * Получить ссылку на событие по id
	 * @param event_id
	 * @return cсылка на событе. Если такого не существует - возвращает null
	 */
	public Event getEvent(int eventId);
	
	/**
	 * Удалить событие по id
	 * @param event_id
	 * @return удалённый объект. Null - если не найден 
	 */
	public Event removeEvent(int eventId);
	
	/**
	 * 
	 * @return
	 */
	public Map<Integer, Event> getEventsMap();
	
	/**
	 * 
	 * @return
	 */
	public Collection<Event> getEventsCollection();
        
        public boolean containsEvent(int eventId);
}
