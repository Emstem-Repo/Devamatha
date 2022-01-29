package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.to.inventory.VendorTO;

public class AmcDetailsForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String itemNo;
	private List<InvAmcTO> amcList; 
	private String itemCategoryName;
	private String selectedItemNo;
	private String itemName;
	private List<VendorTO> vendorList;
	
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	
	public String getItemCategoryName() {
		return itemCategoryName;
	}
	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}
	public String getSelectedItemNo() {
		return selectedItemNo;
	}
	public void setSelectedItemNo(String selectedItemNo) {
		this.selectedItemNo = selectedItemNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public List<VendorTO> getVendorList() {
		return vendorList;
	}
	public void setVendorList(List<VendorTO> vendorList) {
		this.vendorList = vendorList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public List<InvAmcTO> getAmcList() {
		return amcList;
	}
	public void setAmcList(List<InvAmcTO> amcList) {
		this.amcList = amcList;
	}


}
