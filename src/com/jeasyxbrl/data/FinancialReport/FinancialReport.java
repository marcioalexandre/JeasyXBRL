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
//  @since 2015-10-03

package com.jeasyxbrl.data.FinancialReport;

import java.util.ArrayList;

import com.jeasyxbrl.data.taxonomy.instance.XbrlInstance;
import com.jeasyxbrl.data.taxonomy.linkbase.Arc;
import com.jeasyxbrl.data.taxonomy.linkbase.Locator;
import com.jeasyxbrl.data.taxonomy.linkbase.XbrlLinkbase;
import com.jeasyxbrl.data.user.User;
import com.jeasyxbrl.parser.ArcParser;
import com.jeasyxbrl.parser.LocatorParser;


public class FinancialReport {
	private XbrlInstance instance = null;
	private ArrayList<XbrlLinkbase> linkbaseList = null;
	
	public XbrlInstance getInstance() {
		return instance;
	}
	public void setInstance(XbrlInstance instance) {
		this.instance = instance;
	}
	public ArrayList<XbrlLinkbase> getLinkbaseList() {
		return linkbaseList;
	}
	public void setLinkbaseList(ArrayList<XbrlLinkbase> linkbaseList) {
		this.linkbaseList = linkbaseList;
	}	
	
	public ArrayList<FinancialReport> getFinancialReports(
			User user,
			ArrayList<String> instanceNameList,
			ArrayList<ArrayList<String>> doubleLinkList,
			int trigger) throws Throwable{
		ArrayList<FinancialReport> frList = new ArrayList<FinancialReport>();
		int j = 0;
		for (String inst: instanceNameList){
			FinancialReport freport = new FinancialReport();
			if (trigger== 1){ //there is linkbase for processing
				freport = freport.getFinancialReport(inst, doubleLinkList, trigger, j);
				LocatorParser locParser = new LocatorParser();
				ArrayList<Locator> locList = new ArrayList<Locator>();
				for (ArrayList<String> linkName: doubleLinkList){
					locList = locParser.getLocatorsFromFile(linkName.get(j));
					ArcParser arcParser = new ArcParser();
					ArrayList<Arc> arcList = new ArrayList<Arc>();
					arcList = arcParser.getArcsFromFile(linkName.get(j));
					XbrlLinkbase link = new XbrlLinkbase();
					link.setFileName(linkName.get(j));
					link.setArcList(arcList);
					link.setLocList(locList);
					freport.getLinkbaseList().add(link);
				}
			}else{ //there is no linkbase for processing
				freport = freport.getFinancialReport(inst, null, trigger, j);
			}	
			frList.add(freport);
		}
		return frList;
}//getFinancialReports
	
	public FinancialReport getFinancialReport(
		String instanceName, 
		ArrayList<ArrayList<String>> linkbaseNameList,
		int trigger,
		int position) throws Throwable{
			
		//getting instnace
		XbrlInstance ins = new XbrlInstance();
		ArrayList<XbrlLinkbase> linkList = new ArrayList<XbrlLinkbase>();
		if (trigger == 1){
			//getting linkbases
			for (ArrayList<String> linkNameList: linkbaseNameList){
				XbrlLinkbase linkbase = new XbrlLinkbase();
				if (linkNameList != null){
					for (String linkName: linkNameList){
						if (linkName != null){
							XbrlLinkbase link = linkbase.getLinkbase(linkName);
							if (link == null){
								throw new Exception("There is no linkbase result!");
							}else
								linkList.add(link);
						}
					}
				}
			}
			this.setLinkbaseList(linkList);
		}
		this.setInstance(ins.getInstance(instanceName, linkList, trigger, position));
		return this;
	}
	
	public void print(){
		XbrlInstance xi = this.getInstance();
		xi.print();
	}
	
}
