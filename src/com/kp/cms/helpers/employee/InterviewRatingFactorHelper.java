	package com.kp.cms.helpers.employee;
	
	import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.employee.InterviewRatingFactor;
import com.kp.cms.forms.employee.InterviewRatingFactorForm;
import com.kp.cms.to.employee.InterviewRatingFactorTO;
	
	public class InterviewRatingFactorHelper {
		private static final Log log = LogFactory.getLog(InterviewRatingFactorHelper.class);
		public static volatile InterviewRatingFactorHelper interviewRatingFactorHelper=null;
		public static InterviewRatingFactorHelper getInstance(){
			if(interviewRatingFactorHelper == null){
				interviewRatingFactorHelper = new InterviewRatingFactorHelper();
				return interviewRatingFactorHelper;
			}
			return interviewRatingFactorHelper;
			
		}
		/**
		 * @param list
		 * @return
		 */
		@SuppressWarnings("null")
		public List<InterviewRatingFactorTO> copyBOToTO(
				List<InterviewRatingFactor> list)throws Exception {
			List<InterviewRatingFactorTO> interviewRatingFactorTOs =new ArrayList<InterviewRatingFactorTO>();
			Iterator<InterviewRatingFactor> ratingFactors=list.iterator();
			while (ratingFactors.hasNext()) {
				InterviewRatingFactor interviewRatingFactor = (InterviewRatingFactor) ratingFactors
						.next();
				InterviewRatingFactorTO factorTO=new InterviewRatingFactorTO();
				if(interviewRatingFactor.getId() != 0){
					factorTO.setId(interviewRatingFactor.getId());
				}
				if(interviewRatingFactor.getRatingFactor() !=null && !interviewRatingFactor.getRatingFactor().isEmpty()){
					factorTO.setRatingFactor(interviewRatingFactor.getRatingFactor());
				}
				if(interviewRatingFactor.getMaxScore() !=null && interviewRatingFactor.getMaxScore() !=0){
					factorTO.setMaxScore(interviewRatingFactor.getMaxScore());
				}
				if(interviewRatingFactor.getDisplayOrder() !=null && interviewRatingFactor.getDisplayOrder() !=0){
					factorTO.setDisplayOrder(interviewRatingFactor.getDisplayOrder());
				}
				if(interviewRatingFactor.getTeaching() && interviewRatingFactor.getTeaching() != null){
					factorTO.setTeaching("Yes");
				}
				if(!interviewRatingFactor.getTeaching() && interviewRatingFactor.getTeaching() !=null){
					factorTO.setTeaching("No");
				}
				interviewRatingFactorTOs.add(factorTO);
			}
			return interviewRatingFactorTOs;
		}
		/**
		 * @param interviewRatingForm
		 * @return
		 */
		public InterviewRatingFactor copyDataFromFormToBO(InterviewRatingFactorForm interviewRatingForm,String mode)throws Exception {
			InterviewRatingFactor factor=new InterviewRatingFactor();
			if(mode.equalsIgnoreCase("Add")){
				factor.setId(interviewRatingForm.getId());
				factor.setRatingFactor(interviewRatingForm.getRatingFactor());
				factor.setMaxScore(interviewRatingForm.getMaxScore());
				factor.setDisplayOrder(interviewRatingForm.getDisplayOrder());
				factor.setTeaching(interviewRatingForm.getTeaching());
				factor.setCreatedBy(interviewRatingForm.getUserId());
				factor.setCreatedDate(new Date());
				factor.setModifiedBy(interviewRatingForm.getUserId());
				factor.setLastModifiedDate(new Date());
				factor.setIsActive(true);
			}else if(mode.equalsIgnoreCase("Edit")){
				factor.setId(interviewRatingForm.getId());
				factor.setRatingFactor(interviewRatingForm.getRatingFactor());
				factor.setMaxScore(interviewRatingForm.getMaxScore());
				factor.setDisplayOrder(interviewRatingForm.getDisplayOrder());
				factor.setTeaching(interviewRatingForm.getTeaching());
				factor.setModifiedBy(interviewRatingForm.getUserId());
				factor.setLastModifiedDate(new Date());
				factor.setIsActive(true);
			}
			return factor;
		}
		/**
		 * @param factor
		 * @return
		 */
		public InterviewRatingFactorTO copyDateFromBOToTO(
				InterviewRatingFactor factor) {
			InterviewRatingFactorTO factorTO=new InterviewRatingFactorTO();
			factorTO.setId(factor.getId());
			factorTO.setRatingFactor(factor.getRatingFactor());
			factorTO.setMaxScore(factor.getMaxScore());
			factorTO.setDisplayOrder(factor.getDisplayOrder());
			if(factor.getTeaching() && factor.getTeaching() !=null){
				factorTO.setTeaching("Yes");
			}
			if(!factor.getTeaching() && factor.getTeaching() !=null){
				factorTO.setTeaching("No");
			}
			return factorTO;
		}
	}
