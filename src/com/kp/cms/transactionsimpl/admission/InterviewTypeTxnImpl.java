package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.handlers.admission.InterviewTypeHandler;
import com.kp.cms.transactions.admission.IInterviewTypeTxn;
import com.kp.cms.utilities.HibernateUtil;

public class InterviewTypeTxnImpl implements IInterviewTypeTxn {

	/**
	 * This method will return unique instance of InterviewTypeTxnImpl class.
	 */

	private static volatile InterviewTypeTxnImpl self = null;
	private static Log log = LogFactory.getLog(InterviewTypeHandler.class);

	public static InterviewTypeTxnImpl getInstance() {
		if (self == null)
			self = new InterviewTypeTxnImpl();
		return self;
	}

	private InterviewTypeTxnImpl() {

	}

	/**
	 * This method is used to get interview type details.
	 * 
	 * @return list of type InterviewType.
	 */

	public List getInterviewType() throws Exception {
		log.info("entered getInterviewType..");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			String interviewHibernateQuery = "from InterviewProgramCourse";
			List interviewTypes = session.createQuery(interviewHibernateQuery)
					.list();
			transaction.commit();
			session.flush();
			//session.close();
			return interviewTypes;
		} catch (Exception e) {
			if (session != null) {
				transaction.rollback();
				session.flush();
				//session.close();
			}
			log.info("Error while getting Interview Type" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to get candidate count based on course id.
	 * 
	 * @return int value.
	 */

	public int getCandidateCount(int courseID) throws Exception {
		log.info("entered getCandidateCount..");
		Session session = null;
		Transaction transaction = null;
		int count = 0;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();
			transaction.begin();
			String interviewHibernateQuery = "from AdmAppln a where a.course.id="
					+ courseID;
			List interviewTypes = session.createQuery(interviewHibernateQuery)
					.list();
			count = interviewTypes.size();
			transaction.commit();
			session.flush();
			//session.close();
			return count;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();
			log.info("Error while getting Candidates Count" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to candidate details based on course id, year,
	 * interview id.
	 * 
	 * @return list of type InterviewSelected.
	 */

	public List getCandidates(int courseID, int programID, int years,
			ArrayList<Integer> interviewList, int noOfCandidates, int examCenterId)
			throws Exception {

		log.info("entered getCandidates..");
		Session session = null;
		Transaction transaction = null;
		boolean isCardGenerated = false;
		boolean isbypassed = false;
		Query query = null;
		StringBuffer sqlString = new StringBuffer();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if (courseID != 0) {
				sqlString.append("from InterviewSelected i where i.interviewProgramCourse.id in (:interviewID) and i.interviewProgramCourse.course.id = :courseID and i.admAppln.appliedYear = :years and i.isCardGenerated = :isCardGenerated and i.admAppln.isBypassed = :isbypassed ");
				if(examCenterId > 0){
					sqlString.append(" and i.admAppln.examCenter.id = " + examCenterId);
				}
				sqlString.append(" order by i.admAppln.id" );
				query = session.createQuery(sqlString.toString());				
				query.setInteger("courseID", courseID);
			} else {
				sqlString.append("from InterviewSelected i where i.interviewProgramCourse.id in (:interviewID) and i.interviewProgramCourse.program.id = :programID and i.admAppln.appliedYear = :years and i.isCardGenerated = :isCardGenerated and i.admAppln.isBypassed = :isbypassed ");
				if(examCenterId > 0){
					sqlString.append(" and i.admAppln.examCenter.id = " + examCenterId);
				}
				sqlString.append("order by i.admAppln.id");

				query = session.createQuery(sqlString.toString());				
				query.setInteger("programID", programID);
			}
			query.setParameterList("interviewID", interviewList);
			query.setInteger("years", years);
			query.setBoolean("isCardGenerated", isCardGenerated);
			query.setBoolean("isbypassed", isbypassed);
			query.setMaxResults(noOfCandidates);
			List candidateList = query.list();
			transaction.commit();
			session.flush();
			//session.close();
			return candidateList;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();
			log.info("Error while getting Candidates Details" + e);
			throw new ApplicationException(e);
		}
	}

	// public List getByStudentDetails(int applicationID, int programTypeID, int
	// programID, int courseID, java.sql.Date birthDate, String firstName)
	// throws Exception {
	// log.info("entered getByStudentDetails..");
	// Session session = null;
	// Transaction transaction = null;
	// int count=0;
	// try {
	//			
	// StringBuffer sf = new StringBuffer();
	// sf.append("from AdmAppln a");
	// boolean added = false;
	//			
	// if(0 != applicationID){
	// added = true;
	// sf.append(" where a.applnNo="+ applicationID);
	// }
	// if(0 != programTypeID & 0 != programID & 0 != courseID){
	// if(added){
	// sf.append(" and ");
	// } else {
	// sf.append(" where ");
	// }
	// added = true;
	// sf.append("a.course.id='"+ courseID +"'");
	// }
	//			
	// if(null != firstName && !"".equals(firstName)){
	// if(added){
	// sf.append(" and ");
	// } else {
	// sf.append(" where ");
	// }
	// added = true;
	// sf.append("a.personalData.firstName like '%"+ firstName +"%'");
	// }
	// if(null != birthDate && !"".equals(birthDate)){
	// if(!birthDate.equals("0")){
	// if(added){
	// sf.append(" and ");
	// } else {
	// sf.append(" where ");
	// }
	// added = true;
	// sf.append("a.personalData.dateOfBirth='"+ birthDate+"'");
	// }
	// }
	//			
	// //SessionFactory sessionFactory = InitSessionFactory.getInstance();
	// //session = sessionFactory.openSession();
	// transaction = session.beginTransaction();
	// transaction.begin();
	// List interviewTypes = session.createQuery(
	// sf.toString()).list();
	// transaction.commit();
	// session.flush();
	// //session.close();
	// return interviewTypes;
	// } catch (Exception e) {
	// if (session != null)
	// transaction.rollback();
	// session.flush();
	// //session.close();
	//				
	// log.info("Error while getting Student Details"+e);
	// throw e;
	// }
	// }

	/**
	 * This method is used to get student details.
	 * 
	 * @return list of type InterviewType.
	 */

	public List getByStudentDetails(int applicationID, int interviewtypeid,
			int programTypeID, int programID, int courseID, int year,
			java.sql.Date birthDate, String firstName) throws Exception {
		log.info("entered getByStudentDetails..");
		Session session = null;
		Transaction transaction = null;
		int count = 0;
		try {

			StringBuffer sf = new StringBuffer();
			sf.append("from InterviewCard i");
			boolean added = false;

			if (0 != applicationID) {
				added = true;
				sf.append(" where i.admAppln.applnNo=" + applicationID);
			}
			if (0 != interviewtypeid) {
				if (added) {
					sf.append(" and ");
				} else {
					sf.append(" where ");
				}
				added = true;
				sf.append("i.interview.interview.interviewProgramCourse.id='"
						+ interviewtypeid + "'");
			}

			if (0 != programTypeID) {
				if (added) {
					sf.append(" and ");
				} else {
					sf.append(" where ");
				}
				added = true;
				sf
						.append("i.interview.interview.interviewProgramCourse.course.program.programType.id='"
								+ programTypeID + "'");
			}
			if (0 != programID) {
				if (added) {
					sf.append(" and ");
				} else {
					sf.append(" where ");
				}
				added = true;
				sf
						.append("i.interview.interview.interviewProgramCourse.course.program.id='"
								+ programID + "'");
			}
			if (0 != courseID) {
				if (added) {
					sf.append(" and ");
				} else {
					sf.append(" where ");
				}
				added = true;
				sf
						.append("i.interview.interview.interviewProgramCourse.course.id='"
								+ courseID + "'");
			}
			if (0 != year) {
				if (added) {
					sf.append(" and ");
				} else {
					sf.append(" where ");
				}
				added = true;
				sf.append("i.admAppln.appliedYear='" + year + "'");
			}

			if (null != firstName && !"".equals(firstName)) {
				if (added) {
					sf.append(" and ");
				} else {
					sf.append(" where ");
				}
				added = true;
				sf.append("i.admAppln.personalData.firstName like '%"
						+ firstName + "%'");
			}
			if (null != birthDate && !"".equals(birthDate)) {
				if (!birthDate.equals("0")) {
					if (added) {
						sf.append(" and ");
					} else {
						sf.append(" where ");
					}
					added = true;
					sf.append("i.admAppln.personalData.dateOfBirth='"
							+ birthDate + "'");
				}
			}

			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			List interviewTypes = session.createQuery(sf.toString()).list();
			transaction.commit();
			session.flush();
			//session.close();
			return interviewTypes;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();

			log.info("Error while getting Student Details" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to get Birth date details based on course id and
	 * date.
	 * 
	 * @return list of type InterviewType.
	 */

	public List getByBirthDate(java.sql.Date birthDate, int courseID)
			throws Exception {
		log.info("entered getByBirthDate..");
		Session session = null;
		int count = 0;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("from AdmAppln a where a.personalData.dateOfBirth = :birthDate and a.course.id = :courseID");
			query.setDate("birthDate", birthDate);
			query.setInteger("courseID", courseID);
			List interviewTypes = query.list();
			transaction.commit();
			session.flush();
			//session.close();
			return interviewTypes;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();

			log.info("Error while getting AdmAppln by BirthDate" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to get personal data based on first name and course
	 * id.
	 * 
	 * @return list of type InterviewType.
	 */

	public List getByFirstName(String firstName, int courseID) throws Exception {
		log.info("entered getByFirstName..");
		Session session = null;
		Transaction transaction = null;
		int count = 0;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("from AdmAppln a where a.personalData.firstName = :firstName and a.course.id = :courseID");
			query.setString("firstName", firstName);
			query.setInteger("courseID", courseID);
			List interviewTypes = query.list();
			transaction.commit();
			session.flush();
			//session.close();
			return interviewTypes;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();

			log.info("Error while getting AdmAppln by FirstName" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to get Interview details.
	 * 
	 * @param id
	 * @return list of type InterviewType.
	 * @throws Exception
	 */

	public List getByInterviewId(String id) throws Exception {
		log.info("entered getByInterviewId..");
		Session session = null;
		Transaction transaction = null;
		int count = 0;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("from Interview i where i.id = :id");
			query.setInteger("id", Integer.parseInt(id));
			List interviewTypes = query.list();
			transaction.commit();
			session.flush();
			//session.close();
			return interviewTypes;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();

			log.info("Error while getting Interview" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to persist the interview details.
	 * 
	 * @return boolean value.
	 */

	public boolean persistInterview(Interview interview) throws Exception {

		log.info("entered persistInterview..");
		Transaction trans = null;
		//SessionFactory sessfactory = InitSessionFactory.getInstance();
		//Session dbSession = sessfactory.openSession();
		Session dbSession = HibernateUtil.getSession();
		try {
			trans = dbSession.beginTransaction();
			dbSession.save(interview);
			trans.commit();

			return true;
		} catch (ConstraintViolationException e) {
			trans.rollback();

			log.info("Error while Saving Interview" + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			trans.rollback();

			log.info("Error while Saving Interview" + e);
			throw new ApplicationException(e);
		} finally {
			dbSession.flush();
			dbSession.close();
		}

	}

	/**
	 * This method is used to updateAdmAppln details based on course id.
	 * 
	 * @return boolean value.
	 */

	public boolean updateAdmAppln(int courseID) throws Exception {
		log.info("entered updateAdmAppln..");
		Transaction trans = null;
		//SessionFactory sessfactory = InitSessionFactory.getInstance();
		//Session dbSession = sessfactory.openSession();
		Session dbSession = HibernateUtil.getSession();
		try {
			trans = dbSession.beginTransaction();
			String hql = "update AdmAppln set isInterviewSelected = :isInterviewSelected where course.id = :courseid";
			Query query = dbSession.createQuery(hql);
			query.setInteger("courseid", courseID);
			query.setBoolean("isInterviewSelected", true);
			int rowCount = query.executeUpdate();
			trans.commit();
			dbSession.flush();
			dbSession.close();
			return true;
		} catch (ConstraintViolationException e) {

			trans.rollback();
			dbSession.flush();
			dbSession.close();
			log.info("Error while Updating Admin Application Details" + e);
			throw new BusinessException(e);
		} catch (Exception e) {

			trans.rollback();
			log.info("Error while Updating Admin Application Details" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to updateInterviewSelected details.
	 * 
	 * @return boolean value.
	 */

	public boolean updateInterviewSelected(Set applNoSet) throws Exception {
		log.info("entered updateInterviewSelected..");
		Transaction trans = null;
		//SessionFactory sessfactory = InitSessionFactory.getInstance();
		//Session dbSession = sessfactory.openSession();
		Session dbSession = HibernateUtil.getSession(); 
		try {
			trans = dbSession.beginTransaction();
			String hql = "update InterviewSelected i set i.isCardGenerated = :isCardGenerated where i.admAppln.id in (:applNoSet)";
			// String hql =
			// "update InterviewSelected i set i.isCardGenerated = :isCardGenerated"
			// ;
			Query query = dbSession.createQuery(hql);

			query.setParameterList("applNoSet", applNoSet);
			query.setBoolean("isCardGenerated", true);

			int rowCount = query.executeUpdate();

			trans.commit();
			dbSession.flush();
			dbSession.close();
			return true;
		} catch (ConstraintViolationException e) {

			trans.rollback();
			dbSession.flush();
			dbSession.close();
			log.info("Error while Updating InterviewSelected" + e);
			throw new BusinessException(e);
		} catch (Exception e) {

			trans.rollback();
			log.info("Error while Updating InterviewSelected" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to get interview card details based on course id and
	 * year.
	 * 
	 * @return list of type InterviewType.
	 */

	public List getStudentsList(int courseID, int years) throws Exception {
		log.info("entered getStudentsList..");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("from InterviewCard i where i.admAppln.courseBySelectedCourseId.id = :courseID and i.admAppln.appliedYear = :years order by i.createdDate desc");
			query.setInteger("courseID", courseID);
			query.setInteger("years", years);
			List interviewTypes = query.list();
			transaction.commit();
			session.flush();
			//session.close();
			return interviewTypes;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();
			log.info("Error while getting Student Details" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to get interview card details.
	 * 
	 * @return list of type InterviewType.
	 */

	public List getInterviewCard(Set applNoSet) throws Exception {
		log.info("entered getStudentsList..");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("from InterviewCard i where i.admAppln.applnNo in (:applNoSet)");
			query.setParameterList("applNoSet", applNoSet);
			List interviewTypes = query.list();
			transaction.commit();
			session.flush();
			//session.close();
			return interviewTypes;
		} catch (Exception e) {

			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();
			log.info("Error while getting Student Details" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to get interview card details.
	 * 
	 * @return list of type InterviewType.
	 */

	public List getInterviewCardForMail(Set applNoSet) throws Exception {
		log.info("entered getStudentsList..");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("from InterviewCard i where i.admAppln.id in (:applNoSet)");
			query.setParameterList("applNoSet", applNoSet);
			List interviewTypes = query.list();
			transaction.commit();
			session.flush();
			//session.close();
			return interviewTypes;
		} catch (Exception e) {

			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();
			log.info("Error while getting Student Details" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to get program details based on interviewtype id.
	 * 
	 * @return InterviewProgramCourse instance.
	 */

	public InterviewProgramCourse getProgramDetails(int interviewTypeId)
			throws Exception {
		log.info("entered getProgramDetails..");
		InterviewProgramCourse intervewProgramCourse = new InterviewProgramCourse();
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InterviewProgramCourse i where i.id = :interviewTypeId");
			query.setInteger("interviewTypeId", interviewTypeId);
			List listofprograms = query.list();
			if (listofprograms != null && !listofprograms.isEmpty()) {
				Iterator<InterviewProgramCourse> itr = listofprograms
						.iterator();

				while (itr.hasNext()) {
					intervewProgramCourse = (InterviewProgramCourse) itr.next();
					break;
				}
				session.flush();
				//session.close();
				return intervewProgramCourse;
			}
		} catch (Exception e) {
			session.flush();
			//session.close();

			log.info("Error while getting Program Details" + e);
			throw new ApplicationException(e);
		}
		return new InterviewProgramCourse();
	}

	/**
	 * This method is used to save interview card details.
	 * 
	 * @return boolean value.
	 */

	public boolean persistInterviewCard(InterviewCard[] interviewCard,
			String[] candidateList) throws Exception {
		log.info("entered persistInterviewCard..");
		Transaction trans = null;
//		SessionFactory sessfactory = InitSessionFactory.getInstance();
//		Session dbSession = sessfactory.openSession();
		Session dbSession = HibernateUtil.getSession();
		
		try {
			trans = dbSession.beginTransaction();
			for (int i = 0; i < interviewCard.length; i++) {
				if (interviewCard[i] != null) {
					dbSession.saveOrUpdate(interviewCard[i]);
					AdmAppln admAppln=(AdmAppln)dbSession.get(AdmAppln.class, interviewCard[i].getAdmAppln().getId());
					admAppln.setAdmStatus(null);
					admAppln.setLastModifiedDate(new Date());
					admAppln.setModifiedBy(interviewCard[i].getModifiedBy());
					dbSession.update(admAppln);
					
				}
				if (30 % candidateList.length == 0) {
					dbSession.flush();
					dbSession.clear();
				}
			}
			trans.commit();
			dbSession.flush();
			dbSession.close();
			return true;
		} catch (ConstraintViolationException e) {

			trans.rollback();
			dbSession.flush();
			dbSession.close();
			log.info("Error while saving InterviewCard" + e);
			throw new BusinessException(e);
		} catch (Exception e) {

			trans.rollback();
			dbSession.flush();
			dbSession.close();
			log.info("Error while saving InterviewCard" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to update Interview card.
	 * 
	 * @return boolean value.
	 */

	public boolean updateInterviewCard(List interviewCardList) throws Exception {
		log.info("entered updateInterviewCard..");
		Transaction trans = null;
		//SessionFactory sessfactory = InitSessionFactory.getInstance();
		//Session dbSession = sessfactory.openSession();
		Session dbSession = HibernateUtil.getSession();
		try {
			trans = dbSession.beginTransaction();
			if (interviewCardList != null) {
				Iterator<InterviewCard> itr = interviewCardList.iterator();
				while (itr.hasNext()) {
					InterviewCard interviewCard = (InterviewCard) itr.next();
					dbSession.update(interviewCard);
				}
			}
			trans.commit();
			dbSession.flush();
			dbSession.close();
			return true;
		} catch (ConstraintViolationException e) {

			trans.rollback();
			dbSession.flush();
			dbSession.close();
			log.info("Error while updating InterviewCard" + e);
			throw new BusinessException(e);
		} catch (Exception e) {

			trans.rollback();
			log.info("Error while updating InterviewCard" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to InterviewProgramCourse details.
	 * 
	 * @return string value.
	 */

	public String getIntCardContentFromTable(int courseid, String interviewType)
			throws Exception {
		log.info("entered getIntCardContentFromTable..");
		Session session = null;
		Transaction transaction = null;
		String intCardContent = "";
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("from InterviewProgramCourse i where course.id = :courseid and i.name=:interviewType");
			query.setInteger("courseid", courseid);
			query.setString("interviewType", interviewType);
			InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) query
					.uniqueResult();
			if (interviewProgramCourse != null) {
				intCardContent = interviewProgramCourse.getContent();
			}
			transaction.commit();
			session.flush();
			//session.close();
			return intCardContent;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
			session.flush();
			//session.close();
			log.info("Error while getting Student Details" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to get course id based on admappln id.
	 * 
	 * @return int value.
	 */

	public int getcourseID(int applnid) throws Exception {
		log.info("entered getcourseID..");
		Session session = null;
		Transaction transaction = null;
		int courseID = 0;
		AdmAppln admAppln = new AdmAppln();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("from AdmAppln a where applnNo = :applnid");
			query.setInteger("applnid", applnid);
			List course = query.list();
			if (course != null && !course.isEmpty()) {
				Iterator<AdmAppln> itr = course.iterator();

				while (itr.hasNext()) {
					admAppln = (AdmAppln) itr.next();
					break;
				}
			}
			if (admAppln != null) {
				courseID = admAppln.getCourseBySelectedCourseId().getId();
			}
			transaction.commit();
			session.flush();
			session.close();
			return courseID;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
			session.flush();
			session.close();
			log.info("Error while getting Student Details" + e);
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param programId
	 * @param examCenterId
	 * @return
	 * @throws Exception
	 */
	public Integer getExamCenter(int programId, int examCenterId) throws Exception {
		log.info("entered getProgramDetails..");
		Session session = null;
		int currentSeatNo = 0;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamCenter e where e.id = :examCenterId and e.program.id = :programId");
			query.setInteger("examCenterId", examCenterId);
			query.setInteger("programId", programId);
			List<ExamCenter> examCenterList = query.list();
			if(examCenterList!= null && examCenterList.size()!= 0){
				ExamCenter examCenter = (ExamCenter) examCenterList.get(0);
				currentSeatNo = examCenter.getCurrentSeatNo();
			}
			session.flush();
			return currentSeatNo;
		} catch (Exception e) {
			session.flush();
			session.close();
			log.info("Error while getting Program Details" + e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * @param applNoSet
	 * @throws ApplicationException
	 */
	public void updateSeatNo(Set applNoSet) throws ApplicationException {
		log.info("entering into updateGeneralWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator itr = applNoSet.iterator();

			while (itr.hasNext()) {
				Integer applId = (Integer) itr.next();
				AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class, applId);
				if(admAppln.getExamCenter() == null){
					continue;
				}
				int programId = admAppln.getCourseBySelectedCourseId().getProgram().getId(); 
				int examCenterId = admAppln.getExamCenter().getId();
				int seatNo = getExamCenter(programId,examCenterId); 
				if(admAppln.getSeatNo() == null || admAppln.getSeatNo().trim().isEmpty() && seatNo > 0){
					admAppln.setSeatNo(Integer.toString(seatNo));
					session.update(admAppln);
					int newSeatNo = seatNo + 1;
					ExamCenter examCenter = (ExamCenter) session.get(ExamCenter.class, examCenterId);
					examCenter.setCurrentSeatNo(newSeatNo);
					session.update(examCenter);
				}
				
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateGeneralWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateGeneralWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
	}
	/**
	 * 
	 */
	public void updateExamCenterTable(int programId, int examCenterId, int seatNo, Session dbSession) throws Exception {
		log.info("entered updateExamCenterTable..");
		Transaction trans = null;
		try {
			trans = dbSession.beginTransaction();
			trans.begin();
			seatNo = seatNo + 1;
			String hql = "update ExamCenter e set e.currentSeatNo = " + seatNo + " where program.id = " + programId + " and id = " + examCenterId;
			Query query = dbSession.createQuery(hql);
			query.executeUpdate();
			trans.commit();
		} catch (ConstraintViolationException e) {

			trans.rollback();
			log.info("Error in updateExamCenterTable" + e);
			throw new BusinessException(e);
		} catch (Exception e) {

			trans.rollback();
			log.info("Error in updateExamCenterTable" + e);
			throw new ApplicationException(e);
		}
	}
	
}