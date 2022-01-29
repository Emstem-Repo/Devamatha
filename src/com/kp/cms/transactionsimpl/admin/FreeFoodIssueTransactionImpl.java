package com.kp.cms.transactionsimpl.admin;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.FreeFoodIssueBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.helpers.admin.FreeFoodIssueHelper;
import com.kp.cms.transactions.admin.IFreeFoodIssueTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class FreeFoodIssueTransactionImpl implements IFreeFoodIssueTransaction{
	private static final Log log = LogFactory.getLog(FreeFoodIssueTransactionImpl.class);	
	public static volatile FreeFoodIssueTransactionImpl freeFoodIssueTransactionImpl = null;
	
	/**
	 * @return
	 */
	public static FreeFoodIssueTransactionImpl getInstance() {
		if (freeFoodIssueTransactionImpl == null) {
			freeFoodIssueTransactionImpl = new FreeFoodIssueTransactionImpl();
		}
		return freeFoodIssueTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IFreeFoodIssueTransaction#getStudentDetails(java.lang.String)
	 */
	public List<Object[]> getStudentDetails(String registerNumber) throws Exception {
		Session session = null;
		List<Object[]> objArray =null;
		try {
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("select s.id,p.firstName,add.titleFather,add.titleMother,"+ 
																" p.incomeByFatherIncomeId.id,p.incomeByMotherIncomeId.id,cls.name from PersonalData p"+
																" inner join  p.admApplns adm"+
																" inner join adm.students s"+
																" inner join s.classSchemewise.classes cls"+
																" left join adm.admapplnAdditionalInfo add "+
																" where s.registerNo='"+registerNumber+"'");
			objArray=(List<Object[]>) query.list();
			session.flush();
			return objArray;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IFreeFoodIssueTransaction#saveStudentdata(com.kp.cms.bo.admin.FreeFoodIssueBo)
	 */
	public boolean saveStudentdata(FreeFoodIssueBo bo) throws Exception {
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 
			 tx = session.beginTransaction();
			 tx.begin();
			 session.save(bo);
			 tx.commit();
	    	 return true;
		 } catch (ConstraintViolationException e) {
			 tx.rollback();
			 log.debug("Txn Impl : Leaving saveStudentdata with Exception"+e.getMessage());
			 throw e;				 
		 } catch (Exception e) {
			 if(tx!=null)
			       tx.rollback();
			 log.debug("Txn Impl : Leaving saveStudentdata with Exception");
			 throw e;				 
		 }finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		 } 
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IFreeFoodIssueTransaction#checkingDuplicateEntry(java.lang.String)
	 */
	public List<Object[]> checkingDuplicateEntry(String registerNumber) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Properties prop=new Properties();
			InputStream stream=FreeFoodIssueHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			String dupCheckingTime =prop.getProperty("knowledgepro.admin.free.food.issue.dup.checking.time");
			Date date = new Date();
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			/*SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String curDateTime = sdfDateTime.format(date);
		    String curTime=printFormat.format(date);*/
		    int hour=date.getHours();
		    int min=date.getMinutes();
		    int sec=date.getSeconds();
		    int formatHour=0;
		    if(hour > 12){
		    	 formatHour=hour-12;
		    }
		    String formatTime=String.valueOf(formatHour)+":"+min+":"+sec;
		    String curDate = sdfDate.format(date);
		    
			Query query=session.createSQLQuery("select f.id,time(f.date_time) from free_food_issue f "+
												" inner join student ON f.student_id = student.id"+
												" where student.register_no='"+registerNumber+"' and f.is_active=1 "+
												" and date(f.date_time) = '"+curDate+"'"+
												" and f.date_time <= date_sub('"+curDate+" "+formatTime+"', INTERVAL "+dupCheckingTime+" MINUTE)"+
												" order by time(f.date_time) desc LIMIT 1");
			List<Object[]>  List = query.list();
			session.flush();
			return List;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}
	}
	
	
	public Integer foodIssuedCount(String registerNumber) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("select p "+ 
												"  from FreeFoodIssueBo p"+
												"  where  p.isEligible='Not Eligible' and p.studentId.registerNo='"+registerNumber+"'"+ " group by  date(dateAndTime) " );
												
			List<FreeFoodIssueBo> bo= (List<FreeFoodIssueBo>) query.list();
			session.flush();
			
			return bo.size();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}
	}
}
