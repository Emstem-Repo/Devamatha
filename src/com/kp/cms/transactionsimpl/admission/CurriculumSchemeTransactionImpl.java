package com.kp.cms.transactionsimpl.admission;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admission.ICurriculumSchemeTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CurriculumSchemeTransactionImpl implements	ICurriculumSchemeTransaction {
	private static final Log log = LogFactory.getLog(CurriculumSchemeTransactionImpl.class);

	/**
	 * 
	 * @param courseid
	 * @returns ProgramType Name, Program Name & Course Name based on the
	 *          courseId
	 */

	public Course getCourseProgrmProgramType(final int courseid) throws Exception {
		log.info("Entering into getCourseProgrmProgramType of CurriculumSchemeTransactionImpl");
		Session session = null;
		Course course = null;
		try {
			//session=InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			course = (Course) session.createQuery("from Course course where course.id=?").setInteger(0,courseid).uniqueResult();		
		} catch (Exception e) {
			log.error("Error occured in getting courseName, programName, programtypeName...."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Leaving into getCourseProgrmProgramType of CurriculumSchemeTransactionImpl");
		return course;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method saves a curricular scheme based on the startdate and
	 *         end date for a particular course and year
	 * @throws Exception
	 */

	public boolean setCurriculumScheme(CurriculumScheme scheme)throws Exception {
		log.info("Entering into setCurriculumScheme of CurriculumSchemeTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session=InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(scheme);
			transaction.commit();
			log.info("Leaving into setCurriculumScheme of CurriculumSchemeTransactionImpl");
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Unable to set a curriculumscheme....."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param  Checking in database for duplicate record based on the courseID and year
	 * @returns if any exact record exists.
	 * @throws Exception
	 */

	public List<CurriculumScheme> isExistCourseId(int courseId, int year) throws Exception
	{
		log.info("Entering into isExistCourseId of CurriculumSchemeTransactionImpl");
		Session session = null;
		List<CurriculumScheme> isExistCourseId=null;
		try {
		//session=InitSessionFactory.getInstance().openSession();
		session = HibernateUtil.getSession();
		isExistCourseId=session.createQuery("from CurriculumScheme scheme where scheme.course.id = '" + courseId +" ' and scheme.year = ' " + year + " ' ").list();		
		}
		catch(Exception e){
			log.error("Unable to check courseId."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Leaving into isExistCourseId of CurriculumSchemeTransactionImpl");
		return isExistCourseId;		
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param  
	 * @returns Gets all the records of curriculumScheme
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public List<CurriculumScheme> getCurriculumSchemeDetails ()throws Exception
	{
		log.info("Entering into getCurriculumSchemeDetails of CurriculumSchemeTransactionImpl");
		Session session = null;
		Calendar cal = Calendar.getInstance();
		int CurrentYear = cal.get(Calendar.YEAR);
		int NextYear=CurrentYear+1;
		String Year=CurrentYear+"-"+NextYear;
		System.out.println("Curdate!!!!!!!!!!!"+CurrentYear);
		System.out.println("Nextyear!!!!!!!!!!!"+NextYear);
		System.out.println("Year!!!!!!!!!!!"+Year);
		
		List<CurriculumScheme> detailsList;
		try {
		//session=InitSessionFactory.getInstance().openSession();
		session = HibernateUtil.getSession();
		// where curriculumScheme.year=:Year
	//	detailsList=session.createQuery("from CurriculumScheme curriculumscheme where curriculumscheme.year=:CurrentYear").list();
		 Query query = session.createQuery("from CurriculumScheme curriculumscheme where curriculumscheme.year=:CurrentYear");
         query.setInteger("CurrentYear",CurrentYear);
         detailsList = query.list();
		
		
		}
		catch(Exception e){
			log.error("Error occured at getCurriculumSchemeDetails of IMPL");
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Leaving into getCurriculumSchemeDetails of CurriculumSchemeTransactionImpl");
		return detailsList;	
	}
	/**
	 * Gets a Curriculumscheme based on the id
	 */	
	public CurriculumScheme getCurriculumSchemeDetailsOnId(final int id) throws Exception
	{
		log.info("Entering into getCurriculumSchemeDetailsOnId of CurriculumSchemeTransactionImpl");
		Session session = null;
		CurriculumScheme curriculumBO=null;
		try {
			//session=InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			curriculumBO =(CurriculumScheme) session.createQuery("from CurriculumScheme scheme where scheme.id=?").setInteger(0,id).uniqueResult();
		} catch (Exception e) {
			log.error("Error occured in getting curriculumscheme Details based on Id"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Leaving into getCurriculumSchemeDetailsOnId of CurriculumSchemeTransactionImpl");
		return curriculumBO;	
	}
	/**
	 * Deletes a record from Curriculumscheme based on the ID
	 */
	
	public boolean deleteCurriculumScheme(CurriculumScheme curriculumScheme)throws Exception
	{
		log.info("Entering into deleteCurriculumScheme of CurriculumSchemeTransactionImpl");
		Session session = null;
		Transaction transaction=null;
		try {
			//session=InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction= session.beginTransaction();
			session.delete(curriculumScheme);
			transaction.commit();
			log.info("Leaving into deleteCurriculumScheme of CurriculumSchemeTransactionImpl");
			return true;
			}
			catch (StaleStateException e) {
				if(transaction != null){
					transaction.rollback();
				}
			log.error("Unable to delete in curriculumscheme---");
			throw  new ApplicationException(e);
			}
			catch(Exception e){
				if(transaction != null){
					transaction.rollback();
				}
			log.error("Unable to delete in curriculumscheme---");			
			throw  new BusinessException(e);
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}	
	}
	
	/**
	 *Updates a record in Curriculumscheme DB
	 */
	
	public boolean updateCurriculumScheme(CurriculumScheme curriculumScheme)throws Exception 
	{
		log.info("Entering into updateCurriculumScheme of CurriculumSchemeTransactionImpl");
		Session session = null;
		Transaction transaction=null;
		try {
				if(curriculumScheme != null){
				//session=InitSessionFactory.getInstance().openSession();
				session = HibernateUtil.getSession();
				transaction= session.beginTransaction();
				session.update(curriculumScheme);
				transaction.commit();
				return true;
				}
			}
			catch(Exception e){
				if(transaction != null){
					transaction.rollback();
				}
			log.error("Unable to update in curriculumscheme---");			
			throw  new ApplicationException(e);
				
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
			}	
				log.info("leaving into updateCurriculumScheme of CurriculumSchemeTransactionImpl");
				return false;
		}
	
	/**
	  * Gets CurriculumScheme by Course and Year
	  */
	
	 public List<CurriculumScheme> getCurriculumSchemeCourseByCourse(int courseId,int year) throws Exception {
		 log.info("Entering into getCurriculumSchemeCourseByCourse of CurriculumSchemeTransactionImpl");
		Session session = null;
		try {
//			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			 session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from CurriculumScheme where course.id = :courseId" +
			 								   " and year = :year");
					                          
			 query.setInteger("courseId", courseId);
			 query.setInteger("year",year);
			 List<CurriculumScheme> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.info("Leaving into getCurriculumSchemeCourseByCourse of CurriculumSchemeTransactionImpl");
			 return list;
		 } catch (Exception e) {
			 log.error("Error occured atgetCurriculumSchemeCourseByCourse of IMPL ");
			 if(session!= null){
				 session.close();
			 }
			 log.error(e);
			 throw e;
		 }
	}

	@Override
	public List<CurriculumScheme> getCurriculumSchemeDetailsYearwise(int year)throws Exception
	{
		log.info("Entering into getCurriculumSchemeDetails of CurriculumSchemeTransactionImpl");
		Session session = null;
		
		List<CurriculumScheme> detailsList;
		try {
		session = HibernateUtil.getSession();
		 Query query = session.createQuery("from CurriculumScheme curriculumscheme where curriculumscheme.year=:year");
         query.setInteger("year",year);
         detailsList = query.list();
			
		}
		catch(Exception e){
			log.error("Error occured at getCurriculumSchemeDetails of IMPL");
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Leaving into getCurriculumSchemeDetails of CurriculumSchemeTransactionImpl");
		return detailsList;	
	}
	 public List<CurriculumSchemeDuration> getScheme(int id, Integer appliedYear,Student student)throws Exception{
		 Session session = null;
			try {
//				 SessionFactory sessionFactory = InitSessionFactory.getInstance();
//				 session =sessionFactory.openSession();
				 session = HibernateUtil.getSession();
				 String applNo = Integer.toString(student.getAdmAppln().getApplnNo());
				 String qy = "";
				 if(student.getClassSchemewise() == null ){
					 qy = "from CurriculumSchemeDuration cd where cd.academicYear= " +appliedYear+
				 		" and cd.curriculumScheme.course.id= " +id+
				 		" and cd.curriculumScheme.year="+appliedYear;
				 }
				 else{
					 qy = "from CurriculumSchemeDuration cd where cd.academicYear= (select csd.academicYear from Student s " +
				 		" left join s.classSchemewise.curriculumSchemeDuration csd " +
				 		" left join csd.curriculumScheme cs " +
				 		" where cs.year= " +appliedYear+
				 		" and s.admAppln.applnNo='"+applNo+"') " +
				 		" and cd.curriculumScheme.course.id= " +id+
				 		" and cd.curriculumScheme.year="+appliedYear;
				 }
				 Query query = session.createQuery(qy);
				
				 List<CurriculumSchemeDuration> list = query.list();
				 //session.close();
				 //sessionFactory.close();
				 log.info("Leaving into getCurriculumSchemeCourseByCourse of CurriculumSchemeTransactionImpl");
				 return list;
			 } catch (Exception e) {
				 log.error("Error occured atgetCurriculumSchemeCourseByCourse of IMPL ");
				 if(session!= null){
					 session.close();
				 }
				 log.error(e);
				 throw e;
			 }
	 }
	
	 public CurriculumSchemeDuration getCurriculumSchemeDurationByClassId(int classId){
		 Session session = null;
		 CurriculumSchemeDuration currSchemeDuration=null;
			try {
				 session = HibernateUtil.getSession();
				 String qy  = "select c.curriculumSchemeDuration from ClassSchemewise c where c.classes.id = " +classId;
				 		
				 Query query = session.createQuery(qy);
				
				 currSchemeDuration = (CurriculumSchemeDuration) query.uniqueResult();
				
				 log.info("Leaving into getCurriculumSchemeDurationByClassId of CurriculumSchemeTransactionImpl");
				 
			 } catch (Exception e) {
				 log.error("Error occured at getCurriculumSchemeDurationByClassId of IMPL ");
				 e.printStackTrace();
				 if(session!= null){
					 session.close();
				 }
				 log.error(e);
			 }
			 return currSchemeDuration;
	 }	
}
