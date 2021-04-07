package com.kp.cms.actions.examallotment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.forms.examallotment.StudentClassGroupForm;
import com.kp.cms.handlers.examallotment.StudentClassGroupHandler;
import com.kp.cms.to.examallotment.StudentsClassGroupTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class StudentClassGroupAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(StudentClassGroupAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentsClassGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentClassGroupForm classGroupForm=(StudentClassGroupForm) form;
		classGroupForm.reset();
		setClassGroupRequiredDataToForm(classGroupForm, request);
		return mapping.findForward(CMSConstants.INIT_STUDENTS_CLASS_GROUP);
	}
	
	/**
	 * @param classGroupForm
	 * @param request
	 * @throws Exception
	 */
	public void setClassGroupRequiredDataToForm(StudentClassGroupForm classGroupForm,HttpServletRequest request)
			throws Exception {
		Map<Integer, String> locationMap=StudentClassGroupHandler.getInstance().getWorkLocation();
		classGroupForm.setLocationMap(locationMap);
		Map<Integer, String> classGroupMap=StudentClassGroupHandler.getInstance().getClassGroups();
		classGroupForm.setClassGroupMap(classGroupMap);
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(classGroupForm.getAcademicYear()!=null && !classGroupForm.getAcademicYear().isEmpty()){
			year=Integer.parseInt(classGroupForm.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
		List<StudentsClassGroupTo> groupToList=StudentClassGroupHandler.getInstance().getStudentClassGroup(year,classGroupForm);
		classGroupForm.setGroupClassesList(groupToList);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getClassesByYearAndLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        StudentClassGroupForm classGroupForm=(StudentClassGroupForm) form;
        log.info("Entered getClassesByYearAndLocation input");
		try{
			Map<Integer, String> classMap=StudentClassGroupHandler.getInstance().getClassesByYearAndLocation(classGroupForm);
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			classGroupForm.setClassMap(classMap);
			List<StudentsClassGroupTo> groupToList=StudentClassGroupHandler.getInstance().getStudentClassGroup(Integer.parseInt(classGroupForm.getAcademicYear()),classGroupForm);
			classGroupForm.setGroupClassesList(groupToList);
		 } catch (Exception e) {
				log.error("Error occured in edit pool Details", e);
				String msg = super.handleApplicationException(e);
				classGroupForm.setErrorMessage(msg);
				classGroupForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		 return mapping.findForward(CMSConstants.INIT_STUDENTS_CLASS_GROUP);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentDetailsByClasses(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentClassGroupForm classGroupForm=(StudentClassGroupForm) form;
		setUserId(request, classGroupForm);
		ActionErrors errors=classGroupForm.validate(mapping, request);
        log.info("Entered getClassesByYearAndLocation input");
        if(errors.isEmpty()){
        	try{
        		List<StudentsClassGroupTo> classGroupToList=StudentClassGroupHandler.getInstance().getStudentDetailsByClasses(classGroupForm);
        		if(!classGroupToList.isEmpty()){
        			classGroupForm.setClassGroupToList(classGroupToList);
        			classGroupForm.setDisable(true);
        			classGroupForm.setFlag(true);
        			classGroupForm.setDisable2(false);
        			classGroupForm.setClassGroup(null);
        		}else{
        			errors.add("error", new ActionError("knowledgepro.exam.allotment.class.group.get.student.for.classes"));
        			saveErrors(request, errors);
        		}
        	} catch (Exception e) {
        		log.error("Error occured in edit pool Details", e);
        		String msg = super.handleApplicationException(e);
        		classGroupForm.setErrorMessage(msg);
        		classGroupForm.setErrorStack(e.getMessage());
        		return mapping.findForward(CMSConstants.ERROR_PAGE);
        	}
        }else{
        	saveErrors(request, errors);
        }
         setClassGroupRequiredDataToForm(classGroupForm, request);
		 return mapping.findForward(CMSConstants.INIT_STUDENTS_CLASS_GROUP);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addClassGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentClassGroupForm classGroupForm=(StudentClassGroupForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, classGroupForm);
		validateStudentSelectedOrNot(classGroupForm,errors);
		if(errors.isEmpty()){
			try{
				boolean isAdded=false;
				isAdded=StudentClassGroupHandler.getInstance().addClassGroup(classGroupForm);
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.class.group.addsuccess"));
					saveMessages(request, messages);
					classGroupForm.reset();
					setClassGroupRequiredDataToForm(classGroupForm, request);
					log.info("added addExamPoolName method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.class.group.addFailure"));
					addErrors(request, errors);
					classGroupForm.reset();
					setClassGroupRequiredDataToForm(classGroupForm, request);
				}
				
			} catch (Exception e) {
        		log.error("Error occured in edit pool Details", e);
        		String msg = super.handleApplicationException(e);
        		classGroupForm.setErrorMessage(msg);
        		classGroupForm.setErrorStack(e.getMessage());
        		return mapping.findForward(CMSConstants.ERROR_PAGE);
        	}
		}else{
			saveErrors(request, errors);
			setClassGroupRequiredDataToForm(classGroupForm, request);
			List<StudentsClassGroupTo> classGroupToList=StudentClassGroupHandler.getInstance().getStudentDetailsByClasses(classGroupForm);
			classGroupForm.setClassGroupToList(classGroupToList);
		}
		return mapping.findForward(CMSConstants.INIT_STUDENTS_CLASS_GROUP);
	}

	/**
	 * @param classGroupForm
	 * @param errors
	 */
	private void validateStudentSelectedOrNot(
			StudentClassGroupForm classGroupForm, ActionErrors errors) {
		boolean isChecked=false;
		List<StudentsClassGroupTo> groupToList=classGroupForm.getClassGroupToList();
		for (StudentsClassGroupTo studentsClassGroupTo : groupToList) {
			if(studentsClassGroupTo.getChecked()!=null && studentsClassGroupTo.getChecked().equalsIgnoreCase("on")){
				isChecked=true;
				break;
			}
		}
		if(!isChecked){
			errors.add("error", new ActionError("knowledgepro.attendance.student.required"));
		}
		if(classGroupForm.getClassGroup()==null || classGroupForm.getClassGroup().isEmpty())
			errors.add("error", new ActionError("knowledgepro.ExamAllotment.Students.Class.group.required"));
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editStudentsClassGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentClassGroupForm classGroupForm=(StudentClassGroupForm) form;
		setUserId(request, classGroupForm);
		try{
			Map<Integer, String> classGroupMap=StudentClassGroupHandler.getInstance().getClassGroups();
			classGroupForm.setClassGroupMap(classGroupMap);
			List<StudentsClassGroupTo> classGroupToList=StudentClassGroupHandler.getInstance().editStudentGroupClassByGroupId(classGroupForm);
			if(!classGroupToList.isEmpty()){
				classGroupForm.setClassGroupToList(classGroupToList);
				classGroupForm.setDisable(true);
				classGroupForm.setFlag(true);
				classGroupForm.setDisable2(true);
			}
			request.setAttribute("studentsClassGroupOperation", "edit");
		} catch (Exception e) {
    		log.error("Error occured in edit pool Details", e);
    		String msg = super.handleApplicationException(e);
    		classGroupForm.setErrorMessage(msg);
    		classGroupForm.setErrorStack(e.getMessage());
    		return mapping.findForward(CMSConstants.ERROR_PAGE);
    	}
		return mapping.findForward(CMSConstants.INIT_STUDENTS_CLASS_GROUP);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateGroupClassWithStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentClassGroupForm classGroupForm=(StudentClassGroupForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, classGroupForm);
		validateStudentSelectedOrNot(classGroupForm,errors);
		if(errors.isEmpty()){
			try{
				boolean isUpdate=false;
				isUpdate=StudentClassGroupHandler.getInstance().updateGroupClassWithStudents(classGroupForm);
				if (isUpdate) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.class.group.updatedsuccess"));
					saveMessages(request, messages);
					classGroupForm.reset();
					setClassGroupRequiredDataToForm(classGroupForm, request);
					log.info("added updateGroupClassWithStudents method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.class.group.updateFailure"));
					addErrors(request, errors);
					classGroupForm.reset();
					setClassGroupRequiredDataToForm(classGroupForm, request);
				}
				
			} catch (Exception e) {
        		log.error("Error occured in edit pool Details", e);
        		String msg = super.handleApplicationException(e);
        		classGroupForm.setErrorMessage(msg);
        		classGroupForm.setErrorStack(e.getMessage());
        		return mapping.findForward(CMSConstants.ERROR_PAGE);
        	}
		}else{
			saveErrors(request, errors);
			setClassGroupRequiredDataToForm(classGroupForm, request);
			List<StudentsClassGroupTo> classGroupToList=StudentClassGroupHandler.getInstance().getStudentDetailsByClasses(classGroupForm);
			classGroupForm.setClassGroupToList(classGroupToList);
		}
		return mapping.findForward(CMSConstants.INIT_STUDENTS_CLASS_GROUP);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteStudentsClassGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentClassGroupForm classGroupForm=(StudentClassGroupForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, classGroupForm);
			try{
				boolean isDeleted=false;
				isDeleted=StudentClassGroupHandler.getInstance().deleteGroupClassWithStudents(classGroupForm);
				if (isDeleted) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.class.group.deletedsuccess"));
					saveMessages(request, messages);
					classGroupForm.reset();
					setClassGroupRequiredDataToForm(classGroupForm, request);
					log.info("added updateGroupClassWithStudents method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.class.group.deleteFailure"));
					addErrors(request, errors);
					classGroupForm.reset();
					setClassGroupRequiredDataToForm(classGroupForm, request);
				}
				
			} catch (Exception e) {
        		log.error("Error occured in edit pool Details", e);
        		String msg = super.handleApplicationException(e);
        		classGroupForm.setErrorMessage(msg);
        		classGroupForm.setErrorStack(e.getMessage());
        		return mapping.findForward(CMSConstants.ERROR_PAGE);
        	}
		return mapping.findForward(CMSConstants.INIT_STUDENTS_CLASS_GROUP);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getClassGroupDetailsByYear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentClassGroupForm classGroupForm=(StudentClassGroupForm) form;
		try{
			List<StudentsClassGroupTo> groupToList=StudentClassGroupHandler.getInstance().getStudentClassGroup(Integer.parseInt(classGroupForm.getAcademicYear()),classGroupForm);
			classGroupForm.setGroupClassesList(groupToList);
			
		} catch (Exception e) {
    		log.error("Error occured in edit pool Details", e);
    		String msg = super.handleApplicationException(e);
    		classGroupForm.setErrorMessage(msg);
    		classGroupForm.setErrorStack(e.getMessage());
    		return mapping.findForward(CMSConstants.ERROR_PAGE);
    	}
		return mapping.findForward(CMSConstants.INIT_STUDENTS_CLASS_GROUP);
	}
}
