package com.kp.cms.helpers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import com.kp.cms.forms.inventory.QuotationForm;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * Helper Class for PurchaseOrderAction
 *
 */
public class QuotationHelper {
private static final Log log = LogFactory.getLog(QuotationHelper.class);
	
	public static volatile QuotationHelper self=null;
	public static QuotationHelper getInstance(){
		if(self==null){
			self= new QuotationHelper();
		}
		return self;
	}
	private QuotationHelper(){
		
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
		Collections.sort(itemtos);
		return itemtos;
	}
	/**
	 * @param orderForm
	 * @return
	 * @throws Exception
	 */
	public InvQuotation prepareFinalQuotation(QuotationForm orderForm) throws Exception{
		InvQuotation neworder=new InvQuotation();
		
		/*String prefix=orderForm.getPrefix();
		String maxorderNo=orderForm.getQuotationNo();
		 String tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
		 if(tempMax!=null && StringUtils.isNumeric(tempMax))
		 {
			int maxOrder=Integer.parseInt(tempMax);
			neworder.setQuoteNo(maxOrder);
		 }*/
		if(orderForm.getMode()!=null && orderForm.getMode().equalsIgnoreCase("Edit")){
			neworder.setId(orderForm.getQuotationId());
			neworder.setQuoteNo(orderForm.getQuotationNo());
		}
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		neworder.setYear(year);
		neworder.setQuoteDate(CommonUtil.ConvertStringToSQLDate(orderForm.getPurchaseDate()));
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
		if(orderForm.getVendorId()!=null && !StringUtils.isEmpty(orderForm.getVendorId()) && StringUtils.isNumeric(orderForm.getVendorId()))
		{
			InvVendor vendr= new InvVendor();
			vendr.setId(Integer.parseInt(orderForm.getVendorId()));
			neworder.setInvVendor(vendr);
		}
		if(orderForm.getAddnDiscount()!=null && !orderForm.getAddnDiscount().isEmpty())
			neworder.setAdditionalDiscount(new BigDecimal(orderForm.getAddnDiscount()));
		InvCampus invCampus=new InvCampus();
		invCampus.setId(Integer.parseInt(orderForm.getCampusId()));
		neworder.setInvCampus(invCampus);
		
		InvCompany invCompany=new InvCompany();
		invCompany.setId(Integer.parseInt(orderForm.getCompanyId()));
		neworder.setInvCompany(invCompany);

		if(orderForm.getServiceTax()!=null && orderForm.getServiceTax().trim().length()>0){
			neworder.setServiceTax(new BigDecimal(orderForm.getServiceTax()));
		}
		if(orderForm.getServiceTaxCost()!=null && !orderForm.getServiceTaxCost().isEmpty() && CommonUtil.isValidDecimal(orderForm.getServiceTaxCost())){
			neworder.setServiceCost(new BigDecimal(orderForm.getServiceTaxCost()));
		}
		//set items
		setQuotationItems(orderForm,neworder);
		
		return neworder;
	}
	/**
	 *  sets the InvQuotationItems data from form to bo
	 * @param orderForm
	 * @param neworder
	 * @throws Exception
	 */
	private void setQuotationItems(QuotationForm orderForm, InvQuotation neworder) throws Exception {
		if(orderForm.getPurchaseItemList()!=null){
			Set<InvQuotationItem> itembos= new HashSet<InvQuotationItem>();
			Iterator<InvPurchaseOrderItemTO> itmItr=orderForm.getPurchaseItemList().iterator();
			while (itmItr.hasNext()) {
				InvPurchaseOrderItemTO orderItmTO = (InvPurchaseOrderItemTO) itmItr.next();
				InvQuotationItem itembo= new InvQuotationItem();
				// setting Id of InvQuotationItem bo in case of edit
				if(orderForm.getMode()!=null && orderForm.getMode().equalsIgnoreCase("Edit")){
					itembo.setId(orderItmTO.getQuotationItemId());
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
					itembo.setUnitDiscount(new BigDecimal(orderItmTO.getDiscount()));
				if(orderItmTO.getVat()!=null && orderItmTO.getVat().trim().length()>0){
					itembo.setVat(new BigDecimal(orderItmTO.getVat()));
				}
				if(orderItmTO.getVatCost()!=null && !orderItmTO.getVatCost().isEmpty() && CommonUtil.isValidDecimal(orderItmTO.getVatCost())){
					itembo.setVatCost(new BigDecimal(orderItmTO.getVatCost()));
				}
				itembos.add(itembo);
			}
			neworder.setInvQuotationItems(itembos);
		}
	}
}
