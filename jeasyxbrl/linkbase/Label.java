/**
 This software is subject to the terms of the GNU GENERAL PUBLIC LICENSE
 Agreement, available at the following URL:
 http://www.gnu.org/licenses/gpl.txt.
 You must accept the terms of that agreement to use this software.

 Copyright (C) 2015 Marcio Alexandre Pereira da Silva
 All Rights Reserved.
 * @author Marcio Alexandre
 * @email marcio.alexandre83@gmail.com
 * @since 2015-09-19
 * @version beta0.0
 */

package jeasyxbrl.linkbase;
import jeasyxbrl.global.XbrlTaxonomyRe;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jeasyxbrl.conn.Connection;


public class Label {
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
	
	public ArrayList<Label> getLabelsFromFile(String file) throws IOException{
		Connection conn = new Connection();
		BufferedReader br = conn.getConnection(file);
		String currentLine;
		ArrayList<Label> labelList = new ArrayList<Label>();
		XbrlTaxonomyRe re = new XbrlTaxonomyRe();
		int j=0; //position
		while ((currentLine = br.readLine()) != null){
			currentLine = currentLine.trim();
			Label label = new Label();

			//getting label element
			Pattern plabel = Pattern.compile(re.getLabel());
			Matcher mlabel = plabel.matcher(currentLine);
			if (mlabel.find()){
				//getting position
				label.position = j;
				
				//getting XML line
				label.xmlLine = mlabel.group();
				//System.out.println();
				//System.out.println(this.xmlLine);
			
				//getting labelAttr
				Pattern plabelA = Pattern.compile(re.getLabelAttr());
				Matcher mlabelA = plabelA.matcher(label.xmlLine);
				if (mlabelA.find()){
					String[] labelA = mlabelA.group().split("\\s");
					labelA[0] 		= labelA[0].replace("\"","");
					labelA[0] 		= labelA[0].replace("\'","");
					label.labelattr	= labelA[0];
					//System.out.println("label attribute: ["+this.labelattr+"]");
				}
				
				//getting Id
				Pattern pid = Pattern.compile(re.getId());
				Matcher mid = pid.matcher(label.xmlLine);
				if (mid.find()){
					String[] id = mid.group().split("\\s");
					id[0] 		= id[0].replace("\"","");
					id[0] 		= id[0].replace("\'","");
					label.id 	= id[0];
					//System.out.println("id attribute: ["+this.id+"]");
				}
				
				//getting role
				Pattern prole = Pattern.compile(re.getRole());
				Matcher mrole = prole.matcher(label.xmlLine);
				if (mrole.find()){
					String[] role = mrole.group().split("\\s");
					role[0] = role[0].replace("\"","");
					role[0] = role[0].replace("\'","");
					label.role = role[0];
					//System.out.println("Role: ["+this.role+"]");
				}
				
				//getting lang
				Pattern plang = Pattern.compile(re.getLang());
				Matcher mlang = plang.matcher(label.xmlLine);
				if (mlang.find()){
					String[] lang = mlang.group().split("\\s");
					lang[0] = lang[0].replace("\"","");
					lang[0] = lang[0].replace("\'","");
					label.lang = lang[0];
					//System.out.println("lang: ["+this.lang+"]");
				}
				
				//get Element Value
				Pattern pv	= Pattern.compile(re.getValue()+".*label>)");
				Matcher mv	= pv.matcher(label.xmlLine);
				if (mv.find()){
					//System.out.println("Value: ["+mv.group()+"]");
					label.value = mv.group();
				}
				j++;				
				labelList.add(label);
			}
		}
		return labelList;
	}
	
	public ArrayList<Label> getLabelsByArc(ArrayList<Label> labels, Arc arc){
		ArrayList<Label> labList = new ArrayList<Label>();
		try{
			for (Label lab: labels){
				if (!lab.labelattr.equals(null)){
					if (lab.labelattr.contains(arc.getTo())){
						labList.add(lab);
					}
				}
			}
		}catch(Exception e){
			System.out.println("Exception [jeasyxbrl].[linkbase].[Label.java]: "+e.getMessage());
		}
		return labList;
		/* improving the performance in the labelList [Sep21st,2015]
		// min of label position is ((arc.position*2)-50);
		int min =(arc.getPosition()*2-50);
		if (min < 0){
			min = 0;
		}
		// max of label position is ((arc.position*2)+50);
		int max= (arc.getPosition()*2+50);
		if (max > labels.size()){
			max = labels.size();
		}
		
		for (int i = min; i< max;i++){
			if (labels.get(i).labelattr.contains(arc.getTo())){
				System.out.println("[label.java] label position: "+labels.get(i).position);
				labList.add(labels.get(i));
			}
		}
		*/
	}

	/* 
	 * Label tester
	 * 
	public static void main(String[] args) throws IOException{
		//String file = "../../../../../xbrlfiles/apple/aapl-20150627_lab.xml";
		//String file = "../../../../../xbrlfiles/msft/msft-20131231_lab.xml";
		//String file = "../../../../../xbrlfiles/ifrs/lab_IFRSF_2014-05-22.xml"; //label ok!
		//String file = "../../../../../xbrlfiles/bobs/bobs-20120930_lab.xml";
		//String file = "../../../../../xbrlfiles/oxfo/oxfo-20130930_lab.xml"; //label ok!
		String file = "../../../../../xbrlfiles/fb/fb-20131231_lab.xml"; //label ok!
		Label lab = new Label();
		ArrayList<Label> labList = lab.getLabelsFromFile(file);
	}
	*/
}
