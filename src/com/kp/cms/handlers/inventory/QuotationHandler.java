package com.kp.cms.handlers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvCompany;
import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.bo.admin.InvQuotationItem;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.QuotationForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.inventory.PurchaseOrderHelper;
import com.kp.cms.helpers.inventory.QuotationHelper;
import com.kp.cms.to.inventory.InvCampusTo;
import com.kp.cms.to.inventory.InvCompanyTO;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.to.inventory.VendorTO;
import com.kp.cms.transactions.inventory.IPurchaseOrderTransaction;
import com.kp.cms.transactions.inventory.IQuotationTransaction;
import com.kp.cms.transactionsimpl.inventory.PurchaseOrderTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.QuotationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Handler class for QuotationAction
 *
 */
public class QuotationHandler {
	private static final Log log = LogFactory.getLog(QuotationHandler.class);
	
	public static volatile QuotationHandler self=null;
	public static QuotationHandler getInstance(){
		if(self==null){
			self= new QuotationHandler();
		}
		return self;
	}
	private QuotationHandler(){
		
	}
	/**
	 * @param orderForm
	 */
	public void setQuoteNo(QuotationForm orderForm) throws Exception {
		IQuotationTransaction txn= QuotationTransactionImpl.getInstance();
		String purchaseOrderNo= txn.getLatestQuoteNo(CMSConstants.QUOTATION_COUNTER,orderForm);
		orderForm.setQuotationNo(purchaseOrderNo);
	}
	/**
	 * @return
	 */
	public List<InvItemTO> getItemList()throws Exception {
		IQuotationTransaction txn=  QuotationTransactionImpl.getInstance();
		List<InvItem> ItemBos=txn.getItemsList();
		List<InvItemTO> itemtos=QuotationHelper.convertItemBosToTOs(ItemBos);
		return itemtos;
	}
	/**
	 * @param pchItemList
	 * @param orderForm
	 */
	public void updatePurchaseItemList(List<InvPurchaseOrderItemTO> pchItemList, QuotationForm orderForm) throws Exception{
		InvPurchaseOrderItemTO to= new InvPurchaseOrderItemTO();
		to.setItemList(QuotationHandler.getInstance().getItemList());
		InvItemTO invItemTo=new InvItemTO();
		to.setInvItem(invItemTo);
		pchItemList.add(to);
	}
	/**
	 * final submission
	 * @param orderForm
	 * @return
	 */
	public boolean placeFinalQuotation(QuotationForm orderForm) throws Exception{
		InvQuotation finalOrder=QuotationHelper.getInstance().prepareFinalQuotation(orderForm);
		boolean updated=false;
		if(finalOrder!=null){
			//save it
			IQuotationTransaction txn= QuotationTransactionImpl.getInstance();
			updated=txn.placeFinalQuotation(finalOrder,orderForm);
		}
		return updated;
	}
	/**
	 * @param orderForm
	 * @return
	 * @throws Exception
	 */
	public List<InvPurchaseOrderItemTO> setListToForm(QuotationForm orderForm) throws Exception{
		 List<InvPurchaseOrderItemTO> purchaseOrderToList=new ArrayList<InvPurchaseOrderItemTO>();
		 List<InvItemTO> itemList=QuotationHandler.getInstance().getItemList();
			InvPurchaseOrderItemTO to= new InvPurchaseOrderItemTO();
			to.setItemList(itemList);
			InvItemTO invItemTo=new InvItemTO();
			to.setInvItem(invItemTo);
			purchaseOrderToList.add(to);
			orderForm.setCountId("1");
			to.setCountId(1);
		return purchaseOrderToList;
	}
	/**
	 * @param orderForm
	 * @throws Exception
	 */
	public void getQuotationDetails(QuotationForm orderForm,boolean firstPage) throws Exception {
		if(orderForm.getMode().equalsIgnoreCase("Edit")){
			List<InvPurchaseOrderItemTO> purchaseOrderToList=new ArrayList<InvPurchaseOrderItemTO>();
			IQuotationTransaction txn= QuotationTransactionImpl.getInstance();
			InvQuotation quotationBo=txn.getQuotationForEdit(orderForm.getQuotationNo());
			Double totalDiscount=0.0;
			Double totalCost=0.0;
			Double totalPriceExcludingVat=0.0;
			Double totalVatAmt=0.0;
			int count=1;
			if(quotationBo!=null ){
				if(firstPage){
				orderForm.setQuotationId(quotationBo.getId());
				//set vendor name to form
				orderForm.setVendorId(String.valueOf(quotationBo.getInvVendor().getId()));
				//set campus name to form
				orderForm.setCampusId(String.valueOf(quotationBo.getInvCampus().getId()));
				// set Company name to form
				orderForm.setCompanyId(quotationBo.getInvCompany()!=null?String.valueOf(quotationBo.getInvCompany().getId()):"");
				orderForm.setPurchaseDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(quotationBo.getQuoteDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
				orderForm.setRemarks(quotationBo.getRemarks()!=null?quotationBo.getRemarks():"");
				
				if(quotationBo.getTermsandconditions()!=null && !quotationBo.getTermsandconditions().isEmpty()){
					String savedTcDesc=quotationBo.getTermsandconditions();
						
						String[] args=savedTcDesc.split("<br>");
						StringBuilder strTc=new StringBuilder();
						for (String string : args) {
							strTc.append(string);
							strTc.append("\r\n");
						}
						if(strTc!=null && !strTc.toString().isEmpty())
							orderForm.setTermConditions(strTc.toString());
					
				}
				orderForm.setSiteDelivery(quotationBo.getDeliverySite()!=null?quotationBo.getDeliverySite():"");
				orderForm.setDataExistsForEdit(true);
				}
				else if(!firstPage){
					Set<InvQuotationItem> invQuotationItems=quotationBo.getInvQuotationItems();
					for (InvQuotationItem invQuotationItem : invQuotationItems) {
						List<InvItemTO> itemList=QuotationHandler.getInstance().getItemList();
						InvPurchaseOrderItemTO to= new InvPurchaseOrderItemTO();
						to.setItemList(itemList);
						to.setQuotationItemId(invQuotationItem.getId());
						to.setSelectedItemId(String.valueOf(invQuotationItem.getInvItem().getId()));
						to.setQuantity(invQuotationItem.getQuantity().toPlainString());
						InvItemTO invItemTo=new InvItemTO();
						invItemTo.setInvUomName(invQuotationItem.getInvItem().getInvUomByInvPurchaseUomId().getName());
						invItemTo.setPurchaseCost(invQuotationItem.getUnitCost().toPlainString());
						
						Double unitDiscount=0.0; 
						if(invQuotationItem.getUnitDiscount()!=null){
							to.setDiscount(invQuotationItem.getUnitDiscount().toPlainString());
							unitDiscount=invQuotationItem.getUnitDiscount().doubleValue();
						}
						 totalCost=CommonUtil.Round(((invQuotationItem.getUnitCost().doubleValue()*invQuotationItem.getQuantity().doubleValue())-unitDiscount)
								, 2);
						 totalPriceExcludingVat=totalPriceExcludingVat+totalCost;
						to.setTotalCost(totalCost.toString());
						to.setVat(invQuotationItem.getVat()!=null?invQuotationItem.getVat().toPlainString():"");
						if(invQuotationItem.getVatCost()!=null){
							to.setVatCost(invQuotationItem.getVatCost().toPlainString());
							totalVatAmt=totalVatAmt+invQuotationItem.getVatCost().doubleValue();
						}
						to.setInvItem(invItemTo);
						purchaseOrderToList.add(to);
					    totalDiscount=CommonUtil.Round((totalDiscount+unitDiscount),2);
					    to.setCountId(count);
						orderForm.setCountId(String.valueOf(count));
	 					count++;
					}
					orderForm.setPurchaseItemList(purchaseOrderToList);
					orderForm.setTotalPriceExcludingVat(totalPriceExcludingVat.toString());
					orderForm.setTotalVatAmt(totalVatAmt.toString());
					orderForm.setAdditionalCost(quotationBo.getAdditionalCost()!=null?quotationBo.getAdditionalCost().toPlainString():"");
					orderForm.setAddnDiscount(quotationBo.getAdditionalDiscount()!=null?quotationBo.getAdditionalDiscount().toPlainString():"");
					if(quotationBo.getAdditionalDiscount()!=null){
						totalDiscount=CommonUtil.Round((totalDiscount+quotationBo.getAdditionalDiscount().doubleValue()),2);
					}
					
					orderForm.setTotalDiscount(totalDiscount.toString());
					orderForm.setServiceTax(quotationBo.getServiceTax()!=null?quotationBo.getServiceTax().toPlainString():"");
					orderForm.setServiceTaxCost(quotationBo.getServiceCost()!=null?quotationBo.getServiceCost().toPlainString():"");
					orderForm.setTotalCost(quotationBo.getTotalCost().toPlainString());
				}
			}
		}
		else if(orderForm.getMode().equalsIgnoreCase("Add")){
			// set item list
			orderForm.setPurchaseItemList(setListToForm(orderForm));
			orderForm.setTotalCost(null);
			orderForm.setAdditionalCost(null);
			orderForm.setAddnDiscount(null);
			orderForm.setTotalDiscount(null);
			orderForm.setServiceTax(null);
			orderForm.setServiceTaxCost(null);
			orderForm.setTotalPriceExcludingVat(null);
			orderForm.setTotalVatAmt(null);
		}
	}
	/**
	 * returns a list of companies from master table
	 * @return
	 * @throws Exception
	 */
	public List<InvCompanyTO> getCompany() throws Exception {
		IQuotationTransaction txn=QuotationTransactionImpl.getInstance();
		List<InvCompanyTO> companyList=new ArrayList<InvCompanyTO>();
		List<InvCompany> companybos=txn.getCompanyList();
		for (InvCompany invCompany : companybos) {
			InvCompanyTO to=new InvCompanyTO();
			to.setCompanyName(invCompany.getName());
			to.setId(invCompany.getId());
			companyList.add(to);
		}
		return companyList;
	}
}
