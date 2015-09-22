/**
 * @author Marcio Alexandre
 * @email marcio.alexandre83@gmail.com
 * @since 2015-09-22
 * @version beta0.0
 */
package sample;
import jeasyxbrl.JeasyXbrl;
import jeasyxbrl.instance.XbrlElement;
import java.util.ArrayList;
/*
 * just a how-to
 */
public class index {

	public static void main(String[] args) {
		JeasyXbrl jx = new JeasyXbrl();
		/*
		 * This sample is setting financial documents from Microsft
		 * You can get them in SEC website 
		 */
		
		// XBRL instances
		ArrayList<String> instances = new ArrayList<String>();
		instances.add("/xbrlfiles/msft/msft-20130331.xml");
		instances.add("/xbrlfiles/msft/msft-20130630.xml");
		instances.add("/xbrlfiles/msft/msft-20130930.xml");
		instances.add("/xbrlfiles/msft/msft-20131231.xml");
		//XBRL label linkbases
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("/xbrlfiles/msft/msft-20130331_lab.xml");
		labels.add("/xbrlfiles/msft/msft-20130630_lab.xml");
		labels.add("/xbrlfiles/msft/msft-20130930_lab.xml");
		labels.add("/xbrlfiles/msft/msft-20131231_lab.xml");
		
		//calling the method to load every XBRL data in memory
		ArrayList<ArrayList<XbrlElement>> eleList = new ArrayList<ArrayList<XbrlElement>>(); 
		long start = System.currentTimeMillis();
		long end = 0;
		try {
			eleList = jx.getAllElements(
					instances, 	//XBRL instances
					labels, 	//XBRL labels files
					null,
					null,
					null,
					null,
					null,
					1); //with linkbase
			end = System.currentTimeMillis();
			//System.out.println(elelist.size());
			jx.printElements(eleList);
		} catch (Throwable e) {
			System.out.println("Exception [jeasyxbrl].[sample].[index.java]: ["+e.getMessage()+"]");
			e.printStackTrace();
		}
		System.out.print("Performance Time (milliseconds): ");
		System.out.println(end-start);
	}

}
