	package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.employee.InterviewRatingFactor;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.DuplicateException1;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.exceptions.ReActivateException1;
import com.kp.cms.forms.employee.InterviewRatingFactorForm;
import com.kp.cms.helpers.employee.InterviewRatingFactorHelper;
import com.kp.cms.to.employee.InterviewRatingFactorTO;
import com.kp.cms.transactions.employee.IInterviewRatingFactorTransaction;
import com.kp.cms.transactionsimpl.employee.InterviewRatingFactorTxnImpl;
	
	public class InterviewRatingFactorHandler {
		private static final Log log = LogFactory.getLog(InterviewRatingFactorHandler.class);
	public static volatile InterviewRatingFactorHandler interviewRatingFactorHandler=null;
	public static InterviewRatingFactorHandler getInstance(){
		if(interviewRatingFactorHandler == null){
			interviewRatingFactorHandler = new InterviewRatingFactorHandler();
			return interviewRatingFactorHandler;
		}
		return interviewRatingFactorHandler;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<InterviewRatingFactorTO> getInterviewRatingList()throws Exception {
		List<InterviewRatingFactorTO> ratingFactorTOs = null;
		IInterviewRatingFactorTransaction transaction = new InterviewRatingFactorTxnImpl();
		List<InterviewRatingFactor> list = transaction.getInterviewRatingList();
		ratingFactorTOs = InterviewRatingFactorHelper.getInstance().copyBOToTO(list);
		return ratingFactorTOs;
	}
	/**
	 * @param interviewRatingForm
	 * @return
	 * @throws Exception 
	 */
	public boolean addInterviewRatingFactor(
			InterviewRatingFactorForm interviewRatingForm, String mode)
			throws Exception {
		boolean isAdded = false;
		IInterviewRatingFactorTransaction transaction = new InterviewRatingFactorTxnImpl();
		InterviewRatingFactor ratingFactor = null;
		InterviewRatingFactor factor=null;
		InterviewRatingFactor interviewRatingFactor=null;
		if (mode.equalsIgnoreCase("Add")) {
			ratingFactor = transaction.isDuplicated(interviewRatingForm);
			factor=transaction.isDuplicatedDisplayOrder(interviewRatingForm);
			interviewRatingFactor=transaction.isReactivate(interviewRatingForm);
			if(ratingFactor != null && ratingFactor.getIsActive()) {
				throw new DuplicateException();
			} 
			if(factor!=null && factor.getIsActive()){
				throw new DuplicateException();
			}
			if(interviewRatingFactor!=null && interviewRatingFactor.getIsActive()){
				throw new DuplicateException();
			}else if(interviewRatingFactor!=null && !interviewRatingFactor.getIsActive()){
				interviewRatingForm.setDuplId(interviewRatingFactor.getId());
				throw new ReActivateException();
			}
		}else if(mode.equalsIgnoreCase("Edit")){
			ratingFactor = transaction.isDuplicated(interviewRatingForm);
			factor=transaction.isDuplicatedDisplayOrder(interviewRatingForm);
			
			if(ratingFactor != null) {
				if(interviewRatingForm.getId()!=ratingFactor.getId()){
					throw new DuplicateException();
				}
			} 
			if(factor!=null){
				if(interviewRatingForm.getId()!=factor.getId()){
						throw new DuplicateException1();
				}
			}
		}
		ratingFactor = InterviewRatingFactorHelper.getInstance()
				.copyDataFromFormToBO(interviewRatingForm, mode);
		isAdded = transaction.addInterviewRatingFactor(ratingFactor, mode);
		return isAdded;
	}
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public InterviewRatingFactorTO editInterviewRatingFactor(int id) throws Exception {
		InterviewRatingFactorTO ratingFactorTO=null;
		IInterviewRatingFactorTransaction factorTransaction=InterviewRatingFactorTxnImpl.getInstance();
		InterviewRatingFactor factor=factorTransaction.editInterviewRatingFactor(id);
		ratingFactorTO = InterviewRatingFactorHelper.getInstance().copyDateFromBOToTO(factor);
		return ratingFactorTO;
	}
	/**
	 * @param id
	 * @param b
	 * @param ratingFactorForm
	 * @return
	 * @throws Exception 
	 */
	public boolean deleteInterviewRatingFactor(int id, boolean activate,InterviewRatingFactorForm ratingFactorForm) throws Exception {
		boolean isdeleted=false;
		IInterviewRatingFactorTransaction factorTransaction=InterviewRatingFactorTxnImpl.getInstance();
		isdeleted=factorTransaction.deleteInterviewRatingFactor(id, activate, ratingFactorForm);
		return isdeleted;
	}
	/**
	 * @param string
	 * @param ratingFactorForm
	 * @return
	 */
	public boolean reactivateInterviewRatingFactor(String mode,InterviewRatingFactorForm ratingFactorForm)throws Exception {
		boolean isReactivated=false;
		InterviewRatingFactor factor=null;
		InterviewRatingFactor interviewRatingFactor=null;
		IInterviewRatingFactorTransaction factorTransaction=InterviewRatingFactorTxnImpl.getInstance();
		if (mode.equalsIgnoreCase("Add")) {
			factor = factorTransaction.isDuplicated(ratingFactorForm);
			interviewRatingFactor=factorTransaction.isDuplicatedDisplayOrder(ratingFactorForm);
		}
		if(factor != null && factor.getIsActive()) {
			throw new DuplicateException();
		} 
		if(interviewRatingFactor!=null && interviewRatingFactor.getIsActive()){
			throw new DuplicateException1();
		}
		factor = InterviewRatingFactorHelper.getInstance()
				.copyDataFromFormToBO(ratingFactorForm, mode);
		isReactivated = factorTransaction.addInterviewRatingFactor(factor, mode);
		return isReactivated;
	}
	/**
	 * @param interviewRatingFactorForm
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public boolean updateInterviewRatingFactor(InterviewRatingFactorForm interviewRatingFactorForm, String mode)throws Exception{
		boolean isUpdated=false;
		IInterviewRatingFactorTransaction factorTransaction=InterviewRatingFactorTxnImpl.getInstance();
		InterviewRatingFactor factor=null;
		if(mode.equalsIgnoreCase("Edit")){
			factor=factorTransaction.isDuplicatedDisplayOrder(interviewRatingFactorForm);
			if(factor!=null){
				if(factor.getId() == interviewRatingFactorForm.getId()){
					if(!factor.getIsActive()){
						factor = InterviewRatingFactorHelper.getInstance()
						.copyDataFromFormToBO(interviewRatingFactorForm, mode);
						isUpdated = factorTransaction.addInterviewRatingFactor(factor, mode);
					}else{
						throw new DuplicateException1();
					}
				}else{
					throw new DuplicateException1();
				}
			}else{
				factor = InterviewRatingFactorHelper.getInstance()
				.copyDataFromFormToBO(interviewRatingFactorForm, mode);
				isUpdated = factorTransaction.addInterviewRatingFactor(factor, mode);
			}
		}
		return isUpdated;
	}
	}
