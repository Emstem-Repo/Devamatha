package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.reports.ISecondLanguageAbstractTxn;
import com.kp.cms.utilities.HibernateUtil;

public class SecondLanguageAbstractTxnImpl implements ISecondLanguageAbstractTxn {
	private static final Log log = LogFactory.getLog(SecondLanguageAbstractTxnImpl.class);
	public static volatile SecondLanguageAbstractTxnImpl secondLanguageAbstractTxnImpl = null;

	public static SecondLanguageAbstractTxnImpl getInstance() {
		if (secondLanguageAbstractTxnImpl == null) {
			secondLanguageAbstractTxnImpl = new SecondLanguageAbstractTxnImpl();
			return secondLanguageAbstractTxnImpl;
		}
		return secondLanguageAbstractTxnImpl;
	}

	/**
	 * getting class names with second language count
	 */
	public List<Object[]> getStudentsWithLanguageCount(int programTypeId, int semester, String language) throws Exception{
		log.debug("start getRequiredRegdNos");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			List<Object[]> regNoList = session.createQuery("select s.classSchemewise.classes.name, count(s.classSchemewise.classes.name) " +
					"from Student s where s.classSchemewise.curriculumSchemeDuration.semesterYearNo = " + semester +  
					" and s.admAppln.courseBySelectedCourseId.program.programType.id =  "+ programTypeId + 
					" and s.admAppln.personalData.secondLanguage = '" + language + "'" +
					" and s.isActive = 1" +
					" group by s.classSchemewise.classes.id").list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getRequiredRegdNos");
			return regNoList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}

			
}
