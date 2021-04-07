package com.kp.cms.transactionsimpl.fee;

import java.util.ArrayList;
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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Fee;
import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.SemesterWiseCourseBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.fee.FeeAssignmentForm;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.forms.fee.SemesterWiseCourseEntryForm;
import com.kp.cms.transactions.fee.IFeeTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * 
 * @author microhard
 * @version 1.0
 * Date 09/jan/2009
 * This is Transaction Implementation Class for Fee Assignment.  
 * This class used to interact with fee,fee_assignment_account related tables.
 */
public class FeeTransactionTmpl implements IFeeTransaction{
        
	private static FeeTransactionTmpl feeAssignmentTransactionTmpl = null;
	private static final Log log = LogFactory.getLog(FeeTransactionTmpl.class);
	public static FeeTransactionTmpl getInstance() {
	    if(feeAssignmentTransactionTmpl == null ){
		    feeAssignmentTransactionTmpl = new FeeTransactionTmpl();
	       return feeAssignmentTransactionTmpl;
	    }
	    return feeAssignmentTransactionTmpl;
	}
	
	/**
	 * This method add the Fee and FeeAccountAssignment.
	 */
	public boolean addFeeAssignment(Fee fee) throws ConstraintViolationException,Exception{
		log.debug("Txn Impl : Entering addFeeAssignment ");
		Session session = null; 
		Transaction tx = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			 session = HibernateUtil.getSession();
			 session=sessionFactory.openSession();
			 tx = session.beginTransaction();
			 session.saveOrUpdate(fee);
			 tx.commit();
			 session.close();
			 log.debug("Txn Impl : Leaving addFeeAssignment with success");
	    	 return true;
		 } catch (ConstraintViolationException e) {
			 tx.rollback();
			 session.close();
			 log.debug("Txn Impl : Leaving addFeeAssignment with Exception"+e.getMessage());
			 throw e;				 
		 } catch (Exception e) {
			 tx.rollback();
			 session.close();
			 log.debug("Txn Impl : Leaving addFeeAssignment with Exception"+e.getMessage());
			 throw e;				 
		 } 
			 
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeAssignmentTransaction#getAllFeeAssignments()
	 */
	
