package com.kp.cms.to.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.io.Serializable;

public class GradeDefinitionBatchWisTo implements Serializable {

	private int id;
	private String course;;
	private String fromBatch;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getFromBatch() {
		return fromBatch;
	}
	public void setFromBatch(String fromBatch) {
		this.fromBatch = fromBatch;
	}

}
