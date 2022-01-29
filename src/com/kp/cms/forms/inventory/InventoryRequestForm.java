package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InventoryRequestTO;

public class InventoryRequestForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SingleFieldMasterTO> inventoryLocationList;
	private List<InventoryRequestTO> inventoryRequestList;
	
	public List<SingleFieldMasterTO> getInventoryLocationList() {
		return inventoryLocationList;
	}

	public void setInventoryLocationList(
			List<SingleFieldMasterTO> inventoryLocationList) {
		this.inventoryLocationList = inventoryLocationList;
	}

	public List<InventoryRequestTO> getInventoryRequestList() {
		return inventoryRequestList;
	}

	public void setInventoryRequestList(
			List<InventoryRequestTO> inventoryRequestList) {
		this.inventoryRequestList = inventoryRequestList;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void clear() {
		super.clear();
	}
}
