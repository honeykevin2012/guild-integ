package com.game.common.basics.domain;

public class EasyUIResult implements java.io.Serializable{
	private static final long serialVersionUID = -6799267100848455212L;
	
	private static final long ZERO_INT 	= 0;
	
	private long total = ZERO_INT;
	
	private Object rows = null;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}
}
