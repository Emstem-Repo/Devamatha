package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.SMSTemplateTo;

public class SMSTemplateForm extends BaseActionForm {


	private static final long serialVersionUID = 1L;
	
	public String desc;
	public String applnMailDesc;
	private int templateId;
	private List<ProgramTypeTO> programTypeList;
	public String templateName;
	public String templateDescription;
	public List<SMSTemplateTo> templateList;
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getApplnMailDesc() {
		return applnMailDesc;
	}

	public void setApplnMailDesc(String applnMailDesc) {
		this.applnMailDesc = applnMailDesc;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public List<SMSTemplateTo> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<SMSTemplateTo> templateList) {
		this.templateList = templateList;
	}

	@Override
	public void clear() {
		super.clear();
		
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		this.templateName = "";
		this.templateDescription = "";
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

}
