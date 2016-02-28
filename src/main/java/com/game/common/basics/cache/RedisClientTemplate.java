//package com.game.common.basics.cache;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class RedisClientTemplate {
//
////    private static final Logger log = Logger.getLogger(RedisClientTemplate.class);
//	
//	@Autowired
//	private RedisTemplate<String, String> redisTemplate;
//
//	/**
//	 * 压栈
//	 * 
//	 * @param key
//	 * @param value
//	 * @return
//	 */
//	public Long push(String key, String value) {
//		return redisTemplate.opsForList().leftPush(key, value);
//	}
//
//	/**
//	 * 出栈
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public String pop(String key) {
//		return (String) redisTemplate.opsForList().leftPop(key);
//	}
//
//	/**
//	 * 入队
//	 * 
//	 * @param key
//	 * @param value
//	 * @return
//	 */
//	public Long in(String key, String value) {
//		return redisTemplate.opsForList().rightPush(key, value);
//	}
//
//	/**
//	 * 出队
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public String out(String key) {
//		return (String) redisTemplate.opsForList().leftPop(key);
//	}
//
//	/**
//	 * 栈/队列长
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public Long length(String key) {
//		return redisTemplate.opsForList().size(key);
//	}
//
//	/**
//	 * 范围检索
//	 * 
//	 * @param key
//	 * @param start
//	 * @param end
//	 * @return
//	 */
//	public List<String> range(String key, int start, int end) {
//		return redisTemplate.opsForList().range(key, start, end);
//	}
//
//	/**
//	 * 移除
//	 * 
//	 * @param key
//	 * @param i
//	 * @param value
//	 */
//	public void remove(String key, long i, String value) {
//		redisTemplate.opsForList().remove(key, i, value);
//	}
//
//	/**
//	 * 检索
//	 * 
//	 * @param key
//	 * @param index
//	 * @return
//	 */
//	public String index(String key, long index) {
//		return (String) redisTemplate.opsForList().index(key, index);
//	}
//
//	/**
//	 * 置值
//	 * 
//	 * @param key
//	 * @param index
//	 * @param value
//	 */
//	public void set(String key, long index, String value) {
//		redisTemplate.opsForList().set(key, index, value);
//	}
//
//	/**
//	 * 裁剪
//	 * 
//	 * @param key
//	 * @param start
//	 * @param end
//	 */
//	public void trim(String key, long start, int end) {
//		redisTemplate.opsForList().trim(key, start, end);
//	}
//
//	public long size(String key) {
//		return redisTemplate.opsForList().size(key);
//	}
//	
//	public RedisTemplate<String, String> getRedisTemplate() {
//		return redisTemplate;
//	}
//
//	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
//		this.redisTemplate = redisTemplate;
//	}
//}