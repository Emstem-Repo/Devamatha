package com.kp.cms.handlers.timetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.TTClasses;
import com.kp.cms.bo.admin.TTClassesHistory;
import com.kp.cms.bo.admin.TTPeriodWeek;
import com.kp.cms.bo.admin.TTSubjectBatch;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.timetable.TimeTableForClassForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.helpers.attendance.TeacherDepartmentEntryHelper;
import com.kp.cms.helpers.timetable.TimeTableForClassHelper;
import com.kp.cms.to.timetable.TTSubjectBatchForCopyTo;
import com.kp.cms.to.timetable.TTSubjectBatchTo;
import com.kp.cms.to.timetable.TimeTableClassTo;
import com.kp.cms.to.timetable.TimeTablePeriodTo;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.timetable.ITimeTableForClassTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.timetable.TimeTableForClassTransactionImpl;

public class TimeTableForClassHandler {

	/**
	 * Singleton object of TimeTableForClassHandler
	 */
	private static volatile TimeTableForClassHandler timeTableForClassHandler = null;
	private static final Log log = LogFactory.getLog(TimeTableForClassHandler.class);
	private TimeTableForClassHandler() {

	}
	/**
	 * return singleton object of TimeTableForClassHandler.
	 * @return
	 */
	public static TimeTableForClassHandler getInstance() {
		if (timeTableForClassHandler == null) {
			timeTableForClassHandler = new TimeTableForClassHandler();
		}
		return timeTableForClassHandler;
	}
	/**
	 * @param timeTableForClassForm
	 * @return
	 */
	public List<TimeTableClassTo> getTimeTableForInputClass(TimeTableForClassForm timeTableForClassForm) throws Exception {
		log.info("Entered Into getTimeTableForInputClass");
		String periodQuery="from Period p where p.isActive=1 and p.classSchemewise.id="+timeTableForClassForm.getClassId();
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Period> periodList=transaction.getDataForQuery(periodQuery);
		if(periodList==null || periodList.isEmpty()){
			timeTableForClassForm.setMsg("Periods Must be Entered For A Given Class");
			return null;
		}
		Collections.sort(periodList);
		timeTableForClassForm.setPeriodList(periodList);
		String timeTableQuery="from TTClasses tc where tc.isActive=1 and tc.classSchemewise.classes.isActive=1 and tc.classSchemewise.id="+timeTableForClassForm.getClassId();
		List<TTClasses> boList=transaction.getDataForQuery(timeTableQuery);
		Map<String,Map<Integer,TimeTablePeriodTo>> ttMap=TimeTableForClassHelper.getInstance().convertBoListToMap(boList,timeTableForClassForm);
		log.info("Exit From getTimeTableForInputClass");
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();

		if (timeTableForClassForm.getClassId() != null
				&& timeTableForClassForm.getClassId().length() != 0) {
			ClassSchemewise classSchemewise = CommonAjaxHandler
					.getInstance().getDetailsonClassSchemewiseId(
							Integer.parseInt(timeTableForClassForm
									.getClassId()));
			if (classSchemewise.getCurriculumSchemeDuration()
					.getAcademicYear() != null
					&& classSchemewise.getClasses().getCourse().getId() != 0
					&& classSchemewise.getClasses().getTermNumber() != 0) {
				int year = classSchemewise.getCurriculumSchemeDuration()
						.getAcademicYear();
				int courseId = classSchemewise.getClasses().getCourse()
						.getId();
				int term = classSchemewise.getClasses().getTermNumber();
				if (timeTableForClassForm.getClsOrcrs()=="classWise" || timeTableForClassForm.getClsOrcrs().equalsIgnoreCase("classWise")) {
					subjectMap = TimeTableForClassTransactionImpl.getInstance().getSubjectByCourseIdTermYear(courseId, year, term,timeTableForClassForm.getClassId(),timeTableForClassForm.getUserId(),timeTableForClassForm.getRoleId());
				}else{
					String classId=null;
				subjectMap = TimeTableForClassTransactionImpl.getInstance().getSubjectByCourseIdTermYear(courseId, year, term, classId,timeTableForClassForm.getUserId(),timeTableForClassForm.getRoleId());
				}
			}
		}
		String subQuery=null;
		if (timeTableForClassForm.getRoleId()!=null &&  CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(Integer.parseInt(timeTableForClassForm.getRoleId()))) {
			subQuery="select subject_group_subjects.subject_id, subject.is_second_language, subject_group.is_common_sub_grp, subject.is_certificate_course"+
					" from teacher_class_subject "+
					" inner join class_schemewise ON class_schemewise.id = teacher_class_subject.class_schemewise_id"+ 
					" inner join subject_group_subjects ON teacher_class_subject.subject_id = subject_group_subjects.subject_id"+
					" inner join  subject_group ON subject_group.id = subject_group_subjects.subject_group_id"+
					" inner join  subject ON subject.id = subject_group_subjects.subject_id"+
					" inner join  classes on class_schemewise.id = classes.id"+
					" where classes.is_active=1"+
					" and subject_group.is_active=1"+
					" and subject_group_subjects.is_active=1"+
					" and class_schemewise.id="+timeTableForClassForm.getClassId()+
					" and teacher_class_subject.teacher_id="+timeTableForClassForm.getRollNo();
			
		}else{
		subQuery="select subject_group_subjects.subject_id, subject.is_second_language, subject_group.is_common_sub_grp, subject.is_certificate_course"+
						" from teacher_class_subject "+
						" inner join class_schemewise ON class_schemewise.id = teacher_class_subject.class_schemewise_id"+ 
						" inner join subject_group_subjects ON teacher_class_subject.subject_id = subject_group_subjects.subject_id"+
						" inner join  subject_group ON subject_group.id = subject_group_subjects.subject_group_id"+
						" inner join  subject ON subject.id = subject_group_subjects.subject_id"+
						" inner join  classes on class_schemewise.id = classes.id"+
						" where classes.is_active=1"+
						" and subject_group.is_active=1"+
						" and subject_group_subjects.is_active=1"+
						" and class_schemewise.id="+timeTableForClassForm.getClassId();
		}
		List<Object[]> subList=TimeTableForClassTransactionImpl.getInstance().getSubjectByClassId(subQuery);
		if(subList!=null && !subList.isEmpty()){
			List<Integer> commonSubList=new ArrayList<Integer>();
			List<Integer> secLanguageList=new ArrayList<Integer>();
			List<Integer> electiveList=new ArrayList<Integer>();
			List<Integer> certificateList=new ArrayList<Integer>();
			Iterator<Object[]> itr=subList.iterator();
			while (itr.hasNext()) {
				Object[] obj= itr.next();
				if(obj[0]!=null){
					if(obj[1]!=null && (obj[1].toString().equalsIgnoreCase("1") || obj[1].toString().equalsIgnoreCase("true"))){
						secLanguageList.add(Integer.parseInt(obj[0].toString()));
					}
					if(obj[2]!=null && (obj[2].toString().equalsIgnoreCase("1") || obj[2].toString().equalsIgnoreCase("true"))){
						commonSubList.add(Integer.parseInt(obj[0].toString()));
					}
					if(obj[3]!=null && (obj[3].toString().equalsIgnoreCase("1") || obj[3].toString().equalsIgnoreCase("true"))){
						certificateList.add(Integer.parseInt(obj[0].toString()));
					}else if(obj[2]!=null && obj[1]!=null && (obj[1].toString().equalsIgnoreCase("0") || obj[1].toString().equalsIgnoreCase("false")) && (obj[2].toString().equalsIgnoreCase("0") || obj[2].toString().equalsIgnoreCase("false"))){
						electiveList.add(Integer.parseInt(obj[0].toString()));
					}

				}
			}
			timeTableForClassForm.setSecLanguageList(secLanguageList);
			timeTableForClassForm.setCommonSubList(commonSubList);
			timeTableForClassForm.setElectiveList(electiveList);
			timeTableForClassForm.setCertificateList(certificateList);
		}
		// activity Map by Attendance Type 
		List<Activity> activityList=transaction.getDataForQuery("from Activity a where a.isActive=1 and a.attendanceType.isActive=1");
		Map<Integer,Map<Integer,String>> activityMap=new HashMap<Integer, Map<Integer,String>>();
		Map<Integer,String> map=null;
		if(activityList!=null && !activityList.isEmpty()){
			Iterator<Activity> actItr=activityList.iterator();
			while (actItr.hasNext()) {
				Activity activity = actItr.next();
				if(activity.getAttendanceType()!=null){
					if(activityMap.containsKey(activity.getAttendanceType().getId()))
						map=activityMap.get(activity.getAttendanceType().getId());
					else
						map=new HashMap<Integer, String>();

					map.put(activity.getId(),activity.getName());
					activityMap.put(activity.getAttendanceType().getId(),map);

				}
			}
		}
		timeTableForClassForm.setActivityMap(activityMap);

		// Batchs For Selected Classes
		String batchQuery="select bs.batch" +
				" from BatchStudent bs" +
				" where bs.batch.isActive=1" +
				" and bs.classSchemewise.classes.isActive=1" +
				" and bs.classSchemewise.id ="+timeTableForClassForm.getClassId()+
				" group by bs.batch.id order by bs.batch.batchName";
		List<Batch> batchBoList=transaction.getDataForQuery(batchQuery);

		List<Integer> batchList=new ArrayList<Integer>();
		Map<Integer,Map<Integer,String>> batchMap=new HashMap<Integer, Map<Integer,String>>();
		List<Integer> activityBatchList=new ArrayList<Integer>();
		Map<Integer,Map<Integer,String>> activityBatchMap=new HashMap<Integer, Map<Integer,String>>();
		Map<Integer,String> batchMasterMap=null;
		if(batchBoList!=null && !batchBoList.isEmpty()){
			Iterator<Batch> batchItr=batchBoList.iterator();
			while (batchItr.hasNext()) {
				Batch batch = (Batch) batchItr.next();
				if(batch.getSubject()!=null){
					if(!batchList.contains(batch.getSubject().getId())){
						batchList.add(batch.getSubject().getId());
					}
					if(batchMap.containsKey(batch.getSubject().getId())){
						batchMasterMap=batchMap.get(batch.getSubject().getId());
					}else
						batchMasterMap=new HashMap<Integer, String>();

					batchMasterMap.put(batch.getId(), batch.getBatchName());
					batchMap.put(batch.getSubject().getId(), batchMasterMap);
				}else if(batch.getActivity()!=null){

					if(!activityBatchList.contains(batch.getActivity().getId())){
						activityBatchList.add(batch.getActivity().getId());
					}
					if(activityBatchMap.containsKey(batch.getActivity().getId())){
						batchMasterMap=activityBatchMap.get(batch.getActivity().getId());
					}else
						batchMasterMap=new HashMap<Integer, String>();

					batchMasterMap.put(batch.getId(), batch.getBatchName());
					activityBatchMap.put(batch.getActivity().getId(), batchMasterMap);

				}
			}
		}

		timeTableForClassForm.setBatchList(batchList);
		timeTableForClassForm.setSubjectMap(subjectMap);
		timeTableForClassForm.setBatchMap(batchMap);
		timeTableForClassForm.setActivityBatchList(activityBatchList);
		timeTableForClassForm.setActivityBatchMap(activityBatchMap);

		String attendanceTypeQuery="select a.id,a.name from AttendanceType a where a.isActive=1";
		List<Object[]> attList=transaction.getDataForQuery(attendanceTypeQuery);
		Iterator<Object[]> itr=attList.iterator();
		Map<Integer,String> attTypeMap=new HashMap<Integer, String>();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			if(obj[0]!=null && obj[1]!=null)
				attTypeMap.put(Integer.parseInt(obj[0].toString()),obj[1].toString());
		}
		timeTableForClassForm.setAttTypeMap(attTypeMap);
		Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getTeacherDepartmentsName();
		timeTableForClassForm.setTeachersMap(teachersMap);
		Map<Integer, String> departmentMap=UserInfoHandler.getInstance().getDepartment();
		timeTableForClassForm.setDepartmentMap(departmentMap);
		return TimeTableForClassHelper.getInstance().getTimeTableForInputClass(timeTableForClassForm,ttMap,periodList);
	}
	/**
	 * @param timeTableForClassForm
	 * @return
	 * @throws Exception
	 */
	public boolean addTimeTableForaPeriod(List<TTSubjectBatchTo> subjectList,TimeTableForClassForm timeTableForClassForm) throws Exception {
		List<TTSubjectBatch> boList=TimeTableForClassHelper.getInstance().convertTotoBoList(subjectList,timeTableForClassForm);
		ITimeTableForClassTransaction transaction=TimeTableForClassTransactionImpl.getInstance();
		return transaction.addTimeTableForaPeriod(boList,timeTableForClassForm);
	}
	/**
	 * @param timeTableForClassForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateFlagForTimeTable( TimeTableForClassForm timeTableForClassForm) throws Exception {
		ITimeTableForClassTransaction transaction=TimeTableForClassTransactionImpl.getInstance();
		return transaction.updateFlagForTimeTable(timeTableForClassForm.getUserId(),timeTableForClassForm.getTtClassId(),timeTableForClassForm.getFinalApprove());
	}
	/**
	 * returns true if timeTable class history exists in TTClassHistory table for the input class
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public boolean checkTimeTableClassHistory(String classId) throws Exception {
		boolean timeTableClassHistoryExists=false;
		ITimeTableForClassTransaction transaction=TimeTableForClassTransactionImpl.getInstance();
		timeTableClassHistoryExists=transaction.checkForTtClassHistory(classId);
		return timeTableClassHistoryExists;
	}
	/**
	 * getting the data from history table to set in form
	 * @param timeTableForClassForm
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<TimeTableClassTo>> getTimeTableHistoryForInputClass(TimeTableForClassForm timeTableForClassForm) throws Exception{
		/*log.info("Entered Into getTimeTableForInputClass");
		String periodQuery="from Period p where p.isActive=1 and p.classSchemewise.id="+timeTableForClassForm.getClassId();
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Period> periodList=transaction.getDataForQuery(periodQuery);
		Collections.sort(periodList);
		timeTableForClassForm.setPeriodList(periodList);*/
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		String timeTableHistoryQuery="from TTClassesHistory tc where tc.classSchemewise.id="+timeTableForClassForm.getClassId();
		List<TTClassesHistory> boList=transaction.getDataForQuery(timeTableHistoryQuery);
		Map<String,Map<String, Map<Integer, TimeTablePeriodTo>>> ttMap=TimeTableForClassHelper.getInstance().convertHistoryBoListToMap(boList,timeTableForClassForm);
		log.info("Exit From getTimeTableForInputClass");
		return TimeTableForClassHelper.getInstance().getTimeTableHistoryForInputClass(timeTableForClassForm,ttMap,timeTableForClassForm.getPeriodList());
	}
	/**
	 * @param to
	 * @throws Exception
	 */
	public boolean checkMisMatchData(TTSubjectBatchTo to,TimeTableForClassForm timeTableForClassForm) throws Exception {
		boolean isExists=false;
		String[] users=to.getUserId();
		StringBuffer intType=new StringBuffer();
		for (int i = 0; i < users.length; i++) {
			if(intType.length()>0)
				intType.append(","+users[i]);
			else
				intType.append(users[i]);
		}
		String query="select t from TTUsers t left join t.ttSubjectBatch.subject subject " +
				" where t.isActive=1 and t.ttSubjectBatch.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.isActive=1" +
				" and t.users.id in ("+intType+")" +
				" and t.ttSubjectBatch.ttPeriodWeek.period.periodName=(select p.periodName from Period p where p.id="+timeTableForClassForm.getPeriodId()+")" +
				" and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+timeTableForClassForm.getWeek()+"'" +
				" and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate > " +
				" (select c.curriculumSchemeDuration.startDate from ClassSchemewise c where c.classes.isActive=1 and c.id="+timeTableForClassForm.getClassId()+")" +
				((to.getSubjectId()!=null && !to.getSubjectId().isEmpty())?" and (subject.id is null or subject.id!="+to.getSubjectId()+") ":"")+
				((to.getActivityId()!=null && !to.getActivityId().isEmpty())?" and (subject.id is not null) ":"")+
				" and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.id !="+timeTableForClassForm.getClassId();
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			isExists=true;
			StringBuilder className=new StringBuilder();
			Iterator<TTUsers> iterator = list.iterator();
			while (iterator.hasNext()) {
				TTUsers ttUsers = (TTUsers) iterator.next();
				if(ttUsers.getTtSubjectBatch() != null && ttUsers.getTtSubjectBatch().getTtPeriodWeek() != null && 
						ttUsers.getTtSubjectBatch().getTtPeriodWeek().getTtClasses() != null && ttUsers.getTtSubjectBatch().getTtPeriodWeek().getTtClasses().getClassSchemewise() != null){
					className.append(ttUsers.getTtSubjectBatch().getTtPeriodWeek().getTtClasses().getClassSchemewise().getClasses().getName()).append(" ");
				}
			}
			timeTableForClassForm.setClasscode(className.toString());
		}
		return isExists;
	}
	/**
	 * @param timeTableForClassForm
	 * @return
	 * @throws Exception
	 */
	public List<TTSubjectBatchForCopyTo> getSubjectList(TimeTableForClassForm timeTableForClassForm) throws Exception{
		List<TimeTableClassTo> classTos=timeTableForClassForm.getClassTos();
		List<TTSubjectBatchTo> subjectList = new ArrayList<TTSubjectBatchTo>();

		List<TTSubjectBatchForCopyTo> subjectListForCopy = new ArrayList<TTSubjectBatchForCopyTo>();
		try{
			if((timeTableForClassForm.getFromDay())>0){
				Iterator<TimeTableClassTo> itr=classTos.iterator();
				while (itr.hasNext()) {

					TimeTableClassTo to= itr.next();
					if(to.getPosition()==(timeTableForClassForm.getFromDay())){
						List<TimeTablePeriodTo> timeTablePeriodTos=to.getTimeTablePeriodTos();
						Iterator<TimeTablePeriodTo> perioditr=timeTablePeriodTos.iterator();
						while (perioditr.hasNext()) {
							TimeTablePeriodTo periodTo =  perioditr .next();
							if(periodTo.getPeriodId()==timeTableForClassForm.getFromPeriodId()){
								if(periodTo.getSubjectList()==null && periodTo.getSubjectList().isEmpty()){
									throw new BusinessException();
								}else{
									subjectList=periodTo.getSubjectList();
									timeTableForClassForm.setSubjectListForCopy(subjectList);
								}
							}
						}
					}
				}
			}	
			Iterator<TimeTableClassTo> itr=classTos.iterator();
			while (itr.hasNext()) {
				TimeTableClassTo to= itr.next();
				if(to.getPosition()==timeTableForClassForm.getToDay()){
					List<TimeTablePeriodTo> timeTablePeriodTos=to.getTimeTablePeriodTos();
					Iterator<TimeTablePeriodTo> perioditr=timeTablePeriodTos.iterator();
					while (perioditr.hasNext()) {
						TimeTablePeriodTo periodTo =  perioditr .next();
						if(periodTo.getPeriodId()==timeTableForClassForm.getToPeriodId()){
							timeTableForClassForm.setPeriodName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(periodTo.getPeriodId(),"Period",true,"periodName"));
							Map<Integer,Map<Integer, String>> batchMap=timeTableForClassForm.getBatchMap();
							Map<Integer,Map<Integer, String>> activityBatchMap=timeTableForClassForm.getActivityBatchMap();
							Map<Integer,Map<Integer, String>> activityMap=timeTableForClassForm.getActivityMap();
							Map<Integer, String> map=null;
							if(periodTo.getSubjectList()!=null && !periodTo.getSubjectList().isEmpty()){
								subjectList=periodTo.getSubjectList();
								Iterator<TTSubjectBatchTo> subjectitr=subjectList.iterator();
								while (subjectitr.hasNext()) {
									TTSubjectBatchTo subjectTo=subjectitr.next();
									TTSubjectBatchForCopyTo subjectListForCopyTo =new TTSubjectBatchForCopyTo();
									subjectListForCopyTo.setId(subjectTo.getId());
									subjectListForCopyTo.setIsActive(false);
									subjectListForCopyTo.setOrigIsActive(false);
									subjectListForCopyTo.setAttendanceType(subjectTo.getAttendanceType());
									subjectListForCopyTo.setAttendanceTypeId(subjectTo.getAttendanceTypeId());
									subjectListForCopyTo.setOrigAttendanceTypeId(subjectTo.getOrigAttendanceTypeId());
									subjectListForCopyTo.setOrigAttendanceTypeId(subjectTo.getOrigAttendanceTypeId());
									subjectListForCopyTo.setOrigAttendanceType(subjectTo.getOrigAttendanceType());
									subjectListForCopyTo.setAttTypeName(subjectTo.getAttTypeName());
									subjectListForCopyTo.setSubjectId(subjectTo.getSubjectId());
									subjectListForCopyTo.setOrigSubjectId(subjectTo.getOrigSubjectId());
									subjectListForCopyTo.setSubjectName(subjectTo.getSubjectName());
									subjectListForCopyTo.setActivityId(subjectTo.getActivityId());
									subjectListForCopyTo.setOrigActivityId(subjectTo.getOrigActivityId());
									subjectListForCopyTo.setActivityName(subjectTo.getActivityName());
									subjectListForCopyTo.setBatchId(subjectTo.getBatchId());
									subjectListForCopyTo.setOrigBatchId(subjectTo.getOrigBatchId());
									subjectListForCopyTo.setBatchName(subjectTo.getBatchName());
									subjectListForCopyTo.setRoomNo(subjectTo.getRoomNo());
									subjectListForCopyTo.setOrigRoomNo(subjectTo.getOrigRoomNo());
									subjectListForCopyTo.setDeleteCount(subjectTo.getDeleteCount());
									subjectListForCopyTo.setUserId(subjectTo.getUserId());
									subjectListForCopyTo.setOrigUserId(subjectTo.getOrigUserId());
									subjectListForCopyTo.setUserMap(subjectTo.getUserMap());
									subjectListForCopyTo.setUserName(subjectTo.getUserName());

									subjectListForCopy.add(subjectListForCopyTo);
								}
								subjectList=timeTableForClassForm.getSubjectListForCopy();
								Iterator<TTSubjectBatchTo> subjecitr=subjectList.iterator();
								while (subjecitr.hasNext()) {
									TTSubjectBatchTo subjectTo=subjecitr.next();
									TTSubjectBatchForCopyTo subjectListForCopyTo =new TTSubjectBatchForCopyTo();
									// subjectListForCopyTo.setId(subjectTo.getId());
									subjectListForCopyTo.setIsActive(subjectTo.getIsActive());
									subjectListForCopyTo.setOrigIsActive(subjectTo.getOrigIsActive());
									subjectListForCopyTo.setAttendanceType(subjectTo.getAttendanceType());
									subjectListForCopyTo.setAttendanceTypeId(subjectTo.getAttendanceTypeId());
									subjectListForCopyTo.setOrigAttendanceTypeId(subjectTo.getOrigAttendanceTypeId());
									subjectListForCopyTo.setOrigAttendanceTypeId(subjectTo.getOrigAttendanceTypeId());
									subjectListForCopyTo.setOrigAttendanceType(subjectTo.getOrigAttendanceType());
									subjectListForCopyTo.setAttTypeName(subjectTo.getAttTypeName());
									subjectListForCopyTo.setSubjectId(subjectTo.getSubjectId());
									subjectListForCopyTo.setOrigSubjectId(subjectTo.getOrigSubjectId());
									subjectListForCopyTo.setSubjectName(subjectTo.getSubjectName());
									subjectListForCopyTo.setActivityId(subjectTo.getActivityId());
									subjectListForCopyTo.setOrigActivityId(subjectTo.getOrigActivityId());
									subjectListForCopyTo.setActivityName(subjectTo.getActivityName());
									subjectListForCopyTo.setBatchId(subjectTo.getBatchId());
									subjectListForCopyTo.setOrigBatchId(subjectTo.getOrigBatchId());
									subjectListForCopyTo.setBatchName(subjectTo.getBatchName());
									subjectListForCopyTo.setRoomNo(subjectTo.getRoomNo());
									subjectListForCopyTo.setOrigRoomNo(subjectTo.getOrigRoomNo());
									subjectListForCopyTo.setDeleteCount(subjectTo.getDeleteCount());
									subjectListForCopyTo.setUserId(subjectTo.getUserId());
									subjectListForCopyTo.setOrigUserId(subjectTo.getOrigUserId());
									subjectListForCopyTo.setUserMap(subjectTo.getUserMap());
									subjectListForCopyTo.setUserName(subjectTo.getUserName());
									subjectListForCopy.add(subjectListForCopyTo);
								}
							}else{
								subjectList=timeTableForClassForm.getSubjectListForCopy();
								Iterator<TTSubjectBatchTo> subjecitr=subjectList.iterator();

								while (subjecitr.hasNext()) {
									TTSubjectBatchTo subjectTo=subjecitr.next();
									TTSubjectBatchForCopyTo subjectListForCopyTo =new TTSubjectBatchForCopyTo();
									// subjectListForCopyTo.setId(subjectTo.getId());
									subjectListForCopyTo.setIsActive(subjectTo.getIsActive());
									subjectListForCopyTo.setOrigIsActive(subjectTo.getOrigIsActive());
									subjectListForCopyTo.setAttendanceType(subjectTo.getAttendanceType());
									subjectListForCopyTo.setAttendanceTypeId(subjectTo.getAttendanceTypeId());
									subjectListForCopyTo.setOrigAttendanceTypeId(subjectTo.getOrigAttendanceTypeId());
									subjectListForCopyTo.setOrigAttendanceTypeId(subjectTo.getOrigAttendanceTypeId());
									subjectListForCopyTo.setOrigAttendanceType(subjectTo.getOrigAttendanceType());
									subjectListForCopyTo.setAttTypeName(subjectTo.getAttTypeName());
									subjectListForCopyTo.setSubjectId(subjectTo.getSubjectId());
									subjectListForCopyTo.setOrigSubjectId(subjectTo.getOrigSubjectId());
									subjectListForCopyTo.setSubjectName(subjectTo.getSubjectName());
									subjectListForCopyTo.setActivityId(subjectTo.getActivityId());
									subjectListForCopyTo.setOrigActivityId(subjectTo.getOrigActivityId());
									subjectListForCopyTo.setActivityName(subjectTo.getActivityName());
									subjectListForCopyTo.setBatchId(subjectTo.getBatchId());
									subjectListForCopyTo.setOrigBatchId(subjectTo.getOrigBatchId());
									subjectListForCopyTo.setBatchName(subjectTo.getBatchName());
									subjectListForCopyTo.setRoomNo(subjectTo.getRoomNo());
									subjectListForCopyTo.setOrigRoomNo(subjectTo.getOrigRoomNo());
									subjectListForCopyTo.setDeleteCount(subjectTo.getDeleteCount());
									subjectListForCopyTo.setUserId(subjectTo.getUserId());
									subjectListForCopyTo.setOrigUserId(subjectTo.getOrigUserId());
									subjectListForCopyTo.setUserMap(subjectTo.getUserMap());
									subjectListForCopyTo.setUserName(subjectTo.getUserName());
									subjectListForCopy.add(subjectListForCopyTo);
								}
							}
							ListIterator<TTSubjectBatchForCopyTo> itr1=subjectListForCopy.listIterator();
							while (itr1.hasNext()) {
								TTSubjectBatchForCopyTo to1=itr1.next();
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
							timeTableForClassForm.setTtPeriodWeekId(periodTo.getId());
							timeTableForClassForm.setPeriodId(periodTo.getPeriodId());
							timeTableForClassForm.setWeek(to.getWeek());
						}
					}
				}
			}
		}catch (Exception e) {
			throw new BusinessException();
		}
		return subjectListForCopy;
	}

	/**
	 * @param subjectList
	 * @param timeTableForClassForm
	 * @return
	 * @throws Exception
	 */
	public boolean addTimeTableForaPeriodForCopyPeriods(List<TTSubjectBatchForCopyTo> subjectList,TimeTableForClassForm timeTableForClassForm) throws Exception {
		List<TTSubjectBatch> boList=TimeTableForClassHelper.getInstance().convertTotoBoListForCopyPeriods(subjectList,timeTableForClassForm);
		ITimeTableForClassTransaction transaction=TimeTableForClassTransactionImpl.getInstance();
		return transaction.addTimeTableForaPeriod(boList,timeTableForClassForm);
	}
	public Map<Integer, String> getTeachersMap(String string) throws Exception {
		List<Object[]> objectList=null;
		objectList=TimeTableForClassTransactionImpl.getInstance().getTeachers(string);
		Map<Integer, String> teacherMap=TimeTableForClassHelper.getInstance().getTeachersMap(objectList);
		return teacherMap;
	}
	public boolean updatePeriodCourseWise(TimeTableForClassForm timeTableForClassForm) throws Exception {
		boolean result=false;
		ITimeTableForClassTransaction transaction=TimeTableForClassTransactionImpl.getInstance();
		String selectedClasses[] = timeTableForClassForm.getClasses();
		String selectedPeriod[] = timeTableForClassForm.getPeriods();
		String selectedWeeks[] = timeTableForClassForm.getWeeks();
		String selectedTeachers[] = timeTableForClassForm.getTeachers();
		List<TTUsers> availttList=new ArrayList<TTUsers>();
		List<TTClasses> avattclassesList=new ArrayList<TTClasses>();
		List<TTPeriodWeek> avaperiodweekList=new ArrayList<TTPeriodWeek>();
		List<TTSubjectBatch> avasubjbatchList=new ArrayList<TTSubjectBatch>();
		StringBuffer pBuffer=new StringBuffer("");
		String periodNameForQuery=null;
		Set<Integer> classesIdsSet = new HashSet<Integer>();
		for (int i = 0; i < selectedClasses.length; i++) {
			classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
		}
		Set<Integer> teachersIdsSet = new HashSet<Integer>();
		for (int i = 0; i < selectedTeachers.length; i++) {
			teachersIdsSet.add(Integer.parseInt(selectedTeachers[i]));
		}

		Set<Integer> selectedPeriodSet = new HashSet<Integer>();
		for (int i = 0; i < selectedPeriod.length; i++) {
			selectedPeriodSet.add(Integer.parseInt(selectedPeriod[i]));
		}
		Set<Integer> selectedWeeksSet = new HashSet<Integer>();
		for (int i = 0; i < selectedWeeks.length; i++) {
			selectedWeeksSet.add(Integer.parseInt(selectedWeeks[i]));
		}
		StringBuffer classesBuf=new StringBuffer("");
		for(Integer cls:classesIdsSet){
			classesBuf.append(cls+",");
		}
		classesBuf.deleteCharAt(classesBuf.length()-1);
		
		StringBuffer teachersbuf=new StringBuffer("");
		for(Integer teacher:teachersIdsSet){
			teachersbuf.append(teacher+",");
		}teachersbuf.deleteCharAt(teachersbuf.length()-1);
		
		
		StringBuffer weeksbuf=new StringBuffer("");
		for(Integer week:selectedWeeksSet){
			if (week==1) {
				weeksbuf.append("'Monday',");
			}
			else if (week==2) {
				weeksbuf.append("'Tuesday',");
			}
			else if (week==3) {
				weeksbuf.append("'Wednesday',");
			}
			else if (week==4) {
				weeksbuf.append("'Thursday',");
			}
			else if (week==5) {
				weeksbuf.append("'Friday',");
			}
			else if (week==6) {
				weeksbuf.append("'Saturday',");
			}
		}weeksbuf.deleteCharAt(weeksbuf.length()-1);

		for(Integer period:selectedPeriodSet){
			pBuffer.append("'Hour "+period+"',");
		}
		pBuffer.deleteCharAt(pBuffer.length()-1);
		//pBuffer.deleteCharAt(pBuffer.length()-1);
		periodNameForQuery=pBuffer.toString();
		
		List<Period> periodBo=transaction.getperiods(classesIdsSet,periodNameForQuery);
		
		String weeksForquery=weeksbuf.toString();
		String teachersidorQuery=teachersbuf.toString();
		String classIds=classesBuf.toString();
		String ttusQuery="from TTUsers ts where ts.isActive=true and ts.ttSubjectBatch.ttPeriodWeek.weekDay in("+ weeksForquery+") and ts.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.id in("+classIds+")";
		String ttclsQuery="from TTClasses ts where ts.isActive=true and ts.classSchemewise.id in("+classIds+")";
		String ttcsubatchQuery="from TTSubjectBatch ts where ts.isActive=true and ts.ttPeriodWeek.weekDay in("+ weeksForquery+") and ts.ttPeriodWeek.ttClasses.classSchemewise.id in("+classIds+")";
		String ttperiodweek="from TTPeriodWeek ts where ts.isActive=true and ts.weekDay in("+ weeksForquery+") and ts.ttClasses.classSchemewise.id in("+classIds+")";
		availttList=transaction.getAvailableTimetable(ttusQuery);
		avattclassesList=transaction.getAvailableTimetable(ttclsQuery);
		avasubjbatchList=transaction.getAvailableTimetable(ttcsubatchQuery);
		avaperiodweekList=transaction.getAvailableTimetable(ttperiodweek);
		result=TimeTableForClassTransactionImpl.getInstance().saveAttendanceBo(periodBo, timeTableForClassForm,selectedWeeksSet,classesIdsSet,teachersIdsSet,availttList,avattclassesList,avasubjbatchList,avaperiodweekList);
		return result;
	}
}

