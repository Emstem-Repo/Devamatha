package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.hostel.HostelReAdmissionForm;
import com.kp.cms.transactions.hostel.IHostelReAdmissionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HostelReAdmissionTxnImpl implements IHostelReAdmissionTransaction{

	private static final Log log = LogFactory.getLog(HostelReAdmissionTxnImpl.class);
	
    public static volatile HostelReAdmissionTxnImpl hostelReAdmissionTxnImpl = null;
    
    
    /**
     * @return
     */
    public static HostelReAdmissionTxnImpl getInstance() {
		if (hostelReAdmissionTxnImpl == null) {
			hostelReAdmissionTxnImpl = new HostelReAdmissionTxnImpl();
			return hostelReAdmissionTxnImpl;
		}
		return hostelReAdmissionTxnImpl;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelReAdmissionTransaction#getStudentDetailsByHostelIdAndYear(com.kp.cms.forms.hostel.HostelReAdmissionForm)
	 */
	@SuppressWarnings("unchecked")
	public List<HostelOnlineApplication> getStudentDetailsByHostelIdAndYear(HostelReAdmissionForm admissionForm) {
		Session session=null;
		List<HostelOnlineApplication> onlineApplicationList=null;
		try{
			session=HibernateUtil.getSession();
			String quer="";if(admissionForm.getHlAdmSelection()!=null && admissionForm.getHlAdmSelection().equalsIgnoreCase("NotSelected")){
				quer="from HostelOnlineApplication online where online.isActive=1" +
				" and online.academicYear='"+admissionForm.getAcademicYear()+"' and online.hlHostel.id='"+admissionForm.getHostelIdForReAdm()+"'" +
				" and (online.selectedRoomType is null or online.selectedRoomType='') order by online.student.registerNo ";
			}else if(admissionForm.getHlAdmSelection()!=null && admissionForm.getHlAdmSelection().equalsIgnoreCase("Selected")){
				quer="from HostelOnlineApplication online where online.isActive=1" +
				" and online.academicYear='"+admissionForm.getAcademicYear()+"' and online.hlHostel.id='"+admissionForm.getHostelIdForReAdm()+"'" +
				" and (online.selectedRoomType is not null and online.selectedRoomType !='') order by online.student.registerNo ";
			}else{
				quer="from HostelOnlineApplication online where online.isActive=1" +
			" and online.academicYear='"+admissionForm.getAcademicYear()+"' and online.hlHostel.id='"+admissionForm.getHostelIdForReAdm()+"'" +
			" order by online.student.registerNo ";
			}
			Query query=session.createQuery(quer);
			onlineApplicationList = query.list();
		}catch(Exception e){
			log.info("exception occured in getStudentDetailsByChallanNo Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return onlineApplicationList;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelReAdmissionTransaction#updateHostelOnlineApplication(java.util.List)
	 */
	@Override
	public boolean updateHostelOnlineApplication(List<HostelOnlineApplication> applications) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(applications!=null && !applications.isEmpty()){
				isUpdated=true;
				for (HostelOnlineApplication hostelOnlineApplication : applications) {
					session.merge(hostelOnlineApplication);
				}
			}
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return isUpdated;
	}
	
	
	
	public Student getStudentDetails(int id) {
		Session session=null;
		Student s=null;
		try{
			session=HibernateUtil.getSession();
				String quer=" select s from Student s where s.id="+id;
			
			Query query=session.createQuery(quer);
			s =(Student) query.uniqueResult();
		}catch(Exception e){
			log.info("exception occured in getStudentDetailsByChallanNo Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return s;
	}
    
    
    
}
