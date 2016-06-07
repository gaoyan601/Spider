package gov.sc.spider;

import gov.sc.config.Config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;

public class Twitter extends Spider {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Twitter.class);

	/**
	 * 
	 */
	public Twitter() {
		super();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			HtmlPage loginPage = webClient.getPage("https://twitter.com/login");
			List<HtmlForm> forms = loginPage.getForms();
			HtmlForm form = forms.get(2);
			HtmlInput userName = form
					.getInputByName("session[username_or_email]");
			userName.setValueAttribute("scdx.spider@gmail.com");
			HtmlPasswordInput passwd = form.getInputByName("session[password]");
			passwd.setValueAttribute("scdx123123");
			Thread.sleep(20000);
			HtmlElement loginButton = form.getElementsByAttribute("button",
					"class", "submit btn primary-btn").get(0);
			HtmlPage homePage = loginButton.click();
			synchronized (homePage) {
				homePage.wait(5000);
			}
			homePage = webClient.getPage("https://twitter.com/");
			synchronized (homePage) {
				homePage.wait(5000);
			}
			String content = getContent(homePage.asXml());
			writeToFile(Config.absPath + "Twitter.txt", content);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		}

	}

}
