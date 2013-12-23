package juniors.server.core.feed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;
import juniors.server.core.log.Logger;
import juniors.server.core.log.Logs;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Parser for XML data from provider.
 * (Also see documentation of feed provider http://xmlfeed.intertops.com/xmloddsfeed/ ).
 * @author watson
 *
 */
public class FeedSAXParser extends DefaultHandler {
	/**
	 * Last event that was parsed(To this event we add markets).
	 */
	Event curEvent;
	
	/**
	 * Last market that was parsed(To this market we add outcomes).
	 */
	Market curMarket;
	
	/**
	 * Last outcome that was parsed.
	 */
	Outcome curOutcome;
	
	/**
	 * Name of last element.
	 */
	String curQName;

	Logger logger;

	
	public FeedSAXParser() {
		super();
		logger = Logs.getInstance().getLogger("feed");
	}
	
	@Override
	public void startDocument() throws SAXException {
		logger.info("Start parse XML...");
	}
	
	/**
	 * Runs when parser find start of some elements.
	 * We understand what element found, and add it to data.
	 **/
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		curQName = qName;
		if (qName.equals("m")) {
			int id = Integer.parseInt(atts.getValue("id"));
			long time = parseTime(atts.getValue("dt"));
			String name = atts.getValue("n");
			curEvent = new Event(id, time, name);
			if (DataManager.getInstance().containsEvent(id)) {
				curEvent = DataManager.getInstance().getEvent(id);
			} else {
				DataManager.getInstance().addEvent(curEvent);
			}
		}
		if (qName.equals("t")) {
			int id = Integer.parseInt(atts.getValue("id"));
			String name = atts.getValue("n");
			curMarket = new Market(id, name);
			if (DataManager.getInstance().getMarketsMap(curEvent.getEventId()).containsKey(id)) {
				curMarket = DataManager.getInstance().getMarketsMap(curEvent.getEventId()).get(id);
			} else {
				curEvent.addMarket(curMarket);
			}
		}
		if (qName.equals("l")) {
			int id = Integer.parseInt(atts.getValue("id"));
			Double coefficent = Double.parseDouble(atts.getValue("o"));
			curOutcome = new Outcome(id, coefficent);
		}
	}

	
	/**
	 * Runs when founded Symbolic data.
	 * It is necessary only for outcomes.s 
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (curQName.equals("l")) {
			curOutcome.setDescription(new String(ch, start, length));
		}
	}

	
	/**
	 * Runs when parser find end of some elements.
	 * It needs only for outcomes, because only outcomes contains description in symbol data. 
	 * 
	 **/
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (qName.equals("l")) {
			if (!DataManager.getInstance().containsOutcome(curOutcome.getOutcomeId())) {
				DataManager.getInstance().addOutcome(curOutcome,
						curEvent.getEventId(), curMarket.getMarketId());
			}
		}
	}
	
	@Override
	public void endDocument() {
		logger.info("Stop parse XML data.");
	}

	/**
	 * Parses time getting from provider(format is in specification of provider). 
	 * @param s - String representation of time from provider.
	 * @return -the number of milliseconds since January 1, 1970, 00:00:00 GMT (Standard time).
	 */
	public long parseTime(String s) {
		s = s + " +0000";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm Z");
		Date date = new Date(0);
		try {
			date = format.parse(s);
		} catch (ParseException e) {
			logger.warning("Cannot parse date, cause" + e.getMessage());
		}
		return date.getTime();
	}

}
