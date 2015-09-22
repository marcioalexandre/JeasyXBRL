package global;

import java.util.ArrayList;

/*
 * This class carries every RE about XBRL taxonomy
 * RE specialist has to work here.
 */
public class XbrlTaxonomyRe {
	//Instance
	private String elementLine 		=	"^(?!<[link:.*|xbrl.*|\\p{Punct}.*|segment.*|context.*])((<.*[-]{1}.*:.*>.*)|(<.*:.*>.*))$";
	private String elementTagName	=	"^<.*:.+\\s";
	private String elementName 		=	"(?<=:)\\w+\\s";
	private String elementCRef		=	"((?<=context[R|r]ef\\p{Punct}{1}).+[\"\'])";
	private String elementURef		=	"((?<=unit[R|r]ef\\p{Punct}{1}).+[\"\'])";
	private String elementDecimals	=	"((?<=decimals\\p{Punct}{1}).+[\"\'])";
	//Elements linkbases from [label, calculation, presentation, definition, reference]
	private String loc				=	"(<.*[Ll]oc\\s.*/>)";
	private String arc				=	"(<.*[Aa]rc\\s.*/>)";
	private String label			=	"(<.*label\\s.*>.*</.*label>)";
	//-Attributes
	private String href				=	"(?<=xlink:href=[\"\']).*(?=[\"\']\\s)";
	private String labelAttr		= 	"(?<=label=[\"\']).*(?=[\"\'])";
	private String role				=	"(?<=role=[\"\']).*(?=[\"\']\\s)";
	private String order			=	"(?<=order=[\"\']).*(?=[\"\']\\s)";
	private String from				= 	"(?<=from=[\"\']).*(?=[\"\']\\s)";
	private String to				=	"(?<=to=[\"\']).*(?=[\"\'])";
	private String title			=	"(?<=title=[\"\']).+(?=[\"\'])";
	private String weight			=	"(?<=weight=[\"\']).*(?=[\"\']\\s)";
	private String priority			=	"(?<=priority=[\"\']).*(?=[\"\']\\s)";
	private String use				=	"(?<=use=[\"\']).*(?=[\"\'])";
	private String id				=	"(?<=id=[\"\']).*(?=[\"\'])";
	private String value			=	"(?<=>).+(?=</"; //you need to close the last "(".
	private String lang				=	"(?<=lang=[\"\']).*(?=[\"\'])";
	/*
	 * linkbases.others [geo, formula]
	 * User can put new elements (and their RE) from new linkbases.
	 */
	private ArrayList<String> newLinkEle	=	null;
	private ArrayList<String> newLinkEleRe	= 	null;
	public String getElementLine() {
		return elementLine;
	}
	public void setElementLine(String elementLine) {
		this.elementLine = elementLine;
	}
	public String getElementTagName() {
		return elementTagName;
	}
	public void setElementTagName(String elementTagName) {
		this.elementTagName = elementTagName;
	}
	public String getElementName() {
		return elementName;
	}
	public void setName(String elementName) {
		this.elementName = elementName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String elementId) {
		this.id = elementId;
	}
	public String getElementCRef() {
		return elementCRef;
	}
	public void setElementCRef(String elementCRef) {
		this.elementCRef = elementCRef;
	}
	public String getElementURef() {
		return elementURef;
	}
	public void setElementURef(String elementURef) {
		this.elementURef = elementURef;
	}
	public String getElementDecimals() {
		return elementDecimals;
	}
	public void setElementDecimals(String elementDecimals) {
		this.elementDecimals = elementDecimals;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getArc() {
		return arc;
	}
	public void setArc(String arc) {
		this.arc = arc;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getLabelAttr() {
		return labelAttr;
	}
	public void setLabelAttr(String labelAttr) {
		this.labelAttr = labelAttr;
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
	public ArrayList<String> getNewLinkEle() {
		return newLinkEle;
	}
	public void setNewLinkEle(ArrayList<String> newLinkEle) {
		this.newLinkEle = newLinkEle;
	}
	public ArrayList<String> getNewLinkEleRe() {
		return newLinkEleRe;
	}
	public void setNewLinkEleRe(ArrayList<String> newLinkEleRe) {
		this.newLinkEleRe = newLinkEleRe;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
		
}
