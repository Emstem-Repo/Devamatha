package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.ConductCertificateDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.ConductCertificateDetailsForm;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.transactions.admission.IConductCertificateDetailsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ConductCertificateDetailsImpl implements IConductCertificateDetailsTransaction{
	
	private static volatile ConductCertificateDetailsImpl conductCertificateDetailsImpl = null;
	private static final Log log = LogFactory.getLog(ConductCertificateDetailsImpl.class);
	private ConductCertificateDetailsImpl() {
		
	}
	/**
	 * return singleton object of ConductCertificateDetailsImpl.
	 * @return
	 */
	public static ConductCertificateDetailsImpl getInstance() {
		if (conductCertificateDetailsImpl == null) {
			conductCertificateDetailsImpl = new ConductCertificateDetailsImpl();
		}
		return conductCertificateDetailsImpl;
	}
	
	public Boolean updateStudentTCDetails(List<Student> studentList,ConductCertificateDetailsForm conductCertificateDetailsForm)
	throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			int ccNo=Integer.parseInt(conductCertificateDetailsForm.getConductCertificateNo());
			transaction = session.beginTransaction();
			transaction.begin();
			
			if(studentList!=null && !studentList.isEmpty()){
				Iterator<Student> itr = studentList.iterator();
				int count=0;
			//	int startingTCNumber = Integer.parseInt(tcDetailsForm.getStartingNumber());
				while(itr.hasNext()){
					Student stu = itr.next();
					ConductCertificateDetails bo = (ConductCertificateDetails)session.createQuery("from ConductCertificateDetails cd where cd.student.id="+stu.getId()).uniqueResult();
					//StudentTCDetails bo=(StudentTCDetails)session.createQuery("from StudentTCDetails t where t.student.id="+stu.getId()).uniqueResult();
					if(bo==null){
						bo=new ConductCertificateDetails();
						bo.setCreatedBy(conductCertificateDetailsForm.getUserId());
						bo.setCreatedDate(new Date());
					}
					bo.setLastModifiedDate(new Date());
					bo.setModifiedBy(conductCertificateDetailsForm.getUserId());
					bo.setIsActive(true);
					bo.setDateOfApplication(CommonUtil.ConvertStringToSQLDate(conductCertificateDetailsForm.getDateOfApplication()));
					bo.setCourseName(conductCertificateDetailsForm.getCourseName());
					bo.setAcademicDuration(conductCertificateDetailsForm.getAcademicDuration());
					bo.setAcademicYear(conductCertificateDetailsForm.getAcademicYear());
					bo.setClassOfLeaving(conductCertificateDetailsForm.getClassOfLeaving());
					bo.setIsAided(conductCertificateDetailsForm.getIsAided());
					bo.setCcNo(String.valueOf(ccNo));
					
					if(conductCertificateDetailsForm.getCharacterId()!=null && !conductCertificateDetailsForm.getCharacterId().isEmpty()){
						CharacterAndConduct conduct=new CharacterAndConduct();
						conduct.setId(Integer.parseInt(conductCertificateDetailsForm.getCharacterId()));
						bo.setCharacterAndConduct(conduct);
					}
					if(conductCertificateDetailsForm.getId()!=null)
						bo.setId(Integer.parseInt(conductCertificateDetailsForm.getId()));
					bo.setStudent(stu);
					Classes cls = new Classes();
					cls.setId(Integer.parseInt(conductCertificateDetailsForm.getClassId()));
					bo.setClasses(cls);
					//bo.setIsKerala(true);
						session.saveOrUpdate(bo);
						if(++count % 20 == 0){
							session.flush();
							session.clear();
						}	
						ccNo++;
				}
			}
			transaction.commit();
			session.flush();
			session.close();

			return true;
		} catch (Exception e) {
			log.error("Error during saving complete application data...", e);
			e.printStackTrace();
			if ( transaction != null){
				transaction.rollback();
				e.printStackTrace();
			}
			if (session != null){
				session.flush();
				session.close();

			}
			return false;
		}
}
	
	public Boolean updateStudentTCDetailsEdit(ConductCertificateDetailsForm conductCertificateDetailsForm)
	throws Exception {
		Session session = null;
		Session session2 = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			session2 = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			
				int count=0;
			//	int startingTCNumber = Integer.parseInt(tcDetailsForm.getStartingNumber());
				
					//ConductCertificateDetails bo = (ConductCertificateDetails)session.createQuery("from ConductCertificateDetails cd where cd.student.id="+Integer.parseInt(conductCertificateDetailsForm.getStudentId())).uniqueResult();
					ConductCertificateDetails bo=new ConductCertificateDetails();
					
					bo.setLastModifiedDate(new Date());
					bo.setModifiedBy(conductCertificateDetailsForm.getUserId());
					bo.setIsActive(true);
					bo.setDateOfApplication(CommonUtil.ConvertStringToSQLDate(conductCertificateDetailsForm.getDateOfApplication()));
					bo.setCourseName(conductCertificateDetailsForm.getCourseName());
					bo.setAcademicDuration(conductCertificateDetailsForm.getAcademicDuration());
					bo.setAcademicYear(conductCertificateDetailsForm.getAcademicYear());
					bo.setClassOfLeaving(conductCertificateDetailsForm.getClassOfLeaving());
					bo.setIsAided(conductCertificateDetailsForm.getIsAided());
					/*if(conductCertificateDetailsForm.getIsKerala().equalsIgnoreCase("yes")){
						bo.setIsKerala(true);
					}else{
						bo.setIsKerala(false);
					}*/
					
					if(conductCertificateDetailsForm.getCharacterId()!=null && !conductCertificateDetailsForm.getCharacterId().isEmpty()){
						CharacterAndConduct conduct=new CharacterAndConduct();
						conduct.setId(Integer.parseInt(conductCertificateDetailsForm.getCharacterId()));
						bo.setCharacterAndConduct(conduct);
					}
					if(conductCertificateDetailsForm.getId()!=null)
						bo.setId(Integer.parseInt(conductCertificateDetailsForm.getId()));
					Student stu = new Student();
					stu.setId(Integer.parseInt(conductCertificateDetailsForm.getStudentId()));
					bo.setStudent(stu);
					Classes cls = new Classes();
					cls.setId(Integer.parseInt(conductCertificateDetailsForm.getClassId()));
					bo.setClasses(cls);
					if(conductCertificateDetailsForm.getId()!=null)
					bo.setId(Integer.parseInt(conductCertificateDetailsForm.getId()));
						session.saveOrUpdate(bo);
						if(++count % 20 == 0){
							session.flush();
							session.clear();
						}				
				
			
			transaction.commit();
			session.flush();
			session.close();
			session2.flush();
			session2.close();

			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
				log.error("Error during saving complete application data...", e);
			}
			if (session != null){
				session.flush();
				session.close();
				session2.flush();
				session2.close();

			}
			return false;
		}
}
	
	public boolean checkCcAvailable(int studentId)
	throws Exception {
		Session session = null;
		List<StudentTCDetails> list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from ConductCertificateDetails s where s.student.id="+studentId);
			
			list = selectedCandidatesQuery.list();
			if(list.isEmpty() && list.size()==0)
				return false;
			
			else
				return true;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	public String getSubjectsForAllStudentsByClass(ConductCertificateDetailsForm conductCertificateDetailsForm)throws Exception
	{
		Session session = null;
		String subjectsStudied = null;
		try {		
			session = HibernateUtil.getSession();
			String sqlQuery = "select concat_ws(', ', course.core_in_course," + 
	   		   									    " course.complementary_in_course," + 
	   		   									    " course.common_in_course," + 
	   		   									    " course.elective_in_course) from classes classes" +
	   		   				  " inner join course course ON classes.course_id = course.id"+
	   		   				  " where classes.id = " + conductCertificateDetailsForm.getClassId();
			Query query = session.createSQLQuery(sqlQuery);			
			subjectsStudied = (String)query.uniqueResult();
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return subjectsStudied;
		
	}
	
	public ExamDefinitionBO getExamForAllStudentsByClass(ConductCertificateDetailsForm conductCertificateDetailsForm) {
		
		Session session = null;
		Transaction transaction = null;
		ExamDefinitionBO examdef=null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			String query="select distinct m.exam from ExamRegularApplication m where m.classes.id="+Integer.parseInt(conductCertificateDetailsForm.getClassId())+" and m.exam.examTypeID = 1";
			Query selectedCandidatesQuery=session.createQuery(query);
			examdef = (ExamDefinitionBO) selectedCandidatesQuery.uniqueResult();
			
		} catch (Exception e) {
			log.error("Error during saving complete application data...", e);
		}
		return examdef;
	}	
	
	public ConductCertificateDetails getStudentTCDetails(String query) throws Exception {
		Session session = null;
		ConductCertificateDetails conductCertificateDetails = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			conductCertificateDetails = (ConductCertificateDetails)selectedCandidatesQuery.uniqueResult();
			return conductCertificateDetails;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	public List<Student> getStudentList(String classId, String studentId) throws Exception {
		Session session=null;
		try {
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer(
					"from Student s where  "	);
			
			
				sqlString.append(" s.admAppln.isSelected=1 and s.admAppln.isApproved=1 ");
			
			//OR ( s.student.admAppln.isCancelled = 1 and s.student.admAppln.isApproved=1)
			//" and s.tcNo is null"
			if(classId!=null && !classId.trim().isEmpty() && (studentId==null || studentId.trim().isEmpty()) || studentId.equalsIgnoreCase("undefined"))
				sqlString.append(" and s.classSchemewise.id="+classId);
						
			
			;
			if(studentId!=null && !studentId.trim().isEmpty())
			{
				if(studentId.equalsIgnoreCase("undefined")==false)
				sqlString.append(" and s.id='"+studentId+"'");
			}
			Query  query = session.createQuery(sqlString.toString());
			List<Student> studentList = query.list();
			session.flush();
			return studentList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}		
	}
	
	public ConductCertificateDetails getStudentListIfCcDetailsExist(int studentId)
	throws Exception {
		Session session=null;
		ConductCertificateDetails student=null;
		try {
			session = HibernateUtil.getSession();
			Query  query = session.createQuery("from ConductCertificateDetails s where s.student.id=:id");
			query.setInteger("id", studentId);
			student = (ConductCertificateDetails) query.uniqueResult();
			session.flush();
			return student;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}

	}
	@Override
	public String getClourseId(String classId) throws Exception {
		Session session=null;
		int classes=0;
		try {
			session = HibernateUtil.getSession();
			Query  query = session.createQuery("select s.course.id from Classes s where s.id="+Integer.parseInt(classId));
			classes = (Integer) query.uniqueResult();
			session.flush();
			return String.valueOf(classes);
		} catch (Exception e) {
			throw  new BusinessException(e);
		}	
}
	@Override
    public String getStudentSubjectCode(String classId) throws ApplicationException {
        Session session = null;
        String subCode = null;
        try {
            session = HibernateUtil.getSession();
            final Query query = session.createQuery("select c.course.name from Classes c where c.id=" + classId);
            subCode = (String)query.uniqueResult();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        finally {
            if (session != null) {
                session.flush();
            }
        }
        if (session != null) {
            session.flush();
        }
        return subCode;
    }
	@Override
	public int getPtype(String classId) {
		Session session = null;
		int ptype=0;
		try {
			session = HibernateUtil.getSession();
			String query = "select c.course.program.programType.id from Classes c where c.id=" +classId;
			ptype= (Integer) session.createQuery(query).uniqueResult();
			
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
		}
		return ptype;
	}
	@Override
	public String getStudentroleNo(String studentId) {
		Session session = null;
		String rollNo=null;
		try {
			session = HibernateUtil.getSession();
			String query = "select s.student.rollNo from ConductCertificateDetails s where s.student.admAppln.isSelected=1 and s.student.admAppln.isApproved=1  and s.student.id=" +studentId;
			rollNo=(String) session.createQuery(query).uniqueResult();
			
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
		}
		return rollNo;
	}
	@Override
	public String getAccDuration(String classId) {
		Session session = null;
		String accDuration = null;
		try {		
			session = HibernateUtil.getSession();
			String sqlQuery =" select  curriculum_scheme.`year` "+
					" FROM curriculum_scheme_duration "+
					" INNER JOIN curriculum_scheme ON "+
					" curriculum_scheme.id = curriculum_scheme_duration.curriculum_scheme_id "+
					" INNER JOIN course ON course.id = curriculum_scheme.course_id "+
					" INNER JOIN class_schemewise ON "+
					" class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
					" INNER JOIN  classes ON classes.id = class_schemewise.class_id "+
					" AND classes.course_id = course.id where classes.id="+classId;
					
			Query query = session.createSQLQuery(sqlQuery);			
			accDuration = query.uniqueResult().toString();
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return accDuration;
	}
	@Override
	public List<Student> getStudentListForCCEntry(ConductCertificateDetailsForm conductCertificateDetailsForm) {
		Session session = null;
		Transaction transaction = null;
		List<Student> studentList=null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String query="from Student s where  s.admAppln.isCancelled=0 " +
			" and s.isActive=1 and s.classSchemewise.id="+conductCertificateDetailsForm.getClassId();
			
			query= query+" order by s.admAppln.personalData.firstName";
			studentList= session.createQuery(query).list();
			session.flush();
			session.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}
	@Override
	public Boolean updateStudentCCNo(List<Student> studentList,ConductCertificateDetailsForm conductCertificateDetailsForm) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			int count=0;
			int ccNo=0;
			ccNo=Integer.parseInt(conductCertificateDetailsForm.getConductCertificateNo());
			for(Student bo:studentList)
			{	
				bo.setCcNo(String.valueOf(ccNo));
				ccNo++;
				session.saveOrUpdate(bo);
				if(++count%20==0)
				{
					session.flush();
					session.clear();
				}
			}	
			transaction.commit();
			session.flush();
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
			}
		}
		return true;
	}
}
