package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

public class ClearanceCertificateTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String registerNo;
	private String className;
	private String date;
	private List<String> comments;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
}
