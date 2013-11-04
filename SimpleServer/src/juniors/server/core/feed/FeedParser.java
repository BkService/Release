package juniors.server.core.feed;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


public class FeedParser {
	SAXParserFactory factory;
	SAXParser parser;
	FeedSAXParser feedSAXParser;
	
	public FeedParser() throws ParserConfigurationException, SAXException  {
		
		factory = SAXParserFactory.newInstance(); 
		parser = factory.newSAXParser(); 
		feedSAXParser = new FeedSAXParser();
	}
	
	public void parse(InputStream is)   {
		try {
			parser.parse(is, feedSAXParser);
		} catch (SAXException e) {
			System.err.println("Error, while parsing xml file:");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Problem while getting xml file from server:");
			e.printStackTrace();
		}
	}
}
