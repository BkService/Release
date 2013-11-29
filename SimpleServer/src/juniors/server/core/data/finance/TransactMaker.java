package juniors.server.core.data.finance;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import juniors.server.core.data.users.Bookmaker;
import juniors.server.core.data.users.User;

/**
 * Хранит историю операций и букмекера
 * @author kovalev
 *
 */
public class TransactMaker {
    private Bookmaker bookmaker;
    private Set<Integer> transections;
    
    public TransactMaker(Bookmaker bookmaker){
	transections = new ConcurrentSkipListSet<Integer>();
	this.bookmaker = bookmaker;
    }
    
    /**
     * Добавляет транзакцию
     * @param betId
     * @return true - если она ещё не осуществлялась
     */
    public boolean addTransact(int betId){
	return transections.add(betId);
    }
}
