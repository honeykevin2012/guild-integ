package com.game.current.envelope;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.game.current.RedEnvelopeHelper;

/**
 * 红包入口
 * @author kevin
 * 
 */
public class Test implements Serializable {
	private static final long serialVersionUID = 4478283586977059492L;

//	private static final String RED_ENVELOPE = "red-envelope-";
//	private String guildId;
//	public EntryRedEnvelope(){
//		
//	}
//	public EntryRedEnvelope(String guildId){
//		this.guildId = guildId;
//	}
//	
//	private Map<String, BelongRedEnvelope> items = new HashMap<String, BelongRedEnvelope>();
//	
//	public Map<String, BelongRedEnvelope> getItems() {
//		return items;
//	}
//
//	public void setItems(Map<String, BelongRedEnvelope> items) {
//		this.items = items;
//	}
//
//	public String getGuildId() {
//		return guildId;
//	}
//	public void setGuildId(String guildId) {
//		this.guildId = guildId;
//	}
//	
//	/**
//	 * 获取所属公会所有红包对象BelongRedEnvelope
//	 * @param key 公会ID
//	 * @return
//	 */
//	public BelongRedEnvelope getBelongRedEnvelope(String id) {
//		String key = RED_ENVELOPE + id;
//		if (this.getItems() != null && !this.getItems().isEmpty()) {
//			return this.getItems().get(key);
//		}
//		return null;
//	}
//
//	public void setBelongRedEnvelope(String id, BelongRedEnvelope belong) {
//		String key = RED_ENVELOPE + id;
//		this.getItems().put(key, belong);
//	}
	
	public static void main(String[] args) {
		Integer guildId = 99999;
		Integer userId = 100004;
		RedEnvelopeItem obtained = new RedEnvelopeItem();
		obtained.setAmount(386);
		obtained.setEnvelopeId(10);
		obtained.setObtainedTime(new Date());
		obtained.setUserId(100004);
		
		RedEnvelopeItem notObtained = new RedEnvelopeItem();
		notObtained.setAmount(512);
		notObtained.setEnvelopeId(10);
		notObtained.setObtainedTime(null);
		notObtained.setUserId(null);
		
		RedEnvelope envelope = new RedEnvelope();
		envelope.setGuildId(guildId);
		envelope.setUserId(100003);
		envelope.setDesc("特殊红包奖励");
		envelope.setTotalAmount(898);
		envelope.setRemainAmount(512);
		envelope.setTotalQuantity(2);
		envelope.setRemainQuantity(1);
		envelope.setCreateTime(new Date());
		
		Integer guildId1 = 88888;
		RedEnvelopeItem obtained1 = new RedEnvelopeItem();
		obtained1.setAmount(386);
		obtained1.setEnvelopeId(10);
		obtained1.setObtainedTime(new Date());
		obtained1.setUserId(100004);
		
		RedEnvelopeItem notObtained1 = new RedEnvelopeItem();
		notObtained1.setAmount(512);
		notObtained1.setEnvelopeId(10);
		notObtained1.setObtainedTime(null);
		notObtained1.setUserId(null);
		
		RedEnvelope envelope1 = new RedEnvelope();
		envelope1.setId(1);
		envelope1.setGuildId(guildId1);
		envelope1.setUserId(100003);
		envelope1.setDesc("特殊红包奖励");
		envelope1.setTotalAmount(898);
		envelope1.setRemainAmount(512);
		envelope1.setTotalQuantity(2);
		envelope1.setRemainQuantity(1);
		envelope1.setCreateTime(new Date());
		envelope1.getObtained().add(obtained1);
		envelope1.getObtained().add(obtained);
		envelope1.getNotObtained().add(notObtained1);
		envelope1.getNotObtained().add(notObtained);
		
		BelongRedEnvelope belong1 = new BelongRedEnvelope();
		belong1.getList().add(envelope1);
		belong1.getList().add(envelope);
		
		RedEnvelope obtainedEnvelope = RedEnvelopeHelper.getObtained(userId.toString(), belong1);
		RedEnvelopeItem obtainedEnvelopeItem = obtainedEnvelope.getObtainedEnvelopeItem();
		String getbelong = JSONObject.toJSONString(obtainedEnvelope);
		System.out.println(getbelong);
		long start = System.currentTimeMillis();
		System.out.println(belong1.canObtainedNext(userId.toString()));
		
		String itemJson1 = JSONObject.toJSONString(obtainedEnvelopeItem);
		System.out.println(itemJson1);
		System.out.println(System.currentTimeMillis() - start);
	}
}
