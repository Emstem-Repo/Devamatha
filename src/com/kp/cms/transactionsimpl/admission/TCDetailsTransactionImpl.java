package com.kp.cms.transactionsimpl.admission;

import java.text.SimpleDateFormat;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.SubjectGroup;
import java.util.Iterator;
import org.hibernate.SessionFactory;
import com.kp.cms.bo.admin.Classes;
import java.util.Date;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.bo.admin.CharacterAndConduct;
import org.hibernate.Transaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.bo.admin.AdmAppln;
import java.io.Serializable;
import com.kp.cms.bo.admin.StudentTCDetails;
import org.hibernate.Query;
import org.hibernate.Session;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.bo.admin.Student;
import java.util.List;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.kp.cms.transactions.admission.ITCDetailsTransaction;

public class TCDetailsTransactionImpl implements ITCDetailsTransaction
{
    private static volatile TCDetailsTransactionImpl tCDetailsTransactionImpl;
    private static final Log log;
    
    static {
        TCDetailsTransactionImpl.tCDetailsTransactionImpl = null;
        log = LogFactory.getLog((Class)TCDetailsTransactionImpl.class);
    }
    
    private TCDetailsTransactionImpl() {
    }
    
    public static TCDetailsTransactionImpl getInstance() {
        if (TCDetailsTransactionImpl.tCDetailsTransactionImpl == null) {
            TCDetailsTransactionImpl.tCDetailsTransactionImpl = new TCDetailsTransactionImpl();
        }
        return TCDetailsTransactionImpl.tCDetailsTransactionImpl;
    }
    
