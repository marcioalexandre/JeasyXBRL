/**
This file is part of jeasyXBRL.

jeasyXBRL is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

jeasyXBRL is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with jeasyXBRL.  If not, see <http://www.gnu.org/licenses/>.

 Copyright (C) 2015 Marcio Alexandre Pereira da Silva
 All Rights Reserved.
 
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
		 * This sample is handling financial documents from Facebook
		 * You can get this financial files in SEC website 
		 */
		
		// XBRL instances
		ArrayList<String> instances = new ArrayList<String>();
		instances.add("/xbrlfiles/fb/fb-20130331.xml");
		instances.add("/xbrlfiles/fb/fb-20130630.xml");
		instances.add("/xbrlfiles/fb/fb-20130930.xml");
		instances.add("/xbrlfiles/fb/fb-20131231.xml");
		instances.add("/xbrlfiles/fb/fb-20130331.xml");
		//XBRL label linkbases
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("/xbrlfiles/fb/fb-20130331_lab.xml");
		labels.add("/xbrlfiles/fb/fb-20130630_lab.xml");
		labels.add("/xbrlfiles/fb/fb-20130930_lab.xml");
		labels.add("/xbrlfiles/fb/fb-20131231_lab.xml");
		labels.add("/xbrlfiles/fb/fb-20130331_lab.xml");
		
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
			jx.printElements(eleList);
		} catch (Throwable e) {
			System.out.println("Exception [jeasyxbrl].[sample].[index.java]: ["+e.getMessage()+"]");
			e.printStackTrace();
		}
		System.out.print("Performance Time (milliseconds): ");
		System.out.println(end-start);
		System.out.println(jx.printCopyright());
	}

}
