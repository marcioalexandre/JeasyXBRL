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

package com.jeasyxbrl.data.company;

import java.util.ArrayList;

import com.jeasyxbrl.data.FinancialReport.FinancialReport;
import com.jeasyxbrl.data.company.Company;
import com.jeasyxbrl.data.taxonomy.linkbase.Arc;
import com.jeasyxbrl.data.taxonomy.linkbase.Locator;
import com.jeasyxbrl.data.taxonomy.linkbase.XbrlLinkbase;
import com.jeasyxbrl.data.user.User;
import com.jeasyxbrl.parser.ArcParser;
import com.jeasyxbrl.parser.LocatorParser;

/**
 * @author Marcio
 *
 */
public class Company {
	private String id = null; //identifier contained in XBRL Instance
	private String name = null;
	private String idUser = null;
	private ArrayList<FinancialReport> finRepList = null;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public ArrayList<FinancialReport> getFinRepList() {
		return finRepList;
	}
	public void setFinRepList(ArrayList<FinancialReport> finRepList) {
		this.finRepList = finRepList;
	}
	
	public ArrayList<Company> getCompanies(User user, ArrayList<String> instanceNameList,
		ArrayList<ArrayList<String>> doubleLinkList,int trigger) throws Throwable{
			ArrayList<Company> comList = new ArrayList<Company>();
			if (instanceNameList.isEmpty() || instanceNameList.equals(null) || instanceNameList == null){
				throw new Exception("There is no XBRL instance informed by user");
			}else{
				int i = 0;
				for (String instanceName: instanceNameList){
					FinancialReport fr = new FinancialReport();
					int flag = 0;
					fr = fr.getFinancialReport(instanceName, doubleLinkList, trigger, i);
					if (comList.isEmpty()){
						ArrayList<FinancialReport> newFRList = new ArrayList<FinancialReport>();
						Company company = new Company();
						company.setId(fr.getInstance().getIdCompany());
						company.setName(fr.getInstance().getCompany());
						company.setIdUser(user.getEmail());
						newFRList.add(fr);
						company.setFinRepList(newFRList);
						comList.add(company);
					}else{
						int j=0;
						for (Company com: comList){
							if (com.getName().equalsIgnoreCase(fr.getInstance().getCompany())){
								comList.get(j).getFinRepList().add(fr);
								flag++;
							}
							j++;
						}
						if (flag == 0){
							ArrayList<FinancialReport> newFRList = new ArrayList<FinancialReport>();
							Company company = new Company();
							company.setId(fr.getInstance().getIdCompany());
							company.setName(fr.getInstance().getCompany());
							company.setIdUser(user.getEmail());
							newFRList.add(fr);
							company.setFinRepList(newFRList);
							comList.add(company);
						}
					}
					
					i++;
				}
			}	
			return comList;
	}//getCompanies
	
	public Company addNewCompany(Company company, FinancialReport fr){
		company.setName(fr.getInstance().getCompany());
		if (company.getFinRepList() == null){
			company.setFinRepList(new ArrayList<FinancialReport>()); 
		}
		company.getFinRepList().add(fr);
		company.setId(company.getFinRepList().get(0).getInstance().getIdCompany());
		return company;
	}
	
	@Deprecated
	public ArrayList<Company> getData(
		User user,
		ArrayList<String> instanceNameList,
		ArrayList<ArrayList<String>> doubleLinkList,
		int trigger) throws Throwable{
			ArrayList<Company> comList = new ArrayList<Company>();
			if (instanceNameList.isEmpty() || instanceNameList.equals(null) || instanceNameList == null){
				throw new Exception("There is no XBRL instance informed by user");
			}else{
				ArrayList<FinancialReport> frList = new ArrayList<FinancialReport>();
				int j = 0;
				for (String inst: instanceNameList){
					Company company = new Company();
					company.setIdUser(user.getEmail());
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
					//if there already is the company
					int flag = 0;
					if (!comList.isEmpty()){
						for (Company com: comList){
							if (com.getName() == freport.getInstance().getCompany()){
								flag++;
								company = com;
							}
						}
					}
					if (flag == 0){
						company.setName(freport.getInstance().getCompany());
					}
					company.setFinRepList(frList);
					company.setId(company.getFinRepList().get(0).getInstance().getIdCompany());
					comList.add(company);
					j++;
				}
			}
			return comList;
	}
	
	public void print(){
		try{
			System.out.println(":User id:      ["+this.getIdUser()+"]");
			System.out.println(":Company Name: ["+this.getName()+"]");
			System.out.println(":Company id:   ["+this.getId()+"]");
			for (FinancialReport fr: this.getFinRepList()){
				fr.print();
			}
		}catch(Exception e){
			System.out.println("No company or error["+e.getStackTrace()+"]");
		}
		System.out.println(":}//Company Data");
	}
}
