package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.City;
import com.kp.cms.transactions.admin.ICityTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CityTransactionImpl implements ICityTransaction{
		
public static volatile CityTransactionImpl cityTransactionImpl = null;
	
	public static CityTransactionImpl getInstance() {
		   if(cityTransactionImpl == null ){
			   cityTransactionImpl = new CityTransactionImpl();
			   return cityTransactionImpl;
		   }
		   return cityTransactionImpl;
	}
	
	/**
	 * This method add a single city to Database.
	 *  @return true / false based on result.
	 */
	public boolean addCity(City city){
		 Session session = null;
		 Transaction tx = null;
		 try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 session.save(city);
			 tx.commit();
			 session.flush();
			 session.close();

	    	 return true;
		 } catch (Exception e){
			 if ( tx!= null){
					tx.rollback(); 
			 }
			 if ( session != null){
				 session.flush();
				 session.close();
			 }
			 return false;
		 }
	 }
	
	/**
	 * This method return list of city all objects.
	 */
	public List<City> getCities(){
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from City");
			 List<City> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 return list;
		 } catch (Exception e) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }	 
		 }
		 return new ArrayList<City>();
	 }
	
	/**
	 * This method update the city to Database.
	 *  @return true / flase based on result.
	 */
	public boolean updateCity(City city){
		Session session = null;
		Transaction tx = null;
		 try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 session.update(city);
			 tx.commit();
			 session.flush();
			 session.close();
	    	 return true;
		 } catch (Exception e){
			 if( tx != null ){
				 tx.rollback();
			 }
			 if ( session != null ){
				 session.flush();
				 session.close();
			 }
			 return false;
		 }
	 }
	
	/**
	 * This method delete the city to Database.
	 *  @return true / flase based on result.
	 */
	public boolean deleteCity(City city){
		 Session session = null;
		 Transaction tx = null;
		 try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 session.delete(city);
			 tx.commit();
			 session.flush();
			 session.close();
	    	 return true;
		 } catch (Exception e){
			 if( tx != null){
				 tx.rollback();
			 }
			 if(session != null){
				 session.flush();
				 session.close();
			 }
			 return false;
		 }
	 
	 }

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICityTransaction#getCityById(int)
	 * This method will return a single City object based on Id.
	 */
	public City getCityById(int id) {
		 Session session = null;
		 Transaction tx = null;
		 try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();
			 City city = (City)session.get(City.class, id);
			 tx.commit();
			 session.flush();
			 //session.close();
	    	 return city;
		 } catch (Exception e){
			 if ( tx != null ){
				 tx.rollback();
			 }
			 if ( session != null){
				 session.flush();
				 //session.close();
			 }
			 return null;
		 }
		
	}
}
