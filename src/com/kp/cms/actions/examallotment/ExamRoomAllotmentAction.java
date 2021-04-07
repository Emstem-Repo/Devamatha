package com.kp.cms.actions.examallotment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.AdmissionFormAction;
import com.kp.cms.actions.ajax.CommonAjaxAction;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.exam.AdminHallTicketForm;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.AdminHallTicketHandler;
import com.kp.cms.handlers.exam.DownloadHallTicketHandler;
import com.kp.cms.handlers.exam.ExamBlockUnblockHallTicketHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentHandler;
import com.kp.cms.handlers.examallotment.ExamRoomAvailabilityHandler;
import com.kp.cms.handlers.examallotment.RoomAllotmentStatusHandler;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.to.exam.ShowMarksCardTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentTO;
import com.kp.cms.transactions.examallotment.IExamRoomAllotment;
import com.kp.cms.transactionsimpl.examallotment.ExamRoomAllotmentImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

public class ExamRoomAllotmentAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentAction.class);
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initRoomAllotment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExamRoomAllotmentForm allotmentForm = (ExamRoomAllotmentForm) form;
		allotmentForm.resetFields();
		try{
			Map<Integer,String> workLocationMap = ExamRoomAvailabilityHandler.getInstance().getWorkLocation();
			allotmentForm.setWorkLocationMap(workLocationMap);
			Map<Integer,String> cycleMap = ExamRoomAllotmentHandler.getInstance().getCycleMap();
			allotmentForm.setCycleMap(cycleMap);
			setRequiredDatatoForm(allotmentForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			allotmentForm.setErrorMessage(msg);
			allotmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT);
	}
	/**
	 * @param allotmentForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(ExamRoomAllotmentForm allotmentForm) throws Exception{
		//to get Exam List
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if(allotmentForm.getYear()!=null && !allotmentForm.getYear().isEmpty()){
			currentYear=Integer.parseInt(allotmentForm.getYear());
		}else{
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			 if(year!=0){
				currentYear=year;
				
			}
			 allotmentForm.setYear(Integer.toString(currentYear));
		}
		Map<Integer, String> examNameMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType("Both",allotmentForm.getYear());
		
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		allotmentForm.setExamNameList(examNameMap);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetailsForAllotment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExamRoomAllotmentForm allotmentForm = (ExamRoomAllotmentForm) form;
		ActionErrors errors = new ActionErrors();
		try{
			if(allotmentForm.getCycleId() == null || allotmentForm.getCycleId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.room.allotment.mandatory"));
			}
			if((allotmentForm.getAllotmentType() == null || allotmentForm.getAllotmentType().length == 0) && errors.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.room.allotment.mandatory"));
			}
			if((allotmentForm.getCampus() == null || allotmentForm.getCampus().isEmpty()) && errors.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.room.allotment.mandatory"));
			}
			if((allotmentForm.getExamId() == null || allotmentForm.getExamId().isEmpty()) && errors.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.room.allotment.mandatory"));
			}
			if(errors.isEmpty()){
				if(allotmentForm.getAllotmentType() != null && allotmentForm.getAllotmentType().length != 0){
					for (String type : allotmentForm.getAllotmentType()) {
						List<ExamRoomAllotmentTO> allotmentList = new ArrayList<ExamRoomAllotmentTO>();
						if(type.equalsIgnoreCase("Pool")){
							allotmentList = ExamRoomAllotmentHandler.getInstance().getCourcesForAllotment(allotmentForm);
						}
						if(type.equalsIgnoreCase("Group")){
							List<ExamRoomAllotmentTO> allotmentListGroup = ExamRoomAllotmentHandler.getInstance().getCourcesForAllotmentGroupWise(allotmentForm);
							allotmentList.addAll(allotmentListGroup);
						}
						if(type.equalsIgnoreCase("Specialization")){
							List<ExamRoomAllotmentTO> allotmentListForSpecialization =ExamRoomAllotmentHandler.getInstance().getCoursesForAllotmentSpecialization(allotmentForm);
							allotmentList.addAll(allotmentListForSpecialization);
						}
						allotmentForm.setAllotmentList(allotmentList);
					}
				}
				setRequiredDatatoForm(allotmentForm);
			}else{
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			allotmentForm.setErrorMessage(msg);
			allotmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward roomAllotment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExamRoomAllotmentForm allotmentForm = (ExamRoomAllotmentForm) form;
		ActionMessages messages = new ActionMessages();
		try{
			setUserId(request, allotmentForm);
			if(allotmentForm.getAllotmentType() != null && allotmentForm.getAllotmentType().length != 0){
				boolean multipleAllotment = false;
				for (String type : allotmentForm.getAllotmentType()) {
					if(type.equalsIgnoreCase("Pool")){
						ExamRoomAllotmentHandler.getInstance().allotRoomsForStudent(allotmentForm);
						multipleAllotment = true;
					}
					if(type.equalsIgnoreCase("Group")){
						ExamRoomAllotmentHandler.getInstance().allotRoomsForStudentFromGroup(allotmentForm,multipleAllotment);
						multipleAllotment = true;
					}
					if(type.equalsIgnoreCase("Specialization")){
						ExamRoomAllotmentHandler.getInstance().allotRoomsForStudentFromSpecialization(allotmentForm,multipleAllotment);
						multipleAllotment = true;
					}
				}
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.room.allotment.success"));
				saveMessages(request, messages);
				allotmentForm.resetFields();
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			allotmentForm.setErrorMessage(msg);
			allotmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initRoomAllotmentForStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentForm allotmentForm = (ExamRoomAllotmentForm) form;
		allotmentForm.resetFields();
		setUserId(request, allotmentForm);
		try{
			Map<Integer,String> workLocationMap = ExamRoomAvailabilityHandler.getInstance().getWorkLocation();
			allotmentForm.setWorkLocationMap(workLocationMap);
			setRequiredDatatoForm(allotmentForm);
			/*Map<Integer, String> classMap = CommonAjaxHandler.getInstance() .getClassesByYearForMuliSelect( Integer.parseInt(allotmentForm.getYear()));
			allotmentForm.setClassesMap(classMap);*/
			Map<Integer,String> sessionMap = ExamRoomAllotmentHandler.getInstance().getSessionMap();
			allotmentForm.setSessionMap(sessionMap);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			allotmentForm.setErrorMessage(msg);
			allotmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT_STUDENTS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward roomAllotmentNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamRoomAllotmentForm allotmentForm = (ExamRoomAllotmentForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=allotmentForm.validate(mapping, request);
		allotmentForm.setErrorMessage(null);
		try{
			if(errors.isEmpty()){
				String[] clsIds = allotmentForm.getSelectedClasses();
				List<Integer> classList =new ArrayList<Integer>();
				for(int i =0;clsIds.length>i;i++){
					classList.add(Integer.parseInt(clsIds[i]));
				}
				if(allotmentForm.getTotalStudentsCount() == 0){
					errors.add("error", new ActionError("knowledgepro.exam.room.allotment.no.student.selected.classes"));
				}else if(allotmentForm.getTotalRoomCount() == 0){
					errors.add("error", new ActionError("knowledgepro.exam.room.allotment.student.count.greaterthan.room.capacity"));
				}else if(allotmentForm.getTotalStudentsCount()>allotmentForm.getTotalRoomCount()){
					errors.add("error", new ActionError("knowledgepro.exam.room.allotment.student.count.greaterthan.room.capacity"));
				}else {
					// checking if for the exam, date, and session, any of the selected class is already allotted.
					boolean isDuplicate = ExamRoomAllotmentHandler.getInstance().checkDuplicateAllotment(allotmentForm,classList);
					if(isDuplicate){
						String errorMessage= allotmentForm.getErrorMessage();
						errors.add("error", new ActionError("knowledgepro.exam.room.allotment.classes.duplicate",errorMessage));
					}else {
						// checking whether Time Table is Defined  for the selected date and session for all the classes.
						boolean isTimeTableDefine = ExamRoomAllotmentHandler.getInstance().isTimeTableDefine(allotmentForm,classList);
						if(isTimeTableDefine){ // false
							String errorMessage= allotmentForm.getErrorMessage();
							errors.add("error", new ActionError("knowledgepro.exam.room.allotment.timetable.notdefined",errorMessage));
						}
					}
				}
				if(errors.isEmpty()){
					// All the rooms selected ,check in common allotment those rooms which are the clasees. 
					//Then for those classes time table is there for the same date and session, then do not allow.
					boolean flag = ExamRoomAllotmentHandler.getInstance().checkRoomIsAlreadyAlloted(allotmentForm);
					if(flag){
						String error = allotmentForm.getErrorMessage();
						errors.add("error", new ActionError("knowledgepro.exam.room.allotment.room.already.alloted",error));
					}
				}
				if(errors.isEmpty()){
					ExamRoomAllotmentHandler.getInstance().roomAllotmentBasedOnClasses(allotmentForm,classList);
					// if the students are remaining who are not alloted  ,then rollback the process and displaying the error msg.
					if(allotmentForm.getRemainingStudentsCount()>0){
						setFieldsToForm(allotmentForm);
						errors.add("error", new ActionError("knowledgepro.exam.room.allotment.notdone.students",allotmentForm.getRemainingStudentsCount()));
						saveErrors(request, errors);
					}else{
						// after completing the allotment successfully displaying the success msg.
						messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.room.allotment.success"));
						saveMessages(request, messages);
					}
					allotmentForm.resetFields();
				}else {
					setFieldsToForm(allotmentForm);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT_STUDENTS);
				}
			}else{
				setFieldsToForm(allotmentForm);
				saveErrors(request, errors);
				}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			allotmentForm.setErrorMessage(msg);
			allotmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT_STUDENTS);
	}
	/**
	 * @param allotmentForm
	 * @throws Exception
	 */
	private void setFieldsToForm(ExamRoomAllotmentForm allotmentForm)throws Exception {
		String examName = allotmentForm.getExamId();
		String examType = "0";
		String programId ="0";
		String deanaryName = "0";
		Map<Integer,String> mainClassesMap = ExamPublishHallTicketHandler.getInstance().getclassesMap(examName, examType, programId, deanaryName);
		Map<Integer, String> roomMasterMap=RoomAllotmentStatusHandler.getInstance().getRoomNoByWorkLocationId(Integer.parseInt(allotmentForm.getCampus()));
		allotmentForm.setCollectionMap(roomMasterMap);
		Map<Integer,String> selectedClassMap = new HashMap<Integer, String>();
		for (String classes : allotmentForm.getSelectedClasses()) {
			if(mainClassesMap.containsKey(Integer.parseInt(classes))){
				String className = mainClassesMap.get(Integer.parseInt(classes));
				mainClassesMap.remove(Integer.parseInt(classes));
				selectedClassMap.put(Integer.parseInt(classes), className);
			}
		}
		if(mainClassesMap!=null && !mainClassesMap.isEmpty()){
			allotmentForm.setClassesMap(mainClassesMap);
		}
		if(selectedClassMap!=null && !selectedClassMap.isEmpty()){
			allotmentForm.setSelectedClassesMap(selectedClassMap);
		}
		Map<Integer,String> selectedRoomMap = new HashMap<Integer, String>();
		for (String rooms : allotmentForm.getSelectedRooms()) {
			if(roomMasterMap.containsKey(Integer.parseInt(rooms))){
				String roomName = roomMasterMap.get(Integer.parseInt(rooms));
				roomMasterMap.remove(Integer.parseInt(rooms));
				selectedRoomMap.put(Integer.parseInt(rooms),roomName);
			}
		}
		if(roomMasterMap!=null && !roomMasterMap.isEmpty()){
			allotmentForm.setCollectionMap(roomMasterMap);
		}
		if(selectedRoomMap!=null && !selectedRoomMap.isEmpty()){
			allotmentForm.setSelectedRoomsMap(selectedRoomMap);
		}
		allotmentForm.setClasses(null);
		allotmentForm.setSelectedClasses(null);
		allotmentForm.setRooms(null);
		allotmentForm.setSelectedRooms(null);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTotalStudentsForSelectedClasses(ActionMapping mapping, ActionForm form,
 HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRoomAllotmentForm allotmentForm = (ExamRoomAllotmentForm) form;
		HttpSession session = request.getSession();
		try {
			Map<String, String> totalStudents = new HashMap<String, String>();
			totalStudents.put(String.valueOf(0), String.valueOf(0));
			session.setAttribute("optionMap", totalStudents);
			String classesId = allotmentForm.getPropertyName();
			if (classesId != null && !classesId.isEmpty()) {
				String[] clsIds = new String[] {};
				clsIds = classesId.split(",");
				List<Integer> classList = new ArrayList<Integer>();
				for (int i = 0; clsIds.length > i; i++) {
					classList.add(Integer.parseInt(clsIds[i]));
				}
				String academicYear = allotmentForm.getAcademicYear();
				int totalNOofStudent = ExamRoomAllotmentHandler.getInstance()
						.getTotalNOofStudentsForSelectedClasses(classList,
								Integer.parseInt(academicYear));
				if (totalNOofStudent > 0) {
					totalStudents.put(String.valueOf(totalNOofStudent), String
							.valueOf(totalNOofStudent));
					session.setAttribute("optionMap", totalStudents);
				}
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			allotmentForm.setErrorMessage(msg);
			allotmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTotalRoomCapacity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
 throws Exception {
		ExamRoomAllotmentForm allotmentForm = (ExamRoomAllotmentForm) form;
		HttpSession session = request.getSession();
		try {
			Map<String, String> totalRoomCapcity = new HashMap<String, String>();
			totalRoomCapcity.put(String.valueOf(0), String.valueOf(0));
			session.setAttribute("optionMap", totalRoomCapcity);
			String roomIds = allotmentForm.getPropertyName();
			if (roomIds != null && !roomIds.isEmpty()) {
				int totalRoomsCapacity = ExamRoomAllotmentHandler.getInstance()
						.getTotalRoomsCapacity(allotmentForm, roomIds);
				totalRoomCapcity = new HashMap<String, String>();
				if (totalRoomsCapacity > 0) {
					totalRoomCapcity.put(String.valueOf(totalRoomsCapacity),
							String.valueOf(totalRoomsCapacity));
					session.setAttribute("optionMap", totalRoomCapcity);
				}
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			allotmentForm.setErrorMessage(msg);
			allotmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
}
