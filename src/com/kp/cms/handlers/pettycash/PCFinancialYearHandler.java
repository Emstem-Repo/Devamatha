package com.kp.cms.handlers.pettycash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.pettycash.PCFinancialYearEntryForm;
import com.kp.cms.helpers.pettycash.PCFinancialYearHelper;
import com.kp.cms.to.pettycash.PCFinancialYearTO;
import com.kp.cms.transactions.pettycash.IPCFinancialYearTransaction;
import com.kp.cms.transactionsimpl.pettycash.PCFinancialYearTransactionImpl;
import common.Logger;


/**
 * @author kolli.ramamohan
 * @version 1.0
 */
public class PCFinancialYearHandler {

	private static final Logger log = Logger
			.getLogger(PCFinancialYearHandler.class);
	private static volatile PCFinancialYearHandler pcFinancialYearHandler = null;

	IPCFinancialYearTransaction iPCFinancialYearTransaction = new PCFinancialYearTransactionImpl();

	@SuppressWarnings("unused")
	private PCFinancialYearHandler() {

	}
	public static PCFinancialYearHandler getInstance() {
		if (pcFinancialYearHandler == null) {
			pcFinancialYearHandler = new PCFinancialYearHandler();
		}
		return pcFinancialYearHandler;
	}
	
	/** 
	 * Used to add PCFinancialYear	
	 * @param com.kp.cms.forms.PC.PCFinancialYearEntryForm, javax.servlet.http.HttpServletRequest
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public boolean addPcFinancialYear(
			PCFinancialYearEntryForm pcFinancialYearEntryForm, ActionErrors errors) throws Exception {
		log.info("Start of addPCFinancialYear of PCFinancialYearHandler");

		PcFinancialYear pcFinancialYear = null;
		PCFinancialYearTO pcFinancialYearTO = new PCFinancialYearTO();	
		
		List<PCFinancialYearTO> pcFinancialYearTOList=getPCFinancialDetails();
		Iterator<PCFinancialYearTO> pcFinancialYearTOIterator=pcFinancialYearTOList.iterator();

		if (pcFinancialYearTO != null) {
			while(pcFinancialYearTOIterator.hasNext()){
				pcFinancialYearTO=pcFinancialYearTOIterator.next();
				if(pcFinancialYearEntryForm.getIsCurrent().equals("true") && pcFinancialYearTO.getIsCurrent().equals("Yes")){				
					//errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_FINANCIAL_YEAR_NOT_CURRENT + " "+ feeFinancialYearTO.getYear()+" "+ CMSConstants.FEE_FINANCIAL_YEAR_ALREADY_STORED));
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PC_FINANCIAL_YEAR_MORETHAN_ONE));
					throw new BusinessException();					
				}
			}
			pcFinancialYearTO.setCreatedBy(pcFinancialYearEntryForm.getUserId());
			pcFinancialYearTO.setModifiedBy(pcFinancialYearEntryForm.getUserId());
			pcFinancialYearTO.setYear(pcFinancialYearEntryForm.getPcFinancialYear());
			pcFinancialYearTO.setIsCurrent(pcFinancialYearEntryForm.getIsCurrent());
			pcFinancialYear = PCFinancialYearHelper.getInstance().populateTOtoBO(pcFinancialYearTO);
			if (pcFinancialYear != null && iPCFinancialYearTransaction != null) {
				return iPCFinancialYearTransaction.addPcFinancialYear(pcFinancialYear);
			}
		}	
		log.info("End of addPCFinancialYear of PCFinancialYearHandler");
		return false;
	}

	
	/** 
	 * Used to get PCFinancialYear Details	
	 * @param void
	 * @return List<PCFinancialYear>
	 * @throws Exception
	 */
	public List<PCFinancialYearTO> getPCFinancialDetails() throws Exception {
		log.info("Start of getPCFinancialDetails of PCFinancialYearHandler");

		if (iPCFinancialYearTransaction != null) {
			List<PcFinancialYear> pcFinancialYearList = iPCFinancialYearTransaction.getPcFinancialYearDetails();
			if (pcFinancialYearList != null) {
				return PCFinancialYearHelper.getInstance().poupulateBOtoTO(
						pcFinancialYearList);
			}
		}
		log.info("End of getPCFinancialDetails of PCFinancialYearHandler");
		return new ArrayList<PCFinancialYearTO>();
	}
	
