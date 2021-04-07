package com.kp.cms.transactionsimpl.examallotment;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.examallotment.ClassGroup;
import com.kp.cms.bo.examallotment.GroupClasses;
import com.kp.cms.bo.examallotment.StudentClassGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.examallotment.StudentClassGroupForm;
import com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentClassGroupTxnImpl implements IStudentClassGroupTransaction {

	private static volatile StudentClassGroupTxnImpl studentClassGroupTxnImpl=null;
	private static final Log log = LogFactory.getLog(StudentClassGroupTxnImpl.class);
	 
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public static StudentClassGroupTxnImpl getInstance(){
		if(studentClassGroupTxnImpl == null){
			studentClassGroupTxnImpl=new StudentClassGroupTxnImpl();
		}
		return studentClassGroupTxnImpl;
	}
	
	public StudentClassGroupTxnImpl() {

	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentsClassGroupTransaction#getWorkLocations()
	 */
	@Override
	public List<EmployeeWorkLocationBO> getWorkLocations() throws Exception {
		Session session = null;
		List<EmployeeWorkLocationBO> locationBOList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from EmployeeWorkLocationBO location where location.isActive=1";
			Query query =  session.createQuery(hqlQuery);
			locationBOList = query.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.close();
		}
		
		return locationBOList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentsClassGroupTransaction#getClassGroupList()
	 */
	@Override
	public List<ClassGroup> getClassGroupList() throws Exception {
		Session session = null;
		List<ClassGroup> classGroupsList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ClassGroup c where c.isActive=1";
			Query query =  session.createQuery(hqlQuery);
			classGroupsList = query.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.close();
		}
		return classGroupsList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#getclassesByYearAndLocationId(com.kp.cms.forms.examallotment.StudentClassGroupForm)
	 */
	@Override
	public List<Classes> getclassesByYearAndLocationId(
			StudentClassGroupForm classGroupForm) throws Exception {
		Session session = null;
		List<Classes> classList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select c from Classes c join c.course cou join c.classSchemewises scheme " +
					" where c.isActive=1 and cou.isActive=1" +
					" and scheme.curriculumSchemeDuration.academicYear='"+classGroupForm.getAcademicYear()+"'" ;
			if(classGroupForm.getCampusName()!=null && !classGroupForm.getCampusName().isEmpty()){
				hqlQuery=hqlQuery+"and cou.workLocation.id='"+classGroupForm.getCampusName()+"'";
			}if(classGroupForm.getSchemeNo()!=null && !classGroupForm.getSchemeNo().isEmpty()){
				hqlQuery=hqlQuery+"and c.termNumber='"+classGroupForm.getSchemeNo()+"'";
			}
			hqlQuery=hqlQuery+"order by c.name";
			Query query =  session.createQuery(hqlQuery);
			classList = query.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		return classList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#getStudentDetailsByClasses(com.kp.cms.forms.examallotment.StudentClassGroupForm, java.util.List)
	 */
	@Override
	public List<Student> getStudentDetailsByClasses(StudentClassGroupForm classGroupForm,List<Integer> selectedClassList) throws Exception {
		Session session = null;
		List<Student> studentList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = " select student from Student student where student.isActive=1 " +
			" and student.classSchemewise.curriculumSchemeDuration.academicYear='"+classGroupForm.getAcademicYear()+"' " +
			" and student.classSchemewise.classes.id  in(:classIds)" +
			" and  student.classSchemewise.curriculumSchemeDuration.semesterYearNo='"+classGroupForm.getSchemeNo()+"'" +
			" and (student.isHide=0 or student.isHide is null) and student.admAppln.isCancelled=0 " +
			" and student.id not in (select det.student.id from ExamStudentDetentionRejoinDetails det " +
			" where (det.detain=1 or det.discontinued=1) and (det.rejoin=0 or det.rejoin is null))" +
			" order by student.registerNo" ;
			Query query =  session.createQuery(hqlQuery);
			query.setParameterList("classIds", selectedClassList);
			studentList = query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		return studentList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#addStudentToClassGroup(java.util.List)
	 */
	@Override
	public boolean addStudentToClassGroup(List<GroupClasses> groupClassList)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean saved=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(!groupClassList.isEmpty()){
			for (GroupClasses groupClasses : groupClassList) {
			    session.save(groupClasses);
			}
			 saved=true;
			transaction.commit();
			}
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return saved;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#getStudentClassGroup()
	 */
	@Override
	public List<GroupClasses> getStudentClassGroup(int year) throws Exception {
		Session session = null;
		List<GroupClasses> groupClassList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select cl from GroupClasses cl join cl.classId.classSchemewises scheme where scheme.curriculumSchemeDuration.academicYear="+year +
					" and cl.isActive=1 group by cl.classGroup.id" ;
			Query query =  session.createQuery(hqlQuery);
			groupClassList = query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		return groupClassList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#getStudentFromGroupClassByClasses(java.util.List)
	 */
	@Override
	public List<StudentClassGroup> getStudentFromGroupClassByClasses(
			List<Integer> selectedClassList) throws Exception {
		Session session = null;
		List<StudentClassGroup> studentClassGroupList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from StudentClassGroup classGroup where classGroup.isActive=1 and classGroup.groupClasses.classId.id in(:classIds)" ;
			Query query =  session.createQuery(hqlQuery);
			query.setParameterList("classIds", selectedClassList);
			studentClassGroupList = query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		return studentClassGroupList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#getGroupClassesByGroupId(int)
	 */
	@Override
	public List<GroupClasses> getGroupClassesByGroupId(int groupId)
			throws Exception {
		Session session = null;
		List<GroupClasses> groupClassList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from GroupClasses groupClass where groupClass.isActive=1 and groupClass.classGroup.id="+groupId ;
			Query query =  session.createQuery(hqlQuery);
			groupClassList = query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		return groupClassList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#getGroupClassByGroupIdAndClassId(java.lang.String, java.lang.String)
	 */
	@Override
	public GroupClasses getGroupClassByGroupIdAndClassId(String classGroup,
			String classId) throws Exception {
		Session session = null;
		GroupClasses groupClasses=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from GroupClasses groupClass where groupClass.isActive=1 and groupClass.classGroup.id='"+classGroup+"' and groupClass.classId.id='"+classId+"'";
			Query query =  session.createQuery(hqlQuery);
			groupClasses = (GroupClasses) query.uniqueResult();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		return groupClasses;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#getStudentFromGroupClassByClassesAndGroupId(java.util.List, java.lang.String)
	 */
	@Override
	public List<StudentClassGroup> getStudentFromGroupClassByClassesAndGroupId(
			List<Integer> selectedClassList, String classGroup)
			throws Exception {
		Session session = null;
		List<StudentClassGroup> studentClassGroupList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from StudentClassGroup classGroup where classGroup.isActive=1 and classGroup.groupClasses.classId.id in(:classIds)  and classGroup.groupClasses.classGroup.id='"+classGroup+"'" ;
			Query query =  session.createQuery(hqlQuery);
			query.setParameterList("classIds", selectedClassList);
			studentClassGroupList = query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		return studentClassGroupList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#updateStudentToClassGroup(java.util.List)
	 */
	@Override
	public boolean updateStudentToClassGroup(List<GroupClasses> groupClassList)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean updated=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(!groupClassList.isEmpty()){
			for (GroupClasses groupClasses : groupClassList) {
			    session.saveOrUpdate(groupClasses);
			}
			updated=true;
			transaction.commit();
			}
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
		}
		return updated;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#getStudentFromOtherGroupClass(java.util.List, java.util.List)
	 */
	@Override
	public List<Integer> getStudentFromOtherGroupClass(
			List<Integer> classIdList, List<Integer> studentIdList) throws Exception {
		Session session = null;
		List<Integer> otherGroupStudentIdList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select classGroup.student.id from StudentClassGroup classGroup where classGroup.isActive=1 and classGroup.groupClasses.classId.id in(:classIds) and classGroup.student.id  not in(:studentIds)" ;
			Query query =  session.createQuery(hqlQuery);
			query.setParameterList("classIds", classIdList);
			query.setParameterList("studentIds", studentIdList);
			otherGroupStudentIdList = query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		return otherGroupStudentIdList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction#deleteGroupClassStudent(java.util.List)
	 */
	@Override
	public boolean deleteGroupClassStudent(List<GroupClasses> groupClassList)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean deleted=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(!groupClassList.isEmpty()){
			for (GroupClasses groupClasses : groupClassList) {
			    session.merge(groupClasses);
			}
			deleted=true;
			transaction.commit();
			}
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return deleted;
	}
	
}
