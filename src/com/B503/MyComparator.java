package com.B503;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



public class MyComparator  {
	
	public static <K,V extends Comparable<? super V>> 
    List<Entry<K, V>> entriesSortedByValues(Map<K,V> map) {

List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());

Collections.sort(sortedEntries, 
    new Comparator<Entry<K,V>>() {
        @Override
        public int compare(Entry<K,V> e1, Entry<K,V> e2) {
            return e1.getValue().compareTo(e2.getValue());
        }
    }
);

System.out.println("Sorted Entries are"+sortedEntries);
return sortedEntries;
}
}
