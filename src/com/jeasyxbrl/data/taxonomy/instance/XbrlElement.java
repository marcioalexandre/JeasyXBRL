// Copyright (C) 2015 Marcio Alexandre Pereira da Silva
// All Rights Reserved.

// This file is part of jeasyXBRL-0.3.

// jeasyXBRL-0.3 is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.jeasyXBRL-0.3 is distributed in the 
// hope that it will be useful,but WITHOUT ANY WARRANTY; without even the 
// implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
// See the GNU General Public License for more details. You should check 
// the GNU General Public License in the link <http://www.gnu.org/licenses/>.

//  @author Marcio Alexandre
//  @email marcio.alexandre83@gmail.com
//  @site xbrlframework.com | marcioalexandre.wordpress.com
//  @since 2015-09-18

package com.jeasyxbrl.data.taxonomy.instance;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeasyxbrl.data.taxonomy.linkbase.XbrlLabel;
import com.jeasyxbrl.global.XbrlTaxonomyRe;

public class XbrlElement{
	private	int	   position			=	0;
	private String xmlLine			=	null;
	private String name 			=	null;
	private String decimals			=	null;
	private String id				=	null;
	private String contextRef		=	null;
	private String unitRef			=	null;
	private String value			=	null;
	private ArrayList<XbrlLabel> labels	=	null;
	private String tagName			= 	null;
	
	//getters and setters
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ArrayList<XbrlLabel> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<XbrlLabel> labels) {
		this.labels = labels;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public XbrlElement getElementByLine(String currentLine){
		XbrlTaxonomyRe 	re 		= new XbrlTaxonomyRe(); 
		Pattern p = Pattern.compile(re.getElementLine());
		Matcher m = p.matcher(currentLine);
		try{
			if (m.find()){
				this.setPosition(position);
				this.setXmlLine(m.group());
				
				//get element tag name
				Pattern ptag = Pattern.compile(re.getElementTagName());
				Matcher mtag = ptag.matcher(this.getXmlLine());
				if (mtag.find()){
					//System.out.println("");
					//System.out.println(m.group());
					String tagname 	= mtag.group().replace("<"," ").trim();
					String[] tname	= tagname.split("\\s");
					//System.out.println("Tag Name: ["+tname[0]+"]");
					this.setTagName(tname[0].trim());
				}else{	}
				
				//get element name
				Pattern pname = Pattern.compile(re.getElementName());
				Matcher mname = pname.matcher(this.getXmlLine());
				if (mname.find()){
					//System.out.println("Name: ["+mname.group().trim()+"]");
					this.setName(mname.group().trim());
				}else{	}
				
				// get element id
				Pattern pid = Pattern.compile(re.getId());
				Matcher mid = pid.matcher(this.getXmlLine());
				if (mid.find()){
					String[] rid = mid.group().split("\\s");
					rid[0] = rid[0].replace("\"","");
					rid[0] = rid[0].replace("\'","");
					rid[0] = rid[0].replace(">&lt;div",""); 
					//System.out.println("Id: ["+rid[0].trim()+"]");
					this.setId(rid[0].trim());
				}else{	}
				//get Element Context_Ref
				Pattern pcr = Pattern.compile(re.getElementCRef());
				Matcher mcr = pcr.matcher(this.getXmlLine());
				if (mcr.find()){
					String[] rcr = mcr.group().split("\\s");
					rcr[0] = rcr[0].replace("\"","");
					rcr[0] = rcr[0].replace("\'","");
					//System.out.println("ContextRef: ["+rcr[0]+"]");
					this.setContextRef(rcr[0].trim());
				}else{  }
				
				//get Element Unit_Ref
				Pattern pur	= Pattern.compile(re.getElementURef());
				Matcher mur	= pur.matcher(this.getXmlLine());
				if (mur.find()){
					String[] rur	=	mur.group().split("\\s"); 
					rur[0]	= rur[0].replace("\"","");
					rur[0]	= rur[0].replace("\'","");
					//System.out.println("UnitRef: ["+rur[0]+"]");
					this.setUnitRef(rur[0].trim());
				}else{}
				
				//get Element Decimals
				Pattern pd	= Pattern.compile(re.getElementDecimals());
				Matcher md	= pd.matcher(this.getXmlLine());
				if (md.find()){
					String[] rd = md.group().split("\\s");
					rd[0] = rd[0].replace("\"","");
					rd[0] = rd[0].replace("\'","");
					//System.out.println("Decimals: ["+rd[0]+"]");
					this.setDecimals(rd[0].trim());
				}else{}
				
				//get Element Value
				Pattern pv	= Pattern.compile(re.getValue()+this.getTagName()+")");
				Matcher mv	= pv.matcher(this.getXmlLine());
				if (mv.find()){
					//System.out.println("Value: ["+mv.group()+"]");
					this.setValue(mv.group().trim());
				}else{}
			}
		}catch(Exception e){}
		return this;
	}
	public void print(){
		try{
			System.out.println("Elemento number: ["+this.getPosition()+"]");
			System.out.println("XML line:        ["+this.getXmlLine()+"]");
			System.out.println("Name:            ["+this.getName()+"]");
			System.out.println("Value:           ["+this.getValue()+"]");
			System.out.println("Unit:            ["+this.getUnitRef()+"]");
			System.out.println("Context:         ["+this.getContextRef()+"]");
			System.out.println("Id:              ["+this.getId()+"]");
			System.out.println("Decimals:        ["+this.getDecimals()+"]");
			System.out.println("Label(s){");
		}catch(Exception e){
			System.out.println(":::::::::: Element Error["+e.getMessage()+"]");
		}finally{
			try{
				for (XbrlLabel label: this.getLabels()){
					label.print();
				}
			}catch(Exception e){
				System.out.println(":::::::: No Label or error ["+e.getMessage()+"]");
			}finally{
				System.out.println("}");
			}
		}

	}
}
