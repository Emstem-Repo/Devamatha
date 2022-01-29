package com.kp.cms.transactionsimpl.smartcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.forms.smartcard.ScLostCorrectionViewForm;
import com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction;
import com.kp.cms.utilities.HibernateUtil;
import common.Logger;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionViewTransactionImpl implements IScLostCorrectionViewTransaction {
	
	private static final Logger log = Logger.getLogger(ScLostCorrectionViewTransactionImpl.class);

	public static volatile ScLostCorrectionViewTransactionImpl scLostCorrectionViewTransactionImpl;

	public static ScLostCorrectionViewTransactionImpl getInstance() {
		if (scLostCorrectionViewTransactionImpl == null) {
			scLostCorrectionViewTransactionImpl = new ScLostCorrectionViewTransactionImpl();
		}
		return scLostCorrectionViewTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction#getDetailsList(java.lang.String)
	 */
	public List<ScLostCorrection> getDetailsList(String searchCriteria)
	throws Exception{
		
		Session session = null;
		List<ScLostCorrection> scSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			scSearchResult = session.createQuery(searchCriteria).list();

		} catch (Exception e) {

			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		return scSearchResult;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction#getAllStudentList(java.util.List)
	 */
	public List<ScLostCorrection> getAllSelectedList(List<Integer> idList) throws Exception {
		Session session = null;
		List<ScLostCorrection> list = null;

		try {
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from ScLostCorrection sc where sc.isActive=1 " +
					"and sc.isTextFileRequired=1 and sc.id in (:studentIdList)");
//			query.setParameter("stat", stat);
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
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction#setStatus(com.kp.cms.forms.smartcard.ScLostCorrectionViewForm)
	 */
	public boolean setStatus(ScLostCorrectionViewForm scForm) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			ScLostCorrection bo = (ScLostCorrection) session.get(ScLostCorrection.class, scForm.getId());
			if(scForm.getStatus2().equalsIgnoreCase("Approve")){
				bo.setStatus("Approved");
				bo.setReasonForRejection(null);
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
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction#getDetailsListAfter(com.kp.cms.forms.smartcard.ScLostCorrectionViewForm)
	 */
	public List<ScLostCorrection> getDetailsListAfter(ScLostCorrectionViewForm scForm)
	throws Exception{
		
		Session session = null;
		List<ScLostCorrection> scSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
				scSearchResult = session.createQuery("from ScLostCorrection sc where sc.isActive=1 and sc.status='"+scForm.getStatus1()+"'"+" and sc.isEmployee=0").list();
			}
			else{
				scSearchResult = session.createQuery("from ScLostCorrection sc where sc.isActive=1 and sc.status='"+scForm.getStatus1()+"'"+" and sc.isEmployee=1").list();
			}

		} catch (Exception e) {

			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		return scSearchResult;
	}
	
	/* (non-Javadoc)
	 * getting the ids without photos and regNos to display in the form
	 * 
	 */
	public List<Integer> getStudentsWithoutPhotosAndRegNos(ScLostCorrectionViewForm scForm) throws Exception {
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
						" where s.isAdmitted=1 " +
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
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction#getStudentPhotos(java.util.List)
	 */
	@Override
	public Map<String, byte[]> getStudentPhotos(List<Integer> studentIds)
	throws Exception {
		Session session = null;
		Map<String, byte[]> studentPhotos=new HashMap<String, byte[]>();
		List list = null;
		try {
			if(studentIds!=null && !studentIds.isEmpty()){
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery("select s.registerNo, docs.document from Student s" +
				" join s.admAppln.applnDocs docs where (s.isHide=0 or s.isHide is null) and s.admAppln.isCancelled=0" +
				" and docs.isPhoto=1 and docs.document is not null and docs.document!='' and s.id in (:studentList)" +
				" and s.registerNo is not null and s.registerNo!=''").setParameterList("studentList",studentIds);
				list = selectedCandidatesQuery.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
						if(objects[0]!=null && objects[1]!=null)
							studentPhotos.put(objects[0].toString(),(byte[])objects[1]);
					}
				}
			}
			return studentPhotos;
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
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction#getEmployeePhotos(java.util.List)
	 */
	@Override
	public Map<String, byte[]> getEmployeePhotos(List<Integer> employeeIds) throws Exception {
		Session session = null;
		Map<String, byte[]> employeePhotos=new HashMap<String, byte[]>();
		List list = null;
		try {
			if(employeeIds!=null && !employeeIds.isEmpty()){
				session = HibernateUtil.getSession();
				
				Query selectedEmployeesQuery=session.createQuery("select e.employee.fingerPrintId,e.empPhoto from EmpImages e where (e.empPhoto <> null or trim(e.empPhoto) <> '')" +
						" and e.employee.isActive=1 and e.employee.active=1 and e.employee.fingerPrintId  is not null and e.employee.fingerPrintId!='' and e.employee.id in (:employeeList)" +
						" group by e.employee.id order by e.employee.fingerPrintId ").setParameterList("employeeList",employeeIds);
				list = selectedEmployeesQuery.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
						if(objects[0]!=null && objects[1]!=null)
							employeePhotos.put(objects[0].toString(),(byte[])objects[1]);
					}
				}
			}
			return employeePhotos;
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
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction#getEmployeesWithoutPhotosAndRegNos(com.kp.cms.forms.smartcard.ScLostCorrectionViewForm)
	 */
	public List<Integer> getEmployeesWithoutPhotosAndRegNos(ScLostCorrectionViewForm scForm) throws Exception {
		
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
