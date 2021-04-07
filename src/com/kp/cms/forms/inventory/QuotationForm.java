package com.kp.cms.forms.inventory;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.InvCampusTo;
import com.kp.cms.to.inventory.InvCompanyTO;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.to.inventory.VendorTO;

/**
 * Form for Quotation
 *
 */
public class QuotationForm extends BaseActionForm {

	private String quotationNo;
	private String purchaseDate;
	private String vendorId;
	private String vendorName;
	private String remarks;
	private String termConditions;
	private String siteDelivery;
	private String selectedItemId;
	private String selectedItemQty;
	private String additionalCost;
	private String totalCost;
	private String searchItem;
	private List<VendorTO> vendorList;
	private List<InvItemTO> itemList;
	private List<InvPurchaseOrderItemTO> purchaseItemList;
	private int deleteItemId;
	private String prefix;
	List<InvCampusTo> campusList;
	private Map<Integer, String> invLocationMap;
	private String campusId;
	private String locationId;
	private String campusName;
	private String locationName;
	private String totalDiscount;
	private String countId;
	private String addnDiscount;
	private String itemToCountNo;
	private String mode;
	private Boolean dataExistsForEdit;
	private List<InvCompanyTO> invCompanyList;
	private String companyId;
	private String companyName;
	private String totalPriceExcludingVat;
	private String totalVatAmt;
	private String serviceTax;
	private String serviceTaxCost;
	private int quotationId;
	
	public String getQuotationNo() {
		return quotationNo;
	}
	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTermConditions() {
		return termConditions;
	}
	public void setTermConditions(String termConditions) {
		this.termConditions = termConditions;
	}
	public String getSiteDelivery() {
		return siteDelivery;
	}
	public void setSiteDelivery(String siteDelivery) {
		this.siteDelivery = siteDelivery;
	}
	public String getSelectedItemId() {
		return selectedItemId;
	}
	public void setSelectedItemId(String selectedItemId) {
		this.selectedItemId = selectedItemId;
	}
	public String getSelectedItemQty() {
		return selectedItemQty;
	}
	public void setSelectedItemQty(String selectedItemQty) {
		this.selectedItemQty = selectedItemQty;
	}
	public String getAdditionalCost() {
		return additionalCost;
	}
	public void setAdditionalCost(String additionalCost) {
		this.additionalCost = additionalCost;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public List<InvPurchaseOrderItemTO> getPurchaseItemList() {
		return purchaseItemList;
	}
	public void setPurchaseItemList(List<InvPurchaseOrderItemTO> purchaseItemList) {
		this.purchaseItemList = purchaseItemList;
	}
	
	public List<VendorTO> getVendorList() {
		return vendorList;
	}
	public void setVendorList(List<VendorTO> vendorList) {
		this.vendorList = vendorList;
	}
	public List<InvItemTO> getItemList() {
		return itemList;
	}
	public void setItemList(List<InvItemTO> itemList) {
		this.itemList = itemList;
	}
	public int getDeleteItemId() {
		return deleteItemId;
	}
	public void setDeleteItemId(int deleteItemId) {
		this.deleteItemId = deleteItemId;
	}
	public String getSearchItem() {
		return searchItem;
	}
	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public List<InvCampusTo> getCampusList() {
		return campusList;
	}
	public void setCampusList(List<InvCampusTo> campusList) {
		this.campusList = campusList;
	}
	public String getCampusId() {
		return campusId;
	}
	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public Map<Integer, String> getInvLocationMap() {
		return invLocationMap;
	}
	public void setInvLocationMap(Map<Integer, String> invLocationMap) {
		this.invLocationMap = invLocationMap;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public String getCountId() {
		return countId;
	}
	public void setCountId(String countId) {
		this.countId = countId;
	}
	public String getAddnDiscount() {
		return addnDiscount;
	}
	public void setAddnDiscount(String addnDiscount) {
		this.addnDiscount = addnDiscount;
	}
	public String getItemToCountNo() {
		return itemToCountNo;
	}
	public void setItemToCountNo(String itemToCountNo) {
		this.itemToCountNo = itemToCountNo;
	}
	/* (non-Javadoc)
	 * validation call
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Boolean getDataExistsForEdit() {
		return dataExistsForEdit;
	}
	public void setDataExistsForEdit(Boolean dataExistsForEdit) {
		this.dataExistsForEdit = dataExistsForEdit;
	}
	public List<InvCompanyTO> getInvCompanyList() {
		return invCompanyList;
	}
	public void setInvCompanyList(List<InvCompanyTO> invCompanyList) {
		this.invCompanyList = invCompanyList;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTotalPriceExcludingVat() {
		return totalPriceExcludingVat;
	}
	public void setTotalPriceExcludingVat(String totalPriceExcludingVat) {
		this.totalPriceExcludingVat = totalPriceExcludingVat;
	}
	public String getTotalVatAmt() {
		return totalVatAmt;
	}
	public void setTotalVatAmt(String totalVatAmt) {
		this.totalVatAmt = totalVatAmt;
	}
	public String getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}
	public String getServiceTaxCost() {
		return serviceTaxCost;
	}
	public void setServiceTaxCost(String serviceTaxCost) {
		this.serviceTaxCost = serviceTaxCost;
	}
	public int getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(int quotationId) {
		this.quotationId = quotationId;
	}

	
}
