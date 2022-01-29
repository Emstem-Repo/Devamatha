package com.kp.cms.transactionsimpl.smartcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessForm;
import com.kp.cms.forms.smartcard.ScLostCorrectionViewForm;
import com.kp.cms.transactions.smartcard.IScLostCorrectionProcessTransaction;
import com.kp.cms.utilities.HibernateUtil;
import common.Logger;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionProcessTransactionImpl implements IScLostCorrectionProcessTransaction {
	
	private static final Logger log = Logger.getLogger(ScLostCorrectionProcessTransactionImpl.class);
	public static volatile ScLostCorrectionProcessTransactionImpl scLostCorrectionProcessTransactionImpl;

	public static ScLostCorrectionProcessTransactionImpl getInstance() {
		if (scLostCorrectionProcessTransactionImpl == null) {
			scLostCorrectionProcessTransactionImpl = new ScLostCorrectionProcessTransactionImpl();
		}
		return scLostCorrectionProcessTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionProcessTransaction#getDetailsList(com.kp.cms.forms.smartcard.ScLostCorrectionProcessForm)
	 */
	public List<ScLostCorrection> getDetailsList(ScLostCorrectionProcessForm scProcessForm)
	throws Exception{
		
		Session session = null;
		//List<ScLostCorrection> scSearchResult = null;
		Query query = null;
		
		try {
			session = HibernateUtil.getSession();
			
			if(scProcessForm.getIsEmployee().equalsIgnoreCase("Student")){
				query = session.createQuery("from ScLostCorrection sc where sc.isActive=1 " +
						"and sc.status='"+scProcessForm.getStatus1()+"' and sc.isEmployee=0 order by sc.dateOfSubmission");
			}
			else{
				query = session.createQuery("from ScLostCorrection sc where sc.isActive=1 " +
						"and sc.status='"+scProcessForm.getStatus1()+"' and sc.isEmployee=1 order by sc.dateOfSubmission");
			}
			List<ScLostCorrection> scSearchResult = query.list();
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
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionProcessTransaction#setStatus(com.kp.cms.forms.smartcard.ScLostCorrectionProcessForm)
	 */
	public boolean setStatus(ScLostCorrectionProcessForm scForm) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			ScLostCorrection bo = (ScLostCorrection) session.get(ScLostCorrection.class, scForm.getId());
			if(scForm.getStatus2().equalsIgnoreCase("Process")){
				bo.setStatus("Processed");
				if(scForm.getNewSmartCardNum()!=null || !scForm.getNewSmartCardNum().isEmpty())
					bo.setNewSmartCardNum(scForm.getNewSmartCardNum());
			}
			else if(scForm.getStatus2().equalsIgnoreCase("Reject")){
				bo.setStatus("Rejected");
				bo.setReasonForRejection(scForm.getReasonForRejection());
			}
		
			bo.setLastModifiedDate(new Date());
			bo.setModifiedBy(scForm.getUserId());
			session.update(bo);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * getting the ids without photos and regNos to display in the form
	 * 
	 */
	@Override
	public List<Integer> getStudentsWithoutPhotosAndRegNos(ScLostCorrectionProcessForm scForm) throws Exception {
		Session session = null;
		List<String> stuRegNos=new ArrayList<String>();
		List<String> applnNos=new ArrayList<String>();
		List list=null;
		List<Integer> studIds=scForm.getStudentIds();
		List<Integer> stuIdsWithoutPhotosOrRegNos = new ArrayList<Integer>();
		try {
				session = HibernateUtil.getSession();
				Query query=session.createQuery("select s.registerNo,s.admAppln.applnNo,s.id from Student s" +
						" left join s.admAppln.applnDocs docs " +
						" where s.isAdmitted=1 and s.isSCDataGenerated=0 " +
						" and s.admAppln.isCancelled=0 and (s.isHide=0 or s.isHide is null) and docs.isPhoto=1 and " +
						" ((docs.document is null or docs.document='') or (s.registerNo is null or s.registerNo='')) and s.id in (:studentIds) group by s.id order by s.registerNo" ).setParameterList("studentIds", studIds);
				list=query.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object> itr=list.iterator();
					while(itr.hasNext()){
					Object[] obj=(Object[])itr.next();
					if(obj[0]!=null && !obj[0].toString().trim().isEmpty()){
						stuRegNos.add(obj[0].toString());
					}
					else if(obj[1]!=null){
						applnNos.add(obj[1].toString());
					}
					if(obj[2]!=null){
						stuIdsWithoutPhotosOrRegNos.add((Integer)obj[2]);
					}
				}
					scForm.setStudentsWithoutPhotos(stuRegNos);
					scForm.setStudentsWithoutRegNos(applnNos);
			}
				return stuIdsWithoutPhotosOrRegNos;	
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionProcessTransaction#getAllStudentList(java.util.List)
	 */
	public List<ScLostCorrection> getAllStudentList(List<Integer> idList) throws Exception {
		Session session = null;
		List<ScLostCorrection> list = null;

		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ScLostCorrection sc where sc.isActive=1 " +
					"and sc.isTextFileRequired=1 and sc.id in (:studentIdList)");
			query.setParameterList("studentIdList", idList);
			list = query.list();
			return list;
			} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionProcessTransaction#getEmployeesWithoutPhotosAndRegNos(com.kp.cms.forms.smartcard.ScLostCorrectionProcessForm)
	 */
	public List<Integer> getEmployeesWithoutPhotosAndRegNos(ScLostCorrectionProcessForm scForm) throws Exception {
		
		Session session = null;
		List<String> empFingerPrintIds=new ArrayList<String>();
		List<String> firstNames=new ArrayList<String>();
		List list=null;
		List<Integer> empIds=scForm.getEmployeeIds();
		List<Integer> empIdsWithoutPhotosOrRegNos = new ArrayList<Integer>();
		try {
				session = HibernateUtil.getSession();
				Query query=session.createQuery("select e.fingerPrintId,e.firstName,e.id from Employee e join e.empImages i where e.active=1  and e.isActive=1 " +
						" and ((i.empPhoto is null or i.empPhoto='') or (e.fingerPrintId is null or e.fingerPrintId='')) and e.id in (:employeeIds) group by e.id").setParameterList("employeeIds", empIds);
				list=query.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object> itr=list.iterator();
					while(itr.hasNext()){
					Object[] obj=(Object[])itr.next();
					if(obj[0]!=null && !obj[0].toString().trim().isEmpty()){
						empFingerPrintIds.add(obj[0].toString());
					}
					else if(obj[1]!=null && !obj[1].toString().trim().isEmpty()){
						firstNames.add(obj[1].toString());
					}
					if(obj[2]!=null){
						empIdsWithoutPhotosOrRegNos.add((Integer)obj[2]);
					}
				}
					scForm.setEmployeesWithoutPhotos(empFingerPrintIds);
					scForm.setEmployeesWithoutFingerPrintIds(firstNames);
				
			}
				return empIdsWithoutPhotosOrRegNos;	
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	

}
