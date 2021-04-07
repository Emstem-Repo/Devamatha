package com.kp.cms.actions.admin;

import java.util.List;

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
import com.kp.cms.forms.admin.TCMasterForm;
import com.kp.cms.handlers.admin.TCMasterHandler;
import com.kp.cms.to.admin.TCNumberTO;
import com.kp.cms.to.admin.TCPrefixTO;

public class TCMasterAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(TCMasterAction.class);

	/**
	 * setting counterList
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         TC_MASTER
	 * @throws Exception
	 */
	public ActionForward initTCMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		TCMasterForm tcMasterForm = (TCMasterForm) form;
		tcMasterForm.resetFields();
		try {
			tcMasterForm.setToCollege("Christ");
			setUserId(request, tcMasterForm);
			setRequestedDataToForm(tcMasterForm,request);
			
		} catch (Exception e) {
			log.error("error initTCMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				tcMasterForm.setErrorMessage(msg);
				tcMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
	
		return mapping.findForward(CMSConstants.TC_MASTER);
	}

	/**
	 * @param tcMasterForm
	 * @param request 
	 * @throws Exception
	 */
	private void setRequestedDataToForm(TCMasterForm tcMasterForm, HttpServletRequest request) throws Exception {
		
		List<TCPrefixTO> tcPrefixList = TCMasterHandler.getInstance().getTcPrefix();
        request.getSession().setAttribute("tcPrefixList", tcPrefixList);
        
		List<TCNumberTO> list=TCMasterHandler.getInstance().getAllTCNumber();
		tcMasterForm.setList(list);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new  counter
	 * @return to mapping TC_MASTER
	 * @throws Exception
	 */
	public ActionForward addTCMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		TCMasterForm tcMasterForm = (TCMasterForm) form;
		tcMasterForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = tcMasterForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRequestedDataToForm(tcMasterForm,request);
				//space should not get added in the table
				return mapping.findForward(CMSConstants.TC_MASTER);
			}
			isAdded = TCMasterHandler.getInstance().addTCMaster(tcMasterForm, "add"); 
			setRequestedDataToForm(tcMasterForm,request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.tc.master.exists", tcMasterForm.getType()+"/"+tcMasterForm.getPrefix()));
			saveErrors(request, errors);
			setRequestedDataToForm(tcMasterForm,request);
			return mapping.findForward(CMSConstants.TC_MASTER);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.TC_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setRequestedDataToForm(tcMasterForm,request);
			return mapping.findForward(CMSConstants.TC_MASTER);
		} catch (Exception e) {
			log.error("error in final submit of disciplinary type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				tcMasterForm.setErrorMessage(msg);
				tcMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.tcmaster.addsuccess", tcMasterForm.getType());
			messages.add("messages", message);
			saveMessages(request, messages);
			tcMasterForm.resetFields();
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.tcmaster.addfailure", tcMasterForm.getType()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.TC_MASTER);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response...this will update tc master
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateTCMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

			TCMasterForm tcMasterForm = (TCMasterForm) form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = tcMasterForm.validate(mapping, request);
			boolean isUpdated = false;
			try {
				if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRequestedDataToForm(tcMasterForm,request);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.TC_MASTER);
			}
				isUpdated = TCMasterHandler.getInstance().addTCMaster(tcMasterForm, "update"); 
				setRequestedDataToForm(tcMasterForm,request);
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.admin.tc.master.exists", tcMasterForm.getType()));
				saveErrors(request, errors);
				setRequestedDataToForm(tcMasterForm,request);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.TC_MASTER);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(CMSConstants.TC_MASTER_EXIST_REACTIVATE));
				saveErrors(request, errors);
				setRequestedDataToForm(tcMasterForm,request);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.TC_MASTER);
			} catch (Exception e) {
				log.error("error in final submit of counter master type page...", e);
				if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				tcMasterForm.setErrorMessage(msg);
				tcMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
				}
			}
			if (isUpdated) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.admin.tcmaster.updatesuccess", tcMasterForm.getType());
				messages.add("messages", message);
				saveMessages(request, messages);
				tcMasterForm.resetFields();
			}
			else
			{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.tcmaster.updatefailure", tcMasterForm.getType()));
				saveErrors(request, errors);
			}
				request.setAttribute(CMSConstants.OPERATION, "add");
				return mapping.findForward(CMSConstants.TC_MASTER);
			}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response...this will delete the Tc master
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteTCMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		TCMasterForm tcMasterForm = (TCMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (tcMasterForm.getId() != 0) {
				int id = tcMasterForm.getId();
				isDeleted = TCMasterHandler.getInstance().deleteTCMaster(id, false, tcMasterForm);
			}
		} catch (Exception e) {
			log.error("error in deleteTCMaster...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				tcMasterForm.setErrorMessage(msg);
				tcMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setRequestedDataToForm(tcMasterForm,request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.tcmaster.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			tcMasterForm.resetFields();
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.tcmaster.deletefailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.TC_MASTER);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the tc master
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateTCMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		TCMasterForm tcMasterForm = (TCMasterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (tcMasterForm.getDuplId() != 0) {
				int id = tcMasterForm.getDuplId();  //setting id for activate
				isActivated = TCMasterHandler.getInstance().deleteTCMaster(id, true, tcMasterForm);
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.TC_MASTER_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setRequestedDataToForm(tcMasterForm,request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.TC_MASTER_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			tcMasterForm.resetFields();
		}
		return mapping.findForward(CMSConstants.TC_MASTER);
	}
	
	public ActionForward initTCMasterForCjc(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		TCMasterForm tcMasterForm = (TCMasterForm) form;
		tcMasterForm.resetFields();
		try 
		{
			tcMasterForm.setToCollege("Cjc");
			setUserId(request, tcMasterForm);
			setRequestedDataToForm(tcMasterForm,request);
		}
		catch (Exception e) 
		{
			log.error("error initTCMaster...", e);
			if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				tcMasterForm.setErrorMessage(msg);
				tcMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else 
			{
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.TC_MASTER);
	}
}
