package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ChallanUploadData;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ChallanUploadDataExam;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMarksEntryUtilBO;
import com.kp.cms.bo.exam.ExamRegularApplication;
import com.kp.cms.bo.fees.ChallanUploadDataFee;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.admission.ExcelDataTO;
import com.kp.cms.transactions.admission.IExcelTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExcelTransactionImpl implements IExcelTransaction {

	private static final Log log = LogFactory.getLog(ExcelTransactionImpl.class);
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IExcelTransaction#isDataUploaded(java.util.List)
	 */
	@Override
	public boolean isDataUploaded(List<Student> resultList)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if (resultList != null && !resultList.isEmpty()) {
				Iterator<Student> iterator = resultList.iterator();
				while (iterator.hasNext()) {
					Student student = iterator.next();
						session.save(student);
				}
			transaction.commit();
			isAdded = true;
			}
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}
	
	/**
	 * This method is used to get AdmAppln details from database.
	 */

	@Override
	public Map<Integer, Integer> getAdmAppDetails(int year,int csDurationId,int courseId) throws Exception {
		log.info("call of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String hqlQuery="select st.admAppln.applnNo,st.admAppln.id " +
			"from Student st where st.isAdmitted=1" +
			" and st.isActive = 1" +
			" and st.admAppln.isSelected=1" +
			" and st.admAppln.isCancelled=0 " +
			" and st.admAppln.isApproved=1 and st.admAppln.courseBySelectedCourseId.id = :courseId";
			if(csDurationId>0){
				hqlQuery=hqlQuery+" and st.classSchemewise.curriculumSchemeDuration.semesterYearNo=(select c.semesterYearNo " +
				" from CurriculumSchemeDuration c where c.id = :csDurationId)";
			}
			Query query = session.createQuery(hqlQuery)
								 .setInteger("courseId", courseId)
								 .setInteger("csDurationId", csDurationId);
			Iterator<Object[]> it = query.iterate();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				if(obj!=null){
					map.put((Integer)obj[0],(Integer)obj[1]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		return map;
	}
	
	
	@Override
	public Map<String, Integer> getClasses(int courseId, int csId,int academicYear) throws Exception {
		log.info("call of getClasses method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Map<String,Integer> classesMap = new HashMap<String, Integer>();
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			String hqlQuery="select classSchemewise.classes.name, " +
			" classSchemewise.classes.id " +
			" from ClassSchemewise classSchemewise " +
			" where classSchemewise.curriculumSchemeDuration.academicYear = :year " +
			" and classSchemewise.classes.course.id = :courseId" + 
			" and classSchemewise.classes.isActive = 1";
			if(csId>0){
				hqlQuery=hqlQuery+" and classSchemewise.curriculumSchemeDuration.id = :csId" ;
			}
			session = HibernateUtil.getSession();
			Query query = session.createQuery(hqlQuery);
			query.setInteger("courseId", courseId);
			if(csId>0){
				query.setInteger("csId",csId);
			}
			query.setInteger("year",academicYear);
			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				if(obj!=null){
					classesMap.put((String)obj[0],(Integer)obj[1]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		return classesMap;
	}
	
	/**
	 * This method is used get class id based on courseId,secName,curriculumSchemeDurationId,year.
	 */
	
	@Override
	public int getClassId(int courseId, String className, int csDurationId, Integer year)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		int id = 0;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			List queryList = session.createQuery("select cs.classes.id from ClassSchemewise cs where cs.classes.course.id= '"+ courseId +"' and cs.classes.name= '"+className+"' and cs.curriculumSchemeDuration.id= '"+csDurationId+"' and cs.curriculumSchemeDuration.academicYear= '"+year+"' and cs.classes.id <> null and cs.classes.id <> '' and cs.classes.isActive= 1").list();
			
			if(queryList!=null && !queryList.isEmpty()){
				id = (Integer) session.createQuery("select cs.classes.id from ClassSchemewise cs where cs.classes.course.id= '"+ courseId +"' and cs.classes.name= '"+className+"' and cs.curriculumSchemeDuration.id= '"+csDurationId+"' and cs.curriculumSchemeDuration.academicYear= '"+year+"' and cs.classes.id <> null and cs.classes.id <> '' and cs.classes.isActive= 1").uniqueResult();
			}
		}catch(Exception e){
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return id;
	}
	
	/**
	 *	This method is used to get classSchemeWiseId based on classId, curriculumSchemeDurationId. 
	 */
	
	@Override
	public int getClassSchemeWiseId(int classId, int csDurationId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		int id = 0;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			String query="select id from ClassSchemewise csw where csw.classes.id= '"+classId+"' and csw.id <> null and csw.id <> '' ";
			if(csDurationId!=0){
				query=query+" and csw.curriculumSchemeDuration.id= '"+ csDurationId +"'";
			}
			id = (Integer) session.createQuery(query).uniqueResult();
		}catch(Exception e){
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return id;
	}

	/**
	 *	This method is used to check admApplnId in student table. 
	 */

	@Override
	public boolean checkAdmApplnId(int admApplnId) throws Exception {
		Session session = null;
		boolean isExist =true;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select id from Student s where s.admAppln.id= :admId");
			query.setInteger("admId",admApplnId);
			List<Student> list=query.list();
			 if(list!=null && !list.isEmpty()){
				 isExist=false;
			 }
		} catch (Exception e) {
			isExist =true;
			log.error("Error while getting applicant details in checkAdmApplnId...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of checkDuplicate method in  UploadInterviewResultTransactionImpl class.");
		return isExist;
	}


	/**
	 * This method is used to get studentId based on admApplnId.
	 */

	@Override
	public int getStudentId(int admId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		int id = 0;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			id = (Integer) session.createQuery("select id from Student s where s.isActive=1 and s.admAppln.id='"+admId+"'").uniqueResult();
			
		}catch(Exception e){
			log.error("Error while getting applicant details in getStudentId...",e);
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return id;
	}
	
	/**
	 * This method is used to update student data.
	 */

	@Override
	public boolean updateStudent(List<ExcelDataTO> dataTOList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ClassSchemewise classSchemewise;
		boolean isAdded = false;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(dataTOList != null && !dataTOList.isEmpty()){
				Iterator itr = dataTOList.iterator();
				while (itr.hasNext()) {
					ExcelDataTO dataTO = (ExcelDataTO) itr.next();
					classSchemewise = new ClassSchemewise();
					Student student = (Student)session.get(Student.class,dataTO.getStudentId());
					if(dataTO.getRegistrationNumber()!=null){
						student.setRegisterNo(dataTO.getRegistrationNumber());
					}if(dataTO.getRollNumber()!=null){
						student.setRollNo(dataTO.getRollNumber());
					}
					classSchemewise.setId(dataTO.getClassSchemeWiseId());
					student.setClassSchemewise(classSchemewise);
					session.update(student);
				}
				transaction.commit();
				isAdded = true;
			}
			
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}

	/**
	 * This method is used to check duplicate sectionName.
	 */

	@Override
	public boolean checkDuplicateSectionName(int courseId, int termNo, String sectionName) throws Exception {
		Session session = null;
		boolean isExist =false;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query= session.createQuery("from Classes csw where csw.sectionName= :secName and csw.course.id= :courseId and csw.termNumber= :curscheme and csw.isActive= 1");
			query.setInteger("courseId",courseId);
			query.setInteger("curscheme", termNo);
			query.setString("secName", sectionName);
			List<ClassSchemewise> list = query.list(); 
			 if(list!=null && !list.isEmpty()){
				 isExist=true;
			 }
		} catch (Exception e) {
			isExist =false;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of checkDuplicate method in  UploadInterviewResultTransactionImpl class.");
		return isExist;
	}
	
	/**
	 * This method is used to update student data.
	 */

	@Override
	public boolean updateChallan(List<ExcelDataTO> dataTOList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ClassSchemewise classSchemewise;
		boolean isAdded = false;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(dataTOList != null && !dataTOList.isEmpty()){
				Iterator itr = dataTOList.iterator();
				int count=0;
				while (itr.hasNext()) {
					ExcelDataTO dataTO = (ExcelDataTO) itr.next();
					ChallanUploadData data=new ChallanUploadData();
					data.setChallanNo(dataTO.getChallanNumber());
					data.setChallanDate(dataTO.getChallanDate());
					data.setYear(dataTO.getYear());
					data.setCreatedDate(new Date());
					data.setCreatedBy(dataTO.getUserId());
					
					session.persist(data);
					if(++count%20==0)
					{
						session.flush();
						session.clear();
					}
					
				}
				
				transaction.commit();
				isAdded = true;
			}
			
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}
	
	
	

	public List<String> getChallanList(String year) throws Exception
	{
		
		List<String> challanList=new ArrayList<String>();
		try
		{
			Session session=HibernateUtil.getSession();
			String Query=	"select cu.challanNo from ChallanUploadData cu where cu.year='"+year+"'";
			
			challanList=session.createQuery(Query).list();
				
		}
		catch (Exception e) {
			throw new BusinessException(e.getMessage());
			
		}
		return challanList;
	}
	
	@Override
	public boolean updateChallanExam(List<ExcelDataTO> dataTOList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ClassSchemewise classSchemewise;
		boolean isAdded = false;
		int count1=0;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(dataTOList != null && !dataTOList.isEmpty()){
				Iterator itr = dataTOList.iterator();
				int count=0;
				while (itr.hasNext()) {
					ExcelDataTO dataTO = (ExcelDataTO) itr.next();
					//ExamDefinitionBO exam=new ExamDefinitionBO();
					ChallanUploadDataExam data=new ChallanUploadDataExam();
					data.setChallanNo(dataTO.getChallanNumber());
					data.setChallanDate(dataTO.getChallanDate());
					data.setAmount(dataTO.getAmount());
					//data.setYear(dataTO.getYear());
					data.setCreatedDate(new Date());
					data.setCreatedBy(dataTO.getUserId());
					/*if(dataTO.getExamId()!=null)
					exam.setId(Integer.parseInt(dataTO.getExamId()));
					data.setExam(exam);*/
					
					session.persist(data);
					count1++;
					if(++count%20==0)
					{
						session.flush();
						session.clear();
					}
					
				}
				
				transaction.commit();
				if(count1>0)
				isAdded = true;
			}
			
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}
	
	@Override
	public boolean verifyChallanExam(List<ExcelDataTO> dataTOList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ClassSchemewise classSchemewise;
		ExamRegularApplication examRegularApp;
		boolean isAdded = false;
		int count1=0;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(dataTOList != null && !dataTOList.isEmpty()){
				Iterator itr = dataTOList.iterator();
				int count=0;
				while (itr.hasNext()) {
					ExcelDataTO dataTO = (ExcelDataTO) itr.next();
					String query="from ExamRegularApplication e where e.challanNo='"+dataTO.getChallanNumber()+"'";
					examRegularApp=(ExamRegularApplication)session.createQuery(query).uniqueResult();
					if(examRegularApp!=null){
					examRegularApp.setIsChallanVerified(true);
					dataTO.setIsVerified(true);
					session.saveOrUpdate(examRegularApp);
					count1++;
					}
					if(++count%20==0)
					{
						session.flush();
						session.clear();
					}
					
				}
				
				transaction.commit();
				if(count1>0)
				isAdded = true;
			}
			
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}
	
	public List<String> getChallanListForExam(String year,int examId) throws Exception
	{
		
		List<String> challanList=new ArrayList<String>();
		try
		{
			Session session=HibernateUtil.getSession();
			String Query=	"select cu.challanNo from ChallanUploadDataExam cu";
			challanList=session.createQuery(Query).list();
				
		}
		catch (Exception e) {
			throw new BusinessException(e.getMessage());
			
		}
		return challanList;
	}
	
	public List<String> getChallanListForFee() throws Exception
	{
		
		List<String> challanList=new ArrayList<String>();
		try
		{
			Session session=HibernateUtil.getSession();
			String Query=	"select cu.challanNo from ChallanUploadDataFee cu";
			challanList=session.createQuery(Query).list();
				
		}
		catch (Exception e) {
			throw new BusinessException(e.getMessage());
			
		}
		return challanList;
	}
	
	@Override
	public boolean updateChallanFee(List<ExcelDataTO> dataTOList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ClassSchemewise classSchemewise;
		boolean isAdded = false;
		int count1=0;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(dataTOList != null && !dataTOList.isEmpty()){
				Iterator itr = dataTOList.iterator();
				int count=0;
				while (itr.hasNext()) {
					ExcelDataTO dataTO = (ExcelDataTO) itr.next();
					//ExamDefinitionBO exam=new ExamDefinitionBO();
					ChallanUploadDataFee data=new ChallanUploadDataFee();
					data.setChallanNo(dataTO.getChallanNumber());
					data.setChallanDate(dataTO.getChallanDate());
					data.setAmount(dataTO.getAmount());
					//data.setYear(dataTO.getYear());
					data.setCreatedDate(new Date());
					data.setCreatedBy(dataTO.getUserId());
					/*if(dataTO.getExamId()!=null)
					exam.setId(Integer.parseInt(dataTO.getExamId()));
					data.setExam(exam);*/
					
					session.persist(data);
					count1++;
					if(++count%20==0)
					{
						session.flush();
						session.clear();
					}
					
				}
				
				transaction.commit();
				if(count1>0)
				isAdded = true;
			}
			
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}

	@Override
	public boolean updateStudentData(List<ExcelDataTO> updateList)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		ClassSchemewise classSchemewise;
		boolean isAdded = false;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(updateList != null && !updateList.isEmpty()){
				Iterator itr = updateList.iterator();
				while (itr.hasNext()) {
					ExcelDataTO dataTO = (ExcelDataTO) itr.next();
					classSchemewise = new ClassSchemewise();
					Student student = (Student)session.get(Student.class,dataTO.getStudentId());
					if(dataTO.getRegistrationNumber()!=null){
						student.setRegisterNo(dataTO.getRegistrationNumber());
					}if(dataTO.getUniversityRegNo()!=null){
						student.setUniversityRegisterNo(dataTO.getUniversityRegNo());
					}
					classSchemewise.setId(dataTO.getClassSchemeWiseId());
					student.setClassSchemewise(classSchemewise);
					session.update(student);
				}
				transaction.commit();
				isAdded = true;
			}
			
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}

	@Override
	public int getStudentObj(int admId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		int id = 0;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			id = (Integer) session.createQuery("select id from Student s where s.isActive=1 and s.admAppln.applnNo='"+admId+"'").uniqueResult();
			
		}catch(Exception e){
			log.error("Error while getting applicant details in getStudentId...",e);
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return id;
	}

	@Override
	public int getClassSchId(int studId) throws Exception {
		Session session = null;
		int id = 0;
		try{
			session = HibernateUtil.getSession();
			String s = "select s.classSchemewise.id from Student s where s.id = :studId ";
			Query query = session.createQuery(s)
						.setInteger("studId", studId);
			id = (Integer)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return id;
	}
	
}