package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admission.DemandSlipInstruction;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IFeeDemandSlipInstructionTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

	public class FeeDemandSlipInstructionTxnImpl implements IFeeDemandSlipInstructionTransaction{
		private static final Log log = LogFactory.getLog(FeeDemandSlipInstructionTxnImpl.class);
		private static volatile FeeDemandSlipInstructionTxnImpl feTxnImpl = null;
	
		public static FeeDemandSlipInstructionTxnImpl getInstance() {
			if (feTxnImpl == null) {
				feTxnImpl = new FeeDemandSlipInstructionTxnImpl();
			}
			return feTxnImpl;
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IFeeDemandSlipInstructionTransaction#checkDuplicate(int, int)
		 */
		public DemandSlipInstruction checkDuplicate(int courseId, int schemeNo) throws Exception{
			Session session = null;
			try{
				session=HibernateUtil.getSession();
				String q="from DemandSlipInstruction d";
				String q1="";
				if(courseId>0){
					q1=q1 + "d.course.id='"+courseId+"'";
				}else{
					q1=q1 + "d.course is null";
				}
				if(schemeNo>0){
					if(!q1.isEmpty())
						q1=q1+" and ";
					
					q1=q1+" d.schemeNo='"+schemeNo+"'";
				}else{
					if(!q1.isEmpty())
						q1=q1+" and ";
					
					q1=q1+" d.schemeNo is 0";
				}
				if(!q1.isEmpty()){
					q=q+" where "+q1;
				}
				DemandSlipInstruction deSlipInstruction = (DemandSlipInstruction) session.createQuery(q).uniqueResult();
				return deSlipInstruction;
			}catch (Exception e) {
				throw new ApplicationException(e); 
			}finally{
				if(session !=null){
					session.flush();
				}
			}
		}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IFeeDemandSlipInstructionTransaction#addSlipInstruction(com.kp.cms.bo.admission.DemandSlipInstruction)
		 */
		public boolean addSlipInstruction(DemandSlipInstruction deSlipInstructionBO) throws Exception{
			Session session = null;
			Transaction transaction = null;
			try{
				session=InitSessionFactory.getInstance().openSession();
				transaction=session.beginTransaction();
				if(deSlipInstructionBO!=null)
				session.saveOrUpdate(deSlipInstructionBO);
				transaction.commit();
				session.flush();
				return true;
			}catch (Exception e) {
				if(transaction!=null){
					transaction.rollback();
				}
				throw new ApplicationException(e);
			}finally{
				if(session!=null){
					session.flush();
					session.close();
				}
			}
	}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IFeeDemandSlipInstructionTransaction#getDetailsToEdit(int)
		 */
		public DemandSlipInstruction getDetailsToEdit(int id) throws Exception{
			Session session = null;
			try{
				session=InitSessionFactory.getInstance().openSession();
				DemandSlipInstruction deSlipInstruction = (DemandSlipInstruction) session.createQuery("from DemandSlipInstruction d where d.id="+id).uniqueResult();
				return deSlipInstruction;
			}catch (Exception e) {
				throw new ApplicationException(e); 
			}finally{
				if(session !=null){
					session.flush();
				}
			}
			
		}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IFeeDemandSlipInstructionTransaction#getDetailsToDisplay()
		 */
		public List<DemandSlipInstruction> getDetailsToDisplay() throws Exception{
			Session session = null;
			try{
				session=InitSessionFactory.getInstance().openSession();
				List<DemandSlipInstruction> deSlipInstruction =session.createQuery("from DemandSlipInstruction d where d.isActive=1").list();
				return deSlipInstruction;
			}catch (Exception e) {
				throw new ApplicationException(e); 
			}finally{
				if(session !=null){
					session.flush();
				}
			}
			
		}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IFeeDemandSlipInstructionTransaction#deleteSlipInstruction(int, java.lang.Boolean, java.lang.String)
		 */
		public boolean deleteSlipInstruction(int id,Boolean activate,String userId) throws Exception{
			log.info("inside the deleteSlipInstruction of FeeDemandSlipInstructionTxnImpl");
			Session session = null;
			Transaction transaction = null;
			try {
				session=InitSessionFactory.getInstance().openSession();
				transaction = session.beginTransaction();
				DemandSlipInstruction deSlipInstruction = (DemandSlipInstruction) session
						.get(DemandSlipInstruction.class, id);
				deSlipInstruction.setIsActive(activate);
				deSlipInstruction.setModifiedBy(userId);
				deSlipInstruction.setLastModifiedDate(new Date());
				session.update(deSlipInstruction);
				transaction.commit();
				return true;
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				log.error("error occured deleteSlipInstruction of FeeDemandSlipInstructionTxnImpl"+ e);
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
					
		}
}
