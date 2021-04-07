package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.bo.exam.ExamUpdateExcludeWithheldBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class DownloadHallTicketTransactionImpl implements IDownloadHallTicketTransaction {
	private static final Log log = LogFactory.getLog(DownloadHallTicketTransactionImpl.class);
	public static volatile DownloadHallTicketTransactionImpl downloadHallTicketTransactionImpl = null;
	
	public static DownloadHallTicketTransactionImpl getInstance() {
	if (downloadHallTicketTransactionImpl == null) {
		downloadHallTicketTransactionImpl = new DownloadHallTicketTransactionImpl();
		return downloadHallTicketTransactionImpl;
	}
	return downloadHallTicketTransactionImpl;
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public int getClassId(int studentId, LoginForm loginForm) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.isActive=1 and s.id = " + studentId);
			Student student = (Student) query.uniqueResult();
			session.flush();
			
			if(student.getClassSchemewise()!= null && student.getClassSchemewise().getClasses()!= null){
				loginForm.setRegNo(student.getRegisterNo());
				return student.getClassSchemewise().getClasses().getId();
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassId(int classId, LoginForm loginForm, String hallTicketOrMarksCard) throws Exception {
		Session session = null;
		try {
			Map<Integer,String> revDateMap=new HashMap<Integer,String>();
			List<Integer> revaluationRegClassId=null;
			
			if(hallTicketOrMarksCard.equalsIgnoreCase("Marks Card") && loginForm.getRevaluationRegClassId()!=null)
				revaluationRegClassId=loginForm.getRevaluationRegClassId();
			else
				revaluationRegClassId=new ArrayList<Integer>();
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId = " + classId + " and publishFor = '" + hallTicketOrMarksCard + "' and ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between e.downloadStartDate and e.downloadEndDate or '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between e.downloadEndDate and e.extendedDate)  and e.examDefinitionBO.examTypeID != 3" +
				" and e.examDefinitionBO.examTypeID != 6"; 
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			int examId = 0;
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					
					examId = examPublishHallTicketMarksCardBO.getExamId();
					if(examPublishHallTicketMarksCardBO.getAgreementId()!= null){
						loginForm.setAgreementId(examPublishHallTicketMarksCardBO.getAgreementId());
					}else{
						loginForm.setAgreementId(0);
					}
					if(examPublishHallTicketMarksCardBO.getRevaluationEndDate()!=null){
						if(CommonUtil.getDaysDiff(new Date(), examPublishHallTicketMarksCardBO.getRevaluationEndDate())>= 0){
							if(!revaluationRegClassId.contains(examPublishHallTicketMarksCardBO.getClassId())){
								revaluationRegClassId.add(examPublishHallTicketMarksCardBO.getClassId());
								revDateMap.put(examPublishHallTicketMarksCardBO.getClassId(),CommonUtil.formatSqlDate( examPublishHallTicketMarksCardBO.getRevaluationEndDate().toString()));
							}
						}
					}
				}
				loginForm.setRevaluationRegClassId(revaluationRegClassId);
				loginForm.setRevDateMap(revDateMap);
			}
			session.flush();
			return examId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassIdMarksCard(int classId, LoginForm loginForm, String hallTicketOrMarksCard) throws Exception {
		Session session = null;
		try {
			Map<Integer,String> revDateMap=new HashMap<Integer,String>();
			List<Integer> revaluationRegClassId=null;
			
			if(hallTicketOrMarksCard.equalsIgnoreCase("Marks Card") && loginForm.getRevaluationRegClassId()!=null)
				revaluationRegClassId=loginForm.getRevaluationRegClassId();
			else
				revaluationRegClassId=new ArrayList<Integer>();
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId = " + classId + " and publishFor = '" + hallTicketOrMarksCard + "' and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between e.downloadStartDate and e.downloadEndDate  and e.examDefinitionBO.examTypeID != 3" +
				" and e.examDefinitionBO.examTypeID != 6"; 
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			int examId = 0;
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					
					examId = examPublishHallTicketMarksCardBO.getExamId();
					if(examPublishHallTicketMarksCardBO.getAgreementId()!= null){
						loginForm.setAgreementId(examPublishHallTicketMarksCardBO.getAgreementId());
					}else{
						loginForm.setAgreementId(0);
					}
					if(examPublishHallTicketMarksCardBO.getRevaluationEndDate()!=null){
						if(CommonUtil.getDaysDiff(new Date(), examPublishHallTicketMarksCardBO.getRevaluationEndDate())>= 0){
							if(!revaluationRegClassId.contains(examPublishHallTicketMarksCardBO.getClassId())){
								revaluationRegClassId.add(examPublishHallTicketMarksCardBO.getClassId());
								revDateMap.put(examPublishHallTicketMarksCardBO.getClassId(),CommonUtil.formatSqlDate( examPublishHallTicketMarksCardBO.getRevaluationEndDate().toString()));
							}
						}
					}
				}
				loginForm.setRevaluationRegClassId(revaluationRegClassId);
				loginForm.setRevDateMap(revDateMap);
			}
			session.flush();
			return examId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param studentId
	 * @param classId
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public ExamBlockUnblockHallTicketBO isHallTicketBlockedStudent(int studentId , int classId, int examId, String hallTicketMarksCard) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamBlockUnblockHallTicketBO b where b.classId = " + classId +
				" and b.studentId = " + studentId + " and b.classId = " + classId + " and b.hallTktOrMarksCard = '" + hallTicketMarksCard + "'" + " and " +
				" examId = " + examId;
			Query query = session.createQuery(queryString) ;
			List<ExamBlockUnblockHallTicketBO> blockedList = query.list();
			ExamBlockUnblockHallTicketBO examBlockUnblockHallTicketBO;
			session.flush();
			if(blockedList!= null && blockedList.size() > 0){
				examBlockUnblockHallTicketBO = blockedList.get(0);
				return examBlockUnblockHallTicketBO;
			}
		} catch (Exception e) {
			log.error("Error in isBloHallTicketBlockedStudent..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return null;
	}
	
	/**
	 * 
	 * @param classId
	 * @param examId
	 * @return
	 * @throws Exception
	 */

	public boolean isCurrentDateValidForDownLoadHallTicket(int classId, int examId, String hallTicketMarksCard, boolean isSup) throws Exception {
		Session session = null;
		try {
			String date = CommonUtil.getTodayDate();
			Date curDate = CommonUtil.ConvertStringToSQLDate(date);
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId = " + classId +
			" and examId = " + examId + " and ('" +  curDate + "' >= e.downloadStartDate and '" +
			 	curDate + "' <= e.downloadEndDate) or ('"+curDate+"'>= e.downloadEndDate and '"+curDate+"'<= e.extendedDate) and e.publishFor = " + "'" + hallTicketMarksCard + "'";  
			   
			
			if(isSup){
				 queryString =  queryString + " and (e.examDefinitionBO.examTypeID = 3"  
				+ " or e.examDefinitionBO.examTypeID = 6)";
			}
			else{
				 queryString =  queryString + " and e.examDefinitionBO.examTypeID != 3"  
					+ " and e.examDefinitionBO.examTypeID != 6";
			}
			
			Query query = session.createQuery(queryString);
					
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			
			session.flush();
			if(publishList!= null && publishList.size() > 0){
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			log.error("Error in isCurrentDateValidForDownLoadHallTicket..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param agrementId
	 * @return
	 * @throws Exception
	 */
	public ExamFooterAgreementBO getExamFooterAgreement(int agrementId) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamFooterAgreementBO f where f.isActive=1 and f.id = " + agrementId );
			ExamFooterAgreementBO agreementBO = (ExamFooterAgreementBO) query.uniqueResult();
			session.flush();
			return agreementBO;
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public int getClassIds(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception {
		log.info("Start of getClassIds");
		Session session = null;
		List<Integer> classIdList;
		int previousClassId = 0;
		try {
			session = InitSessionFactory.getInstance().openSession();
			classIdList = session.createQuery("select e.classId from ExamStudentPreviousClassDetailsBO e " +
					" where e.studentId = " + studentId ).list();
			
			StringBuffer classIds = new StringBuffer();
			String classIdString = "";
			if(classIdList!= null && classIdList.size() > 0){
				Iterator<Integer> itr = classIdList.iterator();
				while (itr.hasNext()) {
					Integer classId = (Integer) itr.next();
					classIds.append(Integer.toString(classId)+ ",") ;
				}
			}
			classIds.append(curClassId);
			classIdString = classIds.toString();
			if (classIdString.endsWith(",") == true) {
				classIdString = StringUtils.chop(classIds.toString());
			}
			previousClassId = getExamIdByClassIdFromPreviousStudentDetailsTable(classIdString, isSup, publishedFor);
				
			
			
		} catch (Exception e) {
			log.error("Error in getActiveCourses of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
			}
		}
		log.info("End of getActiveCourses of CourseTransactionImpl");
		return previousClassId;
	}
	
	/**
	 * 
	 * @param classIds
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassIdFromPreviousStudentDetailsTable(String classIds, boolean isSup, String publishedFor) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId in (" + classIds + ")" + " and publishFor = '" +
				publishedFor + "' and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between e.downloadStartDate and e.downloadEndDate";
			
			if(isSup){
				 queryString =  queryString + " and (e.examDefinitionBO.examTypeID = 3"  
				+ " or e.examDefinitionBO.examTypeID = 6)";
			}
			else{
				 queryString =  queryString + " and e.examDefinitionBO.examTypeID != 3"  
					+ " and e.examDefinitionBO.examTypeID != 6";
			}
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			int classId = 0;
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					classId = examPublishHallTicketMarksCardBO.getClassId();
				}
			}
			session.flush();
			return classId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassIdFromPreviousStudentDetailsTable..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	public boolean getIsExcluded(int studentId, int examId) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamUpdateExcludeWithheldBO e where e.studentId = " + studentId + " and " +
								" e.examId = " + examId; 
			Query query = session.createQuery(queryString);
			List<ExamUpdateExcludeWithheldBO> excludeList = query.list();
			int classId = 0;
			boolean excluded = false;
			if(excludeList!= null && excludeList.size() > 0){
				Iterator<ExamUpdateExcludeWithheldBO> itr = excludeList.iterator();
				while (itr.hasNext()) {
					ExamUpdateExcludeWithheldBO examUpdateExcludeWithheldBO = (ExamUpdateExcludeWithheldBO) itr
							.next();
					if(examUpdateExcludeWithheldBO.getExcludeFromResults() || examUpdateExcludeWithheldBO.getWithheld()){
						excluded = true;
					}
				}
			}
			session.flush();
			return excluded;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassIdFromPreviousStudentDetailsTable..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * 
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public Integer getTermNumber(int classId) throws Exception {
		Session session = null;
		Integer termNo = 0;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from Classes c where c.id = " + classId + " and " + " c.isActive = 1"; 
			Query query = session.createQuery(queryString);
			List<Classes> classList = query.list();
			if(classList!= null && classList.size() > 0){
				Iterator<Classes> itr = classList.iterator();
				while (itr.hasNext()) {
					Classes classes = (Classes) itr.next();
					termNo = classes.getTermNumber(); 
				}
			}
			session.flush();
			return termNo;
		} catch (Exception e) {
			log.error("Error in getTermNumber..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param classId
	 * @param loginForm
	 * @param hallTicketOrMarksCard
	 * @return
	 * @throws Exception
	 */
	public String getExamIdByClassIdForSupHallTicket(int classId, LoginForm loginForm, String hallTicketOrMarksCard) throws Exception {
		Session session = null;
		try {
			
			Map<Integer,String> revDateMap=new HashMap<Integer,String>();
			if(loginForm.getSuprevDateMap()!=null && !loginForm.getSuprevDateMap().isEmpty())
				revDateMap=loginForm.getSuprevDateMap();
			
			List<Integer> revaluationSupClassId=null;
			
			if(hallTicketOrMarksCard.equalsIgnoreCase("Marks Card")){
				if(loginForm.getRevaluationSupClassId()!=null)	
					revaluationSupClassId=loginForm.getRevaluationSupClassId();
				else
					revaluationSupClassId=new ArrayList<Integer>();
			}
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId = " + classId + " and publishFor = '" + hallTicketOrMarksCard + "' and '" +
			CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between e.downloadStartDate and e.downloadEndDate and (e.examDefinitionBO.examTypeID = 3" +
				" or e.examDefinitionBO.examTypeID  = 6)"; 
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			String examId = "";
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					if(examId.isEmpty())
						examId = String.valueOf(examPublishHallTicketMarksCardBO.getExamId());
					else
						examId =examId+","+ String.valueOf(examPublishHallTicketMarksCardBO.getExamId());
					if(examPublishHallTicketMarksCardBO.getAgreementId()!= null){
						loginForm.setAgreementId(examPublishHallTicketMarksCardBO.getAgreementId());
					}
					if(hallTicketOrMarksCard.equalsIgnoreCase("Marks Card"))
					if(examPublishHallTicketMarksCardBO.getRevaluationEndDate()!=null){
						if(CommonUtil.getDaysDiff(new Date(), examPublishHallTicketMarksCardBO.getRevaluationEndDate())>= 0){
							if(!revaluationSupClassId.contains(examPublishHallTicketMarksCardBO.getClassId())){
								revaluationSupClassId.add(examPublishHallTicketMarksCardBO.getClassId());
								revDateMap.put(examPublishHallTicketMarksCardBO.getClassId(),CommonUtil.formatSqlDate( examPublishHallTicketMarksCardBO.getRevaluationEndDate().toString()));
							}
						}
					}
				}
				if(hallTicketOrMarksCard.equalsIgnoreCase("Marks Card")){
					loginForm.setRevaluationSupClassId(revaluationSupClassId);
					loginForm.setSuprevDateMap(revDateMap);
				}
			}
			session.flush();
			return examId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param studentId
	 * @param examId
	 * @return
	 */
	public boolean getIsAppliedForSupp(int studentId, int examId,int classId,String mode) {
		Session session = null;
		ArrayList<ExamSupplementaryImprovementApplicationBO> list;
		boolean isAppeared = false;
		try {

			String hql = "from ExamSupplementaryImprovementApplicationBO imp "
					+ " where studentId = " + studentId;
					if(mode.equalsIgnoreCase("hallTicket")){
						hql = hql+"  and (imp.isAppearedTheory = 1  or  imp.isAppearedPractical = 1) " ;
					}else if(mode.equalsIgnoreCase("marksCard")){
						hql = hql+"  and (imp.isAppearedTheory = 1  or  imp.isAppearedPractical = 1) " ;
					}
					hql = hql+ " and imp.examId = " + examId+" and imp.classes.id="+classId;
					 
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);
			list = new ArrayList<ExamSupplementaryImprovementApplicationBO>(query.list()); 
			if(list!= null && list.size() > 0){
				isAppeared = true;
			}
			session.flush();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAppeared;
	}
	@Override
	public List getStudentHallTicket(String hallTicketQuery)
			throws Exception {
		Session session = null;
		List hallTicketList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			 hallTicketList=session.createSQLQuery(hallTicketQuery).list();
			return hallTicketList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	@Override
	public List<Object[]> getStudentSupMarksCard(String hallTicketQuery)
			throws Exception {
		Session session = null;
		List<Object[]>  hallTicketList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
		//	session = HibernateUtil.getSession();
			hallTicketList=(List<Object[]>)session.createSQLQuery(hallTicketQuery).list();
			return hallTicketList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	public List<Integer> getStudentSupMarksCard1(String hallTicketQuery)
	throws Exception {
		Session session = null;
		List<Integer>  hallTicketList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			//	session = HibernateUtil.getSession();
			hallTicketList=(List<Integer>)session.createSQLQuery(hallTicketQuery).list();
			return hallTicketList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

}
	
	
	@SuppressWarnings("unchecked")
	public List<ExamSupplementaryImprovementApplicationBO> getStudentSupMarksCardList(String hallTicketQuery)throws Exception {
		List<ExamSupplementaryImprovementApplicationBO> supDetails=null;
		Session session = null;

		try {
			/*session = HibernateUtil.getSession();
			Query queri = session.createQuery(hallTicketQuery.toString());
			List<ExamSupplementaryImprovementApplicationBO> supDetails = queri.list();*/
			session = HibernateUtil.getSession();
			supDetails=session.createQuery(hallTicketQuery).list();
			//return hallTicketList;
			return supDetails;
			
		} catch (Exception e) {
				throw new ApplicationException(e);
		}
}
	
	
	@Override
	public List<ExamFooterAgreementBO> getFooterDetails(String programTypeId,String type, String classId)
			throws Exception {
		Session session = null;
		List hallTicketList = null;
		try {
			session = HibernateUtil.getSession();
			if(classId != null && !classId.trim().isEmpty()){
				String query = "from ExamFooterAgreementBO e" +
				" where e.hallTktOrMarksCard='"+type+"'" +
				" and e.isFooter=1" +
				" and e.programTypeId="+programTypeId +
				" and e.academicYear <= (select cs.curriculumSchemeDuration.curriculumScheme.year from ClassSchemewise cs where cs.classes.id="+classId+")";
				hallTicketList = session.createQuery(query).list();
			}
			if(hallTicketList == null || hallTicketList.isEmpty()){
				hallTicketList=session.createQuery("from ExamFooterAgreementBO e" +
						" where e.hallTktOrMarksCard='"+type+"'" +
						" and e.isFooter=1" +
						" and e.academicYear is null and e.programTypeId="+programTypeId).list();
			}
			return hallTicketList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	@Override
	public List<Object[]> getDataByHql(String query) throws Exception {
		Session session = null;
		List hallTicketList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			 hallTicketList=session.createQuery(query).list();
			return hallTicketList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	@Override
	public List<Integer> getSupplementaryClassIds(int studentId, int curClassId,
			boolean isSup, String publishedFor) throws Exception {
		log.info("Start of getClassIds");
		Session session = null;
		List<Integer> classIdList;
		List<Integer> previousClassId;
		try {
			session = InitSessionFactory.getInstance().openSession();
			classIdList = session.createQuery("select e.classId from ExamStudentPreviousClassDetailsBO e " +
					" where e.studentId = " + studentId+" group by e.classId" ).list();
			
			StringBuffer classIds = new StringBuffer();
			String classIdString = "";
			if(classIdList!= null && classIdList.size() > 0){
				Iterator<Integer> itr = classIdList.iterator();
				while (itr.hasNext()) {
					Integer classId = (Integer) itr.next();
					classIds.append(Integer.toString(classId)+ ",") ;
				}
			}
			classIds.append(curClassId);
			classIdString = classIds.toString();
			if (classIdString.endsWith(",") == true) {
				classIdString = StringUtils.chop(classIds.toString());
			}
			previousClassId = getExamIdForSupplemetaryClassIdFromPreviousStudentDetailsTable(classIdString, isSup, publishedFor);
				
			
			
		} catch (Exception e) {
			log.error("Error in getActiveCourses of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
			}
		}
		log.info("End of getActiveCourses of CourseTransactionImpl");
		return previousClassId;
	}
	/**
	 * 
	 * @param classIds
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getExamIdForSupplemetaryClassIdFromPreviousStudentDetailsTable(String classIds, boolean isSup, String publishedFor) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId in (" + classIds + ")" + " and publishFor = '" +
				publishedFor + "'" + 
				" and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between e.downloadStartDate and e.downloadEndDate";
			
			if(isSup){
				 queryString =  queryString + " and (e.examDefinitionBO.examTypeID = 3"  
				+ " or e.examDefinitionBO.examTypeID = 6)";
			}
			else{
				 queryString =  queryString + " and e.examDefinitionBO.examTypeID != 3"  
					+ " and e.examDefinitionBO.examTypeID != 6";
			}
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			List<Integer> classId = new ArrayList<Integer>();
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
	              						.next();
					if(!classId.contains(examPublishHallTicketMarksCardBO.getClassId()))
					classId.add(examPublishHallTicketMarksCardBO.getClassId());
				}
			}
		// commneted by manu,session.flush is given its parent method
		//	session.flush();
			return classId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassIdFromPreviousStudentDetailsTable..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getRegularClassIds(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception {
		log.info("Start of getClassIds");
		Session session = null;
		List<Integer> classIdList;
		List<Integer> previousClassId;
		try {
			session = InitSessionFactory.getInstance().openSession();
			classIdList = session.createQuery("select e.classId from ExamStudentPreviousClassDetailsBO e " +
					" where e.studentId = " + studentId ).list();
			
			StringBuffer classIds = new StringBuffer();
			String classIdString = "";
			if(classIdList!= null && classIdList.size() > 0){
				Iterator<Integer> itr = classIdList.iterator();
				while (itr.hasNext()) {
					Integer classId = (Integer) itr.next();
					classIds.append(Integer.toString(classId)+ ",") ;
				}
			}
			classIds.append(curClassId);
			classIdString = classIds.toString();
			if (classIdString.endsWith(",") == true) {
				classIdString = StringUtils.chop(classIds.toString());
			}
			previousClassId = getRegularExamIdByClassIdFromPreviousStudentDetailsTable(classIdString, isSup, publishedFor);
				
			
			
		} catch (Exception e) {
			log.error("Error in getActiveCourses of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
			}
		}
		log.info("End of getActiveCourses of CourseTransactionImpl");
		return previousClassId;
	}
	/**
	 * @param classIds
	 * @param isSup
	 * @param publishedFor
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getRegularExamIdByClassIdFromPreviousStudentDetailsTable(String classIds, boolean isSup, String publishedFor) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId in (" + classIds + ")" + " and publishFor = '" +
				publishedFor + "' and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between e.downloadStartDate and e.downloadEndDate";
			
			if(isSup){
				 queryString =  queryString + " and (e.examDefinitionBO.examTypeID = 3"  
				+ " or e.examDefinitionBO.examTypeID = 6)";
			}
			else{
				 queryString =  queryString + " and e.examDefinitionBO.examTypeID != 3"  
					+ " and e.examDefinitionBO.examTypeID != 6";
			}
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			List<Integer> classId = new ArrayList<Integer>();
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					classId.add(examPublishHallTicketMarksCardBO.getClassId());
				}
			}
			session.flush();
			return classId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassIdFromPreviousStudentDetailsTable..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadHallTicketTransaction#getClassId(int, com.kp.cms.forms.admission.DisciplinaryDetailsForm)
	 */
	public int getClassId(int studentId, DisciplinaryDetailsForm objForm) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.isActive=1 and s.id = " + studentId);
			Student student = (Student) query.uniqueResult();
			session.flush();
			
			if(student.getClassSchemewise()!= null && student.getClassSchemewise().getClasses()!= null){
				objForm.setRegNo(student.getRegisterNo());
				return student.getClassSchemewise().getClasses().getId();
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getClassIdsView(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception {
		log.info("Start of getClassIds");
		Session session = null;
		List<Integer> classIdList;
		List<Integer> previousClassId;
		try {
			session = InitSessionFactory.getInstance().openSession();
			classIdList = session.createQuery("select e.classId from ExamStudentPreviousClassDetailsBO e " +
					" where e.studentId = " + studentId ).list();
			
			StringBuffer classIds = new StringBuffer();
			String classIdString = "";
			if(classIdList!= null && classIdList.size() > 0){
				Iterator<Integer> itr = classIdList.iterator();
				while (itr.hasNext()) {
					Integer classId = (Integer) itr.next();
					classIds.append(Integer.toString(classId)+ ",") ;
				}
			}
			classIds.append(curClassId);
			classIdString = classIds.toString();
			if (classIdString.endsWith(",") == true) {
				classIdString = StringUtils.chop(classIds.toString());
			}
			previousClassId = getExamIdByClassIdFromPreviousStudentDetailsView(classIdString, isSup, publishedFor);
				
			
			
		} catch (Exception e) {
			System.out.println("***************Mary******************");
			e.printStackTrace();
			System.out.println("***************Mary******************");
			log.error("Error in getActiveCourses of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
			}
		}
		log.info("End of getActiveCourses of CourseTransactionImpl");
		return previousClassId;
	}
	@Override
	public int getExamIdByClassId(int classId, DisciplinaryDetailsForm objForm, String publishedFor) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId = " + classId + ")"+ "and publishFor = '" + publishedFor + "'" +
			 " and e.examDefinitionBO.examTypeID != 3" +
				" and e.examDefinitionBO.examTypeID != 6"; 
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			int examId = 0;
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					
					examId = examPublishHallTicketMarksCardBO.getExamId();
					
				}
			}
			session.flush();
			return examId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassId..." ,e);
			System.out.println("***************Mary******************");
			e.printStackTrace();
			System.out.println("***************Mary******************");
			session.flush();
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadHallTicketTransaction#getExamIdByClassIdForSupHallTicket(int, com.kp.cms.forms.admission.DisciplinaryDetailsForm, java.lang.String)
	 */
	public int getExamIdByClassIdForSupHallTicket(int classId, DisciplinaryDetailsForm objForm, String hallTicketOrMarksCard) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId = " + classId + " and publishFor = '" + hallTicketOrMarksCard + "'" +
				" and (e.examDefinitionBO.examTypeID = 3" +
				" or e.examDefinitionBO.examTypeID  = 6)"; 
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			int examId = 0;
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					
					examId = examPublishHallTicketMarksCardBO.getExamId();
					
				}
			}
			session.flush();
			return examId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	public int getTermNo(int studentId, DisciplinaryDetailsForm objForm) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.isActive=1 and s.id = " + studentId);
			Student student = (Student) query.uniqueResult();
			session.flush();
			
			if(student.getClassSchemewise()!= null && student.getClassSchemewise().getClasses()!= null){
				return student.getClassSchemewise().getCurriculumSchemeDuration().getSemesterYearNo();
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * @param classIds
	 * @param isSup
	 * @param publishedFor
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getExamIdByClassIdFromPreviousStudentDetailsView(String classIds, boolean isSup, String publishedFor) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId in (" + classIds + ")" + " and publishFor = '" +
				publishedFor + "'" ;
			if(!isSup){
				queryString=queryString+"  and b.examDefinitionBO.examTypeID != 3 and b.examDefinitionBO.examTypeID != 6";
			}else
				queryString=queryString+"  and (b.examDefinitionBO.examTypeID = 3 or b.examDefinitionBO.examTypeID = 6)";
			
			queryString=queryString + ")";
			
			if(isSup){
				 queryString =  queryString + " and (e.examDefinitionBO.examTypeID = 3"  
				+ " or e.examDefinitionBO.examTypeID = 6)";
			}
			else{
				 queryString =  queryString + " and e.examDefinitionBO.examTypeID != 3"  
					+ " and e.examDefinitionBO.examTypeID != 6";
			}
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			List<Integer> classId = new ArrayList<Integer>();
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					classId.add(examPublishHallTicketMarksCardBO.getClassId());
				}
			}
			session.flush();
			return classId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassIdFromPreviousStudentDetailsTable..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadHallTicketTransaction#getExamBlockUnblock(java.lang.String)
	 */
	@Override
	public ExamBlockUnblockHallTicketBO getExamBlockUnblock(String blockId)
			throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			ExamBlockUnblockHallTicketBO bo =(ExamBlockUnblockHallTicketBO)session.get(ExamBlockUnblockHallTicketBO.class,Integer.parseInt(blockId));
			return bo;
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param classId
	 * @param loginForm
	 * @param hallTicketOrMarksCard
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassIdForSupMarksCard(int classId, LoginForm loginForm, String hallTicketOrMarksCard) throws Exception {
		Session session = null;
		try {
			
			Map<Integer,String> revDateMap=new HashMap<Integer,String>();
			if(loginForm.getSuprevDateMap()!=null && !loginForm.getSuprevDateMap().isEmpty())
				revDateMap=loginForm.getSuprevDateMap();
			
			List<Integer> revaluationSupClassId=null;
			
			if(hallTicketOrMarksCard.equalsIgnoreCase("Marks Card")){
				if(loginForm.getRevaluationSupClassId()!=null)	
					revaluationSupClassId=loginForm.getRevaluationSupClassId();
				else
					revaluationSupClassId=new ArrayList<Integer>();
			}
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId = " + classId + " and publishFor = '" + hallTicketOrMarksCard + "' and '" +
			CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between e.downloadStartDate and e.downloadEndDate and (e.examDefinitionBO.examTypeID = 3" +
				" or e.examDefinitionBO.examTypeID  = 6) and e.examDefinitionBO.id="+loginForm.getSupMCexamID(); 
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			int examId = 0;
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					
					examId = examPublishHallTicketMarksCardBO.getExamId();
					if(examPublishHallTicketMarksCardBO.getAgreementId()!= null){
						loginForm.setAgreementId(examPublishHallTicketMarksCardBO.getAgreementId());
					}
					if(hallTicketOrMarksCard.equalsIgnoreCase("Marks Card"))
					if(examPublishHallTicketMarksCardBO.getRevaluationEndDate()!=null){
						if(CommonUtil.getDaysDiff(new Date(), examPublishHallTicketMarksCardBO.getRevaluationEndDate())>= 0){
							if(!revaluationSupClassId.contains(examPublishHallTicketMarksCardBO.getClassId())){
								revaluationSupClassId.add(examPublishHallTicketMarksCardBO.getClassId());
								revDateMap.put(examPublishHallTicketMarksCardBO.getClassId(),CommonUtil.formatSqlDate( examPublishHallTicketMarksCardBO.getRevaluationEndDate().toString()));
							}
						}
					}
				}
				if(hallTicketOrMarksCard.equalsIgnoreCase("Marks Card")){
					loginForm.setRevaluationSupClassId(revaluationSupClassId);
					loginForm.setSuprevDateMap(revDateMap);
				}
			}
			session.flush();
			return examId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/**
	 * @param hallTicketQuery
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getStudentHallTicketNew(String hallTicketQuery,List<Integer> classList)
			throws Exception {
		Session session = null;
		List hallTicketList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery(hallTicketQuery);
			query.setParameterList("classList", classList);
			 hallTicketList=query.list();
			return hallTicketList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getRegularClassIdsForMarksCard(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception {
		log.info("Start of getClassIds");
		Session session = null;
		List<Integer> classIdList;
		List<Integer> previousClassId;
		try {
			session = InitSessionFactory.getInstance().openSession();
			classIdList = session.createQuery("select e.classId from ExamStudentPreviousClassDetailsBO e " +
					" where e.studentId = " + studentId ).list();
			
			StringBuffer classIds = new StringBuffer();
			String classIdString = "";
			if(classIdList!= null && classIdList.size() > 0){
				Iterator<Integer> itr = classIdList.iterator();
				while (itr.hasNext()) {
					Integer classId = (Integer) itr.next();
					classIds.append(Integer.toString(classId)+ ",") ;
				}
			}
			classIds.append(curClassId);
			classIdString = classIds.toString();
			if (classIdString.endsWith(",") == true) {
				classIdString = StringUtils.chop(classIds.toString());
			}
			previousClassId = getRegularExamIdByClassIdFromPreviousStudentDetailsTableNew(classIdString, isSup, publishedFor);
				
			
			
		} catch (Exception e) {
			log.error("Error in getActiveCourses of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
			}
		}
		log.info("End of getActiveCourses of CourseTransactionImpl");
		return previousClassId;
	}
	
	/**
	 * @param classIds
	 * @param isSup
	 * @param publishedFor
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getRegularExamIdByClassIdFromPreviousStudentDetailsTableNew(String classIds, boolean isSup, String publishedFor) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classId in (" + classIds + ")" + " and publishFor = '" +
				publishedFor + "' and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between e.downloadStartDate and e.downloadEndDate";
			
			if(isSup){
				 queryString =  queryString + " and (e.examDefinitionBO.examTypeID = 3"  
				+ " or e.examDefinitionBO.examTypeID = 6)";
			}
			else{
				 queryString =  queryString + " and e.examDefinitionBO.examTypeID != 3"  
					+ " and e.examDefinitionBO.examTypeID != 6";
			}
			Query query = session.createQuery(queryString);
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			List<Integer> classId = new ArrayList<Integer>();
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishHallTicketMarksCardBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO = (ExamPublishHallTicketMarksCardBO) itr
							.next();
					classId.add(examPublishHallTicketMarksCardBO.getClassId());
				}
			}
			session.flush();
			return classId;
		} catch (Exception e) {
			log.error("Error in getExamIdByClassIdFromPreviousStudentDetailsTable..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadHallTicketTransaction#getRoomNoAndFloorNoAndBlockNo(int, int, int)
	 */
	@Override
	public Object[] getRoomNoAndFloorNoAndBlockNo(int studentId, int examId, int classId,Map<String, ExamRoomAllotmentDetailsTO> examSessionMap) throws Exception{
		Session session = null;
		Object[] obj = null;
		try{
			session = HibernateUtil.getSession();
			/*String str =" select bo.examRoomMasterBO.roomNo,bo.examRoomMasterBO.floorNo,bo.examRoomMasterBO.blockNo from ExamAssignStudentsRoomBO bo" +
						" left join bo.examAssignStudentsRoomStudentListBOset room" +
						" where room.examAssignStudentsRoomBO.id = bo.id" +
						" and bo.classId="+classId+
						" and bo.examId="+examId+
						" and room.studentId ="+studentId;*/
			
			String str = "select room.room_no,room.floor_name,block.block_name,examRoom.date,EXAM_sessions.id,EXAM_sessions.session "
					+ " from EXAM_room_allotment_details details "
					+ " join EXAM_room_allotment examRoom on examRoom.id = details.exam_room_allotment_id "
					+ " join room_master room on examRoom.room_id = room.id"
					+ " join block on block.id = room.block_id "
					+ " left join EXAM_sessions on examRoom.exam_session_id = EXAM_sessions.id "
					+ " where details.is_active=1 and"
					+ " details.student_id="+studentId
					+ " and details.class_id="+classId
					+ " and examRoom.exam_id="+examId
					+ " and examRoom.is_active =1 ";
			Query query = session.createSQLQuery(str);
			List<Object[]> objList = query.list();
			if(objList!=null && !objList.isEmpty()){
				Iterator<Object[]> iterator = objList.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[3] == null || objects[3].toString().isEmpty()){
						ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
						to.setBlockName(objects[2].toString());
						to.setFloor(objects[1].toString());
						to.setRoomNo(objects[0].toString());
						examSessionMap.put("SameRoom", to);
					}else if(objects[3]!=null && objects[4]!=null){
						String dateString= objects[3].toString();
//						String dateString = time.substring(0, 10);
						String inputDateFormat = "yyyy-mm-dd";
						String outPutdateFormat = "dd/mm/yyyy";
						String examDate = CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat);
						String sessionName ="";
						if(objects[5] != null)
							sessionName = objects[5].toString();
						if(!examSessionMap.containsKey(examDate+"_"+sessionName)){
							ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
							to.setBlockName(objects[2].toString());
							to.setFloor(objects[1].toString());
							to.setRoomNo(objects[0].toString());
							to.setExamDate(examDate);
							to.setExamSessionId(Integer.parseInt(objects[4].toString()));
							to.setExamSessionName(sessionName);
							examSessionMap.put(examDate+"_"+objects[4].toString(), to);
						}
						
					}
				}
				
			}

		}catch (Exception e) {
			log.error("Error in getRoomNoAndFloorNoAndBlockNo of DownloadHallTicketTransactionImpl ",e);
			throw new ApplicationException(e);
		} finally {
		if (session != null) {
//			session.flush();
			session.close();
			}
		}
		return obj;
	}
	public String getStudentBatch(int stdId)throws Exception{
		Session session = null;
		String batch="";
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.isActive=1 and s.id = " + stdId);
			Student student = (Student) query.uniqueResult();
			session.flush();
			
			if(student.getAdmAppln().getAppliedYear()!= null && student.getAdmAppln().getAppliedYear()!= null){
				batch=String.valueOf(student.getAdmAppln().getAppliedYear());
				return batch;
			}
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return batch;
	}
	@Override
	public StudentCertificateCourse getStudentCertificateCourseOnGoing(
			int studentId, int termNo) throws Exception {
		Session session = null;
		StudentCertificateCourse studentCertificateCourse=null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from StudentCertificateCourse s where s.isCancelled=0 and s.student.id="+studentId+" and s.schemeNo="+termNo);
			studentCertificateCourse = (StudentCertificateCourse) query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return studentCertificateCourse;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadHallTicketTransaction#getClasesByExamName(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getClasesByExamName(String examId, String year) throws Exception {
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			String sqlQuery = 	"select classes.id,classes.name,curriculum_scheme.year from classes " +
								" INNER JOIN course ON classes.course_id = course.id " +
								" INNER JOIN EXAM_exam_course_scheme_details ON EXAM_exam_course_scheme_details.course_id = course.id AND EXAM_exam_course_scheme_details.scheme_no=classes.term_number " +
								" INNER JOIN course_scheme ON EXAM_exam_course_scheme_details.course_scheme_id=course_scheme.id " +
								" INNER JOIN class_schemewise ON class_schemewise.class_id = classes.id " +
								" INNER JOIN curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
								" INNER JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id " +
								" AND curriculum_scheme.course_id = course.id " +
								" AND curriculum_scheme.course_scheme_id = course_scheme.id " +
								" INNER JOIN EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id " +
								" WHERE EXAM_definition.id= " +examId+
								" AND classes.is_active=1 AND EXAM_exam_course_scheme_details.is_active=1 AND course_scheme.is_active=1 " +
								" AND curriculum_scheme_duration.academic_year= "+year+" order by classes.name";
			Query query = session.createSQLQuery(sqlQuery);
			List<Object[]> list = query.list();
			if(list != null && !list.isEmpty()){
				for (Object[] objects : list) {
					if (objects[0] != null && objects[1] != null && objects[2] != null) {
						map.put(Integer.parseInt(objects[0].toString()), objects[1].toString()+" ("+objects[2].toString()+")");
					}
				}
			}
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadHallTicketTransaction#getRoomNoDetailsForStudents(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<Integer, Map<String, ExamRoomAllotmentDetailsTO>> getRoomNoDetailsForStudents(
			List<Integer> studentIds, String examId, String classId) throws Exception {
		Session session = null;
		Map<Integer, Map<String, ExamRoomAllotmentDetailsTO>> map = null;
		try{
			session = HibernateUtil.getSession();
			
			String str = "select room.room_no,room.floor_name,block.block_name,examRoom.date,EXAM_sessions.id,EXAM_sessions.session,details.student_id as sId "
					+ " from EXAM_room_allotment_details details "
					+ " join EXAM_room_allotment examRoom on examRoom.id = details.exam_room_allotment_id "
					+ " join room_master room on examRoom.room_id = room.id"
					+ " join block on block.id = room.block_id "
					+ " left join EXAM_sessions on examRoom.exam_session_id = EXAM_sessions.id "
					+ " where details.is_active=1 and"
					+ " details.student_id in(:Students)"
					+ " and details.class_id="+classId
					+ " and examRoom.exam_id="+examId
					+ " and examRoom.is_active =1 ";
			Query query = session.createSQLQuery(str).setParameterList("Students", studentIds);
			List<Object[]> objList = query.list();
			if(objList!=null && !objList.isEmpty()){
				map = new HashMap<Integer, Map<String,ExamRoomAllotmentDetailsTO>>();
				Map<String,ExamRoomAllotmentDetailsTO> submap = new HashMap<String, ExamRoomAllotmentDetailsTO>();
				Iterator<Object[]> iterator = objList.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[6]!=null){
						if(map.containsKey(Integer.parseInt(objects[6].toString()))){
							submap = map.remove(Integer.parseInt(objects[6].toString()));
						}else{
							submap = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						}
						if(objects[3] == null || objects[3].toString().isEmpty()){
							ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
							to.setBlockName(objects[2].toString());
							to.setFloor(objects[1].toString());
							to.setRoomNo(objects[0].toString());
							submap.put("SameRoom", to);
						}else if(objects[3]!=null && objects[4]!=null){
							String dateString= objects[3].toString();
							String inputDateFormat = "yyyy-mm-dd";
							String outPutdateFormat = "dd/mm/yyyy";
							String examDate = CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat);
							String sessionName ="";
							if(objects[5] != null)
								sessionName = objects[5].toString();
							if(!submap.containsKey(examDate+"_"+sessionName)){
								ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
								to.setBlockName(objects[2].toString());
								to.setFloor(objects[1].toString());
								to.setRoomNo(objects[0].toString());
								to.setExamDate(examDate);
								to.setExamSessionId(Integer.parseInt(objects[4].toString()));
								to.setExamSessionName(sessionName);
								submap.put(examDate+"_"+objects[4].toString(), to);
							}
						}
						map.put(Integer.parseInt(objects[6].toString()), submap);
					}
				}
			}

		}catch (Exception e) {
			log.error("Error in getRoomNoAndFloorNoAndBlockNo of DownloadHallTicketTransactionImpl ",e);
			throw new ApplicationException(e);
		} finally {
		if (session != null) {
//			session.flush();
			session.close();
			}
		}
		return map;
	}
	
	public int getPreClassId(int studentId, LoginForm loginForm) throws Exception {
		Session session = null;
		try {
			//Query query = session.createQuery("select sp.classes.id from StudentPreviousClassHistory sp where sp.student=" + studentId+" and sp.schemeNo="+1);
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.isActive=1 and s.id = " + studentId);
			Student student = (Student) query.uniqueResult();
			session.flush();
			
			//take old term no
			int classid=0;
			int termno=student.getClassSchemewise().getClasses().getTermNumber()-1;
			if(termno!=0){
			Query q = session.createQuery("select sp.classes.id from StudentPreviousClassHistory sp where sp.student=" + studentId+" and sp.schemeNo="+termno);
			if(q.uniqueResult() != null)
			classid=(Integer)q.uniqueResult();
			}
			loginForm.setRevClassId(classid);
			
			return classid;
			
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	// for revaluation 
	
	public int getClassIdForRevaluation(int studentId, LoginForm loginForm) throws Exception {
		Session session = null;
		try {

			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.isActive=1 and s.id = " + studentId);
			Student student = (Student) query.uniqueResult();
			session.flush();
			if(studentId!=0 && loginForm.getExamIDForMCard()!=0){
				Query q = session.createQuery("select e.classes.id from MarksEntry e where e.exam.id="+loginForm.getExamIDForMCard()+" and e.student.id="+studentId+" group by e.student.id" );
				int classid=(Integer)q.uniqueResult();
				loginForm.setRevClassId(classid);
			}
			if(student.getClassSchemewise()!= null && student.getClassSchemewise().getClasses()!= null){
				loginForm.setRegNo(student.getRegisterNo());
				return student.getClassSchemewise().getClasses().getId();
			}
			else {
				return 0;
			}

		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	@Override
	public byte[] getDocument(int studentId) throws Exception {
		Session session=null;
		byte[] doc=null;
		try{
			session=HibernateUtil.getSession();
			String sql ="select appln_doc.document "
					   + "from student join adm_appln ON adm_appln.id = student.adm_appln_id "
					   + "join appln_doc on appln_doc.adm_appln_id = adm_appln.id "
					   + "where student.id=:sid "
					   + "and appln_doc.is_photo=1";
			Query query = session.createSQLQuery(sql).setInteger("sid", studentId);
			doc=(byte[])query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	@Override
	public int getAcademicYear() throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AcademicYear ac where ac.isCurrent=1");
			AcademicYear academicYear = (AcademicYear) query.uniqueResult();
			session.flush();
			
			return academicYear.getYear();
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
}
