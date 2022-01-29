package com.kp.cms.transactionsimpl.timetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.TTClasses;
import com.kp.cms.bo.admin.TTClassesHistory;
import com.kp.cms.bo.admin.TTPeriodWeek;
import com.kp.cms.bo.admin.TTPeriodWeekHistory;
import com.kp.cms.bo.admin.TTSubjectBatch;
import com.kp.cms.bo.admin.TTSubjectBatchHistory;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.bo.admin.TTUsersHistory;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.timetable.TimeTableForClassForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.timetable.ITimeTableForClassTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class TimeTableForClassTransactionImpl implements
ITimeTableForClassTransaction {
	/**
	 * Singleton object of TimeTableForClassTransactionImpl
	 */
	private static volatile TimeTableForClassTransactionImpl timeTableForClassTransactionImpl = null;
	private static final Log log = LogFactory.getLog(TimeTableForClassTransactionImpl.class);
	private TimeTableForClassTransactionImpl() {

	}
	/**
	 * return singleton object of TimeTableForClassTransactionImpl.
	 * @return
	 */
	public static TimeTableForClassTransactionImpl getInstance() {
		if (timeTableForClassTransactionImpl == null) {
			timeTableForClassTransactionImpl = new TimeTableForClassTransactionImpl();
		}
		return timeTableForClassTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.timetable.ITimeTableForClassTransaction#addTimeTableForaPeriod(java.util.List, com.kp.cms.forms.timetable.TimeTableForClassForm)
	 */
	public boolean addTimeTableForaPeriod(List<TTSubjectBatch> boList, TimeTableForClassForm timeTableForClassForm) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		TTSubjectBatch subjectBatch;
		try {
			if(timeTableForClassForm.getTtClassId()>0 && timeTableForClassForm.isChanged() && timeTableForClassForm.getFinalApprove().equalsIgnoreCase("on"))
				saveHistory(timeTableForClassForm.getTtClassId());
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();


			TTPeriodWeek periodBo=null;
			TTClasses ttClasses=null;
			if(timeTableForClassForm.getTtClassId()>0){
				ttClasses=(TTClasses)session.get(TTClasses.class,timeTableForClassForm.getTtClassId());
				ttClasses.setLastModifiedDate(new Date());
				ttClasses.setModifiedBy(timeTableForClassForm.getUserId());
				//ra
				//if(timeTableForClassForm.getTtClassId()>0 && timeTableForClassForm.isChanged() && timeTableForClassForm.getFinalApprove().equalsIgnoreCase("on"))
				//ttClasses.setIsApproved(false);
				ttClasses.setIsApproved(true);
				//raghu
				session.update(ttClasses);
			}else{
				ttClasses=new TTClasses();
				ClassSchemewise classSchemewise=new ClassSchemewise();
				classSchemewise.setId(Integer.parseInt(timeTableForClassForm.getClassId()));
				ttClasses.setClassSchemewise(classSchemewise);
				ttClasses.setCreatedBy(timeTableForClassForm.getUserId());
				ttClasses.setCreatedDate(new Date());
				ttClasses.setLastModifiedDate(new Date());
				ttClasses.setModifiedBy(timeTableForClassForm.getUserId());
				//ttClasses.setIsApproved(false);
				//ra
				ttClasses.setIsApproved(true);
				ttClasses.setIsActive(true);
				session.save(ttClasses);
			}

			if(timeTableForClassForm.getTtPeriodWeekId()>0){
				periodBo=(TTPeriodWeek)session.get(TTPeriodWeek.class, timeTableForClassForm.getTtPeriodWeekId());
				periodBo.setLastModifiedDate(new Date());
				periodBo.setModifiedBy(timeTableForClassForm.getUserId());
				session.update(periodBo);
			}else{
				periodBo=new TTPeriodWeek();
				Period period=new Period();
				period.setId(timeTableForClassForm.getPeriodId());
				periodBo.setPeriod(period);
				periodBo.setWeekDay(timeTableForClassForm.getWeek());
				periodBo.setLastModifiedDate(new Date());
				periodBo.setModifiedBy(timeTableForClassForm.getUserId());
				periodBo.setCreatedDate(new Date());
				periodBo.setCreatedBy(timeTableForClassForm.getUserId());
				periodBo.setIsActive(true);
				periodBo.setTtClasses(ttClasses);
				session.save(periodBo);
			}
			transaction.commit();

			Iterator<TTSubjectBatch> tcIterator = boList.iterator();
			int count = 0;
			Session session1=null;
			session1 = InitSessionFactory.getInstance().openSession();
			Transaction transaction1 = session1.beginTransaction();
			transaction1.begin();
			while(tcIterator.hasNext()){
				subjectBatch = tcIterator.next();
				subjectBatch.setTtPeriodWeek(periodBo);
				session1.saveOrUpdate(subjectBatch);
				if(++count % 20 == 0){
					session1.flush();
					session1.clear();
				}
			}
			transaction1.commit();
			session.flush();
			session.close();
			session1.flush();
			session1.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	/**
	 * @param ttClasses
	 */
	private void saveHistory(int id) throws Exception {

		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			TTClasses ttClasses=(TTClasses)session.get(TTClasses.class,id);
			TTClassesHistory ttClassesHistory=new TTClassesHistory();
			PropertyUtils.copyProperties(ttClassesHistory, ttClasses);

			Set<TTPeriodWeekHistory> periodHistSet=new HashSet<TTPeriodWeekHistory>();
			Iterator<TTPeriodWeek> periodItr=ttClasses.getTtPeriodWeeks().iterator();
			while (periodItr.hasNext()) {
				TTPeriodWeek ttPeriodWeek = periodItr .next();
				TTPeriodWeekHistory ttPeriodWeekHistory=new TTPeriodWeekHistory();
				PropertyUtils.copyProperties(ttPeriodWeekHistory, ttPeriodWeek);

				Set<TTSubjectBatch> subjectBatchs=ttPeriodWeek.getTtSubjectBatchs();
				Set<TTSubjectBatchHistory> subjectBatchsHist=new HashSet<TTSubjectBatchHistory>();

				Iterator<TTSubjectBatch> subItr=subjectBatchs.iterator();
				while (subItr.hasNext()) {
					TTSubjectBatch ttSubjectBatch = subItr .next();
					TTSubjectBatchHistory ttSubjectBatchHistory=new TTSubjectBatchHistory();
					PropertyUtils.copyProperties(ttSubjectBatchHistory, ttSubjectBatch);

					Iterator<TTUsers> uesrItr=ttSubjectBatch.getTtUsers().iterator();
					Set<TTUsersHistory> userHist=new HashSet<TTUsersHistory>();
					while (uesrItr.hasNext()) {
						TTUsers ttUsers = uesrItr.next();
						TTUsersHistory ttUsersHistory=new TTUsersHistory();
						PropertyUtils.copyProperties(ttUsersHistory, ttUsers);

						ttUsersHistory.setId(0);
						userHist.add(ttUsersHistory);
					}
					ttSubjectBatchHistory.setTtUsersHistory(userHist);
					ttSubjectBatchHistory.setId(0);
					subjectBatchsHist.add(ttSubjectBatchHistory);
				}
				ttPeriodWeekHistory.setId(0);
				ttPeriodWeekHistory.setTtSubjectBatchsHistory(subjectBatchsHist);
				periodHistSet.add(ttPeriodWeekHistory);
			}
			ttClassesHistory.setTtPeriodWeeksHistory(periodHistSet);
			ttClassesHistory.setId(0);
			ttClassesHistory.setDate(new Date());
			session.save(ttClassesHistory);
			transaction.commit();
			session.flush();
			session.clear();
			session.close();
			log.debug("leaving addTermsConditionCheckList");
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}



	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.timetable.ITimeTableForClassTransaction#updateFlagForTimeTable(java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean updateFlagForTimeTable(String userId, int ttClassId,
			String finalApprove) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			TTClasses ttClasses=(TTClasses)session.get(TTClasses.class, ttClassId);
			if(finalApprove.equalsIgnoreCase("on"))
				ttClasses.setIsApproved(true);
			else
				ttClasses.setIsApproved(false);
			ttClasses.setLastModifiedDate(new Date());
			ttClasses.setModifiedBy(userId);

			session.update(ttClasses);
			if(finalApprove.equalsIgnoreCase("on")){
				//session.createQuery("delete from TeacherClassSubject ts where ts.classId="+ttClasses.getClassSchemewise().getId()).executeUpdate();   /* by chandra */
				Iterator<TTPeriodWeek> periodItr=ttClasses.getTtPeriodWeeks().iterator();
				while (periodItr.hasNext()) {
					TTPeriodWeek ttPeriodWeek =periodItr.next();
					Iterator<TTSubjectBatch> subItr=ttPeriodWeek.getTtSubjectBatchs().iterator();
					while (subItr.hasNext()) {
						TTSubjectBatch ttSubjectBatch = subItr .next();
						if(ttSubjectBatch.getIsActive() && ttSubjectBatch.getSubject()!=null){
							Iterator<TTUsers> userItr=ttSubjectBatch.getTtUsers().iterator();
							while (userItr.hasNext()) {
								TTUsers ttUsers = userItr.next();
								List list=session.createQuery("from TeacherClassSubject t where t.classId="+ttClasses.getClassSchemewise().getId()+" and t.year="+ttClasses.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()+" and t.teacherId="+ttUsers.getUsers().getId()+" and t.subject.id="+ttSubjectBatch.getSubject().getId()).list();
								/*if(list==null || list.isEmpty()){
								TeacherClassSubject teacherClassSubject=new TeacherClassSubject(0,ttClasses.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear().toString(),ttUsers.getUsers(),ttClasses.getClassSchemewise(),ttSubjectBatch.getSubject(),null,true,userId,new Date(),userId,new Date());
								session.save(teacherClassSubject);
							}*/
								if(list!=null && !list.isEmpty()){
									Iterator<TeacherClassSubject> tcList=list.iterator();
									while (tcList.hasNext()) {
										TeacherClassSubject tc =(TeacherClassSubject)tcList.next();
										tc.setLastModifiedDate(new Date());
										tc.setModifiedBy(userId);
										session.update(tc);
									}
								}else{
									TeacherClassSubject tc =new TeacherClassSubject();
									ClassSchemewise cs=new ClassSchemewise();
									Subject sub=new Subject();
									Users us=new Users();
									us.setId(ttUsers.getUsers().getId());
									sub.setId(ttSubjectBatch.getSubject().getId());
									cs.setId(ttClasses.getClassSchemewise().getId());
									tc.setClassId(cs);
									tc.setSubject(sub);
									tc.setTeacherId(us);
									tc.setYear((ttClasses.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()).toString());
									tc.setNumericCode(null);
									tc.setIsActive(true);
									tc.setCreatedBy(userId);
									tc.setCreatedDate(new Date());
									tc.setLastModifiedDate(new Date());
									tc.setModifiedBy(userId);
									session.save(tc);

								}
							}
						}
					}
				}
			}
			transaction.commit();
			session.flush();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * checks the TTClassHistory table for data for the input class id and returns true or false
	 * @see com.kp.cms.transactions.timetable.ITimeTableForClassTransaction#checkForTtClassHistory(java.lang.String)
	 */
	@Override
	public boolean checkForTtClassHistory(String classId) throws Exception {
		boolean historyExists=false;
		Session session = null;
		List<TTClassesHistory> ttClassHistoryList = null;
		try {
			session = HibernateUtil.getSession();
			ttClassHistoryList = session.createQuery("from TTClassesHistory tc where tc.classSchemewise.id=:classSchemeId").setInteger("classSchemeId",
					Integer.parseInt(classId)).list();
			if(ttClassHistoryList!=null && !ttClassHistoryList.isEmpty())
				historyExists=true;
			return historyExists;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	@Override
	public List<Object[]> getTeachers(String string) throws Exception {
		List list=null;
		List<Object[]> teacherDepartmentList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query=
					" select"+ 
					" users.id,"+
					" employee.first_name"+
					" from teacher_class_subject"+
					" inner join class_schemewise ON class_schemewise.id = teacher_class_subject.class_schemewise_id"+
					" inner join users ON users.id = teacher_class_subject.teacher_id"+
					" inner join subject ON subject.id = teacher_class_subject.subject_id"+
					" inner join employee ON employee.id = users.employee_id "+
					" inner join department ON department.id = employee.department_id"+
					" where subject.id ="+ string ;

			//		code ends here
			SQLQuery quer=session.createSQLQuery(query);
			teacherDepartmentList=quer.list();
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return teacherDepartmentList;
	}

	@Override
	public boolean saveAttendanceBo(List<Period> periodBo,TimeTableForClassForm timetableform, Set<Integer> selectedWeeksSet, Set<Integer> classesIdsSet, Set<Integer> teachersIdsSet, List<TTUsers> availttList, List<TTClasses> avattclassesList, List<TTSubjectBatch> avasubjbatchList, List<TTPeriodWeek> avaperiodweekList) throws Exception {
		List<TTUsers> ttUserslist=null;
		Session session=null;
		Transaction transaction = null;
		List<TTClasses> ttclassesList=new ArrayList<TTClasses>();
		List<TTPeriodWeek> periodweekList=new ArrayList<TTPeriodWeek>();
		List<TTSubjectBatch> subjbatchList=new ArrayList<TTSubjectBatch>();
		List<TTUsers> tuserList =new ArrayList<TTUsers>();
		List<TTUsers> availabletuserList =null;
		
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();

			for(Integer classId:classesIdsSet){
				TTClasses clas=null;
				if (!avattclassesList.isEmpty() &&  avattclassesList!=null) {
					int i=0;
					for(TTClasses aclas:avattclassesList){
						if (aclas.getClassSchemewise().getId()==classId) {
							clas=(TTClasses) session.get(TTClasses.class,aclas.getId());
							clas.setLastModifiedDate(new Date());
							clas.setModifiedBy(timetableform.getUserId());
							ttclassesList.add(clas);
							session.update(clas);
							i++;
							break;
						}
							
					
					}
					if (i==0) {
						clas=new TTClasses();
						clas.setCreatedBy(timetableform.getUserId());
						clas.setCreatedDate(new Date());
						clas.setLastModifiedDate(new Date());
						clas.setIsActive(true);
						clas.setModifiedBy(timetableform.getUserId());
						clas.setIsApproved(true);
						ClassSchemewise schemewise=new ClassSchemewise();
						schemewise.setId(classId);
						clas.setClassSchemewise(schemewise);
						ttclassesList.add(clas);
						session.save(clas);
				}
				}else{
				
				clas=new TTClasses();
				clas.setCreatedBy(timetableform.getUserId());
				clas.setCreatedDate(new Date());
				clas.setLastModifiedDate(new Date());
				clas.setIsActive(true);
				clas.setModifiedBy(timetableform.getUserId());
				clas.setIsApproved(true);
				ClassSchemewise schemewise=new ClassSchemewise();
				schemewise.setId(classId);
				clas.setClassSchemewise(schemewise);
				ttclassesList.add(clas);
				session.save(clas);
				}
			}
			for(TTClasses clas:ttclassesList){
				for(Integer weekId:selectedWeeksSet){
					String weekday=null;
					if (weekId==1) {
						weekday="Monday";
					}
					else if (weekId==2) {
						weekday="Tuesday";
					}
					else if (weekId==3) {
						weekday="Wednesday";
					}
					else if (weekId==4) {
						weekday="Thursday";
					}
					else if (weekId==5) {
						weekday="Friday";
					}
					else if (weekId==6) {
						weekday="Saturday";
					}
					for(Period period:periodBo){
						if (period.getClassSchemewise().getId()==clas.getClassSchemewise().getId()) {
							TTPeriodWeek periodweek=null;
							if (!avaperiodweekList.isEmpty() && avaperiodweekList!=null) {
								int i=0;
							for(TTPeriodWeek  ttuser:avaperiodweekList){
								
								if ((ttuser.getWeekDay()==weekday || ttuser.getWeekDay().equalsIgnoreCase(weekday)) &&(ttuser.getPeriod().getId()==period.getId()) && ttuser.getTtClasses().getClassSchemewise().getId()==clas.getClassSchemewise().getId()) {
										periodweek=(TTPeriodWeek) session.get(TTPeriodWeek.class, ttuser.getId());
										periodweek.setLastModifiedDate(new Date());
										periodweek.setModifiedBy(timetableform.getUserId());
										periodweekList.add(periodweek);
										session.update(periodweek);
										i++;
										break;
								}
							}if (i==0) {
								periodweek=new TTPeriodWeek();
								periodweek.setCreatedBy(timetableform.getUserId());
								periodweek.setCreatedDate(new Date());
								periodweek.setLastModifiedDate(new Date());
								periodweek.setIsActive(true);
								periodweek.setModifiedBy(timetableform.getUserId());
								periodweek.setPeriod(period);
								TTClasses cl=new TTClasses();
								cl.setId(clas.getId());
								periodweek.setTtClasses(cl);
								periodweek.setWeekDay(weekday);
								periodweekList.add(periodweek);
								session.save(periodweek);
							}
						}else{
							periodweek=new TTPeriodWeek();
							periodweek.setCreatedBy(timetableform.getUserId());
							periodweek.setCreatedDate(new Date());
							periodweek.setLastModifiedDate(new Date());
							periodweek.setIsActive(true);
							periodweek.setModifiedBy(timetableform.getUserId());
							periodweek.setPeriod(period);
							TTClasses cl=new TTClasses();
							cl.setId(clas.getId());
							periodweek.setTtClasses(cl);
							periodweek.setWeekDay(weekday);
							periodweekList.add(periodweek);
							session.save(periodweek);
						}
						}
					}
				}
			}
		
			for(TTPeriodWeek pweek:periodweekList){
				TTSubjectBatch subBatch=null;
				if (!avasubjbatchList.isEmpty() && avasubjbatchList!=null) {
					int i=0;
				for(TTSubjectBatch  ttuser:avasubjbatchList){
					if ((ttuser.getTtPeriodWeek().getWeekDay()==pweek.getWeekDay() 
							|| ttuser.getTtPeriodWeek().getWeekDay().equalsIgnoreCase(pweek.getWeekDay())) 
							&&(ttuser.getTtPeriodWeek().getPeriod().getId()==pweek.getPeriod().getId()) 
							&&(ttuser.getTtPeriodWeek().getTtClasses().getClassSchemewise().getId()==pweek.getTtClasses().getClassSchemewise().getId())
							&&(ttuser.getSubject().getId()==Integer.parseInt(timetableform.getSubjectId()))) {
						subBatch=(TTSubjectBatch) session.get(TTSubjectBatch.class, ttuser.getId());
						subBatch.setLastModifiedDate(new Date());
						subBatch.setModifiedBy(timetableform.getUserId());
						Subject  subj=new Subject();
						subj.setId(Integer.parseInt(timetableform.getSubjectId()));
						subBatch.setSubject(subj);
						//AttendanceType atype=new AttendanceType();
						//atype.setId(2);
						//subBatch.setAttendanceType(atype);
						subjbatchList.add(subBatch);
						session.update(subBatch);
						i++;
						break;
					}
				}
				if (i==0) {
				subBatch=new TTSubjectBatch();
				subBatch.setCreatedBy(timetableform.getUserId());
				subBatch.setCreatedDate(new Date());
				subBatch.setLastModifiedDate(new Date());
				subBatch.setModifiedBy(timetableform.getUserId());
				TTPeriodWeek pw=new TTPeriodWeek();
				pw.setId(pweek.getId());
				subBatch.setTtPeriodWeek(pw);
				Subject  subj=new Subject();
				subj.setId(Integer.parseInt(timetableform.getSubjectId()));
				subBatch.setSubject(subj);
				subBatch.setIsActive(true);
				AttendanceType atype=new AttendanceType();
				atype.setId(2);
				subBatch.setAttendanceType(atype);
				subjbatchList.add(subBatch);
				session.save(subBatch);
				}

			}else{
				subBatch=new TTSubjectBatch();
				subBatch.setCreatedBy(timetableform.getUserId());
				subBatch.setCreatedDate(new Date());
				subBatch.setLastModifiedDate(new Date());
				subBatch.setModifiedBy(timetableform.getUserId());
				TTPeriodWeek pw=new TTPeriodWeek();
				pw.setId(pweek.getId());
				subBatch.setTtPeriodWeek(pw);
				Subject  subj=new Subject();
				subj.setId(Integer.parseInt(timetableform.getSubjectId()));
				subBatch.setSubject(subj);
				subBatch.setIsActive(true);
				AttendanceType atype=new AttendanceType();
				atype.setId(2);
				subBatch.setAttendanceType(atype);
				subjbatchList.add(subBatch);
				session.save(subBatch);
			}
			}

			for(Integer teacherId:teachersIdsSet){
				for(TTSubjectBatch subj:subjbatchList){
					TTUsers tusers=null;
					if (!availttList.isEmpty() && availttList!=null) {
						int i=0;
						availabletuserList=availttList;
					for(TTUsers  ttuser:availabletuserList){
						System.out.println(ttuser.getTtSubjectBatch().getId()+"oustide");
						if (ttuser.getTtSubjectBatch().getId()==subj.getId()) {
							//if(ttuser.getTtSubjectBatch().getTtPeriodWeek().getWeekDay()==subj.getTtPeriodWeek().getWeekDay() || ttuser.getTtSubjectBatch().getTtPeriodWeek().getWeekDay().equalsIgnoreCase(subj.getTtPeriodWeek().getWeekDay())){
							int id=ttuser.getId();
							tusers=(TTUsers) session.get(TTUsers.class, id);
							tusers.setLastModifiedDate(new Date());
							tusers.setModifiedBy(timetableform.getUserId());
							TTSubjectBatch sub=new TTSubjectBatch();
							sub.setId(subj.getId());
							tusers.setTtSubjectBatch(sub);
							tuserList.add(tusers);
							session.update(tusers);
							i++;
							
							break;
							//}
						}
					}
					if (i==0) {
						tusers=new TTUsers();
						tusers.setCreatedBy(timetableform.getUserId());
						tusers.setCreatedDate(new Date());
						tusers.setLastModifiedDate(new Date());
						tusers.setIsActive(true);
						tusers.setModifiedBy(timetableform.getUserId());
						Users user=new Users();
						user.setId(teacherId);
						tusers.setUsers(user);
						TTSubjectBatch sub=new TTSubjectBatch();
						sub.setId(subj.getId());
						tusers.setTtSubjectBatch(sub);
						tuserList.add(tusers);
						session.save(tusers);
					}
				}else{
					tusers=new TTUsers();
					tusers.setCreatedBy(timetableform.getUserId());
					tusers.setCreatedDate(new Date());
					tusers.setLastModifiedDate(new Date());
					tusers.setIsActive(true);
					tusers.setModifiedBy(timetableform.getUserId());
					Users user=new Users();
					user.setId(teacherId);
					tusers.setUsers(user);
					TTSubjectBatch sub=new TTSubjectBatch();
					sub.setId(subj.getId());
					tusers.setTtSubjectBatch(sub);
					tuserList.add(tusers);
					session.save(tusers);
				}
				}
			}
			System.out.println(tuserList);


			transaction.commit();


		}catch(Exception e){
			log.error("Error during updating time table..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return true;
	}
	@Override
	public List<Period> getperiods(Set<Integer> classesIdsSet,String periodNameForQuery) throws ApplicationException {
		List<Period> boList=null;
		Session session=null;

		try{
			session=HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Period p where p.classSchemewise.id in (:classSchemeId) and p.isActive=true and p.periodName in("+periodNameForQuery+")")
					.setParameterList("classSchemeId", classesIdsSet);


			boList=(List<Period>) query.list();
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return boList;
	}

	@Override
	public List getAvailableTimetable(String timeTableDeletequery) throws ApplicationException {
		List ttuserList=null;
		Session session=null;

		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery(timeTableDeletequery);
			ttuserList=query.list();
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return ttuserList;
	}
	@Override
	public String getAttendanceType(BaseActionForm baseActionForm) throws ApplicationException {
		Session session=null;
		String result=null;
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("select sub.isTheoryPractical from Subject sub where sub.id="+baseActionForm.getSubjectId());
			result=(String) String.valueOf(query.uniqueResult());
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return result;
	}
	@Override
	public String getsubjectsFromTeacherClass(String userid) throws ApplicationException {
		Session session=null;
		String result=null;
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("select sub.subjectType.id from Subject sub where sub.id="+userid);
			result=(String) String.valueOf(query.uniqueResult());
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return result;
	}
	public Map<Integer, String> getSubjectByCourseIdTermYear(int courseId,
			int year, int term, String classId, String userid,String roleId ) {
	
		List<Object[]> curriculumSchemeDurationList = null;
		CurriculumSchemeDuration curriculumSchemeDuration = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String qure=null;
			if (CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(Integer.parseInt(roleId))) {
			if (classId!=null) {
				qure=" select subject.id,subject.name,subject.code"+
						" from teacher_class_subject"+
						" inner join class_schemewise ON class_schemewise.id = teacher_class_subject.class_schemewise_id"+ 
						" inner join users ON users.id = teacher_class_subject.teacher_id"+
						" inner join subject ON subject.id = teacher_class_subject.subject_id"+
						" inner join employee ON employee.id = users.employee_id"+ 
						" inner join department ON department.id = employee.department_id"+
						" inner join classes ON classes.id = class_schemewise.class_id"+
						" inner join course on course.id = classes.course_id"+
						" where teacher_class_subject.`year`="+year+
						" and subject.scheme_no="+term+
						" and course.id ="+courseId+
						" and class_schemewise.id ="+classId+
						" and teacher_class_subject.is_active =true"+
						"  and users.id ="+userid;
			}else{
			qure=
					" select subject.id,subject.name,subject.code"+
					" from teacher_class_subject"+
					" inner join class_schemewise ON class_schemewise.id = teacher_class_subject.class_schemewise_id"+ 
					" inner join users ON users.id = teacher_class_subject.teacher_id"+
					" inner join subject ON subject.id = teacher_class_subject.subject_id"+
					" inner join employee ON employee.id = users.employee_id"+ 
					" inner join department ON department.id = employee.department_id"+
					" inner join classes ON classes.id = class_schemewise.class_id"+
					" inner join course on course.id = classes.course_id"+
					" where teacher_class_subject.`year`="+year+
					" and subject.scheme_no="+term+
					" and teacher_class_subject.is_active =true"+
					" and course.id ="+courseId;
			}
			
			}else{
				qure=" select subject.id,subject.name,subject.code"+
						" from teacher_class_subject"+
						" inner join class_schemewise ON class_schemewise.id = teacher_class_subject.class_schemewise_id"+ 
						" inner join users ON users.id = teacher_class_subject.teacher_id"+
						" inner join subject ON subject.id = teacher_class_subject.subject_id"+
						" inner join employee ON employee.id = users.employee_id"+ 
						" inner join department ON department.id = employee.department_id"+
						" inner join classes ON classes.id = class_schemewise.class_id"+
						" inner join course on course.id = classes.course_id"+
						" where teacher_class_subject.`year`="+year+
						" and subject.scheme_no="+term+
						" and class_schemewise.id ="+classId+
						" and teacher_class_subject.is_active =true";
			}
						SQLQuery query= session.createSQLQuery(qure);
					curriculumSchemeDurationList=query.list();
			int count = 0;
			if (!curriculumSchemeDurationList.isEmpty()) {
				for (Iterator<Object[]> iterator = curriculumSchemeDurationList.iterator(); iterator
						.hasNext();) {
					String code=null;
					int id=0;
					String name=null;
					Object[] objects = (Object[]) iterator.next();
					if (objects[0] != null) {
						id=(Integer.parseInt(objects[0].toString()));
					}
					if (objects[1] != null) {
						name=objects[1].toString();
					}
					if (objects[2] != null) {
						code=objects[2].toString();
					}
					subjectMap.put(id, name+"("+code+")");
				}
			}

			session.flush();
			// session.close();
			Map<Integer, String> valueMap = sortByValue(subjectMap);
			return valueMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return subjectMap;
	}
	public Map<Integer, String> sortByValue(Map<Integer, String> map) {
		List<Entry<Integer, String>> list = new LinkedList<Entry<Integer, String>>(
				map.entrySet());

		Collections.sort(list, new Comparator<Entry<Integer, String>>() {

			@Override
			public int compare(Map.Entry<Integer, String> o1,
					Map.Entry<Integer, String> o2) {
				return o1.getValue().compareTo(o2.getValue());

			}

		});
		Map<Integer, String> result = new LinkedHashMap<Integer, String>();
		Iterator<Entry<Integer, String>> listIterator = list.iterator();
		while (listIterator.hasNext()) {
			Map.Entry<java.lang.Integer, java.lang.String> entry = (Map.Entry<java.lang.Integer, java.lang.String>) listIterator
					.next();
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}
	
	public List<Object[]> getSubjectByClassId(String courseId
			) {
	
		List<Object[]> subList = null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
						SQLQuery query= session.createSQLQuery(courseId);
						subList=query.list();

			session.flush();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return subList;
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesForAttendanceByYearAndUserId(int year, String evenOrOdd, String userId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = null;
			if(evenOrOdd.equalsIgnoreCase("odd")){
				query = session
					.createQuery("from TeacherClassSubject tc where tc.classId.classes.termNumber in(1,3,5,7) and tc.teacherId="+userId+" and tc.year=:academicYear and tc.isActive=true order by tc.classId.classes.name")
					.setInteger("academicYear", year);
			}else if(evenOrOdd.equalsIgnoreCase("even")){
				query = session
						.createQuery("from TeacherClassSubject tc where tc.classId.classes.termNumber in(2,4,6,8) and tc.teacherId="+userId+" and tc.year=:academicYear and tc.isActive=true order by tc.classId.classes.name")
						.setInteger("academicYear", year);
			}
			List<TeacherClassSubject> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<TeacherClassSubject> itr = classList.iterator();
			TeacherClassSubject teacherClass;

			while (itr.hasNext()) {
				teacherClass = (TeacherClassSubject) itr.next();
				classMap.put(teacherClass.getClassId().getClasses().getId(), 
						teacherClass.getClassId().getClasses().getName());
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesForAttendanceByYearAndUserIdAndSubjectId(int year, String evenOrOdd, String userId,String SubjectId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = null;
			if (SubjectId!=null && !SubjectId.isEmpty()) {
				if(evenOrOdd.equalsIgnoreCase("odd")){
					query = session
						.createQuery("from TeacherClassSubject tc where tc.classId.classes.termNumber in(1,3,5,7) and tc.teacherId="+userId+" and tc.year=:academicYear and tc.subject.id="+SubjectId+" and tc.isActive=true order by tc.classId.classes.name")
						.setInteger("academicYear", year);
				}else if(evenOrOdd.equalsIgnoreCase("even")){
					query = session
							.createQuery("from TeacherClassSubject tc where tc.classId.classes.termNumber in(2,4,6,8) and tc.teacherId="+userId+" and tc.year=:academicYear and tc.subject.id="+SubjectId+" and tc.isActive=true order by tc.classId.classes.name")
							.setInteger("academicYear", year);
				}
			}else{
			if(evenOrOdd.equalsIgnoreCase("odd")){
				query = session
					.createQuery("from TeacherClassSubject tc where tc.classId.classes.termNumber in(1,3,5,7) and tc.teacherId="+userId+" and tc.year=:academicYear and tc.isActive=true order by tc.classId.classes.name")
					.setInteger("academicYear", year);
			}else if(evenOrOdd.equalsIgnoreCase("even")){
				query = session
						.createQuery("from TeacherClassSubject tc where tc.classId.classes.termNumber in(2,4,6,8) and tc.teacherId="+userId+" and tc.year=:academicYear and tc.isActive=true order by tc.classId.classes.name")
						.setInteger("academicYear", year);
			}
			}
			List<TeacherClassSubject> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<TeacherClassSubject> itr = classList.iterator();
			TeacherClassSubject teacherClass;

			while (itr.hasNext()) {
				teacherClass = (TeacherClassSubject) itr.next();
				classMap.put(teacherClass.getClassId().getClasses().getId(), 
						teacherClass.getClassId().getClasses().getName());
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}

}
