package com.kp.cms.transactionsimpl.attendance;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zefer.cache.b;
import org.zefer.html.doc.q;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.CoCurricularTeacherSubjectsBO;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.StuCocurrLeaveDetails;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.attandance.ICocurricularLeaveApproveTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CocurricularLeaveApproveTransactionImpl  implements ICocurricularLeaveApproveTransaction{

	private static Log log = LogFactory.getLog(CocurricularLeaveApproveTransactionImpl.class);
	private static volatile CocurricularLeaveApproveTransactionImpl cocurricularLeaveApproveTransactionImpl  =  null;
	public static CocurricularLeaveApproveTransactionImpl getInstance()
	{
		if(cocurricularLeaveApproveTransactionImpl== null)
		{
			cocurricularLeaveApproveTransactionImpl = new CocurricularLeaveApproveTransactionImpl();
		}
		return cocurricularLeaveApproveTransactionImpl;
	}
	
	@Override
	public List<StuCocurrLeave> getCocurricularApplications(String activityIds,Date fromDate, Date toDate) throws Exception {
		log.debug("call of getCocurricularApplications method in CocurricularLeaveApproveTransactionImpl.class");
		List<StuCocurrLeave>  cocurrLeavesList = new ArrayList<StuCocurrLeave>();
		Session session = null;
		try
		{
			session = HibernateUtil.getSession();
			String hqlQuery = " select c from StuCocurrLeave c where c.approvedByTeacher=0 and c.cancelledByTeacher=0 and c.isCocurrLeaveCancelled=0  and "+
			                  " c.startDate between '"+fromDate+"' and '"+toDate+"' and c.activity.id in ("+activityIds+")" ;
			Query query = session.createQuery(hqlQuery);
			cocurrLeavesList = query.list();
		}
		catch (Exception e) {
			log.error("Error in getCocurricularApplications method in CocurricularLeaveApproveTransactionImpl.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			
		}
		log.debug("end of getCocurricularApplications method in CocurricularLeaveApproveTransactionImpl.class");
		return cocurrLeavesList;
	}
	
	@Override
	public boolean approveCocurricularApplicationsByTeacher(List<StuCocurrLeave> cocurrLeavesBoList) throws Exception {
		log.debug("call of approveCocurricularApplicationsByTeacher method in CocurricularLeaveApproveTransactionImpl.class");
		boolean isAdded = false;
		Session session = null;
		int count=0;
		Transaction transaction = null;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Iterator<StuCocurrLeave> iterator = cocurrLeavesBoList.iterator();
			while(iterator.hasNext())
			{
				StuCocurrLeave leave = iterator.next();
				StuCocurrLeave bo = (StuCocurrLeave) session.get(StuCocurrLeave.class, leave.getId());
				bo.setModifiedBy(leave.getModifiedBy());
				bo.setLastModifiedDate(leave.getLastModifiedDate());
				bo.setApprovedByTeacher(leave.getApprovedByTeacher());
				bo.setApproverTeacher(leave.getApproverTeacher());
				bo.setCancelledByTeacher(leave.getCancelledByTeacher());
				session.update(bo);
				Set<StuCocurrLeaveDetails> detailset = bo.getStuCocurrLeaveDetailses();
				StuCocurrLeaveDetails details = detailset.iterator().next();
				String hqlQuery="select s from AttendanceStudent s  join s.attendance.attendancePeriods p " +
						" where s.attendance.attendanceDate='"+bo.getStartDate()+"'"+
						" and s.attendance.subject.id = "+bo.getSubject().getId()+
						" and s.student.id="+details.getStudent().getId()+
						" and p.period.id="+bo.getStartPeriod().getId()+
						//newly added
						" and s.attendance.isCanceled=0";
				Query query1 = session.createQuery(hqlQuery);
				//newly added
				query1.setMaxResults(1);
				
				AttendanceStudent student = (AttendanceStudent) query1.uniqueResult();
				student.setIsPresent(false);
				Attendance main = new Attendance();
				main.setId(student.getAttendance().getId());
				student.setAttendance(main);
				student.setIsCoCurricularLeave(Boolean.TRUE);
				student.setLastModifiedDate(new java.util.Date());
				session.update(student);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			isAdded = true;
		}
		catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
				session.close();
			}
		}
		
		log.debug("end of approveCocurricularApplicationsByTeacher method in CocurricularLeaveApproveTransactionImpl.class");
		return isAdded;
	}
	
	
	public boolean saveCocurricularAttendaceAndUpdateAttendace(List<StuCocurrLeave> cocurrLeavesBoList) throws Exception {
		log.debug("call of getCocurricularApplicationsForAuthorize method in CocurricularLeaveApproveTransactionImpl.class");
		boolean isSaved = false;
		Session session = null;
		int count=0;
		Transaction transaction = null;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Iterator<StuCocurrLeave> iterator = cocurrLeavesBoList.iterator();
			while(iterator.hasNext())
			{
				StuCocurrLeave leave = iterator.next();
				StuCocurrLeave bo = (StuCocurrLeave) session.get(StuCocurrLeave.class, leave.getId());
				bo.setModifiedBy(leave.getModifiedBy());
				bo.setLastModifiedDate(leave.getLastModifiedDate());
				Set<StuCocurrLeaveDetails> detailset = bo.getStuCocurrLeaveDetailses();
				
				session.update(bo);
				
				StuCocurrLeaveDetails details = detailset.iterator().next();
				String hqlQuery="select s from AttendanceStudent s  join s.attendance.attendancePeriods p " +
						" where s.attendance.attendanceDate='"+bo.getStartDate()+"'"+
						" and s.attendance.subject.id = "+bo.getSubject().getId()+
						" and s.student.id="+details.getStudent().getId()+
						" and p.period.id="+bo.getStartPeriod().getId()+
						//newly added
				" and s.attendance.isCanceled=0";
				Query query1 = session.createQuery(hqlQuery);
				
				AttendanceStudent student = (AttendanceStudent) query1.uniqueResult();
				
				
				student.setIsPresent(false);
				Attendance main = new Attendance();
				main.setId(student.getAttendance().getId());
				student.setAttendance(main);
				student.setLastModifiedDate(new java.util.Date());
				session.update(student);
				
				
				
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			isSaved = true;
			
		}
		catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
				session.close();
			}
		}
		
		log.debug("end of getCocurricularApplicationsForAuthorize method in CocurricularLeaveApproveTransactionImpl.class");
		return isSaved;
	}
	@Override
	public boolean cancelCocurricularApplicationsByTeacher(List<StuCocurrLeave> cocurrLeavesBoList) throws Exception {
		log.debug("call of cancelCocurricularApplicationsByTeacher method in CocurricularLeaveApproveTransactionImpl.class");
		boolean isCanceled = false;
		Session session = null;
		Transaction transaction = null;
		int count=0;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Iterator<StuCocurrLeave> iterator = cocurrLeavesBoList.iterator();
			while(iterator.hasNext())
			{
				StuCocurrLeave leave = iterator.next();
				StuCocurrLeave bo = (StuCocurrLeave) session.get(StuCocurrLeave.class, leave.getId());
				bo.setModifiedBy(leave.getModifiedBy());
				bo.setLastModifiedDate(leave.getLastModifiedDate());
				bo.setApprovedByTeacher(leave.getApprovedByTeacher());
				bo.setApproverTeacher(leave.getApproverTeacher());
				bo.setCancelledByTeacher(leave.getCancelledByTeacher());
				session.update(bo);
				Set<StuCocurrLeaveDetails> detailset = bo.getStuCocurrLeaveDetailses();
				StuCocurrLeaveDetails details = detailset.iterator().next();
				String hqlQuery="select s from AttendanceStudent s  join s.attendance.attendancePeriods p " +
						" where s.attendance.attendanceDate='"+bo.getStartDate()+"'"+
						" and s.attendance.subject.id = "+bo.getSubject().getId()+
						" and s.student.id="+details.getStudent().getId()+
						" and p.period.id="+bo.getStartPeriod().getId()+
						//newly added
				 " and s.attendance.isCanceled=0";
				Query query1 = session.createQuery(hqlQuery);
				//newly added
				query1.setMaxResults(1);
				AttendanceStudent student = (AttendanceStudent) query1.uniqueResult();
				student.setIsPresent(false);
				Attendance main = new Attendance();
				main.setId(student.getAttendance().getId());
				student.setAttendance(main);
				student.setIsCoCurricularLeave(Boolean.FALSE);
				student.setLastModifiedDate(new java.util.Date());
				session.update(student);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			isCanceled = true;
			
		}
		catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
				session.close();
			}
		}
		
		log.debug("end of cancelCocurricularApplicationsByTeacher method in CocurricularLeaveApproveTransactionImpl.class");
		return isCanceled;
	}

	@Override
	public Map<Integer, String> getAssignedActivityMap(String userId)throws Exception {
		log.debug("call of getAssignedActivityMap method in CocurricularLeaveApproveTransactionImpl.class");
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try
		{
			session = HibernateUtil.getSession();
			String hqlQuery = "select c.activity.id,c.activity.name from CoCurricularTeacherSubjectsBO c where c.coCurricularTeacherBO.users.id="+userId+" order by c.activity.name ";
			Query query = session.createQuery(hqlQuery);
			list= query.list();
			if(list!= null && list.size()>0)
			{
				Iterator<Object[]> iterator = list.iterator();
				while(iterator.hasNext())
				{
					Object[] object = iterator.next();
					activityMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
				}
			}
			
		}
		catch (Exception e) {
			log.error("Error in getAssignedActivityMap method in CocurricularLeaveApproveTransactionImpl.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
			
		}
		log.debug("end of getAssignedActivityMap method in CocurricularLeaveApproveTransactionImpl.class");
		
		return activityMap;
	}

	@Override
	public List<StuCocurrLeave> searchCocurricularApplications(String activityIdForQuery, Date fromDate, Date toDate,String searchType, String userId) throws Exception 
	{
		log.debug("call of searchCocurricularApplications method in CocurricularLeaveApproveTransactionImpl.class");
		List<StuCocurrLeave>  cocurrLeavesList = new ArrayList<StuCocurrLeave>();
		Session session = null;
		try
		{
			session = HibernateUtil.getSession();
			String hqlQuery ="";
			if(searchType.equalsIgnoreCase("View Application"))
			{
				 hqlQuery = " select c from StuCocurrLeave c where c.approvedByTeacher=0 and c.cancelledByTeacher=0 and c.isCocurrLeaveCancelled=0  and "+
                " c.startDate between '"+fromDate+"' and '"+toDate+"' and c.activity.id in ("+activityIdForQuery+")";
				
			}
			else if(searchType.equalsIgnoreCase("View Approved"))
			{
				 hqlQuery = " select c from StuCocurrLeave c where c.approvedByTeacher=1 and   "+
                " c.startDate between '"+fromDate+"' and '"+toDate+"' and  c.activity.id in ("+activityIdForQuery+") and c.approverTeacher="+userId ;
			}
			else if(searchType.equalsIgnoreCase("View Rejected"))
			{
				 hqlQuery = " select c from StuCocurrLeave c where c.cancelledByTeacher=1 and c.approvedByTeacher=0 and c.isCocurrLeaveCancelled=0  and "+
                " c.startDate between '"+fromDate+"' and '"+toDate+"' and c.activity.id in ("+activityIdForQuery+") and c.approverTeacher="+userId ;
			}
			else
			{
				 hqlQuery = " select c from StuCocurrLeave c where c.approvedByTeacher=0 and c.isCocurrLeaveCancelled=0  and "+
                " c.startDate between '"+fromDate+"' and '"+toDate+"' and c.activity.id in ("+activityIdForQuery+") " ;
			}
			
			Query query = session.createQuery(hqlQuery);
			cocurrLeavesList = query.list();
		}
		catch (Exception e) {
			log.error("Error in searchCocurricularApplications method in CocurricularLeaveApproveTransactionImpl.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			
		}
		log.debug("end of searchCocurricularApplications method in CocurricularLeaveApproveTransactionImpl.class");
		return cocurrLeavesList;
	}
	
	
	   
	
}
