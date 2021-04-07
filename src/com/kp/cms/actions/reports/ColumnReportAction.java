package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.reports.ColumnReportForm;
import com.kp.cms.handlers.reports.ConfigureColumnHandler;
import com.kp.cms.to.reports.ConfigureColumnForReportTO;
import com.kp.cms.to.reports.ReportNameSummaryTO;

@SuppressWarnings("deprecation")
public class ColumnReportAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ColumnReportAction.class);
	
	public ActionForward initSubmitColumnReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Inside of initSubmitColumnReport of ColumnReportAction");
		ColumnReportForm columnForm = (ColumnReportForm)form;
		try {			
		}catch (Exception exception) {
			log.error("Error occured at initSubmitColumnReport of ColumnReportAction",exception);
			String msg = super.handleApplicationException(exception);
			columnForm.setErrorMessage(msg);
			columnForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("End of initSubmitColumnReport of ColumnReportAction");
		return mapping.findForward(CMSConstants.COLUMN_DISPLAY);
	}

	
	public ActionForward submitColumnForReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Inside of submitColumnForReport of ColumnReportAction");
		ColumnReportForm columnForm = (ColumnReportForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = columnForm.validate(mapping, request);
		try{
			setUserId(request, columnForm);
		if (errors.isEmpty()) {
			
			boolean isAdded = ConfigureColumnHandler.getInstance().saveColumnForReport(columnForm);
			if(isAdded){
				ActionMessage message = new ActionMessage("knowledgepro.reports.addSuccess");// Adding success message.
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				columnForm.reset(mapping, request);	
			}else{				
			}
			
		} else {
			saveErrors(request, errors);
		}
		
		}catch (Exception exception) {	
			log.error("Error occured at submitColumnForReport of ColumnReportAction",exception);
			String msg = super.handleApplicationException(exception);
			columnForm.setErrorMessage(msg);
			columnForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of submitColumnForReport of ColumnReportAction");
		return mapping.findForward(CMSConstants.COLUMN_DISPLAY);
	}
	
	/**
	 * Used to add or update the column report
	 */

	public ActionForward initConfigReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initConfigReport of ColumnReportAction");
			ColumnReportForm columnForm = (ColumnReportForm)form;
			try {
				assignListToForm(columnForm);
			} catch (Exception e) {
				log.error("Error occured at initConfigReport of ColumnReportAction",e);
				String msg = super.handleApplicationException(e);
				columnForm.setErrorMessage(msg);
				columnForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("End of initConfigReport of ColumnReportAction");
		return mapping.findForward(CMSConstants.INIT_CONFIG_REPORT);
	}
	
	/**
	 * Used to get all the configReport names.
	 */
	public void assignListToForm(BaseActionForm form) throws Exception
	{
		log.info("Entering into assignListToForm of ColumnReportAction");
		ColumnReportForm columnForm = (ColumnReportForm)form;
		List<ConfigureColumnForReportTO> reportNameList = ConfigureColumnHandler.getInstance().getReportNames();
		if(reportNameList!=null && !reportNameList.isEmpty()){
			columnForm.setReportNameList(reportNameList);
		}
		log.info("Leaving into assignListToForm of ColumnReportAction");
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
	public ActionForward showConfigReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into showConfigReport of ColumnReportAction");
		ColumnReportForm columnForm = (ColumnReportForm)form;
		 ActionErrors errors = columnForm.validate(mapping, request);
		try {
			if(errors.isEmpty()){
			ConfigureColumnHandler.getInstance().getDetailsOnReportName(columnForm);
			}
			else{
				addErrors(request, errors);
				assignListToForm(columnForm);
				return mapping.findForward(CMSConstants.INIT_CONFIG_REPORT);
			}
		} catch (Exception e) {
			log.error("Error occured at showConfigReport of ColumnReportAction",e);
			String msg = super.handleApplicationException(e);
			columnForm.setErrorMessage(msg);
			columnForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into showConfigReport of ColumnReportAction");
		return mapping.findForward(CMSConstants.VIEW_CONFIG_REPORT_DETAILS);
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
	public ActionForward updateConfigReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into updateConfigReport of ColumnReportAction");
		ColumnReportForm columnForm = (ColumnReportForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, columnForm);			
			ConfigureColumnForReportTO reportTO = columnForm.getReportTO();
			//Used to validate the fields
			errors = validateColumnProperties(errors, columnForm);
			if(errors != null && !errors.isEmpty()){
				saveErrors(request, errors);
			}else{
				boolean isUpdated;
				isUpdated = ConfigureColumnHandler.getInstance().updateConfigReport(reportTO, columnForm);
				if(isUpdated){
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.CONFIG_UPDATE_SUCCESS));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.INIT_CONFIG_REPORT);
				}
				else{
					errors = new ActionErrors();
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.CONFIG_UPDATE_FAILURE));
					saveErrors(request, errors);
				}				
			}
		} catch (Exception e) {
			log.error("Error occured at updateConfigReport of ColumnReportAction",e);
			String msg = super.handleApplicationException(e);
			columnForm.setErrorMessage(msg);
			columnForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("Leaving into updateConfigReport of ColumnReportAction");
		return mapping.findForward(CMSConstants.VIEW_CONFIG_REPORT_DETAILS);
	}
	
	/**
	 * Used to validate the form properties
	 */
	
	public ActionErrors validateColumnProperties(ActionErrors errors, ColumnReportForm columnForm)throws Exception{
		ConfigureColumnForReportTO reportTO = columnForm.getReportTO();
		List<ReportNameSummaryTO> detailsList = reportTO.getReportNameSummaryList();
		List<String> positionList = new ArrayList<String>();
		if(detailsList!=null){
			Iterator<ReportNameSummaryTO> it = detailsList.iterator();
			while (it.hasNext()) {
				ReportNameSummaryTO summaryTO = it.next();
				if((summaryTO.getShowColumn().equals(CMSConstants.TRUE))){  
						if(summaryTO.getPosition().trim()==null || summaryTO.getPosition().trim().isEmpty()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.CONFIG_POSITION_REQUIRED));
							return errors;
						}
				}
				if(summaryTO.getShowColumn().trim().equals(CMSConstants.TRUE) && summaryTO.getPosition() != null || !summaryTO.getPosition().isEmpty()){
					boolean isNumeric = StringUtils.isNumeric(summaryTO.getPosition());
					if(!isNumeric){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.CONFIG_POSITION_NUMERIC));
						return errors;
					}			
				}
				if((summaryTO.getShowColumn().equals(CMSConstants.TRUE))){
					if(summaryTO.getPosition() != null && !summaryTO.getPosition().isEmpty()&& positionList.contains(summaryTO.getPosition())){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.CONFIG_POSITION_DUPLICATE));
						return errors;
					}
				}
				if((summaryTO.getShowColumn().equals(CMSConstants.TRUE))){
					if(summaryTO.getPosition() != null && !summaryTO.getPosition().isEmpty()){
							if((Integer.parseInt(summaryTO.getPosition()) == 0)){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.CONFIG_POSITIOND_ZERO));
								return errors;
							}
					}
				}
				if((summaryTO.getShowColumn().equals(CMSConstants.TRUE))){
					if(summaryTO.getPosition() != null && !summaryTO.getPosition().isEmpty()){
						positionList.add(summaryTO.getPosition());
					}
				}
			}
		}
		return errors;
	}
}
