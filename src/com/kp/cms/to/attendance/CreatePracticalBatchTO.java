package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;


@SuppressWarnings("serial")
public class CreatePracticalBatchTO implements Serializable {
	
	private int id;
	private boolean checkValue;
	private ClassSchemewiseTO classSchemewiseTO;
	private SubjectTO subjectTO;
	private StudentTO studentTO;
	private String createdBy;
	private String modifiedBy;
	private String batchName;
	private boolean dummyCheckValue;
	private boolean tempCheckValue;
	private List<BatchStudentTO> batchStudentTOList;
	private int batchStudentId;
	private ActivityTO activityTO;
	private String attendanceTypeId;
	private String attendanceTypeName;
	private String checked;
	
	public int getBatchStudentId() {
		return batchStudentId;
	}
	public void setBatchStudentId(int batchStudentId) {
		this.batchStudentId = batchStudentId;
	}
	public boolean isTempCheckValue() {
		return tempCheckValue;
	}
	public void setTempCheckValue(boolean tempCheckValue) {
		this.tempCheckValue = tempCheckValue;
	}
	
	public boolean isCheckValue() {
		return checkValue;
	}
	public void setCheckValue(boolean checkValue) {
		this.checkValue = checkValue;
	}
	public ClassSchemewiseTO getClassSchemewiseTO() {
		return classSchemewiseTO;
	}
	public void setClassSchemewiseTO(ClassSchemewiseTO classSchemewiseTO) {
		this.classSchemewiseTO = classSchemewiseTO;
	}
	public SubjectTO getSubjectTO() {
		return subjectTO;
	}
	public void setSubjectTO(SubjectTO subjectTO) {
		this.subjectTO = subjectTO;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public boolean isDummyCheckValue() {
		return dummyCheckValue;
	}
	public void setDummyCheckValue(boolean dummyCheckValue) {
		this.dummyCheckValue = dummyCheckValue;
	}
	public List<BatchStudentTO> getBatchStudentTOList() {
		return batchStudentTOList;
	}
	public void setBatchStudentTOList(List<BatchStudentTO> batchStudentTOList) {
		this.batchStudentTOList = batchStudentTOList;
	}
	public StudentTO getStudentTO() {
		return studentTO;
	}
	public void setStudentTO(StudentTO studentTO) {
		this.studentTO = studentTO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ActivityTO getActivityTO() {
		return activityTO;
	}
	public void setActivityTO(ActivityTO activityTO) {
		this.activityTO = activityTO;
	}
	public String getAttendanceTypeId() {
		return attendanceTypeId;
	}
	public void setAttendanceTypeId(String attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}
	public String getAttendanceTypeName() {
		return attendanceTypeName;
	}
	public void setAttendanceTypeName(String attendanceTypeName) {
		this.attendanceTypeName = attendanceTypeName;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}	

}
