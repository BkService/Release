package juniors.server.core.logic;

import java.util.Date;

import juniors.server.core.data.events.Event;

/**
 * Helper to time check for event. (Event occured or not).
 * 
 * @author watson
 * 
 */

public class TimeChecker {
	public TimeChecker() {

	}

	/**
	 * Check occurred event or not.
	 * 
	 * @param event
	 *            - event to check.
	 * @return - true if event occurred, false otherwise.
	 */
	public boolean checkOccurred(Event event) {
		return new Date().after(new Date(event.getStartTime()));
	}
}
