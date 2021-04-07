package com.kp.cms.transactionsimpl.attendance;

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

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.CopyClassTeacherForm;
import com.kp.cms.transactions.attandance.ICopyClassTeacherTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CopyClassTeacherTransactionImpl implements ICopyClassTeacherTransaction{
	private static Log log = LogFactory.getLog(CopyClassTeacherTransactionImpl.class);

	private static volatile CopyClassTeacherTransactionImpl copyClassTeacherTransactionImpl = null;
	/**
	 * @return
	 */
	public static CopyClassTeacherTransactionImpl getInstance(){
		if(copyClassTeacherTransactionImpl == null){
			copyClassTeacherTransactionImpl = new CopyClassTeacherTransactionImpl();
			return copyClassTeacherTransactionImpl;
		}
		return copyClassTeacherTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.ICopyClassTeacherTransaction#getTeachers(com.kp.cms.forms.attendance.CopyClassTeacherForm)
	 */
	public List<TeacherClass> getTeachers(CopyClassTeacherForm copyClassTeacherForm) throws Exception {
		log.debug("inside getTeachers");
		Session session=null;
		List<TeacherClass> teacherClass;
		List semTypeEven =new ArrayList();
		List semTypeOdd =new ArrayList();
			try{
			session=HibernateUtil.getSession();
			List<Integer>	classes=session.createQuery("select c.termNumber from Classes c where c.isActive=1 group by termNumber").list();
			 for( int i=0; i<classes.size(); i++)
	          {         
	                   int s = classes.get(i);
	                   if(s % 2==0){
	                	   semTypeEven.add(s);
	                   }else
	                	   semTypeOdd.add(s);
	          }
			String sql="from TeacherClass teacherClass "+
						"where teacherClass.isActive=1 and teacherClass.classId in "+
						"(select c.id from ClassSchemewise c ";
			if(copyClassTeacherForm.getSemType().equalsIgnoreCase("EVEN")){
				sql = sql + " inner join   c.classes cls "+
				 " where c.curriculumSchemeDuration.academicYear=" +copyClassTeacherForm.getFromAcademicYear()+" and cls.termNumber in(:semTypeEven))";
			}else{
				sql = sql + " inner join  c.classes cls"+
				" where c.curriculumSchemeDuration.academicYear=" +copyClassTeacherForm.getFromAcademicYear()+" and cls.termNumber in(:semTypeOdd))";
			}
			Query query1=session.createQuery(sql);
			if(copyClassTeacherForm.getSemType().equalsIgnoreCase("EVEN")){
			query1.setParameterList("semTypeEven", semTypeEven);
			}else{
			query1.setParameterList("semTypeOdd", semTypeOdd);
			}
			teacherClass= query1.list();
			session.flush();
			log.debug("leaving getTeachers");
			}catch (Exception e) {
				log.error("Error in getDetails...", e);
				session.flush();
				throw new ApplicationException(e);
			}finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
			return teacherClass;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.ICopyClassTeacherTransaction#copyTeachers(java.util.List)
	 */
	public boolean copyTeachers(List<TeacherClass> teacherList) throws Exception {

		Session session=null;
		Transaction transaction = null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(teacherList !=null && !teacherList.isEmpty()){
			Iterator<TeacherClass> iterator = teacherList.iterator();
			while (iterator.hasNext()) {
				TeacherClass teacherClas = (TeacherClass) iterator.next();
				Query query = session.createQuery("from TeacherClass e where e.classId = :classId and  e.teacherId=:TeacherId and e.isActive=1");
				if( teacherClas.getClassId()!=null){
				query.setInteger("classId", (teacherClas.getClassId().getId()));
				query.setInteger("TeacherId", (teacherClas.getTeacherId().getId()));
				}
				TeacherClass teacherClassBo =(TeacherClass)query.uniqueResult();
				if(teacherClassBo==null){
					session.save(teacherClas);
					flag=true;
				}
				}
			}
			transaction.commit();
			return flag;
		}catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		finally {
			if (session != null) {
				session.flush();
				session.close();
				}
			}
		}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.ICopyClassTeacherTransaction#getToYearClasses(com.kp.cms.forms.attendance.CopyClassTeacherForm)
	 */
	public Map<String, Integer> getToYearClasses(CopyClassTeacherForm copyClassTeacherForm) throws Exception {
		log.debug("Txn Impl : Entering getToYearClasses ");
		Session session = null;
		try {
			Map<String, Integer> map=new HashMap();
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from ClassSchemewise c where c.curriculumSchemeDuration.academicYear="+copyClassTeacherForm.getToAcademicYear()); 
			 List<ClassSchemewise> list = query.list();
			 Iterator<ClassSchemewise> iterator = list.iterator();
				while (iterator.hasNext()) {
					ClassSchemewise classSchemewise = (ClassSchemewise) iterator.next();
					map.put(classSchemewise.getClasses().getName(), classSchemewise.getId());
				}
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Txn Impl : Leaving getToYearClasses ");
			 return map;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getToYearClasses with Exception");
			 //session.close();
			 throw e;
		 }
	}
	}


