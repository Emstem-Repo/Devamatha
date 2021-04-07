package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.MarksCardSiNoTO;

public class MarksCardSiNoForm extends BaseActionForm {
	
	private String startNo;
	private List<MarksCardSiNoTO> toList; 
	private boolean isAdded;
	private String academicYear;
	private String semister;
	private List<ProgramTypeTO> programTypeList;
	private String prefix;
	
	public String getStartNo() {
		return startNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}
	
	public void reset(){
		this.startNo=null;
		this.toList=null;
		this.prefix=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	

	public List<MarksCardSiNoTO> getToList() {
		return toList;
	}

	public void setToList(List<MarksCardSiNoTO> toList) {
		this.toList = toList;
	}

	public boolean getIsAdded() {
		return isAdded;
	}

	public void setIsAdded(boolean isAdded) {
		this.isAdded = isAdded;
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

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	
}
