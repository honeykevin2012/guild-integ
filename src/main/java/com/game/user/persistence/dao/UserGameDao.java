package com.game.user.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserGame;
import com.game.user.domain.UserGameResult;

public interface UserGameDao extends BaseAccessDao<UserGame> {
	public UserGame selectUnique(UserGame userGame) throws DataAccessException;
	public List<UserGameResult> selectGameByUserId(Integer userId) throws DataAccessException;
	public UserGameResult selectUserAmount(Integer userId) throws DataAccessException;
	public List<UserGame> selectGameRoles(Map<String, Integer> map) throws DataAccessException;
	public UserGame selectByUserServRole(Map<String, String> map) throws DataAccessException;
}
