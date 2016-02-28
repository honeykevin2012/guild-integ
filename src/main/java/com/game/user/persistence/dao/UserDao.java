package com.game.user.persistence.dao;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.User;

public interface UserDao extends BaseAccessDao<User> {
	public User selectCheckUserExists(Map<String, String> map);
	public User selectCheckUserExistsByName(String userName);
	public void updateUserVipGroup(Integer userId);
	public void updateUserInfo(User user);
	public int updateBalance(User user);
	public int updatePoint(User user);
	public User selectUserGroup(Integer userId);
	public List<User> selectByIds(String[] ids) throws DataAccessException;
	public List<JSONObject> selectCostTopQuantity(int limit) throws DataAccessException;
}
