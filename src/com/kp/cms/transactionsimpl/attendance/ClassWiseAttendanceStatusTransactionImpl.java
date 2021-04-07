package com.kp.cms.transactionsimpl.attendance;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceEntryDay;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.exam.HolidayBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ClassWiseAttendanceStatusForm;
import com.kp.cms.transactions.attandance.IClassWiseAttendanceStatusTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ClassWiseAttendanceStatusTransactionImpl implements
		IClassWiseAttendanceStatusTransaction {
	public static volatile ClassWiseAttendanceStatusTransactionImpl classWiseAttendanceStatusTransactionImpl = null;
	public static final Log log = LogFactory
			.getLog(ClassWiseAttendanceStatusTransactionImpl.class);

	public static ClassWiseAttendanceStatusTransactionImpl getInstance() {
		if (classWiseAttendanceStatusTransactionImpl == null) {
			classWiseAttendanceStatusTransactionImpl = new ClassWiseAttendanceStatusTransactionImpl();
		}
		return classWiseAttendanceStatusTransactionImpl;
	}

	@Override
	public CurriculumSchemeDuration checkingDatesInCoCurriculumSchemeDuration(
			ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm) throws Exception{
		CurriculumSchemeDuration curriculumSchemeDuration = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			curriculumSchemeDuration = (CurriculumSchemeDuration) session
					.createQuery(
							"select c.curriculumSchemeDuration from ClassSchemewise c where c.classes.id= "
									+ Integer
											.valueOf(classWiseAttendanceStatusForm
													.getClasses())
									+ " and c.classes.termNumber="
									+ Integer
											.valueOf(classWiseAttendanceStatusForm
													.getSemister())
									+ " and c.classes.isActive=1")
					.uniqueResult();

		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return curriculumSchemeDuration;

	}


	@Override
	public String getClassName(ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm)throws Exception {

		log.error("Start getClassNames of ClassWiseAttendanceStatusTransactionImpl");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query classNameQuery=session.createQuery("select classes.name from Classes classes where classes.id ="+Integer.valueOf(classWiseAttendanceStatusForm.getClasses()));
			String className =(String) classNameQuery.uniqueResult();
			log.error("End getPeriods of AttendanceSlipDetailsTransactionImpl");
			return className;
		} catch (Exception exception) {
			log.error("Error while retrieving classNames.." +exception);
			throw  new ApplicationException(exception);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	
	}

	@Override
	public List<Attendance> getAttendanceListDetails(String searchCriteria,List<Integer> periodList)throws Exception {

		log.error("Start of getAttendanceListDetails of ClassWiseAttendanceStatusTransactionImpl");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery(searchCriteria).setParameterList("periodsList", periodList);
			List<Attendance> slipDetailsList = query.list();
			log.error("End of getAttendanceListDetails of ClassWiseAttendanceStatusTransactionImpl");
			return slipDetailsList;
		} catch (Exception exception) {
			log.error("Error while retrieving attendance slip details.." +exception);
			throw  new ApplicationException(exception);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	
	}

	@Override
	public List<Integer> getPeriodsIdsUsingClassId(ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm) throws Exception{
		log.debug("impl: inside getPeriodsUsingClassId");
		Session session = null;
		List<Integer> period;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select p.id from Period p where p.classSchemewise.classes.id="+Integer.valueOf(classWiseAttendanceStatusForm.getClasses()) +" and p.classSchemewise.classes.termNumber="+Integer.valueOf(classWiseAttendanceStatusForm.getSemister()) +" and p.isActive=1 and p.classSchemewise.classes.isActive=1 order by p.periodName");
			period = query.list();
			session.flush();
			log.debug("impl: leaving getPeriodsUsingClassId");
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			throw new ApplicationException(e);
		}
		return period;
	
	}

	@Override
	public List<String> getPeriodNamesUsingClassId(ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm)throws Exception {
		log.debug("impl: inside getPeriodsUsingClassId");
		Session session = null;
		List<String> period;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select p.periodName from Period p where p.classSchemewise.classes.id="+Integer.valueOf(classWiseAttendanceStatusForm.getClasses()) +" and p.classSchemewise.classes.termNumber="+Integer.valueOf(classWiseAttendanceStatusForm.getSemister()) +" and p.isActive=1 and p.classSchemewise.classes.isActive=1 order by p.periodName");
			period = query.list();
			session.flush();
			log.debug("impl: leaving getPeriodsUsingClassId");
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			throw new ApplicationException(e);
		}
		return period;
		
	}

	@Override
	public List totalPeriodDetailsDayWiseList(String totalPeriodDetailsDayWise) throws Exception {
		log.debug("impl: inside totalPeriodDetailsDayWiseList");
		Session session = null;
		List totalPeriodDetailsDayWiseList=null;
		try{
			session = HibernateUtil.getSession();
			Query query=session.createSQLQuery(totalPeriodDetailsDayWise);
			totalPeriodDetailsDayWiseList=query.list();
		}catch (Exception e) {
			log.error("Error while retrieving totalPeriodDetailsDayWiseList.." +e);
			throw  new ApplicationException(e);
		}finally{
			if (session != null) {
				session.flush();
			}	
		}
		return totalPeriodDetailsDayWiseList;
	}

	@Override
	public String checkingInAttendanceDayEntry(String attendanceDate) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String weekDay =(String) session.createQuery("select a.day from AttendanceEntryDay a where a.date='"+CommonUtil
					.ConvertStringToSQLDate(attendanceDate)+"'"+" and a.isActive=1").uniqueResult();
			session.flush();
			return weekDay;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}


}
