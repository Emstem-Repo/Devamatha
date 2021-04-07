package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.transactions.admin.ICountryTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * 
 * @version 1.0
 * Date 09/jan/2009
 * This is Transaction Class for Country 
 * This class used to interact with country table.
 */

public class CountryTranscationImpl implements ICountryTransaction{
	
	private static final Log log = LogFactory
	.getLog(CountryTranscationImpl.class);
	
	public static volatile CountryTranscationImpl countryTranscationImpl = null;
	
	public static CountryTranscationImpl getInstance() {
		   if(countryTranscationImpl == null ){
			   countryTranscationImpl = new CountryTranscationImpl();
			   return countryTranscationImpl;
		   }
		   return countryTranscationImpl;
	}
	
	/**
	 * This method add the country to Database.
	 *  @return true / flase based on result.
	 */
	public boolean addCountry(Country country) throws DuplicateException,ApplicationException{
		log.info("call of addCountry in CountryTransactionImpl class");
		Session session = null;
		Transaction transaction = null;
		 try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from Country where name = :name");
			 query.setString("name", country.getName());
			 
			 Country testCountry = (Country)query.uniqueResult();
			 transaction = session.beginTransaction();
			 if(testCountry != null) {
				 throw new DuplicateException();
			 }
			 transaction.begin();
			 session.save(country);
			 transaction.commit();
			 session.flush();
			 session.close();
			 log.info("end of addCountry in CountryTransactionImpl class");
	    	 return true;
		 } catch (DuplicateException e){
			 if ( transaction != null){
				 transaction.rollback();
			 }
			 if( session != null ){
				 session.flush();
				 //session.close();
			 }
			 throw e;
		 } catch (Exception e){
			 if ( transaction != null){
				 transaction.rollback();
			 }
			 if( session != null ){
				 session.flush();
				 //session.close();
			 }
			log.error("error occured in addCountry in CountryTransactionImpl",e);
			 throw new ApplicationException(e);
		 }
	 
	 }
	
	/**
	 * This method return list of country all objects.
	 */
	public List<Country> getCountries() {
		log.info("call of getCountries in CountryTransactionImpl");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from Country c where c.isActive=1 order by c.name");
			 List<Country> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getCountries in CountryTransactionImpl");
			 return list;
		 } catch (Exception e) {
			 log.error("error occured in getCountries in CountryTransactionImpl",e);
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
		 }
		 return new ArrayList<Country>();
	 }
	
	/**
	 * This method update the country to Database.
	 *  @return true / flase based on result.
	 */
	public boolean updateCountry(Country country) throws Exception{
		log.info("call of updateCountry in CountryTransactionImpl class");
		Session session = null;
		Transaction tx = null;
		 try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 session.update(country);
			 tx.commit();
			 session.flush();
			 session.close();
			 log.info("end of updateCountry in CountryTransactionImpl class");
	    	 return true;
		 } catch (Exception e){
			 log.error("error occured in updateCountry in CountryTransactionImpl",e);
			 if ( tx != null){
				 tx.rollback();
			 }
			 if( session != null ){
				 session.flush();
				 session.close();
			 }
			 return false;
		 }
	 }
	
	/**
	 * This method delete the country to Database.
	 *  @return true / flase based on result.
	 */
	public boolean deleteCountry(Country country) throws Exception{
		log.info("call of deleteCountry in CountryTransactionImpl class");
		 Session session = null;
		 Transaction tx = null;
		 try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 session.delete(country);
			 tx.commit();
			 session.flush();
			 session.close();
			 log.info("end of deleteCountry in CountryTransactionImpl class");
	    	 return true;
		 } catch (Exception e){
			 log.error("error occured in deleteCountry in CountryTransactionImpl class",e);
			 if ( tx != null) {
				 tx.rollback();
			 }
			 if( session != null ){
				 session.flush();
				 session.close();
			 }
			 return false;
		 }
	 }
}