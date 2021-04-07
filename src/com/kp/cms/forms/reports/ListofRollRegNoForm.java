package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.ClasswithStudentTO;
import com.kp.cms.to.reports.ListofRollRegNoReportTO;

public class ListofRollRegNoForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ListofRollRegNoReportTO> totalstudentList;
	private List<ClasswithStudentTO> studentListWithClass;
	private String organizationName;
	
	public List<ListofRollRegNoReportTO> getTotalstudentList() {
		return totalstudentList;
	}
	public List<ClasswithStudentTO> getStudentListWithClass() {
		return studentListWithClass;
	}
	public void setTotalstudentList(List<ListofRollRegNoReportTO> totalstudentList) {
		this.totalstudentList = totalstudentList;
	}
	public void setStudentListWithClass(
			List<ClasswithStudentTO> studentListWithClass) {
		this.studentListWithClass = studentListWithClass;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		super.setProgramTypeId(null);
		super.setClassId(null);
		
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
