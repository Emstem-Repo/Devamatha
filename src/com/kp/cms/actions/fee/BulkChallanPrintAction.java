package com.kp.cms.actions.fee;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.CopyInterviewDefinitionForm;
import com.kp.cms.forms.fee.BulkChallanPrintForm;
import com.kp.cms.handlers.admission.GensmartCardDataHandler;
import com.kp.cms.handlers.fee.BulkChallanPrintHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CommonUtil;

public class BulkChallanPrintAction extends BaseDispatchAction{
private static final Log log = LogFactory.getLog(BulkChallanPrintAction.class);
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initBulkChallanPrint(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside initBulkChallanPrint in Action");
		 BulkChallanPrintForm bulkChallanPrintForm = (BulkChallanPrintForm) form;
		try{
			bulkChallanPrintForm.resetFields();
			setUserId(request, bulkChallanPrintForm);
			bulkChallanPrintForm.setPrint(false);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			bulkChallanPrintForm.setErrorMessage(msg);
			bulkChallanPrintForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT_INIT);
	}
	
	
	/**
	 * method to print the challan for the given date range in the input
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward printChallan(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside printChallan in Action");
		 BulkChallanPrintForm bulkChallanPrintForm = (BulkChallanPrintForm) form;
		 ActionMessages messages= new ActionMessages();
		 ActionErrors errors=bulkChallanPrintForm.validate(mapping, request);
		 validateDate(errors,bulkChallanPrintForm);
		 if (errors.isEmpty()) {
				try {
					boolean isDataAvailable = BulkChallanPrintHandler.getInstance().getChallanData(bulkChallanPrintForm,request);
					if (!isDataAvailable) {
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
						saveErrors(request, errors);
						bulkChallanPrintForm.setPrint(false);
						log.info("Exit BulkChallanPrintAction - printChallan size 0");
						return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT_INIT);
					} 
					bulkChallanPrintForm.setPrint(true);
					saveErrors(request, errors);
					saveMessages(request, messages);
				}  catch (Exception exception) {
					String msg = super.handleApplicationException(exception);
					bulkChallanPrintForm.setErrorMessage(msg);
					bulkChallanPrintForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			} else {
				addErrors(request, errors);
				bulkChallanPrintForm.setPrint(false);
				bulkChallanPrintForm.resetFields();
				log.info("Exit BulkChallanPrintAction - printChallan errors not empty ");
				return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT_INIT);
			}
			
			log.info("Exit BulkChallanPrintAction - printChallan");
			return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT_INIT);
		 	
	}
	/**
	 * validates the entered year by user
	 * @param errors
	 * @param copyCheckListAssignmentForm
	 */
	public void validateDate(ActionErrors errors,BulkChallanPrintForm bulkChallanPrintForm){
		if(bulkChallanPrintForm.getFromDate()!=null && !StringUtils.isEmpty(bulkChallanPrintForm.getFromDate())&& !CommonUtil.isValidDate(bulkChallanPrintForm.getFromDate())){
		if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
			errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
		}
	}
	if(bulkChallanPrintForm.getToDate()!=null && !StringUtils.isEmpty(bulkChallanPrintForm.getToDate())&& !CommonUtil.isValidDate(bulkChallanPrintForm.getToDate())){
		if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
			errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
		}
	}
	if(CommonUtil.checkForEmpty(bulkChallanPrintForm.getFromDate()) && CommonUtil.checkForEmpty(bulkChallanPrintForm.getToDate())){
		Date startDate = CommonUtil.ConvertStringToDate(bulkChallanPrintForm.getFromDate());
		Date endDate = CommonUtil.ConvertStringToDate(bulkChallanPrintForm.getToDate());

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		if(daysBetween <= 0) {
			errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
	}
	

	/**
	 * redirecting to pop up jsp's
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */	

	public ActionForward popUpPrint(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response){
		log.debug("inside initBulkChallanPrint in Action");
	 	return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT_POPUP);
	 
	}
}
