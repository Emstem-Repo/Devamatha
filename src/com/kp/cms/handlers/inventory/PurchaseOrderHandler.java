package com.kp.cms.handlers.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvPOTermsAndConditions;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseOrderItem;
import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.PurchaseOrderForm;
import com.kp.cms.helpers.inventory.PurchaseOrderHelper;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.transactions.inventory.IPOTermsConditionsTransaction;
import com.kp.cms.transactions.inventory.IPurchaseOrderTransaction;
import com.kp.cms.transactions.inventory.IPurchaseReturnTransaction;
import com.kp.cms.transactionsimpl.inventory.POTermsConditionsTransactionimpl;
import com.kp.cms.transactionsimpl.inventory.PurchaseOrderTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.PurchaseReturnTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Handler class for PurchaseOrderAction
 *
 */
public class PurchaseOrderHandler {
	
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	
	private static final Log log = LogFactory.getLog(PurchaseOrderHandler.class);
	
	public static volatile PurchaseOrderHandler self=null;
	public static PurchaseOrderHandler getInstance(){
		if(self==null){
			self= new PurchaseOrderHandler();
		}
		return self;
	}
	private PurchaseOrderHandler(){
		
	}
	/**
	 * @param orderForm
	 */
	public void setPurchaseOrderNo(PurchaseOrderForm orderForm) throws Exception {
		IPurchaseOrderTransaction txn= new PurchaseOrderTransactionImpl();
		String purchaseOrderNo= txn.getLatestPurchaseOrderNo(CMSConstants.PURCHASE_ORDER_COUNTER,orderForm);
		orderForm.setPurchaseOrderNo(purchaseOrderNo);
	}
	/**
	 * @return
	 */
	public List<InvItemTO> getItemList()throws Exception {
		IPurchaseOrderTransaction txn= new PurchaseOrderTransactionImpl();
		List<InvItem> ItemBos=txn.getItemsList();
		List<InvItemTO> itemtos=PurchaseOrderHelper.convertItemBosToTOs(ItemBos);
		return itemtos;
	}
	/**
	 * @param pchItemList
	 * @param orderForm
	 */
	public void updatePurchaseItemList(List<InvPurchaseOrderItemTO> pchItemList, PurchaseOrderForm orderForm) throws Exception {
		
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
	public boolean placeFinalPurchaseOrder(PurchaseOrderForm orderForm) throws Exception{
		InvPurchaseOrder finalOrder=PurchaseOrderHelper.getInstance().prepareFinalPurchaseOrder(orderForm);
		boolean updated=false;
		if(finalOrder!=null){
			//save it
			IPurchaseOrderTransaction txn= new PurchaseOrderTransactionImpl();
			updated=txn.placeFinalPurchaseOrder(finalOrder,orderForm);
		}
		return updated;
	}
	/**
	 * @return
	 */
	public List<InvItemTO> getItemList(int vendorId)throws Exception {
		IPurchaseOrderTransaction txn= new PurchaseOrderTransactionImpl();
		List<InvItem> ItemBos=txn.getItemsList(vendorId);
		List<InvItemTO> itemtos=PurchaseOrderHelper.convertItemBosToTOs(ItemBos);
		return itemtos;
	}
	/**
	 * @param orderForm
	 * @return
	 */
	public InvPurchaseOrder getPrintData(PurchaseOrderForm orderForm) throws Exception {
		IPurchaseReturnTransaction txn= new PurchaseReturnTransactionImpl();
		InvPurchaseOrder orderBO=null;
		String prefix=QuotationReportHandler.getInstance().getQuotationPrefix(CMSConstants.PURCHASE_ORDER_COUNTER);
		String maxorderNo=orderForm.getOrderNo();
		String tempMax="";
		
		if(prefix!=null && !prefix.isEmpty() && maxorderNo.startsWith(prefix) )
		tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
		
		if(tempMax!=null && !tempMax.isEmpty() && StringUtils.isNumeric(tempMax))
		orderBO=txn.getPurchaseorderDetails(tempMax);
		
		if(orderBO==null)
			return null;
		else
		return orderBO;
	}
	/**
	 * @param orderBO
	 * @param orderForm
	 * @throws Exception
	 */
	public void setBoDataToForm(InvPurchaseOrder orderBO,PurchaseOrderForm orderForm) throws Exception {
		
	//	String prefix=QuotationReportHandler.getInstance().getQuotationPrefix(CMSConstants.PURCHASE_ORDER_COUNTER);
		orderForm.setPurchaseOrderNo(orderBO.getOrderNo());
		orderForm.setPurchaseDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(orderBO.getOrderDate()), PurchaseOrderHandler.SQL_DATEFORMAT,PurchaseOrderHandler.FROM_DATEFORMAT));
		InvVendor invVendor=orderBO.getInvVendor();
		if(invVendor!=null){
			orderForm.setVendorName(invVendor.getName());
			orderForm.setVendorAddress1(invVendor.getAddressLine1());
			orderForm.setVendorAddress2(invVendor.getAddressLine2());
		}
		List<InvPurchaseOrderItemTO> purchaseItemList=new ArrayList<InvPurchaseOrderItemTO>();
		Set<InvPurchaseOrderItem> itemList=orderBO.getInvPurchaseOrderItems();
		long cost=0;
		if(itemList!=null && !itemList.isEmpty()){
			Iterator<InvPurchaseOrderItem> itr=itemList.iterator();
			while (itr.hasNext()) {
				InvPurchaseOrderItem bo = (InvPurchaseOrderItem) itr.next();
				InvPurchaseOrderItemTO to=new InvPurchaseOrderItemTO();
				to.setQuantity(bo.getQuantity().toString());
				to.setTotalCost(bo.getUnitCost().toString());
				cost=cost+(bo.getQuantity().longValue()*bo.getUnitCost().longValue());
				InvItemTO invItem=new InvItemTO();
				InvItem bo1=bo.getInvItem();
				if(bo1!=null){
					invItem.setCode(bo1.getCode());
					invItem.setName(bo1.getName());
					invItem.setPurchaseCost(bo1.getPurchaseCost().toString());
					invItem.setInvUomName(bo1.getInvUomByInvPurchaseUomId().getName());
					to.setInvItem(invItem);
				}
				purchaseItemList.add(to);
			}
		}
		orderForm.setPurchaseItemList(purchaseItemList);
		orderForm.setTotalCost(orderBO.getTotalCost().toString());
		int vatTax=0;
		int serviceTax=0;
		long vat=0;
		long ser=0;
		
