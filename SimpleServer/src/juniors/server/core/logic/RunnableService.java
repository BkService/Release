package juniors.server.core.logic;

import java.util.concurrent.TimeUnit;

public interface RunnableService {
    
    void start();
    
    void stop();
    
    boolean isStarted();
    
    long getDelay();
    
    TimeUnit getTimeUnitDelay();
}
