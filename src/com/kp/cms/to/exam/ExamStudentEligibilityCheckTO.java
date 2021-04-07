package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ExamStudentEligibilityCheckTO implements Serializable,Comparable<ExamStudentEligibilityCheckTO> {

	private Integer id;
	private Integer rowId;
	private Integer detailId;
	private Integer studentId;
	private String rollNo;
	private String regNO;
	private String studentName;
	private String classCode;
	private String remarks;
	private Integer classId;
	private ArrayList<ExamExamEligibilityTO> listOfEligibility;

	private String courseFees;
	private String examFees;
	private String attendance;
	private String labFees;
	private String examElgibility;

	private boolean dummycourseFees;
	private boolean dummyexamFees;
	private boolean dummyattendance;
	private boolean dummylabFees;
	private boolean dummyexamElgibility;
	private boolean dummycourseFeeDisable;
	private boolean dummyexamFeeDisable;
	private boolean dummyattendanceDisable;
	private boolean dummylabFeeDisable;
	private boolean checkExam;

	public ExamStudentEligibilityCheckTO() {
		super();
	}

	public ExamStudentEligibilityCheckTO(int id, int studentId, String rollNo,
			String regNO, String studentName, String classCode, String remarks,
			ArrayList<ExamExamEligibilityTO> listOfEligibility,
			boolean dummycourseFees, boolean dummyexamFees,
			boolean dummyattendance, boolean dummylabFees,
			boolean dummyexamElgibility) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.rollNo = rollNo;
		this.regNO = regNO;
		this.studentName = studentName;
		this.classCode = classCode;
		this.remarks = remarks;
		this.listOfEligibility = listOfEligibility;
		this.dummycourseFees = dummycourseFees;
		this.dummyexamFees = dummyexamFees;
		this.dummyattendance = dummyattendance;
		this.dummylabFees = dummylabFees;
		this.dummyexamElgibility = dummyexamElgibility;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getRegNO() {
		return regNO;
	}

	public void setRegNO(String regNO) {
		this.regNO = regNO;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ArrayList<ExamExamEligibilityTO> getListOfEligibility() {
		return listOfEligibility;
	}

	public void setListOfEligibility(
			ArrayList<ExamExamEligibilityTO> listOfEligibility) {
		this.listOfEligibility = listOfEligibility;
	}

	public boolean getDummycourseFees() {
		return dummycourseFees;
	}

	public void setDummycourseFees(boolean dummycourseFees) {
		this.dummycourseFees = dummycourseFees;
	}

	public boolean getDummyexamFees() {
		return dummyexamFees;
	}

	public void setDummyexamFees(boolean dummyexamFees) {
		this.dummyexamFees = dummyexamFees;
	}

	public boolean getDummyattendance() {
		return dummyattendance;
	}

	public void setDummyattendance(boolean dummyattendance) {
		this.dummyattendance = dummyattendance;
	}

	public boolean getDummylabFees() {
		return dummylabFees;
	}

	public void setDummylabFees(boolean dummylabFees) {
		this.dummylabFees = dummylabFees;
	}

	public String getCourseFees() {
		return courseFees;
	}

	public void setCourseFees(String courseFees) {
		this.courseFees = courseFees;
	}

	public String getExamFees() {
		return examFees;
	}

	public void setExamFees(String examFees) {
		this.examFees = examFees;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getLabFees() {
		return labFees;
	}

	public void setLabFees(String labFees) {
		this.labFees = labFees;
	}

	public String getExamElgibility() {
		return examElgibility;
	}

	public void setExamElgibility(String examElgibility) {
		this.examElgibility = examElgibility;
	}

	public boolean getDummyexamElgibility() {
		return dummyexamElgibility;
	}

	public void setDummyexamElgibility(boolean dummyexamElgibility) {
		this.dummyexamElgibility = dummyexamElgibility;
	}

	public boolean getDummycourseFeeDisable() {
		return dummycourseFeeDisable;
	}

	public void setDummycourseFeeDisable(boolean dummycourseFeeDisable) {
		this.dummycourseFeeDisable = dummycourseFeeDisable;
	}

	public boolean getDummyexamFeeDisable() {
		return dummyexamFeeDisable;
	}

	public void setDummyexamFeeDisable(boolean dummyexamFeeDisable) {
		this.dummyexamFeeDisable = dummyexamFeeDisable;
	}

	public boolean getDummyattendanceDisable() {
		return dummyattendanceDisable;
	}

	public void setDummyattendanceDisable(boolean dummyattendanceDisable) {
		this.dummyattendanceDisable = dummyattendanceDisable;
	}

	public boolean getDummylabFeeDisable() {
		return dummylabFeeDisable;
	}

	public void setDummylabFeeDisable(boolean dummylabFeeDisable) {
		this.dummylabFeeDisable = dummylabFeeDisable;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public Integer getRowId() {
		return rowId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setCheckExam(boolean checkExam) {
		this.checkExam = checkExam;
	}

	public boolean getCheckExam() {
		return checkExam;
	}

	@Override
	public int compareTo(ExamStudentEligibilityCheckTO arg0) {
		if(arg0!=null && this!=null && arg0.getExamElgibility()!=null
				 && this.getExamElgibility()!=null){
			return this.getExamElgibility().compareTo(arg0.getExamElgibility());
		}else
		return 0;
	}

}
