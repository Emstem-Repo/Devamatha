package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.examallotment.ExaminationSessionsForm;
import com.kp.cms.transactions.examallotment.IExaminationSessionsTrans;
import com.kp.cms.utilities.HibernateUtil;

public class ExaminationSessionsTransImpl implements IExaminationSessionsTrans{
	public static volatile ExaminationSessionsTransImpl examinationSessionsTransImpl=null;
	//private constructor
	private ExaminationSessionsTransImpl(){
		
	}
	//singleton object
	public static ExaminationSessionsTransImpl getInstance(){
		if(examinationSessionsTransImpl==null){
			examinationSessionsTransImpl=new ExaminationSessionsTransImpl();
			return examinationSessionsTransImpl;
		}
		return examinationSessionsTransImpl;
	}
	public boolean checkDuplicate(ExaminationSessionsForm examinationSessionsForm,String string) throws Exception{
		boolean flag=false;
		ExaminationSessions examinationSessions=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from ExaminationSessions e where e.isActive=1 and e.session='"+examinationSessionsForm.getSession()+"'");
			examinationSessions=(ExaminationSessions)hqlQuery.uniqueResult();
			session.flush();
			if(examinationSessions!=null){
				if(string.equalsIgnoreCase("update")){
					if(examinationSessionsForm.getId()==examinationSessions.getId()){
						flag=false;
					}else{
						flag=true;
					}
				}else{
					flag=true;
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return flag;
	}
	@Override
	public boolean add(ExaminationSessions examinationSessions)
			throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(examinationSessions);
			tx.commit();
			session.close();
			flag=true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		return flag;
	}
	@Override
	public List<ExaminationSessions> getListOfExaminationSessions()
			throws Exception {
		List<ExaminationSessions> list=new ArrayList<ExaminationSessions>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from ExaminationSessions e where e.isActive=1 order by e.session");
			list=hqlQuery.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	@Override
	public boolean delete(int id,String userId) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				ExaminationSessions examinationSessions = (ExaminationSessions) session.get(ExaminationSessions.class, id);
				examinationSessions.setIsActive(false);
				examinationSessions.setModifiedBy(userId);
				examinationSessions.setLastModifiedDate(new Date());
				session.update(examinationSessions);
			tx.commit();
			session.flush();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return result;
	}
	@Override
	public ExaminationSessions getExaminationSessionById(int id)
			throws Exception {
		ExaminationSessions examinationSessions=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from ExaminationSessions e where e.isActive=1 and e.id="+id);
			examinationSessions=(ExaminationSessions)hqlQuery.uniqueResult();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return examinationSessions;
	}
	@Override
	public boolean update(ExaminationSessions examinationSessions)
			throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			 transaction=session.beginTransaction();
				transaction.begin();
					session.save(examinationSessions);
				transaction.commit();
				flag=true;
		}catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}finally{
			if(session !=null){
				session.flush();
				}
		}
		return flag;
	}
}
