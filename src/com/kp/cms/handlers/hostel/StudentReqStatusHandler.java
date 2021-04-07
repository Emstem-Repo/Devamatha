package com.kp.cms.handlers.hostel;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.to.hostel.StudentReqStatusTO;
import com.kp.cms.transactions.hostel.IStudentReqStatusTransaction;
import com.kp.cms.transactionsimpl.hostel.StudentReqStatusTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class StudentReqStatusHandler {
	
	private static final Log log = LogFactory.getLog(StudentReqStatusHandler.class);
	
	public static volatile StudentReqStatusHandler reqStatusHandler = null;

	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static StudentReqStatusHandler getInstance() {
		if (reqStatusHandler == null) {
			reqStatusHandler = new StudentReqStatusHandler();
		}
		return reqStatusHandler;
	}
	
		
	public StudentReqStatusTO getStudentDetailsList(int hlId) throws Exception {
		log.info("entered getStudentDetailsList in StudentReqStatusHandler class");
		
		IStudentReqStatusTransaction statusTransaction = StudentReqStatusTransactionImpl.getInstance();
//		int admApplnId = 0;
		List<HlApplicationForm> appList = null; 
//		if(hlId != 0){
//			admApplnId = statusTransaction.getAdmApplnId(hlId);
//		}
		if(hlId != 0){
			appList = statusTransaction.getStudentDetailsList(hlId);
		}
		StudentReqStatusTO reqStatusTO = new StudentReqStatusTO();
		if(appList != null && appList.size() != 0){
		Iterator<HlApplicationForm> iterator = appList.iterator();
		while (iterator.hasNext()) {
			HlApplicationForm applicationForm = (HlApplicationForm) iterator.next();
			
			if(applicationForm.getAppliedDate() != null){
				reqStatusTO.setAppliedDate(CommonUtil.formatDate(applicationForm.getAppliedDate(),"dd/MM/yyyy"));
			}
			if(applicationForm.getRequisitionNo() != null){
				reqStatusTO.setHostelReqNo(String.valueOf(applicationForm.getRequisitionNo()));
			}
			if(applicationForm.getAdmAppln() != null){
				reqStatusTO.setApplicationNo(String.valueOf(applicationForm.getAdmAppln().getApplnNo()));
			}
			if(applicationForm.getHlHostelByHlAppliedHostelId() != null && applicationForm.getHlHostelByHlAppliedHostelId().getName() != null){
				reqStatusTO.setHostelName(applicationForm.getHlHostelByHlAppliedHostelId().getName());
			}
			if(applicationForm.getHlHostelByHlApprovedHostelId() != null && applicationForm.getHlHostelByHlApprovedHostelId().getName() != null){
				reqStatusTO.setApprovedHostelName(applicationForm.getHlHostelByHlApprovedHostelId().getName());
			}
			if(applicationForm.getHlRoomTypeByHlAppliedRoomTypeId() != null && applicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName() != null){
				reqStatusTO.setReqRoomType(applicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName());
			}
			if(applicationForm.getHlRoomTypeByHlApprovedRoomTypeId() != null && applicationForm.getHlRoomTypeByHlApprovedRoomTypeId().getName() != null){
				reqStatusTO.setApprovedRoomType(applicationForm.getHlRoomTypeByHlApprovedRoomTypeId().getName());
			}
			if(applicationForm.getHlStatus() != null && applicationForm.getHlStatus().getName() != null){
				reqStatusTO.setStatus(applicationForm.getHlStatus().getStatusType());
			}
			
			String studentName = "";
			if (applicationForm.getAdmAppln().getPersonalData().getFirstName() != null) {
				studentName = studentName
						+ applicationForm.getAdmAppln().getPersonalData().getFirstName()
						+ " ";
			}
			if (applicationForm.getAdmAppln().getPersonalData().getMiddleName() != null) {
				studentName = studentName
						+ applicationForm.getAdmAppln().getPersonalData().getMiddleName()
						+ " ";
			}
			if (applicationForm.getAdmAppln().getPersonalData().getLastName() != null) {
				studentName = studentName
						+ applicationForm.getAdmAppln().getPersonalData().getLastName()
						+ " ";
			}			
			reqStatusTO.setStudentName(studentName);
		  } 
		}
		log.info("exit of getStudentDetailsList in StudentReqStatusHandler class");
		return reqStatusTO;
	}


	public List<StudentReqStatusTO> getStudentReqDetailsList(int studentId) throws Exception {
		IStudentReqStatusTransaction statusTransaction = StudentReqStatusTransactionImpl.getInstance();
		String queryString="select h from HlApplicationForm h join h.admAppln.students s where h.isActive=1 and s.id="+studentId+" group by h.id";
		return statusTransaction.getStudentReqDetailsList(queryString);
	}
}