package com.kp.cms.to.exam;

/**
 * Jan 4, 2010 Created By 9Elements
 */
import java.io.Serializable;
import java.util.List;

public class ExamSpecializationSubjectGroupTO implements Serializable,Comparable<ExamSpecializationSubjectGroupTO> {

	private int id;
	private String specialization;
	private List<DisplayValueTO> listSubjects;
	private String[] subjectIds;

	public ExamSpecializationSubjectGroupTO(int id, String specialization,
			List<DisplayValueTO> listSubjects) {
		super();
		this.id = id;
		this.specialization = specialization;
		this.listSubjects = listSubjects;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public ExamSpecializationSubjectGroupTO() {
		super();
	}

	public List<DisplayValueTO> getListSubjects() {
		return listSubjects;
	}

	public void setListSubjects(List<DisplayValueTO> listSubjects) {
		this.listSubjects = listSubjects;
	}

	public void setSubjectIds(String[] subjectIds) {
		this.subjectIds = subjectIds;
	}

	public String[] getSubjectIds() {
		return subjectIds;
	}

	@Override
	public int compareTo(ExamSpecializationSubjectGroupTO arg0) {
		if(arg0!=null && this!=null && arg0.getSpecialization()!=null 
				 && this.getSpecialization()!=null){
			
				return this.getSpecialization().compareTo(arg0.getSpecialization());
		}else
		return 0;
	}
}