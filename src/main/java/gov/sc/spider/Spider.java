package gov.sc.spider;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public abstract class Spider {

	final WebClient webClient;

	/**
	 * @param webClient
	 */
	public Spider() {
		webClient = new WebClient(BrowserVersion.CHROME, "127.0.0.1", 1080);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setCssEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	}

	public abstract void run();

	public String getContent(String doc) throws IOException, SAXException,
			TikaException {
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		InputStream inputstream = new ByteArrayInputStream(
				doc.getBytes("UTF-8"));
		ParseContext pcontext = new ParseContext();
		HtmlParser parser = new HtmlParser();
		parser.parse(inputstream, handler, metadata, pcontext);
		return handler.toString();
	}
	
	public void writeToFile(String fileAbsPath,String content) throws IOException{
		File file = new File(fileAbsPath);
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	}
}
