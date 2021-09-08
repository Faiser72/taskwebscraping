package com.aspiresys.task1.beans.response;

import java.util.List;

public class Task1Response {

	private boolean success;
	private String message;
	private Object object;
	private List<?> listObject;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public List<?> getListObject() {
		return listObject;
	}

	public void setListObject(List<?> listObject) {
		this.listObject = listObject;
	}
}
