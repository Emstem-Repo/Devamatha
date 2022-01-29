package com.kp.cms.actions.serviceLearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.actions.admission.StudentEditAction;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;
import com.kp.cms.forms.serviceLearning.ServiceLearningMarksEntryForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.ActivityLearningHandler;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.StudentEditHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.serviceLearning.DepartmentNameEntryHandler;
import com.kp.cms.handlers.serviceLearning.ProgrammeEntryHandler;
import com.kp.cms.handlers.serviceLearning.ServiceLearningMarksEntryHandler;
import com.kp.cms.handlers.usermanagement.StudentLoginHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.GrievanceStatusTo;
import com.kp.cms.to.exam.GrievanceStudentTo;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;
import com.kp.cms.to.serviceLearning.ServiceLearningMarksEntryTo;
import com.kp.cms.to.serviceLearning.ServiceLearningmarksOverallEntryTo;
import com.kp.cms.transactions.serviceLearning.IProgrammeEntryTransaction;
import com.kp.cms.transactionsimpl.serviceLearning.ProgrammeEntryTransactionImpl;

public class ServiceLearningMarksEntryAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ServiceLearningMarksEntryAction.class);
	
	public ActionForward initServiceLearningMarksEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Caste");
		try{
			List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();
			serviceLearningMarksEntryForm.setProgrammeEntryToList(programmeEntryToList);
			//List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			//departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
			request.setAttribute("operation", "search");
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				serviceLearningMarksEntryForm.setErrorMessage(msg);
				serviceLearningMarksEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY);
	}
	
	public ActionForward searchServiceLearningActivity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		ActionMessages errors = serviceLearningMarksEntryForm.validate(mapping, request);
		try {
			ProgrammeEntryBo programmeEntryBo=ServiceLearningMarksEntryHandler.getInstance().getProgrammesById(serviceLearningMarksEntryForm);
			if(programmeEntryBo!=null){
				if(programmeEntryBo.getDepartmentNameEntry()!=null && !programmeEntryBo.getDepartmentNameEntry().toString().isEmpty()){
					serviceLearningMarksEntryForm.setDepartmentName(programmeEntryBo.getDepartmentNameEntry().getDepartmentName());
				}
			serviceLearningMarksEntryForm.setProgramName(programmeEntryBo.getProgrammeName());
			serviceLearningMarksEntryForm.setInchargeName(programmeEntryBo.getInchargeName());

			ServiceLearningMarksEntryHandler.getInstance().getServiceLearningActivityByProgrameId(serviceLearningMarksEntryForm);
			
			}else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
				saveErrors(request, errors);
				request.setAttribute("operation", "search");
				return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY);
			}
			
		} catch (Exception e) {
			log.error("error in searchServiceLearningActivity...", e);
			throw e;

		}
		log.info("exit searchServiceLearningActivity..");
		return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_RESULT);
	}
	
	public ActionForward saveServiceLearningActivity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		ActionMessages errors = serviceLearningMarksEntryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		boolean isSaved=false;
		try {
			setUserId(request, serviceLearningMarksEntryForm);
			isSaved=ServiceLearningMarksEntryHandler.getInstance().saveServiceLearningActivity(serviceLearningMarksEntryForm.getServiceLearningActivityToList(),serviceLearningMarksEntryForm);
			if(isSaved){
				ActionMessage message = new ActionMessage("knowledgepro.servicelearing.marksentry.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.servicelearing.marksentry.addfailure"));
				saveErrors(request, errors);

			}
		} catch (Exception e) {
			log.error("error in searchServiceLearningActivity...", e);
			throw e;

		}
		log.info("exit searchServiceLearningActivity..");
		request.setAttribute("operation", "search");
		return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_RESULT);
	}
	public ActionForward printServiceLearningActivityMarksEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering the getHallTicket");
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;

		return mapping.findForward(CMSConstants.PRINT_SERVICE_LEARNING_MARKS_ENTRY_RESULT);
	}
	public ActionForward initOverallMarkByStudentId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		ActionMessages errors = serviceLearningMarksEntryForm.validate(mapping, request);
		boolean isSaved=false;
		request.setAttribute("department", "edit");
		serviceLearningMarksEntryForm.setShowList(false);
		try {
			serviceLearningMarksEntryForm.setServiceLearningmarksOverallEntryToList(null);
			serviceLearningMarksEntryForm.setServiceLearningmarksEntryToList(null);
			serviceLearningMarksEntryForm.setServiceLearningActivityToList(null);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			serviceLearningMarksEntryForm.setProgramTypeList(programTypeList);
			//List<CourseTO> courseList = CourseHandler.getInstance().getCourse(0);
			//serviceLearningMarksEntryForm.setCourseList(courseList);
			if(serviceLearningMarksEntryForm.getProgramTypeId()!=null){
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCoursesByProgramTypes(serviceLearningMarksEntryForm.getProgramTypeId());
			serviceLearningMarksEntryForm.setCourseMap(courseMap);
			}
			//ServiceLearningMarksEntryHandler.getInstance().getOverallMarkByStudentId(Integer.parseInt(serviceLearningMarksEntryForm.getStudentId()),serviceLearningMarksEntryForm);
			//isSaved=ServiceLearningMarksEntryHandler.getInstance().saveServiceLearningActivity(serviceLearningMarksEntryForm.getServiceLearningActivityToList());
			
		} catch (Exception e) {
			log.error("error in searchServiceLearningActivity...", e);
			throw e;

		}
		log.info("exit searchServiceLearningActivity..");
		return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT);
	}
	public ActionForward initOverallMarkEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		ActionMessages errors = serviceLearningMarksEntryForm.validate(mapping, request);
		boolean isSaved=false;
		request.setAttribute("department", "edit");
		serviceLearningMarksEntryForm.setShowList(false);
		try {
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			serviceLearningMarksEntryForm.setProgramTypeList(programTypeList);
			//List<CourseTO> courseList = CourseHandler.getInstance().getCourse(0);
			//serviceLearningMarksEntryForm.setCourseList(courseList);
			if(serviceLearningMarksEntryForm.getProgramTypeId()!=null){
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCoursesByProgramTypes(serviceLearningMarksEntryForm.getProgramTypeId());
			serviceLearningMarksEntryForm.setCourseMap(courseMap);
			}
			//ServiceLearningMarksEntryHandler.getInstance().getOverallMarkByStudentId(Integer.parseInt(serviceLearningMarksEntryForm.getStudentId()),serviceLearningMarksEntryForm);
			//isSaved=ServiceLearningMarksEntryHandler.getInstance().saveServiceLearningActivity(serviceLearningMarksEntryForm.getServiceLearningActivityToList());
			
		} catch (Exception e) {
			log.error("error in searchServiceLearningActivity...", e);
			throw e;

		}
		log.info("exit searchServiceLearningActivity..");
		return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT_ADMIN);
	}
	
	public ActionForward getOverallMarkByStudentId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		ActionMessages errors = serviceLearningMarksEntryForm.validate(mapping, request);
		//boolean isSaved=false;
		request.setAttribute("department", "print");
		try {
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			serviceLearningMarksEntryForm.setProgramTypeList(programTypeList);
			if(serviceLearningMarksEntryForm.getProgramTypeId()!=null){
				Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCoursesByProgramTypes(serviceLearningMarksEntryForm.getProgramTypeId());
				serviceLearningMarksEntryForm.setCourseMap(courseMap);
				}
			List<ServiceLearningmarksOverallEntryTo> serviceLearningmarksOverallEntryToList = ServiceLearningMarksEntryHandler.getInstance().getOverallMarkByStudentId(serviceLearningMarksEntryForm);
			if(serviceLearningmarksOverallEntryToList.size()!=0){
			    serviceLearningMarksEntryForm.setServiceLearningmarksOverallEntryToList(serviceLearningmarksOverallEntryToList);
			}else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT);
			}

			serviceLearningMarksEntryForm.reset(mapping, request);
			//isSaved=ServiceLearningMarksEntryHandler.getInstance().saveServiceLearningActivity(serviceLearningMarksEntryForm.getServiceLearningActivityToList());
			
		} catch (Exception e) {
			log.error("error in searchServiceLearningActivity...", e);
			throw e;

		}
		log.info("exit searchServiceLearningActivity..");
		return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT);
	}
	
	public ActionForward printOverallMarkByStudentId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering the getHallTicket");
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		serviceLearningMarksEntryForm.setProgramTypeList(programTypeList);
		if(serviceLearningMarksEntryForm.getProgramTypeId()!=null){
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCoursesByProgramTypes(serviceLearningMarksEntryForm.getProgramTypeId());
			serviceLearningMarksEntryForm.setCourseMap(courseMap);
			}
		List<ServiceLearningmarksOverallEntryTo> serviceLearningmarksOverallEntryToList=ServiceLearningMarksEntryHandler.getInstance().getOverallMarkByStudentId(serviceLearningMarksEntryForm);
	    serviceLearningMarksEntryForm.setServiceLearningmarksOverallEntryToList(serviceLearningmarksOverallEntryToList);
	
		return mapping.findForward(CMSConstants.PRINT_SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT);
	}
	
	public ActionForward showOverallMarkDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering the getHallTicket");
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		serviceLearningMarksEntryForm.setProgramTypeList(programTypeList);
		if(serviceLearningMarksEntryForm.getProgramTypeId()!=null){
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCoursesByProgramTypes(serviceLearningMarksEntryForm.getProgramTypeId());
			serviceLearningMarksEntryForm.setCourseMap(courseMap);
			}
		
		List<ServiceLearningMarksEntryTo> serviceLearningmarksEntryToList=ServiceLearningMarksEntryHandler.getInstance().getStudentDetailsById(serviceLearningMarksEntryForm,request);
		serviceLearningMarksEntryForm.setServiceLearningmarksEntryToList(serviceLearningmarksEntryToList);
		List<ServiceLearningmarksOverallEntryTo> serviceLearningmarksOverallEntryToList=ServiceLearningMarksEntryHandler.getInstance().getOverallMarkByStudentId(serviceLearningMarksEntryForm);
	    serviceLearningMarksEntryForm.setServiceLearningmarksOverallEntryToList(serviceLearningmarksOverallEntryToList);
	
		return mapping.findForward(CMSConstants.SHOW_SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT);
	}
	
	public ActionForward getOverallMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		ActionMessages errors = serviceLearningMarksEntryForm.validate(mapping, request);
		//boolean isSaved=false;
		request.setAttribute("department", "print");
		try {
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			serviceLearningMarksEntryForm.setProgramTypeList(programTypeList);
			List<ExtraCreditActivityTypeTo> activityLearningToList = ProgramTypeHandler.getInstance().getactivityLearning();
			serviceLearningMarksEntryForm.setList(activityLearningToList);
			if(serviceLearningMarksEntryForm.getProgramTypeId()!=null){
				Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCoursesByProgramTypes(serviceLearningMarksEntryForm.getProgramTypeId());
				serviceLearningMarksEntryForm.setCourseMap(courseMap);
				}
			List<ProgrammeEntryTo> programmeEntryToList = StudentLoginHandler.getInstance().getProgrammeNameEntry();
			serviceLearningMarksEntryForm.setProgrammeEntryToList(programmeEntryToList);
			
			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			serviceLearningMarksEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
			if(serviceLearningMarksEntryForm.getProgramId()!=null){
				Map<Integer, String> programeMap =CommonAjaxHandler.getInstance().getProgramesByDepartmentId(serviceLearningMarksEntryForm.getProgramId());
				serviceLearningMarksEntryForm.setProgrameMap(programeMap);
			}
			if(serviceLearningMarksEntryForm.getExtraCreditActivityType()!=null){
				Map<Integer,  String> programCode =CommonAjaxHandler.getInstance().getProgramesByActivityId(serviceLearningMarksEntryForm.getExtraCreditActivityType());
				serviceLearningMarksEntryForm.setProgrameCode(programCode);
			}
			serviceLearningMarksEntryForm.reset(mapping, request);
			//isSaved=ServiceLearningMarksEntryHandler.getInstance().saveServiceLearningActivity(serviceLearningMarksEntryForm.getServiceLearningActivityToList());
			
		} catch (Exception e) {
			log.error("error in searchServiceLearningActivity...", e);
			throw e;

		}
		log.info("exit searchServiceLearningActivity..");
		return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT_ADMIN);
	}
	public ActionForward getOverallStudentMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		validateData(request,errors,serviceLearningMarksEntryForm);
		request.setAttribute("department", "print");
		if(errors.isEmpty()){
			try {
				List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
				serviceLearningMarksEntryForm.setProgramTypeList(programTypeList);
				
				List<ProgrammeEntryTo> programmeEntryToList = StudentLoginHandler.getInstance().getProgrammeNameEntry();
				serviceLearningMarksEntryForm.setProgrammeEntryToList(programmeEntryToList);
				if(serviceLearningMarksEntryForm.getDepartmentId()!=null && !serviceLearningMarksEntryForm.getDepartmentId().isEmpty()){
					List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
					serviceLearningMarksEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
					if(serviceLearningMarksEntryForm.getProgramId()!=null){
						Map<Integer, String> programeMap =CommonAjaxHandler.getInstance().getProgramesByDepartmentId(serviceLearningMarksEntryForm.getProgramId());
						serviceLearningMarksEntryForm.setProgrameMap(programeMap);
					}
				
				}
				if(serviceLearningMarksEntryForm.getProgramTypeId()!=null){
					Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCoursesByProgramTypes(serviceLearningMarksEntryForm.getProgramTypeId());
					serviceLearningMarksEntryForm.setCourseMap(courseMap);
					}
				List<ServiceLearningmarksOverallEntryTo> serviceLearningmarksOverallEntryToList = ServiceLearningMarksEntryHandler.getInstance().getOverallMark(serviceLearningMarksEntryForm);
				if(serviceLearningmarksOverallEntryToList.size()!=0){
				    serviceLearningMarksEntryForm.setServiceLearningmarksOverallEntryToList(serviceLearningmarksOverallEntryToList);
				}else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT);
				}
	
				serviceLearningMarksEntryForm.reset(mapping, request);
				//isSaved=ServiceLearningMarksEntryHandler.getInstance().saveServiceLearningActivity(serviceLearningMarksEntryForm.getServiceLearningActivityToList());
				
			} catch (Exception e) {
				log.error("error in searchServiceLearningActivity...", e);
				throw e;
	
			}
		}else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT_ADMIN);
		}
		log.info("exit searchServiceLearningActivity..");
		return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT_ADMIN1);
	}
	
	public ActionForward saveServiceLearningMarkEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceLearningMarksEntryForm serviceLearningMarksEntryForm = (ServiceLearningMarksEntryForm) form;
		ActionMessages errors = serviceLearningMarksEntryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		boolean isSaved=false;
		try {
			setUserId(request, serviceLearningMarksEntryForm);
			isSaved=ServiceLearningMarksEntryHandler.getInstance().saveServiceLearningMarkEntry(serviceLearningMarksEntryForm.getServiceLearningmarksOverallEntryToList(),serviceLearningMarksEntryForm);
			if(isSaved){
				ActionMessage message = new ActionMessage("knowledgepro.servicelearing.marksentry.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.servicelearing.marksentry.addfailure"));
				saveErrors(request, errors);

			}
		} catch (Exception e) {
			log.error("error in searchServiceLearningActivity...", e);
			throw e;

		}
		log.info("exit searchServiceLearningActivity..");
		request.setAttribute("operation", "search");
		return mapping.findForward(CMSConstants.SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT_ADMIN1);
	}
	private void validateData(HttpServletRequest request, ActionErrors errors,
			ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) {
		
		if(serviceLearningMarksEntryForm.getYear()==null || serviceLearningMarksEntryForm.getYear().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.ServiceLearning.academicYear"));
		}
		if(serviceLearningMarksEntryForm.getProgramTypeId()==null || serviceLearningMarksEntryForm.getProgramTypeId().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.ServiceLearning.programtype"));
		}
		if(serviceLearningMarksEntryForm.getCourseId()==null || serviceLearningMarksEntryForm.getCourseId().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.ServiceLearning.courseId"));
		}
		if(serviceLearningMarksEntryForm.getExtraCreditActivityType()==null || serviceLearningMarksEntryForm.getExtraCreditActivityType().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.ServiceLearning.activity"));
		}
		if(serviceLearningMarksEntryForm.getProgramId()==null || serviceLearningMarksEntryForm.getProgramId().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.servicelearing.programmeName.required"));
		}
		if(serviceLearningMarksEntryForm.getExtraCreditActivityType()!=null && !serviceLearningMarksEntryForm.getExtraCreditActivityType().isEmpty()){
			if((Integer.parseInt(serviceLearningMarksEntryForm.getExtraCreditActivityType()))==1){
				if(serviceLearningMarksEntryForm.getDepartmentId()==null || serviceLearningMarksEntryForm.getDepartmentId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.servicelearing.deptId.required"));
				}
			}
		}
	}
}
