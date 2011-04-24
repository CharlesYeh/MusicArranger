package logic;

import music.Piece;

import java.io.*;
import java.net.URL;
import org.dom4j.*;
import org.dom4j.io.*;

public class XMLParser {
	SAXReader _reader;
	public XMLParser() {
		_reader = new SAXReader();
	}
	
	public Piece parse(String fileName) throws Exception {
		Document doc = _reader.read(new FileReader(fileName));
		
		Element root = doc.getRootElement();
		
		// iterate through staffs
		Iterator<Element> i = root.elementIterator();
		//NodeList nodeList = doc.getElementsByTagName("
		while(i.hasNext()){
			Element currElement = i.next();
			
			SolarBody obj;
			String elementType = currElement.attribute("TYPE").getStringValue();
			
		}
	}
}