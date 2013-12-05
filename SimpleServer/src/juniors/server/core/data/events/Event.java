package juniors.server.core.data.events;

//import java.sql.Time;
import java.util.Collection;
import java.util.HashMap;
import java.util.Date;

import juniors.server.core.data.markets.*;
/**
 * Описывает событие (любое). Есть строковое описание события 
 * Так же есть контейнер с маркетами, время начала и время конца (предполагаемое) событие
 * 
 * @author kovalev
 *
 */
public class Event {
	private HashMap<Integer, Market> markets; // контейнер с маркетами. У каждого маркета свой ID
	private String description; 	// описание события
	private final int id; 	// идентефикатор события
	private long startTime;	// время начала
	private long finishTime;	// время окончания
	
	public Event(int id, long startTime){
		this.id = id;
                this.startTime = startTime;
		markets = new HashMap<Integer, Market>();
		description = "No description available.";
	}
	
	public Event(int id, long startTime, String newDescription){
		this.id = id;
                this.startTime = startTime;
		markets = new HashMap<Integer, Market>();
		description = newDescription;
	}
	
	public Event(int id, long startTime, HashMap<Integer, Market> newMarkets, String newDescription){
		this.id = id;
                this.startTime = startTime;
		markets = newMarkets;
		description = newDescription;
	}
	
	/**
	 * Получить маркет по id. 
	 * @param id
	 * @return - маркет с таким id
	 */
	public Market getMarket(Integer id){
		return markets.get(id);
	}
	
	/**
	 * Добавить новый маркет.
	 * @param new_market
	 * @return ссылка на прошлый маркет с таким id. Т. е. если не null - то уже такой был
	 */
	public Market addMarket(Market newMarket){
		return markets.put(newMarket.getMarketId(), newMarket);
	}
	
	/**
	 * Заменить описание
	 * @param new_description
	 * @return
	 */
	public boolean createDescription(String newDescription){
		description = newDescription;
		return true;
	}
	
	/**
	 * 
	 * @return описание события
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * 
	 * @return id события
	 */
	public Integer getEventId(){
		return id;
	}
	
	/**
	 * 
	 * @return количество маркетов
	 */
	public int getMarketsCount(){
		return markets.size();
	}
	
	public HashMap<Integer, Market> getMarketsMap(){
	    return markets;
	}
	
	public Collection<Market> getMarketsCollection(){
	    return markets.values();
	}
        
        public boolean containsMarket(int marketId){
            return markets.containsKey(marketId);
        }
        
        public void setStartTime(long newStartTime){
            startTime = newStartTime;
        }
        
        public void setFinishTime(long newFinishTime){
            finishTime = newFinishTime;
        }
        
        public long getStartTime(){
            return startTime;
        }
        
        public long getFinishTime(){
            return finishTime;
        }
        
        /**
         * 
         * @return время начала
         */
        public String getStartTimeToString(){
            Date date = new Date(startTime);
            return date.toString();
        }
        
        /**
         * 
         * @return время конца
         */
        public String getFinishTimeToString(){
            // если время конца не задано, то возвращается сообщение об этом
            if (finishTime == 0){
                return "Unknown";
            }
            else{
                Date date = new Date(finishTime);
                return date.toString(); 
            }
        }
        
        /**
         * Время начала события + описание + все маркеты
         */
        @Override
        public String toString() {
            String ans = startTime + " " + description + "\n";
            for (Market market : markets.values()) {
        	ans += market + "\n";
            }
            return ans;
        }
        
        
}