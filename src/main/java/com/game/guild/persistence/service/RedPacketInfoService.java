/*
 * @author yangchengwei
 * created on 2015-08-05
 */

package com.game.guild.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.MessageConsValue;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.RedpacketUtil;
import com.game.games.transfer.SyncRequestGameHelper;
import com.game.guild.domain.GuildHistoryMessage;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildMembers;
import com.game.guild.domain.RedPacketInfo;
import com.game.guild.domain.RedPacketItem;
import com.game.guild.helper.GuildMessageHelper;
import com.game.guild.persistence.dao.GuildInfoDao;
import com.game.guild.persistence.dao.GuildMembersDao;
import com.game.guild.persistence.dao.RedPacketInfoDao;
import com.game.guild.persistence.dao.RedPacketItemDao;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserRedEnvelopeTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.user.domain.User;
import com.game.user.domain.UserGame;
import com.game.user.persistence.dao.UserDao;
import com.game.user.persistence.dao.UserGameDao;

@Service
public class RedPacketInfoService {
	private static final Logger logger = Logger.getLogger(RedPacketInfoService.class);
	@Autowired
	private RedPacketInfoDao redPacketInfoDao;
	@Autowired
	private RedPacketItemDao redPacketItemDao;
	@Autowired
	private GuildMembersDao guildMembersDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserGameDao userGameDao;
	@Autowired
	private GuildInfoDao guildInfoDao;

