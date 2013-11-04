package juniors.server.core.data;

import java.util.Collection;
import java.util.Map;

import juniors.server.core.data.events.*;
import juniors.server.core.data.users.*;

public class Data implements UserManagerInterface, EventManagerInterface{
	
	private UserManagerInterface userManager;
	private EventManagerInterface eventManager;
	
	public Data(){
		userManager = new UserManager();
		eventManager = new EventManager();
	}
	
	@Override
	public Event addEvent(Event newEvent) {
		return eventManager.addEvent(newEvent);
	}

	@Override
	public Event getEvent(int eventId) {
		return eventManager.getEvent(eventId);
	}

	@Override
	public Event removeEvent(int eventId) {
		return eventManager.removeEvent(eventId);
	}

	@Override
	public boolean containsUser(String login) {
		return userManager.containsUser(login);
	}

	@Override
	public boolean changeLogin(String login, String newLogin) {
		return userManager.changeLogin(login, newLogin);
	}

	@Override
	public User getUser(String login) {
		return userManager.getUser(login);
	}

	@Override
	public boolean authorizeUser(Integer userId) {
		return userManager.authorizeUser(userId);
	}

	@Override
	public int getCountUsers() {
		return userManager.getCountUsers();
	}

	@Override
	public int getCountAuthorizedUsers() {
		return userManager.getCountAuthorizedUsers();
	}

	@Override
	public boolean createUser(String newLogin, String newName,
			String newSurname, String newPassword, String newBankAccount) {
		return userManager.createUser(newLogin, newName, newSurname, newPassword, newBankAccount);
	}

	@Override
	public boolean changeUserData(String login, String newName,
			String newSurname, String newPassword, String newBankAccount) {
		return userManager.changeUserData(login, newName, newSurname, newPassword, newBankAccount);
	}

	@Override
	public Map<Integer, Event> getEventsMap() {
		return eventManager.getEventsMap();
	}

	@Override
	public Collection<Event> getEventsCollection() {
		return eventManager.getEventsCollection();
	}
	
}
