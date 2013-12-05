package juniors.server.core.data.finance;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import juniors.server.core.data.bets.Bet;

/**
 * Хранит историю операций и ошибочные операции
 * @author kovalev
 *
 */
public class TransactSaver {
	private Set<Bet> transactions;
	private Set<Bet> failTransact;

	public TransactSaver(){
		transactions = new ConcurrentSkipListSet<Bet>();
		failTransact = new ConcurrentSkipListSet<Bet>();
	}

	/**
	 * Добавляет транзактцию
	 * @param betId 
	 * @return true - если она ещё не осуществлялась
	 */
	public boolean addTransact(Bet bet){
		return transactions.add(bet);
	}

	/**
	 * Удаляет транзактцию из списка осуществлённых и добавляет в список ошибочных
	 * @param betId
	 * @return - true - если в ошибках её ещё не было
	 */
	public boolean addFailTransact(Bet bet){
		transactions.remove(bet);
		return failTransact.add(bet);
	}

	/**
	 * 
	 * @return - возвращает контейнер с ошибочными транзактциями
	 */
	public Set<Bet> getFailTransact(){
		return failTransact;
	}
}
