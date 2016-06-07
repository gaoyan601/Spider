package gov.sc.spider;

import gov.sc.config.Config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class GooglePlus extends Spider {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GooglePlus.class);

	/**
	 * 
	 */
	public GooglePlus() {
		super();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			HtmlPage loginPage = webClient
					.getPage("https://accounts.google.com/login");
			List<HtmlForm> emailForms = loginPage.getForms();
			HtmlForm emailForm = emailForms.get(0);
			HtmlInput userName = emailForm.getInputByName("Email");
			userName.setValueAttribute("scdx.spider@gmail.com");
			HtmlSubmitInput next = emailForm.getInputByName("signIn");
			HtmlPage passwdPage = next.click();
			synchronized (passwdPage) {
				passwdPage.wait(3000);
			}
			List<HtmlForm> passwdForms = passwdPage.getForms();
			HtmlForm passwdForm = passwdForms.get(0);
			HtmlPasswordInput passwd = passwdForm.getInputByName("Passwd");
			passwd.setValueAttribute("scdx123123");
			Thread.sleep(5000);
			HtmlSubmitInput loginButton =passwdForm.getInputByName("signIn");
			HtmlPage homePage = loginButton.click();
			homePage  = webClient.getPage("https://plus.google.com/");
			synchronized (homePage) {
				homePage.wait(5000);
			}
			String content = getContent(homePage.asXml());
			writeToFile(Config.absPath+"GooglePlus.txt", content);
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
