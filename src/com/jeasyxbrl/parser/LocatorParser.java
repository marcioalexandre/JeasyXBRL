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
//  @since 2015-10-10


package com.jeasyxbrl.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeasyxbrl.conn.Connection;
import com.jeasyxbrl.data.taxonomy.linkbase.Locator;
import com.jeasyxbrl.global.XbrlTaxonomyRe;

/**
 * @author Marcio
 *
 */
public class LocatorParser {
	
	public ArrayList<Locator> getLocatorsFromFile(String file) throws IOException{
		Connection conn = new Connection();
		BufferedReader br = conn.getConnection(file);
		String currentLine;
		ArrayList<Locator> locList = new ArrayList<Locator>();
		XbrlTaxonomyRe re = new XbrlTaxonomyRe();
		int j=0; //position
		while ((currentLine = br.readLine()) != null){
			Locator loc = new Locator();
			currentLine = currentLine.trim();
			//setting locator
			Pattern ploc = Pattern.compile(re.getLoc());
			Matcher mloc = ploc.matcher(currentLine);
			if (mloc.find()){
				//setting position
				loc.setPosition(j);
				//setting XML line
				loc.setXmlLine(mloc.group());
				//setting href
				Pattern phref = Pattern.compile(re.getHref());
				Matcher mhref = phref.matcher(loc.getXmlLine());
				if (mhref.find()){
					String[] href = mhref.group().split("\\s");
					href[0] = href[0].replace("\"", "");
					href[0] = href[0].replace("\'", "");
					loc.setHref(href[0]);
				}
				//setting label
				Pattern plabel = Pattern.compile(re.getLabelAttr());
				Matcher mlabel = plabel.matcher(loc.getXmlLine());
				if (mlabel.find()){
					String[] label = mlabel.group().split("\\s");
					label[0] 	= label[0].replace("\"", "");
					label[0] 	= label[0].replace("\'", "");
					loc.setLabel(label[0]);
				}
				j++;
				locList.add(loc);
			}

		}
		return locList;
	}

}
