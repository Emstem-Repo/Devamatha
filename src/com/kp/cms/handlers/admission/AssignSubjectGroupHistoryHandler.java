package com.kp.cms.handlers.admission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm;
import com.kp.cms.helpers.admission.AssignSubjectGroupHistoryHelper;
import com.kp.cms.to.admission.AssignSubjectGroupHistoryTO;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;
import com.kp.cms.transactions.admission.IAssignSubjectGroupHistoryTxn;
import com.kp.cms.transactionsimpl.admission.AssignSubjectGroupHistoryTransactionImpl;

public class AssignSubjectGroupHistoryHandler {
	private static final Log log = LogFactory .getLog(AssignSubjectGroupHistoryHandler.class);
 private static volatile AssignSubjectGroupHistoryHandler subjectGroupHistoryHandler = null;
 public static AssignSubjectGroupHistoryHandler getInstance(){
	 if(subjectGroupHistoryHandler == null){
		 subjectGroupHistoryHandler = new AssignSubjectGroupHistoryHandler();
		 return subjectGroupHistoryHandler;
	 }
	 return subjectGroupHistoryHandler;
 }
/**
 * @param assignSubGrpHistory
 * @return
 */
 public List<AssignSubjectGroupHistoryTO> getStudentDetails( AssignSubjectGroupHistoryForm assignSubGrpHistory)throws Exception {
	 log.info("call of getStudentDetails method in AssignSubjectGroupHistoryHandler class.");
	IAssignSubjectGroupHistoryTxn transaction= AssignSubjectGroupHistoryTransactionImpl.getInstance();
	ClassSchemewise classSchemewises = transaction.getClassSchemwises(assignSubGrpHistory);
	List<StudentPreviousClassHistory> classDetailsBOs = transaction.getStudentPreviousClassDetails(classSchemewises,assignSubGrpHistory);
	List<AssignSubjectGroupHistoryTO> groupHistoryTOs = AssignSubjectGroupHistoryHelper.getInstance().populatePreviousClassBoToTO(classDetailsBOs,new HashMap<Integer, Integer>());
	int halfLength = 0;
	int totLength = groupHistoryTOs.size();
	if (totLength % 2 == 0) {
		halfLength = totLength / 2;
	} else {
		halfLength = (totLength / 2) + 1;
	}
	assignSubGrpHistory.setHalfLength(halfLength);
	log.info("end of getStudentDetails method in AssignSubjectGroupHistoryHandler class.");
	return groupHistoryTOs;
}
/**
 * @param assignSubGrpHistory
 * @return
 * @throws Exception
 */
	public List<SubjectGroupDetailsTo> getSubjectGroups( AssignSubjectGroupHistoryForm assignSubGrpHistory)throws Exception {
		log.info("call of getSubjectGroups method in AssignSubjectGroupHistoryHandler class.");
	IAssignSubjectGroupHistoryTxn transaction= AssignSubjectGroupHistoryTransactionImpl.getInstance();
	Map<Integer,SubjectGroup> SubjectGroupMap = transaction.getSubjectGroups(assignSubGrpHistory);
	List<SubjectGroupDetailsTo> groupDetailsTos = AssignSubjectGroupHistoryHelper.getInstance().convertBOToTO(SubjectGroupMap);
	log.info("end of getSubjectGroups method in AssignSubjectGroupHistoryHandler class.");
	return groupDetailsTos;
}
/**
 * @param assignSubGrpHistory
 * @return
 * @throws Exception
 */
	public List<SubjectGroupDetailsTo> getSubjectGroupsList( AssignSubjectGroupHistoryForm assignSubGrpHistory) throws Exception{
		log.info("call of getSubjectGroupsList method in AssignSubjectGroupHistoryHandler class.");
	IAssignSubjectGroupHistoryTxn transaction= AssignSubjectGroupHistoryTransactionImpl.getInstance();
	ClassSchemewise classSchemewises = transaction.getClassSchemwises(assignSubGrpHistory);
	List<SubjectGroup> utilBOs = transaction.getSubjectGroupsList(assignSubGrpHistory,classSchemewises);
	List<SubjectGroupDetailsTo> groupDetailsTos = AssignSubjectGroupHistoryHelper.getInstance().populateSubGrpDetailsToTO(utilBOs);
	log.info("end of getSubjectGroupsList method in AssignSubjectGroupHistoryHandler class.");
	return groupDetailsTos;
}
/**
 * @param assignSubGrpHistoryForm
 * @return
 * @throws Exception
 */
	public boolean addStudentSubjectGroups( AssignSubjectGroupHistoryForm assignSubGrpHistoryForm) throws Exception {
		log.info("call of addStudentSubjectGroups method in AssignSubjectGroupHistoryHandler class.");
		boolean isAdded=false;
		IAssignSubjectGroupHistoryTxn transaction= AssignSubjectGroupHistoryTransactionImpl.getInstance();
		isAdded= transaction.addStudentSubjectGroup(assignSubGrpHistoryForm);
		log.info("end of addStudentSubjectGroups method in AssignSubjectGroupHistoryHandler class.");
		return isAdded;
	}
/**
 * @param assignSubGrpHistoryForm
 * @return
 * @throws Exception
 */
public List<AssignSubjectGroupHistoryTO> getEditSubjectGroups( AssignSubjectGroupHistoryForm assignSubGrpHistoryForm) throws Exception {
	log.info("call of getEditSubjectGroups method in AssignSubjectGroupHistoryHandler class.");
	IAssignSubjectGroupHistoryTxn transaction= AssignSubjectGroupHistoryTransactionImpl.getInstance();
	ClassSchemewise classSchemewises = transaction.getClassSchemwises(assignSubGrpHistoryForm);
	List<StudentPreviousClassHistory> classDetailsBOs = transaction.getStudentPreviousClassDetails(classSchemewises,assignSubGrpHistoryForm);
	Map<Integer,Integer> map = transaction.getEditSubjectGroups(assignSubGrpHistoryForm);
	List<AssignSubjectGroupHistoryTO> groupHistoryTOs = AssignSubjectGroupHistoryHelper.getInstance().populatePreviousClassBoToTO(classDetailsBOs,map);
	log.info("end of getEditSubjectGroups method in AssignSubjectGroupHistoryHandler class.");
	return groupHistoryTOs;
}
}
