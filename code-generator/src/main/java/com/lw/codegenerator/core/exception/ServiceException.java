package com.lw.codegenerator.core.exception;

import java.io.Serializable;

/**
 * @Classname ServiceException
 * @Description service异常处理类
 * @Date 2019/12/10 17:57
 * @Author lw
 */
public class ServiceException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 1213855733833039552L;

	public ServiceException() { }

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}