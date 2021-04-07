package com.kp.cms.helpers.timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TTClasses;
import com.kp.cms.bo.admin.TTClassesHistory;
import com.kp.cms.bo.admin.TTPeriodWeek;
import com.kp.cms.bo.admin.TTPeriodWeekHistory;
import com.kp.cms.bo.admin.TTSubjectBatch;
import com.kp.cms.bo.admin.TTSubjectBatchHistory;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.bo.admin.TTUsersHistory;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.timetable.TimeTableForClassForm;
import com.kp.cms.to.attendance.TeacherDepartmentTO;
import com.kp.cms.to.timetable.TTSubjectBatchForCopyTo;
import com.kp.cms.to.timetable.TTSubjectBatchTo;
import com.kp.cms.to.timetable.TimeTableClassTo;
import com.kp.cms.to.timetable.TimeTablePeriodTo;
import com.kp.cms.transactions.attandance.ITeacherDepartmentEntryTransaction;
import com.kp.cms.transactions.timetable.ITimeTableForClassTransaction;
import com.kp.cms.transactionsimpl.attendance.TeacherDepartmentEntryTransactionImpl;
import com.kp.cms.transactionsimpl.timetable.TimeTableForClassTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class TimeTableForClassHelper {
	static int count=0;
	/**
	 * Singleton object of TimeTableForClassHelper
	 */
	private static volatile TimeTableForClassHelper timeTableForClassHelper = null;
	private static final Log log = LogFactory.getLog(TimeTableForClassHelper.class);
	private TimeTableForClassHelper() {

	}
	/**
	 * return singleton object of TimeTableForClassHelper.
	 * @return
	 */
	public static TimeTableForClassHelper getInstance() {
		if (timeTableForClassHelper == null) {
			timeTableForClassHelper = new TimeTableForClassHelper();
		}
		return timeTableForClassHelper;
	}
	/**
	 * @param boList
	 * @return
	 */
	public Map<String, Map<Integer, TimeTablePeriodTo>> convertBoListToMap( List<TTClasses> boList,TimeTableForClassForm timeTableForClassForm) throws Exception {
		log.info("Entered Into convertBoListToMap");
		Map<String, Map<Integer, TimeTablePeriodTo>> map=new HashMap<String, Map<Integer,TimeTablePeriodTo>>();
		Map<Integer, TimeTablePeriodTo> periodMap=null;
		TimeTablePeriodTo to=null;
		List<TTSubjectBatchTo> subjectList=null;
		TTSubjectBatchTo suBatchTo=null;
		String periodName="";   //added for to display period name when all records are deleted 
		if(boList!=null && !boList.isEmpty()){
			Iterator<TTClasses> itr=boList.iterator();
			if(itr.hasNext()) {
				TTClasses bo=itr.next();
				timeTableForClassForm.setTtClassId(bo.getId());
				if(bo.getIsApproved()!=null && bo.getIsApproved())
					timeTableForClassForm.setFinalApprove("on");
				else
					timeTableForClassForm.setFinalApprove("off");
				Set<TTPeriodWeek> periodSet=bo.getTtPeriodWeeks();
				if(periodSet!=null && !periodSet.isEmpty()){
					Iterator<TTPeriodWeek> periodItr=periodSet.iterator();
					while (periodItr.hasNext()) {
						TTPeriodWeek periodBo= periodItr .next();

						if(map.containsKey(periodBo.getWeekDay()))
							periodMap=map.remove(periodBo.getWeekDay());
						else
							periodMap=new HashMap<Integer, TimeTablePeriodTo>();

						if(periodMap.containsKey(periodBo.getPeriod().getId()))
							to=periodMap.remove(periodBo.getPeriod().getId());
						else{
							to=new TimeTablePeriodTo();
							to.setId(periodBo.getId());
						}
						if(to.getSubjectList()!=null)
							subjectList=to.getSubjectList();
						else
							subjectList=new ArrayList<TTSubjectBatchTo>();

						StringBuffer subjectNames=new StringBuffer();

						Set<TTSubjectBatch> subjectSet=periodBo.getTtSubjectBatchs();
						if(subjectSet!=null && !subjectSet.isEmpty()){
							Iterator<TTSubjectBatch> subjectItr=subjectSet.iterator();
							int deleteCount=1;
							while (subjectItr.hasNext()) {
								TTSubjectBatch subjectBatchBo =subjectItr .next();

								if(subjectBatchBo.getIsActive()){
									if(subjectNames.length()!=0)
										subjectNames.append(",");
									if(subjectBatchBo.getSubject()!=null)
										subjectNames.append(subjectBatchBo.getSubject().getCode());
									else if(subjectBatchBo.getActivity()!=null)
										subjectNames.append(subjectBatchBo.getActivity().getName());
								}	
								//added for to display period name when all records are deleted 
								else
								{  
									if(subjectNames.length()==0)	
										periodName=(periodBo.getPeriod().getPeriodName());
								}
								suBatchTo=new TTSubjectBatchTo();
								if(subjectBatchBo.getIsActive()) //added for bug fixed to take only active records
								{
									suBatchTo.setId(subjectBatchBo.getId());
									suBatchTo.setDeleteCount(deleteCount);
									suBatchTo.setIsActive(subjectBatchBo.getIsActive());
									suBatchTo.setOrigIsActive(subjectBatchBo.getIsActive());
									deleteCount++;
									if(subjectBatchBo.getAttendanceType()!=null){
										suBatchTo.setAttendanceType(String.valueOf(subjectBatchBo.getAttendanceType().getId()));
										suBatchTo.setAttendanceTypeId(String.valueOf(subjectBatchBo.getAttendanceType().getId()));
										suBatchTo.setOrigAttendanceTypeId(String.valueOf(subjectBatchBo.getAttendanceType().getId()));
										suBatchTo.setOrigAttendanceType(String.valueOf(subjectBatchBo.getAttendanceType().getId()));
										suBatchTo.setAttTypeName(subjectBatchBo.getAttendanceType().getName());
									}
									if(subjectBatchBo.getSubject()!=null){
										suBatchTo.setSubjectId(String.valueOf(subjectBatchBo.getSubject().getId()));
										suBatchTo.setOrigSubjectId(String.valueOf(subjectBatchBo.getSubject().getId()));
										suBatchTo.setSubjectName(subjectBatchBo.getSubject().getCode());
									}
									if(subjectBatchBo.getActivity()!=null){
										suBatchTo.setActivityId(String.valueOf(subjectBatchBo.getActivity().getId()));
										suBatchTo.setOrigActivityId(String.valueOf(subjectBatchBo.getActivity().getId()));
										suBatchTo.setActivityName(subjectBatchBo.getActivity().getName());
									}
									if(subjectBatchBo.getBatch()!=null){
										suBatchTo.setBatchId(String.valueOf(subjectBatchBo.getBatch().getId()));
										suBatchTo.setOrigBatchId(String.valueOf(subjectBatchBo.getBatch().getId()));
										suBatchTo.setBatchName(subjectBatchBo.getBatch().getBatchName());
									}
									suBatchTo.setRoomNo(subjectBatchBo.getRoomNo());
									suBatchTo.setOrigRoomNo(subjectBatchBo.getRoomNo());
									suBatchTo.setIsActive(subjectBatchBo.getIsActive());
									Map<Integer,Integer> userMap=new HashMap<Integer, Integer>();
									Set<TTUsers> userSet=subjectBatchBo.getTtUsers();
									String userName="";
									if(userSet!=null && !userSet.isEmpty()){
										Iterator<TTUsers> userItr=userSet.iterator();
										List<String> userList=new ArrayList<String>();
										while (userItr.hasNext()) {
											TTUsers ttUsers = userItr .next();
											userMap.put(ttUsers.getUsers().getId(),ttUsers.getId());
											if(userName.isEmpty())
												userName=((ttUsers.getUsers().getEmployee() != null) ? ttUsers.getUsers().getEmployee().getFirstName() : (ttUsers.getUsers().getGuest() != null) ? ttUsers.getUsers().getGuest().getFirstName() : "");
											else
												userName=userName + "," + ((ttUsers.getUsers().getEmployee() != null) ? ttUsers.getUsers().getEmployee().getFirstName() : (ttUsers.getUsers().getGuest() != null) ? ttUsers.getUsers().getGuest().getFirstName() : "");
											userList.add(String.valueOf(ttUsers.getUsers().getId()));
										}
										String[] userids=new String[userList.size()];
										Iterator<String> periodSetIterator = userList.iterator();
										int count = 0;
										while (periodSetIterator.hasNext()) {
											userids[count++] = periodSetIterator.next();
										}
										suBatchTo.setUserId(userids);
										suBatchTo.setOrigUserId(userids);
										suBatchTo.setUserMap(userMap);
										suBatchTo.setUserName(userName);
									}
									//raghu
									else{
										String[] userids=new String[1];
										userids[0] = timeTableForClassForm.getUserId();
										userName=timeTableForClassForm.getEmpName();
										suBatchTo.setUserId(userids);
										suBatchTo.setOrigUserId(userids);
										suBatchTo.setUserName(userName);
									}

									subjectList.add(suBatchTo);
								}
							}
						}

						to.setSubjectList(subjectList);
						if(subjectNames.toString()!=null && !subjectNames.toString().isEmpty())
							to.setPeriodName(subjectNames.toString());
						else
							to.setPeriodName(periodName);	
						to.setStartTime(periodBo.getPeriod().getStartTime());
						to.setPeriodId(periodBo.getPeriod().getId());
						periodMap.put(periodBo.getPeriod().getId(), to);
						map.put(periodBo.getWeekDay(),periodMap);
					}
				}
			}
		}
		log.info("Exit From convertBoListToMap");
		return map;
	}
	/**
	 * @param timeTableForClassForm
	 * @param ttMap
	 * @return
	 */
	public List<TimeTableClassTo> getTimeTableForInputClass( TimeTableForClassForm timeTableForClassForm, Map<String, Map<Integer, TimeTablePeriodTo>> ttMap,List<Period> periodList) throws Exception {
		count=0;
		List<TimeTableClassTo> classTos=new ArrayList<TimeTableClassTo>();
		TimeTableClassTo classTo=new TimeTableClassTo();
		classTo.setPosition(1);
		classTo.setWeek("Monday");
		setPeriodListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
		classTos.add(classTo);
		classTo=new TimeTableClassTo();
		classTo.setPosition(2);
		classTo.setWeek("Tuesday");
		setPeriodListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
		classTos.add(classTo);
		classTo=new TimeTableClassTo();
		classTo.setPosition(3);
		classTo.setWeek("Wednesday");
		setPeriodListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
		classTos.add(classTo);
		classTo=new TimeTableClassTo();
		classTo.setPosition(4);
		classTo.setWeek("Thursday");
		setPeriodListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
		classTos.add(classTo);
		classTo=new TimeTableClassTo();
		classTo.setPosition(5);
		classTo.setWeek("Friday");
		setPeriodListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
		classTos.add(classTo);
		classTo=new TimeTableClassTo();
		classTo.setPosition(6);
		classTo.setWeek("Saturday");
		setPeriodListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
		classTos.add(classTo);
		//raghu
		//classTo=new TimeTableClassTo();
		//classTo.setPosition(7);
		//classTo.setWeek("Sunday");
		//setPeriodListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
		//classTos.add(classTo);
		return classTos;
	}
	/**
	 * @param map
	 * @param classTo
	 */
	private void setPeriodListToClassTo(Map<Integer, TimeTablePeriodTo> map, TimeTableClassTo classTo,List<Period> periodList) throws Exception {
		if(periodList!=null && !periodList.isEmpty()){
			Iterator<Period> itr=periodList.iterator();
			TimeTablePeriodTo to=null;
			List<TimeTablePeriodTo> periodToList=new ArrayList<TimeTablePeriodTo>();
			while (itr.hasNext()) {
				Period period = itr.next();
				count++;
				if(map!=null && map.containsKey(period.getId())){
					to=map.get(period.getId());
				}else{
					to=new TimeTablePeriodTo();
					to.setPeriodId(period.getId());
					to.setPeriodName(period.getPeriodName());
					to.setStartTime(period.getStartTime());
				}
				to.setCount(count);
				periodToList.add(to);
			}
			Collections.sort(periodToList);
			classTo.setTimeTablePeriodTos(periodToList);
		}
	}
	/**
	 * @param subjectList
	 * @param timeTableForClassForm
	 * @return
	 * @throws Exception
	 */
	public List<TTSubjectBatch> convertTotoBoList( List<TTSubjectBatchTo> subjectList, TimeTableForClassForm timeTableForClassForm) throws Exception {
		List<TTSubjectBatch> boList=new ArrayList<TTSubjectBatch>();
		TTSubjectBatch bo=null;
		TTSubjectBatchTo to=null;
		Iterator<TTSubjectBatchTo> itr=subjectList.iterator();
		Map<Integer,Integer> ttUsersId=null;
		boolean proceed=false;
		boolean changed=false;
		while (itr.hasNext()) {
			to= (TTSubjectBatchTo) itr.next();
			proceed=true;
			if(!to.getIsActive() && to.getId()==0)
				proceed=false;
			if(proceed){
				if(timeTableForClassForm.getFinalApprove().equalsIgnoreCase("on")){
					if(to.getId()==0){
						changed=true;
					}else{
						if(to.getRoomNo()!=null && to.getOrigRoomNo()!=null){
							if(!to.getRoomNo().equals(to.getOrigRoomNo()))
								changed=true;
						}
						if(to.getSubjectId()!=null && to.getOrigSubjectId()!=null){
							if(!to.getSubjectId().equals(to.getOrigSubjectId()))
								changed=true;
						}
						if(to.getBatchId()!=null && to.getOrigBatchId()!=null){
							if(!to.getBatchId().equals(to.getOrigBatchId()))
								changed=true;
						}
						if(to.getAttendanceTypeId()!=null && to.getOrigAttendanceTypeId()!=null){
							if(!to.getAttendanceTypeId().equals(to.getOrigAttendanceTypeId()))
								changed=true;
						}
						if(to.getUserId()!=null && to.getOrigUserId()!=null){
							if(!Arrays.equals(to.getUserId(),to.getOrigUserId()))
								changed=true;
						}
						if(to.getIsActive()!=null && to.getOrigIsActive()!=null){
							if(to.getIsActive()!=to.getOrigIsActive())
								changed=true;
						}
						if(to.getActivityId()!=null && to.getOrigActivityId()!=null){
							if(!to.getActivityId().equals(to.getOrigActivityId()))
								changed=true;
						}

					}
				}

				bo=new TTSubjectBatch();
				if(to.getId()>0)
					bo.setId(to.getId());
				if(to.getBatchId()!=null && !to.getBatchId().isEmpty()){
					Batch batch=new Batch();
					batch.setId(Integer.parseInt(to.getBatchId()));
					bo.setBatch(batch);
				}
				if(to.getSubjectId()!=null && !to.getSubjectId().isEmpty()){
					Subject subject=new Subject();
					subject.setId(Integer.parseInt(to.getSubjectId()));
					bo.setSubject(subject);
				}
				if(to.getAttendanceTypeId()!=null && !to.getAttendanceTypeId().isEmpty()){
					AttendanceType attendanceType=new AttendanceType();
					attendanceType.setId(Integer.parseInt(to.getAttendanceTypeId()));
					bo.setAttendanceType(attendanceType);
				}
				if(to.getActivityId()!=null && !to.getActivityId().isEmpty()){
					Activity activity=new Activity();
					activity.setId(Integer.parseInt(to.getActivityId()));
					bo.setActivity(activity);
				}
				bo.setIsActive(to.getIsActive());
				bo.setCreatedBy(timeTableForClassForm.getUserId());
				bo.setCreatedDate(new Date());
				bo.setLastModifiedDate(new Date());
				bo.setModifiedBy(timeTableForClassForm.getUserId());
				bo.setRoomNo(to.getRoomNo());
				Set<TTUsers> ttUsers=new HashSet<TTUsers>();

				ttUsersId=to.getUserMap();
				if(to.getUserId()!=null){
					String[] users=to.getUserId();
					for (int i = 0; i < users.length; i++) {
						TTUsers userBo=new TTUsers();
						userBo.setIsActive(true);
						userBo.setCreatedBy(timeTableForClassForm.getUserId());
						userBo.setCreatedDate(new Date());
						userBo.setLastModifiedDate(new Date());
						userBo.setModifiedBy(timeTableForClassForm.getUserId());
						if(ttUsersId!=null &&  ttUsersId.containsKey(Integer.parseInt(users[i]))){
							userBo.setId(ttUsersId.get(Integer.parseInt(users[i])));
						}
						Users user=new Users();
						user.setId(Integer.parseInt(users[i]));
						userBo.setUsers(user);
						ttUsers.add(userBo);
					}
					bo.setTtUsers(ttUsers);
				}
				boList.add(bo);
			}
		}
		timeTableForClassForm.setChanged(changed);
		return boList;
	}
	/**
	 * Converting the TTClassHistory BO to TO 
	 * @param boList
	 * @param timeTableForClassForm
	 * @return
	 * @throws Exception
	 */
	public Map<String,Map<String, Map<Integer, TimeTablePeriodTo>>> convertHistoryBoListToMap(List<TTClassesHistory> boList,
			TimeTableForClassForm timeTableForClassForm) throws Exception {
		log.info("Entered Into convertBoListToMap");
		Map<String,Map<String, Map<Integer, TimeTablePeriodTo>>> hismap=new TreeMap<String,Map<String, Map<Integer,TimeTablePeriodTo>>>();
		Map<String, Map<Integer, TimeTablePeriodTo>> map=null;
		Map<Integer, TimeTablePeriodTo> periodMap=null;
		TimeTablePeriodTo to=null;
		if(boList!=null && !boList.isEmpty()){
			Iterator<TTClassesHistory> itr=boList.iterator();
			while(itr.hasNext()) {
				TTClassesHistory bo=itr.next();

				if(hismap.containsKey(CommonUtil.formatSqlDateTimeToString(bo.getDate().toString())))
					map=hismap.get(CommonUtil.formatSqlDateTimeToString(bo.getDate().toString()));
				else
					map=new HashMap<String, Map<Integer,TimeTablePeriodTo>>();

				Set<TTPeriodWeekHistory> periodSet=bo.getTtPeriodWeeksHistory();
				if(periodSet!=null && !periodSet.isEmpty()){
					Iterator<TTPeriodWeekHistory> periodItr=periodSet.iterator();
					while (periodItr.hasNext()) {
						TTPeriodWeekHistory periodBo= periodItr .next();

						if(map.containsKey(periodBo.getWeekDay()))
							periodMap=map.remove(periodBo.getWeekDay());
						else
							periodMap=new HashMap<Integer, TimeTablePeriodTo>();

						if(periodMap.containsKey(periodBo.getPeriod().getId()))
							to=periodMap.remove(periodBo.getPeriod().getId());
						else{
							to=new TimeTablePeriodTo();
							//	to.setId(periodBo.getId());
						}
						StringBuffer subjectNames=new StringBuffer();
						List<TTSubjectBatchTo> subjectList=new ArrayList<TTSubjectBatchTo>();
						TTSubjectBatchTo suBatchTo=null;
						Set<TTSubjectBatchHistory> subjectSet=periodBo.getTtSubjectBatchsHistory();
						if(subjectSet!=null && !subjectSet.isEmpty()){
							Iterator<TTSubjectBatchHistory> subjectItr=subjectSet.iterator();
							while (subjectItr.hasNext()) {
								TTSubjectBatchHistory subjectBatchBo =subjectItr .next();

								if(subjectBatchBo.getIsActive()){
									if(subjectNames.length()!=0)
										subjectNames.append(",");
									subjectNames.append(subjectBatchBo.getSubject().getCode());
								}
								suBatchTo=new TTSubjectBatchTo();
								if(subjectBatchBo.getAttendanceType()!=null){
									suBatchTo.setAttTypeName(subjectBatchBo.getAttendanceType().getName());
								}
								if(subjectBatchBo.getSubject()!=null){
									suBatchTo.setSubjectName(subjectBatchBo.getSubject().getCode());
								}
								if(subjectBatchBo.getActivity()!=null){
									suBatchTo.setActivityName(subjectBatchBo.getActivity().getName());
								}
								if(subjectBatchBo.getBatch()!=null){
									suBatchTo.setBatchName(subjectBatchBo.getBatch().getBatchName());
								}
								suBatchTo.setRoomNo(subjectBatchBo.getRoomNo());
								Set<TTUsersHistory> userSet=subjectBatchBo.getTtUsersHistory();
								String userName="";
								if(userSet!=null && !userSet.isEmpty()){
									Iterator<TTUsersHistory> userItr=userSet.iterator();
									while (userItr.hasNext()) {
										TTUsersHistory ttUsers = userItr .next();
										if(userName.isEmpty())
											userName=ttUsers.getUsers().getEmployee().getFirstName();
										else
											userName=userName+","+ttUsers.getUsers().getEmployee().getFirstName();
									}
								}
								suBatchTo.setUserName(userName);
								subjectList.add(suBatchTo);
							}
							to.setSubjectList(subjectList);
							to.setPeriodName(subjectNames.toString());
							to.setStartTime(periodBo.getPeriod().getStartTime());
							periodMap.put(periodBo.getPeriod().getId(), to);
							map.put(periodBo.getWeekDay(),periodMap);
						}
					}
					hismap.put(CommonUtil.formatSqlDateTimeToString(bo.getDate().toString()), map);
				}
			}
		}
		log.info("Exit From convertBoListToMap");
		return hismap;

	}
	/**
	 * @param timeTableForClassForm
	 * @param ttMap
	 * @param periodList
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<TimeTableClassTo>> getTimeTableHistoryForInputClass(TimeTableForClassForm timeTableForClassForm,
			Map<String,Map<String, Map<Integer, TimeTablePeriodTo>>> hisMap,List<Period> periodList) throws Exception {
		Map<String,List<TimeTableClassTo>> dateMap=new HashMap<String, List<TimeTableClassTo>>();
		for (Map.Entry<String,Map<String, Map<Integer, TimeTablePeriodTo>>> entry : hisMap.entrySet()) {
			Map<String, Map<Integer, TimeTablePeriodTo>> ttMap=entry.getValue();
			count=0;
			List<TimeTableClassTo> classTos=new ArrayList<TimeTableClassTo>();
			TimeTableClassTo classTo=new TimeTableClassTo();
			classTo.setPosition(1);
			classTo.setWeek("Monday");
			setPeriodHistoryListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
			classTos.add(classTo);
			classTo=new TimeTableClassTo();
			classTo.setPosition(2);
			classTo.setWeek("Tuesday");
			setPeriodHistoryListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
			classTos.add(classTo);
			classTo=new TimeTableClassTo();
			classTo.setPosition(3);
			classTo.setWeek("Wednesday");
			setPeriodHistoryListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
			classTos.add(classTo);
			classTo=new TimeTableClassTo();
			classTo.setPosition(4);
			classTo.setWeek("Thursday");
			setPeriodHistoryListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
			classTos.add(classTo);
			classTo=new TimeTableClassTo();
			classTo.setPosition(5);
			classTo.setWeek("Friday");
			setPeriodHistoryListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
			classTos.add(classTo);
			classTo=new TimeTableClassTo();
			classTo.setPosition(6);
			classTo.setWeek("Saturday");
			setPeriodHistoryListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
			classTos.add(classTo);
			//raghu
			//classTo=new TimeTableClassTo();
			//classTo.setPosition(7);
			//classTo.setWeek("Sunday");
			//setPeriodHistoryListToClassTo(ttMap.get(classTo.getWeek()),classTo,periodList);
			//classTos.add(classTo);
			dateMap.put(entry.getKey(), classTos);
		}

		return dateMap;
	}

	/**
	 * @param map
	 * @param classTo
	 */
	private void setPeriodHistoryListToClassTo(Map<Integer, TimeTablePeriodTo> map, TimeTableClassTo classTo,List<Period> periodList) throws Exception {
		if(periodList!=null && !periodList.isEmpty()){
			Iterator<Period> itr=periodList.iterator();
			TimeTablePeriodTo to=null;
			List<TimeTablePeriodTo> periodToList=new ArrayList<TimeTablePeriodTo>();
			while (itr.hasNext()) {
				Period period = itr.next();
				count++;
				if(map!=null && map.containsKey(period.getId())){
					to=map.get(period.getId());
				}else{
					to=new TimeTablePeriodTo();
					to.setPeriodId(period.getId());
					to.setPeriodName(period.getPeriodName());
					to.setStartTime(period.getStartTime());
				}
				to.setCount(count);
				periodToList.add(to);
			}
			Collections.sort(periodToList);
			classTo.setTimeTablePeriodTos(periodToList);
		}
	}

	public List<TTSubjectBatch> convertTotoBoListForCopyPeriods( List<TTSubjectBatchForCopyTo> subjectList, TimeTableForClassForm timeTableForClassForm) throws Exception {
		List<TTSubjectBatch> boList=new ArrayList<TTSubjectBatch>();
		TTSubjectBatch bo=null;
		TTSubjectBatchForCopyTo to=null;
		Iterator<TTSubjectBatchForCopyTo> itr=subjectList.iterator();
		Map<Integer,Integer> ttUsersId=null;
		boolean proceed=false;
		boolean changed=false;
		while (itr.hasNext()) {
			to= (TTSubjectBatchForCopyTo) itr.next();
			proceed=true;
			if(!to.getIsActive() && to.getId()==0)
				proceed=false;
			if(proceed){
				if(timeTableForClassForm.getFinalApprove().equalsIgnoreCase("on")){
					if(to.getId()==0){
						changed=true;
					}
				}

				bo=new TTSubjectBatch();
				if(to.getId()>0)
					bo.setId(to.getId());
				if(to.getBatchId()!=null && !to.getBatchId().isEmpty()){
					Batch batch=new Batch();
					batch.setId(Integer.parseInt(to.getBatchId()));
					bo.setBatch(batch);
				}
				if(to.getSubjectId()!=null && !to.getSubjectId().isEmpty()){
					Subject subject=new Subject();
					subject.setId(Integer.parseInt(to.getSubjectId()));
					bo.setSubject(subject);
				}
				if(to.getAttendanceTypeId()!=null && !to.getAttendanceTypeId().isEmpty()){
					AttendanceType attendanceType=new AttendanceType();
					attendanceType.setId(Integer.parseInt(to.getAttendanceTypeId()));
					bo.setAttendanceType(attendanceType);
				}
				if(to.getActivityId()!=null && !to.getActivityId().isEmpty()){
					Activity activity=new Activity();
					activity.setId(Integer.parseInt(to.getActivityId()));
					bo.setActivity(activity);
				}
				bo.setIsActive(to.getIsActive());
				bo.setCreatedBy(timeTableForClassForm.getUserId());
				bo.setCreatedDate(new Date());
				bo.setLastModifiedDate(new Date());
				bo.setModifiedBy(timeTableForClassForm.getUserId());
				bo.setRoomNo(to.getRoomNo());

				Set<TTUsers> ttUsers=new HashSet<TTUsers>();
				if(to.getId() >0){
					ttUsersId=to.getUserMap();
					if(to.getUserId()!=null){
						String[] users=to.getUserId();
						for (int i = 0; i < users.length; i++) {
							TTUsers userBo=new TTUsers();
							userBo.setIsActive(true);
							userBo.setCreatedBy(timeTableForClassForm.getUserId());
							userBo.setCreatedDate(new Date());
							userBo.setLastModifiedDate(new Date());
							userBo.setModifiedBy(timeTableForClassForm.getUserId());
							if(ttUsersId!=null &&  ttUsersId.containsKey(Integer.parseInt(users[i]))){
								userBo.setId(ttUsersId.get(Integer.parseInt(users[i])));
							}
							Users user=new Users();
							user.setId(Integer.parseInt(users[i]));
							userBo.setUsers(user);
							ttUsers.add(userBo);
						}
						bo.setTtUsers(ttUsers);

					}
					boList.add(bo);
				}
				else{
					ttUsersId=to.getUserMap();
					if(to.getUserId()!=null){
						String[] users=to.getUserId();
						for (int i = 0; i < users.length; i++) {
							TTUsers userBo=new TTUsers();
							userBo.setIsActive(true);
							userBo.setCreatedBy(timeTableForClassForm.getUserId());
							userBo.setCreatedDate(new Date());
							userBo.setLastModifiedDate(new Date());
							userBo.setModifiedBy(timeTableForClassForm.getUserId());
							//modified by chandra
							/*if(ttUsersId!=null &&  ttUsersId.containsKey(Integer.parseInt(users[i]))){
							userBo.setId(ttUsersId.get(Integer.parseInt(users[i]))+1);
						}*/
							Users user=new Users();
							user.setId(Integer.parseInt(users[i]));
							userBo.setUsers(user);
							ttUsers.add(userBo);
						}
						bo.setTtUsers(ttUsers);
					}
					boList.add(bo);
				}
			}
		}
		if(timeTableForClassForm.getFinalApprove().equalsIgnoreCase("on")){
			if(!boList.isEmpty()){
				timeTableForClassForm.setChanged(true);
			}
		}

		return boList;
	}
	public Map<Integer, String> getTeachersMap(List<Object[]> teacherDepartmentList) throws Exception {
		Map<Integer, String> teacherMap = new LinkedHashMap<Integer, String>();
		Iterator<Object[]> itr=teacherDepartmentList.iterator();
		while(itr.hasNext()){
			Object[] tDepartment=(Object[])itr.next();
			int userId=Integer.parseInt(tDepartment[0].toString());
			String name=tDepartment[1].toString();
			teacherMap.put(userId, name);
			teacherMap = (Map<Integer, String>) CommonUtil.sortMapByValue(teacherMap);
			
	}
		return teacherMap;
}
}