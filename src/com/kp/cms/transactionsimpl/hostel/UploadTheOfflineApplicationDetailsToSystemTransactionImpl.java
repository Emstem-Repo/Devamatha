package com.kp.cms.transactionsimpl.hostel;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.UploadTheOfflineApplicationDetailsToSystemForm;
import com.kp.cms.transactions.hostel.IUploadOfflineAppliDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class UploadTheOfflineApplicationDetailsToSystemTransactionImpl implements IUploadOfflineAppliDetailsTransaction{
	private static final Log log = LogFactory.getLog(UploadTheOfflineApplicationDetailsToSystemTransactionImpl.class);
	private static volatile UploadTheOfflineApplicationDetailsToSystemTransactionImpl offlineAppDetailsTrnsImpl = null;
	
	private UploadTheOfflineApplicationDetailsToSystemTransactionImpl() {
	}
	
	public static UploadTheOfflineApplicationDetailsToSystemTransactionImpl getInstance() {
		if (offlineAppDetailsTrnsImpl == null) {
			offlineAppDetailsTrnsImpl = new UploadTheOfflineApplicationDetailsToSystemTransactionImpl();
		}
		return offlineAppDetailsTrnsImpl;
	}
	public Map<String,Integer> getRoomType(int hostelId)throws Exception{
		Session session = null;
		Map<String,Integer> map = new HashMap<String,Integer>();
		List<HlRoomType> hlRoomTypeList=null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Query query = session.createQuery("select hlType from HlRoomType hlType where hlType.isActive=1 "+
					" and hlType.hlHostel.id="+hostelId);
			hlRoomTypeList=query.list();
			if(hlRoomTypeList!=null && !hlRoomTypeList.isEmpty()){
				Iterator<HlRoomType> iterator = hlRoomTypeList.iterator();
				while (iterator.hasNext()) {
					HlRoomType bo = (HlRoomType) iterator.next();
					if(bo!=null){
						map.put(bo.getName(),bo.getId());
					}
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of getAppDetails method in  UploadSecondLanguageTransactionImpl class.");
		return map;
		
	}
	
	
	public boolean saveUploadOfflineApplication(Map<Integer,HostelOnlineApplication> sapbo,UploadTheOfflineApplicationDetailsToSystemForm objform) throws Exception {
		log.debug("inside uploadBlockListForHallticketOrMarkscard");
		
		//ExamBlockUnblockHallTicketBO examBo = new ExamBlockUnblockHallTicketBO();
		
		Session session = null;
		Transaction transaction = null;
		boolean isSaved=false;
			try {
			
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			if(sapbo!=null && !sapbo.isEmpty()){
			Iterator<Entry<Integer, HostelOnlineApplication>> iterator = sapbo.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, HostelOnlineApplication> entry = (Map.Entry<Integer, HostelOnlineApplication>) iterator.next();
				HostelOnlineApplication bo=entry.getValue();
			if(bo!=null){	
				Query query = session.createQuery("from HostelOnlineApplication e where  e.student.id=:studentId" +
													" and e.hlHostel.id=:hostelId and e.academicYear=:academicYear");
				if( bo.getStudent()!= null){
					query.setInteger("studentId", bo.getStudent().getId());
				}
				if(bo.getHlHostel()!=null ){
					query.setInteger("hostelId", bo.getHlHostel().getId());
				}
				if(objform.getAcademicYear1()!=null && !objform.getAcademicYear1().isEmpty()){
					query.setInteger("academicYear", Integer.parseInt(objform.getAcademicYear1()));
				}
				
				
				HostelOnlineApplication examBo = (HostelOnlineApplication)query.uniqueResult();
				if(examBo !=null){
					examBo.setLastModifiedDate(new Date());
					examBo.setModifiedBy(objform.getUserId());
					session.update(examBo);
					
				}else{
					session.save(bo);
				}
				isSaved=true;
				
			}
				
		}
			transaction.commit();
			session.flush();
	}else{
				isSaved=false;
			}
			session.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocTypeDuplcated");
		return isSaved;
	}
	
	public	Map<String,Integer> getStudentIdByStudentRegNum(List<String> regNumList) throws Exception{

		Session session=null;
		Map<String,Integer> studentIdMap=new HashMap<String,Integer>();
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.registerNo != null and s.isHide = 0 and s.admAppln.isCancelled=0 " +
											  " and s.registerNo in (:regNumList)");
		  query.setParameterList("regNumList", regNumList);
		  List<Student> listl=query.list();
			Iterator<Student> itr = listl.iterator();
			while (itr.hasNext()) {
				Student student = (Student) itr.next();
				studentIdMap.put(student.getRegisterNo(), student.getId());
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
			session.close();
		}
		
		return studentIdMap;
    }


}
