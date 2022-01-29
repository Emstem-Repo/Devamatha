package com.kp.cms.actions.employee;

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
import com.kp.cms.forms.employee.LeaveInitializationForm;
import com.kp.cms.handlers.employee.LeaveInitializationHandler;

public class LeaveInitializationAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(LeaveInitializationAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initLeaveInitialize(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initEmpApplyLeave");
		LeaveInitializationForm leaveInitializationForm = (LeaveInitializationForm) form;
		leaveInitializationForm.resetFields();
		setRequiredDatatoForm(leaveInitializationForm);
		log.info("Exit initEmpApplyLeave");
		
		return mapping.findForward(CMSConstants.INIT_LEAVE_INITIALIZE);
	}

	/**
	 * @param leaveInitializationForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(LeaveInitializationForm leaveInitializationForm) throws Exception{
//			Map<Integer,String> empTypeMap=LeaveInitializationHandler.getInstance().getEmployeeTypesForMonth("");
//			leaveInitializationForm.setEmpTypeMap(empTypeMap);
		
			Map<String,String> monthMap=LeaveInitializationHandler.getInstance().getMonthFromEmployeeType();
			leaveInitializationForm.setMonthMap(monthMap);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveLeaveInitialization(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered LeaveInitializationAction - saveLeaveInitialization");
		
		LeaveInitializationForm leaveInitializationForm = (LeaveInitializationForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = leaveInitializationForm.validate(mapping, request);
		setUserId(request, leaveInitializationForm);
		if (errors.isEmpty()) {
			try {
				boolean isAlreadyExists=LeaveInitializationHandler.getInstance().checkAlreadyExists(leaveInitializationForm);
				if(!isAlreadyExists){
					boolean isSaved=LeaveInitializationHandler.getInstance().saveLeaveInitialization(leaveInitializationForm);
					if (isSaved) {
						ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess"," Leave Initilization");
						messages.add("messages", message);
						saveMessages(request, messages);
						leaveInitializationForm.resetFields();
					}else{
						// failed
						errors.add("error", new ActionError("kknowledgepro.admin.addfailure"," Leave Initilization"));
						saveErrors(request, errors);
					}
				}else{
					errors.add("error", new ActionError("knowledgepro.admission.empty.err.message"," Leave Initilization is Already Happen"));
					saveErrors(request, errors);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				leaveInitializationForm.setErrorMessage(msg);
				leaveInitializationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(leaveInitializationForm);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_LEAVE_INITIALIZE);
		}
		log.info("Entered LeaveInitializationAction - saveLeaveInitializatio");
		return mapping.findForward(CMSConstants.INIT_LEAVE_INITIALIZE);
	}
}
