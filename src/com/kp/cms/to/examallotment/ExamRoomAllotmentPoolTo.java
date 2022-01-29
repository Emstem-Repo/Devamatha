package com.kp.cms.to.examallotment;

import java.io.Serializable;

public class ExamRoomAllotmentPoolTo implements Serializable {
	
	private int id;
	private String poolName;
	
	
	
	public ExamRoomAllotmentPoolTo() {
	}
	

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	

}
