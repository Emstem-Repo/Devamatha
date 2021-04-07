package com.kp.cms.actions.attendance;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
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
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceEntryHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.helpers.attendance.AttendanceTypeHelper;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.ActivityTO;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.transactions.admin.ISubjectTransaction;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactions.attandance.IAttendanceTypeTransaction;
import com.kp.cms.transactions.attandance.ITeacherClassEntryTransaction;
import com.kp.cms.transactions.attandance.ITeacherDepartmentEntryTransaction;
import com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction;
import com.kp.cms.transactionsimpl.admin.SubjectTransactionImp;
import com.kp.cms.transactionsimpl.attendance.AcademicyearTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceTypeTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.TeacherClassEntryImpl;
import com.kp.cms.transactionsimpl.attendance.TeacherDepartmentEntryTransactionImpl;
import com.kp.cms.transactionsimpl.employee.DownloadEmployeeResumeTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * Action class for Attendance related activities
 * this method will be used in the taking,modifying,canceling of attendance. 
 *
 */
@SuppressWarnings("deprecation")
public class AttendanceEntryAction extends BaseDispatchAction {
	
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final Log log = LogFactory.getLog(AttendanceEntryAction.class);
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
			if(request.getHeader("User-Agent").indexOf("Mobile") != -1) {
				   attendanceEntryForm.setIsMobileAccess("true");
				  } else {
				    //nope, this is probably a desktop
					   attendanceEntryForm.setIsMobileAccess("false");
				  }
			setUserId(request, attendanceEntryForm);
			attendanceEntryForm.clearAll();
			// setting the necessary data to form on loading
			initsetDataToForm(attendanceEntryForm,request);
			request.setAttribute("attendanceEntryOperation", "load");
			attendanceEntryForm.setDisplayRequired(CMSConstants.isSmsRequired);
	        IAttendanceEntryTransaction transaction=AttendanceEntryTransactionImpl.getInstance();
			Date attDate=transaction.getLastEnteredAttendanceDate();
			
			// To Display the message for which date attendance has been entered
			if(attDate!=null){
				ActionMessage message = new ActionMessage("knowledgepro.admin.attendance.lastEntered", CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(attDate), AttendanceEntryAction.SQL_DATEFORMAT,AttendanceEntryAction.FROM_DATEFORMAT));
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
		
