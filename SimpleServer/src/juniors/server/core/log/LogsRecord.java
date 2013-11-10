package juniors.server.core.log;

import java.util.Date;

public class LogsRecord {

    public static enum Type {

	INFO(java.util.logging.Level.INFO), WARNING(
		java.util.logging.Level.WARNING), 
		SEVERE(java.util.logging.Level.SEVERE),
		CONFIG(java.util.logging.Level.CONFIG),
		FINE(java.util.logging.Level.FINE),
		FINES(java.util.logging.Level.FINEST),
		FINER(java.util.logging.Level.FINER),
		ALL(java.util.logging.Level.ALL);

	private java.util.logging.Level levelJavaLogging;

	private Type(java.util.logging.Level level) {
	    levelJavaLogging = level;
	}

	private java.util.logging.Level getLevelJavaLogger() {
	    return levelJavaLogging;
	}

	private static Type valueOf(java.util.logging.Level javaLoggingLevel) {
	    for (Type type : Type.values())
		if (type.getLevelJavaLogger().equals(javaLoggingLevel))
		    return type;
	    return ALL;
	}
    };

    private String message;
    private long date;
    private Type type;

    public LogsRecord(String message, long date, Type type) {
	this.date = date;
	this.type = type;
    }

    public Type getLevel() {
	return type;
    }

    public String getMessage() {
	return message;
    }

    public Date getDate() {
	return new Date(date);
    }

    public static LogsRecord valueOf(java.util.logging.LogRecord logRecord) {
	return new LogsRecord(logRecord.getMessage(),
		logRecord.getMillis(), Type.valueOf(logRecord.getLevel()));
    }
}
