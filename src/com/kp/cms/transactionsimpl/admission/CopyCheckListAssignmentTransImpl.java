package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.ICopyCheckListAssignmentTransaction;
import com.kp.cms.utilities.HibernateUtil;

	public class CopyCheckListAssignmentTransImpl implements ICopyCheckListAssignmentTransaction{
		private static final Log log = LogFactory.getLog(CopyCheckListAssignmentTransImpl.class);
		private static volatile CopyCheckListAssignmentTransImpl copyCheckListTransImpl = null;
	
		public static CopyCheckListAssignmentTransImpl getInstance() {
			if (copyCheckListTransImpl == null) {
				copyCheckListTransImpl = new CopyCheckListAssignmentTransImpl();
			}
			return copyCheckListTransImpl;
		}
		
		public List<DocChecklist> getCheckListByYear(int fromYear) throws Exception{
			Session session=null;
			List<DocChecklist> docCheckList; 
			try {
				//session =InitSessionFactory.getInstance().openSession();
				session = HibernateUtil.getSession();
				docCheckList= session.createQuery("from DocChecklist d where d.isActive = :isactive and d.year="+fromYear+" and  d.docType.isActive = 1").setBoolean("isactive",true).list();
				//session.flush();
			} catch (Exception e) {
				log.error("Error while getting checkList..."+e);
				throw  new ApplicationException(e);
			}finally{
				if (session != null){
					session.flush();
					//session.close();
				}
			}
			return docCheckList;
		}
		

		public boolean copyCheckList(List<DocChecklist> docList) throws Exception{
			Session session = null;
			Transaction transaction = null;
			try{
				session = HibernateUtil.getSession();
				transaction = session.beginTransaction();
				Iterator<DocChecklist> checkList = docList.iterator();
				while (checkList.hasNext()) {
					DocChecklist check = (DocChecklist) checkList.next();
					session.save(check);
				}
				transaction.commit();
				session.flush();
			}catch (Exception e) {
				if(transaction!=null){
					transaction.rollback();
					throw new ApplicationException(e);
				}
			}finally{
				if(session!=null){
					session.flush();
				}
			}
			return true;
			
		}

		public boolean checkDuplicate(String query) throws Exception{
			Session session = null;
			List<DocChecklist> docCheckList=null;
			try{
				session = HibernateUtil.getSession();
				docCheckList=session.createQuery(query).list();
				if(docCheckList!=null && !docCheckList.isEmpty()){
					return true;
				}
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
