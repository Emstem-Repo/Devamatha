package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.management.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamAttendanceMarksBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.transactionsimpl.reports.ScoreSheetTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;

public class ExamAttendanceMarksImpl extends ExamGenImpl {
	private static final Log log = LogFactory.getLog(ExamAttendanceMarksImpl.class);

	public ExamAttendanceMarksBO loadRoomMaster(int id) throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session=HibernateUtil.getSession();
			ExamAttendanceMarksBO objBO = (ExamAttendanceMarksBO) session.get(
					ExamAttendanceMarksBO.class, id);
			session.flush();
			//session.close();
			return objBO != null ? objBO : null;
		} catch (Exception e) {
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isDuplicated(int id, int courseId,
			BigDecimal startPercInput, BigDecimal endPercInput,
			BigDecimal marksBD, String theoryPractical) throws Exception {
		boolean duplicate = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		BigDecimal toDB = new BigDecimal(0);
		try{
		Criteria crit = session.createCriteria(ExamAttendanceMarksBO.class);
		crit.add(Restrictions.eq("courseId", courseId));
		Object[] object=null;
		if(theoryPractical.equalsIgnoreCase("Theory") )
		{
		  object=new Object[]{"Theory and Practical","Theory"};
		  crit.add(Restrictions.in("theoryPractical", object));
		  
		}
		else if(theoryPractical.equalsIgnoreCase("Practical"))
		{
			object=new Object[]{"Theory and Practical","Practical"};
			crit.add(Restrictions.in("theoryPractical", object));
			crit.setFlushMode(FlushMode.AUTO);
		}
			
		List<ExamAttendanceMarksBO> list = crit.list();

		if (list.size() > 0) {
			Iterator<ExamAttendanceMarksBO> it = list.iterator();
			ExamAttendanceMarksBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamAttendanceMarksBO) it.next();
				BigDecimal fromDB = objbo.getFromPercentage();
				toDB = objbo.getToPercentage();
            
				
				if ((fromDB.doubleValue() <= startPercInput.doubleValue() && startPercInput
						.doubleValue() <= toDB.doubleValue())
						|| (fromDB.doubleValue() <= endPercInput.doubleValue() && (endPercInput
								.doubleValue() <= toDB.doubleValue()))) {
					if (id != objbo.getId()) {
						if (objbo.getIsActive() == true) {
							throw new DuplicateException();
						}
						}
					
				}
			}

		}
		return duplicate;
		}catch (DuplicateException e) {
			throw new DuplicateException();
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	public boolean update(int id, BigDecimal startPercBD, BigDecimal endPercBD,
			BigDecimal marksBD, String theoryPractical, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamAttendanceMarksBO objbo = (ExamAttendanceMarksBO) session.get(
					ExamAttendanceMarksBO.class, id);
			objbo.setFromPercentage(startPercBD);
			objbo.setToPercentage(endPercBD);
			objbo.setMarks(marksBD);
			if (theoryPractical.equalsIgnoreCase("Theory")) {
				objbo.setTheory(1);
				objbo.setPractical(0);
			} else if (theoryPractical.equalsIgnoreCase("Practical")) {
				objbo.setTheory(0);
				objbo.setPractical(1);
			} else {
				objbo.setTheory(1);
				objbo.setPractical(1);
			}
			objbo.setTheoryPractical(theoryPractical);
			objbo.setLastModifiedDate(new Date());
			objbo.setModifiedBy(userId);
			session.update(objbo);
			transaction.commit();
			session.flush();
//			session.close();
			return true;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		}
	}

