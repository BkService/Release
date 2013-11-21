package juniors.server.core.data.bets;

import juniors.server.core.data.markets.*;
import juniors.server.core.data.users.*;


/**
 * 
 * @author kovalev
 *
 */
public class Bet implements Comparable<Bet>{
	private final User user;
	private final Outcome outcome;
	private final Double coefficient;
	private final Float sum; // сумма ставки
	private final Integer id;
	
	public Bet(User user, Outcome outcome, double current_coefficient, Float sum){
		this.user = user;
		this.outcome = outcome;
		this.coefficient = current_coefficient;
		this.sum = sum;
		this.id = getNextId();
	}
	
	static Integer nextId = 1;
	
	private static Integer getNextId() {
		return nextId++;
	}
	
	/**
	 * 
	 * @return User - который сделал эту ставку
	 */
	public User getUser(){
		return user;
	}
	
	/**
	 *  
	 * @return - исход, на который сделана ставка
	 */
	public Outcome getOutcome(){
		return outcome;
	}
	
	/**
	 * 
	 * @return - коеффициент исхода, во время создания ставки.
	 */
	public Double getCoefficient(){
		return coefficient;
	}
	
	/**
	 * 
	 * @return - сумма ставки
	 */
	public Float getSum(){
	    return sum;
	}

	@Override
	public int compareTo(Bet o) {
		if(o.id == this.id)
			return 0;
		if(o.id < this.id)
			return -1;
		return 1;
	}
}
