package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ReligionTO;

/**
 * A Java Bean associated with ReligionAction.
 * @see ActionForm
 * @author prashanth.mh
 */
public class ReligionForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String religionName;
	private String editedField;
	private List<ReligionTO> religionList;

	public String getReligionName() {
		return religionName;
	}

	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}

	public List<ReligionTO> getReligionList() {
		return religionList;
	}

	public void setReligionList(List<ReligionTO> religionList) {
		this.religionList = religionList;
	}

	public String getEditedField() {
		return editedField;
	}

	public void setEditedField(String editedField) {
		this.editedField = editedField;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.religionName = null;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
