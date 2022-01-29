package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentForCJCForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentForCJCTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ExamRoomAllotmentForCJCTxnImpl implements IExamRoomAllotmentForCJCTransaction{
	private static volatile ExamRoomAllotmentForCJCTxnImpl txnImpl ;
	public static ExamRoomAllotmentForCJCTxnImpl getInstance(){
		if(txnImpl ==null){
			txnImpl = new ExamRoomAllotmentForCJCTxnImpl();
		}
		return txnImpl;
	}
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentStatusTxnImpl.class);
	/**
	 * @param hqlQuery
	 * @return
	 * @throws Exception
	 */
	public List getDetailsForQuery( String hqlQuery) throws Exception {
		Session session = null;
		List<Object[]> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery(hqlQuery);
			list = query.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving getRoomsAllotedForCycle.." +e);
			throw  new ApplicationException(e);
		} 
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentForCJCTransaction#getDetailsForQuery1(java.lang.String)
	 */
	public ExamRoomAllotment getDetailsForQuery1( String hqlQuery) throws Exception {
		Session session = null;
		ExamRoomAllotment bo = new ExamRoomAllotment();
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery(hqlQuery);
			bo = (ExamRoomAllotment) query.uniqueResult();
			return bo;
		} catch (Exception e) {
			log.error("Error while retrieving getRoomsAllotedForCycle.." +e);
			throw  new ApplicationException(e);
		} 
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentForCJCTransaction#getRegisterNo(java.lang.String)
	 */
	@Override
	public String getRegisterNo(String hqlQuery) throws Exception {
		Session session  = null;
		String regNo = "";
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(hqlQuery);
			regNo = (String) query.uniqueResult();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return regNo;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentForCJCTransaction#getStudentForSelectedClass(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public Map<String,StudentTO> getStudentForSelectedClass(String academicYear,String registerNo, int noOfStudents,int classId) throws Exception {
		Session session = null;
		Map<String,StudentTO> studentMap= new HashMap<String, StudentTO>();
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select s.registerNo,s.id,s.classSchemewise.classes.name,s.classSchemewise.classes.id from Student s " +
					" left join s.examStudentDetentionRejoinDetails exam" +
					" where s.isAdmitted=1 and s.isActive=1 and (s.isHide=0 or s.isHide is null)" ;
					if(registerNo!=null && !registerNo.isEmpty()){
						hqlQuery= hqlQuery+ " and s.registerNo> '"+registerNo+"'";
					}
			hqlQuery= hqlQuery+ " and s.admAppln.isCancelled=0  and  s.classSchemewise.classes.id ="+classId+
					" and s.classSchemewise.curriculumSchemeDuration.academicYear=" +Integer.parseInt(academicYear)+
					" and (exam.detain is null or exam.discontinued is null)" +
					" and (exam.rejoin = 1 or exam.rejoin is null)" +
					" order by s.registerNo";
			Query query = session.createQuery(hqlQuery);
			query.setMaxResults(noOfStudents);
			List<Object[]>regNoList =  query.list();
			if(regNoList!=null && !regNoList.isEmpty()){
				Iterator<Object[]> iterator = regNoList.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					StudentTO to = new StudentTO();
					to.setStudentId(Integer.parseInt(obj[1].toString()));
					to.setClassName(obj[2].toString());
					to.setClassId(Integer.parseInt(obj[3].toString()));
					to.setRegisterNo(obj[0].toString());
					studentMap.put(obj[0].toString(), to);
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return studentMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentForCJCTransaction#getSaveOrUpdateAllotmentDetails(com.kp.cms.bo.examallotment.ExamRoomAllotment)
	 */
	@Override
	public boolean getSaveOrUpdateAllotmentDetails( ExamRoomAllotment allotment) throws Exception {
		Session session = null;
		Transaction tx= null;
		boolean isAdded = false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			session.saveOrUpdate(allotment);
			tx.commit();
			isAdded = true;
		}catch (Exception e) {
			if(tx != null)
				tx.rollback();
			if (session != null) {
				session.flush();
				session.close();
			}
		}finally{
			session.flush();
			session.close();
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentForCJCTransaction#getRegisterNumbersList(com.kp.cms.forms.examallotment.ExamRoomAllotmentForCJCForm, java.lang.String, int, java.lang.String)
	 */
	@Override
	public LinkedList<String> getRegisterNumbersList( ExamRoomAllotmentForCJCForm objForm, String registerNo,
			int noOfStudents, String mode,String tempField) {
		Session session = null;
		 LinkedList<String> registerNumbersList = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select s.registerNo from Student s " +
					" left join s.examStudentDetentionRejoinDetails exam" ;
					 if(mode.equalsIgnoreCase("Subject")){
						 hqlQuery = hqlQuery + " left join s.admAppln.applicantSubjectGroups appSub" +
						 					   " left join appSub.subjectGroup.subjectGroupSubjectses subGroup" ;
					}
					 hqlQuery = hqlQuery + " where s.isAdmitted=1 and s.isActive=1 and (s.isHide=0 or s.isHide is null)" ;
					if(registerNo!=null && !registerNo.isEmpty()){
						hqlQuery= hqlQuery+ " and s.registerNo> '"+registerNo+"'";
					}
					if(mode.equalsIgnoreCase("Class")){
						hqlQuery= hqlQuery+" and  s.classSchemewise.classes.id ="+Integer.parseInt(objForm.getClassId());
					}else if(mode.equalsIgnoreCase("Subject")){
						hqlQuery= hqlQuery+"and subGroup.isActive=1 and subGroup.subject.id="+Integer.parseInt(objForm.getSubjectId())+
											" and appSub.subjectGroup.course.workLocation.id="+Integer.parseInt(objForm.getCampusId()) ;
					}
					hqlQuery= hqlQuery+ " and s.admAppln.isCancelled=0  " +
					" and s.classSchemewise.curriculumSchemeDuration.academicYear="+Integer.parseInt(objForm.getAcademicYear())+
					" and (exam.detain is null or exam.discontinued is null)" +
					" and (exam.rejoin = 1 or exam.rejoin is null)" +
					" order by s.registerNo";
			Query query = session.createQuery(hqlQuery);
			if(!tempField.equalsIgnoreCase("remainingCount")){ // if the condition is FALSE  get the remaining total students of the 
				query.setMaxResults(noOfStudents);             // selected class or subject, if it is TRUE  get the number of students entered.
			}
			List<String> list =  query.list();
			registerNumbersList = new LinkedList<String>(list);
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return registerNumbersList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentForCJCTransaction#getStudentsForSelectedSubject(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public Map<String, StudentTO> getStudentsForSelectedSubject( String academicYear, String lastRegisterNo,
			int noOfStudents,int subjectId, String campusId) throws Exception {
		Session session = null;
		Map<String,StudentTO> studentMap= new HashMap<String, StudentTO>();
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select s.registerNo,s.id,s.classSchemewise.classes.name,s.classSchemewise.classes.id," +
					" subGroup.subject.id as subjectId,subGroup.subject.name as subName,subGroup.subject.code as subCode" +
					" from Student s " +
					" left join s.examStudentDetentionRejoinDetails exam" +
					" left join s.admAppln.applicantSubjectGroups appSub" +
				    " left join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
					" where s.isAdmitted=1 and s.isActive=1 and (s.isHide=0 or s.isHide is null)" ;
					if(lastRegisterNo!=null && !lastRegisterNo.isEmpty()){
						hqlQuery= hqlQuery+ " and s.registerNo> '"+lastRegisterNo+"'";
					}
					hqlQuery= hqlQuery+ " and s.admAppln.isCancelled=0  and subGroup.subject.id=" +subjectId+
					" and subGroup.isActive =1"+
					" and appSub.subjectGroup.course.workLocation.id="+Integer.parseInt(campusId) +
					" and s.classSchemewise.curriculumSchemeDuration.academicYear=" +Integer.parseInt(academicYear)+
					" and (exam.detain is null or exam.discontinued is null)" +
					" and (exam.rejoin = 1 or exam.rejoin is null)" +
					" order by s.registerNo";
			Query query = session.createQuery(hqlQuery);
			query.setMaxResults(noOfStudents);
			List<Object[]>regNoList =  query.list();
			if(regNoList!=null && !regNoList.isEmpty()){
			
				Iterator<Object[]> iterator = regNoList.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					StudentTO to = new StudentTO();
					to.setStudentId(Integer.parseInt(obj[1].toString()));
					to.setClassName(obj[2].toString());
					to.setClassId(Integer.parseInt(obj[3].toString()));
					to.setSubjectId(obj[4].toString());
					to.setRegisterNo(obj[0].toString());
					to.setSubjectName(obj[5].toString()+" "+obj[6].toString());
					studentMap.put(obj[0].toString(), to);
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return studentMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentForCJCTransaction#getSubjectDetailsOnDateAndSession(com.kp.cms.forms.examallotment.ExamRoomAllotmentForCJCForm)
	 */
	@Override
	public List<Object[]> getSubjectDetailsOnDateAndSession( ExamRoomAllotmentForCJCForm objForm) throws Exception {
		Session session = null;
		List<Object[]> subjectBoList = new ArrayList<Object[]>();
		try{
			session = HibernateUtil.getSession();
			String str ="select subject.id,subject.name,subject.code from EXAM_time_table t" +
					" join EXAM_exam_course_scheme_details ON t.exam_course_scheme_id = EXAM_exam_course_scheme_details.id" +
					" join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id" +
					" join EXAM_sessions ON t.exam_session_id = EXAM_sessions.id" +
					" join subject ON t.subject_id = subject.id" +
					" where t.is_active =1" +
					" and EXAM_definition.id= " +objForm.getExamid()+
					" and EXAM_sessions.id ='" +objForm.getSessionId()+"'"+
					" and date_format(t.date_starttime,'%Y-%m-%d') = '"+CommonUtil.ConvertStringToSQLDate(objForm.getAllottedDate())+"'";
					Query query = session.createSQLQuery(str);
			subjectBoList = query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return subjectBoList;
	}
}
