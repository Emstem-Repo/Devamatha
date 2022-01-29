package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * Feb 21, 2010 Created By 9Elements Team
 */
public class BatchClassTO implements Serializable,Comparable<BatchClassTO>{
	private String processId;
	private String joiningBatch;
	private String className;
	private String batchCheck;
	private boolean batchDummyCheck=true;
	private String classId;
	private int semester;
	
	public BatchClassTO(String processId, String joiningBatch,
			String className, String batchCheck, int semester) {
		super();
		this.processId = processId;
		this.joiningBatch = joiningBatch;
		this.className = className;
		this.batchCheck = batchCheck;
		this.semester = semester;
	}

	
	
	public BatchClassTO() {
		super();
	}



	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getJoiningBatch() {
		return joiningBatch;
	}
	public void setJoiningBatch(String joiningBatch) {
		this.joiningBatch = joiningBatch;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setBatchCheck(String batchCheck) {
		this.batchCheck = batchCheck;
	}
	public String getBatchCheck() {
		return batchCheck;
	}



	public void setClassId(String classId) {
		this.classId = classId;
	}



	public String getClassId() {
		return classId;
	}



	public void setBatchDummyCheck(boolean batchDummyCheck) {
		this.batchDummyCheck = batchDummyCheck;
	}



	public boolean getBatchDummyCheck() {
		return batchDummyCheck;
	}



	public int getSemester() {
		return semester;
	}



	public void setSemester(int semester) {
		this.semester = semester;
	}



	@Override
	public int compareTo(BatchClassTO arg0) {
		if(arg0!=null && this!=null && arg0.getClassName()!=null && this.getClassName()!=null){
			return this.getClassName().compareTo(arg0.getClassName());
		}
		return 0;
	}
	
}
