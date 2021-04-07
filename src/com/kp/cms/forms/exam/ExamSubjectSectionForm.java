package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamSubjectSectionMasterTO;

/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSubjectSectionForm extends BaseActionForm {
	private int id = 0;
	private String name = "";
	private String isinitialise = "";
	
	private String orgName = "";
	private String orgIsinitialise = "";

	private List<ExamSubjectSectionMasterTO> listOfSubjectSection;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage() {
		this.name = "";
		this.isinitialise = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsinitialise() {
		return isinitialise;
	}

	public void setIsinitialise(String isinitialise) {
		this.isinitialise = isinitialise;
	}

	public List<ExamSubjectSectionMasterTO> getListOfSubjectSection() {
		return listOfSubjectSection;
	}

	public void setListOfSubjectSection(
			List<ExamSubjectSectionMasterTO> listOfSubjectSection) {
		this.listOfSubjectSection = listOfSubjectSection;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgIsinitialise() {
		return orgIsinitialise;
	}

	public void setOrgIsinitialise(String orgIsinitialise) {
		this.orgIsinitialise = orgIsinitialise;
	}

}
