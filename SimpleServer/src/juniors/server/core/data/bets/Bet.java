package juniors.server.core.data.bets;

import juniors.server.core.data.markets.*;
import juniors.server.core.data.users.*;

/**
 * 
 * @author kovalev
 *
 */
public class Bet{
	private final User user;
	private final Outcome outcome;
	private final Double coefficient;
//	private sum; // как работать с финансами - потом решим
	
	public Bet(User user, Outcome outcome, double current_coefficient){
		this.user = user;
		this.outcome = outcome;
		this.coefficient = current_coefficient;
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
}
