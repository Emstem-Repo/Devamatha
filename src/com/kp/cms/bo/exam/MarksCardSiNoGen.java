package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;

public class MarksCardSiNoGen implements Serializable {
	
	private int id;
	private Classes classId;
	private Student studentId;
	private ExamDefinition examId;
	private String marksCardNo;
	private boolean isDuplicate;
	private boolean isConsolidate;
	private boolean isRegular;
	private boolean isSupplementary;
	private boolean isImprovement;
	private boolean isRevaluation;
	private String marksCardNo1;
	private Date createdDate;
	private String createdBy;
	private Integer additionalNo;
	private boolean isGrace;
	
	public MarksCardSiNoGen(){}
	
	public MarksCardSiNoGen(int id, Classes classId, Student studentId,
			ExamDefinition examId, String marksCardNo, boolean isDuplicate,
			boolean isConsolidate, boolean isRegular, boolean isSupplementary,
			boolean isImprovement, boolean isRevaluation, String marksCardNo1,
			Date createdDate, String createdBy,Integer additionalNo) {
		super();
		this.id = id;
		this.classId = classId;
		this.studentId = studentId;
		this.examId = examId;
		this.marksCardNo = marksCardNo;
		this.isDuplicate = isDuplicate;
		this.isConsolidate = isConsolidate;
		this.isRegular = isRegular;
		this.isSupplementary = isSupplementary;
		this.isImprovement = isImprovement;
		this.isRevaluation = isRevaluation;
		this.marksCardNo1 = marksCardNo1;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.additionalNo=additionalNo;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Classes getClassId() {
		return classId;
	}
	public void setClassId(Classes classId) {
		this.classId = classId;
	}
	public Student getStudentId() {
		return studentId;
	}
	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}
	public ExamDefinition getExamId() {
		return examId;
	}
	public void setExamId(ExamDefinition examId) {
		this.examId = examId;
	}
	public String getMarksCardNo() {
		return marksCardNo;
	}
	public void setMarksCardNo(String marksCardNo) {
		this.marksCardNo = marksCardNo;
	}
	public boolean getIsDuplicate() {
		return isDuplicate;
	}
	public void setIsDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}
	
	public boolean getIsConsolidate() {
		return isConsolidate;
	}
	public void setIsConsolidate(boolean isConsolidate) {
		this.isConsolidate = isConsolidate;
	}

	public boolean getIsRegular() {
		return isRegular;
	}

	public void setIsRegular(boolean isRegular) {
		this.isRegular = isRegular;
	}

	public boolean getIsSupplementary() {
		return isSupplementary;
	}

	public void setIsSupplementary(boolean isSupplementary) {
		this.isSupplementary = isSupplementary;
	}

	public boolean getIsImprovement() {
		return isImprovement;
	}

	public void setIsImprovement(boolean isImprovement) {
		this.isImprovement = isImprovement;
	}

	public boolean getIsRevaluation() {
		return isRevaluation;
	}

	public void setIsRevaluation(boolean isRevaluation) {
		this.isRevaluation = isRevaluation;
	}
	
	public String getMarksCardNo1() {
		return marksCardNo1;
	}
	
	public void setMarksCardNo1(String marksCardNo1) {
		this.marksCardNo1 = marksCardNo1;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getAdditionalNo() {
		return additionalNo;
	}

	public void setAdditionalNo(Integer additionalNo) {
		this.additionalNo = additionalNo;
	}

	public boolean getIsGrace() {
		return isGrace;
	}

	public void setIsGrace(boolean isGrace) {
		this.isGrace = isGrace;
	}

	
}
