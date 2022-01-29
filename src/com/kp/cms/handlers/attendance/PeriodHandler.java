package com.kp.cms.handlers.attendance;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.attendance.PeriodForm;
import com.kp.cms.helpers.attendance.PeriodHelper;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.transactions.attandance.IPeriodTransaction;
import com.kp.cms.transactionsimpl.attendance.PeriodTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * 
 * @author
 *
 */
public class PeriodHandler {
	private static Log log = LogFactory.getLog(PeriodHandler.class);
	public static volatile PeriodHandler periodHandler = null;
	public static PeriodHandler getInstance() {
		if (periodHandler == null) {
			periodHandler = new PeriodHandler();
			return periodHandler;
		}
		return periodHandler;
	}
	

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */
	public boolean addPeriod(PeriodForm periodForm, String mode) throws Exception {
		log.debug("inside handler addPeriod");
		IPeriodTransaction iPeriodTransaction = PeriodTransactionImpl.getInstance();
		boolean isAdded = false;
		Boolean isDuplicated = false;
		String selectedClass[] = periodForm.getSelectedClasses();	
		
		String startMins; 
		String endMins;
		if(periodForm.getStartMins() != null && !periodForm.getStartMins().isEmpty()){
			startMins = periodForm.getStartMins();
		}
		else{
			startMins = "00";
		}
		if(periodForm.getEndMins() != null && !periodForm.getEndMins().isEmpty()){
			endMins = periodForm.getEndMins();
		}
		else{
			endMins = "00";
		}			
		
		//duplication checking.. taking each class and checking the duplication
		for (int x = 0; x < selectedClass.length; x++){
			isDuplicated = iPeriodTransaction.isClassAndPeriodNameDuplicated(Integer.parseInt(selectedClass[x]), periodForm.getPeriodName(), periodForm.getId());
			if(isDuplicated){
				throw new DuplicateException();
			}
			isDuplicated = iPeriodTransaction.isClassAndPeriodDuplicated(Integer.parseInt(selectedClass[x]), CommonUtil.getTime(periodForm.getStartHours(), startMins), CommonUtil.getTime(periodForm.getEndHours(), endMins), periodForm.getId());			
			if(isDuplicated){
				throw new DuplicateException();
			}			
		}
		
		
		List<Period> PeriodList = PeriodHelper.getInstance().copyFromFormToBO(periodForm); 
		isAdded = iPeriodTransaction.addPeriod(PeriodList, mode);
		log.debug("leaving handler addPeriod");
		return isAdded;
	}
	

	/**
	 * getting period list based on ID
	 * @param id
	 * @param periodForm
	 * @return list of PeriodTO
	 * @throws Exception
	 */
	public List<PeriodTO> getPeriod(int id, PeriodForm periodForm) throws Exception {
		log.debug("handler. inside getPeriod");
		IPeriodTransaction iPeriodTransaction = new PeriodTransactionImpl();
		List<Period> periodList = iPeriodTransaction.getPeriod(id);
		List<PeriodTO> periodToList = PeriodHelper.getInstance().copyPeriodBOToTO(periodList);
		log.debug("handler. leaving getPeriod");
		return periodToList;
	}
	

	/**
	 * getting all the period for UI display
	 * @param id
	 * @param periodForm
	 * @return list of PeriodTO
	 * @throws Exception
	 */
	public List<PeriodTO> getAllPeriod(PeriodForm periodForm) throws Exception {
		log.debug("handler. inside getAllPeriod");
		IPeriodTransaction iPeriodTransaction = new PeriodTransactionImpl();
		List<Period> periodList = iPeriodTransaction.getAllPeriod(periodForm.getYear());
		List<PeriodTO> periodToList = PeriodHelper.getInstance().copyPeriodBOToTO(periodList);
		log.debug("handler. leaving getAllPeriod");
		return periodToList;
	}	
	
	/**
	 * delete period
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deletePeriod(int id,Boolean activate,PeriodForm periodForm) throws Exception {
		IPeriodTransaction iPeriodTransaction = PeriodTransactionImpl.getInstance();
//		Period period = new Period();
//		period.setId(id);
		boolean result = iPeriodTransaction.deletePeriod(id,false,periodForm);
		return result;
	}

	
}
