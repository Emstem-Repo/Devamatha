package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ConsolidateMarksCardNoGen;
import com.kp.cms.bo.exam.ConsolidateMarksCardSiNo;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.MarksCardNoGen;
import com.kp.cms.bo.exam.MarksCardSiNo;
import com.kp.cms.bo.exam.MarksCardSiNoGen;
import com.kp.cms.bo.exam.MarksCardSlNo;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ConsolidateMarksCardForm;
import com.kp.cms.forms.exam.MarksCardForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.exam.IMarksCardTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

@SuppressWarnings("unchecked")
public class MarksCardTransactionImpl implements IMarksCardTransaction{
	
	public int getStudentCount(MarksCardForm marksCardForm) throws Exception{
		int result= -1;
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
				 query="select count(e.studentBO.id) from ExamStudentEligibilityCheckBO e"
					 	+ " where e.classBO.id=:ClassId and e.examDefinitionBO.id=:ExamName and e.isExamEligibile=1"
					 	+ " group by e.examDefinitionBO.id, e.classBO.id";
			 Query qr = session.createQuery(query);
			 qr.setString("ExamName", marksCardForm.getExamName());
			 qr.setString("ClassId", marksCardForm.getClassId());
			 Long obj=(Long)qr.uniqueResult();
			 result = obj.intValue();
			 
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return result;
	}
	
	public int getCurrentNO(MarksCardForm marksCardForm)throws Exception{
		int result= -1;
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
				 query="select bo from MarksCardSiNo bo inner join bo.courseId.classes cls inner join cls.classSchemewises sch  where bo.isActive=1 and cls.id ="+marksCardForm.getClassId()+" and sch.curriculumSchemeDuration.academicYear = bo.academicYear and cls.termNumber = bo.semister";
			 Query qr = session.createQuery(query);
			 
			 MarksCardSiNo obj=(MarksCardSiNo)qr.uniqueResult();
			 
			 if(obj!=null){
				 result = Integer.parseInt(obj.getCurrentNo());
			 }
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return result;
	}
	
	public boolean getUpdateSI(String totalCount,MarksCardForm marksCardForm) throws Exception{
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 Transaction tran = session.beginTransaction();
			 String query="";
				 query="select bo from MarksCardSlNo bo where bo.isActive=1 and bo.programType = " + marksCardForm.getProgramTypeId() + " and bo.academicYear = " + marksCardForm.getYear() + " and bo.scheme = " + marksCardForm.getSemister();
			 Query qr = session.createQuery(query);
			 
			 MarksCardSlNo obj=(MarksCardSlNo)qr.uniqueResult();
			 
			 if(obj!=null){
				 obj.setCurrentNo(Integer.parseInt(totalCount));
				 session.merge(obj);
				 tran.commit();
				 return true;
			 }
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return false;
	}
	
	public int getAcademicId(String year)throws Exception{
		int result= -1;
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
				 query="from AcademicYear a where a.isActive=1 and a.year="+year;
			 Query qr = session.createQuery(query);
			 AcademicYear obj=(AcademicYear)qr.uniqueResult();
			 
			 if(obj!=null){
				 result = obj.getId();
			 }
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return result;
	}
	
