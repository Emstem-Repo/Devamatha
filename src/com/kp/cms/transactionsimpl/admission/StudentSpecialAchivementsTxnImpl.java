package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.SpecialAchievement;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admission.IStudentSpecialAchivementsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

	public class StudentSpecialAchivementsTxnImpl implements IStudentSpecialAchivementsTransaction{
		private static final Log log = LogFactory.getLog(StudentSpecialAchivementsTxnImpl.class);
		public static volatile StudentSpecialAchivementsTxnImpl objHandler = null;

		public static StudentSpecialAchivementsTxnImpl getInstance() {
			if (objHandler == null) {
				objHandler = new StudentSpecialAchivementsTxnImpl();
				return objHandler;
			}
			return objHandler;
		}
		
		/* to save the achivements
		 * (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IStudentSpecialAchivementsTransaction#addAchivements(com.kp.cms.bo.admin.SpecialAchievement)
		 */
		public boolean addAchivements(SpecialAchievement achivementBO) throws Exception{
			
			Session session=null;
			Transaction transaction=null;
			try{
				session=HibernateUtil.getSession();
				transaction = session.beginTransaction();
				session.save(achivementBO);
				transaction.commit();
//				session.flush();
				return true;
			}
			catch (Exception e) {
				if(transaction!=null)
				{
					transaction.rollback();
				}
				throw new BusinessException(e);
			}
			finally{
				if(session!=null)
				{
					session.flush();
//					session.close();
				}
			}
		}
		
		/* to check valid regNo or not
		 * (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IStudentSpecialAchivementsTransaction#validRegNo(java.lang.String)
		 */
		public Student validRegNo(String regNo) throws Exception{
			Session session=null;
			Student stu=null;
			try{
				session=HibernateUtil.getSession();
				stu= (Student)session.createQuery("from Student s where s.registerNo='"+regNo+"'").uniqueResult();
					return stu;
			}catch (Exception e) {
				throw new BusinessException(e);
			}
			finally{
				if(session!=null)
				{
					session.flush();
//					session.close();
				}
			}
		}
		
		public List<SpecialAchievement> getSpecialAchivementList()throws Exception
		{
			List<SpecialAchievement> achievements;
			Session session=null;
			try
			{
				session=HibernateUtil.getSession();
				String query="from SpecialAchievement s where s.isActive=1 order by s.student.registerNo";
				achievements=session.createQuery(query).list();
			}
			catch (Exception e) {
				// TODO: handle exception
				throw new ApplicationException(e);
			}
			return achievements;
		}
		
		public boolean deleteStudentAchivements(Integer id)throws Exception
		{
			Session session=null;
			boolean result=false;
			Transaction transaction=null;
			try
			{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				SpecialAchievement achievement=(SpecialAchievement)session.get(SpecialAchievement.class, id);
				achievement.setIsActive(false);
				session.update(achievement);
				transaction.commit();
				result=true;
				
			}
			catch (Exception e) {
				// TODO: handle exception
				transaction.rollback();
				throw new ApplicationException(e);
			}
			return result;
		}
		
		public SpecialAchievement duplicateCheck(Student student, String termNumber)throws Exception
		{
			Session session=null;
			SpecialAchievement achievement=null;
			try
			{
				session=HibernateUtil.getSession();
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
					String query="from SpecialAchievement s where s.student.id="+student.getId()+" and s.termNumber="+termNumber;
					achievement=(SpecialAchievement)session.createQuery(query).uniqueResult();
				}
				
			}
			catch (Exception e) 
			{
				throw new ApplicationException(e);
			}
			return achievement;
		}
		
		public SpecialAchievement getSpecialAchivement(Integer id)throws Exception
		{
			Session session=null;
			Transaction transaction=null;
			SpecialAchievement achievement=null;
			try
			{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				achievement=(SpecialAchievement)session.get(SpecialAchievement.class, id);
			}
			catch (Exception e) {
				// TODO: handle exception
				transaction.rollback();
				throw new ApplicationException(e);
			}
			return achievement;
		}
		
		public boolean updateAchivements(SpecialAchievement achievement)throws Exception
		{
			Session session=null;
			Transaction transaction=null;
			try
			{
				session=HibernateUtil.getSession();
				transaction = session.beginTransaction();
				session.update(achievement);
				transaction.commit();
				return true;
			}
			catch (Exception e) 
			{
				if(transaction!=null)
				{
					transaction.rollback();
				}
				throw new BusinessException(e);
			}
			finally
			{
				if(session!=null)
				{
					session.flush();
				}
			}
		}
		
		public boolean reActivateAchivement(Integer id)throws Exception
		{
			Session session=null;
			boolean result=false;
			Transaction transaction=null;
			try
			{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				SpecialAchievement achievement=(SpecialAchievement)session.get(SpecialAchievement.class, id);
				achievement.setIsActive(true);
				session.update(achievement);
				transaction.commit();
				result=true;
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				transaction.rollback();
				throw new ApplicationException(e);
			}
			return result;
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IStudentSpecialAchivementsTransaction#getTermNumberMap()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Map<Integer, String> getTermNumberMap() throws Exception {
			Session session=null;
			Map<Integer, String> map = new HashMap<Integer, String>();
			try{
				session=HibernateUtil.getSession();
				List<Classes> classes = session.createQuery("from Classes c where c.isActive=1 group by c.termNumber order by c.termNumber").list();
				if(classes != null){
					Iterator<Classes> iterator = classes.iterator();
					while (iterator.hasNext()) {
						Classes classes2 = (Classes) iterator.next();
						map.put(classes2.getTermNumber(), String.valueOf(classes2.getTermNumber()));
					}
				}
				return map;
			}catch (Exception e) {
				throw new BusinessException(e);
			}
			finally{
				if(session!=null)
				{
					session.flush();
//					session.close();
				}
			}
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IStudentSpecialAchivementsTransaction#getCurrentTermNumber(java.lang.String)
		 */
		@Override
		public String getCurrentTermNumber(String regNo) throws Exception {
			Session session=null;
			String termNo = "";
			try{
				session=HibernateUtil.getSession();
				Student student = (Student)session.createQuery("from Student s where s.registerNo='"+regNo+"'").uniqueResult();
				if(student != null && student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber()!=null){
					termNo = String.valueOf(student.getClassSchemewise().getClasses().getTermNumber());
				}
				return termNo;
			}catch (Exception e) {
				throw new BusinessException(e);
			}
			finally{
				if(session!=null)
				{
					session.flush();
//					session.close();
				}
			}
		}
}
