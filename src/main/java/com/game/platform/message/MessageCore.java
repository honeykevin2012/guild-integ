package com.game.platform.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageCore {
	private MessageTemplate template;
	private Map<String, Object> params = new HashMap<String, Object>();
	private List<PracticVirtual> attacts = new ArrayList<PracticVirtual>();
	/**
	 * 设置适配器, 可填充不同的时间模板
	 * @param template
	 * @return
	 */
	public MessageCore setAdapter(MessageTemplate template) {
		this.template = template;
		return this;
	}

	/**
	 * 传递业务参数到指定的模板
	 * @param values 变参, 根据具体模板实现传递不同的参数 
	 * 0=用户ID, 1=金额, 2=来源(公会名称或用户昵称)
	 * @param practics 多个实物或虚拟物的物品信息集合, 需要持久化到信息附件表中
	 * @return
	 */
	public MessageCore transfer(List<PracticVirtual> practics, Object... values) {
		this.setAttacts(practics);
		this.template.setPractics(practics);
		this.setParams(template.getParameters(values));
		return this;
	}
	
	/**
	 * 传递业务参数到指定的模板
	 * @param values 变参, 根据具体模板实现传递不同的参数
	 * 0=用户ID, 2=来源(公会名称或用户昵称)
	 * @param practics 单个实物或虚拟物的物品信息对象, 需要持久化到信息附件表中
	 * @return
	 */
	public MessageCore transfer(PracticVirtual practic, Object... values) {
		if(practic.isLegal()) {
			this.template.getPractics().add(practic);
			this.attacts.add(practic);
		}
		this.setParams(template.getParameters(values));
		return this;
	}
	
	/**
	 * 传递业务参数到指定的模板
	 * @param values 变参, 根据具体模板实现传递不同的参数
	 * 0=用户ID, 1=金额, 2=来源(公会名称或用户昵称)
	 * @return
	 */
	//public MessageCore transfer(Object... values) {
	//	this.setParams(template.getParameters(values));
	//	return this;
	//}
	
	public void send() {
		PersistMessage.persist(this.params, this.attacts);
	}
	
	public void setParams(Map<String, Object> params){
		this.params = params;
	}
	
	public void setAttacts(List<PracticVirtual> attacts) {
		this.attacts = attacts;
	}

	public static void main(String[] args) {
		//MessageCore core = new MessageCore();
		//List<PracticVirtual> attacts = new ArrayList<PracticVirtual>();
		PracticVirtual practic = new PracticVirtual();
		practic.setId(0);
		practic.setName("test practic");
		practic.setQuantity(115);
		//attacts.add(practic);
		//core.setAdapter(new MessageUserSignTemplate()).transfer(practic, "4", "6").send();
		//core.setAdapter(new MessageUserSignTemplate()).transfer("4").send();
		
		//Set<String> set = new HashSet<String>();
		//set.add("qq");
		//set.add("phone");
		//set.add("avatar");
		//set.add("email");
		//core.setAdapter(new MessageUserEditTemplate(set)).transfer("5");
		//core.send();
	}
}
