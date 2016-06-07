package gov.sc.spider;

import gov.sc.config.Config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;

public class Facebook extends Spider {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Facebook.class);

	/**
	 * 
	 */
	public Facebook() {
		super();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			HtmlPage loginPage = webClient
					.getPage("https://en-gb.facebook.com/login/");
			List<HtmlForm> forms = loginPage.getForms();
			HtmlForm form = forms.get(0);
			HtmlInput userName = form.getInputByName("email");
			userName.setValueAttribute("spider.scdx@gmail.com");
			HtmlPasswordInput passwd = form.getInputByName("pass");
			passwd.setValueAttribute("scdx123123");
			Thread.sleep(20000);
			HtmlButton loginButton = form.getButtonByName("login");
			HtmlPage homePage = loginButton.click();
			synchronized (homePage) {
				homePage.wait(5000);
			}
			String content = getContent(homePage.asXml());
			writeToFile(Config.absPath+"Facebook.txt", content);
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
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		}

	}

}
