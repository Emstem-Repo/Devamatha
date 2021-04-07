package com.kp.cms.actions.exam;

import java.util.HashMap;
import java.util.Iterator;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.SupplementaryFeesForm;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.SupplementaryFeesHandler;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.exam.InternalRedoFeesTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.RegularExamFeesTo;
import com.kp.cms.to.exam.RevaluationExamFeesTo;
import com.kp.cms.to.exam.SupplementaryFeesTo;

@SuppressWarnings("deprecation")
public class SupplementaryFeesAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SupplementaryFeesAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSupplementaryFees(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered into initSupplementaryFees");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		supplementaryFeesForm.resetFields();
		setRequiredDatatoForm(supplementaryFeesForm,request);
		log.info("Exit from initSupplementaryFees");
		
		return mapping.findForward(CMSConstants.INIT_SUPPLEMENTARY_FEES);
	}

	/**
	 * @param supplementaryFeesForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm( SupplementaryFeesForm supplementaryFeesForm,HttpServletRequest request) throws Exception {
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			supplementaryFeesForm.setProgramTypeList(programTypeList);
		}
		if(supplementaryFeesForm.getProgramTypeId()!=null && !supplementaryFeesForm.getProgramTypeId().isEmpty()){
			Map<Integer, String> map = CommonAjaxHandler.getInstance().getClassesByProgramTypeAndAcademicYear1(Integer.parseInt(supplementaryFeesForm.getProgramTypeId()),Integer.parseInt(supplementaryFeesForm.getAcademicYear()));
			request.setAttribute("classesMap",map);
			supplementaryFeesForm.setCourseMap(map);
		}
		List<ProgramTO> programList=ProgramHandler.getInstance().getProgram();
		supplementaryFeesForm.setProgramList(programList);
		List<SupplementaryFeesTo> toList=SupplementaryFeesHandler.getInstance().getActiveList();
		supplementaryFeesForm.setToList(toList);
		List<ReligionSectionTO> listSection=ReligionHandler.getInstance().getReligionSection();
		supplementaryFeesForm.setReligionSectionList(listSection);
	}
	
	
	/**
	 * Performs the add Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward addOrUpdatePublish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into initPublishSupplementary");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = supplementaryFeesForm.validate(mapping, request);

		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, supplementaryFeesForm);
			isPublishSupAppAdded =SupplementaryFeesHandler.getInstance().addOrUpdate(supplementaryFeesForm);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.supplementaryFees.program.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_SUPPLEMENTARY_FEES);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.supplementaryFees.program.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_SUPPLEMENTARY_FEES);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				supplementaryFeesForm.setErrorMessage(msg);
				supplementaryFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isPublishSupAppAdded) {
				if(supplementaryFeesForm.getMode().equalsIgnoreCase("add")){
					ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess","Supplementary Fees");
					messages.add("messages", message);
				}
				else{
					ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Supplementary Fees");
					messages.add("messages", message);
				}
				saveMessages(request, messages);
				supplementaryFeesForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Supplementary Fees"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoForm(supplementaryFeesForm,request);
		return mapping.findForward(CMSConstants.INIT_SUPPLEMENTARY_FEES);
	}
	
	/**
	 * Performs the add Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward deleteOrReactivatePublish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into initPublishSupplementary");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = supplementaryFeesForm.validate(mapping, request);

		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, supplementaryFeesForm);
			isPublishSupAppAdded =SupplementaryFeesHandler.getInstance().deleteOrReactivate(supplementaryFeesForm.getId(),supplementaryFeesForm.getMode(),supplementaryFeesForm.getUserId());
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.supplementaryFees.program.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_SUPPLEMENTARY_FEES);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.supplementaryFees.program.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_SUPPLEMENTARY_FEES);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				supplementaryFeesForm.setErrorMessage(msg);
				supplementaryFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isPublishSupAppAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.deletesuccess","Supplementary Fees");
				messages.add("messages", message);
				saveMessages(request, messages);
				supplementaryFeesForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Supplementary Fees"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoForm(supplementaryFeesForm,request);
		return mapping.findForward(CMSConstants.INIT_SUPPLEMENTARY_FEES);
	}
	
	public ActionForward initRegularFees(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered into initSupplementaryFees");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		supplementaryFeesForm.resetFields();
		setRequiredDatatoFormRegular(supplementaryFeesForm,request);
		log.info("Exit from initSupplementaryFees");
		
		return mapping.findForward(CMSConstants.INIT_REGULAR_FEES);
	}
	private void setRequiredDatatoFormRegular( SupplementaryFeesForm supplementaryFeesForm,HttpServletRequest request) throws Exception {

		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			supplementaryFeesForm.setProgramTypeList(programTypeList);
		}
		if(supplementaryFeesForm.getProgramTypeId()!=null && !supplementaryFeesForm.getProgramTypeId().isEmpty()){
			Map<Integer, String> map = CommonAjaxHandler.getInstance().getClassesByProgramTypeAndAcademicYear1(Integer.parseInt(supplementaryFeesForm.getProgramTypeId()),Integer.parseInt(supplementaryFeesForm.getAcademicYear()));
			request.setAttribute("classesMap",map);
			supplementaryFeesForm.setCourseMap(map);
		}
		List<RegularExamFeesTo> toList=SupplementaryFeesHandler.getInstance().getActiveListRegular();
		supplementaryFeesForm.setRegularExamToList(toList);
		List<ReligionSectionTO> listSection=ReligionHandler.getInstance().getReligionSection();
		supplementaryFeesForm.setReligionSectionList(listSection);
	}
	
	public ActionForward addOrUpdatePublishRegular(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into initPublishSupplementary");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = supplementaryFeesForm.validate(mapping, request);

		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, supplementaryFeesForm);
			isPublishSupAppAdded =SupplementaryFeesHandler.getInstance().addOrUpdateRegularFee(supplementaryFeesForm);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.regular.fees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoFormRegular(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_REGULAR_FEES);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.regular.fees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoFormRegular(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_REGULAR_FEES);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				supplementaryFeesForm.setErrorMessage(msg);
				supplementaryFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isPublishSupAppAdded) {
				if(supplementaryFeesForm.getMode().equalsIgnoreCase("add")){
					ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess","Regular Fees");
					messages.add("messages", message);
				}
				else{
					ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Regular Fees");
					messages.add("messages", message);
				}
				saveMessages(request, messages);
				supplementaryFeesForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Regular Fees"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoFormRegular(supplementaryFeesForm,request);
		return mapping.findForward(CMSConstants.INIT_REGULAR_FEES);
	}
	
	public ActionForward deleteOrReactivatePublishRegular(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into initPublishSupplementary");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = supplementaryFeesForm.validate(mapping, request);

		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, supplementaryFeesForm);
			isPublishSupAppAdded =SupplementaryFeesHandler.getInstance().deleteOrReactivateRegular(supplementaryFeesForm.getId(),supplementaryFeesForm.getMode(),supplementaryFeesForm.getUserId());
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.regular.fees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoFormRegular(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_REGULAR_FEES);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.regular.fees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoFormRegular(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_REGULAR_FEES);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				supplementaryFeesForm.setErrorMessage(msg);
				supplementaryFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isPublishSupAppAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.deletesuccess","Regular Fees");
				messages.add("messages", message);
				saveMessages(request, messages);
				supplementaryFeesForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Regular Fees"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoFormRegular(supplementaryFeesForm,request);
		return mapping.findForward(CMSConstants.INIT_REGULAR_FEES);
	}
	
	//revaluation fees
	public ActionForward initRevaluationFees(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//this one for I18 App
		//request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.SIMPLIFIED_CHINESE);
		
		log.info("Entered into initSupplementaryFees");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		supplementaryFeesForm.resetFields();
		setRequiredDatatoFormRevaluation(supplementaryFeesForm,request);
		log.info("Exit from initSupplementaryFees");
		
		return mapping.findForward(CMSConstants.INIT_REVALUATION_FEES);
	}
	private void setRequiredDatatoFormRevaluation( SupplementaryFeesForm supplementaryFeesForm,HttpServletRequest request) throws Exception {

		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			supplementaryFeesForm.setProgramTypeList(programTypeList);
		}
		Map<Integer,String> courseMap = new HashMap<Integer,String>();
		if(supplementaryFeesForm.getProgramTypeId()!=null && !supplementaryFeesForm.getProgramTypeId().isEmpty()){
			List<KeyValueTO> list = CommonAjaxHandler.getInstance().getCoursesByProgramTypes1(supplementaryFeesForm.getProgramTypeId());
			if(list!=null && !list.isEmpty()){
				Iterator<KeyValueTO> itr=list.iterator();
				while (itr.hasNext()) {
					KeyValueTO keyValueTO = (KeyValueTO) itr.next();
					courseMap.put(keyValueTO.getId(),keyValueTO.getDisplay());
				}
			}
			request.setAttribute("coursesMap",courseMap);
			supplementaryFeesForm.setCourseMap(courseMap);
		}
		List<RevaluationExamFeesTo> toList=SupplementaryFeesHandler.getInstance().getActiveListRevaluation();
		supplementaryFeesForm.setRevaluationExamToList(toList);
	}
	
	public ActionForward addOrUpdatePublishRevaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into initPublishSupplementary");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = supplementaryFeesForm.validate(mapping, request);

		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, supplementaryFeesForm);
			isPublishSupAppAdded =SupplementaryFeesHandler.getInstance().addOrUpdateRevaluationFee(supplementaryFeesForm);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.revaluation.fees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoFormRegular(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_REVALUATION_FEES);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.revaluation.fees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoFormRegular(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_REVALUATION_FEES);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				supplementaryFeesForm.setErrorMessage(msg);
				supplementaryFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isPublishSupAppAdded) {
				if(supplementaryFeesForm.getMode().equalsIgnoreCase("add")){
					ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess","Revaluation Fees");
					messages.add("messages", message);
				}
				else{
					ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Revaluation Fees");
					messages.add("messages", message);
				}
				saveMessages(request, messages);
				supplementaryFeesForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Revaluation Fees"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoFormRevaluation(supplementaryFeesForm,request);
		return mapping.findForward(CMSConstants.INIT_REVALUATION_FEES);
	}
	public ActionForward deleteOrReactivatePublishRevaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into initPublishSupplementary");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = supplementaryFeesForm.validate(mapping, request);

		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, supplementaryFeesForm);
			isPublishSupAppAdded =SupplementaryFeesHandler.getInstance().deleteOrReactivateRevaluation(supplementaryFeesForm.getId(),supplementaryFeesForm.getMode(),supplementaryFeesForm.getUserId());
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.revaluation.fees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoFormRegular(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_REVALUATION_FEES);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.revaluation.fees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoFormRegular(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_REVALUATION_FEES);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				supplementaryFeesForm.setErrorMessage(msg);
				supplementaryFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isPublishSupAppAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.deletesuccess","Revaluation Fees");
				messages.add("messages", message);
				saveMessages(request, messages);
				supplementaryFeesForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Revaluation Fees"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoFormRevaluation(supplementaryFeesForm,request);
		return mapping.findForward(CMSConstants.INIT_REVALUATION_FEES);
	}
	
	/**
	 * For storing details of Internal Redo
	 * @author Arun Sudhakaran
	 */
	public ActionForward initInternalRedoFees(ActionMapping mapping, 
											  ActionForm form,
											  HttpServletRequest request,
											  HttpServletResponse response) throws Exception {
		
		log.info("Entered into initSupplementaryFees");
		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		supplementaryFeesForm.resetFields();
		setRequiredDatatoFormForInternalRedo(supplementaryFeesForm,request);
		log.info("Exit from initSupplementaryFees");
		
		return mapping.findForward(CMSConstants.INIT_INTERNAL_REDO_FEES);
	}
	
	private void setRequiredDatatoFormForInternalRedo( SupplementaryFeesForm supplementaryFeesForm,HttpServletRequest request) throws Exception {
		
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			supplementaryFeesForm.setProgramTypeList(programTypeList);
		}

		if(supplementaryFeesForm.getProgramTypeId()!=null && !supplementaryFeesForm.getProgramTypeId().isEmpty()){
			Map<Integer, String> map = CommonAjaxHandler.getInstance().getClassesByProgramTypeAndAcademicYear1(Integer.parseInt(supplementaryFeesForm.getProgramTypeId()),Integer.parseInt(supplementaryFeesForm.getAcademicYear()));
			request.setAttribute("classesMap",map);
			supplementaryFeesForm.setCourseMap(map);
		}
		List<ProgramTO> programList=ProgramHandler.getInstance().getProgram();
		supplementaryFeesForm.setProgramList(programList);
		List<InternalRedoFeesTO> toList=SupplementaryFeesHandler.getInstance().getActiveListInternalRedo();
		supplementaryFeesForm.setInternalRedoTOList(toList);
		List<ReligionSectionTO> listSection=ReligionHandler.getInstance().getReligionSection();
		supplementaryFeesForm.setReligionSectionList(listSection);
	}
	
	public ActionForward addOrUpdateInternalRedoFees(ActionMapping mapping,
													 ActionForm form,
													 HttpServletRequest request,
													 HttpServletResponse response) throws Exception {

		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = supplementaryFeesForm.validate(mapping, request);

		boolean isAddedUpdated = false;
		
		if (errors.isEmpty()) {
			try{
				setUserId(request, supplementaryFeesForm);
				isAddedUpdated = SupplementaryFeesHandler.getInstance().addOrUpdateInternalRedoFee(supplementaryFeesForm);
			}
			catch (Exception e) {
				
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.internalRedoFees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_INTERNAL_REDO_FEES);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.internalRedoFees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_INTERNAL_REDO_FEES);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				supplementaryFeesForm.setErrorMessage(msg);
				supplementaryFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isAddedUpdated) {
				if(supplementaryFeesForm.getMode().equalsIgnoreCase("add")){
					ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess","Internal Redo Fees");
					messages.add("messages", message);
				}
				else{
					ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Internal Redo Fees");
					messages.add("messages", message);
				}
				saveMessages(request, messages);
				supplementaryFeesForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Internal Redo Fees"));
				saveErrors(request, errors);
			}
		}
		else {
			saveErrors(request, errors);
		}
		setRequiredDatatoFormForInternalRedo(supplementaryFeesForm,request);
		return mapping.findForward(CMSConstants.INIT_INTERNAL_REDO_FEES);
	}
	
	public ActionForward deleteOrReactivateInternalRedoFee(ActionMapping mapping, 
														   ActionForm form,
														   HttpServletRequest request, 
														   HttpServletResponse response) throws Exception {

		SupplementaryFeesForm supplementaryFeesForm = (SupplementaryFeesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = supplementaryFeesForm.validate(mapping, request);

		boolean isAddedUpdated = false;
		
		if (errors.isEmpty()) {
			try{
				setUserId(request, supplementaryFeesForm);
				isAddedUpdated =SupplementaryFeesHandler.getInstance().deleteOrReactivateInternalRedoFees(supplementaryFeesForm.getId(),supplementaryFeesForm.getMode(),supplementaryFeesForm.getUserId());
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.internalRedoFees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_INTERNAL_REDO_FEES);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.internalRedoFees.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(supplementaryFeesForm,request);
					return mapping.findForward(CMSConstants.INIT_INTERNAL_REDO_FEES);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				supplementaryFeesForm.setErrorMessage(msg);
				supplementaryFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isAddedUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.deletesuccess","Internal Redo Fees");
				messages.add("messages", message);
				saveMessages(request, messages);
				supplementaryFeesForm.resetFields();
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.interviewdefinition.deletefailure","Internal Redo Fees"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoFormForInternalRedo(supplementaryFeesForm,request);
		return mapping.findForward(CMSConstants.INIT_INTERNAL_REDO_FEES);
	}
}
