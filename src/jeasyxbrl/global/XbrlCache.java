/**
 * /** 
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
 * @since 2015-10-03
 * @version beta0.0
 * 
 * it was very usefull to understand: https://sites.google.com/site/lipe82/Home/diaadia/cache-java
 */
package jeasyxbrl.global;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import jeasyxbrl.taxonomy.instance.XbrlElement;
import jeasyxbrl.taxonomy.instance.XbrlInstance;
import jeasyxbrl.taxonomy.linkbase.XbrlLabel;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class XbrlCache {
    public static boolean ASC = true;
    public static boolean DESC = false;
    private Cache<Integer, XbrlInstance> xbrlCache; //id key, value

    public XbrlCache() {
        xbrlCache = CacheBuilder.newBuilder().maximumSize(1000).build();
        // mByteCache = CacheBuilder.newBuilder().maximumSize(15).build();
        System.out.println("Initiated a cache with max 10000");
    }

    public void insert(Integer key, XbrlInstance value) {
    	xbrlCache.put(key, value);
    }

    public void remove(Integer key) {
    	xbrlCache.invalidate(key);
    }

    public ArrayList<XbrlInstance> getXbrlInstanceListByCache(){
    	Map<Integer, XbrlInstance> asMap = xbrlCache.asMap();
        // long size = mByteCache.size();
        // Set<Integer> keySet = asMap.keySet();

        int j = 0;
        String value = "";
        ArrayList<XbrlInstance> xiList = new ArrayList<XbrlInstance>();
        for (Entry<Integer, XbrlInstance> entry : asMap.entrySet()) {
        	XbrlInstance xi = entry.getValue();
            xiList.add(xi);
        }
        return xiList;
    }
    
    public void print() throws InterruptedException {

        System.out.println("\nprinting cache: ");

        for (int i = 0; i <= xbrlCache.size(); i++) {
            Map<Integer, XbrlInstance> asMap = xbrlCache.asMap();
            // long size = mByteCache.size();
            // Set<Integer> keySet = asMap.keySet();

            int j = 0;
            String value = "";
            for (Entry<Integer, XbrlInstance> entry : asMap.entrySet()) {
            	XbrlInstance xi = entry.getValue();
                System.out.println("Key : " + entry.getKey() + " Instance : " + xi.getCompany());
                System.out.println("Key : " + entry.getKey() + " Instance : " + xi.getFilename());
                System.out.println("Key : " + entry.getKey() + " Instance : " + xi.getId());
                ArrayList<XbrlElement> xe = xi.getEleList();
                for (int x=0; x<=10;x++){
                	System.out.println("====== Element : " + xe.get(x).getPosition());
                	System.out.println("====== Element Name: " + xe.get(x).getName());
                	System.out.println("====== Element Value: "+xe.get(x).getValue() +"("+ xe.get(x).getUnitRef()+")");
                	try{
                	ArrayList<XbrlLabel> xl = xe.get(x).getLabels();
	                	for (XbrlLabel lab: xl){
	                		System.out.println("====== Element Label(s) : "+lab.getValue());
	                	}
	                	System.out.println("");
                	}catch(Exception e){
                		System.out.println("Config. for presenting no label [jx.loadingInstancesInCache(,,,,,,0)]");
                	}
                }
                System.out.println("");
                System.out.println("");
                j++;
            }
            //System.out.print("\n " + i + " - size [" + j + "]: " + value);
            //Thread.sleep(1000);
        }
    }

}