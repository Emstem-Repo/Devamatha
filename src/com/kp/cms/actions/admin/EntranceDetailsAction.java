package com.kp.cms.actions.admin;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.EntranceDetailsForm;
import com.kp.cms.handlers.admin.EntranceDetailsHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.ProgramTypeTO;

@SuppressWarnings("deprecation")

public class EntranceDetailsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EntranceDetailsAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will get all the details from  entrance table 
	 * @return
	 * @throws Exception
	 */

	public ActionForward initEntranceDetails(ActionMapping mapping, ActionForm form, 
										HttpServletRequest request,	HttpServletResponse response) throws Exception {
		EntranceDetailsForm enForm = (EntranceDetailsForm) form;
		initFields(enForm);

		try {
			final String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
			setUserId(request, enForm);  //setting userID for updating last changed details
		} catch (Exception e) {
			log.error("error submit entrance page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				enForm.setErrorMessage(msg);
				enForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);

	}
	
	/**
	 * method used for adding entrance data to table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward addEntranceDeatils(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		EntranceDetailsForm enForm = (EntranceDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = enForm.validate(mapping, request);
		enForm.setId(0);
		boolean isAdded = false;
		try {

			if (!errors.isEmpty()) {
				setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
				setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
			}
			
			isAdded = EntranceDetailsHandler.getInstance().addEntranceDetails(enForm, "add"); 

			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.entrance.addexist"));
			saveErrors(request, errors);
			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
			return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.ENTRANCE_DETAILS_EXIST_REACTIVATE));
			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
		} catch (Exception e) {
			log.error("error in update addEntranceDeatils...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				enForm.setErrorMessage(msg);
				enForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.entrance.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(enForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.entrance.addfailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);

	}

	/**
	 * method used for updating entrance details data to table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward updateEntranceDeatils(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		EntranceDetailsForm enForm = (EntranceDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = enForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			
			if(isCancelled(request)){
				setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
				setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
				setRequiredDataToForm(enForm, request);
				setprogramMapToRequest(request, enForm);				
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
				setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
				setprogramMapToRequest(request, enForm);				
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
			}
			
			isUpdated = EntranceDetailsHandler.getInstance().addEntranceDetails(enForm, "edit"); 

			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.entrance.addexist"));
			saveErrors(request, errors);
			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
			setprogramMapToRequest(request, enForm);				
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.ENTRANCE_DETAILS_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
			setprogramMapToRequest(request, enForm);				
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
		} catch (Exception e) {
			log.error("error in update addEntranceDeatils...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				enForm.setErrorMessage(msg);
				enForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.entrance.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(enForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.entrance.updatefailure"));
			saveErrors(request, errors);
		}
		request.setAttribute(CMSConstants.OPERATION, "add");
		
		return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);

	}		
	
	/**
	 * 
	 * @param enForm
	 */
	public void initFields(EntranceDetailsForm enForm) {
		enForm.setProgramTypeId(null);
		enForm.setProgramId(null);
		enForm.setName(null);
	}
	
	/**
	 *setting programtype list to request for selecting program type from combo 
	 * @param request
	 * @throws Exception
	 */
	
	public void setProgramtypelist(HttpServletRequest request) throws Exception {
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		if (!programTypeList.isEmpty()) {
			request.setAttribute("programTypeList", programTypeList);
		} else {
			log.error("No records found :: List is empty");
		}
	}
	
	/**
	 * setting enDetailsList list to request for UI display 
	 * @param request
	 * @throws Exception
	 */
	public void setEntranceDetailsToRequest(HttpServletRequest request) throws Exception {
		List<EntrancedetailsTO> enDetailsList = EntranceDetailsHandler.getInstance().getEntranceDeatils();
		request.setAttribute("enDetailsList", enDetailsList);
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
	public ActionForward editEntranceDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {

		EntranceDetailsForm entranceDetailsForm = (EntranceDetailsForm) form;
		setRequiredDataToForm(entranceDetailsForm, request);
		request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
	}

	/**
	 * 
	 * @param enForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(EntranceDetailsForm enForm, HttpServletRequest request) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		List<EntrancedetailsTO> entranceList = EntranceDetailsHandler.getInstance().getEntranceDeatilsByid(id);
		Iterator<EntrancedetailsTO> enIt = entranceList.iterator();

		setProgramtypelist(request);
		setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display

		while (enIt.hasNext()) {
			EntrancedetailsTO entrancedetailsTO = (EntrancedetailsTO) enIt.next();
			int progTypeId = entrancedetailsTO.getProgramTO().getProgramTypeTo().getProgramTypeId();
			enForm.setProgramTypeId(Integer.toString(progTypeId));
			enForm.setProgramId(Integer.toString(entrancedetailsTO.getProgramTO().getId()));
			enForm.setName(entrancedetailsTO.getName());
			// setting to check the duplication
		}

		Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(enForm.getProgramTypeId()));
		request.setAttribute("programMap", programMap);
		request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
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
	public ActionForward deleteEntranceDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		EntranceDetailsForm enForm  = (EntranceDetailsForm) form;
		int id = enForm.getId();
		boolean isEntranceDeleted = EntranceDetailsHandler.getInstance().deleteEntranceDetails(id, enForm, false);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
		if (isEntranceDeleted) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.entrance.name.deletesuccess");// Adding added message.
			messages.add("messages", message);
			saveMessages(request, messages);
			request.setAttribute("Update", "Update");
			initFields(enForm);
		} else {
			errors.add("error", new ActionError("knowledgepro.admin.entrance.name.deletefail"));// Adding failure message
			saveErrors(request, errors);
		}
		}catch (Exception e) {
			log.error("error in final submit of Program type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				enForm.setErrorMessage(msg);
				enForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the remark type
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateEntranceDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		EntranceDetailsForm enForm = (EntranceDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (enForm.getDuplId() != 0) {
				int id = enForm.getDuplId();  //setting id for activate
				isActivated = EntranceDetailsHandler.getInstance().deleteEntranceDetails(id, enForm, true); 
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.ENTRANCE_DETAILS_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
		setEntranceDetailsToRequest(request);  //setting entranceDetails list to request for UI display
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.ENTRANCE_DETAILS_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		return mapping.findForward(CMSConstants.ENTRANCE_DETAILS_ENTRY);
	}


	/**
	 * 
	 * @return This method sets propgram map to request for setting in edit
	 *         option
	 * @throws Exception
	 */

	public void setprogramMapToRequest(HttpServletRequest request, EntranceDetailsForm enForm) {
		if (enForm.getProgramTypeId() != null && (!enForm.getProgramTypeId().isEmpty())) {
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(enForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}
	}

	
}
