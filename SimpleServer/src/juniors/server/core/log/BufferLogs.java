package juniors.server.core.log;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class BufferLogs extends Handler /* implements RunnableService */{

	// private static final BufferLogs instance;

	// public static final String ENCODING = "UTF-8";

	/*
	 * public static final TimeUnit TIME_UNIT = TimeUnit.MINUTES; public static
	 * final int DELAY = 30;
	 */

	// private boolean started = false;
	//
	// private ScheduledExecutorService executor;

	/*
	 * static { instance = new BufferLogs(); }
	 */
	private Deque<LogsRecord> buffer;
	private int size;

	// private String path;

	public BufferLogs(int size) {
		// executor = Executors.newSingleThreadScheduledExecutor();
		buffer = new ConcurrentLinkedDeque<LogsRecord>();
		this.size = size;
		// path = DEFAULT_PATH;
	}

	/*
	 * public static BufferLogs getInstance() { return instance; }
	 */

	@Override
	public void publish(LogRecord record) {
		if (buffer.size() > size)
			buffer.pop();
		buffer.push(LogsRecord.valueOf(record));
	}

	@Override
	public void flush() {
		buffer.clear();
	}

	@Override
	public void close() throws SecurityException {
		buffer.clear();
	}

	public LogsRecord[] getLastRecords(int count) {
		int size = (buffer.size() < count) ? buffer.size() : count;
		LogsRecord[] result = new LogsRecord[size];
		if (size == 0)
			return result;
		Iterator<LogsRecord> iter = buffer.iterator();
		for (int i = 0; i < size; i++)
			result[i] = iter.next();
		return result;
	}

	public int countsRecords() {
		return buffer.size();
	}

	public void setMaxSize(int size) {
		if (size > 0)
			this.size = size;
	}

	public int getMaxSize() {
		return size;
	}

	/*
	 * public void setPath(String path) { this.path = path; }
	 */

	/*
	 * private class Task implements Runnable {
	 * 
	 * @Override public void run() { try { PrintWriter writer = new
	 * PrintWriter(new BufferedWriter( new OutputStreamWriter( new
	 * FileOutputStream(path, false), ENCODING)));
	 * writer.write(HtmlFormatter.getHTMLHead()); writer.flush();
	 * while(buffer.size() != 0) {
	 * writer.println(HtmlFormatter.formatRecord(buffer.poll()));
	 * writer.flush(); } writer.write(HtmlFormatter.getHTMLTail());
	 * writer.flush(); writer.close(); } catch (UnsupportedEncodingException |
	 * FileNotFoundException e) { System.out.println(e.getMessage()); }
	 * 
	 * } }
	 * 
	 * @Override public void start() { if (!started) { executor.schedule(new
	 * Task(), DELAY, TIME_UNIT); started = true; } }
	 * 
	 * @Override public void stop() { if(started) { executor.shutdown(); started
	 * = false; }
	 * 
	 * }
	 * 
	 * @Override public boolean isStarted() { return started; }
	 * 
	 * @Override public long getDelay() { return DELAY; }
	 * 
	 * @Override public TimeUnit getTimeUnitDelay() { return TIME_UNIT; }
	 */
}
