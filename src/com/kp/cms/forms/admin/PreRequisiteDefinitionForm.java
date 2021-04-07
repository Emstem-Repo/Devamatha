package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CoursePrerequisiteTO;

public class PreRequisiteDefinitionForm extends BaseActionForm{

	private int id;
	private String programTypeId;
	private String programId;
	private String courseId;
	private String prereqid1;
	private String prereqid2;
	private String percentage1;
	private String percentage2;
	private String totalMark1;
	private String totalMark2;
	
	private List<CoursePrerequisiteTO> prerequsitedeflist;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getPrereqid1() {
		return prereqid1;
	}
	public void setPrereqid1(String prereqid1) {
		this.prereqid1 = prereqid1;
	}
	public String getPrereqid2() {
		return prereqid2;
	}
	public void setPrereqid2(String prereqid2) {
		this.prereqid2 = prereqid2;
	}
	public String getPercentage1() {
		return percentage1;
	}
	public void setPercentage1(String percentage1) {
		this.percentage1 = percentage1;
	}
	public String getPercentage2() {
		return percentage2;
	}
	public void setPercentage2(String percentage2) {
		this.percentage2 = percentage2;
	}
	public List<CoursePrerequisiteTO> getPrerequsitedeflist() {
		return prerequsitedeflist;
	}
	public void setPrerequsitedeflist(List<CoursePrerequisiteTO> prerequsitedeflist) {
		this.prerequsitedeflist = prerequsitedeflist;
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.programTypeId = null;
		this.programId = null;
		this.courseId = null;
		this.prereqid1 = null;
		this.prereqid2 = null;
		this.percentage1 = null;
		this.percentage2 = null;
		this.totalMark1 = null;
		this.totalMark2 = null;
		
		
	}
	public String getTotalMark1() {
		return totalMark1;
	}
	public String getTotalMark2() {
		return totalMark2;
	}
	public void setTotalMark1(String totalMark1) {
		this.totalMark1 = totalMark1;
	}
	public void setTotalMark2(String totalMark2) {
		this.totalMark2 = totalMark2;
	}

}
