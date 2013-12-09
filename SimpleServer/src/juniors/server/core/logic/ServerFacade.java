package juniors.server.core.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import juniors.server.core.feed.FeedLoader;
import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;
import juniors.server.core.logic.services.Services;
import juniors.server.core.resultprovider.ResultProvider;

/**
 * 
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 */
public class ServerFacade {

	public static enum TypeRunService {
		SERVICE_FEEDLOADER, SERVICE_STATISTIC, RESULT_PROVIDER
	}

	private boolean started = false;

	private static final ServerFacade instance;
	static {
		instance = new ServerFacade();
	}

	private static final Logger log = Logs.getInstance().getLogger(
			ServerFacade.class.getSimpleName());

	private HashMap<TypeRunService, RunnableService> runnableServices;

	private static AtomicInteger countRequests;
	static {
		countRequests = new AtomicInteger(0);
	}

	public static ServerFacade getInstance() {
		return instance;
	}

	private ServerFacade() {
		runnableServices = new HashMap<TypeRunService, RunnableService>(
				TypeRunService.values().length);
		runnableServices.put(TypeRunService.SERVICE_STATISTIC, (RunnableService) Services
				.getInstance().getStatisticService());
		runnableServices.put(TypeRunService.SERVICE_FEEDLOADER, new FeedLoader());
		runnableServices.put(TypeRunService.RESULT_PROVIDER, new ResultProvider());
	}

	private void runAllServices() {
		for (RunnableService service : runnableServices.values()) {
			if (!service.isStarted()) {
				service.start();
			}
		}
	}

	private void stopAllServices() {
		for (RunnableService service : runnableServices.values()) {
			if (service.isStarted()) {
				service.stop();
			}
		}
	}
	
	Date date = null;

	public synchronized void start() {
		if (!started) {
			runAllServices();
			started = true;
			log.info("Server start");
			date = new Date();
		}
	}
	
	public Date getTime() {
		return this.date;
	}

	/**
	 * @see getStatusService();
	 * @return status thread feed loader
	 */
	@Deprecated
	public boolean getStatusFL() {
		return runnableServices.get(TypeRunService.SERVICE_FEEDLOADER).isStarted();
	}

	public boolean runService(TypeRunService typeService) {
		if (started) {
			RunnableService service = runnableServices.get(typeService);
			if (service.isStarted())
				return false;
			service.start();
			return true;
		}
		return false;
	}

	/**
	 * Остановка службы
	 * @param typeService
	 * @return
	 */
	public boolean stopService(TypeRunService typeService) {
		if (started) {
			RunnableService service = runnableServices.get(typeService);
			if (!service.isStarted())
				return false;
			service.stop();
			return true;
		}
		return false;
	}

	/**
	 * @param typeService
	 * @return
	 */
	public boolean getStatusService(TypeRunService typeService) {
		RunnableService service = runnableServices.get(typeService);
		return (started == false) || (service == null) ? false : service
				.isStarted();
	}

	public boolean getStatusServer() {
		return started;
	}

	public void stop(TypeRunService typeService) {
		RunnableService service = runnableServices.get(typeService);
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
