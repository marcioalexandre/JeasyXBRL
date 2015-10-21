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



package com.jeasyxbrl.data.taxonomy.linkbase;

import java.io.IOException;
import java.util.ArrayList;

import com.jeasyxbrl.parser.ArcParser;
import com.jeasyxbrl.parser.LocatorParser;

public class XbrlLinkbase {
	//<!-- common attributes -->
	private int		idInstance		=	0;
	private int		idLinkbaseDoc	=	0;
	private String 	fileName 		= 	null;
	private String	linkbaseName	=	null; //<!-- label, calculation, presentation,formula...-->
	private ArrayList<Arc> arcList	= 	null;
	private ArrayList<Locator> locList 	= null;
	private ArrayList<? extends Object> linkbaseElement = null;
	public int getIdInstance() {
		return idInstance;
	}
	public void setIdInstance(int idInstance) {
		this.idInstance = idInstance;
	}
	public int getIdLinkbaseDoc() {
		return idLinkbaseDoc;
	}
	public void setIdLinkbaseDoc(int idLinkbaseDoc) {
		this.idLinkbaseDoc = idLinkbaseDoc;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLinkbaseName() {
		return linkbaseName;
	}
	public void setLinkbaseName(String linkbaseName) {
		this.linkbaseName = linkbaseName;
	}
	public ArrayList<Arc> getArcList() {
		return arcList;
	}
	public void setArcList(ArrayList<Arc> arcList) {
		this.arcList = arcList;
	}
	public ArrayList<Locator> getLocList() {
		return locList;
	}
	public void setLocList(ArrayList<Locator> locList) {
		this.locList = locList;
	}
	public ArrayList<? extends Object> getLinkbaseElement() {
		return linkbaseElement;
	}
	public void setLinkbaseElement(ArrayList<? extends Object> linkbaseElement) {
		this.linkbaseElement = linkbaseElement;
	}
	
	
	public XbrlLinkbase getLinkbase(String linkbaseName) throws IOException{
		XbrlLinkbase link = new XbrlLinkbase();
		ArcParser arcParser = new ArcParser();
		LocatorParser locParser = new LocatorParser();
		link.setFileName(linkbaseName);
		link.setArcList(arcParser.getArcsFromFile(link.getFileName()));
		link.setLocList(locParser.getLocatorsFromFile(link.getFileName()));
		XbrlLabel lab	=	new XbrlLabel();
		if (linkbaseName.contains("label") || linkbaseName.contains("lab")){
			link.setLinkbaseElement(lab.getLinkbaseElementFromFile(linkbaseName));
		}else{
			// future works
			if (linkbaseName.contains("cal") || linkbaseName.contains("calc") || linkbaseName.contains("calculation") ){
				//here you have to create the xbrl linkbase elements (ex.: xbrlcalculation)
				//(...)
			}else{
				// and so on
			}
		}
		
		return link;
	}
	
	
}
