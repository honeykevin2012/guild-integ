/*
 * @author zhaolianjun
 * created on 2015-02-15
 */

package com.game.guild.persistence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.ResultHandler;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildHistoryMessage;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildItem;
import com.game.guild.domain.GuildItemVirtual;
import com.game.guild.domain.GuildLevel;
import com.game.guild.domain.GuildMemberDonation;
import com.game.guild.domain.GuildMemberLevel;
import com.game.guild.domain.GuildMembers;
import com.game.guild.domain.MemberVO;
import com.game.guild.helper.ConstantKeys;
import com.game.guild.helper.GuildMessageHelper;
import com.game.guild.persistence.dao.GuildInfoDao;
import com.game.guild.persistence.dao.GuildItemDao;
import com.game.guild.persistence.dao.GuildItemVirtualDao;
import com.game.guild.persistence.dao.GuildLevelDao;
import com.game.guild.persistence.dao.GuildMemberDonationDao;
import com.game.guild.persistence.dao.GuildMemberLevelDao;
import com.game.guild.persistence.dao.GuildMembersDao;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserPracticTemplate;
import com.game.platform.message.MessageUserPracticVirtualTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.shop.domain.ProductInfo;
import com.game.shop.persistence.dao.ProductInfoDao;
import com.game.user.domain.User;
import com.game.user.persistence.dao.UserDao;

@Service
public class GuildMembersService {

	@Autowired
	private GuildMembersDao guildMembersDao;
	@Autowired
	private GuildInfoDao guildInfoDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private GuildLevelDao levelDao;
	@Autowired
	private GuildItemDao guildItemDao;
	@Autowired
	private GuildItemVirtualDao guildItemVirtualDao;
	@Autowired
	private GuildMemberDonationDao guildMemberDonationDao;
	@Autowired
	private GuildMemberLevelDao memberLevelDao;
	@Autowired
	private ProductInfoDao productInfoDao;

