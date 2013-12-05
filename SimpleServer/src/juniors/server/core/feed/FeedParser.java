package juniors.server.core.feed;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import juniors.server.core.log.Logger;

import org.xml.sax.SAXException;

/**
 * Helper to work with SAX parsers.
 * @author watson
 *
 */
public class FeedParser {
	/**
	 * SAX parser. 
	 */
	SAXParser parser;
	
	/**
	 * Parser, which specified rules of analysis data from provider.
	 */
	FeedSAXParser feedSAXParser;
	
	Logger logger;
	
	/**
	 * Constructs SAXParser and parser for provider.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public FeedParser() throws ParserConfigurationException, SAXException  {		
		parser = SAXParserFactory.newInstance().newSAXParser(); 
		feedSAXParser = new FeedSAXParser();
	}
	
	
	/**
	 * Parse XML which is in InputStream.
	 * This method writes all data to server data directly.
	 * @param is - inputStream with data.
	 */
	public void parse(InputStream is)   {
		try {
			parser.parse(is, feedSAXParser);
		} catch (SAXException e) {
			logger.warning("Error, while parsing xml file, cause:" + e.getMessage());
		} catch (IOException e) {
			logger.warning("Problem while getting xml file from server, cause:" + e.getMessage());
		}
	}
}
