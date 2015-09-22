/**
 * @author Marcio Alexandre
 * @email marcio.alexandre83@gmail.com
 * @since 2015-09-19
 * @version beta0.0
 */

package jeasyxbrl.conn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Connection {
	
	/*
	 * @param filename "just the path and name of XML file"
	 */
	public BufferedReader getConnection(String filename){
		try {
			return new BufferedReader (new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.print("Error [jeasyxbrl].[conn].[Connection.java] "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Actually I'm not using this method, but it's still here for future coding, though
	 * unfortuntely, this method sets my library in CC (cyclomatic complexity) number 4.
	 * before it, the CC was 2.
	 * 
	 * @param filename "just the path and name of XML file"
	 */
	public Document getConnectionXML(String filename){
		Document doc = null;
		try {
			File inputFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = null;
			dBuilder = dbFactory.newDocumentBuilder();
			try {
				doc = dBuilder.parse(inputFile);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}finally{
			return doc;
		}
	}
	
}
