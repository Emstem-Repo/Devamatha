package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.transactions.admin.IReGeneratePasswordTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ReGeneratePasswordTransactionImpl implements
		IReGeneratePasswordTransaction {

	@Override
	public List<StudentLogin> getStudentLogins(ArrayList<String> registerNoList)
			throws Exception {
		Session session = null;
		List<StudentLogin> selectedCandidatesList = null;
		try {
			StringBuffer intType =new StringBuffer();
			for(int i=0;i<registerNoList.size();i++){
				 intType.append("'").append(registerNoList.get(i)).append("'");
				 if(i<(registerNoList.size()-1)){
					 intType.append(",");
				 }
			}
			String query="from StudentLogin s where s.isActive=1 and s.isStudent=1 and s.student.isActive=1 and s.student.admAppln.isCancelled=0" +
					" and s.student.registerNo in ("+intType+")";
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}

	@Override
	public List<String> getStudentsByRegisterNo(ArrayList<String> registerNoList)
			throws Exception {
		Session session = null;
		List<String> selectedCandidatesList = null;
		try {
			StringBuffer intType =new StringBuffer();
			for(int i=0;i<registerNoList.size();i++){
				intType.append("'").append(registerNoList.get(i)).append("'");
				 if(i<(registerNoList.size()-1)){
					 intType.append(",");
				 }
			}
			String query="select upper(s.student.registerNo) from StudentLogin s where s.isActive=1 and s.isStudent=1 and s.student.isActive=1 and s.student.admAppln.isCancelled=0" +
					" and s.student.registerNo in ("+intType+")";
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	@Override
	public boolean checkDuplicateRegisterNo(StudentLogin login)
			throws Exception {
		Session session = null;
		List<StudentLogin> selectedCandidatesList = null;
		boolean isDuplicate=false;
		try {
			String query="from StudentLogin s where s.isActive=1 and s.userName='"+login.getUserName()+"'";
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			if(selectedCandidatesList!=null && !selectedCandidatesList.isEmpty()){
				Iterator<StudentLogin> itr=selectedCandidatesList.iterator();
				while (itr.hasNext()) {
					StudentLogin studentLogin = (StudentLogin) itr.next();
					if(login.getId()!=studentLogin.getId()){
					String msg=" The User Name is Already Assigned to the Student"+studentLogin.getStudent().getRegisterNo();
					throw new ApplicationException(msg);
					}else{
						isDuplicate=true;
					}
				}
				
			}else{
				isDuplicate=true;
			}
			return isDuplicate;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}

	@Override
	public boolean updateStudentLogin(List<StudentLogin> finalStudentLogins)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		StudentLogin studentLogin;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<StudentLogin> tcIterator = finalStudentLogins.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				studentLogin = tcIterator.next();
				session.merge(studentLogin);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			throw new ApplicationException(e);
		}
	}

}
