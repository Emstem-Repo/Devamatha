package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlInOut;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.hostel.StudentInoutTo;
import com.kp.cms.transactions.hostel.IStudentInoutTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class StudentInoutTransactionImpl implements IStudentInoutTransactions {
	private static final Log log = LogFactory.getLog(StudentInoutTransactionImpl.class);
	private static volatile StudentInoutTransactionImpl studentInoutTransactionImpl = null;

	public static StudentInoutTransactionImpl getInstance() {
		if (studentInoutTransactionImpl == null) {
			studentInoutTransactionImpl = new StudentInoutTransactionImpl();
		}
		return studentInoutTransactionImpl;
	}
	public List<Object> getStudentDetails(String searchQuery) throws Exception {
		log.info("Entering getStudentDetails StudentInoutTransactionImpl");
		Session session = null;
		List<Object> studentDetails;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			studentDetails = session.createQuery(searchQuery).list();
			} catch (Exception e) {
			log.error("error while getting the getApplicantHostelDetailsList  "+e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Exiting getStudentDetails StudentInoutTransactionImpl");
		return studentDetails;
	}
	public String submitStudentDetails(HlInOut hlInOut)  throws Exception{
		log.info("Entering submitStudentDetails StudentInoutTransactionImpl");
		Session session=null;
		String data="false";
		try {
			/*SessionFactory sessFactory=InitSessionFactory.getInstance();
			session=sessFactory.openSession();*/
			session = HibernateUtil.getSession();
			Transaction trans=session.beginTransaction();
			session.save(hlInOut);
			//session.save(hlRoomTransaction.getHlApplicationForm());
			trans.commit();
			data="true";
		} catch (Exception e) {
			throw new ApplicationException(e);			
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return data;
	}
	
	public boolean checkStudentInOutForADay(StudentInoutTo studentInoutTo)throws Exception
	{
		boolean inOutTaken=false;
		log.info("checking checkStudentInOutForADay");
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			Query obj=session.createQuery("from HlInOut h where h.hlApplicationForm.id="+studentInoutTo.getAppFormId()+" and h.inTime like '"+CommonUtil.ConvertStringToSQLDate(studentInoutTo.getInTime())+" %' or h.outTime like '"+CommonUtil.ConvertStringToSQLDate(studentInoutTo.getOutTime())+" %'");
			List<HlInOut> hlInOut=obj.list();
			if(hlInOut.size()!=0)
			{
				inOutTaken=true;
			}
		}
		catch (Exception e) {
			log.error("Error during checkStudentInOutForADay..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return inOutTaken;
	}

}