	public List<Integer> getStudentId(MarksCardForm marksCardForm)throws Exception{
		List<Integer> studList = new ArrayList<Integer>();
		List<Integer> getList = new ArrayList<Integer>();
		Session session = null;
		List<Object[]> objList=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
			 
				 query="select sfd.student.id from StudentFinalMarkDetails sfd "
						+"	where sfd.classes.id=:ClassId and sfd.exam.id=:ExamName" 
						+"	and sfd.student.id not in "
						+"	(select eb.studentId from ExamBlockUnblockHallTicketBO eb where eb.hallTktOrMarksCard='H' and eb.classId=:ClassId and eb.examId=:ExamName) ";
				 		
				 
				 if(marksCardForm.getRadioId().equalsIgnoreCase("1")){
					 query=query+" and sfd.student.registerNo='"+marksCardForm.getRegForConsolidate()+"'";
				 }
				 else{
				 if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
					 query=query+" and sfd.student.id="+marksCardForm.getBaseStudentId();
				 }
				 }
				 query=query+"  group by sfd.student.id order by sfd.student.registerNo";
			 Query qr = session.createQuery(query);
			qr.setString("ExamName", marksCardForm.getExamName());
			 qr.setString("ClassId", marksCardForm.getClassId());
			 getList = (List<Integer>) qr.list();
			 Iterator<Integer> itr = getList.iterator();
			 while (itr.hasNext()) {
				Integer integer = (Integer) itr.next();
				studList.add(integer);
			}
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return studList;
	}
	
	public boolean updateStudent(List<MarksCardNoGen> boList)throws Exception{
		Session session = null;
		Transaction tx = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 tx=session.beginTransaction();
			 Iterator<MarksCardNoGen> itr = boList.iterator();
			 while (itr.hasNext()) {
				MarksCardNoGen marksCardNoGen = (MarksCardNoGen) itr.next();
				session.save(marksCardNoGen);
			}
			 tx.commit();
			 return true;
		 }catch (Exception e) {
			 tx.rollback();
			 return false;
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	public Map<Integer, String> getStudentList(MarksCardForm marksCardForm)throws Exception{
		Map<Integer, String> mapList = new HashMap<Integer, String>();
		Session session = null;
		List<Object[]> objList=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
			 if(marksCardForm.getRadioId().equalsIgnoreCase("1")){
				 query="select mg.studentId.id, mg.marksCardNo from MarksCardSiNoGen mg "
					  +" join mg.studentId s"
					 +" where mg.classId.id=:ClassId and mg.examId.id=:ExamName  and mg.isConsolidate=1 and s.registerNo='"+marksCardForm.getRegNo()+"'"; 
					  
				 if(marksCardForm.getExamType().equalsIgnoreCase("Regular")){
					 	query=query+" and mg.isRegular=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
						query=query+" and mg.isSupplementary=1 ";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
						query=query+" and mg.isRevaluation=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
						query=query+" and mg.isImprovement=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
						query=query+" and mg.isGrace=1 ";
					}
				 
			 }else{
				 query="select mg.studentId.id, mg.marksCardNo from MarksCardSiNoGen mg "
				+" where mg.classId.id=:ClassId and mg.examId.id=:ExamName ";
				 
				 if(marksCardForm.getExamType().equalsIgnoreCase("Regular")){
					 	query=query+" and mg.isRegular=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
						query=query+" and mg.isSupplementary=1 ";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
						query=query+" and mg.isRevaluation=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
						query=query+" and mg.isImprovement=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
						query=query+" and mg.isGrace=1 ";
					}
			 }
			 Query qr = session.createQuery(query);
			 qr.setString("ExamName", marksCardForm.getExamName());
			 qr.setString("ClassId", marksCardForm.getClassId());
			 objList = qr.list();
			 Iterator<Object[]> itr = objList.iterator();
			 while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				mapList.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
			}
			
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return mapList;
	}
	
	public boolean checkRegNo(MarksCardForm marksCardForm)throws Exception{
		Session session = null;
		Transaction tx=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 Query query = session.createQuery("from Student s where s.registerNo=:regNo");
			 query.setString("regNo", marksCardForm.getRegNo());
			 Student bo =(Student) query.uniqueResult();
			 if(bo!=null){
				 marksCardForm.setBaseStudentId(Integer.toString(bo.getId()));
				 return true;
			 }
			
		 }catch (Exception e) {
			 return false;
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		 return false;
	}
	
	public void updateSingleStudent(MarksCardNoGen bo)throws Exception{
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
				session.save(bo);
				
		 }catch (Exception e) {
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
	}

	@Override
	public Student getStudentBoDetails(int studentId)
			throws Exception {
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 Transaction tran = session.beginTransaction();
			 String query="";
			 query="select s  from Student s "
				 +" where s.id='"+studentId+"'"; 
			 Query qr = session.createQuery(query);
			 Student obj=(Student)qr.uniqueResult();
			 session.flush();
			return obj;
		 }catch (Exception e) {
			 throw e; 
		
		}
		
	}

	@Override
	public MarksCardNoGen getMarksCardNoGen(MarksCardForm marksCardForm,
			List<Integer> studentId) throws Exception {
		Session session = null;
		MarksCardNoGen obj=null; 
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 Transaction tran = session.beginTransaction();
			 String query="";
			query="select mg from MarksCardNoGen mg"
				  +" join mg.studentId s"
				  +" where mg.classId.id=:ClassId and mg.examId.id=:ExamName and s.registerNo='"+marksCardForm.getRegNo()+"' and mg.isConsolidate=true"; 
			 		
			Query qr = session.createQuery(query);
			qr.setString("ExamName", marksCardForm.getExamName());
			 qr.setString("ClassId", marksCardForm.getClassId());
			 obj = (MarksCardNoGen) qr.uniqueResult();
		 }catch (Exception e) {
			 throw e; 
		
		}
		return obj;
		
	}
	
	
	
	public int getConsolidateCurrentNO(ConsolidateMarksCardForm consolidateMarksCardForm)throws Exception{
		int result= -1;
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
				 query="from ConsolidateMarksCardSiNo m where m.isActive=1 and m.programType.id="+consolidateMarksCardForm.getProgramTypeId()+ "and m.academicYear="+consolidateMarksCardForm.getYear();
			 Query qr = session.createQuery(query);
			 
			 ConsolidateMarksCardSiNo obj=(ConsolidateMarksCardSiNo)qr.uniqueResult();
			 
			 if(obj!=null){
				 result = Integer.parseInt(obj.getCurrentNo());
			 }
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return result;
	}
	
	
	
	
	public List<Integer> getStudentDetails(ConsolidateMarksCardForm consolidateMarksCardForm)throws Exception{
	
	

		List<Integer> studList = new ArrayList<Integer>();
		List<Integer> getList = new ArrayList<Integer>();
		Session session = null;
		List<Object[]> objList=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
			 
			 query="select s.id from Student s join s.admAppln a where a.appliedYear=:year and a.course.id=:courseId order by s.registerNo";
				
			
			 Query qr = session.createQuery(query);
			 qr.setString("year", consolidateMarksCardForm.getYear());
			 qr.setString("courseId", consolidateMarksCardForm.getCourseId());
			 getList = (List<Integer>) qr.list();
			 Iterator<Integer> itr = getList.iterator();
			 while (itr.hasNext()) {
				Integer integer = (Integer) itr.next();
				studList.add(integer);
			}
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return studList;
	
	
	
	}
	
	public Map<Integer, String> getStudentDetailList(ConsolidateMarksCardForm consolidateMarksCardForm)throws Exception{
		Map<Integer, String> mapList = new HashMap<Integer, String>();
		Session session = null;
		List<Object[]> objList=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
			
				 query="select mg.studentId.id, mg.marksCardNo from ConsolidateMarksCardNoGen mg  where mg.courseId.id=:courseId and mg.year=:year "; 
			
			 Query qr = session.createQuery(query);
			 qr.setString("courseId", consolidateMarksCardForm.getCourseId());
			 qr.setString("year", consolidateMarksCardForm.getYear());
			// qr.setString("ClassId", consolidateMarksCardForm.getClassId());
			 objList = qr.list();
			 Iterator<Object[]> itr = objList.iterator();
			 while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				mapList.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
			}
			
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return mapList;
	}
	
	
	
	
	
	public boolean updateConsolidate(List<ConsolidateMarksCardNoGen> boList)throws Exception{
		Session session = null;
		Transaction tx = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 tx=session.beginTransaction();
			 Iterator<ConsolidateMarksCardNoGen> itr = boList.iterator();
			 while (itr.hasNext()) {
				 ConsolidateMarksCardNoGen consolidateMarksCardNoGen = (ConsolidateMarksCardNoGen) itr.next();
				session.save(consolidateMarksCardNoGen);
			}
			 tx.commit();
			 return true;
		 }catch (Exception e) {
			 tx.rollback();
			 return false;
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	
	
	public boolean getUpdate(String totalCount,ConsolidateMarksCardForm consolidateMarksCardForm) throws Exception{
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 Transaction tran = session.beginTransaction();
			 String query="";
				 query="from ConsolidateMarksCardSiNo c where c.isActive=1 and c.programType.id="+consolidateMarksCardForm.getProgramTypeId()+" and c.academicYear="+consolidateMarksCardForm.getYear();
			 Query qr = session.createQuery(query);
			 
			 ConsolidateMarksCardSiNo obj=(ConsolidateMarksCardSiNo)qr.uniqueResult();
			 
			 if(obj!=null){
				 obj.setCurrentNo(totalCount);
				 session.merge(obj);
				 tran.commit();
				 return true;
			 }
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return false;
	}
	
	public boolean updateStudent1(List<MarksCardSiNoGen> boList)throws Exception{
		Session session = null;
		Transaction tx = null;
		int count = 0;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 tx=session.beginTransaction();
			 Iterator<MarksCardSiNoGen> itr = boList.iterator();
			 while (itr.hasNext()) {
				 MarksCardSiNoGen marksCardNoGen = (MarksCardSiNoGen) itr.next();
				session.save(marksCardNoGen);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			 tx.commit();
			 return true;
		 }catch (Exception e) {
			 tx.rollback();
			 return false;
		 }finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		
	}
	
	public List<StudentFinalMarkDetails> getStudentIdForExamType(MarksCardForm marksCardForm)throws Exception{
		List<StudentFinalMarkDetails> studList = new ArrayList<StudentFinalMarkDetails>();
		
		Session session = null;
		List<Object[]> objList=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
			 
				 query="from StudentFinalMarkDetails sfd "
						+"	where sfd.classes.id=:ClassId and sfd.exam.id=:ExamName" 
						+"	and sfd.student.id not in "
						+"	(select eb.studentId from ExamBlockUnblockHallTicketBO eb where eb.hallTktOrMarksCard='H' and eb.classId=:ClassId and eb.examId=:ExamName) "
						+"  and sfd.student.id not in "
						+"  (select mg.studentId from MarksCardSiNoGen mg where mg.classId=:ClassId and mg.examId=:ExamName and mg.isSupplementary = 1)";
				 		
				  if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
						query=query+" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id=:ClassId and mg.examId.id=:ExamName  and mg.isSupplementary=1 ) " +
								" and sfd.student.id in (select ss.student.id from StudentSupplementaryImprovementApplication ss where ss.classes.id=:ClassId and (ss.isSupplementary=1 or ss.isImprovement=1) and (ss.isAppearedTheory=1 or ss.isAppearedPractical=1) and ss.examDefinition.id=:ExamName)";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
						query=query+" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id=:ClassId and mg.examId.id=:ExamName  and mg.isRevaluation=1) "+
						" and sfd.student.id in (select ss.student.id from ExamRevaluationApplication ss where ss.classes.id=:ClassId  and ss.exam.id=:ExamName)";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
						query=query+" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id=:ClassId and mg.examId.id=:ExamName  and mg.isImprovement=1 ) " +
						" and sfd.student.id in (select ss.student.id from StudentSupplementaryImprovementApplication ss where ss.classes.id=:ClassId and (ss.isSupplementary=1 or ss.isImprovement=1) and (ss.isAppearedTheory=1 or ss.isAppearedPractical=1) and ss.examDefinition.id=:ExamName)";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
						query=query+" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id=:ClassId and mg.examId.id=:ExamName  and mg.isGrace=1 ) " +
						" and sfd.isGracing=1";	
					}
				  
				 if(marksCardForm.getRadioId().equalsIgnoreCase("1")){
					 query=query+" and sfd.student.registerNo='"+marksCardForm.getRegForConsolidate()+"'";
				 }
				 else{
				 if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
					 query=query+" and sfd.student.id="+marksCardForm.getBaseStudentId();
				 }
				 }
				 query=query+"  group by sfd.student.id order by sfd.student.registerNo";
			 Query qr = session.createQuery(query);
			 qr.setString("ExamName", marksCardForm.getExamName());
			 qr.setString("ClassId", marksCardForm.getClassId());
			 studList = qr.list();
			 
			 //getting old student for garce marks card
			 if(studList.size()!=0){/*
				 if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
					 
					 
					 String queryold="select sfd from StudentFinalMarkDetails sfd " +
						" join sfd.student.studentPreviousClassesHistory classHis" +
						" join classHis.classes.classSchemewises classSchemewise" +
						" where sfd.student.admAppln.isCancelled=0  and (s.isHide=0 or s.isHide is null)" +
						" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
						" from ExamDefinitionBO e where e.id="+marksCardForm.getExamName()+")" +
						//" and classSchemewise.curriculumSchemeDuration.semesterYearNo=" +newExamMarksEntryForm.getSchemeNo()+
						//" and classSchemewise.classes.course.id="+newExamMarksEntryForm.getCourseId()+
						" and classSchemewise.classes.id="+marksCardForm.getClassId()+
						" and sfd.student.id not in (select eb.studentId from ExamBlockUnblockHallTicketBO eb where eb.hallTktOrMarksCard='H' and eb.classId=:ClassId and eb.examId=:ExamName) "+
					 	" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id=:ClassId and mg.examId.id=:ExamName  and mg.isGrace=1 ) " +
						" and sfd.isGracing=1 group by sfd.student.id order by sfd.student.registerNo";	
					
					 
					 qr = session.createQuery(query);
					 qr.setString("ExamName", marksCardForm.getExamName());
					 qr.setString("ClassId", marksCardForm.getClassId());
					 studList = qr.list();
					 }
					 
				 */} 
			 
			 
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return studList;
	}
	
	public boolean validateGenerateNoForExamType(MarksCardForm marksCardForm)throws Exception{
		Session session = null;
		Transaction tx=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String  query="select mg.studentId.id, mg.marksCardNo from MarksCardSiNoGen mg "
					//+" where mg.classId.id=:ClassId and mg.examId.id=:ExamName   ";
				 +" where mg.classId.id=:ClassId and mg.isRegular=1";
			 	/*if(marksCardForm.getExamType().equalsIgnoreCase("Regular")){
					query=query+" and mg.isRegular=1 )";	
				}
			 	if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
					query=query+" and mg.isSupplementary=1 )";	
				}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
					query=query+" and mg.isRevaluation=1 )";
				}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
					query=query+" and mg.isImprovement=1 ) ";
				}*/
				 Query qr = session.createQuery(query);
				// qr.setString("ExamName", marksCardForm.getExamName());
				 qr.setString("ClassId", marksCardForm.getClassId());
				 List objList = qr.list();
				 if(objList.size()!=0){
				 return true;
				 }
			
		 }catch (Exception e) {
			 return false;
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		 return false;
	}
	
	
	public Map<Integer, MarksCardSiNoGen> getStudentList1(MarksCardForm marksCardForm)throws Exception{
		Map<Integer, MarksCardSiNoGen> mapList = new HashMap<Integer, MarksCardSiNoGen>();
		Session session = null;
		List<Object[]> objList=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
				 query="select mg.studentId.id, mg from MarksCardSiNoGen mg "
				//+" where mg.classId.id=:ClassId and mg.examId.id=:ExamName  and mg.isRegular=1 ";
				 +" where mg.classId.id=:ClassId  and mg.isRegular=1 ";
			 
			 Query qr = session.createQuery(query);
			// qr.setString("ExamName", marksCardForm.getExamName());
			 qr.setString("ClassId", marksCardForm.getClassId());
			 objList = qr.list();
			 Iterator<Object[]> itr = objList.iterator();
			 while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				mapList.put(Integer.parseInt(objects[0].toString()), (MarksCardSiNoGen)objects[1]);
			}
			
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return mapList;
	}
	
	
	

	// TODO Auto-generated method stub
	public boolean updateStudentForExamType(MarksCardForm marksCardForm)throws Exception{
	boolean isAdded = false;
	Session session = null;
	Transaction transaction = null;
	Map<Integer,MarksCardSiNoGen> studentMap=marksCardForm.getStudentMap();
	
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		List<StudentTO> list = marksCardForm.getStudentList();
		
		if (list != null && !list.isEmpty()) {
			
				Iterator<StudentTO> itr = list.iterator();
			
				int count = 0;
				
				while (itr.hasNext()) {
					
					StudentTO  studentTO = (StudentTO) itr.next();
				
				if (studentTO.isChecked()) {
					
					MarksCardSiNoGen m=studentMap.get(studentTO.getId());
					
					String currentNO = m.getMarksCardNo1();
					
					MarksCardSiNoGen bo = new MarksCardSiNoGen();
					
					Student student = new Student();
					student.setId(studentTO.getId());
					bo.setStudentId(student);
					
					Classes classes = new Classes();
					classes.setId(Integer.parseInt(marksCardForm.getClassId()));
					bo.setClassId(classes);
					ExamDefinition examDef = new ExamDefinition();
					examDef.setId(Integer.parseInt(marksCardForm.getExamName()));
					bo.setExamId(examDef);
					bo.setMarksCardNo(m.getMarksCardNo());
					
					if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
						bo.setIsDuplicate(true);
					}else{
						bo.setIsDuplicate(false);
					}
					if(marksCardForm.getRadioId().equalsIgnoreCase("1")){
						bo.setIsConsolidate(true);
					}else{
						bo.setIsConsolidate(false);
					}
					
					currentNO = currentNO.substring(0,2) + "S" + currentNO.substring(2);
					bo.setMarksCardNo1(currentNO);
					bo.setIsSupplementary(true);
					bo.setCreatedDate(new Date());
					bo.setCreatedBy(marksCardForm.getUserId());
					
					transaction.begin();
					session.save(bo);
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}
										
					
				}
				
			}
			transaction.commit();
			isAdded = true;
		}
	} catch (Exception exception) {
		if (transaction != null) {
			transaction.rollback();
		}

	} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
	}
	
	return isAdded;
	}
	
	
	

	//
	
	public List<MarksCardSiNoGen> getDataAvailable(MarksCardForm marksCardForm)throws Exception{
		List<MarksCardSiNoGen> bo = new ArrayList<MarksCardSiNoGen>();
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
		if(marksCardForm.getExamType().equalsIgnoreCase("Regular")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isRegular=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isRegular=1").list();
					
				}
		}
		else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isSupplementary=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isSupplementary=1").list();
					
				}
	
		}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isRevaluation=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isRevaluation=1").list();
					
				}
		
			
		}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isImprovement=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isImprovement=1").list();
					
				}
			
		}
		else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isGrace=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id="+marksCardForm.getClassId()+" and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isGrace=1").list();
					
				}
		}
			
			
