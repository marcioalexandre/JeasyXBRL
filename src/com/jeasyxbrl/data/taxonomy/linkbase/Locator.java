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
//  @since 2015-09-19, updated 2015-10-03


package com.jeasyxbrl.data.taxonomy.linkbase;

import java.util.ArrayList;

import com.jeasyxbrl.data.taxonomy.instance.XbrlElement;

public class Locator {
	private	int	   position	=	0;
	private String xmlLine	=	null;
	private String type		=	"locator";
	private String href		=	null;
	private String label	=	null;
	
	
	//getters and setters
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
	
	public Locator getLocatorByElement(ArrayList<Locator> locs, XbrlElement ele){
		Locator l = new Locator();
		try{
			searchLoc:	{
				for (Locator loc: locs){
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
		@SuppressWarnings("unused")
		Locator locItem 	= new Locator();
		Arc a	= new Arc();
		int i=0;
		try{
			searchLoc:	{
				for (Locator loc: locs){
					if (loc.href.contains("_"+ele.getName())){
						locItem = loc;
						a = arcs.get(i);
						break searchLoc;
					}
					i++;
				}
			}
		}catch(Exception e){
			System.out.println("Exception: "+e.getLocalizedMessage());
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
