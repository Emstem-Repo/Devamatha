package com.kp.cms.transactionsimpl.admission;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamSubDefinitionCourseWiseBO;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admission.ITransferCertificateTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class TransferCertificateTransactionImpl implements ITransferCertificateTransaction
{
	public List<Student> getStudentList(String classId,String fromReg, String toReg,String duplicate,String studentId)throws Exception
	{
		Session session=null;
		try {
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer(
					"select s.student from StudentTCDetails s where  "	);
			
			if(fromReg!= null && toReg!= null && !fromReg.trim().isEmpty() && !toReg.trim().isEmpty() && fromReg.trim().equalsIgnoreCase(toReg.trim())){
				sqlString.append("( s.student.admAppln.isSelected=1 and s.student.admAppln.isApproved=1 OR ( s.student.admAppln.isCancelled = 1))");
			}
			else{
				sqlString.append(" s.student.admAppln.isSelected=1 and s.student.admAppln.isApproved=1 ");
			}
			//OR ( s.student.admAppln.isCancelled = 1 and s.student.admAppln.isApproved=1)
			//" and s.tcNo is null"
			if(classId!=null && !classId.trim().isEmpty())
				sqlString.append(" and s.student.classSchemewise.id="+classId);
						
			if(fromReg!=null && !fromReg.trim().isEmpty())
				sqlString.append(" and s.student.registerNo>='"+fromReg+"'");
			if(toReg!=null && !toReg.trim().isEmpty())
				sqlString.append(" and s.student.registerNo<='"+toReg+"'");
			//if(duplicate.equalsIgnoreCase("no"))
			//	sqlString.append(" and s.student.tcNo is null");
			if(studentId!=null && !studentId.trim().isEmpty())
			{
				if(studentId.equalsIgnoreCase("undefined")==false)
				sqlString.append(" and s.student.id='"+studentId+"'");
			}
			Query  query = session.createQuery(sqlString.toString());
			List<Student> studentList = query.list();
			session.flush();
			return studentList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
	public List<TCNumber> getTCNumber(Integer year, String tcFor)throws Exception
	{
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<TCNumber> tc =session.createQuery("from TCNumber t where t.isActive=1 and t.tcFor='"+tcFor+"' and t.year="+year).list();
			session.flush();
			return tc;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}
	
	public void updateStudentsTcNo(List<Student> studentsTakenTcList)throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			int count=0;
			for(Student bo:studentsTakenTcList)
			{	
				session.update(bo);
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
	}
	
	public String getOldClassNameByStudentId(int studentId)throws Exception
	{
		String oldClassName="";
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			ExamStudentPreviousClassDetailsBO bo =(ExamStudentPreviousClassDetailsBO)session.createQuery("from ExamStudentPreviousClassDetailsBO t where t.schemeNo=1 and t.studentId="+studentId).uniqueResult();
			if(bo!=null)
				oldClassName=bo.getClassUtilBO().getName();
			session.flush();
			return oldClassName;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}
	
	public String getOldClassNameByStudentIdNew(int studentId, int Sem)throws Exception
	{
		String oldClassName="";
		Session session = null;
		int termNumber=0;
		try {
			session = HibernateUtil.getSession();
			
        StudentPreviousClassHistory bo =(StudentPreviousClassHistory)session.createQuery("from StudentPreviousClassHistory st where st.student.id = "+studentId+" and st.schemeNo = "+Sem).uniqueResult();
		
			
			if(bo!=null)
				oldClassName=bo.getClasses().getCourse().getProgram().getName().toUpperCase();
			   
			session.flush();
			return oldClassName;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}
	public List<Student> getStudentsByRegNo(String reg,String academicYear,String classId,String toReg,String studentId,boolean rePrint)throws Exception
	{
		Session session=null;
		List<Student> student=null;
		try {
			session = HibernateUtil.getSession();
			/* code changed by sudhir */
			/*StringBuffer sqlString = new StringBuffer(
					"select s from Student s " +
					"where s.registerNo='"+reg+"' " +
					" and s.tcNo is not null");*/
			StringBuffer sqlString = new StringBuffer(
			"select s.student from StudentTCDetails s where  "	);
			if(reg!= null && toReg!= null && !reg.trim().isEmpty() && !toReg.trim().isEmpty() && reg.trim().equalsIgnoreCase(toReg.trim())){
				sqlString.append("( s.student.admAppln.isSelected=1 and s.student.admAppln.isApproved=1 OR ( s.student.admAppln.isCancelled = 1))");
			}
			else{
				sqlString.append(" s.student.admAppln.isSelected=1 and s.student.admAppln.isApproved=1 ");
			}
			//OR ( s.student.admAppln.isCancelled = 1 and s.student.admAppln.isApproved=1)
			//" and s.tcNo is null"
			if(reg!=null && !reg.trim().isEmpty())
				sqlString.append(" and s.student.registerNo>='"+reg+"'");
			if(toReg!=null && !toReg.trim().isEmpty())
				sqlString.append(" and s.student.registerNo<='"+toReg+"'");
			if(studentId.equalsIgnoreCase("undefined")==true || studentId==null)
			{
				if(academicYear!=null && !academicYear.trim().isEmpty())
					sqlString.append(" and s.student.classSchemewise.curriculumSchemeDuration.academicYear='"+academicYear+"'");
			}			
			if(classId!=null && !classId.trim().isEmpty())
				sqlString.append(" and s.student.classSchemewise.classes.id='"+classId+"'");
			if(studentId!=null && !studentId.trim().isEmpty())
			{
				if(studentId.equalsIgnoreCase("undefined")==false)
				sqlString.append(" and s.student.id='"+studentId+"'");
			}
			if(rePrint==true)
			{
				sqlString.append(" and s.student.tcNo is not null");
			}
			if(rePrint==false)			
			sqlString.append(" and s.student.tcNo is null");
			/*-------------------------------*/
			Query  query = session.createQuery(sqlString.toString());
			student = query.list();
			session.flush();
			return student;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
	public List<Student> getStudentListForReprint(String classId,String fromReg, String toReg,String includeFail,String tcType)throws Exception
	{
		Session session=null;
		List<Student> studentList=null;
		try {
			session = HibernateUtil.getSession();
			//code added by sudhir
			if(tcType.equalsIgnoreCase("Discontinued")){
				StringBuffer sqlString = new StringBuffer("select stc.student from ExamStudentDetentionRejoinDetails e join e.student.studentTCDetails stc where e.discontinued=1");
				//" and s.tcNo is null"
				if(classId!=null && !classId.trim().isEmpty()){
					sqlString.append(" and e.classSchemewise.id="+classId);
					sqlString.append(" and e.student.classSchemewise.id="+classId);
				}
				if(fromReg!=null && !fromReg.trim().isEmpty())
					sqlString.append(" and e.student.registerNo>='"+fromReg+"'");
				if(toReg!=null && !toReg.trim().isEmpty())
					sqlString.append(" and e.student.registerNo<='"+toReg+"'");
				/*if(tcType.equalsIgnoreCase("Duplicate") || tcType.equalsIgnoreCase("Duplicate(Discontinued)"))
					sqlString.append(" and stc.student.tcNo is not null");
				else
					sqlString.append(" and stc.student.tcNo is null");*/
				sqlString.append(" and stc.student.tcNo is not null");
				sqlString.append(" and (e.rejoin is null or e.rejoin = 0)");
				Query  query = session.createQuery(sqlString.toString());
				studentList=query.list();
			//
			}else{
				StringBuffer sqlString = new StringBuffer(
						"select s.student from StudentTCDetails s where s.student.admAppln.isSelected=1 " +
						" and s.student.admAppln.isApproved=1 " 
						);
				//" and s.tcNo is null"
				if(classId!=null && !classId.trim().isEmpty())
					sqlString.append(" and s.student.classSchemewise.id="+classId);
				if(fromReg!=null && !fromReg.trim().isEmpty())
					sqlString.append(" and s.student.registerNo>='"+fromReg+"'");
				if(toReg!=null && !toReg.trim().isEmpty())
					sqlString.append(" and s.student.registerNo<='"+toReg+"'");
				if(includeFail.equalsIgnoreCase("no"))
					sqlString.append(" and s.passed='yes'");
				sqlString.append(" and s.student.tcNo is not null");
				Query  query = session.createQuery(sqlString.toString());
				studentList = query.list();
			}
			session.flush();
			return studentList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	public List<Student> getStudentListForCjc(String classId, String fromReg,String toReg, String tcType, String includeFail)throws Exception
	{
		Session session=null;
		List<Student> studentList=null;
		try {
			session = HibernateUtil.getSession();
			if(tcType.equalsIgnoreCase("Discontinued")){
				StringBuffer sqlString = new StringBuffer("select stc.student from ExamStudentDetentionRejoinDetails e join e.student.studentTCDetails stc where e.discontinued=1");
				//" and s.tcNo is null"
				if(classId!=null && !classId.trim().isEmpty()){
					sqlString.append(" and e.classSchemewise.id="+classId);
					sqlString.append(" and e.student.classSchemewise.id="+classId);
				}
				if(fromReg!=null && !fromReg.trim().isEmpty())
					sqlString.append(" and e.student.registerNo>='"+fromReg+"'");
				if(toReg!=null && !toReg.trim().isEmpty())
					sqlString.append(" and e.student.registerNo<='"+toReg+"'");
				if(tcType.equalsIgnoreCase("Duplicate") || tcType.equalsIgnoreCase("Duplicate(Discontinued)"))
					sqlString.append(" and stc.student.tcNo is not null");
				else
					sqlString.append(" and stc.student.tcNo is null");
				
				sqlString.append(" and (e.rejoin is null or e.rejoin = 0)");
				Query  query = session.createQuery(sqlString.toString());
				studentList=query.list();

			}else{
				StringBuffer sqlString = new StringBuffer(
						"select s.student from StudentTCDetails s where s.student.admAppln.isSelected=1 " +
						" and s.student.admAppln.isApproved=1 " 
						);
				//" and s.tcNo is null"
				if(classId!=null && !classId.trim().isEmpty())
					sqlString.append(" and s.student.classSchemewise.id="+classId);
				if(fromReg!=null && !fromReg.trim().isEmpty())
					sqlString.append(" and s.student.registerNo>='"+fromReg+"'");
				if(toReg!=null && !toReg.trim().isEmpty())
					sqlString.append(" and s.student.registerNo<='"+toReg+"'");
				if(tcType.equalsIgnoreCase("Duplicate") || tcType.equalsIgnoreCase("Duplicate(Discontinued)"))
					sqlString.append(" and s.student.tcNo is not null");
				else
					sqlString.append(" and s.student.tcNo is null");
				if(includeFail.equalsIgnoreCase("no"))
					sqlString.append(" and s.passed='yes'");
				Query  query = session.createQuery(sqlString.toString());
				studentList=query.list();
			}
			session.flush();
			return studentList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITransferCertificateTransaction#getOnlyTCNumber(int, java.lang.String, boolean)
	 */
	@Override
	public TCNumber getOnlyTCNumber(int year, String tcFor, boolean isTC) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			TCNumber tc =null;
			if(isTC)
				tc=(TCNumber)session.createQuery("from TCNumber t where t.isActive=1 and t.tcFor='"+tcFor+"' and t.year="+year+" and t.type='TC'").uniqueResult();
			else
				tc=(TCNumber)session.createQuery("from TCNumber t where t.isActive=1 and t.tcFor='"+tcFor+"' and t.year="+year+" and t.type='MC'").uniqueResult();
			session.flush();
			return tc;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}

	@Override
	public List<Student> getStudentListByInputQuery(String query)
			throws Exception {
		Session session = null;
		List<Student> selectedCandidatesList = null;
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
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public Integer getAdmittedSemester(int studentId)throws Exception
	{
		Integer termNumber = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<Integer> termNumberList = session.createSQLQuery("select min(scheme_no)" +
															      " from EXAM_student_previous_class_details"+
															     " where student_id = " + studentId).list();
			if(termNumberList!=null && termNumberList.size() > 0 ){
				termNumber=termNumberList.get(0);
			}
			Integer classId = (Integer)session.createQuery("select s.classSchemewise.classes.id from Student s where s.id = "+studentId).uniqueResult();
			if(termNumber == null) {
				termNumber = (Integer)session.createQuery("select c.termNumber from Classes c where id = "+classId).uniqueResult();
			}
			session.flush();
			return termNumber;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param courseId
	 * @param appliedYear
	 * @return
	 * @throws Exception
	 */
	public String getCourseSchemeId(int courseId, Integer appliedYear)throws Exception
	{
		Session session=null;
		List<String> curr=null;
		String courseScheme = "";
		try {
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer(
					"select c.courseScheme.name from CurriculumScheme c " +
					"where c.course.id = " + courseId + " and c.year = " + appliedYear);
			Query  query = session.createQuery(sqlString.toString());
			curr = query.list();
			if(curr!= null && curr.get(0)!= null && curr.get(0)!= null){
				courseScheme = curr.get(0);
			}
			session.flush();
			return courseScheme;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
		
	}	
	public BigInteger getRejoinYear(int studentId)throws Exception {
		BigInteger rejoinYear = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<BigInteger> rejoinList = session.createSQLQuery("select min(convert(batch, unsigned)) from EXAM_student_detention_rejoin_details " +
																	" where  rejoin = 1"+
															     " and student_id = " + studentId).list();
			if(rejoinList!=null && rejoinList.size() > 0 ){
				rejoinYear=rejoinList.get(0);
			}
			
			session.flush();
			return rejoinYear;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}
	/**
	 * to get discontinued student id's list 
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getDiscontinuedStudentId()throws Exception
	{
		
		Session session=null;
		try {
				session = HibernateUtil.getSession();
				Query  query = session.createQuery("select e.student.id from ExamStudentDetentionRejoinDetails e join e.student.studentTCDetails stc where e.discontinued=1 and (e.rejoin is null or e.rejoin = 0)");
				List<Integer> studentId=query.list();
			session.flush();
			
			return studentId;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITransferCertificateTransaction#getStudentSubject(java.util.List, int, int)
	 */
	public String getStudentSubject(Set<ApplicantSubjectGroup> subjectGroup,int courseId,int academicYear)throws Exception{
		String subjectName="";
		List<Integer> subjectId = new ArrayList<Integer>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			if(subjectGroup!=null && !subjectGroup.isEmpty()){
				Iterator< ApplicantSubjectGroup> ite=subjectGroup.iterator();
				while (ite.hasNext()) {
					ApplicantSubjectGroup bo = (ApplicantSubjectGroup) ite.next();
					Set<SubjectGroupSubjects> subjectGroupSubject= bo.getSubjectGroup().getSubjectGroupSubjectses();
					if(subjectGroupSubject!=null && !subjectGroupSubject.isEmpty()){
						Iterator<SubjectGroupSubjects> it = subjectGroupSubject.iterator();
						while (it.hasNext()) {
							SubjectGroupSubjects subjectGroupSub = (SubjectGroupSubjects) it.next();
							if(subjectGroupSub.getSubject()!=null && subjectGroupSub.getSubject().getIsActive())
								subjectId.add(subjectGroupSub.getSubject().getId());
					
						}
					}
					
				}
			}
			List<ExamSubDefinitionCourseWiseBO> subjects = session.createQuery("select e from ExamSubDefinitionCourseWiseBO e where e.courseId = "+courseId+" and e.academicYear="+academicYear+" and e.subjectId in(:subjectIds) and e.subjectUtilBO.isSecondLanguage = 0 and e.subjectUtilBO.name not like '%english%' order by subjectOrder").setParameterList("subjectIds", subjectId).list();
			Iterator<ExamSubDefinitionCourseWiseBO> iterator=subjects.iterator();
			while (iterator.hasNext()) {
				ExamSubDefinitionCourseWiseBO examSubDefinitionCourseWiseBO=iterator.next();
				SubjectUtilBO sub=examSubDefinitionCourseWiseBO.getSubjectUtilBO();
				if(!sub.getCode().equals("GD") && !sub.getCode().equals("VED") && !sub.getCode().equals("CA") && (sub.getName().toLowerCase().indexOf("remidial")==-1) &&(sub.getName().toLowerCase().indexOf("aeee")==-1) &&(sub.getName().toLowerCase().indexOf("cet")==-1) )
				if(!subjectName.isEmpty())
					subjectName = subjectName+", "+sub.getName().toUpperCase();
				else
					subjectName =sub.getName().toUpperCase();
				
			}
			session.flush();
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
		}
		return subjectName;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITransferCertificateTransaction#getClassTermNumber(int)
	 */
	@Override
	public int getClassTermNumber(int classId) throws Exception {
		int termNumber = 0;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query = "select c.classes.termNumber from ClassSchemewise c where c.id = " +classId;
			termNumber = (Integer) session.createQuery(query).uniqueResult();
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
		}
		return termNumber;
	}

	@Override
	public Classes getAdmittedClassesDetails(int studentId) throws Exception {

		Classes classes = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<Integer> classId1 = (List<Integer>) session.createQuery("select e1.classId from ExamStudentPreviousClassDetailsBO e1 where e1.studentId="+studentId +
															      " and e1.schemeNo =(select min(e.schemeNo)"+
															     " from ExamStudentPreviousClassDetailsBO e where e.studentId=" + studentId+")").list();
			if(classId1 != null){
				classes = (Classes)session.createQuery("from Classes c where id = "+classId1.get(0)).uniqueResult();
			}
			Integer classId = (Integer)session.createQuery("select s.classSchemewise.classes.id from Student s where s.id = "+studentId).uniqueResult();
			if(classes == null) {
				classes = (Classes)session.createQuery("from Classes c where id = "+classId).uniqueResult();
			}
			session.flush();
			return classes;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}

	@Override
	public List<StudentTCDetails> getStudentsByName(String name,boolean rePrint, String classId) throws Exception {
	
		Session session=null;
		List<StudentTCDetails> student=null;
		try {
			session = HibernateUtil.getSession();
			/* code changed by sudhir */
			/*StringBuffer sqlString = new StringBuffer(
					"select s from Student s " +
					"where s.registerNo='"+reg+"' " +
					" and s.tcNo is not null");*/
			StringBuffer sqlString = new StringBuffer(
			"from StudentTCDetails stc where " +
			"stc.student.id in (select s.id from Student s " +
			"where s.admAppln.personalData.firstName like '%"+name+"%'  "	);			
			if(rePrint==false){
				sqlString.append("and s.tcNo is null");
			}else{
				sqlString.append("and s.tcNo is not null");
			}
			if(classId!=null && !classId.equalsIgnoreCase(""))
				sqlString.append(" and stc.student.classSchemewise.id="+Integer.parseInt(classId));
			sqlString.append(")");
			Query  query = session.createQuery(sqlString.toString());
			student = query.list();
			session.flush();
			return student;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}	
	}	
}
