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

import jeasyxbrl.instance.XbrlElement;
import jeasyxbrl.linkbase.Label;

import java.util.ArrayList;

public class JeasyXbrl {
	public ArrayList<ArrayList<XbrlElement>> getAllElements(
		ArrayList<String> instances, 
		ArrayList<String> labels, 
		ArrayList<String> calculations, 
		ArrayList<String> references,
		ArrayList<String> presentations,
		ArrayList<String> definitions,
		ArrayList<String> others,
		int trigger) throws Throwable{
			XbrlElement ele = new XbrlElement();
			return ele.getAllElementsByFiles(instances, labels, calculations, references, presentations, definitions, others, trigger);
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
				"along with Foobar.  If not, see <http://www.gnu.org/licenses/>.\n\n"+
				"@author: Marcio A. P. da Silva\n"+
				"@since: September 22nd, 2015\n"+
				"@Email: marcio.alexandre83@gmail.com\n"+
				"===================================================\n\n";
		return copyright;
	}
	public void printElements(ArrayList<ArrayList<XbrlElement>> eles){
		int i = 0;
		for (ArrayList<XbrlElement> eleList: eles){
			for (XbrlElement ele: eleList){
				try{
					i++;
					System.out.println("");
					System.out.println("Elemento number: ["+ele.getPosition()+"]");
					System.out.println("File name:       ["+ele.getFile_name()+"]");
					System.out.println("XML line:        ["+ele.getXmlLine()+"]");
					System.out.println("Name:            ["+ele.getName()+"]");
					System.out.println("Company:         ["+ele.getCompany()+"]");
					System.out.println("Value:           ["+ele.getValue()+"]");
					System.out.println("Unit:            ["+ele.getUnit_ref()+"]");
					System.out.println("Context:         ["+ele.getContext_ref()+"]");
					System.out.println("Id:              ["+ele.getId()+"]");
					System.out.println("Decimals:        ["+ele.getDecimals()+"]");
					System.out.println("Label(s){");
					try{
						for (Label l: ele.getLabels()){
							System.out.println("  ::::Value ["+l.getValue()+"]");
							if (l.getValue() != "No labels"){
								System.out.println("  :: Lang  ["+l.getLang()+"]");
								System.out.println("  :: Id    ["+l.getId()+"]");
								System.out.println("  :: Label ["+l.getLabel()+"]");
								System.out.println("  :: Role  ["+l.getRole()+"]");
								System.out.println("  :: Type  ["+l.getType()+"]");
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

}
