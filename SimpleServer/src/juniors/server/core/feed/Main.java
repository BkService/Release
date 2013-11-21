package juniors.server.core.feed;


public class Main {
	public static void main(String[] args)  {
	        System.out.println("start");
		FeedLoader fl = new FeedLoader();
		fl.start();
		/*
	    	FeedSAXParser parser = new FeedSAXParser();
	    	System.out.println(new Date().getTime());
	    	System.out.println(parser.parseTime("2013-11-10T19:26"));
	    	*/
	}
}
