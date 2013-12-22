package juniors.server.ext.emulator;

import java.io.Serializable;

import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;

/**
 * 
 * @description Часть сообщения 'Events Data'
 * Содержит всю необходимую информацию
 * о событии. Массив такого типа данных 
 * нужен роботу для поиска коэффициентов,
 * согласно его алгоритму работы
 * 
 * @author alex
 * 
 * */
public class EventMessage  implements Serializable {

	private static final long serialVersionUID = 321325L;

	private long date;					// start time
	private int id;
	private MarketMessage[] markets;

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public MarketMessage[] getMarkets() {
		return markets;
	}

	public void setMarkets(MarketMessage[] markets) {
		this.markets = markets;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void initEvent(Event event) {
		this.date = event.getStartTime();
		this.id = event.getEventId();
		Market[] marks = new Market[event.getMarketsCollection().size()];
		marks = event.getMarketsCollection().toArray(marks);
		this.markets = new MarketMessage[marks.length];
		for(int i = 0; i < marks.length; ++i) {
			this.markets[i].initMarket(marks[i]);
		}
	}
}
