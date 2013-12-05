package juniors.server.core.logic.services;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.bets.Bet;

public class TransactionHelper {

 public static void makeTransaction(Bet bet, Float sum) {
  if (DataManager.getInstance().getBookmaker().calculateBet(bet, sum))
   bet.getUser().getBalance().changeBalance(sum);
 }
}
