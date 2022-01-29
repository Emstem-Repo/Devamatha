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
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.InvVendorTO;
import com.kp.cms.to.inventory.VendorTO;

/**
 * Form for purchase order
 *
 */
public class PurchaseOrderForm extends BaseActionForm {

	private String purchaseOrderNo;
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
	private String vat;
	private String vatCost;
	private String serviceTax;
	private String serviceTaxCost;
	private String totalTaxCost;
	private String printReceipt;
	private String vendorAddress1;
	private String vendorAddress2;
	private String prefix;
	private boolean print;
	private String orderNo;
	private List<InvCampusTo> campusList;
	private Map<Integer, String> invLocationMap;
	private String campusId;
	private String locationId;
	private String deliverySchedule;
	private String campusName;
	private String locationName;
	private String countId;
	private String totalDiscount;
	private String addnDiscount;
	private String quotationNo;
	private String totalPriceExcludingVat;
	private String totalVatAmt;
	private String quotationDate;
	private int quotationId;
	private Boolean discountExists;
	private Boolean vatExists;
	private String itemToCountNo;
	private String companyId;
	private List<InvCompanyTO> invCompanyList;
	private String companyName;
	private String mode;
	private Boolean dataExistsForEdit;
	private int purchaseOrderId;
	private String origPurchaseCost;
	
	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}
	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
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
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getVatCost() {
		return vatCost;
	}
	public void setVatCost(String vatCost) {
		this.vatCost = vatCost;
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
	public String getTotalTaxCost() {
		return totalTaxCost;
	}
	public void setTotalTaxCost(String totalTaxCost) {
		this.totalTaxCost = totalTaxCost;
	}
	public String getPrintReceipt() {
		return printReceipt;
	}
	public void setPrintReceipt(String printReceipt) {
		this.printReceipt = printReceipt;
	}
	public String getVendorAddress1() {
		return vendorAddress1;
	}
	public void setVendorAddress1(String vendorAddress1) {
		this.vendorAddress1 = vendorAddress1;
	}
	public String getVendorAddress2() {
		return vendorAddress2;
	}
	public void setVendorAddress2(String vendorAddress2) {
		this.vendorAddress2 = vendorAddress2;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public boolean isPrint() {
		return print;
	}
	public void setPrint(boolean print) {
		this.print = print;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<InvCampusTo> getCampusList() {
		return campusList;
	}
	public void setCampusList(List<InvCampusTo> campusList) {
		this.campusList = campusList;
	}
	public Map<Integer, String> getInvLocationMap() {
		return invLocationMap;
	}
	public void setInvLocationMap(Map<Integer, String> invLocationMap) {
		this.invLocationMap = invLocationMap;
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
	public String getDeliverySchedule() {
		return deliverySchedule;
	}
	public void setDeliverySchedule(String deliverySchedule) {
		this.deliverySchedule = deliverySchedule;
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
	public String getCountId() {
		return countId;
	}
	public void setCountId(String countId) {
		this.countId = countId;
	}
	public String getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public String getAddnDiscount() {
		return addnDiscount;
	}
	public void setAddnDiscount(String addnDiscount) {
		this.addnDiscount = addnDiscount;
	}
	public String getQuotationNo() {
		return quotationNo;
	}
	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
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
	public String getQuotationDate() {
		return quotationDate;
	}
	public void setQuotationDate(String quotationDate) {
		this.quotationDate = quotationDate;
	}
	public int getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(int quotationId) {
		this.quotationId = quotationId;
	}
	public Boolean getDiscountExists() {
		return discountExists;
	}
	public void setDiscountExists(Boolean discountExists) {
		this.discountExists = discountExists;
	}
	public Boolean getVatExists() {
		return vatExists;
	}
	public void setVatExists(Boolean vatExists) {
		this.vatExists = vatExists;
	}
	public String getItemToCountNo() {
		return itemToCountNo;
	}
	public void setItemToCountNo(String itemToCountNo) {
		this.itemToCountNo = itemToCountNo;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public List<InvCompanyTO> getInvCompanyList() {
		return invCompanyList;
	}
	public void setInvCompanyList(List<InvCompanyTO> invCompanyList) {
		this.invCompanyList = invCompanyList;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public int getPurchaseOrderId() {
		return purchaseOrderId;
	}
	public void setPurchaseOrderId(int purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}
	public String getOrigPurchaseCost() {
		return origPurchaseCost;
	}
	public void setOrigPurchaseCost(String origPurchaseCost) {
		this.origPurchaseCost = origPurchaseCost;
	}

}
