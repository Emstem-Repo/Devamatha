package com.kp.cms.transactionsimpl.admission;

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

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.ISmartCardNumberUploadTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class SmartCardNumberTransactionImpl implements ISmartCardNumberUploadTransaction{
	private static final Log log = LogFactory
	.getLog(SmartCardNumberTransactionImpl.class);
	private static volatile SmartCardNumberTransactionImpl smartCardNumberTransactionImpl=null;
	public static SmartCardNumberTransactionImpl getInstance(){
		if(smartCardNumberTransactionImpl ==null){
			smartCardNumberTransactionImpl = new SmartCardNumberTransactionImpl();
			return smartCardNumberTransactionImpl;
		}
		return smartCardNumberTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ISmartCardNumberUploadTransaction#getRegisterNumber()
	 */
	@Override
	public Map<String, Student> getRegisterNumber()throws Exception {
		Session session=null;
		List<Student> list=null;
		Map<String,Student> stuMap=new HashMap<String, Student>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Student stu where stu.isActive=1");
			list=query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
			session.close();
		}
		if(list!=null){
			Iterator<Student> iterator=list.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					stuMap.put(student.getRegisterNo(), student);
				}
			}
		}
		return stuMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ISmartCardNumberUploadTransaction#addSmartCardNumber(java.util.List, java.lang.String)
	 */
	@Override
	public boolean addSmartCardNumber(List<Student> results, String user)
			throws Exception {
		log.info("call of addSmartCardNumber method in  SmartCardNumberTransactionImpl class.");
		Session session=null;
		Transaction transaction = null;
		Student student = null;
		Student studentObj = null;
		boolean isAdded = false; 
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(results!=null && !results.isEmpty()){
				Iterator<Student> iterator=results.iterator();
				int count = 0; 
				while (iterator.hasNext()) {
					 student =  iterator.next();
					 studentObj =(Student) session.get(Student.class, student.getId());
					 studentObj.setBankAccNo(student.getBankAccNo());
					 studentObj.setSmartCardNo(student.getSmartCardNo());
					 studentObj.setModifiedBy(user);
					 studentObj.setLastModifiedDate(new Date());
					 studentObj.setIsSCDataDelivered(true);
					 session.update(studentObj);
					 if (++count % 20 == 0) {
							session.flush();
							session.clear();
						}
				}
			}
			transaction.commit();
			isAdded = true;
		}catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			isAdded = false;
			log.error("Error in addSmartCardNumber..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addSmartCardNumber method in  SmartCardNumberTransactionImpl class.");
		return isAdded;
	}
	@Override
	public Map<String,Student> getStudents(List<String> regNos) throws Exception {
		Session session=null;
		List<Student> list=null;
		Map<String,Student> map=new HashMap<String, Student>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Student stu where stu.isActive=1 and stu.registerNo in (:list)");
			query.setParameterList("list", regNos);
			list=query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Student> iterator=list.iterator();
				while (iterator.hasNext()) {
					Student student = (Student) iterator.next();
					map.put(student.getRegisterNo(), student);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		return map;
	}
	@Override
	public boolean update(List<Student> students) throws Exception{
		log.info("call of addSmartCardNumber method in  SmartCardNumberTransactionImpl class.");
		Session session=null;
		Transaction transaction = null;
		Student student = null;
		boolean isAdded = false; 
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
				Iterator<Student> iterator=students.iterator();
				int count = 0; 
				while (iterator.hasNext()) {
					 student =  iterator.next();
					 session.update(student);
					 if (++count % 20 == 0) {
							session.flush();
							session.clear();
						}
			}
			transaction.commit();
			isAdded = true;
		}catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			isAdded = false;
			log.error("Error in addSmartCardNumber..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addSmartCardNumber method in  SmartCardNumberTransactionImpl class.");
		return isAdded;
	}
	
}
