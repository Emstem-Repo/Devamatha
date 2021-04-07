package com.kp.cms.forms.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.studentfeedback.BlockBoTo;

public class BlockBoForm extends BaseActionForm
{
	 private int id;
	 private String locationId;
	 private String locationName;
	 private String blockName;
     private List<BlockBoTo> blockBoList;
     private String allotmentOrderNo;
     

    
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        id = 0;
        locationId = null;
        locationName = null;
        blockName = null;
        allotmentOrderNo=null;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String formName = request.getParameter("formName");
        ActionErrors actionErrors = super.validate(mapping, request, formName);
        return actionErrors;
    }


	public String getAllotmentOrderNo() {
		return allotmentOrderNo;
	}

	public void setAllotmentOrderNo(String allotmentOrderNo) {
		this.allotmentOrderNo = allotmentOrderNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public List<BlockBoTo> getBlockBoList() {
		return blockBoList;
	}

	public void setBlockBoList(List<BlockBoTo> blockBoList) {
		this.blockBoList = blockBoList;
	}
   
    
}
