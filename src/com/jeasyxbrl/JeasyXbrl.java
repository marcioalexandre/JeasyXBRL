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
//  @since 2015-09-22

package com.jeasyxbrl;

import java.util.ArrayList;

import com.jeasyxbrl.data.company.Company;
import com.jeasyxbrl.data.user.User;
import com.jeasyxbrl.global.JeasyXbrlData;
import com.jeasyxbrl.global.XbrlCache;

public class JeasyXbrl {
	XbrlCache xbrlCache = new XbrlCache();
	
	public void loadingXbrlData(
		User user,
		ArrayList<String> instanceNameList, 
		ArrayList<String> labelNameList, 
		ArrayList<String> calculationNameList, 
		ArrayList<String> referenceNameList,
		ArrayList<String> presentationNameList,
		ArrayList<String> definitionNameList,
		ArrayList<String> otherNameList,
		int trigger) throws Throwable{
			
			Company com = new Company();
			ArrayList<ArrayList<String>> doubleLinkList = new ArrayList<ArrayList<String>>(); 
			if (labelNameList != null){
				doubleLinkList.add(labelNameList);
			}
			if (calculationNameList != null){
				doubleLinkList.add(calculationNameList);
			}
			if (referenceNameList != null){
				doubleLinkList.add(referenceNameList);
			}
			if (presentationNameList != null){
				doubleLinkList.add(presentationNameList);
			}
			if (definitionNameList != null){
				doubleLinkList.add(definitionNameList);
			}
			if (otherNameList != null){
				doubleLinkList.add(otherNameList);
			}
			
			ArrayList<Company> companyList = com.getCompanies(
					user,
					instanceNameList,
					doubleLinkList,
					trigger);
			//Inserting instance in memory (guava)
			int i=0;
			for (Company company: companyList){
				JeasyXbrlData jxd = new JeasyXbrlData();
				jxd.setCompany(company);
				jxd.setUser(user);
				xbrlCache.insert(i, jxd);
				i++;
			}
		}

	public ArrayList<JeasyXbrlData> getXbrlData(User user){
		return xbrlCache.getXbrlData(user);
	}
	
	public void cleanCache(User user){
		xbrlCache.remove(user);
	}
	
	public String printCopyright(){
		String copyright = "\n\n"+
			"Copyright (C) 2015 Marcio Alexandre Pereira da Silva\n"
			+ "All Rights Reserved.\n"
			+ "This file is part of jeasyXBRL-0.3.\n"
			+ "\n"
			+ " jeasyXBRL-0.3 is free software: you can redistribute it and/or modify\n"
			+ " it under the terms of the GNU General Public License as published by\n"
			+ " the Free Software Foundation, either version 3 of the License, or\n"
			+ " (at your option) any later version. jeasyXBRL-0.3 is distributed in the\n" 
			+ " hope that it will be useful,but WITHOUT ANY WARRANTY; without even the \n"
			+ " implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n"  
			+ " See the GNU General Public License for more details. You should check \n"
			+ " the GNU General Public License in the link <http://www.gnu.org/licenses/>.\n"
			+ "\n"
			+ " @author Marcio Alexandre\n"
			+ " @email marcio.alexandre83@gmail.com\n"
			+ " @site xbrlframework.com | marcioalexandre.wordpress.com\n"
			+ " @since 2015-09-19, updated 2015-10-10";
	return copyright;
	}
	public void print(ArrayList<JeasyXbrlData> jxdList){
		for (JeasyXbrlData jxd: jxdList){
			jxd.print();
		}
	}
	/*
	public String allDataToJsonFromMemory(){
		Gson gson = new Gson();
		JsonElement je = gson.toJsonTree(xbrlCache.getXbrlInstanceListByCache());
		return gson.toJson(je);
	}
	
	public String ToJsonFromInstance(XbrlInstance ifile){
		Gson gson = new Gson();
		JsonElement je = gson.toJsonTree(ifile);
		return gson.toJson(je);
	}
	*/
	public void printCache(User user){
		try {
			xbrlCache.printFromCache(user);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	
}
