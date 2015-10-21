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
//  @since 2015-09-19, updated 2015-10-10


package sample;
import java.util.ArrayList;

import com.jeasyxbrl.JeasyXbrl;
import com.jeasyxbrl.data.user.User;
import com.jeasyxbrl.global.JeasyXbrlData;
/*
 * just a how-to
 */
public class index {

	public static void main(String[] args) {
		JeasyXbrl jx = new JeasyXbrl();
		JeasyXbrlData jxd = new JeasyXbrlData();
		
		for (int i=0;i<1;i++){
			User user = new User();
			user.setName("Marcio");
			user.setEmail("marcio.alexandre83@gmail.com");
			if (user == null || user.equals(null)){
				System.out.println("empty");	
			}else{
				jxd.setUser(user);
			}

			ArrayList<String> instances = new ArrayList<String>();
			instances.add("../xbrlfiles/mwog/mwog-20141031.xml");
			instances.add("../xbrlfiles/fb/fb-20130630.xml");
			instances.add("../xbrlfiles/fb/fb-20130930.xml");
			instances.add("../xbrlfiles/mobq/mobq-20140930.xml");
			instances.add("../xbrlfiles/fb/fb-20131231.xml");
		
			ArrayList<String> labels = new ArrayList<String>();
			labels.add("../xbrlfiles/mwog/mwog-20141031_lab.xml");
			labels.add("../xbrlfiles/fb/fb-20130630_lab.xml");
			labels.add("../xbrlfiles/fb/fb-20130930_lab.xml");
			labels.add("../xbrlfiles/mobq/mobq-20140930_lab.xml");
			labels.add("../xbrlfiles/fb/fb-20131231_lab.xml");
				
			long start = System.currentTimeMillis();
			long end = 0;
			try {
				jx.loadingXbrlData(
					jxd.getUser(),
					instances, 	//XBRL instances
					labels, 	//XBRL labels files
					null,
					null,
					null,
					null,
					null,
					1); //[1]with linkbase analysis, [0] without linkbase analysis
				end = System.currentTimeMillis();
				//jx.printElementsByCache(); 
				//ArrayList<JeasyXbrlData> jxdList = jx.getXbrlData(user);
				//System.out.println("Size from cache:"+jxdList.size());
				//jx.print(jxdList);
				jx.printCache(user);
			} catch (Throwable e) {
				e.printStackTrace();
			}

			System.out.print("Performance Time (milliseconds): ");
			System.out.println(end-start);
					
			System.out.println(jx.printCopyright());
		}

	}
}
