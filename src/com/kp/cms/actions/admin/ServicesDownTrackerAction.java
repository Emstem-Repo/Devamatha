package com.kp.cms.actions.admin;

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
import com.kp.cms.forms.admin.MaintenanceAlertForm;
import com.kp.cms.forms.admin.ServicesDownTrackerForm;
import com.kp.cms.forms.exam.ExternalEvaluatorForm;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;
import com.kp.cms.handlers.admin.ServicesDownTrackerHandler;
import com.kp.cms.handlers.exam.ExternalEvaluatorHandler;
import com.kp.cms.to.admin.ServicesDownTrackerTO;
import com.kp.cms.to.exam.ExternalEvaluatorTO;


/**
 * @author dIlIp
 *
 */
public class ServicesDownTrackerAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(MaintenanceAlertAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initServicesDownTracker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			ServicesDownTrackerForm trackerForm=(ServicesDownTrackerForm) form;
			trackerForm.reset();
		try{
			setRequiredDataToForm(request);
			assignListToForm(trackerForm);
			
		}catch (Exception e) {
			log.error("error in editing ServicesDownTracker...", e);
			String msg = super.handleApplicationException(e);
			trackerForm.setErrorMessage(msg);
			trackerForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_SERVICES_DOWN_TRACKER);
	}

	/**
	 * @param form
	 * @throws Exception
	 */
	private void assignListToForm(ActionForm form) throws Exception{
		
		ServicesDownTrackerForm trackerForm=(ServicesDownTrackerForm) form;
		try {
			List<ServicesDownTrackerTO> trackerList = ServicesDownTrackerHandler.getInstance().getTrackerList(trackerForm.getServicesId(), "");
			trackerForm.setServicesDownTrackerTOList(trackerList);
		} catch (Exception e) {
			log.error("Error in assignListToForm of ServicesDownTracker Action",e);
				String msg = super.handleApplicationException(e);
				trackerForm.setErrorMessage(msg);
				trackerForm.setErrorStack(e.getMessage());
			}
		
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(HttpServletRequest request) throws Exception {
		
		Map<Integer, String> servicesMap = ServicesDownTrackerHandler.getInstance().getServicesMap();
		request.setAttribute("servicesMap",servicesMap);
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addServicesDownTracker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServicesDownTrackerForm trackerForm=(ServicesDownTrackerForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, trackerForm);
		String mode="Add";
		try {
			boolean isDuplicate = ServicesDownTrackerHandler.getInstance().duplicateCheck(trackerForm);
			if(!isDuplicate){
				boolean isAdded=true;
				isAdded = ServicesDownTrackerHandler.getInstance().addOrUpdateServicesDownTracker(trackerForm,mode);
				if(isAdded){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.Services.addsuccess"));
					saveMessages(request, messages);
					trackerForm.reset();
				} else {
					errors.add("error", new ActionError("knowledgepro.admin.Services.addfailure"));
					addErrors(request, errors);
					trackerForm.reset();
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.Services.name.exists"));
				addErrors(request, errors);
			}
			assignListToForm(trackerForm);
			setRequiredDataToForm(request);
		} catch (Exception e) {
			log.error("error in addServicesDownTracker...", e);
			String msg = super.handleApplicationException(e);
			trackerForm.setErrorMessage(msg);
			trackerForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_SERVICES_DOWN_TRACKER);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editServicesDownTracker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServicesDownTrackerForm trackerForm=(ServicesDownTrackerForm) form;
		try {
			ServicesDownTrackerHandler.getInstance().editServicesDownTracker(trackerForm);
			setRequiredDataToForm(request);
			request.setAttribute("trackerOperation", "edit");

		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			trackerForm.setErrorMessage(msg);
			trackerForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_SERVICES_DOWN_TRACKER);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateServicesDownTracker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServicesDownTrackerForm trackerForm=(ServicesDownTrackerForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, trackerForm);
		String mode="Update";
		try {
			boolean isDuplicate = ServicesDownTrackerHandler.getInstance().duplicateCheck(trackerForm);
			if(!isDuplicate){
			boolean isAdded=false;
			isAdded = ServicesDownTrackerHandler.getInstance().addOrUpdateServicesDownTracker(trackerForm,mode);
			if(isAdded){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.Services.updatesuccess"));
				saveMessages(request, messages);
				trackerForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.admin.Services.updatefailure"));
				addErrors(request, errors);
				trackerForm.reset();
			}
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.Services.name.exists"));
				addErrors(request, errors);
			}
			assignListToForm(trackerForm);
			setRequiredDataToForm(request);
			
		} catch (Exception e) {
			log.error("error in editing ServicesDownTracker...", e);
			String msg = super.handleApplicationException(e);
			trackerForm.setErrorMessage(msg);
			trackerForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_SERVICES_DOWN_TRACKER);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteServicesDownTracker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServicesDownTrackerForm trackerForm=(ServicesDownTrackerForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, trackerForm);
		String mode="Delete";
		try {
			boolean isDeleted=false;
			isDeleted = ServicesDownTrackerHandler.getInstance().deleteServicesDownTracker(trackerForm,mode);
			if(isDeleted){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.Services.deletesuccess"));
				saveMessages(request, messages);
				trackerForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.admin.Services.deletefailure"));
				addErrors(request, errors);
				trackerForm.reset();
			}
			assignListToForm(trackerForm);
			setRequiredDataToForm(request);
			
		} catch (Exception e) {
			log.error("error in deleting ServicesDownTracker...", e);
			String msg = super.handleApplicationException(e);
			trackerForm.setErrorMessage(msg);
			trackerForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_SERVICES_DOWN_TRACKER);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayAccServices(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ServicesDownTrackerForm trackerForm=(ServicesDownTrackerForm) form;
		try {
			String mode = "services";
			List<ServicesDownTrackerTO> trackerList = ServicesDownTrackerHandler.getInstance().getTrackerList(trackerForm.getServicesId(), mode);
			trackerForm.setServicesDownTrackerTOList(trackerList);
			setRequiredDataToForm(request);
		} catch (Exception e) {
			log.error("Error in assignListToForm of ServicesDownTracker Action",e);
				String msg = super.handleApplicationException(e);
				trackerForm.setErrorMessage(msg);
				trackerForm.setErrorStack(e.getMessage());
			}
		
		return mapping.findForward(CMSConstants.INIT_SERVICES_DOWN_TRACKER);
		
	}
	
	
}
