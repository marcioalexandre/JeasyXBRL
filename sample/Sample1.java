package sample;

import instance.XbrlElement;

import java.util.ArrayList;

/*
 * just a how-to
 */
public class Sample1 {

	public static void main(String[] args) {
		XbrlElement ele	= new XbrlElement();
		ArrayList<ArrayList<XbrlElement>> eleList = new ArrayList<ArrayList<XbrlElement>>();
		
		//setting instances
		ArrayList<String> instances = new ArrayList<String>();
		instances.add("../xbrlfiles/fb/fb-20130331.xml");
		instances.add("../xbrlfiles/fb/fb-20130630.xml");
		instances.add("../xbrlfiles/fb/fb-20130930.xml");
		instances.add("../xbrlfiles/fb/fb-20131231.xml");
		
		//setting labels
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("../xbrlfiles/fb/fb-20130331_lab.xml");
		labels.add("../xbrlfiles/fb/fb-20130630_lab.xml");
		labels.add("../xbrlfiles/fb/fb-20130930_lab.xml");
		labels.add("../xbrlfiles/fb/fb-20131231_lab.xml");
		
		//calling the method to load every xbrl element in memory
		long start = System.currentTimeMillis();
		long end = 0;
		try {
			eleList = ele.getAllElementsByFiles(instances,labels,null,null,null,null,null,1); //with linkbase
			end = System.currentTimeMillis();
			//System.out.println(elelist.size());
			ele.printElements(eleList);
		} catch (Throwable e) {
			System.out.println("Erro com.jeasyxbrl.sample.Sample1: "+e.getMessage());
			e.printStackTrace();
		}
		System.out.print("Performance Time: ");
		System.out.println(end-start);
	}

}
