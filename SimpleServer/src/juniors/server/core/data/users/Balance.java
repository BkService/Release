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
    Map<Integer, Float> reserve;
    
    public Balance() {
    	reserve = new ConcurrentHashMap<Integer, Float>();
    }
    
    public int getBalanceValue() {
    	return (int)this.available;
    }
}
