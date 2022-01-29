package com.kp.cms.transactionsimpl.studentLogin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm;
import com.kp.cms.transactions.studentLogin.ISyllabusDisplayForStudentTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class SyllabusDisplayForStudentTransactionImpl implements ISyllabusDisplayForStudentTransaction{
	private static final Log log = LogFactory.getLog(SyllabusDisplayForStudentTransactionImpl.class);	
	public static volatile SyllabusDisplayForStudentTransactionImpl syllabusDisplayForStudentTIMPL = null;
	
	/**
	 * @return
	 */
	public static SyllabusDisplayForStudentTransactionImpl getInstance() {
		if (syllabusDisplayForStudentTIMPL == null) {
			syllabusDisplayForStudentTIMPL = new SyllabusDisplayForStudentTransactionImpl();
		}
		return syllabusDisplayForStudentTIMPL;
	}

	
	

/* (non-Javadoc)
 * @see com.kp.cms.transactions.studentLogin.ISyllabusDisplayForStudentTransaction#getConsolidateMarksCardBo(com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm)
 */
public List<Object[]> getConsolidateMarksCardBo(SyllabusDisplayForStudentForm form ) throws Exception {
	Session session = null;
	try {
		session = HibernateUtil.getSession();
		
		List<Object[]> objArray =null;
		Query query=session.createQuery("select cCsd.semesterYearNo,cCsd.endDate,c.subjectCode,c.subjectName,cours.id,cus.year "+
																	" from ConsolidateMarksCard c "+
																	" inner join c.classes.course cours" +
																	" inner join c.classes.classSchemewises cCsw "+
																	" inner join cCsw.curriculumSchemeDuration cCsd "+
																	" inner join cCsd.curriculumScheme cus "+
																	" where c.passOrFail='Fail' and c.dontConsiderFailureTotalResult=0"+
																	" and c.regNo='"+form.getRegistrNo()+"' order by c.termNumber,c.subjectCode");
		objArray=(List<Object[]>) query.list();
		session.flush();
		return objArray;
	} catch (Exception e) {
		if (session != null) {
			session.flush();
			session.close();
		}
		return null;
	}
}


/* (non-Javadoc)
 * @see com.kp.cms.transactions.studentLogin.ISyllabusDisplayForStudentTransaction#getStudentDetails(com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm)
 */
public Student getStudentDetails(String registerNumber) throws Exception {
	Session session = null;
	try {
		session = HibernateUtil.getSession();
		
		Student studentBo =(Student) session.createQuery("from Student s" +
				" where s.isAdmitted=1 and (s.isHide=0 or s.isHide is null) and s.admAppln.isCancelled=0 " +
				" and s.isActive=1 and s.registerNo='"+registerNumber+"'").uniqueResult();
		session.flush();
		return studentBo;
	} catch (Exception e) {
		if (session != null) {
			session.flush();
			session.close();
		}
		return null;
	}
}


/* (non-Javadoc)
 * @see com.kp.cms.transactions.studentLogin.ISyllabusDisplayForStudentTransaction#getCurrentStudyingBatchjoiningYear(com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm)
 */
public List<Object[]> getCurrentStudyingBatchjoiningYear(SyllabusDisplayForStudentForm form) throws Exception {
	Session session = null;
	Calendar now = Calendar.getInstance();
	int currentYear=now.get(Calendar.YEAR);
	   Date date = new Date();
	 java.sql.Date sqlDate=new java.sql.Date(date.getTime());
	 List<Object[]> objArray=null;
	try {
		session = HibernateUtil.getSession();
		Query query=session.createQuery("select csw.year,csd.id from CurriculumSchemeDuration csd "+
											" inner join csd.curriculumScheme csw "+
											" where csw.course.id="+form.getCourseid()+
											" and csd.academicYear <="+currentYear+" and csd.semesterYearNo="+form.getSemNo()+
											" and csd.startDate <='"+sqlDate+"'"+
											" order by csd.startDate desc");
		query.setMaxResults(1);
		objArray=(List<Object[]>) query.list();
		session.flush();
		return objArray;
	} catch (Exception e) {
		if (session != null) {
			session.flush();
			session.close();
		}
		return objArray;
	}
}

/* (non-Javadoc)
 * @see com.kp.cms.transactions.studentLogin.ISyllabusDisplayForStudentTransaction#getSubjectName(int, com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm)
 */
public String getSubjectName(int csdId,SyllabusDisplayForStudentForm form) throws Exception {
	Session session = null;
	 String subjectName=null;
	try {
		session = HibernateUtil.getSession();
		if(form.getPaperCode()!=null && !form.getPaperCode().isEmpty() && csdId!=0){
		Query query=session.createQuery("select s.name from CurriculumSchemeSubject css "+
											" inner join css.subjectGroup.subjectGroupSubjectses sgs "+
											" inner join sgs.subject s "+
											" where css.curriculumSchemeDuration.id="+csdId+
											" and s.code='"+form.getPaperCode()+"'");
		
		subjectName=(String)query.uniqueResult();
		session.flush();
		}
		return subjectName;
	} catch (Exception e) {
		if (session != null) {
			session.flush();
			session.close();
		}
		return subjectName;
	}
}
	
}
