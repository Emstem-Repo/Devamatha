package com.kp.cms.bo.exam;

import java.util.Date;

/**
 * Dec 17, 2009 Created By 9Elements Team
 */
public class ExamAssignmentTypeMasterBO extends ExamGenBO {

	public ExamAssignmentTypeMasterBO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamAssignmentTypeMasterBO(IExamGenBO obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}

	public ExamAssignmentTypeMasterBO(int id, String name, String createdBy,
			Date createdDate, Date lastModifiedDate, String modifiedBy,
			boolean isActive) {
		super(id, name, createdBy, createdDate, lastModifiedDate, modifiedBy, isActive);
		// TODO Auto-generated constructor stub
	}

	public ExamAssignmentTypeMasterBO(int id, String name, String modifiedBy) {
		super(id, name, modifiedBy);
		// TODO Auto-generated constructor stub
	}
	
}
