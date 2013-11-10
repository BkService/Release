package juniors.server.core.data;

import juniors.server.core.data.events.Event;

public class DataManager {
	private static volatile Data instance;
   
    public DataManager(){
    	
    }   
    
    public static Data getInstance() {
        Data localInstance = instance;
        if(localInstance == null) {
              synchronized(Data.class) {
                  instance = localInstance = new Data();                       
              }
        }
        return localInstance;
   }
    
    /**
     * для тестов
     * @param args
     */
    public static void main(String [] args){
    	DataManager.getInstance().addEvent(new Event(2, System.currentTimeMillis(), "dvdfvd"));
    	String s = DataManager.getInstance().getEvent(2).getDescription();
    }
}
