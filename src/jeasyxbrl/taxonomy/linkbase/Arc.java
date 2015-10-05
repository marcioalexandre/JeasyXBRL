/**
 This software is subject to the terms of the GNU GENERAL PUBLIC LICENSE
 Agreement, available at the following URL:
 http://www.gnu.org/licenses/gpl.txt.
 You must accept the terms of that agreement to use this software.

 Copyright (C) 2015 Marcio Alexandre Pereira da Silva
 All Rights Reserved.
 * @author Marcio Alexandre
 * @email marcio.alexandre83@gmail.com
 * @since 2015-09-19, update 2015-10-03
 * @version beta0.0
 */
package jeasyxbrl.taxonomy.linkbase;
import jeasyxbrl.global.XbrlTaxonomyRe;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jeasyxbrl.conn.Connection;


public class Arc {
	private int		position= 0;
	private String	xmlLine	=	null;
	private String	type	=	"arc";
	private String	role	=	null;
	private String	order	=	null;
	private String	from	=	null;
	private String	to		=	null;
	private String	title	=	null;
    //calculationarc/definitionarc
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
				arc.position = j;
				
				//getting XML line
				arc.xmlLine = marc.group();
				//System.out.println();
				//System.out.println(this.xmlLine);
			
				//getting role
				Pattern prole = Pattern.compile(re.getRole());
				Matcher mrole = prole.matcher(arc.xmlLine);
				if (mrole.find()){
					String[] role = mrole.group().split("\\s");
					role[0] = role[0].replace("\"","");
					role[0] = role[0].replace("\'","");
					arc.role = role[0];
					//System.out.println("Role: ["+this.role+"]");
				}
				
				//getting order
				Pattern porder = Pattern.compile(re.getOrder());
				Matcher morder = porder.matcher(arc.xmlLine);
				if (morder.find()){
					String[] order = morder.group().split("\\s");
					order[0] = order[0].replace("\"","");
					order[0] = order[0].replace("\'","");
					arc.order = order[0];
					//System.out.println("order: ["+this.order+"]");
				}
					
			    
			    //getting from
				Pattern pfrom = Pattern.compile(re.getFrom());
				Matcher mfrom = pfrom.matcher(arc.xmlLine);
				if (mfrom.find()){
					String[] from = mfrom.group().split("\\s");
					from[0] = from[0].replace("\"","");
					from[0] = from[0].replace("\'","");
					arc.from = from[0];
					//System.out.println("from: ["+this.from+"]");
				}
				
			    //getting to
				Pattern pto = Pattern.compile(re.getTo());
				Matcher mto = pto.matcher(arc.xmlLine);
				if (mto.find()){
					String[] to = mto.group().split("\\s");
					to[0] = to[0].replace("\"","");
					to[0] = to[0].replace("\'","");
					arc.to = to[0];
					//System.out.println("to: ["+this.to+"]");
				}
				
			    //getting title
				Pattern ptitle = Pattern.compile(re.getTitle());
				Matcher mtitle = ptitle.matcher(arc.xmlLine);
				if (mtitle.find()){
					String title = mtitle.group().replace("\"","");
					title = title.replace("\'","");
					arc.title = title;
					//System.out.println("title: ["+this.title+"]");
				}
				
			    //getting weight
				Pattern pweight = Pattern.compile(re.getWeight());
				Matcher mweight = pweight.matcher(arc.xmlLine);
				if (mweight.find()){
					String[] weight = mweight.group().split("\\s");
					weight[0] = weight[0].replace("\"","");
					weight[0] = weight[0].replace("\'","");
					arc.weight = weight[0];
					//System.out.println("weight: ["+this.weight+"]");
				}
				
			    //getting priority
				Pattern ppriority = Pattern.compile(re.getPriority());
				Matcher mpriority = ppriority.matcher(arc.xmlLine);
				if (mpriority.find()){
					String[] priority = mpriority.group().split("\\s");
					priority[0] = priority[0].replace("\"","");
					priority[0] = priority[0].replace("\'","");
					arc.priority = priority[0];
					//System.out.println("priority: ["+this.priority+"]");
				}
				
			    //getting use
				Pattern puse = Pattern.compile(re.getUse());
				Matcher muse = puse.matcher(arc.xmlLine);
				if (muse.find()){
					String[] use = muse.group().split("\\s");
					use[0] = use[0].replace("\"","");
					use[0] = use[0].replace("\'","");
					arc.use = use[0];
					//System.out.println("use: ["+this.use+"]");
				}
				j++; //arc position
				arcList.add(arc);
			}
		}
		return arcList;
	}

	public Arc getArcByLocator(ArrayList<Arc> arcs, Locator loc){
		Arc a = null;
		int i=0;
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
	
	/*
	 * Arc tester
	 * 
	public static void main(String[] args) throws IOException{
		String file = "../../../../../xbrlfiles/apple/aapl-20150627_lab.xml";
		//String file = "../../../../../xbrlfiles/msft/msft-20131231_lab.xml";
		//String file = "../../../../../xbrlfiles/ifrs/lab_IFRSF_2014-05-22.xml";
		//String file = "../../../../../xbrlfiles/bobs/bobs-20120930_lab.xml";
		//String file = "../../../../../xbrlfiles/oxfo/oxfo-20130930_lab.xml";
		//String file = "../../../../../xbrlfiles/fb/fb-2013131_lab.xml";
		Arc arc = new Arc();
		ArrayList<Arc> arcList = arc.getArcsFromFile(file);
		for (Arc arc1: arcList){
			//System.out.println(arc1.from);
			if (arc1.from.contains("aapl_StockRepurchaseProgramLineItems")){
				System.out.println("[Arc.java] "+arc1.from);
			}
		}
	}
	*/
	
}
