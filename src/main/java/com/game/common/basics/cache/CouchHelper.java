package com.game.common.basics.cache;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;

public class CouchHelper {
	private CouchbaseTemplate template;
	private static CouchHelper instance = null;

	public void setTemplate(CouchbaseTemplate template) {
		this.template = template;
	}
	
	private CouchHelper(){}

	public synchronized static CouchHelper getInstance() {
		if (instance == null) {
			instance = new CouchHelper();
		}
		return instance;
	}

	public void add(String key, Object value) {
		template.add(key, 0, value);
	}
	
	public void add(String key, int exp, Object value) {
		template.add(key, exp, value);
	}
	
	public Object set(String key, Object value) {
		return template.set(key, 0, value);
	}
	
	public Object set(String key, int exp, Object value) {
		return template.set(key, exp, value);
	}
	
	public void delete(String key) {
		template.delete(key);
	}
	
	public void replace(String key, Object value) {
		template.replace(key, value);
	}
	
	public void replace(String key, int exp, Object value) {
		template.replace(key, exp, value);
	}
	
	public void append(String key, Object value) {
		template.append(key, value);
	}
	/**
	 * @param version 版本号,数据一致性控制
	 * @param key
	 * @param value
	 */
	public void append(long version, String key, Object value) {
		template.append(version, key, value);
	}
	
	public void incr(String key, int by) {
		template.incr(key, by);
	}
	
	public void incr(String key, int by, int def) {
		template.incr(key, by, def);
	}
	
	public void incr(String key, long by) {
		template.incr(key, by);
	}
	
	public void incr(String key, long by, long def) {
		template.incr(key, by, def);
	}
	
	public void decr(String key, int by) {
		template.decr(key, by);
	}
	
	public void decr(String key, int by, int def) {
		template.decr(key, by, def);
	}
	
	public void decr(String key, long by) {
		template.decr(key, by);
	}
	
	public void decr(String key, long by, long def) {
		template.decr(key, by, def);
	}
	
	public Object get(String key) {
		return template.get(key);
	}
	/**
	 * 更新一个key的过期时间
	 * @param key
	 * @param exp
	 */
	public void touch(String key, int exp){
		template.touch(key, exp);
	}
	
	public long casunique(String key){
		return template.gets(key).getCas();
	}
	
	public static void main(String[] args) throws IOException {
		ArrayList<URI> serverList = new ArrayList<URI>();
		URI base = null;
		base = URI.create(String.format("http://%s/pools", "localhost:8091"));
		serverList.add(base);
		CouchbaseConnectionFactory cf = new CouchbaseConnectionFactory(serverList, "default", "");
		CouchbaseClient cc = new CouchbaseClient(cf);
		cc.add("test", "kevin");
		cc.set("test", new Date());
		//cc.append("test", "Hello");
		cc.add("kk", "madoka");
		System.out.println(cc.get("test"));
		System.out.println(cc.get("kk"));
	}
}
