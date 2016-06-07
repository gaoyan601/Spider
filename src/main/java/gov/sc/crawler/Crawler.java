package gov.sc.crawler;

import gov.sc.spider.Facebook;
import gov.sc.spider.Spider;

public class Crawler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Spider spider = new Facebook();
		spider.run();
	}
}
