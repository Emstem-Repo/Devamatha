package com.kp.cms.actions.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.MISComparisionReportForm;
import com.kp.cms.handlers.reports.MISComparisionReportHandler;
import com.kp.cms.to.reports.MISComparisionReportTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class MISComparisionReportAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(MISComparisionReportAction.class);
	private static final String DISCREPANCY_REPORT = "discrepancyReport";
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchMISComparision(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MISComparisionReportForm comparisionReportForm = (MISComparisionReportForm)form;
		comparisionReportForm.setTransactionDate(null);
		HttpSession session = request.getSession(false);
		session.removeAttribute(DISCREPANCY_REPORT);
		
		return mapping.findForward(CMSConstants.MISCOMPARISION_SEARCH);
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
	public ActionForward reportMISComparision(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MISComparisionReportForm comparisionReportForm = (MISComparisionReportForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute(DISCREPANCY_REPORT) == null){
			 ActionErrors errors = comparisionReportForm.validate(mapping, request);
			errors = validateTransactionDate(comparisionReportForm, errors);
			
			if (errors != null && !errors.isEmpty()) {
				log.info("Entered reportMISComparision Action errors size > 0");
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.MISCOMPARISION_SEARCH);
			}else{
				try {
					List<MISComparisionReportTO> discrepancyList = MISComparisionReportHandler.getInstance().getMISComparisionReport(comparisionReportForm);
					if (discrepancyList != null) {
						session.setAttribute(DISCREPANCY_REPORT, discrepancyList);
					}
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					comparisionReportForm.setErrorMessage(msg);
					comparisionReportForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
		}
		return mapping.findForward(CMSConstants.MISCOMPARISION_REPORT);
	}
	
	/**
	 * Method to validate the manually entered date format
	 * @param comparisionReportForm
	 * @param errors
	 */
	private ActionErrors validateTransactionDate(MISComparisionReportForm comparisionReportForm, ActionErrors errors) {
		
		if (comparisionReportForm.getTransactionDate() != null && !StringUtils.isEmpty(comparisionReportForm.getTransactionDate()) && !CommonUtil.isValidDate(comparisionReportForm.getTransactionDate())) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.TRANSACTION_DATE_INVALID));
		}
		return errors;
	}
}