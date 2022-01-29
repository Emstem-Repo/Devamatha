package com.kp.cms.actions.reports;

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
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.reports.DivisionReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.fee.FeeDivisionHandler;
import com.kp.cms.handlers.reports.DivisionReportHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.fee.FeeDivisionTO;

public class DivisionReportAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ConcessionSlipBookReportAction.class);	
	
	public ActionForward initDivisionReport(ActionMapping mapping,
			   ActionForm form, HttpServletRequest request,
			   HttpServletResponse response) throws Exception {
		DivisionReportForm divForm = (DivisionReportForm) form;
		setDivListToRequest(request);		
		divForm.setDivId(null);
		
		log.debug("Entering initDivisionReport ");
	 	return mapping.findForward(CMSConstants.DIVISION_REPORT_SEARCH);
	}
	
	public void setDivListToRequest(HttpServletRequest request)throws Exception
	{
		List<FeeDivisionTO> feeDivisionList = FeeDivisionHandler.getInstance().getFeeDivisionList();
		request.setAttribute("feeDivisionList", feeDivisionList);
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
	@SuppressWarnings("deprecation")
	public ActionForward submitDivisions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DivisionReportForm divForm = (DivisionReportForm) form;
		HttpSession session = request.getSession(false);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				divForm.setOrganizationName(orgTO.getOrganizationName());
			}				
			
			List<FeeDivisionTO> divisionList = DivisionReportHandler.getInstance().getFeeDivisionWithAccounts(divForm); 
			setDivListToRequest(request);
			session.setAttribute("divisionList", divisionList);
			divForm.setPrint("true");
		} catch (Exception e) {
			if(e instanceof DataNotFoundException) {
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.norecords"));
				saveErrors(request,errors);
				return mapping.findForward(CMSConstants.DIVISION_REPORT_SEARCH);
			}
    		else if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				divForm.setErrorMessage(msg);
				divForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}		
		return mapping.findForward(CMSConstants.DIVISION_REPORT_SEARCH);
	
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
	public ActionForward displayPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			   HttpServletResponse response) throws Exception {
		DivisionReportForm divForm = (DivisionReportForm) form;
		divForm.setPrint("false");
		log.debug("Entering displayPage ");
		return mapping.findForward(CMSConstants.DIVISION_REPORT_RESULT);
	}		

	
}
