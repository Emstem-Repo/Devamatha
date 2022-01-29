package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.to.admission.AdmSubjectForRankTo;
import com.kp.cms.to.admission.PgAdmSubjectForRankTo;

public class PgAdmSubjectForRankForm extends BaseActionForm {
	
	private int id;
	private String subjectname;
	private String programId;
	private String programTypeId;
    private boolean isactive;
    private String origsubjectname;
    private String origstream;
	private String origgroupname;
	private String selectedProgram;
	private String selectedCourse;
	private List<ProgramTypeTO> programTypeList;
	private Map<Integer,String> programMap;
	private List<UGCoursesTO> ugcourseList;
	private String[] selectedCourseId;
	private List<PgAdmSubjectForRankTo> subjecttolist;

    public String getOrigstream() {
		return origstream;
	}
	public void setOrigstream(String origstream) {
		this.origstream = origstream;
	}
	public String getOriggroupname() {
		return origgroupname;
	}
	public void setOriggroupname(String origgroupname) {
		this.origgroupname = origgroupname;
	}
	
    
	public String getOrigsubjectname() {
		return origsubjectname;
	}
	public void setOrigsubjectname(String origsubjectname) {
		this.origsubjectname = origsubjectname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubjectname() {
		return subjectname;
	}
	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}
	
	public boolean isIsactive() {
		return isactive;
	}
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		this.subjectname = null;
		this.id=0;
		this.setSelectedCourseId(selectedCourseId);
	    super.reset(mapping, request);
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		return super.validate(mapping, request, formName);
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
	public String getSelectedProgram() {
		return selectedProgram;
	}
	public void setSelectedProgram(String selectedProgram) {
		this.selectedProgram = selectedProgram;
	}
	public String getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(String selectedCourse) {
		this.selectedCourse = selectedCourse;
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
	public List<UGCoursesTO> getUgcourseList() {
		return ugcourseList;
	}
	public void setUgcourseList(List<UGCoursesTO> ugcourseList) {
		this.ugcourseList = ugcourseList;
	}
	public String[] getSelectedCourseId() {
		return selectedCourseId;
	}
	public void setSelectedCourseId(String[] selectedCourseId) {
		this.selectedCourseId = selectedCourseId;
	}
	public List<PgAdmSubjectForRankTo> getSubjecttolist() {
		return subjecttolist;
	}
	public void setSubjecttolist(List<PgAdmSubjectForRankTo> subjecttolist) {
		this.subjecttolist = subjecttolist;
	}
	
	
	
	

}
