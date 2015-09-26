/**
 This file is part of jeasyXBRL.

jeasyXBRL is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

jeasyXBRL is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with jeasyXBRL.  If not, see <http://www.gnu.org/licenses/>.

 Copyright (C) 2015 Marcio Alexandre Pereira da Silva
 All Rights Reserved.
 * @author Marcio Alexandre
 * @email marcio.alexandre83@gmail.com
 * @since 2015-09-18
 * @version beta0.0
 */

package jeasyxbrl.instance;
import jeasyxbrl.global.XbrlTaxonomyRe;

import java.sql.Time;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import jeasyxbrl.linkbase.Arc;
import jeasyxbrl.linkbase.Label;
import jeasyxbrl.linkbase.Locator;
import jeasyxbrl.conn.Connection;


public class XbrlElement{
	private	int	   position			=	0;
	private String xmlLine			=	null;
	private String name 			=	null;
	private String decimals			=	null;
	private String id				=	null;
	private String contextRef		=	null;
	private String unitRef			=	null;
	private String value			=	null;
	private String company			=	null;
	private ArrayList<Label> labels	=	null;
	private String fileName			=	null;
	private String tagName			= 	null;
	
	
	// getters and Setters
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getXmlLine() {
		return xmlLine;
	}
	public void setXmlLine(String xmlLine) {
		this.xmlLine = xmlLine;
	}
	public String getContextRef() {
		return contextRef;
	}
	public void setContextRef(String contextRef) {
		this.contextRef = contextRef;
	}
	public String getUnitRef() {
		return unitRef;
	}
	public void setUnitRef(String unitRef) {
		this.unitRef = unitRef;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public void setXml_line(String xml_line) {
		this.xmlLine = xml_line;
	}	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDecimals() {
		return decimals;
	}
	public void setDecimals(String decimals) {
		this.decimals = decimals;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContext_ref() {
		return contextRef;
	}
	public void setContext_ref(String context_ref) {
		this.contextRef = context_ref;
	}
	public String getUnit_ref() {
		return unitRef;
	}
	public void setUnit_ref(String unit_ref) {
		this.unitRef = unit_ref;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public ArrayList<Label> getLabels() {
		return labels;
	}
	public void setLabel(ArrayList<Label> labels) {
		this.labels = labels;
	}
	public String getFile_name() {
		return fileName;
	}
	public void setFile_name(String file_name) {
		this.fileName = file_name;
	}
	public String getTag_name() {
		return tagName;
	}
	public void setTag_name(String tag_name) {
		this.tagName = tag_name;
	}
	public void setLabels(ArrayList<Label> labels) {
		this.labels = labels;
	}
	
	/*
	 * marcio.alexandre83@gmail.com Sep19th, 2015.
	 * @param instances		"A list with path/name of XBRL instance(s) [1-n]"
	 * linkbases:
	 * @param labels		"A list with path/name of XBRL label(s) [0-n]"
	 * @param calculations	"A list with path/name of XBRL calculation(s) [0-n]"
	 * @param references	"A list with path/name of XBRL reference(s) [0-n]"
	 * @param presentations	"A list with path/name of XBRL Presentation(s) [0-n]"
	 * @param definitions	"A list with path/name of XBRL definition(s) [0-n]"
	 * @param others		"A list with path/name of others XBRL linkbase(s), as geographic, formula, ... [0-n]"
	 * @param trigger		"it triggers the processing of the XBRL linkbases [0 (no) or 1 (yes)]"
	 */
	public ArrayList<ArrayList<XbrlElement>> getAllElementsByFiles(
			ArrayList<String> instances, 
			ArrayList<String> labels, 
			ArrayList<String> calculations, 
			ArrayList<String> references,
			ArrayList<String> presentations,
			ArrayList<String> definitions,
			ArrayList<String> others,
			int trigger) throws Throwable{
		ArrayList<ArrayList<XbrlElement>> eleListFromFiles = new ArrayList<ArrayList<XbrlElement>>();
		
		//each instances
		for(String i: instances){
			//System.out.println("Arquivo: ["+i+"]");
			Connection 		conn 	= new Connection();
			XbrlTaxonomyRe 	re 		= new XbrlTaxonomyRe(); 
			BufferedReader br = null;
			String flag = null;
			ArrayList<XbrlElement> eleList = new ArrayList<XbrlElement>();
			String currentLine;
			br 	= conn.getConnection(i);
			int j=0;
			
			//loading Locator, Arc and Label by sent linkbase.
			Locator lo	= new Locator();
			Arc		ar	= new Arc();
			Label 	la	=	new Label();
			ArrayList<Locator> 	locList = new ArrayList<Locator>();
			ArrayList<Arc>		arcList = new ArrayList<Arc>();
			ArrayList<Label>	labelList = new ArrayList<Label>();
			//gettin locator, arc,labels if trigger is ok.
			if (trigger == 1){ 
				locList 	= lo.getLocatorsFromFile(labels.get(j));;
				arcList		= ar.getArcsFromFile(labels.get(j));
				labelList	= la.getLabelsFromFile(labels.get(j));
			}
			
			while ((currentLine	= br.readLine()) != null){
				currentLine = currentLine.trim();
				XbrlElement ele = new XbrlElement();
				Pattern p = Pattern.compile(re.getElementLine());
				Matcher m = p.matcher(currentLine);
				if (m.find()){
					ele.position 	= j;
					ele.xmlLine		= m.group();
					ele.fileName 	= i;
					
					//get element tag name
					Pattern ptag = Pattern.compile(re.getElementTagName());
					Matcher mtag = ptag.matcher(ele.xmlLine);
					if (mtag.find()){
						//System.out.println("");
						//System.out.println(m.group());
						String tagname 	= mtag.group().replace("<"," ").trim();
						String[] tname	= tagname.split("\\s");
						//System.out.println("Tag Name: ["+tname[0]+"]");
						ele.tagName = tname[0].trim();
					}else{	}
					
					//get element name
					Pattern pname = Pattern.compile(re.getElementName());
					Matcher mname = pname.matcher(ele.xmlLine);
					if (mname.find()){
						//System.out.println("Name: ["+mname.group().trim()+"]");
						ele.name = mname.group().trim();
					}else{	}
					
					// get element id
					Pattern pid = Pattern.compile(re.getId());
					Matcher mid = pid.matcher(ele.xmlLine);
					if (mid.find()){
						String[] rid = mid.group().split("\\s");
						rid[0] = rid[0].replace("\"","");
						rid[0] = rid[0].replace("\'","");
						rid[0] = rid[0].replace(">&lt;div",""); 
						//System.out.println("Id: ["+rid[0].trim()+"]");
						ele.id = rid[0].trim();
					}else{	}
					
					//get Element Context_Ref
					Pattern pcr = Pattern.compile(re.getElementCRef());
					Matcher mcr = pcr.matcher(ele.xmlLine);
					if (mcr.find()){
						String[] rcr = mcr.group().split("\\s");
						rcr[0] = rcr[0].replace("\"","");
						rcr[0] = rcr[0].replace("\'","");
						//System.out.println("ContextRef: ["+rcr[0]+"]");
						ele.contextRef = rcr[0].trim();
					}else{  }
					
					//get Element Unit_Ref
					Pattern pur	= Pattern.compile(re.getElementURef());
					Matcher mur	= pur.matcher(ele.xmlLine);
					if (mur.find()){
						String[] rur	=	mur.group().split("\\s"); 
						rur[0]	= rur[0].replace("\"","");
						rur[0]	= rur[0].replace("\'","");
						//System.out.println("UnitRef: ["+rur[0]+"]");
						ele.unitRef = rur[0].trim();
					}
					
					//get Element Decimals
					Pattern pd	= Pattern.compile(re.getElementDecimals());
					Matcher md	= pd.matcher(ele.xmlLine);
					if (md.find()){
						String[] rd = md.group().split("\\s");
						rd[0] = rd[0].replace("\"","");
						rd[0] = rd[0].replace("\'","");
						//System.out.println("Decimals: ["+rd[0]+"]");
						ele.decimals = rd[0].trim();
					}
						//get Element Value
					Pattern pv	= Pattern.compile(re.getValue()+ele.tagName+")");
					Matcher mv	= pv.matcher(ele.xmlLine);
					if (mv.find()){
						//System.out.println("Value: ["+mv.group()+"]");
						ele.value = mv.group().trim();
					}
					
					//get Company
					if (ele.tagName.equals("dei:EntityRegistrantName") || ele.name.equals("EntityRegistrantName")){
						flag = ele.value.trim();
					}else{	}
						
					//getting labels
					if (trigger == 1){
						ele.labels	= la.getLabelsByArc(labelList,lo.fromLocatorGetArcByPosition(locList, arcList, ele));
					}//trigger end]
				eleList.add(ele);
				j++;
				} //m.matches()
					
			} //currentLine end
			XbrlElement eleTemp = new XbrlElement();
			if (!flag.equals(null)){
				eleTemp.setCompanyInElements(eleList, flag);
			}
			eleListFromFiles.add(eleList);
			locList = null;
			arcList = null;
			labelList = null;
		} // for instance
		return eleListFromFiles;
	}
	
	public ArrayList<XbrlElement> setCompanyInElements(ArrayList<XbrlElement> eleList,String flag){
		ArrayList<XbrlElement> eleTemp = new ArrayList<XbrlElement>(); 
		for (XbrlElement ele: eleList){
			ele.company = flag;
			eleTemp.add(ele);
		}
		return eleTemp;
	}
	
	/*
	 * This method is for useing XML DOM  or Sax technologies, 
	 * but at first moment, it shows a very slow performance, 
	 * that's whay I choose the another way
	 * which you can see in the jeasyXBRL methods. 
	 * (Marcio Alexandre - Sep19th,2015)
	 * 
	 * @param filename "just the name of the XML file" 
	 
	public void getElementsDOMSAX(String filename){
		Connection conn = new Connection();
		Document doc = (Document) conn.getConnectionXML(filename);
		NodeList nodes = doc.getElementsByTagName(this.tagName);
		//NodeList node = nodes.item(0);
		Node node = nodes.item(0);
		//System.out.println("Name: ["+this.name+"] | Value: ["+node.getChildNodes().item(0).getNodeValue()+"]");						
		for (int z=0; z < node.getAttributes().getLength(); z++){
			// get every Node attributes and its values
			//System.out.println("Attr: ["+node.getAttributes().item(z).getNodeName()+"] | Value: ["+node.getAttributes().item(z).getNodeValue()+"].");
		}
		
		// attributes adHoc
		// Found problem: if there is no "id", "contextref", "unitref", "decimals" (...) in the node, it creates a runtime error.
		NamedNodeMap nnl = nodes.item(0).getAttributes();
		System.out.println("Valor: ["+nodes.item(0).getChildNodes().item(0).getNodeValue()+"]");
		System.out.println("Id: ["+nnl.getNamedItem("id").getNodeValue()+"]");
		System.out.println("ContextRef: ["+nnl.getNamedItem("contextRef").getNodeValue()+"]");
		System.out.println("UnitRef: ["+nnl.getNamedItem("unitRef").getNodeValue()+"]");
		System.out.println("Decimals: ["+nnl.getNamedItem("decimals").getNodeValue()+"]");
		//System.out.println(nnl.getNamedItem("name").getNodeValue());
		
	}
	*/
	
}
