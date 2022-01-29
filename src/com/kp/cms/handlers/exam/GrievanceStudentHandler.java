package com.kp.cms.handlers.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.bo.exam.GrievanceStudentBo;
import com.kp.cms.forms.exam.GrievanceStudentForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.helpers.exam.GrievanceStudentHelper;
import com.kp.cms.to.exam.GrievanceStudentTo;
import com.kp.cms.transactions.IGrievanceStudentTransaction;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.usermanagement.IStudentLoginTransaction;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.GrievanceStudentTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.StudentLoginTransactionImpl;

public class GrievanceStudentHandler {
	
	private static final Log log = LogFactory.getLog(GrievanceStudentHandler.class);
	public static volatile GrievanceStudentHandler grievanceStudentHandler = null;
	
	public static GrievanceStudentHandler getInstance() {
	if (grievanceStudentHandler == null) {
		grievanceStudentHandler = new GrievanceStudentHandler();
		return grievanceStudentHandler;
	}
	return grievanceStudentHandler;
	}
	
	public List<GrievanceStudentTo>  getStudents(GrievanceStudentForm grievanceStudentForm) throws Exception {
		
		IGrievanceStudentTransaction iGrievanceStudentTransaction = GrievanceStudentTransactionImpl.getInstance();
		List<GrievanceRemarkBo> grievanceRemarkBoList = iGrievanceStudentTransaction.getStudents(grievanceStudentForm);
		List<GrievanceStudentTo> grievanceStudentToList = GrievanceStudentHelper.getInstance().convertBotoTo(grievanceRemarkBoList,grievanceStudentForm);
		return grievanceStudentToList;
	}
	
	public List<GrievanceStudentTo>  getGrievanceStudentdetails(GrievanceStudentForm grievanceStudentForm) throws Exception {
		
		IGrievanceStudentTransaction iGrievanceStudentTransaction = GrievanceStudentTransactionImpl.getInstance();
		List<GrievanceRemarkBo> grievanceRemarkBoList = iGrievanceStudentTransaction.getGrievanceStudentdetails(grievanceStudentForm);
		List<GrievanceStudentTo> grievanceStudentToList = GrievanceStudentHelper.getInstance().convertBotoTo(grievanceRemarkBoList,grievanceStudentForm);
		return grievanceStudentToList;
	}
	public boolean  saveRemarks(List<GrievanceStudentTo> studentList,GrievanceStudentForm grievanceStudentForm,Integer roleId) throws Exception {
		boolean isUpadated=false;
		boolean isSaved=false;
		IGrievanceStudentTransaction iGrievanceStudentTransaction = GrievanceStudentTransactionImpl.getInstance();
		List<GrievanceStudentBo> grievanceStudentBoList = GrievanceStudentHelper.getInstance().convertTotoBo(studentList,grievanceStudentForm);
		isUpadated = iGrievanceStudentTransaction.updateGrievanceBo(studentList,roleId);
		//isSaved = iGrievanceStudentTransaction.saveRemarks(grievanceStudentBoList);
		return isUpadated;
	}
	
	public Integer getRoleIdByUserId(GrievanceStudentForm grievanceStudentForm) throws Exception { 
		Integer roleId=0;
		IGrievanceStudentTransaction iGrievanceStudentTransaction = GrievanceStudentTransactionImpl.getInstance();
		roleId = iGrievanceStudentTransaction.getRoleIdByUserId(grievanceStudentForm);
		return roleId;
	}

}
