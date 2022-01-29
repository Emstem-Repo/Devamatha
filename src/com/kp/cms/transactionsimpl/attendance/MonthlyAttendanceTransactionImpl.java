package com.kp.cms.transactionsimpl.attendance;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceClass;
import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.MonthlyAttendanceEntryForm;
import com.kp.cms.to.attendance.HourseHeldTO;
import com.kp.cms.to.attendance.MonthlyAttendanceTO;
import com.kp.cms.transactions.attandance.IMonthlyAttendanceTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class MonthlyAttendanceTransactionImpl implements IMonthlyAttendanceTransaction {

	/**
	 * @see com.kp.cms.transactions.attandance.IMonthlyAttendanceTransaction#getAttendenceStudents(java.sql.Date, java.sql.Date, java.util.Set)
	 */
	@Override
	public List<Object[]> getAttendenceStudents(Date startDate,
			Date endDate, Set<Integer> classSchemeId  ,MonthlyAttendanceEntryForm monthlyAttendanceEntryForm)
			throws ApplicationException {

		Session session = null;
		List<Object[]> attendanceClassesResults = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			
			StringBuffer searchQuery = new StringBuffer();
			if(monthlyAttendanceEntryForm.getBatchId() != null && monthlyAttendanceEntryForm.getBatchId().trim().length() > 0) {
				searchQuery.append(" attendance.batch.id = " + monthlyAttendanceEntryForm.getBatchId() + " and ");
			} 
			
			if(monthlyAttendanceEntryForm.getSubjectId()!=null && monthlyAttendanceEntryForm.getSubjectId().trim().length() > 0) {
				searchQuery.append(" attendance.subject.id  =" + monthlyAttendanceEntryForm.getSubjectId() + " and ");
			}
			
			if(monthlyAttendanceEntryForm.getActivityId()!=null && monthlyAttendanceEntryForm.getActivityId().trim().length() > 0) {
				searchQuery.append(" attendance.activity.id  =" + monthlyAttendanceEntryForm.getActivityId() + " and ");
			}
			
			String selectionQuery = "";
			Query attendanceClassesQuery = null;
			if(monthlyAttendanceEntryForm.getSelectedTeacher().size() == 0) {
				selectionQuery = " select attendance.id,attendance.attendanceDate, attendance.hoursHeld,attendanceStudents.student,attendance from Attendance attendance "
					+ " inner join attendance.attendanceStudents attendanceStudents "
					+ " inner join attendance.attendanceClasses  attennenceClasses " +
							" inner join attendance.attendanceInstructors attendanceInstructors " +
							 " where " +
							 " attennenceClasses.classSchemewise.id in (:classSchemeId) and " +
							" attendance.isCanceled = 0 and " +
							" attendance.isMonthlyAttendance = 1 and attendance.attendanceType.id = " + monthlyAttendanceEntryForm.getAttendanceTypeId() + " and " + searchQuery
					+ "  attendance.attendanceDate  between :startDate and :endDate  "
					+ " group by attendance.subject.id , attendance.attendanceDate,attendanceStudents.student.id "
					+ " order by attendance.attendanceDate ";
				 attendanceClassesQuery = session
					.createQuery(selectionQuery);
			} else {
				 selectionQuery = " select attendance.id,attendance.attendanceDate, attendance.hoursHeld,attendanceStudents.student,attendance from Attendance attendance "
					+ " inner join attendance.attendanceStudents attendanceStudents "
					+ " inner join attendance.attendanceClasses  attennenceClasses " +
							" inner join attendance.attendanceInstructors attendanceInstructors " +
							 " where " +
							 "   attendanceInstructors.users.id in (:teacherSet) and"
							 +"  attendanceInstructors.users.isActive=1 and"
					+ " attennenceClasses.classSchemewise.id in (:classSchemeId) and " +
							" attendance.isCanceled = 0 and " +
							" attendance.isMonthlyAttendance = 1 and attendance.attendanceType.id = " + monthlyAttendanceEntryForm.getAttendanceTypeId() + " and " + searchQuery
					+ "  attendance.attendanceDate  between :startDate and :endDate  "
					+ " group by attendance.subject.id , attendance.attendanceDate,attendanceStudents.student.id "
					+ " order by attendance.attendanceDate ";
				 attendanceClassesQuery = session
					.createQuery(selectionQuery);
					attendanceClassesQuery.setParameterList("teacherSet",
							monthlyAttendanceEntryForm.getSelectedTeacher());
			}
			
		

			attendanceClassesQuery.setParameterList("classSchemeId",
					classSchemeId);		
			attendanceClassesQuery.setDate("startDate", startDate);
			attendanceClassesQuery.setDate("endDate", endDate);
			attendanceClassesResults = attendanceClassesQuery.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return attendanceClassesResults;
	}

	@Override
	public void persistMonthlyAttendance(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm)
			throws ApplicationException {

	List<Attendance> attendanceList =	getAttendanceList(monthlyAttendanceEntryForm);
          Iterator<Attendance> attendanceIterator = attendanceList.iterator();
      	Session session = null; 
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			while (attendanceIterator.hasNext()) {
				Attendance attendance = (Attendance) attendanceIterator.next();
				attendance.setIsMonthlyAttendance(true);
				session.saveOrUpdate(attendance);
			}
			
			transaction.commit();
		} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}				
			 throw new ApplicationException(e);		 
		 } finally {
			 if (session != null) {
					session.flush();
					session.close();
				}
		 }

	}

	/**
	 * Get the attendanceList.
	 * @param monthlyAttendanceEntryForm
	 * @return
	 * @throws ApplicationException
	 */
	private List<Attendance> getAttendanceList(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm)
			throws ApplicationException {
		List<Attendance> attendanList = new ArrayList<Attendance>();
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();

			List<MonthlyAttendanceTO> monthlyAttendanceTOList = monthlyAttendanceEntryForm
					.getMonthlyAttendanceTOList();

			Iterator<MonthlyAttendanceTO> monthlyAttendanceTOIterator = monthlyAttendanceTOList
					.iterator();

			AttendanceType attendanceType = (AttendanceType) session.get(
					AttendanceType.class, Integer
							.valueOf(monthlyAttendanceEntryForm
									.getAttendanceTypeId()));
			boolean isActivity = false;
			Activity activity = null;
			Subject subject = null;
			if (monthlyAttendanceEntryForm.getActivityId() != null
					&& monthlyAttendanceEntryForm.getActivityId().trim()
							.length() != 0) {
				isActivity = true;
				activity = (Activity) session.get(Activity.class, Integer
						.valueOf(monthlyAttendanceEntryForm.getActivityId()));
			} else {
				subject = (Subject) session.get(Subject.class, Integer
						.valueOf(monthlyAttendanceEntryForm.getSubjectId()));
			}

			Batch batch = null;
			if (monthlyAttendanceEntryForm.getBatchId() != null
					&& monthlyAttendanceEntryForm.getBatchId().trim().length() > 0) {
				batch = (Batch) session.get(Batch.class, Integer
						.valueOf(monthlyAttendanceEntryForm.getBatchId()));
			}

			while (monthlyAttendanceTOIterator.hasNext()) {
				MonthlyAttendanceTO monthlyAttendanceTO = (MonthlyAttendanceTO) monthlyAttendanceTOIterator
						.next();

				List<HourseHeldTO> hourseHeld = monthlyAttendanceTO
						.getHoursHeldTOList();

				Iterator<HourseHeldTO> hoursHeldIterator = hourseHeld
						.iterator();
				while (hoursHeldIterator.hasNext()) {
					HourseHeldTO hourseHeldTO = (HourseHeldTO) hoursHeldIterator
							.next();
					Integer month = Integer
					.valueOf(monthlyAttendanceEntryForm.getMonth()) + 1;
					Date fromDate = CommonUtil
					.ConvertStringToSQLDate(hourseHeldTO.getDay()
							+ "/"
							+ month
							+ "/"
							+ monthlyAttendanceEntryForm
									.getYear1());
			String hoursHeldValue =	monthlyAttendanceEntryForm
					.getDaysList().get(
							fromDate.getDate() - 1)
					.getHoursHeld();
			
			int hoursAttended =0;
			if(hourseHeldTO
					.getHourseHeld()!=null && hourseHeldTO
					.getHourseHeld().isEmpty()) {
				hoursAttended =0;
			} else {
				hoursAttended = Integer.valueOf(hourseHeldTO.getHourseHeld());
			}
					
					if (hoursHeldValue !=null && !hoursHeldValue.isEmpty() ) {

						Attendance attendance = null;

						if (hourseHeldTO.getAttenndenceId() == 0) {
							attendance = new Attendance();
							attendance.setCreatedBy(monthlyAttendanceEntryForm
									.getUserId());
							attendance.setCreatedDate(new java.util.Date());

							Set<Integer> selectedClassesSet = monthlyAttendanceEntryForm
									.getSelectedClasses();
							Iterator<Integer> selectedClassesSetIterator = selectedClassesSet
									.iterator();
							attendance.setAttendanceType(attendanceType);
							while (selectedClassesSetIterator.hasNext()) {
								Integer integer = (Integer) selectedClassesSetIterator
										.next();
								AttendanceClass attendenceClass = new AttendanceClass();
								ClassSchemewise classSchemewise = new ClassSchemewise();
								classSchemewise.setId(Integer.valueOf(integer));

								attendenceClass
										.setClassSchemewise(classSchemewise);
								attendance.getAttendanceClasses().add(
										attendenceClass);
							}

							Set<Integer> selectedTeacherSet = monthlyAttendanceEntryForm
									.getSelectedTeacher();

							Iterator<Integer> instructorIterator = selectedTeacherSet
									.iterator();
							while (instructorIterator.hasNext()) {
								Integer integer = (Integer) instructorIterator
										.next();
								AttendanceInstructor attendanceInstructor = new AttendanceInstructor();
								Users user = new Users();
								user.setId(integer);
								attendanceInstructor.setUsers(user);
								attendance.getAttendanceInstructors().add(
										attendanceInstructor);								
							}
							
							AttendanceStudent attendanStudent = new AttendanceStudent();
							attendanStudent.setStudent(monthlyAttendanceTO
									.getStudent());
							
						
							
							if (hoursAttended > 0) {
								attendanStudent.setIsPresent(true);
								attendanStudent.setIsOnLeave(false);
							} else {
								attendanStudent.setIsPresent(false);
								attendanStudent.setIsOnLeave(false);
							}
							attendance.getAttendanceStudents().add(
									attendanStudent);
							
							attendance.setActivity(activity);
							attendance.setIsActivityAttendance(isActivity);
							attendance.setBatch(batch);
							attendance.setSubject(subject);
						} else {
							attendance = (Attendance) session.get(
									Attendance.class, hourseHeldTO
											.getAttenndenceId());
							attendance.setModifiedBy(monthlyAttendanceEntryForm
									.getUserId());
							attendance
									.setLastModifiedDate(new java.util.Date());
							Iterator<AttendanceStudent> attendanceStudentIterator = attendance
									.getAttendanceStudents().iterator();
							while (attendanceStudentIterator.hasNext()) {
								AttendanceStudent attendanceStudent = (AttendanceStudent) attendanceStudentIterator
										.next();
								if (hoursAttended > 0) {
									attendanceStudent.setIsPresent(true);
									attendanceStudent.setIsOnLeave(false);
								} else {
									attendanceStudent.setIsPresent(false);
									attendanceStudent.setIsOnLeave(false);
								}
							}
						}

						

						attendance.setHoursHeld(hoursAttended);
						attendance.setAttendanceDate(fromDate);
						attendance.setHoursHeldMonthly(Integer
								.valueOf(hoursHeldValue));
						attendance.setIsCanceled(false);
						attendanList.add(attendance);

					}

				}

			}

			return attendanList;

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	@Override
	public void cancelMonthlyAttendance(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm)
			throws ApplicationException {

		List<Attendance> attendanceList = getAttendanceList(monthlyAttendanceEntryForm);
		Iterator<Attendance> attendanceIterator = attendanceList.iterator();
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			while (attendanceIterator.hasNext()) {
				Attendance attendance = (Attendance) attendanceIterator.next();
				attendance.setIsCanceled(true);
				session.saveOrUpdate(attendance);
			}

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			session.close();
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}

	}

}
