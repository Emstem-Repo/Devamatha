package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;

public class HostelDisciplinaryDetailsForm extends BaseActionForm {

	private static final long serialVersionUID = 1L;
	private int id;
	private int boId;
	private String registerNo;
	private String year;
	private String disciplineName;
	private String disciplineId;
	private String description;
	private String admissionId;
	private String date;
	private String studentName;
	private String studentRoomNo;
	private String studentBedNo;
	private String studentBlock;
	private String studentUnit;
	private String studentClass;
	private String studentHostel;
	private String status;

	private List<HlDisciplinaryDetailsTO> disciplineList;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}


	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentRoomNo() {
		return studentRoomNo;
	}

	public void setStudentRoomNo(String studentRoomNo) {
		this.studentRoomNo = studentRoomNo;
	}

	public String getStudentBedNo() {
		return studentBedNo;
	}

	public void setStudentBedNo(String studentBedNo) {
		this.studentBedNo = studentBedNo;
	}

	public String getStudentBlock() {
		return studentBlock;
	}

	public void setStudentBlock(String studentBlock) {
		this.studentBlock = studentBlock;
	}

	public String getStudentUnit() {
		return studentUnit;
	}

	public void setStudentUnit(String studentUnit) {
		this.studentUnit = studentUnit;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	public String getStudentHostel() {
		return studentHostel;
	}

	public void setStudentHostel(String studentHostel) {
		this.studentHostel = studentHostel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getBoId() {
		return boId;
	}

	public void setBoId(int boId) {
		this.boId = boId;
	}

	public String getAdmissionId() {
		return admissionId;
	}

	public void setAdmissionId(String admissionId) {
		this.admissionId = admissionId;
	}

	public String getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDisciplineName() {
		return disciplineName;
	}

	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<HlDisciplinaryDetailsTO> getDisciplineList() {
		return disciplineList;
	}

	public void setDisciplineList(List<HlDisciplinaryDetailsTO> disciplineList) {
		this.disciplineList = disciplineList;
	}

	public void clear(){
		super.clear();
		this.registerNo=null;
		this.year=null;
		this.disciplineName=null;
		this.disciplineId=null;
		this.description=null;
		this.disciplineList=null;
		this.date=null;
		this.status=null;
	}
	
	public void clearStudentDetails(){
		this.studentName=null;
		this.studentBedNo=null;
		this.studentBlock=null;
		this.studentClass=null;
		this.studentHostel=null;
		this.studentRoomNo=null;
		this.studentUnit=null;
		this.status=null;
	}

	public void clearAfterAdd(){
		super.clear();
		this.registerNo=null;
		this.year=null;
		this.disciplineName=null;
		this.disciplineId=null;
		this.description=null;
		this.date=null;
		this.studentName=null;
		this.studentBedNo=null;
		this.studentBlock=null;
		this.studentClass=null;
		this.studentHostel=null;
		this.studentRoomNo=null;
		this.studentUnit=null;
		this.status=null;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
