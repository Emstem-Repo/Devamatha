package com.kp.cms.bo.exam;

import java.util.Date;

/**
 * Dec 17, 2009 Created By 9Elements Team
 */
public class ExamSubjectTypeMasterBO extends ExamGenBO {

	public ExamSubjectTypeMasterBO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamSubjectTypeMasterBO(IExamGenBO obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}

	public ExamSubjectTypeMasterBO(int id, String name, String createdBy,
			Date createdDate, Date lastModifiedDate, String modifiedBy,
			boolean isActive) {
		super(id, name, createdBy, createdDate, lastModifiedDate, modifiedBy,
				isActive);
		// TODO Auto-generated constructor stub
	}

	public ExamSubjectTypeMasterBO(int id, String name, String modifiedBy) {
		super(id, name, modifiedBy);
		// TODO Auto-generated constructor stub
	}
	private Integer order;
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
