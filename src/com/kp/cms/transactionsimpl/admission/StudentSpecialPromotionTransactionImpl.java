package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.StudentSpecialPromotionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IStudentSpecialPromotionTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class StudentSpecialPromotionTransactionImpl implements
		IStudentSpecialPromotionTransaction {

	/**
	 * Singleton object of StudentSpecialPromotionHelper
	 */
	private static volatile StudentSpecialPromotionTransactionImpl studentSpecialPromotionTransactionImpl = null;
	private static final Log log = LogFactory.getLog(StudentSpecialPromotionTransactionImpl.class);
	private StudentSpecialPromotionTransactionImpl() {
		
	}
	/**
	 * return singleton object of studentSpecialPromotionHelper.
	 * @return
	 */
	public static StudentSpecialPromotionTransactionImpl getInstance() {
		if (studentSpecialPromotionTransactionImpl == null) {
			studentSpecialPromotionTransactionImpl = new StudentSpecialPromotionTransactionImpl();
		}
		return studentSpecialPromotionTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentSpecialPromotionTransaction#getStudentDetails(java.lang.String)
	 */
	@Override
	public List<Student> getStudentDetails(String query) throws Exception {
		Session session = null;
		List<Student> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	@Override
	public boolean updateStudentsDetails(
			StudentSpecialPromotionForm studentSpecialPromotionForm)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ClassSchemewise c=(ClassSchemewise)session.get(ClassSchemewise.class, Integer.parseInt(studentSpecialPromotionForm.getPromotionClassId()));
			Course c1=(Course)session.createQuery("select c.classes.course from ClassSchemewise c where c.id="+studentSpecialPromotionForm.getPromotionClassId()).uniqueResult();
			Iterator<StudentTO> itr = studentSpecialPromotionForm.getList().iterator();
			int count = 0;
			List<Integer> subList=session.createQuery("select cs.subjectGroup.id from CurriculumSchemeDuration csd" +
					" join csd.curriculumSchemeSubjects cs " +
					" join csd.classSchemewises c" +
					" where cs.subjectGroup.isCommonSubGrp=1 and c.id="+c.getId()).list();
			while(itr.hasNext()){
				StudentTO to = itr.next();
				if(to.getChecked1()!=null && to.getChecked1().equals("on")){
					Student bo=(Student)session.get(Student.class, to.getId());
					bo.setModifiedBy(studentSpecialPromotionForm.getUserId());
					bo.setLastModifiedDate(new Date());
					ExamStudentPreviousClassDetailsBO previousClass=new ExamStudentPreviousClassDetailsBO();
					previousClass.setIsActive(true);
					previousClass.setCreatedBy(studentSpecialPromotionForm.getUserId());
					previousClass.setCreatedDate(new Date());
					previousClass.setModifiedBy(studentSpecialPromotionForm.getUserId());
					previousClass.setLastModifiedDate(new Date());
					previousClass.setAcademicYear(Integer.parseInt(studentSpecialPromotionForm.getAcademicYear()));
					previousClass.setSchemeNo(bo.getClassSchemewise().getClasses().getTermNumber());
					previousClass.setClassId(bo.getClassSchemewise().getClasses().getId());
					previousClass.setStudentId(bo.getId());
					Set<ApplicantSubjectGroup> subSet=bo.getAdmAppln().getApplicantSubjectGroups();
					if(subSet!=null && !subSet.isEmpty()){
						Iterator<ApplicantSubjectGroup> subitr=subSet.iterator();
						while (subitr.hasNext()) {
							ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) subitr.next();
							ExamStudentSubGrpHistoryBO historyBo=new ExamStudentSubGrpHistoryBO();
							historyBo.setSchemeNo(bo.getClassSchemewise().getClasses().getTermNumber());
							historyBo.setStudentId(bo.getId());
							historyBo.setSubjectGroupId(applicantSubjectGroup.getSubjectGroup().getId());
							historyBo.setCreatedBy(studentSpecialPromotionForm.getUserId());
							historyBo.setCreatedDate(new Date());
							historyBo.setModifiedBy(studentSpecialPromotionForm.getUserId());
							historyBo.setLastModifiedDate(new Date());
							List subjHisList=session.createQuery("from ExamStudentSubGrpHistoryBO e where e.studentId=:studentId and e.schemeNo=:schemeNo and e.subjectGroupId=:gid").setInteger("studentId",historyBo.getStudentId()).setInteger("schemeNo",historyBo.getSchemeNo()).setInteger("gid",historyBo.getSubjectGroupId()).list();
							if(subjHisList==null || subjHisList.isEmpty())
								session.save(historyBo);
						}
					}
					Set<ApplicantSubjectGroup> sub=new HashSet<ApplicantSubjectGroup>();
					if(subList!=null && !subList.isEmpty()){
						Iterator<Integer> it=subList.iterator();
						while (it.hasNext()) {
							Integer id = (Integer) it.next();
							ApplicantSubjectGroup s=new ApplicantSubjectGroup();
							SubjectGroup sg=new SubjectGroup();
							sg.setId(id);
							AdmAppln a=bo.getAdmAppln();
							s.setSubjectGroup(sg);
							s.setAdmAppln(a);
							sub.add(s);
						}
					}
					if(bo.getAdmAppln().getPersonalData().getSecondLanguage()!=null){
						Integer id=(Integer)session.createQuery("select cs.subjectGroup.id from CurriculumSchemeDuration csd" +
							" join csd.curriculumSchemeSubjects cs join csd.classSchemewises c" +
							" where c.id="+c.getId()+" and cs.subjectGroup.secondLanguageId=(select b.id " +
							" from ExamSecondLanguageMasterBO b where b.name='"+bo.getAdmAppln().getPersonalData().getSecondLanguage()+"')").uniqueResult();
						if(id!=null){
						ApplicantSubjectGroup s=new ApplicantSubjectGroup();
						SubjectGroup sg=new SubjectGroup();
						sg.setId(id);
						AdmAppln a=bo.getAdmAppln();
						s.setSubjectGroup(sg);
						s.setAdmAppln(a);
						sub.add(s);
						}
					}
					if(!sub.isEmpty()){
						bo.getAdmAppln().setApplicantSubjectGroups(sub);
					}else{
						bo.getAdmAppln().setApplicantSubjectGroups(null);
					}
					bo.setClassSchemewise(c);
					if(bo.getAdmAppln().getCourseBySelectedCourseId().getId()!=c1.getId()){
						bo.getAdmAppln().setCourseBySelectedCourseId(c1);
					}
					List previousList=session.createQuery("from ExamStudentPreviousClassDetailsBO e where e.studentId=:studentId and e.academicYear=:year and e.classId=:classId and e.schemeNo=:schemeNo").setInteger("studentId",previousClass.getStudentId()).setInteger("year",previousClass.getAcademicYear())
					.setInteger("classId",previousClass.getClassId()).setInteger("schemeNo",previousClass.getSchemeNo()).list();
					if(previousList==null || previousList.isEmpty())
						session.save(previousClass);
					session.update(bo);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			
		} catch (ConstraintViolationException e) {
			transaction.rollback();
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentSpecialPromotionTransaction#getPromotionClasses(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getPromotionClasses(String classId,String courseId)
			throws Exception {
		Session session = null;
		Map<Integer, String> classMap=new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			List<Object[]> list=session.createQuery("select csd.semesterYearNo,csd.curriculumScheme.id,csd.academicYear" +
					" from CurriculumSchemeDuration csd" +
					" join csd.classSchemewises cs where cs.id="+classId).list();
			if(list!=null && !list.isEmpty()){
				int schemeNo=0;
				int csId=0;
				int year=0;
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if(obj[0]!=null){
						schemeNo=Integer.parseInt(obj[0].toString());
						schemeNo+=1;
					}
					if(obj[1]!=null)
						csId=Integer.parseInt(obj[1].toString());
					
					if(obj[2]!=null)
						year=Integer.parseInt(obj[2].toString());
					
					Integer ny=(Integer)session.createQuery("select csd.academicYear" +
							" from CurriculumSchemeDuration csd where csd.semesterYearNo="+schemeNo+
							" and csd.curriculumScheme.id="+csId).uniqueResult();
					int newyear=0;
					if(ny!=null)
						newyear=ny;
					
					if(newyear>0){
					List<Object[]> classList=session.createQuery("select cs.id," +
							" cs.classes.name" +
							" from CurriculumSchemeDuration csd" +
							" join csd.classSchemewises cs" +
							" where csd.academicYear=" +newyear+
							" and cs.classes.course.id=" +courseId+
							" and csd.semesterYearNo="+schemeNo).list();
					
					if(classList!=null && !classList.isEmpty()){
						Iterator<Object[]> itr1=classList.iterator();
						while (itr1.hasNext()) {
							Object[] ob = (Object[]) itr1.next();
							if(ob[0]!=null && ob[1]!=null)
								classMap.put(Integer.parseInt(ob[0].toString()), ob[1].toString());
						}
					}
					}
						
				}
			}
			session.flush();
		} catch (ConstraintViolationException e) {
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return classMap;
	}

}
