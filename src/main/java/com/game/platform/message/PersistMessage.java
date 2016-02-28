package com.game.platform.message;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.game.common.basics.ApplicationContextLoader;
import com.game.platform.domain.MessageReader;
import com.game.platform.domain.MessageReaderAttach;
import com.game.platform.persistence.dao.MessageReaderAttachDao;
import com.game.platform.persistence.dao.MessageReaderDao;

public class PersistMessage {
	private static final boolean isDebug = false;
	private static MessageReaderDao messageReaderDao = null;
	private static MessageReaderAttachDao messageReaderAttachDao = null;
	static{
		if(!isDebug){
			messageReaderDao = (MessageReaderDao) ApplicationContextLoader.getBean("messageReaderDao");
			messageReaderAttachDao = (MessageReaderAttachDao) ApplicationContextLoader.getBean("messageReaderAttachDao");
		}
	}
	public static void persist(Map<String, Object> params, List<PracticVirtual> practics) {
		if(params == null || params.isEmpty()) return;
		MessageReader entity = new MessageReader();
		Integer receive = null;
		Integer guildId = 0;
		String title = (String) params.get(ConsValues.TITLE);
		String content = (String) params.get(ConsValues.CONTENT);
		Integer userId = Integer.parseInt(params.get(ConsValues.USER_ID).toString());
		String entry = (String) params.get(ConsValues.ENTRY);
		if(params.containsKey(ConsValues.RECEIVE)) receive = Integer.parseInt(params.get(ConsValues.RECEIVE).toString());
		if(params.containsKey(ConsValues.GUILD_ID)) guildId = Integer.parseInt(params.get(ConsValues.GUILD_ID).toString());
		
		entity.setTitle(title);//标题
		entity.setContent(content);//内容
		entity.setUserId(userId);//用户ID
		entity.setType(entry);//类型1领取N币，2实物，3虚拟物
		entity.setStatus("0");//是否已读
		entity.setIsReceived("0");//是否领取N币标识1已领取0未领取
		entity.setSendTime(new Date());//消息产生时间
		entity.setFromGuildId(guildId);
		if(isDebug) System.out.println(entity.toString());
		else messageReaderDao.insert(entity);
		if(practics == null || practics.isEmpty()){
			//如果邮件为普通邮件(无需领取), 则不需要创建附件信息
			if(EntryEnum.NORMAL.getIdentify().equals(entry)) return;
			MessageReaderAttach attach = new MessageReaderAttach();
			attach.setMessageReaderId(entity.getId());
			attach.setType(entry);
			attach.setQuantity(receive == null ? 0 : receive);
			attach.setDataId(0);
			attach.setName(ConsValues.CURRENCY);
			attach.setOrderId(null);
			if(isDebug) System.out.println(attach.toString());
			else messageReaderAttachDao.insert(attach);
		}else{
			for(PracticVirtual practic : practics){
				MessageReaderAttach attach = new MessageReaderAttach();
				attach.setMessageReaderId(entity.getId());
				attach.setType(entry);
				attach.setQuantity(practic.getQuantity());
				attach.setDataId(EntryEnum.GOLD.getIdentify().equals(entry) ? 0 : practic.getId());
				attach.setName(EntryEnum.GOLD.getIdentify().equals(entry) ? ConsValues.CURRENCY : practic.getName());
				attach.setOrderId(practic.getOrderId());
				if(isDebug) System.out.println(attach.toString());
				else messageReaderAttachDao.insert(attach);
			}
		}
	}
}