		if(attendanceEntryForm.getIsStaff()){
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
		}else{
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
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
	 * This will be called when second page of attendance will be needed.
	 * this method will redirect to different pages based on the requirement.
	 */
	public ActionForward initAttendanceEntrySecondPage(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntrySecondPage");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		
		if(attendanceEntryForm.getSubjectId() == null || attendanceEntryForm.getSubjectId().isEmpty()) {
			attendanceEntryForm.setSubjectId(attendanceEntryForm.getSubjectIdHidden());
		}
		ActionMessages errors=attendanceEntryForm.validate(mapping, request);
		//httpSession added by mehaboob
		HttpSession session=request.getSession();
		
		
		try {
			if(attendanceEntryForm.getAttendancedate()!= null){
				boolean isHolidayDate=AttendanceEntryHandler.getInstance().checkHolidayDate(attendanceEntryForm.getAttendancedate());
				if(isHolidayDate){
					errors.add("error", new ActionError("knowledgepro.attendance.date.holiday"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
				}
			}
			
			if(errors.isEmpty()) {
			attendanceEntryForm.setRoleId(session.getAttribute("rid").toString());	
			boolean isallow=true;
			boolean isrestrict=false;
			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			//current time
			java.util.Date d1= new java.util.Date();
			Time curTime = new Time(d1.getTime());
			Set<Integer> restrictSetClassIds=new HashSet<Integer>();
			
			// restrict time for particular class
			
			//	commented by Arun Sudhakaran
				/*List<AttendanceTimeRestrictionClasswise> timeRestrictList = AttendanceEntryHandler.getInstance().getAttendanceTimeRestriction(attendanceEntryForm.getClasses());
				if(!timeRestrictList.isEmpty()){
				Iterator<AttendanceTimeRestrictionClasswise> itr = timeRestrictList.iterator();
				while(itr.hasNext()){
					AttendanceTimeRestrictionClasswise bo = itr.next();
					restrictSetClassIds.add(bo.getClassSchemewise().getId());
					SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
					java.util.Date d = df1.parse(bo.getTime());
					Calendar cal1 = Calendar.getInstance();
					cal1.set(Calendar.HOUR_OF_DAY, d.getHours()); // Your hour
					cal1.set(Calendar.MINUTE, d.getMinutes()); // Your Mintue
					cal1.set(Calendar.SECOND, d.getSeconds()); // Your second
					Time restrictTime = new Time(cal1.getTime().getTime());
					if(restrictTime.before(curTime)){
						  errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_TIME_RESTRICTED));
						  isrestrict=true;
						  break;
					}
				}			
				
				}*/
			
			
			
			if(!isrestrict){
			
					Set<Integer> allclassesIdsSet = new HashSet<Integer>(); 
					if(attendanceEntryForm.getClasses()!= null && attendanceEntryForm.getClasses().length != 0) {
						for(String str :attendanceEntryForm.getClasses()) {
							if(str != null){
								allclassesIdsSet.add(Integer.parseInt(str));
								}
							}
						}
			
			if(allclassesIdsSet.size()>restrictSetClassIds.size()){
				
				SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
				java.util.Date d = df1.parse("18:30:00");
				Calendar cal1 = Calendar.getInstance();
				cal1.set(Calendar.HOUR_OF_DAY, d.getHours()); // Your hour
				cal1.set(Calendar.MINUTE, d.getMinutes()); // Your Mintue
				cal1.set(Calendar.SECOND, d.getSeconds()); // Your second
				Time restrictTime = new Time(cal1.getTime().getTime());
				  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Mon") && curTime.before(restrictTime)){
					  int i=0;
				       while(i<8){
				    	  /*cal.add(Calendar.DATE, -i);
				    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    	  }*/
				    	   Calendar cale = Calendar.getInstance();
				    	   cale.add(Calendar.DATE, -i);
				    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    		  break;
				    	  }
				    	   i++;
				       }
					}
				  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Tue")){
					  int i=0;
				       while(i<3){
				    	   
				    	  /*cal.add(Calendar.DATE, -i);
				    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    	  }*/
				    	   Calendar cale = Calendar.getInstance();
				    	   cale.add(Calendar.DATE, -i);
				    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    		  break;
				    	  }
				    	   i++;
				       }
					}
				  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Wed")){
					  int i=0;
				       while(i<4){
				    	   Calendar cale = Calendar.getInstance();
				    	   cale.add(Calendar.DATE, -i);
				    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    		  break;
				    	  }
				    	   i++;
				       }
					}
				  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Thu")){
					  int i=0;
				       while(i<5){
				    	   
				    	 /* cal.add(Calendar.DATE, -i);
				    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    	  }*/
				    	   Calendar cale = Calendar.getInstance();
				    	   cale.add(Calendar.DATE, -i);
				    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    		  break;
				    	  }
				    	   i++;
				       }
					}
				  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Fri")){
					  int i=0;
				       while(i<5){
				    	   
				    	 /* cal.add(Calendar.DATE, -i);
				    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    	  }*/
				    	   Calendar cale = Calendar.getInstance();
				    	   cale.add(Calendar.DATE, -i);
				    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    		  break;
				    	  }
				    	   i++;
				       }
					}
				  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Sat")){
					  int i=0;
				       while(i<6){
				    	   
				    	/*  cal.add(Calendar.DATE, -i);
				    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    	  }*/
				    	   Calendar cale = Calendar.getInstance();
				    	   cale.add(Calendar.DATE, -i);
				    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    		  break;
				    	  }
				    	   i++;
				       }
					}
				  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Sun")){
					  int i=0;
				       while(i<7){
				    	   
				    	 /* cal.add(Calendar.DATE, -i);
				    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    	  }*/
				    	   Calendar cale = Calendar.getInstance();
				    	   cale.add(Calendar.DATE, -i);
				    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
				    		  isallow=true;
				    		  break;
				    	  }
				    	   i++;
				       }
					}
				  
				  if(!isallow){
					  if(CMSConstants.ACCESS_ROLE_ID.contains(Integer.parseInt(attendanceEntryForm.getRoleId())))
					  {			
					  }else{
						  errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_TIME_RESTRICTED));
					  }			
				  }
			
			}//size check close
			
			}//restrict close
			else {
				setDataToFormErrorMode(attendanceEntryForm,session);
				saveErrors(request, errors);
				
				if(attendanceEntryForm.getIsStaff()){
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
				}else{
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
				}
				
			}
			
			
			}else {
				setDataToFormErrorMode(attendanceEntryForm,session);
				saveErrors(request, errors);
				
				if(attendanceEntryForm.getIsStaff()){
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
				}else{
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
				}
				
			}
			
			
			
			Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) session.getAttribute("SubjectCodeGroupMap");
			if(subjectCodeGroupMap==null || subjectCodeGroupMap.isEmpty()){
				if(attendanceEntryForm.getBatchIdsArray()!=null && attendanceEntryForm.getBatchIdsArray().length>1){
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_SELECT_ONE_BATCH));
				}
			}
			if(errors.isEmpty()) {
				// These below errors are kind of warning messages
				if((attendanceEntryForm.getClasses() ==null || attendanceEntryForm.getClasses().length ==0) && (attendanceEntryForm.getPeriods() ==null || attendanceEntryForm.getPeriods().length ==0)) {
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED1));
				} else if(attendanceEntryForm.getClasses() ==null || attendanceEntryForm.getClasses().length ==0 ) {
					
					if(attendanceEntryForm.getClassesHidden() != null && attendanceEntryForm.getClassesHidden().length != 0) {
						if(attendanceEntryForm.getClassesHidden()[0].split(",").length > 1) {
							attendanceEntryForm.setClassesHidden(attendanceEntryForm.getClassesHidden()[0].split(","));
						}
						attendanceEntryForm.setClasses(attendanceEntryForm.getClassesHidden());
						Map<Integer,String> classMap = setpClassMapToRequest(attendanceEntryForm);
						String className = "";
						for (int i = 0; i < attendanceEntryForm.getClassesHidden().length; i++) {
							 if(className.isEmpty()){
								 String[] l = attendanceEntryForm.getClassesHidden()[i].split(",");
								 for(String s : l){
									 className=classMap.get(Integer.parseInt(s));
								 }
								//className=classMap.get(Integer.parseInt(attendanceEntryForm.getClassesHidden()[i]));
								// className=classMap.get(Integer.parseInt(attendanceEntryForm.getClassesHidden()[i]));
							 }else
								 className=className+","+classMap.get(Integer.parseInt(attendanceEntryForm.getClassesHidden()[i]));
						}
						
						attendanceEntryForm.setAttendanceClass(className);
					}
					else {
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED2));
					}
				} else if(attendanceEntryForm.getPeriods() ==null || attendanceEntryForm.getPeriods().length ==0) {
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED3));
				}
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
					
					if(attendanceEntryForm.getIsStaff()){
						return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
					}else{
						return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
					}
					
				}
				
				IAttendanceEntryTransaction transaction=AttendanceEntryTransactionImpl.getInstance();
				
				
				AttendanceEntryHandler attendanceEntryHandler = AttendanceEntryHandler.getInstance();
				if(request.getParameter("continueToSecondPage")==null){
					//code added by mehaboob to get batchMap start
					Map<Integer, String> batchMap=null;
					Set<Integer> classesIdsSet1 = new HashSet<Integer>(); 
					if(attendanceEntryForm.getClasses()!= null && attendanceEntryForm.getClasses().length != 0) {
						for(String str :attendanceEntryForm.getClasses()) {
							if(str != null){
								classesIdsSet1.add(Integer.parseInt(str));
							}
						}
					}
					if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
						//				batchMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds(Integer.parseInt(attendanceEntryForm.getSubjectId()),classesIdsSet);
										batchMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds1(attendanceEntryForm.getSubjectId(),classesIdsSet1,session);
										if(batchMap!=null && !batchMap.isEmpty()){
											attendanceEntryForm.setBatchMap(batchMap);
										}
									}
					if(attendanceEntryForm.getActivityId()!=null && attendanceEntryForm.getActivityId().length()!=0){
//						batchMap=CommonAjaxHandler.getInstance().getBatchesByActivityAndClassIds(Integer.parseInt(attendanceEntryForm.getActivityId()), classesIdsSet);
						batchMap=CommonAjaxHandler.getInstance().getBatchesByActivityAndClassIds1(Integer.parseInt(attendanceEntryForm.getActivityId()), classesIdsSet1);
						if(batchMap!=null && !batchMap.isEmpty()){
							attendanceEntryForm.setBatchMap(batchMap);
						}
					}
					//end
				 attendanceEntryHandler.duplicateCheck(attendanceEntryForm,request);
				 // second level validation for teacher duplication 
				 
				 // if condition commented by chandra bcz warning message checking is not working with time table option
				 //if(attendanceEntryForm.getTimeTableFormat().equalsIgnoreCase("no")){
						//boolean flag=false;
						java.sql.Date date=CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
						String teacherId[]=attendanceEntryForm.getTeachers();
						for(int i=0;i<teacherId.length;i++){
							int teachId=Integer.parseInt(teacherId[i]);
							String teacherName=transaction.getTeacherName(teachId);
							String periodId[]=attendanceEntryForm.getPeriods();
								for(int j=0;j<periodId.length;j++){
									int perId=Integer.parseInt(periodId[j]);
									Period period=transaction.getPeriods(perId);
									String startTime=period.getStartTime();
									String endTime=period.getEndTime();
									String time=startTime.substring(0,5)+"-"+endTime.substring(0,5);
									if(request.getParameter("continueToSecondPage")==null){
									if(!CMSConstants.LINK_FOR_CJC){
										
										// /* code added by chandra
										Set<Integer> classesIdsSet = new HashSet<Integer>();
										String[] selectedClasses=attendanceEntryForm.getClasses();
										
										for (int k = 0; k < selectedClasses.length; k++) {
											if(selectedClasses[k] != null){
												classesIdsSet.add(Integer.parseInt(selectedClasses[k]));
											}
										}
										
										
										//raghu
										
										Set<Integer> periodSet = new HashSet<Integer>();
										/*if (attendanceEntryForm.getPeriods() != null) {
											for (String str : attendanceEntryForm.getPeriods()) {
												periodSet.add(Integer.parseInt(str));
											}
										}*/
										
										if (attendanceEntryForm.getPeriods() != null) {
											List<Integer> periodList=transaction.getAllPeriodIdsByInput(attendanceEntryForm);
											periodSet.addAll(periodList);
										//	for (String str : attendanceEntryForm.getPeriods()) {
										//		periodSet.add(Integer.parseInt(str));
										//	}
										}
										// */
										//code added by mehaboob batchIDsArray start 
										if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
											for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
												AttendanceEntryHandler.getInstance().checkAttendanceDuplication(date,teachId,startTime,endTime,attendanceEntryForm.getSubjectId(),request,time,teacherName,teachId,classesIdsSet,batchId,periodSet);
											}
										}else{
											AttendanceEntryHandler.getInstance().checkAttendanceDuplication(date,teachId,startTime,endTime,attendanceEntryForm.getSubjectId(),request,time,teacherName,teachId,classesIdsSet,attendanceEntryForm.getBatchId(),periodSet);
										}
										//end
										/*if(flag){
											errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendance.teacher.period.time",time,teacherName));
											saveErrors(request, errors);
											setDataToFormErrorMode(attendanceEntryForm);
											return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);
										}*/
									}
								}
								}
								
								//raghu
								//if(attendanceEntryForm.getTimeTableFormat().equalsIgnoreCase("yes"))
								if(periodId.length!=0)
								attendanceEntryForm.setHoursHeld(new Integer(periodId.length).toString());
							}
					//} if condition commented by chandra
				 
				// second level validation for teacher duplication till here
				 boolean isSecondRequired = attendanceEntryHandler.isSecondPageRequired(attendanceEntryForm);
				 attendanceEntryForm.setSecondPage(isSecondRequired);
				 attendanceEntryHandler.setCoEntryTeachersName(attendanceEntryForm);
				 //if isSecondRequired is true then show second page else skip.
				 if(isSecondRequired) {
					 return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_SECOND);
				 } else {
					 return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_INITTHIRD);
				 }	
				}else{
					attendanceEntryForm.setSecondPage(false);
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_INITTHIRD);
				}
				
			} else {
				setDataToFormErrorMode(attendanceEntryForm,session);
				saveErrors(request, errors);
				
				if(attendanceEntryForm.getIsStaff()){
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
				}else{
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
				}
				
			}
			}
		 catch(DuplicateException e) {
			// will be thrown when  duplicate occurs.
			if(request.getAttribute("isSubjectId")!=null && request.getAttribute("isSubjectId").equals("true")){
				if(request.getAttribute("subjectName")!=null && request.getAttribute("batchNames")!=null){
					errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendance.teacher.period.time.subject.batch.resend",request.getAttribute("time"),request.getAttribute("teacherName"),request.getAttribute("subjectName"),request.getAttribute("batchNames")));
				}else if(request.getAttribute("subjectName")!=null){
					errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendance.teacher.period.time.resend",request.getAttribute("time"),request.getAttribute("teacherName"),request.getAttribute("subjectName")));
				}else if(request.getAttribute("batchNames")!=null){
					errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendance.teacher.period.time.batch.resend",request.getAttribute("time"),request.getAttribute("teacherName"),request.getAttribute("batchNames")));
				}else{
					if(request.getAttribute("attendanceClass")!=null)
						errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendance.teacher.period.time.class.resend",request.getAttribute("time"),request.getAttribute("teacherName"),request.getAttribute("attendanceClass")));	
				}
			}//changed by mehaboob batchId Array
			else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
				for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
					if(batchId!=null && !batchId.isEmpty()){
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_EXIST_BATCH));
						break;
					}
				}
			} else if(request.getAttribute("isCommonSubject")!=null && request.getAttribute("isCommonSubject").equals("true")){
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.attendance.period.time.class.commonsubject.batch.resend",request.getAttribute("classNames"),request.getAttribute("subjectNames"),request.getAttribute("batchNames")));
			}else {	
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_EXIST));
			}	
			setDataToFormErrorMode(attendanceEntryForm,session);
    		saveErrors(request,errors);
    		
    		if(attendanceEntryForm.getIsStaff()){
    			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
    		}else{
    			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
    		}
    		
	 	} catch (NumberFormatException e) {
	 		e.printStackTrace();
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
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_SECOND);
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
		//httpSession added by mehaboob
		HttpSession session=request.getSession();
		attendanceEntryForm.setBatchIdsArray(null);
		setDataToFormErrorMode(attendanceEntryForm,session);
		
		if(attendanceEntryForm.getIsStaff()){
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
		}else{
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
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
	 * 		  This method will load the third page of attendance with students list.
	 */
	public ActionForward initAttendanceEntryThirdPage(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntryThirdPage");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		
		if(attendanceEntryForm.getSubjectId() == null ||  attendanceEntryForm.getSubjectId().isEmpty()) {
			attendanceEntryForm.setSubjectId(attendanceEntryForm.getSubjectIdHidden());
		}
		//httpSession added by mehaboob
		HttpSession session=request.getSession();
		Subject subject = new Subject();
		subject.setId(Integer.parseInt(attendanceEntryForm.getSubjectId()));
		ISubjectTransaction txSub = SubjectTransactionImp.getInstance();
		attendanceEntryForm.setAttendanceSubject(txSub.loadSubject(subject).getName());
		Map<Integer,String> classMap=setpClassMapToRequest(attendanceEntryForm);
		String className = "";
		int classListLength = (attendanceEntryForm.getClasses() == null) ? attendanceEntryForm.getClassesHidden().length : attendanceEntryForm.getClasses().length;
		for(int i=0; i<classListLength;i++)
		{
			if(className.isEmpty())
			{
			//	className=classMap.get(Integer.parseInt(attendanceEntryForm.getClassesHidden()[i]));
				int classId = 0;
				if(attendanceEntryForm.getClasses() == null || attendanceEntryForm.getClasses()[i].length() ==0) {
					classId = Integer.parseInt(attendanceEntryForm.getClassesHidden()[i]);
				}
				else {
					classId = Integer.parseInt(attendanceEntryForm.getClasses()[i]);
				}
				
				className = AttendanceEntryHandler.getInstance().getclassName(classId);
			}
			else
			{
				if(attendanceEntryForm.getClasses() == null || attendanceEntryForm.getClasses()[i].length() ==0) {
					className=className+","+classMap.get(Integer.parseInt(attendanceEntryForm.getClassesHidden()[i]));
				}
				else {
					className=className+","+classMap.get(Integer.parseInt(attendanceEntryForm.getClasses()[i]));
				}
			}
		}
		attendanceEntryForm.setAttendanceClass(className);
		//raghu
		String periodId[]=attendanceEntryForm.getPeriods();
		//if(attendanceEntryForm.getTimeTableFormat().equalsIgnoreCase("yes"))
		if(periodId.length!=0)
		attendanceEntryForm.setHoursHeld(new Integer(periodId.length).toString());
		try {
			//AttendanceEntryHandler.getInstance().getStudents(attendanceEntryForm,session);
			//raghu write for new
			AttendanceEntryHandler.getInstance().getStudentsList(attendanceEntryForm,session);
			if (attendanceEntryForm.getStudentList() != null && attendanceEntryForm.getStudentList().isEmpty()) {
				message = new ActionMessage(
						CMSConstants.ATTENDANCE_ENTRY_NORECORD);
				messages.add(CMSConstants.MESSAGES, message);
				addMessages(request, messages);
				setDataToFormErrorMode(attendanceEntryForm,session);
				
				if(attendanceEntryForm.getIsStaff()){
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
				}else{
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
				}
				
			}
			if(attendanceEntryForm.getAttendanceTypeId()!=null && attendanceEntryForm.getAttendanceTypeId().length() !=0) {
				Set<Integer> set = new HashSet<Integer>();
				set.add(Integer.valueOf(attendanceEntryForm.getAttendanceTypeId()));
				
				// This will add activities in to map they belong to particular attendancetypeid.
				Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
				attendanceEntryForm.setActivityMap(activityMap);
				if(attendanceEntryForm.getActivityId()!=null && !attendanceEntryForm.getActivityId().isEmpty()){
					attendanceEntryForm.setActivityId(attendanceEntryForm.getActivityId());
					attendanceEntryForm.setAttendanceActivity(activityMap.get(Integer.parseInt(attendanceEntryForm.getActivityId())));
				}
			}	
		} catch (Exception e) {
			log.debug(e.getMessage());
			throw e;
			
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_THIRD1);
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
		//httpSession added by mehaboob
		HttpSession session=request.getSession();
		//raghu
		String periodId[]=attendanceEntryForm.getPeriods();
		//if(attendanceEntryForm.getTimeTableFormat().equalsIgnoreCase("yes"))
		if(periodId.length!=0)
		attendanceEntryForm.setHoursHeld(new Integer(periodId.length).toString());
		
		try {
			boolean flag=false;
			flag=AttendanceEntryHandler.getInstance().saveAttendance(attendanceEntryForm,session);
			if(flag){
				if(attendanceEntryForm.getIsSMSRequired().equals("Yes")){
				//AttendanceEntryHelper.getInstance().sendSMS(attendanceEntryForm);
				}
				//messages.add("messages",new ActionMessage(CMSConstants.ATTENDANCE_ADDED_SUCCESSFULLY,attendanceEntryForm.getSlipNo()));
				messages.add("messages",new ActionMessage(CMSConstants.ATTENDANCE_ADDED_SUCCESSFULLY));
				saveMessages(request, messages);
			}else{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ADDING_FAILED));
	    		saveErrors(request,errors);
			}
			attendanceEntryForm.clearAll();
			initsetDataToForm(attendanceEntryForm,request);
			saveMessages(request, messages);
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ADDING_FAILED));
    		saveErrors(request,errors);
		}
		log.info("Leaving into saveAttendance");
		
		if(attendanceEntryForm.getIsStaff()){
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
		}else{
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
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
		return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE);
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
    		return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
			throw e;
		}
	 	return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE);
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
			return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE);
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
	 	return mapping.findForward(CMSConstants.CANCEL_ATTENDANCE);
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
			if(request.getHeader("User-Agent").indexOf("Mobile") != -1) {
				   attendanceEntryForm.setIsMobileAccess("true");
				  } else {
				    //nope, this is probably a desktop
					   attendanceEntryForm.setIsMobileAccess("false");
				  }
			setUserId(request, attendanceEntryForm);
			attendanceEntryForm.clearAll();
			attendanceEntryForm.setIsTeacherLogin(false);
			setDataToFormCanceMode(attendanceEntryForm);
			request.setAttribute("attendanceEntryOperation", "load");
	        attendanceEntryForm.setDisplayRequired(CMSConstants.isSmsRequired);
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
		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
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
		
		HttpSession session=request.getSession();		
		attendanceEntryForm.setRoleId(session.getAttribute("rid").toString());	
		
		boolean isallow=false;
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		//current time
		java.util.Date d1= new java.util.Date();
		Time curTime = new Time(d1.getTime());
		
		//restrict time 6:30:00
		SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
		java.util.Date d = df1.parse("18:30:00");
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY, d.getHours()); // Your hour
		cal1.set(Calendar.MINUTE, d.getMinutes()); // Your Mintue
		cal1.set(Calendar.SECOND, d.getSeconds()); // Your second
		Time restrictTime = new Time(cal1.getTime().getTime());
			
		  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Mon") && curTime.before(restrictTime)){
			  int i=0;
		       while(i<8){
		    	  /*cal.add(Calendar.DATE, -i);
		    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    	  }*/
		    	   Calendar cale = Calendar.getInstance();
		    	   cale.add(Calendar.DATE, -i);
		    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    		  break;
		    	  }
		    	   i++;
		       }
			}
		  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Tue")){
			  int i=0;
		       while(i<3){
		    	   
		    	  /*cal.add(Calendar.DATE, -i);
		    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    	  }*/
		    	   Calendar cale = Calendar.getInstance();
		    	   cale.add(Calendar.DATE, -i);
		    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    		  break;
		    	  }
		    	   i++;
		       }
			}
		  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Wed")){
			  int i=0;
		       while(i<4){
		    	   Calendar cale = Calendar.getInstance();
		    	   cale.add(Calendar.DATE, -i);
		    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    		  break;
		    	  }
		    	   i++;
		       }
			}
		  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Thu")){
			  int i=0;
		       while(i<5){
		    	   
		    	 /* cal.add(Calendar.DATE, -i);
		    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    	  }*/
		    	   Calendar cale = Calendar.getInstance();
		    	   cale.add(Calendar.DATE, -i);
		    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    		  break;
		    	  }
		    	   i++;
		       }
			}
		  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Fri")){
			  int i=0;
		       while(i<5){
		    	   
		    	 /* cal.add(Calendar.DATE, -i);
		    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    	  }*/
		    	   Calendar cale = Calendar.getInstance();
		    	   cale.add(Calendar.DATE, -i);
		    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    		  break;
		    	  }
		    	   i++;
		       }
			}
		  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Sat")){
			  int i=0;
		       while(i<6){
		    	   
		    	/*  cal.add(Calendar.DATE, -i);
		    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    	  }*/
		    	   Calendar cale = Calendar.getInstance();
		    	   cale.add(Calendar.DATE, -i);
		    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    		  break;
		    	  }
		    	   i++;
		       }
			}
		  else  if(cal.getTime().toString().substring(0,3).equalsIgnoreCase("Sun")){
			  int i=0;
		       while(i<7){
		    	   
		    	 /* cal.add(Calendar.DATE, -i);
		    	  if( df.format(cal.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    	  }*/
		    	   Calendar cale = Calendar.getInstance();
		    	   cale.add(Calendar.DATE, -i);
		    	  if( df.format(cale.getTime()).substring(0,10).compareTo(attendanceEntryForm.getAttendancedate())==0){
		    		  isallow=true;
		    		  break;
		    	  }
		    	   i++;
		       }
			}
		  if(!isallow){
			  if(CMSConstants.ACCESS_ROLE_ID.contains(Integer.parseInt(attendanceEntryForm.getRoleId())))
			  {	
				  
			  }else{
				  errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_TIME_RESTRICTED));
			  }	
		  }
		  
		  
		if(attendanceEntryForm.getClasses() == null || attendanceEntryForm.getClasses().length == 0) {
			errors.add("errors",new ActionError(CMSConstants.ATTENDANCE_CLASS_REQUIRED));
		}	
		try {
			if(errors.isEmpty()) {
				setUserId(request, attendanceEntryForm);
				AttendanceEntryHandler.getInstance().getAttendanceForModify(attendanceEntryForm);
				attendanceEntryForm.setClassMap(CommonAjaxHandler.getInstance().getClassMapForTeacherAndDate(Integer.parseInt(attendanceEntryForm.getUserId()), attendanceEntryForm.getAttendancedate()));
			}
			else {
				attendanceEntryForm.setAttendanceList(null);
				saveErrors(request, errors);
			}
		} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NORECORD));
    		saveErrors(request,errors);
    		attendanceEntryForm.setAttendanceList(null);
    		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NORECORD));
    		saveErrors(request,errors);
    		attendanceEntryForm.setAttendanceList(null);
    		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
		}
	 	return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
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
		//httpSession added by mehaboob
		HttpSession session=request.getSession();
		boolean subjectChange=true;//added by mahi subjectChange
		try {
			if(request.getHeader("User-Agent").indexOf("Mobile") != -1) {
				   attendanceEntryForm.setIsMobileAccess("true");
				  } else {
				    //nope, this is probably a desktop
					   attendanceEntryForm.setIsMobileAccess("false");
				  }
			if(errors.isEmpty()) {
				AttendanceEntryHandler.getInstance().searchStudents(attendanceEntryForm);
				//raghu write for new
				AttendanceEntryHandler.getInstance().getStudentsList3(attendanceEntryForm,session);
				
				setMondatoryFields(attendanceEntryForm);
				initSetDataOnError(attendanceEntryForm,session,subjectChange);
				//return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_SECONDPAGE);
				return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_SECONDPAGE1);
			}
			else {
				initSetDataOnError(attendanceEntryForm,session,subjectChange);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
			}
		} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NOTPRESENT));
	 		initSetDataOnError(attendanceEntryForm,session,subjectChange);
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
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
					//AttendanceEntryHelper.getInstance().sendSMSUpdate(attendanceEntryForm);
					}
				attendanceEntryForm.clear();
				AttendanceEntryHandler.getInstance().getAttendanceForModify(attendanceEntryForm);
				initsetDataToFormCanceMode(attendanceEntryForm);
				ActionMessage message = new ActionMessage(CMSConstants.ATTENDANCE_MODIFY_SUCCESSFULLY);
				messages.add("messages", message);
				saveMessages(request, messages);
				attendanceEntryForm.setSubjectId(null);
				return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
			} else {
				attendanceEntryForm.setSubjectId(null);
				saveErrors(request, errors);
				//return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_SECONDPAGE);
				return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_SECONDPAGE1);
			}
		} catch(DuplicateException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_EXIST));
    		saveErrors(request,errors);
    		//return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_SECONDPAGE);
    		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE_SECONDPAGE1);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_MODIFY_FAILED));
    		saveErrors(request,errors);
		}
		log.info("Leaving into saveAttendance");
		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
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
		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
	}
	 
	/**
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 *         This method will set the data back to form.
	 */
	public void initsetDataToForm(AttendanceEntryForm attendanceEntryForm,HttpServletRequest request) throws Exception{
		
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
	//	Map<Integer,String> teachersMap =UserInfoHandler.getInstance().getTeachingStaff();
	//	attendanceEntryForm.setTeachersMap(teachersMap);
		int rid=Integer.parseInt(request.getSession().getAttribute("rid").toString());
		ITeacherDepartmentEntryTransaction iTeacherDepartmentEntryTransaction=new TeacherDepartmentEntryTransactionImpl();
		List<Integer> list=iTeacherDepartmentEntryTransaction.getUsersId(rid);
		if(list==null || list.isEmpty()){
		//Mary code to concatenate teacher name with department.
		
			Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getTeacherDepartmentsName();
			attendanceEntryForm.setTeachersMap(teachersMap);
		}else{
			// code to list the only logged in users
			Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getTeacherDepartmentsNameNew(list);
			attendanceEntryForm.setTeachersMap(teachersMap);
			
		}
		
		Map<Integer, String> departmentMap=UserInfoHandler.getInstance().getDepartment();
		attendanceEntryForm.setDepartmentMap(departmentMap);
		
		// verifying whether user is staff
		IAttendanceEntryTransaction transaction=AttendanceEntryTransactionImpl.getInstance();
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
		IDownloadEmployeeResumeTransaction transaction1 = DownloadEmployeeResumeTransactionImpl.getInstance();
		List<Department> departmentList = transaction1.getDepartmentList1();
		attendanceEntryForm.setDepartmentList(departmentList);
		
		//raghu
		AcademicyearTransactionImpl impl = new AcademicyearTransactionImpl();
		int curYear = impl.getCurrentAcademicYearforTeacher();
		attendanceEntryForm.setYear(new Integer(curYear).toString());
		
		String teacherName=transaction.getTeacherName(Integer.parseInt(attendanceEntryForm.getUserId()));
		attendanceEntryForm.setEmpName(teacherName);
		
		attendanceEntryForm.setAttendancedate(CommonUtil.getTodayDate());
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
		IAttendanceEntryTransaction transaction=AttendanceEntryTransactionImpl.getInstance();
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
	public void initSetDataOnError(AttendanceEntryForm attendanceEntryForm,HttpSession session,boolean subjectChange) throws Exception {
		
		//commented by mehaboob
		//Map<Integer,String> subjectMap = new LinkedHashMap<Integer,String>();
		 //subjectMap added by mehaboob
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
		   
			//code commented by mehaboob no use of this code
			
			/*List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
			Iterator<ClassSchemewise> itr = classSchemewiseList.iterator();
			ClassSchemewise classSchemewise;
			while(itr.hasNext()) {
				classSchemewise = itr.next();
				if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=null && classSchemewise.getClasses().getCourse().getId()!= 0 && classSchemewise.getClasses().getTermNumber()!=0){
					int year=classSchemewise.getCurriculumSchemeDuration().getAcademicYear();
					int courseId=classSchemewise.getClasses().getCourse().getId();
					int term=classSchemewise.getClasses().getTermNumber();*/
					String[] tempTeacher=attendanceEntryForm.getTeachers();
					Map<String,String> tempMap = CommonAjaxHandler.getInstance().getSubjectByCourseIdTermYearTeacher(Integer.parseInt(tempTeacher[0]),attendanceEntryForm.getClasses(),session,subjectChange);
					subjectMap.putAll(tempMap);
				/*}
			}*/
		   //end	
		  			
			periodMap = CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseId(classesIdsSet);
			
			//code changed by mehaboob added parmeter in method getBatchesBySubjectAndClassIds1 session and String subjectId
			if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
//				batchMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds(Integer.parseInt(attendanceEntryForm.getSubjectId()),classesIdsSet);
				//Session Parameter Added by Mehaboob
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
			classMap = AttendanceEntryHandler.getInstance().getClassesByTeacher(Integer.parseInt(attendanceEntryForm.getUserId()),String.valueOf(currentYear));
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
		if(!attendanceEntryForm.getIsTeacherLogin()) {	//	normal - person with full privilege
			Map<Integer,String> classMap = setupClassMapToRequest(attendanceEntryForm);
			attendanceEntryForm.setClassMap(classMap);
		}
		else {
			Map<Integer,String> classMap = setupClassMapToRequestForATeacherAndDate(attendanceEntryForm);
			attendanceEntryForm.setClassMap(classMap);
		}
	}
	/**
	 * Sets all the classes for the current year in request scope
	 */
	
	public Map<Integer,String> setupClassMapToRequest(AttendanceEntryForm attendanceEntryForm) throws Exception{
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
			e.printStackTrace();
		}
		return classMap;
	}
	/**
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 * 		   This method will load the predefined data in cancel mode.
	 */
	public void setDataToFormErrorMode(AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception{
		//commented by mehaboob subjectMap
		//Map<Integer,String> subjectMap = new LinkedHashMap<Integer,String>();
		//added by mehaboob subjectMap
		Map<String,String> subjectMap = new LinkedHashMap<String,String>();
		//end
		Map<Integer,String> batchMap = new HashMap<Integer,String>();
		Map<Integer,String> periodMap = new LinkedHashMap<Integer,String>();
		Map<Integer,String> classMap;
		// This will load the all attendance type in application.
		if(attendanceEntryForm.getAttendanceTypeId()!=null && attendanceEntryForm.getAttendanceTypeId().length() !=0) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(attendanceEntryForm.getAttendanceTypeId()));
			
			// This will add activities in to map they belong to particular attendancetypeid.
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
			attendanceEntryForm.setActivityMap(activityMap);
		}
		if(attendanceEntryForm.getTimeTableFormat()!=null && attendanceEntryForm.getTimeTableFormat().equalsIgnoreCase("yes")){
			if(attendanceEntryForm.getAttendancedate()!=null && CommonUtil.isValidDate(attendanceEntryForm.getAttendancedate()) &&
					attendanceEntryForm.getTeachers()!=null && attendanceEntryForm.getTeachers().length!=0 &&
					attendanceEntryForm.getYear()!=null && !attendanceEntryForm.getYear().isEmpty()){
				String teacheId="";
				for(String str :attendanceEntryForm.getTeachers()) {
					if(str != null){
						if(!teacheId.isEmpty())
							teacheId=teacheId+","+str;
						else
							teacheId=str;
					}
				}
				String day=CommonUtil.getDayForADate(attendanceEntryForm.getAttendancedate());
				AttendanceTO to = CommonAjaxHandler.getInstance().getPeriodsByTeacher( teacheId, attendanceEntryForm.getYear() ,day,attendanceEntryForm.getAttendancedate());
				attendanceEntryForm.setPeriodMap(to.getPeriodMap());
				if(attendanceEntryForm.getPeriods()!=null && attendanceEntryForm.getPeriods().length!=0){
					String periods="";
					for(String str :attendanceEntryForm.getPeriods()) {
						if(str != null){
							if(!periods.isEmpty())
								periods=periods+","+str;
							else
								periods=str;
						}
					}
					classMap=CommonAjaxHandler.getInstance().getClassesByPeriods(teacheId,attendanceEntryForm.getYear(),periods,day,attendanceEntryForm.getAttendancedate());
					to=CommonAjaxHandler.getInstance().getSubjectsByPeriods(teacheId,attendanceEntryForm.getYear(),periods,day,attendanceEntryForm.getAttendancedate(),to.getPeriodName(),session);
					subjectMap=to.getSubjectMap();
					if(to.getBatchId()!=0){
						batchMap=CommonAjaxHandler.getInstance().getBatchesByPeriods(teacheId,attendanceEntryForm.getYear(),periods,day,attendanceEntryForm.getAttendancedate());
					}else if(to.getBatchList()!=null && !to.getBatchList().isEmpty()){
						batchMap=CommonAjaxHandler.getInstance().getBatchesByPeriods(teacheId,attendanceEntryForm.getYear(),periods,day,attendanceEntryForm.getAttendancedate());
					}
					attendanceEntryForm.setBatchMap(batchMap);
					attendanceEntryForm.setSubjectMap(subjectMap);
					attendanceEntryForm.setClassMap(classMap);
				}
			} 
			
		}else{
			if(attendanceEntryForm.getClasses()!= null && attendanceEntryForm.getClasses().length != 0) {
				Set<Integer> classesIdsSet = new HashSet<Integer>(); 
				for(String str :attendanceEntryForm.getClasses()) {
					if(str != null){
						classesIdsSet.add(Integer.parseInt(str));
					}
				}
			
				//code commentde by mehaboob no use of this code
				
				/*List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
				Iterator<ClassSchemewise> itr = classSchemewiseList.iterator();
				ClassSchemewise classSchemewise;
				while(itr.hasNext()) {
					classSchemewise = itr.next();
					if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=null && classSchemewise.getClasses().getCourse().getId()!= 0 && classSchemewise.getClasses().getTermNumber()!=0){
						int year = classSchemewise.getCurriculumSchemeDuration()
						.getAcademicYear();
				int courseId = classSchemewise.getClasses().getCourse()
						.getId();
				int term = classSchemewise.getClasses().getTermNumber();*/
				String[] tempTeacher=attendanceEntryForm.getTeachers();
				Map<String,String> tempMap = CommonAjaxHandler.getInstance()
						.getSubjectByCourseIdTermYearTeacher(Integer.parseInt(tempTeacher[0]),attendanceEntryForm.getClasses(),session,false);
				subjectMap.putAll(tempMap);
						subjectMap.putAll(tempMap);
				/*	}
				}*/
				
				// Code has Written by Balaji 
				if(classesIdsSet != null && !classesIdsSet.isEmpty()){
					Object selectedClasses[] =classesIdsSet.toArray();
					Set<Integer> temClassIdsSet=new HashSet<Integer>();
					temClassIdsSet.add(Integer.parseInt(selectedClasses[0].toString()));
					periodMap = CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseId(temClassIdsSet);
				}
				//code changed by mehaboob added parmeter in method getBatchesBySubjectAndClassIds1 session and String subjectId
				if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
	//				batchMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds(Integer.parseInt(attendanceEntryForm.getSubjectId()),classesIdsSet);
					batchMap = CommonAjaxHandler.getInstance().getBatchesBySubjectAndClassIds1(attendanceEntryForm.getSubjectId(),classesIdsSet,session);
				}
				if(attendanceEntryForm.getActivityId()!=null && attendanceEntryForm.getActivityId().length()!=0){
					batchMap=CommonAjaxHandler.getInstance().getBatchesByActivityAndClassIds(Integer.parseInt(attendanceEntryForm.getActivityId()), classesIdsSet);
	//				batchMap=CommonAjaxHandler.getInstance().getBatchesByActivityAndClassIds(Integer.parseInt(attendanceEntryForm.getActivityId()), classesIdsSet);
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
				classMap = AttendanceEntryHandler.getInstance().getClassesByTeacher(attendanceEntryForm.getTeachers(),attendanceEntryForm.getYear());
				attendanceEntryForm.setClassMap(classMap);
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
	 * This method will be invoke when the particular link clicked.
	 * 
	 */
	public ActionForward setDataForNumericCode(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.info("Inside of initAttendanceEntry");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		//httpSession added by mehaboob
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
					initsetDataToForm(attendanceEntryForm,request);
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
		
		if(attendanceEntryForm.getIsStaff()){
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
		}else{
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
		}
		
	}
	
	
	
	private void validatePeriodsForClasses(AttendanceEntryForm attendanceEntryForm, ActionMessages errors)  throws Exception{
		if((attendanceEntryForm.getClasses() !=null && attendanceEntryForm.getClasses().length !=0) && (attendanceEntryForm.getPeriods() !=null && attendanceEntryForm.getPeriods().length !=0)) {
			IAttendanceEntryTransaction transaction=AttendanceEntryTransactionImpl.getInstance();
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			String[] selectedClasses=attendanceEntryForm.getClasses();
			for (int i = 0; i < selectedClasses.length; i++) {
				if(selectedClasses[i] != null){
					classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
				}
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
	public ActionForward viewAttendanceSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Inside of modifyAttendanceSearch");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionMessages errors=attendanceEntryForm.validate(mapping, request);
		if(attendanceEntryForm.getClasses() == null || attendanceEntryForm.getClasses().length == 0) {
		errors.add("errors",new ActionError(CMSConstants.ATTENDANCE_CLASS_REQUIRED));
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
		return mapping.findForward(CMSConstants.VIEW_ATTENDANCE);
		} catch (Exception e) {
		log.debug(e.getMessage());
		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NORECORD));
		saveErrors(request,errors);
		attendanceEntryForm.setAttendanceList(null);
		return mapping.findForward(CMSConstants.VIEW_ATTENDANCE);
		}
		return mapping.findForward(CMSConstants.VIEW_ATTENDANCE);
}
	
	
	public ActionForward viewAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Inside of modifyAttendance");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionMessages errors = new ActionErrors();
		//httpSession added by mehaboob
		HttpSession session=request.getSession();
		//added by mahi subjectChange
		boolean subjectChange=false;
		//end
		try {
		if(errors.isEmpty()) {
		AttendanceEntryHandler.getInstance().searchStudents(attendanceEntryForm);
		//raghu write for new
		AttendanceEntryHandler.getInstance().getStudentsList2(attendanceEntryForm,session);
		setMondatoryFields(attendanceEntryForm);
		initSetDataOnError(attendanceEntryForm,session,subjectChange);
		//return mapping.findForward(CMSConstants.VIEW_ATTENDANCE_SECONDPAGE);
		return mapping.findForward(CMSConstants.VIEW_ATTENDANCE_SECONDPAGE1);
		}
		else {
		initSetDataOnError(attendanceEntryForm,session,subjectChange);
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.VIEW_ATTENDANCE);
		}
		} catch(DataNotFoundException e) {
		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NOTPRESENT));
		initSetDataOnError(attendanceEntryForm,session,subjectChange);
		saveErrors(request,errors);
		return mapping.findForward(CMSConstants.VIEW_ATTENDANCE);
		} catch (Exception e) {
		log.debug(e.getMessage());
		throw e;
		}
}
	
	
	public ActionForward initViewAttendanceEntry(ActionMapping mapping,
												 ActionForm form, 
												 HttpServletRequest request,
												 HttpServletResponse response) throws Exception {
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;		
		try {
			setUserId(request, attendanceEntryForm);
		     attendanceEntryForm.clearAll();
		     request.setAttribute("chakka", "chakka");
			 attendanceEntryForm.setIsTeacherLogin(false);
		     setDataToFormCanceMode(attendanceEntryForm);
		     request.setAttribute("attendanceEntryOperation", "load");
		     attendanceEntryForm.setDisplayRequired(CMSConstants.isSmsRequired);
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
		return mapping.findForward(CMSConstants.VIEW_ATTENDANCE);
	}
	
	public ActionForward loadViewAttendancePage(ActionMapping mapping,
												ActionForm form, 
												HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.VIEW_ATTENDANCE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchDeptWise(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//raghu
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		try{
			/*if(attendanceEntryForm.getSearchDept()!=null && !attendanceEntryForm.getSearchDept().isEmpty()){
				Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getFilteredTeacherDepartmentsName(attendanceEntryForm);
				attendanceEntryForm.setTeachersMap(teachersMap);
			}else{
				Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getTeacherDepartmentsName();
				attendanceEntryForm.setTeachersMap(teachersMap);
			}*/
			
		}catch (Exception e) {
			
			if(attendanceEntryForm.getIsStaff()){
				return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
			}else{
				return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
			}
			
		}
		
		if(attendanceEntryForm.getIsStaff()){
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST);	
		}else{
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST1);
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
	public ActionForward newAttendanceEntry(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		try {
			setUserId(request, attendanceEntryForm);
			String[] users = {attendanceEntryForm.getUserId()};
			attendanceEntryForm.setTeachers(users);
			attendanceEntryForm.clearAll();
			initsetDataToNewForm(attendanceEntryForm);
			request.setAttribute("attendanceEntryOperation", "load");
			attendanceEntryForm.setDisplayRequired(CMSConstants.isSmsRequired);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
	}
	/**
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 *         This method will set the data back to form.
	 */
	public void initsetDataToNewForm(AttendanceEntryForm attendanceEntryForm) throws Exception{
		
		IAttendanceTypeTransaction iAttendanceTypeTransaction = AttendanceTypeTransactionImpl.getInstance();
		List<AttendanceType> attendanceTypeList = iAttendanceTypeTransaction.getAttendanceType(); 
		List<AttendanceTypeTO> attendanceTypeToList = AttendanceTypeHelper.getInstance().copyAttendanceTypeBosToTosWithMandatory(attendanceTypeList); 
		attendanceEntryForm.setAttendanceTypeList(attendanceTypeToList);
		// This will load the all attendance type in application.
		AttendanceTypeHandler attendanceTypeHandler = AttendanceTypeHandler.getInstance();
		attendanceTypeHandler.getAttendanceTypeMandatory(attendanceEntryForm,null);
		attendanceEntryForm.setAttendanceTypeId(null);
//		attendanceEntryForm.setAttendancedate(CommonUtil.getTodayDate());
		if(attendanceEntryForm.getAttendanceTypeId()!=null && attendanceEntryForm.getAttendanceTypeId().length() !=0) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(attendanceEntryForm.getAttendanceTypeId()));
			// This will add activities in to map they belong to particular attendancetypeid.
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
			attendanceEntryForm.setActivityMap(activityMap);
			List<ActivityTO> activityList = new ArrayList<ActivityTO>();
			if(!activityMap.isEmpty()){
				Iterator<Entry<Integer, String>> iterator = activityMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) iterator.next();
					ActivityTO to = new ActivityTO();
					to.setName(entry.getValue());
					to.setId(entry.getKey());
					activityList.add(to);
				}
				attendanceEntryForm.setActivityList(activityList);
			}
		}
		
		Map<Integer,String> classMap = setpClassMapToRequest(attendanceEntryForm);
		attendanceEntryForm.setClassMap(classMap);
		List<ClassesTO> classList = new ArrayList<ClassesTO>();
		if(classMap != null){
			Iterator<Entry<Integer, String>> iterator = classMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) iterator.next();
				ClassesTO to = new ClassesTO();
				to.setClassName(entry.getValue());
				to.setId(entry.getKey());
				classList.add(to);
			}
			attendanceEntryForm.setClassList(classList);
		}
		attendanceEntryForm.setHoursHeld("1");
		AttendanceEntryHandler.getInstance().setUserDetailsToForm(attendanceEntryForm);
		attendanceEntryForm.setAttendancedate(CommonUtil.getTodayDate());
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPeriodDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionMessages errors = new ActionMessages();
		attendanceEntryForm.setCurrentPeriodName(null);
		attendanceEntryForm.setPeriods(null);
		//session is added by mehaboob 
		HttpSession session=request.getSession();
		try {
			List<ClassesTO> classList = attendanceEntryForm.getClassList();
			List<Integer> classes = AttendanceEntryHandler.getInstance().getClassesArray(classList,attendanceEntryForm);
			AttendanceEntryHandler.getInstance().getPeriodList(classes,attendanceEntryForm);
			if(attendanceEntryForm.getPeriods() == null || attendanceEntryForm.getPeriods().length == 0){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_NO_PERIOD_DEFINE));
				saveErrors(request, errors);
			}
			if(attendanceEntryForm.getPeriods() != null && attendanceEntryForm.getPeriods().length != 0){
				List<SubjectTO> subjectList = AttendanceEntryHandler.getInstance().getSubjectList(classes,attendanceEntryForm,session);
				attendanceEntryForm.setSubjectList(subjectList);
			}
			Map<Integer, String> batchMap = new HashMap<Integer, String>();
			if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
				batchMap = AttendanceEntryHandler.getInstance().getBatchesBySubjectAndClassIds1(attendanceEntryForm.getSubjectId(),classes,session);
			}
			if(attendanceEntryForm.getActivityId()!=null && attendanceEntryForm.getActivityId().length()!=0){
				batchMap=AttendanceEntryHandler.getInstance().getBatchesByActivityAndClassIds(Integer.parseInt(attendanceEntryForm.getActivityId()), classes);
			}
			attendanceEntryForm.setBatchMap(batchMap);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
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
	@SuppressWarnings("null")
	public ActionForward initAttendanceEntrySecondPageNew(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntrySecondPage");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionMessages errors = new ActionMessages();
		//httpSession added by mehaboob
		HttpSession session=request.getSession();
		try {
			AttendanceEntryHandler.getInstance().setPeriodsToForm(attendanceEntryForm);
			AttendanceEntryHandler.getInstance().setTeachersIdsTOForm(attendanceEntryForm);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			attendanceEntryForm.setYear(String.valueOf(year));
			attendanceEntryForm.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new java.util.Date()), "dd-MMM-yyyy",	"dd/MM/yyyy"));
			errors=attendanceEntryForm.validate(mapping, request);
			if(!attendanceEntryForm.getIsTheoryPractical().equalsIgnoreCase("B")){
				if(attendanceEntryForm.getBatchMap() != null && !attendanceEntryForm.getBatchMap().isEmpty()){
					if(attendanceEntryForm.getBatchIdsArray()==null || attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
								errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_BATCH_REQUIRED));
					}else if(attendanceEntryForm.getBatchIdsArray().length==1){
						for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
							if(batchId==null || batchId.isEmpty()){
								errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_BATCH_REQUIRED));
							}
						}
					}
					/*if(attendanceEntryForm.getBatchId() == null || attendanceEntryForm.getBatchId().trim().isEmpty()){
						errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_BATCH_REQUIRED));
					}*/
				}
			}
			Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) session.getAttribute("SubjectCodeGroupMap");
			if(subjectCodeGroupMap==null || subjectCodeGroupMap.isEmpty()){
				if(attendanceEntryForm.getBatchIdsArray()!=null && attendanceEntryForm.getBatchIdsArray().length>1){
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_SELECT_ONE_BATCH));
				}
			}
			if(errors.isEmpty()) {
				// These below errors are kind of warning messages
				if((attendanceEntryForm.getClasses() ==null || attendanceEntryForm.getClasses().length ==0) && (attendanceEntryForm.getPeriods() ==null || attendanceEntryForm.getPeriods().length ==0)) {
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED1));
				} else if(attendanceEntryForm.getClasses() ==null || attendanceEntryForm.getClasses().length ==0 ) {
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED2));
				} else if(attendanceEntryForm.getPeriods() ==null || attendanceEntryForm.getPeriods().length ==0) {
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED3));
				}
		        if(attendanceEntryForm.getClasses()!=null && attendanceEntryForm.getAttendancedate()!=null){
		        boolean isMinDate=AttendanceEntryHandler.getInstance().checkAttendanceDateMinRange(attendanceEntryForm.getClasses(),attendanceEntryForm.getAttendancedate());
		        if(isMinDate){
		        	errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.attendance.olddate.range"));
		        }
		        }
		        if(errors.isEmpty()){
		        	validatePeriodsForClassesNew(attendanceEntryForm, errors);
		        }
				if(!errors.isEmpty()) {
					setDataToFormErrorMode(attendanceEntryForm,session);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
				}
				
				AttendanceEntryHandler attendanceEntryHandler = AttendanceEntryHandler.getInstance();
				attendanceEntryHandler.duplicateCheck(attendanceEntryForm,request);
				boolean isSecondRequired = attendanceEntryHandler.isSecondPageRequired(attendanceEntryForm);
				attendanceEntryForm.setSecondPage(isSecondRequired);
				//if isSecondRequired is true then show second page else skip.
				if(isSecondRequired) {
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_SECOND);
				} else {
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_INITTHIRD_NEW);
				}	
				
			} else {
				setDataToFormErrorMode(attendanceEntryForm,session);
				attendanceEntryForm.setActivityId(null);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
			}
			
		} catch(DuplicateException e) {
			// will be thrown when  duplicate occurs.
			if(attendanceEntryForm.getBatchIdsArray()!= null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
				for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
					if(batchId!=null && !batchId.isEmpty()){
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_EXIST_BATCH));
						break;
					}
				}
			} else {	
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_EXIST));
			}	
			setDataToFormErrorMode(attendanceEntryForm,session);
