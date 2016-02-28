package com.game.adaptor;

import java.util.HashMap;
import java.util.Map;

import com.game.common.utility.https.HttpUtil;

public class CaseUtils {
	public static void main(String[] args) throws Exception {
		// 批量创建游戏角色
		// 用户id，游戏id，创建角色数量
		//星际战舰
		createRole("100015", "100", 4);

		// 扣款模拟
		// 用户id，游戏id，服务器id，角色id，金额
		//deducts("41", "100", "004", "404278", -6000L);
	}

	public static String createRole(String userId, String gameId, int count) {
		String adaptorURL = "http://localhost:8080/integration/user/game/adaptor/creator";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("gameId", gameId);
		map.put("count", count + "");
		String result = HttpUtil.post(adaptorURL, map);
		System.out.println(result);
		return result;
	}

	public static String deducts(String userId, String gameId, String serverId, String roleId, Long amount) {
		String adaptorURL = "http://localhost:8080/integration/user/game/adaptor/deducts";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("gameId", gameId);
		map.put("serverId", serverId);
		map.put("roleId", roleId);
		map.put("amount", amount + "");
		String result = HttpUtil.post(adaptorURL, map);
		System.out.println(result);
		return result;
	}
}
