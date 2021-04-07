package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.to.admission.TCDetailsTO;

public class ConductCertificateDetailsForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	private String courseName;
	List<CharacterAndConductTO> list;
	private String dateOfApplication;
	private String classOfLeaving;
	private String characterId;
	private String classId;
	private String academicYear;
	private String academicDuration;
	private boolean isAided;
	Map<Integer, String> classMap;
	List<BoardDetailsTO> boardList;
	private String studentId;
	private String id;
	List<PrintTcDetailsTo> studentList;
	private String isKerala;
	private String programTypeId;
	private String courseId;
	private String classes;
	private String fromUsn;
	private String toUsn;
	private String conductCertificateNo;
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String getIsKerala() {
		return isKerala;
	}
	public void setIsKerala(String isKerala) {
		this.isKerala = isKerala;
	}
	public List<PrintTcDetailsTo> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<PrintTcDetailsTo> studentList) {
		this.studentList = studentList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public List<BoardDetailsTO> getBoardList() {
		return boardList;
	}
	public void setBoardList(List<BoardDetailsTO> boardList) {
		this.boardList = boardList;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public boolean getIsAided() {
		return isAided;
	}
	public void setIsAided(boolean isAided) {
		this.isAided = isAided;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public List<CharacterAndConductTO> getList() {
		return list;
	}
	public void setList(List<CharacterAndConductTO> list) {
		this.list = list;
	}
	public String getDateOfApplication() {
		return dateOfApplication;
	}
	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}
	
	public String getClassOfLeaving() {
		return classOfLeaving;
	}
	public void setClassOfLeaving(String classOfLeaving) {
		this.classOfLeaving = classOfLeaving;
	}
	public String getCharacterId() {
		return characterId;
	}
	public void setCharacterId(String characterId) {
		this.characterId = characterId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getAcademicDuration() {
		return academicDuration;
	}
	public void setAcademicDuration(String academicDuration) {
		this.academicDuration = academicDuration;
	}
	
	public void resetFields(){
		super.setYear(null);
		super.setAcademicYear(null);
		
		this.studentId=null;
		this.classId=null;
		this.courseName=null;
		this.academicDuration=null;
		this.dateOfApplication=null;
		this.characterId=null;
		this.isAided = true;
		this.classOfLeaving=null;
		this.id=null;
		
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getFromUsn() {
		return fromUsn;
	}
	public void setFromUsn(String fromUsn) {
		this.fromUsn = fromUsn;
	}
	public String getToUsn() {
		return toUsn;
	}
	public void setToUsn(String toUsn) {
		this.toUsn = toUsn;
	}
	public String getConductCertificateNo() {
		return conductCertificateNo;
	}
	public void setConductCertificateNo(String conductCertificateNo) {
		this.conductCertificateNo = conductCertificateNo;
	}
	
	

}
