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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.pettycash.CollectionLedgerForm;
import com.kp.cms.forms.pettycash.ConsolidatedCollectionLedgerForm;
import com.kp.cms.forms.pettycash.ConsolidatedCollectionReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.pettycash.CollectionLedgerHandler;
import com.kp.cms.handlers.pettycash.ConsolidatedCollectionLedgerHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.pettycash.CollectionLedgerTO;
import com.kp.cms.to.pettycash.PcAccountHeadsTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class CollectionLedgerAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CollectionLedgerAction.class);
	
	/**   
	 * Setting the data to display the CollectionLedgerReport.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCollectionLedger(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Enter into the initCollectionLedger");
		CollectionLedgerForm collectionLedgerForm=(CollectionLedgerForm)form;
		collectionLedgerForm.setMode(null);
		collectionLedgerForm.setDownloadExcel(null);
		try {
			collectionLedgerForm.resetFields();
			setAccountListtoForm(collectionLedgerForm);
			} catch (Exception e) {
			log.error("Error occured in initCollectionLedger of CollectionLedgerAction", e);
			String msg = super.handleApplicationException(e);
			collectionLedgerForm.setErrorMessage(msg);
			collectionLedgerForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit from the initCollectionLedger");
		return mapping.findForward(CMSConstants.INIT_COLLECTION_LEDGER);
	}

	/**
	 * setting the required data to CollectionLedgerForm
	 * @param collectionLedgerForm
	 */
	private void setAccountListtoForm(CollectionLedgerForm collectionLedgerForm) throws Exception {
		log.info("entering into setAccountListtoForm in CollectionLedgerAction class..");
		List<PcAccountHeadsTO> accountList = CollectionLedgerHandler.getInstance().getAccountList();
		collectionLedgerForm.setAccountList(accountList);
		log.info("exit from setAccountListtoForm in CollectionLedgerAction class..");
		
	}
	
	/**
	 * search the data for input
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Enter into the searchData in CollectionLedger Action");
		CollectionLedgerForm collectionLedgerForm=(CollectionLedgerForm)form;
		 ActionErrors errors = collectionLedgerForm.validate(mapping, request);
		collectionLedgerForm.setMode(null);
		collectionLedgerForm.setDownloadExcel(null);
		collectionLedgerForm.setMsg(null);
		validateDate(collectionLedgerForm, errors);
		PcAccHeadGroup group=null;
		int userId=0;
		CollectionLedgerHandler collectionLedgerHandler=CollectionLedgerHandler.getInstance();
		boolean account=false;
		if(collectionLedgerForm.getAccount()!=null){
		if(collectionLedgerForm.getAccount().equals("true")){
			validateAccount(collectionLedgerForm,errors);
		}
		if(collectionLedgerForm.getAccount().equals("false")){
			validateGroupCode(collectionLedgerForm,errors);
		}
		}
		if(collectionLedgerForm.getAccount()!=null){
			if(collectionLedgerForm.getAccount().equals("false") && collectionLedgerForm.getGroupCode()!=null && !collectionLedgerForm.getGroupCode().isEmpty()){
				group=collectionLedgerHandler.verifyGroupCode(collectionLedgerForm.getGroupCode());
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
		if(collectionLedgerForm.getUserType()!=null){
			if(collectionLedgerForm.getUserType().equals("otherUser")){
				validateUser(collectionLedgerForm,errors);
			}
		}
		if(collectionLedgerForm.getUserType()!=null){
			if(collectionLedgerForm.getUserType().equals("otherUser")&& collectionLedgerForm.getOtherName()!=null && !collectionLedgerForm.getOtherName().isEmpty()){
				Users user=collectionLedgerHandler.verifyUser(collectionLedgerForm.getOtherName());
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
				setUserId(request, collectionLedgerForm);
				if(collectionLedgerForm.getUserType()!=null){
					if(collectionLedgerForm.getUserType().equals("false")){
						userId=Integer.parseInt(collectionLedgerForm.getUserId());
					}
				}
				
				//Mary
				String accCode=collectionLedgerForm.getAccountCode();
				String AccountName = CollectionLedgerHandler.getInstance().getAccountList1(accCode);
							
				//End of code
				List<CollectionLedgerTO> selectedData = collectionLedgerHandler.getListOfCandidates(collectionLedgerForm,account,userId);
				  
				if(selectedData!=null && !selectedData.isEmpty()){
					collectionLedgerForm.setCollectionList(selectedData);
				}
				if (selectedData.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setAccountListtoForm(collectionLedgerForm);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_COLLECTION_LEDGER);
				} 
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);     
					collectionLedgerForm.setOrganizationName(orgTO.getOrganizationName());
					System.out.println("   !!!!!!!!!!   organisation name  !!!!!!!!  "+orgTO.getOrganizationName());
				}
				request.getSession().setAttribute("SelectedData", selectedData);
				
				getReportHeading(collectionLedgerForm,AccountName);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				collectionLedgerForm.setErrorMessage(msg);
				collectionLedgerForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setAccountListtoForm(collectionLedgerForm);		
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_COLLECTION_LEDGER);
		}
		log.info("Exit from the searchData in CollectionLedger Action");
		return mapping.findForward(CMSConstants.COLLECTION_LEDGER_REPORT);
	}
	/**
	 * @param collectionLedgerForm
	 * @throws Exception
	 */
	private void getReportHeading(CollectionLedgerForm collectionLedgerForm,String AccountName ) throws Exception {
		// TODO Auto-generated method stub
		String msg="";
		
		if(collectionLedgerForm.getAccount().equals("true")){
			
			msg=msg+" Account Name :"+AccountName;
		}else{
			msg=msg+" Account Group :"+collectionLedgerForm.getGroupCode();
		}
		
		if(collectionLedgerForm.getUserType().equals("true")){
			msg=msg+" USER TYPE : ALL USERS";
		}else if(collectionLedgerForm.getUserType().equals("false")){
			msg=msg+" USER TYPE : Current USER";
		}else {
			msg=msg+" USER TYPE : OTHER USER :"+collectionLedgerForm.getOtherName();
		}
		collectionLedgerForm.setMsg(msg);
	}

	/**
	 * validate for Group Code
	 * @param consolidatedCollectionReportForm
	 * @param errors
	 */
	private void validateGroupCode(
			CollectionLedgerForm collectionLedgerForm,
			ActionErrors errors) {
		if(collectionLedgerForm.getGroupCode()==null || collectionLedgerForm.getGroupCode().isEmpty()){
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
	private void validateUser(CollectionLedgerForm collectionLedgerForm,
			ActionErrors errors) {
		if(collectionLedgerForm.getOtherName()==null || collectionLedgerForm.getOtherName().isEmpty()){
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
	private void validateAccount(CollectionLedgerForm collectionLedgerForm,
			ActionErrors errors) throws Exception{
		if(collectionLedgerForm.getAccountCode()==null || collectionLedgerForm.getAccountCode().isEmpty()){
			if (errors.get(CMSConstants.PC_ACCOUNT_CODE_REQUIRED) != null&& !errors.get(CMSConstants.PC_ACCOUNT_CODE_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PC_ACCOUNT_CODE_REQUIRED,new ActionError(CMSConstants.PC_ACCOUNT_CODE_REQUIRED));
			}
		}		
	}

	/**
	 * Method to validate the Date format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateDate(CollectionLedgerForm collectionLedgerForm, ActionErrors errors) throws Exception{
		
		if(collectionLedgerForm.getStartDate()!=null && !StringUtils.isEmpty(collectionLedgerForm.getStartDate())&& !CommonUtil.isValidDate(collectionLedgerForm.getStartDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(collectionLedgerForm.getEndDate()!=null && !StringUtils.isEmpty(collectionLedgerForm.getEndDate())&& !CommonUtil.isValidDate(collectionLedgerForm.getEndDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(collectionLedgerForm.getStartDate()) && CommonUtil.checkForEmpty(collectionLedgerForm.getEndDate())&& CommonUtil.isValidDate(collectionLedgerForm.getEndDate())&& CommonUtil.isValidDate(collectionLedgerForm.getStartDate())){
			Date startDate = CommonUtil.ConvertStringToDate(collectionLedgerForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(collectionLedgerForm.getEndDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
		if(collectionLedgerForm.getAccount()==null && StringUtils.isEmpty(collectionLedgerForm.getAccount())){
			if (errors.get(CMSConstants.PC_ACCOUNT_REQUIRED) != null&& !errors.get(CMSConstants.PC_ACCOUNT_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PC_ACCOUNT_REQUIRED,new ActionError(CMSConstants.PC_ACCOUNT_REQUIRED));
			}
		}
		if(collectionLedgerForm.getUserType()==null && StringUtils.isEmpty(collectionLedgerForm.getUserType())){
			if (errors.get(CMSConstants.PC_USERTYPE_REQUIRED) != null&& !errors.get(CMSConstants.PC_USERTYPE_REQUIRED).hasNext()) {
				errors.add(CMSConstants.PC_USERTYPE_REQUIRED,new ActionError(CMSConstants.PC_USERTYPE_REQUIRED));
			}
		}
	}
	/**
	 * printing the Collection Ledger Report Result
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printCollectionLedgerReportResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered into printCollectionLedger");
		
		log.info("Exit from printCollectionLedger");
		
		return mapping.findForward("collectionLedgerPrint");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ExportToExcelAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered exportToExcel..");
		CollectionLedgerForm collectionLedgerForm=(CollectionLedgerForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		collectionLedgerForm.setMode(null);
		collectionLedgerForm.setDownloadExcel(null);
			try {
				  boolean isUpdated =CollectionLedgerHandler.getInstance().exportTOExcel(collectionLedgerForm,request);
			 		if(isUpdated){
			 			collectionLedgerForm.setMode("excel");
			 			collectionLedgerForm.setDownloadExcel("download");
						ActionMessage message = new ActionMessage("knowledgepro.reports.columnsUpdate");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
				else {
					errors.add("error", new ActionError("knowledgepro.select.atleast.onecolumn"));
					addErrors(request, errors);			
					return mapping.findForward(CMSConstants.COLLECTION_LEDGER_REPORT);
				}
		
		}catch (ApplicationException ae) {
			log.error("error occured in exportToExcel in CollectionLedgerAction",ae);
			String msg = super.handleApplicationException(ae);
			collectionLedgerForm.setErrorMessage(msg);
			collectionLedgerForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error occured in exportToExcel in CollectionLedgerAction",e);
			String msg = super.handleApplicationException(e);
			collectionLedgerForm.setErrorMessage(msg);
			collectionLedgerForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	
		log.info("exit exportToExcel..");
		return mapping.findForward(CMSConstants.COLLECTION_LEDGER_REPORT);
}
	
}
