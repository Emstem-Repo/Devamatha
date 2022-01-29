package com.kp.cms.transactionsimpl.admission;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.ICopyClassesTransaction;
import com.kp.cms.utilities.InitSessionFactory;

	public class CopyClassesTransactionImpl implements ICopyClassesTransaction{
		private static final Log log = LogFactory.getLog(CopyClassesTransactionImpl.class);
		private static volatile CopyClassesTransactionImpl copyClassesTransactionImpl = null;
	
		public static CopyClassesTransactionImpl getInstance() {
			if (copyClassesTransactionImpl == null) {
				copyClassesTransactionImpl = new CopyClassesTransactionImpl();
			}
			return copyClassesTransactionImpl;
		}
		
		public List<Classes> getClassesByYear(int year) throws Exception{
			Session session = null;
			try{
				session = InitSessionFactory.getInstance().openSession();
				List<Classes> classesBO = session.createQuery("select c from Classes c join c.classSchemewises cs where c.isActive=1 and cs.curriculumSchemeDuration.academicYear="+year).list();
				return classesBO;
				}catch (Exception e) {
					throw new ApplicationException(e);
				}finally{
					if(session!=null){
						session.flush();
				}
			}
		}
		
		public Map<String,Integer> getClassesByToYear(int toYear) throws Exception{
			Session session = null;
			try{
				session = InitSessionFactory.getInstance().openSession();
				List<Object[]> list = session.createQuery("select schemeDuration.id,schemeDuration.semesterYearNo,schemeDuration.curriculumScheme.course.name from CurriculumSchemeDuration schemeDuration where schemeDuration.academicYear="+toYear+" order by schemeDuration.semesterYearNo").list();
				Map<String,Integer> schemeMap = new HashMap<String,Integer>();
				if (list.size()!=0 ) {
					Iterator<Object[]> itr = list.iterator();
					while (itr.hasNext()) {
						Object[] obj = itr.next();
						if (obj[0] != null) {
							
							schemeMap.put(obj[2].toString()+"_"+obj[1].toString(),Integer.parseInt(obj[0].toString()));
						}
					}
				}
				return schemeMap;
				}catch (Exception e) {
					throw new ApplicationException(e);
				}finally{
					if(session!=null){
						session.flush();
					}
			}
			
		}
		
		public boolean saveClasses(List<Classes> classesBO) throws Exception{
			Session session = null;
			Transaction transaction = null;
			try{
				session = InitSessionFactory.getInstance().openSession();
				transaction=session.beginTransaction();
				Iterator<Classes> classesList = classesBO.iterator();
				Classes classes;
				while (classesList.hasNext()) {
					classes = (Classes) classesList.next();
					session.save(classes);
				}
				transaction.commit();
				session.flush();
				return true;
			}catch (Exception e) {
				if(transaction!=null)
				{
					transaction.rollback();
				}
				throw new ApplicationException(e);
				}finally{
					if(session!=null){
						session.flush();
				}
			}
		}

		public boolean checkDuplicate(String query) throws Exception{
			Session session = null;
			try{
				session = InitSessionFactory.getInstance().openSession();
				List<ClassSchemewise> classesBO = session.createQuery(query).list();
				if(classesBO!=null && !classesBO.isEmpty()){
					return true;
				}else
					return false;
				
				}catch (Exception e) {
					throw new ApplicationException(e);
				}finally{
					if(session!=null){
					session.flush();
				}
			}
		}
}
