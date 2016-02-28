package com.game.common.basics.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.common.basics.ApplicationContextLoader;

public class CbaseHelper {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//循环次数
	private static final int LOOP_TIME = 3;
	//超时时间
	private static final int TIME_OUT = 1000;
	/**缓存时效永久 */
    private static final int EVICT_TIME = 0; // 永久生效
    /**缓存时效 1天 */  
    public static final int CACHE_EXP_DAY = 3600 * 24;
    /**缓存时效 1周 */  
    public static final int CACHE_EXP_WEEK = 3600 * 24 * 7;
    /**缓存时效 1月 */  
    public static final int CACHE_EXP_MONTH = 3600 * 24 * 30 * 7;
    
    public static final String COLLECTION_KEYS = "collection_keys";

    private MemcachedClient memcachedClient = (MemcachedClient) ApplicationContextLoader.getBean("memcachedClient");
	private CbaseHelper(){}
	
	private static CbaseHelper instance = null;

	public synchronized static CbaseHelper getInstance() {
		if (instance == null) {
			instance = new CbaseHelper();
		}
		return instance;
	}

	public boolean set(String key, int time, Object object) {
		boolean result = false;
		int loop = 0;
		while (!result && loop < LOOP_TIME) {
			try {
				loop++;
				result = memcachedClient.set(key, time, object, TIME_OUT);
                if(logger.isDebugEnabled()) logger.debug(" set "+key+" "+result);
			} catch (TimeoutException e) {
				logger.error(key + " store into cbase, receive TimeoutException, try :" + loop + " times", e);
			} catch (InterruptedException e) {
				logger.error(key + " store into cbase, receive InterruptedException, try :" + loop + " times", e);
			} catch (MemcachedException e) {
				logger.error(key + " store into cbase, receive MemcachedException, try :" + loop + " times", e);
			}
		}
		return result;
	}

	public boolean add(String key, int time, Object object) {
		boolean result = false;
		int loop = 0;
		while (!result && loop < LOOP_TIME) {
			try {
				loop++;
				result = memcachedClient.add(key, time, object, TIME_OUT);
                if(logger.isDebugEnabled()) logger.debug(" add "+key+" "+result);
			} catch (TimeoutException e) {
				logger.error(key + " store into cbase, receive TimeoutException, try :" + loop + " times", e);
			} catch (InterruptedException e) {
				logger.error(key + " store into cbase, receive InterruptedException, try :" + loop + " times", e);
			} catch (MemcachedException e) {
				logger.error(key + " store into cbase, receive MemcachedException, try :" + loop + " times", e);
			}
		}
		return result;
	}
	
	public boolean replace(String key, int time, Object object) {
		boolean result = false;
		int loop = 0;
		while (!result && loop < LOOP_TIME) {
			try {
				loop++;
				result = memcachedClient.replace(key, time, object, TIME_OUT);
                if(logger.isDebugEnabled()) logger.debug(" add "+key+" "+result);
			} catch (TimeoutException e) {
				logger.error(key + " replace into cbase, receive TimeoutException, try :" + loop + " times", e);
			} catch (InterruptedException e) {
				logger.error(key + " replace into cbase, receive InterruptedException, try :" + loop + " times", e);
			} catch (MemcachedException e) {
				logger.error(key + " replace into cbase, receive MemcachedException, try :" + loop + " times", e);
			}
		}
		return result;
	}
	public boolean cas(String key, int time, final Object newValue) {
		boolean result = false;
			try {
				result = memcachedClient.cas(key, 0, new CASOperation<Object>() {
                    @Override
                    public int getMaxTries() {
                        return LOOP_TIME;  //max try times
                    }
                    @Override
                    public Object getNewValue(long currentCAS, Object currentValue) {                   
                        return newValue; //return new value to update
                    }
                });
                if(logger.isDebugEnabled()) logger.debug(" cas "+key+" "+result);
			} catch (TimeoutException e) {
				logger.error(key + " store into cbase, receive TimeoutException", e);
			} catch (InterruptedException e) {
				logger.error(key + " store into cbase, receive InterruptedException", e);
			} catch (MemcachedException e) {
				logger.error(key + " store into cbase, receive MemcachedException", e);
			}
		return result;
	}
	public boolean delete(String key){
		boolean result = false;
		int loop = 0;
		while (!result && loop < LOOP_TIME) {
			try {
				loop++;
				result = memcachedClient.delete(key);
                if(logger.isDebugEnabled()) logger.debug(" delete "+key+" "+result);
			} catch (TimeoutException e) {
				logger.error(key + " delete cbase, receive TimeoutException, try :" + loop + " times", e);
			} catch (InterruptedException e) {
				logger.error(key + " delete cbase, receive InterruptedException, try :" + loop + " times", e);
			} catch (MemcachedException e) {
				logger.error(key + " delete cbase, receive MemcachedException, try :" + loop + " times", e);
			}
		}
		return result;
	}

	public <T> T get(String key) {
		T result = null;
        boolean retry = false;
		int loop = 0;
		do {
			try {
				loop++;
				result = memcachedClient.get(key, TIME_OUT);
				retry = false;
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
		} while (retry && loop < LOOP_TIME) ;
		return result;
	}

	public <T> Map<String, T> get(Collection<String> keys) {
		Map<String, T> result = null;
		int loop = 0;
        boolean retry = false;
		do {
			try {
				loop++;
				result = memcachedClient.get(keys, TIME_OUT);
				retry = false;
			} catch (TimeoutException e) {
				logger.error(keys + " get from cbase, receive TimeoutException, try :" + loop + " times", e);
                retry = true;
			} catch (InterruptedException e) {
				logger.error(keys + " get from cbase, receive InterruptedException, try :" + loop + " times", e);
                retry = true;
			} catch (MemcachedException e) {
				logger.error(keys + " get from cbase, receive MemcachedException, try :" + loop + " times", e);
                retry = true;
			}
		} while (retry && loop < LOOP_TIME);
        return result;
	}
	
	@SuppressWarnings("unchecked")
	public boolean setKeys(String value) {
		Object o = this.get(COLLECTION_KEYS);
		Set<String> set = null;
		if(o != null) {
			set = (Set<String>) o;
		}
		if(set == null) set = new HashSet<String>();
		set.add(value);
		return this.set(COLLECTION_KEYS, 0, set);
	}

	public boolean set(String key, Object object) {
		return set(key, EVICT_TIME, object);
	}

	public boolean add(String key, Object object) {
		return add(key, EVICT_TIME, object);
	}
	
	public boolean replace(String key, Object object) {
		return replace(key, EVICT_TIME, object);
	}
	
	public boolean cas(String key, Object newValue) {
		return cas(key, EVICT_TIME, newValue);
	}
}
