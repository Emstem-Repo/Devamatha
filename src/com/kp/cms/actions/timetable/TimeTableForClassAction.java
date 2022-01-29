package com.kp.cms.actions.timetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.timetable.TimeTableForClassForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.timetable.TimeTableForClassHandler;
import com.kp.cms.to.timetable.TTSubjectBatchForCopyTo;
import com.kp.cms.to.timetable.TTSubjectBatchTo;
import com.kp.cms.to.timetable.TimeTableClassTo;
import com.kp.cms.to.timetable.TimeTablePeriodTo;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.timetable.TimeTableForClassTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

public class TimeTableForClassAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(TimeTableForClassAction.class);

	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTimeTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into initTimeTable");
		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		setUserId(request, timeTableForClassForm);
		setRoleId(request, timeTableForClassForm);
		timeTableForClassForm.resetFormFields();
		timeTableForClassForm.resetFields();
		timeTableForClassForm.setEvenOrOdd("odd");
		setRequiredDatatoForm(timeTableForClassForm);
		timeTableForClassForm.setToggler("f");
		log.info("Exit from initTimeTable");

		return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
	}

	private void setRoleId(HttpServletRequest request, TimeTableForClassForm timeTableForClassForm) {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("rid")!=null){
			String roleId=(session.getAttribute("rid").toString());
			timeTableForClassForm.setRoleId(roleId);
		}
		
	}

	/**
	 * @param timeTableForClassForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm( TimeTableForClassForm timeTableForClassForm) throws Exception {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		String roleId=timeTableForClassForm.getRoleId();
		int year = CurrentAcademicYear.getInstance().getAcademicyear();
		if (year != 0) {
			currentYear = year;
		}// end
		if(timeTableForClassForm.getYear()!=null && !timeTableForClassForm.getYear().isEmpty())
			currentYear=Integer.parseInt(timeTableForClassForm.getYear());
		String userId=timeTableForClassForm.getUserId();
		//Map<Integer, String> classMap=CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
		Map<Integer, String> classMap=null;
		if (CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(Integer.parseInt(timeTableForClassForm.getRoleId()))) {
			classMap=TimeTableForClassTransactionImpl.getInstance().getClassesForAttendanceByYearAndUserId(currentYear, timeTableForClassForm.getEvenOrOdd(),timeTableForClassForm.getUserId());
		}
		else{
		classMap=CommonAjaxHandler.getInstance().getClassesForAttendanceByYear(currentYear, timeTableForClassForm.getEvenOrOdd());
		}
		Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByyearandTerm(String.valueOf(currentYear), "odd",userId,roleId);

		timeTableForClassForm.setClassMap(classMap);
		timeTableForClassForm.setSubjectMap(courseMap);


		//raghu
		// verifying whether user is staff
		IAttendanceEntryTransaction transaction=AttendanceEntryTransactionImpl.getInstance();
		boolean isStaff=transaction.checkIsStaff(timeTableForClassForm.getUserId());
		if(isStaff){
			String[] selectedTeachers = {timeTableForClassForm.getUserId()};
			//timeTableForClassForm.setTeachers(selectedTeachers);
			timeTableForClassForm.setIsStaff(true);

		}else{
			timeTableForClassForm.setIsStaff(false);

		}

		String teacherName=transaction.getTeacherName(Integer.parseInt(timeTableForClassForm.getUserId()));
		timeTableForClassForm.setEmpName(teacherName);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPeriods(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered TimeTableForClassAction - getPeriods");

		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		ActionErrors errors=new ActionErrors();
		
			try {
				timeTableForClassForm.resetFields();
				timeTableForClassForm.setMsg(null);
				if (timeTableForClassForm.getClsOrcrs()=="classWise" || timeTableForClassForm.getClsOrcrs().equalsIgnoreCase("classWise")) {
					errors = timeTableForClassForm.validate(mapping, request);
					if (errors.isEmpty()) {
					List<TimeTableClassTo> classTos=TimeTableForClassHandler.getInstance().getTimeTableForInputClass(timeTableForClassForm);
					if(classTos==null || classTos.isEmpty()){
						if(timeTableForClassForm.getMsg()!=null && !timeTableForClassForm.getMsg().isEmpty()){
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message",timeTableForClassForm.getMsg()));
						}else
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);

						setRequiredDatatoForm(timeTableForClassForm);
						log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
						return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
					}
					timeTableForClassForm.setToggler("f");
					timeTableForClassForm.setClassTos(classTos);
					timeTableForClassForm.setTtClassHistoryExists(TimeTableForClassHandler.getInstance().checkTimeTableClassHistory(timeTableForClassForm.getClassId()));
					timeTableForClassForm.setClassName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(timeTableForClassForm.getClassId()),"ClassSchemewise",false,"classes.name"));
				}
					else {
						addErrors(request, errors);
						setRequiredDatatoForm(timeTableForClassForm);	
						timeTableForClassForm.setToggler("f");
						log.info("Exit TimeTableForClassAction - getPeriods errors not empty ");
					}
				}
				
				
				if (timeTableForClassForm.getClsOrcrs()=="courseWise" || timeTableForClassForm.getClsOrcrs().equalsIgnoreCase("courseWise")) {
					String clsOrCrs=null;
					if (timeTableForClassForm.getSubjectId()==null || timeTableForClassForm.getSubjectId().isEmpty()) {
						errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Course required"));
						saveErrors(request, errors);
					}
					
					if (errors.isEmpty()) {
						Calendar calendar = Calendar.getInstance();
						int currentYear = calendar.get(Calendar.YEAR);

						int year = CurrentAcademicYear.getInstance().getAcademicyear();
						if (year != 0) {
							currentYear = year;
						}// end
						if(timeTableForClassForm.getYear()!=null && !timeTableForClassForm.getYear().isEmpty())
							currentYear=Integer.parseInt(timeTableForClassForm.getYear());
						Map<Integer, String> classMap=null;
						if (CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(Integer.parseInt(timeTableForClassForm.getRoleId()))) {
							classMap=TimeTableForClassTransactionImpl.getInstance().getClassesForAttendanceByYearAndUserIdAndSubjectId(currentYear, timeTableForClassForm.getEvenOrOdd(),timeTableForClassForm.getUserId(),timeTableForClassForm.getSubjectId());
						}
						else{
						classMap=CommonAjaxHandler.getInstance().getClassesForAttendanceByYear(currentYear, timeTableForClassForm.getEvenOrOdd());
						}
						timeTableForClassForm.setClassMap(classMap);
						Map<Integer, String> teachersMap=TimeTableForClassHandler.getInstance().getTeachersMap(timeTableForClassForm.getSubjectId());
						timeTableForClassForm.setTeachersMap(teachersMap);
						timeTableForClassForm.setToggler("t");
					}
					clsOrCrs=timeTableForClassForm.getClsOrcrs();
					timeTableForClassForm.setClsOrcrs(clsOrCrs);
					System.out.println(timeTableForClassForm.getSubjectId());
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
				}
				
			}catch (Exception exception) {
				exception.printStackTrace();
				String msg = super.handleApplicationException(exception);
				timeTableForClassForm.setErrorMessage(msg);
				timeTableForClassForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		
		log.info("Entered TimeTableForClassAction - getPeriods");
		timeTableForClassForm.setFlag(false);
		return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPeriodDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered TimeTableForClassAction - getPeriods");

		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		HttpSession session = request.getSession(false);
		List<TimeTableClassTo> classTos=timeTableForClassForm.getClassTos();
		if(timeTableForClassForm.getCount()>0 && timeTableForClassForm.getPosition()>0){
			Iterator<TimeTableClassTo> itr=classTos.iterator();
			while (itr.hasNext()) {
				TimeTableClassTo to= itr.next();
				if(to.getPosition()==timeTableForClassForm.getPosition()){
					List<TimeTablePeriodTo> timeTablePeriodTos=to.getTimeTablePeriodTos();
					Iterator<TimeTablePeriodTo> perioditr=timeTablePeriodTos.iterator();
					while (perioditr.hasNext()) {
						TimeTablePeriodTo periodTo =  perioditr .next();

						if(periodTo.getCount()==timeTableForClassForm.getCount()){
							timeTableForClassForm.setPeriodName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(periodTo.getPeriodId(),"Period",true,"periodName"));
							Map<Integer,Map<Integer, String>> batchMap=timeTableForClassForm.getBatchMap();
							Map<Integer,Map<Integer, String>> activityBatchMap=timeTableForClassForm.getActivityBatchMap();
							Map<Integer,Map<Integer, String>> activityMap=timeTableForClassForm.getActivityMap();
							Map<Integer, String> map=null;
							List<TTSubjectBatchTo> subjectList=periodTo.getSubjectList();
							if(subjectList==null){
								subjectList=new ArrayList<TTSubjectBatchTo>();
							}
							if(subjectList.isEmpty()){
								TTSubjectBatchTo to1=new TTSubjectBatchTo();
								to1.setIsActive(true);
								to1.setAttendanceTypeId("1");
								if(to1.getAttendanceTypeId()!=null && !to1.getAttendanceTypeId().isEmpty()){
									if(activityMap.containsKey(Integer.parseInt(to1.getAttendanceTypeId())))
										to1.setActivity(activityMap.get(Integer.parseInt(to1.getAttendanceTypeId())));
								}
								to1.setTeachersMap(timeTableForClassForm.getTeachersMap());
								subjectList.add(to1); 
							}else{
								ListIterator<TTSubjectBatchTo> itr1=subjectList.listIterator();
								while (itr1.hasNext()) {
									TTSubjectBatchTo to1=itr1.next();
									if(to1.getSubjectId()!=null && !to1.getSubjectId().isEmpty()){
										if(batchMap!=null && batchMap.containsKey(Integer.parseInt(to1.getSubjectId()))){
											map=batchMap.get(Integer.parseInt(to1.getSubjectId()));
											to1.setBatchs(map);
										}
									}else if(to1.getActivityId()!=null && !to1.getActivityId().isEmpty()){
										if(activityBatchMap!=null && activityBatchMap.containsKey(Integer.parseInt(to1.getActivityId()))){
											map=activityBatchMap.get(Integer.parseInt(to1.getActivityId()));
											to1.setBatchs(map);
										}
									}
									if(to1.getAttendanceTypeId()!=null && !to1.getAttendanceTypeId().isEmpty()){
										if(activityMap.containsKey(Integer.parseInt(to1.getAttendanceTypeId())))
											to1.setActivity(activityMap.get(Integer.parseInt(to1.getAttendanceTypeId())));
									}
									to1.setTeachersMap(timeTableForClassForm.getTeachersMap());
								}
								//modified by chandra for delete button
								checkSizeOfSubjectListForPeriodDetail(subjectList,timeTableForClassForm);
							}

							String roleId=null;
							if(session.getAttribute("rid")!=null){
								roleId=(session.getAttribute("rid").toString());
								System.out.println(roleId);
								timeTableForClassForm.setRoleId(roleId);
							}
							Collections.sort(subjectList);
							timeTableForClassForm.setSubjectList(subjectList);
							timeTableForClassForm.setTtPeriodWeekId(periodTo.getId());
							timeTableForClassForm.setPeriodId(periodTo.getPeriodId());
							timeTableForClassForm.setWeek(to.getWeek());
							break;
						}
					}
				}
			}

			//raghu
			timeTableForClassForm.setDisplayWarning(null);
			timeTableForClassForm.setDisplayWarning1(null);

			return mapping.findForward(CMSConstants.TIME_TABLE_PERIOD);
		}
		log.info("Entered TimeTableForClassAction - getPeriods");
		return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
	}
	/**
	 * @param subjectList
	 * @param timeTableForClassForm
	 * @throws Exception
	 */
	private void checkSizeOfSubjectList(List<TTSubjectBatchTo> subjectList, TimeTableForClassForm timeTableForClassForm) throws Exception {
		Iterator<TTSubjectBatchTo> itr=subjectList.iterator();
		int count=0;
		while (itr.hasNext()) {
			TTSubjectBatchTo to = itr.next();
			if(to.getIsActive()!=null && to.getIsActive())
				count++;
		}
		if(count>1)
			timeTableForClassForm.setDeleteSubject(true);
		else
			timeTableForClassForm.setDeleteSubject(false);

	}
	/**
	 * @param subjectList
	 * @param timeTableForClassForm
	 * @throws Exception
	 */
	//created for PeriodDetails , created by chandra
	private void checkSizeOfSubjectListForPeriodDetail(List<TTSubjectBatchTo> subjectList, TimeTableForClassForm timeTableForClassForm) throws Exception {
		Iterator<TTSubjectBatchTo> itr=subjectList.iterator();
		int count=0;
		while (itr.hasNext()) {
			TTSubjectBatchTo to = itr.next();
			if(to.getIsActive()!=null && to.getIsActive())
				count++;
		}
		if(count>=1)
			timeTableForClassForm.setDeleteSubject(true);
		else
			timeTableForClassForm.setDeleteSubject(false);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMoreToPeriodDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered TimeTableForClassAction - getPeriods");

		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		ActionErrors errors = new ActionErrors();
		List<TTSubjectBatchTo> subjectList=timeTableForClassForm.getSubjectList();
		boolean isAdmore=true;
		boolean isAdd=validateTimeTableForPeriod(errors,subjectList,timeTableForClassForm,isAdmore);
		Map<Integer,Map<Integer, String>> batchMap=timeTableForClassForm.getBatchMap();
		Map<Integer,Map<Integer, String>> activityBatchMap=timeTableForClassForm.getActivityBatchMap();
		Map<Integer,Map<Integer, String>> activityMap=timeTableForClassForm.getActivityMap();
		Map<Integer, String> map=null;
		ListIterator<TTSubjectBatchTo> itr=subjectList.listIterator();
		while (itr.hasNext()) {
			TTSubjectBatchTo to=itr.next();
			if(to.getSubjectId()!=null && !to.getSubjectId().isEmpty()){
				if(batchMap!=null && batchMap.containsKey(Integer.parseInt(to.getSubjectId()))){
					map=batchMap.get(Integer.parseInt(to.getSubjectId()));
					to.setBatchs(map);
				}else if(to.getActivityId()!=null && !to.getActivityId().isEmpty()){
					if(activityBatchMap!=null && activityBatchMap.containsKey(Integer.parseInt(to.getActivityId()))){
						map=activityBatchMap.get(Integer.parseInt(to.getActivityId()));
						to.setBatchs(map);
					}
				}
			}
			if(to.getAttendanceTypeId()!=null && !to.getAttendanceTypeId().isEmpty()){
				if(activityMap.containsKey(Integer.parseInt(to.getAttendanceTypeId())))
					to.setActivity(activityMap.get(Integer.parseInt(to.getAttendanceTypeId())));
			}

			if(to.getDepId()!=null && !to.getDepId().trim().isEmpty()){
				Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getFilteredTeacherDepartmentsName(to.getDepId());
				to.setTeachersMap(teachersMap);
			}else{
				to.setTeachersMap(timeTableForClassForm.getTeachersMap());
			}
		}
		if(errors.isEmpty()){
			if(!isAdd){
				TTSubjectBatchTo to=new TTSubjectBatchTo();
				to.setIsActive(true);
				to.setDeleteCount(subjectList.size()+1);
				to.setAttendanceTypeId("1");
				if(to.getAttendanceTypeId()!=null && !to.getAttendanceTypeId().isEmpty()){
					if(activityMap.containsKey(Integer.parseInt(to.getAttendanceTypeId())))
						to.setActivity(activityMap.get(Integer.parseInt(to.getAttendanceTypeId())));
				}

				//raghu
				if(timeTableForClassForm.getIsStaff()){
					String s[]={timeTableForClassForm.getUserId()};
					to.setUserId(s);
					to.setUserName(timeTableForClassForm.getEmpName());
				}


				subjectList.add(to);
				to.setTeachersMap(timeTableForClassForm.getTeachersMap());
				checkSizeOfSubjectList(subjectList, timeTableForClassForm);
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","We Cannot add More than one Common subject"));
				saveErrors(request, errors);
			}
		}else{
			if(timeTableForClassForm.getDisplayWarning1()==null || timeTableForClassForm.getDisplayWarning1().isEmpty()
					||timeTableForClassForm.getDisplayWarning()==null || timeTableForClassForm.getDisplayWarning().isEmpty()){
				saveErrors(request, errors);
			}
			checkSizeOfSubjectList(subjectList, timeTableForClassForm);
			setTimeTableForaPeriod(timeTableForClassForm);
		}
		log.info("Entered TimeTableForClassAction - getPeriods");
		return mapping.findForward(CMSConstants.TIME_TABLE_PERIOD);
	}

	/**
	 * @param errors
	 * @param subjectList
	 * @param timeTableForClassForm
	 * @return
	 * @throws Exception
	 */
	private boolean validateTimeTableForPeriod(ActionErrors errors, List<TTSubjectBatchTo> subjectList, TimeTableForClassForm timeTableForClassForm,boolean isAdmore)  throws Exception{
		Iterator<TTSubjectBatchTo> itr=subjectList.iterator();
		List<Integer> batchList=timeTableForClassForm.getBatchList();
		List<Integer> activityBatchList=timeTableForClassForm.getActivityBatchList();
		List<Integer> commonSubList=timeTableForClassForm.getCommonSubList();
		List<Integer> secSubList=timeTableForClassForm.getSecLanguageList();
		List<Integer> electiveSubList=timeTableForClassForm.getElectiveList();
		List<Integer> certificateList=timeTableForClassForm.getCertificateList();
		List<Integer> userList=new ArrayList<Integer>();

		boolean isCommonSub=false;
		boolean isSecSub=false;
		boolean isEleSub=false;
		boolean isCertificate=false;
		boolean sameUser=false;
		String[] user=null;
		while (itr.hasNext()) {
			TTSubjectBatchTo to =itr.next();
			if(to.getIsActive()){
				/*if(to.getRoomNo()==null || to.getRoomNo().isEmpty()){
				if (errors.get(CMSConstants.ROOMNO_REQUIRED) != null&& !errors.get(CMSConstants.ROOMNO_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ROOMNO_REQUIRED,new ActionError(CMSConstants.ROOMNO_REQUIRED));
				}
			}*/
				if(to.getAttendanceTypeId()==null || to.getAttendanceTypeId().isEmpty()){
					if (errors.get(CMSConstants.Attendance_Required) != null&& !errors.get(CMSConstants.Attendance_Required).hasNext()) {
						//errors.add(CMSConstants.Attendance_Required,new ActionError(CMSConstants.Attendance_Required));
					}
				}
				if(to.getUserId()==null || to.getUserId().length==0){
					if (errors.get(CMSConstants.ATTENDANCE_TEACHER_REQUIRED) != null&& !errors.get(CMSConstants.ATTENDANCE_TEACHER_REQUIRED).hasNext()) {
						errors.add(CMSConstants.ATTENDANCE_TEACHER_REQUIRED,new ActionError(CMSConstants.ATTENDANCE_TEACHER_REQUIRED));
					}
				}else{
					user=to.getUserId();
					for (int i = 0; i < user.length; i++) {
						if(user[i]!=null && !user[i].isEmpty()){
							if(userList.contains(Integer.parseInt(user[i])))
								sameUser=true;
							else
								userList.add(Integer.parseInt(user[i]));

						}
					}
				}
				if((to.getSubjectId()==null || to.getSubjectId().isEmpty() || to.getAttendanceTypeId()==null || to.getAttendanceTypeId().isEmpty()) && (to.getActivityId()==null || to.getActivityId().isEmpty())){
					if (errors.get("admissionFormForm.subject.required") != null&& !errors.get("admissionFormForm.subject.required").hasNext()) {
						//errors.add("knowledgepro.create.practicalBatch.subject.attendance",new ActionError("knowledgepro.create.practicalBatch.subject.attendance"));
						errors.add("admissionFormForm.subject.required",new ActionError("admissionFormForm.subject.required"));

					}
				}
				if((to.getSubjectId()!=null && !to.getSubjectId().isEmpty()) && (to.getActivityId()!=null && !to.getActivityId().isEmpty())){
					if (errors.get("knowledgepro.create.practicalBatch.subject.attendance.either") != null&& !errors.get("knowledgepro.create.practicalBatch.subject.attendance.either").hasNext()) {
						errors.add("knowledgepro.create.practicalBatch.subject.attendance.either",new ActionError("knowledgepro.create.practicalBatch.subject.attendance.either"));
					}
				}
				if(to.getActivityId()!=null && !to.getActivityId().isEmpty()){
					if(activityBatchList.contains(Integer.parseInt(to.getActivityId()))){
						if(to.getBatchId()==null || to.getBatchId().isEmpty()){
							if (errors.get(CMSConstants.ATTENDANCE_BATCH_REQUIRED) != null&& !errors.get(CMSConstants.ATTENDANCE_BATCH_REQUIRED).hasNext()) {
								errors.add(CMSConstants.ATTENDANCE_BATCH_REQUIRED,new ActionError(CMSConstants.ATTENDANCE_BATCH_REQUIRED));
							}
						}
					}
				}
				if(to.getSubjectId()!=null && !to.getSubjectId().isEmpty()){
					if(batchList.contains(Integer.parseInt(to.getSubjectId()))){
						String subType=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(to.getSubjectId()),"Subject",true,"isTheoryPractical");
						if(subType!=null ){
							if(!subType.equalsIgnoreCase("B"))
								if(to.getBatchId()==null || to.getBatchId().isEmpty()){
									if (errors.get(CMSConstants.ATTENDANCE_BATCH_REQUIRED) != null&& !errors.get(CMSConstants.ATTENDANCE_BATCH_REQUIRED).hasNext()) {
										errors.add(CMSConstants.ATTENDANCE_BATCH_REQUIRED,new ActionError(CMSConstants.ATTENDANCE_BATCH_REQUIRED));
									}
								}
						}else{
							if(to.getBatchId()==null || to.getBatchId().isEmpty()){
								if (errors.get(CMSConstants.ATTENDANCE_BATCH_REQUIRED) != null&& !errors.get(CMSConstants.ATTENDANCE_BATCH_REQUIRED).hasNext()) {
									errors.add(CMSConstants.ATTENDANCE_BATCH_REQUIRED,new ActionError(CMSConstants.ATTENDANCE_BATCH_REQUIRED));
								}
							}
						}
					}else{
						if (commonSubList!=null) {
						if(commonSubList.contains(Integer.parseInt(to.getSubjectId()))){
							//isCommonSub=true;
						}
						}
					}
					if (secSubList!=null) {
					if(secSubList.contains(Integer.parseInt(to.getSubjectId())))
						isSecSub=true;
				}
					if (electiveSubList!=null) {
					if(electiveSubList.contains(Integer.parseInt(to.getSubjectId())))
						isEleSub=true;
					}
					if (certificateList!=null) {
					if(certificateList.contains(Integer.parseInt(to.getSubjectId())))
						isCertificate=true;
					}
				}
			}
		}
		if(isCommonSub && (isSecSub || isEleSub || isCertificate)){
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.COMMON_SEC_HELD));
		}
		if(isSecSub && isEleSub)
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ELEC_SEC_HELD));
		if(isSecSub && isCertificate)
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.CERTIFICATE_SEC_HELD));
		if(isCertificate && isEleSub)
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ELEC_CERTIFICATE_HELD));

		if(sameUser && !isAdmore){
			timeTableForClassForm.setDisplayWarning1("Same Teacher Cannot be assigned in Same Period");
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.TT_SAME_TEACHER_ERROR));
		}

		return isCommonSub;
	}



	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTimeTableForaPeriod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		List<TTSubjectBatchTo> subjectList=timeTableForClassForm.getSubjectList();
		boolean isAdmore=false;
		validateTimeTableForPeriod(errors,subjectList,timeTableForClassForm,isAdmore);
		if(errors.isEmpty())
			validateTimeTableForTeacher(errors,subjectList,timeTableForClassForm);
		setUserId(request, timeTableForClassForm);
		if (errors.isEmpty()) {
			try{
				boolean isPeriodsAdded = TimeTableForClassHandler.getInstance().addTimeTableForaPeriod(subjectList,timeTableForClassForm);
				if (isPeriodsAdded) {
					ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Time Table");
					messages.add("messages", message);
					saveMessages(request, messages);
					//raghu
					updateFlagForTimeTable(mapping, timeTableForClassForm, request, response);
					request.setAttribute("blink", timeTableForClassForm.isChanged());
					timeTableForClassForm.resetFields();
				}else{
					// failed
					errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Time Table"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.TIME_TABLE_PERIOD);
				}
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				timeTableForClassForm.setErrorMessage(msg);
				timeTableForClassForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			if(timeTableForClassForm.getDisplayWarning()==null || timeTableForClassForm.getDisplayWarning1()==null
					|| timeTableForClassForm.getDisplayWarning().isEmpty() || timeTableForClassForm.getDisplayWarning1().isEmpty()){
				saveErrors(request, errors);
			}
			setTimeTableForaPeriod(timeTableForClassForm);
			return mapping.findForward(CMSConstants.TIME_TABLE_PERIOD);
		}
		List<TimeTableClassTo> classTos=TimeTableForClassHandler.getInstance().getTimeTableForInputClass(timeTableForClassForm);
		timeTableForClassForm.setClassTos(classTos);
		return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
	}

	/**
	 * @param errors
	 * @param subjectList
	 * @throws Exception
	 */
	private void validateTimeTableForTeacher(ActionErrors errors, List<TTSubjectBatchTo> subjectList,TimeTableForClassForm timeTableForClassForm) throws Exception {

		for (TTSubjectBatchTo to : subjectList) {
			if(to.getIsActive()){
				boolean isExists=TimeTableForClassHandler.getInstance().checkMisMatchData(to,timeTableForClassForm);
				if(isExists){
					timeTableForClassForm.setDisplayWarning("The teacher is assigned for the same period in "+timeTableForClassForm.getClasscode());
					String msg = "The teacher is assigned for the same period in "+timeTableForClassForm.getClasscode();
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message",msg));
				}
			}
		}

	}

	/**
	 * @param timeTableForClassForm
	 * @throws Exception
	 */
	private void setTimeTableForaPeriod( TimeTableForClassForm timeTableForClassForm) throws Exception {
		Map<Integer,Map<Integer, String>> batchMap=timeTableForClassForm.getBatchMap();
		Map<Integer,Map<Integer, String>> activityBatchMap=timeTableForClassForm.getActivityBatchMap();
		Map<Integer,Map<Integer, String>> activityMap=timeTableForClassForm.getActivityMap();
		Map<Integer, String> map=null;
		List<TTSubjectBatchTo> subjectList=timeTableForClassForm.getSubjectList();

		ListIterator<TTSubjectBatchTo> itr1=subjectList.listIterator();
		while (itr1.hasNext()) {
			TTSubjectBatchTo to1=itr1.next();
			if(to1.getSubjectId()!=null && !to1.getSubjectId().isEmpty()){
				if(batchMap!=null && batchMap.containsKey(Integer.parseInt(to1.getSubjectId()))){
					map=batchMap.get(Integer.parseInt(to1.getSubjectId()));
					to1.setBatchs(map);
				}
			}else if(to1.getActivityId()!=null && !to1.getActivityId().isEmpty()){
				if(activityBatchMap!=null && activityBatchMap.containsKey(Integer.parseInt(to1.getActivityId()))){
					map=activityBatchMap.get(Integer.parseInt(to1.getActivityId()));
					to1.setBatchs(map);
				}
			}
			if(to1.getAttendanceTypeId()!=null && !to1.getAttendanceTypeId().isEmpty()){
				if(activityMap.containsKey(Integer.parseInt(to1.getAttendanceTypeId())))
					to1.setActivity(activityMap.get(Integer.parseInt(to1.getAttendanceTypeId())));
			}
			if(to1.getDepId()!=null && !to1.getDepId().trim().isEmpty()){
				Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getFilteredTeacherDepartmentsName(to1.getDepId());
				to1.setTeachersMap(teachersMap);
			}else{
				to1.setTeachersMap(timeTableForClassForm.getTeachersMap());
			}

		}

		checkSizeOfSubjectList(subjectList,timeTableForClassForm);
		Collections.sort(subjectList);
		timeTableForClassForm.setSubjectList(subjectList);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToMainPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered TimeTableForClassAction - getPeriods");
		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		timeTableForClassForm.resetPeriodFields();
		log.info("Entered TimeTableForClassAction - getPeriods");
		return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeFromPeriodDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered TimeTableForClassAction - getPeriods");

		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		ActionErrors errors = new ActionErrors();
		timeTableForClassForm.setDisplayWarning(null);
		timeTableForClassForm.setDisplayWarning1(null);
		List<TTSubjectBatchTo> subjectList=timeTableForClassForm.getSubjectList();
		ListIterator<TTSubjectBatchTo> itr=subjectList.listIterator();
		while (itr.hasNext()) {
			TTSubjectBatchTo to=itr.next();
			if(to.getDeleteCount()==timeTableForClassForm.getDeleteCount()){
				to.setIsActive(false);
				timeTableForClassForm.setDeleteBackButton(true);
			}
		}
		saveErrors(request, errors);
		checkSizeOfSubjectList(subjectList, timeTableForClassForm);
		log.info("Entered TimeTableForClassAction - getPeriods");
		return mapping.findForward(CMSConstants.TIME_TABLE_PERIOD);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateFlagForTimeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		setUserId(request, timeTableForClassForm);
		try{
			boolean isPeriodsAdded = TimeTableForClassHandler.getInstance().updateFlagForTimeTable(timeTableForClassForm);
			if (isPeriodsAdded) {
				if(timeTableForClassForm.getFinalApprove().equalsIgnoreCase("on"))
					request.setAttribute("noofcandidates", "Time Table Is Approved");
				else
					request.setAttribute("noofcandidates", "Time Table Is Not Approved");

			}else{
				request.setAttribute("noofcandidates", "Time Table Is Approved Not Updated");
			}
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			timeTableForClassForm.setErrorMessage(msg);
			timeTableForClassForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		return mapping.findForward("ajaxResponseForInterview");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTimeTableHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered TimeTableForClassAction - getTimeTableHistory");
		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		//		timeTableForClassForm.resetFields();
		try {
			Map<String,List<TimeTableClassTo>> historyMap=TimeTableForClassHandler.getInstance().getTimeTableHistoryForInputClass(timeTableForClassForm);
			timeTableForClassForm.setHistoryMap(historyMap);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			timeTableForClassForm.setErrorMessage(msg);
			timeTableForClassForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Entered TimeTableForClassAction - getTimeTableHistory");
		return mapping.findForward(CMSConstants.TIME_TABLE_HISTORY);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward copyPeriodDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered TimeTableForClassAction - copyPeriodDetails");
		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		try{
			if(timeTableForClassForm.getFromDay()==0 || timeTableForClassForm.getFromPeriodId()==0 || timeTableForClassForm.getToDay()==0 || timeTableForClassForm.getToPeriodId()==0){
				if(timeTableForClassForm.getFromDay()==0){ 
					errors.add("error", new ActionError("knowledgepro.from.day.required"));
					saveErrors(request, errors);
				}
				if(timeTableForClassForm.getFromPeriodId()==0){
					errors.add("error", new ActionError("knowledgepro.from.period.required"));
					saveErrors(request, errors);
				}
				if(timeTableForClassForm.getToDay()==0){
					errors.add("error", new ActionError("knowledgepro.to.day.required"));
					saveErrors(request, errors);
				}
				if(timeTableForClassForm.getToPeriodId()==0){
					errors.add("error", new ActionError("knowledgepro.to.period.required"));
					saveErrors(request, errors);
				}
			}else{
				if((timeTableForClassForm.getFromDay()) == (timeTableForClassForm.getToDay()) && (timeTableForClassForm.getToPeriodId())==(timeTableForClassForm.getFromPeriodId())){
					errors.add("error", new ActionError("knowledgepro.same.day.same.period"));
					saveErrors(request, errors);
					timeTableForClassForm.resetDayAndPeriodFields();
				}
			}
			if(errors.isEmpty()){
				List<TTSubjectBatchForCopyTo> subjectList = TimeTableForClassHandler.getInstance().getSubjectList(timeTableForClassForm);
				setUserId(request, timeTableForClassForm);
				boolean isPeriodsAdded = TimeTableForClassHandler.getInstance().addTimeTableForaPeriodForCopyPeriods(subjectList,timeTableForClassForm);
				if (isPeriodsAdded) {
					ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Time Table");
					messages.add("messages", message);
					saveMessages(request, messages);
					request.setAttribute("blink", timeTableForClassForm.isChanged());
					timeTableForClassForm.resetFields();
				}else{
					// failed
					errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Time Table"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
				}
			}else {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
			}
		}catch (BusinessException e) {
			timeTableForClassForm.setErrormsg("The from period does not have any data");
			timeTableForClassForm.resetDayAndPeriodFieldsForCorrecrPeriod();
			return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
		}catch (Exception e) {
			log.error("Error occured in copyPeriodDetails Time Table Action", e);
			String msg = super.handleApplicationException(e);
			timeTableForClassForm.setErrorMessage(msg);
			timeTableForClassForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<TimeTableClassTo> classTos1=TimeTableForClassHandler.getInstance().getTimeTableForInputClass(timeTableForClassForm);
		timeTableForClassForm.setClassTos(classTos1);
		timeTableForClassForm.resetDayAndPeriodFields();
		return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward copyPeriods(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		timeTableForClassForm.setFlag(true);
		return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * code added by mehaboob after warning message displayed user clicks OK  button only
	 */
	public ActionForward addTimeTableForaPeriodAfterConfirmation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		List<TTSubjectBatchTo> subjectList=timeTableForClassForm.getSubjectList();
		setUserId(request, timeTableForClassForm);
		try{
			boolean isPeriodsAdded = TimeTableForClassHandler.getInstance().addTimeTableForaPeriod(subjectList,timeTableForClassForm);
			if (isPeriodsAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Time Table");
				messages.add("messages", message);
				saveMessages(request, messages);
				//raghu
				updateFlagForTimeTable(mapping, timeTableForClassForm, request, response);
				request.setAttribute("blink", timeTableForClassForm.isChanged());
				timeTableForClassForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Time Table"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.TIME_TABLE_PERIOD);
			}
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			timeTableForClassForm.setErrorMessage(msg);
			timeTableForClassForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<TimeTableClassTo> classTos=TimeTableForClassHandler.getInstance().getTimeTableForInputClass(timeTableForClassForm);
		timeTableForClassForm.setClassTos(classTos);
		return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
	}
	public ActionForward updatePeriodCourseWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeTableForClassForm timeTableForClassForm = (TimeTableForClassForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request, timeTableForClassForm);
		boolean result=false;
		
		if (timeTableForClassForm.getTeachers()==null) {
			errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Faculty required"));
			saveErrors(request, errors);
		}
		if (timeTableForClassForm.getWeeks()==null) {
			errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Day required"));
			saveErrors(request, errors);
		}
		if (timeTableForClassForm.getPeriods()==null) {
			errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Period required"));
			saveErrors(request, errors);
		}
		if (timeTableForClassForm.getSubjectId()==null || timeTableForClassForm.getSubjectId().isEmpty()) {
			errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Course required"));
			saveErrors(request, errors);
		}
		if (timeTableForClassForm.getClasses()==null) {
			errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Class required"));
			saveErrors(request, errors);
		}
		if (timeTableForClassForm.getTeachers()!=null) {
			if (timeTableForClassForm.getAttType()=="T") {
			if (timeTableForClassForm.getTeachers().length>1) {
			errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","For theory subject Only one faculty Allowed"));
			saveErrors(request, errors);
			}
			}
		}
		if (errors.isEmpty()) {
		result=TimeTableForClassHandler.getInstance().updatePeriodCourseWise(timeTableForClassForm);
		
		if (result) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Time Table");
			messages.add("messages", message);
			saveMessages(request, messages);
			//raghu
			updateFlagForTimeTable(mapping, timeTableForClassForm, request, response);
			request.setAttribute("blink", timeTableForClassForm.isChanged());
			//timeTableForClassForm.resetFields();
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.admission.empty.err.message","Failed to add timetable"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.TIME_TABLE_PERIOD);
		}
		}
		timeTableForClassForm.setFlag(true);
		timeTableForClassForm.setToggler("t");
		return mapping.findForward(CMSConstants.INIT_TIME_TABLE);
	}
}
