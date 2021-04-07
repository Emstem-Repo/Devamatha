package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.LeaveType;
import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.transactions.attandance.IApproveLeaveTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

/**
 * An implementation class for the Approve leave transaction.
 *
 */
public class ApproveLeaveTransactionImpl implements IApproveLeaveTransaction {
	
	private static Log log = LogFactory
			.getLog(ApproveLeaveTransactionImpl.class);

	/** 
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#isAttendenceTaken(com.kp.cms.forms.attendance.ApproveLeaveForm)
	 */
	@Override
	public List<Attendance> isAttendenceTaken(ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		log.info("entering into isAttendenceTaken of ApproveLeaveTransactionImpl class.");
		List<Attendance> attendenceList = null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query attendenceQuery = session
					.createQuery("from Attendance attendence inner join attendence.attendanceClasses attendenceClass with attendenceClass.classSchemewise.id = :classSchemewiseId where attendence.attendanceDate between :startDate and :endDate ");

			attendenceQuery.setDate("startDate", CommonUtil
					.ConvertStringToSQLDate(approveLeaveForm.getFromDate()));
			attendenceQuery.setDate("endDate", CommonUtil
					.ConvertStringToSQLDate(approveLeaveForm.getToDate()));
			attendenceQuery.setString("classSchemewiseId", approveLeaveForm
					.getClassSchemewiseId());
			if (attendenceQuery.list().size() > 0) {
				List attendenceObjects = attendenceQuery.list();
				attendenceList = getAttendenceListfromObjects(attendenceObjects);
			}

		} catch (Exception e) {
			log.info("error in isAttendenceTaken of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of isAttendenceTaken of ApproveLeaveTransactionImpl class.");
		return attendenceList;
	}

	/**
	 * Get the attendance list from the raw list.
	 * @param attendenceObjects - Represents the list of object array.
	 * @return - List of attendance objects.
	 */
	private List<Attendance> getAttendenceListfromObjects(List attendenceObjects) {
		log.info("entering into getAttendenceListfromObjects of ApproveLeaveTransactionImpl class.");
		List<Attendance> attendanceList = new ArrayList<Attendance>();
		Iterator attendanceIterator = attendenceObjects.iterator();
		while (attendanceIterator.hasNext()) {
			Object[] object = ((Object[]) attendanceIterator.next());
			attendanceList.add((Attendance) object[0]);
		}
		log.info("exit of getAttendenceListfromObjects of ApproveLeaveTransactionImpl class.");
		return attendanceList;
	}

	/** 
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#getAttendenceList(java.lang.String, java.util.Set, com.kp.cms.forms.attendance.ApproveLeaveForm)
	 */
	@Override
	public List<AttendanceStudent> getAttendenceList(String attendenceDate,
			Set<Integer> periods, ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		log.info("entering into getAttendenceList of ApproveLeaveTransactionImpl class.");
		List<AttendanceStudent> attendenceList = null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();

			Set<String> registerNoSet = new HashSet<String>();
			registerNoSet.addAll(approveLeaveForm.getRegisterNoList());
			Query attendenceQuery = null;
			if (approveLeaveForm.isRegisterNo()) {
				attendenceQuery = session
						.createQuery("from AttendanceStudent attendenceStudent "
								+ "  inner join attendenceStudent.attendance attendence with attendence.attendanceDate = :attendenceDate "
								+ "  inner join attendence.attendancePeriods periods  "
								+ "  inner join attendence.attendanceClasses attendenceClass with attendenceClass.classSchemewise.id = :classSchemewiseId "
								+ " where periods.period.id in (:periodList) and  attendenceStudent.student.registerNo in (:registerNo) ");
			} else {
				attendenceQuery = session
						.createQuery("from AttendanceStudent attendenceStudent "
								+ "  inner join attendenceStudent.attendance attendence with attendence.attendanceDate = :attendenceDate "
								+ "  inner join attendence.attendancePeriods periods  "
								+ "  inner join attendence.attendanceClasses attendenceClass with attendenceClass.classSchemewise.id = :classSchemewiseId "
								+ " where periods.period.id in (:periodList) and  attendenceStudent.student.rollNo in (:registerNo)");
			}

			attendenceQuery.setDate("attendenceDate", CommonUtil
					.ConvertStringToSQLDate(attendenceDate));
			attendenceQuery.setParameterList("periodList", periods);
			attendenceQuery.setString("classSchemewiseId", approveLeaveForm
					.getClassSchemewiseId());
			attendenceQuery.setParameterList("registerNo", registerNoSet);
			if (attendenceQuery.list().size() > 0) {
				List attendenceObjects = attendenceQuery.list();
				attendenceList = getAttendenceStudanceListfromObjects(attendenceObjects);
			}

		} catch (Exception e) {
			log.info("error in getAttendenceList of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getAttendenceList of ApproveLeaveTransactionImpl class.");
		return attendenceList;
	}

