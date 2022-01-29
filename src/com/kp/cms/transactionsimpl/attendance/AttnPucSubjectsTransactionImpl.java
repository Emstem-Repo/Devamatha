package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.AttnPucCetMarks;
import com.kp.cms.bo.admin.AttnPucDefineRange;
import com.kp.cms.bo.admin.AttnPucSubjects;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AttnPucSubjectForm;
import com.kp.cms.transactions.attandance.IAttnPucSubjectsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AttnPucSubjectsTransactionImpl implements IAttnPucSubjectsTransaction {
	private static final Log log = LogFactory
	.getLog(AttnPucSubjectsTransactionImpl.class);
	private static volatile AttnPucSubjectsTransactionImpl attnPucSubjectsTransactionImpl = null;
	public static AttnPucSubjectsTransactionImpl getInstance(){
		if(attnPucSubjectsTransactionImpl == null){
			attnPucSubjectsTransactionImpl= new AttnPucSubjectsTransactionImpl();
			return attnPucSubjectsTransactionImpl;
		}
		return attnPucSubjectsTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttnPucSubjectsTransaction#addPucSubjects(java.util.List)
	 */
	@Override
	public boolean addPucSubjects(List<AttnPucSubjects> pucSubjects)
			throws Exception {
		boolean isAdded= false;
		Session session= null;
		Transaction tx= null;
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(pucSubjects!=null){
				Iterator<AttnPucSubjects> iterator = pucSubjects.iterator();
				while (iterator.hasNext()) {
					AttnPucSubjects attnPucSubjects = (AttnPucSubjects) iterator .next();
					if(attnPucSubjects.getClasses()!=null && !attnPucSubjects.getClasses().isEmpty()){
					String str = "from AttnPucSubjects attnSub where attnSub.academicYear="+attnPucSubjects.getAcademicYear()+"and attnSub.classes='"+attnPucSubjects.getClasses()+"'";
					Query query = session.createQuery(str);
					AttnPucSubjects subjects = (AttnPucSubjects) query.uniqueResult();
					if(subjects == null){
						session.save(attnPucSubjects);
					    }
					}
				}
				tx.commit();
				session.flush();
				isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	@Override
	public boolean addPucCetMarks(List<AttnPucCetMarks> list,AttnPucSubjectForm attnPucSubjectForm) throws Exception {
		boolean isAdded= false;
		Session session= null;
		Transaction tx= null;
		int count=0;
		List<String> regNos=new ArrayList<String>();
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(list!=null){
				int size=list.size();
				Iterator<AttnPucCetMarks> iterator = list.iterator();
				while (iterator.hasNext()) {
					AttnPucCetMarks attnCetMarks = (AttnPucCetMarks) iterator .next();
					if(attnCetMarks.getClasses()!=null && !attnCetMarks.getClasses().isEmpty()){
					String str = "from AttnPucCetMarks cet where cet.regNo='"+attnCetMarks.getRegNo()+"' and cet.classes='"+attnCetMarks.getClasses()+"' and cet.testId='"+attnCetMarks.getTestId()+"'";
					Query query = session.createQuery(str);
					AttnPucCetMarks cetMarks = (AttnPucCetMarks) query.uniqueResult();
					if(cetMarks == null){
						session.save(attnCetMarks);
					}else{
						regNos.add(attnCetMarks.getRegNo());
						count++;
					     }
					}	
				}
				attnPucSubjectForm.setRegNosList(regNos);
				tx.commit();
				session.flush();
				if(size==count)
					isAdded=false;
				else
				    isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	@Override
	public List<AttnPucCetMarks> getAttnCetMarks(
			AttnPucSubjectForm attnPucSubjectForm) throws Exception {
		log.info("entering into getAttnCetMarks in AttnPucSubjectsTransactionImpl class");
		Session session = null;
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="from AttnPucCetMarks attn where attn.academicYear="+attnPucSubjectForm.getAcademicYear()+ " and attn.classes='"+attnPucSubjectForm.getCourseName()+"'";
			 Query query = session.createQuery(queryString);
			 List<AttnPucCetMarks> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getAttnCetMarks in AttnPucSubjectsTransactionImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getAttnCetMarks data..." , e);
			 throw e;
		 }
	}

	@Override
	public AttnPucSubjects getAttnSubject(String className) throws Exception {
		log.info("entering into getAttnSubject in AttnPucSubjectsTransactionImpl class");
		Session session = null;
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="from AttnPucSubjects sub where sub.classes='"+className+"'";
			 Query query = session.createQuery(queryString);
			 List<AttnPucSubjects> attnSubjects = query.list();
			 session.flush();
			 Iterator<AttnPucSubjects> itr=attnSubjects.iterator();
			 AttnPucSubjects subjects=new AttnPucSubjects();
			 while(itr.hasNext()){
				 subjects=itr.next();
				 break;
			 }
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getAttnSubject in AttnPucSubjectsTransactionImpl class");
			 return subjects;
		 } catch (Exception e) {
			 log.error("Error during getAttnSubject data..." , e);
			 throw e;
		 }
	}

	@Override
	public boolean addAttDefineRange(List<AttnPucDefineRange> list) throws Exception {
			
		boolean isAdded= false;
		Session session= null;
		Transaction tx= null;
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(list!=null){
				Iterator<AttnPucDefineRange> iterator = list.iterator();
				while (iterator.hasNext()) {
					AttnPucDefineRange defineRange = (AttnPucDefineRange) iterator .next();
						session.save(defineRange);
					}
				}
				tx.commit();
				session.flush();
				    isAdded = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
}
