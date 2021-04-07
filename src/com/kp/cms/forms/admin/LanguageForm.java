package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.LanguageTO;

/**
 * A Java Bean associated with Language action.
 * @version 1.0 12 Jan 2009
 * @see ActionForm
 */
public class LanguageForm extends BaseActionForm {
	

	private int languageId;

	private String languageName;

	private List<LanguageTO> languageList;
	
	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public List<LanguageTO> getLanguageList() {
		return languageList;
	}

	public void setLanguageList(List<LanguageTO> languageList) {
		this.languageList = languageList;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.LANGUAGE_ENTRY);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.languageName = null;
	}

}
