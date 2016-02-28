package com.game.common.basics.cache;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

public class CacheableCouchBaseManager extends AbstractCacheManager {

	private Collection<Cache> caches;
	private net.rubyeye.xmemcached.MemcachedClient client = null;

	public CacheableCouchBaseManager() {

	}

	public CacheableCouchBaseManager(net.rubyeye.xmemcached.MemcachedClient client) {
		setClient(client);
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		return this.caches;
	}

	public void setCaches(Collection<Cache> caches) {
		this.caches = caches;
	}

	public void setClient(net.rubyeye.xmemcached.MemcachedClient client) {
		this.client = client;
		updateCaches();
	}

	public Cache getCache(String name) {
		checkState();
		Cache cache = super.getCache(name);
		if (cache == null) {
			cache = new CacheableCouchBase(name, client);
			addCache(cache);
		}
		return cache;
	}

	private void checkState() {
		if (client == null) {
			throw new IllegalStateException("MemcacheClient must not be null.");
		}
		// TODO check memcache state
	}

	private void updateCaches() {
		if (caches != null) {
			for (Cache cache : caches) {
				if (cache instanceof CacheableCouchBase) {
					CacheableCouchBase memcacheCache = (CacheableCouchBase) cache;
					memcacheCache.setClient(client);
				}
			}
		}
	}

}