	/**
	 *  Returns the Attendance students objects from the raw List
	 * @param attendenceObjects - Represents the List of Object array.
	 * @return  List of attendance students
	 */
	private List<AttendanceStudent> getAttendenceStudanceListfromObjects(
			List attendenceObjects) {
		log.info("entering into getAttendenceStudanceListfromObjects of ApproveLeaveTransactionImpl class.");
		List<AttendanceStudent> attendanceList = new ArrayList<AttendanceStudent>();
		Iterator attendanceStudentIterator = attendenceObjects.iterator();
		while (attendanceStudentIterator.hasNext()) {
			Object[] object = ((Object[]) attendanceStudentIterator.next());
			attendanceList.add((AttendanceStudent) object[0]);
		}
		log.info("exit of getAttendenceStudanceListfromObjects of ApproveLeaveTransactionImpl class.");
		return attendanceList;
	}

	/** 
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#getAttendenceListBetweenDays(java.lang.String, java.lang.String, java.util.Set, com.kp.cms.forms.attendance.ApproveLeaveForm)
	 */
	@Override
	public List<AttendanceStudent> getAttendenceListBetweenDays(
			String dayAfterStart, String dayAfterEnd, Set<Integer> periods,
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log.info("entering into getAttendenceListBetweenDays of ApproveLeaveTransactionImpl class.");
		List<AttendanceStudent> attendenceList = null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Set<String> registerNoSet = new HashSet<String>();
			registerNoSet.addAll(approveLeaveForm.getRegisterNoList());
			Query attendenceQuery = null;

			if (approveLeaveForm.isRegisterNo()) {
				attendenceQuery = session
						.createQuery("from AttendanceStudent attendenceStudent "
								+ "  inner join attendenceStudent.attendance attendence with attendence.attendanceDate between :startDate and :endDate "
								+ "  inner join attendence.attendancePeriods periods  "
								+ "  inner join attendence.attendanceClasses attendenceClass with attendenceClass.classSchemewise.id = :classSchemewiseId "
								+ " where periods.period.id  in (:periodList) and  attendenceStudent.student.registerNo in (:registerNo) group by attendenceStudent.id" );
			} else {
				attendenceQuery = session
						.createQuery("from AttendanceStudent attendenceStudent "
								+ "  inner join attendenceStudent.attendance attendence with attendence.attendanceDate between :startDate and :endDate "
								+ "  inner join attendence.attendancePeriods periods  "
								+ "  inner join attendence.attendanceClasses attendenceClass with attendenceClass.classSchemewise.id = :classSchemewiseId "
								+ " where periods.period.id  in (:periodList) and attendenceStudent.student.rollNo  in (:registerNo)group by attendenceStudent.id " );
			}

			attendenceQuery.setDate("startDate", CommonUtil
					.ConvertStringToSQLDate(dayAfterStart));
			attendenceQuery.setDate("endDate", CommonUtil
					.ConvertStringToSQLDate(dayAfterEnd));
			attendenceQuery.setParameterList("periodList", periods);
			attendenceQuery.setParameterList("registerNo", registerNoSet);
			attendenceQuery.setString("classSchemewiseId", approveLeaveForm
					.getClassSchemewiseId());
			if (attendenceQuery.list().size() > 0) {
				List attendenceObjects = attendenceQuery.list();
				attendenceList = getAttendenceStudanceListfromObjects(attendenceObjects);
			}

		} catch (Exception e) {
			log.info("error in getAttendenceListBetweenDays of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getAttendenceListBetweenDays of ApproveLeaveTransactionImpl class.");
		return attendenceList;
	}

	


