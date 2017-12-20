package br.com.desafio.infoglobo;


import java.net.URL;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MainCrawler {
	
	public static void leWebservice() {
		
		  URL url = new URL("http://revistaautoesporte.globo.com/rss/ultimas/feed.xml");

          Document document = getDocumento(url);

          Element root = document.getRootElement();
	}

}
