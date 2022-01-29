package com.kp.cms.actions.pettycash;

import java.util.Calendar;
import java.util.Date;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.pettycash.ConsolidatedCollectionReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.pettycash.CollectionLedgerHandler;
import com.kp.cms.handlers.pettycash.ConsolidatedCollectionReportHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.pettycash.ConsolidatedCollectionReportTO;
import com.kp.cms.to.pettycash.PcAccountTo;
import com.kp.cms.utilities.CommonUtil;

public class ConsolidatedCollectionReportAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(ConsolidatedCollectionReportAction.class);
	
	/**
	 * initconsolidatedCollectionList method to display the consolidatedCollectionReport.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initConsolidatedCollectionList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
			{
		log.info("Entered initConsolidatedCollectionReport..");
		ConsolidatedCollectionReportForm consolidatedCollectionReportForm = (ConsolidatedCollectionReportForm)form;
		try {
		consolidatedCollectionReportForm.resetFields();
		setRequiredDataToForm(consolidatedCollectionReportForm,request);
		} catch (Exception e) {
			log.error("Error occured in initCollectionLedger of CollectionLedgerAction", e);
			String msg = super.handleApplicationException(e);
			consolidatedCollectionReportForm.setErrorMessage(msg);
			consolidatedCollectionReportForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initConsolidatedCollectionReport..");
		return mapping.findForward(CMSConstants.INIT_CONSOLIDATED_COLLECTION_REPORT);
	}
	
	/**
	 * setting the data to request
	 * @param consolidatedCollectionReportForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(ConsolidatedCollectionReportForm consolidatedCollectionReportForm ,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm. of ConsolidatedCollectionReport");	
		    //setting programTypeList to Request
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			consolidatedCollectionReportForm.setProgramTypeList(programTypeList);
			List<PcAccountTo> accNoList=ConsolidatedCollectionReportHandler.getInstance().getListOfAccNo();
			consolidatedCollectionReportForm.setAccNolist(accNoList);
		log.info("entered setRequiredDataToForm. of ConsolidatedCollectionReport");	
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStudentList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
			{
			
		log.info("Entered searchStudentList in ConsolidatedCollectionReportAction..");
		ConsolidatedCollectionReportForm consolidatedCollectionReportForm = (ConsolidatedCollectionReportForm)form;
		 ActionErrors errors = consolidatedCollectionReportForm.validate(mapping, request);
		validateDate(consolidatedCollectionReportForm, errors);
		PcAccHeadGroup group=null;
		int userId=0;
		CollectionLedgerHandler collectionLedgerHandler=CollectionLedgerHandler.getInstance();
		boolean account=false;
		if(consolidatedCollectionReportForm.getAccount()!=null){
		if(consolidatedCollectionReportForm.getAccount().equals("true")){
			validateAccount(consolidatedCollectionReportForm,errors);
		}
		if(consolidatedCollectionReportForm.getAccount().equals("false")){
			validateGroupCode(consolidatedCollectionReportForm,errors);
		}
		}
		if(consolidatedCollectionReportForm.getAccount()!=null){
			if(consolidatedCollectionReportForm.getAccount().equals("false") && consolidatedCollectionReportForm.getGroupCode()!=null && !consolidatedCollectionReportForm.getGroupCode().isEmpty()){
				group=collectionLedgerHandler.verifyGroupCode(consolidatedCollectionReportForm.getGroupCode());
				if(group==null){
					if (errors.get(CMSConstants.PC_GROUP_CODE_NOT_VALID) != null&& !errors.get(CMSConstants.PC_GROUP_CODE_NOT_VALID).hasNext()) {
						errors.add(CMSConstants.PC_GROUP_CODE_NOT_VALID,new ActionError(CMSConstants.PC_GROUP_CODE_NOT_VALID));
					}
				}
				if(group!=null){
					account=true;
				}
			}
		}
		if(consolidatedCollectionReportForm.getUserType()!=null){
			if(consolidatedCollectionReportForm.getUserType().equals("otherUser")){
				validateUser(consolidatedCollectionReportForm,errors);
			}
		}
		if(consolidatedCollectionReportForm.getUserType()!=null){
			if(consolidatedCollectionReportForm.getUserType().equals("otherUser")&& consolidatedCollectionReportForm.getOtherName()!=null && !consolidatedCollectionReportForm.getOtherName().isEmpty()){
				Users user=collectionLedgerHandler.verifyUser(consolidatedCollectionReportForm.getOtherName());
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
		if (errors.isEmpty()) {
			try {
				setUserId(request, consolidatedCollectionReportForm);
				if(consolidatedCollectionReportForm.getUserType()!=null){
					if(consolidatedCollectionReportForm.getUserType().equals("false")){
						userId=Integer.parseInt(consolidatedCollectionReportForm.getUserId());
					}
				}
				ConsolidatedCollectionReportHandler consolidatedCollectionReportHandler=ConsolidatedCollectionReportHandler.getInstance();
				List<ConsolidatedCollectionReportTO> selectedData=consolidatedCollectionReportHandler.getListOfCandidates(consolidatedCollectionReportForm, account, userId);
				if (selectedData.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDataToForm(consolidatedCollectionReportForm,request);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_CONSOLIDATED_COLLECTION_REPORT);
				} 
				else{
					consolidatedCollectionReportForm.setSelectedData(selectedData);
				}
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					consolidatedCollectionReportForm.setOrganizationName(orgTO.getOrganizationName());
				}
				//request.getSession().setAttribute("SelectedConsolidatedData", selectedData);
				
			}  catch (Exception exception) {
				exception.printStackTrace();
				String msg = super.handleApplicationException(exception);
				consolidatedCollectionReportForm.setErrorMessage(msg);
				consolidatedCollectionReportForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDataToForm(consolidatedCollectionReportForm,request);
			log.info("Exit searchStudentList in ConsolidatedCollectionReportAction with validation..  ");
			return mapping.findForward(CMSConstants.INIT_CONSOLIDATED_COLLECTION_REPORT);
		}
		log.info("Exit searchStudentList in ConsolidatedCollectionReportAction..");
		return mapping.findForward(CMSConstants.GET_CONSOLIDATED_COLLECTION_REPORT);
	}
	/**
	 * validate for Group Code
	 * @param consolidatedCollectionReportForm
	 * @param errors
	 */
	private void validateGroupCode(
			ConsolidatedCollectionReportForm consolidatedCollectionReportForm,
			ActionErrors errors) {
		if(consolidatedCollectionReportForm.getGroupCode()==null || consolidatedCollectionReportForm.getGroupCode().isEmpty()){
			if (errors.get(CMSConstants.PC_GROUP_CODE_REQUIRED) != null&& !errors.get(CMSConstants.PC_GROUP_CODE_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PC_GROUP_CODE_REQUIRED,new ActionError(CMSConstants.PC_GROUP_CODE_REQUIRED));
			}
		}	
	}

	/**
	 * method to validate other user
	 * @param collectionLedgerForm
	 * @param errors
	 */
	private void validateUser(ConsolidatedCollectionReportForm consolidatedCollectionReportForm,ActionErrors errors) {
		if(consolidatedCollectionReportForm.getOtherName()==null | consolidatedCollectionReportForm.getOtherName().isEmpty()){
			if (errors.get(CMSConstants.PC_OTHER_NAME_REQUIRED) != null&& !errors.get(CMSConstants.PC_OTHER_NAME_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PC_OTHER_NAME_REQUIRED,new ActionError(CMSConstants.PC_OTHER_NAME_REQUIRED));
			}
		}	
	}

	/**
	 * Method to validate the Account code
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateAccount(ConsolidatedCollectionReportForm consolidatedCollectionReportForm,ActionErrors errors) throws Exception{
		if(consolidatedCollectionReportForm.getAccountNumber()==null){
			if (errors.get(CMSConstants.PC_ACCOUNT_NO_REQUIRED) != null&& !errors.get(CMSConstants.PC_ACCOUNT_NO_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PC_ACCOUNT_NO_REQUIRED,new ActionError(CMSConstants.PC_ACCOUNT_NO_REQUIRED));
			}
		}		
	}

	/**
	 * Method to validate the Date format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateDate(ConsolidatedCollectionReportForm consolidatedCollectionReportForm, ActionErrors errors) throws Exception{
		
		if(consolidatedCollectionReportForm.getStartDate()!=null && !StringUtils.isEmpty(consolidatedCollectionReportForm.getStartDate())&& !CommonUtil.isValidDate(consolidatedCollectionReportForm.getStartDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(consolidatedCollectionReportForm.getEndDate()!=null && !StringUtils.isEmpty(consolidatedCollectionReportForm.getEndDate())&& !CommonUtil.isValidDate(consolidatedCollectionReportForm.getEndDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(consolidatedCollectionReportForm.getStartDate()) && CommonUtil.checkForEmpty(consolidatedCollectionReportForm.getEndDate())&& CommonUtil.isValidDate(consolidatedCollectionReportForm.getEndDate())&& CommonUtil.isValidDate(consolidatedCollectionReportForm.getStartDate())){
			Date startDate = CommonUtil.ConvertStringToDate(consolidatedCollectionReportForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(consolidatedCollectionReportForm.getEndDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
		if(consolidatedCollectionReportForm.getAccount()==null && StringUtils.isEmpty(consolidatedCollectionReportForm.getAccount())){
			if (errors.get(CMSConstants.PC_ACCOUNT_REQUIRED) != null&& !errors.get(CMSConstants.PC_ACCOUNT_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PC_ACCOUNT_REQUIRED,new ActionError(CMSConstants.PC_ACCOUNT_REQUIRED));
			}
		}
		if(consolidatedCollectionReportForm.getUserType()==null && StringUtils.isEmpty(consolidatedCollectionReportForm.getUserType())){
			if (errors.get(CMSConstants.PC_USERTYPE_REQUIRED) != null&& !errors.get(CMSConstants.PC_USERTYPE_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PC_USERTYPE_REQUIRED,new ActionError(CMSConstants.PC_USERTYPE_REQUIRED));
			}
		}
	}
	
	public ActionForward printconsolidatedCollectionList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("printConsolidatedResult");
	}
}
