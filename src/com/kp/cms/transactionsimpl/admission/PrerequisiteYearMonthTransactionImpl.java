package com.kp.cms.transactionsimpl.admission;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admission.PrerequisitsYearMonth;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IPrerequisitesYearMonth;
import com.kp.cms.utilities.HibernateUtil;

public class PrerequisiteYearMonthTransactionImpl implements IPrerequisitesYearMonth
{

    private static final Logger log = Logger.getLogger(PrerequisiteYearMonthTransactionImpl.class);

	
	 @SuppressWarnings("unchecked")
		public List<PrerequisitsYearMonth> getPrerequisiteYearMonthDetails()
	        throws Exception
	    {
	        Session session;
	        List<PrerequisitsYearMonth> yearList;
	        session = null;
	        try
	        {
	            session = HibernateUtil.getSession();
	            yearList = session.createQuery("from PrerequisitsYearMonth where isActive=1 order by year").list();
	        }
	        catch(Exception e)
	        {
	            log.error((new StringBuilder("Exception occured in getPrerequisiteYearMonthDetails() in PrerequisiteYearMonthTransactionImpl : ")).append(e).toString());
	            throw new ApplicationException(e);
	        }
	       
	        finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
	        return yearList;
	    }

	    public boolean addAcademicYear(PrerequisitsYearMonth prereqYearMonth)
	        throws Exception
	    {
	    	Session session = null;
			Transaction transaction = null;
	       
	        try
	        {
	            session = HibernateUtil.getSession();
	            transaction = session.beginTransaction();
	            if(prereqYearMonth != null)
	            {
	                session.save(prereqYearMonth);
	            }
	            transaction.commit();
	            return true;
	        }
	        catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				log.error("Exception occured in addFeeFinancialYear in FeeFinancialYearTransactionImpl : "+ e);
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
	    }

	    public PrerequisitsYearMonth getPrereqYear(int year, int month)
	        throws Exception
	    {
	        Session session;
	        Transaction transaction;
	        PrerequisitsYearMonth prereqYearMonth;
	        session = null;
	        transaction = null;
	        prereqYearMonth = null;
	        try
	        {
	            session = HibernateUtil.getSession();
	            transaction = session.beginTransaction();
	            session = HibernateUtil.getSession();
	            Criteria criteria = session.createCriteria(PrerequisitsYearMonth.class);
	            criteria.add(Restrictions.eq("year", year));
	            criteria.add(Restrictions.eq("month", month));
	            prereqYearMonth = (PrerequisitsYearMonth)criteria.uniqueResult();
	            transaction.commit();
	        }
	        catch(Exception e)
	        {
	            if(transaction != null)
	            {
	                transaction.rollback();
	            }
	            log.error((new StringBuilder("Exception occured in getAcademicYear in PrerequisiteYearMonthTransactionImpl : ")).append(e).toString());
	            throw new ApplicationException(e);
	        }
	        finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
	        return prereqYearMonth;
	    }

	    public PrerequisitsYearMonth getPreReqYearDetailsWithId(int yearId)
	        throws Exception
	    {
	        Session session;
	        PrerequisitsYearMonth preqYearMonth;
	        session = null;
	        preqYearMonth = null;
	        try
	        {
	            session = HibernateUtil.getSession();
	            Query query = session.createQuery("from PrerequisitsYearMonth pre where pre.id= :id");
	            query.setInteger("id", yearId);
	            preqYearMonth = (PrerequisitsYearMonth)query.uniqueResult();
	        }
	        catch(Exception e)
	        {
	            log.error("Exception occured in getPreReqYearDetailsWithId in PrerequisiteYearMonthTransactionImpl"+e);
	            throw new ApplicationException(e);
	        }
	        finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
	        return preqYearMonth;
	    }

	    public boolean updatePreYearMonth(PrerequisitsYearMonth prereqYearMonth)
	        throws Exception
	    {
	    	Session session = null;
	        Transaction transaction = null;
	        try
	        {
	            session = HibernateUtil.getSession();
	            transaction = session.beginTransaction();
	            session.merge(prereqYearMonth);
	            transaction.commit();
	            return true;
	        }
	        catch(Exception e)
	        {
	            if(transaction != null)
	            {
	                transaction.rollback();
	            }
	            log.error((new StringBuilder("Exception occured in updatePreYearMonth in PrerequisiteYearMonthTransactionImpl : ")).append(e).toString());
	            throw new ApplicationException(e);
	        }
	        finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
	    }

	    public boolean deleteAcademicYearDetails(int yearId, String userId)
	        throws Exception
	    {
	        
	       Session  session = null;
	        Transaction transaction = null;
	        try
	        {
	            session = HibernateUtil.getSession();
	            transaction = session.beginTransaction();
	            PrerequisitsYearMonth PreYearMonth = (PrerequisitsYearMonth)session.get(PrerequisitsYearMonth.class, yearId);
	            PreYearMonth.setIsActive(Boolean.valueOf(false));
	            PreYearMonth.setModifiedBy(userId);
	            PreYearMonth.setLastModifiedDate(new Date());
	            session.update(PreYearMonth);
	            transaction.commit();
	            return true;
	        }
	        catch(Exception e)
	        {
	            if(transaction != null)
	            {
	                transaction.rollback();
	            }
	            log.error((new StringBuilder("Exception occured in deleteAcademicYearDetails of PrerequisiteYearMonthTransactionImpl : ")).append(e).toString());
	            throw new ApplicationException(e);
	        }
	        finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
	              
	    }

	    public boolean reActivateAcademicYear(int year,int month, String userId)
	        throws Exception
	    {
	    	Session session = null;
	        Transaction transaction = null;
	        try
	        {
	            session = HibernateUtil.getSession();
	            Query query = session.createQuery("from PrerequisitsYearMonth pre where pre.year = :year and pre.month = :month");
	            query.setInteger("year", year);
	            query.setInteger("month", month);
	            PrerequisitsYearMonth pre = (PrerequisitsYearMonth)query.uniqueResult();
	            transaction = session.beginTransaction();
	            pre.setIsActive(Boolean.valueOf(true));
	            pre.setModifiedBy(userId);
	            pre.setLastModifiedDate(new Date());
	            session.update(pre);
	            transaction.commit();
	            return true;
	        }
	        catch(Exception e)
	        {
	            if(transaction != null)
	            {
	                transaction.rollback();
	            }
	            log.error((new StringBuilder("Exception occured in reActivateAcademicYear in PrerequisiteYearMonthTransactionImpl : ")).append(e).toString());
	            throw new ApplicationException(e);
	        }
	        finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
	    }
	   
	
	

}
