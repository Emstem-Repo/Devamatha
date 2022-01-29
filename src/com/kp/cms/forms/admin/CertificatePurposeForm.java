package com.kp.cms.forms.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.export.ResetableExporterFilter;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CertificatePurposeTO;

public class CertificatePurposeForm extends BaseActionForm {
	private int id;
	private String purposeName;
	private int dupId;
	private String orgPurposeName;
	private List<CertificatePurposeTO> purposeList;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.purposeName = null;

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPurposeName() {
		return purposeName;
	}
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}
	public void setPurposeList(List<CertificatePurposeTO> purposeList) {
		this.purposeList = purposeList;
	}
	public List<CertificatePurposeTO> getPurposeList() {
		return purposeList;
	}

	public void setDupId(int dupId) {
		this.dupId = dupId;
	}

	public int getDupId() {
		return dupId;
	}
	
	public void setOrgPurposeName(String orgPurposeName) {
		this.orgPurposeName = orgPurposeName;
	}

	public String getOrgPurposeName() {
		return orgPurposeName;
	}

}
