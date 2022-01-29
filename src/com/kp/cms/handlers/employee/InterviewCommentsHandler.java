package com.kp.cms.handlers.employee;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpInterview;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.forms.employee.InterviewCommentsForm;
import com.kp.cms.helpers.employee.InterviewCommentsHelper;
import com.kp.cms.to.employee.InterviewCommentsTO;
import com.kp.cms.transactions.employee.IInterviewCommentsTransaction;
import com.kp.cms.transactionsimpl.employee.InterviewCommentsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class InterviewCommentsHandler {
	private static final Log log = LogFactory
			.getLog(InterviewCommentsHandler.class);
	public static volatile InterviewCommentsHandler objHandler = null;

	public static InterviewCommentsHandler getInstance() {
		if (objHandler == null) {
			objHandler = new InterviewCommentsHandler();
			return objHandler;
		}
		return objHandler;
	}

	public List<InterviewCommentsTO> getDetails(String name) throws Exception {
		IInterviewCommentsTransaction txn = new InterviewCommentsTransactionImpl();
		List<Object[]> list = txn.getDetails(name);
		return InterviewCommentsHelper.getInstance().convertBOToTO(list);
	}

	public InterviewCommentsForm getComments(InterviewCommentsForm objform)
			throws Exception {
		IInterviewCommentsTransaction txn = new InterviewCommentsTransactionImpl();
		List<Object[]> list = txn.getInterviewDetails(objform.getId());
		Object[] employeeName;
		if (list != null) {
			Iterator<Object[]> iterator = list.iterator();
			while (iterator.hasNext()) {
				employeeName = (Object[]) iterator.next();
				if (employeeName[0] != null) {
					objform.setName(employeeName[0].toString());
				}
				if (employeeName[1] != null) {
					objform.setDepartmentId(employeeName[1].toString());
				}
			}
		}

		List<EmpInterview> listInterviewComments = txn.getInterviewComments(objform.getId());

		if (listInterviewComments != null && !listInterviewComments.isEmpty()) {
			Iterator<EmpInterview> itr = listInterviewComments.iterator();
			EmpInterview objEIBO = null;
			while (itr.hasNext()) {
				objEIBO = (EmpInterview) itr.next();
				
				objform.setInterviewCommentsId(objEIBO.getId());
				
				if (objEIBO.getInterviewDate() != null) {
					objform.setDateOfInterview(CommonUtil
							.ConvertStringToDateFormat(CommonUtil
									.getStringDate(objEIBO.getInterviewDate()),
									"dd-MMM-yyyy", "dd/MM/yyyy"));
				}
				if (objEIBO.getComments()!= null) {
					objform.setComments(objEIBO.getComments());
				}
				if (objEIBO.getEvaluatedBy() != null) {
					objform.setEvaluatedBy(objEIBO.getEvaluatedBy());
				}
				if (objEIBO.getIsMarksCardsVerified() != null) {
					if(objEIBO.getIsMarksCardsVerified()){
						objform.setMarksCards("on");
					}else{
						objform.setMarksCards("off");
					}

				}
				if (objEIBO.getIsExperienceCertificateVerified() != null) {
					if(objEIBO.getIsExperienceCertificateVerified()){
						objform.setExperienceCertificate("on");
					}else{
						objform.setExperienceCertificate("off");
					}

				}

			}
		}else{
			objform.setInterviewCommentsId(0);
		}

		return objform;
	}

	public boolean saveInterviewComments(InterviewCommentsForm objform)
			throws Exception {
		IInterviewCommentsTransaction txn = new InterviewCommentsTransactionImpl();
		boolean flag = false;
		if (objform.getId() != 0) {
			
			EmpInterview objBO = InterviewCommentsHelper.getInstance()
					.convertFormToBO(objform.getId(),objform.getInterviewCommentsId(),
							objform.getDateOfInterview(),
							objform.getComments(), objform.getEvaluatedBy(),
							objform.getMarksCards(),
							objform.getExperienceCertificate(),
							objform.getUserId());
			flag = txn.save(objBO);

		}
		return flag;
	}

	public InterviewCommentsForm getResumeDetails(InterviewCommentsForm objform)
			throws Exception {
		IInterviewCommentsTransaction txn = new InterviewCommentsTransactionImpl();

		objform.setInterviewCommentsTO(InterviewCommentsHelper.getInstance()
				.convertBOToTO(txn.getResumeDetails(objform.getId())));
		return objform;
	}
	
	public boolean updateStaus(InterviewCommentsForm objForm)throws Exception{
		if(objForm.getListOfDetails()!=null)
		{
//			List<EmpOnlineResume> resumeList=InterviewCommentsHelper.getInstance().convertFormToBO(objForm.getListOfDetails());
			IInterviewCommentsTransaction txn = new InterviewCommentsTransactionImpl();
			return txn.updateStatus(objForm.getListOfDetails());
			
		}
		return false;
	}
}
