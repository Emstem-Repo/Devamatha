package com.kp.cms.actions.reports;

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
import com.kp.cms.forms.reports.ListofRollRegNoForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.ListofRollRegNoHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;

@SuppressWarnings("deprecation")
public class ListofRollRegNoAction extends BaseDispatchAction{

	private static final Log log = LogFactory.getLog(ListofRollRegNoAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initListofRollRegNo(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		ListofRollRegNoForm regNoForm = (ListofRollRegNoForm) form;
		ActionMessages messages = new ActionMessages();
		regNoForm.reset(mapping, request);
		regNoForm.setTotalstudentList(null);
		regNoForm.setStudentListWithClass(null);
		try {
			setProgramTypeListToRequest(request);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			regNoForm.setErrorMessage(msg);
			regNoForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.LIST_ROLL_REG_NO);
		
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
	public ActionForward searchRollRegNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ListofRollRegNoForm regNoForm = (ListofRollRegNoForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = regNoForm.validate(mapping, request);
		if(regNoForm.getTotalstudentList() == null || regNoForm.getTotalstudentList().size() == 0){
			try {
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					setProgramTypeListToRequest(request);
					return mapping.findForward(CMSConstants.LIST_ROLL_REG_NO);
				}
				
				//Required to get the organization name
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();					
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					regNoForm.setOrganizationName(orgTO.getOrganizationName());
				}
				
				ListofRollRegNoHandler.getInstance().getListofRegNoRollNo(regNoForm);
			} catch (Exception e) {
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					regNoForm.setErrorMessage(msg);
					regNoForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		}
		
		return mapping.findForward(CMSConstants.SHOW_LIST_ROLL_REG_NO);
	
	}

	
	/**
	 * 
	 * @param request
	 *            This method sets the program type list to Request useful in
	 *            populating in program type selection.
	 * @throws Exception
	 */
	public void setProgramTypeListToRequest(HttpServletRequest request)	throws Exception {
		log.debug("inside setProgramTypeListToRequest");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.debug("leaving setProgramTypeListToRequest");
	}
	
	
}