//			List<ClassesTO> classList = attendanceEntryForm.getClassList();
//			List<Integer> classes = AttendanceEntryHandler.getInstance().getClassesArray(classList,attendanceEntryForm);
//			List<PeriodTO> periodList = AttendanceEntryHandler.getInstance().getPeriodList(classes,attendanceEntryForm);
//			attendanceEntryForm.setPeriodList(periodList);
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
	 	} catch (Exception e) {
	 		String msg = super.handleApplicationException(e);
	 		attendanceEntryForm.setErrorMessage(msg);
	 		attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}
	/**
	 * @param attendanceEntryForm
	 * @param errors
	 * @throws Exception
	 */
	private void validatePeriodsForClassesNew(AttendanceEntryForm attendanceEntryForm, ActionMessages errors)  throws Exception{
		if((attendanceEntryForm.getClasses() !=null && attendanceEntryForm.getClasses().length !=0) && (attendanceEntryForm.getPeriods() !=null && attendanceEntryForm.getPeriods().length !=0)) {
			IAttendanceEntryTransaction transaction=AttendanceEntryTransactionImpl.getInstance();
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			String[] selectedClasses=attendanceEntryForm.getClasses();
			for (int i = 0; i < selectedClasses.length; i++) {
				if(selectedClasses[i] != null){
					classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
				}
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
				if(periodMap.size()!=classesIdsSet.size()){
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
	public ActionForward initAttendanceEntryThirdPageNew(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntryThirdPage");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		//httpSession added by mehaboob
		HttpSession session=request.getSession();
		try {
			AttendanceEntryHandler.getInstance().setRequiredDataToNextPage(attendanceEntryForm);
//			AttendanceEntryHandler.getInstance().setPeriodsToForm(attendanceEntryForm);
			AttendanceEntryHandler.getInstance().getStudents(attendanceEntryForm,session);
			if (attendanceEntryForm.getStudentList() != null
					&& attendanceEntryForm.getStudentList().isEmpty()) {
				message = new ActionMessage(
						CMSConstants.ATTENDANCE_ENTRY_NORECORD);
				messages.add(CMSConstants.MESSAGES, message);
				addMessages(request, messages);
				setDataToFormErrorMode(attendanceEntryForm,session);
				return	mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			throw e;
			
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_THIRD_NEW);
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
	public ActionForward saveAttendanceNew(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of saveAttendanceNew");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		//httpSession added by mehaboob
		HttpSession session=request.getSession();
		try {
			boolean flag=false;
			flag=AttendanceEntryHandler.getInstance().saveAttendance(attendanceEntryForm,session);
			if(flag){
				if(attendanceEntryForm.getIsSMSRequired().equals("Yes")){
				AttendanceEntryHelper.getInstance().sendSMS(attendanceEntryForm);
				}
				//messages.add("messages",new ActionMessage(CMSConstants.ATTENDANCE_ADDED_SUCCESSFULLY,attendanceEntryForm.getSlipNo()));
				messages.add("messages",new ActionMessage(CMSConstants.ATTENDANCE_ADDED_SUCCESSFULLY));
				saveMessages(request, messages);
			}else{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ADDING_FAILED));
	    		saveErrors(request,errors);
			}
			attendanceEntryForm.clearAll();
			initsetDataToNewForm(attendanceEntryForm);
			saveMessages(request, messages);
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ADDING_FAILED));
    		saveErrors(request,errors);
		}
		log.info("Leaving into saveAttendanceNew");
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMultiTeachersForPractical(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	
		log.info("Inside of getMultiTeachersForPractical");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;	
		ActionErrors errors = new ActionErrors();
		attendanceEntryForm.setTeachersList(null);
		//session is Added by mehaboob
		HttpSession session=request.getSession();
		try {
			List<ClassesTO> classList = attendanceEntryForm.getClassList();
			AttendanceEntryHandler.getInstance().updatePeriodList(attendanceEntryForm);
			List<Integer> classes = AttendanceEntryHandler.getInstance().getClassesArray(classList,attendanceEntryForm);
			if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty() && !attendanceEntryForm.getAttendanceTypeId().equalsIgnoreCase("1")){
				AttendanceEntryHandler.getInstance().getMultiTeachersForPractical(classes,attendanceEntryForm);
			}
			if(classes == null || classes.isEmpty()){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_CLASS_REQUIRED));
	    		saveErrors(request,errors);
			}
			AttendanceEntryHandler.getInstance().setActivityListTOForm(attendanceEntryForm);
			Map<Integer, String> batchMap = new HashMap<Integer, String>();
			if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
				//session parameter added by mehaboob
				batchMap = AttendanceEntryHandler.getInstance().getBatchesBySubjectAndClassIds1(attendanceEntryForm.getSubjectId(),classes,session);
			}
			if(attendanceEntryForm.getActivityId()!=null && attendanceEntryForm.getActivityId().length()!=0){
				batchMap=AttendanceEntryHandler.getInstance().getBatchesByActivityAndClassIds(Integer.parseInt(attendanceEntryForm.getActivityId()), classes);
			}
			attendanceEntryForm.setBatchMap(batchMap);
			setMondatoryFields(attendanceEntryForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getMultiTeachersForPractical");
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttendanceEntryByTimeTableForEmployeeLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Inside of initAttendanceEntryByTimeTableForEmployeeLogin");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionErrors errors=new ActionErrors();
		HttpSession session=request.getSession();
		try {
			setUserId(request, attendanceEntryForm);
			attendanceEntryForm.clearAll();
			request.setAttribute("attendanceEntryOperation", "load");
			attendanceEntryForm.setDisplayRequired(CMSConstants.isSmsRequired);
			attendanceEntryForm.setAttendancedate(CommonUtil.getTodayDate());
			String[] teachers={attendanceEntryForm.getUserId()};
			attendanceEntryForm.setTeachers(teachers);
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}// end
			attendanceEntryForm.setYear(Integer.toString(currentYear));
			attendanceEntryForm.setAcaYear(Integer.toString(currentYear));
			attendanceEntryForm.setHoursHeld("1");
			setDataToFormByTimeTable(attendanceEntryForm,session,errors);
			if(!errors.isEmpty()){
				saveErrors(request, errors);
			}
		}  catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initAttendanceEntryByTimeTableForEmployeeLogin");
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_BY_TIMETABLE);
	}

	/**
	 * @param attendanceEntryForm
	 * @throws Exception
	 */
	private void setDataToFormByTimeTable( AttendanceEntryForm attendanceEntryForm,HttpSession session,ActionErrors errors) throws Exception{
		AttendanceTypeHandler attendanceTypeHandler = AttendanceTypeHandler.getInstance();
		attendanceTypeHandler.getAttendanceTypeMandatory(attendanceEntryForm,null);
		String teacheId="";
		for(String str :attendanceEntryForm.getTeachers()) {
			if(str != null){
				if(!teacheId.isEmpty())
					teacheId=","+str;
				else
					teacheId=str;
			}
		}
		String day=CommonUtil.getDayForADate(attendanceEntryForm.getAttendancedate());
		AttendanceTO to= CommonAjaxHandler.getInstance().getPeriodsByTeacher( teacheId, attendanceEntryForm.getYear() ,day,attendanceEntryForm.getAttendancedate());
		attendanceEntryForm.setPeriodMap(to.getPeriodMap());
		Map<Integer,String> periodMap=to.getPeriodMap();
		if(to.getPeriodId()>0){
				attendanceEntryForm.setTimeTableAvailable(true);
				attendanceEntryForm.setAttendancePeriod(periodMap.get(to.getPeriodId()));
				attendanceEntryForm.setAdditionalUserMap(to.getAdditionalUserMap());
				String[] period={String.valueOf(to.getPeriodId())};
				attendanceEntryForm.setPeriods(period);
				String periods=String.valueOf(to.getPeriodId());
				
				Map<Integer,String> classMap=CommonAjaxHandler.getInstance().getClassesByPeriods(teacheId,attendanceEntryForm.getYear(),periods,day,attendanceEntryForm.getAttendancedate());
				to=CommonAjaxHandler.getInstance().getSubjectsByPeriods(teacheId,attendanceEntryForm.getYear(),periods,day,attendanceEntryForm.getAttendancedate(),to.getPeriodName(),session);
				if(!to.getError()){
					if(to.getSubjectBatchMap()!=null && !to.getSubjectBatchMap().isEmpty()){
						attendanceEntryForm.setSubjectBatchMap(to.getSubjectBatchMap());
					}
					if(to.getBatchId()!=0){
						Map<Integer,String>	batchMap=CommonAjaxHandler.getInstance().getBatchesByPeriods(teacheId,attendanceEntryForm.getYear(),periods,day,attendanceEntryForm.getAttendancedate());
						attendanceEntryForm.setBatchMap(batchMap);
						attendanceEntryForm.setBatchId(String.valueOf(to.getBatchId()));
						attendanceEntryForm.setAttendanceBatchName(batchMap.get(to.getBatchId()));
						
						}//added by mehaboob start
						else if(to.getBatchList()!=null && !to.getBatchList().isEmpty()){
							Map<Integer,String>	batchMap=CommonAjaxHandler.getInstance().getBatchesByPeriods(teacheId,attendanceEntryForm.getYear(),periods,day,attendanceEntryForm.getAttendancedate());
							attendanceEntryForm.setBatchMap(batchMap);
							//attendanceEntryForm.setBatchId(String.valueOf(to.getBatchId()));
							//attendanceEntryForm.setAttendanceBatchName(batchMap.get(to.getBatchId()));
							attendanceEntryForm.setBatchList(to.getBatchList());
							String batchName="";
							for (Integer batchId : to.getBatchList()) {
								for (Entry<Integer, Integer> subbatchMap : to.getSubjectBatchMap().entrySet()) {
									if(batchId.equals(subbatchMap.getValue())){
										//int subjectId=subbatchMap.getKey();
										//String subjectCode=AttendanceEntryHandler.getInstance().getsubjectCodeBySubjectId(subjectId);
										//if(subjectCode!=null && !subjectCode.isEmpty()){
											//batchName=batchName+", "+batchMap.get(batchId)+"("+subjectCode+")";
										//}else{
											batchName=batchName+", "+batchMap.get(batchId);
										//}
									}
								}
							}
							batchName=batchName.substring(1);
							attendanceEntryForm.setAttendanceBatchName(batchName);
						}//end
						attendanceEntryForm.setSubjectMap(to.getSubjectMap());
						attendanceEntryForm.setClassMap(classMap);
						// Setting Names
						String classId=to.getClassId();
						if(!classId.isEmpty()){
							String[] classes=classId.split(",");
							attendanceEntryForm.setClasses(classes);
							String className="";
							for (int i = 0; i < classes.length; i++) {
								 if(className.isEmpty()){
									 className=classMap.get(Integer.parseInt(classes[i]));
								 }else
									 className=className+","+classMap.get(Integer.parseInt(classes[i]));
							}
							
							attendanceEntryForm.setAttendanceClass(className);
						}
						Map<String,String> subjectMap=to.getSubjectMap();
						if(to.getSubjectId()!=null && !to.getSubjectId().isEmpty()){
							attendanceEntryForm.setSubjectId(to.getSubjectId());
							
							attendanceEntryForm.setAttendanceSubject(subjectMap.get(to.getSubjectId()));
						}
						if(to.getAttendanceTypeId()>0){
							attendanceEntryForm.setAttendanceTypeId(String.valueOf(to.getAttendanceTypeId()));
							attendanceEntryForm.setAttenType(attendanceEntryForm.getAttendanceTypes().get(to.getAttendanceTypeId()));
							//added by mahi
							if(attendanceEntryForm.getAttendanceTypes().get(to.getAttendanceTypeId()).trim().equalsIgnoreCase("Extra Curricular")){
								attendanceEntryForm.setSubjectMandatory(CMSConstants.NO);
							}
							//end
						}
						// This will load the all attendance type in application.
						if(attendanceEntryForm.getAttendanceTypeId()!=null && attendanceEntryForm.getAttendanceTypeId().length() !=0) {
							Set<Integer> set = new HashSet<Integer>();
							set.add(Integer.valueOf(attendanceEntryForm.getAttendanceTypeId()));
							
							// This will add activities in to map they belong to particular attendancetypeid.
							Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
							attendanceEntryForm.setActivityMap(activityMap);
							if(to.getActivityId()>0){
								attendanceEntryForm.setActivityId(String.valueOf(to.getActivityId()));
								attendanceEntryForm.setAttendanceActivity(activityMap.get(to.getActivityId()));
							}
						}
						attendanceEntryForm.setAdditionalPeriods(to.getAdditionalPeriods());
						AttendanceEntryHandler.getInstance().setUserDetailsToForm(attendanceEntryForm);
				}else{
					errors.add("error", new ActionError("knowledgepro.time.table.setting.errors"));
				}
				
		}else{
			attendanceEntryForm.setTimeTableAvailable(false);
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
	public ActionForward initAttendanceEntrySecondPageByTimeTable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Inside of initAttendanceEntrySecondPage");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionMessages errors = attendanceEntryForm.validate(mapping, request);
		attendanceEntryForm.setOrginalPeriod(attendanceEntryForm.getPeriods());
		attendanceEntryForm.setOrginalTeacher(attendanceEntryForm.getTeachers());
		attendanceEntryForm.setAdditionalPeriodIds(request.getParameterValues("additionalPeriodIds"));
		attendanceEntryForm.setAdditionalUserIds(request.getParameterValues("additionalUserIds"));
		if(attendanceEntryForm.getAdditionalUserIds()!=null){
			String[] userId=(String[])ArrayUtils.addAll(attendanceEntryForm.getTeachers(),attendanceEntryForm.getAdditionalUserIds());
			attendanceEntryForm.setTeachers(userId);
		}
		if(attendanceEntryForm.getAdditionalPeriodIds()!=null){
			String[] periodId=(String[])ArrayUtils.addAll(attendanceEntryForm.getPeriods(),attendanceEntryForm.getAdditionalPeriodIds());
			attendanceEntryForm.setPeriods(periodId);
		}
		try {
			if (errors.isEmpty()) {
				// These below errors are kind of warning messages
				if ((attendanceEntryForm.getClasses() == null || attendanceEntryForm
						.getClasses().length == 0)
						&& (attendanceEntryForm.getPeriods() == null || attendanceEntryForm
								.getPeriods().length == 0)) {
					errors.add(CMSConstants.ERRORS, new ActionError(
							CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED1));
				} else if (attendanceEntryForm.getClasses() == null
						|| attendanceEntryForm.getClasses().length == 0) {
					errors.add(CMSConstants.ERRORS, new ActionError(
							CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED2));
				} else if (attendanceEntryForm.getPeriods() == null
						|| attendanceEntryForm.getPeriods().length == 0) {
					errors.add(CMSConstants.ERRORS, new ActionError(
							CMSConstants.ATTENDANCE_ENTRY_UNABLETOPROCEED3));
				}
				if (attendanceEntryForm.getClasses() != null
						&& attendanceEntryForm.getAttendancedate() != null) {
					boolean isMinDate = AttendanceEntryHandler.getInstance()
							.checkAttendanceDateMinRange(
									attendanceEntryForm.getClasses(),
									attendanceEntryForm.getAttendancedate());
					if (isMinDate) {
						errors.add(CMSConstants.ERRORS, new ActionError(
								"knowledgepro.attendance.olddate.range"));
					}
				}
				if (errors.isEmpty()) {
					// validateClassesAndPeriod(attendanceEntryForm,errors);
					validatePeriodsForClasses(attendanceEntryForm, errors);
				}
				if (!errors.isEmpty()) {
//					setDataToFormErrorMode(attendanceEntryForm);
					saveErrors(request, errors);
					return mapping
							.findForward(CMSConstants.ATTENDANCE_ENTRY_BY_TIMETABLE);
				}

				AttendanceEntryHandler attendanceEntryHandler = AttendanceEntryHandler
						.getInstance();
				attendanceEntryHandler.duplicateCheck(attendanceEntryForm,request);
				boolean isSecondRequired = attendanceEntryHandler
						.isSecondPageRequired(attendanceEntryForm);
				attendanceEntryForm.setSecondPage(isSecondRequired);
				// if isSecondRequired is true then show second page else skip.
				if (isSecondRequired) {
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_SECOND);
				} else {
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_INITTHIRD_FOR_TIMETABLE);
				}

			} else {
//				setDataToFormErrorMode(attendanceEntryForm);
				saveErrors(request, errors);
				attendanceEntryForm.setTeachers(attendanceEntryForm.getOrginalTeacher());
				attendanceEntryForm.setPeriods(attendanceEntryForm.getOrginalPeriod());
				attendanceEntryForm.setAdditionalPeriodIds(request.getParameterValues("additionalPeriodIds"));
				attendanceEntryForm.setAdditionalUserIds(request.getParameterValues("additionalUserIds"));
				return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_BY_TIMETABLE);
			}

		} catch (DuplicateException e) {
			// will be thrown when duplicate occurs.
			if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
				errors.add(CMSConstants.ERRORS, new ActionError(
						CMSConstants.ATTENDANCE_ENTRY_EXIST_BATCH));
			}else if (attendanceEntryForm.getBatchIdsArray() != null
					&& !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()) {
				for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
					if(batchId!=null && !batchId.isEmpty()){
						errors.add(CMSConstants.ERRORS, new ActionError(
								CMSConstants.ATTENDANCE_ENTRY_EXIST_BATCH));
						
						break;
					}
				}
			} else {
				errors.add(CMSConstants.ERRORS, new ActionError(
						CMSConstants.ATTENDANCE_ENTRY_EXIST));
			}
