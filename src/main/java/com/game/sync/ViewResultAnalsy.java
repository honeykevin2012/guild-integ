package com.game.sync;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.game.init.CacheListener;

public class ViewResultAnalsy {
	public static List<ViewResult> list(String userId) {
		if(CacheListener.CAHCE_GAME.isEmpty()) CacheListener.loadGame();
		GameRequestAdaptor adaptor = new GameRequestAdaptor();
		Map<String, List<GameResult>> mapResult = adaptor.gameRequest(userId);
		ViewResult result = new ViewResult();
		result.setTotalPlatformAmount(adaptor.getTotalPlatformAmount());
		Set<String> set = mapResult.keySet();
		Iterator<String> it = set.iterator();
		List<ViewResult> viewResultList = new ArrayList<ViewResult>();
		while(it.hasNext()){
			String gameId = it.next();
			ViewResult viewResult = new ViewResult();
			viewResult.setGameId(gameId);
			viewResult.setGameName(CacheListener.CAHCE_GAME.get(gameId).getName());
			viewResult.setGameIcon(CacheListener.CAHCE_GAME.get(gameId).getIcon());
			
			List<ViewRoleResult> roleList = new ArrayList<ViewRoleResult>();
			List<GameResult> list = mapResult.get(gameId);
			for(GameResult game : list){
				ViewRoleResult role = new ViewRoleResult();
				role.setRoleId(game.getRoleId());
				role.setRoleName(game.getRoleName());
				role.setServerId(game.getServerId());
				role.setGameAmount(game.getAmount());
				role.setPlatAmount(game.getPlatformAmount());
				role.setServerName("");
				roleList.add(role);
			}
			viewResult.setRoles(roleList);
			viewResultList.add(viewResult);
		}
		return viewResultList;
	}

	public static List<ViewResult> list(String userId, String serverId) {
		if(CacheListener.CAHCE_GAME.isEmpty()) CacheListener.loadGame();
		GameRequestAdaptor adaptor = new GameRequestAdaptor();
		Map<String, List<GameResult>> mapResult = adaptor.gameRequest(userId, serverId);
		ViewResult result = new ViewResult();
		result.setTotalPlatformAmount(adaptor.getTotalPlatformAmount());
		Set<String> set = mapResult.keySet();
		Iterator<String> it = set.iterator();
		List<ViewResult> viewResultList = new ArrayList<ViewResult>();
		while(it.hasNext()){
			String gameId = it.next();
			ViewResult viewResult = new ViewResult();
			viewResult.setGameId(gameId);
			viewResult.setGameName(CacheListener.CAHCE_GAME.get(gameId).getName());
			viewResult.setGameIcon(CacheListener.CAHCE_GAME.get(gameId).getIcon());
			
			List<ViewRoleResult> roleList = new ArrayList<ViewRoleResult>();
			List<GameResult> list = mapResult.get(gameId);
			for(GameResult game : list){
				ViewRoleResult role = new ViewRoleResult();
				role.setRoleId(game.getRoleId());
				role.setRoleName(game.getRoleName());
				role.setServerId(game.getServerId());
				role.setGameAmount(game.getAmount());
				role.setPlatAmount(game.getPlatformAmount());
				role.setServerName("");
				roleList.add(role);
			}
			viewResult.setRoles(roleList);
			viewResultList.add(viewResult);
		}
		return viewResultList;
	}

	public static List<ViewResult> list(String userId, String serverId, String roleId) {
		if(CacheListener.CAHCE_GAME.isEmpty()) CacheListener.loadGame();
		GameRequestAdaptor adaptor = new GameRequestAdaptor();
		Map<String, List<GameResult>> mapResult = adaptor.gameRequest(userId, serverId, roleId);
		ViewResult result = new ViewResult();
		result.setTotalPlatformAmount(adaptor.getTotalPlatformAmount());
		Set<String> set = mapResult.keySet();
		Iterator<String> it = set.iterator();
		List<ViewResult> viewResultList = new ArrayList<ViewResult>();
		while(it.hasNext()){
			String gameId = it.next();
			ViewResult viewResult = new ViewResult();
			viewResult.setGameId(gameId);
			viewResult.setGameName(CacheListener.CAHCE_GAME.get(gameId).getName());
			viewResult.setGameIcon(CacheListener.CAHCE_GAME.get(gameId).getIcon());
			
			List<ViewRoleResult> roleList = new ArrayList<ViewRoleResult>();
			List<GameResult> list = mapResult.get(gameId);
			for(GameResult game : list){
				ViewRoleResult role = new ViewRoleResult();
				role.setRoleId(game.getRoleId());
				role.setRoleName(game.getRoleName());
				role.setServerId(game.getServerId());
				role.setGameAmount(game.getAmount());
				role.setPlatAmount(game.getPlatformAmount());
				role.setServerName("");
				roleList.add(role);
			}
			viewResult.setRoles(roleList);
			viewResultList.add(viewResult);
		}
		return viewResultList;
	}
}
