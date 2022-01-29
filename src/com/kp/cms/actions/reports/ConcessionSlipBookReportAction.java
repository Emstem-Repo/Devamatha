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
import com.kp.cms.forms.reports.ConcessionSlipBookReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.reports.ConcessionSlipBookReportHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.reports.ConcessionSlipBookReportTO;

public class ConcessionSlipBookReportAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ConcessionSlipBookReportAction.class);	
	
	public ActionForward initConcSlipBooksReport(ActionMapping mapping,
			   ActionForm form, HttpServletRequest request,
			   HttpServletResponse response) throws Exception {
		ConcessionSlipBookReportForm slipBookReportForm = (ConcessionSlipBookReportForm) form;
		slipBookReportForm.setType(null);
		log.debug("Entering initConcSlipBooksReport ");
	 	return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOK_REPORT);
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
	public ActionForward displayConcessionSlipBooks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ConcessionSlipBookReportForm slipBookReportForm = (ConcessionSlipBookReportForm) form;
		HttpSession session = request.getSession(false);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				slipBookReportForm.setOrganizationName(orgTO.getOrganizationName());
			}				
			
			List<ConcessionSlipBookReportTO> concessionBookList = ConcessionSlipBookReportHandler.getInstance().getConcessionSlipBookDetails(slipBookReportForm); 
			
			session.setAttribute("concessionBookList", concessionBookList);
			slipBookReportForm.setPrint("true");
		} catch (Exception e) {
			if(e instanceof DataNotFoundException) {
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.norecords"));
				saveErrors(request,errors);
				return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOK_REPORT);
			}
    		else if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				slipBookReportForm.setErrorMessage(msg);
				slipBookReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}		
		return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOK_REPORT);
	
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
		ConcessionSlipBookReportForm slipBookReportForm = (ConcessionSlipBookReportForm) form;
		slipBookReportForm.setPrint("false");
		log.debug("Entering displayPage ");
		return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOK_REPORT_RESULT);
	}		
}
