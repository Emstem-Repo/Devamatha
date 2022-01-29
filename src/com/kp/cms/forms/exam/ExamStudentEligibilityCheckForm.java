package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamStudentEligibilityCheckTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamStudentEligibilityCheckForm extends BaseActionForm {
	private String classValues;
	private String displayFor;
	private String classIdsFrom;
	private String classIdsTo;
	private String examName;
	private boolean examFeeStatus;
	private boolean courseFeeStatus;
	private boolean labFeeStatus;
	private boolean attendenceStatus;
	private boolean noElgibilityStatus;
	private String rowCount;
	private int examId;
	private String display;
	private ArrayList<KeyValueTO> listExamType;
	private ArrayList<KeyValueTO> listExamName;
	private ArrayList<KeyValueTO> listAddEligibility;
	private Map<Integer, String> retainFromMapClass;
	private Map<Integer, String> retainMapSelectedClass;
	private Map<Integer, String> mapClass;
	private Map<Integer, String> mapSelectedClass;
	private ArrayList<ExamStudentEligibilityCheckTO> listStudent;
	//added by smitha
	 private Map<Integer,String> examNameMap;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		this.displayFor = "";
		this.examName = "";

	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getClassValues() {
		return classValues;
	}

	public void setClassValues(String classValues) {
		this.classValues = classValues;
	}

	public String getDisplayFor() {
		return displayFor;
	}

	public void setDisplayFor(String displayFor) {
		this.displayFor = displayFor;
	}

	public ArrayList<KeyValueTO> getListExamType() {
		return listExamType;
	}

	public void setListExamType(ArrayList<KeyValueTO> listExamType) {
		this.listExamType = listExamType;
	}

	public ArrayList<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public void setListExamName(ArrayList<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}

	public String getClassIdsFrom() {
		return classIdsFrom;
	}

	public void setClassIdsFrom(String classIdsFrom) {
		this.classIdsFrom = classIdsFrom;
	}

	public String getClassIdsTo() {
		return classIdsTo;
	}

	public void setClassIdsTo(String classIdsTo) {
		this.classIdsTo = classIdsTo;
	}

	public Map<Integer, String> getMapClass() {
		return mapClass;
	}

	public void setMapClass(Map<Integer, String> mapClass) {
		this.mapClass = mapClass;
	}

	public Map<Integer, String> getMapSelectedClass() {
		return mapSelectedClass;
	}

	public void setMapSelectedClass(Map<Integer, String> mapSelectedClass) {
		this.mapSelectedClass = mapSelectedClass;
	}

	public void setListStudent(
			ArrayList<ExamStudentEligibilityCheckTO> listStudent) {
		this.listStudent = listStudent;
	}

	public ArrayList<ExamStudentEligibilityCheckTO> getListStudent() {
		return listStudent;
	}

	public void setListAddEligibility(ArrayList<KeyValueTO> listAddEligibility) {
		this.listAddEligibility = listAddEligibility;
	}

	public ArrayList<KeyValueTO> getListAddEligibility() {
		return listAddEligibility;
	}

	public void setExamFeeStatus(boolean examFeeStatus) {
		this.examFeeStatus = examFeeStatus;
	}

	public boolean isExamFeeStatus() {
		return examFeeStatus;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public String getRowCount() {
		return rowCount;
	}

	public boolean getCourseFeeStatus() {
		return courseFeeStatus;
	}

	public void setCourseFeeStatus(boolean courseFeeStatus) {
		this.courseFeeStatus = courseFeeStatus;
	}

	public boolean getLabFeeStatus() {
		return labFeeStatus;
	}

	public void setLabFeeStatus(boolean labFeeStatus) {
		this.labFeeStatus = labFeeStatus;
	}

	public boolean getAttendenceStatus() {
		return attendenceStatus;
	}

	public void setAttendenceStatus(boolean attendenceStatus) {
		this.attendenceStatus = attendenceStatus;
	}

	public void setNoElgibilityStatus(boolean noElgibilityStatus) {
		this.noElgibilityStatus = noElgibilityStatus;
	}

	public boolean isNoElgibilityStatus() {
		return noElgibilityStatus;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getExamId() {
		return examId;
	}

	public void setRetainFromMapClass(Map<Integer, String> retainFromMapClass) {
		this.retainFromMapClass = retainFromMapClass;
	}

	public Map<Integer, String> getRetainFromMapClass() {
		return retainFromMapClass;
	}

	public void setRetainMapSelectedClass(
			Map<Integer, String> retainMapSelectedClass) {
		this.retainMapSelectedClass = retainMapSelectedClass;
	}

	public Map<Integer, String> getRetainMapSelectedClass() {
		return retainMapSelectedClass;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}

	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
}
