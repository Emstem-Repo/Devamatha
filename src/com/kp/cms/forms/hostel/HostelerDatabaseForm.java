package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelerDatabaseTO;
import com.kp.cms.to.hostel.RequisitionsTo;

public class HostelerDatabaseForm extends BaseActionForm{
	
	private String method;
	private String searchBy;
	private String textToSearch;
	private List<HostelerDatabaseTO> studentList;
	  private List<RequisitionsTo> reqList; 
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public String getTextToSearch() {
		return textToSearch;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public void setTextToSearch(String textToSearch) {
		this.textToSearch = textToSearch;
	}
	
	public List<HostelerDatabaseTO> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<HostelerDatabaseTO> studentList) {
		this.studentList = studentList;
	}

	public void clear(){
		this.setHostelId(null);
		this.searchBy = null;
		this.textToSearch = null;
		this.setStudentList(null);
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
