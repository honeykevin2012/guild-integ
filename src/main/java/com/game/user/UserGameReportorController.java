package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.basics.BaseController;
import com.game.common.basics.ResultHandler;
import com.game.user.domain.UserGame;
import com.game.user.domain.form.UserGameForm;
import com.game.user.persistence.service.UserGameService;

/**
 * 游戏数据上报接口<p>
 * 1、上报用户与游戏角色的关系<p>
 * 2、上报游戏角色的当前游戏币数量
 * @author kevin
 *
 */
@Controller
public class UserGameReportorController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserGameReportorController.class);
	@Autowired
	private UserGameService service;
	
	@RequestMapping(value="/user/game/reportor", method = { POST })
    public @ResponseBody Object reportor(@Valid @ModelAttribute("userGameForm") UserGameForm form, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				Date date = new Date();
				UserGame userGame = new UserGame();
				userGame.setUserId(form.getUserId());
				userGame.setGameId(Integer.valueOf(form.getGameId()));
				userGame.setAmount(form.getAmount());
				userGame.setRoleId(form.getRoleId());
				userGame.setRoleName(form.getRoleName());
				userGame.setServerId(form.getServerId());
				userGame.setServerName(form.getServerName());
				userGame.setLastLoginTime(date);
				userGame.setLastLogoutTime(date);
				
				UserGame select = service.selectUnique(userGame);
				if(select == null){
					service.insert(userGame);
				}else{
					select.setAmount(form.getAmount());
					service.update(select);
				}
				return ResultHandler.bindResult("ok#数据上报成功.");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
}
