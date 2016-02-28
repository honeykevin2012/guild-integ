package com.game.common.basics.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -4782223728164949336L;

	public AppRuntimeException() {
		super();
	}

	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppRuntimeException(String message) {
		super(message);
	}

	public AppRuntimeException(Throwable cause) {
		super(cause);
	}

	public String toString() {
		return "异常类型： " + super.toString();
	}

	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

	public void printStackTrace() {
		super.printStackTrace();
	}

	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
	}

	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
	}

	public Throwable getCause() {
		return super.getCause();
	}

	public String getMessages() {
		StringBuilder builder = new StringBuilder();
		builder.append("原因：").append(super.getMessage());
		builder.append("方法：").append(super.getStackTrace()[0].getMethodName());
		builder.append("第").append(super.getStackTrace()[0].getLineNumber()).append("行");
		return builder.toString();
	}

	public String getMessage() {
		return super.getMessage();
	}
}
