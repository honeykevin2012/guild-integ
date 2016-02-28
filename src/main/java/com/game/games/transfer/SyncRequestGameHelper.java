package com.game.games.transfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.game.init.CacheListener;

public class SyncRequestGameHelper {

	private static CloseableHttpClient httpClient;
	private static PoolingHttpClientConnectionManager connectionManager;
	private static RequestConfig requestConfig;
	private static int maxConnectionPerHost = 500;
	private static int maxTotalConnections = 500;
	private static int connectionTimeOut = 10 * 1000;
	private static int socketTimeOut = 10 * 1000;
	static {
		if(CacheListener.CAHCE_GAME.isEmpty()) CacheListener.loadGame();
		connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setDefaultMaxPerRoute(maxConnectionPerHost);
		connectionManager.setMaxTotal(maxTotalConnections);
		requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeOut).setSocketTimeout(socketTimeOut).build();
		httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
	}

	private static String post(String url, Map<String, String> params, String encode) {
		try {
			String result = null;
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + encode);
			Set<String> keySet = params.keySet();// 将表单的值放入postMethod中
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(keySet.size());
			for (String key : keySet) {
				String value = params.get(key);
				pairs.add(new BasicNameValuePair(key, value));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, "utf-8"));
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();// 执行postMethod
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "utf-8");
				}
				EntityUtils.consume(entity);
				response.close();
			} else {
				JSONObject json = new JSONObject();
				json.put("state", "error");
				json.put("msg", statusCode);
				result = json.toString();
			}
			return result;
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("state", "error");
			json.put("msg", e.getMessage());
			return json.toString();
		}
	}

	private static String post(String url, Map<String, String> params) {
		String result = post(url, params, "UTF-8");
		return result;
	}

	private static String basePost(Integer userId, Integer gameId, String servId, String roleId, long amount) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId + "");
		map.put("gameId", gameId + "");
		map.put("serverId", servId);
		map.put("roleId", roleId);
		map.put("amount", amount + "");
		map.put("sign", amount + "");
		String gameURL = CacheListener.CAHCE_GAME.get(gameId.toString()).getGameAmountDeductUrl();
		String result = post(gameURL, map);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 游戏扣款
	 * @param map
	 * @return
	 */
	public static String reduce(Map<String, String> map) {
		if (map == null || map.isEmpty())
			return null;
		Long amount = Long.valueOf(map.get("amount"));
		if (amount <= 0) {
			JSONObject json = new JSONObject();
			json.put("state", "error");
			json.put("msg", "扣款金额不合法.");
			return json.toString();
		}
		String gameURL = CacheListener.CAHCE_GAME.get(map.get("gameId")).getGameAmountDeductUrl();
		String result = post(gameURL, map);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 游戏扣款
	 * @param userId
	 * @param gameId
	 * @param servId
	 * @param roleId
	 * @param amount
	 * @return
	 */
	public static String reduce(Integer userId, Integer gameId, String servId, String roleId, long amount) {
		if (amount <= 0) {
			JSONObject json = new JSONObject();
			json.put("state", "error");
			json.put("msg", "扣款金额不合法.");
			return json.toString();
		}
		return basePost(userId, gameId, servId, roleId, amount);
	}

	/**
	 * 游戏退款
	 * @param map
	 * @return
	 */
	public static String returns(Map<String, String> map) {
		if (map == null || map.isEmpty())
			return null;
		Long amount = Long.valueOf(map.get("amount"));
		if (amount >= 0) {
			JSONObject json = new JSONObject();
			json.put("state", "error");
			json.put("msg", "退款金额不合法.");
			return json.toString();
		}
		String gameURL = CacheListener.CAHCE_GAME.get(map.get("gameId")).getGameAmountDeductUrl();
		String result = post(gameURL, map);
		return JSONObject.fromObject(result).toString();
	}
	/**
	 * 游戏退款
	 * @param userId
	 * @param gameId
	 * @param servId
	 * @param roleId
	 * @param amount
	 * @return
	 */
	public static String returns(Integer userId, Integer gameId, String servId, String roleId, long amount) {
		if (amount >= 0) {
			JSONObject json = new JSONObject();
			json.put("state", "error");
			json.put("msg", "退款金额不合法.");
			return json.toString();
		}
		return basePost(userId, gameId, servId, roleId, amount);
	}
	
	public static String query(Integer userId, Integer gameId, String servId, String roleId){
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId + "");
		map.put("gameId", gameId + "");
		map.put("serverId", servId);
		map.put("roleId", roleId);
		map.put("sign", userId + "");
		String gameURL = CacheListener.CAHCE_GAME.get(gameId.toString()).getGameAmountQueryUrl();
		String result = post(gameURL, map);
		return JSONObject.fromObject(result).toString();
	}
	
	public static void main(String[] args) {
		System.out.println(returns(4, 100, "1001", "aabb1001", 1000L));
	}
}
