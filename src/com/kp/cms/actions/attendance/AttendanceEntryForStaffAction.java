package com.kp.cms.actions.attendance;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceEntryHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactions.attandance.ITeacherClassEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.TeacherClassEntryImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * Action class for Attendance related activities
 * this method will be used in the taking,modifying,canceling of attendance. 
 *
 */
@SuppressWarnings("deprecation")
public class AttendanceEntryForStaffAction extends BaseDispatchAction {
	
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final Log log = LogFactory.getLog(AttendanceEntryForStaffAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This method will be invoke when the particular link clicked.
	 * 
	 */
	public ActionForward initAttendanceEntry(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntry");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, attendanceEntryForm);
			attendanceEntryForm.clearAll();
			// setting the necessary data to form on loading
			initsetDataToForm(attendanceEntryForm);
			request.setAttribute("attendanceEntryOperation", "load");
			Properties prop = new Properties();
			InputStream in = AttendanceEntryForStaffAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	        prop.load(in);
	        String path=prop.getProperty("knowledgepro.attendance.sms.required");
	        if(path!=null && path.equals("true")){
	        	attendanceEntryForm.setDisplayRequired("true");
	        }else{
	        	attendanceEntryForm.setDisplayRequired("false");
	        }
	        IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
			Date attDate=transaction.getLastEnteredAttendanceDate();
			// To Display the message for which date attendance has been entered
			if(attDate!=null){
				ActionMessage message = new ActionMessage("knowledgepro.admin.attendance.lastEntered", CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(attDate), AttendanceEntryForStaffAction.SQL_DATEFORMAT,AttendanceEntryForStaffAction.FROM_DATEFORMAT));
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		} catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initAttendanceEntry");
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_FIRST);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This will be called when second page of attendance will be needed.
	 * this method will redirect to different pages based on the requirement.
	 */
	public ActionForward initAttendanceEntrySecondPage(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntrySecondPage");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionMessages errors=attendanceEntryForm.validate(mapping, request);
		HttpSession session=request.getSession();
		try {
			if(errors.isEmpty()) {
				// These below errors are kind of warning messages
				if(attendanceEntryForm.getAttendancedate()!=null && !attendanceEntryForm.getAttendancedate().isEmpty())
				{
					java.util.Date startDate = new java.util.Date();
					java.util.Date endDate = CommonUtil.ConvertStringToDate(attendanceEntryForm.getAttendancedate());

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(startDate);
					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(endDate);
					if((CommonUtil.getDaysBetweenDates(cal2, cal1)-1)>2)
					{
						errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED4));
					}
				}
				if((attendanceEntryForm.getClasses() ==null || attendanceEntryForm.getClasses().length ==0) && (attendanceEntryForm.getPeriods() ==null || attendanceEntryForm.getPeriods().length ==0)) {
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED1));
				} else if(attendanceEntryForm.getClasses() ==null || attendanceEntryForm.getClasses().length ==0 ) {
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED2));
				} else if(attendanceEntryForm.getPeriods() ==null || attendanceEntryForm.getPeriods().length ==0) {
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED3));
				}
