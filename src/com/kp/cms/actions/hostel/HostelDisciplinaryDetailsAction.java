package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelDisciplinaryDetailsForm;
import com.kp.cms.handlers.hostel.HostelDisciplinaryDetailsHandler;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;

public class HostelDisciplinaryDetailsAction extends BaseDispatchAction {	
	
	private static final Logger log = Logger.getLogger(HostelDisciplinaryDetailsAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDisciplinaryDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		log.info("Start of initDisciplinaryDetails in HostelDisciplinaryDetailsAction class");
		HostelDisciplinaryDetailsForm detailsForm=(HostelDisciplinaryDetailsForm)form;		
		try {
			setDisciplinesListToRequest(request);
			detailsForm.clear();
			detailsForm.clearStudentDetails();
		} catch (Exception e) {
			String msg=super.handleApplicationException(e);
			detailsForm.setErrorMessage(msg);
			detailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of initDisciplinaryDetails in HostelDisciplinaryDetailsAction class");
		return mapping.findForward(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_PAGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assignListToFormAcc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.info("Entering into -- HostelDisciplinaryDetailsAction --- assignListToForm");
		HostelDisciplinaryDetailsForm hostelForm	= (HostelDisciplinaryDetailsForm) form;
		try {
			List<HlDisciplinaryDetailsTO> disciplineList = HostelDisciplinaryDetailsHandler.getInstance().getDisciplineDetailsAcc(hostelForm);
			hostelForm.setDisciplineList(disciplineList);
			
		} catch (Exception e) {
			log.error("Error in assignListToForm of HostelDisciplinaryDetails Action",e);
				String msg = super.handleApplicationException(e);
				hostelForm.setErrorMessage(msg);
				hostelForm.setErrorStack(e.getMessage());
			}
		log.info("Leaving from -- HostelDisciplinaryDetailsAction --- assignListToForm");
		setDisciplinesListToRequest(request);
		hostelForm.setStatus("Yes");
		return mapping.findForward(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_PAGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addHostelStudentDisciplineDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Start of addHostelStudentDisciplineDetails in HostelDisciplinaryDetailsAction class");
		HostelDisciplinaryDetailsForm detailsForm=(HostelDisciplinaryDetailsForm)form;
		 ActionErrors errors = detailsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		addErrors(request,errors);
		request.setAttribute("disciplinaryOperation", "add");
		boolean detialsAdded = false;
		try {
			if(errors.isEmpty()){
				setUserId(request, detailsForm);

				HlAdmissionBo isStudent = HostelDisciplinaryDetailsHandler.getInstance().checkStudentPresent(detailsForm.getYear(), detailsForm.getRegisterNo());
				
				if(isStudent == null)
				{
					errors.add("errors", new ActionError("knowledgepro.hostel.disciplinary.action.details.norecords"));
					saveErrors(request, errors);
					setDisciplinesListToRequest(request);
				}else{
					detailsForm.setBoId(isStudent.getId());
					detialsAdded=HostelDisciplinaryDetailsHandler.getInstance().addHostelStudentDisciplineDetails(detailsForm);
					if (detialsAdded) {
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.DISCIPLINARY_DETAILS_ADD_SUCCESS));
						saveMessages(request, messages);
						List<HlDisciplinaryDetailsTO> disciplineList = HostelDisciplinaryDetailsHandler.getInstance().getDisciplineDetailsAcc(detailsForm);
						detailsForm.setDisciplineList(disciplineList);
						setDisciplinesListToRequest(request);
						detailsForm.clearAfterAdd();
						return mapping.findForward(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_PAGE);
					} else {
						errors.add("errors", new ActionError(CMSConstants.DISCIPLINARY_DETAILS_ADD_FAILED));
						setDisciplinesListToRequest(request);
						saveErrors(request, errors);
					}
				}
			} else{
				setStudentInfoToRequest(request, detailsForm);
				setDisciplinesListToRequest(request);
				saveErrors(request, errors);
				detailsForm.setStatus(null);
				return mapping.findForward(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_PAGE);
			}
		
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);			
			detailsForm.setErrorMessage(msg);
			detailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("End of addHostelStudentDisciplineDetails in HostelDisciplinaryDetailsAction class");
		/* code added by sudhir*/
		List<HlDisciplinaryDetailsTO> disciplineList = HostelDisciplinaryDetailsHandler.getInstance().getDisciplineDetailsAcc(detailsForm);
		detailsForm.setDisciplineList(disciplineList);
		if(disciplineList==null || disciplineList.isEmpty())
		detailsForm.clearStudentDetails();
		/*-------------------*/
		return mapping.findForward(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_PAGE);
	}	
	
	
	public void setDisciplinesListToRequest(HttpServletRequest request) throws Exception {
		log.info("Start of setDisciplinesListToRequest in HostelDisciplinaryDetailsAction class");
		List<HlDisciplinaryDetailsTO> hostelDisciplinesTOList=HostelDisciplinaryDetailsHandler.getInstance().getHostelDisciplinesList();
		request.setAttribute("hostelDisciplines", hostelDisciplinesTOList);
		log.info("End of setDisciplinesListToRequest in HostelDisciplinaryDetailsAction class");
	}
	
	/**
	 * @param request
	 * @param form
	 * @throws Exception
	 */
	public void setStudentInfoToRequest(HttpServletRequest request, ActionForm form) throws Exception {
		
		HostelDisciplinaryDetailsForm detailsForm=(HostelDisciplinaryDetailsForm)form;
		HlAdmissionBo hlAdmissionBo=(HlAdmissionBo) HostelDisciplinaryDetailsHandler.getInstance().verifyRegisterNumberAndGetNameAcc1(detailsForm);
		if(hlAdmissionBo!=null)
		{
			request.setAttribute("msg","notNull");
			if(hlAdmissionBo.getStudentId()!=null && hlAdmissionBo.getStudentId().getAdmAppln()!=null && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData()!=null){
				request.setAttribute("studentName", hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				detailsForm.setStudentName(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			}
			else
				request.setAttribute("studentName", " ");
			if(hlAdmissionBo.getRoomId()!=null){
				request.setAttribute("studentRoomNo", hlAdmissionBo.getRoomId().getName());
				detailsForm.setStudentRoomNo(hlAdmissionBo.getRoomId().getName());
			}
			else
				request.setAttribute("studentRoomNo", " ");
			if(hlAdmissionBo.getBedId()!=null){
				request.setAttribute("studentBedNo", hlAdmissionBo.getBedId().getBedNo());
				detailsForm.setStudentBedNo(hlAdmissionBo.getBedId().getBedNo());
			}
			else
				request.setAttribute("studentBedNo", " ");
			if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlBlock()!=null){
				request.setAttribute("studentBlock", hlAdmissionBo.getRoomId().getHlBlock().getName());
				detailsForm.setStudentBlock(hlAdmissionBo.getRoomId().getHlBlock().getName());
			}
			else
				request.setAttribute("studentBlock", " ");
			if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlUnit()!=null){
				request.setAttribute("studentUnit", hlAdmissionBo.getRoomId().getHlUnit().getName());
				detailsForm.setStudentUnit(hlAdmissionBo.getRoomId().getHlUnit().getName());
			}
			else
				request.setAttribute("studentUnit", " ");
			if(hlAdmissionBo.getStudentId()!=null && hlAdmissionBo.getStudentId().getClassSchemewise()!=null && hlAdmissionBo.getStudentId().getClassSchemewise().getClasses()!=null){
				request.setAttribute("studentClass", hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName());
				detailsForm.setStudentClass(hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName());
			}
			else
				request.setAttribute("studentClass", " ");
			if(hlAdmissionBo.getHostelId()!=null){
				request.setAttribute("studentHostel", hlAdmissionBo.getHostelId().getName());
				detailsForm.setStudentHostel(hlAdmissionBo.getHostelId().getName());
			}
			else
				request.setAttribute("studentHostel", " ");
		}
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editHostelStudentDisciplineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into---- HostelDisciplinaryDetailsAction --- editHostelStudentDisciplineDetails");
		HostelDisciplinaryDetailsForm hldForm=(HostelDisciplinaryDetailsForm)form;
		
		try {
			hldForm.clear();
			/**
			 * Get the particular row based on the id while clicking edit button
			 */
			HlDisciplinaryDetailsTO hlDisciplinaryDetailsTO=HostelDisciplinaryDetailsHandler.getInstance().getDetailsonId(hldForm.getId());
				
				if(hlDisciplinaryDetailsTO!=null){
						hldForm.setDescription(hlDisciplinaryDetailsTO.getDescription());
						hldForm.setRegisterNo(hlDisciplinaryDetailsTO.getRegisterNo());
						hldForm.setYear(hlDisciplinaryDetailsTO.getYear());
						hldForm.setDisciplineId(String.valueOf(hlDisciplinaryDetailsTO.getDisciplinaryTypeTO().getId()));
						hldForm.setDate(hlDisciplinaryDetailsTO.getDate());
				}
				
			setStudentInfoToRequest(request, hldForm);	
			request.setAttribute("disciplinaryOperation", CMSConstants.EDIT_OPERATION);
			setDisciplinesListToRequest(request);
		} catch (Exception e) {
			log.error("Error in editing HostelDisciplinaryDetailsAction in Action",e);
				String msg = super.handleApplicationException(e);
				hldForm.setErrorMessage(msg);
				hldForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		hldForm.setStatus(null);
		log.info("Leaving from-- HostelDisciplinaryDetailsAction --- editHostelStudentDisciplineDetails");
		return mapping.findForward(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_PAGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateHostelStudentDisciplineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- HostelDisciplinaryDetailsAction --- updateHostelStudentDisciplineDetails");
		HostelDisciplinaryDetailsForm hldForm=(HostelDisciplinaryDetailsForm)form; 
		 ActionErrors errors = hldForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty())
			{
				setUserId(request, hldForm);
				HlAdmissionBo isStudent = HostelDisciplinaryDetailsHandler.getInstance().checkStudentPresent(hldForm.getYear(), hldForm.getRegisterNo());
				
				if(isStudent != null){
				boolean isUpdated;
						hldForm.setBoId(isStudent.getId());
						isUpdated=HostelDisciplinaryDetailsHandler.getInstance().updateHostelStudentDisciplineDetails(hldForm);
						
						//If update is successful then add the success message else show the error message
						
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_UPDATE_SUCCESS));
							saveMessages(request, messages);
							hldForm.clearStudentDetails();
							//hldForm.clear();
							//setDisciplinesListToRequest(request);
							//return mapping.findForward(CMSConstants.INIT_EDIT_DISCIPLINARY_ACTION);				
						}
						else {
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_UPDATE_FAILED));
							saveErrors(request, errors);
							//setDisciplinesListToRequest(request);
							//return mapping.findForward(CMSConstants.INIT_EDIT_DISCIPLINARY_ACTION);
						}
				}else{
					errors.add("errors", new ActionError("knowledgepro.fee.register.no.not.present"));
					saveErrors(request, errors);
					request.setAttribute("disciplinaryOperation", CMSConstants.EDIT_OPERATION);
				}

			}
		else{
			request.setAttribute("disciplineOperation",CMSConstants.EDIT_OPERATION);
		}
		}catch (Exception e) {
			log.error("Error in updating HostelDisciplinaryDetailsAction in updateHostelStudentDisciplineDetails",e);
				String msg = super.handleApplicationException(e);
				hldForm.setErrorMessage(msg);
				hldForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		/* code added by sudhir */
		List<HlDisciplinaryDetailsTO> disciplineList =HostelDisciplinaryDetailsHandler.getInstance().searchDisciplinaryDetailsByRegNoAndAcademicYear(hldForm);
		if(disciplineList!=null && !disciplineList.isEmpty()){
			hldForm.setDisciplineList(disciplineList);
		}
		/*--------------------*/
		log.info("Leaving from --- HostelDisciplinaryDetailsAction --- updateHostelStudentDisciplineDetails");
		saveErrors(request, errors);
		//setDisciplinesListToRequest(request);
		return mapping.findForward(CMSConstants.INIT_EDIT_DISCIPLINARY_ACTION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteHostelStudentDisciplineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into--- HostelDisciplinaryDetailsAction --- deleteHostelStudentDisciplineDetails");
		HostelDisciplinaryDetailsForm hldForm=(HostelDisciplinaryDetailsForm)form;
		 ActionErrors errors = hldForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			setUserId(request, hldForm);
			int disciplineId = hldForm.getId();
			String userId=hldForm.getUserId();
			boolean isDisciplineFormDeleted;
			/**
			 * Request for deleting a guidelinesEntry based on the Id
			 */
			isDisciplineFormDeleted = HostelDisciplinaryDetailsHandler.getInstance().deleteHostelStudentDisciplineDetails(disciplineId, userId);
			//If delete operation is success then append the success message.
			if (isDisciplineFormDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.DISCIPLINARY_DETAILS_DELETE_SUCCESS));
				saveMessages(request, messages);
				//hldForm.clear();
				//setDisciplinesListToRequest(request);
			}
			//If delete operation is failure then add the error message.
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DISCIPLINARY_DETAILS_DELETE_FAILED));
				saveErrors(request, errors);
				//setDisciplinesListToRequest(request);
			}
		} catch (Exception e) {
			log.error("Error in deleting HostelDisciplinaryDetails in HostelDisciplinaryDetails Action",e);
				String msg = super.handleApplicationException(e);
				hldForm.setErrorMessage(msg);
				hldForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		/* code added by sudhir */
			List<HlDisciplinaryDetailsTO> disciplineList =HostelDisciplinaryDetailsHandler.getInstance().searchDisciplinaryDetailsByRegNoAndAcademicYear(hldForm);
			if(disciplineList!=null && !disciplineList.isEmpty()){
				hldForm.setDisciplineList(disciplineList);
			}
		log.info("Leaving from-- HostelDisciplinaryDetailsAction --- deleteHostelStudentDisciplineDetails");
		return mapping.findForward(CMSConstants.INIT_EDIT_DISCIPLINARY_ACTION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewHostelStudentDisciplineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into---- HostelDisciplinaryDetailsAction --- viewHostelStudentDisciplineDetails");
		HostelDisciplinaryDetailsForm hldForm=(HostelDisciplinaryDetailsForm)form;
		hldForm.clear();
		hldForm.clearStudentDetails();
		try {
			/**
			 * Get the particular row based on the id while clicking edit button
			 */
			HlDisciplinaryDetailsTO hlDisciplinaryDetailsTO=HostelDisciplinaryDetailsHandler.getInstance().getDetailsonId(hldForm.getId());
				
				if(hlDisciplinaryDetailsTO!=null){
						hldForm.setDescription(hlDisciplinaryDetailsTO.getDescription());
						hldForm.setRegisterNo(hlDisciplinaryDetailsTO.getRegisterNo());
						hldForm.setYear(hlDisciplinaryDetailsTO.getYear());
						hldForm.setDisciplineId(String.valueOf(hlDisciplinaryDetailsTO.getDisciplinaryTypeTO().getId()));
						hldForm.setDisciplineName(String.valueOf(hlDisciplinaryDetailsTO.getDisciplinaryTypeTO().getName()));
						hldForm.setDate(hlDisciplinaryDetailsTO.getDate());
				}
				
			setStudentInfoToRequest(request, hldForm);	
			//request.setAttribute("disciplinaryOperation", CMSConstants.EDIT_OPERATION);
			setDisciplinesListToRequest(request);
		} catch (Exception e) {
			log.error("Error in editing HostelDisciplinaryDetailsAction in Action",e);
				String msg = super.handleApplicationException(e);
				hldForm.setErrorMessage(msg);
				hldForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- HostelDisciplinaryDetailsAction --- viewHostelStudentDisciplineDetails");
		return mapping.findForward(CMSConstants.VIEW_HOSTEL_DISCIPLINARY_DETAILS_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEditDisciplinaryDetailsAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelDisciplinaryDetailsForm hldForm=(HostelDisciplinaryDetailsForm)form;
		hldForm.clear();
		hldForm.clearStudentDetails();
		/*int year = CurrentAcademicYear.getInstance().getAcademicyear();
		hldForm.setYear(String.valueOf(year));*/
		return mapping.findForward(CMSConstants.INIT_EDIT_DISCIPLINARY_ACTION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchHostelStudentDisciplineDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelDisciplinaryDetailsForm hldForm=(HostelDisciplinaryDetailsForm)form;
		 ActionErrors errors = hldForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				List<HlDisciplinaryDetailsTO> disciplineList =HostelDisciplinaryDetailsHandler.getInstance().searchDisciplinaryDetailsByRegNoAndAcademicYear(hldForm);
				if(disciplineList!=null && !disciplineList.isEmpty()){
					hldForm.setDisciplineList(disciplineList);
				}else{
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			log.error("Error in assignListToForm of HostelDisciplinaryDetails Action",e);
			String msg = super.handleApplicationException(e);
			hldForm.setErrorMessage(msg);
			hldForm.setErrorStack(e.getMessage());
		}
		return mapping.findForward(CMSConstants.INIT_EDIT_DISCIPLINARY_ACTION);
	}
	

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assignListToFormCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.info("Entering into -- HostelDisciplinaryDetailsAction --- assignListToForm");
		HostelDisciplinaryDetailsForm hostelForm	= (HostelDisciplinaryDetailsForm) form;
		try {
			List<HlDisciplinaryDetailsTO> disciplineList = HostelDisciplinaryDetailsHandler.getInstance().getDisciplineDetailsAcc(hostelForm);
			hostelForm.setDisciplineList(disciplineList);
			
		} catch (Exception e) {
			log.error("Error in assignListToForm of HostelDisciplinaryDetails Action",e);
				String msg = super.handleApplicationException(e);
				hostelForm.setErrorMessage(msg);
				hostelForm.setErrorStack(e.getMessage());
			}
		log.info("Leaving from -- HostelDisciplinaryDetailsAction --- assignListToForm");
		setDisciplinesListToRequest(request);
		hostelForm.setStatus("Yes");
		hostelForm.clearAfterAdd();
		return mapping.findForward(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_PAGE);
    }
}