    @Override
    public List<Student> getStudentDetails(final String query) throws Exception {
        Session session = null;
        List<Student> selectedCandidatesList = null;
        try {
            session = HibernateUtil.getSession();
            final Query selectedCandidatesQuery = session.createQuery(query);
            selectedCandidatesList = (List<Student>)selectedCandidatesQuery.list();
            return selectedCandidatesList;
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
        finally {
            if (session != null) {
                session.flush();
            }
        }
    }
    
    @Override
    public Student getStudentTCDetails(final String query) throws Exception {
        Session session = null;
        Student student = null;
        try {
            session = HibernateUtil.getSession();
            final Query selectedCandidatesQuery = session.createQuery(query);
            student = (Student)selectedCandidatesQuery.uniqueResult();
            return student;
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
        finally {
            if (session != null) {
                session.flush();
            }
        }
    }
    
    @Override
    public boolean saveStudentTCDetails(final StudentTCDetails bo) throws Exception {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSession();
            session.close();
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate((Object)bo);
            final Student student = (Student)session.get((Class)Student.class, (Serializable)bo.getStudent().getId());
            final AdmAppln admAppln = (AdmAppln)session.get((Class)AdmAppln.class, (Serializable)student.getAdmAppln().getId());
            admAppln.setAdmissionDate(CommonUtil.ConvertStringToDate(bo.getAdmissionDate()));
            session.update((Object)admAppln);
            transaction.commit();
            session.flush();
            session.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            if (session != null) {
                session.flush();
                session.close();
            }
            return false;
        }
    }
    
    @Override
    public List<CharacterAndConduct> getAllCharacterAndConduct() throws Exception {
        Session session = null;
        List<CharacterAndConduct> list = null;
        try {
            session = HibernateUtil.getSession();
            final Query selectedCandidatesQuery = session.createQuery("from CharacterAndConduct c where c.isActive=1");
            list = (List<CharacterAndConduct>)selectedCandidatesQuery.list();
            return list;
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
        finally {
            if (session != null) {
                session.flush();
            }
        }
    }
    
    @Override
    public Boolean updateStudentTCDetails(final TCDetailsForm tcDetailsForm) throws Exception {
        Session session = null;
        Session session2 = null;
        Transaction transaction = null;
        try {
            final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = (Session)sessionFactory.openSession();
            session2 = (Session)sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            String query = "from Student s where  s.admAppln.isCancelled=0  and s.isActive=1 and s.classSchemewise.id=" + tcDetailsForm.getClassId();
            if (tcDetailsForm.getRegisterNo() != null && !tcDetailsForm.getRegisterNo().isEmpty()) {
                query = String.valueOf(query) + " and s.registerNo='" + tcDetailsForm.getRegisterNo() + "'";
            }
            query = String.valueOf(query) + " order by s.admAppln.personalData.firstName";
            final List<Student> studentList = (List<Student>)session2.createQuery(query).list();
            if (studentList != null && !studentList.isEmpty()) {
                final Iterator<Student> itr = studentList.iterator();
                int count = 0;
                int startingTCNumber = Integer.parseInt(tcDetailsForm.getStartingNumber());
                final String accr = String.valueOf(tcDetailsForm.getAcademicYear()).substring(2);
                final String finAccyr = String.valueOf(tcDetailsForm.getAcademicYear()) + "-" + (Integer.parseInt(accr) + 1);
                while (itr.hasNext()) {
                    final Student stu = itr.next();
                    StudentTCDetails bo = (StudentTCDetails)session.createQuery("from StudentTCDetails t where t.student.id=" + stu.getId()).uniqueResult();
                    if (bo == null) {
                        bo = new StudentTCDetails();
                        bo.setCreatedBy(tcDetailsForm.getUserId());
                        bo.setCreatedDate(new Date());
                    }
                    bo.setLastModifiedDate(new Date());
                    bo.setModifiedBy(tcDetailsForm.getUserId());
                    bo.setPassed(tcDetailsForm.getPassed());
                    bo.setFeePaid(tcDetailsForm.getFeePaid());
                    bo.setScholarship(tcDetailsForm.getScholarship());
                    bo.setReasonOfLeaving(tcDetailsForm.getReasonOfLeaving());
                    bo.setIsActive(Boolean.valueOf(true));
                    bo.setDateOfApplication((Date)CommonUtil.ConvertStringToSQLDate(tcDetailsForm.getDateOfApplication()));
                    bo.setDateOfLeaving((Date)CommonUtil.ConvertStringToSQLDate(tcDetailsForm.getDateOfLeaving()));
                    if (tcDetailsForm.getCharacterId() != null && !tcDetailsForm.getCharacterId().isEmpty()) {
                        final CharacterAndConduct conduct = new CharacterAndConduct();
                        conduct.setId(Integer.parseInt(tcDetailsForm.getCharacterId()));
                        bo.setCharacterAndConduct(conduct);
                    }
                    bo.setMonth(tcDetailsForm.getMonth());
                    bo.setYear(Integer.valueOf(Integer.parseInt(tcDetailsForm.getYear())));
                    bo.setPublicExaminationName(tcDetailsForm.getPublicExamName());
                    bo.setShowRegNo(tcDetailsForm.getShowRegisterNo());
                    bo.setStudent(stu);
                    bo.setSubjectPassed(tcDetailsForm.getTcDetailsTO().getSubjectPassed());
                    bo.setPromotionToNextClass(tcDetailsForm.getTcDetailsTO().getPromotionToNextClass());
                    bo.setExamMonth(tcDetailsForm.getTcDetailsTO().getExamMonth());
                    bo.setExamYear(tcDetailsForm.getTcDetailsTO().getExamYear());
                    bo.setClassOfLeaving(tcDetailsForm.getTcDetailsTO().getClassOfLeaving());
                    bo.setStudentName(stu.getAdmAppln().getPersonalData().getFirstName());
                    bo.setDateOfBirth(stu.getAdmAppln().getPersonalData().getDateOfBirth());
                    final Classes cls = new Classes();
                    cls.setId(Integer.parseInt(tcDetailsForm.getClassId()));
                    bo.setClasses(cls);
                    bo.setTcDate((Date)CommonUtil.ConvertStringToSQLDate(tcDetailsForm.getDateOfLeaving()));
                    bo.setAdmissionNo(stu.getAdmAppln().getAdmissionNumber());
                    bo.setDateOfIssue((Date)CommonUtil.ConvertStringToSQLDate(tcDetailsForm.getDateOfIssue()));
                    if (bo.getTcNo() == null || bo.getTcNo().isEmpty()) {
                        final String studentTCNumber = String.valueOf(tcDetailsForm.getPrefixForTC()) + startingTCNumber++ + "/" + finAccyr;
                        bo.setTcNo(studentTCNumber);
                        session.saveOrUpdate((Object)bo);
                        if (++count % 20 != 0) {
                            continue;
                        }
                        session.flush();
                        session.clear();
                    }
                }
            }
            transaction.commit();
            session.flush();
            session.close();
            session2.flush();
            session2.close();
            return true;
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (session != null) {
                session.flush();
                session.close();
                session2.flush();
                session2.close();
            }
            return false;
        }
    }
    
    @Override
    public List<SubjectGroup> getStudentSubjectGroup(final int applnId) throws Exception {
        Session session = null;
        Transaction transaction = null;
        List<SubjectGroup> selectedCandidatesList = null;
        try {
            final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = (Session)sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            final String query = "select a.subjectGroup from ApplicantSubjectGroup a where a.admAppln.id=" + applnId;
            final Query selectedCandidatesQuery = session.createQuery(query);
            selectedCandidatesList = (List<SubjectGroup>)selectedCandidatesQuery.list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return selectedCandidatesList;
    }
    
    @Override
    public ExamDefinitionBO getStudentExamName(final int studentId, final CurriculumSchemeDuration csd) throws Exception {
        Session session = null;
        Transaction transaction = null;
        ExamDefinitionBO selectedCandidatesList = null;
        try {
            final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = (Session)sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            final Integer semno = csd.getSemesterYearNo();
            final String classid = "select c.classes.id from ClassSchemewise c where c.curriculumSchemeDuration.id =  (select s.classSchemewise.curriculumSchemeDuration.id  from Student s where s.classSchemewise.curriculumSchemeDuration.semesterYearNo=" + semno + " and s.id=" + studentId + ")\t ";
            final Query selectedCandidateClassQuery = session.createQuery(classid);
            Integer clsid = (Integer)selectedCandidateClassQuery.uniqueResult();
            if (clsid == null) {
                clsid = 0;
            }
            final String query = "select m.exam from ExamRegularApplication m where m.classes.id=" + clsid + " and m.student.id=" + studentId + " " + "and m.exam.examTypeUtilBO.name='regular'";
            final Query selectedCandidatesQuery = session.createQuery(query);
            selectedCandidatesList = (ExamDefinitionBO)selectedCandidatesQuery.uniqueResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return selectedCandidatesList;
    }
    
    @Override
    public List<CurriculumSchemeDuration> getCurriculumSchemeDuration(final String classId, final int studentId) throws Exception {
        Session session = null;
        Transaction transaction = null;
        List<CurriculumSchemeDuration> list = null;
        try {
            final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = (Session)sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            final String query = "from CurriculumSchemeDuration c where c.curriculumScheme.id=(select s.classSchemewise.curriculumSchemeDuration.curriculumScheme.id from Student s where s.id=" + studentId + ") " + "order by c.semesterYearNo desc";
            final Query selectedCandidatesQuery = session.createQuery(query);
            list = (List<CurriculumSchemeDuration>)selectedCandidatesQuery.list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List getStudentSubjects(final int applnId) throws Exception {
        Session session = null;
        Transaction transaction = null;
        List selectedCandidatesList = null;
        try {
            final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = (Session)sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            final String query = "select s.department.name from AdmAppln aa join aa.applicantSubjectGroups asg join asg.subjectGroup sg join sg.subjectGroupSubjectses sgs join sgs.subject s where aa.id=" + applnId + " and sg.isActive=1 and sgs.isActive=1 and s.isActive=1";
            final Query selectedCandidatesQuery = session.createQuery(query);
            selectedCandidatesList = selectedCandidatesQuery.list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return selectedCandidatesList;
    }
    
    @Override
    public String getSubjectsForAllStudentsByClass(final TCDetailsForm tcDetailsForm) throws Exception {
        Session session = null;
        String subjectsStudied = null;
        try {
            session = HibernateUtil.getSession();
            final String sqlQuery = "select concat_ws(', ', course.core_in_course, course.complementary_in_course, course.common_in_course, course.elective_in_course) from classes classes inner join course course ON classes.course_id = course.id where classes.id = " + tcDetailsForm.getClassId();
            final Query query = (Query)session.createSQLQuery(sqlQuery);
            subjectsStudied = (String)query.uniqueResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return subjectsStudied;
    }
    
    @Override
    public Classes getClasses(final TCDetailsForm tcDetailsForm) {
        Session session = null;
        Transaction transaction = null;
        Classes classes = null;
        try {
            final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = (Session)sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            final String cls = " from ClassSchemewise c where c.id=" + tcDetailsForm.getClassId();
            final Query selectedCandidateClassQuery = session.createQuery(cls);
            classes = ((ClassSchemewise)selectedCandidateClassQuery.uniqueResult()).getClasses();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
    
    @Override
    public ExamDefinitionBO getExamForAllStudentsByClass(final TCDetailsForm tcDetailsForm) {
        Session session = null;
        Transaction transaction = null;
        ExamDefinitionBO examdef = null;
        try {
            final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = (Session)sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            final String query = "select distinct m.exam from ExamRegularApplication m where m.classes.id=" + tcDetailsForm.getClassId() + " and m.exam.examTypeID = 1";
            final Query selectedCandidatesQuery = session.createQuery(query);
            examdef = (ExamDefinitionBO)selectedCandidatesQuery.uniqueResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return examdef;
    }
    
    @Override
    public boolean verifyGeneratedTCNumbers(final String sqlQuery) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            final Query query = (Query)session.createSQLQuery(sqlQuery);
            //final Query query = session.createQuery(sqlQuery);
            
            final List<Integer> students = (List<Integer>)query.list();
            session.flush();
            return students == null || students.isEmpty();
        }
        catch (Exception ex) {
            if (session != null) {
                session.flush();
                session.close();
            }
            throw new BusinessException(ex);
        }
    }
    
    @Override
    public List getStudentFromTC(final int classId) throws Exception {
        Session session = null;
        Transaction transaction = null;
        List studentList = null;
        try {
            final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = (Session)sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            final String query = "select s.student.id from StudentTCDetails s where s.classes.id=" + classId;
            final Query selectedCandidatesQuery = session.createQuery(query);
            studentList = selectedCandidatesQuery.list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }
    
    @Override
    public boolean checkDuplicationOfTc(final TCDetailsForm tcDetailsForm) throws Exception {
        Session session = null;
        List<StudentTCDetails> list = null;
        try {
            session = HibernateUtil.getSession();
            final Query selectedCandidatesQuery = session.createQuery("from StudentTCDetails s where s.student.id=" + tcDetailsForm.getStudentId());
            list = (List<StudentTCDetails>)selectedCandidatesQuery.list();
            return !list.isEmpty() || list.size() != 0;
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
        finally {
            if (session != null) {
                session.flush();
            }
        }
    }
    
    @Override
    public StudentTCDetails getStudentFromTcDetails(final TCDetailsForm tcDetailsForm) throws Exception {
        Session session = null;
        StudentTCDetails tcDetails = null;
        try {
            session = HibernateUtil.getSession();
            final Query selectedCandidatesQuery = session.createQuery("from StudentTCDetails s where s.student.id=" + tcDetailsForm.getStudentId());
            tcDetails = (StudentTCDetails)selectedCandidatesQuery.uniqueResult();
            return tcDetails;
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
        finally {
            if (session != null) {
                session.flush();
            }
        }
    }
    
    @Override
    public Student getStudentList(final String studentId) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            final StringBuffer sqlString = new StringBuffer("select s.student from StudentTCDetails s where s.student.id=" + studentId);
            final Query query = session.createQuery(sqlString.toString());
            final Student student = (Student)query.uniqueResult();
            session.flush();
            return student;
        }
        catch (Exception e) {
            throw new BusinessException(e);
        }
    }
    
    @Override
    public void updateStudentsTcNo(final List<Student> studentsTakenTcList) throws Exception {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            int count = 0;
            for (final Student bo : studentsTakenTcList) {
                session.update((Object)bo);
                if (++count % 20 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
            session.flush();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (session != null) {
                session.flush();
            }
        }
    }
    
    @Override
    public List<Integer> getDiscontinuedStudentId() throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            final Query query = session.createQuery("select e.student.id from ExamStudentDetentionRejoinDetails e join e.student.studentTCDetails stc where e.discontinued=1 and (e.rejoin is null or e.rejoin = 0)");
            final List<Integer> studentId = (List<Integer>)query.list();
            session.flush();
            return studentId;
        }
        catch (Exception e) {
            throw new BusinessException(e);
        }
    }
    
    @Override
    public boolean checkTcAvailable(final int studentId) throws Exception {
        Session session = null;
        List<StudentTCDetails> list = null;
        try {
            session = HibernateUtil.getSession();
            final Query selectedCandidatesQuery = session.createQuery("from StudentTCDetails s where s.student.id=" + studentId);
            list = (List<StudentTCDetails>)selectedCandidatesQuery.list();
            return !list.isEmpty() || list.size() != 0;
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
        finally {
            if (session != null) {
                session.flush();
            }
        }
    }
    
    @Override
    public int getAdmApplnDetails(final TCDetailsForm tcDetailsForm) throws Exception {
        Session session = null;
        int admId = 0;
        try {
            session = HibernateUtil.getSession();
            final String s = "select s.admAppln.id from Student s where s.id= :studId";
            final Query query = session.createQuery(s).setInteger("studId", Integer.parseInt(tcDetailsForm.getStudentId()));
            admId = (Integer)query.uniqueResult();
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
        return admId;
    }
    
    @Override
    public Date getDate(final int admApplnId) throws Exception {
        Session session = null;
        String date = null;
        Date date2 = null;
        try {
            session = HibernateUtil.getSession();
            final String s = "select a.admissionDate from AdmAppln a where a.id= :admApplnId ";
            final Query query = session.createQuery(s).setInteger("admApplnId", admApplnId);
            date = (String)query.uniqueResult();
            if (date != null) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date2 = sdf.parse(date);
            }
            else {
                date2 = null;
            }
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
        return date2;
    }
    
    @Override
    public String getStudentSubjectCode(final String classId) throws ApplicationException {
        Session session = null;
        String subCode = null;
        try {
            session = HibernateUtil.getSession();
            final Query query = session.createQuery("select c.course.code from Classes c where c.id=" + classId);
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
	public StudentTCDetails getStudentTCDetailsByClass(String classId) throws Exception {

        Session session = null;
        StudentTCDetails student = null;
        try {
            session = HibernateUtil.getSession();
            final Query selectedCandidatesQuery = session.createQuery("from StudentTCDetails s where s.classes.id="+classId+" group by s.classes.id");
            student = (StudentTCDetails)selectedCandidatesQuery.uniqueResult();
            return student;
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
        finally {
            if (session != null) {
                session.flush();
            }
        }
    
	}
}