//				Properties prop = new Properties();
//				InputStream in = AttendanceEntryAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
//		        prop.load(in);
//		        String path=prop.getProperty("knowledgepro.attendance.slip.no.enable");
//		        if(path!=null && path.equals("true")){
//		        	if(attendanceEntryForm.getSlipNo()==null || attendanceEntryForm.getSlipNo().isEmpty()){
//		        		errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendanceentry.slipNo.required"));
//		        	}
//		        }
//		        if(attendanceEntryForm.getYear()!=null && !attendanceEntryForm.getYear().isEmpty()){
//		        	AttendanceSlipNumber  attendanceSlipNumberBo = AttendanceEntryHandler.getInstance().checkDuplicateSlipNo(attendanceEntryForm);
//		        	if(attendanceSlipNumberBo == null ) {
//		        		errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_SLIPNUMBER_CONFIGURE));
//		        	}
//		        }
		        if(attendanceEntryForm.getClasses()!=null && attendanceEntryForm.getAttendancedate()!=null){
		        boolean isMinDate=AttendanceEntryHandler.getInstance().checkAttendanceDateMinRange(attendanceEntryForm.getClasses(),attendanceEntryForm.getAttendancedate());
		        if(isMinDate){
		        	errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendance.olddate.range"));
		        }
		        }
		        if(errors.isEmpty()){
//		        	validateClassesAndPeriod(attendanceEntryForm,errors);
		        	validatePeriodsForClasses(attendanceEntryForm, errors);
		        }
				if(!errors.isEmpty()) {
					setDataToFormErrorMode(attendanceEntryForm,session);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_FIRST);
				}
				
				AttendanceEntryHandler attendanceEntryHandler = AttendanceEntryHandler.getInstance();
				attendanceEntryHandler.duplicateCheck(attendanceEntryForm,request);
				boolean isSecondRequired = attendanceEntryHandler.isSecondPageRequired(attendanceEntryForm);
				attendanceEntryForm.setSecondPage(isSecondRequired);
				//if isSecondRequired is true then show second page else skip.
				if(isSecondRequired) {
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_SECOND);
				} else {
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_INITTHIRD);
				}	
				
			} else {
				setDataToFormErrorMode(attendanceEntryForm,session);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_FIRST);
			}
			
		} catch(DuplicateException e) {
			// will be thrown when  duplicate occurs.
			if(attendanceEntryForm.getBatchId()!= null && attendanceEntryForm.getBatchId().length() != 0){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_EXIST_BATCH));
			} else {	
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_EXIST));
			}	
			setDataToFormErrorMode(attendanceEntryForm,session);
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_FIRST);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
			throw e;
		}
	}
	
	private void validateClassesAndPeriod(AttendanceEntryForm attendanceEntryForm, ActionMessages errors)  throws Exception{
		if((attendanceEntryForm.getClasses() !=null && attendanceEntryForm.getClasses().length !=0) && (attendanceEntryForm.getPeriods() !=null && attendanceEntryForm.getPeriods().length !=0)) {
			IAttendanceEntryTransaction transaction=AttendanceEntryTransactionImpl.getInstance();
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			String[] selectedClasses=attendanceEntryForm.getClasses();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			
			Map<Integer,Integer> finalMap=new HashMap<Integer, Integer>();
			
			Map<Integer,Integer> periodMap=transaction.getperiodsForInputClasses(classesIdsSet);
			String[] selectedPeriods=attendanceEntryForm.getPeriods();
			for (int i = 0; i < selectedPeriods.length; i++) {
				if(periodMap.containsKey(Integer.parseInt(selectedPeriods[i]))){
					if(finalMap.containsKey(periodMap.get(Integer.parseInt(selectedPeriods[i])))){
						int j=finalMap.remove(periodMap.get(Integer.parseInt(selectedPeriods[i])));
						finalMap.put(periodMap.get(Integer.parseInt(selectedPeriods[i])), j+1);
					}else{
						finalMap.put(periodMap.get(Integer.parseInt(selectedPeriods[i])), 1);
					}
				}
			}
			
			if(finalMap.size()!=selectedClasses.length){
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.attendance.not.valid.period"));
			}else{
				ArrayList<Integer> ar=new ArrayList<Integer>();
				ar.addAll(finalMap.values());
				int i=ar.get(0);
				Iterator<Integer> itr=ar.iterator();
				while (itr.hasNext()) {
					Integer integer = (Integer) itr.next();
					if(integer!=i){
						errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.attendance.not.valid.period"));
						break;
					}
				}
			}
			
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This page will just loads the 2nd page of attendance.
	 */
	public ActionForward loadAttendanceEntrySecondPage(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_SECOND);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		  This will redirect the control to 1st page of attendance.
	 */
	public ActionForward loadAttendanceEntryFirstPage(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		HttpSession session=request.getSession();
		setDataToFormErrorMode(attendanceEntryForm,session);
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_FIRST);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		  This method will load the third page of attendance with students list.
	 */
	public ActionForward initAttendanceEntryThirdPage(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntryThirdPage");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
        HttpSession session=request.getSession();
		try {
			AttendanceEntryHandler.getInstance().getStudents(attendanceEntryForm,session);
			if (attendanceEntryForm.getStudentList() != null
					&& attendanceEntryForm.getStudentList().isEmpty()) {
				message = new ActionMessage(
						CMSConstants.ATTENDANCE_ENTRY_NORECORD);
				messages.add(CMSConstants.MESSAGES, message);
				addMessages(request, messages);
				setDataToFormErrorMode(attendanceEntryForm,session);
				return	mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_FIRST);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			throw e;
			
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_THIRD);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward.
	 * @throws Exception
	 *          This method will save the attendance to database.
	 */
	public ActionForward saveAttendance(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of saveAttendance");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		HttpSession session=request.getSession();
		try {
			boolean flag=false;
			flag=AttendanceEntryHandler.getInstance().saveAttendance(attendanceEntryForm,session);
			if(flag){
				if(attendanceEntryForm.getIsSMSRequired().equals("Yes")){
				AttendanceEntryHelper.getInstance().sendSMS(attendanceEntryForm);
				}
				messages.add("messages",new ActionMessage(CMSConstants.ATTENDANCE_ADDED_SUCCESSFULLY,attendanceEntryForm.getSlipNo()));
				saveMessages(request, messages);
			}else{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ADDING_FAILED));
	    		saveErrors(request,errors);
			}
			attendanceEntryForm.clearAll();
			initsetDataToForm(attendanceEntryForm);
			saveMessages(request, messages);
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ADDING_FAILED));
    		saveErrors(request,errors);
		}
		log.info("Leaving into saveAttendance");
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_FIRST);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		   This method will be invoked when cancel attendance is invoked.
	 */
	public ActionForward initCancelAttendanceEntry(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initCancelAttendanceEntry");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;		
		try {
			setUserId(request, attendanceEntryForm);
			attendanceEntryForm.clearAll();
			setDataToFormCanceMode(attendanceEntryForm);
			request.setAttribute("attendanceEntryOperation", "load");
		} catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initCancelAttendanceEntry");
		return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE_FOR_STAFF);
	}
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		 This method will lists the attendance taken between two dates.
	 */
	public ActionForward cancelAttendanceSearch(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of cancelAttendanceSecondPage");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionErrors errors=attendanceEntryForm.validate(mapping, request);
		if(attendanceEntryForm.getClasses() == null || attendanceEntryForm.getClasses().length == 0) {
			errors.add("errors",new ActionError(CMSConstants.ATTENDANCE_CLASS_REQUIRED));
		}	
		if(attendanceEntryForm.getAttendanceTypeId() == null || attendanceEntryForm.getAttendanceTypeId().length()== 0) {
			errors.add("errors",new ActionError(CMSConstants.Attendance_Required));
		}
	
		try {
			if(errors.isEmpty()) {
				AttendanceEntryHandler.getInstance().getAttendance(attendanceEntryForm);
			} else {
				attendanceEntryForm.setAttendanceList(null);
				saveErrors(request, errors);
			}
		} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NORECORD));
    		saveErrors(request,errors);
    		attendanceEntryForm.setAttendanceList(null);
    		return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE_FOR_STAFF);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
			throw e;
		}
	 	return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE_FOR_STAFF);
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 			This method will mark the attendance canceled.
	 */
	public ActionForward cancelSelectedAttendance(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of cancelSelectedAttendance");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		try {
			AttendanceEntryHandler.getInstance().cancelAttendance(attendanceEntryForm);
			ActionMessage message = new ActionMessage(CMSConstants.ATTENDANCE_CANCELED_SUCCESSFULLY);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
			AttendanceEntryHandler.getInstance().getAttendanceNoException(attendanceEntryForm);
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE_FOR_STAFF);
			//initsetDataToFormCanceMode(attendanceEntryForm);
		} catch (DataNotFoundException e) {
			  errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_CANCEL_SELECTONE));
			  saveErrors(request,errors);
		} catch (Exception e) {
		  log.debug(e.getMessage());
		  errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_CANCEL_FAILED));
		  saveErrors(request,errors);
		  attendanceEntryForm.setAttendanceList(null);
		}
		log.info("Leaving cancelSelectedAttendance");
	 	return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE_FOR_STAFF);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward.
	 * @throws Exception
	 * 		   This method will be called when modify attendance is initiated.
	 */
	public ActionForward initModifyAttendanceEntry(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntry");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;		
		try {
			setUserId(request, attendanceEntryForm);
			attendanceEntryForm.clearAll();
			setDataToFormCanceMode(attendanceEntryForm);
			request.setAttribute("attendanceEntryOperation", "load");
			Properties prop = new Properties();
			InputStream in = AttendanceEntryForStaffAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	        prop.load(in);
	        String path=prop.getProperty("knowledgepro.attendance.sms.required");
	        if(path!=null && path.equals("true")){
	        	attendanceEntryForm.setDisplayRequired("true");
	        }else{
	        	attendanceEntryForm.setDisplayRequired("false");
	        }
		} catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initAttendanceEntry");
		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		 This method will lists the attendance taken between two dates.
	 */
	public ActionForward modifyAttendanceSearch(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of modifyAttendanceSearch");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionMessages errors=attendanceEntryForm.validate(mapping, request);
		if(attendanceEntryForm.getClasses() == null || attendanceEntryForm.getClasses().length == 0) {
			errors.add("errors",new ActionError(CMSConstants.ATTENDANCE_CLASS_REQUIRED));
		}
		if(attendanceEntryForm.getAttendancedate()!=null && !attendanceEntryForm.getAttendancedate().isEmpty())
		{
			java.util.Date startDate = new java.util.Date();
			java.util.Date endDate = CommonUtil.ConvertStringToDate(attendanceEntryForm.getAttendancedate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			if((CommonUtil.getDaysBetweenDates(cal2, cal1)-1)>2)
			{
				errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED4));
			}
		}
		try {
			if(errors.isEmpty()) {
				AttendanceEntryHandler.getInstance().getAttendanceForModify(attendanceEntryForm);
			}
			else {
				attendanceEntryForm.setAttendanceList(null);
				saveErrors(request, errors);
			}
		} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NORECORD));
    		saveErrors(request,errors);
    		attendanceEntryForm.setAttendanceList(null);
    		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NORECORD));
    		saveErrors(request,errors);
    		attendanceEntryForm.setAttendanceList(null);
    		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF);
		}
	 	return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This method lists the students based on conditions for an particular attendance taken.
	 */
	public ActionForward modifyAttendance(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of modifyAttendance");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionMessages errors = new ActionErrors();
		HttpSession session=request.getSession();
		try {
			if(errors.isEmpty()) {
				AttendanceEntryHandler.getInstance().searchStudents(attendanceEntryForm);
				setMondatoryFields(attendanceEntryForm);
				initSetDataOnError(attendanceEntryForm,session);
				return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF_SECONDPAGE);
			}
			else {
				initSetDataOnError(attendanceEntryForm,session);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
			}
		} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NOTPRESENT));
	 		initSetDataOnError(attendanceEntryForm,session);
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 			This method will update the attendance.
	 */
	public ActionForward updateAttendance(ActionMapping mapping,ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of saveAttendance");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionMessages messages = new ActionMessages();
		ActionMessages errors=attendanceEntryForm.validate(mapping, request);
		try {
			validateClassesAndPeriod(attendanceEntryForm, errors);
 			if(errors.isEmpty()) {
				boolean isDuplicateCheckReq = AttendanceEntryHandler.getInstance().isDuplicateCheckRequired(attendanceEntryForm);
				if(isDuplicateCheckReq) {
					AttendanceEntryHandler.getInstance().duplicateCheckWhileUpdate(attendanceEntryForm);
				}	
				AttendanceEntryHandler.getInstance().updateAttendance(attendanceEntryForm);
				if(attendanceEntryForm.getDisplayRequired().equals("true")){
					AttendanceEntryHelper.getInstance().sendSMSUpdate(attendanceEntryForm);
					}
				attendanceEntryForm.clear();
				AttendanceEntryHandler.getInstance().getAttendanceForModify(attendanceEntryForm);
				initsetDataToFormCanceMode(attendanceEntryForm);
				ActionMessage message = new ActionMessage(CMSConstants.ATTENDANCE_MODIFY_SUCCESSFULLY);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF);
			} else {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF_SECONDPAGE);
			}
		} catch(DuplicateException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_EXIST));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF_SECONDPAGE);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_MODIFY_FAILED));
    		saveErrors(request,errors);
		}
		log.info("Leaving into saveAttendance");
		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This page will just loads the 2nd page of attendance.
	 */
	public ActionForward loadModifyFirstPage(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		attendanceEntryForm.setBatchId(null);
		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_FOR_STAFF);
	}
	 
	/**
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 *         This method will set the data back to form.
	 */
	public void initsetDataToForm(AttendanceEntryForm attendanceEntryForm) throws Exception{
		// This will load the all attendance type in application.
		AttendanceTypeHandler attendanceTypeHandler = AttendanceTypeHandler.getInstance();
		attendanceTypeHandler.getAttendanceTypeMandatory(attendanceEntryForm,null);
//		attendanceEntryForm.setAttendancedate(CommonUtil.getTodayDate());
		if(attendanceEntryForm.getAttendanceTypeId()!=null && attendanceEntryForm.getAttendanceTypeId().length() !=0) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(attendanceEntryForm.getAttendanceTypeId()));
			// This will add activities in to map they belong to particular attendancetypeid.
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
			attendanceEntryForm.setActivityMap(activityMap);
		}
		Map<Integer,String> teachersMap =UserInfoHandler.getInstance().getTeachingStaff();
		attendanceEntryForm.setTeachersMap(teachersMap);
		// verifying whether user is staff
		IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
		boolean isStaff=transaction.checkIsStaff(attendanceEntryForm.getUserId());
		if(isStaff){
			String[] selectedTeachers = {attendanceEntryForm.getUserId()};
			attendanceEntryForm.setTeachers(selectedTeachers);
			attendanceEntryForm.setIsStaff(true);
			// Getting the class map current academic year.
			Map<Integer,String> classMap = setpClassMapToRequest(attendanceEntryForm);
			attendanceEntryForm.setClassMap(classMap);
		}else{
			attendanceEntryForm.setIsStaff(false);
			attendanceEntryForm.setClassMap(new HashMap<Integer, String>());
		}
		attendanceEntryForm.setHoursHeld("1");
	}
	
	/**
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 * 		   This method will load the predefined data in cancel mode.
	 */
	public void initsetDataToFormCanceMode(AttendanceEntryForm attendanceEntryForm) throws Exception{
		// This will load the all attendance type in application.
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		Map<Integer,String> attendanceTypes = new HashMap<Integer, String>();
		Iterator<AttendanceTypeTO> itr = attendanceTypeList.iterator();
		AttendanceTypeTO attendanceTypeTO;
		while(itr.hasNext()) {
			attendanceTypeTO = itr.next();
			attendanceTypes.put(attendanceTypeTO.getId(), attendanceTypeTO.getAttendanceTypeName());
		}
		attendanceEntryForm.setAttendanceTypes(attendanceTypes);
		attendanceEntryForm.setAttendancedate(CommonUtil.getTodayDate());
		
		// verifying whether user is staff
		IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
		boolean isStaff=transaction.checkIsStaff(attendanceEntryForm.getUserId());
		if(isStaff){
			String[] selectedTeachers = {attendanceEntryForm.getUserId()};
			attendanceEntryForm.setTeachers(selectedTeachers);
			attendanceEntryForm.setIsStaff(true);
			// Getting the class map current academic year.
			Map<Integer,String> classMap = setpClassMapToRequest(attendanceEntryForm);
			attendanceEntryForm.setClassMap(classMap);
		}else{
			attendanceEntryForm.setIsStaff(false);
		}
		
		
	}
	
	public void setMondatoryFields(AttendanceEntryForm attendanceEntryForm)throws Exception {
		// This will load the all attendance type in application.
		AttendanceTypeHandler attendanceTypeHandler = AttendanceTypeHandler.getInstance();
		attendanceTypeHandler.getAttendanceTypeMandatory(attendanceEntryForm,attendanceEntryForm.getAttendanceTypeId());
	}
	
	/**
	 *  This method will load the data on error mode.
	 */
	public void initSetDataOnError(AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception {
		Map<String,String> subjectMap = new LinkedHashMap<String,String>();
		Map<Integer,String> batchMap = new HashMap<Integer,String>();
		Map<Integer,String> periodMap = new LinkedHashMap<Integer,String>();
		
		if(attendanceEntryForm.getAttendanceTypeId()!=null && attendanceEntryForm.getAttendanceTypeId().length() !=0) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(attendanceEntryForm.getAttendanceTypeId()));
			
			// This will add activities in to map they belong to particular attendancetypeid.
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
			attendanceEntryForm.setActivityMap(activityMap);
		}
		
		if(attendanceEntryForm.getClasses()!= null && attendanceEntryForm.getClasses().length != 0) {
			Set<Integer> classesIdsSet = new HashSet<Integer>(); 
			for(String str :attendanceEntryForm.getClasses()) {
				classesIdsSet.add(Integer.parseInt(str));
			}
		
			List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
			Iterator<ClassSchemewise> itr = classSchemewiseList.iterator();
			ClassSchemewise classSchemewise;
			while(itr.hasNext()) {
				classSchemewise = itr.next();
				if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=null && classSchemewise.getClasses().getCourse().getId()!= 0 && classSchemewise.getClasses().getTermNumber()!=0){
					int year=classSchemewise.getCurriculumSchemeDuration().getAcademicYear();
					int courseId=classSchemewise.getClasses().getCourse().getId();
					int term=classSchemewise.getClasses().getTermNumber();
					Map<Integer,String> tempMap = CommonAjaxHandler.getInstance().getSubjectByCourseIdTermYear(courseId,year,term);
					//subjectMap.putAll(tempMap);
				}
			}
			
			periodMap = CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseId(classesIdsSet);
			
			if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