	public List<RedPacketInfo> query(PageQuery querys) throws DataAccessException {
		try {
			return redPacketInfoDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<RedPacketInfo> selectAll() throws DataAccessException {
		try {
			return redPacketInfoDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<RedPacketInfo> selectByEntity(RedPacketInfo redPacketInfo) throws DataAccessException {
		try {
			return redPacketInfoDao.selectByEntity(redPacketInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public RedPacketInfo select(Integer id) throws DataAccessException {
		try {
			return redPacketInfoDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void insert(RedPacketInfo redPacketInfo) throws DataAccessException {
		try {
			redPacketInfoDao.insert(redPacketInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void update(RedPacketInfo redPacketInfo) throws DataAccessException {
		try {
			redPacketInfoDao.update(redPacketInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void delete(Integer id) throws DataAccessException {
		try {
			redPacketInfoDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			redPacketInfoDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public Object insertRedPacket(RedPacketInfo info, String moneyFrom, Map<String, String> extMap) {
		boolean fromFlag = false;
		Map<String, Integer> param1 = new HashMap<String, Integer>();
		param1.put("userId", info.getUserId());
		param1.put("guildId", info.getGuildId());
		GuildMembers fromUser = guildMembersDao.selectByUserAndGuildId(param1);
		if (fromUser == null) {
			return ResultHandler.bindResult("error#没有操作权限.");
		}
		User user = userDao.select(info.getUserId());
		if ("1".equals(moneyFrom)) {
			// 钱包
			if (Long.valueOf(user.getBalance()) > Math.abs(info.getTotalMoney())) {
				user.setBalance(user.getBalance() - Math.abs(info.getTotalMoney()));
				int rst = userDao.updateBalance(user);
				if (rst > 0) {
					fromFlag = true;
				}
			} else {
				return BuildFormErrorUtils.buildFormError("钱包余额不足.");
			}
		} else if ("2".equals(moneyFrom)) {
			// 公会
			GuildInfo guild = guildInfoDao.select(info.getGuildId());
			if (guild.getCurrency() > Math.abs(info.getTotalMoney())) {
				guild.setCurrency(guild.getCurrency() - Math.abs(info.getTotalMoney()));
				int rst = guildInfoDao.updateBalance(guild);
				if (rst > 0) {
					fromFlag = true;
				}
			} else {
				return BuildFormErrorUtils.buildFormError("公会余额不足.");
			}
		} else if ("3".equals(moneyFrom)) {
			UserGame userGame = userGameDao.selectByUserServRole(extMap);// 查询用户所选捐款游戏是否正确
			if (userGame == null) {
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			}
			// HTTP请求游戏扣除金币
			long gameAmount = Double.valueOf(info.getTotalMoney() / userGame.getExchangeDivide()).longValue();
			String response = SyncRequestGameHelper.reduce(Integer.valueOf(extMap.get("userId")), Integer.valueOf(extMap.get("gameId")), extMap.get("serverId"), extMap.get("roleId"), gameAmount);
			JSONObject json = JSONObject.fromObject(response);
			logger.info("待扣款游戏信息：" + userGame.toString());
			logger.info("游戏扣款结果：" + json.toString());
			if (!"1".equals(json.get("state"))) {
				return BuildFormErrorUtils.buildFormError("游戏扣款失败.");
			} else {
				fromFlag = true;
			}
		}

		int rst1 = redPacketInfoDao.insert(info);
		long[] packets = RedpacketUtil.generate(info.getTotalMoney(), info.getTotalPeople(), 1);
		List<RedPacketItem> insertList = new ArrayList<RedPacketItem>();
		for (long l : packets) {
			RedPacketItem item = new RedPacketItem();
			item.setMoney(l);
			item.setPacketId(info.getId());
			insertList.add(item);
		}
		int rst2 = redPacketItemDao.insertRedPacketItem(insertList);
		if (rst1 > 0 && rst2 > 0 && fromFlag) {
			// 公会历史消息记录
			String content = String.format("%s发了一个%sN币的红包", user.getUserName(), info.getTotalMoney());
			GuildMessageHelper.saveMsg(GuildHistoryMessage.MsgTypeEnum.REDPACKET.getValue(), info.getGuildId(), info.getUserId(), info.getUserId(), content);

			JsonResult jsonResult = new JsonResult();
			jsonResult.setMsg("红包创建成功");
			return jsonResult;
		} else {
			return BuildFormErrorUtils.buildFormError("红包创建失败.");
		}
	}

	public Object updateRecieveRedPacket(Integer userId, Integer guildId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("guildId", guildId);
		RedPacketInfo redPacket = redPacketInfoDao.selectGuildLimit(map);
		if (redPacket == null) {
			JsonResult jsonResult = new JsonResult();
			JSONObject obj = new JSONObject();
			obj.put("redPacket", 0);
			jsonResult.setData(obj);
			jsonResult.setMsg("来晚了 红包已经领完.");
			return jsonResult;
		}
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("userId", userId);
		param.put("guildId", guildId);
		GuildMembers guildMember = guildMembersDao.selectByUserAndGuildId(param);
		if (guildMember == null) {
			return BuildFormErrorUtils.buildFormError("非公会成员不能领取红包.");
		}

		RedPacketItem packet = redPacketItemDao.selectOne(redPacket.getId());
		if (packet != null) {
			packet.setUserId(userId);
			packet.setReceiveTime(new Date());
			int rst1 = redPacketItemDao.update(packet);
			redPacket.setLeftMoney(redPacket.getTotalMoney() - packet.getMoney());
			redPacket.setLeftPeople(redPacket.getLeftPeople() - 1);
			int rst2 =0;
			while(rst1>0&&rst2==0){
				rst2=redPacketInfoDao.update(redPacket);
				if(rst2==0){
					redPacket=redPacketInfoDao.select(redPacket.getId());
					if(redPacket==null){
						break;
					}
				}
			}
			User u = userDao.select(userId);
			// u.setBalance((Long.valueOf(u.getBalance()) +
			// Math.abs(packet.getMoney())) + "");
			// int rst3=userDao.updateBalance(u);
			// 邮件领取更新
			JsonResult jsonResult = new JsonResult();
			RedPacketInfo haveNext = redPacketInfoDao.selectGuildLimit(map);
			if (rst1 > 0 && rst2 > 0) {
				// 公会历史消息记录
				String content = String.format("%s领取了一个%sN币的红包", u.getUserName(), packet.getMoney());
				GuildMessageHelper.saveMsg(GuildHistoryMessage.MsgTypeEnum.REDPACKET.getValue(), redPacket.getGuildId(), userId, userId, content);

				// 邮件通知
				MessageCore core = new MessageCore();
				PracticVirtual practic = new PracticVirtual();
				practic.setId(0);
				practic.setQuantity(packet.getMoney().intValue());
				core.setAdapter(new MessageUserRedEnvelopeTemplate()).transfer(practic, u.getId(), u.getUserName()).send();
				JSONObject obj = new JSONObject();
				obj.put("money", packet.getMoney());
				obj.put("redPacket", haveNext==null?0:1);
				jsonResult.setData(obj);
				return jsonResult;
			} else {
				throw new RuntimeException("领取红包失败");
			
			}
		} else {
			JsonResult jsonResult = new JsonResult();
			JSONObject obj = new JSONObject();
			obj.put("redPacket", 0);
			jsonResult.setData(obj);
			jsonResult.setMsg("来晚了 红包已经领完.");
			return jsonResult;
		}

	}

	public RedPacketInfo selectGuildLimit(Integer guildId, Integer userId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("guildId", guildId);
			map.put("userId", userId);
			return redPacketInfoDao.selectGuildLimit(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}