	/**
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#getLeaveType()
	 */
	public List<LeaveType> getLeaveType() throws ApplicationException {
		log.info("entering into getLeaveType of ApproveLeaveTransactionImpl class.");
		List<LeaveType> leaveTypeList;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query leaveTypeQuery = session
					.createQuery("from LeaveType leaveType where leaveType.isActive = 1");

			leaveTypeList = leaveTypeQuery.list();

		} catch (Exception e) {
			log.info("error in getLeaveType of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getLeaveType of ApproveLeaveTransactionImpl class.");
		return leaveTypeList;

	}

	/**
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#updateApproveLeave(java.util.Map, com.kp.cms.forms.attendance.ApproveLeaveForm, com.kp.cms.bo.admin.StudentLeave, java.lang.String)
	 */
	@Override
	public void updateApproveLeave(
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap,
			ApproveLeaveForm approveLeaveForm, StudentLeave studentLeave,
			String mode) throws ApplicationException {
		log.info("entering into updateApproveLeave of ApproveLeaveTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			if ("update".equals(mode)) {
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
										.valueOf(approveLeaveTO
												.getAttendenceId()));
						attendence.setIsOnLeave(false);
						attendence.setModifiedBy(approveLeaveForm.getUserId());
						attendence.setLastModifiedDate(new Date());
						session.update(attendence);
					}
				}
			}

			List<ApproveLeaveTO> approveLeaveTOForAttendence = approveLeaveToMap
					.get(1);
			if (approveLeaveTOForAttendence != null) {
				Iterator<ApproveLeaveTO> AttendenceIterator = approveLeaveTOForAttendence
						.iterator();
				while (AttendenceIterator.hasNext()) {
					ApproveLeaveTO approveLeaveTO = (ApproveLeaveTO) AttendenceIterator
							.next();

					AttendanceStudent attendence = (AttendanceStudent) session
							.get(AttendanceStudent.class, Integer
									.valueOf(approveLeaveTO.getAttendenceId()));
					attendence.setIsOnLeave(true);
					attendence.setIsPresent(false);
					attendence.setModifiedBy(approveLeaveForm.getUserId());
					attendence.setLastModifiedDate(new Date());
					session.update(attendence);
				}
			}
			if ("update".equals(mode)) {
				session.update(studentLeave);
			} else {
				session.save(studentLeave);
			}
			transaction.commit();
		} catch (Exception e) {
			log.info("error in updateApproveLeave of ApproveLeaveTransactionImpl class.",e);
			if (transaction != null)
				transaction.rollback();
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateApproveLeave of ApproveLeaveTransactionImpl class.");
	}

	/**
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#getApprovedLeaves(java.util.Date, java.util.Date)
	 */
	public List<StudentLeave> getApprovedLeaves(Date fromDate, Date toDate)
			throws Exception {
		log.info("entering into getApprovedLeaves of ApproveLeaveTransactionImpl class.");
		List<StudentLeave> studentLeaveTypes;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query leaveTypeQuery = session
					.createQuery("from StudentLeave s "
							+ " where s.startDate >= :fromDate and s.endDate <= :endDate and leaveCanceled = 0");
			leaveTypeQuery.setDate("fromDate", fromDate);
			leaveTypeQuery.setDate("endDate", toDate);

			studentLeaveTypes = leaveTypeQuery.list();

		} catch (Exception e) {
			log.info("error in getApprovedLeaves of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getApprovedLeaves of ApproveLeaveTransactionImpl class.");
		return studentLeaveTypes;
	}

	

	/**
	 * This method delete the FeeAssignment from fee & fee_account_assignment
	 * tables.
	 * 
	 * @return goes fine if no error and throws exception if fails.
	 */
	public StudentLeave getLeaveApproval(int leaveId) throws Exception {
		log.debug("Txn Impl : Entering getLeaveApproval ");
		Session session = null;
		Transaction transaction = null;
		StudentLeave student;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			student = (StudentLeave) session.get(StudentLeave.class, leaveId);
			transaction.commit();
			//session.close();
			log.info("exit of isAttendenceTaken of ApproveLeaveTransactionImpl class.");
			return student;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getLeaveApproval with Exception");
			transaction.rollback();
			//session.close();
			throw e;
		}
		
	}

	/**
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#getStudentIdByCourseAnd(int, boolean, java.lang.String)
	 */
	public Integer getStudentIdByCourseAnd(int classSehemeId, boolean isRegNo,
			String num) throws ApplicationException {
		log.info("entering into getStudentIdByCourseAnd of ApproveLeaveTransactionImpl class.");
		Integer studentId;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();

			StringBuffer query = new StringBuffer();

			if (isRegNo)
				query.append(" select s.id from Student s where s.classSchemewise.id =:classId and s.registerNo = :num and s.admAppln.isCancelled=0");
			else
				query.append( " select s.id from Student s where s.classSchemewise.id =:classId and s.rollNo = :num and s.admAppln.isCancelled=0");
			Query leaveTypeQuery = session.createQuery(query.toString());

			leaveTypeQuery.setInteger("classId", classSehemeId);
			leaveTypeQuery.setString("num", num);
			studentId = (Integer) leaveTypeQuery.uniqueResult();

		} catch (Exception e) {
			log.info("error in getStudentIdByCourseAnd of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getStudentIdByCourseAnd of ApproveLeaveTransactionImpl class.");
		return studentId;

	}
	
	/**
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#getApprovedLeaves(java.util.Date, java.util.Date)
	 */
	public List<StudentLeave> getApprovedLeavesForDayAndClass(Date date,Set<Integer> classSet) throws Exception {
		log.info("entering into getApprovedLeavesForDayAndClass of ApproveLeaveTransactionImpl class.");
		List<StudentLeave> studentLeaveTypes;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query leaveTypeQuery = session
					.createQuery("from StudentLeave s "
							+ " where s.startDate <= :fromDate and s.endDate >= :endDate and leaveCanceled = 0 " +
							" and s.classSchemewise.id in (:classId)");
			leaveTypeQuery.setDate("fromDate", date);
			leaveTypeQuery.setDate("endDate", date);
			leaveTypeQuery.setParameterList("classId", classSet);

			studentLeaveTypes = leaveTypeQuery.list();

		} catch (Exception e) {
			log.info("error in getApprovedLeavesForDayAndClass of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getApprovedLeavesForDayAndClass of ApproveLeaveTransactionImpl class.");
		return studentLeaveTypes;
	}

	/** 
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#isLeaveAlreadyApproved(com.kp.cms.forms.attendance.ApproveLeaveForm)
	 */
	@Override
	public boolean isLeaveAlreadyApproved(ApproveLeaveForm approveLeaveForm,String startTime,String endTime)
			throws ApplicationException {
		
		log.info("entering into isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.");

		boolean isLeaveApproved = false;
		Session session = null;
		List<StudentLeave> period = null;

		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query studentQuery = null;
			if(approveLeaveForm.getFullDay()!=null && approveLeaveForm.getFullDay().equalsIgnoreCase("Yes")){

				if (approveLeaveForm.isRegisterNo()) {
					
					String query = "from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
					" where ( ( :startDate between studentLeave.startDate   and  studentLeave.endDate )" +
					"  or (:endDate  between studentLeave.startDate  and  studentLeave.endDate ) " +
					" or (:startDate >=  studentLeave.startDate and :endDate <=  studentLeave.endDate))"+
					" and studentLeaveDetails.student.registerNo in (:registerNo) " +
					" and studentLeave.leaveCanceled = 0 and studentLeave.classSchemewise.id="+approveLeaveForm.getClassSchemewiseId();
					
					studentQuery = session.createQuery(query);			
			
				} else {
					String query = "from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
					" where ( ( :startDate between studentLeave.startDate   and  studentLeave.endDate )" +
					"  or (:endDate  between studentLeave.startDate  and  studentLeave.endDate ) " +
					" or (:startDate >=  studentLeave.startDate and :endDate <=  studentLeave.endDate))"+
					" and studentLeaveDetails.student.rollNo in (:registerNo) " +
					" and studentLeave.leaveCanceled = 0 and studentLeave.classSchemewise.id="+approveLeaveForm.getClassSchemewiseId();

					studentQuery = session.createQuery(query);			
				}
			}
			else{
			
				if (approveLeaveForm.isRegisterNo()) {
				
				String query = "from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
				" where ( ( :startDate between studentLeave.startDate   and  studentLeave.endDate )" +
				"  or (:endDate  between studentLeave.startDate  and  studentLeave.endDate ) " +
				" or (:startDate >=  studentLeave.startDate and :endDate <=  studentLeave.endDate))"
				+ " and(( '"+startTime+"' >= studentLeave.startPeriod.startTime and '"+endTime+"'<= studentLeave.endPeriod.startTime)" +
				" or( '"+startTime+"' >= studentLeave.startPeriod.startTime and '"+endTime+"'<= studentLeave.endPeriod.startTime )" +
				" or ( '"+startTime+"'<= studentLeave.startPeriod.startTime and '"+endTime+"' >= studentLeave.endPeriod.startTime))"+
				" and ( (studentLeave.startPeriod between :startPeriod and :endPeriod) " +
				" or (studentLeave.endPeriod between :startPeriod and :endPeriod) )" +
				" and studentLeaveDetails.student.registerNo in (:registerNo) " +
				" and studentLeave.leaveCanceled = 0 ";
				
				/*studentQuery = session
						.createQuery("from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
								" where ( ( studentLeave.startDate between :startDate and  :endDate )" +
								"  or (studentLeave.endDate  between  :startDate and  :endDate )" ) " +
								" and ( (studentLeave.startPeriod between :startPeriod and :endPeriod) " +
								" or (studentLeave.endPeriod between :startPeriod and :endPeriod) )" +
								" and studentLeaveDetails.student.registerNo in (:registerNo) " +
								" and studentLeave.leaveCanceled = 0 ");*/
				studentQuery = session.createQuery(query);			
				
		
			} else {
				String query = "from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
				" where ( ( :startDate between studentLeave.startDate   and  studentLeave.endDate )" +
				"  or (:endDate  between studentLeave.startDate  and  studentLeave.endDate ) " +
				" or (:startDate >=  studentLeave.startDate and :endDate <=  studentLeave.endDate))"
				+ " and(( '"+startTime+"' >= studentLeave.startPeriod.startTime and '"+endTime+"'<= studentLeave.endPeriod.startTime)" +
				" or( '"+startTime+"' >= studentLeave.startPeriod.startTime and '"+endTime+"'<= studentLeave.endPeriod.startTime )" +
				" or ( '"+startTime+"'<= studentLeave.startPeriod.startTime and '"+endTime+"' >= studentLeave.endPeriod.startTime))"+
				" and ( (studentLeave.startPeriod between :startPeriod and :endPeriod) " +
				" or (studentLeave.endPeriod between :startPeriod and :endPeriod) )" +
				" and studentLeaveDetails.student.rollNo in (:registerNo) " +
				" and studentLeave.leaveCanceled = 0 ";
				
//				String query = "from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
//				" where ( ( :startDate between studentLeave.startDate   and  studentLeave.endDate )" +
//				"  or (:endDate  between studentLeave.startDate  and  studentLeave.endDate ) " +
//				" or (:startDate >=  studentLeave.startDate and :endDate <=  studentLeave.endDate))"+
//				" and ( (studentLeave.startPeriod between :startPeriod and :endPeriod) " +
//				" or (studentLeave.endPeriod between :startPeriod and :endPeriod) )" +
//				" and studentLeaveDetails.student.rollNo in (:registerNo) " +
//				" and studentLeave.leaveCanceled = 0 ";
				
				studentQuery = session.createQuery(query);			
			
				/*studentQuery = session
				.createQuery("from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
						" where ( ( studentLeave.startDate between :startDate and  :endDate )" +
						"  or (studentLeave.endDate  between  :startDate and  :endDate ) " ) " +
						" and ( (studentLeave.startPeriod between :startPeriod and :endPeriod) " +
						" or (studentLeave.endPeriod between :startPeriod and :endPeriod) )" +
						" and studentLeaveDetails.student.rollNo in (:registerNo) " +
						" and studentLeave.leaveCanceled = 0 ");*/
		

			}
				studentQuery.setInteger("startPeriod",Integer.valueOf(approveLeaveForm.getFromPeriod()));
				studentQuery.setInteger("endPeriod",Integer.valueOf(approveLeaveForm.getToPeriod()));
		}
		
			studentQuery.setDate("startDate", CommonUtil
					.ConvertStringToSQLDate(approveLeaveForm.getFromDate()));
			studentQuery.setDate("endDate", CommonUtil
					.ConvertStringToSQLDate(approveLeaveForm.getToDate()));
				studentQuery.setParameterList("registerNo", approveLeaveForm
					.getRegisterNoSet());
			period = studentQuery.list();
			
			if (period != null && !period.isEmpty()) {
				isLeaveApproved = true;
			}
		} catch (Exception e) {
			log.info("error in isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.");
		return isLeaveApproved;
	}
	
	
	
	/**
	 * @see com.kp.cms.transactions.attandance.IApproveLeaveTransaction#isActivityAttendance(com.kp.cms.forms.attendance.ApproveLeaveForm)
	 */
	public boolean isCocurricularLeave(ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		
		log.info("entering into isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.");

		boolean isLeaveApproved = false;
		Session session = null;
		List<StudentLeave> period = null;

		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query studentQuery = null;
			if (approveLeaveForm.isRegisterNo()) {
				studentQuery = session
						.createQuery("from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses " +
								" where ( ( stuCocurrLeave.startDate between :startDate and  :endDate )" +
								"  or (stuCocurrLeave.endDate  between  :startDate and  :endDate ) ) " +
								" and ( (stuCocurrLeave.startPeriod between :startPeriod and :endPeriod) " +
								" or (stuCocurrLeave.endPeriod between :startPeriod and :endPeriod) )" +
								" and stuCocurrLeaveDetailses.student.registerNo in (:registerNo) " +
								" and stuCocurrLeave.isCocurrLeaveCancelled = 0 ");
				
		
			} else {
			
				studentQuery = session
				.createQuery("from StuCocurrLeave stuCocurrLeave inner join stuCocurrLeave.stuCocurrLeaveDetailses stuCocurrLeaveDetailses " +
						" where ( ( stuCocurrLeave.startDate between :startDate and  :endDate )" +
						"  or (stuCocurrLeave.endDate  between  :startDate and  :endDate ) ) " +
						" and ( (stuCocurrLeave.startPeriod between :startPeriod and :endPeriod) " +
						" or (stuCocurrLeave.endPeriod between :startPeriod and :endPeriod) )" +
						" and stuCocurrLeaveDetailses.student.rollNo in (:registerNo) " +
						" and stuCocurrLeave.isCocurrLeaveCancelled = 0 ");
		

			}
			
		
			studentQuery.setDate("startDate", CommonUtil
					.ConvertStringToSQLDate(approveLeaveForm.getFromDate()));
			studentQuery.setDate("endDate", CommonUtil
					.ConvertStringToSQLDate(approveLeaveForm.getToDate()));
			studentQuery.setInteger("startPeriod",Integer.valueOf(approveLeaveForm.getFromPeriod()));
			studentQuery.setInteger("endPeriod",Integer.valueOf(approveLeaveForm.getToPeriod()));	studentQuery.setParameterList("registerNo", approveLeaveForm
					.getRegisterNoSet());
			period = studentQuery.list();
			
			if (period != null && !period.isEmpty()) {
				isLeaveApproved = true;
			}
		} catch (Exception e) {
			log.info("error in isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.");
		return isLeaveApproved;
	}

	@Override
	public void deleteLeaveApproval(int leaveId,
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap,String userId)
			throws Exception {
		
		

		log.debug("Txn Impl : Entering deleteLeaveApproval ");
		Session session = null;
		Transaction transaction = null;
		StudentLeave student;
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
					attendence.setIsOnLeave(false);
					attendence.setModifiedBy(userId);
					attendence.setLastModifiedDate(new Date());
					session.update(attendence);
				}

			}
			student = (StudentLeave) session.get(StudentLeave.class, leaveId);
			student.setLeaveCanceled(true);
			transaction.commit();
			session.close();
			log.debug("Txn Impl : Leaving deleteLeaveApproval with success");
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving deleteLeaveApproval with Exception");
			transaction.rollback();
			session.close();
			throw e;
		}
		log.info("exit of isAttendenceTaken of ApproveLeaveTransactionImpl class.");
	
		
	}

	@Override
	public boolean isLeaveAlreadyApproved(ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		
		log.info("entering into isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.");

		boolean isLeaveApproved = false;
		Session session = null;
		List<StudentLeave> period = null;

		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query studentQuery = null;
			if (approveLeaveForm.isRegisterNo()) {
				
				String query = "from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
				" where ( ( :startDate between studentLeave.startDate   and  studentLeave.endDate )" +
				"  or (:endDate  between studentLeave.startDate  and  studentLeave.endDate ) " +
				" or (:startDate >=  studentLeave.startDate and :endDate <=  studentLeave.endDate))"+
				" and ( (studentLeave.startPeriod between :startPeriod and :endPeriod) " +
				" or (studentLeave.endPeriod between :startPeriod and :endPeriod) )" +
				" and studentLeaveDetails.student.registerNo in (:registerNo) " +
				" and studentLeave.leaveCanceled = 0 ";
				
				/*studentQuery = session
						.createQuery("from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
								" where ( ( studentLeave.startDate between :startDate and  :endDate )" +
								"  or (studentLeave.endDate  between  :startDate and  :endDate )" ) " +
								" and ( (studentLeave.startPeriod between :startPeriod and :endPeriod) " +
								" or (studentLeave.endPeriod between :startPeriod and :endPeriod) )" +
								" and studentLeaveDetails.student.registerNo in (:registerNo) " +
								" and studentLeave.leaveCanceled = 0 ");*/
				studentQuery = session.createQuery(query);			
				
		
			} else {
				String query = "from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
				" where ( ( :startDate between studentLeave.startDate   and  studentLeave.endDate )" +
				"  or (:endDate  between studentLeave.startDate  and  studentLeave.endDate ) " +
				" or (:startDate >=  studentLeave.startDate and :endDate <=  studentLeave.endDate))"+
				" and ( (studentLeave.startPeriod between :startPeriod and :endPeriod) " +
				" or (studentLeave.endPeriod between :startPeriod and :endPeriod) )" +
				" and studentLeaveDetails.student.rollNo in (:registerNo) " +
				" and studentLeave.leaveCanceled = 0 ";
				
				studentQuery = session.createQuery(query);			
			
				/*studentQuery = session
				.createQuery("from StudentLeave studentLeave inner join studentLeave.studentLeaveDetails studentLeaveDetails " +
						" where ( ( studentLeave.startDate between :startDate and  :endDate )" +
						"  or (studentLeave.endDate  between  :startDate and  :endDate ) " ) " +
						" and ( (studentLeave.startPeriod between :startPeriod and :endPeriod) " +
						" or (studentLeave.endPeriod between :startPeriod and :endPeriod) )" +
						" and studentLeaveDetails.student.rollNo in (:registerNo) " +
						" and studentLeave.leaveCanceled = 0 ");*/
		

			}
			
		
			studentQuery.setDate("startDate", CommonUtil
					.ConvertStringToSQLDate(approveLeaveForm.getFromDate()));
			studentQuery.setDate("endDate", CommonUtil
					.ConvertStringToSQLDate(approveLeaveForm.getToDate()));
			studentQuery.setInteger("startPeriod",Integer.valueOf(approveLeaveForm.getFromPeriod()));
			studentQuery.setInteger("endPeriod",Integer.valueOf(approveLeaveForm.getToPeriod()));	studentQuery.setParameterList("registerNo", approveLeaveForm
					.getRegisterNoSet());
			period = studentQuery.list();
			
			if (period != null && !period.isEmpty()) {
				isLeaveApproved = true;
			}
		} catch (Exception e) {
			log.info("error in isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of isLeaveAlreadyApproved of ApproveLeaveTransactionImpl class.");
		return isLeaveApproved;
	}
}