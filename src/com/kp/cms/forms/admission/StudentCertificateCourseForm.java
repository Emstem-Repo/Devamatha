package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;
import com.kp.cms.to.admission.StudentCertificateCourseTO;

public class StudentCertificateCourseForm extends BaseActionForm{
	private List<CertificateCourseTO> mandatorycourseList;
	private List<CertificateCourseTO> optionalCourseList;
	private List<StudentCertificateCourseTO> courseList;
	private int certificateCourseId;
	private String feeAmount;
	private StudentTO studentTO;
	private String printCourse;
	private String certificateCourseName;
	private CertificateCourseTeacherTO certificateCourseTeacherTO;
	private String registerNO;
	private String semester;
	
	private String method;
	private String searchItem;
	private String optionalCourseId;
	private String studendId;
	
	//Mary 
	private String subjectCode;
	private String subjectName;
	private String subjectGroupName;
	private String academicYear;
	private String semType;
	private String certificateCourse;
	private Map<Integer,String> courseMap;
	private Map<Integer, String> courseCodeName;
	
	//Mary code ends
	
	
	
	
	
	private List<StudentCertificateCourseTO> studentCertificateCourse;
	
	public List<CertificateCourseTO> getMandatorycourseList() {
		return mandatorycourseList;
	}
	public void setMandatorycourseList(List<CertificateCourseTO> mandatorycourseList) {
		this.mandatorycourseList = mandatorycourseList;
	}
	public List<CertificateCourseTO> getOptionalCourseList() {
		return optionalCourseList;
	}
	public void setOptionalCourseList(List<CertificateCourseTO> optionalCourseList) {
		this.optionalCourseList = optionalCourseList;
	}
	public int getCertificateCourseId() {
		return certificateCourseId;
	}
	public void setCertificateCourseId(int certificateCourseId) {
		this.certificateCourseId = certificateCourseId;
	}
	public String getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	public StudentTO getStudentTO() {
		return studentTO;
	}
	public void setStudentTO(StudentTO studentTO) {
		this.studentTO = studentTO;
	}
	public String getPrintCourse() {
		return printCourse;
	}
	public void setPrintCourse(String printCourse) {
		this.printCourse = printCourse;
	}
	public String getCertificateCourseName() {
		return certificateCourseName;
	}
	public void setCertificateCourseName(String certificateCourseName) {
		this.certificateCourseName = certificateCourseName;
	}
	public CertificateCourseTeacherTO getCertificateCourseTeacherTO() {
		return certificateCourseTeacherTO;
	}
	public void setCertificateCourseTeacherTO(
			CertificateCourseTeacherTO certificateCourseTeacherTO) {
		this.certificateCourseTeacherTO = certificateCourseTeacherTO;
	}
	public String getRegisterNO() {
		return registerNO;
	}
	public void setRegisterNO(String registerNO) {
		this.registerNO = registerNO;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	 
	public String getSearchItem() {
		return searchItem;
	}
	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}
	public String getOptionalCourseId() {
		return optionalCourseId;
	}
	public void setOptionalCourseId(String optionalCourseId) {
		this.optionalCourseId = optionalCourseId;
	}
	public String getStudendId() {
		return studendId;
	}
	public void setStudendId(String studendId) {
		this.studendId = studendId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void resetFields() {
		this.registerNO=null;
		this.semester=null;
		this.printCourse="false";
		this.searchItem=null;
		this.optionalCourseId=null;
	}
	
	public void resetSubGrpAssig() {
		this.subjectCode=null;
		this.subjectGroupName=null;
		this.subjectName=null;
		this.certificateCourse="";
	}
	public List<StudentCertificateCourseTO> getStudentCertificateCourse() {
		return studentCertificateCourse;
	}
	public void setStudentCertificateCourse(
			List<StudentCertificateCourseTO> studentCertificateCourse) {
		this.studentCertificateCourse = studentCertificateCourse;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectGroupName() {
		return subjectGroupName;
	}
	public void setSubjectGroupName(String subjectGroupName) {
		this.subjectGroupName = subjectGroupName;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getSemType() {
		return semType;
	}
	public void setSemType(String semType) {
		this.semType = semType;
	}
	public List<StudentCertificateCourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<StudentCertificateCourseTO> courseList) {
		this.courseList = courseList;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public String getCertificateCourse() {
		return certificateCourse;
	}
	public void setCertificateCourse(String certificateCourse) {
		this.certificateCourse = certificateCourse;
	}
	public Map<Integer, String> getCourseCodeName() {
		return courseCodeName;
	}
	public void setCourseCodeName(Map<Integer, String> courseCodeName) {
		this.courseCodeName = courseCodeName;
	}
	
}
