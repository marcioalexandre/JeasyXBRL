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

 * @author 	Marcio Alexandre P. da Silva
 * @email 	marcio.alexandre83@gmail.com
 * @since 	2015-10-03
 */
package jeasyxbrl.taxonomy.instance;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jeasyxbrl.conn.Connection;
import jeasyxbrl.global.XbrlTaxonomyRe;
import jeasyxbrl.taxonomy.linkbase.Arc;
import jeasyxbrl.taxonomy.linkbase.XbrlLabel;
import jeasyxbrl.taxonomy.linkbase.Locator;

public class XbrlInstance {
	private int id = 0;
	private String filename = null;
	private String company = null;
	private ArrayList<XbrlElement> eleList = null;
	private long size = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public ArrayList<XbrlElement> getEleList() {
		return eleList;
	}
	public void setEleList(ArrayList<XbrlElement> eleList) {
		this.eleList = eleList;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
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
	public XbrlInstance getAllElementsByInstance(
			String instance, 
			String label, 
			String calculation, 
			String reference,
			String presentation,
			String definition,
			String other,
			int trigger,
			int position) throws Throwable{
		
			XbrlInstance inst = new XbrlInstance();
			inst.setId(position+1);
			ArrayList<XbrlElement> eleList = new ArrayList<XbrlElement>();
			Connection 		conn 	= new Connection();
			BufferedReader br = null;
			String currentLine;
			br 	= conn.getConnection(instance);
			
			//loading Locator, Arc and Label by sent linkbase.
			Locator lo	= new Locator();
			Arc		ar	= new Arc();
			XbrlLabel 	la	=	new XbrlLabel();
			ArrayList<Locator> 	locList = new ArrayList<Locator>();
			ArrayList<Arc>		arcList = new ArrayList<Arc>();
			ArrayList<XbrlLabel>	labelList = new ArrayList<XbrlLabel>();
			//gettin locator, arc,labels if trigger is ok.
			if (trigger == 1){ 
				locList 	= lo.getLocatorsFromFile(label);;
				arcList		= ar.getArcsFromFile(label);
				labelList	= (ArrayList<XbrlLabel>) la.getLinkbaseElementFromFile(label);
				//here we can set the xbrllinkbase.java
			}
			inst.setFilename(instance);
			XbrlTaxonomyRe 	re 		= new XbrlTaxonomyRe(); 
			int j=1;
			while ((currentLine	= br.readLine()) != null){
				currentLine = currentLine.trim();
				XbrlElement ele = new XbrlElement();
				Pattern p = Pattern.compile(re.getElementLine());
				Matcher m = p.matcher(currentLine);
				if (m.find()){
					
					ele.setPosition(j);
					ele.setXmlLine(m.group());
					inst.setFilename(instance);
					
					//get element tag name
					Pattern ptag = Pattern.compile(re.getElementTagName());
					Matcher mtag = ptag.matcher(ele.getXmlLine());
					if (mtag.find()){
						//System.out.println("");
						//System.out.println(m.group());
						String tagname 	= mtag.group().replace("<"," ").trim();
						String[] tname	= tagname.split("\\s");
						//System.out.println("Tag Name: ["+tname[0]+"]");
						ele.setTagName(tname[0].trim());
					}else{	}
					
					//get element name
					Pattern pname = Pattern.compile(re.getElementName());
					Matcher mname = pname.matcher(ele.getXmlLine());
					if (mname.find()){
						//System.out.println("Name: ["+mname.group().trim()+"]");
						ele.setName(mname.group().trim());
					}else{	}
					
					// get element id
					Pattern pid = Pattern.compile(re.getId());
					Matcher mid = pid.matcher(ele.getXmlLine());
					if (mid.find()){
						String[] rid = mid.group().split("\\s");
						rid[0] = rid[0].replace("\"","");
						rid[0] = rid[0].replace("\'","");
						rid[0] = rid[0].replace(">&lt;div",""); 
						//System.out.println("Id: ["+rid[0].trim()+"]");
						ele.setId(rid[0].trim());
					}else{	}
					
					//get Element Context_Ref
					Pattern pcr = Pattern.compile(re.getElementCRef());
					Matcher mcr = pcr.matcher(ele.getXmlLine());
					if (mcr.find()){
						String[] rcr = mcr.group().split("\\s");
						rcr[0] = rcr[0].replace("\"","");
						rcr[0] = rcr[0].replace("\'","");
						//System.out.println("ContextRef: ["+rcr[0]+"]");
						ele.setContextRef(rcr[0].trim());
					}else{  }
					
					//get Element Unit_Ref
					Pattern pur	= Pattern.compile(re.getElementURef());
					Matcher mur	= pur.matcher(ele.getXmlLine());
					if (mur.find()){
						String[] rur	=	mur.group().split("\\s"); 
						rur[0]	= rur[0].replace("\"","");
						rur[0]	= rur[0].replace("\'","");
						//System.out.println("UnitRef: ["+rur[0]+"]");
						ele.setUnitRef(rur[0].trim());
					}
					
					//get Element Decimals
					Pattern pd	= Pattern.compile(re.getElementDecimals());
					Matcher md	= pd.matcher(ele.getXmlLine());
					if (md.find()){
						String[] rd = md.group().split("\\s");
						rd[0] = rd[0].replace("\"","");
						rd[0] = rd[0].replace("\'","");
						//System.out.println("Decimals: ["+rd[0]+"]");
						ele.setDecimals(rd[0].trim());
					}
						//get Element Value
					Pattern pv	= Pattern.compile(re.getValue()+ele.getTagName()+")");
					Matcher mv	= pv.matcher(ele.getXmlLine());
					if (mv.find()){
						//System.out.println("Value: ["+mv.group()+"]");
						ele.setValue(mv.group().trim());
					}
					
					//get Company
					if (ele.getTagName().equals("dei:EntityRegistrantName") || ele.getName().equals("EntityRegistrantName")){
						//flag = ele.value.trim();
						inst.setCompany(ele.getValue().trim());
					}else{	}
						
					//getting labels
					if (trigger == 1){
						ele.setLabels((ArrayList<XbrlLabel>) la.getLinkbaseElementByArc(labelList,lo.fromLocatorGetArcByPosition(locList, arcList, ele)));
					}//trigger end]
				eleList.add(ele);
				j++;
				} //m.matches()
				
			} //currentLine end
			inst.setEleList(eleList);
			locList = null;
			arcList = null;
			labelList = null;
		return inst;
	}
	
}
