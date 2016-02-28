package com.game.current;

import java.util.List;

import com.game.common.basics.cache.CbaseHelper;
import com.game.current.envelope.BelongRedEnvelope;
import com.game.current.envelope.RedEnvelope;

public class RedEnvelopeHelper {
	private static final String RED_ENVELOPE = "red-envelope-";
	private static CbaseHelper cache = null;

	public synchronized static RedEnvelope getObtained(String userId, String guildId) {
		BelongRedEnvelope belong = getBelongRedEnvelope(guildId);
		if (belong == null) return null;
		List<RedEnvelope> list = belong.getList();
		for (RedEnvelope envelope : list) {
			if (envelope.canObtained(userId)) {// 可抢
				RedEnvelope obtainedEnvelope = envelope.startObtained(userId);
				return obtainedEnvelope;
			}
		}
		return null;
	}
	
	public synchronized static RedEnvelope getObtained(String userId, BelongRedEnvelope belong) {
		if (belong == null) return null;
		List<RedEnvelope> list = belong.getList();
		for (RedEnvelope envelope : list) {
			if (envelope.canObtained(userId)) {// 可抢
				RedEnvelope obtainedEnvelope = envelope.startObtained(userId);
				return obtainedEnvelope;
			}
		}
		return null;
	}

	public static BelongRedEnvelope getBelongRedEnvelope(String id) {
		if(cache == null) cache = CbaseHelper.getInstance();
		String key = RED_ENVELOPE + id;
		Object cacheObject = cache.get(key);
		if (cacheObject == null) return null;
		BelongRedEnvelope belong = (BelongRedEnvelope) cacheObject;
		return belong;
	}
}
