package com.kp.cms.transactionsimpl.reports;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.ICiaOverallReportTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CiaOverallReportTxnImpl implements ICiaOverallReportTxn {
	private static final Log log = LogFactory.getLog(CiaOverallReportTxnImpl.class);
	private static volatile CiaOverallReportTxnImpl ciaOverallReportTxnImpl = null;
	

	public static CiaOverallReportTxnImpl getInstance() {
		if (ciaOverallReportTxnImpl == null) {
			ciaOverallReportTxnImpl = new CiaOverallReportTxnImpl();
		}
		return ciaOverallReportTxnImpl;
	}
	/**
	 * 
	 * @param studentId
	 * @param classId
	 * @param examId
	 * @return
	 * @throws ApplicationException
	 */
	public List<ExamStudentOverallInternalMarkDetailsBO> getStudentWiseOverAllExamMarkDetails( int studentId, int classId, int examId) throws ApplicationException {
		Session session = null;
		List<ExamStudentOverallInternalMarkDetailsBO> overAllBOList = null;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery(" from ExamStudentOverallInternalMarkDetailsBO int " +
													 " where int.studentId = " + studentId +
													 " and int.examId = " + examId + " and int.classId = " + classId);
			overAllBOList = studentQuery.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return overAllBOList;
	}	
	/**
	 * 
	 * @param classId
	 * @param hallTicketOrMarksCard
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassId(int classId) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishExamResultsBO e where  e.classId = " + classId +
				" and publishDate = (select max(publishDate) from ExamPublishExamResultsBO b where  b.classId = " + 
				classId + " )"; 
			Query query = session.createQuery(queryString);
			List<ExamPublishExamResultsBO> publishList = query.list();
			int examId = 0;
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishExamResultsBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishExamResultsBO examPublishExamResultsBO = (ExamPublishExamResultsBO) itr
							.next();
					
					examId = examPublishExamResultsBO.getExamId();
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
	 * @return
	 * @throws Exception
	 */
	public ExamPublishExamResultsBO getClassIds(int studentId, int curClassId)throws Exception {
		log.info("Start of getClassIds");
		Session session = null;
		List<Integer> classIdList;
		ExamPublishExamResultsBO examPublishExamResultsBO = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			classIdList = session.createQuery("select e.classId from ExamStudentPreviousClassDetailsBO e " +
					" where e.studentId = " + studentId ).list();
			if(classIdList!= null && classIdList.size() > 0){
				StringBuffer classIds = new StringBuffer();
				String classIdString = "";
				Iterator<Integer> itr = classIdList.iterator();
				while (itr.hasNext()) {
					Integer classId = (Integer) itr.next();
					classIds.append(Integer.toString(classId)+ ",") ;
				}
				classIds.append(curClassId);
				classIdString = classIds.toString();
				if (classIdString.endsWith(",") == true) {
					classIdString = StringUtils.chop(classIds.toString());
				}
				examPublishExamResultsBO = getPublishedClassId(classIdString);
				
			}
			
		} catch (Exception e) {
			log.error("Error in getActiveCourses of Course Impl",e);
			System.out.println("Get getClassIds ID ******BALAJI** :"+e);
			e.printStackTrace();
			System.out.println("Get getClassIds ID ******BALAJI**END :"+e);
			throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
			}
		}
		log.info("End of getActiveCourses of CourseTransactionImpl");
		return examPublishExamResultsBO;
	}
	
	/**
	 * 
	 * @param classIds
	 * @return
	 * @throws Exception
	 */
	public ExamPublishExamResultsBO getPublishedClassId(String classIds) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishExamResultsBO e where  e.classId in (" + classIds + ")" +
				" and e.publishDate = (select max(publishDate) from ExamPublishExamResultsBO b where  b.classId in (" + 
				classIds + " ) and  (isPublishOverallInternalCompOnly is not null and isPublishOverallInternalCompOnly = 1))"; 
			Query query = session.createQuery(queryString);
			List<ExamPublishExamResultsBO> publishList = query.list();
			ExamPublishExamResultsBO returnBO = null;
			if(publishList!= null && publishList.size() > 0){
				Iterator<ExamPublishExamResultsBO> itr = publishList.iterator();
				while (itr.hasNext()) {
					ExamPublishExamResultsBO examPublishExamResultsBO = (ExamPublishExamResultsBO) itr
							.next();
					returnBO = examPublishExamResultsBO;
				}
			}
			session.flush();
			return returnBO;
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
	public int getClassId(int studentId) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.isActive=1 and s.id = " + studentId);
			Student student = (Student) query.uniqueResult();
			session.flush();
			
			if(student.getClassSchemewise()!= null && student.getClassSchemewise().getClasses()!= null){
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
}