	/** 
	 * Used to get PCFinancialYear	
	 * @param java.lang.String
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PcFinancialYear getPcFinancialYear(String pcFinancialYear)
			throws Exception {
		log.info("Start of getPCFinancialYear of PCFinancialYearHandler");
		if (iPCFinancialYearTransaction != null) {
			return iPCFinancialYearTransaction
					.getPcFinancialYear(pcFinancialYear);
		}
		log.info("End of getPCFinancialYear of PCFinancialYearHandler");
		return new PcFinancialYear();
	}
	
	/** 
	 * Used to get PCFinancialYear with Id	
	 * @param int
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PCFinancialYearTO getPCFinancialDetailsWithId(int id)
			throws Exception {
		log.info("Start of getPCFinancialDetailsWithId of PCFinancialYearHandler");
		if (iPCFinancialYearTransaction != null) {
			PcFinancialYear pcFinancialYear = iPCFinancialYearTransaction
					.getPcFinancialDetailsWithId(id);
			if (pcFinancialYear != null) {
				return PCFinancialYearHelper.getInstance().populateBOtoTOEdit(
						pcFinancialYear);
			}
		}
		log.info("End of getPCFinancialDetailsWithId of PCFinancialYearHandler");
		return new PCFinancialYearTO();
	}
	/** 
	 * Used to update PCFinancialYear
	 * @param com.kp.cms.forms.fee.PCFinancialYearEntryForm
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public boolean updatePCFinancialYear(
			PCFinancialYearEntryForm pcFinancialYearEntryForm,ActionErrors errors)
			throws Exception {
		log.info("Start of updatePCFinancialYear of PCFinancialYearHandler");	
		
		PcFinancialYear pcFinancialYear = null;
		PCFinancialYearTO pcFinancialYearTO = new PCFinancialYearTO();		
		List<PCFinancialYearTO> pcFinancialYearTOList=getPCFinancialDetails();
		Iterator<PCFinancialYearTO> pcFinancialYearTOIterator=pcFinancialYearTOList.iterator();
	
		if (pcFinancialYearTO != null) {
			while(pcFinancialYearTOIterator.hasNext()){
				pcFinancialYearTO=pcFinancialYearTOIterator.next();
				if(pcFinancialYearEntryForm.getIsCurrent().equals("true") && pcFinancialYearTO.getIsCurrent().equals("Yes")){					
					//errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_FINANCIAL_YEAR_NOT_CURRENT + " "+ feeFinancialYearTO.getYear()+" "+ CMSConstants.FEE_FINANCIAL_YEAR_ALREADY_STORED));
					if(pcFinancialYearEntryForm.getPcFinancialYear().equals(pcFinancialYearTO.getYear())){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PC_FINANCIAL_YEAR_IS_ALREADY_CURRENT));
						throw new BusinessException();
					}
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PC_FINANCIAL_YEAR_MORETHAN_ONE));
					throw new BusinessException();				
				}
			}
			if (pcFinancialYearEntryForm != null) {
				pcFinancialYearTO.setId(pcFinancialYearEntryForm.getId());
				pcFinancialYearTO.setIsCurrent(pcFinancialYearEntryForm
						.getIsCurrent());
				pcFinancialYearTO.setYear(pcFinancialYearEntryForm
						.getPcFinancialYear());
				pcFinancialYearTO.setModifiedBy(pcFinancialYearEntryForm
						.getUserId());
				pcFinancialYearTO.setCreatedBy(pcFinancialYearEntryForm
						.getUserId());
			}
			pcFinancialYear = PCFinancialYearHelper.getInstance().populateTOtoBOUpdate(pcFinancialYearTO);
			if (iPCFinancialYearTransaction != null && pcFinancialYear != null) {
				return iPCFinancialYearTransaction.updatePcFinancialYear(pcFinancialYear);
			}
		}
		log.info("End of updatePCFinancialYear of PCFinancialYearHandler");
		return false;
	}
	
	/** 
	 * Used to delete PCFinancialYear Details
	 * @param int, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deletePCFinancialYearDetails(int id, String userId)
			throws Exception {
		log.info("Start of deletePCFinancialYearDetails of PCFinancialYearHandler");
		if (iPCFinancialYearTransaction != null) {
			return iPCFinancialYearTransaction.deletePcFinancialYearDetails(
					id, userId);
		}
		log.info("End of deletePCFinancialYearDetails of PCFinancialYearHandler");
		return false;
	}
	
	/** 
	 * Used to reactivate PCFinancialYear	
	 * @param java.lang.String, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reActivatePCFinancialYear(String year, String userId)
			throws Exception {
		log.info("Start of reActivatePCFinancialYear of PCFinancialYearHandler");
		if (iPCFinancialYearTransaction != null) {
			return iPCFinancialYearTransaction.reActivatePcFinancialYear(
					year, userId);
		}
		log.info("End of reActivatePCFinancialYear of PCFinancialYearHandler");
		return false;
	}
}
