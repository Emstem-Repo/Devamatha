package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.exam.IUploadInternalOverAllTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class UploadInternalOverAllTransactionImpl implements
		IUploadInternalOverAllTransaction {

	@Override
	public Map<String, Integer> getStudentMap() throws Exception {
		Map<String,Integer> studentMap=new HashMap<String, Integer>();
		Session session = null;
		List<Object[]> student = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("select s.registerNo,s.id from Student s where s.registerNo is not null" +
					" and s.registerNo !='' and s.isActive=1 and s.admAppln.isCancelled=0");
			student = selectedCandidatesQuery.list();
			if(student!=null && !student.isEmpty()){
				Iterator<Object[]> itr=student.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null && objects[1]!=null){
						studentMap.put(objects[0].toString(),Integer.parseInt(objects[1].toString()));
					}
				}
			}
			return studentMap;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	@Override
	public Map<String, Integer> getclassMap() throws Exception {
		Map<String,Integer> studentMap=new HashMap<String, Integer>();
		Session session = null;
		List<Object[]> student = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("select schemewise.classes.name,schemewise.curriculumSchemeDuration.academicYear,schemewise.classes.id" +
					" from ClassSchemewise schemewise where schemewise.classes.course.isActive = 1 and schemewise.classes.isActive = 1 ");
			student = selectedCandidatesQuery.list();
			if(student!=null && !student.isEmpty()){
				Iterator<Object[]> itr=student.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null && objects[1]!=null && objects[2]!=null){
						studentMap.put(objects[0].toString().trim()+"_"+objects[1].toString().trim(),Integer.parseInt(objects[2].toString()));
					}
				}
			}
			return studentMap;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	@Override
	public Map<String, Integer> getSubjectCodeMap() throws Exception {
		Map<String,Integer> studentMap=new HashMap<String, Integer>();
		Session session = null;
		List<Object[]> subject = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("select s.code,s.id from Subject s where s.isActive=1 and s.code!='' and s.code is not null");
			subject = selectedCandidatesQuery.list();
			if(subject!=null && !subject.isEmpty()){
				Iterator<Object[]> itr=subject.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null && objects[1]!=null){
						studentMap.put(objects[0].toString(),Integer.parseInt(objects[1].toString()));
					}
				}
			}
			return studentMap;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	@Override
	public Map<String, Integer> getExamMap() throws Exception {
		Map<String,Integer> examMap=new HashMap<String, Integer>();
		Session session = null;
		List<Object[]> student = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("select e.name,e.id from ExamDefinitionBO e where e.isActive=1");
			student = selectedCandidatesQuery.list();
			if(student!=null && !student.isEmpty()){
				Iterator<Object[]> itr=student.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null && objects[1]!=null){
						examMap.put(objects[0].toString(),Integer.parseInt(objects[1].toString()));
					}
				}
			}
			return examMap;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	@Override
	public boolean saveExamStudentOverallInternalMarkDetailsBOList(
			List<ExamStudentOverallInternalMarkDetailsBO> list,String action)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		ExamStudentOverallInternalMarkDetailsBO markDetailsBO;
		int count = 0;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamStudentOverallInternalMarkDetailsBO> tcIterator = list.iterator();
			
			while(tcIterator.hasNext()){
				markDetailsBO = tcIterator.next();
				ExamStudentOverallInternalMarkDetailsBO oldDetails=getOldMarkDetails(markDetailsBO.getStudentId(), markDetailsBO.getSubjectId(), markDetailsBO.getClassId(),markDetailsBO.getExamId());
				if(oldDetails!=null)
				{
					if(action.equalsIgnoreCase("internal"))
					{	
						oldDetails.setTheoryTotalSubInternalMarks(markDetailsBO.getTheoryTotalSubInternalMarks());
						oldDetails.setPracticalTotalSubInternalMarks(markDetailsBO.getPracticalTotalSubInternalMarks());
					}
					else
					if(action.equalsIgnoreCase("attendance"))
					{
						oldDetails.setTheoryTotalAttendenceMarks(markDetailsBO.getTheoryTotalAttendenceMarks());
						oldDetails.setPracticalTotalAttendenceMarks(markDetailsBO.getPracticalTotalAttendenceMarks());
					}
					double theoryTotalMarks=0;
					if(oldDetails.getTheoryTotalSubInternalMarks()!=null && CommonUtil.isValidDecimal(oldDetails.getTheoryTotalSubInternalMarks())){
						theoryTotalMarks=Double.parseDouble(oldDetails.getTheoryTotalSubInternalMarks());
						theoryTotalMarks=Math.round(theoryTotalMarks);
					}
					if(oldDetails.getTheoryTotalAttendenceMarks()!=null && CommonUtil.isValidDecimal(oldDetails.getTheoryTotalAttendenceMarks())){
						theoryTotalMarks+=Double.parseDouble(oldDetails.getTheoryTotalAttendenceMarks());
						theoryTotalMarks=Math.round(theoryTotalMarks);
						}
					oldDetails.setTheoryTotalMarks(String.valueOf(theoryTotalMarks));
					
					double practicalTotalMarks=0;
					if(oldDetails.getPracticalTotalSubInternalMarks()!=null && CommonUtil.isValidDecimal(oldDetails.getPracticalTotalSubInternalMarks())){
						practicalTotalMarks=Double.parseDouble(oldDetails.getPracticalTotalSubInternalMarks());
						practicalTotalMarks=Math.round(practicalTotalMarks);
					}
					if(oldDetails.getPracticalTotalAttendenceMarks()!=null && CommonUtil.isValidDecimal(oldDetails.getPracticalTotalAttendenceMarks())){
						practicalTotalMarks+=Double.parseDouble(oldDetails.getPracticalTotalAttendenceMarks());
						practicalTotalMarks=Math.round(practicalTotalMarks);
					}
					oldDetails.setPracticalTotalMarks(String.valueOf(practicalTotalMarks));
					
					oldDetails.setPassOrFail(markDetailsBO.getPassOrFail());
					oldDetails.setModifiedBy(markDetailsBO.getModifiedBy());
					oldDetails.setLastModifiedDate(new Date());
					session.update(oldDetails);
				}
				else
				{	
					session.save(markDetailsBO);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

	@Override
	public boolean checkDuplicate(String studentId, String subjectId,
			String classId, String examId) throws Exception {
		Session session = null;
		boolean isNotExist=true;
		try {
			session = HibernateUtil.getSession();
			List<Object> selectedCandidatesQuery=session.createQuery("from ExamStudentOverallInternalMarkDetailsBO e where e.classId="+classId+" and e.subjectId="+subjectId
					+" and e.studentId="+studentId+" and e.examId="+examId).list();
			if(selectedCandidatesQuery!=null && !selectedCandidatesQuery.isEmpty()){
				isNotExist=false;
			}
			return isNotExist;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	public ExamStudentOverallInternalMarkDetailsBO getOldMarkDetails(int studentId, int subjectId,
			int classId, int examId) throws Exception {
		Session session = null;
		ExamStudentOverallInternalMarkDetailsBO oldDetails=null;
		try {
			session = HibernateUtil.getSession();
			oldDetails=(ExamStudentOverallInternalMarkDetailsBO)session.createQuery("from ExamStudentOverallInternalMarkDetailsBO e where e.classId="+classId+" and e.subjectId="+subjectId+" and e.studentId="+studentId+" and e.examId="+examId).uniqueResult();
			return oldDetails;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.clear();
			}
		}
		
	}

	@Override
	public boolean uploadESEOverAllData(List<ExamStudentFinalMarkDetailsBO> eseList)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		ExamStudentFinalMarkDetailsBO markDetailsBO;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamStudentFinalMarkDetailsBO> tcIterator = eseList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				markDetailsBO = tcIterator.next();
				ExamStudentFinalMarkDetailsBO oldDetails=getOldESEMarkDetails(markDetailsBO.getStudentId(), markDetailsBO.getSubjectId(), markDetailsBO.getClassId(),markDetailsBO.getExamId());
				ExamSubjectRuleSettingsBO bo=getExamSubjectRuleSettingsBo(markDetailsBO.getSubjectId(),markDetailsBO.getClassId());
				BigDecimal minMarks=new BigDecimal(0);
				BigDecimal praMinMarks=new BigDecimal(0);
				if(bo!=null){
					if(bo.getTheoryEseMinimumMark()!=null){
						minMarks=bo.getTheoryEseMinimumMark();
					}
					if(bo.getPracticalEseMinimumMark()!=null){
						praMinMarks=bo.getPracticalEseMinimumMark();
					}
				}
				if(oldDetails!=null)
				{
					oldDetails.setStudentTheoryMarks(markDetailsBO.getStudentTheoryMarks());
					oldDetails.setStudentPracticalMarks(markDetailsBO.getStudentPracticalMarks());
					oldDetails.setLastModifiedDate(markDetailsBO.getLastModifiedDate());
					oldDetails.setModifiedBy(markDetailsBO.getModifiedBy());
					oldDetails.setPassOrFail(markDetailsBO.getPassOrFail());
					oldDetails.setSubjectTheoryMark(String.valueOf(minMarks));
					oldDetails.setSubjectPracticalMark(String.valueOf(praMinMarks));
					session.update(oldDetails);
				}
				else
				{	
					session.save(markDetailsBO);
				}	
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

	private ExamSubjectRuleSettingsBO getExamSubjectRuleSettingsBo(
			int subjectId, int classId) throws Exception {
		Session session = null;
		ExamSubjectRuleSettingsBO sett=null;
		try {
			session = HibernateUtil.getSession();
			sett=(ExamSubjectRuleSettingsBO)session.createQuery("from ExamSubjectRuleSettingsBO bo" +
					" where bo.academicYear=( select c.curriculumSchemeDuration.academicYear " +
					" from ClassSchemewise c where c.classes.isActive=1 and c.classes.id="+classId+")" +
					" and bo.isActive=1and bo.courseId=( select c1.course.id " +
					" from Classes c1 where c1.isActive=1 and c1.id="+classId+")" +
					" and bo.schemeNo=(" +
					"select c1.termNumber from Classes c1 where c1.isActive=1 and c1.id="+classId +
					") and bo.subjectId="+subjectId).uniqueResult();
			return sett;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.clear();
			}
		}
		
	}

	public ExamStudentFinalMarkDetailsBO getOldESEMarkDetails(int studentId, int subjectId,
			int classId, int examId) throws Exception {
		Session session = null;
		ExamStudentFinalMarkDetailsBO oldDetails=null;
		try {
			session = HibernateUtil.getSession();
			oldDetails=(ExamStudentFinalMarkDetailsBO)session.createQuery("from ExamStudentFinalMarkDetailsBO e where e.classId="+classId+" and e.subjectId="+subjectId+" and e.studentId="+studentId+" and e.examId="+examId).uniqueResult();
			return oldDetails;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.clear();
			}
		}
		
	}
}
