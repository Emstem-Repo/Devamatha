package com.kp.cms.actions.pettycash;

import java.util.ArrayList;
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
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadsForm;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.pettycash.PettyCashAccountHeadsHandler;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.pettycash.AccountHeadsTo;
import com.kp.cms.to.pettycash.PettyCashAccountHeadGroupCodeTO;
import com.kp.cms.to.pettycash.PettyCashAccountHeadsTO;
import com.kp.cms.to.pettycash.PettyCashAccountNumberTO;
import com.kp.cms.to.pettycash.PettyCashCollectionTo;
import com.kp.cms.utilities.CommonUtil;


@SuppressWarnings("deprecation")
public class PettyCashAccountHeadsAction extends BaseDispatchAction{
	
private static final Log log = LogFactory.getLog(PettyCashAccountHeadsAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return To fetch the total account entries from the database
	 * @throws Exception
	 */

	public ActionForward initAccountHeadGroup(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{	
		log.info("Entering into initAccountHeadGroup in PettyCashAccountHeadsAction class..");
		PettyCashAccountHeadsForm pettyCashAccHeadForm=(PettyCashAccountHeadsForm) form;
		pettyCashAccHeadForm.clear();
		try
		{	
			setProgramToRequest(request);
			setListToRequest(request,pettyCashAccHeadForm);
			List<PettyCashAccountNumberTO> pcAccountNumberTOList= PettyCashAccountHeadsHandler.getInstance().getAllpettyCashBankAccNumber();
			List<PettyCashAccountHeadGroupCodeTO> pettycashAccHeadGroupTOList=PettyCashAccountHeadsHandler.getInstance().getAllpettyCashAccHeadGroup();
			pettyCashAccHeadForm.setPcAccountHeadGroupCodeTOList(pettycashAccHeadGroupTOList);
			pettyCashAccHeadForm.setPcAccountNumberTOList(pcAccountNumberTOList);
			
	
		}catch (Exception e) {
			log.error("error in loading initAccountHeadGroup...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				pettyCashAccHeadForm.setErrorMessage(msg);
				pettyCashAccHeadForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("leaving from initpettyCashAccountHeads in PettyCashAccountHeadsAction class..");
		return mapping.findForward(CMSConstants.INIT_ACC_HEAD_GROUP);
	}
	
	/**
	 * @param request
	 * @throws Exception
	 */
	private void setProgramToRequest(HttpServletRequest request) throws Exception{
		log.debug("inside setProgramListToRequest");
		List<ProgramTO> programList = ProgramHandler.getInstance().getProgram();
		request.setAttribute("programList", programList);
		log.debug("leaving setProgramListToRequest");
	}

	public ActionForward manageAccountHead(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		log.info("entering into manageAccountHead in PettyCashAccountHeadsAction class..");
		PettyCashAccountHeadsForm pettyCashAccHeadForm=(PettyCashAccountHeadsForm) form;
		//PettyCashAccountHeadsForm tempPettyCashAccHeadsForm=null;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = pettyCashAccHeadForm.validate(mapping, request);
		String accountname = pettyCashAccHeadForm.getAccCode();
		
		boolean isAdded=false;
		boolean isUpdated=false;
		String task=null;
		
		if(pettyCashAccHeadForm.getId()!=null && !pettyCashAccHeadForm.getId().equals(""))
		{
			if(pettyCashAccHeadForm.getId().intValue()==0)
			{
				pettyCashAccHeadForm.setId(null);
			}
		}
		
		if(pettyCashAccHeadForm.getId()!=null && !pettyCashAccHeadForm.getId().equals(""))
		{
			 task="Update";
		}
		else
		{
			task="Add";
		}
		try
		{
			setUserId(request, pettyCashAccHeadForm);
			
			if(isCancelled(request)){
				if(pettyCashAccHeadForm.getOldId()!=null)
				{
					pettyCashAccHeadForm.setId(pettyCashAccHeadForm.getOldId());
					PettyCashAccountHeadsHandler.getInstance().editAccountHead(pettyCashAccHeadForm);
					
				}
				if(pettyCashAccHeadForm!=null)
				{
					setListToRequest(request,pettyCashAccHeadForm);
				}
				request.setAttribute("task", "Update");
				return mapping.findForward(CMSConstants.INIT_ACC_HEAD_GROUP);
			}
			
			
			if(errors.isEmpty())
			{
				if(pettyCashAccHeadForm.getId()!=null && !pettyCashAccHeadForm.getId().equals("") )
				{	if(pettyCashAccHeadForm.getAccCode().equalsIgnoreCase(pettyCashAccHeadForm.getOldAccountCode())){
						task="Update";
						isUpdated= PettyCashAccountHeadsHandler.getInstance().manageAccountHead(pettyCashAccHeadForm,task);
						pettyCashAccHeadForm.clear();
						setListToRequest(request,pettyCashAccHeadForm);
					}
					else{
						PcAccountHead head = PettyCashAccountHeadsHandler.getInstance().existanceCheck(pettyCashAccHeadForm.getAccCode());
						if(head!=null){
							errors.add("error", new ActionError(
									"knowledgepro.admin.feeaccount.addexist", accountname));
							saveErrors(request,errors);
							request.setAttribute("task", task);
							return mapping.findForward("initAccountHeadGroup");
						}
						else{
							task="Update";
							isUpdated= PettyCashAccountHeadsHandler.getInstance().manageAccountHead(pettyCashAccHeadForm,task);
							pettyCashAccHeadForm.clear();
							setListToRequest(request,pettyCashAccHeadForm);
						}
					}
					
				}
				else
				{	 task="Add";
					isAdded = PettyCashAccountHeadsHandler.getInstance().manageAccountHead(pettyCashAccHeadForm,task);
					pettyCashAccHeadForm.clear();
					setListToRequest(request,pettyCashAccHeadForm);
					request.setAttribute("task",task);
				}
				if (isAdded || isUpdated) 
				{
					ActionMessage message=null;
					if(task.equalsIgnoreCase("Add"))
					{
						message = new ActionMessage("knowledgepro.pettycash.accheadgroup.addsuccess",
								accountname);
					}
					else
					{
						 message = new ActionMessage("knowledgepro.pettycash.accheadgroup.updatesuccess",
								 accountname);
					}
					messages.add("messages", message);
					saveMessages(request, messages);
					pettyCashAccHeadForm.reset(mapping, request);
				} else {
					if(task.equalsIgnoreCase("Add"))
					{
					errors.add("error", new ActionError("knowledgepro.pettycash.accheadgroup.addfail",
							accountname));
					}
					else
					{
						errors.add("error", new ActionError("knowledgepro.pettycash.accheadgroup.updatefail",
								accountname));
						
					}
					saveErrors(request,errors);
				}
			}else
			{
				saveErrors(request,errors);
			}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.admin.feeaccount.addexist", accountname));
			saveErrors(request,errors);
		} 
		catch (Exception e) {
			log.error("error in delete of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				pettyCashAccHeadForm.setErrorMessage(msg);
				pettyCashAccHeadForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			
		}
		if(!errors.isEmpty())
		{
			setListToRequest(request,pettyCashAccHeadForm);
			if(request.getAttribute("task")!=null)
			{
				request.setAttribute("task",request.getAttribute("task").toString());
				
			}
			else
			{
				request.setAttribute("task",task);
			}
			
		}setProgramToRequest(request);
		log.info("leaving from manageAccountHead in PettyCashAccountHeadsAction class..");
		return mapping.findForward(CMSConstants.INIT_ACC_HEAD_GROUP);
	}
	
	public ActionForward deletePettyCashAccountHead(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		log.info("entering into deletePettyCashAccountHead in PettyCashAccountHeadsAction class..");
		
		PettyCashAccountHeadsForm pettyCashAccHeadForm=(PettyCashAccountHeadsForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = pettyCashAccHeadForm.validate(mapping, request);
		String task="Delete";
		boolean isDelete=false;
		try
		{
			setUserId(request, pettyCashAccHeadForm);
			isDelete =  PettyCashAccountHeadsHandler.getInstance().deleteAccountHead(pettyCashAccHeadForm, task);
			pettyCashAccHeadForm.clear();
			setListToRequest(request,pettyCashAccHeadForm);
			if (isDelete) {
				ActionMessage message = new ActionMessage("knowledgepro.pettycash.accheadgroup.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				
			} else {
				errors.add("error", new ActionError("knowledgepro.pettycash.accheadgroup.deletefail"));
				saveErrors(request,errors);
			}
		
		}
		catch (Exception e) {
			log.error("error in delete of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				pettyCashAccHeadForm.setErrorMessage(msg);
				pettyCashAccHeadForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			
		}
		setProgramToRequest(request);
		log.info("leaving from deletePettyCashAccountHead in PettyCashAccountHeadsAction class..");
		return mapping.findForward(CMSConstants.INIT_ACC_HEAD_GROUP);
	}
	
	public ActionForward editPettyCashAccountHead(ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		log.info("entering into editPettyCashAccountHead in PettyCashAccountHeadsAction class..");
		ActionMessages messages=new ActionMessages();
		PettyCashAccountHeadsForm pettyCashAccHeadForm=(PettyCashAccountHeadsForm) form;
		try
		{   
			setProgramToRequest(request);
			PettyCashAccountHeadsHandler.getInstance().editAccountHead(pettyCashAccHeadForm);
			request.setAttribute("task", "Update");
			setListToRequest(request,pettyCashAccHeadForm);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("error in delete of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				pettyCashAccHeadForm.setErrorMessage(msg);
				pettyCashAccHeadForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
			
		}
		log.info("leaving from editPettyCashAccountHead in PettyCashAccountHeadsAction class..");
		return mapping.findForward(CMSConstants.INIT_ACC_HEAD_GROUP);
		
	}
	
	public void setListToRequest(HttpServletRequest request,ActionForm accountForm)throws Exception
	{
		log.info("entering into setListToRequest in PettyCashAccountHeadsAction class..");
		PettyCashAccountHeadsForm pettyCashAccHeadForm=(PettyCashAccountHeadsForm) accountForm;
		List<PettyCashAccountHeadsTO> pcAccountHeadTOList=PettyCashAccountHeadsHandler.getInstance().getAllPettyCashAccHeads();
		if(pcAccountHeadTOList!=null && !pcAccountHeadTOList.isEmpty())
		{
			pettyCashAccHeadForm.setPcAccountHeadTOList(pcAccountHeadTOList);
		}
		else
		{
			pettyCashAccHeadForm.setPcAccountHeadTOList(new ArrayList<PettyCashAccountHeadsTO>());
		}
		log.info("leaving from setListToRequest in PettyCashAccountHeadsAction class.");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAccountHeadReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initAccountHeadReport input");
		PettyCashAccountHeadsForm pcAccountHeadsForm = (PettyCashAccountHeadsForm)form;
		pcAccountHeadsForm.clear();
		log.info("Leaving initAccountHeadReport input");
		return mapping.findForward(CMSConstants.ACCOUNT_HEAD_REPORT);
	} 
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAccountHeadsDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitAttendSummaryReport..");	
		PettyCashAccountHeadsForm pcAccountHeadsForm = (PettyCashAccountHeadsForm)form;
		 ActionErrors errors = pcAccountHeadsForm.validate(mapping, request);
		HttpSession session=request.getSession();
		validateDate(pcAccountHeadsForm, errors,request);
		if (errors.isEmpty()) {	
			try {
			     List<AccountHeadsTo> accountHeadsToList=PettyCashAccountHeadsHandler.getInstance().getAccountHeads(pcAccountHeadsForm);
			     session.setAttribute("accountHeadsList", accountHeadsToList);
			}catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				pcAccountHeadsForm.setErrorMessage(msg);
				pcAccountHeadsForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
					throw e;
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.ACCOUNT_HEAD_REPORT);
		}
		log.info("Exit submitAttendSummaryReport..");
		return mapping.findForward(CMSConstants.SUBMIT_ACCOUNT_HEAD_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPettyCash(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initAccountHeadReport input");
		//PettyCashAccountHeadsForm pcAccountHeadsForm = (PettyCashAccountHeadsForm)form;
		//pcAccountHeadsForm.resetFields();
		log.info("Leaving initAccountHeadReport input");
		return mapping.findForward(CMSConstants.INIT_PETTY_CASH);
	} 
	
	/**
	 * @param pcAccountHeadsForm
	 * @param actionErrors
	 * @param request
	 */
	public void validateDate(PettyCashAccountHeadsForm pcAccountHeadsForm,
			ActionErrors actionErrors,HttpServletRequest request) {
		log.info("entering into validateDate of HolidaysDetailsAction class.");
		ActionError message=null;
		if (pcAccountHeadsForm.getStartDate() != null
				&& pcAccountHeadsForm.getStartDate().length() != 0
				&& pcAccountHeadsForm.getEndDate() != null
				&& pcAccountHeadsForm.getEndDate().length() != 0) {
			Date startDate = CommonUtil.ConvertStringToDate(pcAccountHeadsForm
					.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(pcAccountHeadsForm
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
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPettyCashCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initPettyCashCollection input");
		PettyCashAccountHeadsForm pcAccountHeadsForm = (PettyCashAccountHeadsForm)form;
		pcAccountHeadsForm.clear();
		log.info("Leaving initPettyCashCollection input");
		return mapping.findForward(CMSConstants.INIT_COLLECTION_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCollectionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitAttendSummaryReport..");	
		PettyCashAccountHeadsForm pcAccountHeadsForm = (PettyCashAccountHeadsForm)form;
		 ActionErrors errors = pcAccountHeadsForm.validate(mapping, request);
		HttpSession session=request.getSession();
		validateDate(pcAccountHeadsForm, errors,request);
		if (errors.isEmpty()) {	
			try {
				List<PettyCashCollectionTo> collectionToList=PettyCashAccountHeadsHandler.getInstance().getCollections(pcAccountHeadsForm);
			     session.setAttribute("collectionList", collectionToList);
			}catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				pcAccountHeadsForm.setErrorMessage(msg);
				pcAccountHeadsForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
					throw e;
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_COLLECTION_REPORT);
		}
		log.info("Exit submitAttendSummaryReport..");
		return mapping.findForward(CMSConstants.SUBMIT_COLLECTION_REPORT);
	}
}




	
	
	
	


