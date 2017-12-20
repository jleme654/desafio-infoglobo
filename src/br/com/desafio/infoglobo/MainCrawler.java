package br.com.desafio.infoglobo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;

import br.com.desafio.infoglobo.model.Feed;
import br.com.desafio.infoglobo.model.Item;

public class MainCrawler {

	private static Feed feedObject;
	private static ArrayList<Item> itens;

	public MainCrawler() {	}

	private static Document parseXML(InputStream stream) throws Exception {
		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		try {
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

			doc = objDocumentBuilder.parse(stream);
		} catch (Exception ex) {
			throw ex;
		}

		return doc;
	}

	public static Feed leWebservice() throws Exception {
		feedObject = new Feed();
		itens = new ArrayList<>();

		try {
			URL url = new URL("http://revistaautoesporte.globo.com/rss/ultimas/feed.xml");

			URLConnection connection = url.openConnection();

			Document doc = parseXML(connection.getInputStream());
	
			NodeList descNodes = doc.getElementsByTagName("title");
			NodeList descNodes2 = doc.getElementsByTagName("link");
			
			for (int i = 0; i < descNodes.getLength(); i++) {
				Item item = new Item();	
				//System.out.println(descNodes.item(i).getTextContent());
				//System.out.println("-------------------");
				for (int j = 0; j < descNodes2.getLength(); j++) {
					item.setTitle(descNodes.item(i).getTextContent());
					item.setLink(descNodes2.item(j).getTextContent());
					itens.add(item);
				}
			}

			if (null != itens && !itens.isEmpty())
				feedObject.setItens(itens);

		} catch (MalformedURLException | DocumentException e) {
			e.printStackTrace();
		}
		return feedObject;
	}
	
	public static void convertToJSON(Feed feed) {
		Gson gson = new Gson();
	 
		// converte objetos Java para JSON e retorna JSON como String
		String json = gson.toJson(feed);
	 
		try {
			//Escreve Json convertido em arquivo chamado "infoglobo.json"
			FileWriter writer = new FileWriter("C://arqjson/infoglobo.json");
			writer.write(json);
			writer.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}	 
		System.out.println(json);
	 
	}

	public static void main(String[] args) {
		try {
			Feed feed = leWebservice();
			convertToJSON(feed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
