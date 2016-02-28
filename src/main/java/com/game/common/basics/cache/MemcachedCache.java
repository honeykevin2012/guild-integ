package com.game.common.basics.cache;

import java.util.concurrent.TimeoutException;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;



public class MemcachedCache implements Cache{
	
	private com.google.code.ssm.Cache cache;
	
	public com.google.code.ssm.Cache getCache() {
		return cache;
	}

	public void setCache(com.google.code.ssm.Cache cache) {
		this.cache = cache;
	}

	@Override
	public String getName() {
		return this.cache.getName();
	}

	@Override
	public Object getNativeCache() {
		return this.cache;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object object = null;
		try {
			object = this.cache.get(key.toString(), SerializationType.JAVA);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return (object != null ? new SimpleValueWrapper(object) : null);
	}

	@Override
	public void put(Object key, Object value) {
		try {
			this.cache.set(key.toString(), 86400, value, SerializationType.JAVA);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void evict(Object key) {
		try {
			this.cache.delete((String)key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clear() {
		try {
			this.cache.flush();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> type) {
		try {
			Object cacheValue = this.cache.get((String)key, SerializationType.JAVA);
			Object value = (cacheValue != null ? cacheValue : null);
			if (type != null && !type.isInstance(value)) {
				throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
			}
			return (T) value;
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		ValueWrapper wrapper = null;
		try {
			Object object = this.cache.get((String)key, SerializationType.JAVA);
			if(object != null){
				wrapper = new SimpleValueWrapper(object);
			}else {
				this.cache.set((String)key, 86400, value, SerializationType.JAVA);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return wrapper;
	}
}

