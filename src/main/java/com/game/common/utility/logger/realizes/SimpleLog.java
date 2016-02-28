package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 示例
 * @author kevin
 *
 */
public class SimpleLog extends BaseLogger{

	public SimpleLog() {
		super("SimpleLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.baseParameter()).append(this.append);
	    buffer.append("10").append(this.append);
	    buffer.append(20).append(this.append);
	    buffer.append(30).append(this.append);
	    buffer.append(40).append(this.append);
	    buffer.append(50);
	    return buffer.toString();
	}
}
