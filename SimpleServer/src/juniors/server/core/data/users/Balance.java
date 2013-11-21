package juniors.server.core.data.users;

import juniors.server.core.data.bets.Bet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Клас для хранения финансовой информации. 
 * Доступ к полям класса осуществляется только из функций User
 * @author kovalev
 *
 */
public class Balance {
    float available;
    Map<Bet, Float> reserve;
    
    public Balance() {
    	reserve = new ConcurrentHashMap<Bet, Float>();
    }
    
    public int getBalanceValue() {
    	return (int)this.available;
    }
}
