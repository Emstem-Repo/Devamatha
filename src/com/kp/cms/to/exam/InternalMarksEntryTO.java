package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Feb 6, 2010 Created By 9Elements Team
 */
public class InternalMarksEntryTO implements Serializable{

	private Integer id;
	private int examId;
	private String examName;
	private int classId;
	private String className;
	private int subId;
	private String subjectName;
	private String subjectCode;
	private String status;
	private List<StudentMarksTO> marksList = new ArrayList<StudentMarksTO>();
	private String subjectType;
	private String courseId;
	private String schemeNo;
	private String finalStatus;
	private int programTypeId;
	private boolean theoryOpen;
	private boolean practicalOpen;
	private String endDate;
	private int batchId;
	private String batchName;
	private String classesId;
	private List<Integer> classList;
	private String theoryPractical;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getSubId() {
		return subId;
	}
	public void setSubId(int subId) {
		this.subId = subId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<StudentMarksTO> getMarksList() {
		return marksList;
	}
	public void setMarksList(List<StudentMarksTO> marksList) {
		this.marksList = marksList;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	public int getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(int programTypeId) {
		this.programTypeId = programTypeId;
	}
	public boolean isTheoryOpen() {
		return theoryOpen;
	}
	public void setTheoryOpen(boolean theoryOpen) {
		this.theoryOpen = theoryOpen;
	}
	public boolean isPracticalOpen() {
		return practicalOpen;
	}
	public void setPracticalOpen(boolean practicalOpen) {
		this.practicalOpen = practicalOpen;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getClassesId() {
		return classesId;
	}
	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}
	public List<Integer> getClassList() {
		return classList;
	}
	public void setClassList(List<Integer> classList) {
		this.classList = classList;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getTheoryPractical() {
		return theoryPractical;
	}
	public void setTheoryPractical(String theoryPractical) {
		this.theoryPractical = theoryPractical;
	}
	
}
