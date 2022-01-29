package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.transactions.attandance.IActivityAttendenceTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

/**
 * An implementation class for handling activity attendance transactions.
 */
public class ActivityAttendenceTransactionImpl implements
		IActivityAttendenceTransaction {
	private static final Log log = LogFactory
			.getLog(ActivityAttendenceTransactionImpl.class);

	/**
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#
	 *      getStudentListByClassSchemeWiseId(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentListByClassSchemeWiseId(int classSchemeWiseId)
			throws ApplicationException {
		log
				.info("entering into getStudentListByClassSchemeWiseId of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		List<Student> studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			
			Query studentQuery = session
					.createQuery("from Student student where student.classSchemewise.id = :classSchemeWiseId  and student.isAdmitted = true and (student.isHide = false or student.isHide is null)");
			studentQuery.setInteger("classSchemeWiseId", classSchemeWiseId);
			studentSearchResult = studentQuery.list();

		} catch (Exception e) {
			log
					.info(
							"error in getStudentListByClassSchemeWiseId of ActivityAttendenceTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of getStudentListByClassSchemeWiseId of ActivityAttendenceTransactionImpl class.");
		return studentSearchResult;
	}

	/**
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#getPeriod
	 *      (int)
	 */
	@Override
	public Period getPeriod(int periodId) throws ApplicationException {
		log
				.info("entering into getPeriod of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		Period period = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from Period period where period.id = :periodId");
			studentQuery.setInteger("periodId", periodId);
			period = (Period) studentQuery.uniqueResult();

		} catch (Exception e) {
			log
					.info(
							"error in getPeriod of ActivityAttendenceTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of getPeriod of ActivityAttendenceTransactionImpl class.");
		return period;
	}

	/**
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#
	 *      getPeriodListForStartDate(int, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Period> getPeriodListForStartDate(int classSchemeWiseId,
			String startTime, String endTime) throws ApplicationException {
		log
				.info("entering into getPeriodListForStartDate of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		List<Period> period = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from Period period where period.classSchemewise.id = :classSchemeWiseId and period.isActive = true"
							+ " and period.startTime between :startTime and :endTime order by period.startTime");
			studentQuery.setInteger("classSchemeWiseId", classSchemeWiseId);
			studentQuery.setString("startTime", startTime);
			studentQuery.setString("endTime", endTime);
			period = studentQuery.list();

		} catch (Exception e) {
			log
					.info(
							"error in getPeriodListForStartDate of ActivityAttendenceTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of getPeriodListForStartDate of ActivityAttendenceTransactionImpl class.");
		return period;
	}

	/**
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#
	 *      getPeriodListForEndDate(java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Period> getPeriodListForEndDate(Integer classSchemeWiseId,
			String endTime) throws ApplicationException {
		log
				.info("entering into getPeriodListForEndDate of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		List<Period> period = null;
		String starttime = "00:00:00";
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from Period period where period.classSchemewise.id = :classSchemeWiseId and period.isActive = true "
							+ " and period.endTime between :startTime and :endTime order by period.endTime");
			studentQuery.setInteger("classSchemeWiseId", classSchemeWiseId);
			studentQuery.setString("endTime", endTime);
			studentQuery.setString("startTime", starttime);
			period = studentQuery.list();

		} catch (Exception e) {
			log
					.info(
							"error in getPeriodListForEndDate of ActivityAttendenceTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of getPeriodListForEndDate of ActivityAttendenceTransactionImpl class.");
		return period;

	}

	/**
	 * 
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#
	 *      getPeriodList(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Period> getPeriodList(Integer classSchemeWiseId)
			throws ApplicationException {
		log
				.info("entering into getPeriodList of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		List<Period> period = null;

		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from Period period where period.classSchemewise.id = :classSchemeWiseId and period.isActive = true"
							+ " order by period.startTime");
			studentQuery.setInteger("classSchemeWiseId", classSchemeWiseId);

			period = studentQuery.list();

		} catch (Exception e) {
			log
					.info(
							"error in getPeriodList of ActivityAttendenceTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of getPeriodList of ActivityAttendenceTransactionImpl class.");
		return period;

	}

	/**
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#
	 *      checkIsRegisterNo(int)
	 */
	@Override
	public Boolean checkIsRegisterNo(int classSchemeWiseId)
			throws ApplicationException {
		log
				.info("entering into checkIsRegisterNo of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		ClassSchemewise classSchemewise = null;

		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from ClassSchemewise classSchemeWise where classSchemeWise.id = :classSchemeWiseId ");
			studentQuery.setInteger("classSchemeWiseId", classSchemeWiseId);

			classSchemewise = (ClassSchemewise) studentQuery.uniqueResult();

		} catch (Exception e) {
			log
					.info(
							"error in checkIsRegisterNo of ActivityAttendenceTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of checkIsRegisterNo of ActivityAttendenceTransactionImpl class.");
		return classSchemewise.getClasses().getCourse().getProgram()
				.getIsRegistrationNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.attandance.IActivityAttendenceTransaction#
	 * initModifyActivityAttendence(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Attendance> initModifyActivityAttendence(String fromDate,
			String toDate) throws ApplicationException {
		log
				.info("entering into initModifyActivityAttendence of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		List<Attendance> modifyActivityAttendanceList = null;

		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query modifyActivityAttendanceQuery = session
					.createQuery(" from Attendance attendance "
							+ " where attendance.attendanceDate >= :fromDate "
							+ " and attendance.attendanceDate <= :toDate "
							+ " and attendance.isActivityAttendance = 1 "
							+ " and attendance.isCanceled = 0 "
							+ " order by attendance.attendanceDate ");
			modifyActivityAttendanceQuery.setDate("fromDate", CommonUtil
					.ConvertStringToSQLDate(fromDate));
			modifyActivityAttendanceQuery.setDate("toDate", CommonUtil
					.ConvertStringToSQLDate(toDate));
			modifyActivityAttendanceList = modifyActivityAttendanceQuery.list();
		} catch (Exception e) {
			log
					.info(
							"error in initModifyActivityAttendence of ActivityAttendenceTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of initModifyActivityAttendence of ActivityAttendenceTransactionImpl class.");
		return modifyActivityAttendanceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.attandance.IActivityAttendenceTransaction#
	 * getActivityAttendanceById(int)
	 */
	@Override
	public Attendance getActivityAttendanceById(int attendanceId)
			throws ApplicationException {
		log
				.info("entering into getActivityAttendanceById of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		Attendance getActivityAttendanceById = null;

		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query getActivityAttendanceByIdQuery = session
					.createQuery(" from Attendance attendance "
							+ " where attendance.id = :attendanceId "
							+ " and attendance.isActivityAttendance = 1 ");
			getActivityAttendanceByIdQuery.setInteger("attendanceId",
					attendanceId);
			getActivityAttendanceById = (Attendance) getActivityAttendanceByIdQuery
					.uniqueResult();
		} catch (Exception e) {
			log
					.info(
							"error in getActivityAttendanceById of ActivityAttendenceTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of getActivityAttendanceById of ActivityAttendenceTransactionImpl class.");
		return getActivityAttendanceById;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isCoCurricularLeaveApproved(ApproveLeaveForm activityAttendenceForm,String startTime,String endTime)
			throws ApplicationException {

		log
				.info("entering into isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.");

		boolean isLeaveApproved = false;
		Session session = null;
		List<Object[]> obj = null;
		StringBuffer reggNos=new StringBuffer();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			String Query ="";
			if(activityAttendenceForm.getFullDay()!=null && activityAttendenceForm.getFullDay().equalsIgnoreCase("Yes")){
				if (activityAttendenceForm.isRegisterNo()) {
					Query = "select stuCocurrLeaveDetailses.student.registerNo from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses "
							+ " where ( ( :startDate between stuCocurrLeave.startDate  and stuCocurrLeave.endDate )"
							+ "  or (:endDate  between  stuCocurrLeave.startDate  and stuCocurrLeave.endDate  )  " 
							+ " or (:startDate >=  stuCocurrLeave.startDate and :endDate <=  stuCocurrLeave.endDate))"						
							+ " and stuCocurrLeaveDetailses.student.registerNo in (:registerNo) " 
							+ " and stuCocurrLeave.isCocurrLeaveCancelled = 0 and stuCocurrLeave.id<>"+Integer.parseInt(activityAttendenceForm.getId())+
							  " and stuCocurrLeave.classSchemewise.id="+activityAttendenceForm.getClassSchemewiseId();
							 	
	
				} else {
					
					Query ="select stuCocurrLeaveDetailses.student.registerNo from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses "
							+ " where ( ( :startDate between stuCocurrLeave.startDate  and stuCocurrLeave.endDate )"
							+ "  or (:endDate  between  stuCocurrLeave.startDate  and stuCocurrLeave.endDate  )  " 
							+ " or (:startDate >=  stuCocurrLeave.startDate and :endDate <=  stuCocurrLeave.endDate))"						
							+ " and stuCocurrLeaveDetailses.student.rollNo in (:registerNo) " 
							+ " and stuCocurrLeave.isCocurrLeaveCancelled = 0  and stuCocurrLeave.id<>"+Integer.parseInt(activityAttendenceForm.getId())+
							  " and stuCocurrLeave.classSchemewise.id="+activityAttendenceForm.getClassSchemewiseId();
				
				}
				}else{
						if (activityAttendenceForm.isRegisterNo()) {
							Query ="select stuCocurrLeaveDetailses.student.registerNo from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses "
									+ " where ( ( :startDate between stuCocurrLeave.startDate  and stuCocurrLeave.endDate )"
									+ "  or (:endDate  between  stuCocurrLeave.startDate  and stuCocurrLeave.endDate  )  " +
									" or (:startDate >=  stuCocurrLeave.startDate and :endDate <=  stuCocurrLeave.endDate))";
							if((startTime!=null && !startTime.isEmpty())&& (endTime!=null && !endTime.isEmpty())){
								Query=Query+ " and(( '"+startTime+"' >= stuCocurrLeave.startPeriod.startTime and '"+endTime+"'<= stuCocurrLeave.endPeriod.startTime)" +
										" or( '"+startTime+"' >= stuCocurrLeave.startPeriod.startTime and '"+endTime+"'<= stuCocurrLeave.endPeriod.startTime )" +
										" or ( '"+startTime+"'<= stuCocurrLeave.startPeriod.startTime and '"+endTime+"' >= stuCocurrLeave.endPeriod.startTime))";
							}	
							Query=Query+ " and stuCocurrLeaveDetailses.student.registerNo in (:registerNo) " +
											" and stuCocurrLeave.isCocurrLeaveCancelled = 0 and stuCocurrLeave.id<>"+Integer.parseInt(activityAttendenceForm.getId())+
											" and stuCocurrLeave.classSchemewise.id="+activityAttendenceForm.getClassSchemewiseId();
							
							
			/*				studentQuery = session
									.createQuery("from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses "
											+ " where ( ( stuCocurrLeave.startDate between :startDate and  :endDate )"
											+ "  or (stuCocurrLeave.endDate  between  :startDate and  :endDate ) ) "
											+ " and ( (stuCocurrLeave.startPeriod between :startPeriod and :endPeriod) "
											+ " or (stuCocurrLeave.endPeriod between :startPeriod and :endPeriod) )"
											+ " and stuCocurrLeaveDetailses.student.registerNo in (:registerNo) " +
													" and stuCocurrLeave.isCocurrLeaveCancelled = 0");
			*/
						} else {
							
							Query ="select stuCocurrLeaveDetailses.student.registerNo from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses "
									+ " where ( ( :startDate between stuCocurrLeave.startDate  and stuCocurrLeave.endDate )"
									+ "  or (:endDate  between  stuCocurrLeave.startDate  and stuCocurrLeave.endDate  )  " +
									" or (:startDate >=  stuCocurrLeave.startDate and :endDate <=  stuCocurrLeave.endDate))";
							if((startTime!=null && !startTime.isEmpty())&& (endTime!=null && !endTime.isEmpty())){
								Query=Query+" and(( '"+startTime+"' >= stuCocurrLeave.startPeriod.startTime and '"+endTime+"'<= stuCocurrLeave.endPeriod.startTime)" +
									" or( '"+startTime+"' >= stuCocurrLeave.startPeriod.startTime and '"+endTime+"'<= stuCocurrLeave.endPeriod.startTime )" +
									" or ( '"+startTime+"'<= stuCocurrLeave.startPeriod.startTime and '"+endTime+"' >= stuCocurrLeave.endPeriod.startTime))";
							}
							Query=Query+ " and stuCocurrLeaveDetailses.student.rollNo in (:registerNo) " +
									" and stuCocurrLeave.isCocurrLeaveCancelled = 0  and stuCocurrLeave.id<>"+Integer.parseInt(activityAttendenceForm.getId())+
									" and stuCocurrLeave.classSchemewise.id="+activityAttendenceForm.getClassSchemewiseId();
							/*
							studentQuery = session
							.createQuery("from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses "
									+ " where ( ( :startDate between stuCocurrLeave.startDate  and stuCocurrLeave.endDate )"
									+ "  or (:endDate  between  stuCocurrLeave.startDate  and stuCocurrLeave.endDate  )  " +
									" or (:startDate >=  stuCocurrLeave.startDate and :endDate <=  stuCocurrLeave.endDate))"						
									+ " and ( (stuCocurrLeave.startPeriod between :startPeriod and :endPeriod) "
									+ " or (stuCocurrLeave.endPeriod between :startPeriod and :endPeriod) )"
									+ " and stuCocurrLeaveDetailses.student.rollNo in (:registerNo) " +
											" and stuCocurrLeave.isCocurrLeaveCancelled = 0");
							studentQuery = session
									.createQuery("from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses "
											+ " where ( ( stuCocurrLeave.startDate between :startDate and  :endDate )"
											+ "  or (stuCocurrLeave.endDate  between  :startDate and  :endDate ) ) "
											+ " and ( (stuCocurrLeave.startPeriod between :startPeriod and :endPeriod) "
											+ " or (stuCocurrLeave.endPeriod between :startPeriod and :endPeriod) )"
											+ " and stuCocurrLeaveDetailses.student.rollNo in (:registerNo) " +
													" and stuCocurrLeave.isCocurrLeaveCancelled = 0 ");*/
			
						}
			}
			Query studentQuery =session.createQuery(Query);
			
			studentQuery.setDate("startDate", CommonUtil
					.ConvertStringToSQLDate(activityAttendenceForm
							.getFromDate()));
			studentQuery
					.setDate("endDate", CommonUtil
							.ConvertStringToSQLDate(activityAttendenceForm
									.getToDate()));
//			studentQuery.setInteger("startPeriod", Integer
//					.valueOf(activityAttendenceForm.getFromPeriod()));
//			studentQuery.setInteger("endPeriod", Integer
//					.valueOf(activityAttendenceForm.getToPeriod()));
			studentQuery.setParameterList("registerNo", activityAttendenceForm
					.getRegisterNoSet());
			obj = studentQuery.list();
			String[] str = null;
			if (obj != null && !obj.isEmpty()) {
				Iterator<Object[]> itr=obj.iterator();
				while(itr.hasNext()){
					Object regNo=(Object)	itr.next();
					if(reggNos==null || reggNos.length()==0){
						reggNos.append(regNo);
					}else{
						
						String dupRegNos=reggNos.toString();
					if(regNo!=null && !dupRegNos.isEmpty()){
						str = dupRegNos.split(",");
						boolean flag=true;
						for (int x = 0; x < str.length; x++) {
							if (str[x] != null && str[x].length() > 0) {
								if(str[x].equalsIgnoreCase(String.valueOf(regNo))){
									flag=false;
									
								}
							}
						}
						
						if(flag){
							reggNos.append(","+regNo);
						}
						
					}
					
					}
				}
				activityAttendenceForm.setExistingCurrLeaveRegNos(reggNos.toString());
				isLeaveApproved = true;
			}
		} catch (Exception e) {
			log
					.info(
							"error in isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.");
		return isLeaveApproved;

	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#updateCoCcurricularLeave(java.util.Map, com.kp.cms.forms.attendance.ApproveLeaveForm, com.kp.cms.bo.admin.StuCocurrLeave, java.lang.String)
	 */
	@Override
	public void updateCoCcurricularLeave(
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap,
			ApproveLeaveForm activityAttendanceForm,
			StuCocurrLeave studentLeave, String mode)
			throws ApplicationException {

		log
				.info("entering into updateApproveLeave of ApproveLeaveTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			if ("update".equals(mode)) {
				List<ApproveLeaveTO> approveLeaveTOForAttendence = approveLeaveToMap
						.get(2);
				if (approveLeaveTOForAttendence != null && !approveLeaveTOForAttendence.isEmpty()) {
					Iterator<ApproveLeaveTO> AttendenceIterator = approveLeaveTOForAttendence
							.iterator();
					while (AttendenceIterator.hasNext()) {
						ApproveLeaveTO approveLeaveTO = (ApproveLeaveTO) AttendenceIterator
								.next();

						AttendanceStudent attendence = (AttendanceStudent) session
								.get(AttendanceStudent.class, Integer
										.valueOf(approveLeaveTO
												.getAttendenceId()));
						attendence.setIsCoCurricularLeave(false);
						attendence.setModifiedBy(activityAttendanceForm
								.getUserId());
						attendence.setLastModifiedDate(new Date());
						session.update(attendence);
					}
				}
			}

			List<ApproveLeaveTO> approveLeaveTOForAttendence = approveLeaveToMap
					.get(1);
			if (approveLeaveTOForAttendence != null && !approveLeaveTOForAttendence.isEmpty()) {
				Iterator<ApproveLeaveTO> AttendenceIterator = approveLeaveTOForAttendence
						.iterator();
				while (AttendenceIterator.hasNext()) {
					ApproveLeaveTO approveLeaveTO = (ApproveLeaveTO) AttendenceIterator
							.next();

					AttendanceStudent attendence = (AttendanceStudent) session
							.get(AttendanceStudent.class, Integer
									.valueOf(approveLeaveTO.getAttendenceId()));
					attendence.setIsCoCurricularLeave(true);
					attendence.setIsPresent(false);
					attendence
							.setModifiedBy(activityAttendanceForm.getUserId());
					attendence.setLastModifiedDate(new Date());
					session.update(attendence);
				}
			}
			//if(approveLeaveToMap!=null && !approveLeaveToMap.isEmpty()){
			if ("update".equals(mode)) {
				session.update(studentLeave);
			} else {
				session.save(studentLeave);
			}
			transaction.commit();
			//}
		} catch (Exception e) {
			log
					.info(
							"error in updateApproveLeave of ApproveLeaveTransactionImpl class.",
							e);
			if (transaction != null)
				transaction.rollback();
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log
				.info("exit of updateApproveLeave of ApproveLeaveTransactionImpl class.");

	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#getCoCcurricularLeaves(java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StuCocurrLeave> getCoCcurricularLeaves(Date fromDate,
			Date toDate) throws ApplicationException {
		log
				.info("entering into getApprovedLeaves of ApproveLeaveTransactionImpl class.");
		List<StuCocurrLeave> studentLeaveTypes;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query leaveTypeQuery = session
					.createQuery("from StuCocurrLeave s "
							+ " where s.startDate >= :fromDate and s.endDate <= :endDate and s.isCocurrLeaveCancelled = 0 ");
			leaveTypeQuery.setDate("fromDate", fromDate);
			leaveTypeQuery.setDate("endDate", toDate);

			studentLeaveTypes = leaveTypeQuery.list();

		} catch (Exception e) {
			log
					.info(
							"error in getApprovedLeaves of ApproveLeaveTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of getApprovedLeaves of ApproveLeaveTransactionImpl class.");
		return studentLeaveTypes;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#getCoCurricularLeave(int)
	 */
	@Override
	public StuCocurrLeave getCoCurricularLeave(int leaveId)
			throws ApplicationException {

		log.debug("Txn Impl : Entering getLeaveApproval ");
		Session session = null;
		Transaction transaction = null;
		StuCocurrLeave student;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			student = (StuCocurrLeave) session.get(StuCocurrLeave.class,
					leaveId);
			transaction.commit();
			//session.close();
			log
					.info("exit of isAttendenceTaken of ApproveLeaveTransactionImpl class.");
			return student;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getLeaveApproval with Exception");
			transaction.rollback();
			//session.close();
			throw new ApplicationException(e);
		}

	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#deleteCoCcurricularLeave(com.kp.cms.forms.attendance.ApproveLeaveForm, java.util.Map)
	 */
	@Override
	public void deleteCoCcurricularLeave(ApproveLeaveForm approveLeaveForm,
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap)
			throws ApplicationException {

		log.debug("Txn Impl : Entering deleteLeaveApproval ");
		Session session = null;
		Transaction transaction = null;
		StuCocurrLeave student;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			List<ApproveLeaveTO> approveLeaveTOForAttendence = approveLeaveToMap
					.get(2);
			if (approveLeaveTOForAttendence != null) {
				Iterator<ApproveLeaveTO> AttendenceIterator = approveLeaveTOForAttendence
						.iterator();
				while (AttendenceIterator.hasNext()) {
					ApproveLeaveTO approveLeaveTO = (ApproveLeaveTO) AttendenceIterator
							.next();

					AttendanceStudent attendence = (AttendanceStudent) session
							.get(AttendanceStudent.class, Integer
									.valueOf(approveLeaveTO.getAttendenceId()));
					attendence.setIsCoCurricularLeave(false);
					attendence.setModifiedBy(approveLeaveForm.getUserId());
					attendence.setLastModifiedDate(new Date());
					session.update(attendence);
				}

			}
			student = (StuCocurrLeave) session.get(StuCocurrLeave.class,
					Integer.parseInt(approveLeaveForm.getId()));
			student.setIsCocurrLeaveCancelled(true);
			transaction.commit();
			session.close();
			log.debug("Txn Impl : Leaving deleteLeaveApproval with success");
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving deleteLeaveApproval with Exception");
			transaction.rollback();
			//session.close();
			throw new ApplicationException(e);
		}
		log
				.info("exit of isAttendenceTaken of ApproveLeaveTransactionImpl class.");

	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getStudentListByclass(ApproveLeaveForm approveLeaveForm)throws ApplicationException {
	
     log.info("entering into getStudentListByClassSchemeWiseId of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		List<Student> studentSearchResult = null;
		Map<String,String>  regNoMap=new HashMap<String,String>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			
			Query studentQuery = session
					.createQuery("select s from Student s "+
								 " inner join s.classSchemewise cls"+
								 " inner join s.admAppln adm"+
								 " where cls.id in(:classIdsLIst) and adm.isCancelled=0 and s.isAdmitted=1 and (s.isHide=0 or s.isHide is null)"+
								 " and s.id not in(select esdrd.student.id from ExamStudentDetentionRejoinDetails esdrd "+
								 " where (esdrd.detain=1 or esdrd.discontinued=1) and (esdrd.rejoin is null or esdrd.rejoin=0))"+
								 " group by s.id");
			studentQuery.setString("classIdsLIst", approveLeaveForm.getClassSchemewiseId());
					
			studentSearchResult = studentQuery.list();
			
			if(studentSearchResult !=null && !studentSearchResult.isEmpty()){
				Iterator<Student> itr = studentSearchResult.iterator();
				while (itr.hasNext()) {
					Student student = (Student) itr.next();
					if (student != null ) {
						if(approveLeaveForm.isRegisterNo()){
							regNoMap.put(student.getRegisterNo(), student.getRegisterNo());
						}else{
							regNoMap.put(student.getRollNo(), student.getRollNo());
						}
					}
				}
			}
		
		} catch (Exception e) {
			log.info("error in getStudentListByClassSchemeWiseId of ActivityAttendenceTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getStudentListByClassSchemeWiseId of ActivityAttendenceTransactionImpl class.");
		return regNoMap;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Period> getPeriodListForStartDateSelectedDateMoreThanOneDay(int classSchemeWiseId,
			String startTime) throws ApplicationException {
		log
				.info("entering into getPeriodListForStartDate of ActivityAttendenceTransactionImpl class.");
		Session session = null;
		List<Period> period = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from Period period where period.classSchemewise.id = :classSchemeWiseId and period.isActive = true"
							+ " and period.startTime >=:startTime order by period.startTime");
			studentQuery.setInteger("classSchemeWiseId", classSchemeWiseId);
			studentQuery.setString("startTime", startTime);
			period = studentQuery.list();

		} catch (Exception e) {
			log
					.info(
							"error in getPeriodListForStartDate of ActivityAttendenceTransactionImpl class.",
							e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log
				.info("exit of getPeriodListForStartDate of ActivityAttendenceTransactionImpl class.");
		return period;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getStudentRegisterNumbersListByclass(ApproveLeaveForm approveLeaveForm)throws ApplicationException {
		
	     log.info("entering into getStudentListByClassSchemeWiseId of ActivityAttendenceTransactionImpl class.");
			Session session = null;
			List<Student> studentSearchResult = null;
			List<Integer> regNoList = new ArrayList<Integer>();
			Map<String,String>  regNoMap=new HashMap<String,String>();
			String a = approveLeaveForm.getClassValues();
			String[] str = a.split(",");
			for (int x = 0; x < str.length; x++) {
				if (str[x] != null && str[x].length() > 0) {
					regNoList.add(Integer.parseInt((str[x]).toString()));
				}
			}
			
			
			
			try {
				//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = HibernateUtil.getSession();
				
				Query studentQuery = session
						.createQuery("select s from Student s "+
									 " inner join s.classSchemewise cls"+
									 " inner join s.admAppln adm"+
									 " where cls.id in(:classIdsLIst) and adm.isCancelled=0 and s.isAdmitted=1 and (s.isHide=0 or s.isHide is null)"+
									 " and s.id not in(select esdrd.student.id from ExamStudentDetentionRejoinDetails esdrd "+
									 " where (esdrd.detain=1 or esdrd.discontinued=1) and (esdrd.rejoin is null or esdrd.rejoin=0))"+
									 " group by s.id");
				studentQuery.setParameterList("classIdsLIst", regNoList);
						
				studentSearchResult = studentQuery.list();
				
				if(studentSearchResult !=null && !studentSearchResult.isEmpty()){
					Iterator<Student> itr = studentSearchResult.iterator();
					while (itr.hasNext()) {
						Student student = (Student) itr.next();
						if (student != null ) {
								regNoMap.put(student.getRegisterNo(), student.getRegisterNo());
						}
					}
				}
			
			} catch (Exception e) {
				log.info("error in getStudentListByClassSchemeWiseId of ActivityAttendenceTransactionImpl class.",e);
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
			log.info("exit of getStudentListByClassSchemeWiseId of ActivityAttendenceTransactionImpl class.");
			return regNoMap;
		}
	
	
	
	@SuppressWarnings("unchecked")
	public Map<Integer,String> getClassesBySemType(ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log.debug("inside getTeachers");
		Session session=null;
		List<Integer> semTypeEven =new ArrayList<Integer>();
		List<Integer> semTypeOdd =new ArrayList<Integer>();
		Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			try{
			session=HibernateUtil.getSession();
			List<Integer>	classes=session.createQuery("select c.termNumber from Classes c where c.isActive=1 group by termNumber").list();
			 for( int i=0; i<classes.size(); i++)
	          {         
	                   int s = classes.get(i);
	                   if(s % 2==0){
	                	   semTypeEven.add(s);
	                   }else
	                	   semTypeOdd.add(s);
	          }
			String sql="select c from ClassSchemewise c ";
			if(approveLeaveForm.getSemType().equalsIgnoreCase("even")){
				sql = sql + " inner join   c.classes cls "+
				 " where c.curriculumSchemeDuration.academicYear=" +approveLeaveForm.getYear()+" and cls.termNumber in(:semTypeEven)";
			}else{
				sql = sql + " inner join  c.classes cls"+
				" where c.curriculumSchemeDuration.academicYear=" +approveLeaveForm.getYear()+" and cls.termNumber in(:semTypeOdd)";
			}
			Query query1=session.createQuery(sql);
			if(approveLeaveForm.getSemType().equalsIgnoreCase("even")){
			query1.setParameterList("semTypeEven", semTypeEven);
			}else{
			query1.setParameterList("semTypeOdd", semTypeOdd);
			}
			List<ClassSchemewise> classList = query1.list();
			if(classList!=null && !classList.isEmpty()){
				for (ClassSchemewise classSchemewise : classList) {
					if(classSchemewise.getClasses()!=null){
						if(classSchemewise.getClasses().getName()!=null && !classSchemewise.getClasses().getName().isEmpty()){
							classMap.put(classSchemewise.getId(), classSchemewise.getClasses().getName());
						}
					}
				}
			}
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			log.debug("leaving getTeachers");
			}catch (Exception e) {
				log.error("Error in getDetails...", e);
				session.flush();
				throw new ApplicationException(e);
			}
			return classMap;
	}

	/* (non-Javadoc)
	 * Added by mahi to get Period Id 
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#getPeriodIdByNameAndClassId(java.lang.String, int)
	 */
	@Override
	public Integer getPeriodIdByNameAndClassId(String periodName, int classId,String startTime,String endTime)
			throws Exception {
		Session session=null;
		Integer periodId=null;
		try{
			session=HibernateUtil.getSession();
			String quer="select period.id from Period period where period.periodName='"+periodName+"'" +
					" and period.isActive=1 and period.classSchemewise.id="+classId;
			if(startTime!=null && endTime!=null){
				quer=quer+" and period.startTime='"+startTime+"' and period.endTime='"+endTime+"'";
			}
			Query query=session.createQuery(quer);
			periodId = (Integer) query.uniqueResult();
		}catch(Exception e){
			log.info("exception occured in getStudentDetailsByChallanNo Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return periodId;
	}

	/* (non-Javadoc)
	 * addded by mahi
	 * @see com.kp.cms.transactions.attandance.IActivityAttendenceTransaction#getStartPeriodAndEndPeriodByClassId(com.kp.cms.forms.attendance.ApproveLeaveForm)
	 */
	@Override
	public List<Integer> getStartPeriodAndEndPeriodByClassId(
			ApproveLeaveForm activityAttendanceForm,String startTime,String endTime) throws Exception {
		Session session=null;
		List<Integer> periodList=new ArrayList<Integer>();
		try{
			session=HibernateUtil.getSession();
			String quer="select period.id from Period period where  period.isActive=1" +
					" and period.classSchemewise.id='"+activityAttendanceForm.getClassSchemewiseId()+"'";
			if(startTime!=null && endTime!=null){
				quer=quer+" and period.startTime between '"+startTime+"' and '"+endTime+"'";
			}
			quer=quer+" order by period.startTime asc" ;
			
			Query query=session.createQuery(quer);
			query.setMaxResults(1);
			Integer periodId =  (Integer) query.uniqueResult();
			if(periodId!=null && periodId>0){
				periodList.add(periodId);
			}
			String quer1="select period.id from Period period where  period.isActive=1" +
			    " and period.classSchemewise.id='"+activityAttendanceForm.getClassSchemewiseId()+"'";
			if(startTime!=null && endTime!=null){
				quer1=quer1+" and period.startTime between '"+startTime+"' and '"+endTime+"'";
			}
			quer1=quer1+" order by period.startTime desc";
			Query query1=session.createQuery(quer1);
			query1.setMaxResults(1);
			Integer periodId1 =  (Integer) query1.uniqueResult();
			if(periodId1!=null && periodId1>0){
				periodList.add(periodId1);
			}
		}catch(Exception e){
			log.info("exception occured in getStudentDetailsByChallanNo Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return periodList;
	}

}