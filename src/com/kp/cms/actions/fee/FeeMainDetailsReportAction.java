package com.kp.cms.actions.fee;

import java.util.Calendar;
import java.util.Date;
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
import com.kp.cms.forms.fee.FeeMainDetailsReportForm;
import com.kp.cms.handlers.fee.FeeMainDetailsReportHandler;
import com.kp.cms.to.fee.FeeMainDetailsTO;
import com.kp.cms.utilities.CommonUtil;

public class FeeMainDetailsReportAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(FeeMainDetailsReportAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFeeMainDetailsReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered FeeMainDetailsReportAction input");
		//pcAccountHeadsForm.clear();
		log.info("Leaving FeeMainDetailsReportAction input");
		return mapping.findForward(CMSConstants.INIT_FEEMAIN_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFeeMainDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered FeeMainDetailsReportAction input");
		//pcAccountHeadsForm.clear();
		log.info("Leaving FeeMainDetailsReportAction input");
		return mapping.findForward(CMSConstants.INIT_FEEMAIN_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFeeMainDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitAttendSummaryReport..");	
		FeeMainDetailsReportForm feeMainDetailsReportForm = (FeeMainDetailsReportForm)form;
		 ActionErrors errors = feeMainDetailsReportForm.validate(mapping, request);
		HttpSession session=request.getSession();
		validateDate(feeMainDetailsReportForm, errors,request);
		if (errors.isEmpty()) {	
			try {
				List<FeeMainDetailsTO> feeMainDetailsTOList=FeeMainDetailsReportHandler.getInstance().getFeeMainDetails(feeMainDetailsReportForm);
			     session.setAttribute("feeMainList", feeMainDetailsTOList);
			}catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				feeMainDetailsReportForm.setErrorMessage(msg);
				feeMainDetailsReportForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
					throw e;
			}
		} else {
			addErrors(request, errors);
			//feeMainDetailsReportForm.reset();
			return mapping.findForward(CMSConstants.INIT_FEEMAIN_REPORT);
		}
		log.info("Exit getFeeMainDetails..");
		return mapping.findForward(CMSConstants.FEEMAIN_DETAILS_RESULT);
	}
	/**
	 * @param feeMainDetailsReportForm
	 * @param actionErrors
	 * @param request
	 */
	public void validateDate(FeeMainDetailsReportForm feeMainDetailsReportForm,
			ActionErrors actionErrors,HttpServletRequest request) {
		log.info("entering into validateDate of HolidaysDetailsAction class.");
		ActionError message=null;
		if (feeMainDetailsReportForm.getStartDate() != null
				&& feeMainDetailsReportForm.getStartDate().length() != 0
				&& feeMainDetailsReportForm.getEndDate() != null
				&& feeMainDetailsReportForm.getEndDate().length() != 0) {
			Date startDate = CommonUtil.ConvertStringToDate(feeMainDetailsReportForm
					.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(feeMainDetailsReportForm
					.getEndDate());
			if(!startDate.after(endDate) && !endDate.before(startDate)){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if (daysBetween <= 0) {
				actionErrors.add("error", new ActionError(
						CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
			}else{
				message = new ActionError(
				"knowledgepro.employee.holidays.dateError");
				actionErrors.add(CMSConstants.MESSAGES, message);
				//addErrors(request, actionErrors);
			}
		}
		log.info("exit of validateDate of ApproveLeaveAction class.");
	}
}
