package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;
import com.kp.cms.to.exam.ExamSpecializationTO;

public class SubjectGroupDetailsForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	
	private List<CreatePracticalBatchTO> studentList;
	
	private String reset;
	private int halfLength;
	
	private List<CreatePracticalBatchTO> allBatchList;
	int batchId;
	String message;	
	private List<StudentTO>existingStudentList;
	private String oldBatchName;
	private boolean check;
	private String year;
	private String className;
	private List<SubjectGroupDetailsTo> subjectList;
	private List<SubjectGroupDetailsTo> subjectGroupMap;
	private List<SubjectGroupDetailsTo> subjectGroupList;
	private boolean flag;
	private boolean flag1;
	private List<String> list;
	private String displayMessage;
	private String displayMsg;
	private List<ExamSpecializationTO> examSpecializationTO;
	private String specializationId;
	private List<String> specializationStuList;
	private String specializationMessage;
	private Map<Integer,String> specializationMap1;
	
	public String getOldBatchName() {
		return oldBatchName;
	}
	public void setOldBatchName(String oldBatchName) {
		this.oldBatchName = oldBatchName;
	}
	public List<StudentTO> getExistingStudentList() {
		return existingStudentList;
	}
	public void setExistingStudentList(List<StudentTO> existingStudentList) {
		this.existingStudentList = existingStudentList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public String getReset() {
		return reset;
	}
	public void setReset(String reset) {
		this.reset = reset;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<CreatePracticalBatchTO> getStudentList() {
		return studentList;
	}
	public int getHalfLength() {
		return halfLength;
	}
	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}
	public List<CreatePracticalBatchTO> getAllBatchList() {
		return allBatchList;
	}
	public void setAllBatchList(List<CreatePracticalBatchTO> allBatchList) {
		this.allBatchList = allBatchList;
	}
	public void setStudentList(List<CreatePracticalBatchTO> studentList) {
		this.studentList = studentList;
	}
	public String getAttendanceTypeId() {
		return attendanceTypeId;
	}
	public void setAttendanceTypeId(String attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Used while validating form fields
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public void clear(){
		this.setYear(null);
		this.setClassId(null);
		this.setSubjectId(null);
		this.oldBatchName = null;
		this.setClassSchemewiseId(null);
		this.attendanceTypeId=null;
		super.clear();
	}
	public void clearList(){
		this.studentList=null;
		this.subjectList=null;
		this.setSubjectGroupMap(null);
		this.subjectGroupList=null;
		this.setClassSchemewiseId(null);
		this.flag=false;
		this.flag1=false;
		this.displayMessage = null;
	}

	public void setSubjectList(List<SubjectGroupDetailsTo> subjectList) {
		this.subjectList = subjectList;
	}
	public List<SubjectGroupDetailsTo> getSubjectList() {
		return subjectList;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public boolean isCheck() {
		return check;
	}
	public void setSubjectGroupList(List<SubjectGroupDetailsTo> subjectGroupList) {
		this.subjectGroupList = subjectGroupList;
	}
	public List<SubjectGroupDetailsTo> getSubjectGroupList() {
		return subjectGroupList;
	}
	public void setSubjectGroupMap(List<SubjectGroupDetailsTo> subjectGroupMap) {
		this.subjectGroupMap = subjectGroupMap;
	}
	public List<SubjectGroupDetailsTo> getSubjectGroupMap() {
		return subjectGroupMap;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public boolean isFlag() {
		return flag;
	}
	public boolean isFlag1() {
		return flag1;
	}
	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public List<String> getList() {
		return list;
	}
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	public String getDisplayMessage() {
		return displayMessage;
	}
	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}
	public String getDisplayMsg() {
		return displayMsg;
	}
	public void setExamSpecializationTO(List<ExamSpecializationTO> examSpecializationTO) {
		this.examSpecializationTO = examSpecializationTO;
	}
	public List<ExamSpecializationTO> getExamSpecializationTO() {
		return examSpecializationTO;
	}
	public void setSpecializationId(String specializationId) {
		this.specializationId = specializationId;
	}
	public String getSpecializationId() {
		return specializationId;
	}
	public void setSpecializationStuList(List<String> specializationStuList) {
		this.specializationStuList = specializationStuList;
	}
	public List<String> getSpecializationStuList() {
		return specializationStuList;
	}
	public void setSpecializationMessage(String specializationMessage) {
		this.specializationMessage = specializationMessage;
	}
	public String getSpecializationMessage() {
		return specializationMessage;
	}
	public void setSpecializationMap1(Map<Integer,String> specializationMap1) {
		this.specializationMap1 = specializationMap1;
	}
	public Map<Integer,String> getSpecializationMap1() {
		return specializationMap1;
	}
	

	
}
