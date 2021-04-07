package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.OccupationTO;

/**
 * A Java Bean associated with Occupation.
 * @version 1.0 12 Jan 2009
 * @see ActionForm
 */
public class OccupationForm extends BaseActionForm {

	private int occupationId;

	private String occupationName;
	
	private List<OccupationTO> occupationList;

	public int getOccupationId() {
		return occupationId;
	}

	public void setOccupationId(int occupationId) {
		this.occupationId = occupationId;
	}

	public String getOccupationName() {
		return occupationName;
	}

	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}
	

	public List<OccupationTO> getOccupationList() {
		return occupationList;
	}

	public void setOccupationList(List<OccupationTO> occupationList) {
		this.occupationList = occupationList;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.OCCUPATION_ENTRY);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.occupationName = null;
	}

}
