package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceBatchForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceBatchHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AttendanceBatchAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AttendanceBatchAction.class);
	private static final String CLASSMAP = "classMap";
	private static final String SUBJECTMAP ="subjectMap";
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCreatePracticalBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initCreatePracticalBatch input");
		AttendanceBatchForm attendanceBatchForm = (AttendanceBatchForm) form;
		attendanceBatchForm.resetFields();
		setRequiredDatatoForm(attendanceBatchForm);
		setClassMapToRequest(request, attendanceBatchForm);
		log.info("Exit initCreatePracticalBatch input");
		
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH);
	}
	
	
	/**
	 * @param attendanceBatchForm
	 */
	private void setRequiredDatatoForm(AttendanceBatchForm attendanceBatchForm) throws Exception {

		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		Map<Integer,String> attendanceTypes = new HashMap<Integer, String>();
		Iterator<AttendanceTypeTO> itr = attendanceTypeList.iterator();
		AttendanceTypeTO attendanceTypeTO;
		while(itr.hasNext()) {
			attendanceTypeTO = itr.next();
			attendanceTypes.put(attendanceTypeTO.getId(), attendanceTypeTO.getAttendanceTypeName());
		}
		attendanceBatchForm.setAttendanceTypes(attendanceTypes);
		if(attendanceBatchForm.getAttendanceType()!=null && attendanceBatchForm.getAttendanceType().length() !=0) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(attendanceBatchForm.getAttendanceType()));
			// This will add activities in to map they belong to particular attendancetypeid.
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
			attendanceBatchForm.setActivityMap(activityMap);
		}
	}
	/**
	 * 
	 * @param request
	 * @param attendanceBatchForm
	 * Sets all the classes for the current year in request scope
	 * @throws Exception
	 */

	public void setClassMapToRequest(HttpServletRequest request, AttendanceBatchForm attendanceBatchForm)throws Exception {
		log.info("Entering into setClassMapToRequest of CreatePracticalBatchAction");		
		try {
				if (attendanceBatchForm.getYear() == null || attendanceBatchForm.getYear().isEmpty()) {
					//Below statements is used to get the current year and for the year get the class Map.
					Calendar calendar = Calendar.getInstance();
					int currentYear = calendar.get(Calendar.YEAR);
					
					// code by hari
					int year=CurrentAcademicYear.getInstance().getAcademicyear();
					if(year!=0){
						currentYear=year;
					}// end
					Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
					request.setAttribute(CLASSMAP, classMap);
				}
				//Otherwise get the classMap for the selected year
				else {
					int year = Integer.parseInt(attendanceBatchForm.getYear().trim());
					Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
					request.setAttribute(CLASSMAP, classMap);
			}
			//Used to get subjectMap for based on the class
			if(attendanceBatchForm.getClassesId()!=null){
				Map<Integer,String> subjectsMap = new HashMap<Integer,String>();
				String selectedClasses[] = attendanceBatchForm.getClassesId();

				Set<Integer> classesIdsSet = new HashSet<Integer>();
				for (int i = 0; i < selectedClasses.length; i++) {
					classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
				}
	
				List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler
						.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
				Iterator<ClassSchemewise> itr = classSchemewiseList.iterator();
				ClassSchemewise classSchemewise;
				while (itr.hasNext()) {
					classSchemewise = itr.next();
					if (classSchemewise.getCurriculumSchemeDuration()
							.getAcademicYear() != null
							&& classSchemewise.getClasses().getCourse().getId() != 0
							&& classSchemewise.getClasses().getTermNumber() != 0) {
						int year = classSchemewise.getCurriculumSchemeDuration()
								.getAcademicYear();
						int courseId = classSchemewise.getClasses().getCourse()
								.getId();
						int term = classSchemewise.getClasses().getTermNumber();
						Map<Integer,String> tempMap = CommonAjaxHandler.getInstance()
								.getSubjectByCourseIdTermYear(courseId, year, term);
						subjectsMap.putAll(tempMap);
					}
				}
				request.setAttribute(SUBJECTMAP, subjectsMap);
			}
			} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in Create Practical Batch Action", e);
			throw new ApplicationException(e);
		}
			log.info("Leaving into setClassMapToRequest of CreatePracticalBatchAction");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPracticalBatchDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered AttendanceBatchAction - getPracticalBatchDetails");
		
		AttendanceBatchForm attendanceBatchForm = (AttendanceBatchForm) form;
		 ActionErrors errors = attendanceBatchForm.validate(mapping, request);
		String[] classIds=attendanceBatchForm.getClassesId();
		//String[] classIds=request.getParameterValues("classesId");
		if(classIds==null || classIds.length==0){
			errors.add(CMSConstants.ERROR,new ActionError("errors.required","class"));
		}
		attendanceBatchForm.setClassesId(classIds);
		validatePracticalBatchSearch(attendanceBatchForm, errors);
		if (errors.isEmpty()) {
			try {
				attendanceBatchForm.clearFields();
				assignPracticalBatchs(attendanceBatchForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				attendanceBatchForm.setErrorMessage(msg);
				attendanceBatchForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setClassMapToRequest(request, attendanceBatchForm);
			setRequiredDatatoForm(attendanceBatchForm);			
			log.info("Exit AttendanceBatchAction - getPracticalBatchDetails errors not empty ");
			return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH);
		}
		log.info("Entered AttendanceBatchAction - getPracticalBatchDetails");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH_RESULT);
	}
	
	/**
	 * @param attendanceBatchForm
	 * @throws Exception
	 */
	private void assignPracticalBatchs(AttendanceBatchForm attendanceBatchForm) throws Exception{
		List<CreatePracticalBatchTO> batchList = AttendanceBatchHandler.getInstance().getPracticalBatchDetailsBySubjectClass(attendanceBatchForm.getClassesId(),attendanceBatchForm.getSubjectId(),attendanceBatchForm.getActivityAttendance());
		attendanceBatchForm.setBatchList(batchList);
	}


	/**
	 * @param attendanceBatchForm
	 * @param errors
	 * @throws Exception
	 */
	private void validatePracticalBatchSearch(AttendanceBatchForm attendanceBatchForm, ActionErrors errors) throws Exception {
		if((attendanceBatchForm.getSubjectId()==null || attendanceBatchForm.getSubjectId().isEmpty()) && (attendanceBatchForm.getAttendanceType()==null || attendanceBatchForm.getAttendanceType().isEmpty())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.create.practicalBatch.subject.attendance"));
		}
		if((attendanceBatchForm.getSubjectId()!=null && !attendanceBatchForm.getSubjectId().isEmpty()) && (attendanceBatchForm.getAttendanceType()!=null && !attendanceBatchForm.getAttendanceType().isEmpty())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.create.practicalBatch.subject.attendance.either"));
		}
		if(attendanceBatchForm.getAttendanceType()!=null && !attendanceBatchForm.getAttendanceType().isEmpty()){
			if(attendanceBatchForm.getActivityAttendance()==null || attendanceBatchForm.getActivityAttendance().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("errors.required","Activity Type"));
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
	public ActionForward searchStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered AttendanceBatchAction - getPracticalBatchDetails");
		
		AttendanceBatchForm attendanceBatchForm = (AttendanceBatchForm) form;
		 ActionErrors errors = attendanceBatchForm.validate(mapping, request);
		validateSearchBatch(attendanceBatchForm,errors);
		if (errors.isEmpty()) {
			try {
				checkActivateOrDeActivate(attendanceBatchForm,errors,request);
				if(errors.isEmpty()){
					List<CreatePracticalBatchTO> studentList=AttendanceBatchHandler.getInstance().getStudentList(attendanceBatchForm);
					if(studentList.isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
					}
					//Below condition is used to display the student lists in UI in two tables
					int halfLength = 0;
					int totLength = studentList.size();
					if(totLength % 2 == 0) {
						halfLength = totLength / 2;
					}
					else{
						halfLength = (totLength / 2) + 1;
					}
					attendanceBatchForm.setStudentList(studentList);
					attendanceBatchForm.setHalfLength(halfLength);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				attendanceBatchForm.setErrorMessage(msg);
				attendanceBatchForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit AttendanceBatchAction - getPracticalBatchDetails errors not empty ");
		}
		log.info("Entered AttendanceBatchAction - getPracticalBatchDetails");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH_RESULT);
	}

	/**
	 * @param attendanceBatchForm
	 * @param errors
	 * @param request
	 * @throws Exception
	 */
	private void checkActivateOrDeActivate(AttendanceBatchForm attendanceBatchForm, ActionErrors errors,HttpServletRequest request) throws Exception {
		int batchId=0;
		if(attendanceBatchForm.getBatchId()!=null && !attendanceBatchForm.getBatchId().isEmpty()){
			batchId=Integer.parseInt(attendanceBatchForm.getBatchId());
		}
		List<Batch> batchList=AttendanceBatchHandler.getInstance().checkAlreadyExists(attendanceBatchForm);
		Iterator<Batch> itr=batchList.iterator();
		String alreadyExists="";
		String reactiveIds="";
		while (itr.hasNext()) {
			Batch batch = (Batch) itr.next();
			if(batch.getId()!=batchId){
				if(batch.getIsActive()){
					if(alreadyExists.isEmpty())
						alreadyExists=batch.getBatchName();
					else
						alreadyExists=alreadyExists+","+batch.getBatchName();
				}else{
					if(reactiveIds.isEmpty())
						reactiveIds=String.valueOf(batch.getId());
					else
						reactiveIds=alreadyExists+","+batch.getId();
				}
			}
		}
		if(!alreadyExists.isEmpty() || !reactiveIds.isEmpty()){
			if(!alreadyExists.isEmpty())
				errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_EXIST));
			if(!reactiveIds.isEmpty()){
				attendanceBatchForm.setBatchId(reactiveIds);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE));
			}
			saveErrors(request, errors);
		}
		
	}


	/**
	 * @param attendanceBatchForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateSearchBatch(AttendanceBatchForm attendanceBatchForm,ActionErrors errors) throws Exception {
		if(attendanceBatchForm.getRegNoFrom()!=null  && !attendanceBatchForm.getRegNoFrom().isEmpty() && !StringUtils.isAlphanumeric(attendanceBatchForm.getRegNoFrom().trim())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
		}
		if(attendanceBatchForm.getRegNoTo()!=null && !attendanceBatchForm.getRegNoTo().isEmpty() && !StringUtils.isAlphanumeric(attendanceBatchForm.getRegNoTo().trim())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
		}
		if(attendanceBatchForm.getBatchName()!=null && !attendanceBatchForm.getBatchName().isEmpty()){
			if(validateBatchName(attendanceBatchForm.getBatchName().trim())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCHNAME_INVALID));
			}
		}
	}
	
	/**
	 * 
	 * @param Used to validate batchName
	 * @return
	 * Only alphanumeric and space is allowed
	 */
	private boolean validateBatchName(String batchName) throws Exception{	
		log.info("Entering into validateBatchName of CreatePracticalBatchAction");
		boolean result=false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \t]+");
        Matcher matcher = pattern.matcher(batchName);
        result = matcher.find();
        log.info("Leaving into validateBatchName of CreatePracticalBatchAction");
        return result;
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePracticalBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  deletePracticalBatch action");
		AttendanceBatchForm attendanceBatchForm = (AttendanceBatchForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, attendanceBatchForm);
			boolean isDeleted=false;
			//Request the handler to delete the selected roomtype
			isDeleted = AttendanceBatchHandler.getInstance().deletePracticalBatch(attendanceBatchForm.getBatchId(),attendanceBatchForm.getUserId());
			//If success then append the success message
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_DELETE_SUCCESS));
				assignPracticalBatchs(attendanceBatchForm);
				saveMessages(request, messages);
				attendanceBatchForm.setBatchId(null);
			}
			//Else add the error message
			else {
				assignPracticalBatchs(attendanceBatchForm);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_DELETE_FAILED ));
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in deletePracticalBatch", e);
			String msg = super.handleApplicationException(e);
			attendanceBatchForm.setErrorMessage(msg);
			attendanceBatchForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit from  deletePracticalBatch");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH_RESULT); 	
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reActivatePracticalBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  reActivatePracticalBatch action");
		AttendanceBatchForm attendanceBatchForm = (AttendanceBatchForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, attendanceBatchForm);
			boolean isDeleted=false;
			//Request the handler to delete the selected roomtype
			isDeleted = AttendanceBatchHandler.getInstance().reactivePracticalBatch(attendanceBatchForm.getBatchId(),attendanceBatchForm.getUserId());
			//If success then append the success message
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE_SUCCESS));
				assignPracticalBatchs(attendanceBatchForm);
				saveMessages(request, messages);
				attendanceBatchForm.setBatchId(null);
				attendanceBatchForm.setBatchName(null);
			}
			//Else add the error message
			else {
				assignPracticalBatchs(attendanceBatchForm);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE_FAILED ));
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in reActivatePracticalBatch", e);
			String msg = super.handleApplicationException(e);
			attendanceBatchForm.setErrorMessage(msg);
			attendanceBatchForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit from  deletePracticalBatch");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH_RESULT); 	
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPracticalBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered AttendanceBatchAction - getPracticalBatchDetails");
		
		AttendanceBatchForm attendanceBatchForm = (AttendanceBatchForm) form;
		 ActionErrors errors = attendanceBatchForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
					editPracticalBatchToForm(attendanceBatchForm,request);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				attendanceBatchForm.setErrorMessage(msg);
				attendanceBatchForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit AttendanceBatchAction - getPracticalBatchDetails errors not empty ");
		}
		log.info("Entered AttendanceBatchAction - getPracticalBatchDetails");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH_RESULT);
	}
	
	
	/**
	 * @param attendanceBatchForm
	 * @param request
	 * @throws Exception
	 */
	private void editPracticalBatchToForm(AttendanceBatchForm attendanceBatchForm, HttpServletRequest request) throws Exception {
		List<CreatePracticalBatchTO> studentList=AttendanceBatchHandler.getInstance().getStudentListForEdit(attendanceBatchForm);
		attendanceBatchForm.setBatchName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(attendanceBatchForm.getBatchId()),"Batch",true,"batchName"));
		//Below condition is used to display the student lists in UI in two tables
		int halfLength = 0;
		int totLength = studentList.size();
		if(totLength % 2 == 0) {
			halfLength = totLength / 2;
		}
		else{
			halfLength = (totLength / 2) + 1;
		}
		attendanceBatchForm.setStudentList(studentList);
		attendanceBatchForm.setHalfLength(halfLength);
		request.setAttribute(CMSConstants.OPERATION,"edit");
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePracticalBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  savePracticalBatch action");
		AttendanceBatchForm attendanceBatchForm = (AttendanceBatchForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		validateSearchBatch(attendanceBatchForm,errors);
		validateAttendanceBatch(attendanceBatchForm,errors);
		checkActivateOrDeActivate(attendanceBatchForm, errors, request);
		if (errors.isEmpty()) {
			try {
				setUserId(request, attendanceBatchForm);
				boolean isAdded=false;
				//Request the handler to delete the selected roomtype
				isAdded = AttendanceBatchHandler.getInstance().savePracticalBatch(attendanceBatchForm);
				//If success then append the success message
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_ADDED));
					saveMessages(request, messages);
					attendanceBatchForm.clearFields();
				}
				//Else add the error message
				else {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_ADD_FAILED ));
					addErrors(request, errors);
				}
				assignPracticalBatchs(attendanceBatchForm);
			} catch (Exception e) {
				log.error("Error occured in savePracticalBatch", e);
				String msg = super.handleApplicationException(e);
				attendanceBatchForm.setErrorMessage(msg);
				attendanceBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			resetCheckFields(attendanceBatchForm);
			saveErrors(request, errors);
		}
		log.info("exit from  deletePracticalBatch");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH_RESULT); 	
	}


	/**
	 * @param attendanceBatchForm
	 * @throws Exception
	 */
	private void resetCheckFields(AttendanceBatchForm attendanceBatchForm) throws Exception {
		List<CreatePracticalBatchTO> studentList=attendanceBatchForm.getStudentList();
		Iterator<CreatePracticalBatchTO> itr=studentList.iterator();
		List<CreatePracticalBatchTO> resetList=new ArrayList<CreatePracticalBatchTO>();
		while (itr.hasNext()) {
			CreatePracticalBatchTO createPracticalBatchTO = itr.next();
			if(createPracticalBatchTO.isCheckValue()){
			createPracticalBatchTO.setDummyCheckValue(true);
			createPracticalBatchTO.setCheckValue(true); // made true to allow duplicate students for different batches
			createPracticalBatchTO.setTempCheckValue(true);
			}
			else{
				createPracticalBatchTO.setDummyCheckValue(false);
				createPracticalBatchTO.setCheckValue(false);
				createPracticalBatchTO.setTempCheckValue(false);
			}
			resetList.add(createPracticalBatchTO);
		}
		attendanceBatchForm.setStudentList(resetList);
	}


	/**
	 * @param attendanceBatchForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateAttendanceBatch(AttendanceBatchForm attendanceBatchForm, ActionErrors errors) throws Exception {
		List<CreatePracticalBatchTO> studentList=attendanceBatchForm.getStudentList();
		Map<Integer,String> studentExistMap=AttendanceBatchHandler.getInstance().getStudentExistInAnotherBatch(attendanceBatchForm);
		Iterator<CreatePracticalBatchTO> itr=studentList.iterator();
		String existStudents="";
		boolean notSelected=true;
		int count=0;
		int count1=0;
		while (itr.hasNext()) {
			
			CreatePracticalBatchTO to =itr.next();
			if(to.isCheckValue()){
				count++;
				to.setChecked("true");
			}else{
				to.setChecked("false");
			}
			if(to.getChecked().equalsIgnoreCase("true")){
				count1++;
				notSelected=false;
				if(studentExistMap.containsKey(to.getStudentTO().getId())){
					if(existStudents.isEmpty())
						existStudents=studentExistMap.get(to.getStudentTO().getId());
					else
						existStudents=existStudents+","+studentExistMap.get(to.getStudentTO().getId());
				}
			}else{
				to.setTempCheckValue(false);
				to.setDummyCheckValue(false);
				to.setCheckValue(false);
			}
		}
		if(count==0 && count1==0){
			attendanceBatchForm.setCheckBoxSelectAll(false);
		}
		else if(count==count1){
			attendanceBatchForm.setCheckBoxSelectAll(true);
		}else{
			attendanceBatchForm.setCheckBoxSelectAll(false);
		}
		if(notSelected){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_SELECTSTUDENT));
		}
		if(!existStudents.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.student.exists",existStudents));
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.batch.create.confirm"));
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
	public ActionForward updatePracticalBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  deletePracticalBatch action");
		AttendanceBatchForm attendanceBatchForm = (AttendanceBatchForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		if(isCancelled(request)){
			editPracticalBatchToForm(attendanceBatchForm, request);
			return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH_RESULT);
		}
		validateSearchBatch(attendanceBatchForm,errors);
		validateAttendanceBatch(attendanceBatchForm,errors);
		checkActivateOrDeActivate(attendanceBatchForm, errors, request);
		if (errors.isEmpty()) {
			try {
				setUserId(request, attendanceBatchForm);
				boolean isAdded=false;
				//Request the handler to delete the selected roomtype
				isAdded = AttendanceBatchHandler.getInstance().savePracticalBatch(attendanceBatchForm);
				//If success then append the success message
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_UPDATE_SUCCESS));
					saveMessages(request, messages);
					attendanceBatchForm.clearFields();
				}
				//Else add the error message
				else {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_UPDATE_FAILED ));
					addErrors(request, errors);
				}
				assignPracticalBatchs(attendanceBatchForm);
			} catch (Exception e) {
				log.error("Error occured in deletePracticalBatch", e);
				String msg = super.handleApplicationException(e);
				attendanceBatchForm.setErrorMessage(msg);
				attendanceBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			request.setAttribute(CMSConstants.OPERATION,"edit");
			resetCheckFields(attendanceBatchForm);
			saveErrors(request, errors);
		}
		log.info("exit from  deletePracticalBatch");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH_RESULT); 	
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  savePracticalBatch action");
		AttendanceBatchForm attendanceBatchForm = (AttendanceBatchForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		validateSearchBatch(attendanceBatchForm,errors);
		checkActivateOrDeActivate(attendanceBatchForm, errors, request);
		if (errors.isEmpty()) {
			try {
				setUserId(request, attendanceBatchForm);
				boolean isAdded=false;
				//Request the handler to delete the selected roomtype
				isAdded = AttendanceBatchHandler.getInstance().savePracticalBatch(attendanceBatchForm);
				//If success then append the success message
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_ADDED));
					saveMessages(request, messages);
					attendanceBatchForm.clearFields();
				}
				//Else add the error message
				else {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_ADD_FAILED ));
					addErrors(request, errors);
				}
				assignPracticalBatchs(attendanceBatchForm);
			} catch (Exception e) {
				log.error("Error occured in savePracticalBatch", e);
				String msg = super.handleApplicationException(e);
				attendanceBatchForm.setErrorMessage(msg);
				attendanceBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			resetCheckFields(attendanceBatchForm);
			saveErrors(request, errors);
		}
		log.info("exit from  deletePracticalBatch");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_BATCH_RESULT); 	
	}
	
	
}
