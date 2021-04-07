package com.kp.cms.bo.exam;

import java.util.Date;

/**
 * Dec 17, 2009 Created By 9Elements Team
 */
public class ExamRevaluationTypeBO extends ExamGenBO {

	public ExamRevaluationTypeBO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamRevaluationTypeBO(IExamGenBO obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}

	public ExamRevaluationTypeBO(int id, String name, String createdBy,
			Date createdDate, Date lastModifiedDate, String modifiedBy,
			boolean isActive) {
		super(id, name, createdBy, createdDate, lastModifiedDate, modifiedBy,
				isActive);
		// TODO Auto-generated constructor stub
	}

	public ExamRevaluationTypeBO(int id, String name, String modifiedBy) {
		super(id, name, modifiedBy);
		// TODO Auto-generated constructor stub
	}
}
