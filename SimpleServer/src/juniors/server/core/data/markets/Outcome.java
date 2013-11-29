package juniors.server.core.data.markets;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import juniors.server.core.data.bets.*;

public class Outcome {
	private final Integer outcomeId;
	private Double coefficient; //всегда больше 1
	private String description;
	private boolean isWin;
	private boolean isFinished;
	// как это с многопоточностью связать, потом решу
	private Set<Bet> bets; // контейнер со ставками на данный исход
	
	public Outcome(Integer id){
		outcomeId = id;
		coefficient = 1.0;
		description = "No available description.";
		bets = new ConcurrentSkipListSet<Bet>();
	}
	
	public Outcome(Integer id, Double coefficient){
		outcomeId = id;
		this.coefficient = coefficient;
		description = "No available description.";
		bets = new ConcurrentSkipListSet<Bet>();
	}
	
	public Outcome(Integer id, Double coefficient, String description){
		outcomeId = id;
		this.coefficient = coefficient;
		this.description = description;
		bets = new ConcurrentSkipListSet<Bet>();
	}
	
	/**
	 * 
	 * @return коэффициент для исхода
	 */
	public Double getCoefficient(){
		return coefficient;
	}
	
	/**
	 * Коэффициент должен быть больше 1 (а надо ли это проверять?)
	 * @param newCoefficient
	 * @return true - коэффициент задан. false - не удалось задать
	 */
	public boolean setCoefficient(Double newCoefficient){
		/*if (new_coefficient <= 1){
			return false;
		}*/
		
		coefficient = newCoefficient;
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getOutcomeId(){
		return outcomeId;
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
	 * Доработать проверку корректности ставки 
	 * Создаёт ставку на данный исход 
	 */
	public boolean addBet(Bet newBet){
		bets.add(newBet);
		return true;
	}
	
	/**
	 * 
	 * @return контейнер со ставками
	 */
	public Set<Bet> getBets(){
		return bets;
	}
        
        public boolean removeBet(Bet bet){
            return bets.remove(bet);
        }

	public void setWin() {
	    isWin = true;
	    isFinished = true;
	}
	public void setLose() {
	    isWin = false;
	    isFinished = true;
	}
	@Override
	public String toString() {
	    return description + " " + coefficient + " " + isFinished + " " + isWin;  
	}
	
	public boolean getWin() {
		return this.isWin;
	}
	
	public boolean getFinish() {
		return this.isFinished;
	}

	public boolean isWin() {
	    return isWin;
	}
}














