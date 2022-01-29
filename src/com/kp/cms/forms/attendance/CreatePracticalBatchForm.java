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

public class CreatePracticalBatchForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private String batchName;
	private String regNoFrom;
	private String regNoTo;
	private List<CreatePracticalBatchTO> studentList;
	private String checked[];
	private String reset;
	private int halfLength;
	private String regdNoDisplay;	
	private List<CreatePracticalBatchTO> allBatchList;
	int batchId;
	String message;	
	private List<StudentTO>existingStudentList;
	private String oldBatchName;
	private List<Integer> oldStudentList;
	private String attendanceTypeId;
	private String activityId;
	private Map<Integer,String> attendanceTypes;
	private Map<Integer,String> activityMap;
	private String activityName;
	private String attTypeName;

	public List<Integer> getOldStudentList() {
		return oldStudentList;
	}

	public void setOldStudentList(List<Integer> oldStudentList) {
		this.oldStudentList = oldStudentList;
	}

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

	public String getRegdNoDisplay() {
		return regdNoDisplay;
	}

	public void setRegdNoDisplay(String regdNoDisplay) {
		this.regdNoDisplay = regdNoDisplay;
	}

	public String getReset() {
		return reset;
	}

	public void setReset(String reset) {
		this.reset = reset;
	}

	public String[] getChecked() {
		return checked;
	}

	public void setChecked(String[] checked) {
		this.checked = checked;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getRegNoFrom() {
		return regNoFrom;
	}

	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}

	public String getRegNoTo() {
		return regNoTo;
	}

	public void setRegNoTo(String regNoTo) {
		this.regNoTo = regNoTo;
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

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Map<Integer, String> getAttendanceTypes() {
		return attendanceTypes;
	}

	public void setAttendanceTypes(Map<Integer, String> attendanceTypes) {
		this.attendanceTypes = attendanceTypes;
	}

	public Map<Integer, String> getActivityMap() {
		return activityMap;
	}

	public void setActivityMap(Map<Integer, String> activityMap) {
		this.activityMap = activityMap;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getAttTypeName() {
		return attTypeName;
	}

	public void setAttTypeName(String attTypeName) {
		this.attTypeName = attTypeName;
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
		this.batchName = null;
		this.regNoFrom = null;
		this.regNoTo = null;
		this.oldBatchName = null;
		this.setClassSchemewiseId(null);
		this.activityId=null;
		this.attendanceTypeId=null;
		super.clear();
	}
	public void clearList(){
		this.studentList=null;
	}
}
