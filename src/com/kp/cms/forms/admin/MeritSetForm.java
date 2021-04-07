package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.MeritSetTO;

/**
 * A Java Bean associated with MeritSetAction.
 * @version 1.0 12 Jan 2009
 * @see ActionForm
 */
public class MeritSetForm extends BaseActionForm {

	private int meritSetId;

	private String meritSetName;

	private List<MeritSetTO> meritSetList;

	public int getMeritSetId() {
		return meritSetId;
	}

	public void setMeritSetId(int meritSetId) {
		this.meritSetId = meritSetId;
	}

	public String getMeritSetName() {
		return meritSetName;
	}

	public void setMeritSetName(String meritSetName) {
		this.meritSetName = meritSetName;
	}

	public List<MeritSetTO> getMeritSetList() {
		return meritSetList;
	}

	public void setMeritSetList(List<MeritSetTO> meritSetList) {
		this.meritSetList = meritSetList;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.MERIT_SET_ENTRY);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.meritSetName = null;
	}


}
