package com.kp.cms.transactionsimpl.smartcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessedForm;
import com.kp.cms.to.smartcard.ScLostCorrectionProcessedTO;
import com.kp.cms.transactions.smartcard.IScLostCorrectionProcessedTransaction;
import com.kp.cms.utilities.HibernateUtil;
import common.Logger;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionProcessedTransactionImpl implements IScLostCorrectionProcessedTransaction {
	
	private static final Logger log = Logger.getLogger(ScLostCorrectionProcessedTransactionImpl.class);
	public static volatile ScLostCorrectionProcessedTransactionImpl scLostCorrectionProcessedTransactionImpl;

	public static ScLostCorrectionProcessedTransactionImpl getInstance() {
		if (scLostCorrectionProcessedTransactionImpl == null) {
			scLostCorrectionProcessedTransactionImpl = new ScLostCorrectionProcessedTransactionImpl();
		}
		return scLostCorrectionProcessedTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionProcessedTransaction#getDetailsList(com.kp.cms.forms.smartcard.ScLostCorrectionProcessedForm)
	 */
	public List<ScLostCorrection> getDetailsList(ScLostCorrectionProcessedForm scProcessedForm)
	throws Exception{
		
		Session session = null;
		List<ScLostCorrection> scSearchResult = null;
		Query query = null;
		try {
			session = HibernateUtil.getSession();
			if(scProcessedForm.getIsEmployee().equalsIgnoreCase("Student")){
				query = session.createQuery("from ScLostCorrection sc where sc.isActive=1 " +
						"and sc.status='"+scProcessedForm.getStatus()+"' and sc.isEmployee=0 order by sc.dateOfSubmission");
			}
			else{
				query = session.createQuery("from ScLostCorrection sc where sc.isActive=1 " +
						"and sc.status='"+scProcessedForm.getStatus()+"' and sc.isEmployee=1 order by sc.dateOfSubmission");
			}
			scSearchResult = query.list();
			return scSearchResult;

		} catch (Exception e) {

			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionProcessedTransaction#addScLostCorrectionProcessed(com.kp.cms.forms.smartcard.ScLostCorrectionProcessedForm)
	 */
	public boolean addScLostCorrectionProcessed(ScLostCorrectionProcessedForm scProcessedForm) throws Exception {
		Session session = null;
		boolean flagSet=false;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			if(scProcessedForm.getScList()!=null && !scProcessedForm.getScList().isEmpty()){
				
				if(scProcessedForm.getStatus().equalsIgnoreCase("Processed")){
				Iterator<ScLostCorrectionProcessedTO> itr=scProcessedForm.getScList().iterator();
				while (itr.hasNext()) {
					ScLostCorrectionProcessedTO scProcessedTO = (ScLostCorrectionProcessedTO) itr.next();
					
					if(scProcessedTO.getChecked()!=null && scProcessedTO.getChecked().equalsIgnoreCase("On")){
						
						ScLostCorrection scDetails = (ScLostCorrection) session.get(ScLostCorrection.class, scProcessedTO.getId());
						/*Query query=session.createQuery("from ScLostCorrection sc where sc.id="+scProcessedTO.getId());
						ScLostCorrection scDetails=(ScLostCorrection)query.uniqueResult();*/
						if(scDetails!=null && !scDetails.toString().isEmpty()){
							scDetails.setStatus("Received");
							scDetails.setLastModifiedDate(new Date());
							scDetails.setModifiedBy(scProcessedForm.getUserId());
							session.merge(scDetails);
						}
					}
				}
			}
				else if(scProcessedForm.getStatus().equalsIgnoreCase("Received")){
					Iterator<ScLostCorrectionProcessedTO> itr=scProcessedForm.getScList().iterator();
					while (itr.hasNext()) {
						ScLostCorrectionProcessedTO scProcessedTO = (ScLostCorrectionProcessedTO) itr.next();
						
						if(scProcessedTO.getChecked()!=null && scProcessedTO.getChecked().equalsIgnoreCase("On")){
							
							ScLostCorrection scDetails = (ScLostCorrection) session.get(ScLostCorrection.class, scProcessedTO.getId());
							/*Query query=session.createQuery("from ScLostCorrection sc where sc.id="+scProcessedTO.getId());
							ScLostCorrection scDetails=(ScLostCorrection)query.uniqueResult();*/
							if(scDetails!=null && !scDetails.toString().isEmpty()){
								scDetails.setStatus("Issued");
								scDetails.setLastModifiedDate(new Date());
								scDetails.setModifiedBy(scProcessedForm.getUserId());
								session.merge(scDetails);
								//update Student table if new smart number is given
								if(scDetails.getNewSmartCardNum()!=null && !scDetails.getNewSmartCardNum().isEmpty()){
									if(scProcessedForm.getIsEmployee().equalsIgnoreCase("Student")){
										Student stu = scDetails.getStudentId();
										stu.setSmartCardNo(scDetails.getNewSmartCardNum());
										session.update(stu);
									}
									else{
										Employee emp = scDetails.getEmployeeId();
										emp.setSmartCardNo(scDetails.getNewSmartCardNum());
										session.update(emp);
									}
								}
							}
						}
					}
				}
				else if(scProcessedForm.getStatus().equalsIgnoreCase("Rejected")){
					Iterator<ScLostCorrectionProcessedTO> itr=scProcessedForm.getScList().iterator();
					while (itr.hasNext()) {
						ScLostCorrectionProcessedTO scProcessedTO = (ScLostCorrectionProcessedTO) itr.next();
						
						if(scProcessedTO.getChecked()!=null && scProcessedTO.getChecked().equalsIgnoreCase("On")){
						
							ScLostCorrection scDetails = (ScLostCorrection) session.get(ScLostCorrection.class, scProcessedTO.getId());
							/*Query query=session.createQuery("from ScLostCorrection sc where sc.id="+scProcessedTO.getId());
							ScLostCorrection scDetails=(ScLostCorrection)query.uniqueResult();*/
							if(scDetails!=null && !scDetails.toString().isEmpty()){
								scDetails.setStatus("Applied");
								scDetails.setReasonForRejection(null);
								if(scProcessedTO.getReSendRemarks()!=null && !scProcessedTO.getReSendRemarks().isEmpty())
									scDetails.setRemarks(scProcessedTO.getReSendRemarks());
								scDetails.setLastModifiedDate(new Date());
								scDetails.setModifiedBy(scProcessedForm.getUserId());
								session.merge(scDetails);
							}
						}
					}
				}
			}
			flagSet=true;
			txn.commit();
			return flagSet;
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while updating selected candidates data.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
			
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionProcessedTransaction#editRemarks(com.kp.cms.forms.smartcard.ScLostCorrectionProcessedForm)
	 */
	public boolean editRemarks(ScLostCorrectionProcessedForm scProcessedForm) throws Exception {
		Session session = null;
		boolean flagSet=false;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			
			ScLostCorrection bo = (ScLostCorrection) session.get(ScLostCorrection.class, scProcessedForm.getId());
			/*Query query=session.createQuery("from ScLostCorrection sc where sc.id="+scProcessedForm.getId());
			ScLostCorrection scDetails=(ScLostCorrection)query.uniqueResult();*/
			if(bo!=null && !bo.toString().isEmpty()){
				bo.setRemarks(scProcessedForm.getEditedRemarks());
				bo.setLastModifiedDate(new Date());
				bo.setModifiedBy(scProcessedForm.getUserId());
				session.merge(bo);
			}
			
			flagSet=true;
			txn.commit();
			return flagSet;
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while updating selected candidates data.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
			
		}
	}
	
	

}
