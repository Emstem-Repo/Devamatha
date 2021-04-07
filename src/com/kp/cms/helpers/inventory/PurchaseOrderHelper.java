package com.kp.cms.helpers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvCampus;
import com.kp.cms.bo.admin.InvCompany;
import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseOrderItem;
import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.bo.admin.InvQuotationItem;
import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.forms.inventory.PurchaseOrderForm;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * Helper Class for PurchaseOrderAction
 *
 */
public class PurchaseOrderHelper {
private static final Log log = LogFactory.getLog(PurchaseOrderHelper.class);
	
	public static volatile PurchaseOrderHelper self=null;
	public static PurchaseOrderHelper getInstance(){
		if(self==null){
			self= new PurchaseOrderHelper();
		}
		return self;
	}
	private PurchaseOrderHelper(){
		
	}
	/**
	 * @param itemBos
	 * @return
	 */
	public static List<InvItemTO> convertItemBosToTOs(List<InvItem> itemBos) {
		List<InvItemTO> itemtos=new ArrayList<InvItemTO>();
		if(itemBos!=null){
			Iterator<InvItem> itmItr=itemBos.iterator();
			while (itmItr.hasNext()) {
				InvItem item = (InvItem) itmItr.next();
				InvItemTO itmTO= new InvItemTO();
				itmTO.setId(item.getId());
				if(item.getCode()!=null )
				{
					itmTO.setNameWithCode(item.getName()+"("+item.getCode()+")");
				}else
				itmTO.setNameWithCode(item.getName());
				
				itmTO.setName(item.getName());
				itmTO.setPurchaseCost(String.valueOf(item.getPurchaseCost()));
				itmTO.setCode(item.getCode());
				if(item.getInvUomByInvPurchaseUomId()!=null){
					itmTO.setInvUomByInvPurchaseUomId(item.getInvUomByInvPurchaseUomId().getId());
					itmTO.setInvUomName(item.getInvUomByInvPurchaseUomId().getName());
				}
				itemtos.add(itmTO);
			}
		}
		return itemtos;
	}
	/**
	 * @param orderForm
	 * @return
	 * @throws Exception
	 */
	public InvPurchaseOrder prepareFinalPurchaseOrder( PurchaseOrderForm orderForm) throws Exception{
		InvPurchaseOrder neworder=new InvPurchaseOrder();
		/*String prefix=orderForm.getPrefix();
		String maxorderNo=orderForm.getPurchaseOrderNo();
		 String tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
		 if(tempMax!=null && StringUtils.isNumeric(tempMax))
		 {
			int maxOrder=Integer.parseInt(tempMax);
			neworder.setOrderNo(maxOrder);
		 }*/
		// In Case of Edit id and orderNo of PurchaseOrderBo is set 
		if(orderForm.getMode()!=null && orderForm.getMode().equalsIgnoreCase("Edit")){
			neworder.setId(orderForm.getPurchaseOrderId());
			neworder.setOrderNo(orderForm.getPurchaseOrderNo());
		}
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		neworder.setYear(year);
		neworder.setOrderDate(CommonUtil.ConvertStringToSQLDate(orderForm.getPurchaseDate()));
		neworder.setRemarks(orderForm.getRemarks());
		neworder.setTermsandconditions(orderForm.getTermConditions());
		neworder.setDeliverySite(orderForm.getSiteDelivery());
		if(orderForm.getAdditionalCost()!=null && ! StringUtils.isEmpty(orderForm.getAdditionalCost())
				&& CommonUtil.isValidDecimal(orderForm.getAdditionalCost()))
		neworder.setAdditionalCost(new BigDecimal(orderForm.getAdditionalCost()));
		if(orderForm.getTotalCost()!=null && ! StringUtils.isEmpty(orderForm.getTotalCost())
				&& CommonUtil.isValidDecimal(orderForm.getTotalCost()))
		neworder.setTotalCost(new BigDecimal(orderForm.getTotalCost()));
		neworder.setIsActive(true);
		//only in case of add created by and created date will be saved
		if(orderForm.getMode()!=null && orderForm.getMode().equalsIgnoreCase("Add")){
			neworder.setCreatedBy(orderForm.getUserId());
			neworder.setCreatedDate(new Date());
		}
		
		neworder.setModifiedBy(orderForm.getUserId());
		neworder.setLastModifiedDate(new Date());
	//	neworder.setPrefix(prefix);
	//Commented the below code as Vat is against each item and should be saved to the InvPurchaseOrderItems	
	/*	if(orderForm.getVat()!=null&&orderForm.getVat().trim().length()>0){
			neworder.setVat(new BigDecimal(orderForm.getVat()))	;
			
		}*/
		
		if(orderForm.getServiceTax()!=null && orderForm.getServiceTax().trim().length()>0){
			neworder.setServiceTax(new BigDecimal(orderForm.getServiceTax()))	;
		}
		
		
		if(orderForm.getVendorId()!=null && !StringUtils.isEmpty(orderForm.getVendorId()) && StringUtils.isNumeric(orderForm.getVendorId()))
		{
			InvVendor vendr= new InvVendor();
			vendr.setId(Integer.parseInt(orderForm.getVendorId()));
			neworder.setInvVendor(vendr);
		}
		
		if(orderForm.getAddnDiscount()!=null && !orderForm.getAddnDiscount().isEmpty() && CommonUtil.isValidDecimal(orderForm.getAddnDiscount()))
		neworder.setAdditionalDiscount(new BigDecimal(orderForm.getAddnDiscount()));
		
		InvCampus invCampus=new InvCampus();
		invCampus.setId(Integer.parseInt(orderForm.getCampusId()));
		neworder.setInvCampus(invCampus);
		
		InvCompany invCompany=new InvCompany();
		invCompany.setId(Integer.parseInt(orderForm.getCompanyId()));
		neworder.setInvCompany(invCompany);
		
		neworder.setDeliverySchedule(orderForm.getDeliverySchedule());
		
		if(orderForm.getServiceTaxCost()!=null && !orderForm.getServiceTaxCost().isEmpty() && CommonUtil.isValidDecimal(orderForm.getServiceTaxCost())){
			neworder.setServiceCost(new BigDecimal(orderForm.getServiceTaxCost()));
		}
		if(orderForm.getQuotationNo()!=null && !orderForm.getQuotationNo().isEmpty()){
			InvQuotation invQuotation=new InvQuotation();
			invQuotation.setId(orderForm.getQuotationId());
			neworder.setInvQuotation(invQuotation);
		}
		//set items
		setPurchaseOrderItems(orderForm,neworder);
		
		return neworder;
	}
	/**
	 * sets the PurchaseOrderItems data from form to bo
	 * @param orderForm
	 * @param neworder
	 * @throws Exception
	 */
	private void setPurchaseOrderItems(PurchaseOrderForm orderForm, InvPurchaseOrder neworder) throws Exception {
		if(orderForm.getPurchaseItemList()!=null){
			Set<InvPurchaseOrderItem> itembos= new HashSet<InvPurchaseOrderItem>();
			Iterator<InvPurchaseOrderItemTO> itmItr=orderForm.getPurchaseItemList().iterator();
			while (itmItr.hasNext()) {
				InvPurchaseOrderItemTO orderItmTO = (InvPurchaseOrderItemTO) itmItr.next();
				InvPurchaseOrderItem itembo= new InvPurchaseOrderItem();
				
				// setting Id of InvPurchaseOrderItem bo in case of edit
				if(orderForm.getMode()!=null && orderForm.getMode().equalsIgnoreCase("Edit")){
					itembo.setId(orderItmTO.getPoItemId());
				}
				
				if(orderItmTO.getInvItem()!=null){
					InvItem item= new InvItem();
					item.setId(Integer.parseInt(orderItmTO.getSelectedItemId()));
					itembo.setInvItem(item);
					if(orderItmTO.getInvItem().getPurchaseCost()!=null && ! StringUtils.isEmpty(orderItmTO.getInvItem().getPurchaseCost())
								&& CommonUtil.isValidDecimal(orderItmTO.getInvItem().getPurchaseCost()))	
					itembo.setUnitCost(new BigDecimal(orderItmTO.getInvItem().getPurchaseCost()));
				}
				if(orderItmTO.getQuantity()!=null && ! StringUtils.isEmpty(orderItmTO.getQuantity())
						&& CommonUtil.isValidDecimal(orderItmTO.getQuantity()))
				itembo.setQuantity(new BigDecimal(orderItmTO.getQuantity()));
				
				itembo.setIsActive(true);
				//only in case of add created by and created date is saved
				if(orderForm.getMode()!=null && orderForm.getMode().equalsIgnoreCase("Add")){
					itembo.setCreatedBy(orderForm.getUserId());
					itembo.setCreatedDate(new Date());
				}
				itembo.setModifiedBy(orderForm.getUserId());
				itembo.setLastModifiedDate(new Date());
				if(orderItmTO.getDiscount()!=null && ! StringUtils.isEmpty(orderItmTO.getDiscount())
						&& CommonUtil.isValidDecimal(orderItmTO.getDiscount()))	
				{
					orderForm.setDiscountExists(true);
					itembo.setUnitDiscount(new BigDecimal(orderItmTO.getDiscount()));
				}
				if(orderItmTO.getVat()!=null && orderItmTO.getVat().trim().length()>0){
					orderForm.setVatExists(true);
					itembo.setVat(new BigDecimal(orderItmTO.getVat()))	;
				}
				if(orderItmTO.getVatCost()!=null && !orderItmTO.getVatCost().isEmpty() && CommonUtil.isValidDecimal(orderItmTO.getVatCost())){
					itembo.setVatCost(new BigDecimal(orderItmTO.getVatCost()));
				}
				//to display the item name and item code in print of PO
				for(InvItemTO invItemTo:orderItmTO.getItemList()){
					if(String.valueOf(invItemTo.getId()).equalsIgnoreCase(orderItmTO.getSelectedItemId())){
						InvItemTO to=orderItmTO.getInvItem();
						to.setName(invItemTo.getName());
						to.setCode(invItemTo.getName());
						orderItmTO.setInvItem(to);
					}
				}
				// to display total cost per item including vat amt in print of PO
				if(orderItmTO.getTotalCost()!=null && !orderItmTO.getTotalCost().isEmpty() && CommonUtil.isValidDecimal(orderItmTO.getTotalCost())){
					Double totalCost=Double.parseDouble(orderItmTO.getTotalCost());
					Double vatAmt=0.0;
					if(orderItmTO.getVatCost()!=null && !orderItmTO.getVatCost().isEmpty() && CommonUtil.isValidDecimal(orderItmTO.getVatCost()))
						 vatAmt=Double.parseDouble(orderItmTO.getVatCost());
					Double TotalWithVat=vatAmt+totalCost;
					orderItmTO.setTotalCostInclusiveVat(TotalWithVat.toString());
				}
				itembos.add(itembo);
			}
			neworder.setInvPurchaseOrderItems(itembos);
		}
	}
	/**
	 * @param invquotBo
	 * @return
	 * @throws Exception
	 */
	public List<InvPurchaseOrderItemTO> convertBotoTo(InvQuotation invquotBo,List<InvItemTO> itemList,PurchaseOrderForm orderForm) throws Exception{
		List<InvPurchaseOrderItemTO> orderList=null;
		Double totalDiscount=0.0;
		Double totalCost=0.0;
		Double totalPriceExcludingVat=0.0;
		Double totalVatAmt=0.0;
		if(invquotBo!=null && invquotBo.getInvQuotationItems()!=null && !invquotBo.getInvQuotationItems().isEmpty()){
			orderForm.setQuotationDate(CommonUtil.ConvertStringToDateFormat((CommonUtil.getStringDate(invquotBo.getQuoteDate())), "dd-MMM-yyyy", "dd/MM/yyyy"));
			orderForm.setQuotationId(invquotBo.getId());
			orderList=new ArrayList<InvPurchaseOrderItemTO>();
			int count = 1;
			for (InvQuotationItem invQuotationItem : invquotBo.getInvQuotationItems()) {
				InvPurchaseOrderItemTO to=new InvPurchaseOrderItemTO();
				to.setCountId(count);
				orderForm.setCountId(String.valueOf(count));
				to.setItemList(itemList);
				to.setSelectedItemId(String.valueOf(invQuotationItem.getInvItem().getId()));
				to.setQuantity(invQuotationItem.getQuantity().toString());
				InvItemTO invItemTo=new InvItemTO();
				invItemTo.setInvUomName(invQuotationItem.getInvItem().getInvUomByInvPurchaseUomId().getName());
				invItemTo.setPurchaseCost(invQuotationItem.getUnitCost().toPlainString());
				invItemTo.setName(invQuotationItem.getInvItem().getName());
				invItemTo.setCode(invQuotationItem.getInvItem().getCode());
				to.setInvItem(invItemTo);
		//		orderForm.setOrigPurchaseCost(invQuotationItem.getUnitCost().toPlainString());
				
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
				 totalDiscount=CommonUtil.Round((totalDiscount+unitDiscount),2);
				orderList.add(to);
				count++;
			}
			orderForm.setTotalPriceExcludingVat(totalPriceExcludingVat.toString());
			orderForm.setTotalVatAmt(totalVatAmt.toString());
			orderForm.setAdditionalCost(invquotBo.getAdditionalCost()!=null?invquotBo.getAdditionalCost().toPlainString():"");
			orderForm.setAddnDiscount(invquotBo.getAdditionalDiscount()!=null?invquotBo.getAdditionalDiscount().toPlainString():"");
			if(invquotBo.getAdditionalDiscount()!=null){
				totalDiscount=CommonUtil.Round((totalDiscount+invquotBo.getAdditionalDiscount().doubleValue()),2);
			}
			
			orderForm.setTotalDiscount(totalDiscount.toString());
			orderForm.setServiceTax(invquotBo.getServiceTax()!=null?invquotBo.getServiceTax().toPlainString():"");
			orderForm.setServiceTaxCost(invquotBo.getServiceCost()!=null?invquotBo.getServiceCost().toPlainString():"");
			orderForm.setTotalCost(invquotBo.getTotalCost().toPlainString());
		}
		return orderList;
	}
}
