package juniors.server.core.logic.services;

import java.util.Collection;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.events.Event;

/**
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 *
 */
public class EventService {

        public EventService() {

        }

        public Collection<Event> getEventsMap() {                    
                return DataManager.getInstance().getEventsCollection();
        }

        public Event addEvent(Event newEvent) {
                return DataManager.getInstance().addEvent(newEvent);
        }

        public Event getEvent(int eventId) {
                return DataManager.getInstance().getEvent(eventId);
        }
}