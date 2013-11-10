package juniors.server.core.log;

/**
 * Logger
 * @author Dmitrii Shakshin (trueCoder)<d.shakshin@gmail.com>
 *
 */

public class Logger {
    private java.util.logging.Logger logger;
    
    private Logger(java.util.logging.Logger logger) {
	this.logger = logger;
    }
    
    public void info(String msg) {
	logger.info(msg);
    }
    
    public void severe(String msg) {
	logger.severe(msg);
    }
    
    public void config(String msg) {
	logger.config(msg);
    }
    
    public void fine(String msg) {
	logger.fine(msg);
    }
    
    public void fines(String msg) {
	logger.finest(msg);
    }
    
    public void warning(String msg) {
	logger.warning(msg);
    }
   
    public static Logger valueOf(java.util.logging.Logger logger) {
	return new Logger(logger);
    }
}
