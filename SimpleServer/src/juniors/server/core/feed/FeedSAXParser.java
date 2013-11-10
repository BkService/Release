package juniors.server.core.feed;
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
	  System.out.println("Start parse XML..."); 
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		  curQName = qName;
		  if (qName.equals("m")) {
			  int id = Integer.parseInt(atts.getValue("id"));
			  String name = atts.getValue("n"); 
			  curEvent = new Event(id, 0, name);
		  }
		  if (qName.equals("t")) {
			  int id = Integer.parseInt(atts.getValue("id"));
			  String name = atts.getValue("n");
			  curMarket = new Market(id, name);
		  }
		  if (qName.equals("l")) {
			  int id = Integer.parseInt(atts.getValue("id"));
			  Double coefficent = Double.parseDouble(atts.getValue("o"));
			  curOutcome = new Outcome(id, coefficent);
		  }
	} 

	@Override 
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException { 
		if (qName.equals("m")) {
			DataManager.getInstance().addEvent(curEvent);
			System.out.println(curEvent);
		}
		if (qName.equals("t")) {
			curEvent.addMarket(curMarket);
		}
		if (qName.equals("l")) {
			curMarket.addOutcome(curOutcome);
		}
	} 
	public void characters(char[] ch, int start, int length) throws SAXException { 
		if (curQName.equals("l")) {
			curOutcome.setDescription(new String(ch, start, length));
		}
	}
	@Override 
	public void endDocument() { 
	  System.out.println("Stop parse XML..."); 
	}
}
