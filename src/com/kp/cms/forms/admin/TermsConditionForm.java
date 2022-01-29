package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.TermsConditionChecklistTO;

/*
 * Author : Kshirod
 * A FormBean for Terms&Condition
 */

public class TermsConditionForm extends BaseActionForm {

	private List<ProgramTypeTO> programtypelist;
//	private int programType;
	//private int program;
//	private int course;
	private String description;
//	private List<TermsConditionTO> termsconditionlist;
	private int id;
	private String years;
	private String programTypeId;
	private String programId;
	private String courseId;
	private int origCourseId;
	private int origYear;
	private int duplId;
	private String viewDesc;
	private List<TermsConditionChecklistTO> termsConditionList;
	private String noOfDesc;
	

	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	
/*
	public List<TermsConditionTO> getTermsconditionlist() {
		return termsconditionlist;
	}

	public void setTermsconditionlist(List<TermsConditionTO> termsconditionlist) {
		this.termsconditionlist = termsconditionlist;
	}
*/
	public List<ProgramTypeTO> getProgramtypelist() {
		return programtypelist;
	}

	public void setProgramtypelist(List<ProgramTypeTO> programtypelist) {
		this.programtypelist = programtypelist;
	}
/*
	public int getProgramType() {
		return programType;
	}

	public void setProgramType(int programType) {
		this.programType = programType;
	}

	public int getProgram() {
		return program;
	}

	public void setProgram(int program) {
		this.program = program;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}
*/
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
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

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	/*
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		courseId = null;
		programId = null;
		programTypeId = null;
		description = null;
		years = null;
	}
	*/

	public int getDuplId() {
		return duplId;
	}

	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}

	public String getViewDesc() {
		return viewDesc;
	}

	public void setViewDesc(String viewDesc) {
		this.viewDesc = viewDesc;
	}

	public List<TermsConditionChecklistTO> getTermsConditionList() {
		return termsConditionList;
	}

	public void setTermsConditionList(
			List<TermsConditionChecklistTO> termsConditionList) {
		this.termsConditionList = termsConditionList;
	}

	public String getNoOfDesc() {
		return noOfDesc;
	}

	public void setNoOfDesc(String noOfDesc) {
		this.noOfDesc = noOfDesc;
	}

	
}
