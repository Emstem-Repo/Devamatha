package com.kp.cms.transactionsimpl.admission;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.InitSessionFactory;

public class ApplicationTxnImpl extends HttpServlet {

	public String getGeneratedOnlineAppNo(int courseID, int appliedyear,
			boolean isOnline, Student admBO) throws Exception {
		synchronized (this) {
		Session session = null;
		String generatedNo = null;
		Transaction txn=null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			
			txn=session.beginTransaction();
			// get the online range for course
			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			String onlineFrom = null;
			String onlineTo = null;
			int onlineStart = 0;
			int onlineEnd = 0;
			List<CourseApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<CourseApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					CourseApplicationNumber appNo = (CourseApplicationNumber) appItr
							.next();
					if (isOnline && appNo.getApplicationNumber() != null) {
						onlineFrom = appNo.getApplicationNumber()
								.getOnlineApplnNoFrom();
						onlineTo = appNo.getApplicationNumber()
								.getOnlineApplnNoTo();
						onlineStart = Integer.parseInt(onlineFrom);
						onlineEnd = Integer.parseInt(onlineTo);
					}
				}
			}
			// check max appno in range
			query = session
					.createQuery("select max(a.applnNo) from AdmAppln a where a.appliedYear=:year and a.applnNo BETWEEN :startRange AND :endRange");
			query.setInteger("year", appliedyear);
			// query.setInteger("courseID", courseID);
			query.setInteger("startRange", onlineStart);
			query.setInteger("endRange", onlineEnd);
			query.setReadOnly(true);
			Integer maxNo = (Integer) query.uniqueResult();
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
			} else {
				int generatedInt = 0;

				generatedInt = maxNo;
				generatedInt++;

				generatedNo = String.valueOf(generatedInt);
			}
			admBO.getAdmAppln().setApplnNo(Integer.parseInt(generatedNo));
			session.saveOrUpdate(admBO);
			txn.commit();
			session.flush();
			session.close();
			sessionFactory.close();
			
			return generatedNo;
		} catch (Exception e) {
			if (session.isOpen()){
				txn.rollback();
			session.flush();
			session.close();
			}
			throw new ApplicationException(e);
		}
		}
		
	}
	
}
