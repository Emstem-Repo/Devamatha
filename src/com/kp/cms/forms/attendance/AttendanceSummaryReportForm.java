package com.kp.cms.forms.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.utilities.CommonUtil;

public class AttendanceSummaryReportForm  extends BaseActionForm{
	private String[] classesName;
	private String semister;
	private String academicYear;
	private String[] attendanceType;
	private String startDate;
	private String endDate;
	private String courseName;	
	private String requiredPercentage;		
	private Boolean leaveType;
	private Boolean coCurricularLeave;
	private Boolean isSecondLanReq;
	private Boolean considerLeave;
	private Boolean reqPercenAggreg;
	private Boolean reqPercenIndivi;
	private String startRegisterNo;
	private String endRegisterNo;
	private String startRollNo;
	private String endRollNo;
	private String[] subjects;
	private Map<Integer, Subject> populateSubjectMap = new HashMap<Integer, Subject>();	
	private Map<Integer, String> classMap = new HashMap<Integer, String>();	
	private Map<Integer, Subject> subjectMap = new HashMap<Integer, Subject>();	
	private	Map<Integer, List<Subject>> subjectMapList;
	private List<Integer> attendanceSummarySizeList = new ArrayList<Integer>();
	private String organisationName;
	private boolean kjcReport;
    private boolean leave;
	private String academicYear1;
	private String attType;
	private Boolean isPractical;
	private Boolean isTheory;
	private Boolean isTheoryNPractical;
	private String isActivity;
	private String attnType;
	private String[] subArray;
	
    public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String[] getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(String[] attendanceType) {
		this.attendanceType = attendanceType;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getSemister() {
		return semister;
	}

	public void setSemister(String semister) {
		this.semister = semister;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Boolean getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(Boolean leaveType) {
		this.leaveType = leaveType;
	}

	public String getStartRegisterNo() {
		return startRegisterNo;
	}

	public String getEndRegisterNo() {
		return endRegisterNo;
	}

	public String getStartRollNo() {
		return startRollNo;
	}

	public String getEndRollNo() {
		return endRollNo;
	}

	public void setStartRegisterNo(String startRegisterNo) {
		this.startRegisterNo = startRegisterNo;
	}

	public void setEndRegisterNo(String endRegisterNo) {
		this.endRegisterNo = endRegisterNo;
	}

	public void setStartRollNo(String startRollNo) {
		this.startRollNo = startRollNo;
	}

	public void setEndRollNo(String endRollNo) {
		this.endRollNo = endRollNo;
	}


	public Map<Integer, String> getClassMap() {
		return classMap;
	}


	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	public String[] getClassesName() {
		return classesName;
	}

	public void setClassesName(String[] classesName) {
		this.classesName = classesName;
	}

	public Boolean getCoCurricularLeave() {
		return coCurricularLeave;
	}

	public Boolean getReqPercenAggreg() {
		return reqPercenAggreg;
	}

	public Boolean getReqPercenIndivi() {
		return reqPercenIndivi;
	}

	public void setCoCurricularLeave(Boolean coCurricularLeave) {
		this.coCurricularLeave = coCurricularLeave;
	}

	public void setReqPercenAggreg(Boolean reqPercenAggreg) {
		this.reqPercenAggreg = reqPercenAggreg;
	}

	public void setReqPercenIndivi(Boolean reqPercenIndivi) {
		this.reqPercenIndivi = reqPercenIndivi;
	}

	public String getRequiredPercentage() {
		return requiredPercentage;
	}

	public void setRequiredPercentage(String requiredPercentage) {
		this.requiredPercentage = requiredPercentage;
	}

	public Boolean getIsSecondLanReq() {
		return isSecondLanReq;
	}

	public Boolean getConsiderLeave() {
		return considerLeave;
	}

	public void setIsSecondLanReq(Boolean isSecondLanReq) {
		this.isSecondLanReq = isSecondLanReq;
	}

	public void setConsiderLeave(Boolean considerLeave) {
		this.considerLeave = considerLeave;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void resetFields() {
		
		this.academicYear = null;
		this.attendanceType = null;		
		this.startDate = null;
		this.endDate = CommonUtil.getTodayDate();
		this.courseName = null;
		this.leaveType = null;
		this.startRegisterNo = null;
		this.startRollNo = null;
		this.endRegisterNo = null;
		this.endRollNo = null;
		this.coCurricularLeave = true;
		this.considerLeave = null;
		this.classesName = null;
		this.requiredPercentage = null;		
		this.coCurricularLeave = true;
		this.isSecondLanReq = null;
		this.considerLeave = null;
		this.reqPercenAggreg = null;
		this.reqPercenIndivi = null;
		this.subjects = null;
		this.attnType = null;
		
	}

	public Map<Integer, Subject> getSubjectMap() {
		return subjectMap;
	}

	public void setSubjectMap(Map<Integer, Subject> subjectMap) {
		this.subjectMap = subjectMap;
	}

	public Map<Integer, List<Subject>> getSubjectMapList() {
		return subjectMapList;
	}

	public void setSubjectMapList(Map<Integer, List<Subject>> subjectMapList) {
		this.subjectMapList = subjectMapList;
	}

	public List<Integer> getAttendanceSummarySizeList() {
		return attendanceSummarySizeList;
	}

	public void setAttendanceSummarySizeList(List<Integer> attendanceSummarySizeList) {
		this.attendanceSummarySizeList = attendanceSummarySizeList;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String[] getSubjects() {
		return subjects;
	}

	public Map<Integer, Subject> getPopulateSubjectMap() {
		return populateSubjectMap;
	}

	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
	}

	public void setPopulateSubjectMap(Map<Integer, Subject> populateSubjectMap) {
		this.populateSubjectMap = populateSubjectMap;
	}

	public boolean isKjcReport() {
		return kjcReport;
	}

	public void setKjcReport(boolean kjcReport) {
		this.kjcReport = kjcReport;
	}

	public void setLeave(boolean leave) {
		this.leave = leave;
	}

	public boolean isLeave() {
		return leave;
	}

	public String getAcademicYear1() {
		return academicYear1;
	}

	public void setAcademicYear1(String academicYear1) {
		this.academicYear1 = academicYear1;
	}

	public String getAttType() {
		return attType;
	}

	public void setAttType(String attType) {
		this.attType = attType;
	}

	public void setIsPractical(Boolean isPractical) {
		this.isPractical = isPractical;
	}

	public Boolean getIsPractical() {
		return isPractical;
	}

	public Boolean getIsTheory() {
		return isTheory;
	}

	public void setIsTheory(Boolean isTheory) {
		this.isTheory = isTheory;
	}

	public Boolean getIsTheoryNPractical() {
		return isTheoryNPractical;
	}

	public void setIsTheoryNPractical(Boolean isTheoryNPractical) {
		this.isTheoryNPractical = isTheoryNPractical;
	}

	public String getIsActivity() {
		return isActivity;
	}

	public void setIsActivity(String isActivity) {
		this.isActivity = isActivity;
	}

	public String getAttnType() {
		return attnType;
	}

	public void setAttnType(String attnType) {
		this.attnType = attnType;
	}

	public String[] getSubArray() {
		return subArray;
	}

	public void setSubArray(String[] subArray) {
		this.subArray = subArray;
	}



}
