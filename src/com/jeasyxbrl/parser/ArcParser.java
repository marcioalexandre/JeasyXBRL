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
import com.jeasyxbrl.data.taxonomy.linkbase.Arc;
import com.jeasyxbrl.global.XbrlTaxonomyRe;

/**
 * @author Marcio
 *
 */
public class ArcParser {
	
	public ArrayList<Arc> getArcsFromFile(String file) throws IOException{
		Connection conn = new Connection();
		BufferedReader br = conn.getConnection(file);
		String currentLine;
		ArrayList<Arc> arcList = new ArrayList<Arc>();
		XbrlTaxonomyRe re = new XbrlTaxonomyRe();
		int j = 0;
		while ((currentLine = br.readLine()) != null){
			Arc arc = new Arc();
			currentLine = currentLine.trim();
			
			//getting arc
			Pattern parc = Pattern.compile(re.getArc());
			Matcher marc = parc.matcher(currentLine);
			if (marc.find()){
				//getting arc position
				arc.setPosition(j);
				
				//getting XML line
				arc.setXmlLine(marc.group());
				//System.out.println();
				//System.out.println(this.xmlLine);
			
				//getting role
				Pattern prole = Pattern.compile(re.getRole());
				Matcher mrole = prole.matcher(arc.getXmlLine());
				if (mrole.find()){
					String[] role = mrole.group().split("\\s");
					role[0] = role[0].replace("\"","");
					role[0] = role[0].replace("\'","");
					arc.setRole(role[0]);
					//System.out.println("Role: ["+this.role+"]");
				}
				
				//getting order
				Pattern porder = Pattern.compile(re.getOrder());
				Matcher morder = porder.matcher(arc.getXmlLine());
				if (morder.find()){
					String[] order = morder.group().split("\\s");
					order[0] = order[0].replace("\"","");
					order[0] = order[0].replace("\'","");
					arc.setOrder(order[0]);
					//System.out.println("order: ["+this.order+"]");
				}
					
			    
			    //getting from
				Pattern pfrom = Pattern.compile(re.getFrom());
				Matcher mfrom = pfrom.matcher(arc.getXmlLine());
				if (mfrom.find()){
					String[] from = mfrom.group().split("\\s");
					from[0] = from[0].replace("\"","");
					from[0] = from[0].replace("\'","");
					arc.setFrom(from[0]);
					//System.out.println("from: ["+this.from+"]");
				}
				
			    //getting to
				Pattern pto = Pattern.compile(re.getTo());
				Matcher mto = pto.matcher(arc.getXmlLine());
				if (mto.find()){
					String[] to = mto.group().split("\\s");
					to[0] = to[0].replace("\"","");
					to[0] = to[0].replace("\'","");
					arc.setTo(to[0]);
					//System.out.println("to: ["+this.to+"]");
				}
				
			    //getting title
				Pattern ptitle = Pattern.compile(re.getTitle());
				Matcher mtitle = ptitle.matcher(arc.getXmlLine());
				if (mtitle.find()){
					String title = mtitle.group().replace("\"","");
					title = title.replace("\'","");
					arc.setTitle(title);
					//System.out.println("title: ["+this.title+"]");
				}
				
			    //getting weight
				Pattern pweight = Pattern.compile(re.getWeight());
				Matcher mweight = pweight.matcher(arc.getXmlLine());
				if (mweight.find()){
					String[] weight = mweight.group().split("\\s");
					weight[0] = weight[0].replace("\"","");
					weight[0] = weight[0].replace("\'","");
					arc.setWeight(weight[0]);
					//System.out.println("weight: ["+this.weight+"]");
				}
				
			    //getting priority
				Pattern ppriority = Pattern.compile(re.getPriority());
				Matcher mpriority = ppriority.matcher(arc.getXmlLine());
				if (mpriority.find()){
					String[] priority = mpriority.group().split("\\s");
					priority[0] = priority[0].replace("\"","");
					priority[0] = priority[0].replace("\'","");
					arc.setPriority(priority[0]);
					//System.out.println("priority: ["+this.priority+"]");
				}
				
			    //getting use
				Pattern puse = Pattern.compile(re.getUse());
				Matcher muse = puse.matcher(arc.getXmlLine());
				if (muse.find()){
					String[] use = muse.group().split("\\s");
					use[0] = use[0].replace("\"","");
					use[0] = use[0].replace("\'","");
					arc.setUse(use[0]);
					//System.out.println("use: ["+this.use+"]");
				}
				j++; //arc position
				arcList.add(arc);
			}
		}
		return arcList;
	}
}