//			session.flush();
//			session.close();
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return bo;
	}
	
	
	public boolean updateSingleStudentDuplicate(MarksCardSiNoGen bo)throws Exception{
		Session session = null;
		boolean update=false;
		Transaction transaction = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 transaction= session.beginTransaction();
			 transaction.begin();
			 session.save(bo);
			 transaction.commit();
			 update=true;
				
		 }catch (Exception e) {
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		 return update;
	}
	public Classes getClassBO(int classId)throws Exception{
		int result= -1;
		Session session = null;
		Classes obj;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
				 query="from Classes c where c.id="+classId;
			 Query qr = session.createQuery(query);
			 obj=(Classes)qr.uniqueResult();
			 if(obj==null)
			 {
				 return null;
			 }
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return obj;
	}
	
	//	by Arun Sudhakaran
	public int getProgramTypeAndScheme(MarksCardForm marksCardForm) throws Exception
	{
		Session session = null;
		
		try
		{
			session = HibernateUtil.getSession();
			String sqlQuery = "select program_type.id" +
							  "  from EXAM_exam_course_scheme_details" +
							  " inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id" +
							  " inner join course ON EXAM_exam_course_scheme_details.course_id = course.id" +
							  " inner join exam_type ON EXAM_definition.exam_type_id = exam_type.id" +
							  " inner join program ON EXAM_exam_course_scheme_details.program_id = program.id and course.program_id = program.id" +
							  " inner join program_type ON program.program_type_id = program_type.id" +
							  " inner join classes on classes.course_id = course.id" +
							  " inner join class_schemewise on class_schemewise.class_id = classes.id" +
							  " inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
							  " where curriculum_scheme_duration.academic_year="+marksCardForm.getYear()+
							  "   and EXAM_definition.id="+marksCardForm.getExamName()+
							  " group by program_type.id";
			Query query = session.createSQLQuery(sqlQuery);
			List<Integer> list = query.list();
			session.flush();			
			return list.get(0);
		}
		catch(Exception ex)
		{
			if(session != null)	
			{
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public int getCurrentNO(int academicYear, int programType, String schemes) throws Exception
	{
		Session session = null;
		int result= -1;
		
		try
		{
			session = HibernateUtil.getSession();			
			String query="from MarksCardSlNo bo where bo.academicYear = "+academicYear+" and bo.programType.id = "+programType+" and bo.scheme = " + schemes;
			Query qr = session.createQuery(query);
				 
			MarksCardSlNo obj=(MarksCardSlNo)qr.uniqueResult();
				 
			if(obj!=null){
				 result = obj.getCurrentNo();
			}			 
			return result;
		}
		catch(Exception ex)
		{
			if(session != null)	
			{
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}
		
	public List<Integer> getClassIds(int academicYear, String scheme) throws Exception
	{
		Session session = null;
		
		try
		{
			session = HibernateUtil.getSession();
			String sqlQuery = "select classes.id from classes" +
							  " inner join class_schemewise on class_schemewise.class_id = classes.id" +
							  " inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
							  " where curriculum_scheme_duration.academic_year=" + academicYear + " and classes.term_number = " + scheme +
							  " group by classes.id";
			List<Integer> classIds = session.createSQLQuery(sqlQuery).list();
			session.flush();
			return classIds;
		}
		catch(Exception ex)
		{
			if(session != null)	
			{
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}
	
	public List<StudentFinalMarkDetails> getStudentId(MarksCardForm marksCardForm, String classIds)throws Exception
	{
		
		List<StudentFinalMarkDetails> getList = new ArrayList<StudentFinalMarkDetails>();
		Session session = null;
		List<Object[]> objList=null;
		
		try 
		{
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="select sfd from StudentFinalMarkDetails sfd "
						  +"	where sfd.classes.id in (" + classIds +") and sfd.exam.id=:ExamName" 
						  +"	and sfd.student.id not in "
						  +"	(select eb.studentId from ExamBlockUnblockHallTicketBO eb where eb.hallTktOrMarksCard='H' and eb.classId in (" + classIds +") and eb.examId=:ExamName) "; 		
				 
			 if(marksCardForm.getRadioId().equalsIgnoreCase("1")){
				 query=query+" and sfd.student.registerNo='"+marksCardForm.getRegForConsolidate()+"'";
			 }
			 else{
				 if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
					 query=query+" and sfd.student.id="+marksCardForm.getBaseStudentId();
				 }
			 }
			 query=query+"  group by sfd.student.id order by sfd.student.registerNo";
			 Query qr = session.createQuery(query);
			 qr.setString("ExamName", marksCardForm.getExamName());			
			 getList =  qr.list();
			 
		 }
		 catch (Exception e) 
		 {
			 throw e; 
		 }
		 finally {
			if (session != null) {
				session.flush();
			}
		}
		return getList;
	}
	
	public Map<Integer, String> getStudentList(MarksCardForm marksCardForm, String classIds)throws Exception
	{
		Map<Integer, String> mapList = new HashMap<Integer, String>();
		Session session = null;
		List<Object[]> objList=null;
		try 
		{
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session =sessionFactory.openSession();
			String query="";
			if(marksCardForm.getRadioId().equalsIgnoreCase("1"))
			{
				query="select mg.studentId.id, mg.marksCardNo from MarksCardSiNoGen mg "
					  +" join mg.studentId s"
					  +" where mg.classId.id in (" + classIds +") and mg.examId.id=:ExamName  and mg.isConsolidate=1 and s.registerNo='"+marksCardForm.getRegNo()+"'"; 
					  
				 if(marksCardForm.getExamType().equalsIgnoreCase("Regular")){
					 	query=query+" and mg.isRegular=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
						query=query+" and mg.isSupplementary=1 ";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
						query=query+" and mg.isRevaluation=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
						query=query+" and mg.isImprovement=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
						query=query+" and mg.isGrace=1 ";
					}
				 
			 }else{
				 query="select mg.studentId.id, mg.marksCardNo from MarksCardSiNoGen mg "
				+" where mg.classId.id in (" + classIds +") and mg.examId.id=:ExamName ";
				 
				 if(marksCardForm.getExamType().equalsIgnoreCase("Regular")){
					 	query=query+" and mg.isRegular=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
						query=query+" and mg.isSupplementary=1 ";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
						query=query+" and mg.isRevaluation=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
						query=query+" and mg.isImprovement=1 ";
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
						query=query+" and mg.isGrace=1 ";
					}
			 }
			 Query qr = session.createQuery(query);
			 qr.setString("ExamName", marksCardForm.getExamName());
			 objList = qr.list();
			 Iterator<Object[]> itr = objList.iterator();
			 while (itr.hasNext()) 
			 {
				Object[] objects = (Object[]) itr.next();
				mapList.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
			 }			
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			if (session != null) {
				session.flush();
			}
		}
		return mapList;
	}
	
	public List<StudentFinalMarkDetails> getStudentIdForExamType(MarksCardForm marksCardForm, String classIds)throws Exception{
		List<StudentFinalMarkDetails> studList = new ArrayList<StudentFinalMarkDetails>();
		
		Session session = null;
		List<Object[]> objList=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
			 
				 query="from StudentFinalMarkDetails sfd "
						+"	where sfd.classes.id in(" + classIds + ") and sfd.exam.id=:ExamName" 
						+"	and sfd.student.id not in "
						+"	(select eb.studentId from ExamBlockUnblockHallTicketBO eb where eb.hallTktOrMarksCard='H' and eb.classId in(" + classIds + ") and eb.examId=:ExamName) ";
				 		
				  if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
						query=query+" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id in(" + classIds + ") and mg.examId.id=:ExamName  and mg.isSupplementary=1 ) " +
								" and sfd.student.id in (select ss.student.id from StudentSupplementaryImprovementApplication ss where ss.classes.id in(" + classIds + ") and ss.isSupplementary=1 and (ss.isAppearedTheory=1 or ss.isAppearedPractical=1) and ss.examDefinition.id=:ExamName)";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
						query=query+" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id in(" + classIds + ") and mg.examId.id=:ExamName  and mg.isRevaluation=1) "+
						" and sfd.student.id in (select ss.student.id from ExamRevaluationApplication ss where ss.classes.id in(" + classIds + ")  and ss.exam.id=:ExamName)";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
						query=query+" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id in(" + classIds + ") and mg.examId.id=:ExamName  and mg.isImprovement=1 ) " +
						" and sfd.student.id in (select ss.student.id from StudentSupplementaryImprovementApplication ss where ss.classes.id in(" + classIds + ") and ss.isImprovement=1 and (ss.isAppearedTheory=1 or ss.isAppearedPractical=1) and ss.examDefinition.id=:ExamName)";	
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
						query=query+" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id in(" + classIds + ") and mg.examId.id=:ExamName  and mg.isGrace=1 ) " +
						" and sfd.isGracing=1";	
					}
				  
				 if(marksCardForm.getRadioId().equalsIgnoreCase("1")){
					 query=query+" and sfd.student.registerNo='"+marksCardForm.getRegForConsolidate()+"'";
				 }
				 else{
				 if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
					 query=query+" and sfd.student.id="+marksCardForm.getBaseStudentId();
				 }
				 }
				 query=query+"  group by sfd.student.id order by sfd.student.registerNo";
			 Query qr = session.createQuery(query);
			 qr.setString("ExamName", marksCardForm.getExamName());
			 studList = qr.list();
			 
			 //getting old student for garce marks card
			 if(studList.size()!=0){/*
				 if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
					 
					 
					 String queryold="select sfd from StudentFinalMarkDetails sfd " +
						" join sfd.student.studentPreviousClassesHistory classHis" +
						" join classHis.classes.classSchemewises classSchemewise" +
						" where sfd.student.admAppln.isCancelled=0  and (s.isHide=0 or s.isHide is null)" +
						" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
						" from ExamDefinitionBO e where e.id="+marksCardForm.getExamName()+")" +
						//" and classSchemewise.curriculumSchemeDuration.semesterYearNo=" +newExamMarksEntryForm.getSchemeNo()+
						//" and classSchemewise.classes.course.id="+newExamMarksEntryForm.getCourseId()+
						" and classSchemewise.classes.id="+marksCardForm.getClassId()+
						" and sfd.student.id not in (select eb.studentId from ExamBlockUnblockHallTicketBO eb where eb.hallTktOrMarksCard='H' and eb.classId=:ClassId and eb.examId=:ExamName) "+
					 	" and sfd.student.id not in (select mg.studentId.id from MarksCardSiNoGen mg  join mg.studentId s where mg.classId.id=:ClassId and mg.examId.id=:ExamName  and mg.isGrace=1 ) " +
						" and sfd.isGracing=1 group by sfd.student.id order by sfd.student.registerNo";	
					
					 
					 qr = session.createQuery(query);
					 qr.setString("ExamName", marksCardForm.getExamName());
					 qr.setString("ClassId", marksCardForm.getClassId());
					 studList = qr.list();
					 }
					 
				 */} 
			 
			 
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return studList;
	}
	
	public boolean validateGenerateNoForExamType(MarksCardForm marksCardForm, String classIds)throws Exception{
		Session session = null;
		Transaction tx=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String  query="select mg.studentId.id, mg.marksCardNo from MarksCardSiNoGen mg "
					//+" where mg.classId.id=:ClassId and mg.examId.id=:ExamName   ";
				 +" where mg.classId.id in (" + classIds + ") and mg.isRegular=1";
			 	/*if(marksCardForm.getExamType().equalsIgnoreCase("Regular")){
					query=query+" and mg.isRegular=1 )";	
				}
			 	if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
					query=query+" and mg.isSupplementary=1 )";	
				}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
					query=query+" and mg.isRevaluation=1 )";
				}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
					query=query+" and mg.isImprovement=1 ) ";
				}*/
				 Query qr = session.createQuery(query);
				// qr.setString("ExamName", marksCardForm.getExamName());
				 List objList = qr.list();
				 if(objList.size()!=0){
				 return true;
				 }
			
		 }catch (Exception e) {
			 return false;
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		 return false;
	}
	
	public Map<Integer, MarksCardSiNoGen> getStudentList1(MarksCardForm marksCardForm, String classIds)throws Exception{
		Map<Integer, MarksCardSiNoGen> mapList = new HashMap<Integer, MarksCardSiNoGen>();
		Session session = null;
		List<Object[]> objList=null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
				 query="select mg.studentId.id, mg from MarksCardSiNoGen mg "
				//+" where mg.classId.id=:ClassId and mg.examId.id=:ExamName  and mg.isRegular=1 ";
				 +" where mg.classId.id in (" + classIds + ")  and mg.isRegular=1 ";
			 
			 Query qr = session.createQuery(query);
			// qr.setString("ExamName", marksCardForm.getExamName());
			 objList = qr.list();
			 Iterator<Object[]> itr = objList.iterator();
			 while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				mapList.put(Integer.parseInt(objects[0].toString()), (MarksCardSiNoGen)objects[1]);
			}
			
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
			}
		}
		return mapList;
	}
	
	public List<MarksCardSiNoGen> getDataAvailable(MarksCardForm marksCardForm, String classIds)throws Exception{
		List<MarksCardSiNoGen> bo = new ArrayList<MarksCardSiNoGen>();
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
		if(marksCardForm.getExamType().equalsIgnoreCase("Regular")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isRegular=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isRegular=1").list();
					
				}
		}
		else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isSupplementary=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isSupplementary=1").list();
					
				}
	
		}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isRevaluation=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isRevaluation=1").list();
					
				}
		
			
		}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isImprovement=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isImprovement=1").list();
					
				}
			
		}
		else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
			if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
				bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isDuplicate=1 and m.isGrace=1").list();
				}else{
					bo = session.createQuery("from MarksCardSiNoGen m where m.classId.id in("+classIds+") and m.examId.id="+marksCardForm.getExamName()+" and m.studentId.registerNo='"+marksCardForm.getRegNo()+"' and m.isGrace=1").list();
					
				}
		}
			
			
