package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.CocurricularAttendnaceEntryForm;
import com.kp.cms.forms.attendance.ExtraCocurricularLeaveEntryForm;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.transactions.attandance.IExtraCocurricularLeaveEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExtraCocurricularLeaveEntryTransactionImpl  implements IExtraCocurricularLeaveEntryTransaction{
	private static volatile ExtraCocurricularLeaveEntryTransactionImpl extraCocurricularLeaveEntryTransactionImpl= null;
	private static final Log log = LogFactory.getLog(ExtraCocurricularLeaveEntryTransactionImpl.class);
	
	public static ExtraCocurricularLeaveEntryTransactionImpl getInstance()
	{
		if(extraCocurricularLeaveEntryTransactionImpl==null)
		{
			extraCocurricularLeaveEntryTransactionImpl = new ExtraCocurricularLeaveEntryTransactionImpl();
		}
		return extraCocurricularLeaveEntryTransactionImpl;
	}

	@Override
	public Student getStudentByStudentId(String studentId, String courseId)throws Exception {
		log.debug("call of getStudentByStudentId method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		Student student = new Student();
		Session session = null;
		try
		{
			session = HibernateUtil.getSession();
			student = (Student)session.get(Student.class, Integer.parseInt(studentId));// get student Object
		}
		catch (Exception e) {
			log.error("Error in getStudentByStudentId method in ExtraCocurricularLeaveEntryTransactionImpl.class");
			log.error("Error is" + e.getMessage());
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			if(session!=null)
			{
				// close 
			}
		}
		log.debug("end of getStudentByStudentId method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		return student;// return student
	}

	@Override
	public List<Period> getPeriodListByClass(int id) throws Exception {
		log.debug("call of getPeriodListByClass method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		List<Period> periodList= new ArrayList<Period>();
		Session session = null;
		
		try
		{
			 session = HibernateUtil.getSession();
			 String hql = "select p from Period p where p.isActive =1 and p.classSchemewise.id=:classid ";
			 Query query  = session.createQuery(hql);
			 query.setInteger("classid", id);
			 periodList = query.list();
			 
		}
		catch (Exception e) {
			// TODO: handle exception
			log.error("Error in getPeriodListByClass method in ExtraCocurricularLeaveEntryTransactionImpl.class");
			log.error("Error is " +e.getMessage());
			throw new ApplicationException(e);
		}
		
		log.debug("end of getPeriodListByClass method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		return periodList;
	}

	@Override
	public Map<Integer, String> getSubjectMap() throws Exception {
		log.debug("call of getSubjectMap method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		Session session = null;
		try
		{
			session =  HibernateUtil.getSession();
			String hql = "select s.id ,s.name from Subject s where s.isActive=1";
			Query query = session.createQuery(hql);
			List<Object[]> list = query.list();
			Iterator<Object[]> iterator = list.iterator();
			while(iterator.hasNext())
			{
				Object[] objects = iterator.next();
				subjectMap.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
			}
		}
		catch (Exception e) {
			log.error("Error in getSubjectMap method in ExtraCocurricularLeaveEntryTransactionImpl.class");
			log.error("Error is"+e.getMessage());
			throw new ApplicationException(e);
		}
		
		log.debug("end of getSubjectMap method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		return subjectMap;
	}

	@Override
	public List<Object[]> getDuplicateRecords(String attendanceDuplicateQuery)throws Exception {
		log.debug("call of getDuplicateRecords method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		List<Object[]> duplicateList= new ArrayList<Object[]>();
		Session   session =null;
		try
		{
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery(attendanceDuplicateQuery);
			duplicateList=query.list();
		}
		catch (Exception e) {
			// TODO: handle exception
			log.error("Error is"+e.getMessage());
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		
		log.debug("end of getDuplicateRecords method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		return duplicateList;
	}

	@Override
	public List<Object[]> getAttendnaceListByDates(String attendanceQuery)throws Exception {
		log.debug("call of getAttendnaceListByDates method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		Session  session = null;
		List<Object[]> attendaneList = new ArrayList<Object[]>();
		try
		{
			session =  HibernateUtil.getSession();
			Query query= session.createSQLQuery(attendanceQuery);
			attendaneList = query.list();
			
		}
		catch (Exception e) {
			log.error("Error in getAttendnaceListByDates method in ExtraCocurricularLeaveEntryTransactionImpl.class");
			log.error("Error is.."+e.getMessage());
			throw new ApplicationException(e);
		}
		log.debug("end of getAttendnaceListByDates method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		return attendaneList;
	}

	@Override
	public boolean saveCocurricularLeaveDetails(List<StuCocurrLeave> 
	cocurricularList,List<ApproveLeaveTO> approveLeaveTOs,
	ExtraCocurricularLeaveEntryForm cocurricularLeaveEntryForm)
			throws Exception {
		log.debug("call of saveCocurricularLeaveDetails method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		boolean isAdded = false;
		Session session = null;
		int count=0;
		Transaction  transaction = null;
		try
		{
			session  =InitSessionFactory.getInstance().openSession();
			Iterator<StuCocurrLeave> iterator = cocurricularList.iterator();
			Iterator<ApproveLeaveTO> iteratorApp= approveLeaveTOs.iterator();
			while(iterator.hasNext())
			{
				StuCocurrLeave  cocurrLeave = iterator.next();
				transaction = session.beginTransaction();
				transaction.begin();
				if(cocurrLeave.getId()==0)
				{
					session.save(cocurrLeave);
				}
				else
				{
					session.saveOrUpdate(cocurrLeave);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
				
				
			}
			// after approve by teacher and authorizer , then only student table will  get updated
			/*if(approveLeaveTOs!=null)
			{
				while(iteratorApp.hasNext())
				{
					ApproveLeaveTO approveLeaveTO = iteratorApp.next();
					AttendanceStudent attendence = (AttendanceStudent) session
					.get(AttendanceStudent.class, Integer.parseInt(approveLeaveTO.getAttStudentId()));
					attendence.setIsCoCurricularLeave(approveLeaveTO.getIsCocurricularAttendance());
					attendence.setIsPresent(false);
					Attendance main = new Attendance();
					main.setId(Integer.parseInt(approveLeaveTO.getAttMainId()));
					attendence.setAttendance(main);
					attendence.setModifiedBy(e.getUserId());
					attendence.setLastModifiedDate(new Date());
					session.update(attendence);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
					
				}
			}
			*/
			
			transaction.commit();
			isAdded = true;
		}
		catch (Exception e) {
			// TODO: handle exception
			log.error("Error is"+e.getMessage());
			transaction.rollback();
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		log.debug("end of saveCocurricularLeaveDetails method in ExtraCocurricularLeaveEntryTransactionImpl.class");
		return isAdded;
	}
}
