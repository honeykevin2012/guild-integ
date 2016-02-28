package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.utility.CommonUtils;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.platform.domain.PlatformBlockComment;
import com.game.platform.persistence.service.PlatformBlockCommentService;
import com.game.shop.persistence.service.ProductInfoService;
import com.game.user.persistence.service.UserService;

@Controller
public class PlatformBlockCommentController extends BaseController {
	private static final Logger logger = Logger.getLogger(PlatformBlockCommentController.class);
	@Autowired
	private PlatformBlockCommentService service;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private GuildInfoService guildService;    
    @RequestMapping(value="/platform/block/select", method = { POST })
    public @ResponseBody Object select(String blockId) {
	    try{
	    	if(CommonUtils.isNullEmpty(blockId)) return this.buildFormError("区块标记为空.");
	    	JsonResult result = new JsonResult();
	    	PlatformBlockComment block = service.selectByBlock(blockId);
	    	if(block != null){
	    		result.setData(block.getHandEdit());
	    	}
	    	return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }
    
    @RequestMapping(value="/platform/block/topList", method = { POST })
    public @ResponseBody Object topList(Integer blockId) {
	    try{
	    	if(blockId==null) return this.buildFormError("区块标记为空.");
			PlatformBlockComment param = new PlatformBlockComment();
			param.setBlockId(blockId + "");
			List<PlatformBlockComment> retemp=service.selectByEntity(param);
			if(retemp!=null&&!retemp.isEmpty()){
				PlatformBlockComment model = retemp.get(0);
				String ids = model.getBusinessIds();
				String[] items = ids.split(",");
				JSONArray list = new JSONArray();	    	

				switch (blockId) {
				case 5001:
					for (String item : items) {
						JSONObject obj = new JSONObject();
						String[] pas = item.split("-");
						obj.put("productName", productInfoService.select(Integer.valueOf(pas[0])).getName());
						obj.put("productId", pas[0]);
						obj.put("daySum", pas[1]);
						obj.put("weekSum", pas[2]);
						list.add(obj);
					}				
					break;
				case 5002:
					for (String item : items) {
						JSONObject obj = new JSONObject();
						String[] pas = item.split("-");
						obj.put("productName", productInfoService.select(Integer.valueOf(pas[0])).getName());
						obj.put("productId", pas[0]);
						obj.put("daySum", pas[1]);
						obj.put("weekSum", pas[2]);
						list.add(obj);
					}				
					break;
				case 5003:
					for (String item : items) {
						JSONObject obj = new JSONObject();
						String[] pas = item.split("-");
						obj.put("userName", userService.select(Integer.valueOf(pas[0])).getUserName());
						obj.put("userId", pas[0]);
						obj.put("amount", pas[1]);
						list.add(obj);
					}
					break;
				case 5004:
					for (String item : items) {
						JSONObject obj = new JSONObject();
						String[] pas = item.split("-");
						obj.put("guildName", guildService.select(Integer.valueOf(pas[0])).getName());
						obj.put("guildId", pas[0]);
						obj.put("amount", pas[1]);
						list.add(obj);
					}				
					break;				
				default:
					return this.buildFormError("区块标记错误.");
				}
		    	JsonResult result = new JsonResult();
		    		result.setData(list);
		    	return result;
			}else{
				return this.buildFormError("区块标记错误.");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }
}
