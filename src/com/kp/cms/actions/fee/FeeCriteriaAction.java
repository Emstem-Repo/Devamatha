package com.kp.cms.actions.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.fee.FeeCriteriaForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.InstituteHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.fee.FeeCriteriaHandler;
import com.kp.cms.handlers.reports.SecondLanguageHandler;
import com.kp.cms.to.fee.FeeCriteriaTO;

public class FeeCriteriaAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(FeeCriteriaAction.class);	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFeeCriteria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			log.info("entered initFeeCriteria..");

			FeeCriteriaForm feeCriteriaForm = (FeeCriteriaForm) form;
			feeCriteriaForm.setNationalities(AdmissionFormHandler.getInstance().getNationalities());
			feeCriteriaForm.setCollegeList(InstituteHandler.getInstance().getInstitute());
			feeCriteriaForm.setUniversityList(UniversityHandler.getInstance().getUniversity());
			feeCriteriaForm.setResidentCategoryList(AdmissionFormHandler.getInstance().getResidentTypes());
			feeCriteriaForm.setAdmittedList(AdmittedThroughHandler.getInstance().getAdmittedThrough());
			feeCriteriaForm.setFeeOptionalGroupMap(FeeCriteriaHandler.getInstance().getFeeAdditionalFeeGroup());
			Map<String,String> secondLanguageMap = SecondLanguageHandler.getInstance().getAllSecondLanguages();
			secondLanguageMap.remove("All");
			feeCriteriaForm.setLanguageMap(secondLanguageMap);
			
			List<FeeCriteriaTO> feeCriteriaList =  FeeCriteriaHandler.getInstance().getFeeCriteria();
			request.setAttribute("feeCriteriaList", feeCriteriaList);
			
			setUserId(request, feeCriteriaForm);
			clear(feeCriteriaForm);
			log.info("exit initFeeCriteria..");

			return mapping.findForward(CMSConstants.INIT_FEE_CRITERIA);
	}
	
	/***
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("deprecation")
	public ActionForward addFeeCriteria(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside updateCourse Action");
		FeeCriteriaForm feeCriteriaForm = (FeeCriteriaForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors = feeCriteriaForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if(( feeCriteriaForm.getAdmittedThroghId() == null || feeCriteriaForm.getAdmittedThroghId().isEmpty())&& (feeCriteriaForm.getLanguage() == null || feeCriteriaForm.getLanguage().trim().isEmpty())){
				errors.add("error", new ActionError("knowledgepro.fee.feecriteria.select.criteria.adm.sec"));
				saveErrors(request, errors);
				List<FeeCriteriaTO> feeCriteriaList =  FeeCriteriaHandler.getInstance().getFeeCriteria();
				request.setAttribute("feeCriteriaList", feeCriteriaList);
				return mapping.findForward(CMSConstants.INIT_FEE_CRITERIA);	
			}
			if( (feeCriteriaForm.getAdmittedThroghId()!= null && !feeCriteriaForm.getAdmittedThroghId().isEmpty()) &&
					(feeCriteriaForm.getInstituteID() == null || feeCriteriaForm.getInstituteID().trim().isEmpty()) &&
					(feeCriteriaForm.getNationalityID() == null || feeCriteriaForm.getNationalityID().trim().isEmpty()) &&
					(feeCriteriaForm.getUniversityId() == null || feeCriteriaForm.getUniversityId().trim().isEmpty()) &&
					(feeCriteriaForm.getResidentCategoryId() == null || feeCriteriaForm.getResidentCategoryId().trim().isEmpty())) {

				errors.add("error", new ActionError("knowledgepro.fee.feecriteria.select.criteria"));
				saveErrors(request, errors);
				List<FeeCriteriaTO> feeCriteriaList =  FeeCriteriaHandler.getInstance().getFeeCriteria();
				request.setAttribute("feeCriteriaList", feeCriteriaList);
				return mapping.findForward(CMSConstants.INIT_FEE_CRITERIA);	
			}
			
			if(feeCriteriaForm.getLanguage()!= null && !feeCriteriaForm.getLanguage().trim().isEmpty() &&
				(feeCriteriaForm.getAdditionalFeeGroup1() == null || feeCriteriaForm.getAdditionalFeeGroup1().isEmpty()) &&
				(feeCriteriaForm.getAdditionalFeeGroup2() == null || feeCriteriaForm.getAdditionalFeeGroup2().isEmpty()) ){
				errors.add("error", new ActionError("knowledgepro.fee.feecriteria.additional.fee.required"));
				saveErrors(request, errors);
				List<FeeCriteriaTO> feeCriteriaList =  FeeCriteriaHandler.getInstance().getFeeCriteria();
				request.setAttribute("feeCriteriaList", feeCriteriaList);
				return mapping.findForward(CMSConstants.INIT_FEE_CRITERIA);	
			}
			
			setUserId(request, feeCriteriaForm);
			isAdded = FeeCriteriaHandler.getInstance().addFeeCriteria(feeCriteriaForm);
			List<FeeCriteriaTO> feeCriteriaList =  FeeCriteriaHandler.getInstance().getFeeCriteria();
			request.setAttribute("feeCriteriaList", feeCriteriaList);
			
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.fee.feecriteria.already.exists"));
			saveErrors(request, errors);
			List<FeeCriteriaTO> feeCriteriaList =  FeeCriteriaHandler.getInstance().getFeeCriteria();
			request.setAttribute("feeCriteriaList", feeCriteriaList);
			return mapping.findForward(CMSConstants.INIT_FEE_CRITERIA);			
		} catch (Exception e) {
			log.error("error in final submit of addFeeCriteria...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				feeCriteriaForm.setErrorMessage(msg);
				feeCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.fee.feecriteria.add.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			clear(feeCriteriaForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.fee.feecriteria.add.failure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addFeeCriteria Action");
		return mapping.findForward(CMSConstants.INIT_FEE_CRITERIA);
	}
		
	/**
	 * 
	 * @param feeCriteriaForm
	 */
	public void clear(FeeCriteriaForm feeCriteriaForm){
		feeCriteriaForm.setInstituteID(null);
		feeCriteriaForm.setNationalityID(null);
		feeCriteriaForm.setResidentCategoryId(null);
		feeCriteriaForm.setAdmittedThroghId(null);
		feeCriteriaForm.setLanguage(null);
		feeCriteriaForm.setUniversityId(null);
		feeCriteriaForm.setAdditionalFeeGroup1(null);
		feeCriteriaForm.setAdditionalFeeGroup2(null);
		feeCriteriaForm.setAdditionalFeeGroup3(null);
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFeeCriteria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("inside Delete Fee Criteria");
		FeeCriteriaForm feeCriteriaForm = (FeeCriteriaForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (feeCriteriaForm.getId() != 0) {
				int id = feeCriteriaForm.getId();
				setUserId(request, feeCriteriaForm); //setting user id for updating last changed details
				isDeleted = FeeCriteriaHandler.getInstance().deleteFeeCriteria(id);
				List<FeeCriteriaTO> feeCriteriaList =  FeeCriteriaHandler.getInstance().getFeeCriteria();
				request.setAttribute("feeCriteriaList", feeCriteriaList);
			}
		} catch (Exception e) {
			log.error("error in delete admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				feeCriteriaForm.setErrorMessage(msg);
				feeCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.fee.fee.criteria.delete.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			clear(feeCriteriaForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.fee.fee.criteria.delete.failure"));
			saveErrors(request, errors);
		}
		log.debug("inside deleteFeeCriteria");
		return mapping.findForward(CMSConstants.INIT_FEE_CRITERIA);
	}	
}
