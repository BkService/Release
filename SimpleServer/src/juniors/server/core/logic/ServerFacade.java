package juniors.server.core.logic;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import juniors.server.core.feed.FeedLoader;
import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;
import juniors.server.core.logic.services.Services;

/**
 * 
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 */
public class ServerFacade {

    private boolean started = false;

    private static final ServerFacade instance;
    static {
	instance = new ServerFacade();
    }

    private static final Logger log = Logs.getInstance().getLogger(
	    ServerFacade.class.getSimpleName());

    private HashMap<Integer, RunnableService> runnableServices;

    private FeedLoader feedLoader;

    // FIXME добавить интерфейс RunnableService
    // в службу и удалить поле

    public static final int COUNT_SERVICES;

    public static final Integer ID_SERVICE_FEEDLOADER, ID_SERVICE_STATISTIC;
    static {
	COUNT_SERVICES = 2;

	ID_SERVICE_FEEDLOADER = 1;
	ID_SERVICE_STATISTIC = 2;
    }

    private static AtomicInteger countRequests;
    static {
	countRequests = new AtomicInteger(0);
    }

    public static ServerFacade getInstance() {
	return instance;
    }

    private ServerFacade() {
	feedLoader = new FeedLoader();
	runnableServices = new HashMap<Integer, RunnableService>(COUNT_SERVICES);
	runnableServices.put(ID_SERVICE_STATISTIC, (RunnableService) Services
		.getInstance().getStatisticService());
	runnableServices.put(ID_SERVICE_FEEDLOADER, feedLoader);
    }

    private void runAllServices() {
	
	feedLoader.start();
	for (RunnableService service : runnableServices.values()) {
	    if (!service.isStarted()) {
		service.start();
	    }
	}
    }

    private void stopAllServices() {
	feedLoader.stop();
    }

    public synchronized void start() {
	if (!started) {
	    runAllServices();
	    started = true;
	    log.info("Server start");
	}
    }

    /**
     * @see getStatusService();
     * @return status thread feed loader
     */
    @Deprecated
    public boolean getStatusFL() {
	return feedLoader.isStarted();
    }

    public boolean runService(Integer id) {
	if (started) {
	    RunnableService service = runnableServices.get(id);
	    if (!service.isStarted())
		return false;
	    service.start();
	}
	return false;
    }

    public boolean stopService(Integer id) {
	if (started) {
	    RunnableService service = runnableServices.get(id);
	    if (service.isStarted())
		return false;
	    service.start();
	}
	return false;
    }

    /**
     * Получение статуса службы
     * 
     * @param id
     * @return если службы запущена - возвращает true
     */
    public boolean getStatusService(Integer id) {
	RunnableService service = runnableServices.get(id);
	return (started == false) || (service == null) ? false : service
		.isStarted();
    }

    public boolean getStatusServer() {
	return started;
    }

    public void stop(Integer id) {
	RunnableService service = runnableServices.get(id);
	if (service != null)
	    service.stop();
    }

    public synchronized void stop() {
	stopAllServices();
	started = false;
	log.info("Server stop");
    }

    public Services getServices() {
	Services services = null;
	if (started) {
	    services = Services.getInstance();
	    countRequests.incrementAndGet();
	}
	return services;
    }

    public static int getCountRequest() {
	return countRequests.get();
    }

    public static void resetCountRequest() {
	countRequests.set(0);
    }
}
