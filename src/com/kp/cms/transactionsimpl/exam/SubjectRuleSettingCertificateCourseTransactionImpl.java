package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.to.exam.SubjectRuleSettingCertificateCourseTo;
import com.kp.cms.transactions.exam.ISubjectRuleSettingCertificateCourseTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SubjectRuleSettingCertificateCourseTransactionImpl implements	ISubjectRuleSettingCertificateCourseTransaction {
	private static final Log log = LogFactory
	.getLog(SubjectRuleSettingCertificateCourseTransactionImpl.class);

public static volatile SubjectRuleSettingCertificateCourseTransactionImpl obImpl = null;

public static SubjectRuleSettingCertificateCourseTransactionImpl getInstance() {
if (obImpl == null) {
	obImpl = new SubjectRuleSettingCertificateCourseTransactionImpl();
     }
      return obImpl;
     }

/* (non-Javadoc)
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#getPhdDetailsList(com.kp.cms.forms.phd.PhdEmployeeForms)
 */
@Override
public List<CertificateCourse> getCertificateCourseList(SubjectRuleSettingsForm objform) throws Exception {
	Session session=null;
	List<CertificateCourse> cerBiList;
	try{
		 session = HibernateUtil.getSession();
		 String str=" from CertificateCourse cer where cer.isActive=1";

		 if(objform.getAcademicYear()!=null && !objform.getAcademicYear().isEmpty()){
	         str=str+" and cer.year="+objform.getAcademicYear();
           }
		 if(objform.getSchemeType()!=null && !objform.getSchemeType().isEmpty()){
			 if(objform.getSchemeType().equalsIgnoreCase("1")){
				 str=str+" and cer.semType like '%ODD%'";
			 } else if(objform.getSchemeType().equalsIgnoreCase("2")){
				 str=str+" and cer.semType like '%EVEN%'";
			 }
           }
		 Query query = session.createQuery(str);
		 cerBiList=query.list();
		
	}catch(Exception e){
		log.error("Unable to getCertificateCourseList",e);
		 throw e;
	}
	return cerBiList;
}

/* (non-Javadoc)
 * @see com.kp.cms.transactions.exam.ISubjectRuleSettingCertificateCourseTransaction#getSubjectGroupsForInput(com.kp.cms.forms.exam.SubjectRuleSettingCertificateCourseForms)
 */
@Override
public List<SubjectGroup> getSubjectGroupsForInput(SubjectRuleSettingsForm subjectRuleSettingsForm)
		throws Exception {
	Session session = null;
	List<SubjectGroup> selectedCandidatesList = null;
	try {
		session = HibernateUtil.getSession();
		String schemeNo="";
		String courseId ="";
		String subjectId ="";
		//String courseName ="";
		List<Object[]> allData =new ArrayList<Object[]>();
		List<Object[]> dataListt = null;
		Iterator<SubjectRuleSettingCertificateCourseTo>  itr=subjectRuleSettingsForm.getSubRulCerCourList().iterator();
		while (itr.hasNext()) {
			SubjectRuleSettingCertificateCourseTo subjectto = (SubjectRuleSettingCertificateCourseTo) itr.next();
			if(subjectto.getChecked()!=null && !subjectto.getChecked().isEmpty()){
				
				String query=" select distinct adm_appln.selected_course_id,classes.term_number,subject.id,course.name"+
				" from student inner join adm_appln ON student.adm_appln_id = adm_appln.id inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
				" inner join classes ON class_schemewise.class_id = classes.id inner join applicant_subject_group on applicant_subject_group.adm_appln_id = adm_appln.id " +
				" inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id " +
				" and subject_group_subjects.is_active=1 inner join subject ON subject_group_subjects.subject_id = subject.id inner join course on adm_appln.selected_course_id = course.id" +
				" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
				" where subject.id in ("+subjectto.getSubjectId()+") and adm_appln.is_cancelled='0'" +
				" and curriculum_scheme_duration.academic_year="+subjectRuleSettingsForm.getAcademicYear();
				Query selectedCourseQuery=session.createSQLQuery(query);
				dataListt = selectedCourseQuery.list();
				allData.addAll(dataListt);
			}
		}
		subjectRuleSettingsForm.setAllDatas(allData);
		Iterator<Object[]> irr=allData.iterator();
		while (irr.hasNext()) {
			Object[] values =(Object[])irr.next();
			courseId=values[0].toString();
			schemeNo=values[1].toString();
			subjectId=values[2].toString();
			//courseName=values[3].toString();
		
		 
	   String query="select cs.subjectGroup from CurriculumSchemeDuration c left join c.curriculumSchemeSubjects cs where c.curriculumScheme.course.id in ("+courseId+") and c.academicYear ="+subjectRuleSettingsForm.getAcademicYear() +
				" and c.semesterYearNo in ("+schemeNo+") group by cs.subjectGroup.id";
		Query selectedCandidatesQuery=session.createQuery(query);
		selectedCandidatesList = selectedCandidatesQuery.list();
		if(selectedCandidatesList==null || selectedCandidatesList.isEmpty()){
			return selectedCandidatesList;
		}
	   }
	} catch (Exception e) {
		log.error("Error while retrieving selected candidates.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
	return selectedCandidatesList;
}

@Override
public boolean addAll(List<SubjectRuleSettings> bos) throws Exception {
	log.debug("inside addTermsConditionCheckList");
	Session session = null;
	Transaction transaction = null;
	SubjectRuleSettings bo;
	try {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		transaction.begin();
		Iterator<SubjectRuleSettings> tcIterator = bos.iterator();
		int count = 0;
		while(tcIterator.hasNext()){
			bo = tcIterator.next();
			session.saveOrUpdate(bo);
			if(++count % 20 == 0){
				session.flush();
				session.clear();
			}
		}
		
		transaction.commit();
		session.flush();
		//session.close();
		log.debug("leaving addTermsConditionCheckList");
		return true;
	} catch (ConstraintViolationException e) {
		transaction.rollback();
		log.error("Error in addTermsConditionCheckList impl...", e);
		throw new BusinessException(e);
	} catch (Exception e) {
		transaction.rollback();
		log.error("Error in addTermsConditionCheckList impl...", e);
		throw new ApplicationException(e);
	}
}

@Override
public List<ExamSubjectRuleSettingsBO> getSubjectrulesIs(String academicYear,String courseId, String schemeNo, String subjectId) throws Exception {
	Session session = null;
	List<ExamSubjectRuleSettingsBO> ruleId = null;
	try {
		session = HibernateUtil.getSession();
		String query="select e from ExamSubjectRuleSettingsBO e" +
		" where e.academicYear= " +academicYear+
		" and e.subjectId ="+subjectId+" and e.schemeNo ="+schemeNo+
		" and e.courseId="+courseId;
		Query selectedCandidatesQuery=session.createQuery(query);
		ruleId = selectedCandidatesQuery.list();
		return ruleId;
		
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
public List<Subject> getSubjectsByCourseYearSemester(String academicYear,String courseId,String schemeNo,String subjectId) throws Exception{
	Session session = null;
	List<Subject> subjects = null;
	try {
		session = HibernateUtil.getSession();
		String query="select ss.subject from CurriculumSchemeDuration cd" +
				" join cd.curriculumSchemeSubjects cs" +
				" join cs.subjectGroup.subjectGroupSubjectses ss" +
				" where cd.semesterYearNo="+schemeNo+" and cd.academicYear="+academicYear+
				" and cd.curriculumScheme.course.id="+courseId+" and ss.isActive=1 and ss.subject.isActive=1 and ss.subject.id ="+subjectId+" group by ss.subject.id";
		Query selectedCandidatesQuery=session.createQuery(query);
		subjects = selectedCandidatesQuery.list();
		return subjects;
	} catch (Exception e) {
		log.error("Error while retrieving selected candidates.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
}

}
