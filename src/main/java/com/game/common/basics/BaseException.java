package com.game.common.basics;

import java.io.PrintStream;
import java.io.PrintWriter;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	private Throwable throwable;

	/**
	  */
	public BaseException() {
		super();
	}

	/**
	  */
	public BaseException(String msg) {
		super(msg);
	}

	/**
	 * @param throwable
	 */
	public BaseException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	  * 
	  */
	public Throwable getException() {
		throwable = super.getCause();
		return throwable;
	}

	/**
	  * 
	  */
	public void printStackTrace() {
		super.printStackTrace();
	}

	/**
	 * 
	 * @param printStream
	 */
	public void printStackTrace(PrintStream printStream) {
		super.printStackTrace(printStream);
	}

	/**
	 * 
	 * @param printWriter
	 */
	public void printStackTrace(PrintWriter printWriter) {
		super.printStackTrace(printWriter);
	}
}
