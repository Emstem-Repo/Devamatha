package com.kp.cms.actions.admission;


import java.util.Calendar;
import java.util.Iterator;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.CertificateCourseEntryForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.CertificateCourseEntryHandler;
import com.kp.cms.handlers.admission.GroupEntryHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.admission.CCGroupTo;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class CertificateCourseEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CertificateCourseEntryAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCertificateCourseEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("Entering certificate course entry");
		CertificateCourseEntryForm certificateCourseEntryForm = (CertificateCourseEntryForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			certificateCourseEntryForm.reset(mapping, request);
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			setTeacherMapToForm(request,certificateCourseEntryForm);
			setSubjectListToRequest(request);
			setCertificateCourseGroupList(certificateCourseEntryForm);
		} catch (Exception e) {
			log.error("error in initCertificateCourseEntry method", e);
			String msg = super.handleApplicationException(e);
			certificateCourseEntryForm.setErrorMessage(msg);
			certificateCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving initCertificateCourseEntry ");
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
	}
	/**
	 * @param request
	 * @param certificateCourseEntryForm
	 * @throws Exception
	 */
	private void setTeacherMapToForm(HttpServletRequest request,
			CertificateCourseEntryForm certificateCourseEntryForm)throws Exception {
		Map<Integer, String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getTeacherDepartmentsName();
			/*UserInfoHandler.getInstance().getTeachingStaffByEmployeeName();*/
		certificateCourseEntryForm.setTeachersMap(teachersMap);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addCerficateCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("inside addCerficateCourse Action");
		CertificateCourseEntryForm certificateCourseEntryForm = (CertificateCourseEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = certificateCourseEntryForm.validate(mapping, request);
		boolean isAdded;
		validateCertificateCourse(certificateCourseEntryForm,errors);
		validateTime(certificateCourseEntryForm, errors);
		ValidateMaxIntake(certificateCourseEntryForm,errors);
		
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramTypeListToRequest(request);
 				setCertificateCourseListToRequest(request,certificateCourseEntryForm);
				setSubjectListToRequest(request);
				return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
			}
			setUserId(request, certificateCourseEntryForm);
			isAdded = CertificateCourseEntryHandler.getInstance().addCerticateCourse(certificateCourseEntryForm); 
			setProgramTypeListToRequest(request); 
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			setSubjectListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.course.name.exists"));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request); 
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			setprogramMapToRequest(request, certificateCourseEntryForm);
			setSubjectListToRequest(request);
			return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.CERTIFICATE_COURSE_REACTIVATE));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request); 
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			setSubjectListToRequest(request);
			return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit addCerficateCourse...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				certificateCourseEntryForm.setErrorMessage(msg);
				certificateCourseEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.certificate.course.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			certificateCourseEntryForm.reset(mapping, request);
			setCertificateCourseGroupList(certificateCourseEntryForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.certificate.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addCerficateCourse Action");
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
	}
	
	
	
	/**
	 * @param certificateCourseEntryForm
	 * @param errors
	 * @throws Exception
	 */
	private void ValidateMaxIntake( CertificateCourseEntryForm certificateCourseEntryForm, ActionErrors errors) throws Exception {
		if(certificateCourseEntryForm.getMaxIntake()!=null && !certificateCourseEntryForm.getMaxIntake().isEmpty() && CommonUtil.isValidDecimal(certificateCourseEntryForm.getMaxIntake())){
			List<CCGroupTo> toList=certificateCourseEntryForm.getGroupList1();
			int maxIntake=0;
			for (Iterator iterator = toList.iterator(); iterator.hasNext();) {
				CCGroupTo to= (CCGroupTo) iterator.next();
				if(to.getMaxInTake()!=null && !to.getMaxInTake().isEmpty()){
				if(!CommonUtil.isValidDecimal(to.getMaxInTake())){
					if (errors.get(CMSConstants.MAX_INTAKE_VALIDDECIMAL) != null&& !errors.get(CMSConstants.MAX_INTAKE_VALIDDECIMAL).hasNext()) {
						errors.add(CMSConstants.MAX_INTAKE_VALIDDECIMAL,new ActionError(CMSConstants.MAX_INTAKE_VALIDDECIMAL));
					}
				}else{
					maxIntake=maxIntake+Integer.parseInt(to.getMaxInTake());
				}
				}
			}
			if(maxIntake!=Integer.parseInt(certificateCourseEntryForm.getMaxIntake()))
				errors.add("knowledgepro.admin.certificateCourse.valid.maxintake",new ActionError("knowledgepro.admin.certificateCourse.valid.maxintake"));
			
		}
	}
	/**
	 * @param certificateCourseEntryForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateCertificateCourse(CertificateCourseEntryForm certificateCourseEntryForm,ActionErrors errors) throws Exception {
		//if(certificateCourseEntryForm.getIsOptional()!=null && !certificateCourseEntryForm.getIsOptional().isEmpty()){
			
			//if(certificateCourseEntryForm.getIsOptional().equalsIgnoreCase("false")){
				/*if(certificateCourseEntryForm.getProgramTypeId()==null || certificateCourseEntryForm.getProgramTypeId().isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","program Type"));
				}
				if(certificateCourseEntryForm.getProgramId()==null || certificateCourseEntryForm.getProgramId().isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","program"));
				}*/
		//	}
			
		//}
		if(certificateCourseEntryForm.getDescription()!=null && !certificateCourseEntryForm.getDescription().isEmpty() && certificateCourseEntryForm.getDescription().length()>5000){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.cc.description.length"));
		}
	}
	/**
	 * 
	 * @param request
	 *            This method sets the program type list to Request useful in
	 *            populating in program type selection.
	 * @throws Exception
	 */
	public void setProgramTypeListToRequest(HttpServletRequest request)	throws Exception {
		log.debug("inside setProgramTypeListToRequest");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.debug("leaving setProgramTypeListToRequest");
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	public void setCertificateCourseListToRequest(HttpServletRequest request,CertificateCourseEntryForm certificateCourseEntryForm) throws Exception {
		log.debug("inside setCertificateCourseListToRequest");
		if (certificateCourseEntryForm.getAcademicYear() == null
				|| certificateCourseEntryForm.getAcademicYear().isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}// end
			List<CertificateCourseTO> courseList = CertificateCourseEntryHandler.getInstance().getActiveCourses1(currentYear,certificateCourseEntryForm.getSemType());
			request.setAttribute("courseList", courseList);
		} else {
			int year = Integer.parseInt(certificateCourseEntryForm.getAcademicYear().trim());
			List<CertificateCourseTO> courseList = CertificateCourseEntryHandler.getInstance().getActiveCourses1(year,certificateCourseEntryForm.getSemType());
			request.setAttribute("courseList", courseList);
		}
		log.debug("leaving setCertificateCourseListToRequest");
	}
	/**
	 * @param request
	 * @param certificateCourseEntryForm
	 * @throws Exception
	 */
	public void setCertificateCourseGroupList(CertificateCourseEntryForm certificateCourseEntryForm) throws Exception {
		log.debug("inside setCertificateCourseListToRequest");
		List<CCGroupTo> groupList=GroupEntryHandler.getInstance().getGroupList();
		certificateCourseEntryForm.setGroupList1(groupList);
		log.debug("leaving setCertificateCourseListToRequest");
	}
	
	/**
	 * 
	 * @param request
	 * @param courseForm
	 */
	public void setprogramMapToRequest(HttpServletRequest request, CertificateCourseEntryForm certificateCourseEntryForm) {
		if (certificateCourseEntryForm.getProgramTypeId() != null
				&& !(certificateCourseEntryForm.getProgramTypeId().isEmpty())) {
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(certificateCourseEntryForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
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
	public ActionForward deleteCertificateCourse(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		log.debug("insidedeleteCertificateCourse: Action");
		CertificateCourseEntryForm certificateCourseEntryForm = (CertificateCourseEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (certificateCourseEntryForm.getId() != 0) {
			int id = certificateCourseEntryForm.getId();
			isDeleted = CertificateCourseEntryHandler.getInstance().deleteCertificateCourse(id, false, certificateCourseEntryForm);
		}
			setProgramTypeListToRequest(request); 
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			setSubjectListToRequest(request);
			setprogramMapToRequest(request, certificateCourseEntryForm);
		} catch (Exception e) {
			errors.add("error", new ActionError("knowledgepro.admin.document.deletefailure"));
			saveErrors(request,errors);
		}
		setProgramTypeListToRequest(request); 
		setCertificateCourseListToRequest(request,certificateCourseEntryForm);
		setprogramMapToRequest(request, certificateCourseEntryForm);
		
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.course.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			certificateCourseEntryForm.reset(mapping, request);
		} 
		log.debug("inside delete deleteCertificateCourse Action");
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
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
	public ActionForward activateCertificateCourse(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.debug("inside activateCertificateCourse in Action");
		CertificateCourseEntryForm certificateCourseEntryForm = (CertificateCourseEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			int id = certificateCourseEntryForm.getDuplId();
			isActivated = CertificateCourseEntryHandler.getInstance().deleteCertificateCourse(id, true, certificateCourseEntryForm); 
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.CERTIFICATE_COURSE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setProgramTypeListToRequest(request); 
		setCertificateCourseListToRequest(request,certificateCourseEntryForm);
		setprogramMapToRequest(request, certificateCourseEntryForm);
		setSubjectListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.CERTIFICATE_COURSE_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("exit activateDocType method in Action");
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
	}
	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void setSubjectListToRequest(HttpServletRequest request)	throws Exception {
		log.debug("inside setSubjectListToRequest");
		List<SubjectTO> subjectList = CertificateCourseEntryHandler.getInstance().getSubjectList();
		request.setAttribute("subjectList", subjectList);
		log.debug("leaving setSubjectListToRequest");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editCertificateCourse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		CertificateCourseEntryForm certificateCourseEntryForm = (CertificateCourseEntryForm) form;
		ActionErrors errors = new ActionErrors();
		try{
			CertificateCourseEntryHandler.getInstance().getDetailsById(certificateCourseEntryForm);
			setSubjectListToRequest(request);
		}catch (Exception e){
		errors.add("error", new ActionError("knowledgepro.admin.certificate.course.editfailure"));
		saveErrors(request,errors);
		}
		setTeacherMapToForm(request, certificateCourseEntryForm);
		setCertificateCourseListToRequest(request,certificateCourseEntryForm);
		request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCertificateCourse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		CertificateCourseEntryForm certificateCourseEntryForm = (CertificateCourseEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = certificateCourseEntryForm.validate(mapping, request);
		boolean isUpdated;
		validateCertificateCourse(certificateCourseEntryForm,errors);
		validateTime(certificateCourseEntryForm, errors);
		ValidateMaxIntake(certificateCourseEntryForm,errors);
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramTypeListToRequest(request);
				setCertificateCourseListToRequest(request,certificateCourseEntryForm);
				setprogramMapToRequest(request, certificateCourseEntryForm);
				setSubjectListToRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
			}
			if(isCancelled(request)){
				CertificateCourseEntryHandler.getInstance().getDetailsById(certificateCourseEntryForm);
				setProgramTypeListToRequest(request); 
				setCertificateCourseListToRequest(request,certificateCourseEntryForm);
				setprogramMapToRequest(request, certificateCourseEntryForm);
				setSubjectListToRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
//				return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
			}else if(errors.isEmpty()){
			setUserId(request, certificateCourseEntryForm);	
			isUpdated = CertificateCourseEntryHandler.getInstance().updateCertificateCourse(certificateCourseEntryForm);
			setProgramTypeListToRequest(request); 
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			setSubjectListToRequest(request);
			if (isUpdated) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.admin.certificate.course.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.ADD);
				certificateCourseEntryForm.reset(mapping, request);
				setCertificateCourseGroupList(certificateCourseEntryForm);
			}
			else
			{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.certificate.course.updatefailure"));
				saveErrors(request, errors);
				setProgramTypeListToRequest(request); 
				setCertificateCourseListToRequest(request,certificateCourseEntryForm);
				setSubjectListToRequest(request);
			}
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.course.name.exists"));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request); 
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			setprogramMapToRequest(request, certificateCourseEntryForm);
			setSubjectListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
//			return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.CERTIFICATE_COURSE_REACTIVATE));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request); 
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			setSubjectListToRequest(request);
			return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit addCerficateCourse...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				certificateCourseEntryForm.setErrorMessage(msg);
				certificateCourseEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving addCerficateCourse Action");
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
	}
private void validateTime(CertificateCourseEntryForm certificateCourseEntryForm, ActionErrors errors) {
		
		
		if(CommonUtil.checkForEmpty(certificateCourseEntryForm.getStartHours())){
			if(!StringUtils.isNumeric(certificateCourseEntryForm.getStartHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(certificateCourseEntryForm.getStartMins())){
			if(!StringUtils.isNumeric(certificateCourseEntryForm.getStartMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(certificateCourseEntryForm.getStartHours())){
			if(Integer.parseInt(certificateCourseEntryForm.getStartHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(certificateCourseEntryForm.getStartMins())){
			if(Integer.parseInt(certificateCourseEntryForm.getStartMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(certificateCourseEntryForm.getEndHours())){
			if(!StringUtils.isNumeric(certificateCourseEntryForm.getEndHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(certificateCourseEntryForm.getEndMins())){
			if(!StringUtils.isNumeric(certificateCourseEntryForm.getEndMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(certificateCourseEntryForm.getEndHours())){
			if(Integer.parseInt(certificateCourseEntryForm.getEndHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(certificateCourseEntryForm.getEndMins())){
			if(Integer.parseInt(certificateCourseEntryForm.getEndMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		//if start time greater than end time then showing error message
		if(CommonUtil.checkForEmpty(certificateCourseEntryForm.getStartHours()) && CommonUtil.checkForEmpty(certificateCourseEntryForm.getEndHours())){
			if(Integer.parseInt(certificateCourseEntryForm.getStartHours())> Integer.parseInt(certificateCourseEntryForm.getEndHours())){
				if(errors.get("knowledgepro.attn.start.greater")!=null && !errors.get("knowledgepro.attn.start.greater").hasNext()){									
					errors.add("knowledgepro.attn.start.greater",new ActionError("knowledgepro.attn.start.greater"));
					return;
				}
			}			
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
	public ActionForward setCertificateCourseEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("Entering certificate course entry");
		CertificateCourseEntryForm certificateCourseEntryForm = (CertificateCourseEntryForm) form;
		try {
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			setTeacherMapToForm(request,certificateCourseEntryForm);
		} catch (Exception e) {
			log.error("error in initCertificateCourseEntry method", e);
			String msg = super.handleApplicationException(e);
			certificateCourseEntryForm.setErrorMessage(msg);
			certificateCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving initCertificateCourseEntry ");
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
	}
	
	public ActionForward getCourseByAcademicYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CertificateCourseEntryForm certificateCourseEntryForm =  (CertificateCourseEntryForm) form;
		try {
			setCertificateCourseListToRequest(request,certificateCourseEntryForm);
			//setTeacherMapToForm(request,certificateCourseEntryForm);
			setSubjectListToRequest(request);
		} catch (Exception e) {
			log.error("error in getCourseByAcademicYear method", e);
			String msg = super.handleApplicationException(e);
			certificateCourseEntryForm.setErrorMessage(msg);
			certificateCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving initCertificateCourseEntry ");
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_ENTRY);
	
	}
}
