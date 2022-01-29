package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.transactions.exam.ISupplementaryDataCreationTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class SupplementaryDataCreationImpl implements ISupplementaryDataCreationTransaction {
	private static volatile SupplementaryDataCreationImpl impl = null;
	public static SupplementaryDataCreationImpl getInstance(){
		if(impl == null){
			impl = new SupplementaryDataCreationImpl();
			return impl;
		}
		return impl;
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ISupplementaryDataCreationTransaction#getDataByQuery(java.lang.String)
	 */
	public List getDataByQuery(String query) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ISupplementaryDataCreationTransaction#getMinMarksMap(com.kp.cms.to.attendance.ClassesTO)
	 */
	@Override
	public Map<Integer, SubjectMarksTO> getMinMarksMap(ClassesTO classTO)
			throws Exception {
		Map<Integer, SubjectMarksTO> map=new HashMap<Integer, SubjectMarksTO>();
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			String query="select s.subject.id,s.theoryEseMinimumMark,s.practicalEseMinimumMark,s.theoryEseMaximumMark,s.practicalEseMaximumMark,s.subjectFinalMinimum" +
			" from SubjectRuleSettings s" +
			" where s.isActive=1" +
			" and s.schemeNo="+classTO.getTermNo()+
			" and s.course.id="+classTO.getCourseId()+
			" and s.academicYear="+classTO.getYear();

			Query selectedCandidatesQuery=session.createQuery(query);
			list = selectedCandidatesQuery.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] obj= (Object[]) itr.next();
					SubjectMarksTO sto=new SubjectMarksTO();
					if(obj[0]!=null)
						sto.setSubjectId(Integer.parseInt(obj[0].toString()));
					if(!map.containsKey(sto.getSubjectId())){
						if(obj[1]!=null)
							sto.setTheoryRegMin(obj[1].toString());
						if(obj[2]!=null)
							sto.setPracticalRegMin(obj[2].toString());
						if(obj[3]!=null)
							sto.setFinalTheoryMarks(obj[3].toString());
						if(obj[4]!=null)
							sto.setFinalPracticalMarks(obj[4].toString());
						if(obj[5]!=null)
							sto.setSubjectFinalMinimum(obj[5].toString());
						map.put(sto.getSubjectId(),sto);
					}
				}
			}
			return map;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ISupplementaryDataCreationTransaction#getExcludedFromResultSubjects(int, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Integer> getExcludedFromResultSubjects(int courseId, Integer termNo, Integer academicYear) throws Exception {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			String HQL_QUERY = "select e.subjectId from ExamSubDefinitionCourseWiseBO e"
								+ " where e.courseId = :courseId and e.schemeNo = " + termNo + 
								" and (dontAddTotMarkClsDecln = 1 or dontConsiderFailureTotalResult=1) and e.academicYear = :academicYear" ;
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			List<Integer> list = query.list();
			List<Integer> excludedSubject = new ArrayList<Integer>();
			if (list != null && list.size() > 0) {
				Iterator<Integer> itr = list.iterator();
				while (itr.hasNext()) {
					Integer subjectId = (Integer) itr.next();
					excludedSubject.add(subjectId);
				}
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return excludedSubject;			
		} catch (Exception e) {
			throw new Exception();
		}
	}
	@Override
	public List<Integer> getExcludedFromTotResultSubjects(int courseId, Integer schemeNo, Integer academicYear) throws Exception {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
	
			String HQL_QUERY = "select e.subjectId from ExamSubDefinitionCourseWiseBO e"
								+ " where e.courseId = :courseId and e.schemeNo = " + schemeNo + 
								" and dontConsiderFailureTotalResult = 1 and e.academicYear = :academicYear" ;
	
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			
			List<Integer> list = query.list();
			
			List<Integer> excludedSubject = new ArrayList<Integer>();
			if (list != null && list.size() > 0) {
	
				Iterator<Integer> itr = list.iterator();
				while (itr.hasNext()) {
					Integer subjectId = (Integer) itr.next();
					excludedSubject.add(subjectId);
				}
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return excludedSubject;			
		} catch (Exception e) {
			throw new Exception();
		}

	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ISupplementaryDataCreationTransaction#deleteOldRecords(int, int)
	 */
	@Override
	public boolean deleteOldRecords(int id, int examId) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
		    tx= session.beginTransaction();
			Query query = session.createQuery(" delete from StudentSupplementaryImprovementApplication s where s.examDefinition.id = " + examId + " and s.classes.id = " + id);
   		    query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ISupplementaryDataCreationTransaction#getDataByStudentIdAndClassId(int, int, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getDataByStudentIdAndClassId(int studentId, int classId, List<Integer> subjectIdList) throws Exception {
		Session session = null;
		List<Object[]> list = null;
		try{
			session  = HibernateUtil.getSession();
			String str = "select subject.id as subjectID,"+
							" theory_marks,practical_marks"+
							" from EXAM_marks_entry_details ed"+
							" INNER JOIN EXAM_marks_entry entry ON ed.marks_entry_id = entry.id"+
							" INNER JOIN student ON entry.student_id = student.id"+
							" INNER JOIN subject ON ed.subject_id = subject.id"+
							" INNER JOIN classes ON entry.class_id = classes.id"+
							" INNER JOIN EXAM_definition ON entry.exam_id = EXAM_definition.id"+
							" INNER JOIN exam_type ON EXAM_definition.exam_type_id = exam_type.id"+
							" where student.id="+studentId+
							" AND subject.id  in(:studentIdList)"+
							" AND (classes.id is null OR classes.id="+classId+")"+
							" AND exam_type.name!='Internal'"+
							" order by EXAM_definition.year desc,EXAM_definition.month desc";
			Query query = session.createSQLQuery(str);
			query.setParameterList("studentIdList", subjectIdList);
			list = query.list();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
		return list;
	}
	@Override
	public Map<Integer, String> getSubjectSections(int courseId, Integer termNo, Integer year) throws Exception {
		Session session = null;
		List<Object[]> list = null;
		Map<Integer, String> subjectSectionsMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String str="select subject.id as subID,"+
						" EXAM_subject_sections.name as SectionName"+
						" from EXAM_sub_definition_coursewise"+
						" INNER JOIN  subject on EXAM_sub_definition_coursewise.subject_id = subject.id"+
						" INNER JOIN course ON EXAM_sub_definition_coursewise.course_id = course.id"+
						" INNER JOIN EXAM_subject_sections ON EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id"+
						" INNER JOIN classes on classes.course_id = course.id"+
						" INNER JOIN class_schemewise on class_schemewise.class_id = classes.id"+
						" INNER JOIN curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id"+
						" and EXAM_sub_definition_coursewise.scheme_no=classes.term_number"+
						" and EXAM_sub_definition_coursewise.academic_year=curriculum_scheme_duration.academic_year"+
						" where course.id="+courseId+
						" and EXAM_sub_definition_coursewise.scheme_no="+termNo+
						" and EXAM_sub_definition_coursewise.academic_year="+year+
						" group by subject.id"+
						" order by EXAM_subject_sections.name";
			Query query = session.createSQLQuery(str);
			list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					subjectSectionsMap.put(Integer.parseInt(obj[0].toString()),obj[1].toString());
				}
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return subjectSectionsMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ISupplementaryDataCreationTransaction#saveSupplimentryStudentsData(java.util.List)
	 */
	@Override
	public boolean saveSupplimentryStudentsData( List<StudentSupplementaryImprovementApplication> boList)
			throws Exception {
		Session session =null;
		Transaction tx = null;
		boolean isUpdate = false;
		try{
			session = HibernateUtil.getSession();
			tx = session .beginTransaction();
			tx.begin();
			if(boList!=null && !boList.isEmpty()){
				Iterator<StudentSupplementaryImprovementApplication> iterator = boList.iterator();
				while (iterator.hasNext()) {
					StudentSupplementaryImprovementApplication bo = (StudentSupplementaryImprovementApplication) iterator .next();
					session.save(bo);
				}
				tx.commit();
				session.flush();
				isUpdate = true;
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return isUpdate;
	}
}
