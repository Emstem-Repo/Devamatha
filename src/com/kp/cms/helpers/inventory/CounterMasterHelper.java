package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvCompany;
import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.CounterMasterForm;
import com.kp.cms.to.inventory.InvCounterTO;


public class CounterMasterHelper {
	private static final Log log = LogFactory.getLog(CounterMasterHelper.class);
	public static volatile CounterMasterHelper counterMasterHelper = null;

	public static CounterMasterHelper getInstance() {
		if (counterMasterHelper == null) {
			counterMasterHelper = new CounterMasterHelper();
			return counterMasterHelper;
		}
		return counterMasterHelper;
	}	
	/**
	 * 
	 * @param counterList
	 *            this will copy the InvCounter BO to TO
	 * @return invCounterTOList having InvCounterTO objects.
	 */
	public List<InvCounterTO> copyInvCounterBosToTos(List<InvCounter> counterList) {
		log.debug("inside copyInvCounterBosToTos");
		List<InvCounterTO> invCounterTOList = new ArrayList<InvCounterTO>();
		Iterator<InvCounter> iterator = counterList.iterator();
		InvCounter invCounter;
		InvCounterTO invCounterTO;
		while (iterator.hasNext()) {
			invCounterTO = new InvCounterTO();
			invCounter = (InvCounter) iterator.next();
			invCounterTO.setId(invCounter.getId());
			invCounterTO.setType(invCounter.getType());
			invCounterTO.setPrefix(invCounter.getPrefix());
			invCounterTO.setStartNo(invCounter.getStartNo());
			if(invCounter.getCurrentNo()!=null)
			invCounterTO.setCurrentNo(invCounter.getCurrentNo());
			invCounterTOList.add(invCounterTO);
		}
		log.debug("leaving copyInvCounterBosToTos");
		return invCounterTOList;
	}

	/**
	 * 
	 * @param counterMasterForm
	 * @return
	 * @throws Exception
	 */
	public InvCounter copyDataFromFormToBO(CounterMasterForm counterMasterForm,String mode) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		InvCounter invCounter = new InvCounter();
		invCounter.setType(counterMasterForm.getType());
		invCounter.setYear(Integer.parseInt(counterMasterForm.getYear()));
		invCounter.setPrefix(counterMasterForm.getPrefix());
		if(counterMasterForm.getStartNo()!= null && !counterMasterForm.getStartNo().trim().isEmpty()){
			invCounter.setStartNo(Integer.parseInt(counterMasterForm.getStartNo()));
		}
		invCounter.setIsActive(true);
		invCounter.setModifiedBy(counterMasterForm.getUserId());
		invCounter.setLastModifiedDate(new Date());
		if(counterMasterForm.getType().equalsIgnoreCase(CMSConstants.PURCHASE_ORDER_COUNTER)){
			InvCompany invCompany=new InvCompany();
			invCompany.setId(Integer.parseInt(counterMasterForm.getCompanyId()));
			invCounter.setInvCompany(invCompany);
		}
		if(mode.equalsIgnoreCase("edit")){
			if(counterMasterForm.getId() != 0) {
				invCounter.setId(counterMasterForm.getId());
			}
			invCounter.setCurrentNo(Integer.parseInt(counterMasterForm.getCurrentNo()));
		}else{
			invCounter.setCurrentNo(Integer.parseInt(counterMasterForm.getStartNo()));
			invCounter.setCreatedBy(counterMasterForm.getUserId());
			invCounter.setCreatedDate(new Date());
		}
		log.debug("leaving copyDataFromFormToBO");
		return invCounter;
		}
	/**
	 * @param counterMasterForm
	 * @return
	 * @throws Exception
	 */
	public String checkCurrentNoExistsQuery(CounterMasterForm counterMasterForm) throws Exception {
		String query="";
		if(counterMasterForm.getType().equals(CMSConstants.INVENTORY_COUNTER_TYPE)){
			query="from InvStockTransfer i where i.isActive=1 and i.transferNo="+counterMasterForm.getCurrentNo();
		}else if(counterMasterForm.getType().equals(CMSConstants.PURCHASE_ORDER_COUNTER)){
			query="from InvPurchaseOrder i where i.isActive=1 and i.orderNo="+counterMasterForm.getCurrentNo();
		}else if(counterMasterForm.getType().equals(CMSConstants.QUOTATION_COUNTER)){
			query="from InvQuotation i where i.isActive=1 and i.quoteNo="+counterMasterForm.getCurrentNo();
		}
		return query;
	}
	/**
	 * @param bo
	 * @param counterMasterForm
	 * @return
	 * @throws Exception
	 */
	public static void convertBotoTo(InvCounter bo,
			CounterMasterForm counterMasterForm) throws Exception {
		if(bo!=null){
			counterMasterForm.setId(bo.getId());
			counterMasterForm.setPrefix(bo.getPrefix());
			counterMasterForm.setStartNo(String.valueOf(bo.getStartNo()));
			counterMasterForm.setType(bo.getType());
			counterMasterForm.setOrigPrefix(bo.getPrefix());
			counterMasterForm.setOrigStartNo(Integer.toString(bo.getStartNo()));
			counterMasterForm.setOrigType(bo.getType());
			if(bo.getCurrentNo()!=null)
			counterMasterForm.setCurrentNo(Integer.toString(bo.getCurrentNo()));
			counterMasterForm.setYear(bo.getYear().toString());
			if(bo.getInvCompany()!=null)
			counterMasterForm.setCompanyId(String.valueOf(bo.getInvCompany().getId()));	
		}
	}
	
}
