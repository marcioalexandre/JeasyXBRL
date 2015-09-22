package linkbase;

import global.XbrlTaxonomyRe;

import java.util.ArrayList;

public class Other {
	/*
	 * marcio.alexandre83@gmail.com - 20/09/2015
	 * This class is for future linkbase which is not considered in the
	 * SEC XBRL, as geoXBRL, formula, etc...
	 */
	private ArrayList<XbrlTaxonomyRe> LinkbaseEle = null;	
	/*
	 * User can inform the element names (and their RE - for getting them -), 
	 * which is contained into future Linkbase file (geoXBRL, formula).
	 */
	public ArrayList<XbrlTaxonomyRe> getLinkbaseEle() {
		return LinkbaseEle;
	}
	public void setLinkbaseEle(ArrayList<XbrlTaxonomyRe> linkbaseEle) {
		LinkbaseEle = linkbaseEle;
	}

}
