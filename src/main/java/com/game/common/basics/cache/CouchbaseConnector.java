package com.game.common.basics.cache;

import java.net.URI;
import java.util.ArrayList;

import com.couchbase.client.CouchbaseConnectionFactory;

public class CouchbaseConnector {
	private String serverAddress;
	private String name;
	private String pwd;

	public CouchbaseConnectionFactory connect() {
		CouchbaseConnectionFactory cf = null;
		try {
			String[] serverNames = serverAddress.split(",");
			ArrayList<URI> serverList = new ArrayList<URI>();
			for (String serverName : serverNames) {
				URI base = null;
				base = URI.create(String.format("http://%s/pools", serverName));
				serverList.add(base);
			}
			cf = new CouchbaseConnectionFactory(serverList, name, pwd);
			return cf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void close() {

	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
