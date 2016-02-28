package com.game.current.envelope;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BelongRedEnvelope {
	/**
	 * 所有红包
	 */
	private List<RedEnvelope> list = new ArrayList<RedEnvelope>();

	public List<RedEnvelope> getList() {
		return list;
	}
	
	public void setList(List<RedEnvelope> list) {
		this.list = list;
	}
	
	/**
	 * 判断是否存在下次可抢红包
	 * @return
	 */
	public boolean canObtainedNext(String userId){
		if(list.size() < 2) return false;
		Set<Integer> set = new HashSet<Integer>();
		for(RedEnvelope envelope : list){
			if(envelope.getCanNotObtainedUnique().contains(userId)) continue;
			set.add(envelope.getId());
		}
		if(set.size() > 0) return true;
		return false;
	}
}
