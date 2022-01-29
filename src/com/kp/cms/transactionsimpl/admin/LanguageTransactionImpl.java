package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Language;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.transactions.admin.ILanguageTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class LanguageTransactionImpl implements ILanguageTransaction {

	
	@Override
	public List<MotherTongue> getLanguages() {
		List<MotherTongue> languageBoList = null;
		Session session = null;
		try{
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			languageBoList = session.createQuery("from MotherTongue m where isActive=1")
					.list();
			session.flush();
			//session.close();
			
		}catch (Exception e) {
			if (session != null){
				session.flush();
				 //session.close();
			}
		}
		return languageBoList;
	}
	
	@Override
	public List<Language> getOriginalLanguages() {
		List<Language> languageBoList = null;
		Session session = null;
		try{
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			languageBoList = session.createQuery("from Language m")
					.list();
			session.flush();
			//session.close();
			
		}catch (Exception e) {
			if (session != null){
				session.flush();
				 //session.close();
			}
		}
		return languageBoList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IManageLanguageTransactions#addLanguage(java.lang.String)
	 */
	@Override	
	public boolean addLanguage(String languageName) {
		boolean isLanguageAdded = false;
		Session session =null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			
			transaction.begin();
			
			MotherTongue motherTongue = new MotherTongue();

			motherTongue.setIsActive(true);
			motherTongue.setName(languageName);
			session.save(motherTongue);

			transaction.commit();
			session.flush();
			session.close();

			isLanguageAdded = true;

		} catch (Exception e) {
			if ( transaction != null ){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				 //session.close();
			}
			isLanguageAdded = false;
		}

		return isLanguageAdded;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IManageLanguageTransactions#deleteLanguage(int)
	 */
	@Override
	public boolean deleteLanguage(int languageId) {
		boolean isLanguagedeleted = false;
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			MotherTongue motherTongue = (MotherTongue) session.get(MotherTongue.class,
					languageId);
			motherTongue.setIsActive(false);

			session.update(motherTongue);
			transaction.commit();
			session.flush();
			session.close();

			isLanguagedeleted = true;
		} catch (Exception e) {
			if ( transaction != null ){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				 session.close();
			}
			isLanguagedeleted = false;
		}
		return isLanguagedeleted;
	}



	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IManageLanguageTransactions#editLanguage(int, java.lang.String)
	 */
	@Override
	public boolean editLanguage(int languageId, String languageName) {
		boolean isLanguageAdded = false;
		Session session =null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			MotherTongue motherTongue = (MotherTongue) session.get(MotherTongue.class,
					languageId);
			motherTongue.setName(languageName);
			session.update(motherTongue);

			transaction.commit();
			session.flush();
			session.close();

			isLanguageAdded = true;

		} catch (Exception e) {
			if ( transaction != null ){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				 //session.close();
			}
			isLanguageAdded = false;
		}

		return isLanguageAdded;
	}
}
