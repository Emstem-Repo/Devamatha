package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseApplicationNoTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;

public class ApplicationNumberForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String onlineAppNoFrom;
	private String onlineAppNoTill;
	private String offlineAppNoFrom;
	private String offlineAppNoTill;
	private int id;
	private String programTypeId;
	private String programId;
	private String courseId;
	private String year;
	private int origCourseId;
	private int origYear;
	private String[] selectedCourse;
	private String[] selectedCourseIdNos;
	private Map<Integer, String> courseMap;
	private List<CourseApplicationNoTO> courseApplicationNumberList; 
	private String origOnlineAppNoFrom;
	private String origOnlineAppNoTill;
	private String origOfflineAppNoFrom;
	private String origOfflineAppNoTill;
	private String currentOnlineApplnNo;
	private String currentOfflineApplnNo;
	private List<ProgramTO> programList;
	private String[] selectedProgram;
	private String[] selectedProgramIdNos;
	private List<ProgramTypeTO> programTypeList;
	private Map<Integer,String> programMap;
		
	public String getOnlineAppNoFrom() {
		return onlineAppNoFrom;
	}
	public void setOnlineAppNoFrom(String onlineAppNoFrom) {
		this.onlineAppNoFrom = onlineAppNoFrom;
	}
	public String getOnlineAppNoTill() {
		return onlineAppNoTill;
	}
	public void setOnlineAppNoTill(String onlineAppNoTill) {
		this.onlineAppNoTill = onlineAppNoTill;
	}
	public String getOfflineAppNoFrom() {
		return offlineAppNoFrom;
	}
	public void setOfflineAppNoFrom(String offlineAppNoFrom) {
		this.offlineAppNoFrom = offlineAppNoFrom;
	}
	public String getOfflineAppNoTill() {
		return offlineAppNoTill;
	}
	public void setOfflineAppNoTill(String offlineAppNoTill) {
		this.offlineAppNoTill = offlineAppNoTill;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		onlineAppNoFrom = null;
		onlineAppNoTill = null;
		offlineAppNoFrom = null;
		offlineAppNoTill = null;
		programTypeId = null;
		programId = null;
		selectedCourse = null;
		selectedProgram = null;
//		courseId = null;
//		year = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public int getOrigCourseId() {
		return origCourseId;
	}
	public void setOrigCourseId(int origCourseId) {
		this.origCourseId = origCourseId;
	}
	public int getOrigYear() {
		return origYear;
	}
	public void setOrigYear(int origYear) {
		this.origYear = origYear;
	}
	public String[] getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(String[] selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}

	public String[] getSelectedCourseIdNos() {
		return selectedCourseIdNos;
	}
	public List<CourseApplicationNoTO> getCourseApplicationNumberList() {
		return courseApplicationNumberList;
	}
	public void setCourseApplicationNumberList(
			List<CourseApplicationNoTO> courseApplicationNumberList) {
		this.courseApplicationNumberList = courseApplicationNumberList;
	}
	public String getOrigOnlineAppNoFrom() {
		return origOnlineAppNoFrom;
	}
	public void setOrigOnlineAppNoFrom(String origOnlineAppNoFrom) {
		this.origOnlineAppNoFrom = origOnlineAppNoFrom;
	}
	public String getOrigOnlineAppNoTill() {
		return origOnlineAppNoTill;
	}
	public void setOrigOnlineAppNoTill(String origOnlineAppNoTill) {
		this.origOnlineAppNoTill = origOnlineAppNoTill;
	}
	public String getOrigOfflineAppNoFrom() {
		return origOfflineAppNoFrom;
	}
	public void setOrigOfflineAppNoFrom(String origOfflineAppNoFrom) {
		this.origOfflineAppNoFrom = origOfflineAppNoFrom;
	}
	public String getOrigOfflineAppNoTill() {
		return origOfflineAppNoTill;
	}
	public void setOrigOfflineAppNoTill(String origOfflineAppNoTill) {
		this.origOfflineAppNoTill = origOfflineAppNoTill;
	}
	public void setSelectedCourseIdNos(String[] selectedCourseIdNos) {
		this.selectedCourseIdNos = selectedCourseIdNos;
	}
	public String getCurrentOnlineApplnNo() {
		return currentOnlineApplnNo;
	}
	public void setCurrentOnlineApplnNo(String currentOnlineApplnNo) {
		this.currentOnlineApplnNo = currentOnlineApplnNo;
	}
	public String getCurrentOfflineApplnNo() {
		return currentOfflineApplnNo;
	}
	public void setCurrentOfflineApplnNo(String currentOfflineApplnNo) {
		this.currentOfflineApplnNo = currentOfflineApplnNo;
	}
	public List<ProgramTO> getProgramList() {
		return programList;
	}
	public void setProgramList(List<ProgramTO> programList) {
		this.programList = programList;
	}
	public String[] getSelectedProgram() {
		return selectedProgram;
	}
	public void setSelectedProgram(String[] selectedProgram) {
		this.selectedProgram = selectedProgram;
	}
	public String[] getSelectedProgramIdNos() {
		return selectedProgramIdNos;
	}
	public void setSelectedProgramIdNos(String[] selectedProgramIdNos) {
		this.selectedProgramIdNos = selectedProgramIdNos;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
}
