package juniors.server.core.data.users;

import juniors.server.core.data.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс для хранения финансовой информации. 
 * Содержит 2 поля для финансовой информации.
 * Одно поле для хранения доступного счёта, другое - для хранения резерва.
 * В резерве сохраняется id ставки и сумма ставки, которая вычитается из 
 * доступных средств.
 * Так же для организации транзакций добавлена блокировка баланса
 * 
 * @author kovalev
 *
 */
public class Balance implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// сумма на счёте, доступная для операций
    private float available;
    // хранит ID ставки и ёё сумму
    private Map<Integer, Float> reserve;
    private volatile boolean lockBalance; // запрет на операции с балансом
    
    public Balance() {
            reserve = new ConcurrentHashMap<Integer, Float>();
            lockBalance = false;
    }
    
    /**
     * лочит доступ к балансу. ПОСЛЕ ВЫПОЛНЕНИЯ САМ ДОЛЖЕН РАЗЛОЧИТЬ!
     * @throws InterruptedException
     */
    public void lockBalance(){
                while (lockBalance){
                    try {
                            Thread.sleep(100);
                    }
                    catch (InterruptedException e){
                            
                    }
                }
        
                lockBalance = true;
    }
    
    /**
     * освободить баланс
     */
    public void unlockBalance() {
            lockBalance = false;
    }
    
    /**
     * Ждёт освобождения баланса
     * @throws InterruptedException
     */
    private void waitUnlockedBalance() {
                while (lockBalance){
                    try {
                            Thread.sleep(100);
                    }
                    catch (InterruptedException e){
                            
                    }
                }
    }
    
    /**
     * @return - доступный баланс
     * @throws InterruptedException 
     */
    public float getBalance(){
                waitUnlockedBalance();
                return available;
    }
    
    /**
     * Изменяет доступный баланс
     * 
     * @param sum - сумма операции
     * @return - новый баланс
     */
    public float changeBalance(float sum){
            return available += sum;
    }
    
    /**
     * Добавляет сумму в резерв, вычитая её из доступного баланса
     * @param betId
     * @param sum
     * @return - false - ставка уже существует
     */
    public boolean addToReserve(int betId, float sum){
                if (reserve.containsKey(betId)){
                    return false;
                }
                
                available -= sum;
                reserve.put(betId, sum);
                
                return true;
    }
    
    /**
     * Удаляет ресерв, не проводя действий над доступным балансом
     * @param betId 
     * @return - false - если не существовало такой ставки
     */
    public boolean removeFromReserve(int betId){
                return !(reserve.remove(betId) == null);
    }
    
    /**
     * Зарезервирована ли сумма для этой ставки
     * @param betId - id ставки
     * @return - true - зарезервирована 
     */
    public boolean containsReserve(int betId){
                return reserve.containsKey(betId);
    }
    
    /**
     * Возвращает сумму ставки.
     * @param betId
     * @return сумма ставки
     */
    public float getSumOfBet(int betId){
                return reserve.get(betId);
    }
    
    /**
     * @return - доступный баланс в формате int 
     * @throws InterruptedException 
     */
    public int getBalanceValue()  {
                waitUnlockedBalance();
                
            return (int)this.available;
    }
    
    /**
     * Проверяет, банкрот ли пользователь. Если доступные средства меньше 
     * величины минимальной ставки и ресервы пусты, то пользователь банкрот
     * 
     * @return - true - да, банкрот
     */
    public boolean isBankrupt(){
            if (available < Data.MIN_BET && reserve.isEmpty()){
                    return true;
            }
            
            return false;
    }
}