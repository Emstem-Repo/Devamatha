package com.kp.cms.transactionsimpl.reports;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamSecondLanguageMasterBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.ISecondLanguageTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SecondLanguageTransactionImpl implements ISecondLanguageTransaction {
	private static final Log log = LogFactory.getLog(SecondLanguageTransactionImpl.class);

	/**
	 * Gets all second languages from Subjects table
	 */
	public Map<String, String> getAllSecondLanguages() throws Exception {
		log.info("Inside of getAllSecondLanguages of SecondLanguageTransactionImpl");
		Session session = null;
		Map<String, String> secondLanguageMap = new LinkedHashMap<String, String>();
		secondLanguageMap.put("All", "All");
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<Subject> subjectList = session.createQuery("from Subject s where s.isActive = 1 and s.isSecondLanguage = 1 order by s.id").list();
			if(subjectList!=null){
				Iterator<Subject> subIterator = subjectList.iterator();
				while (subIterator.hasNext()) {
					Subject subject = subIterator.next();
					secondLanguageMap.put(subject.getName(), subject.getName());
				}
			}
			log.info("End of getAllSecondLanguages of SecondLanguageTransactionImpl");
			return secondLanguageMap;
		} catch (Exception e) {
			log.error("Error occured in getAllSecondLanguages of SecondLanguageTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/**
	 * Returns the student informations with the second language
	 */
	
	public List<Object[]> getSecondLanguageStudents(String searchCriteria)throws Exception {
		log.info("entered getSecondLanguageStudents..");
		Session session = null;
		List<Object[]> studentList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			 studentList = session.createQuery(searchCriteria).list();	
		} catch (Exception e) {
			log.error("error while getting the student in getSecondLanguageStudents "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getSecondLanguageStudents..");
		return studentList;
	}
	
	public Map<String, String> getAllSecondLanguagesFromExamSecondLanguage() throws Exception {
		log.info("Inside of getAllSecondLanguages of SecondLanguageTransactionImpl");
		Session session = null;
		Map<String, String> secondLanguageMap = new LinkedHashMap<String, String>();
		secondLanguageMap.put("All", "All");
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<ExamSecondLanguageMasterBO> subjectList = session.createQuery("from ExamSecondLanguageMasterBO s where s.isActive = 1  order by s.id").list();
			if(subjectList!=null){
				Iterator<ExamSecondLanguageMasterBO> subIterator = subjectList.iterator();
				while (subIterator.hasNext()) {
					ExamSecondLanguageMasterBO subject = subIterator.next();
					secondLanguageMap.put(subject.getName(), subject.getName());
				}
			}
			log.info("End of getAllSecondLanguages of SecondLanguageTransactionImpl");
			return secondLanguageMap;
		} catch (Exception e) {
			log.error("Error occured in getAllSecondLanguages of SecondLanguageTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
}