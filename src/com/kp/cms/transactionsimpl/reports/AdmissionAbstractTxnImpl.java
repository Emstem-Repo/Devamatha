package com.kp.cms.transactionsimpl.reports;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.AdmissionAbstractForm;
import com.kp.cms.transactions.reports.IAdmissionAbstractTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class AdmissionAbstractTxnImpl implements IAdmissionAbstractTransaction {
	public static final Log log = LogFactory.getLog(AdmissionAbstractTxnImpl.class);
	public static volatile AdmissionAbstractTxnImpl admissionAbstractTxnImpl = null;
	public static AdmissionAbstractTxnImpl getInstance() {
		if (admissionAbstractTxnImpl == null) {
			admissionAbstractTxnImpl = new AdmissionAbstractTxnImpl();
			return admissionAbstractTxnImpl;
		}
		return admissionAbstractTxnImpl;
	}
	/**
	 * 
	 * @param admForm
	 * @return
	 * @throws Exception
	 */
	public List<Student> getAdmittedStudents(AdmissionAbstractForm admForm) throws Exception {
		log.debug("inside getAdmittedStudents");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
	/*
			StringBuffer sqlString = new StringBuffer("select adm.course.id, adm.personalData.caste.id, " +
					" adm.personalData.caste.name, adm.personalData.residentCategory.id, adm.personalData.residentCategory.name,"+
					" adm.personalData.gender, adm.course.code, adm.course.program.code, adm.personalData.countryByCountryId.name," +
					" adm.course.program.id"+
					"  from AdmAppln adm " +
					" left join  adm.personalData.countryByCountryId countryByCountryId "+
					" where adm.isCancelled = 0  and " +
					" adm.appliedYear = :year and adm.course.program.programType.id = :programTypeId");
			*/
			
			StringBuffer sqlString = new StringBuffer("select st.admAppln.courseBySelectedCourseId.id," +
			"st.admAppln.personalData.caste.id," +
				 "st.admAppln.personalData.caste.name," +
				 "st.admAppln.personalData.residentCategory.id," +
				 "st.admAppln.personalData.residentCategory.name," +
								"st.admAppln.personalData.gender," +
								 "st.admAppln.courseBySelectedCourseId.code," +
								 "st.admAppln.courseBySelectedCourseId.program.code," +
								 "st.admAppln.personalData.countryByCountryId.name," +
								 "st.admAppln.courseBySelectedCourseId.program.id," +
								 "st.admAppln.personalData.nationality.id," +
								 "st.classSchemewise.curriculumSchemeDuration.semesterYearNo" +
								 " from Student st" +
								 " left join st.classSchemewise.curriculumSchemeDuration cur" +
								 " left join st.admAppln.personalData.countryByCountryId countryByCountryId" +
								 " where st.admAppln.isCancelled = 0" +
								 " and st.admAppln.appliedYear = :year" +
								 " and st.admAppln.courseBySelectedCourseId.program.programType.id = :programTypeId" +
								 " and cur.semesterYearNo = :semYear" );		
			
			
			
			if(admForm.getProgramId()!= null && !admForm.getProgramId().trim().isEmpty()){
				sqlString.append(" and st.admAppln.courseBySelectedCourseId.program.id = :programId");
			}
			
			sqlString.append(" order by st.admAppln.courseBySelectedCourseId.program.programType.name,st.admAppln.courseBySelectedCourseId.program.code, st.admAppln.courseBySelectedCourseId.code");
			Query query = session.createQuery(sqlString.toString());
			
			query.setString("programTypeId", admForm.getProgramTypeId());

			if(admForm.getProgramId()!= null && !admForm.getProgramId().trim().isEmpty()){
				query.setString("programId", admForm.getProgramId());
			}
			if(admForm.getYear()!= null && !admForm.getYear().isEmpty()){
				query.setInteger("year", Integer.parseInt(admForm.getYear()));
			}
			else
			{
				query.setInteger("year", 0);
			}

			query.setInteger("semYear", Integer.parseInt(admForm.getSemister()));
			
			
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
