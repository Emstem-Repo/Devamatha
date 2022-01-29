package com.kp.cms.actions.admission;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.kp.cms.actions.attendance.ClassEntryAction;
import com.kp.cms.bo.admin.Category;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admission.TcDetailsOldStudents;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.forms.admission.TcDetailsOldStudentsForm;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.attendance.ClassEntryForm;
import com.kp.cms.handlers.admission.TCDetailsHandler;
import com.kp.cms.handlers.admission.TcDetailsOldStudentsHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.ClassEntryHandler;
import com.kp.cms.to.admission.TcDetailsOldStudentsTo;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class TcDetailsOldStudentsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(TcDetailsOldStudentsAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTcDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		log.info("Entering initClassEntry");
		TcDetailsOldStudentsForm tcDetailsOldStudetnsForm = (TcDetailsOldStudentsForm)form;
		try{
			tcDetailsOldStudetnsForm.setYear(null);
			setUserId(request, tcDetailsOldStudetnsForm);
			tcDetailsOldStudetnsForm.clear();
			tcDetailsOldStudetnsForm.setTcDetailsOldStudentsToList(null);
			assignListToForm(tcDetailsOldStudetnsForm);
		}catch (Exception exception) {
			// TODO: handle exception
			String msg = super.handleApplicationException(exception);
			tcDetailsOldStudetnsForm.setErrorMessage(msg);
			tcDetailsOldStudetnsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
	}

	/**
	 * @param tcDetailsOldStudetnsForm
	 * @throws Exception
	 */
	private void assignListToForm(TcDetailsOldStudentsForm tcDetailsOldStudetnsForm)throws Exception {
		// TODO Auto-generated method stub
		log.info("Entering assignListToForm of tcDetailsOldStudetnsForm");
		if (tcDetailsOldStudetnsForm.getYear() == null
				|| tcDetailsOldStudetnsForm.getYear().isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}// end
			tcDetailsOldStudetnsForm.setButton(null);
			TcDetailsOldStudentsHandler.getInstance().getOldStudentsList(currentYear+"",tcDetailsOldStudetnsForm);
		} else {
			int year = Integer.parseInt(tcDetailsOldStudetnsForm.getYear().trim());
			tcDetailsOldStudetnsForm.setButton(null);
			TcDetailsOldStudentsHandler.getInstance().getOldStudentsList(year+"",tcDetailsOldStudetnsForm);
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
	public ActionForward setOldStudentsData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Entering setOldStudentsData");
		TcDetailsOldStudentsForm tcDetailsOldStudentsForm = (TcDetailsOldStudentsForm) form;
		try {
			tcDetailsOldStudentsForm.setTcDetailsOldStudentsToList(null);
			assignListToForm(tcDetailsOldStudentsForm);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			tcDetailsOldStudentsForm.setErrorMessage(msg);
			tcDetailsOldStudentsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving setClassEntry");
		return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAddTcDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Entering initAddTcDetails");
		TcDetailsOldStudentsForm tcDetailsOldStudentsForm=(TcDetailsOldStudentsForm)form;
		try{
		setUserId(request, tcDetailsOldStudentsForm);
		tcDetailsOldStudentsForm.clear();
		initAddTcOldStudents(tcDetailsOldStudentsForm);
		}catch (Exception exception) {
			// TODO: handle exception
			String msg = super.handleApplicationException(exception);
			tcDetailsOldStudentsForm.setErrorMessage(msg);
			tcDetailsOldStudentsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_ADD_OLD_STUDENTS);
	}
	
	/**
	 * @param tcDetailsOldStudentsForm
	 */
	public void initAddTcOldStudents(TcDetailsOldStudentsForm tcDetailsOldStudentsForm)throws Exception{
		tcDetailsOldStudentsForm.clear();
		tcDetailsOldStudentsForm.setReligionId(null);
		if(tcDetailsOldStudentsForm.getPassed()==null)
		tcDetailsOldStudentsForm.setPassed("YES");
		tcDetailsOldStudentsForm.setScolorship("NO");
		tcDetailsOldStudentsForm.setFeePaid("YES");
		tcDetailsOldStudentsForm.setCharAndConductToList(TcDetailsOldStudentsHandler.getInstance().getCharacterAndConductList());
		tcDetailsOldStudentsForm.setCategoryToList(TcDetailsOldStudentsHandler.getInstance().getCategoryList());
		tcDetailsOldStudentsForm.setReligionToList(TcDetailsOldStudentsHandler.getInstance().getReligionList());
		tcDetailsOldStudentsForm.setNationalityToList(TcDetailsOldStudentsHandler.getInstance().getNationalityList());
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addOldStudentData(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		TcDetailsOldStudentsForm tcDetailsOldStudentsForm = (TcDetailsOldStudentsForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = tcDetailsOldStudentsForm.validate(mapping, request);
		validateTime(tcDetailsOldStudentsForm, errors);
		validateOthers(tcDetailsOldStudentsForm, errors);
		int flag=0;
		if(errors.isEmpty()){
		try {
			flag=TcDetailsOldStudentsHandler.getInstance().addOldStudent(tcDetailsOldStudentsForm);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			tcDetailsOldStudentsForm.setErrorMessage(msg);
			tcDetailsOldStudentsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else{
			addErrors(request, errors);
			if(tcDetailsOldStudentsForm.getId()!=null){
				editTcOldStudents(tcDetailsOldStudentsForm);
				return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_EDIT_OLD_STUDENTS);
			}else{
//			initAddTcOldStudents(tcDetailsOldStudentsForm);
			return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_ADD_OLD_STUDENTS);
			}
		}
		log.info("Leaving setClassEntry");
		if(flag==1){
			assignListToForm(tcDetailsOldStudentsForm);
				if(tcDetailsOldStudentsForm.getId()==null){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.addsuccess"," Student TC Details "));
					saveMessages(request, messages);
				}else{
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.updatesuccess", " Student TC Details "));
					saveMessages(request, messages);
				}
			return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
		}
		else if(flag==2){
			assignListToForm(tcDetailsOldStudentsForm);
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.configuretcmaster"));
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
		}else if(flag==3){
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.programtype.name.exists"," Register Number "));
			saveMessages(request, messages);
			if(tcDetailsOldStudentsForm.getId()==null){
//				initAddTcOldStudents(tcDetailsOldStudentsForm);
				return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_ADD_OLD_STUDENTS);
			}
			else
				return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_EDIT_OLD_STUDENTS);	
		}
		return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editTcDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		TcDetailsOldStudentsForm tcDetailsOldStudentsForm=(TcDetailsOldStudentsForm)form;
		try{
			editTcOldStudents(tcDetailsOldStudentsForm);
		}catch (Exception exception) {
			// TODO: handle exception
			String msg = super.handleApplicationException(exception);
			tcDetailsOldStudentsForm.setErrorMessage(msg);
			tcDetailsOldStudentsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_EDIT_OLD_STUDENTS);
	}
	
	/**
	 * @param tcDetailsOldStudentsForm
	 * @throws Exception
	 */
	public void editTcOldStudents(TcDetailsOldStudentsForm tcDetailsOldStudentsForm)throws Exception{
		tcDetailsOldStudentsForm.setReligionOthers(null);
		tcDetailsOldStudentsForm.setCasteOthers(null);
		tcDetailsOldStudentsForm.setNationalityOthers(null);
		TcDetailsOldStudentsHandler.getInstance().editTcDetails(tcDetailsOldStudentsForm);
		tcDetailsOldStudentsForm.setCharAndConductToList(TcDetailsOldStudentsHandler.getInstance().getCharacterAndConductList());
		tcDetailsOldStudentsForm.setCategoryToList(TcDetailsOldStudentsHandler.getInstance().getCategoryList());
		tcDetailsOldStudentsForm.setReligionToList(TcDetailsOldStudentsHandler.getInstance().getReligionList());
		tcDetailsOldStudentsForm.setNationalityToList(TcDetailsOldStudentsHandler.getInstance().getNationalityList());
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addOldStudentAndPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		TcDetailsOldStudentsForm tcDetailsOldStudentsForm = (TcDetailsOldStudentsForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = tcDetailsOldStudentsForm.validate(mapping, request);
		validateTime(tcDetailsOldStudentsForm, errors);
		validateOthers(tcDetailsOldStudentsForm, errors);
		int flag=0;
		if(errors.isEmpty()){
		try {
			tcDetailsOldStudentsForm.setButton("false");
			flag=TcDetailsOldStudentsHandler.getInstance().addOldStudent(tcDetailsOldStudentsForm);
			/*if(tcDetailsOldStudentsForm.getId()!=null)
				TcDetailsOldStudentsHandler.getInstance().editTcDetails(tcDetailsOldStudentsForm);*/
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			tcDetailsOldStudentsForm.setErrorMessage(msg);
			tcDetailsOldStudentsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else{
			addErrors(request, errors);
			if(tcDetailsOldStudentsForm.getId()!=null){
				editTcOldStudents(tcDetailsOldStudentsForm);
				return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_EDIT_OLD_STUDENTS);
			}else{
				initAddTcOldStudents(tcDetailsOldStudentsForm);
				return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_ADD_OLD_STUDENTS);
			}
		}
		log.info("Leaving setClassEntry");
		if(flag==1){
			assignListToForm(tcDetailsOldStudentsForm);
			tcDetailsOldStudentsForm.setButton("true");
			return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
		}
		else if(flag==2){
			assignListToForm(tcDetailsOldStudentsForm);
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.configuretcmaster"));
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
		}else if(flag==3){
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.programtype.name.exists"," Register Number "));
			saveMessages(request, messages);
			if(tcDetailsOldStudentsForm.getId()==null){
//				initAddTcOldStudents(tcDetailsOldStudentsForm);
				return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_ADD_OLD_STUDENTS);
			}
			else
				return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_EDIT_OLD_STUDENTS);	
		}
		return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteTc(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception{
		TcDetailsOldStudentsForm tcDetailsOldStudentsForm=(TcDetailsOldStudentsForm)form;
		boolean flag=false;
		ActionMessages messages = new ActionMessages();
			flag=TcDetailsOldStudentsHandler.getInstance().deleteTc(tcDetailsOldStudentsForm);
			if(flag){
				assignListToForm(tcDetailsOldStudentsForm);
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.deletesuccess"," Tc "));
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
			}else{
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.deletesuccess"," Tc Not "));
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_OLD_STUDENTS);
			}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response){
		TcDetailsOldStudentsForm tcDetailsOldStudentsForm=(TcDetailsOldStudentsForm)form;
		tcDetailsOldStudentsForm.setButton("false");
		return mapping.findForward(CMSConstants.ADMISSION_TC_DETAILS_PRINT_OLD_STUDENTS);
	}
	
	private void validateTime(TcDetailsOldStudentsForm tcDetailsOldStudentsForm, ActionErrors errors) {
		
		if(tcDetailsOldStudentsForm.getDateOfBirth()!=null && !StringUtils.isEmpty(tcDetailsOldStudentsForm.getDateOfBirth())&& !CommonUtil.isValidDate(tcDetailsOldStudentsForm.getDateOfBirth())){
			if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
			}
		}
		if(tcDetailsOldStudentsForm.getAdmissionDate()!=null && !StringUtils.isEmpty(tcDetailsOldStudentsForm.getAdmissionDate())&& !CommonUtil.isValidDate(tcDetailsOldStudentsForm.getAdmissionDate())){
			if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID));
			}
		}
		if(tcDetailsOldStudentsForm.getDateOfLeaving()!=null && !StringUtils.isEmpty(tcDetailsOldStudentsForm.getDateOfLeaving())&& !CommonUtil.isValidDate(tcDetailsOldStudentsForm.getDateOfLeaving())){
			if (errors.get(CMSConstants.EMPLOYEE_JOB_LEAVEDT_INVALID) != null&& !errors.get(CMSConstants.EMPLOYEE_JOB_LEAVEDT_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_JOB_LEAVEDT_INVALID,new ActionError(CMSConstants.EMPLOYEE_JOB_LEAVEDT_INVALID));
			}
		}
		
		if(tcDetailsOldStudentsForm.getDateOfApplication()!=null && !StringUtils.isEmpty(tcDetailsOldStudentsForm.getDateOfApplication())&& !CommonUtil.isValidDate(tcDetailsOldStudentsForm.getDateOfApplication())){
			if (errors.get(CMSConstants.ADMISSION_TC_DETAILS_INVALID_DATEOFAPP) != null&& !errors.get(CMSConstants.ADMISSION_TC_DETAILS_INVALID_DATEOFAPP).hasNext()) {
				errors.add(CMSConstants.ADMISSION_TC_DETAILS_INVALID_DATEOFAPP,new ActionError(CMSConstants.ADMISSION_TC_DETAILS_INVALID_DATEOFAPP));
			}
		}
		
		if(tcDetailsOldStudentsForm.getDateOfIssue()!=null && !StringUtils.isEmpty(tcDetailsOldStudentsForm.getDateOfIssue())&& !CommonUtil.isValidDate(tcDetailsOldStudentsForm.getDateOfIssue())){
			if (errors.get(CMSConstants.ADMISSION_TC_DETAILS_INVALID_DATEOFISSUE) != null&& !errors.get(CMSConstants.ADMISSION_TC_DETAILS_INVALID_DATEOFISSUE).hasNext()) {
				errors.add(CMSConstants.ADMISSION_TC_DETAILS_INVALID_DATEOFISSUE,new ActionError(CMSConstants.ADMISSION_TC_DETAILS_INVALID_DATEOFISSUE));
			}
		}
		if(CommonUtil.checkForEmpty(tcDetailsOldStudentsForm.getDateOfBirth()) && CommonUtil.checkForEmpty(tcDetailsOldStudentsForm.getAdmissionDate())
				&& CommonUtil.isValidDate(tcDetailsOldStudentsForm.getDateOfBirth()) && CommonUtil.isValidDate(tcDetailsOldStudentsForm.getAdmissionDate())){
			Date dateOfBirth = CommonUtil.ConvertStringToDate(tcDetailsOldStudentsForm.getDateOfBirth());
			Date admissionDate = CommonUtil.ConvertStringToDate(tcDetailsOldStudentsForm.getAdmissionDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(dateOfBirth);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(admissionDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
			}
		}
		
		if(CommonUtil.checkForEmpty(tcDetailsOldStudentsForm.getAdmissionDate()) && CommonUtil.checkForEmpty(tcDetailsOldStudentsForm.getDateOfLeaving())
				&& CommonUtil.isValidDate(tcDetailsOldStudentsForm.getAdmissionDate()) && CommonUtil.isValidDate(tcDetailsOldStudentsForm.getDateOfLeaving())){
			Date dateOfLeaving=CommonUtil.ConvertStringToDate(tcDetailsOldStudentsForm.getDateOfLeaving());
			Date admissionDate = CommonUtil.ConvertStringToDate(tcDetailsOldStudentsForm.getAdmissionDate());
			Calendar cal1=Calendar.getInstance();
			cal1.setTime(admissionDate);
			
			Calendar cal2=Calendar.getInstance();
			cal2.setTime(dateOfLeaving);
			long daysBetween=CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween<=0){
				errors.add("error",new ActionError(CMSConstants.ADMISSION_TC_DETAILS_ADLELD));
			}
		}
		
		if(CommonUtil.checkForEmpty(tcDetailsOldStudentsForm.getDateOfApplication()) && CommonUtil.checkForEmpty(tcDetailsOldStudentsForm.getDateOfIssue())
				&& CommonUtil.isValidDate(tcDetailsOldStudentsForm.getDateOfApplication()) && CommonUtil.isValidDate(tcDetailsOldStudentsForm.getDateOfIssue())){
			Date dateOfIssue=CommonUtil.ConvertStringToDate(tcDetailsOldStudentsForm.getDateOfIssue());
			Date applicationDate = CommonUtil.ConvertStringToDate(tcDetailsOldStudentsForm.getDateOfApplication());
			Calendar cal1=Calendar.getInstance();
			cal1.setTime(applicationDate);
			
			Calendar cal2=Calendar.getInstance();
			cal2.setTime(dateOfIssue);
			long daysBetween=CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween<=0){
				errors.add("error",new ActionError(CMSConstants.ADMISSION_TC_DETAILS_ADLEID));
			}
		}
		
		if(tcDetailsOldStudentsForm.getYear()!=null && tcDetailsOldStudentsForm.getDateOfBirth()!=null && CommonUtil.isValidDate(tcDetailsOldStudentsForm.getDateOfBirth())){
			int year=Integer.parseInt(tcDetailsOldStudentsForm.getYear());
			int dob=CommonUtil.ConvertStringToDate(tcDetailsOldStudentsForm.getDateOfBirth()).getYear();
			if(dob>=year){
				errors.add("error", new ActionError(CMSConstants.ADMISSION_TC_DETAILS_DNGEYEAT));;
			}
		}
		
		if(tcDetailsOldStudentsForm.getYear()!=null && tcDetailsOldStudentsForm.getYr()!=null && !tcDetailsOldStudentsForm.getYr().isEmpty()){
			int academicYear=Integer.parseInt(tcDetailsOldStudentsForm.getYear());
			
			int year=Integer.parseInt(tcDetailsOldStudentsForm.getYr());
			if(year<academicYear){
				errors.add("error", new ActionError(CMSConstants.ADMISSION_TC_DETAILS_YLEAY));
			}
		}
	}
	
	
	/**
	 * @param tcDetailsOldStudentsForm
	 * @param errors
	 * @return
	 */
	public ActionErrors validateOthers(TcDetailsOldStudentsForm tcDetailsOldStudentsForm, ActionErrors errors){
		
		if(tcDetailsOldStudentsForm.getReligionId()!=null && tcDetailsOldStudentsForm.getReligionId().equalsIgnoreCase("Other") && tcDetailsOldStudentsForm.getReligionOthers()==null){
			errors.add(CMSConstants.ADMISSION_TC_DETAILS_REL_REQUIRED,new ActionError(CMSConstants.ADMISSION_TC_DETAILS_REL_REQUIRED));
		}
			
		if(tcDetailsOldStudentsForm.getNationalityId()!=null && tcDetailsOldStudentsForm.getNationalityId().equalsIgnoreCase("Other") && tcDetailsOldStudentsForm.getNationalityOthers()==null){
			errors.add(CMSConstants.ADMISSION_TC_DETAILS_NATIONALITY_REQUIRED,new ActionError(CMSConstants.ADMISSION_TC_DETAILS_NATIONALITY_REQUIRED));
		}
		
		if(tcDetailsOldStudentsForm.getCategoryId()!=null && tcDetailsOldStudentsForm.getCategoryId().equalsIgnoreCase("Other") && tcDetailsOldStudentsForm.getCasteOthers()==null){
			errors.add(CMSConstants.ADMISSION_TC_DETAILS_CASTE_REQUIRED,new ActionError(CMSConstants.ADMISSION_TC_DETAILS_CASTE_REQUIRED));
		}
		
		/*if(tcDetailsOldStudentsForm.getPassed()!=null && tcDetailsOldStudentsForm.getPassed().equalsIgnoreCase("no") && tcDetailsOldStudentsForm.getSubjectsPassed()!=null 
				&& tcDetailsOldStudentsForm.getSubjectsPassed().isEmpty()){
			errors.add(CMSConstants.ADMISSION_TC_DETAILS_SUBJECT_PASSED,new ActionError(CMSConstants.ADMISSION_TC_DETAILS_SUBJECT_PASSED));
		}*/
		return errors;
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTcOldStudentsForPrint(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		TcDetailsOldStudentsForm tcDetailsOldStudentsForm=(TcDetailsOldStudentsForm)form;
		tcDetailsOldStudentsForm.clear();
		return mapping.findForward(CMSConstants.TC_OLD_STUDENT_PRINT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printTcOldStudents(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		TcDetailsOldStudentsForm tcDetailsOldStudentsForm =(TcDetailsOldStudentsForm)form;
		int flag=0;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = tcDetailsOldStudentsForm.validate(mapping, request);
		if(errors.isEmpty()){
			try{
				TcDetailsOldStudents tcDetailsOldStudents=TcDetailsOldStudentsHandler.getInstance().checkForStudent(tcDetailsOldStudentsForm.getRegisterNo());
				if(tcDetailsOldStudents!=null){
					tcDetailsOldStudentsForm.setButton("oldprint");
					flag=TcDetailsOldStudentsHandler.getInstance().getTcOldPrint(tcDetailsOldStudentsForm,tcDetailsOldStudents);
				}else{
						tcDetailsOldStudentsForm.setButton(null);
						messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.tcnotentered"));
						saveMessages(request, messages);
						return mapping.findForward(CMSConstants.TC_OLD_STUDENT_PRINT);
					}
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				tcDetailsOldStudentsForm.setErrorMessage(msg);
				tcDetailsOldStudentsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			tcDetailsOldStudentsForm.setButton(null);
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.TC_OLD_STUDENT_PRINT);
		}
		
		if(flag==1){
			assignListToForm(tcDetailsOldStudentsForm);
			tcDetailsOldStudentsForm.setButton("true");
			return mapping.findForward(CMSConstants.TC_OLD_STUDENT_PRINT);
		}
		
		return mapping.findForward(CMSConstants.TC_OLD_STUDENT_PRINT);
	}
}
