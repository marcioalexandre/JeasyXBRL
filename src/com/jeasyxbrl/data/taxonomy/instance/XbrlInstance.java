// Copyright (C) 2015 Marcio Alexandre Pereira da Silva
// All Rights Reserved.

// This file is part of jeasyXBRL-0.3.

// jeasyXBRL-0.3 is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version. jeasyXBRL-0.3 is distributed in the 
// hope that it will be useful,but WITHOUT ANY WARRANTY; without even the 
// implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
// See the GNU General Public License for more details. You should check 
// the GNU General Public License in the link <http://www.gnu.org/licenses/>.

//  @author Marcio Alexandre
//  @email marcio.alexandre83@gmail.com
//  @site xbrlframework.com | marcioalexandre.wordpress.com
//  @since 2015-10-03

package com.jeasyxbrl.data.taxonomy.instance;

import java.io.BufferedReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeasyxbrl.conn.Connection;
import com.jeasyxbrl.data.taxonomy.linkbase.Locator;
import com.jeasyxbrl.data.taxonomy.linkbase.XbrlLabel;
import com.jeasyxbrl.data.taxonomy.linkbase.XbrlLinkbase;
import com.jeasyxbrl.global.XbrlTaxonomyRe;

public class XbrlInstance {
	private int id = 0;
	private String idCompany = null;
	private String filename = null;
	private String company = null;
	private String documentType = null;
	private ArrayList<XbrlElement> eleList = null;
	private Date date = null;
	private long size = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(String idCompany) {
		this.idCompany = idCompany;
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
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@SuppressWarnings("unchecked")
	public XbrlInstance getInstance(String instance, ArrayList<XbrlLinkbase> linkList, int trigger,int position) throws Exception{
		XbrlInstance ins = new XbrlInstance();
		ins.setId(position+1); 
		Connection 		conn 	= new Connection();
		BufferedReader br = null;
		String currentLine;
		br 	= conn.getConnection(instance);
		//loading Locator, Arc and Label by sent linkbase.
		XbrlLabel lab	=	new XbrlLabel();
		ArrayList<XbrlElement> eleList = new ArrayList<XbrlElement>();
		//getting locator, arc,labels if trigger is ok.
		ins.setFilename(instance);
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
				ins.setFilename(instance);
				//setting element tag name
				Pattern ptag = Pattern.compile(re.getElementTagName());
				Matcher mtag = ptag.matcher(ele.getXmlLine());
				if (mtag.find()){
					String tagname 	= mtag.group().replace("<"," ").trim();
					String[] tname	= tagname.split("\\s");
					ele.setTagName(tname[0].trim());
				}else{	}
				Pattern pname = Pattern.compile(re.getElementName());
				Matcher mname = pname.matcher(ele.getXmlLine());
				if (mname.find()){
					ele.setName(mname.group().trim());
				}else{	}
				// setting element id
				Pattern pid = Pattern.compile(re.getId());
				Matcher mid = pid.matcher(ele.getXmlLine());
				if (mid.find()){
					String[] rid = mid.group().split("\\s");
					rid[0] = rid[0].replace("\"","");
					rid[0] = rid[0].replace("\'","");
					rid[0] = rid[0].replace(">&lt;div",""); 
					ele.setId(rid[0].trim());
				}else{	}
				//setting Element Context_Ref
				Pattern pcr = Pattern.compile(re.getElementCRef());
				Matcher mcr = pcr.matcher(ele.getXmlLine());
				if (mcr.find()){
					String[] rcr = mcr.group().split("\\s");
					rcr[0] = rcr[0].replace("\"","");
					rcr[0] = rcr[0].replace("\'","");
					ele.setContextRef(rcr[0].trim());
				}else{  }
				//setting Element Unit_Ref
				Pattern pur	= Pattern.compile(re.getElementURef());
				Matcher mur	= pur.matcher(ele.getXmlLine());
				if (mur.find()){
					String[] rur	=	mur.group().split("\\s"); 
					rur[0]	= rur[0].replace("\"","");
					rur[0]	= rur[0].replace("\'","");
					ele.setUnitRef(rur[0].trim());
				}
				
				//setting Element Decimals
				Pattern pd	= Pattern.compile(re.getElementDecimals());
				Matcher md	= pd.matcher(ele.getXmlLine());
				if (md.find()){
					String[] rd = md.group().split("\\s");
					rd[0] = rd[0].replace("\"","");
					rd[0] = rd[0].replace("\'","");
					ele.setDecimals(rd[0].trim());
				}
					//setting Element Value
				Pattern pv	= Pattern.compile(re.getValue()+ele.getTagName()+")");
				Matcher mv	= pv.matcher(ele.getXmlLine());
				if (mv.find()){
					ele.setValue(mv.group().trim());
				}
				//setting Company
				if (ele.getTagName().equalsIgnoreCase("dei:EntityRegistrantName") || ele.getName().equals("EntityRegistrantName")){
					ins.setCompany(ele.getValue().trim());
				}else{	}
				//setting id
				if (ele.getTagName().equalsIgnoreCase("dei:EntityCentralIndexKey") || ele.getName().equalsIgnoreCase("EntityCentralIndexKey")){
					ins.setIdCompany(ele.getValue().trim());
				}else{}
				//setting DocumentType
				if (ele.getTagName().equalsIgnoreCase("dei:DocumentType") || ele.getName().equalsIgnoreCase("DocumentType")){
					ins.setDocumentType(ele.getValue().trim());
				}else {}
				//setting labels
				if (linkList != null){
					for (XbrlLinkbase xl: linkList){
						if (xl.getLocList() == null || xl.getLocList().size() < 1){
							throw new Exception("Cannot get the Label: This is no Locator or Arc elements in this Financial Report");
						}else{
							Locator loc = new Locator();
							ele.setLabels(
									(ArrayList<XbrlLabel>) lab.getLinkbaseElementByArc(
											xl.getLinkbaseElement(),loc.fromLocatorGetArcByPosition(
													xl.getLocList(), xl.getArcList(), ele)));
						}
					}
				}//setting labels
				eleList.add(ele);
			j++;
			} //m.matches()
		} //currentLine end
		ins.setEleList(eleList);
		return ins;
	}
	
	
	public void print(){
		try{
			System.out.println(":::Instance data {");
			System.out.println(":::Instance Id:    ["+this.getId()+"]");	
			System.out.println(":::Instance FName: ["+this.getFilename()+"]");
			int i=0;
			allElement:{
				for (XbrlElement xe: this.getEleList()){
					xe.print();
					i++;
					if (i==0){
						break allElement;
					}
				}
			}
		}catch(Exception e){
			System.out.println("::::::::: Erro["+e.getMessage()+"]");
		}
		System.out.println(":::}//Instance data");
	}

	
}
