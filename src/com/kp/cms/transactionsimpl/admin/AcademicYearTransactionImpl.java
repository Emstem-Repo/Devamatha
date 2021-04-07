package com.kp.cms.transactionsimpl.admin;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IAcademicYearTransaction;
import com.kp.cms.utilities.HibernateUtil;
import java.util.*;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

public class AcademicYearTransactionImpl implements IAcademicYearTransaction {

	private static volatile AcademicYearTransactionImpl obj;
	
	public static AcademicYearTransactionImpl getInstance() {
		if(obj == null) {
			obj = new AcademicYearTransactionImpl();
		}
		return obj;
	}
    
    @SuppressWarnings("unchecked")
	public List<AcademicYear> getAcademicYearDetails()
        throws Exception
    {
        Session session;
        List<AcademicYear> academicYearList;
        session = null;
        try
        {
            session = HibernateUtil.getSession();
            academicYearList = session.createQuery("from AcademicYear where isActive=1 order by year").list();
        }
        catch(Exception e)
        {
            throw new ApplicationException(e);
        }
       
        finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
        return academicYearList;
    }

    public boolean addAcademicYear(AcademicYear academicYear)
        throws Exception
    {
    	Session session = null;
		Transaction transaction = null;
       
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            if(academicYear != null)
            {
                session.save(academicYear);
            }
            transaction.commit();
            return true;
        }
        catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
    }

    public AcademicYear getAcademicYear(int academicYearValue)
        throws Exception
    {
        Session session;
        Transaction transaction;
        AcademicYear academicYear;
        session = null;
        transaction = null;
        academicYear = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session = HibernateUtil.getSession();
            Criteria criteria = session.createCriteria(AcademicYear.class);
            criteria.add(Restrictions.eq("year", academicYearValue));
            academicYear = (AcademicYear)criteria.uniqueResult();
            transaction.commit();
        }
        catch(Exception e)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
            throw new ApplicationException(e);
        }
        finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
        return academicYear;
    }

    public AcademicYear getAcademicYearDetailsWithId(int yearId)
        throws Exception
    {
        Session session;
        AcademicYear academicYear;
        session = null;
        academicYear = null;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from AcademicYear academicYear where academicYear.id= :id");
            query.setInteger("id", yearId);
            academicYear = (AcademicYear)query.uniqueResult();
        }
        catch(Exception e)
        {
            throw new ApplicationException(e);
        }
        finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
        return academicYear;
    }

    public boolean updateAcademicYear(AcademicYear academicYear)
        throws Exception
    {
    	Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.merge(academicYear);
            transaction.commit();
            return true;
        }
        catch(Exception e)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
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
            AcademicYear academicYear = (AcademicYear)session.get(AcademicYear.class, yearId);
            academicYear.setIsCurrent(Boolean.valueOf(false));
            academicYear.setIsActive(Boolean.valueOf(false));
            academicYear.setModifiedBy(userId);
            academicYear.setLastModifiedDate(new Date());
            session.update(academicYear);
            transaction.commit();
            return true;
        }
        catch(Exception e)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
            throw new ApplicationException(e);
        }
        finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
              
    }

    public boolean reActivateAcademicYear(int academicYr, String userId)
        throws Exception
    {
    	Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from AcademicYear academicYear where academicYear.year = :academicYr");
            query.setInteger("academicYr", academicYr);
            AcademicYear academicYear = (AcademicYear)query.uniqueResult();
            transaction = session.beginTransaction();
            academicYear.setIsActive(Boolean.valueOf(true));
            academicYear.setModifiedBy(userId);
            academicYear.setLastModifiedDate(new Date());
            session.update(academicYear);
            transaction.commit();
            return true;
        }
        catch(Exception e)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
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
