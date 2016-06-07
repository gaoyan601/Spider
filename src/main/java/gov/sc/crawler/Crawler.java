package gov.sc.crawler;

import gov.sc.spider.GooglePlus;
import gov.sc.spider.Spider;
import gov.sc.spider.Twitter;

public class Crawler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Spider spider = new Twitter();
		spider.run();
	}
}