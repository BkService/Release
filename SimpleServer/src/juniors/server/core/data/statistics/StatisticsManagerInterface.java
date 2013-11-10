package juniors.server.core.data.statistics;

/**
 * 
 * @author kovalev
 */
public interface StatisticsManagerInterface {
    
    /**
     * 
     * @param newValue
     * @return - время записи новых данных
     */
    public long setCountLoginPerHour(long newValue);
    
    public long getCountLoginPerHour();
    
    public Note getLoginPerHour();
    
    
    /**
     * 
     * @param newValue
     * @return - время записи новых данных
     */
    public long setCountLoginPerDay(long newValue);
    
    public long getCountLoginPerDay();
    
    public Note getLoginPerDay();
    
    
    /**
     * 
     * @param newValue
     * @return - время записи новых данных
     */
    public long setCountLoginPerMonth(long newValue);
    
    public long getCountLoginPerMonth();
    
    public Note getLoginPerMonth();
    
    
    /**
     * 
     * @param newValue
     * @return - время записи новых данных
     */
    public long setCountRequestPerSecond(long newValue);
    
    public long getCountRequestPerSecond();
    
    public Note getRequestPerSecond();
    
    
    /**
     * 
     * @param newValue
     * @return - время записи новых данных
     */
    public long setCountRequestPerMinute(long newValue);
    
    public long getCountRequestPerMinute();
    
    public Note getRequestPerMinute();
    
    
    /**
     * 
     * @param newValue
     * @return - время записи новых данных
     */
    public long setCountRequestPerHour(long newValue);
    
    public long getCountRequestPerHour();
    
    public Note getRequestPerHour();
    
    
    /**
     * 
     * @param newValue
     * @return - время записи новых данных
     */
    public long setCountRequestPerDay(long newValue);
    
    public long getCountRequestPerDay();
    
    public Note getRequestPerDay();
    
    
    
    /**
     * 
     * @param newValue
     * @return - время записи новых данных
     */
    public long setCountBetPerSecond(long newValue);
    
    public long getCountBetPerSecond();
    
    public Note getBetPerSecond();
    
    
    /**
     * 
     * @param newValue
     * @return - время записи новых данных
     */
    public long setCountBetPerMinute(long newValue);
    
    public long getCountBetPerMinute();
    
    public Note getBetPerMinute();
}
