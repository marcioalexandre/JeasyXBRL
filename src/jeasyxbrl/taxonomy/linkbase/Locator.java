/**
 This software is subject to the terms of the GNU GENERAL PUBLIC LICENSE
 Agreement, available at the following URL:
 http://www.gnu.org/licenses/gpl.txt.
 You must accept the terms of that agreement to use this software.

 Copyright (C) 2015 Marcio Alexandre Pereira da Silva
 All Rights Reserved.
 * @author Marcio Alexandre
 * @email marcio.alexandre83@gmail.com
 * @since 2015-09-19, update 2015-10-03
 * @version beta0.0
 */
package jeasyxbrl.taxonomy.linkbase;

import jeasyxbrl.global.XbrlTaxonomyRe;
import jeasyxbrl.taxonomy.instance.XbrlElement;
import jeasyxbrl.conn.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Locator {
	private	int	   position	=	0;
	private String xmlLine	=	null;
	private String type		=	"locator";
	private String href		=	null;
	private String label	=	null;
	
	
	//getters and setters
	public String getXml_line() {
		return xmlLine;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<Locator> getLocatorsFromFile(String file) throws IOException{
		Connection conn = new Connection();
		BufferedReader br = conn.getConnection(file);
		String currentLine;
		ArrayList<Locator> locList = new ArrayList<Locator>();
		XbrlTaxonomyRe re = new XbrlTaxonomyRe();
		int j=0; //position
		while ((currentLine = br.readLine()) != null){
			Locator loc = new Locator();
			currentLine = currentLine.trim();
			
			//getting locator
			Pattern ploc = Pattern.compile(re.getLoc());
			Matcher mloc = ploc.matcher(currentLine);
			if (mloc.find()){
				//getting position
				loc.position =j;
				//getting XML line
				loc.xmlLine = mloc.group();
				//System.out.println();
				//System.out.println(this.xmlLine);
				
				//get href
				Pattern phref = Pattern.compile(re.getHref());
				Matcher mhref = phref.matcher(loc.xmlLine);
				if (mhref.find()){
					String[] href = mhref.group().split("\\s");
					href[0] = href[0].replace("\"", "");
					href[0] = href[0].replace("\'", "");
					loc.href = href[0];
					//System.out.println("href: ["+this.href+"]");
				}
					
				//get label
				Pattern plabel = Pattern.compile(re.getLabelAttr());
				Matcher mlabel = plabel.matcher(loc.xmlLine);
				if (mlabel.find()){
					String[] label = mlabel.group().split("\\s");
					label[0] 	= label[0].replace("\"", "");
					label[0] 	= label[0].replace("\'", "");
					loc.label 	= label[0];
					//System.out.println("label: ["+this.label+"]");
				}
				j++;
				locList.add(loc);
			}

		}
		return locList;
	}
	
	public Locator getLocatorByElement(ArrayList<Locator> locs, XbrlElement ele){
		Locator l = new Locator();
		int i=0;
		try{
			searchLoc:	{
				for (Locator loc: locs){
					i++;
					if (loc.href.contains("_"+ele.getName())){
						l = loc;
						break searchLoc;
					}
				}
			}
		}catch(Exception e){
			System.out.println("Exception [jeasyxbrl].[linkbase].[Locator.java]: "+e.getMessage());
		}
		return l;
	}
	
	/*
	 * The Locator position is the same the Arc position inside List.
	 */
	public Arc fromLocatorGetArcByPosition(ArrayList<Locator> locs, ArrayList<Arc> arcs ,XbrlElement ele){
		Locator l 	= new Locator();
		Arc a		= new Arc();
		int i=0;
		try{
			searchLoc:	{
				for (Locator loc: locs){
					if (loc.href.contains("_"+ele.getName())){
						l = loc;
						a = arcs.get(i);
						break searchLoc;
					}
					i++;
				}
			}
		}catch(Exception e){
			System.out.println("Exception [jeasyxbrl].[linkbase].[Locator.java]: "+e.getLocalizedMessage());
		}
		return a;
	}

	
	/*
	 * Locator tester
	 * 
	public static void main(String[] args) throws IOException{
		//String file = "../../../../../xbrlfiles/apple/aapl-20150627_lab.xml";
		//String file = "../../../../../xbrlfiles/msft/msft-20131231_lab.xml";
		//String file = "../../../../../xbrlfiles/ifrs/lab_IFRSF_2014-05-22.xml";
		//String file = "../../../../../xbrlfiles/bobs/bobs-20120930_lab.xml";
		//String file = "../../../../../xbrlfiles/oxfo/oxfo-20130930_lab.xml";
		String file = "../../../../../xbrlfiles/fb/fb-20131231_lab.xml";
		Locator loc = new Locator();
		ArrayList<Locator> locList = loc.getLocatorsFromFile(file);
		for (Locator loc1: locList){
			//System.out.println(loc1.getHref());
			if (loc1.href.contains("_CapitalLeasesLesseeBalanceSheetAssetsByMajorClassAccumulatedDeprecation")){
				System.out.println(loc1.getHref());
			}
		}
	}
	*/
	
	
}
