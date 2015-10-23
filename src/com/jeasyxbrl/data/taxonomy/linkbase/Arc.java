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



public class Arc {
	private int		position= 0;
	private String	xmlLine	=	null;
	private String	type	=	"arc";
	private String	role	=	null;
	private String	order	=	null;
	private String	from	=	null;
	private String	to		=	null;
	private String	title	=	null;
    private String	weight	=	null;
    private String	priority	=	null;
    private String	use		=	null;
    
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getXmlLine() {
		return xmlLine;
	}
	public void setXmlLine(String xmlLine) {
		this.xmlLine = xmlLine;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

	public Arc getArcByLocator(ArrayList<Arc> arcs, Locator loc){
		Arc a = null;
		try{
			searchArc:{
				for (Arc arc: arcs){
					
					if (arc.from.contains(loc.getLabel())){
						a = arc;
						break searchArc;
					}
				}
			}
		}catch(Exception e){
			//<!-- if you need to check details, print -->
			//System.out.println("[Arc.java].[getArcByLocator]: "+e.getLocalizedMessage());
		}
		return a;
	}	
}
