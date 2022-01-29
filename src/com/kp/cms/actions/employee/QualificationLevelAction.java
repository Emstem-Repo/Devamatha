package com.kp.cms.actions.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.exceptions.DuplicateException1;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.exceptions.ReActivateException1;
import com.kp.cms.forms.employee.EducationMasterForm;
import com.kp.cms.forms.employee.QualificationLevelForm;
import com.kp.cms.handlers.employee.EducationMasterHandler;
import com.kp.cms.handlers.employee.QualificationLevelHandler;
import com.kp.cms.to.employee.QualificationLevelTO;

public class QualificationLevelAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EducationMasterAction.class);
	private static final String QUALIFICATION_ENTRY_OPERATION = "QualificationLevelEntryOperation";
 
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initQualificationLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QualificationLevelForm qualificationLevelForm =(QualificationLevelForm) form;
		try{

			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setQualificationListToRequest(request);
			setUserId(request, qualificationLevelForm);
			initFields(qualificationLevelForm);
		
		} catch (Exception e) {
			log.error("error initEducationMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				qualificationLevelForm.setErrorMessage(msg);
				qualificationLevelForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
	}


	private void initFields(QualificationLevelForm qualificationLevelForm) {
		qualificationLevelForm.setFixedDisplay(false);
		qualificationLevelForm.setName(null);
		qualificationLevelForm.setDisplayOrder(null);
		qualificationLevelForm.setPhdQualification(false);
		}


/**
 * @param request
 * @throws Exception
 */
	private void setQualificationListToRequest(HttpServletRequest request) throws Exception {
	List<QualificationLevelTO> qualificationLevelList=QualificationLevelHandler.getInstance().getQualificationList();
	request.setAttribute("QualificationLevelList", qualificationLevelList);
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward addQualificationLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QualificationLevelForm qualificationLevelForm =(QualificationLevelForm) form;
		ActionMessages messages =new ActionMessages();
		ActionErrors errors = qualificationLevelForm.validate(mapping, request);
		setUserId(request, qualificationLevelForm);
		HttpSession session = request.getSession();
		boolean isAdded = false;
		try{
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
			}
			if(qualificationLevelForm.getName() ==null || qualificationLevelForm.getName().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.required"));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				initFields(qualificationLevelForm);
				return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
			}
			if(qualificationLevelForm.getDisplayOrder()== null || qualificationLevelForm.getDisplayOrder()==0){
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.display.order.required"));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				initFields(qualificationLevelForm);
				return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
			}
			isAdded=QualificationLevelHandler.getInstance().addQualificationLevel(qualificationLevelForm, "Add");
			setQualificationListToRequest(request);
		}catch (DuplicateException e) {
			errors.add("error", new ActionError("knowledgepro.employee.qualification.level.exists"));
			saveErrors(request, errors);
			setQualificationListToRequest(request);
			return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
		}
		catch (ReActivateException e2) {
			errors.add("error", new ActionError(CMSConstants.QUALIFICATION_LEVEL_REACTIVATE));
			saveErrors(request, errors);
			setQualificationListToRequest(request);
			session.setAttribute("REACTIVATEID", qualificationLevelForm.getDuplId());
			session.setAttribute("NAME", qualificationLevelForm.getName());
			session.setAttribute("DISPLAYORDER", qualificationLevelForm.getDisplayOrder());
			session.setAttribute("FIXEDDISPLAY", qualificationLevelForm.isFixedDisplay());
			return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
		} 
		catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				qualificationLevelForm.setErrorMessage(msg);
				qualificationLevelForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.employee.qualification.level.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(qualificationLevelForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.employee.qualification.level.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addQualificationLevel Action");
		return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editQualificationLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QualificationLevelForm qualificationLevelForm =(QualificationLevelForm) form;
		log.debug("inside updateQualificationLevel Action");
		ActionMessages messages = new ActionMessages();
		setUserId(request, qualificationLevelForm);
		
		try{
			if(qualificationLevelForm.getId() !=0){
				int id=qualificationLevelForm.getId();
			QualificationLevelTO qualifiactionTo=QualificationLevelHandler.getInstance().editQualificationLevel(id);
			qualificationLevelForm.setName(qualifiactionTo.getName());
			qualificationLevelForm.setDisplayOrder(qualifiactionTo.getDisplayOrder());
			qualificationLevelForm.setOrigName(qualifiactionTo.getName());
			qualificationLevelForm.setOrigDisplayOrder(qualifiactionTo.getDisplayOrder());
			if(qualifiactionTo.getFixedDisplay().equalsIgnoreCase("Yes")){
				qualificationLevelForm.setFixedDisplay(true);
			}
			if(qualifiactionTo.getFixedDisplay().equalsIgnoreCase("No")){
				qualificationLevelForm.setFixedDisplay(false);
			}
			if(qualifiactionTo.getFixedDisplay().equalsIgnoreCase("Yes")){
				qualificationLevelForm.setOrigFixedDisplay(true);
			}
			if(qualifiactionTo.getFixedDisplay().equalsIgnoreCase("No")){
				qualificationLevelForm.setOrigFixedDisplay(false);
			}
			if(qualifiactionTo.getPhdQualification().equalsIgnoreCase("Yes")){
				qualificationLevelForm.setOrgPhdQualification(true);
			}
			if(qualifiactionTo.getPhdQualification().equalsIgnoreCase("No")){
				qualificationLevelForm.setOrgPhdQualification(false);
			}
			if(qualifiactionTo.getPhdQualification().equalsIgnoreCase("Yes")){
				qualificationLevelForm.setPhdQualification(true);
			}
			if(qualifiactionTo.getPhdQualification().equalsIgnoreCase("No")){
				qualificationLevelForm.setPhdQualification(false);
			}
			}
			setQualificationListToRequest(request);
		}catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				qualificationLevelForm.setErrorMessage(msg);
				qualificationLevelForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		request.setAttribute(QUALIFICATION_ENTRY_OPERATION,
				CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateQualificationLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QualificationLevelForm qualificationLevelForm =(QualificationLevelForm) form;
		log.debug("inside updateQualificationLevel Action");
		ActionMessages messages = new ActionMessages();
		setUserId(request, qualificationLevelForm);
		ActionErrors errors = qualificationLevelForm.validate(mapping, request);
		boolean isUpdate = false;
		try{
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setQualificationListToRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
			}
			if(qualificationLevelForm.getName() ==null || qualificationLevelForm.getName().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.required"));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
			//	initFields(qualificationLevelForm);
				request.setAttribute(QUALIFICATION_ENTRY_OPERATION,
						CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
			}
			if(qualificationLevelForm.getDisplayOrder()== null || qualificationLevelForm.getDisplayOrder()==0){
				errors.add("error", new ActionError("knowledgepro.employee.qualification.level.display.order.required"));
				saveErrors(request, errors);
				setQualificationListToRequest(request);
			//	initFields(qualificationLevelForm);
				request.setAttribute(QUALIFICATION_ENTRY_OPERATION,
						CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
			}
			isUpdate = QualificationLevelHandler.getInstance().updateQualificationLevel(qualificationLevelForm, "Edit");
			setQualificationListToRequest(request);
			
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.qualification.level.exists"));
			saveErrors(request, errors);
			setQualificationListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			request.setAttribute(QUALIFICATION_ENTRY_OPERATION,
					CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
		} 
		catch (ReActivateException e2) {
			errors.add("error", new ActionError(CMSConstants.QUALIFICATION_LEVEL_REACTIVATE));
			saveErrors(request, errors);
			setQualificationListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
		} catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				qualificationLevelForm.setErrorMessage(msg);
				qualificationLevelForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}if (isUpdate) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.employee.qualification.level.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(qualificationLevelForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.employee.qualification.level.updatefailure"));
			saveErrors(request, errors);
		}
		
		request.setAttribute(CMSConstants.OPERATION, "add");
		log.debug("Leaving updateQualificationLevel Action");
		return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteQualificationLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QualificationLevelForm qualificationLevelForm = (QualificationLevelForm) form;
		log.debug("inside deleteEducation");
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		setUserId(request, qualificationLevelForm);
		boolean isDeleted = false;
		try{
			if(qualificationLevelForm.getId() !=0){
				int id=qualificationLevelForm.getId();
				isDeleted=QualificationLevelHandler.getInstance().deleteQualificationLevel(id,false,qualificationLevelForm);
			}
		}catch (Exception e) {
			log.error("error in deleteEducation...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				qualificationLevelForm.setErrorMessage(msg);
				qualificationLevelForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setQualificationListToRequest(request);
	if(isDeleted){
		// success deleted
		ActionMessage message = new ActionMessage("knowledgepro.employee.qualification.level.deletesuccess");
		messages.add("messages", message);
		saveMessages(request, messages);
		initFields(qualificationLevelForm);
	
	}else{

		// failure error message.
		errors.add("error", new ActionError("knowledgepro.employee.qualification.level.deletefailure"));
		saveErrors(request, errors);
	}
	log.debug("Leaving deleteQualificationLevel Action");
	return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateQualificationLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QualificationLevelForm qualificationLevelForm=(QualificationLevelForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		boolean isActivated = false;
		try{
			
			int id=(Integer) session.getAttribute("REACTIVATEID");
			isActivated=QualificationLevelHandler.getInstance().deleteQualificationLevel( id, true, qualificationLevelForm);
		
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.qualification.level.exists"));
			saveErrors(request, errors);
			setQualificationListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
		} catch (DuplicateException1 e1) {
			errors.add("error", new ActionError("knowledgepro.employee.qualification.level.display.order.exists"));
			saveErrors(request, errors);
			setQualificationListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			request.setAttribute(QUALIFICATION_ENTRY_OPERATION,
					CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
		}
		catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.QUALIFICATION_LEVEL_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setQualificationListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.QUALIFICATION_LEVEL_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(qualificationLevelForm);
		}
		log.debug("leaving activateEducation");
		return mapping.findForward(CMSConstants.QUALIFICATION_LEVEL);
		
	}
}
