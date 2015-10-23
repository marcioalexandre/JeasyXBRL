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
//  @since 2015-10-03

//  @reference https://sites.google.com/site/lipe82/Home/diaadia/cache-java

package com.jeasyxbrl.global;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jeasyxbrl.JeasyXbrl;
import com.jeasyxbrl.data.user.User;

public class XbrlCache {
    public static boolean ASC = true;
    public static boolean DESC = false;
    private Cache<Integer, JeasyXbrlData> xbrlCache; //id key, value

    public XbrlCache() {
    	//maximuSize is number of data in cache
        xbrlCache = CacheBuilder.newBuilder().maximumSize(100).build();
     }

    public void insert(Integer key, JeasyXbrlData value) {
    	xbrlCache.put(key, value);
    	System.out.println("Setting data in cache...");
    }

    public void remove(User user) {
    	xbrlCache.invalidate(user);
    }

    public ArrayList<JeasyXbrlData> getXbrlData(User user){
    	Map<Integer, JeasyXbrlData> asMap = xbrlCache.asMap();
        ArrayList<JeasyXbrlData> dataList = new ArrayList<JeasyXbrlData>();
        for (Entry<Integer, JeasyXbrlData> entry : asMap.entrySet()) {
        	JeasyXbrlData jxd = entry.getValue();
        	if (jxd.user.equals(user)){
        		dataList.add(jxd);
        	}else{}
        }
        return dataList;
    }
    
    public void printFromCache(User user) throws InterruptedException {
    	JeasyXbrl jx = new JeasyXbrl();
    	jx.print(this.getXbrlData(user));
    }
    

}