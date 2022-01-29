package com.kp.cms.actions.pettycash;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.pettycash.CollectionsReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.pettycash.CollectionLedgerHandler;
import com.kp.cms.handlers.pettycash.ColllectionHandeler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.pettycash.AccountNOTo;
import com.kp.cms.to.pettycash.DailyCollectionsTo;
import com.kp.cms.utilities.CommonUtil;
public class CollectionsForTheDayAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CollectionsForTheDayAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initcollectionsReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initcollectionsReport. of CollectionsForTheDayAction");	
		CollectionsReportForm  collectionsReportForm = (CollectionsReportForm)form;
		try {
			HttpSession session = request.getSession(false);
			
				session.removeAttribute("dailycollections");
		
				collectionsReportForm.resetFields();
			//Sets programType to formbean
			setRequiredDataToForm(collectionsReportForm, request);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in initcollectionsReport in CollectionsForTheDayAction",e);
			String msg = super.handleApplicationException(e);
			collectionsReportForm.setErrorMessage(msg);
			collectionsReportForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("leaving into initcollectionsReport. of CollectionsForTheDayAction");	
		return mapping.findForward(CMSConstants.COLLECTIONS_FOR_THE_DAY);
	
	}
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward SubmitCollections(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into SubmitCollections. of CollectionsForTheDayAction");	
		CollectionsReportForm collectionsReportForm = (CollectionsReportForm)form;
		setUserId(request,collectionsReportForm);
		HttpSession session = request.getSession(false);
		 ActionErrors errors = collectionsReportForm.validate(mapping, request);
		int userId=0;
		validateTime(collectionsReportForm, errors);
		// validating other name
		if(collectionsReportForm.getUserType()!=null){
			if(collectionsReportForm.getUserType().equals("otherUser")){
				validateUser(collectionsReportForm,errors);
			}
		}
		if(collectionsReportForm.getUserType()!=null){
			if(collectionsReportForm.getUserType().equals("otherUser")&& collectionsReportForm.getOtherName()!=null && !collectionsReportForm.getOtherName().isEmpty()){
				CollectionLedgerHandler collectionLedgerHandler= CollectionLedgerHandler.getInstance();
				Users user=collectionLedgerHandler.verifyUser(collectionsReportForm.getOtherName());
				if(user==null){
					if (errors.get(CMSConstants.PC_USER_NAME_NOT_VALID) != null&& !errors.get(CMSConstants.PC_USER_NAME_NOT_VALID).hasNext()) {
						errors.add(CMSConstants.PC_USER_NAME_NOT_VALID,new ActionError(CMSConstants.PC_USER_NAME_NOT_VALID));
					}
				}
				if(user!=null){
					userId=user.getId();
				}
			}
		}
		if(session.getAttribute("dailycollections")==null){
		try {
			if(errors.isEmpty()){
				List<DailyCollectionsTo> list = ColllectionHandeler.getInstance().getDailyCollections(collectionsReportForm,userId);
				if(list!=null && !list.isEmpty()){
					session.setAttribute("dailycollections",list);
				}
				if (list.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDataToForm(collectionsReportForm, request);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.COLLECTIONS_FOR_THE_DAY);
				} 
			}
			else{
				addErrors(request, errors);
				//Sets programType and program to formbean
			    setRequiredDataToForm(collectionsReportForm, request);
				return mapping.findForward(CMSConstants.COLLECTIONS_FOR_THE_DAY);
			}
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				collectionsReportForm.setOrganizationName(orgTO.getOrganizationName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in SubmitCollections in CollectionsForTheDayAction",e);
			String msg = super.handleApplicationException(e);
			collectionsReportForm.setErrorMessage(msg);
			collectionsReportForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}	
		request.setAttribute("collectionsReportForm", collectionsReportForm);
		log.info("Leaving into SubmitCollections. of CollectionsForTheDayAction");
		return mapping.findForward(CMSConstants.COLLECTIONS_FOR_THE_DAY_SHOW);
	}		

	
// validation for other name
	private void validateUser(CollectionsReportForm collectionsReportForm,
			ActionErrors errors) {
		if(collectionsReportForm.getOtherName()==null | collectionsReportForm.getOtherName().isEmpty()){
			if (errors.get(CMSConstants.PC_OTHER_NAME_REQUIRED) != null&& !errors.get(CMSConstants.PC_OTHER_NAME_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PC_OTHER_NAME_REQUIRED,new ActionError(CMSConstants.PC_OTHER_NAME_REQUIRED));
			}
		}	
	}

	/**
	 * @param collectionsReportForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDataToForm(CollectionsReportForm collectionsReportForm,HttpServletRequest request) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		collectionsReportForm.setEndDate(dateFormat.format(date));
		List<AccountNOTo> accountList=ColllectionHandeler.getInstance().getAccountNo();
		collectionsReportForm.setAccNOList(accountList);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printCollectinsResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			log.info("Entered printCollectinsResult input");
			CollectionsReportForm collectionsReportForm = (CollectionsReportForm)form;
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				collectionsReportForm.setOrganizationName(orgTO.getOrganizationName());
			}
			log.info("Exit from printCollectinsResult input");
			return mapping.findForward(CMSConstants.collections_Print);
	}

	
	/**
	 * Method to validate the time format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateTime(CollectionsReportForm collectionsReportForm, ActionErrors errors) {
		
		if(collectionsReportForm.getStartDate()!=null && !StringUtils.isEmpty(collectionsReportForm.getStartDate())&& !CommonUtil.isValidDate(collectionsReportForm.getStartDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(collectionsReportForm.getEndDate()!=null && !StringUtils.isEmpty(collectionsReportForm.getEndDate())&& !CommonUtil.isValidDate(collectionsReportForm.getEndDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(collectionsReportForm.getStartDate()) && CommonUtil.checkForEmpty(collectionsReportForm.getEndDate())&& CommonUtil.isValidDate(collectionsReportForm.getStartDate()) && CommonUtil.isValidDate(collectionsReportForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(collectionsReportForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(collectionsReportForm.getEndDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError("knowledgepro.inventory.cash.purch.report.from.date.cannot.greater"));
			}
		}
	}
	
}
