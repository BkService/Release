package juniors.server.core.data.statistics;

/**
 * Инструменты для получения, хранения и редактирования статистических данных.
 * Все данные, которые обновляются реже, чем раз в минути вычисляются автоматически.
 * Но для каждого типа данных всё равно данные с минимальной частотой надо обновлять 
 * вручную (если данные обновляются раз в час, день и месяц, то раз в час обновлять надо руками)
 * @author kovalev
 */
public class StatisticsManager implements StatisticsManagerInterface{
    
    protected Note loginPerHour;
    protected Note loginPerDay;
    protected Note loginPerMonth;
    
    protected Note requestPerSecond;
    protected Note requestPerMinute;
    protected Note requestPerHour;
    protected Note requestPerDay;
    
    protected Note betPerSecond;
    protected Note betPerMinute;
    
    /**
     * Все значения - нулевые
     */
    public StatisticsManager(){
        loginPerHour = new Note();
        loginPerDay = new Note();
        loginPerMonth = new Note();
        
        requestPerSecond = new Note();
        requestPerMinute = new Note();
        requestPerHour = new Note();
        requestPerDay = new Note();
        
        betPerSecond = new Note();
        betPerMinute = new Note();
    }
    
    @Override
    public long setCountLoginPerHour(long newValue) {
        
        loginPerDay.addToValue(newValue);
        loginPerMonth.addToValue(newValue);
        
        return loginPerHour.setValue(newValue);
    }

    @Override
    public long getCountLoginPerHour() {
        return loginPerHour.getValue();
    }

    @Override
    public Note getLoginPerHour() {
        return loginPerHour;
    }

    
    @Override
    public long setCountLoginPerDay(long newValue) {
        return loginPerDay.setValue(newValue);
    }

    @Override
    public long getCountLoginPerDay() {
        return loginPerDay.getValue();
    }

    @Override
    public Note getLoginPerDay() {
        return loginPerDay;
    }

    
    @Override
    public long setCountLoginPerMonth(long newValue) {
        return loginPerMonth.setValue(newValue);
    }

    @Override
    public long getCountLoginPerMonth() {
        return loginPerMonth.getValue();
    }

    @Override
    public Note getLoginPerMonth() {
        return loginPerMonth;
    }

    
    
    @Override
    public long setCountRequestPerSecond(long newValue) {
        return requestPerSecond.setValue(newValue);
    }

    @Override
    public long getCountRequestPerSecond() {
        return requestPerSecond.getValue();
    }

    @Override
    public Note getRequestPerSecond() {
        return requestPerSecond;
    }

    
    @Override
    public long setCountRequestPerMinute(long newValue) {
        requestPerHour.addToValue(newValue);
        requestPerDay.addToValue(newValue);
        
        return requestPerMinute.setValue(newValue);
    }

    @Override
    public long getCountRequestPerMinute() {
        return requestPerMinute.getValue();
    }

    @Override
    public Note getRequestPerMinute() {
        return requestPerMinute;
    }

    
    @Override
    public long setCountRequestPerHour(long newValue) {
        return requestPerHour.setValue(newValue);
    }

    @Override
    public long getCountRequestPerHour() {
        return requestPerHour.getValue();
    }

    @Override
    public Note getRequestPerHour() {
        return requestPerHour;
    }

    
    @Override
    public long setCountRequestPerDay(long newValue) {
        return requestPerDay.setValue(newValue);
    }

    @Override
    public long getCountRequestPerDay() {
        return requestPerDay.getValue();
    }

    @Override
    public Note getRequestPerDay() {
        return requestPerDay;
    }

    
    
    @Override
    public long setCountBetPerSecond(long newValue) {
        return betPerSecond.setValue(newValue);
    }

    @Override
    public long getCountBetPerSecond() {
        return betPerSecond.getValue();
    }

    @Override
    public Note getBetPerSecond() {
        return betPerSecond;
    }

    @Override
    public long setCountBetPerMinute(long newValue) {
        return betPerMinute.setValue(newValue);
    }

    @Override
    public long getCountBetPerMinute() {
        return betPerMinute.getValue();
    }

    @Override
    public Note getBetPerMinute() {
        return betPerMinute;
    }
    
}
