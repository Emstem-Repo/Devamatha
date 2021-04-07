package com.kp.cms.handlers.hostel;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.helpers.hostel.ResidentStudentInfoHelper;
import com.kp.cms.to.hostel.ResidentStudentInfoTO;
import com.kp.cms.transactions.hostel.IResidentStudentInfo;
import com.kp.cms.transactionsimpl.hostel.ResidentStudentInfoTransactionImpl;

public class ResidentStudentInfoHandler {
	/**
	 * Singleton object of ResidentStudentInfoHandler
	 */
	private static volatile ResidentStudentInfoHandler residentStudentInfoHandler = null;
	private static final Log log = LogFactory.getLog(ResidentStudentInfoHandler.class);
	private ResidentStudentInfoHandler() {
		
	}
	/**
	 * return singleton object of ResidentStudentInfoHandler.
	 * @return
	 */
	public static ResidentStudentInfoHandler getInstance() {
		if (residentStudentInfoHandler == null) {
			residentStudentInfoHandler = new ResidentStudentInfoHandler();
		}
		return residentStudentInfoHandler;
	}
	ResidentStudentInfoHelper helper=ResidentStudentInfoHelper.getInstance();
	IResidentStudentInfo transaction=new ResidentStudentInfoTransactionImpl();
	/**
	 * getting the applicant details by passing studentId
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public ResidentStudentInfoTO searchStudentById(String studentId,String hostelId) throws Exception{
		String searchCriteria=helper.getSearchCriteriaById(studentId,hostelId);
		HlApplicationForm hlApplicationForm=transaction.getCandidatesById(searchCriteria);
		ResidentStudentInfoTO residentto=null;
		if(hlApplicationForm!=null)
		residentto=helper.convertBOtoTO(hlApplicationForm);
		return residentto;
	}
	
	/**
	 * getting the list of applicants by Names
	 * @param studentName
	 * @return
	 * @throws Exception
	 */
	public List<ResidentStudentInfoTO> searchStudentByName(String studentName,String hostelId) throws Exception{
		String searchCriteria=helper.getSearchCriteriaByName(studentName,hostelId);
		List<HlApplicationForm> list=transaction.getListOfCandidates(searchCriteria);
		List<ResidentStudentInfoTO> studentList=null;
		if(list!=null && !list.isEmpty()){
		studentList=helper.convertBOListtoTOList(list);
		}
		return studentList;
	}
	
	/**
	 * getting the student details by HlApplnId
	 * @param hlappId
	 * @return
	 * @throws Exception
	 */
	public ResidentStudentInfoTO searchHlApplnById(String hlappId) throws Exception{
		
		HlApplicationForm hlApplicationForm=transaction.getCandidatesByHlAppId(hlappId);
		ResidentStudentInfoTO residentto=null;
		if(hlApplicationForm!=null)
		residentto=helper.convertBOtoTO(hlApplicationForm);
		return residentto;
	}
}
