package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.EligibilityCriteriaForm;
import com.kp.cms.helpers.admin.EligibilityCriteriaHelper;
import com.kp.cms.to.admin.EligibilityCriteriaTO;
import com.kp.cms.transactions.admin.IEligibilityCriteria;
import com.kp.cms.transactionsimpl.admin.EligibilityCriteriaTransactionImpl;

public class EligibilityCriteriaHandler {
	private static volatile EligibilityCriteriaHandler eligibilityCriteriaHandler= null;
	private static final Log log = LogFactory.getLog(EligibilityCriteriaHandler.class);
	
	public static EligibilityCriteriaHandler getInstance() {
		if (eligibilityCriteriaHandler == null) {
			eligibilityCriteriaHandler = new EligibilityCriteriaHandler();
			return eligibilityCriteriaHandler;
		}
		return eligibilityCriteriaHandler;
	}
	
	
	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */
	public boolean addEligibilityCriteria(EligibilityCriteriaForm eligibilityCriteriaForm, String mode) throws Exception {
		IEligibilityCriteria iEligibilityCriteria = EligibilityCriteriaTransactionImpl.getInstance();
		boolean isAdded = false;
		//duplication checking with course & year
		Boolean isDuplicated = iEligibilityCriteria.isCourseYearDuplicated(Integer.parseInt(eligibilityCriteriaForm.getCourseId()), Integer.parseInt(eligibilityCriteriaForm.getYear()), eligibilityCriteriaForm.getId());
		if(isDuplicated){
			throw new DuplicateException();
		}
		
		EligibilityCriteria eligibilityCriteria;
		if(mode.equalsIgnoreCase("Add")){
			eligibilityCriteria = EligibilityCriteriaHelper.getInstance().copyFromFormToBO(eligibilityCriteriaForm);
			    eligibilityCriteria.setCreatedBy(eligibilityCriteriaForm.getUserId());
			    eligibilityCriteria.setModifiedBy(eligibilityCriteriaForm.getUserId());
			    eligibilityCriteria.setCreatedDate(new Date());
			    eligibilityCriteria.setLastModifiedDate(new Date());
		}
		else
		{
			eligibilityCriteria = EligibilityCriteriaHelper.getInstance().copyFormToBOForUpdate(eligibilityCriteriaForm);
			eligibilityCriteria.setModifiedBy(eligibilityCriteriaForm.getUserId());
			eligibilityCriteria.setLastModifiedDate(new Date());
		}
		
		isAdded = iEligibilityCriteria.addCriteria(eligibilityCriteria, mode);
		return isAdded;
	}
	
	/**
	 * 
	 * @param id
	 * @param eligibilityCriteriaForm
	 * @param toDisplay
	 * @return criteriaToList for display
	 * @throws Exception
	 */
	public List<EligibilityCriteriaTO> getEligibilityCriteria(int id, EligibilityCriteriaForm eligibilityCriteriaForm, Boolean toDisplay) throws Exception {
		IEligibilityCriteria iEligibilityCriteria = new EligibilityCriteriaTransactionImpl();
		List<EligibilityCriteria> criteriaList = iEligibilityCriteria.getEligibilityCriteria(id);
		List<EligibilityCriteriaTO> criteriaToList = EligibilityCriteriaHelper.getInstance().copyCriteriaBosToTos(criteriaList, eligibilityCriteriaForm, id, toDisplay);
		return criteriaToList;
	}

	/**
	 * 
	 * @param id
	 * @return true/false
	 * @throws Exception
	 */
	public boolean deleteCriteria(int id, Boolean activate,EligibilityCriteriaForm eligibilityCriteriaForm) throws Exception {
		IEligibilityCriteria iEligibilityCriteria = EligibilityCriteriaTransactionImpl.getInstance();

//		EligibilityCriteria eligibilityCriteria = new EligibilityCriteria();
//		eligibilityCriteria.setId(id);
		boolean result = iEligibilityCriteria.deleteCriteria(id,activate,eligibilityCriteriaForm);
		return result;
	}
	
	
}
