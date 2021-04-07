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
import org.zefer.html.doc.l;
import org.zefer.html.doc.q;
import org.zefer.html.doc.t;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.CocurricularAttendnaceEntryForm;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.transactions.attandance.ICocurricularAttendnaceEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CocurricularAttendnaceEntryTransactionImpl  implements ICocurricularAttendnaceEntryTransaction{
    private static volatile CocurricularAttendnaceEntryTransactionImpl cocurricularAttendnaceEntryTransactionImpl= null;
    private static final Log log = LogFactory.getLog(CocurricularAttendnaceEntryTransactionImpl.class);
    public static  CocurricularAttendnaceEntryTransactionImpl getInstance()
    {
    	if(cocurricularAttendnaceEntryTransactionImpl==null)
    	{
    		CocurricularAttendnaceEntryTransactionImpl cocurricularAttendnaceEntryTransactionImpl = new CocurricularAttendnaceEntryTransactionImpl();
    		return cocurricularAttendnaceEntryTransactionImpl;
    	}
    	return cocurricularAttendnaceEntryTransactionImpl;
    }
	@Override
	public List<Student> getStudentList(String studentQuery) throws Exception {
		log.debug("call of getStudentList method in CocurricularAttendnaceEntryTransactionImpl.class");
		List<Student> studentList = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(studentQuery);
			studentList = query.list();
		}
		catch (Exception e) {
			log.error("Error in getStudentList method in CocurricularAttendnaceEntryTransactionImpl.class");
			log.error("Error is.."+e.getMessage());
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		log.debug("end of getStudentList method in CocurricularAttendnaceEntryTransactionImpl.class");
		return studentList;
	}
	@Override
	public Student getStudentByRegNo(String studentByRegnoQuery)throws Exception {
		log.debug("call of getStudentByRegNo method in CocurricularAttendnaceEntryTransactionImpl.class");
		Session session = null;
		Student student = null;
		try
		{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(studentByRegnoQuery);
			student = (Student)query.uniqueResult();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			log.error("Error in getStudentByRegNo method in CocurricularAttendnaceEntryTransactionImpl.class");
			log.error("Error is "+ e.getMessage());
			throw new ApplicationException(e);
		}
		
		log.debug("call of getStudentByRegNo method in CocurricularAttendnaceEntryTransactionImpl.class");
		return student;
	}
	@Override
	public List<Object[]> getAttendnaceListByDates(String attendanceQuery)throws Exception {
		log.debug("call of getAttendnaceListByDates method in CocurricularAttendnaceEntryTransactionImpl.class");
		Session  session = null;
		List<Object[]> attendaneList = new ArrayList<Object[]>();
		try
		{
			session =  HibernateUtil.getSession();
			Query query= session.createSQLQuery(attendanceQuery);
			attendaneList = query.list();
			
		}
		catch (Exception e) {
			log.error("Error in getAttendnaceListByDates method in CocurricularAttendnaceEntryTransactionImpl.class");
			log.error("Error is.."+e.getMessage());
			throw new ApplicationException(e);
		}
		log.debug("end of getAttendnaceListByDates method in CocurricularAttendnaceEntryTransactionImpl.class");
		return attendaneList;
	}
	@Override
	public List<Period> getPeriodListByClass(String classes) throws Exception {
		log.debug("call of getPeriodListByClass method in CocurricularAttendnaceEntryTransactionImpl.class");
		List<Period> periodList= new ArrayList<Period>();
		Session session = null;
		
		try
		{
			 session = HibernateUtil.getSession();
			 String hql = "select p from Period p where p.isActive =1 and p.classSchemewise.id=:classid ";
			 Query query  = session.createQuery(hql);
			 query.setString("classid", classes);
			 periodList = query.list();
			 
		}
		catch (Exception e) {
			// TODO: handle exception
			log.error("Error in getPeriodListByClass method in CocurricularAttendnaceEntryTransactionImpl.class");
			log.error("Error is " +e.getMessage());
			throw new ApplicationException(e);
		}
		
		
		log.debug("end of getPeriodListByClass method in CocurricularAttendnaceEntryTransactionImpl.class");
		return periodList;
	}
	@Override
	public Map<Integer, String> getCocurricularActivity() throws Exception {
		log.debug("call of getCocurricularActivity method in CocurricularAttendnaceEntryTransactionImpl.class");
		Map<Integer, String> cocurricularActivityMap= new HashMap<Integer, String>();
		Session session =  null;
		try
		{
			session =  HibernateUtil.getSession();
			String hql = "select a.id,a.name from Activity a where a.isActive=1";
			Query query = session.createQuery(hql);
			List<Object[]> objList= query.list();
			Iterator<Object[]> iterator = objList.iterator();
			while(iterator.hasNext())
			{
				Object[] object = iterator.next();
				cocurricularActivityMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			log.error("Error in getCocurricularActivity method in CocurricularAttendnaceEntryTransactionImpl.class");
			log.error("Error is .."+e.getMessage());
			throw new ApplicationException(e);
		}
		log.debug("end of getCocurricularActivity method in CocurricularAttendnaceEntryTransactionImpl.class");
		
		return cocurricularActivityMap;
	}
	@Override
	public boolean saveCocurricularLeaveDetails(List<StuCocurrLeave> 
	cocurricularList,List<ApproveLeaveTO> approveLeaveTOs,
	CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm) throws Exception {
		log.debug("call of saveCocurricularLeaveDetails method in CocurricularAttendnaceEntryTransactionImpl.class");
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
			// new modification Operation edit
			if(cocurricularAttendnaceEntryForm.getOprationMode()!=null && cocurricularAttendnaceEntryForm.getOprationMode().equalsIgnoreCase("approve"))
			{
			
			if(approveLeaveTOs!=null)
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
					attendence.setModifiedBy(cocurricularAttendnaceEntryForm.getUserId());
					attendence.setLastModifiedDate(new Date());
					session.update(attendence);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
					
				}
			}
			
		}
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
		log.debug("end of saveCocurricularLeaveDetails method in CocurricularAttendnaceEntryTransactionImpl.class");
		return isAdded;
	}
	@Override
	public List<Object[]> getDuplicateRecords(String attendanceDuplicateQuery)throws Exception {
		log.debug("call of getDuplicateRecords method in CocurricularAttendnaceEntryTransactionImpl.class");
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
		
		log.debug("end of getDuplicateRecords method in CocurricularAttendnaceEntryTransactionImpl.class");
		return duplicateList;
	}
	@Override
	public Map<Integer, String> getSubjectMap() throws Exception {
		log.debug("call of getSubjectMap method in CocurricularAttendnaceEntryTransactionImpl.class");
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
			log.error("Error in getSubjectMap method in CocurricularAttendnaceEntryTransactionImpl.class");
			log.error("Error is"+e.getMessage());
			throw new ApplicationException(e);
		}
		
		
		log.debug("end of getSubjectMap method in CocurricularAttendnaceEntryTransactionImpl.class");
		return subjectMap;
	}
	
	
}
