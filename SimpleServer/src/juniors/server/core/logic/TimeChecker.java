package juniors.server.core.logic;

import java.util.Date;

import juniors.server.core.data.events.Event;

/**
 * It is stub to logic function checkTime.
 * 
 * 
 * @author watson
 *
 */

public class TimeChecker {
    public TimeChecker() {
	
    }
    
    public boolean checkOccured(Event event) {
	return new Date().after(new Date(event.getStartTime()));
    }
}
