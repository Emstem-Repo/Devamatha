package com.kp.cms.actions.employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.WorkTimeEntryForm;
import com.kp.cms.handlers.employee.EmployeeInfoHandler;
import com.kp.cms.handlers.employee.WorkTimeEntryHandler;
import com.kp.cms.to.attendance.WorkTimeTO;
import com.kp.cms.to.employee.EmpWorkTimeTO;
import com.kp.cms.to.employee.EmployeeKeyValueTO;
import com.kp.cms.transactions.employee.IWorkTimeEntryTransaction;
import com.kp.cms.transactionsimpl.employee.WorkTimeEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class WorkTimeEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(WorkTimeEntryAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         WORK_TIME_ENTRY
	 * @throws Exception
	 */
	public ActionForward initWorkTimeEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("Entering initWorkTimeEntry");
		WorkTimeEntryForm workTimeEntryForm = (WorkTimeEntryForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setUserId(request, workTimeEntryForm);
			initFields(workTimeEntryForm);
			setRequestedDataToForm(workTimeEntryForm);
			setEmployeeTypeListToForm(workTimeEntryForm);
			setWorkTimeListToForm(workTimeEntryForm);
		} catch (Exception e) {
			log.error("error initWorkTimeEntry...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				workTimeEntryForm.setErrorMessage(msg);
				workTimeEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving initWorkTimeEntry ");
	
		return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
	}
	
	private void setEmployeeTypeListToForm(WorkTimeEntryForm workTimeEntryForm) throws Exception {
		List<EmployeeKeyValueTO> listEmployeeType = EmployeeInfoHandler.getInstance().getEmployeeType();
		if (listEmployeeType != null && listEmployeeType.size() > 0) {
			workTimeEntryForm.setListEmployeeType(listEmployeeType);
		}
	}

	private void setRequestedDataToForm(WorkTimeEntryForm workTimeEntryForm) {
		List<WorkTimeTO> workList=new ArrayList<WorkTimeTO>();
		WorkTimeTO timeTO=new WorkTimeTO();
		timeTO.setName("Monday");
		workList.add(timeTO);
		WorkTimeTO timeTO1=new WorkTimeTO();
		timeTO1.setName("Tuesday");
		workList.add(timeTO1);
		WorkTimeTO timeTO2=new WorkTimeTO();
		timeTO2.setName("Wednesday");
		workList.add(timeTO2);
		WorkTimeTO timeTO3=new WorkTimeTO();
		timeTO3.setName("Thursday");
		workList.add(timeTO3);
		WorkTimeTO timeTO4=new WorkTimeTO();
		timeTO4.setName("Friday");
		workList.add(timeTO4);
		WorkTimeTO timeTO5=new WorkTimeTO();
		timeTO5.setName("Saturday");
		workList.add(timeTO5);
		WorkTimeTO timeTO6=new WorkTimeTO();
		timeTO6.setName("Sunday");
		workList.add(timeTO6);
		workTimeEntryForm.setWorkList(workList);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new  work time entry
	 * @return to mapping WORK_TIME_ENTRY
	 * @throws Exception
	 */
	public ActionForward addWorkTimeEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addWorkTimeEntry Action");
		WorkTimeEntryForm workEntryForm = (WorkTimeEntryForm) form;
		workEntryForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = workEntryForm.validate(mapping, request);
		validateTime(workEntryForm, errors);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				resetCheckBox(workEntryForm);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
			}
			isAdded = WorkTimeEntryHandler.getInstance().addWorkTimeEntry(workEntryForm, "add"); 
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.work.time.entry.exists"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
		}catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.work.time.entry.reactivate"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
		}catch (Exception e) {
			log.error("error in final submit of work time entry page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				workEntryForm.setErrorMessage(msg);
				workEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.employee.work.time.entry.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(workEntryForm);
			setRequestedDataToForm(workEntryForm);
			setWorkTimeListToForm(workEntryForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.employee.work.time.entry.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addAttributeMaster Action");
		return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
	}
	/**
	 * 
	 * @param workTimeEntryForm
	 */
	public void initFields(WorkTimeEntryForm workTimeEntryForm){
		workTimeEntryForm.setName(null);
		workTimeEntryForm.setInTimeFromHr(null);
		workTimeEntryForm.setInTimeFromMins(null);
		workTimeEntryForm.setInTimeToHrs(null);
		workTimeEntryForm.setInTimeToMins(null);
		workTimeEntryForm.setOutTimeFromHrs(null);
		workTimeEntryForm.setOutTimeFromMins(null);
		workTimeEntryForm.setOutTimeToHrs(null);
		workTimeEntryForm.setOutTimeToMins(null);
	}
	
	public void resetCheckBox(WorkTimeEntryForm workTimeForm){
		List<WorkTimeTO> checklist=workTimeForm.getWorkList();
		List<WorkTimeTO> newList=new ArrayList<WorkTimeTO>();
		Iterator<WorkTimeTO> itr=checklist.iterator();
		while (itr.hasNext()) {
			WorkTimeTO to = (WorkTimeTO) itr.next();
			to.setTempChecked(to.isChecked());
			newList.add(to);
		}
		workTimeForm.setWorkList(newList);
	}
	
	
	/**
	 * time validation. checking time format, start time should not be greater than end time.
	 * @param workTimeEntryForm
	 * @param errors
	 */
	private void validateTime(WorkTimeEntryForm workTimeForm, ActionErrors errors){
		List<WorkTimeTO> checklist=workTimeForm.getWorkList();
		Iterator<WorkTimeTO> itr=checklist.iterator();
		while (itr.hasNext()) {
			WorkTimeTO workTimeEntryForm = (WorkTimeTO) itr.next();
			if(!workTimeEntryForm.isChecked()){
				if(!CommonUtil.checkForEmpty(workTimeEntryForm.getInTimeFromHr())){
					errors.add("knowledgepro.employee.work.time.in.time.from.hour.required",new ActionError("knowledgepro.employee.work.time.in.time.from.hour.required"));
				}
				if(!CommonUtil.checkForEmpty(workTimeEntryForm.getInTimeToHrs())){
					errors.add("knowledgepro.employee.work.time.in.time.to.hour.required",new ActionError("knowledgepro.employee.work.time.in.time.to.hour.required"));
				}
				if(!CommonUtil.checkForEmpty(workTimeEntryForm.getOutTimeFromHrs())){
					errors.add("knowledgepro.employee.work.time.out.time.from.hour.required",new ActionError("knowledgepro.employee.work.time.out.time.from.hour.required"));
				}
				if(!CommonUtil.checkForEmpty(workTimeEntryForm.getOutTimeToHrs())){
					errors.add("knowledgepro.employee.work.time.out.time.to.hour.required",new ActionError("knowledgepro.employee.work.time.out.time.to.hour.required"));
				}
			
			
			String inTimeFromMins; 
			String inTimeToMins;
			String outTimeFromMins; 
			String outTimeToMins;
			
			if( workTimeEntryForm.getInTimeFromMins()!= null && !workTimeEntryForm.getInTimeFromMins().isEmpty()){
				inTimeFromMins = workTimeEntryForm.getInTimeFromMins();
			}
			else{
				inTimeFromMins = "00";
			}
			if(workTimeEntryForm.getInTimeToMins() != null && !workTimeEntryForm.getInTimeToMins().isEmpty()){
				inTimeToMins = workTimeEntryForm.getInTimeToMins();
			}
			else{
				inTimeToMins = "00";
			}					

			if( workTimeEntryForm.getOutTimeFromMins()!= null && !workTimeEntryForm.getOutTimeFromMins().isEmpty()){
				outTimeFromMins = workTimeEntryForm.getOutTimeFromMins();
			}
			else{
				outTimeFromMins = "00";
			}
			if(workTimeEntryForm.getOutTimeToMins() != null && !workTimeEntryForm.getOutTimeToMins().isEmpty()){
				outTimeToMins = workTimeEntryForm.getOutTimeToMins();
			}
			else{
				outTimeToMins = "00";
			}					

			
			
			String inTimeFrom = CommonUtil.getTime(workTimeEntryForm.getInTimeFromHr(), inTimeFromMins).trim();
			String inTimeTo = CommonUtil.getTime(workTimeEntryForm.getInTimeToHrs(), inTimeToMins).trim();

			String outTimeFrom = CommonUtil.getTime(workTimeEntryForm.getOutTimeFromHrs(), outTimeFromMins).trim();
			String outTimeTo = CommonUtil.getTime(workTimeEntryForm.getOutTimeToHrs(), outTimeToMins).trim();
			
			
			int inTimeFromInMins = 0;
			int inTimeToInMins = 0;
			int outTimeFromInMins = 0;
			int outTimeToInMins = 0;
			
			if(workTimeEntryForm.getInTimeFromHr()!= null && !workTimeEntryForm.getInTimeFromHr().trim().isEmpty()){
				inTimeFromInMins = Integer.parseInt(workTimeEntryForm.getInTimeFromHr()) * 60;
			}
			if(workTimeEntryForm.getInTimeToHrs()!= null && !workTimeEntryForm.getInTimeToHrs().trim().isEmpty()){
				inTimeToInMins = Integer.parseInt(workTimeEntryForm.getInTimeToHrs()) * 60;
			}
			
			if(workTimeEntryForm.getInTimeFromMins()!= null && !workTimeEntryForm.getInTimeFromMins().trim().isEmpty()){
				inTimeFromInMins = inTimeFromInMins + Integer.parseInt(workTimeEntryForm.getInTimeFromMins());
			}
			if(workTimeEntryForm.getInTimeToMins()!= null && !workTimeEntryForm.getInTimeToMins().trim().isEmpty()){
				inTimeToInMins = inTimeToInMins + Integer.parseInt(workTimeEntryForm.getInTimeToMins());
			}
			
			
			if(workTimeEntryForm.getOutTimeFromHrs()!= null && !workTimeEntryForm.getOutTimeFromHrs().trim().isEmpty()){
				outTimeFromInMins = Integer.parseInt(workTimeEntryForm.getOutTimeFromHrs()) * 60;
			}
			if(workTimeEntryForm.getOutTimeToHrs()!= null && !workTimeEntryForm.getOutTimeToHrs().trim().isEmpty()){
				outTimeToInMins = Integer.parseInt(workTimeEntryForm.getOutTimeToHrs()) * 60;
			}
			
			if(workTimeEntryForm.getOutTimeFromMins()!= null && !workTimeEntryForm.getOutTimeFromMins().trim().isEmpty()){
				outTimeFromInMins = outTimeFromInMins + Integer.parseInt(workTimeEntryForm.getOutTimeFromMins());
			}
			if(workTimeEntryForm.getOutTimeToMins()!= null && !workTimeEntryForm.getOutTimeToMins().trim().isEmpty()){
				outTimeToInMins = outTimeToInMins + Integer.parseInt(workTimeEntryForm.getOutTimeToMins());
			}		
			
			
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getInTimeFromHr()) && CommonUtil.checkForEmpty(workTimeEntryForm.getInTimeToHrs())){
				 if(inTimeFrom.equals(inTimeTo) ){
						errors.add("knowledgepro.employee.in.from.to.equal",new ActionError("knowledgepro.employee.in.from.to.equal"));
				 }
				
				 if(inTimeFromInMins > inTimeToInMins){
					errors.add("knowledgepro.employee.in.from.greater",new ActionError("knowledgepro.employee.in.from.greater"));
				 }
			}
			
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getOutTimeFromHrs()) && CommonUtil.checkForEmpty(workTimeEntryForm.getOutTimeToHrs())){
				 if(outTimeFrom.equals(outTimeTo) ){
						errors.add("knowledgepro.employee.out.from.to.equal",new ActionError("knowledgepro.employee.out.from.to.equal"));
				 }
				
				 if(outTimeFromInMins!=0 && outTimeToInMins!=0 && outTimeFromInMins > outTimeToInMins){
					errors.add("knowledgepro.employee.out.from.greater",new ActionError("knowledgepro.employee.out.from.greater"));
				 }
			}	
			
			if(inTimeToInMins!=0 && outTimeFromInMins!=0 && inTimeToInMins > outTimeFromInMins){
				errors.add("knowledgepro.employee.in.to.greater.out.from",new ActionError("knowledgepro.employee.in.to.greater.out.from"));
			}
			}
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getInTimeFromHr())){
				if(workTimeEntryForm.getInTimeFromHr().trim().equals("0") ||workTimeEntryForm.getInTimeFromHr().trim().equals("")){
					errors.add("knowledgepro.employee.work.time.in.time.from.hour.required",new ActionError("knowledgepro.employee.work.time.in.time.from.hour.required"));
				}
				if(Integer.parseInt(workTimeEntryForm.getInTimeFromHr())>=24){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					}
				}			
			}
			
			//time format checking
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getInTimeFromMins())){
				if(Integer.parseInt(workTimeEntryForm.getInTimeFromMins())>=60){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					}
				}			
			}		
			
			//time format checking
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getInTimeToHrs())){
				if(workTimeEntryForm.getInTimeToHrs().trim().equals("0") || workTimeEntryForm.getInTimeToHrs().trim().equals("")){
					errors.add("knowledgepro.employee.work.time.in.time.to.hour.required",new ActionError("knowledgepro.employee.work.time.in.time.to.hour.required"));
				}
				if(Integer.parseInt(workTimeEntryForm.getInTimeToHrs())>=24){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					}
				}			
			}		
			
			//time format checking
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getInTimeToMins())){
				if(Integer.parseInt(workTimeEntryForm.getInTimeToMins())>=60){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					}
				}			
			}
			
			
			
			
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getOutTimeFromHrs())){
				if(workTimeEntryForm.getOutTimeFromHrs().trim().equals("0") || workTimeEntryForm.getOutTimeFromHrs().trim().equals("")){
					errors.add("knowledgepro.employee.work.time.out.time.from.hour.required",new ActionError("knowledgepro.employee.work.time.out.time.from.hour.required"));
				}
				if(Integer.parseInt(workTimeEntryForm.getOutTimeFromHrs())>=24){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					}
				}			
			}
			
			//time format checking
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getOutTimeFromMins())){
				if(Integer.parseInt(workTimeEntryForm.getOutTimeFromMins())>=60){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					}
				}			
			}		
			
			//time format checking
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getOutTimeToHrs())){
				if(workTimeEntryForm.getOutTimeToHrs().trim().equals("0") || workTimeEntryForm.getOutTimeToHrs().trim().equals("")){
					errors.add("knowledgepro.employee.work.time.out.time.to.hour.required",new ActionError("knowledgepro.employee.work.time.out.time.to.hour.required"));
				}
				if(Integer.parseInt(workTimeEntryForm.getOutTimeToHrs())>=24){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					}
				}			
			}		
			
			//time format checking
			if(CommonUtil.checkForEmpty(workTimeEntryForm.getOutTimeToMins())){
				if(Integer.parseInt(workTimeEntryForm.getOutTimeToMins())>=60){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					}
				}			
			}
		}
		
		
	}	
	
	public ActionForward deleteWorkTimeEntry(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		WorkTimeEntryForm workTimeEntryForm = (WorkTimeEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int id=workTimeEntryForm.getId();
		boolean isDeleted=false;
		try
		{
			if(id> 0 )
			{
				isDeleted = WorkTimeEntryHandler.getInstance().deleteWorkTimeEntry(id);
			}
		}
		catch(Exception e)
		{
			log.error("error in deleteWorkTimeEntry...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				workTimeEntryForm.setErrorMessage(msg);
				workTimeEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(isDeleted){
			
				// success deleted
				ActionMessage message = new ActionMessage("knowledgepro.employee.work.time.entry.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				initFields(workTimeEntryForm);
				setRequestedDataToForm(workTimeEntryForm);
				setEmployeeTypeListToForm(workTimeEntryForm);
				setWorkTimeListToForm(workTimeEntryForm);
			} else {
				// failure error message.
				errors.add("error", new ActionError("knowledgepro.employee.work.time.entry.delete.failure"));
				saveErrors(request, errors);
			}
		
		log.debug("Exiting deleteWorkTimeEntry of WorkTimeEntryAction");
		return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
	}
	
	public ActionForward editWorkTimeEntry(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		WorkTimeEntryForm workTimeEntryForm = (WorkTimeEntryForm) form;
		try
		{
			workTimeEntryForm.setDuplId(workTimeEntryForm.getId());
			WorkTimeEntryHandler.getInstance().editWorkTimeEntry(workTimeEntryForm);
			setEmployeeTypeListToForm(workTimeEntryForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		}
		catch (Exception e) {
			String msg = super.handleApplicationException(e);
			workTimeEntryForm.setErrorMessage(msg);
			workTimeEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
		
	}
	
	public ActionForward updateWorkTimeEntry(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		log.debug("inside updateWorkTimeEntry Action");
		WorkTimeEntryForm workTimeEntryForm = (WorkTimeEntryForm) form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = workTimeEntryForm.validate(mapping, request);
		validateTime(workTimeEntryForm, errors);
		boolean isUpdated = false;
		try
		{if(isCancelled(request))
		{
			workTimeEntryForm.setDuplId(workTimeEntryForm.getId());
			WorkTimeEntryHandler.getInstance().editWorkTimeEntry(workTimeEntryForm);
			setEmployeeTypeListToForm(workTimeEntryForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		
		}else
			if(errors.isEmpty()){
				if(Integer.parseInt(workTimeEntryForm.getName())!=workTimeEntryForm.getDuplId()){
					IWorkTimeEntryTransaction iTransaction = WorkTimeEntryTransactionImpl.getInstance();
					iTransaction.isWorkTimeEntryDuplcated(workTimeEntryForm); 
				}
				List<WorkTimeTO> workTimeList = workTimeEntryForm.getWorkList();
				isUpdated= WorkTimeEntryHandler.getInstance().updateWorkTimeEntry(workTimeEntryForm,workTimeList);
				if(isUpdated)
				{
					ActionMessage message = new ActionMessage("knowledgepro.employee.work.time.entry.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					setRequestedDataToForm(workTimeEntryForm);
					setEmployeeTypeListToForm(workTimeEntryForm);
					setWorkTimeListToForm(workTimeEntryForm);
					initFields(workTimeEntryForm);
				}
				else 
				{
					errors.add("error", new ActionError("knowledgepro.employee.work.time.entry.update.failure"));
					saveErrors(request, errors);
				}
			}
			else
			{
				resetCheckBox(workTimeEntryForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				setEmployeeTypeListToForm(workTimeEntryForm);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
			}
			
			
		}catch (DuplicateException e1) {
			resetCheckBox(workTimeEntryForm);
			errors.add("error", new ActionError("knowledgepro.employee.work.time.entry.exists"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
		}catch (ReActivateException e1) {
			resetCheckBox(workTimeEntryForm);
			errors.add("error", new ActionError("knowledgepro.employee.work.time.entry.reactivate"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
		}catch(Exception e)
		{
			resetCheckBox(workTimeEntryForm);
			String msg = super.handleApplicationException(e);
			workTimeEntryForm.setErrorMessage(msg);
			workTimeEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
	}
	
	public void setWorkTimeListToForm(WorkTimeEntryForm workTimeEntryForm) throws Exception{
		
		List<EmpWorkTimeTO> empWorkTimeToList=WorkTimeEntryHandler.getInstance().getEmployeeTypeList();
		workTimeEntryForm.setEmpWorkTimeToList(empWorkTimeToList);
	}
	
	public ActionForward reActivateWorkTimeEntry(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		WorkTimeEntryForm workTimeEntryForm = (WorkTimeEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int id=workTimeEntryForm.getDuplId();
		boolean isReActivate=false;
		try
		{
			if(id> 0 )
			{
				isReActivate = WorkTimeEntryHandler.getInstance().reActivateWorkTimeEntry(id);
			}
		}
		catch(Exception e)
		{
			log.error("error in reActivateWorkTimeEntry...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				workTimeEntryForm.setErrorMessage(msg);
				workTimeEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(isReActivate){
			
				// success ReActivate
				ActionMessage message = new ActionMessage("knowledgepro.employee.work.time.entry.reActivate.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				initFields(workTimeEntryForm);
				setRequestedDataToForm(workTimeEntryForm);
				setEmployeeTypeListToForm(workTimeEntryForm);
				setWorkTimeListToForm(workTimeEntryForm);
			} else {
				// failure error message.
				errors.add("error", new ActionError("knowledgepro.employee.work.time.entry.reActivate.failure"));
				saveErrors(request, errors);
			}
		
		log.debug("Exiting reActivateWorkTimeEntry of WorkTimeEntryAction");
		return mapping.findForward(CMSConstants.WORK_TIME_ENTRY);
	}
}
