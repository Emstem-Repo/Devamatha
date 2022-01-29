package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.forms.admission.CancelPromotionForm;
import com.kp.cms.helpers.admission.CancelAdmissionHelper;
import com.kp.cms.to.admission.CancelPromotionTo;
import com.kp.cms.transactions.admission.ICancelPromtionTranscation;
import com.kp.cms.transactionsimpl.admission.CancelPromtionTxnImpl;

public class CancelAdmissionHandler {
	private static final Log log = LogFactory.getLog(CancelAdmissionHandler.class);
	private static volatile CancelAdmissionHandler cancelAdmissionHandler = null;
	public static CancelAdmissionHandler getInstance(){
		if(cancelAdmissionHandler == null){
			cancelAdmissionHandler = new CancelAdmissionHandler();
			return cancelAdmissionHandler;
		}
		return cancelAdmissionHandler;
	}
	/**
	 * @param cancelPromotionForm
	 * @return
	 */
	
	public List<CancelPromotionTo> searchCancelPromotion(CancelPromotionForm cancelPromotionForm) throws Exception{
		ICancelPromtionTranscation transaction = CancelPromtionTxnImpl.getInstance();
		Student student = transaction.getSearchCancelPromotionDetails(cancelPromotionForm);
		List<CancelPromotionTo> cancelPromotionTos= CancelAdmissionHelper.getInstance().populateBoTOTo(student);
		return cancelPromotionTos;
	}
	
	/**
	 * @param stuId
	 * @param semNo
	 * @return
	 * @throws Exception
	 */
	public CancelPromotionTo getPreviousClassDetails(int stuId, int semNo) throws Exception {
		ICancelPromtionTranscation transaction = CancelPromtionTxnImpl.getInstance();
		ExamStudentPreviousClassDetailsBO previousClass=transaction.getPreviousClassDetails(stuId,semNo);
		CancelPromotionTo promotionTo = CancelAdmissionHelper.getInstance().copyBOToTO(previousClass);
		ClassSchemewise classSchemewise = transaction.getClassSchemeWiseDetails(promotionTo);
		promotionTo =CancelAdmissionHelper.getInstance().copyClassSchemeDetailsBOToTO(classSchemewise,promotionTo);
		return promotionTo;
	}
//	/**
//	 * @param promotionTo
//	 * @return
//	 * @throws Exception
//	 */
//	public CancelPromotionTo getClassSchemeWiseDetails( CancelPromotionTo promotionTo) throws Exception{
//		ICancelPromtionTranscation transaction = CancelPromtionTxnImpl.getInstance();
//		ClassSchemewise classSchemewise = transaction.getClassSchemeWiseDetails(promotionTo);
//		promotionTo =CancelAdmissionHelper.getInstance().copyClassSchemeDetailsBOToTO(classSchemewise,promotionTo);
//		return promotionTo;
//	}
	/**
	 * @param admappln 
	 * @param admappln
	 * @param canProTo 
	 * @return
	 * @throws Exception 
	 */
	public boolean cancelPromotionDetails( int admappln, int stuId,CancelPromotionTo canProTo, int semNo) throws Exception {
		boolean isCancelled = false;
		ICancelPromtionTranscation transaction = CancelPromtionTxnImpl.getInstance();
		isCancelled = transaction.saveClassSchemeWise(canProTo,stuId);
		if(isCancelled){
			transaction.deleteApplicantSubjectGroup(admappln);
			List<ExamStudentSubGrpHistoryBO> previousSubGrp=transaction.getSubjectGroupHistory(stuId,semNo);
			List<CancelPromotionTo> promotionToList = CancelAdmissionHelper.getInstance().copySubjectGrpHistoryTOTo(previousSubGrp);
			isCancelled=transaction.saveAppSubGrp(promotionToList,admappln);
			transaction.deletePreviousClasses(canProTo);
			transaction.deletePreviousSubGrp(promotionToList);
		}
		return isCancelled;
	}
}