	public List<Fee> getAllFees() throws Exception {
		log.debug("Txn Impl : Entering getAllFees ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from Fee where " +
					 						   " programType.isActive = 1"+
					 						   " and program.isActive = 1"+
					 						   " and course.isActive = 1"+
			 								   " and isActive = :isActive");
			 query.setBoolean("isActive",true);
			 List<Fee> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getAllFees with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getAllFees with Exception"+e.getMessage());
			 throw e;
		 }
	}
	
	/**
	 * This method delete the FeeAssignment from fee & fee_account_assignment tables.
	 *  @return true / flase based on result.
	 */
	public boolean deleteFeeAssignment(Fee fee) throws Exception{
		log.debug("Txn Impl : Entering deleteFeeAssignment "); 
		Session session = null;
		Transaction tx = null;
		Fee persistantFee;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();

			 persistantFee = (Fee)session.get(Fee.class, fee.getId());
			 Iterator<FeeAccountAssignment> itr = persistantFee.getFeeAccountAssignments().iterator();
			 while(itr.hasNext()){
				 itr.next().setIsActive(false);
			 }
			 persistantFee.setIsActive(false);
			 persistantFee.setModifiedBy(fee.getModifiedBy());
			 persistantFee.setLastModifiedDate(fee.getLastModifiedDate());
			 tx.commit();
			 //session.close();
			 log.debug("Txn Impl : Leaving deleteFeeAssignment with success");
	    	 return true;
		} catch (Exception e){
			 log.debug("Txn Impl : Leaving deleteFeeAssignment with Exception");
			 tx.rollback();
			 //session.close();
			 throw e;
		}
	 }
	
	/**
	 * This method delete the FeeAssignment from fee & fee_account_assignment tables.
	 *  @return true / flase based on result.
	 */
	public boolean activateFeeAssignment(Fee fee) throws Exception{
		log.debug("Txn Impl : Entering activateFeeAssignment ");
		Session session = null;
		Transaction tx = null;
		Fee persistantFee;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 tx.begin();

			 persistantFee = (Fee)session.get(Fee.class, fee.getId());
			 Iterator<FeeAccountAssignment> itr = persistantFee.getFeeAccountAssignments().iterator();
			 while(itr.hasNext()){
				 itr.next().setIsActive(true);
			 }
			 persistantFee.setIsActive(true);
			 persistantFee.setModifiedBy(fee.getModifiedBy());
			 persistantFee.setLastModifiedDate(fee.getLastModifiedDate());
			 
			 tx.commit();
			 //session.close();
			 log.debug("Txn Impl : Leaving activateFeeAssignment with success");
	    	 return true;
		} catch (Exception e){
			 log.debug("Txn Impl : Leaving activateFeeAssignment with Exception");
			 tx.rollback();
			 //session.close();
			 throw e;
		}
	 }
	
	/**
	 * This method add the Fee and FeeAccountAssignment.
	 */
	public boolean updateFeeAssignment(Fee fee) throws Exception{
		log.debug("Txn Impl : Entering updateFeeAssignment ");
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 session.close();
			 session = HibernateUtil.getSession();
		     tx = session.beginTransaction();
			 session.saveOrUpdate(fee);
			 tx.commit();
			 session.flush();
			 session.close();
			 log.debug("Txn Impl : Leaving updateFeeAssignment with success");
	    	 return true;
		 } catch (Exception e) {
			 tx.rollback();
			 session.close();
			 log.debug("Txn Impl : Leaving updateFeeAssignment with Exception");
			 throw e;				 
		 }
	}
	/**
	 * This method load the FeeAssignment from fee & fee_account_assignment tables.
	 *  @return Fee object.
	 */
	public Fee getFeeAssignmentById(int feeId) throws Exception{
		log.debug("Txn Impl : Entering getFeeAssignmentById "); 
		Session session = null;
		Fee fee;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
		    Transaction tx = session.beginTransaction();
			tx.begin();
			fee = (Fee)session.get(Fee.class, feeId);
			tx.commit();
			//session.close();
			log.debug("Txn Impl : Leaving getFeeAssignmentById with success");
	    	return fee;
		} catch (Exception e){
			log.debug("Txn Impl : Leaving getFeeAssignmentById with Exception");
			//session.close();
			throw e;
		}
	 
	 }
	 
	 /**
	  * 
	  */
	 public List<FeeAccountAssignment> getFeesAssignAccounts(int feeId,Set<Integer> accountSet,Set<Integer> applicableSet) throws Exception {
		log.debug("Txn Impl : Entering getFeesAssignAccounts ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from FeeAccountAssignment where fee.id = :feeId"
					                           +" and feeAccount.id in (:accountSet)"
			 								   +" and applicableFees.id in (:applicableSet)");
			 query.setInteger("feeId", feeId);
			 query.setParameterList("accountSet", accountSet);
			 query.setParameterList("applicableSet", applicableSet);
			
			 List<FeeAccountAssignment> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getFeesAssignAccounts with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getFeesAssignAccounts with Exception");
			 throw e;
		 }
	}
	 
	 public List<Fee> getFeesPaymentDetailsForApplicationNo(Set<Integer> courseSet, String year, Set<Integer> semSet, boolean isAided,FeePaymentForm feePaymentForm) throws Exception {
		log.debug("Txn Impl : Entering getFeesPaymentDetailsForApplicationNo ");
		Session session = null;
		boolean isSemesterWise = false;
		try {
			 session = HibernateUtil.getSession();
			 SemesterWiseCourseBO bo = getSemWiseCourse(feePaymentForm);
			 if(bo != null && !bo.toString().isEmpty()){
				 isSemesterWise = true;
			 }
			 String sqlQuery = "";
			 Set<Integer> yearSet = new HashSet<Integer>();
			 Set<String> semPack = new HashSet<String>();
			 Iterator<Integer> itr = semSet.iterator();
			 while (itr.hasNext()){
				 int sem = itr.next();
				 if(sem==1 || sem==2){
					 yearSet.add(1);
				 	semPack.add(String.valueOf(sem));
				 } else if(sem==3 || sem==4){
					 yearSet.add(2);
					 semPack.add(String.valueOf(sem));
				 } else if(sem==5 || sem==6){
					 yearSet.add(3);
					 semPack.add(String.valueOf(sem));
				 }
			 }
			 if(!isSemesterWise){
			 if(isAided){
				 sqlQuery = "select f from Fee f  " 
				 		+ "join f.feeAccountAssignments fa" 
				 		+ "where f.course.id in (:courseSet)"
					   + " and f.semesterNo in (:semsSet) "
						   + " and f.academicYear = :academicYear"
						   + " and f.isActive =:isActive and"
						   + " f.aidedUnaided = 'Aided'"
						   + " and fa.feeCategory.id = :feeCategoryId";
			 }else{
				 sqlQuery = "select f from Fee f " 
				 		+ "join f.feeAccountAssignments fa  " 
				 		+ "where f.course.id in (:courseSet)"
					   + " and f.semesterNo in (:semsSet) "
						   + " and f.academicYear = :academicYear"
						   + " and f.isActive =:isActive and"
						   + " f.aidedUnaided = 'Unaided'"
						   + " and fa.feeCategory.id = :feeCategoryId";
			 }
			}else {
				if(isAided){
					 sqlQuery = "select f from Fee f  " 
					 		+ "join f.feeAccountAssignments fa" 
					 		+ "where f.course.id in (:courseSet)"
						   + " and f.semesterNo in (:semsSet) "
							   + " and f.academicYear = :academicYear"
							   + " and f.isActive =:isActive and"
							   + " f.aidedUnaided = 'Aided'"
							   + " and fa.feeCategory.id = :feeCategoryId"
							   + " and f.courseSemNo in (:semPack)";
				 }else{
					 sqlQuery = "select f from Fee f " 
					 		+ "join f.feeAccountAssignments fa  " 
					 		+ "where f.course.id in (:courseSet)"
						   + " and f.semesterNo in (:semsSet) "
							   + " and f.academicYear = :academicYear"
							   + " and f.isActive =:isActive and"
							   + " f.aidedUnaided = 'Unaided'"
							   + " and fa.feeCategory.id = :feeCategoryId" 
							   + " and f.courseSemNo in (:semPack)";
				 }
			}
			 Query query = session.createQuery(sqlQuery);
			 query.setParameterList("courseSet", courseSet);
			 query.setParameterList("semsSet", yearSet);
			 query.setInteger("academicYear", new Integer(year));
			 query.setBoolean("isActive", true);
			 query.setInteger("feeCategoryId", Integer.parseInt(feePaymentForm.getFeeCategory()));
			 if(isSemesterWise == true){
				 query.setParameterList("semPack", semPack);
			 }
			 
			 List<Fee> list = query.list();
			 log.debug("Txn Impl : Leaving getFeesPaymentDetailsForApplicationNo with success");
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getFeesPaymentDetailsForApplicationNo with Exception");
			 throw e;
		 }
	 }
	 
	private SemesterWiseCourseBO getSemWiseCourse(FeePaymentForm feePaymentForm) throws Exception {
		Session session = null;
		SemesterWiseCourseBO bo = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from SemesterWiseCourseBO s  where s.course.id in (:courseSet)";
			Query query = session.createQuery(s)
							.setInteger("courseSet", Integer.parseInt(feePaymentForm.getCourseId()));
			bo = (SemesterWiseCourseBO) query.uniqueResult();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return bo;
	}

	/**
	* This method load the FeeAssignment from fee & fee_account_assignment tables.
	*  @return Fee object.
	*/
	public Fee getFeeByCompositeKeys(Fee feeOld, int feeCategoryId) throws Exception{
		log.debug("Txn Impl : Entering getFeeByCompositeKeys "); 
		Session session = null;
		Fee fee=null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
		     Transaction tx = session.beginTransaction();
			 tx.begin();
			/* Query query = session.createQuery("from Fee where programType.id = :programType"
					 							+" and program.id = :program "
					 							+" and course.id = :course "
					 							+" and academicYear = :academicYear"
					 							+" and semesterNo = :semesterNo"
					 							+" and aidedUnaided=:aidedUnaided"
					 							);*/
			 Query query = session.createQuery("select f from FeeAccountAssignment faa "
			 			+" inner join faa.fee f where f.programType.id = :programType"
						+" and f.program.id = :program "
						+" and f.course.id = :course "
						+" and f.academicYear = :academicYear"
						+" and f.semesterNo = :semesterNo"
						+" and f.aidedUnaided=:aidedUnaided"
						+" and faa.feeCategory.id=:feeCategory"
						);
			 query.setInteger("programType", feeOld.getProgramType().getId());
			 query.setInteger("program", feeOld.getProgram().getId());
			 query.setInteger("course", feeOld.getCourse().getId());
			 query.setInteger("academicYear", feeOld.getAcademicYear());
			 query.setInteger("semesterNo", feeOld.getSemesterNo());
			 query.setString("aidedUnaided",feeOld.getAidedUnaided());
			 query.setInteger("feeCategory", feeCategoryId);
			 fee =(Fee)query.uniqueResult();
			 tx.commit();
			 //session.close();
			 log.debug("Txn Impl : Leaving getFeeByCompositeKeys with success");
	    	 return fee;
		 } catch (Exception e){
			 log.debug("Txn Impl : Leaving getFeeByCompositeKeys with Exception");
			 //session.close();
			 throw e;
		 }
		 
	 }
	
	/**
	 * 
	 */
	public Map<Integer,String> getFeesGroupDetailsForCourse(Set<Integer>courseSet,String year) throws Exception {
		log.debug("Txn Impl : Entering getFeesGroupDetailsForCourse ");
		Map<Integer,String> feeGroupMap = new HashMap<Integer,String>();
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select distinct fee.feeGroup.id, fee.feeGroup.name "
					 +" from Fee fee"  
					 +" inner join fee.feeGroup.feeHeadings headings" 
					 +" where headings.isActive = 1"
					 +" and fee.feeGroup.isActive = 1"
					 +" and fee.isActive = 1 "
					 +" and fee.course.id = :courseSet"
					 +" and fee.academicYear = :academicYear");
			 query.setParameterList("courseSet", courseSet);
			 query.setInteger("academicYear",Integer.valueOf(year));
			 
			 List<Object[]> list = query.list();
			 Iterator<Object[]> itr = list.iterator();
			 while(itr.hasNext()) {
				 Object[] row = itr.next();
				 feeGroupMap.put((Integer)row[0], row[1].toString());
			 }
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getFeesGroupDetailsForCourse with success");
			 return feeGroupMap;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getFeesGroupDetailsForCourse with Exception");
			 throw e;
		 }
	 }

	
	public List<FeeGroup> getFeeGroup() throws Exception {
		log.info("call of getFeeGroup method in FeeGroupTransactionImpl class.");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select g from FeeGroup g " +
			 		"inner join g.feeHeadings h " +
			 		"where g.isActive=1 " +
			 		"and g.isOptional=0 " +
			 		"group by g.id");
			 List<FeeGroup> feeGroup = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getFeeGroup method in FeeGroupTransactionImpl class.");
			 return feeGroup;
		 } catch (Exception e) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(e);
		 }
	 }

	@Override
	public Map<Integer, String> getCourse() throws Exception {
		Session session = null;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		List<Course> courseList = new ArrayList<Course>();
		try{
			session = HibernateUtil.getSession();
			String s = "from Course c";
			Query query = session.createQuery(s);
			courseList = query.list();
			if(courseList != null ){
				Iterator<Course> iterator = courseList.iterator();
				while(iterator.hasNext()){
					Course course = iterator.next();
					courseMap.put(course.getId(), course.getName());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return courseMap;
	}

	@Override
	public Fee getFeeData(
			SemesterWiseCourseEntryForm semesterWiseCourseEntryForm)
			throws Exception {
		Session session = null;
		Fee fee = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from Fee f  " +
					"where f.course.id = :courseId " +
					"and f.academicYear = :academicYear  " +
					"and f.courseSemNo = :semNo  " +
					"and f.isCourseSemesterWise = 1";
			Query query = session.createQuery(s)
							.setString("courseId", semesterWiseCourseEntryForm.getCourseId())
							.setString("academicYear", semesterWiseCourseEntryForm.getAcademicYear())
							.setString("semNo", semesterWiseCourseEntryForm.getSemester());
			fee = (Fee)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return fee;
	}

	@Override
	public Course getObject(String courseId) throws Exception {
		Session session = null;
		Course course = new Course();
		try{
			session = HibernateUtil.getSession();
			course = (Course) session.get(Course.class, Integer.parseInt(courseId));
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return course;
	}

	@Override
	public boolean saveDetails(SemesterWiseCourseBO bo2) throws Exception {
		Session session = null; 
		Transaction tx = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			 session = HibernateUtil.getSession();
			 session=sessionFactory.openSession();
			 tx = session.beginTransaction();
			 session.saveOrUpdate(bo2);
			 tx.commit();
			 session.close();
			 log.debug("Txn Impl : Leaving addFeeAssignment with success");
	    	 return true;
		 } catch (ConstraintViolationException e) {
			 tx.rollback();
			 session.close();
			 log.debug("Txn Impl : Leaving addFeeAssignment with Exception"+e.getMessage());
			 throw e;				 
		 } catch (Exception e) {
			 tx.rollback();
			 session.close();
			 log.debug("Txn Impl : Leaving addFeeAssignment with Exception"+e.getMessage());
			 throw e;				 
		 } 
			 
	}

	@Override
	public SemesterWiseCourseBO isSemesterWise(FeeAssignmentForm feeAssignmentForm)
			throws Exception {
		Session session = null;
		SemesterWiseCourseBO bo = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from SemesterWiseCourseBO b  " +
					"where b.course.id = :courseId  " +
					"and b.isSemesterWise = 1";
			Query query = session.createQuery(s)
							.setString("courseId", feeAssignmentForm.getCourseId());
			bo = (SemesterWiseCourseBO)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return bo;
	}

	@Override
	public SemesterWiseCourseBO getCourseObject(SemesterWiseCourseEntryForm semesterWiseCourseEntryForm) throws Exception {
		Session session = null;
		SemesterWiseCourseBO bo = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from SemesterWiseCourseBO b  " +
					"where b.course.id = :courseId  " +
					"and b.isSemesterWise = 1";
			Query query = session.createQuery(s)
							.setString("courseId", semesterWiseCourseEntryForm.getCourseId());
			bo = (SemesterWiseCourseBO)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return bo;
	}

	@Override
	public Fee getFeeByCompositeKeysSemesterWise(Fee duplicateFee, int feeCategoryId)
			throws Exception {
		log.debug("Txn Impl : Entering getFeeByCompositeKeys "); 
		Session session = null;
		Fee fee=null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
		     Transaction tx = session.beginTransaction();
			 tx.begin();
			/* Query query = session.createQuery("from Fee where programType.id = :programType"
					 							+" and program.id = :program "
					 							+" and course.id = :course "
					 							+" and academicYear = :academicYear"
					 							+" and semesterNo = :semesterNo"
					 							+" and aidedUnaided=:aidedUnaided"
					 							);*/
			 Query query = session.createQuery("select f from FeeAccountAssignment faa "
			 			+" inner join faa.fee f where f.programType.id = :programType"
						+" and f.program.id = :program "
						+" and f.course.id = :course "
						+" and f.academicYear = :academicYear"
						+" and f.semesterNo = :semesterNo"
						+" and f.aidedUnaided=:aidedUnaided"
						+" and faa.feeCategory.id=:feeCategory"
						+" and f.courseSemNo = :courseSemNo"
						);
			 query.setInteger("programType", duplicateFee.getProgramType().getId());
			 query.setInteger("program", duplicateFee.getProgram().getId());
			 query.setInteger("course", duplicateFee.getCourse().getId());
			 query.setInteger("academicYear", duplicateFee.getAcademicYear());
			 query.setInteger("semesterNo", duplicateFee.getSemesterNo());
			 query.setString("aidedUnaided",duplicateFee.getAidedUnaided());
			 query.setString("courseSemNo", duplicateFee.getCourseSemNo());
			 query.setInteger("feeCategory", feeCategoryId);
			 fee =(Fee)query.uniqueResult();
			 tx.commit();
			 //session.close();
			 log.debug("Txn Impl : Leaving getFeeByCompositeKeys with success");
	    	 return fee;
		 } catch (Exception e){
			 log.debug("Txn Impl : Leaving getFeeByCompositeKeys with Exception");
			 //session.close();
			 throw e;
		 }
	}

	@Override
	public Fee getExistingObject(FeeAssignmentForm feeAssignmentForm)
			throws Exception {
		Session session = null;
		Fee fee = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from Fee f  " +
					"where f.course.id = :courseId " +
					"and f.academicYear = :academicYear  " +
					"and f.courseSemNo = :semNo  " +
					"and f.isCourseSemesterWise = 1";
			Query query = session.createQuery(s)
							.setString("courseId", feeAssignmentForm.getCourseId())
							.setString("academicYear", feeAssignmentForm.getAcademicYear())
							.setString("semNo", feeAssignmentForm.getSem());
			fee = (Fee)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return fee;
	}

	

	
}
