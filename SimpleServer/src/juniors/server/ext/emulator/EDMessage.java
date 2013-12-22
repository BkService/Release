package juniors.server.ext.emulator;

import java.io.Serializable;

/**
 * 
 * @description инкапсулирует информацию о событиях.
 * Исключена лишняя информация для уменьшения размера сообщения
 * и зависимостей классов (простая сериализация) 
 * 
 * @author alex
 * 
 * */
public class EDMessage implements Serializable {

	private static final long serialVersionUID = 321322L;
	
	private EventMessage[] events;

	public EventMessage[] getEvents() {
		return events;
	}

	public void setEvents(EventMessage[] events) {
		this.events = events;
	}

}
