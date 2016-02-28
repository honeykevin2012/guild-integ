package com.game.common.basics.cache;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

public class CacheableCouchBase implements Cache {
	// 循环次数
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 循环次数
	private static final int LOOP_TIME = 3;
	// 超时时间
	private static final int TIME_OUT = 1000;
	/** 缓存时效永久 */
	// private static final int EVICT_TIME = 0; // 永久生效
	/** 缓存时效 1天 */
	public static final int CACHE_EXP_DAY = 3600 * 24;
	/** 缓存时效 1周 */
	public static final int CACHE_EXP_WEEK = 3600 * 24 * 7;
	/** 缓存时效 1月 */
	public static final int CACHE_EXP_MONTH = 3600 * 24 * 30 * 7;

	public static final String COLLECTION_KEYS = "collection_keys";
	private net.rubyeye.xmemcached.MemcachedClient cache;
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public net.rubyeye.xmemcached.MemcachedClient getCache() {
		return cache;
	}

	public void setCache(net.rubyeye.xmemcached.MemcachedClient cache) {
		this.cache = cache;
	}

	public CacheableCouchBase(){  
	      
	} 
	public CacheableCouchBase(String name, net.rubyeye.xmemcached.MemcachedClient cache) {
		Assert.notNull(cache, "Memcache client must not be null");
		this.cache = cache;
		this.name = name;
	}

	@Override
	public void evict(Object key) {
		boolean result = false;
		int loop = 0;
		while (!result && loop < LOOP_TIME) {
			try {
				loop++;
				result = cache.delete(key.toString());
				if (logger.isDebugEnabled())
					logger.debug(" delete " + key + " " + result);
			} catch (TimeoutException e) {
				logger.error(key + " delete cbase, receive TimeoutException, try :" + loop + " times", e);
			} catch (InterruptedException e) {
				logger.error(key + " delete cbase, receive InterruptedException, try :" + loop + " times", e);
			} catch (MemcachedException e) {
				logger.error(key + " delete cbase, receive MemcachedException, try :" + loop + " times", e);
			}
		}
	}

	@Override
	public ValueWrapper get(Object key) {
		Object result = null;
		boolean retry = false;
		int loop = 0;
		ValueWrapper wrapper = null;
		do {
			try {
				loop++;
				result = cache.get(key.toString(), TIME_OUT);
				retry = false;
				if (result != null) {
					wrapper = new SimpleValueWrapper(result);
				}
			} catch (TimeoutException e) {
				logger.error(key + " get from cbase, receive TimeoutException, try :" + loop + " times", e);
				retry = true;
			} catch (InterruptedException e) {
				logger.error(key + " get from cbase, receive InterruptedException, try :" + loop + " times", e);
				retry = true;
			} catch (MemcachedException e) {
				logger.error(key + " get from cbase, receive MemcachedException, try :" + loop + " times", e);
				retry = true;
			}
		} while (retry && loop < LOOP_TIME);

		return wrapper;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		return this.cache;
	}

	@Override
	public void put(Object key, Object value) {
		boolean result = false;
		int loop = 0;
		while (!result && loop < LOOP_TIME) {
			try {
				loop++;
				result = cache.add(key.toString(), CACHE_EXP_DAY, value, TIME_OUT);
				if (logger.isDebugEnabled())
					logger.debug(" add " + key + " " + result);
			} catch (TimeoutException e) {
				logger.error(key + " store into cbase, receive TimeoutException, try :" + loop + " times", e);
			} catch (InterruptedException e) {
				logger.error(key + " store into cbase, receive InterruptedException, try :" + loop + " times", e);
			} catch (MemcachedException e) {
				logger.error(key + " store into cbase, receive MemcachedException, try :" + loop + " times", e);
			}
		}
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		Object result = null;
		boolean retry = false;
		int loop = 0;
		ValueWrapper wrapper = null;
		do {
			try {
				loop++;
				result = cache.get(key.toString(), TIME_OUT);
				retry = false;
				if (result != null) {
					wrapper = new SimpleValueWrapper(result);
				} else {
					cache.add(key.toString(), CACHE_EXP_DAY, value);
				}
			} catch (TimeoutException e) {
				logger.error(key + " get from cbase, receive TimeoutException, try :" + loop + " times", e);
				retry = true;
			} catch (InterruptedException e) {
				logger.error(key + " get from cbase, receive InterruptedException, try :" + loop + " times", e);
				retry = true;
			} catch (MemcachedException e) {
				logger.error(key + " get from cbase, receive MemcachedException, try :" + loop + " times", e);
				retry = true;
			}
		} while (retry && loop < LOOP_TIME);

		return wrapper;
	}

	@Override
	public void clear() {
		try {
			this.cache.flushAll();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> type) {
		try {
			Object cacheValue = this.cache.get(key.toString());
			Object value = (cacheValue != null ? cacheValue : null);
			if (type != null && !type.isInstance(value)) {
				throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
			}
			return (T) value;
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setClient(net.rubyeye.xmemcached.MemcachedClient cache){  
	    this.cache = cache;  
	}  
}