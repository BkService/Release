package juniors.server.core.data.statistics;

/**
 * Запись хранит значение и время создания.
 * 
 * @author kovalev
 */
public class Note {

    protected volatile long value; // величина
    protected volatile long measurTime; // время получения данных
    
    /**
     * Создание статистической записи
     * @param value - статистическая величина
     * @param measurTime - время, когда она была получена
     */
    public Note(long value){
        this.value = value;
        this.measurTime = System.currentTimeMillis();
    }
    
    /**
     * Величина поумолчанию равна 0. Время - время создания
     */
    public Note(){
        value = 0;
        measurTime = System.currentTimeMillis();
    }
    
    /**
     * 
     * @return величина записи
     */
    public long getValue(){
        return value;
    }
    
    /**
     * 
     * @return время, когда была снята статистика
     */
    public long getMeasureTime(){
        return measurTime;
    }
    
    /**
     * Задаёт новую величину и автоматически меняет время.
     * @param newValue
     * @return - новое время записи данных
     */
    public long setValue(long newValue){
        value = newValue;
        measurTime = System.currentTimeMillis();
        return measurTime;
    }
    
    /** 
     * обнуляет поле value
     */
    public void setToZero(){
        value = 0;
        measurTime = System.currentTimeMillis();
    }
    
    /**
     * Добавляет указанное значение к value, не меняя время обновления
     * @param n - величина, добавляемая к value
     * @return - новое value
     */
    public long addToValue(long n){
        value += n;
        return value;
    }
}
