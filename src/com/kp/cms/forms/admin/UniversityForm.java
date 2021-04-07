package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admin.UniversityTO;

public class UniversityForm extends BaseActionForm 
{
	private Integer id;
	private String university;
	private String docTypeId;
	private String dummyUniversity;
	private String dummyDocTypeId;
	private String method;
	private List<SingleFieldMasterTO>doctypeList;
	private List<UniversityTO>universityList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(String docTypeId) {
		this.docTypeId = docTypeId;
	}
	public List<SingleFieldMasterTO> getDoctypeList() {
		return doctypeList;
	}
	public void setDoctypeList(List<SingleFieldMasterTO> doctypeList) {
		this.doctypeList = doctypeList;
	}
	public List<UniversityTO> getUniversityList() {
		return universityList;
	}
	public void setUniversityList(List<UniversityTO> universityList) {
		this.universityList = universityList;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void clear()
	{
		this.id=null;
		this.university=null;
		this.docTypeId=null;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDummyUniversity() {
		return dummyUniversity;
	}
	public void setDummyUniversity(String dummyUniversity) {
		this.dummyUniversity = dummyUniversity;
	}
	public String getDummyDocTypeId() {
		return dummyDocTypeId;
	}
	public void setDummyDocTypeId(String dummyDocTypeId) {
		this.dummyDocTypeId = dummyDocTypeId;
	}
	
}
