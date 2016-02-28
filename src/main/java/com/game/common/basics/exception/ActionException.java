package com.game.common.basics.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ActionException extends AppRuntimeException implements HandlerExceptionResolver {
	private static final long serialVersionUID = 1L;

	public ActionException() {
		super();
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionException(String message) {
		super(message);
	}

	public ActionException(Throwable cause) {
		super(cause);
	}
	
	public String getMessages(Exception ex) {
		StringWriter writer = new StringWriter(); 
		ex.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}
	
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		request.setAttribute("exception", this.getMessages(ex));
		return new ModelAndView("/common/error");
	}
	public static void main(String[] args) {
	}
}
