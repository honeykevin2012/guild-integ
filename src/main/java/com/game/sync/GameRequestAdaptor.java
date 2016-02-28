package com.game.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.game.common.utility.https.HttpUtil;
import com.game.init.CacheListener;

public class GameRequestAdaptor {
	private static final Logger logger = Logger.getLogger(GameRequestAdaptor.class);
	
	private static final String STATUS = "1";//成功标识
	private static final String USERID = "userId";// 
	private static final String SERVERID = "serverId";
	private static final String ROLEID = "roleId";
	private static final String ROLENAME = "roleName";
	private static final String AMOUNT = "amount";
	private long totalPlatformAmount;
	
	private Map<String, List<GameResult>> gameRequest(Map<String, String> params){
		if(CacheListener.CAHCE_GAME.isEmpty()) CacheListener.loadGame();
		Set<String> set = CacheListener.CAHCE_GAME.keySet();
		Iterator<String> it = set.iterator();
		Map<String, List<GameResult>> map = new HashMap<String, List<GameResult>>();//{gameId, List<GameResult>}
		while(it.hasNext()){
			String gameId = it.next();//游戏标识
			String gameURL = CacheListener.CAHCE_GAME.get(gameId).getGameAmountQueryUrl();//根据游戏标识获取要请求的接口地址
			if(gameURL == null || "".equals(gameURL)) continue;
			double divide = CacheListener.CAHCE_GAME.get(gameId).getExchangeDivide();
			String response = HttpUtil.post(gameURL, params);
			if(response == null) continue;
			logger.info(">>>>>>>>>>>>>>>>>>>>>游戏金币查询结果: " + response + " >>>>>>>>>>>>>>>>>>>>>");
			JSONObject json = JSONObject.fromObject(response);
			if(STATUS.equals(json.getString("state"))){
				JSONObject data = json.getJSONObject("data");
				//String transparent = data.getString("transparent");
				JSONArray roles = data.getJSONArray("roles");
				if(roles != null && roles.size() != 0){
					String uid = null;//
					String serverId = null;
					String roleId = null;
					String roleName = null;
					String amount = null;
					List<GameResult> list = new ArrayList<GameResult>();
					for (int i = 0; i < roles.size(); i++) {
						JSONObject roleJson = roles.getJSONObject(i);
						if(roleJson.containsKey(USERID)) 	uid = roleJson.getString(USERID);
						if(roleJson.containsKey(SERVERID)) 	serverId = roleJson.getString(SERVERID);
						if(roleJson.containsKey(ROLEID)) 	roleId = roleJson.getString(ROLEID);
						if(roleJson.containsKey(ROLENAME)) 	roleName = roleJson.getString(ROLENAME);
						if(roleJson.containsKey(AMOUNT)) 	amount = roleJson.getString(AMOUNT);
						GameResult gameResult = new GameResult();
						gameResult.setUserId(uid);
						gameResult.setServerId(serverId);
						gameResult.setRoleId(roleId);
						gameResult.setRoleName(roleName);
						if(amount == null || "".equals(amount)) amount = "0";
						long platformAmount = Double.valueOf(divide * Long.valueOf(amount)).longValue();
						totalPlatformAmount += platformAmount;
						gameResult.setAmount(amount);
						gameResult.setGameId(gameId);
						gameResult.setPlatformAmount(platformAmount + "");
						list.add(gameResult);
					}
					if(!list.isEmpty()) map.put(gameId, list);
				}
			}
		}
		return map;
	}
	
	public Map<String, List<GameResult>> gameRequest(String userId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		return gameRequest(params);
	}
	
	public Map<String, List<GameResult>> gameRequest(String userId, String serverId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("serverId", serverId);
		return gameRequest(params);
	}
	
	public Map<String, List<GameResult>> gameRequest(String userId, String serverId, String roleId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("serverId", serverId);
		params.put("roleId", roleId);
		return gameRequest(params);
	}

	public long getTotalPlatformAmount() {
		return totalPlatformAmount;
	}

	public void setTotalPlatformAmount(long totalPlatformAmount) {
		this.totalPlatformAmount = totalPlatformAmount;
	}
}