//			session.flush();
//			session.close();
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return bo;
	}
	
	public boolean isMarksCardNumberGenerated(MarksCardForm marksCardForm, String classIds) throws Exception
	{
		Session session = null;
		List<MarksCardSiNoGen> markscardGenerated = new ArrayList<MarksCardSiNoGen>();
		try
		{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from MarksCardSiNoGen mkNo where mkNo.classId.id in (" + classIds + ")");			
			markscardGenerated = query.list();
			session.flush();
			return !markscardGenerated.isEmpty();
		}
		catch(Exception ex)
		{
			if (session != null){
				session.flush();
				session.close();				
			}
			throw new ApplicationException();
		}
	}
	
	public Map<Integer, String> getClassIds(MarksCardForm marksCardForm) throws Exception
	{
		Session session = null;
		
		try
		{
			session = HibernateUtil.getSession();
			String sqlQuery = "select classes.id, classes.name,left(curriculum_scheme_duration.academic_year,4) from classes" +
							  " inner join class_schemewise on class_schemewise.class_id = classes.id" +
							  " inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
							  " where curriculum_scheme_duration.academic_year=" + marksCardForm.getYear() + " and classes.term_number = " + marksCardForm.getSemister() +
							  " group by classes.id";
			List<Object[]> classIds = session.createSQLQuery(sqlQuery).list();
			session.flush();
			Map<Integer, String> classMap = new HashMap<Integer, String>();
			for (Object[] objects : classIds) {
				classMap.put(Integer.valueOf(objects[0].toString()),
						objects[1].toString().concat("("+objects[2].toString()+")"));
			}
			return classMap;
		}
		catch(Exception ex)
		{
			if(session != null)	
			{
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}

	public Set<Integer> getClassIdsByExamScehemeAcademicYear(MarksCardForm marksCardForm) throws Exception {
		
		Session session = null;
		
		try {
			
			session = HibernateUtil.getSession();
			String sqlQuery = "select classes.id from class_schemewise "  +
							   "inner join classes ON class_schemewise.class_id = classes.id " +
							   "inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
							   "inner join curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id " +
							   "inner join course ON classes.course_id = course.id " +
								 "and curriculum_scheme.course_id = course.id " +
								 
							   "where course.id in (select coz.id from EXAM_exam_course_scheme_details " +
										 			"inner join course coz ON EXAM_exam_course_scheme_details.course_id = coz.id " +
										 			"inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id " +
										 			"where EXAM_definition.id = " + marksCardForm.getExamName() +
													"  and EXAM_exam_course_scheme_details.scheme_no = " + marksCardForm.getSemister() +
													"  and coz.is_active = 1) " +
								 "and classes.is_active = 1 " +
								 "and course.is_active = 1 " +
								 "and curriculum_scheme_duration.semester_year_no = " + marksCardForm.getSemister() + " " +
								 "and curriculum_scheme_duration.academic_year = " + marksCardForm.getYear();
			List<Integer> classIds = session.createSQLQuery(sqlQuery).list();
			Set<Integer> classes = new HashSet<Integer>(classIds);
			
			return classes;
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
			if(session != null)	
			{
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}
}