	public List<GuildMembers> query(PageQuery querys) throws DataAccessException {
		try {
			return guildMembersDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildMembers> selectAll() throws DataAccessException {
		try {
			return guildMembersDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildMembers> selectByEntity(GuildMembers guildMembers) throws DataAccessException {
		try {
			return guildMembersDao.selectByEntity(guildMembers);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildMembers> selectByUserIds(Integer[] ids) throws DataAccessException {
		try {
			return guildMembersDao.selectByUserIds(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public GuildMembers select(Integer id) throws DataAccessException {
		try {
			return guildMembersDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Integer> selectUsersByIds(Integer[] ids) throws DataAccessException{
		try {
			return guildMembersDao.selectUsersByIds(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void insert(GuildMembers guildMembers) throws DataAccessException {
		try {
			guildMembersDao.insert(guildMembers);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void update(GuildMembers guildMembers) throws DataAccessException {
		try {
			guildMembersDao.update(guildMembers);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void delete(Integer id) throws DataAccessException {
		try {
			guildMembersDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildMembersDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildMembers> selectMemberList(PageQuery querys) throws DataAccessException {
		try {
			return guildMembersDao.selectMemberList(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<MemberVO> selectAdminMember(Integer userId) throws DataAccessException {
		try {
			return guildMembersDao.selectAdminMember(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public int updateMemberApply(Integer id) throws DataAccessException {
		try {
			return guildMembersDao.updateMemberApply(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public int updateMemberApplyByIds(Integer[] ids) throws DataAccessException {
		try {
			return guildMembersDao.updateMemberApplyByIds(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public int updateMemberIsAdmin(Integer id) throws DataAccessException {
		try {
			return guildMembersDao.updateMemberIsAdmin(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public int updateMemberRemoveAdmin(Integer id) throws DataAccessException {
		try {
			return guildMembersDao.updateMemberRemoveAdmin(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public GuildMembers selectByUserAndGuildId(Integer userId, Integer guildId) throws DataAccessException{
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("userId", userId);
			map.put("guildId", guildId);
			return guildMembersDao.selectByUserAndGuildId(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public int deleteByUserAndGuild(Integer userId, Integer guildId) throws DataAccessException{
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("userId", userId);
			map.put("guildId", guildId);
			return guildMembersDao.deleteByUserAndGuild(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public int deleteByGuildId(Integer guildId) throws DataAccessException {
		try {
			return guildMembersDao.deleteByGuildId(guildId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public int deleteByUserId(Integer userId) throws DataAccessException {
		try {
			return guildMembersDao.deleteByUserId(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public int deleteApplyUserExcludeCurrGuild(Integer[] ids, Integer guildId) throws DataAccessException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("guildId", guildId);
			return guildMembersDao.deleteApplyUserExcludeCurrGuild(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	/**
	 * 公会成员捐款
	 * 
	 * @param guildId
	 * @param userId
	 * @param amount
	 * @throws Exception
	 */
	public Object updateDonatinMember(GuildMemberDonation donation, boolean isWalletAmount) throws Exception {
		GuildInfo guild = guildInfoDao.select(donation.getGuildId());
		if (guild == null) return ResultHandler.bindError("error#公会参数错误.");
		User user = userDao.select(donation.getUserId());
		if (user == null) return ResultHandler.bindError("error#用户参数错误.");
		
		guild.setCurrency(guild.getCurrency() + donation.getAmount());
		int rg = guildInfoDao.updateBalance(guild);
		if (rg == 0) {
			throw new Exception("捐款未成功, 请重新操作.");
		}
		guildMemberDonationDao.insert(donation);
		int experi = donation.getAmount().intValue();
		guild.setExp(guild.getExp() + experi);//更新公会经验值
		//需要判断公会等级  当经验达到临界值时，需要更新公会等级

		if(guild.getExp() > guild.getGuildLevel().getExp()){//已经达到下一等级经验阀值，需要更新公会等级为下一等级
			GuildLevel guildLevel = levelDao.selectNextLevel(guild.getExp());
			if(guildLevel != null) guild.setLevel(guildLevel.getId());
		}
		guildInfoDao.update(guild);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", donation.getUserId());
		map.put("guildId", donation.getGuildId());
		GuildMembers guildMember = guildMembersDao.selectByUserAndGuildId(map);
		if(guildMember != null){
			guildMember.setExp(guildMember.getExp() + experi);//更新用户贡献值
			guildMember.setPoint(guildMember.getPoint() + experi);//更新用户贡献值
			GuildMemberLevel memberLevel = memberLevelDao.selectNextLevel(guildMember.getExp());//公会成员下一等级
			if(memberLevel != null){//获取下一级别
				if(guildMember.getExp() >= memberLevel.getExp()){//当前用户经验大于等于下一级别经验，说明可以升级到下一级别
					guildMember.setLevel(memberLevel.getId());
				}
			}
			guildMembersDao.update(guildMember);//更新用户贡献值和等级信息
		}
		if(isWalletAmount){//钱包扣款
			user.setBalance(user.getBalance() - Math.abs(donation.getAmount()));
			userDao.updateBalance(user);//更新用户余额
		}
		return ResultHandler.bindResult("ok#捐款完成.");
	}

	/**
	 * 公会赠送物品
	 * 
	 * @param guildId
	 * @param userId
	 * @param amount
	 * @throws Exception
	 */
	public Object updateGuildGiveUser(Integer from, Integer guildId, Integer userId, String das, HttpServletRequest request) throws Exception {
		String[] datas = das.split("\\|");
		User formUser = userDao.select(from);
		User toUser = userDao.select(userId);
		if (toUser == null)
			throw new Exception("赠送未成功, 请重新操作.");
		for (String data : datas) {
			String[] ids = data.split("-");
			String itemId = ids[0];
			Integer count = Integer.valueOf(ids[1]);
			count = Math.abs(count);
			GuildItem item = guildItemDao.select(Integer.valueOf(itemId));
			ProductInfo product = productInfoDao.select(item.getTypeId());
			
			if (count < 1) return ResultHandler.bindResult("error#数量必须大于0.");
			if (item.getCount() < count) return ResultHandler.bindResult("error#物品余量不足.");
			
			if (item.isVritual()) {
				// 更新虚拟物品，标识已赠送状态
				item.setCount(item.getCount() - count);
				item.setGivedQuantity(item.getGivedQuantity() + count);
				int effective = guildItemDao.update(item);
				if (effective == 0)
					throw new Exception("赠送未成功, 请重新操作.");
				
				GuildItemVirtual entity = new GuildItemVirtual();
				entity.setUserId(String.valueOf(userId));
				entity.setItemId(Integer.valueOf(itemId));
				entity.setLimit(count);
				int updateItemVirtualRows = guildItemVirtualDao.updateGiveUser(entity);
				if (updateItemVirtualRows == 0)
					throw new Exception("赠送未成功, 请重新操作.");
				
				//虚拟物品赠送邮件
				MessageCore core = new MessageCore();
				List<PracticVirtual> attacts = new ArrayList<PracticVirtual>();
				PracticVirtual practic = new PracticVirtual();
				practic.setId(product.getId());
				practic.setName(product.getName());
				practic.setQuantity(count);
				practic.setOrderId(item.getId().toString());
				attacts.add(practic);
				core.setAdapter(new MessageUserPracticVirtualTemplate()).transfer(attacts, userId, guildId, formUser.getUserName()).send();
				
			} else {
				item.setCount(item.getCount() - count);
				item.setGivedQuantity(item.getGivedQuantity() + count);
				int effective = guildItemDao.update(item);
				if (effective == 0)
					throw new Exception("赠送未成功, 请重新操作.");
				
				//实物赠送邮件
				MessageCore core = new MessageCore();
				List<PracticVirtual> attacts = new ArrayList<PracticVirtual>();
				PracticVirtual practic = new PracticVirtual();
				practic.setId(product.getId());
				practic.setName(product.getName());
				practic.setQuantity(count);
				attacts.add(practic);
				core.setAdapter(new MessageUserPracticTemplate()).transfer(attacts, userId, guildId, formUser.getUserName()).send();
			}
			
			String userRole = null;
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("userId", from);
			map.put("guildId", guildId);
			GuildMembers guildMembers = guildMembersDao.selectByUserAndGuildId(map);
			if (guildMembers != null) {
				if (ConstantKeys.Admin.equals(guildMembers.getIsAdmin())) {
					userRole = "管理员";
				} else if (ConstantKeys.President.equals(guildMembers.getIsAdmin())) {
					userRole = "会长";
				}
			}
			String msgFromUserName = formUser.getNickName() == null ? formUser.getUserName() : formUser.getNickName();
			String msgToUserName = toUser.getNickName() == null ? toUser.getUserName() : toUser.getNickName();
			// 公会历史消息记录
			String content = String.format("【%s】%s赠送%s[%s]个%s", userRole, msgFromUserName, msgToUserName, count, product.getName());
			GuildMessageHelper.saveMsg(GuildHistoryMessage.MsgTypeEnum.GUILD.getValue(), guildId, from, userId, content);
		}
		return ResultHandler.bindResult("ok#赠送成功.");
	}

	/**
	 * 公会物品放入仓库
	 * 
	 * @param guildId
	 * @param userId
	 * @param amount
	 * @throws Exception
	 
	public Object updateGuildItemsMove(Integer guildId, Integer itemId, Integer count, String startTime, String endTime) throws Exception {

		GuildItem guildItem = guildItemDao.select(Integer.valueOf(itemId));
		if (Integer.valueOf(count) < 1) {
			return ResultHandler.bindResult("error#数量必须大于0.");
		}
		if (guildItem.getCount() < Integer.valueOf(count)) {
			return ResultHandler.bindResult("error#物品余量不足.");
		}

		GuildItemsStock itemStock = new GuildItemsStock();
		itemStock.setGuildId(guildId);
		itemStock.setIsVirtual(guildItem.getIsVirtual());
		itemStock.setItemId(itemId);
		itemStock.setName(guildItem.getName());
		itemStock.setStartTime(startTime);
		itemStock.setEndTime(endTime);
		itemStock.setCount(count);
		int insertRows = guildItemsStockDao.insert(itemStock);
		if ("1".equals(guildItem.getIsVirtual())) {
			GuildItemVirtual entity = new GuildItemVirtual();
			entity.setLimit(Integer.valueOf(count));
			entity.setStockId(itemStock.getId());
			entity.setItemId(Integer.valueOf(itemId));
			entity.setStatus("1");
			int updateItemVirtualRows = guildItemVirtualDao.updateItemMove(entity);
			if (updateItemVirtualRows == 0) {
				throw new Exception("放入仓库未成功, 请重新操作.");
			}
		}
		guildItem.setCount(guildItem.getCount() - Integer.valueOf(count));
		int ui = guildItemDao.update(guildItem);
		if (insertRows == 0 || ui == 0) {
			throw new Exception("放入仓库失败, 请重新操作.");
		}
		return ResultHandler.bindResult("ok#放入仓库成功.");
	}
	*/

	/**
	 * 公会仓库放回物品管理
	 * 
	 * @param guildId
	 * @param userId
	 * @param amount
	 * @throws Exception
	 
	public Object updateGuildItemsStockMove(Integer guildId, Integer stockId) throws Exception {

		GuildItemsStock itemsStock = guildItemsStockDao.select(stockId);
		GuildItem guildItem = guildItemDao.select(itemsStock.getItemId());
		if ("1".equals(guildItem.getIsVirtual())) {
			GuildItemVirtual entity = new GuildItemVirtual();
			entity.setStockId(itemsStock.getId());
			entity.setItemId(itemsStock.getItemId());
			entity.setStatus("0");
			int updateItemVirtualRows = guildItemVirtualDao.updateStockMove(entity);
			if (updateItemVirtualRows == 0) {
				throw new Exception("放入仓库未成功, 请重新操作.");
			}
		}
		guildItem.setCount(guildItem.getCount() + itemsStock.getCount() - itemsStock.getGived());
		int updateRows = guildItemDao.update(guildItem);
		int deleteRows = guildItemsStockDao.delete(stockId);
		if (updateRows == 0 || deleteRows == 0) {
			throw new Exception("放回物品管理失败, 请重新操作.");
		}
		return ResultHandler.bindResult("ok#放回物品管理成功.");
	}
	*/

	/**
	 * 领取礼包
	 * 
	 * @param guildId
	 * @param userId
	 * @param amount
	 * @throws Exception
	 
	public Object updateGetGift(String[] stockIds, Integer userId) throws Exception {
		StringBuilder errorMsg = new StringBuilder();
		for (String stId : stockIds) {
			Integer stockId = Integer.valueOf(stId);
			GuildItemsStock itemsStock = guildItemsStockDao.select(stockId);
			GuildMembers member = new GuildMembers();
			member.setUserId(Integer.valueOf(userId));
			member.setGuildId(itemsStock.getGuildId());
			List<GuildMembers> memberList = guildMembersDao.selectByEntity(member);
			if (memberList == null || memberList.isEmpty()) {
				errorMsg.append("|");
				errorMsg.append(itemsStock.getName());
				errorMsg.append("领取失败，您不是该礼包所属公会成员.");
			}
			if (itemsStock.getCount() == itemsStock.getGived()) {
				errorMsg.append("|");
				errorMsg.append(itemsStock.getName());
				errorMsg.append("领取失败，该礼包已领完.");
			}
			int stockUpdateRows = guildItemsStockDao.updateGetGift(stockId);

			GuildItemVirtual itemVirtual = new GuildItemVirtual();
			itemVirtual.setLimit(1);
			itemVirtual.setUserId(String.valueOf(userId));
			itemVirtual.setStockId(stockId);
			int updateItemVirtualRows = guildItemVirtualDao.updateGetGift(itemVirtual);
			if (stockUpdateRows == 1 && updateItemVirtualRows == 1) {
				// return ResultHandler.bindResult("ok#领取礼包成功.");
			} else {
				errorMsg.append("|");
				errorMsg.append(itemsStock.getName());
				errorMsg.append("领取失败，系统错误.");
				throw new Exception("领取礼包失败, 请重新操作.");
			}
		}
		if (errorMsg.toString().length() > 0) {
			return ResultHandler.bindResult("error#" + errorMsg.toString());
		} else {
			return ResultHandler.bindResult("ok#领取礼包成功.");
		}
	}
	*/

	public List<GuildMembers> selectAdminByLevel(Integer guildId) {
		try {
			return guildMembersDao.selectAdminByLevel(guildId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}
