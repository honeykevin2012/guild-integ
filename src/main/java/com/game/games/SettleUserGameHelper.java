package com.game.games;

import java.util.List;

import com.game.common.Constants;
import com.game.common.basics.ApplicationContextLoader;
import com.game.user.domain.RoleVo;
import com.game.user.domain.UserGameResult;
import com.game.user.persistence.dao.UserGameDao;

public class SettleUserGameHelper {
	private static final String ROLE_SPLIT = "\\#\\$\\$\\$\\#";
	private static final String ROLE_ITEM_SPLIT = "\\#\\$\\#";
	private static UserGameDao dao = (UserGameDao) ApplicationContextLoader.getBean("userGameDao");
	
	/**
	 * 查询游戏上报用户的数据（FROM DB）
	 * @param userId
	 * @return
	 */
	public static List<UserGameResult> getGameByUserId(Integer userId){
		List<UserGameResult> list = dao.selectGameByUserId(userId);
		for(UserGameResult result : list){
			String append = result.getRoleServAppend();
			if(append == null) continue;
			String[] roles = append.split(ROLE_SPLIT);
			long platTotalAmount = 0;
			long gameTotalAmount = 0;
			if(roles != null){//多角色拼接字符串
				for(String role : roles){
					if(role == null) continue;
					String[] items = role.split(ROLE_ITEM_SPLIT);
					if(items != null && items.length == 6){//数据分组拼接字符串roleId,roleName,serverId,serverName, amount
						String roleId = items[0];
						String roleName = items[1];
						String servId = items[2];
						String servName = items[3];
						String platAamount = items[4];//平台币
						String gameAmount = items[5];
						platTotalAmount += Long.valueOf(platAamount);//平台币总和
						gameTotalAmount += Long.valueOf(gameAmount);
						RoleVo vo = new RoleVo();
						vo.setRoleId(roleId);
						vo.setRoleName(roleName);
						vo.setServerId(servId);
						vo.setServerName(servName);
						vo.setGameAmount(gameAmount);
						vo.setPlatAmount(platAamount);
						result.getRoles().add(vo);
					}
				}
				result.setPlatAmount(platTotalAmount);
				result.setGameAmount(gameTotalAmount);
				result.setGameIcon(Constants.IMAGE_SITE_URL + result.getGameIcon());
			}
			result.setRoleServAppend("");
		}
		return list;
	}
}