		if(orderBO.getVat()!=null){
			vatTax=orderBO.getVat().intValue();
			vat=(cost*vatTax)/100;
			orderForm.setVatCost(String.valueOf(vat));
		}
		if(orderBO.getServiceTax()!=null){
			serviceTax=orderBO.getServiceTax().intValue();
			ser=(cost*serviceTax)/100;
			orderForm.setServiceTaxCost(String.valueOf(ser));
		}
		if(orderBO.getAdditionalCost()!=null)
		orderForm.setAdditionalCost(orderBO.getAdditionalCost().toString());
		
		orderForm.setDate(CommonUtil.getStringDate(new Date()));

		double taxAmount = Double.valueOf(vat)
				+ Double.valueOf(ser);

		orderForm.setTotalTaxCost(Double.toString(taxAmount));
		orderForm.setPrint(true);
	}
	/**
	 * @param quotationNo
	 * @return
	 * @throws Exception
	 */
	public List<InvPurchaseOrderItemTO> setListToForm(PurchaseOrderForm orderForm,InvQuotation invquotBo) throws Exception {
		 List<InvItemTO> itemList=QuotationHandler.getInstance().getItemList();
		 List<InvPurchaseOrderItemTO> purchaseOrderToList=PurchaseOrderHelper.getInstance().convertBotoTo(invquotBo,itemList,orderForm);
		return purchaseOrderToList;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public String getTermsAndConditions() throws Exception {
		IPOTermsConditionsTransaction txn=POTermsConditionsTransactionimpl.getInstance();
		InvPOTermsAndConditions bo=txn.getTCBo();
		if(bo!=null && bo.getIsActive()){
			return bo.getTcDescription();
		}
		else return null;
	}
	/**
	 * In case of edit, checks the purchase order bo and sets the data from DB to first page or second page of form  according to the boolean 
	 * In case of Add ,if Quotation Ref No is entered sets the data of that Ref no to first page or second page according to the boolean
	 * @param orderForm
	 * @param firstPage
	 * @throws Exception
	 */
	public void getPurchaseOrderDetails(PurchaseOrderForm orderForm, boolean firstPage) throws Exception {

		if(orderForm.getMode().equalsIgnoreCase("Edit")){
			List<InvPurchaseOrderItemTO> purchaseOrderToList=new ArrayList<InvPurchaseOrderItemTO>();
			IPurchaseOrderTransaction txn= PurchaseOrderTransactionImpl.getInstance();
			InvPurchaseOrder purchaseOrderBo=txn.getPOForEdit(orderForm.getPurchaseOrderNo());
			Double totalDiscount=0.0;
			Double totalCost=0.0;
			Double totalPriceExcludingVat=0.0;
			Double totalVatAmt=0.0;
			int count=1;
			if(purchaseOrderBo!=null ){
				if(firstPage){
				orderForm.setPurchaseOrderId(purchaseOrderBo.getId());
				//set vendor name to form
				orderForm.setVendorId(purchaseOrderBo.getInvVendor()!=null?String.valueOf(purchaseOrderBo.getInvVendor().getId()):"");
				//set campus name to form
				orderForm.setCampusId(purchaseOrderBo.getInvCampus()!=null?String.valueOf(purchaseOrderBo.getInvCampus().getId()):"");
				// set Company name to form
				orderForm.setCompanyId(purchaseOrderBo.getInvCompany()!=null?String.valueOf(purchaseOrderBo.getInvCompany().getId()):"");
				orderForm.setPurchaseDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(purchaseOrderBo.getOrderDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
				orderForm.setRemarks(purchaseOrderBo.getRemarks()!=null?purchaseOrderBo.getRemarks():"");
				
				if(purchaseOrderBo.getTermsandconditions()!=null && !purchaseOrderBo.getTermsandconditions().isEmpty()){
					String savedTcDesc=purchaseOrderBo.getTermsandconditions();
						
						String[] args=savedTcDesc.split("<br>");
						StringBuilder strTc=new StringBuilder();
						for (String string : args) {
							strTc.append(string);
							strTc.append("\r\n");
						}
						if(strTc!=null && !strTc.toString().isEmpty())
							orderForm.setTermConditions(strTc.toString());
					
				}
				orderForm.setSiteDelivery(purchaseOrderBo.getDeliverySite()!=null?purchaseOrderBo.getDeliverySite():"");
				orderForm.setDeliverySchedule(purchaseOrderBo.getDeliverySchedule()!=null?purchaseOrderBo.getDeliverySchedule():"");
				orderForm.setQuotationNo(purchaseOrderBo.getInvQuotation()!=null?purchaseOrderBo.getInvQuotation().getQuoteNo():"");
				orderForm.setDataExistsForEdit(true);
				}
				else if(!firstPage){
					Set<InvPurchaseOrderItem> invPOItems=purchaseOrderBo.getInvPurchaseOrderItems();
					for (InvPurchaseOrderItem invPOItem : invPOItems) {
						List<InvItemTO> itemList=QuotationHandler.getInstance().getItemList();
						InvPurchaseOrderItemTO to= new InvPurchaseOrderItemTO();
						to.setItemList(itemList);
						to.setPoItemId(invPOItem.getId());
						to.setSelectedItemId(String.valueOf(invPOItem.getInvItem().getId()));
						to.setQuantity(invPOItem.getQuantity().toPlainString());
						InvItemTO invItemTo=new InvItemTO();
						invItemTo.setInvUomName(invPOItem.getInvItem().getInvUomByInvPurchaseUomId().getName());
						invItemTo.setPurchaseCost(invPOItem.getUnitCost().toPlainString());
					//	orderForm.setOrigPurchaseCost(invPOItem.getUnitCost().toPlainString());
						Double unitDiscount=0.0; 
						if(invPOItem.getUnitDiscount()!=null){
							to.setDiscount(invPOItem.getUnitDiscount().toPlainString());
							unitDiscount=invPOItem.getUnitDiscount().doubleValue();
						}
						 totalCost=CommonUtil.Round(((invPOItem.getUnitCost().doubleValue()*invPOItem.getQuantity().doubleValue())-unitDiscount)
								, 2);
						 totalPriceExcludingVat=totalPriceExcludingVat+totalCost;
						to.setTotalCost(totalCost.toString());
						to.setVat(invPOItem.getVat()!=null?invPOItem.getVat().toPlainString():"");
						if(invPOItem.getVatCost()!=null){
							to.setVatCost(invPOItem.getVatCost().toPlainString());
							totalVatAmt=totalVatAmt+invPOItem.getVatCost().doubleValue();
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
					orderForm.setAdditionalCost(purchaseOrderBo.getAdditionalCost()!=null?purchaseOrderBo.getAdditionalCost().toPlainString():"");
					orderForm.setAddnDiscount(purchaseOrderBo.getAdditionalDiscount()!=null?purchaseOrderBo.getAdditionalDiscount().toPlainString():"");
					if(purchaseOrderBo.getAdditionalDiscount()!=null){
						totalDiscount=CommonUtil.Round((totalDiscount+purchaseOrderBo.getAdditionalDiscount().doubleValue()),2);
					}
					
					orderForm.setTotalDiscount(totalDiscount.toString());
					orderForm.setServiceTax(purchaseOrderBo.getServiceTax()!=null?purchaseOrderBo.getServiceTax().toPlainString():"");
					orderForm.setServiceTaxCost(purchaseOrderBo.getServiceCost()!=null?purchaseOrderBo.getServiceCost().toPlainString():"");
					orderForm.setTotalCost(purchaseOrderBo.getTotalCost().toPlainString());
				}
			}
		}
		else if(orderForm.getMode().equalsIgnoreCase("Add")){
			if(orderForm.getQuotationNo()!=null && !orderForm.getQuotationNo().isEmpty()){
				IPurchaseOrderTransaction txn=PurchaseOrderTransactionImpl.getInstance();
				InvQuotation invquotBo=txn.getInvQuotation(orderForm.getQuotationNo());
				if(invquotBo!=null){
					if(firstPage){
						//set vendor name to form
						orderForm.setVendorId(invquotBo.getInvVendor()!=null?String.valueOf(invquotBo.getInvVendor().getId()):"");
						//set campus name to form
						orderForm.setCampusId(invquotBo.getInvCampus()!=null?String.valueOf(invquotBo.getInvCampus().getId()):"");
						// set Company name to form
						orderForm.setCompanyId(invquotBo.getInvCompany()!=null?String.valueOf(invquotBo.getInvCompany().getId()):"");
						orderForm.setRemarks(invquotBo.getRemarks()!=null?invquotBo.getRemarks():"");
						
						if(invquotBo.getTermsandconditions()!=null && !invquotBo.getTermsandconditions().isEmpty()){
							String savedTcDesc=invquotBo.getTermsandconditions();
								
								String[] args=savedTcDesc.split("<br>");
								StringBuilder strTc=new StringBuilder();
								for (String string : args) {
									strTc.append(string);
									strTc.append("\r\n");
								}
								if(strTc!=null && !strTc.toString().isEmpty())
									orderForm.setTermConditions(strTc.toString());
							
						}
						orderForm.setSiteDelivery(invquotBo.getDeliverySite()!=null?invquotBo.getDeliverySite():"");
						orderForm.setQuotationNo(invquotBo.getQuoteNo());
						orderForm.setDataExistsForEdit(true);
					}
					else if(!firstPage){
						// set item list
						orderForm.setPurchaseItemList(PurchaseOrderHandler.getInstance().setListToForm(orderForm,invquotBo));
					}
					
				}
				else {
					// if InvQuotationBO is null throws no records found exception
					if(firstPage){
						throw new BusinessException();
					}else{
					  List<InvPurchaseOrderItemTO> purchaseOrderToList=new ArrayList<InvPurchaseOrderItemTO>();
					  List<InvItemTO> itemList=QuotationHandler.getInstance().getItemList();
						InvPurchaseOrderItemTO to= new InvPurchaseOrderItemTO();
						to.setItemList(itemList);
						InvItemTO invItemTo=new InvItemTO();
						to.setInvItem(invItemTo);
						purchaseOrderToList.add(to);
						orderForm.setCountId("1");
						to.setCountId(1);
						orderForm.setPurchaseItemList(purchaseOrderToList);
						orderForm.setQuotationNo(null);
						throw new BusinessException();
					}
				}
			}
			else{
				// if quotation no is null in case of submitInitPO below logic is executed
				 List<InvPurchaseOrderItemTO> purchaseOrderToList=new ArrayList<InvPurchaseOrderItemTO>();
				  List<InvItemTO> itemList=QuotationHandler.getInstance().getItemList();
					InvPurchaseOrderItemTO to= new InvPurchaseOrderItemTO();
					to.setItemList(itemList);
					InvItemTO invItemTo=new InvItemTO();
					to.setInvItem(invItemTo);
					purchaseOrderToList.add(to);
					orderForm.setCountId("1");
					to.setCountId(1);
					orderForm.setPurchaseItemList(purchaseOrderToList);
			}

		}
			
	}
}