//			setDataToFormErrorMode(attendanceEntryForm);
			saveErrors(request, errors);
			attendanceEntryForm.setTeachers(attendanceEntryForm.getOrginalTeacher());
			attendanceEntryForm.setPeriods(attendanceEntryForm.getOrginalPeriod());
			attendanceEntryForm.setAdditionalPeriodIds(request.getParameterValues("additionalPeriodIds"));
			attendanceEntryForm.setAdditionalUserIds(request.getParameterValues("additionalUserIds"));
			return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_BY_TIMETABLE);
		} catch (Exception e) {
			log.debug(e.getMessage());
			throw e;
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
	public ActionForward initAttendanceEntryThirdPageForTimeTable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Inside of initAttendanceEntryThirdPage");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		//httpSession added by mehaboob
        HttpSession session=request.getSession();
		try {
			AttendanceEntryHandler.getInstance().getStudents( attendanceEntryForm,session);
			if (attendanceEntryForm.getStudentList() != null
					&& attendanceEntryForm.getStudentList().isEmpty()) {
				message = new ActionMessage( CMSConstants.ATTENDANCE_ENTRY_NORECORD);
				messages.add(CMSConstants.MESSAGES, message);
				addMessages(request, messages);
//				setDataToFormErrorMode(attendanceEntryForm);
				return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_BY_TIMETABLE);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			throw e;

		}
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_THIRD_BY_TIMETABLE);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAttendanceByTimeTable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Inside of saveAttendanceNew");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		HttpSession session=request.getSession();
		try {
			boolean flag = false;
			attendanceEntryForm.setTimeTableFormat("yes");
			flag = AttendanceEntryHandler.getInstance().saveAttendance(attendanceEntryForm,session);
			if (flag) {
				if (attendanceEntryForm.getIsSMSRequired().equals("Yes")) {
					AttendanceEntryHelper.getInstance().sendSMS(
							attendanceEntryForm);
				}
				//messages.add("messages",new ActionMessage(CMSConstants.ATTENDANCE_ADDED_SUCCESSFULLY,attendanceEntryForm.getSlipNo()));
				messages.add("messages",new ActionMessage(CMSConstants.ATTENDANCE_ADDED_SUCCESSFULLY));
				saveMessages(request, messages);
			} else {
				errors.add(CMSConstants.ERRORS, new ActionError(
						CMSConstants.ATTENDANCE_ADDING_FAILED));
				saveErrors(request, errors);
			}
			attendanceEntryForm.clearAll();
			attendanceEntryForm.setTimeTableFormat("no");
			request.setAttribute("attendanceEntryOperation", "load");
			attendanceEntryForm.setDisplayRequired(CMSConstants.isSmsRequired);
			attendanceEntryForm.setAttendancedate(CommonUtil.getTodayDate());
			String[] teachers={attendanceEntryForm.getUserId()};
			attendanceEntryForm.setTeachers(teachers);
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}// end
			attendanceEntryForm.setYear(Integer.toString(currentYear));
			attendanceEntryForm.setAcaYear(Integer.toString(currentYear));
			attendanceEntryForm.setHoursHeld("1");
			setDataToFormByTimeTable(attendanceEntryForm,session,errors);
			if(!errors.isEmpty()){
				saveErrors(request, errors);
			}else{
				saveMessages(request, messages);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ADDING_FAILED));
			saveErrors(request, errors);
		}
		log.info("Leaving into saveAttendanceNew");
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_BY_TIMETABLE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getActivityType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	
		log.info("Inside of getMultiTeachersForPractical");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		//Session Added By mehaboob
		HttpSession session=request.getSession();
		try {
			List<ClassesTO> classList = attendanceEntryForm.getClassList();
			List<Integer> classes = AttendanceEntryHandler.getInstance().getClassesArray(classList,attendanceEntryForm);
			AttendanceEntryHandler.getInstance().setActivityListTOForm(attendanceEntryForm);
			Map<Integer, String> batchMap = new HashMap<Integer, String>();
			if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
				//session parameter added by mehaboob
				batchMap = AttendanceEntryHandler.getInstance().getBatchesBySubjectAndClassIds1(attendanceEntryForm.getSubjectId(),classes,session);
			}
			if(attendanceEntryForm.getActivityId()!=null && attendanceEntryForm.getActivityId().length()!=0){
				batchMap=AttendanceEntryHandler.getInstance().getBatchesByActivityAndClassIds(Integer.parseInt(attendanceEntryForm.getActivityId()), classes);
			}
			attendanceEntryForm.setBatchMap(batchMap);
			setMondatoryFields(attendanceEntryForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getMultiTeachersForPractical");
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAditionalPeriods(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	
		log.info("Inside of getAditionalPeriods");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		HttpSession session=request.getSession();
		try {
			List<ClassesTO> classList = attendanceEntryForm.getClassList();
			List<Integer> classes = AttendanceEntryHandler.getInstance().getClassesArray(classList,attendanceEntryForm);
			if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
//				AttendanceEntryHandler.getInstance().getMultiTeachersForPractical(classes,attendanceEntryForm);
				String[] users = {attendanceEntryForm.getUserId()};
				attendanceEntryForm.setTeachers(users);
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				attendanceEntryForm.setYear(String.valueOf(year));
				attendanceEntryForm.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new java.util.Date()), "dd-MMM-yyyy",	"dd/MM/yyyy"));
				AttendanceEntryHandler.getInstance().getAditionalPeriods(classes,attendanceEntryForm,session);
			}
			Map<Integer, String> batchMap = new HashMap<Integer, String>();
			if(attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()){
				//Session Parameter added by mehaboob
				batchMap = AttendanceEntryHandler.getInstance().getBatchesBySubjectAndClassIds1(attendanceEntryForm.getSubjectId(),classes,session);
			}
			if(attendanceEntryForm.getActivityId()!=null && attendanceEntryForm.getActivityId().length()!=0){
				batchMap=AttendanceEntryHandler.getInstance().getBatchesByActivityAndClassIds(Integer.parseInt(attendanceEntryForm.getActivityId()), classes);
			}
			attendanceEntryForm.setBatchMap(batchMap);
			setMondatoryFields(attendanceEntryForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getAditionalPeriods");
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_FIRST_NEW);
	}
	
	// vinodha - time restriction for particular classes
	
	public ActionForward initAttendanceTimeRestriction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered into initTimeTable");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		attendanceEntryForm.clear();
		setUserId(request, attendanceEntryForm);
		attendanceEntryForm.setEvenOrOdd("odd");
		List<AttendanceTO> toList =  AttendanceEntryHandler.getInstance().getAttendanceTimeRestrictedList(attendanceEntryForm);
		attendanceEntryForm.setAttTimeRestrictedList(toList);
		setRequiredDatatoForm(attendanceEntryForm);
		log.info("Exit from initTimeTable");
		
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_RESTRICTION_CLASS);
	}

	/**
	 * @param timeTableForClassForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm( AttendanceEntryForm attendanceEntryForm) throws Exception {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int year = CurrentAcademicYear.getInstance().getAcademicyear();
		if (year != 0) {
			currentYear = year;
		}
		if(attendanceEntryForm.getYear()!=null && !attendanceEntryForm.getYear().isEmpty())
			currentYear=Integer.parseInt(attendanceEntryForm.getYear());
		
		Map<Integer, String> classMap=CommonAjaxHandler.getInstance().getClassesForAttendanceByYear(currentYear, attendanceEntryForm.getEvenOrOdd());
		attendanceEntryForm.setClassMap(classMap);
	}
	
	public ActionForward saveAttendanceTimeRestrictionClasswise(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into saveAttendanceTimeRestrictionClasswise");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionMessages errors=attendanceEntryForm.validate1(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		// validate individual time components
		int timeHours = 0;
		int timeMins = 0;
		if (attendanceEntryForm.getTimeHours() != null
				&& attendanceEntryForm.getTimeHours().trim().length() > 0) {
			timeHours = Integer.parseInt(attendanceEntryForm.getTimeHours());

		} else {
			errors.add("error", new ActionError(
					"knowledgepro.attendance.timeRestriction.emptyHours"));
		}

		if (attendanceEntryForm.getTimeMins() != null
				&& attendanceEntryForm.getTimeMins().trim().length() > 0) {
			timeMins = Integer.parseInt(attendanceEntryForm.getTimeMins());

		} else {
			errors.add("error", new ActionError(
					"knowledgepro.attendance.timeRestriction.emptyMins"));			
		}
		if (timeHours > 24) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamTimeTable.Hour"));
		}

		if (timeMins > 60) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamTimeTable.Min"));
		}		
		saveErrors(request, errors);
		if(errors.isEmpty()){
			try{
				boolean saved = AttendanceEntryHandler.getInstance().saveAttendanceTimeRestriction(attendanceEntryForm);
				if(saved){
					ActionMessage message = new ActionMessage("knowledgepro.attendance.timeRestriction.addsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					attendanceEntryForm.setEvenOrOdd("odd");
					setRequiredDatatoForm(attendanceEntryForm);
				
				}else{
					errors.add("error", new ActionError("knowledgepro.attendance.timeRestriction.addfailure"));
					saveErrors(request, errors);
				}
			}catch(Exception e){
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.attendance.timeRestriction.exists"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_ATTENDANCE_RESTRICTION_CLASS);	
				}else{
					log.error("Error occured in caste Entry Action", e);
					String msg = super.handleApplicationException(e);
					attendanceEntryForm.setErrorMessage(msg);
					attendanceEntryForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}				
			}			
			log.info("Exit from saveAttendanceTimeRestrictionClasswise");
		}
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_RESTRICTION_CLASS);
	}
	public ActionForward editAttendanceTimeRestrictionClasswise(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		List<AttendanceTO> toList =  AttendanceEntryHandler.getInstance().getAttendanceTimeRestrictedList(attendanceEntryForm);
		attendanceEntryForm.setAttTimeRestrictedList(toList);							
		setRequiredDatatoForm(attendanceEntryForm);
		request.setAttribute("operation", "edit");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_RESTRICTION_CLASS);
	}
	
	public ActionForward updateAttendanceTimeRestrictionClasswise(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into updateAttendanceTimeRestrictionClasswise");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();		
		boolean isUpdated = false;
		List<AttendanceTO> toList =  AttendanceEntryHandler.getInstance().getAttendanceTimeRestrictedList(attendanceEntryForm);
		attendanceEntryForm.setAttTimeRestrictedList(toList);							
		
		if(attendanceEntryForm.getAttTimeRestrictionId()!=0){
			isUpdated = AttendanceEntryHandler.getInstance().updateAttendanceTimeRestriction(attendanceEntryForm);
		}
		if (isUpdated) {
			// successfully deleted.
			ActionMessage message = new ActionMessage("knowledgepro.attendance.timeRestriction.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		} else {
			// failed to update.
			errors.add("error", new ActionError("knowledgepro.attendance.timeRestriction.updatefailure"));
			saveErrors(request, errors);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.INIT_ATTENDANCE_RESTRICTION_CLASS);
		}
		request.setAttribute("operation", "submit");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_RESTRICTION_CLASS);
	}
	public ActionForward deleteAttendanceTimeRestrictionClasswise(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into deleteAttendanceTimeRestrictionClasswise");
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();		
		boolean isdeleted = false;
		List<AttendanceTO> toList =  AttendanceEntryHandler.getInstance().getAttendanceTimeRestrictedList(attendanceEntryForm);
		attendanceEntryForm.setAttTimeRestrictedList(toList);							
		
		if(attendanceEntryForm.getAttTimeRestrictionId()!=0){
			isdeleted = AttendanceEntryHandler.getInstance().deleteAttendanceTimeRestriction(attendanceEntryForm);
		}
		if (isdeleted) {
			// successfully deleted.
			ActionMessage message = new ActionMessage("knowledgepro.attendance.timeRestriction.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		} else {
			// failed to update.
			errors.add("error", new ActionError("knowledgepro.attendance.timeRestriction.deletefailure"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_ATTENDANCE_RESTRICTION_CLASS);
		}
		request.setAttribute("operation", "submit");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_RESTRICTION_CLASS);
	}
	
	public ActionForward initModifyAttendanceEntryForTeachers(ActionMapping mapping,
															  ActionForm form, 
															  HttpServletRequest request,
															  HttpServletResponse response) throws Exception {
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;		
		try {
			setUserId(request, attendanceEntryForm);
			attendanceEntryForm.clearAll();
			attendanceEntryForm.setIsTeacherLogin(true);
			setDataToFormCanceMode(attendanceEntryForm);
			request.setAttribute("attendanceEntryOperation", "load");
			attendanceEntryForm.setDisplayRequired(CMSConstants.isSmsRequired);
		} catch (ApplicationException e) {
			e.printStackTrace();
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = super.handleApplicationException(e);
			attendanceEntryForm.setErrorMessage(msg);
			attendanceEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.MODIFY_ATTENDANCE);
	}

	public Map<Integer,String> setupClassMapToRequestForATeacherAndDate(AttendanceEntryForm attendanceEntryForm) throws Exception{
		return CommonAjaxHandler.getInstance().getClassMapForTeacherAndDate(Integer.parseInt(attendanceEntryForm.getUserId()), attendanceEntryForm.getAttendancedate());
	}
	
	public ActionForward initViewAttendanceEntryForTeachers(ActionMapping mapping,
			 												ActionForm form, 
			 												HttpServletRequest request,
			 												HttpServletResponse response) throws Exception {
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;		
		try {
			setUserId(request, attendanceEntryForm);
			attendanceEntryForm.clearAll();
			attendanceEntryForm.setIsTeacherLogin(true);
			request.setAttribute("chakka", "chakka");
			setDataToFormCanceMode(attendanceEntryForm);
			request.setAttribute("attendanceEntryOperation", "load");
			attendanceEntryForm.setDisplayRequired(CMSConstants.isSmsRequired);
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
		return mapping.findForward(CMSConstants.VIEW_ATTENDANCE);
	}
}