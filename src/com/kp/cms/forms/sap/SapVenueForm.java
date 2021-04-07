package com.kp.cms.forms.sap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.sap.SapVenueTO;

public class SapVenueForm extends BaseActionForm {
	
	private int id;
	private String venueName;
	private String capacity;
	private String workLocId;
	private List<SapVenueTO> venueList;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void clearAll()
	{
		this.venueName = null;
		this.capacity = null;
		this.workLocId = null;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getWorkLocId() {
		return workLocId;
	}

	public void setWorkLocId(String workLocId) {
		this.workLocId = workLocId;
	}

	public List<SapVenueTO> getVenueList() {
		return venueList;
	}

	public void setVenueList(List<SapVenueTO> venueList) {
		this.venueList = venueList;
	}
	
	

}
