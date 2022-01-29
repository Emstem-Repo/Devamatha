package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IUploadUniversityEmailTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadUniversityEmailTransactionimpl implements
		IUploadUniversityEmailTransaction {

	/**
	 * Singleton object of UploadUniversityEmailTransactionimpl
	 */
	private static volatile UploadUniversityEmailTransactionimpl uploadUniversityEmailTransactionimpl = null;
	private static final Log log = LogFactory.getLog(UploadUniversityEmailTransactionimpl.class);
	private UploadUniversityEmailTransactionimpl() {
		
	}
	/**
	 * return singleton object of UploadUniversityEmailTransactionimpl.
	 * @return
	 */
	public static UploadUniversityEmailTransactionimpl getInstance() {
		if (uploadUniversityEmailTransactionimpl == null) {
			uploadUniversityEmailTransactionimpl = new UploadUniversityEmailTransactionimpl();
		}
		return uploadUniversityEmailTransactionimpl;
	}
	/* (non-Javadoc)
	 * Method to get personalDataBO  from database
	 * @see com.kp.cms.transactions.admission.IUploadUniversityEmailTransaction#getMapOfPersonalDetails()
	 */
	@Override
	public PersonalData getPersonalDetails(String registerNo) throws Exception {

		Session session = null;
		PersonalData personalData = null;
		try {
			session = HibernateUtil.getSession();
			String query= "from Student s where s.isAdmitted=1 and s.isActive=1 and s.admAppln.isCancelled=0" +
					" and (s.isHide=0 or s.isHide is null) and s.registerNo="+registerNo;
			
			Student stu = (Student)session.createQuery(query).uniqueResult();
			if(stu!=null){
				personalData=stu.getAdmAppln().getPersonalData();
			}
			return personalData;
		} catch (Exception e) {
			log.error("Error while retrieving studentsMap.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * saving the passed BO list to database
	 * @see com.kp.cms.transactions.admission.IUploadUniversityEmailTransaction#uploadUniversityEmail(java.util.List)
	 */
	@Override
	public boolean uploadUniversityEmail(List<PersonalData> personalDataBOList,String user)
			throws Exception {
		Session session = null;
		boolean isAdded=false;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			if(personalDataBOList!=null && !personalDataBOList.isEmpty()){
				Iterator<PersonalData> itr=personalDataBOList.iterator();
				while (itr.hasNext()) {
					PersonalData personalData = (PersonalData) itr.next();
					PersonalData bo=(PersonalData)session.get(PersonalData.class,personalData.getId());
					if(bo!=null){
						bo.setLastModifiedDate(new Date());
						bo.setModifiedBy(user);
						bo.setUniversityEmail(personalData.getUniversityEmail());
						session.update(bo);
						isAdded=true;
					}
				}
			}
			txn.commit();
			return isAdded;
		} catch (Exception e) {
			if(txn!=null){
				isAdded=false;
				txn.rollback();
			}
			log.error("Error while updating personalData BO.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IUploadUniversityEmailTransaction#getAllStudents()
	 */
	@Override
	public Map<String, Integer> getAllStudents() throws Exception {
		Map<String, Integer> map=new HashMap<String, Integer>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query= "select s.registerNo,s.admAppln.personalData.id from Student s where s.isAdmitted=1 and s.isActive=1 and s.admAppln.isCancelled=0" +
					" and (s.isHide=0 or s.isHide is null)";
			List<Object[]> list=session.createQuery(query).list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if(obj[0]!=null && obj[1]!=null)
						map.put(obj[0].toString(),Integer.parseInt(obj[1].toString()));
				}
			}
			return map;
		} catch (Exception e) {
			log.error("Error while retrieving studentsMap.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
}
