package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelBlocksTO;

public class HostelBlocksForm extends BaseActionForm {
	
	private int id;
	private String name;
	private String hostelId;
	private List<HostelBlocksTO> blocksList;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void clearAll()
	{
		this.name = null;
		this.setHostelId(null);
	}

	public String getHostelId() {
		return hostelId;
	}

	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
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

	public List<HostelBlocksTO> getBlocksList() {
		return blocksList;
	}

	public void setBlocksList(List<HostelBlocksTO> blocksList) {
		this.blocksList = blocksList;
	}

}
