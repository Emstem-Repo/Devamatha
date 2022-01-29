package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.StudentsAttendanceReportTO;

/**
 * @author user
 *
 */
public class BlockHallTicketProcessForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private String examId;
	private String comments;
	private String reqPercentage;
	private Map<Integer, String> examNameList;
	private String examName;
	private List<StudentsAttendanceReportTO> studentList;
	private List<ProgramTypeTO> programTypeList;
	private Map<Integer, String> programMap ;
	private Map<Integer,String> courseMap;
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getReqPercentage() {
		return reqPercentage;
	}
	public void setReqPercentage(String reqPercentage) {
		this.reqPercentage = reqPercentage;
	}
	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}
	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public List<StudentsAttendanceReportTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentsAttendanceReportTO> studentList) {
		this.studentList = studentList;
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public Map<Integer, String> getProgramMap() {
		return programMap;
	}
	public void setProgramMap(Map<Integer, String> programMap) {
		this.programMap = programMap;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	/**
	 * 
	 */
	public void resetFields() {
		this.examId=null;
		this.comments="Attendance Shortage";
		this.reqPercentage="85";
		super.setProgramTypeId(null);
		super.setSchemeNo(null);
		super.setProgramId(null);
		super.setCourseId(null);
	}
}