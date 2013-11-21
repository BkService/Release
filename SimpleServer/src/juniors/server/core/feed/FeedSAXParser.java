package juniors.server.core.feed;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import juniors.server.core.data.DataManager;
import juniors.server.core.data.events.Event;
import juniors.server.core.data.markets.Market;
import juniors.server.core.data.markets.Outcome;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FeedSAXParser extends DefaultHandler {
	
	Event curEvent;
	Market curMarket;
	Outcome curOutcome;
	String curQName;

	@Override 
	public void startDocument() throws SAXException { 
	  //System.out.println("Start parse XML..."); 
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		  curQName = qName;
		  if (qName.equals("m")) {
			  int id = Integer.parseInt(atts.getValue("id"));
			  long time = parseTime(atts.getValue("dt"));
			  String name = atts.getValue("n"); 
			  curEvent = new Event(id, time, name);
			  DataManager.getInstance().addEvent(curEvent);
		  }
		  if (qName.equals("t")) {
			  int id = Integer.parseInt(atts.getValue("id"));
			  String name = atts.getValue("n");
			  curMarket = new Market(id, name);
			  curEvent.addMarket(curMarket);
		  }
		  if (qName.equals("l")) {
			  int id = Integer.parseInt(atts.getValue("id"));
			  Double coefficent = Double.parseDouble(atts.getValue("o"));
			  curOutcome = new Outcome(id, coefficent);
		  }
	} 

	@Override 
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException { 
		if (qName.equals("l")) {
		    	DataManager.getInstance().addOutcome(curOutcome, curEvent.getEventId(), curMarket.getMarketId());
		    	//curMarket.addOutcome(curOutcome);
		}
	} 
	public void characters(char[] ch, int start, int length) throws SAXException { 
		if (curQName.equals("l")) {
			curOutcome.setDescription(new String(ch, start, length));
		}
	}
	@Override 
	public void endDocument() { 
	  //System.out.println("Stop parse XML..."); 
	}
	
	public long parseTime(String s) {
	    s = s + " +0000";
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm Z");
	    Date date = new Date(0);
	    try {
		date = format.parse(s);
	    } catch (ParseException e) {
		System.err.println("Cannot parse date, cause");
		e.printStackTrace();
	    }
	    return date.getTime();
	}
	
}
