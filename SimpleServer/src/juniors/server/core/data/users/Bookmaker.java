package juniors.server.core.data.users;

import juniors.server.core.data.bets.Bet;

public class Bookmaker extends User {
    
    public Bookmaker(String newLogin, String newName, String newSurname,
	    String newPassword, String newBankAccount) {

		super(newLogin, newName, newSurname, newPassword, newBankAccount);
		balance.changeBalance(999000);
    }

    /**
     * прибавляет sum к доступному балансу
     * @param sum
     * @return новый счёт. У букмекера он может быть отрицательный
     */
    public float changeBalance(float sum){
    	return balance.changeBalance(sum);	

    }
    
    /**
    * Если sum = 0, то ставка списывается, иначе ставка удаляется, а available 
    * пополняется на sum.
    * 
    * @param bet - ставка, с которой производится работа
    * @param sum - сумма операции (строго > 0).
    * @return - true - операция прошла успешно. False - произошла ошибка 
    */
    @Override
    public boolean calculateBet(Bet bet, float sum){
		// на случай отрицательной суммы нужна ли здесь проверка?
		if (sum < 0){
		    return false;
		}
		// проверка существования такого резерва и ставки 
		if (!balance.containsReserve(bet.getBetId())){
		    return false;
		}
		    
	        // если ставка проиграна, cумма зачисляется букмекеру
		if (sum == 0){
		    // возвращается резерв и плюс деньги игрока
		    balance.changeBalance(bet.getSum() + balance.getSumOfBet(bet.getBetId()));
		    balance.removeFromReserve(bet.getBetId());
		    
		    return true;
	        }
		else { // ставка выиграна, резерв просто удаляется
		    balance.removeFromReserve(bet.getBetId());
		   
		    return true;
		}
    }
    
    /**
     * Добавляет новую ставку и резервирует необходимую сумму в балансе
     * @param newBet
     * @return true - всё добавлено без ошибок. false - ставка уже существует
     */
    @Override
    public boolean addBet(Bet newBet) {
		if (balance.containsReserve(newBet.getBetId())){
		    return false;
		}
		
		// у букмекера резервируется сумма с учётом коэффициента
		float sum = (float) (newBet.getSum() * newBet.getCoefficient());
		balance.addToReserve(newBet.getBetId(), sum);	    
		
		return true;
    }
}
