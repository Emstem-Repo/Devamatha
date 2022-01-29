package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamSettingsTO;

/**
 * Dec 25, 2009 Created By 9Elements
 */
public class ExamSettingsForm extends BaseActionForm{
	private int id=0;
	private String absentCodeMarkEntry="";
	private String notProcedCodeMarkEntry="";
	private String securedMarkEntryBy="";
	private String maxAllwdDiffPrcntgMultiEvaluator="";
	private String gradeForFail="";
	private String gradePointForFail="";
	
	
	
	private String orgAbsentCodeMarkEntry="";
	private String orgNotProcedCodeMarkEntry="";
	private String orgSecuredMarkEntryBy="";
	private String orgMaxAllwdDiffPrcntgMultiEvaluator="";
	private String orgGradeForFail="";
	private String orgGradePointForFail="";
	private String orgWithHeldCodeMarkEntry="";
	
	private List<ExamSettingsTO> listExamSetting;
	private String withHeldCodeMarkEntry="";
	

	public String getOrgWithHeldCodeMarkEntry() {
		return orgWithHeldCodeMarkEntry;
	}

	public void setOrgWithHeldCodeMarkEntry(String orgWithHeldCodeMarkEntry) {
		this.orgWithHeldCodeMarkEntry = orgWithHeldCodeMarkEntry;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void clearPage() {
		this.absentCodeMarkEntry = "";
		this.notProcedCodeMarkEntry = "";
		this.securedMarkEntryBy = "";
		this.maxAllwdDiffPrcntgMultiEvaluator = "";
		this.gradeForFail = "";
		this.gradePointForFail = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAbsentCodeMarkEntry() {
		return absentCodeMarkEntry;
	}

	public void setAbsentCodeMarkEntry(String absentCodeMarkEntry) {
		this.absentCodeMarkEntry = absentCodeMarkEntry;
	}

	public String getNotProcedCodeMarkEntry() {
		return notProcedCodeMarkEntry;
	}

	public void setNotProcedCodeMarkEntry(String notProcedCodeMarkEntry) {
		this.notProcedCodeMarkEntry = notProcedCodeMarkEntry;
	}

	public String getSecuredMarkEntryBy() {
		return securedMarkEntryBy;
	}

	public void setSecuredMarkEntryBy(String securedMarkEntryBy) {
		this.securedMarkEntryBy = securedMarkEntryBy;
	}

	public String getMaxAllwdDiffPrcntgMultiEvaluator() {
		return maxAllwdDiffPrcntgMultiEvaluator;
	}

	public void setMaxAllwdDiffPrcntgMultiEvaluator(
			String maxAllwdDiffPrcntgMultiEvaluator) {
		this.maxAllwdDiffPrcntgMultiEvaluator = maxAllwdDiffPrcntgMultiEvaluator;
	}

	public String getGradeForFail() {
		return gradeForFail;
	}

	public void setGradeForFail(String gradeForFail) {
		this.gradeForFail = gradeForFail;
	}

	public String getGradePointForFail() {
		return gradePointForFail;
	}

	public void setGradePointForFail(String gradePointForFail) {
		this.gradePointForFail = gradePointForFail;
	}

	public String getOrgAbsentCodeMarkEntry() {
		return orgAbsentCodeMarkEntry;
	}

	public void setOrgAbsentCodeMarkEntry(String orgAbsentCodeMarkEntry) {
		this.orgAbsentCodeMarkEntry = orgAbsentCodeMarkEntry;
	}

	public String getOrgNotProcedCodeMarkEntry() {
		return orgNotProcedCodeMarkEntry;
	}

	public void setOrgNotProcedCodeMarkEntry(String orgNotProcedCodeMarkEntry) {
		this.orgNotProcedCodeMarkEntry = orgNotProcedCodeMarkEntry;
	}

	public String getOrgSecuredMarkEntryBy() {
		return orgSecuredMarkEntryBy;
	}

	public void setOrgSecuredMarkEntryBy(String orgSecuredMarkEntryBy) {
		this.orgSecuredMarkEntryBy = orgSecuredMarkEntryBy;
	}

	public String getOrgMaxAllwdDiffPrcntgMultiEvaluator() {
		return orgMaxAllwdDiffPrcntgMultiEvaluator;
	}

	public void setOrgMaxAllwdDiffPrcntgMultiEvaluator(
			String orgMaxAllwdDiffPrcntgMultiEvaluator) {
		this.orgMaxAllwdDiffPrcntgMultiEvaluator = orgMaxAllwdDiffPrcntgMultiEvaluator;
	}

	public String getOrgGradeForFail() {
		return orgGradeForFail;
	}

	public void setOrgGradeForFail(String orgGradeForFail) {
		this.orgGradeForFail = orgGradeForFail;
	}

	public String getOrgGradePointForFail() {
		return orgGradePointForFail;
	}

	public void setOrgGradePointForFail(String orgGradePointForFail) {
		this.orgGradePointForFail = orgGradePointForFail;
	}

	public List<ExamSettingsTO> getListExamSetting() {
		return listExamSetting;
	}

	public String getWithHeldCodeMarkEntry() {
		return withHeldCodeMarkEntry;
	}

	public void setWithHeldCodeMarkEntry(String withHeldCodeMarkEntry) {
		this.withHeldCodeMarkEntry = withHeldCodeMarkEntry;
	}

	public void setListExamSetting(List<ExamSettingsTO> listExamSetting) {
		this.listExamSetting = listExamSetting;
	}
	
	
	
}
