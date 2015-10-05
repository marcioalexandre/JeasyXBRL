/**
 This software is subject to the terms of the GNU GENERAL PUBLIC LICENSE
 Agreement, available at the following URL:
 http://www.gnu.org/licenses/gpl.txt.
 You must accept the terms of that agreement to use this software.

 Copyright (C) 2015 Marcio Alexandre Pereira da Silva
 All Rights Reserved.
 * 
 * @author Marcio Alexandre
 * @email marcio.alexandre83@gmail.com
 * @since 2015-09-22
 * @version beta0.0
 */

package jeasyxbrl;

import jeasyxbrl.global.XbrlCache;
import jeasyxbrl.taxonomy.instance.XbrlElement;
import jeasyxbrl.taxonomy.instance.XbrlInstance;
import jeasyxbrl.taxonomy.linkbase.XbrlLabel;
import jeasyxbrl.taxonomy.linkbase.XbrlLinkbase;

import java.util.ArrayList;

public class JeasyXbrl {
	XbrlCache xbrlCache = new XbrlCache();
	
	public void loadingInstancesInCache(
		ArrayList<String> instanceNameList, 
		ArrayList<String> labelNameList, 
		ArrayList<String> calculationNameList, 
		ArrayList<String> referenceNameList,
		ArrayList<String> presentationNameList,
		ArrayList<String> definitionNameList,
		ArrayList<String> otherNameList,
		int trigger) throws Throwable{
			int j=0;
			ArrayList<XbrlInstance> instanceList = new ArrayList<XbrlInstance>();
			for (String inst: instanceNameList){
				XbrlInstance instance	=	new XbrlInstance();
				if (trigger== 1){
					instance = instance.getAllElementsByInstance(inst, labelNameList.get(j), null, null, null, null, null, trigger, j);
					
				}else{
					instance = instance.getAllElementsByInstance(inst, null, null, null, null, null, null, trigger, j);
				}
				//inserting instance in a list for other methods
				instanceList.add(instance);
				j++;
				//Inserting instance in memory (guava)
				xbrlCache.insert(j, instance);
			}
		}
	
	public String printCopyright(){
		String copyright = "\n\n"+
				"===================================================\n"+
				"Copyright 2015 Marcio Alexandre Pereira da Silva\n\n"+
				"This file is part of jeasyXBRL\n\n"+
				"jeasyXBRL is free software: you can redistribute it and/or modify\n"+
				"it under the terms of the GNU General Public License as published by\n"+
				"the Free Software Foundation, either version 3 of the License, or\n"+
				"(at your option) any later version.\n\n"+
				"jeasyXBRL is distributed in the hope that it will be useful,\n"+
				"but WITHOUT ANY WARRANTY; without even the implied warranty of\n"+
				"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n"+
				"GNU General Public License for more details.\n\n"+
				"You should have received a copy of the GNU General Public License\n"+
				"along with jeasyXBRL.  If not, see <http://www.gnu.org/licenses/>.\n\n"+
				"@author: Marcio A. P. da Silva\n"+
				"@since: September 22nd, 2015\n"+
				"@Email: marcio.alexandre83@gmail.com\n"+
				"===================================================\n\n";
		return copyright;
	}
	public void printElements(ArrayList<XbrlInstance> instanceList){
		int i = 0;
		for (XbrlInstance ins: instanceList){
			System.out.println("");
			System.out.println("Company number: ["+ins.getCompany()+"]");
			System.out.println("File name: 		["+ins.getFilename()+"]");
			for (XbrlElement ele: ins.getEleList()){
				try{
					i++;
					System.out.println("Elemento number: ["+ele.getPosition()+"]");
					System.out.println("XML line:        ["+ele.getXmlLine()+"]");
					System.out.println("Name:            ["+ele.getName()+"]");
					System.out.println("Value:           ["+ele.getValue()+"]");
					System.out.println("Unit:            ["+ele.getUnitRef()+"]");
					System.out.println("Context:         ["+ele.getContextRef()+"]");
					System.out.println("Id:              ["+ele.getId()+"]");
					System.out.println("Decimals:        ["+ele.getDecimals()+"]");
					System.out.println("Label(s){");
					try{
						for (XbrlLabel label: ele.getLabels()){
							System.out.println("  ::::Value ["+label.getValue()+"]");
							if (label.getValue() != "No labels"){
								System.out.println("  :: Lang  ["+label.getLang()+"]");
								System.out.println("  :: Id    ["+label.getId()+"]");
								System.out.println("  :: Label ["+label.getLabel()+"]");
								System.out.println("  :: Role  ["+label.getRole()+"]");
								System.out.println("  :: Type  ["+label.getType()+"]");
								System.out.println("  :::::::::::::::::::::");
							}
						}
					}catch(Exception el){
						System.out.println("::::: [No Labels] or some problem: ["+el.getMessage()+"]");
					}
					System.out.println("} //label(s)");	
				}catch(Exception ee){
					System.out.println("[No element] or some problem: ["+ee.getMessage()+"]");
				}
			}
		}
	}

	public void printElementsByCache(){
		try {
			xbrlCache.print();
		} catch (InterruptedException e) {
			System.out.println("[jeasyxbrl][global][jeasyxbrl.java]");
			e.printStackTrace();
		}
	}
	
	public ArrayList<XbrlInstance> getXbrlInstanceListByCache(){
		return xbrlCache.getXbrlInstanceListByCache();
	}
	
}
