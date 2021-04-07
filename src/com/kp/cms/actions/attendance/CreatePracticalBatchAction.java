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
import javax.servlet.http.HttpSession;

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
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.CreatePracticalBatchForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.attendance.CreatePracticalBatchHandler;
import com.kp.cms.helpers.attendance.CreatePracticalBatchHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.ActivityTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.ClassSchemewiseTO;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;
import com.kp.cms.transactions.attandance.IActivityAttendenceTransaction;
import com.kp.cms.transactions.attandance.ICreatePracticalBatchTransaction;
import com.kp.cms.transactionsimpl.attendance.ActivityAttendenceTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.CreatePracticalBatchTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;


@SuppressWarnings("deprecation")
public class CreatePracticalBatchAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CreatePracticalBatchAction.class);
	private static final String SAVE = "save";
	private static final String SEARCH = "search";
	private static final String BATCHTO = "batchTO";
	private static final String YES = "yes";
	private static final String NO = "no";
	private static final String CLASSMAP = "classMap";
	private static final String SUBJECTMAP ="subjectMap";

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes createPracticalBatch
	 * 			Initializes classMap and subjectMap
	 * @throws Exception
	 */
	
	public ActionForward initCreatePracticalBatch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into initCreatePracticalBatch of CreatePracticalBatchAction");
		CreatePracticalBatchForm practicalBatchForm = (CreatePracticalBatchForm) form;
		try {
			//Setting the classMap to request scope
			setClassMapToRequest(request,practicalBatchForm);
			practicalBatchForm.clear();
			if(practicalBatchForm.getStudentList()!=null && !practicalBatchForm.getStudentList().isEmpty()){
			practicalBatchForm.clearList();
			}
			if(practicalBatchForm.getReset()!=null){
			practicalBatchForm.setReset(null);
			}			
				practicalBatchForm.setMessage(null);
				practicalBatchForm.setExistingStudentList(null);
				setRequestedDataToForm(practicalBatchForm);

		} catch (Exception e) {
			log.error("Error occured in initCreatePracticalBatch of CreatePracticalBatchAction", e);
				String msg = super.handleApplicationException(e);
				practicalBatchForm.setErrorMessage(msg);
				practicalBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initCreatePracticalBatch of CreatePracticalBatchAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_INIT);
	}

	/**
	 * @param practicalBatchForm
	 * @throws Exception
	 */
	private void setRequestedDataToForm(CreatePracticalBatchForm practicalBatchForm) throws Exception{
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		Map<Integer,String> attendanceTypes = new HashMap<Integer, String>();
		Iterator<AttendanceTypeTO> itr = attendanceTypeList.iterator();
		AttendanceTypeTO attendanceTypeTO;
		while(itr.hasNext()) {
			attendanceTypeTO = itr.next();
			attendanceTypes.put(attendanceTypeTO.getId(), attendanceTypeTO.getAttendanceTypeName());
		}
		practicalBatchForm.setAttendanceTypes(attendanceTypes);
		if(practicalBatchForm.getAttendanceTypeId()!=null && practicalBatchForm.getAttendanceTypeId().length() !=0) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(practicalBatchForm.getAttendanceTypeId()));
			// This will add activities in to map they belong to particular attendancetypeid.
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
			practicalBatchForm.setActivityMap(activityMap);
		}
		
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns the second page to search the candidates
	 * @throws Exception
	 */
	public ActionForward getPracticalBatchDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into getPracticalBatchDetails of CreatePracticalBatchAction");
		CreatePracticalBatchForm practicalBatchForm = (CreatePracticalBatchForm) form;
		 ActionErrors errors = practicalBatchForm.validate(mapping, request);
		validatePracticalBatchSearch(practicalBatchForm,errors);
		try {
			if (errors.isEmpty()) {
				practicalBatchForm.setExistingStudentList(null);
				practicalBatchForm.setMessage(null);
				//Create a TO object and store in session and keep classSchemewiseId and subjectID in that
				if(practicalBatchForm.getReset()==null || practicalBatchForm.getReset().isEmpty()){
					HttpSession session = request.getSession(false);
					CreatePracticalBatchTO batchTO = new CreatePracticalBatchTO();
					ClassSchemewiseTO classSchemewiseTO = new ClassSchemewiseTO();
					classSchemewiseTO.setId(Integer.parseInt(practicalBatchForm.getClassSchemewiseId()));
					batchTO.setClassSchemewiseTO(classSchemewiseTO);
					if(practicalBatchForm.getSubjectId()!=null && !practicalBatchForm.getSubjectId().isEmpty()){
					SubjectTO subjectTO = new SubjectTO();
					subjectTO.setId(Integer.parseInt(practicalBatchForm	.getSubjectId()));
					batchTO.setSubjectTO(subjectTO);
					}
					if(practicalBatchForm.getActivityId()!=null && !practicalBatchForm.getActivityId().isEmpty()){
					ActivityTO activityTO=new ActivityTO();
					activityTO.setId(Integer.parseInt(practicalBatchForm.getActivityId()));
					batchTO.setActivityTO(activityTO);
					batchTO.setAttendanceTypeId(practicalBatchForm.getAttendanceTypeId());
					}
					session.setAttribute(BATCHTO, batchTO);
					//Show the roll no./regd. no. as configured in program for this classSchemewiseId
					IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
					boolean isRegnoCheck = activityAttendenceTransaction.checkIsRegisterNo(Integer.parseInt(practicalBatchForm.getClassSchemewiseId()));		
					if(isRegnoCheck){
						practicalBatchForm.setRegdNoDisplay(YES);
					}
					else{
						practicalBatchForm.setRegdNoDisplay(NO);
					}
					//Get all the practical batches for the class and subject
					int classSchemewiseId = Integer.parseInt(practicalBatchForm.getClassSchemewiseId());
					int subjectId =0;
					if(practicalBatchForm.getSubjectId()!=null && !practicalBatchForm.getSubjectId().isEmpty()){
						subjectId = Integer.parseInt(practicalBatchForm.getSubjectId());
					}
					int activityId=0;
					if(practicalBatchForm.getActivityId()!=null && !practicalBatchForm.getActivityId().isEmpty())
						activityId=Integer.parseInt(practicalBatchForm.getActivityId());
					
					assignPracticalBatchAlltoForm(practicalBatchForm, classSchemewiseId, subjectId ,activityId);
					if(practicalBatchForm.getStudentList()!=null && !practicalBatchForm.getStudentList().isEmpty()){
					practicalBatchForm.clearList();
					}
					practicalBatchForm.clear();
					request.setAttribute(CMSConstants.OPERATION, SEARCH);
					return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
				}
				//Below condition works when reset button is clicked in save mode.
				else{
					request.setAttribute(CMSConstants.OPERATION, SEARCH);
					practicalBatchForm.setBatchName(null);
					List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
					if(studentList!=null && !studentList.isEmpty()){
						Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
						while (iterator.hasNext()) {
							CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
								createPracticalBatchTO.setCheckValue(false);
								createPracticalBatchTO.setTempCheckValue(false);								
								createPracticalBatchTO.setDummyCheckValue(false);
						}								
					}
					return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
				}
			}
		} catch (Exception e) {
			log.error("Error occured in getPracticalBatchDetails of CreatePracticalBatchAction", e);
				String msg = super.handleApplicationException(e);
				practicalBatchForm.setErrorMessage(msg);
				practicalBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setClassMapToRequest(request, practicalBatchForm);
		setRequestedDataToForm(practicalBatchForm);
		saveErrors(request, errors);
		log.info("Leaving into getPracticalBatchDetails of CreatePracticalBatchAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_INIT);
	}

	private void validatePracticalBatchSearch(
			CreatePracticalBatchForm practicalBatchForm, ActionErrors errors) throws Exception {
		if((practicalBatchForm.getSubjectId()==null || practicalBatchForm.getSubjectId().isEmpty()) && (practicalBatchForm.getAttendanceTypeId()==null || practicalBatchForm.getAttendanceTypeId().isEmpty())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.create.practicalBatch.subject.attendance"));
		}
		if((practicalBatchForm.getSubjectId()!=null && !practicalBatchForm.getSubjectId().isEmpty()) && (practicalBatchForm.getAttendanceTypeId()!=null && !practicalBatchForm.getAttendanceTypeId().isEmpty())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.create.practicalBatch.subject.attendance.either"));
		}
		if(practicalBatchForm.getAttendanceTypeId()!=null && !practicalBatchForm.getAttendanceTypeId().isEmpty()){
			if(practicalBatchForm.getActivityId()==null || practicalBatchForm.getActivityId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("errors.required","Activity Type"));
			}
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  Used to search the Students based on the subjectgroupID and classSchemewiseID
	 * @throws Exception
	 */

	public ActionForward searchStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into searchStudent of CreatePracticalBatchAction");
		CreatePracticalBatchForm practicalBatchForm = (CreatePracticalBatchForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			practicalBatchForm.setExistingStudentList(null);
			practicalBatchForm.setMessage(null);
			//Retrieve the object from session and send to handler for searching the student
			HttpSession session = request.getSession(false);
			CreatePracticalBatchTO batchTO = (CreatePracticalBatchTO) session.getAttribute(BATCHTO);	
			
			//Get all the practical batches for the class and subject
			int subjectId=0;
			if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
				subjectId=batchTO.getSubjectTO().getId();
			}
			int activityId=0;
			if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
				activityId=batchTO.getActivityTO().getId();
			}
			assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(),subjectId,activityId);
			boolean isValidRegdNo;
			//Below condition is used to validate the regd No. Only alphanumeric is allowed.
			if(practicalBatchForm.getRegNoFrom()!=null  && !practicalBatchForm.getRegNoFrom().isEmpty() 
			&&  practicalBatchForm.getRegNoTo()!=null && !practicalBatchForm.getRegNoTo().isEmpty() ){
				isValidRegdNo = validateRegdNos(practicalBatchForm.getRegNoFrom().trim(), practicalBatchForm.getRegNoTo().trim()); 
				if(!isValidRegdNo){
					if(practicalBatchForm.getRegdNoDisplay()!= null && practicalBatchForm.getRegdNoDisplay().equalsIgnoreCase(YES)){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
					request.setAttribute(CMSConstants.OPERATION, SEARCH);
					}
					else{
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_ROLLNO_TYPE));
						request.setAttribute(CMSConstants.OPERATION, SEARCH);
					}
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
					}
				}
			//Get the studentList for the class and subject by calling the handler method
				List<CreatePracticalBatchTO> studentList = CreatePracticalBatchHandler.getInstance().searchStudents(practicalBatchForm,batchTO);		
				if (studentList != null && !studentList.isEmpty()) {
					practicalBatchForm.setStudentList(studentList);
					request.setAttribute(CMSConstants.OPERATION, SAVE);
				} else {
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_STUDENTS_NOTFOUND));
				request.setAttribute(CMSConstants.OPERATION, SEARCH);
				}
			}
			catch (Exception e) {
			log.error("Error occured in searchStudent of CreatePracticalBatchAction", e);
			if(e instanceof BusinessException){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_SEARCH_FAILED));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
			}
			else {
				String msg = super.handleApplicationException(e);
				practicalBatchForm.setErrorMessage(msg);
				practicalBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		}
		saveMessages(request, messages);
		saveErrors(request, errors);
		log.info("Leaving into searchStudent of CreatePracticalBatchAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Used while inserting a practical batch for a class and subject
	 * @throws Exception
	 */

	public ActionForward savePracticalBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into savePracticalBatch of CreatePracticalBatchAction");
		CreatePracticalBatchForm practicalBatchForm = (CreatePracticalBatchForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		CreatePracticalBatchTO batchTO = (CreatePracticalBatchTO) session.getAttribute(BATCHTO);
			try {					
				if(practicalBatchForm.getExistingStudentList()!=null || practicalBatchForm.getMessage()!=null){
					practicalBatchForm.setExistingStudentList(null);
					practicalBatchForm.setMessage(null);
				}
			//Below condition is used to validate the batch name. Only alphanumeric is allowed
				if(!practicalBatchForm.getBatchName().trim().isEmpty()){
						boolean invalidBatchName = validateBatchName(practicalBatchForm.getBatchName().trim());
							if(invalidBatchName){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCHNAME_INVALID));
								saveErrors(request, errors);
								List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
								//Below condition works when user is not checked any student and saves. After validation it remains in the previous state
								if(studentList!=null && !studentList.isEmpty()){
									Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
									while (iterator.hasNext()) {
										CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
										if(createPracticalBatchTO.isCheckValue()){
										createPracticalBatchTO.setDummyCheckValue(true);
										createPracticalBatchTO.setCheckValue(false);
										createPracticalBatchTO.setTempCheckValue(true);
										}
										else{
											createPracticalBatchTO.setDummyCheckValue(false);
											createPracticalBatchTO.setCheckValue(false);
											createPracticalBatchTO.setTempCheckValue(false);
										}
									}
								}
								//Get all the practical batches for the class and subject
								int subjectId=0;
								if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
									subjectId=batchTO.getSubjectTO().getId();
								}
								int activityId=0;
								if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
									activityId=batchTO.getActivityTO().getId();
								}
								assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(), subjectId,activityId);
								request.setAttribute(CMSConstants.OPERATION, SAVE);					
							}
								else{
								setUserId(request, practicalBatchForm);						
								//Check for the duplicate with batchName, classId and subjectId . If exists append the error message
		 						Batch batch = CreatePracticalBatchHandler.getInstance().getBatchDetailsbyBatchName(practicalBatchForm.getBatchName().trim(),batchTO);
									if(batch!=null && batch.getIsActive()){
										errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_EXIST));
										saveErrors(request, errors);
										
										List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
										//Below condition keeps the students checked which are are selected while tried for inserting
										if(studentList!=null && !studentList.isEmpty()){
											Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
											while (iterator.hasNext()) {
												CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
												if(createPracticalBatchTO.isCheckValue()){
												createPracticalBatchTO.setDummyCheckValue(true);
												createPracticalBatchTO.setCheckValue(false);
												createPracticalBatchTO.setTempCheckValue(true);
												}
												else{
													createPracticalBatchTO.setDummyCheckValue(false);
													createPracticalBatchTO.setCheckValue(false);
													createPracticalBatchTO.setTempCheckValue(false);
												}
											}
										}
										//Get all the practical batches for the class and subject
										int subjectId=0;
										if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
											subjectId=batchTO.getSubjectTO().getId();
										}
										int activityId=0;
										if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
											activityId=batchTO.getActivityTO().getId();
										}
										assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(), subjectId,activityId);
										request.setAttribute(CMSConstants.OPERATION, SAVE);	
										return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
									}
									//Works on reactivation
									//If batch in already exists but in inactive mode
									else if(batch!=null && !batch.getIsActive()){								
										List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
										if(studentList!=null && !studentList.isEmpty()){
											Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
											while (iterator.hasNext()) {
												CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
												if(createPracticalBatchTO.isCheckValue()){
												createPracticalBatchTO.setDummyCheckValue(true);
												createPracticalBatchTO.setCheckValue(false);
												createPracticalBatchTO.setTempCheckValue(true);
												}
												else{
													createPracticalBatchTO.setDummyCheckValue(false);
													createPracticalBatchTO.setCheckValue(false);
													createPracticalBatchTO.setTempCheckValue(false);
												}
											}
										}//Store the old batch name in form. WIll be used while reactivation
										practicalBatchForm.setOldBatchName(practicalBatchForm.getBatchName());
										//Get all the practical batches for the class and subject
										int subjectId=0;
										if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
											subjectId=batchTO.getSubjectTO().getId();
										}
										int activityId=0;
										if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
											activityId=batchTO.getActivityTO().getId();
										}
										assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(), subjectId,activityId);
										errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE));
										saveErrors(request, errors);
										request.setAttribute(CMSConstants.OPERATION, SEARCH);	
										return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
									}
									//Else insert the selected students
								else{
									//Duplicate check for the regd no./roll no. if exists for any batch
								 List<StudentTO> existingStudentList = CreatePracticalBatchHandler.getInstance().duplicateCheckforStudent(practicalBatchForm, batchTO);
								if(existingStudentList.size()!=0){
									//Set the list(duplicate students present) into the form
									practicalBatchForm.setExistingStudentList(existingStudentList);
									//Get all the practical batches for the class and subject
									request.setAttribute(CMSConstants.OPERATION, SAVE);
									List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
									if(studentList!=null && !studentList.isEmpty()){
										Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
										//Below statements are used to refresh the students.
										while (iterator.hasNext()) {
											CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
											if(createPracticalBatchTO.isCheckValue()){
											createPracticalBatchTO.setDummyCheckValue(true);
											createPracticalBatchTO.setCheckValue(false);
											createPracticalBatchTO.setTempCheckValue(true);
											}
											else{
												createPracticalBatchTO.setDummyCheckValue(false);
												createPracticalBatchTO.setCheckValue(false);
												createPracticalBatchTO.setTempCheckValue(false);
											}
										}
									}
									return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
								}
								else{
									boolean isAdded = CreatePracticalBatchHandler.getInstance().savePracticalBatch(practicalBatchForm, batchTO);
									//If Add operation is success then add the success message
									if(isAdded){
											messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_ADDED));
											saveMessages(request, messages);
											request.setAttribute(CMSConstants.OPERATION, SAVE);
											practicalBatchForm.setBatchName(null);
											practicalBatchForm.setRegNoFrom(null);
											practicalBatchForm.setRegNoTo(null);
											List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
											if(studentList!=null && !studentList.isEmpty()){
												Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
												//Makes the students unchecked after successful addition
												while (iterator.hasNext()) {
													CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
													if(createPracticalBatchTO.isCheckValue()){
													createPracticalBatchTO.setCheckValue(false);
													}
													createPracticalBatchTO.setTempCheckValue(false);
													if(createPracticalBatchTO.isDummyCheckValue()){
													createPracticalBatchTO.setDummyCheckValue(false);
													}
												}								
											}
											//Get all the practical batches for the class and subject
											int subjectId=0;
											if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
												subjectId=batchTO.getSubjectTO().getId();
											}
											int activityId=0;
											if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
												activityId=batchTO.getActivityTO().getId();
											}
											assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(), subjectId,activityId);
											return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
										}
										//Else append the error message.
										else{
											errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_ADD_FAILED));
											//Get all the practical batches for the class and subject
											int subjectId=0;
											if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
												subjectId=batchTO.getSubjectTO().getId();
											}
											int activityId=0;
											if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
												activityId=batchTO.getActivityTO().getId();
											}
											assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(),subjectId,activityId);
											request.setAttribute(CMSConstants.OPERATION, SAVE);
										}
									}
								}
						}
				}
			//Below condition works when batch name is empty. Append the error message
			else{
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_EMPTY));
			request.setAttribute(CMSConstants.OPERATION, SAVE);
			List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
			if(studentList!=null && !studentList.isEmpty()){
				Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
				//Makes the students unchecked after successful addition
				while (iterator.hasNext()) {
					CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
					if(createPracticalBatchTO.isCheckValue()){
					createPracticalBatchTO.setCheckValue(false);
					}
					createPracticalBatchTO.setTempCheckValue(false);
					createPracticalBatchTO.setDummyCheckValue(true);
				}								
			}
			//Get all the practical batches for the class and subject
			int subjectId=0;
			if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
				subjectId=batchTO.getSubjectTO().getId();
			}
			int activityId=0;
			if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
				activityId=batchTO.getActivityTO().getId();
			}
			assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(),subjectId,activityId);
			}
		}
		catch(Exception e){
			if(e instanceof BusinessException){
				//Below condition works when user is not checked any student and saves. After validation it remains in the previous state
				List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
				if(studentList!=null && !studentList.isEmpty()){
					Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
					//Below condition keeps the student list in previous state
					while (iterator.hasNext()) {
						CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
						if(createPracticalBatchTO.isDummyCheckValue()){
						createPracticalBatchTO.setDummyCheckValue(false);
						}
						if(createPracticalBatchTO.isTempCheckValue()){
						createPracticalBatchTO.setTempCheckValue(false);	
						}
					}
				}
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_SELECTSTUDENT));
				request.setAttribute(CMSConstants.OPERATION, SAVE);
				practicalBatchForm.setRegNoFrom(null);
				practicalBatchForm.setRegNoTo(null);
				saveErrors(request, errors);
				//Get all the practical batches for the class and subject
				int subjectId=0;
				if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
					subjectId=batchTO.getSubjectTO().getId();
				}
				int activityId=0;
				if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
					activityId=batchTO.getActivityTO().getId();
				}
				assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(), subjectId,activityId);
				return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
			}				
				log.info("Exception occured at savePracticalBatch of CreatePracticalBatchAction", e);
				String msg = super.handleApplicationException(e);
				practicalBatchForm.setErrorMessage(msg);
				practicalBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		saveErrors(request, errors);
		resetStudentList(practicalBatchForm);
		log.info("Leaving into savePracticalBatch of CreatePracticalBatchAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
	}

	/**
	 * 
	 * @param request
	 * @param practicalBatchForm
	 * Sets all the classes for the current year in request scope
	 * @throws Exception
	 */

	public void setClassMapToRequest(HttpServletRequest request, CreatePracticalBatchForm practicalBatchForm)throws Exception {
		log.info("Entering into setClassMapToRequest of CreatePracticalBatchAction");		
		try {
				if (practicalBatchForm.getYear() == null || practicalBatchForm.getYear().isEmpty()) {
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
					int year = Integer.parseInt(practicalBatchForm.getYear().trim());
					Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
					request.setAttribute(CLASSMAP, classMap);
			}
			//Used to get subjectMap for based on the class
				if(practicalBatchForm.getClassSchemewiseId()!=null && practicalBatchForm.getClassSchemewiseId().length()!=0){
					Map<Integer,String> subjectMap = new HashMap<Integer,String>();
					ClassSchemewise classSchemewise = CommonAjaxHandler.getInstance().getDetailsonClassSchemewiseId(Integer.parseInt(practicalBatchForm.getClassSchemewiseId()));
						if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=null && classSchemewise.getClasses().getCourse().getId()!=0 && classSchemewise.getClasses().getTermNumber()!=0){
							int year=classSchemewise.getCurriculumSchemeDuration().getAcademicYear();
							int courseId=classSchemewise.getClasses().getCourse().getId();
							int term=classSchemewise.getClasses().getTermNumber();
							subjectMap = CommonAjaxHandler.getInstance().getSubjectByCourseIdTermYear(courseId,year,term);
						}
				request.setAttribute(SUBJECTMAP, subjectMap);
			}
			} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in Create Practical Batch Action", e);
			throw new ApplicationException(e);
		}
			log.info("Leaving into setClassMapToRequest of CreatePracticalBatchAction");
	}
	
	/**
	 * 
	 * @param regdNoFrom
	 * @param regdNoTo
	 * @return Used to validate regd nos.
	 * Only alphanumeric is allowed
	 * @throws Exception
	 */
	private boolean validateRegdNos(String regdNoFrom, String regdNoTo) throws Exception{
		log.info("Entering into validateRegdNos of CreatePracticalBatchAction");
		if(StringUtils.isAlphanumeric(regdNoFrom) && StringUtils.isAlphanumeric(regdNoTo)){
			return true;
		}
		else{
			log.info("Leaving into validateRegdNos of CreatePracticalBatchAction");
			return false;
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
	 * 
	 * @param practicalBatchForm
	 * @throws Exception
	 * * Used to reset the studentList
	 * Used after addition and after validation fails
	 */
	
	public void resetStudentList(CreatePracticalBatchForm practicalBatchForm)throws Exception{
		log.info("Entering into resetStudentList of CreatePracticalBatchAction");
		List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
			while (iterator.hasNext()) {
				CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
					if(createPracticalBatchTO.isCheckValue()){
					createPracticalBatchTO.setCheckValue(false);
					createPracticalBatchTO.setTempCheckValue(true);
					}
					if(createPracticalBatchTO.isDummyCheckValue()){
					createPracticalBatchTO.setDummyCheckValue(false);
					}
			}								
		}
		log.info("Leaving into resetStudentList of CreatePracticalBatchAction");
	}
	
	/**
	 * 
	 * @param practicalBatchForm
	 * @throws Exception
	 * * Used to reset the studentList in update mode
	 * Used after addition and after validation fails
	 */
	
	public void resetStudentListWhileUpdate(CreatePracticalBatchForm practicalBatchForm)throws Exception{
		log.info("Entering into resetStudentList of CreatePracticalBatchAction");
		List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
			while (iterator.hasNext()) {
				CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
					if(createPracticalBatchTO.isCheckValue()){
					createPracticalBatchTO.setCheckValue(false);
					createPracticalBatchTO.setTempCheckValue(false);
					}
					if(createPracticalBatchTO.isDummyCheckValue()){
						createPracticalBatchTO.setDummyCheckValue(false);
					}
			}								
		}
		log.info("Leaving into resetStudentList of CreatePracticalBatchAction");
	}

	/**
	 * 
	 * @param form
	 * @param classSchemewiseId
	 * @param subjectId
	 * Used in view Practical Batch
	 * Displays all the batches for the particular class and subject
	 * @throws Exception
	 */

	public void assignPracticalBatchAlltoForm(ActionForm form, int classSchemewiseId, int subjectId,int activityId)throws Exception {
		log.info("Inside assignPracticalBatchAlltoForm of CreatePracticalBatchAction");
		CreatePracticalBatchForm practicalBatchForm = (CreatePracticalBatchForm)form;
		List<CreatePracticalBatchTO>batchList = CreatePracticalBatchHandler.getInstance().getPracticalBatchDetailsBySubjectClass(classSchemewiseId, subjectId,activityId);
		practicalBatchForm.setAllBatchList(batchList);
		log.info("End of assignPracticalBatchAlltoForm of CreatePracticalBatchAction");
	}	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Works when user clicks delete practical batch link in the left menu of the application
	 * Returns all present batches for the class and subject
	 * @throws Exception
	 */

	public ActionForward deletePracticalBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into deletePracticalBatch of CreatePracticalBatchAction");
		CreatePracticalBatchForm practicalBatchForm = (CreatePracticalBatchForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if(practicalBatchForm.getExistingStudentList()!=null || practicalBatchForm.getMessage()!=null){
				practicalBatchForm.setExistingStudentList(null);
				practicalBatchForm.setMessage(null);
			}
			setUserId(request, practicalBatchForm);
			//Retrieve the TO object from session and get the classschemewiseId and subjectId
			HttpSession session = request.getSession(false);
			CreatePracticalBatchTO batchTO = (CreatePracticalBatchTO) session.getAttribute(BATCHTO);
			
			int classSchemewiseId = batchTO.getClassSchemewiseTO().getId();
			int subjectId = 0;
			if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
				 subjectId = batchTO.getSubjectTO().getId();
			}
			int activityId=0;
			if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
				activityId=batchTO.getActivityTO().getId();
			}
			
			int batchId = practicalBatchForm.getBatchId();
			String userId = practicalBatchForm.getUserId();
			boolean isDeleted;
			isDeleted = CreatePracticalBatchHandler.getInstance().deletePracticalBatch(batchId, userId);
			//If delete is success append the success message
			if(isDeleted){
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_DELETE_SUCCESS));
				//Get all the practical batches for the class and subject
				assignPracticalBatchAlltoForm(practicalBatchForm, classSchemewiseId, subjectId,activityId);
				resetStudentListWhileUpdate(practicalBatchForm);
				practicalBatchForm.setMessage(null);
				practicalBatchForm.setExistingStudentList(null);
				practicalBatchForm.clear();
				request.setAttribute(CMSConstants.OPERATION, SAVE);
			}
			//Else add the error message
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_DELETE_FAILED));
				//Get all the practical batches for the class and subject
				assignPracticalBatchAlltoForm(practicalBatchForm, classSchemewiseId, subjectId,activityId);
				request.setAttribute(CMSConstants.OPERATION, SEARCH);
			}
		} catch (Exception e) {
			log.error("Error occured in deletePracticalBatch of CreatePracticalBatchAction", e);
				String msg = super.handleApplicationException(e);
				practicalBatchForm.setErrorMessage(msg);
				practicalBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		saveMessages(request, messages);
		saveErrors(request, errors);
		log.info("Leaving into deletePracticalBatch of CreatePracticalBatchAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Method is used to reactivate the practical batch. Make status from inactive to active
	 * @throws Exception
	 */

	public ActionForward reActivatePracticalBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into reActivatePracticalBatch of CreatePracticalBatchAction");
		CreatePracticalBatchForm practicalBatchForm = (CreatePracticalBatchForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if(practicalBatchForm.getExistingStudentList()!=null || practicalBatchForm.getMessage()!=null){
				practicalBatchForm.setExistingStudentList(null);
				practicalBatchForm.setMessage(null);
			}
			setUserId(request, practicalBatchForm);
			boolean isReactivate;
			//Retrieve the TO object from session which contains the classSchemewiseId and subjectId
			HttpSession session = request.getSession(false);
			CreatePracticalBatchTO batchTO = (CreatePracticalBatchTO) session.getAttribute(BATCHTO);
			//Get the batchName, classSchemewiseId, subjectId & userId. and request handler for reactivate
			String batchName = practicalBatchForm.getOldBatchName();
			String userId = practicalBatchForm.getUserId();
			int classSchemewiseId = batchTO.getClassSchemewiseTO().getId();
			int subjectId = 0;
			if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
				 subjectId = batchTO.getSubjectTO().getId();
			}
			int activityId=0;
			if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
				activityId=batchTO.getActivityTO().getId();
			}
			
			Batch batch = CreatePracticalBatchHandler.getInstance().getBatchDetailsbyBatchName(practicalBatchForm.getOldBatchName().trim(),batchTO);
			
			//Get the already present students for the current classSchemewsieId and subjectId
			ICreatePracticalBatchTransaction transaction = new CreatePracticalBatchTransactionImpl();
			List<Batch> batchAssignedStudentList = new ArrayList<Batch>();
			List<Integer> assignedStudentList = new ArrayList<Integer>();
		
			if(transaction != null){
			batchAssignedStudentList = transaction.getStudentsBySubjectAndClassSchemewise(subjectId, classSchemewiseId,activityId);
			}
			if(batchAssignedStudentList!=null && !batchAssignedStudentList.isEmpty()){
				assignedStudentList = CreatePracticalBatchHelper.getInstance().getBatchAssignedStudentList(batchAssignedStudentList);
			}
			if(checkAssignedStudents(batch,assignedStudentList)){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_STUDENTEXISTS));
			saveErrors(request, errors);
			request.setAttribute(CMSConstants.OPERATION, SAVE);
			practicalBatchForm.setBatchName(null);
			resetStudentList(practicalBatchForm);
			//Get all the practical batches for the class and subject
			assignPracticalBatchAlltoForm(practicalBatchForm, classSchemewiseId, subjectId,activityId);
			return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
			}	
			//Call the handler method to reactivate
			isReactivate = CreatePracticalBatchHandler.getInstance().reActivatePracticalBatch(batchName,userId,classSchemewiseId,subjectId,activityId);
			//Get all the practical batches for the class and subject
			
			assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(), subjectId,activityId);
			//If reactivation is success the add the success ,message
			if(isReactivate){
				practicalBatchForm.setMessage(null);
				practicalBatchForm.setExistingStudentList(null);
				resetStudentList(practicalBatchForm);
				request.setAttribute(CMSConstants.OPERATION, SAVE);
				practicalBatchForm.clear();
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE_SUCCESS, batchName));
			}
			//Else add the failure message
			else{
				practicalBatchForm.setMessage(null);
				practicalBatchForm.setExistingStudentList(null);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE_FAILED));
				request.setAttribute(CMSConstants.OPERATION, SEARCH);
			}
		} catch (Exception e) {
			log.error("Error occured in reActivatePracticalBatch of CreatePracticalBatchAction", e);
				String msg = super.handleApplicationException(e);
				practicalBatchForm.setErrorMessage(msg);
				practicalBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		saveMessages(request, messages);
		saveErrors(request, errors);
		log.info("Leaving into reActivatePracticalBatch of CreatePracticalBatchAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
	}
	/**
	 * 
	 * @param mapping Used while edit button is clicked
	 * @param form
	 * @param request Takes the batch Id as input
	 * @param response
	 * @return all the available datas based on the Id
	 * @throws Exception
	 */
	public ActionForward editPracticalBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { 
		log.info("Entering into editPracticalBatch of CreatePracticalBatchAction");
		CreatePracticalBatchForm practicalBatchForm = (CreatePracticalBatchForm)form;
		try {
			if(practicalBatchForm.getExistingStudentList()!=null || practicalBatchForm.getMessage()!=null){
				practicalBatchForm.setExistingStudentList(null);
				practicalBatchForm.setMessage(null);
			}
			//Retrieve the TO object from session which contains the classSchemewiseId and subjectId
			HttpSession session = request.getSession(false);
			CreatePracticalBatchTO batchTO = (CreatePracticalBatchTO) session.getAttribute(BATCHTO);
			//Set the present data to formbean by calling the handler
			CreatePracticalBatchHandler.getInstance().getBatchDetailsById(practicalBatchForm, batchTO);
			//Get all the practical batches for the class and subject
			int subjectId=0;
			if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
				subjectId=batchTO.getSubjectTO().getId();
			}
			int activityId=0;
			if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
				activityId=batchTO.getActivityTO().getId();
			}
			assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(), subjectId,activityId);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error occured in editPracticalBatch of CreatePracticalBatchAction", e);
				String msg = super.handleApplicationException(e);
				practicalBatchForm.setErrorMessage(msg);
				practicalBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("Leaving into editPracticalBatch of CreatePracticalBatchAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
	}
	
	/**
	 * 
	 * @param mapping Used while update button is clicked
	 * @param form
	 * @param request 
	 * @param response Updates the batch
	 * @return all the 
	 * @throws Exception
	 */
	
	public ActionForward updatePracticalBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into updatePracticalBatch of CreatePracticalBatchAction");
		CreatePracticalBatchForm practicalBatchForm = (CreatePracticalBatchForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if(practicalBatchForm.getExistingStudentList()!=null || practicalBatchForm.getMessage()!=null){
				practicalBatchForm.setExistingStudentList(null);
				practicalBatchForm.setMessage(null);
			}
			if(isCancelled(request)){
				//Retrieve the TO object from session which contains the classSchemewiseId and subjectId
				HttpSession session = request.getSession(false);
				CreatePracticalBatchTO batchTO = (CreatePracticalBatchTO) session.getAttribute(BATCHTO);
				//Set the present data to formbean by calling the handler
				CreatePracticalBatchHandler.getInstance().getBatchDetailsById(practicalBatchForm, batchTO);
				//Get all the practical batches for the class and subject
				int subjectId=0;
				if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
					subjectId=batchTO.getSubjectTO().getId();
				}
				int activityId=0;
				if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
					activityId=batchTO.getActivityTO().getId();
				}
				assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(), subjectId,activityId);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				//Get all the practical batches for the class and subject
				int subjectId1=0;
				if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
					subjectId=batchTO.getSubjectTO().getId();
				}
				int activityId1=0;
				if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
					activityId=batchTO.getActivityTO().getId();
				}
				assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(), subjectId1,activityId1);
				return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
			}
			setUserId(request, practicalBatchForm);
			boolean isUpdated = false;
			
			//Retrieve the TO object from session which contains the classSchemewiseId and subjectId
			HttpSession session = request.getSession(false);
			CreatePracticalBatchTO batchTO = (CreatePracticalBatchTO) session.getAttribute(BATCHTO);
			
			//Get the subjectId and classSchemewiseId from the BatchTO
			int subjectId = 0;
			if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
				 subjectId = batchTO.getSubjectTO().getId();
			}
			int activityId=0;
			if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
				activityId=batchTO.getActivityTO().getId();
			}
			int classSchemewiseId = batchTO.getClassSchemewiseTO().getId();
			//Condition is used to validate if no student selected while updation
			boolean noStudentSelected = validateStudentList(practicalBatchForm.getStudentList());
			if(!noStudentSelected){
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_SELECTSTUDENT));
				resetStudentList(practicalBatchForm);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
			}
			if(!practicalBatchForm.getBatchName().trim().isEmpty()){
				boolean invalidBatchName = validateBatchName(practicalBatchForm.getBatchName().trim());
					if(invalidBatchName){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCHNAME_INVALID));
					saveErrors(request, errors);
					//Below condition works when user is not checked any student and saves. After validation it remains in the previous state
					List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
					if(studentList!=null && !studentList.isEmpty()){
						Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
						while (iterator.hasNext()) {
							CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
							if(createPracticalBatchTO.isCheckValue()){
							createPracticalBatchTO.setDummyCheckValue(true);
							createPracticalBatchTO.setCheckValue(false);
							}
							else{
								createPracticalBatchTO.setDummyCheckValue(false);
								createPracticalBatchTO.setCheckValue(false);
							}
						}
					//Get all the practical batches for the class and subject
					assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(),subjectId,activityId);
					request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);					
					}
			}			
			//Check for duplicate
			else if(!practicalBatchForm.getBatchName().equalsIgnoreCase(practicalBatchForm.getOldBatchName())){
				
				//Check for the duplicate with batchName, classId and subjectId . If exists append the error message
					Batch batch = CreatePracticalBatchHandler.getInstance().getBatchDetailsbyBatchName(practicalBatchForm.getBatchName().trim(),batchTO);
					if(batch!=null && batch.getIsActive()){
						List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
						if(studentList!=null && !studentList.isEmpty()){
							Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
							while (iterator.hasNext()) {
								CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
								if(createPracticalBatchTO.isCheckValue()){
								createPracticalBatchTO.setDummyCheckValue(true);
								createPracticalBatchTO.setCheckValue(false);
								createPracticalBatchTO.setTempCheckValue(true);
								}
								else{
									createPracticalBatchTO.setDummyCheckValue(false);
									createPracticalBatchTO.setCheckValue(false);
									createPracticalBatchTO.setTempCheckValue(false);
								}
							}
						}
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_EXIST));
						saveErrors(request, errors);
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);	
						return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
					}	
					//Works on reactivation
					//If batch in already exists but in inactive mode
					if(batch!=null && !batch.getIsActive()){								
						List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
						if(studentList!=null && !studentList.isEmpty()){
							Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
							while (iterator.hasNext()) {
								CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
								if(createPracticalBatchTO.isCheckValue()){
								createPracticalBatchTO.setDummyCheckValue(true);
								createPracticalBatchTO.setCheckValue(false);
								createPracticalBatchTO.setTempCheckValue(true);
								}
								else{
									createPracticalBatchTO.setDummyCheckValue(false);
									createPracticalBatchTO.setCheckValue(false);
									createPracticalBatchTO.setTempCheckValue(false);
								}
							}
						}//Store the old batch name in form. WIll be used while reactivation
						practicalBatchForm.setOldBatchName(practicalBatchForm.getBatchName());
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE));
						saveErrors(request, errors);
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);	
						return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
					}
				
			//Request the handler to update the batch
			isUpdated = CreatePracticalBatchHandler.getInstance().updatePracticalBatch(practicalBatchForm);
			//If update is success append the message and again return back to search mode. 
			if (isUpdated) {
				practicalBatchForm.setBatchName(null);
				//Get all the practical batches for the class and subject
				assignPracticalBatchAlltoForm(practicalBatchForm, classSchemewiseId, subjectId,activityId);
				request.setAttribute(CMSConstants.OPERATION, SAVE);
				resetStudentListWhileUpdate(practicalBatchForm);
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_UPDATE_SUCCESS));
			}
			//Else append the failure message
			else{
				//Get all the practical batches for the class and subject
				assignPracticalBatchAlltoForm(practicalBatchForm, classSchemewiseId, subjectId,activityId);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_UPDATE_FAILED));
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			}
			}
			else{
				//Request the handler to update the batch
				isUpdated = CreatePracticalBatchHandler.getInstance().updatePracticalBatch(practicalBatchForm);
				//If update is success append the message and again return back to search mode. 
				if (isUpdated) {
					practicalBatchForm.setBatchName(null);
					//Get all the practical batches for the class and subject
					assignPracticalBatchAlltoForm(practicalBatchForm, classSchemewiseId, subjectId,activityId);
					request.setAttribute(CMSConstants.OPERATION, SAVE);
					resetStudentListWhileUpdate(practicalBatchForm);
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_UPDATE_SUCCESS));
				}
				//Else append the failure message
				else{
					//Get all the practical batches for the class and subject
					assignPracticalBatchAlltoForm(practicalBatchForm, classSchemewiseId, subjectId,activityId);
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_UPDATE_FAILED));
					request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				}
			}
			}
			//Below condition works when batch name is empty. Append the error message
			else{
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_EMPTY));
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			if(practicalBatchForm.getStudentList()!=null){
				Iterator<CreatePracticalBatchTO> iterator = practicalBatchForm.getStudentList().iterator();
				//Below condition keeps the student list in previous state
				while (iterator.hasNext()) {
					CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
					if(createPracticalBatchTO.isCheckValue() && createPracticalBatchTO.isDummyCheckValue()){
					createPracticalBatchTO.setDummyCheckValue(true);
					createPracticalBatchTO.setCheckValue(false);
					}
					else if(createPracticalBatchTO.isCheckValue() && !createPracticalBatchTO.isDummyCheckValue()){
						createPracticalBatchTO.setDummyCheckValue(true);
						createPracticalBatchTO.setCheckValue(false);
					}
					else if(!createPracticalBatchTO.isCheckValue() && createPracticalBatchTO.isDummyCheckValue()){
						createPracticalBatchTO.setDummyCheckValue(false);
						createPracticalBatchTO.setCheckValue(false);
					}
				}
			}
			//Get all the practical batches for the class and subject
			
			assignPracticalBatchAlltoForm(practicalBatchForm, batchTO.getClassSchemewiseTO().getId(),subjectId,activityId);
			}
		} catch (Exception e) {
			if(e instanceof BusinessException){
				//Below condition works when user is not checked any student and saves. After validation it remains in the previous state
				List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
				if(studentList!=null && !studentList.isEmpty()){
					Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
					//Below condition keeps the student list in previous state
					while (iterator.hasNext()) {
						CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
						if(createPracticalBatchTO.isCheckValue()){
						createPracticalBatchTO.setDummyCheckValue(true);
						createPracticalBatchTO.setCheckValue(false);
						}
						else if(!createPracticalBatchTO.isCheckValue()){
						createPracticalBatchTO.setDummyCheckValue(false);
						}
					}
				}
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
			}
			log.error("Error occured in updatePracticalBatch of CreatePracticalBatchAction", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				practicalBatchForm.setErrorMessage(msg);
				practicalBatchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		}
		saveMessages(request, messages);
		saveErrors(request, errors);
		log.info("Leaving into updatePracticalBatch of CreatePracticalBatchAction");
		return mapping.findForward(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_CREATE);
	}
	
	/**
	 * 
	 * @param studentList
	 * Used to validate student List
	 * If none of the students selected
	 * @returns false
	 * @throws Exception
	 */
	public boolean validateStudentList(List<CreatePracticalBatchTO> studentList)throws Exception{
		log.info("Entering into validateStudentList of CreatePracticalBatchAction");
		boolean isSelected = false;
		if(studentList!=null && !studentList.isEmpty()){			
			Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
			while (iterator.hasNext()) {
				CreatePracticalBatchTO createPracticalBatchTO = iterator.next();
					if(createPracticalBatchTO.isCheckValue()){
						isSelected = true;
						break;
					}					
			}								
		}
		log.info("Leaving into validateStudentList of CreatePracticalBatchAction");
		return isSelected;
	}
	
	/**
	 * Used to check the duplicate for students while reactivate batches
	 * 
	 */
	
	public boolean checkAssignedStudents(Batch batch, List<Integer>assignedStudentList)throws Exception{
		log.info("Entering into checkAssignedStudents of CreatePracticalBatchAction");
		BatchStudent batchStudent;
		if(batch!=null){
			Set<BatchStudent> batchStudentSet = batch.getBatchStudents();
			if(batchStudentSet!=null && !batchStudentSet.isEmpty()){
				Iterator<BatchStudent> iterator = batchStudentSet.iterator();
				while (iterator.hasNext()) {
					batchStudent = iterator.next();
					if(batchStudent.getStudent()!=null){
					if(assignedStudentList!=null && assignedStudentList.contains(Integer.valueOf(batchStudent.getStudent().getId()))){
						return true;
					}
					}	
				}
			}
			
		}
		log.info("Leaving into checkAssignedStudents of CreatePracticalBatchAction");
		return false;
	}	
}
