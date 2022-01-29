package com.kp.cms.transactionsimpl.admission;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.CertificateCourseCopyForm;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.transactions.admission.ICertificateCourseCopy;
import com.kp.cms.utilities.InitSessionFactory;

public class CertificateCourseCopyTxnImpl implements ICertificateCourseCopy {

	public static volatile CertificateCourseCopyTxnImpl certificateCourseCopyTxnImpl = null;
	private static final Log log = LogFactory
			.getLog(CertificateCourseCopyTxnImpl.class);

	public static CertificateCourseCopyTxnImpl getInstance() {
		if (certificateCourseCopyTxnImpl == null) {
			certificateCourseCopyTxnImpl = new CertificateCourseCopyTxnImpl();
			return certificateCourseCopyTxnImpl;
		}
		return certificateCourseCopyTxnImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICertificateCourseCopy#fromCertificateCourseCopy(int, java.lang.String)
	 */
	@Override
	public List<CertificateCourse> fromCertificateCourseCopy(int fromYear,
			String fromSemType) throws Exception {
		Session session = null;
		List<CertificateCourse> fromCourseBoList;
		try {
			session = InitSessionFactory.getInstance().openSession();
			fromCourseBoList = session
					.createQuery(
							"from CertificateCourse c where c.isActive = 1 and c.year="
									+ fromYear + " and c.semType='"
									+ fromSemType + "'").list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("End of fromCertificateCourses of impl");
		return fromCourseBoList;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICertificateCourseCopy#certificateCourseCopy(java.util.List)
	 */
	@Override
	public boolean certificateCourseCopy(
			List<CertificateCourse> certificateCoursesList) throws Exception {
		boolean flag = false;
		Session session = null;
		Transaction tx = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			tx=session.beginTransaction();
			Iterator<CertificateCourse> iterator = certificateCoursesList
					.iterator();
			while (iterator.hasNext()) {
				CertificateCourse course = iterator.next();
				session.merge(course);
			}
			tx.commit();
			flag = true;
			
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new BusinessException();

		}
		return flag;
	}

}
