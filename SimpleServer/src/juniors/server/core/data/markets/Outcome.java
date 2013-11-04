package juniors.server.core.data.markets;

import java.util.LinkedList;
import juniors.server.core.data.bets.*;

public class Outcome {
	private final Integer outcomeId;
	private Double coefficient; //всегда больше 1
	private String description;
	// как это с многопоточностью связать, потом решу
	private LinkedList<Bet> bets; // контейнер со ставками на данный исход
	
	public Outcome(Integer id){
		outcomeId = id;
		coefficient = 1.0;
		description = "No available description.";
		bets = new LinkedList<Bet>();
	}
	
	public Outcome(Integer id, Double coefficient){
		outcomeId = id;
		this.coefficient = coefficient;
		description = "No available description.";
		bets = new LinkedList<Bet>();
	}
	
	public Outcome(Integer id, Double coefficient, String description){
		outcomeId = id;
		this.coefficient = coefficient;
		this.description = description;
		bets = new LinkedList<Bet>();
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
	public boolean createBet(Bet newBet){
		bets.add(newBet);
		
		return true;
	}
	
	/**
	 * 
	 * @return контейнер со ставками
	 */
	public LinkedList<Bet> getBets(){
		return bets;
	}
}














