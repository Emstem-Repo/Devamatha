package com.kp.cms.to.admin;

public class EntrancedetailsTO {
	private int id;
	private String name;
	private ProgramTO programTO;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public ProgramTO getProgramTO() {
		return programTO;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProgramTO(ProgramTO programTO) {
		this.programTO = programTO;
	}
	
}
