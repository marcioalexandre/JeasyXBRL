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
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeasyxbrl.conn.Connection;
import com.jeasyxbrl.global.XbrlTaxonomyRe;


public class XbrlLabel implements XbrlLinkbaseElement {
	private int	    position=	0;
	private String 	xmlLine	=	null;
	private String	type	=	"resource";
	private String	labelattr	=	null;
	private String	lang	=	null;
	private String	role	=	null;
	private String	id		=	null;
	private String	value	=	null;
	
	//getters and setters
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return labelattr;
	}
	public void setLabel(String labelattr) {
		this.labelattr = labelattr;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	public String getLabelattr() {
		return labelattr;
	}
	public void setLabelattr(String labelattr) {
		this.labelattr = labelattr;
	}
	/* (non-Javadoc)
	 * @see jeasyxbrl.taxonomy.linkbase.LinkbaseElement#getLinkbaseElementFromFile(java.lang.String)
	 */
	public ArrayList<XbrlLabel> getLinkbaseElementFromFile(
			String filename) {
		Connection conn = new Connection();
		BufferedReader br = conn.getConnection(filename);
		String currentLine;
		ArrayList<XbrlLabel> labelList = new ArrayList<XbrlLabel>();
		XbrlTaxonomyRe re = new XbrlTaxonomyRe();
		int j=0; //position
		try {
			while ((currentLine = br.readLine()) != null){
				currentLine = currentLine.trim();
				XbrlLabel label = new XbrlLabel();

				//setting label element
				Pattern plabel = Pattern.compile(re.getLabel());
				Matcher mlabel = plabel.matcher(currentLine);
				if (mlabel.find()){
					//setting position
					label.setPosition(j);
					//getting XML line
					label.setXmlLine(mlabel.group());;
					//setting labelAttr
					Pattern plabelA = Pattern.compile(re.getLabelAttr());
					Matcher mlabelA = plabelA.matcher(label.getXmlLine());
					if (mlabelA.find()){
						String[] labelA = mlabelA.group().split("\\s");
						labelA[0] 		= labelA[0].replace("\"","");
						labelA[0] 		= labelA[0].replace("\'","");
						label.setLabelattr(labelA[0]);
					}
					//setting Id
					Pattern pid = Pattern.compile(re.getId());
					Matcher mid = pid.matcher(label.getXmlLine());
					if (mid.find()){
						String[] id = mid.group().split("\\s");
						id[0] 		= id[0].replace("\"","");
						id[0] 		= id[0].replace("\'","");
						label.setId(id[0]);
					}
					//setting role
					Pattern prole = Pattern.compile(re.getRole());
					Matcher mrole = prole.matcher(label.getXmlLine());
					if (mrole.find()){
						String[] role = mrole.group().split("\\s");
						role[0] = role[0].replace("\"","");
						role[0] = role[0].replace("\'","");
						label.setRole(role[0]);
					}
					//setting lang
					Pattern plang = Pattern.compile(re.getLang());
					Matcher mlang = plang.matcher(label.getXmlLine());
					if (mlang.find()){
						String[] lang = mlang.group().split("\\s");
						lang[0] = lang[0].replace("\"","");
						lang[0] = lang[0].replace("\'","");
						label.setLang(lang[0]);
					}
					//setting Element Value
					Pattern pv	= Pattern.compile(re.getValue()+".*label>)");
					Matcher mv	= pv.matcher(label.getXmlLine());
					if (mv.find()){
						label.setValue(mv.group());
					}
					j++;				
					labelList.add(label);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return labelList;
	}
	
	/* (non-Javadoc)
	 * @see jeasyxbrl.taxonomy.linkbase.LinkbaseElement#getLinkbaseElementByArc(java.util.ArrayList, jeasyxbrl.taxonomy.linkbase.Arc)
	 */
	public ArrayList<? extends Object> getLinkbaseElementByArc(
			ArrayList<? extends Object> objects, Arc arc) {
		ArrayList<XbrlLabel> labList = new ArrayList<XbrlLabel>();
		@SuppressWarnings("unchecked")
		ArrayList<XbrlLabel> labels = (ArrayList<XbrlLabel>) objects;
		try{
			for (XbrlLabel lab: labels){
				if (!lab.getLabelattr().equals(null) || lab.getLabelattr() != null){
					if (lab.getLabelattr().contains(arc.getTo())){
						labList.add(lab);
					}
				}
			}
		}catch(Exception e){
			//<!-- if you need to check details, print -->
			//System.out.println("Exception [jeasyxbrl].[linkbase].[Label.java]: "+e.getMessage());
		}
		return labList;
	}
	
	public void print(){
		System.out.println("  :::::::::::::::::::::");
		try{
			System.out.println("  :: Lang  ["+this.getLang()+"]");
			System.out.println("  :: Value ["+this.getValue()+"]");
			System.out.println("  :: Id    ["+this.getId()+"]");
			System.out.println("  :: Label ["+this.getLabel()+"]");
			System.out.println("  :: Role  ["+this.getRole()+"]");
			System.out.println("  :: Type  ["+this.getType()+"]");
			System.out.println("  :::::::::::::::::::::");
		}catch(Exception e){
			System.out.println("::::::: No label or erro: "+e.getMessage());
		}
		System.out.println("  :::::::::::::::::::::");
	}
}
