package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.transactions.admission.IUploadFinalMeritListTxn;
import com.kp.cms.utilities.HibernateUtil;

public class UploadFinalMeritListTransactionImpl implements IUploadFinalMeritListTxn {
	private static final Log log = LogFactory.getLog(UploadFinalMeritListTransactionImpl.class);
	
	private static volatile UploadFinalMeritListTransactionImpl uploadFinalMeritListTransactionImpl = null;

	public static UploadFinalMeritListTransactionImpl getInstance() {
		if (uploadFinalMeritListTransactionImpl == null) {
			uploadFinalMeritListTransactionImpl = new UploadFinalMeritListTransactionImpl();
			return uploadFinalMeritListTransactionImpl;
		}
		return uploadFinalMeritListTransactionImpl;
	}
	
	/**
	 * 
	 * @param id
	 * @return courseMap <key,value>
	 */
	public Map<String, Integer> getCourseByProgram() {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Course c");
			List<Course> courseList = query.list();
			Map<String, Integer> courseMap = new HashMap<String, Integer>();
			if (!courseList.isEmpty()){
				Course course;
				Iterator<Course> itr = courseList.iterator();
				while (itr.hasNext()) {
					course = (Course) itr.next();
					if (course.getIsActive())
						courseMap.put(course.getCode(), course.getId());
				}
			}
			return courseMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<String, Integer>();
	}
	
	@Override
	public boolean addFInalMeritUploaded(List<AdmAppln> applnList, String user,List<AdmapplnAdditionalInfo> admAdditionalList) throws Exception {
		log.info("call of addFInalMeritUploaded method in  UploadFinalMeritListTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		AdmAppln admAppln;
		AdmAppln admApplnUpdateObj;
		AdmapplnAdditionalInfo admApplnAdditional=null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if (applnList != null && !applnList.isEmpty()) {
				Iterator<AdmAppln> itr = applnList.iterator();
				int count = 0;
				while (itr.hasNext()) {
					admAppln = itr.next();
					admApplnUpdateObj = (AdmAppln) session.get(AdmAppln.class, admAppln.getId());
					admApplnUpdateObj.setAdmStatus(admAppln.getAdmStatus());
					if(admAppln.getCourseBySelectedCourseId()!= null && admAppln.getCourseBySelectedCourseId().getId() > 0){
						admApplnUpdateObj.setCourseBySelectedCourseId(admAppln.getCourseBySelectedCourseId());
					}
					if(admAppln.getIsSelected()!=null)
					admApplnUpdateObj.setIsSelected(admAppln.getIsSelected());
					else
					admApplnUpdateObj.setIsSelected(false);
					//added by manu
					if(admAppln.getNotSelected()!=null)
					admApplnUpdateObj.setNotSelected(admAppln.getNotSelected());
					else
					 admApplnUpdateObj.setNotSelected(false);
					if(admAppln.getIsWaiting()!=null)
					admApplnUpdateObj.setIsWaiting(admAppln.getIsWaiting());
					else
					admApplnUpdateObj.setIsWaiting(false);
					//end
					admApplnUpdateObj.setFinalMeritListApproveDate(new Date());
					admApplnUpdateObj.setCreatedBy(user);
					admApplnUpdateObj.setModifiedBy(user);
					
					session.update(admApplnUpdateObj);
					Course course=(Course)session.get(Course.class,admApplnUpdateObj.getCourseBySelectedCourseId().getId());
					if(course.getIsApplicationProcessSms() && admApplnUpdateObj.getAdmStatus()!=null){

						String mobileNo="";
						if(admApplnUpdateObj.getPersonalData().getMobileNo1()!=null && !admApplnUpdateObj.getPersonalData().getMobileNo1().isEmpty()){
							if(admApplnUpdateObj.getPersonalData().getMobileNo1().trim().equals("0091") || admApplnUpdateObj.getPersonalData().getMobileNo1().trim().equals("+91")
									|| admApplnUpdateObj.getPersonalData().getMobileNo1().trim().equals("091") || admApplnUpdateObj.getPersonalData().getMobileNo1().trim().equals("0"))
								mobileNo = "91";
							else
								mobileNo=admApplnUpdateObj.getPersonalData().getMobileNo1();
						}else{
							mobileNo="91";
						}
						if(admApplnUpdateObj.getPersonalData().getMobileNo2()!=null && !admApplnUpdateObj.getPersonalData().getMobileNo2().isEmpty()){
							mobileNo=mobileNo+admApplnUpdateObj.getPersonalData().getMobileNo2();
						}
						if(mobileNo.length()==12){
							UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_FINAL_MERIT_LIST_NOT_SELECTED,admApplnUpdateObj);
						}
					
					}
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}
				}
			}
			
			if(admAdditionalList!=null && !admAdditionalList.isEmpty()){
				Iterator<AdmapplnAdditionalInfo> admItr=admAdditionalList.iterator();
				while (admItr.hasNext()) {
					AdmapplnAdditionalInfo admapplnAdditionalInfo = (AdmapplnAdditionalInfo) admItr.next();
						AdmAppln adm=admapplnAdditionalInfo.getAdmAppln();
						if(adm!=null && adm.getId()!=0){
							admApplnAdditional=(AdmapplnAdditionalInfo)session.createQuery("from AdmapplnAdditionalInfo a where a.admAppln.id=:admId").setInteger("admId", adm.getId()).uniqueResult();
							 if(admApplnAdditional!=null){
								 admApplnAdditional.setAdmissionScheduledDate(admapplnAdditionalInfo.getAdmissionScheduledDate());
								 admApplnAdditional.setAdmissionScheduledTime(admapplnAdditionalInfo.getAdmissionScheduledTime());
								 admApplnAdditional.setModifiedBy(user);
								 admapplnAdditionalInfo.setLastModifiedDate(new Date());
								 session.update(admApplnAdditional);
							 }else{
								 admapplnAdditionalInfo.setCreatedBy(user);
								 admapplnAdditionalInfo.setCreatedDate(new Date());
								 admapplnAdditionalInfo.setIsComedk(false);
								 session.save(admapplnAdditionalInfo);
					    }
					}
				}
			}
			transaction.commit();
			isAdded = true;

		}catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			isAdded = false;
			log.error("Error in addFInalMeritUploaded..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addFInalMeritUploaded method in  UploadFinalMeritListTransactionImpl class.");
		return isAdded;
	}
	
}
