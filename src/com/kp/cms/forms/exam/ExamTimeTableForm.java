package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamSubjectTimeTableTO;
import com.kp.cms.to.exam.ExamTimeTableTO;

@SuppressWarnings("serial")
public class ExamTimeTableForm extends BaseActionForm {
	private String examId;
	private int examTypeId;
	private String examName;
	private Integer examNameId;
	private List<ExamTimeTableTO> mainList;
	private HashMap<Integer, String> examTypeMap;
	private HashMap<Integer, String> mapExamName ;
	private int id;
	private String examType;
	private String batch;
	private String program;
	private String course;
	private String scheme;
	private String status;
	private ExamTimeTableTO examTimeTableTO;
	private ArrayList<ExamSubjectTimeTableTO> examSubjectTimeTableTO;
	private int subId;
	private String subjectCode;
	private String subjectName;
	private String date;
	private String startTimeHour;
	private String startTimeMin;
	private String endTimeHour;
	private String endTimeMin;
	private String programId;
	private String programTypeId;
	private Map<Integer, String> sessionMap;
	//added by Smitha
	 private Map<Integer,String> examNameMap;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		examType = null;

	}

	public int getExamTypeId() {
		return examTypeId;
	}

	public void setExamTypeId(int examTypeId) {
		this.examTypeId = examTypeId;
	}

	public HashMap<Integer, String> getExamTypeMap() {
		return examTypeMap;
	}

	public void setExamTypeMap(HashMap<Integer, String> examTypeMap) {
		this.examTypeMap = examTypeMap;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public List<ExamTimeTableTO> getMainList() {
		return mainList;
	}

	public void setMainList(List<ExamTimeTableTO> mainList) {
		this.mainList = mainList;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ExamTimeTableTO getExamTimeTableTO() {
		return examTimeTableTO;
	}

	public void setExamTimeTableTO(ExamTimeTableTO examTimeTableTO) {
		this.examTimeTableTO = examTimeTableTO;
	}

	public ArrayList<ExamSubjectTimeTableTO> getExamSubjectTimeTableTO() {
		return examSubjectTimeTableTO;
	}

	public void setExamSubjectTimeTableTO(
			ArrayList<ExamSubjectTimeTableTO> examSubjectTimeTableTO) {
		this.examSubjectTimeTableTO = examSubjectTimeTableTO;
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

	public String getStartTimeHour() {
		return startTimeHour;
	}

	public void setStartTimeHour(String startTimeHour) {
		this.startTimeHour = startTimeHour;
	}

	public String getStartTimeMin() {
		return startTimeMin;
	}

	public void setStartTimeMin(String startTimeMin) {
		this.startTimeMin = startTimeMin;
	}

	public String getEndTimeHour() {
		return endTimeHour;
	}

	public void setEndTimeHour(String endTimeHour) {
		this.endTimeHour = endTimeHour;
	}

	public String getEndTimeMin() {
		return endTimeMin;
	}

	public void setEndTimeMin(String endTimeMin) {
		this.endTimeMin = endTimeMin;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public void setExamNameId(Integer examNameId) {
		this.examNameId = examNameId;
	}

	public Integer getExamNameId() {
		return examNameId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamId() {
		return examId;
	}

	public void setMapExamName(HashMap<Integer, String> mapExamName) {
		this.mapExamName = mapExamName;
	}

	public HashMap<Integer, String> getMapExamName() {
		return mapExamName;
	}

	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}

	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	public Map<Integer, String> getSessionMap() {
		return sessionMap;
	}

	public void setSessionMap(Map<Integer, String> sessionMap) {
		this.sessionMap = sessionMap;
	}

}
