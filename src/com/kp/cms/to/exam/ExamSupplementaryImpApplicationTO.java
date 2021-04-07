package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

/**
 * Jan 25, 2010 Created By 9Elements
 */
@SuppressWarnings("serial")
public class ExamSupplementaryImpApplicationTO implements Serializable,Comparable<ExamSupplementaryImpApplicationTO> {
	private Integer id;
	private Integer studentId;
	private String regNumber;
	private String rollNumber;
	private boolean useRollNo;
	private boolean rollNoPresent;
	private boolean regNoPresent;
	private String studentName;
	private Integer classId;
	private Integer courseId;
	private boolean isSupplementary;
	private Integer examId;
	private String courseName;
	private String schemeName;
	private String section;
	List<ExamSupplementaryImpApplicationSubjectTO> subjectList;
	
	public ExamSupplementaryImpApplicationTO(String regNumber,
			String rollNumber, boolean useRollNo, Integer courseId,
			Integer semesterYearNo, Integer schemeId) {
		super();
		this.regNumber = regNumber;
		this.rollNumber = rollNumber;
		this.useRollNo = useRollNo;

		this.courseId = courseId;
		this.semester_year_no = semesterYearNo;
		this.schemeId = schemeId;
	}

	// for search

	public ExamSupplementaryImpApplicationTO(String regNumber,
			String rollNumber, boolean rollNoPresent, boolean regNoPresent,
			Integer courseId, Integer semesterYearNo, Integer schemeId,boolean isSupplementary) {
		super();
		
		this.regNumber = regNumber;
		this.rollNumber = rollNumber;
		this.regNoPresent=regNoPresent;
		this.rollNoPresent=rollNoPresent;

		this.courseId = courseId;
		this.semester_year_no = semesterYearNo;
		this.schemeId = schemeId;
		this.isSupplementary=isSupplementary;
	}
// to display search value
	public ExamSupplementaryImpApplicationTO(Integer studentId, String className,
			String rollNumber, String regNumber, String studentName) {
		super();
		this.studentId = studentId;
		this.className = className;
		this.rollNumber = rollNumber;
		this.regNumber = regNumber;
		this.studentName = studentName;
	}

	private Integer semester_year_no;
	private Integer schemeId;
	private String className;

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

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ExamSupplementaryImpApplicationTO() {
		super();
	}

	public ExamSupplementaryImpApplicationTO(Integer id, Integer studentId,
			String regNumber, String rollNumber, String studentName,
			Integer classId, String className) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.regNumber = regNumber;
		this.rollNumber = rollNumber;
		this.studentName = studentName;
		this.classId = classId;
		this.className = className;
	}

	public void setSemester_year_no(Integer semester_year_no) {
		this.semester_year_no = semester_year_no;
	}

	public Integer getSemester_year_no() {
		return semester_year_no;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	public Integer getSchemeId() {
		return schemeId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setUseRollNo(boolean useRollNo) {
		this.useRollNo = useRollNo;
	}

	public boolean getUseRollNo() {
		return useRollNo;
	}

	public void setRollNoPresent(boolean rollNoPresent) {
		this.rollNoPresent = rollNoPresent;
	}

	public boolean getRollNoPresent() {
		return rollNoPresent;
	}

	public void setRegNoPresent(boolean regNoPresent) {
		this.regNoPresent = regNoPresent;
	}

	public boolean getRegNoPresent() {
		return regNoPresent;
	}

	public void setSupplementary(boolean isSupplementary) {
		this.isSupplementary = isSupplementary;
	}

	public boolean getIsSupplementary() {
		return isSupplementary;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public List<ExamSupplementaryImpApplicationSubjectTO> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(
			List<ExamSupplementaryImpApplicationSubjectTO> subjectList) {
		this.subjectList = subjectList;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Override
	public int compareTo(ExamSupplementaryImpApplicationTO arg0) {
		if(arg0!=null && this!=null && arg0.getRegNumber()!=null
				 && this.getRegNumber()!=null){
			return this.getRegNumber().compareTo(arg0.getRegNumber());
		}else
		return 0;
	}

}