//				batchMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds(Integer.parseInt(attendanceEntryForm.getSubjectId()),classesIdsSet);
				batchMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds1(attendanceEntryForm.getSubjectId(),classesIdsSet,session);
			}
			if(attendanceEntryForm.getActivityId()!=null && attendanceEntryForm.getActivityId().length()!=0){
//				batchMap=CommonAjaxHandler.getInstance().getBatchesByActivityAndClassIds(Integer.parseInt(attendanceEntryForm.getActivityId()), classesIdsSet);
				batchMap=CommonAjaxHandler.getInstance().getBatchesByActivityAndClassIds1(Integer.parseInt(attendanceEntryForm.getActivityId()), classesIdsSet);
			}
			
			attendanceEntryForm.setPeriodMap(periodMap);
			attendanceEntryForm.setBatchMap(batchMap);
			attendanceEntryForm.setSubjectMap(subjectMap);
		} else {
			attendanceEntryForm.setPeriodMap(periodMap);
			attendanceEntryForm.setBatchMap(batchMap);
			attendanceEntryForm.setSubjectMap(subjectMap);
		}
		//setpClassMapToRequest(attendanceEntryForm);
		
	}
	
	/**
	 * Sets all the classes for the current year in request scope
	 */
	
	public Map<Integer,String> setpClassMapToRequest(AttendanceEntryForm attendanceEntryForm) throws Exception{
		log.info("Entering into setpClassMapToRequest of CreatePracticalBatchAction");
		Map<Integer,String> classMap = new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			classMap = CommonAjaxHandler.getInstance().getClassesByTeacher(Integer.parseInt(attendanceEntryForm.getUserId()),String.valueOf(currentYear));
			return classMap;
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error("Error occured in setpClassMapToRequest of CreatePracticalBatchAction");
		}
		log.info("Leaving into setpClassMapToRequest of CreatePracticalBatchAction");
		return classMap;
	}
	/**
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 * 		   This method will load the predefined data in cancel mode.
	 */
	public void setDataToFormCanceMode(AttendanceEntryForm attendanceEntryForm) throws Exception{
		// This will load the all attendance type in application.
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		Map<Integer,String> attendanceTypes = new HashMap<Integer, String>();
		Iterator<AttendanceTypeTO> itr = attendanceTypeList.iterator();
		AttendanceTypeTO attendanceTypeTO;
		while(itr.hasNext()) {
			attendanceTypeTO = itr.next();
			attendanceTypes.put(attendanceTypeTO.getId(), attendanceTypeTO.getAttendanceTypeName());
		}
		attendanceEntryForm.setAttendanceTypes(attendanceTypes);
		attendanceEntryForm.setAttendancedate(CommonUtil.getTodayDate());
		// Setting classes map in to form
		Map<Integer,String> classMap = setupClassMapToRequest(attendanceEntryForm);
		attendanceEntryForm.setClassMap(classMap);
	}
	/**
	 * Sets all the classes for the current year in request scope
	 */
	
	public Map<Integer,String> setupClassMapToRequest(AttendanceEntryForm attendanceEntryForm) throws Exception{
		log.info("Entering into setpClassMapToRequest of CreatePracticalBatchAction");
		Map<Integer,String> classMap = new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			return classMap;
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error("Error occured in setpClassMapToRequest of CreatePracticalBatchAction");
		}
		log.info("Leaving into setpClassMapToRequest of CreatePracticalBatchAction");
		return classMap;
	}
	/**
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 * 		   This method will load the predefined data in cancel mode.
	 */
	public void setDataToFormErrorMode(AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception{
		Map<String,String> subjectMap = new LinkedHashMap<String,String>();
		Map<Integer,String> batchMap = new HashMap<Integer,String>();
		Map<Integer,String> periodMap = new LinkedHashMap<Integer,String>();
		// This will load the all attendance type in application.
		if(attendanceEntryForm.getAttendanceTypeId()!=null && attendanceEntryForm.getAttendanceTypeId().length() !=0) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(attendanceEntryForm.getAttendanceTypeId()));
			
			// This will add activities in to map they belong to particular attendancetypeid.
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
			attendanceEntryForm.setActivityMap(activityMap);
		}
		
		if(attendanceEntryForm.getClasses()!= null && attendanceEntryForm.getClasses().length != 0) {
			Set<Integer> classesIdsSet = new HashSet<Integer>(); 
			for(String str :attendanceEntryForm.getClasses()) {
				classesIdsSet.add(Integer.parseInt(str));
			}
		
		/*	List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
			Iterator<ClassSchemewise> itr = classSchemewiseList.iterator();
			ClassSchemewise classSchemewise;
			while(itr.hasNext()) {
				classSchemewise = itr.next();
				if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=null && classSchemewise.getClasses().getCourse().getId()!= 0 && classSchemewise.getClasses().getTermNumber()!=0){
					int year = classSchemewise.getCurriculumSchemeDuration()
					.getAcademicYear();
			int courseId = classSchemewise.getClasses().getCourse()
					.getId();
			int term = classSchemewise.getClasses().getTermNumber();
			String[] tempTeacher=attendanceEntryForm.getTeachers();
			Map<Integer,String> tempMap = CommonAjaxHandler.getInstance()
					.getSubjectByCourseIdTermYearTeacher(courseId, year, term,Integer.parseInt(tempTeacher[0]),attendanceEntryForm.getClasses());
			subjectMap.putAll(tempMap);
					subjectMap.putAll(tempMap);
				}
			}*/
			
			// Code has Written by Balaji 
			Object selectedClasses[] =classesIdsSet.toArray();
			Set<Integer> temClassIdsSet=new HashSet<Integer>();
			temClassIdsSet.add(Integer.parseInt(selectedClasses[0].toString()));
			periodMap = CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseId(temClassIdsSet);
			
			if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
//				batchMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds(Integer.parseInt(attendanceEntryForm.getSubjectId()),classesIdsSet);
				batchMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds1(attendanceEntryForm.getSubjectId(),classesIdsSet,session);
			}
			if(attendanceEntryForm.getActivityId()!=null && attendanceEntryForm.getActivityId().length()!=0){
//				batchMap=CommonAjaxHandler.getInstance().getBatchesByActivityAndClassIds(Integer.parseInt(attendanceEntryForm.getActivityId()), classesIdsSet);
				batchMap=CommonAjaxHandler.getInstance().getBatchesByActivityAndClassIds1(Integer.parseInt(attendanceEntryForm.getActivityId()), classesIdsSet);
			}
			
			
			attendanceEntryForm.setPeriodMap(periodMap);
			attendanceEntryForm.setBatchMap(batchMap);
			attendanceEntryForm.setSubjectMap(subjectMap);
		} else {
			attendanceEntryForm.setPeriodMap(periodMap);
			attendanceEntryForm.setBatchMap(batchMap);
			attendanceEntryForm.setSubjectMap(subjectMap);
		}
		if(attendanceEntryForm.getTeachers()!=null){
			Map<Integer,String> classMap = AttendanceEntryHandler.getInstance().getClassesByTeacher(attendanceEntryForm.getTeachers(),attendanceEntryForm.getYear());
			attendanceEntryForm.setClassMap(classMap);
		}
	}
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This method will be invoke when the particular link clicked.
	 * 
	 */
	public ActionForward setDataForNumericCode(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.info("Inside of initAttendanceEntry");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		HttpSession session=request.getSession();
		try {
			if(attendanceEntryForm.getNumericCode()!=null && !attendanceEntryForm.getNumericCode().trim().isEmpty()){
				ITeacherClassEntryTransaction transaction=new TeacherClassEntryImpl();
				List<TeacherClassSubject> duplicateList=transaction.getDuplicateList(AttendanceEntryHandler.getInstance().getQueryForNumericCode(attendanceEntryForm));
				if(duplicateList!=null && !duplicateList.isEmpty()){
					AttendanceEntryHandler.getInstance().setDataToForm(attendanceEntryForm,duplicateList,session);
				}else{
					setUserId(request, attendanceEntryForm);
					attendanceEntryForm.clearAll();
					// setting the necessary data to form on loading
					initsetDataToForm(attendanceEntryForm);
					request.setAttribute("attendanceEntryOperation", "load");
				}
			}else{
			}
			
		} catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initAttendanceEntry");
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FOR_STAFF_FIRST);
	}
	
	
	
	private void validatePeriodsForClasses(AttendanceEntryForm attendanceEntryForm, ActionMessages errors)  throws Exception{
		if((attendanceEntryForm.getClasses() !=null && attendanceEntryForm.getClasses().length !=0) && (attendanceEntryForm.getPeriods() !=null && attendanceEntryForm.getPeriods().length !=0)) {
			IAttendanceEntryTransaction transaction=AttendanceEntryTransactionImpl.getInstance();
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			String[] selectedClasses=attendanceEntryForm.getClasses();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			String periodNames="";
			if(attendanceEntryForm.getPeriods() != null) {	
				String [] tempArray = attendanceEntryForm.getPeriods();
				StringBuilder intType = new StringBuilder();
				for(int i=0;i<tempArray.length;i++){
					intType.append(tempArray[i]);
					 if(i<(tempArray.length-1)){
						 intType.append(",");
					 }
				}
				String query="select p.periodName from Period p where p.id in ("+intType+")";
				periodNames=transaction.getPeriodNamesById(query);
			}
			if(!periodNames.isEmpty()){
				String[] periods=periodNames.split(",");
				Map<Integer,String> classNames=transaction.getClassNamesByIds(classesIdsSet);
				Map<Integer,List<String>> periodMap=transaction.getPeriodsForClassAndPeriods(classesIdsSet,attendanceEntryForm.getPeriods());
				if(periodMap.size()!=selectedClasses.length){
					errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendance.periods.not.configured"));
				}else{
					for(int i=0;i<periods.length;i++){
						String name=periods[i];
						Iterator it = periodMap.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry pairs = (Map.Entry)it.next();
							List<String> checkName=(ArrayList<String>)pairs.getValue();
							if(!checkName.contains(name)){
								errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendance.periodname.not.exists",name,classNames.get(Integer.parseInt(pairs.getKey().toString())) ));
							}
						}
					}
				}
			}
		}	
	}
}