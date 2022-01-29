package com.kp.cms.actions.serviceLearning;

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
import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;
import com.kp.cms.forms.serviceLearning.ProgrammeEntryForm;
import com.kp.cms.handlers.serviceLearning.DepartmentNameEntryHandler;
import com.kp.cms.handlers.serviceLearning.ProgrammeEntryHandler;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;

public class ProgrammeEntryAction  extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ProgrammeEntryAction.class);
	
	public ActionForward initGetProgrameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProgrammeEntryForm programmeEntryForm = (ProgrammeEntryForm) form;
		programmeEntryForm.reset(mapping, request);
		//programmeEntryForm.setExtraCreditActivityType(null);
		HttpSession session=request.getSession();
		session.setAttribute("field","Caste");
		try{
			setDepartmentNameEntryToRequest(request,programmeEntryForm);
		    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
		    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
		
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				programmeEntryForm.setErrorMessage(msg);
				programmeEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);
	}
	
	public void setDepartmentNameEntryToRequest(HttpServletRequest request,ProgrammeEntryForm programmeEntryForm) throws Exception {
		List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
		programmeEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
		request.setAttribute("departmentNameEntryToList", departmentNameEntryToList);
		List<ExtraCreditActivityTypeTo> creditActivityTypeTos=DepartmentNameEntryHandler.getInstance().getActivityNames();
		programmeEntryForm.setActivityTypeTos(creditActivityTypeTos);
		log.debug("leaving setReligionListToRequest in Action");
	}
	
	public ActionForward addProgrammeNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProgrammeEntryForm programmeEntryForm = (ProgrammeEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isNameAdded = false;
		if(programmeEntryForm.getProgrammeCode()==null || programmeEntryForm.getProgrammeCode().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.servicelearing.programmeCode.required"));
		}
		if(programmeEntryForm.getProgrammeName()==null || programmeEntryForm.getProgrammeName().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.servicelearing.programmeName.required"));
		}
		if(Integer.parseInt(programmeEntryForm.getExtraCreditActivityType())==1){
			validateForm(errors,programmeEntryForm);
		}
		if (errors.isEmpty()) {
			String name=programmeEntryForm.getProgrammeName();
			try{
			setUserId(request, programmeEntryForm);
			isNameAdded = ProgrammeEntryHandler.getInstance().addProgrammeNameEntry(programmeEntryForm,"Add",request);
			setDepartmentNameEntryToRequest(request,programmeEntryForm);
		    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
		    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.servicelearing.programmename.exists",name));
					saveErrors(request, errors);
					setDepartmentNameEntryToRequest(request,programmeEntryForm);
				    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
				    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
					return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.servicelearing.programmename.addfailure.alreadyexist.reactivate",name));
					saveErrors(request, errors);
					//HttpSession session=request.getSession();
					//session.setAttribute("HostelNameEntry",attendanceEntryDayForm.getAttendanceEntryDay());
					setDepartmentNameEntryToRequest(request,programmeEntryForm);
				    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
				    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
					return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				programmeEntryForm.setErrorMessage(msg);
				programmeEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isNameAdded) {
				programmeEntryForm.reset(mapping, request);
				ActionMessage message = new ActionMessage("knowledgepro.servicelearing.programmename.addsuccess", programmeEntryForm.getProgramName());
				messages.add("messages", message);
				saveMessages(request, messages);
				programmeEntryForm.reset(mapping, request);
				setDepartmentNameEntryToRequest(request,programmeEntryForm);
			    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
			    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
			    programmeEntryForm.setExtraCreditActivityType(null);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.servicelearing.programmename.addfailure",  programmeEntryForm.getProgrammeName()));
				saveErrors(request, errors);
				setDepartmentNameEntryToRequest(request,programmeEntryForm);
			    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
			    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
			}
		} else {
			saveErrors(request, errors);
			setDepartmentNameEntryToRequest(request,programmeEntryForm);
		    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
		    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
			return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);
		}
		
		
		return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);
	}

	public ActionForward deleteProgrammeNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProgrammeEntryForm programmeEntryForm = (ProgrammeEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int id =Integer.parseInt(programmeEntryForm.getId());
		String name=programmeEntryForm.getProgrammeName();
		boolean isDeleted=false;
		try{
			setUserId(request, programmeEntryForm);
			isDeleted = ProgrammeEntryHandler.getInstance().deleteProgrammeNameEntry(id, false, programmeEntryForm);
				
		}catch (Exception e) {
			log.error("error in submit of delete page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programmeEntryForm.setErrorMessage(msg);
				programmeEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				programmeEntryForm.setErrorMessage(msg);
				programmeEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		
		if (isDeleted) {
			ActionMessage message = new ActionMessage("knowledgepro.servicelearing.programmename.deletesuccess", name);
			messages.add("messages", message);
			saveMessages(request, messages);
			programmeEntryForm.reset(mapping, request);
			setDepartmentNameEntryToRequest(request,programmeEntryForm);
		    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
		    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
		    programmeEntryForm.setExtraCreditActivityType(null);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.servicelearing.programmename.deletefailure",name));
			saveErrors(request, errors);
			setDepartmentNameEntryToRequest(request,programmeEntryForm);
		    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
		    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
		}
		return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);
	}
	
	public ActionForward editProgrmmeNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ProgrammeEntryForm programmeEntryForm = (ProgrammeEntryForm) form;
		setUserId(request, programmeEntryForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		try {
			ProgrammeEntryHandler.getInstance().editProgrammeNameEntry(programmeEntryForm);
			request.setAttribute("department", "edit");

		} catch (BusinessException businessException) {
			log.info("Exception editMenus");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			programmeEntryForm.setErrorMessage(msg);
			programmeEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//load the details for module drop down and menus list.
		setDepartmentNameEntryToRequest(request,programmeEntryForm);
	    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
	    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
	    if(Integer.parseInt(programmeEntryForm.getExtraCreditActivityType())==1){
	    	return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);
	    }else{
	    	return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY_INTERN_WORK);
	    }
	}
	
	public ActionForward updateProgrammeNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ProgrammeEntryForm programmeEntryForm = (ProgrammeEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = programmeEntryForm.validate(mapping, request);
		String name=programmeEntryForm.getProgrammeName();
		boolean isUpdate = false;
		//if (errors.isEmpty()) {
			try{
				if (errors.isEmpty()) {
					setDepartmentNameEntryToRequest(request,programmeEntryForm);
				    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
				    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
				}
			setUserId(request, programmeEntryForm);
			
			isUpdate=ProgrammeEntryHandler.getInstance().addProgrammeNameEntry(programmeEntryForm,"Edit",request);

			}catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.servicelearing.programmename.exists"));
				saveErrors(request, errors);
				
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError("knowledgepro.servicelearing.programmename.addfailure.alreadyexist.reactivate"));
				saveErrors(request, errors);
				
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);
			} catch (Exception e) {
				log.error("error in update admitted through page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					request.setAttribute("department", "edit");
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					programmeEntryForm.setErrorMessage(msg);
					programmeEntryForm.setErrorStack(e.getMessage());
					request.setAttribute("department", "edit");
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		
		
		if (isUpdate) {
			ActionMessage message = new ActionMessage("knowledgepro.servicelearing.programmename.updatesuccess", programmeEntryForm.getProgrammeName());
			messages.add("messages", message);
			saveMessages(request, messages);
			programmeEntryForm.reset(mapping, request);
			setDepartmentNameEntryToRequest(request,programmeEntryForm);
		    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
		    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.servicelearing.programmename.updatefailure",  programmeEntryForm.getProgrammeName()));
			saveErrors(request, errors);
			setDepartmentNameEntryToRequest(request,programmeEntryForm);
		    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
		    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
		}
		return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);
	}

	public ActionForward reActivateProgrammeNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProgrammeEntryForm programmeEntryForm = (ProgrammeEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ProgrammeEntryBo programmeEntryBo=(ProgrammeEntryBo)request.getSession().getAttribute("ProgrammeEntryBo");
		boolean isActivate = false;
		try{
			if(programmeEntryForm.getDupId()!=null){
				setUserId(request, programmeEntryForm);
				isActivate=ProgrammeEntryHandler.getInstance().reActivateProgrammeNameEntry(programmeEntryBo,  programmeEntryForm.getUserId());
			}
		}catch (Exception e) {
			log.error("error in delete Department page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				setDepartmentNameEntryToRequest(request,programmeEntryForm);
			    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
			    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programmeEntryForm.setErrorMessage(msg);
				programmeEntryForm.setErrorStack(e.getMessage());
				setDepartmentNameEntryToRequest(request,programmeEntryForm);
			    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
			    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		if(isActivate){
			ActionMessage message = new ActionMessage("knowledgepro.servicelearing.programmename.activate");
			messages.add("messages",message);
			saveMessages(request, messages);
		}
		else{
			errors.add("error", new ActionError("knowledgepro.servicelearing.programmename.activatefailure"));
			saveErrors(request, errors);
		}
		setDepartmentNameEntryToRequest(request,programmeEntryForm);
	    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
	    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
		return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY);
	}
	public ActionForward workAndInternshipMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProgrammeEntryForm programmeEntryForm = (ProgrammeEntryForm) form;
		//programmeEntryForm.reset(mapping, request);
		
		HttpSession session=request.getSession();
		session.setAttribute("field","Caste");
		try{
			setDepartmentNameEntryToRequest(request,programmeEntryForm);
		    List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
		    programmeEntryForm.setProgrammeEntryToList(programmeEntryToList);
		
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				programmeEntryForm.setErrorMessage(msg);
				programmeEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		return mapping.findForward(CMSConstants.PROGRAMME_NAME_ENTRY_INTERN_WORK);
	}
	private void validateForm(ActionErrors errors,
			ProgrammeEntryForm programmeEntryForm) {
		if(programmeEntryForm.getStartDate()==null || programmeEntryForm.getStartDate().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.servicelearing.startDate.required"));
		}
		if(programmeEntryForm.getEndDate()==null || programmeEntryForm.getEndDate().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.servicelearing.endDate.required"));
		}
		if(programmeEntryForm.getDeptId()==null || programmeEntryForm.getDeptId().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.servicelearing.deptId.required"));
		}
		if((programmeEntryForm.getStartHours().trim().equals("00") || programmeEntryForm.getStartHours().trim().equals("0")) && (programmeEntryForm.getStartMins().trim().equals("00") && programmeEntryForm.getStartMins().trim().equals("0")))
		{
			errors.add("error", new ActionError("knowledgepro.new.scheduled.hours.invalid"));
		}
		if(programmeEntryForm.getStartHours()!=null && !programmeEntryForm.getStartHours().isEmpty())
		{
			Integer smsH=Integer.parseInt(programmeEntryForm.getStartHours());
			if(smsH>24)
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.hours.invalid"));
			}
		}
		if(programmeEntryForm.getStartMins()!=null && !programmeEntryForm.getStartMins().isEmpty())
		{
			Integer smsM=Integer.parseInt(programmeEntryForm.getStartMins());
			if(smsM>59)
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.mins.invalid"));
			}
		}
	}
}
