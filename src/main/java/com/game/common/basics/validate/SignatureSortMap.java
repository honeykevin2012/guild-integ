package com.game.common.basics.validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SignatureSortMap {

	private Map<String, Object> map = new HashMap<String, Object>();
	private Set<String> keySet = map.keySet();

	public Object get(String key) {
		return map.get(key);
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public Set<String> keySet() {
		return this.keySet;
	}
	
	public void remove(String key){
		this.map.remove(key);
	}
	
	public boolean containsKey(String key){
		return map.containsKey(key);
	}

	public void sort() {
		List<String> list = new ArrayList<String>(map.keySet());
		Collections.sort(list, new Comparator<String>() {
			public int compare(String a, String b) {
				return a.compareTo(b);
			}
		});
		this.keySet = new TreeSet<String>(list);
	}
	
	public static void main(String[] args) {
		SignatureSortMap map = new SignatureSortMap();
		map.put("userId", 1001);
        map.put("apoint", "0");
        map.put("mac", "11:22:33");
        map.put("yes", "rrrrrr");
        map.put("model", "9300");
        map.put("date", "2012");
        map.sort();
        System.out.println(map.keySet);
	}
}