package juniors.server.core.data;

public class DataManager {
	private static volatile Data instance;
   
    private DataManager(){
    	
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
}
