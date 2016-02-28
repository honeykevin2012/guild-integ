package com.game.games.transfer;

import java.util.List;

import net.sf.json.JSONObject;

import com.game.games.SettleUserGameHelper;
import com.game.user.domain.RoleVo;
import com.game.user.domain.UserGameResult;

public class SyncQuery {
	private Long totalAmount = 0L;
	private Long totalGameAmount = 0L;
	private Integer userId;

	public SyncQuery(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 根据登录用户，实时查询所有游戏信息
	 * @param userId
	 * @return
	 */
	public List<UserGameResult> syncRequest() {
		List<UserGameResult> list = SettleUserGameHelper.getGameByUserId(this.userId);
		if (list == null)
			return null;
		for (UserGameResult result : list) {// 遍历游戏
			Integer gameId = result.getGameId();
			List<RoleVo> roles = result.getRoles();
			long everyGameAmount = 0L;
			long everyPlatAmount = 0L;
			for (RoleVo role : roles) {// 遍历游戏内角色
				String response = SyncRequestGameHelper.query(this.userId, gameId, role.getServerId(), role.getRoleId());
				JSONObject json = JSONObject.fromObject(response);
				if ("ok".equals(json.getString("state"))) {
					Long amount = Double.valueOf(json.getString("data")).longValue();
					role.setGameAmount(amount.toString());//游戏币
					Long platAmount = Double.valueOf((amount*result.getExchangeDivide())).longValue();//转换平台币
					role.setPlatAmount(platAmount.toString());
					totalAmount += platAmount;
					totalGameAmount += amount;
					everyGameAmount += amount;
					everyPlatAmount += platAmount;
				}
			}
			result.setGameAmount(everyGameAmount);
			result.setPlatAmount(everyPlatAmount);
		}
		return list;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getTotalGameAmount() {
		return totalGameAmount;
	}

	public void setTotalGameAmount(Long totalGameAmount) {
		this.totalGameAmount = totalGameAmount;
	}
}
