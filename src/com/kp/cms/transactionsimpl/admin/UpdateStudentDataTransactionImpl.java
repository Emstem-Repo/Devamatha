package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.UpdateStudentDataForm;
import com.kp.cms.to.admin.UpdateStudentDataTO;
import com.kp.cms.transactions.admin.IUpdateStudentDataTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UpdateStudentDataTransactionImpl implements IUpdateStudentDataTransaction {
	private static volatile UpdateStudentDataTransactionImpl updateStudentDataTransactionImpl = null;
	public static UpdateStudentDataTransactionImpl getInstance(){
		if(updateStudentDataTransactionImpl == null){
			updateStudentDataTransactionImpl = new UpdateStudentDataTransactionImpl();
			return updateStudentDataTransactionImpl;
		}
		return updateStudentDataTransactionImpl;
	}
	@Override
	public List<UpdateStudentDataTO> getStudentDetailsList(UpdateStudentDataForm updateStudentDataForm) throws Exception {
		Session session = null;
		List<UpdateStudentDataTO> list = new ArrayList<UpdateStudentDataTO>();
		try{
			session = HibernateUtil.getSession();
			String s = "from Student s where s.classSchemewise.classes.id= :classId " +
					"  order by s.registerNo";
			Query query = session.createQuery(s)
						.setString("classId", updateStudentDataForm.getClassId());
			list = query.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return list;
	}
	@Override
	public List<UpdateStudentDataTO> getPrevStudent(UpdateStudentDataForm updateStudentDataForm) throws Exception {
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			StringBuilder sb = new StringBuilder("select b.studentUtilBO from ExamStudentPreviousClassDetailsBO b where b.classId= :classId  order by b.studentUtilBO.registerNo "); 
			Query query = session.createQuery(sb.toString())
			             .setString("classId", updateStudentDataForm.getClassId());
			return query.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
	@Override
	public String getClassName(UpdateStudentDataForm updateStudentDataForm)
			throws Exception {
		Session session = null;
		String name = null;
		try{
			session = HibernateUtil.getSession();
			String s = " select c.name from Classes c where c.id= :classId";
			Query query = session.createQuery(s)
							.setString("classId", updateStudentDataForm.getClassId());
			name = (String)query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return name;
	}
	@Override
	public PersonalData getData(int studentId) throws Exception {
		Session session = null;
		PersonalData data = null;
		try{
			session = HibernateUtil.getSession();
			String s = " select pd from PersonalData pd join pd.admApplns ad join ad.students s where s.id = :studentId";
			Query query = session.createQuery(s)
							.setInteger("studentId", studentId);
			data = (PersonalData)query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return data;
	}
	@Override
	public boolean updateStudentData(List<PersonalData> bo) throws Exception {
		Session session = null;
		Transaction transaction = null;
		PersonalData bo1;
		try {
			session = HibernateUtil.getSession();
			session.close();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();			
			Iterator<PersonalData> tcIterator = bo.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo1 = tcIterator.next();				
				session.update(bo1);
				System.out.println("Object count:"+ count);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			if(transaction != null) {
				transaction.rollback();
			}
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			e.printStackTrace();
			if(transaction != null) {
				transaction.rollback();
			}
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
	}
	@Override
	public String getParentMob(int studentId) throws Exception {
		Session session = null;
		String mobileNo = null;
		try{
			session = HibernateUtil.getSession();
			String s = "select pd.parentMob2 from PersonalData pd " +
					"join pd.admApplns ad " +
					"join ad.students s  " +
					"where  s.id = :studentId";
			Query query = session.createQuery(s)
							.setInteger("studentId", studentId);
			mobileNo = query.uniqueResult().toString();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return mobileNo;
	}
}
