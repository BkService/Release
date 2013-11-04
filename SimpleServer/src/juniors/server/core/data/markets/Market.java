package juniors.server.core.data.markets;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import juniors.server.core.data.bets.Bet;
import juniors.server.core.data.users.User;

public class Market {
	private final Integer marketId;
	private Map<Integer, Outcome> outcomesMap;
	private String description;
	//private long finishTime; 
	
	public Market(Integer id){
		marketId = id;
		description = "No available description.";
		outcomesMap = new ConcurrentHashMap<Integer, Outcome>();
	}
	
	public Market(Integer id, String newDescription){
		marketId = id;
		description = newDescription;
		outcomesMap = new ConcurrentHashMap<Integer, Outcome>();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * 
	 * @param new_description
	 * @return true - описание задано. False - произошла ошибка.
	 */
	public boolean setDescription(String newDescription){
		description = newDescription;
		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getMarketId(){
		return marketId;
	}
	
	/**
	 * Добавить новый исход. Id исхода в пределах маркета должен быть уникальным
	 * @param new_outcome
	 * @return true - добавил. false - не добавил
	 */
	public boolean addOutcome(Outcome newOutcome){
		
		outcomesMap.put(newOutcome.getOutcomeId(), newOutcome);
		return true;
		
	}
	
	// нужна ли такая возможность и как её лучше реализовать?
	public Map<Integer, Outcome> getOutcomeMap(){
		return outcomesMap;
	}
	
	/**
	 * Пока заглушка
	 * 
	 * @param betId
	 * @param user
	 * @param outcomeId
	 * @return
	 */
	public Bet makeBet(Integer betId, User user, Integer outcomeId){
		return null;
	}
	
	/**
	 * заглушка
	 * @param idWinOutcame
	 * @return
	 */
	public boolean calculateMarket(int idWinOutcame){
		return false;
	}
}
























