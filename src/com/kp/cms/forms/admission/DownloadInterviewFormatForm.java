package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;

public class DownloadInterviewFormatForm extends BaseActionForm {
	
	private String[] interviewTypeids;
	private String[] interviewSubRoundIds;
	private String interviewDate;
	private String startingTimeHours;
	private String startingTimeMins;
	private String endingTimeHours;
	private String endingTimeMins;
	List<ProgramTypeTO> programTypeList;
	private boolean export;
	
	public String[] getInterviewTypeids() {
		return interviewTypeids;
	}
	public void setInterviewTypeids(String[] interviewTypeids) {
		this.interviewTypeids = interviewTypeids;
	}
	public String[] getInterviewSubRoundIds() {
		return interviewSubRoundIds;
	}
	public void setInterviewSubRoundIds(String[] interviewSubRoundIds) {
		this.interviewSubRoundIds = interviewSubRoundIds;
	}
	public String getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}
	public String getStartingTimeHours() {
		return startingTimeHours;
	}
	public void setStartingTimeHours(String startingTimeHours) {
		this.startingTimeHours = startingTimeHours;
	}
	public String getStartingTimeMins() {
		return startingTimeMins;
	}
	public void setStartingTimeMins(String startingTimeMins) {
		this.startingTimeMins = startingTimeMins;
	}
	public String getEndingTimeHours() {
		return endingTimeHours;
	}
	public void setEndingTimeHours(String endingTimeHours) {
		this.endingTimeHours = endingTimeHours;
	}
	public String getEndingTimeMins() {
		return endingTimeMins;
	}
	public void setEndingTimeMins(String endingTimeMins) {
		this.endingTimeMins = endingTimeMins;
	}
	
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public boolean isExport() {
		return export;
	}
	public void setExport(boolean export) {
		this.export = export;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields(){
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		this.interviewTypeids=null;
		this.interviewSubRoundIds=null;
		this.interviewDate=null;
		this.startingTimeHours=null;
		this.startingTimeMins=null;
		this.endingTimeHours=null;
		this.endingTimeMins=null;
		super.setYear(null);
		this.export=false;
	}
}
