package com.game.common.basics.cache;

import java.io.IOException;

import com.couchbase.client.CouchbaseClient;

public class CouchbaseTemplate extends CouchbaseClient{
	public CouchbaseTemplate(CouchbaseConnector couchbaseConnector) throws IOException{
		super(couchbaseConnector.connect());
	}
}
