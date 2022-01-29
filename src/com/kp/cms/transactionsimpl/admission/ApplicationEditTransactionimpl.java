package com.kp.cms.transactionsimpl.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePreferenceEntranceDetails;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.FeeCriteria;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Preferences;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.StudentCommonRank;
import com.kp.cms.bo.admin.StudentCourseAllotment;
import com.kp.cms.bo.admin.StudentCourseAllotmentPrev;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admin.StudentRank;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admin.StudentCourseAllotmentTo;
import com.kp.cms.transactions.admission.IApplicationEditTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.to.admin.AdmSubjectMarkForRankTO;

public class ApplicationEditTransactionimpl implements
		IApplicationEditTransaction {
	/**
	 * Singleton object of ApplicationEditTransactionimpl
	 */
	private static volatile ApplicationEditTransactionimpl applicationEditTransactionimpl= null;
	private static final Log log = LogFactory.getLog(ApplicationEditTransactionimpl.class);
	public ApplicationEditTransactionimpl() {
		
	}
	/**
	 * return singleton object of ApplicationEditTransactionimpl.
	 * @return
	 */
	public static ApplicationEditTransactionimpl getInstance() {
		if (applicationEditTransactionimpl == null) {
			applicationEditTransactionimpl = new ApplicationEditTransactionimpl();
		}
		return applicationEditTransactionimpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getApplicantDetails(java.lang.String, int)
	 */
	@Override
	public AdmAppln getApplicantDetails(String applicationNumber,int applicationYear) throws Exception {


		Session session = null;
		AdmAppln applicantDetails = null;
		int appNO = 0;
		if (StringUtils.isNumeric(applicationNumber))
			appNO = Integer.parseInt(applicationNumber);
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "from AdmAppln a where a.applnNo=:AppNO and a.appliedYear="
						+ applicationYear + "and a.isCancelled=0";
			Query qr = session.createQuery(sql);
			qr.setInteger("AppNO", appNO);
			applicantDetails = (AdmAppln) qr.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		return applicantDetails;
	
	}
	
	public List<AdmAppln> getApplicantDetail(ApplicationEditForm admForm) throws Exception {


		Session session = null;
		List<AdmAppln> applicantDetails = new ArrayList<AdmAppln>();
		int applicationYear=Integer.parseInt( admForm.getAcademicYear());
		int courseId= Integer.parseInt( admForm.getCourseId());
		
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "from AdmAppln a where  a.appliedYear="+applicationYear+" and a.isCancelled=0 and a.course.id="+courseId;
			Query qr = session.createQuery(sql);
			
			applicantDetails = qr.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				//session.flush();
				//session.close();
			}
		}
		return applicantDetails;
	
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForInstNatRes(int, int, int)
	 */
	@Override
	public int getAdmittedThroughForInstNatRes(int instId, int natId,int resId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  is null and " +
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForInstNationality(int, int)
	 */
	@Override
	public int getAdmittedThroughForInstNationality(int instId, int natId)	throws Exception {

		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id = " + natId + "and" +
					" f.university.id is null and " +
					" f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForInstNationalityUni(int, int, int)
	 */
	@Override
	public int getAdmittedThroughForInstNationalityUni(int instId, int natId,int uniId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id =" + natId + " and " +
					" f.university.id  =" + uniId + " and " + 
					" f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForInstNationalityUniRes(int, int, int, int)
	 */
	@Override
	public int getAdmittedThroughForInstNationalityUniRes(int instId,int natId, int uniId, int resId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  = " + uniId + " and " + 
					" f.residentCategory.id =" + resId);
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForInstRes(int, int)
	 */
	@Override
	public int getAdmittedThroughForInstRes(int instId, int resId)	throws Exception {

		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id is null and " +
					" f.university.id  is null and " + 
					" f.residentCategory.id =" + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForInstUni(int, int)
	 */
	@Override
	public int getAdmittedThroughForInstUni(int instId, int uniId)	throws Exception {

		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id is null and " +
					" f.university.id =" + uniId + " and " + 
					" f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstUni...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForNatRes(int, int)
	 */
	@Override
	public int getAdmittedThroughForNatRes(int natId, int resId)	throws Exception {

		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  is null and " +
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForNatUni(int, int)
	 */
	@Override
	public int getAdmittedThroughForNatUni(int natId, int uniId)	throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  = " + uniId + " and " +
					" f.residentCategory.id is null " );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForNatUniRes(int, int, int)
	 */
	@Override
	public int getAdmittedThroughForNatUniRes(int natId, int uniId,	int resId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  = " + uniId + " and " + 
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForResUni(int, int)
	 */
	@Override
	public int getAdmittedThroughForResUni(int resId, int uniId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id is null and " +
					" f.university.id  = " + uniId + " and " +
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughForUniResInst(int, int, int)
	 */
	@Override
	public int getAdmittedThroughForUniResInst(int uniId, int resId, int instId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id is null and " +
					" f.university.id  = " + uniId + " and " + 
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughIdForInst(int)
	 */
	@Override
	public int getAdmittedThroughIdForInst(int instId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.university.id is null and college.id = " + instId + " and nationality.id is null and residentCategory.id is null");
			
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughIdForInst...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	@Override
	public int getAdmittedThroughIdForNationality(int natId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.nationality.id="+natId + " and f.college.id is null and f.university.id is null and f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughIdForNationality...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughIdForUniveristy(int)
	 */
	@Override
	public int getAdmittedThroughIdForUniveristy(int uniId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.university.id="+uniId + " and college.id is null and nationality.id is null and residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughIdForUniveristy...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmittedThroughResidentCategory(int)
	 */
	@Override
	public int getAdmittedThroughResidentCategory(int resId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.residentCategory.id="+resId + " and f.college.id is null and f.nationality.id is null and f.university.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getExamtypes(int, java.lang.Integer)
	 */
	@Override
	public List<DocChecklist> getExamtypes(int courseId, Integer year)	throws Exception {
		List<DocChecklist> docchecklist = new ArrayList<DocChecklist>();
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DocChecklist d where d.course.id = :courseId and d.year=:year and d.isActive=1 and d.docType.isActive=1");
			query.setInteger("courseId", courseId);
			query.setInteger("year", year);
			List<DocChecklist> listofdocs = query.list();
			if (listofdocs != null && !listofdocs.isEmpty()) {
				Iterator<DocChecklist> itr = listofdocs.iterator();
				DocChecklist checkdocs;
				while (itr.hasNext()) {
					checkdocs = (DocChecklist) itr.next();
					if (checkdocs != null && checkdocs.getIsMarksCard()) {
						docchecklist.add(checkdocs);

					}
				}

			}

		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}

			log.error("Error during getting doc checklists...", e);
			throw new ApplicationException(e);
		}

		session.flush();
//		session.close();
		return docchecklist;
	}
	
	
	
	
	public List<DocChecklist> getDoctypes(int admId)	throws Exception {
		List<DocChecklist> docchecklist = new ArrayList<DocChecklist>();
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select d from DocChecklist d  inner join d.ednQualifications e inner join  e.personalData p inner join p.admApplns a where a.id="+admId);
			
			List<DocChecklist> listofdocs = query.list();
			if (listofdocs != null && !listofdocs.isEmpty()) {
				Iterator<DocChecklist> itr = listofdocs.iterator();
				DocChecklist checkdocs;
				while (itr.hasNext()) {
					checkdocs = (DocChecklist) itr.next();
					if (checkdocs != null && checkdocs.getIsMarksCard()) {
						docchecklist.add(checkdocs);

					}
				}

			}

		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}

			log.error("Error during getting doc checklists...", e);
			throw new ApplicationException(e);
		}

		session.flush();
//		session.close();
		return docchecklist;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getDocExamsByID(int)
	 */
	@Override
	public List<DocTypeExams> getDocExamsByID(int id) throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from DocTypeExams c where c.isActive=1 and c.docType.isActive=1 and c.docType.id= :docID");
			query.setInteger("docID", id);
			query.setReadOnly(true);
			List<DocTypeExams> list = query.list();

			session.flush();
//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}
	/*
	 * (non-Javadoc) get list of docs needed for course
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getNeededDocumentList(java.lang.String)
	 */
	@Override
	public List<DocChecklist> getNeededDocumentList(String courseID) throws Exception {
		Session session = null;

		List<DocChecklist> listofdocs = null;
		// check consolidated needed or not
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DocChecklist d where d.course.id = :courseId and d.isActive=1");
			query.setInteger("courseId", Integer.parseInt(courseID));
			listofdocs = query.list();

			session.flush();
//			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}
			log.error(
					"Error during getting needed documents for attachment...",
					e);
			throw new ApplicationException(e);
		}
		return listofdocs;
	}
	@Override
	public List<ExamCenter> getExamCenterForProgram(int programId) throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			

			Query query = session
					.createQuery("from ExamCenter e where e.isActive=1 and e.program.id= " + programId);
			List<ExamCenter> list = query.list();

			session.flush();
//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	@Override
	public List<Nationality> getNationalities() throws Exception {
		Session session = null;

		try {
/*			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Nationality n");
			query.setReadOnly(true);
			List<Nationality> list = query.list();

			session.flush();
//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting nationalities...", e);
			if(session!= null){
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
	}
	@Override
	public List<ResidentCategory> getResidentTypes() throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ResidentCategory c where c.isActive=1");
			List<ResidentCategory> list = query.list();

			session.flush();
//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting resident categories..." + e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}

	}
	@Override
	public List<Income> getIncomes() throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Income i where i.isActive=1 ");
			query.setFlushMode(FlushMode.COMMIT);
			List<Income> list = query.list();

			//session.flush();
//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting incomes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	@Override
	public List<Currency> getCurrencies() throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Currency c where c.isActive=1");
			query.setReadOnly(true);
			List<Currency> list = query.list();

			session.flush();
//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	@Override
	public List<Course> getCourseForPreference(String courseId)	throws Exception {
		Session session = null;
		Preferences preference = null;
		List<Course> courseList = new ArrayList<Course>();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" from Preferences c where courseByCourseId.id = :courseId and isActive = 1");
			query.setInteger("courseId", Integer.parseInt(courseId));
			query.setFlushMode(FlushMode.COMMIT);
			List<Preferences> list = query.list();
			
			// boolean isDefaultCourseAdded = false;
			//for (Iterator iterator = query.iterate(); iterator.hasNext();) {
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				preference = (Preferences) iterator.next();
				// if(!isDefaultCourseAdded){
				// Course prefCourse1=preference.getCourseByCourseId();
				// courseList.add(prefCourse1);
				// isDefaultCourseAdded = true;
				// }
				Course prefCourse = preference.getCourseByPrefCourseId();
				courseList.add(prefCourse);
			//}}
			}
			//session.flush();
//			session.close();
			return courseList;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			log.error("Error during getting program type for preference...", e);
			throw new ApplicationException(e);
		}
	}
	@Override
	public Set<Integer> getSubjectGroupByYearAndCourse(Integer appliedYear, int id) throws Exception {
		Session session = null;
		Transaction txn = null;
		List subjectGroupList=null;
		Set<Integer> setSubjectGroup=new HashSet<Integer>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q=(Query)session.createQuery("from CurriculumSchemeSubject c " +
					" join c.curriculumSchemeDuration c1 " +
					" join c1.curriculumScheme cs " +
					" where cs.course.id=:cid and cs.year=:year and c1.semesterYearNo=1");
			q.setInteger("cid",id);
			q.setInteger("year",appliedYear);
			subjectGroupList=q.list();
			if(subjectGroupList!=null){
			Iterator itr=subjectGroupList.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				if(object[0]!=null){
					CurriculumSchemeSubject c=(CurriculumSchemeSubject)object[0];
					if(c.getSubjectGroup()!=null && !setSubjectGroup.contains(c.getSubjectGroup().getId())){
						setSubjectGroup.add(c.getSubjectGroup().getId());
					}
				}
				
			}
			}
			session.flush();
			session.close();
		} catch (ConstraintViolationException e) {
			log.error("Error during saving complete application data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving complete application data...", e);
			throw new ApplicationException(e);
		}
		return setSubjectGroup;
	}
	@Override
	public String getInterviewDateOfApplicant(String applicationNumber,int year) throws Exception {
		Session session = null;
		String date=null;
		Date d=null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("select max(i.interview.date) from InterviewCard i where i.admAppln.applnNo="+applicationNumber+" and i.admAppln.appliedYear="+year );
			d =(Date) query.uniqueResult();
			if(d!=null){
				date=CommonUtil.ConvertStringToDateFormat(d.toString(), "yyyy-mm-dd", "dd/mm/yyyy");
			}
			session.flush();
			session.close();
			return date;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/*
	 * (non-Javadoc) updates edit data
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * updateCompleteApplication(com.kp.cms.bo.admin.AdmAppln, boolean)
	 */
	@Override
	public boolean updateCompleteApplication(AdmAppln admBO, boolean admissionForm) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
//			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			//raghu
			session.flush();
			session.saveOrUpdate(admBO);
/*			// if admission, set student admitted
			if (admissionForm) {

				if (admBO.getIsApproved()) {
					Integer maxNo = 1;
					Query query = session
							.createQuery("select max(student.programTypeSlNo)"
									+ " from Student student "
									+ " where student.admAppln.courseBySelectedCourseId.program.programType.id = :programTypeId "
									+ " and student.admAppln.appliedYear = :year");
					query.setInteger("programTypeId", admBO.getCourse()
							.getProgram().getProgramType().getId());
					query.setInteger("year", admBO.getAppliedYear().intValue());
					if (query.uniqueResult() != null
							&& query.uniqueResult() != "0") {
						maxNo = (Integer) query.uniqueResult();
						maxNo = maxNo + 1;
					}
					query = session
							.createQuery("update Student st set st.isAdmitted= :sele,st.isActive= :act, st.programTypeSlNo = :maxNo, st.isSCDataGenerated=:gene, st.isSCDataDelivered=:deli where st.admAppln.id= :admId");
					query.setBoolean("sele", true);
					query.setBoolean("act", true);
					query.setBoolean("gene", false);
					query.setBoolean("deli", false);
					query.setInteger("maxNo", maxNo.intValue());
					query.setInteger("admId", admBO.getId());
					query.executeUpdate();
					admBO.setIsSelected(true);
					session.update(admBO);
				} else {
					Query query = session
							.createQuery("update Student st set st.isAdmitted= :sele where st.admAppln.id= :admId");
					query.setBoolean("sele", false);
					query.setInteger("admId", admBO.getId());
					query.executeUpdate();
				}
			}*/
			txn.commit();
			session.clear();
			session.close();
//			sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during updating complete application data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during updating complete application data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
	public List<Object[]>  getInterviewSelectionScheduleOnline(int programId, int year)throws Exception{
		List<Object[]>  schedule=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createSQLQuery("select interview_selection_schedule.id, " +
											"interview_selection_schedule.venue_id, interview_selection_schedule.max_no_seats_online, "+
											"interview_selection_schedule.max_no_seats_offline,interview_selection_schedule.cut_off_date, "+
											"date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y'), "+
											"count(adm_appln.id) as total_applied, exam_center.center " +
											"from interview_selection_schedule "+
											"left join adm_appln on adm_appln.interview_schedule_id = interview_selection_schedule.id and adm_appln.mode='Online'"+
											"inner join exam_center on interview_selection_schedule.venue_id= exam_center.id "+
											"where interview_selection_schedule.program_id= "+programId +" and interview_selection_schedule.academic_year="+year
	                                        + " and interview_selection_schedule.cut_off_date>=curdate() and interview_selection_schedule.is_active=1 "+
                                            "group by interview_selection_schedule.id" );
					
				
			schedule = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return schedule;
	}
	
	public List<Object[]>  getInterviewSelectionScheduleOffline(int programId, int year)throws Exception{
		List<Object[]>  schedule=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createSQLQuery("select interview_selection_schedule.id, interview_selection_schedule.venue_id, interview_selection_schedule.max_no_seats_online, "+
											"interview_selection_schedule.max_no_seats_offline,interview_selection_schedule.cut_off_date, "+
											"date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y'), "+
											"count(adm_appln.id) as total_applied, exam_center.center " +
											"from interview_selection_schedule "+
											"left join adm_appln on adm_appln.interview_schedule_id = interview_selection_schedule.id and adm_appln.mode!='Online'"+
											"inner join exam_center on interview_selection_schedule.venue_id= exam_center.id "+
											"where interview_selection_schedule.program_id= "+programId +" and interview_selection_schedule.academic_year="+year
                                           + " and interview_selection_schedule.cut_off_date>=curdate() and interview_selection_schedule.is_active=1"+
                                            " group by interview_selection_schedule.id" );
					
				
			schedule = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	return schedule;
	}
	
	
	public List<InterviewSelectionSchedule> getInterviewSelectionScheduleByPgmId(int programId, int year)throws Exception{
		List<InterviewSelectionSchedule> schedule=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("select iss from InterviewSelectionSchedule iss "+
											"left join iss.programId p "+
											"where  iss.isActive=1 and p.isOpen=1 and p.id= "+ programId
											+" and p.isActive=1 and iss.academicYear= '"+year+"'");
			schedule = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	return schedule;
	}
	
	public Object[] getInterviewSelectionVenueForApplicant(ApplicationEditForm admForm) throws ApplicationException{
		Object[] appRecord=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("select a.interScheduleSelection.id,a.interScheduleSelection.selectionProcessDate, a.examCenter.id, a.examCenter.center from AdmAppln a where a.applnNo="+ admForm.getApplicationNumber());
			appRecord = (Object[]) query.uniqueResult();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	return appRecord;
	}
	
	public List<InterviewCard> getGeneratedCardDetails(String applnNo)	throws Exception {
		List<InterviewCard> interviewCard=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select c from InterviewCard c join c.admAppln a where a.isCancelled=0 and a.applnNo='" +applnNo+ "'");
			interviewCard=hqlQuery.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return interviewCard;


}
	
	
	
	@Override
	public boolean saveIndexMark(List<StudentIndexMark> markdList)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
//			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			txn.begin();
			Iterator<StudentIndexMark> itr=markdList.iterator();
			int i=1;
			while(itr.hasNext()){
			StudentIndexMark mark=(StudentIndexMark)itr.next() ;
			
			session.saveOrUpdate(mark);
			System.out.println(i++);
			}
			txn.commit();
//			sessionFactory.close();
			result = true;
		
		} catch (Exception e) {
			
			log.error("generate index mark impl..." + e);
			throw e;
		}finally{
			session.flush();
			session.close();
		}
		return result;
		
	}
	
	
	@Override
	public boolean saveRank(List<StudentRank> markdList)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
//			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			txn.begin();
			Iterator<StudentRank> itr=markdList.iterator();
			while(itr.hasNext()){
				StudentRank mark=(StudentRank)itr.next() ;
			
			session.saveOrUpdate(mark);
			
			}
			txn.commit();
//			sessionFactory.close();
			result = true;
		
		} catch (Exception e) {
			
			log.error("generate rank  impl..." + e);
			throw e;
		}finally{
			session.flush();
			session.close();
		}
		return result;
		
	}
	
	
	
	
	@Override
	public boolean generateCourseAllotment(List<StudentCourseAllotment> marksList)		throws Exception {
		// TODO Auto-generated method stub
		
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			if(marksList.size()!=0){
				
			
			txn.begin();
			Iterator<StudentCourseAllotment> itr=marksList.iterator();
			while(itr.hasNext()){
				StudentCourseAllotment mark=(StudentCourseAllotment)itr.next() ;
			    
				
			
			session.saveOrUpdate(mark);
			
			}
			txn.commit();
			
//			sessionFactory.close();
			result = true;
			}
		} catch (Exception e) {
			
			log.error("======================= generate course allotment impl...================" + e);
			throw e;
		}finally{
			session.flush();
			//session.clear();
			//session.close();
		}
		return result;
		
	}
	
	
	
	
	//raghu added newly
	@Override
	public Map<Integer,String> get12thclassSubject(String docName,String language) throws Exception{
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer,String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from AdmSubjectForRank a where a.stream=:docName and a.groupName!=:language");
			query.setString("docName",docName);
			query.setString("language",language);
			List<AdmSubjectForRank> list=query.list();
			if(list!=null){
				Iterator <AdmSubjectForRank> itr=list.iterator();
				while(itr.hasNext()){
					AdmSubjectForRank adm=itr.next();
					if(adm!=null && adm.getName()!=null && adm.getId()>0){
						
						map.put( adm.getId(), adm.getName());
					}
				}
			}
		}
		catch(Exception exception){
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		map=(Map<Integer,String>) CommonUtil.sortMapByValue(map);
		return map;
		
	}
	@Override
	public Map<Integer,String> get12thclassLangSubject(String docName,String language) throws Exception{
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer,String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from AdmSubjectForRank a where a.stream=:docName and a.groupName=:language");
			query.setString("docName",docName);
			query.setString("language",language);
			List<AdmSubjectForRank> list=query.list();
			if(list!=null){
				Iterator <AdmSubjectForRank> itr=list.iterator();
				while(itr.hasNext()){
					AdmSubjectForRank adm=itr.next();
					if(adm!=null && adm.getName()!=null && adm.getId()>0){
						
						map.put( adm.getId(), adm.getName());
					}
				}
			}
		}
		catch(Exception exception){
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		map=(Map<Integer,String>) CommonUtil.sortMapByValue(map);
		return map;
		
	}

	@Override
	public List<Course> getCourseForPreferencesbyug(String ugId)	throws Exception {
Session session = null;
//Preferences preference = null;
List<Course> courseList = new ArrayList<Course>();
try {
	//SessionFactory sessionFactory = InitSessionFactory.getInstance();
	//session = sessionFactory.openSession();
	session = HibernateUtil.getSession();
	Query query = session.createQuery(" from Course c where c.program.programType.id="+Integer.parseInt(ugId));
    
    courseList=query.list();
	
/*Commented By Manu,Iteration is not required,directly get list of CourseByPrefCourseId()			
	Query query = session.createQuery(" from Preferences c where courseByCourseId.id = :courseId and isActive = 1");
	query.setInteger("courseId", Integer.parseInt(courseId));
	// boolean isDefaultCourseAdded = false;
	for (Iterator iterator = query.iterate(); iterator.hasNext();) {
		preference = (Preferences) iterator.next();
		// if(!isDefaultCourseAdded){
		// Course prefCourse1=preference.getCourseByCourseId();
		// courseList.add(prefCourse1);
		// isDefaultCourseAdded = true;
		// }
		Course prefCourse = preference.getCourseByPrefCourseId();
		courseList.add(prefCourse);
	}
*/
	session.flush();
//	session.close();
	return courseList;
} catch (Exception e) {
	if (session != null) {
		session.flush();
	}
	log.error("Error during getting program type for preference...", e);
	throw new ApplicationException(e);
}
}
	
	@Override
	public List<StudentRank> getStudentRank(ApplicationEditForm admForm,int admId)	throws Exception {
		List<StudentRank> studentRank=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from StudentRank s where s.course.id="+admForm.getCourseId()+" and  s.prefNo="+admForm.getPreNo()+" and s.rank<="+admForm.getCutoffRank()+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+" and s.admAppln.id="+admId+" order by s.rank asc");
			studentRank=hqlQuery.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return studentRank;


}
	
	@Override
	public Map<Integer,Integer> getAllotedStudent(ApplicationEditForm admForm)	throws Exception {
		Map<Integer,Integer> studentRank=new LinkedHashMap<Integer, Integer>();
		List list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select s.admAppln.id,s.prefNo from StudentCourseAllotment s where  s.isAlloted=1  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			list=hqlQuery.list();
			
			
			if(list!=null){
				Iterator  itr=list.iterator();
				while(itr.hasNext()){
					Object obj[]=(Object[])itr.next();
					if(obj[0]!=null && obj[1]!=null){
						
						studentRank.put( Integer.parseInt(obj[0].toString()),Integer.parseInt( obj[1].toString()));
					}
				}
			}
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return studentRank;


}
	
	
	@Override
	public List<SeatAllocation> getSeatAllocation(ApplicationEditForm admForm)	throws Exception {
		List<SeatAllocation> studentRank=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from SeatAllocation s where s.noOfSeats!=0 and s.course.id="+Integer.parseInt(admForm.getCourseId()));
			studentRank=hqlQuery.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return studentRank;


}
	
	
	
	
	@Override
	public List getAllotedStudentsOnCourse(ApplicationEditForm admForm)	throws Exception {
		List list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from StudentCourseAllotment s where  s.isAlloted=1  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+" and s.admAppln.course.program.programType.id="+Integer.parseInt(admForm.getProgramTypeId()));
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


}
	
	
	
	@Override
	public List getIndexMarksOnCourse(ApplicationEditForm admForm)	throws Exception {
		List list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from StudentIndexMark s where  s.generateCourseId="+Integer.parseInt(admForm.getCourseId())+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


}
	
	
	
	@Override
	public List getRanksOnCourse(ApplicationEditForm admForm) throws Exception {
		List list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from StudentRank s where  s.course.id="+Integer.parseInt(admForm.getCourseId())+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


}
	
	

	@Override
	public List getSeatCountOnCourse(String quata,String casteName,int courseid,ApplicationEditForm admForm) throws Exception {
		List list=null;
		Session session = null;
		Query hqlQuery=null;
		try{
			session = HibernateUtil.getSession();
			if(quata.equalsIgnoreCase("General")){
				
				hqlQuery = session.createQuery("from StudentCourseAllotment s where s.course.id=:courseId and s.isGeneral=1  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
				hqlQuery.setInteger("courseId",courseid);
				
			}else{
				hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.name=:casteName and s.course.id=:courseId and s.isCaste=1  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
				hqlQuery.setInteger("courseId",courseid);
				hqlQuery.setString("casteName",casteName);
			}
			
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


}
	
	
	
	
	@Override
	public StudentRank getRankOnAdmApplPreference(int admId,int preNo) throws Exception {
		
		Session session = null;
		StudentRank studentRank=null;
		List list=null;
		try{
			session = HibernateUtil.getSession();
			String s="from StudentRank s where s.admAppln.id="+admId;
			if(preNo!=0){
				s=s+" and s.prefNo="+preNo;
			}
			Query hqlQuery = session.createQuery(s);
					
			//studentRank=(StudentRank)hqlQuery.uniqueResult();
			list=hqlQuery.list();
			if(list!=null && list.size()!=0){
				studentRank=(StudentRank)list.get(0);
			}
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return studentRank;


}
	
	
	
	@Override
	public List getAdmApplonStudentIndexMark(ApplicationEditForm admForm) throws Exception {
		
		Session session = null;
		List list=null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select distinct s.admAppln,max(s.indexMark) from StudentIndexMark s  where s.remark='Eligible' and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+"group by s.indexMark order by s.indexMark desc");
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


	}
	
	
	@Override
	public List getAllotedStudentsOnCourseCheck(ApplicationEditForm admForm)	throws Exception {
		List list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from StudentCourseAllotment s where  s.isAssigned=1 or  s.isCancel=1 and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


}
	
	
	@Override
	public List<StudentCourseAllotment> getStudentDetails(ApplicationEditForm admForm) throws Exception {
		Session session = null;
		List<StudentCourseAllotment> list=null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from StudentCourseAllotment s where s.isAssigned=0 and s.isCancel=0 and s.isAlloted=1  and s.course.id="+admForm.getCourseId()+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+" order by s.rank");
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	
	
	
	@Override
	public boolean assignStudentsToCourse(ApplicationEditForm admForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			List<StudentCourseAllotmentTo> list = admForm.getStudentList();
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<StudentCourseAllotmentTo> itr = list.iterator();
				
					int count = 0;
					
					while (itr.hasNext()) {
						
						StudentCourseAllotmentTo  allotmentTo = (StudentCourseAllotmentTo) itr.next();
					
					if (allotmentTo.getChecked()!=null && allotmentTo.getChecked().equalsIgnoreCase("on")) {
						
						StudentCourseAllotment courseAllotment=(StudentCourseAllotment)session.get(StudentCourseAllotment.class, allotmentTo.getId()); 
						courseAllotment.setLastModifiedDate(new java.util.Date());
						courseAllotment.setModifiedBy(admForm.getUserId());
						courseAllotment.setIsAssigned(true);
						courseAllotment.setIsSatisfied(allotmentTo.getIsSatisfied());
						
						AdmAppln appln=(AdmAppln)session.get(AdmAppln.class, allotmentTo.getAdmApplnTO().getId()); 
						appln.setLastModifiedDate(new java.util.Date());
						appln.setModifiedBy(admForm.getUserId());
						
						Course course=new Course();
						course.setId(allotmentTo.getCourseTO().getId());
						appln.setCourse(course);
						
						Course selectcourse=new Course();
						selectcourse.setId(allotmentTo.getCourseTO().getId());
						appln.setCourseBySelectedCourseId(selectcourse);
						
						
						transaction.begin();
						session.update(appln);	
						transaction.commit();
						
						transaction.begin();
						session.update(courseAllotment);	
						transaction.commit();
						
						//if (++count % 20 == 0) {
						//session.flush();
						//session.clear();
						//}
						isAdded = true;
					}
					
				}
					
			}
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;
		
	}
	
	
	
	@Override
	public Integer getAllotedNoOnCourse(ApplicationEditForm admForm)	throws Exception {
		Integer allotedNo=0;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select max(s.allotmentNo) from StudentCourseAllotment s where  s.isAlloted=1  and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+" and s.admAppln.course.program.programType.id="+Integer.parseInt(admForm.getProgramTypeId()));
			allotedNo=(Integer)hqlQuery.uniqueResult();
			
				
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return allotedNo;


}
	
	
	@Override
	public List getAdmApplonStudentIndexMarkMultipleTimes(ApplicationEditForm admForm) throws Exception {
		
		Session session = null;
		List list=null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select distinct s.admAppln,max(s.indexMark) from StudentIndexMark s  where s.remark='Eligible' " +
					"and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  s1.isAlloted=1 and ((s1.prefNo=1) or (s1.isCancel=1 and s1.prefNo!=1)) and s1.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+")"+
					" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+" group by s.admAppln.id order by s.indexMark desc");
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


	}
	
	
	@Override
	public List getSeatCountOnCourseMultipleTime(String quata,String casteName,int courseid,ApplicationEditForm admForm) throws Exception {
		List list=null;
		Session session = null;
		Query hqlQuery=null;
		try{
			session = HibernateUtil.getSession();
			if(quata.equalsIgnoreCase("General")){
				
				hqlQuery = session.createQuery("from StudentCourseAllotment s where s.course.id=:courseId and s.isGeneral=1 and s.isAssigned=1 and s.isCancel=0   and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
				hqlQuery.setInteger("courseId",courseid);
				
			}else{
				hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.name=:casteName and s.course.id=:courseId and s.isCaste=1 and s.isAssigned=1 and s.isCancel=0 and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
				hqlQuery.setInteger("courseId",courseid);
				hqlQuery.setString("casteName",casteName);
			}
			
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


}
	
	@Override
	public List getSeatCountOnCourseAllotedNo(String quata,String casteName,int courseid,ApplicationEditForm admForm) throws Exception {
		List list=null;
		Session session = null;
		Query hqlQuery=null;
		try{
			session = HibernateUtil.getSession();
			if(quata.equalsIgnoreCase("General")){
				
				hqlQuery = session.createQuery("from StudentCourseAllotment s where s.course.id=:courseId and s.isGeneral=1 and s.allotmentNo="+(admForm.getAllotedNo()+1)+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
				hqlQuery.setInteger("courseId",courseid);
				
			}else{
				hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.personalData.religionSection.name=:casteName and s.course.id=:courseId and s.isCaste=1 and s.allotmentNo="+(admForm.getAllotedNo()+1)+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
				hqlQuery.setInteger("courseId",courseid);
				hqlQuery.setString("casteName",casteName);
			}
			
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


}
	

	
	
	@Override
	public Map<Integer,Integer> getAllotedStudentMultipleTime(ApplicationEditForm admForm)	throws Exception {
		Map<Integer,Integer> studentRank=new LinkedHashMap<Integer, Integer>();
		List list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select s.admAppln.id,s.prefNo from StudentCourseAllotment s where  s.isAlloted=1  and s.allotmentNo="+(admForm.getAllotedNo()+1)+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			list=hqlQuery.list();
			
			
			if(list!=null){
				Iterator  itr=list.iterator();
				while(itr.hasNext()){
					Object obj[]=(Object[])itr.next();
					if(obj[0]!=null && obj[1]!=null){
						
						studentRank.put( Integer.parseInt(obj[0].toString()),Integer.parseInt( obj[1].toString()));
					}
				}
			}
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return studentRank;


}
	
	
	
	@Override
	public List<StudentCourseAllotment> getStudentDetailsMultipleTime(ApplicationEditForm admForm) throws Exception {
		Session session = null;
		List<StudentCourseAllotment> list=null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from StudentCourseAllotment s where s.isAssigned=0 and s.isCancel=0  and s.isAlloted=1  and s.course.id="+admForm.getCourseId()+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+" and s.allotmentNo="+(admForm.getAllotedNo())+" order by s.rank");
			list=hqlQuery.list();
			
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	
	
	@Override
	public List<StudentRank> getStudentRankMultipleTime(ApplicationEditForm admForm,int admId)	throws Exception {
		List<StudentRank> studentRank=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			//Query hqlQuery = session.createQuery("from StudentRank s where s.course.id="+admForm.getCourseId()+" and  s.prefNo="+admForm.getPreNo()+" and s.rank<="+admForm.getCutoffRank()+" and s.admAppln.id="+admId+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+" and s.admAppln.id not in(select distinct s1.admAppln.id from StudentCourseAllotment s1 where  s1.isAlloted=1 and s1.prefNo=1 and s1.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+") order by s.rank asc");
			Query hqlQuery = session.createQuery("from StudentRank s where s.course.id="+admForm.getCourseId()+" and  s.prefNo="+admForm.getPreNo()+" and s.rank<="+admForm.getCutoffRank()+" and s.admAppln.id="+admId+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+"  order by s.rank asc");
			
			studentRank=hqlQuery.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return studentRank;


}
	
	
	@Override
	public boolean assignStudentsToCourseMultipleTime(ApplicationEditForm admForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			List<StudentCourseAllotmentTo> list = admForm.getStudentList();
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<StudentCourseAllotmentTo> itr = list.iterator();
				
					int count = 0;
					
					while (itr.hasNext()) {
						
						StudentCourseAllotmentTo  allotmentTo = (StudentCourseAllotmentTo) itr.next();
					
					if (allotmentTo.getChecked()!=null && allotmentTo.getChecked().equalsIgnoreCase("on")) {
						
						StudentCourseAllotment courseAllotment=(StudentCourseAllotment)session.get(StudentCourseAllotment.class, allotmentTo.getId()); 
						courseAllotment.setLastModifiedDate(new java.util.Date());
						courseAllotment.setModifiedBy(admForm.getUserId());
						courseAllotment.setIsAssigned(true);
						courseAllotment.setIsSatisfied(allotmentTo.getIsSatisfied());
						
						AdmAppln appln=(AdmAppln)session.get(AdmAppln.class, allotmentTo.getAdmApplnTO().getId()); 
						appln.setLastModifiedDate(new java.util.Date());
						appln.setModifiedBy(admForm.getUserId());
						
						Course course=new Course();
						course.setId(allotmentTo.getCourseTO().getId());
						appln.setCourse(course);
						
						Course selectcourse=new Course();
						selectcourse.setId(allotmentTo.getCourseTO().getId());
						appln.setCourseBySelectedCourseId(selectcourse);
						
						
						transaction.begin();
						session.update(appln);	
						transaction.commit();
						
						transaction.begin();
						session.update(courseAllotment);	
						transaction.commit();
						
						
						Query hqlQuery = session.createQuery("from StudentCourseAllotment s where  s.allotmentNo="+(admForm.getAllotedNo()-1)+" and s.admAppln.id="+appln.getId()+"  order by s.rank asc");
						
						List l=hqlQuery.list();
						
						if(l.size()!=0){
							Iterator i=l.iterator();
							while(i.hasNext()){
								StudentCourseAllotment  allot = (StudentCourseAllotment) i.next();

								allot.setIsCancel(true);
								allot.setIsAssigned(false);
								transaction.begin();
								session.update(allot);
								transaction.commit();
								
							}
						}
						
						//if (++count % 20 == 0) {
						//session.flush();
						//session.clear();
						//}
						isAdded = true;
					}
					
				}
					
			}
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;
		
	}
	
	
	
	@Override
	public List<StudentRank> getAdmApplonStudentOnRankPreference(ApplicationEditForm admForm,int pref,int srank) throws Exception {
		
		Session session = null;
		List list=null;
		try{
			session = HibernateUtil.getSession();
			String s="from StudentRank s where  s.prefNo="+pref+" and s.rank="+srank+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+"  order by s.rank asc";
			
			//for testing course wise
			//s="from StudentRank s where  s.prefNo="+pref+" and s.rank="+srank+" and s.course.id=12 and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+"  order by s.rank asc";
			
			Query hqlQuery = session.createQuery(s);
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


	}

	
	
	
	@Override
	public List<StudentRank> getAdmApplonStudentOnRankPreferenceMultiple(ApplicationEditForm admForm,int pref,int srank) throws Exception {
		
		Session session = null;
		List list=null;
		String allotNos="";
		
		/*if((admForm.getAllotedNo()+1)==1){
			allotNos=admForm.getAllotedNo().toString();
		}else{
			for(int i=1;i<admForm.getAllotedNo()+1;i++){
				allotNos=allotNos+i+",";
			}
			allotNos=allotNos+admForm.getAllotedNo()+1;
		}
		*/
		try{
			session = HibernateUtil.getSession();
			
			String s="from StudentRank s where  s.prefNo="+pref+" and s.rank="+srank+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+
					" and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  s1.isAlloted=1 and s1.prefNo=1  and s1.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+") order by s.rank asc";
			//this is testing for single course
					//s="from StudentRank s where  s.prefNo="+pref+" and s.rank="+srank+" and s.course.id=12 and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+
					//" and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  s1.isAlloted=1 and ((s1.prefNo=1) or (s1.isCancel=1 and s1.prefNo!=1)) and s1.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+") order by s.rank asc";
			Query hqlQuery = session.createQuery(s);
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


	}
	
	
	@Override
	public Integer getMaxRank(ApplicationEditForm admForm) throws Exception {
		
		Session session = null;
		Integer i=0;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select max(s.rank) from StudentRank s where s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			i=(Integer)hqlQuery.uniqueResult();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return i;


	}

	
	@Override
	public List<CandidatePreference> getStudentDetailsForExam(ApplicationEditForm admForm) throws Exception {
		Session session = null;
		List<CandidatePreference> list=null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from CandidatePreference s where  s.course.id="+admForm.getCourseId()+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+
			" and s.admAppln.id not in(select p.admAppln.id from CandidatePreferenceEntranceDetails p where  p.course.id="+admForm.getCourseId()+" and p.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear())+")");
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	
	@Override
	public boolean assignStudentsForExam(ApplicationEditForm admForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			List<StudentCourseAllotmentTo> list = admForm.getStudentList();
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<StudentCourseAllotmentTo> itr = list.iterator();
				
					int count = 0;
					
					while (itr.hasNext()) {
						
						StudentCourseAllotmentTo  allotmentTo = (StudentCourseAllotmentTo) itr.next();
					
					if (allotmentTo.getChecked()!=null && allotmentTo.getChecked().equalsIgnoreCase("on")) {
						
						CandidatePreferenceEntranceDetails entranceDetails=new CandidatePreferenceEntranceDetails(); 
						//courseAllotment.setLastModifiedDate(new java.util.Date());
						//courseAllotment.setModifiedBy(admForm.getUserId());
						entranceDetails.setYearPassing(Integer.parseInt(admForm.getAcademicYear()));
						entranceDetails.setPrefNo(allotmentTo.getPrefNo());
						
						
						AdmAppln appln=new AdmAppln(); 
						appln.setId(allotmentTo.getAdmApplnTO().getId());
						entranceDetails.setAdmAppln(appln);
						
						Course course=new Course();
						course.setId(allotmentTo.getCourseTO().getId());
						entranceDetails.setCourse(course);
						
						transaction.begin();
						session.save(entranceDetails);	
						transaction.commit();
						
						//if (++count % 20 == 0) {
						//session.flush();
						//session.clear();
						//}
						isAdded = true;
					}
					
				}
					
			}
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;
		
	}
	
	
	@Override
	public List<CandidatePreferenceEntranceDetails> getStudentDetailsForExamMarks(ApplicationEditForm admForm) throws Exception {
		Session session = null;
		List<CandidatePreferenceEntranceDetails> list=null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from CandidatePreferenceEntranceDetails p where  p.course.id="+admForm.getCourseId()+" and p.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	
	@Override
	public boolean assignStudentsForExamMarks(ApplicationEditForm admForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			List<CandidatePreferenceEntranceDetailsTO> list = admForm.getPreferenceEntranceDetailsList();
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<CandidatePreferenceEntranceDetailsTO> itr = list.iterator();
				
					int count = 0;
					
					while (itr.hasNext()) {
						
						CandidatePreferenceEntranceDetailsTO  allotmentTo = (CandidatePreferenceEntranceDetailsTO) itr.next();
					
						
						CandidatePreferenceEntranceDetails entranceDetails=new CandidatePreferenceEntranceDetails(); 
						entranceDetails.setId(allotmentTo.getId());
						//courseAllotment.setLastModifiedDate(new java.util.Date());
						//courseAllotment.setModifiedBy(admForm.getUserId());
						if(allotmentTo.getMarksObtained()!=null & !allotmentTo.getMarksObtained().equalsIgnoreCase(""))
						entranceDetails.setMarksObtained(new BigDecimal(allotmentTo.getMarksObtained()));
						if(allotmentTo.getTotalMarks()!=null & !allotmentTo.getTotalMarks().equalsIgnoreCase(""))
						entranceDetails.setTotalMarks(new BigDecimal(allotmentTo.getTotalMarks()));
						entranceDetails.setYearPassing(Integer.parseInt(admForm.getAcademicYear()));
						entranceDetails.setPrefNo(allotmentTo.getPrefNo());
						
						AdmAppln appln=new AdmAppln(); 
						appln.setId(allotmentTo.getAdmApplnTO().getId());
						entranceDetails.setAdmAppln(appln);
						
						Course course=new Course();
						course.setId(allotmentTo.getCourseTO().getId());
						entranceDetails.setCourse(course);
						
						transaction.begin();
						session.update(entranceDetails);	
						transaction.commit();
						
						//if (++count % 20 == 0) {
						//session.flush();
						//session.clear();
						//}
						isAdded = true;
					}
					
				}
					
			
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;
		
	}
	

@Override
	public List<AdmSubjectMarkForRank> getAdmSubjectMarkList(int id)
			throws Exception {
		// TODO Auto-generated method stub
		
		Session session = null;
		List<AdmSubjectMarkForRank> list=new LinkedList<AdmSubjectMarkForRank>();
		AdmSubjectMarkForRank admSubjectMarkForRank=null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from AdmSubjectMarkForRank s where ednQualification.id="+id+" and s.admSubjectForRank.groupName='Language'");
		List list1=hqlQuery.list();
			Query hqlQuery1 = session.createQuery("from AdmSubjectMarkForRank s where ednQualification.id="+id+" and s.admSubjectForRank.groupName!='Language'");
			List list2=hqlQuery1.list();
			if(list1.size()!=0){
				Iterator<AdmSubjectMarkForRank> itr=list1.iterator();
				while(itr.hasNext()){
					admSubjectMarkForRank=(AdmSubjectMarkForRank) itr.next();
					list.add(admSubjectMarkForRank);
				}
			}
           if(list2.size()!=0){
        	   Iterator<AdmSubjectMarkForRank> itr1=list2.iterator();
				while(itr1.hasNext()){
					admSubjectMarkForRank=(AdmSubjectMarkForRank) itr1.next();
					list.add(admSubjectMarkForRank);
				}
			}
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}finally{
			//session.close();
			
		}
		return list;

		
		
	}
	@Override
	public List<AdmSubjectMarkForRank> getAdmList(int id) throws Exception {
		// TODO Auto-generated method stub
		List<AdmSubjectMarkForRank> admList=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from AdmSubjectMarkForRank s where s.ednQualification.id="+id);
			
			admList=hqlQuery.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return admList;
		
		
	}
	


	@Override
	      public CandidatePreferenceEntranceDetails getEntranceDetails(int id, int id2)
	                  throws Exception {
	            // TODO Auto-generated method stub
	            Session session = null;
	            CandidatePreferenceEntranceDetails candidatePreferenceEntranceDetails=null;
	            List list=null;
	            try{
	                  session = HibernateUtil.getSession();
	                  String s="from CandidatePreferenceEntranceDetails s where s.admAppln.id="+id2+" and s.course.id="+id;
	                  
	                  Query hqlQuery = session.createQuery(s);
	                              
	                  
	                  list=hqlQuery.list();
	                  if(list!=null && list.size()!=0){
	                        candidatePreferenceEntranceDetails=(CandidatePreferenceEntranceDetails)list.get(0);
	                  }
	                  
	            }catch (Exception e) {
	                  if (session != null) {
	                        session.flush();
	                  }
	                  throw new ApplicationException(e);
	            }
	            return candidatePreferenceEntranceDetails;

	            
	      }


	@Override
	public Map<String,String> get12thclassGroupSubject(String language) throws Exception{
		Session session=null;
		Map<String,String> map=new HashMap<String,String>();
		try{
			session=HibernateUtil.getSession();
			Query query=null;
			if(!language.equalsIgnoreCase("")){
				query=session.createQuery("from AdmSubjectForRank where isActive=1 and stream='Class 12' and groupName='Language'");
				
			}else{
				query=session.createQuery("from AdmSubjectForRank where isActive=1 and stream='Class 12' and groupName!='Language'");
				
			}
			
			List<AdmSubjectForRank> list=query.list();
			if(list!=null){
				Iterator <AdmSubjectForRank> itr=list.iterator();
				while(itr.hasNext()){
					AdmSubjectForRank adm=itr.next();
					if(adm!=null && adm.getName()!=null && adm.getId()>0){
						
						map.put( adm.getName(), adm.getGroupName());
					}
				}
			}
		}
		catch(Exception exception){
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		//map=(Map<String,String>) CommonUtil.sortMapByValue(map);
		return map;
		
	}


	@Override
	public Map<Integer,Integer> getAllotedStudentMultipleTimeOnRank(ApplicationEditForm admForm,int pre,int rank)	throws Exception {
		Map<Integer,Integer> studentRank=new LinkedHashMap<Integer, Integer>();
		List list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			//Query hqlQuery = session.createQuery("select s.admAppln.id,s.prefNo from StudentCourseAllotment s where  s.isAlloted=1  and s.allotmentNo="+(admForm.getAllotedNo()+1)+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			Query hqlQuery = session.createQuery("select s.admAppln.id,s.prefNo from StudentCourseAllotment s where  s.isAlloted=1  and (s.allotmentNo="+(admForm.getAllotedNo()+1)+"  or (s.prefNo="+pre+" and s.rank="+rank+")) and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			
			list=hqlQuery.list();
			
			
			if(list!=null){
				Iterator  itr=list.iterator();
				while(itr.hasNext()){
					Object obj[]=(Object[])itr.next();
					if(obj[0]!=null && obj[1]!=null){
						
						studentRank.put( Integer.parseInt(obj[0].toString()),Integer.parseInt( obj[1].toString()));
					}
				}
			}
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return studentRank;


}
	
	@Override
	public List<EdnQualification> getAdmSubList(ApplicationEditForm form) throws Exception {
		// TODO Auto-generated method stub
		List<EdnQualification> admList=null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select distinct edn from AdmSubjectMarkForRank admsub  inner join admsub.ednQualification edn inner join edn.personalData per inner join per.admApplns adm where adm.appliedYear="+Integer.parseInt(form.getAcademicYear())+" and adm.course="+Integer.parseInt(form.getCourseId()));
			
			admList=hqlQuery.list();
			
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return admList;
		
		
	}
	
	
	@Override
	public boolean saveGroupMark(List<StudentCommonRank> markdList)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
//			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			txn.begin();
			Iterator<StudentCommonRank> itr=markdList.iterator();
			//int i=1;
			while(itr.hasNext()){
				StudentCommonRank mark=(StudentCommonRank)itr.next() ;
			
			session.saveOrUpdate(mark);
			//System.out.println(i++);
			}
			txn.commit();
//			sessionFactory.close();
			result = true;
		
		} catch (Exception e) {
			
			log.error("generate index mark impl..." + e);
			throw e;
		}finally{
			session.flush();
			session.close();
		}
		return result;
		
	}
	
	
	@Override
	public List getGroupMarksOnCourse(ApplicationEditForm admForm)	throws Exception {
		List list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from StudentCommonRank s where  s.course.id="+Integer.parseInt(admForm.getCourseId())+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


	}

	
	
	
	// get student rank object list based on course and general or caste 
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmApplonStudentOnCourseCategory(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<StudentRank> getAdmApplonStudentOnCourseCategory(Integer courseId,Integer year,String category) throws Exception 
	{
		
		Session session = null;
		List list=null;
		try{
			
			
			
			Properties prop = new Properties();
			try 
			{
				InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("resources/application.properties");
				prop.load(in);
			} 
			catch (FileNotFoundException e)
			{
				log.error("Unable to read properties file...", e);

			} 
			catch (IOException e)
			{
				log.error("Unable to read properties file...", e);

			}
			
			//sc
			Integer scId=0;	
			String sc = prop.getProperty ("knowledgepro.admission.religionsection.sc");
			if(sc !=null && ! StringUtils.isEmpty(sc)){ 
				scId=Integer.parseInt(sc);
			}
			
			//ezhava_thiyya_billava
			Integer ezhava_thiyya_billava_Id=0;	
			String ezhava_thiyya_billava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava_thiyya_billava");
			if(ezhava_thiyya_billava !=null && ! StringUtils.isEmpty(ezhava_thiyya_billava)){ 
				ezhava_thiyya_billava_Id=Integer.parseInt(ezhava_thiyya_billava);
			}
			
			//mu
			Integer muId=0;	
			String mu = prop.getProperty ("knowledgepro.admission.religionsection.mu");
			if(mu !=null && ! StringUtils.isEmpty(mu)){ 
				muId=Integer.parseInt(mu);
			}
			
			//st
			Integer stId=0;	
			String st = prop.getProperty ("knowledgepro.admission.religionsection.st");
			if(st !=null && ! StringUtils.isEmpty(st)){ 
				stId=Integer.parseInt(st);
			}
			
			//lc
			Integer lcId=0;	
			String lc = prop.getProperty ("knowledgepro.admission.religionsection.lc");
			if(lc !=null && ! StringUtils.isEmpty(lc)){ 
				lcId=Integer.parseInt(lc);
			}
			
			//lc
			Integer lctaId=0;	
			String lcta = prop.getProperty ("knowledgepro.admission.religionsection.lcta");
			if(lcta !=null && ! StringUtils.isEmpty(lcta)){ 
				lctaId=Integer.parseInt(lcta);
			}
			
			//obh
			Integer obhId=0;	
			String obh = prop.getProperty ("knowledgepro.admission.religionsection.obh");
			if(obh !=null && ! StringUtils.isEmpty(obh)){ 
				obhId=Integer.parseInt(obh);
			}
			
			//obx
			Integer obxId=0;	
			String obx = prop.getProperty ("knowledgepro.admission.religionsection.obx");
			if(obx !=null && ! StringUtils.isEmpty(obx)){ 
				obxId=Integer.parseInt(obx);
			}
			
			//ezhava
			Integer ezhavaId=0;	
			String ezhava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava");
			if(ezhava !=null && ! StringUtils.isEmpty(ezhava)){ 
				ezhavaId=Integer.parseInt(ezhava);
			}
			

			//billava
			Integer billavaId=0;	
			String billava = prop.getProperty ("knowledgepro.admission.religionsection.billava");
			if(billava !=null && ! StringUtils.isEmpty(billava)){ 
				billavaId=Integer.parseInt(billava);
			}
			
			//thiyya
			Integer thiyyaId=0;	
			String thiyya = prop.getProperty ("knowledgepro.admission.religionsection.thiyya");
			if(thiyya !=null && ! StringUtils.isEmpty(thiyya)){ 
				thiyyaId=Integer.parseInt(thiyya);
			}
			
			//community
			Integer communityId=0;	
			String community = prop.getProperty ("knowledgepro.admission.religionsection.community");
			if(community !=null && ! StringUtils.isEmpty(community)){ 
				communityId=Integer.parseInt(community);
			}
			
			
			
			
			session = HibernateUtil.getSession();
			String s="";
			
			//general
			if(category.equalsIgnoreCase("GENERAL"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+"  order by s.rank asc";		
			}
			
			//sc
			else if(category.equalsIgnoreCase("SC"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+scId+" order by s.rank asc";
			}
			
			//st
			else if(category.equalsIgnoreCase("ST"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+stId+" order by s.rank asc";
			}
			
			//lc
			else if(category.equalsIgnoreCase("LC"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and (s.admAppln.personalData.religionSection.id ="+lcId+" or s.admAppln.personalData.religionSection.id ="+lctaId+") order by s.rank asc";
			}
			// BC mens EZHAVA,THIYYA,BILLAVA
			/*else if(category.equalsIgnoreCase("BC"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and (s.admAppln.personalData.religionSection.id ="+ezhavaId+" or s.admAppln.personalData.religionSection.id ="+thiyyaId+" or s.admAppln.personalData.religionSection.id ="+billavaId+") order by s.rank asc";
			}*/
			
			//mu
			else if(category.equalsIgnoreCase("MU"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+muId+" order by s.rank asc";
			}
			
			//community
			
			else if(category.equalsIgnoreCase("COMMUNITY"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+communityId+" order by s.rank asc";
			}
			
			//obh
			else if(category.equalsIgnoreCase("OBH"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+obhId+" order by s.rank asc";
			}
			
			//obx
			else if(category.equalsIgnoreCase("OBX"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+obxId+" order by s.rank asc";
			}
			
			//ezhava_thiyya_billava_Id
			else if(category.equalsIgnoreCase("BC"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+ezhava_thiyya_billava_Id+" order by s.rank asc";
			}
			
			
			Query hqlQuery = session.createQuery(s);
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


	}

	
	
	
	
	
	// get student rank object list based on course and general or caste  for Multiple allotment
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationEditTransaction#getAdmApplonStudentOnCourseCategory(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<StudentRank> getAdmApplonStudentOnCourseCategoryForMultipleAllotment(Integer courseId,Integer year,String category) throws Exception 
	{
		
		Session session = null;
		List list=null;
		try{
			
			
			
			Properties prop = new Properties();
			try 
			{
				InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("resources/application.properties");
				prop.load(in);
			} 
			catch (FileNotFoundException e)
			{
				log.error("Unable to read properties file...", e);

			} 
			catch (IOException e)
			{
				log.error("Unable to read properties file...", e);

			}
			
			//sc
			Integer scId=0;	
			String sc = prop.getProperty ("knowledgepro.admission.religionsection.sc");
			if(sc !=null && ! StringUtils.isEmpty(sc)){ 
				scId=Integer.parseInt(sc);
			}
			
			//ezhava_thiyya_billava
			Integer ezhava_thiyya_billava_Id=0;	
			String ezhava_thiyya_billava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava_thiyya_billava");
			if(ezhava_thiyya_billava !=null && ! StringUtils.isEmpty(ezhava_thiyya_billava)){ 
				ezhava_thiyya_billava_Id=Integer.parseInt(ezhava_thiyya_billava);
			}
			
			//mu
			Integer muId=0;	
			String mu = prop.getProperty ("knowledgepro.admission.religionsection.mu");
			if(mu !=null && ! StringUtils.isEmpty(mu)){ 
				muId=Integer.parseInt(mu);
			}
			
			//st
			Integer stId=0;	
			String st = prop.getProperty ("knowledgepro.admission.religionsection.st");
			if(st !=null && ! StringUtils.isEmpty(st)){ 
				stId=Integer.parseInt(st);
			}
			
			//lc
			Integer lcId=0;	
			String lc = prop.getProperty ("knowledgepro.admission.religionsection.lc");
			if(lc !=null && ! StringUtils.isEmpty(lc)){ 
				lcId=Integer.parseInt(lc);
			}
			
			//lc
			Integer lctaId=0;	
			String lcta = prop.getProperty ("knowledgepro.admission.religionsection.lcta");
			if(lcta !=null && ! StringUtils.isEmpty(lcta)){ 
				lctaId=Integer.parseInt(lcta);
			}
			
			//obh
			Integer obhId=0;	
			String obh = prop.getProperty ("knowledgepro.admission.religionsection.obh");
			if(obh !=null && ! StringUtils.isEmpty(obh)){ 
				obhId=Integer.parseInt(obh);
			}
			
			//obx
			Integer obxId=0;	
			String obx = prop.getProperty ("knowledgepro.admission.religionsection.obx");
			if(obx !=null && ! StringUtils.isEmpty(obx)){ 
				obxId=Integer.parseInt(obx);
			}
			
			//ezhava
			Integer ezhavaId=0;	
			String ezhava = prop.getProperty ("knowledgepro.admission.religionsection.ezhava");
			if(ezhava !=null && ! StringUtils.isEmpty(ezhava)){ 
				ezhavaId=Integer.parseInt(ezhava);
			}
			

			//billava
			Integer billavaId=0;	
			String billava = prop.getProperty ("knowledgepro.admission.religionsection.billava");
			if(billava !=null && ! StringUtils.isEmpty(billava)){ 
				billavaId=Integer.parseInt(billava);
			}
			
			//thiyya
			Integer thiyyaId=0;	
			String thiyya = prop.getProperty ("knowledgepro.admission.religionsection.thiyya");
			if(thiyya !=null && ! StringUtils.isEmpty(thiyya)){ 
				thiyyaId=Integer.parseInt(thiyya);
			}
			//community
			Integer communityId=0;	
			String community = prop.getProperty ("knowledgepro.admission.religionsection.community");
			if(community !=null && ! StringUtils.isEmpty(community)){ 
				communityId=Integer.parseInt(community);
			}
			
			
			
			
			session = HibernateUtil.getSession();
			String s="";
			
			
			if(category.equalsIgnoreCase("GENERAL"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+
				 " and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where (s1.isAssigned=0 or (s1.prefNo=1 and s1.isAssigned=1 and s1.isGeneral=1)or(s1.isGeneral=1 and s1.isSatisfied=1)) and s1.admAppln.appliedYear="+year+") order by s.rank asc";	
			}
			else if(category.equalsIgnoreCase("SC"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+scId+
				 " and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  (s1.isAssigned=0 or (s1.prefNo=1 and s1.isAssigned=1)or(s1.isSatisfied=1)) and s1.admAppln.appliedYear="+year+") order by s.rank asc";
			}
			else if(category.equalsIgnoreCase("ST"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+stId+
				 " and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  (s1.isAssigned=0 or (s1.prefNo=1 and s1.isAssigned=1)or(s1.isSatisfied=1)) and s1.admAppln.appliedYear="+year+") order by s.rank asc";
			}
			
			else if(category.equalsIgnoreCase("LC"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and (s.admAppln.personalData.religionSection.id ="+lcId+" or s.admAppln.personalData.religionSection.id ="+lctaId+")" +
				 " and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  (s1.isAssigned=0 or (s1.prefNo=1 and s1.isAssigned=1)or(s1.isSatisfied=1)) and s1.admAppln.appliedYear="+year+") order by s.rank asc";
			}
			// BC mens EZHAVA,THIYYA,BILLAVA
			//else if(category.equalsIgnoreCase("BC"))
			//{
				//s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and (s.admAppln.personalData.religionSection.id ="+ezhavaId+
			//	" or s.admAppln.personalData.religionSection.id ="+thiyyaId+" or s.admAppln.personalData.religionSection.id ="+billavaId+")"+
			//	 " and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  s1.isAlloted=1 and s1.prefNo=1 and s1.admAppln.appliedYear="+year+") order by s.rank asc";
				
		//	}
			else if(category.equalsIgnoreCase("MU"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+muId+
				 " and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  (s1.isAssigned=0 or (s1.prefNo=1 and s1.isAssigned=1)or(s1.isSatisfied=1)) and s1.admAppln.appliedYear="+year+") order by s.rank asc";
			}
			//else if(category.equalsIgnoreCase("LCTA"))
			//{
				//s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id=9 order by s.rank asc";
			//}
			else if(category.equalsIgnoreCase("OBH"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+obhId+
				 " and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where (s1.isAssigned=0 or (s1.prefNo=1 and s1.isAssigned=1)or(s1.isSatisfied=1)) and s1.admAppln.appliedYear="+year+") order by s.rank asc";	
			}
			else if(category.equalsIgnoreCase("OBX"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+obxId+
				 " and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  (s1.isAssigned=0 or (s1.prefNo=1 and s1.isAssigned=1)or(s1.isSatisfied=1)) and s1.admAppln.appliedYear="+year+") order by s.rank asc";
			}
			
			
			//ezhava_thiyya_billava_Id not mu
			else if(category.equalsIgnoreCase("BC"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+ezhava_thiyya_billava_Id+
				" and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where (s1.isAssigned=0 or (s1.prefNo=1 and s1.isAssigned=1)or(s1.isSatisfied=1))and s1.admAppln.appliedYear="+year+") order by s.rank asc";
				
			}
			
			//COMMUNITY
			else if(category.equalsIgnoreCase("COMMUNITY"))
			{
				s="from StudentRank s where   s.course.id="+courseId+" and s.admAppln.appliedYear="+year+" and s.admAppln.personalData.religionSection.id="+communityId+
				" and s.admAppln.id not in (select distinct s1.admAppln.id from StudentCourseAllotment s1 where  (s1.isAssigned=0 or (s1.prefNo=1 and s1.isAssigned=1)or(s1.isSatisfied=1)) and s1.admAppln.appliedYear="+year+") order by s.rank asc";
				
			}
			
			
			Query hqlQuery = session.createQuery(s);
			list=hqlQuery.list();
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;


	}
	@Override
	public Map<Integer, StudentCourseAllotment> getallotmentMap(int pgmtype,int year) throws ApplicationException {
		
		Map<Integer,StudentCourseAllotment> allotmentMap = new LinkedHashMap<Integer, StudentCourseAllotment>();
		
		Session session = null;
		List list1 = null;
		
		try{
			session = HibernateUtil.getSession();
			//Query hqlQuery = session.createQuery("select s.admAppln.id,s.prefNo from StudentCourseAllotment s where  s.isAlloted=1  and s.allotmentNo="+(admForm.getAllotedNo()+1)+" and s.admAppln.appliedYear="+Integer.parseInt(admForm.getAcademicYear()));
			Query hqlQuery = session.createQuery("from StudentCourseAllotment s where s.admAppln.appliedYear="+year+" and s.admAppln.course.program.programType.id="+pgmtype);
			
			list1=hqlQuery.list();
			
			
			if(list1!=null){
				Iterator  itr=list1.iterator();
				while(itr.hasNext()){
				
				StudentCourseAllotment stdnt =(StudentCourseAllotment)itr.next();;
				
					
			    allotmentMap.put(stdnt.getAdmAppln().getId(),stdnt);
					
				}
			}
			
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return allotmentMap;
	}

	
	@Override
	public Map<Integer,String> getDegClassGroupSubject(String sub) throws Exception{
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer,String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from AdmSubjectForRank where isActive=1 and groupName=:stream");
			query.setString("stream",sub);
			List<AdmSubjectForRank> list=query.list();
			if(list!=null){
				Iterator <AdmSubjectForRank> itr=list.iterator();
				while(itr.hasNext()){
					AdmSubjectForRank adm=itr.next();
					if(adm!=null && adm.getName()!=null && adm.getId()>0){
						
						map.put( adm.getId(), adm.getGroupName());
					}
				}
			}
		}
		catch(Exception exception){
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		//map=(Map<String,String>) CommonUtil.sortMapByValue(map);
		return map;
		
	}
	@Override
	public void savePrevCourseAllotment(List<StudentCourseAllotmentPrev> prevAllotmentList)throws Exception {
		
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			if(prevAllotmentList.size()!=0){
				
			
			txn.begin();
			Iterator<StudentCourseAllotmentPrev> itr=prevAllotmentList.iterator();
			while(itr.hasNext()){
				StudentCourseAllotmentPrev mark=(StudentCourseAllotmentPrev)itr.next() ;
			
			session.save(mark);
			
			}
			txn.commit();
			
//			sessionFactory.close();
			
			}
		} catch (Exception e) {
			
			log.error("======================= save previous course allotment impl...================" + e);
			throw e;
		}finally{
			session.flush();
			//session.clear();
			//session.close();
		}
		
	}

	
}
