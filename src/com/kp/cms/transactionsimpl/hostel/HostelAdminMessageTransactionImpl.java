package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlComplaint;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.hostel.IHostelAdminMessageTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelAdminMessageTransactionImpl implements IHostelAdminMessageTransactions {

	private static final Log log=LogFactory.getLog(HostelAdminMessageTransactionImpl.class);
	private static volatile HostelAdminMessageTransactionImpl hostelAdminMessageTxImpl=null;
	public static HostelAdminMessageTransactionImpl getInstance()
	{
		if(hostelAdminMessageTxImpl==null)
		{
			hostelAdminMessageTxImpl=new HostelAdminMessageTransactionImpl();
			return hostelAdminMessageTxImpl;
		}
		return hostelAdminMessageTxImpl;
	}
	@SuppressWarnings("unchecked")
	public List<HlStatus> getHostelStatusList()throws Exception
	{
		log.info("entering into getHostelStatusList in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		 List<HlStatus> list=null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 String queryString="from HlStatus h  where h.isActive=1 order by h.statusType";
			 Query query = session.createQuery(queryString);
			 list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getHostelStatusList in HostelAdminMessageTransactionImpl class..");
			 if(list!=null && !list.isEmpty())
			 {
			 return list;
			 }
		 } catch (Exception e) {
			 log.error("Error during getHostelStatusList data..." , e);
			 throw e;
		 }
		 return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]>  getHostelAdminMessageList(String str)throws Exception
	{
		log.info("entering into getHostelAdminMessageList in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		List<Object[]> messageList=null;
		try {
		//session = InitSessionFactory.getInstance().openSession();
			 session = HibernateUtil.getSession();
			
				Query query=session.createQuery(str);
				messageList=query.list();
				
		} catch (Exception e) {		
			 log.error("Error during getHostelAdminMessageList data..." , e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		 log.info("leaving from getHostelAdminMessageList in HostelAdminMessageTransactionImpl class..");
		return messageList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]>  getLeaveStatusListByadmnApplId(String commonId,String messageTypeId) throws Exception
	{
		log.info("entering into getLeaveStatusListByadmnApplId in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		//HlLeave hlLeave=null;
		List<Object[]> messageList=new ArrayList<Object[]>();
		String str="";
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			if(messageTypeId.equalsIgnoreCase("2"))
			{
				 str=str+"select hl.id, hl.hlApplicationForm.admAppln.id, hl.employee.id, " +
							"hl.startDate, hl.endDate, hl.reasons, hl.hlLeaveType.name, hl.hlStatus.id, hl.hlStatus.statusType, "+
							"hl.approvedBy, hl.approvedDate, hl.approverRemarks from HlLeave hl "+
							"where hl.id=:CommonId";
			}
			else
			{
				 str=str+"select hlc.id, hlc.hlApplicationForm.admAppln.id, hlc.employee.id, " +
				"hlc.hlComplaintType.type, hlc.subject, hlc.description, "+
				"hlc.actionTaken,hlc.hlStatus.id,hlc.approvedBy, hlc.approvedDate,hlc.hlStatus.statusType  from HlComplaint hlc "+
				"where  hlc.id=:CommonId";
			}
				Query query=session.createQuery(str);
				query.setString("CommonId", commonId);
				messageList=query.list();
				//hlLeave=(HlLeave)messageList.get(0);
		} catch (Exception e) {		
			 log.error("Error during getHostelAdminMessageList data..." , e);
			
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		 log.info("leaving from getLeaveStatusListByadmnApplId in HostelAdminMessageTransactionImpl class..");
		return messageList;
	}
	
	
	public boolean manageLeaveStatus(Object obj) throws Exception
	{
		log.info("Start of manageLeaveStatus");
		Session session = null;
		Transaction transaction = null;
		HlLeave hlLeave=null;
		HlComplaint hlComplaint=null;
		if(obj instanceof HlLeave)
		{
			 hlLeave=(HlLeave)obj;
		}
		else
		{
			hlComplaint=(HlComplaint)obj;
		}
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(hlLeave!=null)
			{
			session.update(hlLeave);
			}
			else if(hlComplaint!=null)
			{
				session.update(hlComplaint);
			}
			transaction.commit();
			session.flush();
			session.close();
			log.info("End of manageLeaveStatus");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving manage LeaveStatus data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving manage LeaveStatus data..." , e);
			throw new ApplicationException(e);
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getEmployeeDetails() throws Exception
	{
		log.info("entering into getEmployeeDetails in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		 List<Object[]> list=null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 String queryString="select emp.id, emp.firstName, emp.middleName, emp.lastName from Employee emp";
			 Query query = session.createQuery(queryString);
			 list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getEmployeeDetails in HostelAdminMessageTransactionImpl class..");
			 if(list!=null && !list.isEmpty())
			 {
			 return list;
			 }
		 } catch (Exception e) {
			 log.error("Error during getEmployeeDetails data..." , e);
			 throw e;
		 }
		 return list;
	}
	
	@SuppressWarnings("unchecked")
	public Users getUserNameByUserId(String id) throws Exception
	{
		log.info("entering into getUserNameByUserId in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		Users users=null;
		try {
			String str="from Users u where u.id=:ID and u.isActive=1";
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
				Query query=session.createQuery(str);
				query.setString("ID",id);
				List userList=query.list();
				 users=(Users)userList.get(0);
			
		} catch (Exception e) {		
			 log.error("Error during getUserNameByUserId data..." , e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		 log.info("leaving from getUserNameByUserId in HostelAdminMessageTransactionImpl class..");
		return users;
		
	}
	
	
	public List<Object[]> getStudentId() throws Exception
	{
		log.info("entering into getUserNameByUserId in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		Student std=null;
		List<Object[]> userList=null;
		try {
			//admAppln
			String str="select s.registerNo, s.admAppln.id from Student s";
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
				Query query=session.createQuery(str);
				//query.setString("ID",id);
				userList=query.list();
				//std=(Student)userList.get(0);
			
		} catch (Exception e) {		
			 log.error("Error during getUserNameByUserId data..." , e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		 log.info("leaving from getUserNameByUserId in HostelAdminMessageTransactionImpl class..");
		return userList;
		
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public HlLeave getHlLeaveByLeaveId(Integer leaveId)throws Exception
	{
		log.info("entering into getHlLeaveByLeaveId in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		HlLeave leaves=null;
		try {
			String str="from HlLeave hl where hl.id=:ID and hl.isActive=1";
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
				Query query=session.createQuery(str);
				query.setInteger("ID",leaveId);
				List leaveList=query.list();
				leaves=(HlLeave)leaveList.get(0);
			//essageList = session.createQuery(queryString).list();
		} catch (Exception e) {		
			 log.error("Error during getHlLeaveByLeaveId data..." , e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		 log.info("leaving from getHlLeaveByLeaveId in HostelAdminMessageTransactionImpl class..");
		return leaves;
	}
	
	
	public HlComplaint getHlComplaintByComplaintId(Integer complaintId)throws Exception
	{
		log.info("entering into getHlLeaveByLeaveId in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		HlComplaint complaint=null;
		try {
			String str="from HlComplaint hlc where hlc.id=:ID and hlc.isActive=1";
			//session = InitSessionFactory.getInstance().openSession();
				session=HibernateUtil.getSession();
				Query query=session.createQuery(str);
				query.setInteger("ID",complaintId);
				List<HlComplaint> complaintList=query.list();
				complaint=(HlComplaint)complaintList.get(0);
			//essageList = session.createQuery(queryString).list();
		} catch (Exception e) {		
			 log.error("Error during getHlLeaveByLeaveId data..." , e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		 log.info("leaving from getHlLeaveByLeaveId in HostelAdminMessageTransactionImpl class..");
		return complaint;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public HlApplicationForm  getHlApplicationFormByTypeId(String typeId) throws Exception
	{
		log.info("entering into getLeaveStatusListByStudentId in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		Query query=null;
		
		String str=null;
		HlApplicationForm hlApplicationForm=new HlApplicationForm();
		List<HlApplicationForm> hlApplicationFormList=null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
				str="From HlApplicationForm hlForm  where hlForm.admAppln.id=:TypeId";	 
			query=session.createQuery(str);
			query.setString("TypeId", typeId);
				hlApplicationFormList=query.list();
				if(hlApplicationFormList!=null && !hlApplicationFormList.isEmpty())
				{
					hlApplicationForm=hlApplicationFormList.get(0);
				}
				
		} catch (Exception e) {		
			 log.error("Error during getHostelAdminMessageList data..." , e);
			
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		 log.info("leaving from getLeaveStatusListByStudentId in HostelAdminMessageTransactionImpl class..");
		return hlApplicationForm;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]>  getStudentViewMessageList(String queryString) throws Exception
	{
		log.info("entering into getStudentViewMessageList in HostelAdminMessageTransactionImpl class..");
		Session session = null;
		//String str=null;
		List<Object[]> leaveList=new ArrayList<Object[]>();
	
		try {
				//session = InitSessionFactory.getInstance().openSession();
			    session = HibernateUtil.getSession();
				Query query=session.createQuery(queryString);
				leaveList=query.list();
		} catch (Exception e) {		
			 log.error("Error during getStudentViewMessageList data..." , e);
			
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		 log.info("leaving from getStudentViewMessageList in HostelAdminMessageTransactionImpl class..");
		return leaveList;
	}
	
	
	
	
}
