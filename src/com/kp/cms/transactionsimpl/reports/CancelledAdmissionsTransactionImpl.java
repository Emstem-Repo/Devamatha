package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.CancelledAdmissionsForm;
import com.kp.cms.transactions.reports.ICancelledAdmissionsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class CancelledAdmissionsTransactionImpl implements ICancelledAdmissionsTransaction {
	private static final Log log = LogFactory.getLog(CancelledAdmissionsTransactionImpl.class);
	private static volatile CancelledAdmissionsTransactionImpl cancelledAdmissionsTransactionImpl;
	public static CancelledAdmissionsTransactionImpl getInstance() {
		if (cancelledAdmissionsTransactionImpl == null) {
			cancelledAdmissionsTransactionImpl = new CancelledAdmissionsTransactionImpl();
			return cancelledAdmissionsTransactionImpl;
		}
		return cancelledAdmissionsTransactionImpl;
	}
	/**
	 * retrieving cancelled details from student table 
	 */
	public List<Student> getCancelledAdmissions(CancelledAdmissionsForm cancelledAdmForm) throws Exception {
		log.debug("inside getCancelledAdmissions");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			StringBuffer sqlString = new StringBuffer("from Student s where s.admAppln.isCancelled = 1 and " +
					" admAppln.appliedYear = :year " );
			
			if(cancelledAdmForm.getProgramTypeId()!= null && !cancelledAdmForm.getProgramTypeId().trim().isEmpty()){
				sqlString.append(" and s.admAppln.courseBySelectedCourseId.program.programType.id = :programTypeId");
			}
			if(cancelledAdmForm.getProgramId()!= null && !cancelledAdmForm.getProgramId().trim().isEmpty()){
				sqlString.append(" and s.admAppln.courseBySelectedCourseId.program.id = :programId");
			}
			if(cancelledAdmForm.getCourseId()!= null && !cancelledAdmForm.getCourseId().trim().isEmpty()){
				sqlString.append(" and s.admAppln.courseBySelectedCourseId.id = :courseId");
			}
			if(cancelledAdmForm.getFromDate()!=null && !cancelledAdmForm.getFromDate().isEmpty()){
				sqlString.append(" and s.admAppln.cancelDate >='"+CommonUtil.ConvertStringToSQLDate(cancelledAdmForm.getFromDate())+"'");
			}
			if(cancelledAdmForm.getToDate()!=null && !cancelledAdmForm.getToDate().isEmpty()){
				sqlString.append(" and s.admAppln.cancelDate <='"+CommonUtil.ConvertStringToSQLDate(cancelledAdmForm.getToDate())+"'");
			}
			sqlString.append(" order by s.admAppln.personalData.firstName");
			Query query = session.createQuery(sqlString.toString());
			
			if(cancelledAdmForm.getProgramTypeId()!= null && !cancelledAdmForm.getProgramTypeId().trim().isEmpty()){
				query.setString("programTypeId", cancelledAdmForm.getProgramTypeId());
			}
			if(cancelledAdmForm.getProgramId()!= null && !cancelledAdmForm.getProgramId().trim().isEmpty()){
				query.setString("programId", cancelledAdmForm.getProgramId());
			}
			if(cancelledAdmForm.getYear()!= null && !cancelledAdmForm.getYear().isEmpty()){
				query.setInteger("year", Integer.parseInt(cancelledAdmForm.getYear()));
			}
			else
			{
				query.setInteger("year", 0);
			}
			if(cancelledAdmForm.getCourseId()!= null && !cancelledAdmForm.getCourseId().trim().isEmpty()){
				query.setString("courseId", cancelledAdmForm.getCourseId());
			}
			
			
			
			List<Student> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getCancelledAdmissions");
			return list;
		} catch (Exception e) {
			log.error("Error during getting getCancelledAdmissions..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

}
