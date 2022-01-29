package com.kp.cms.handlers.exam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.forms.exam.RevaluationMarksUpdateForm;
import com.kp.cms.helpers.exam.RevaluationMarksUpdateHelper;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.RevaluationMarksUpdateTo;
import com.kp.cms.transactions.exam.IRevaluationMarksUpdateTransaction;
import com.kp.cms.transactionsimpl.exam.RevaluationMarksUpdateTransactionImpl;

public class RevaluationMarksUpdateHandler {
private static final Log log = LogFactory.getLog(RevaluationMarksUpdateHandler.class);
private static volatile RevaluationMarksUpdateHandler revaluationMarksUpdateHandler = null;
	
	/**
	 * @return
	 */
	public static RevaluationMarksUpdateHandler getInstance() {
		if (revaluationMarksUpdateHandler == null) {
			revaluationMarksUpdateHandler = new RevaluationMarksUpdateHandler();
		}
		return revaluationMarksUpdateHandler;
	}
	
	public List<RevaluationMarksUpdateTo> getStudentDetails(RevaluationMarksUpdateForm form) throws Exception{
		IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
		List<Object> boList=transaction.getStudentsNewMarksList(form);
		Map<Integer, Map<Integer,RevaluationMarksUpdateTo>> studentMap=RevaluationMarksUpdateHelper.getInstance().convertBotoTo(boList,form);
		// map is not sending  the values from jsp to form thats why again  converting the map to list
		List<RevaluationMarksUpdateTo> studentDetailsList=RevaluationMarksUpdateHelper.getInstance().convertMaptoList(studentMap,form);
		return studentDetailsList;
	}
	public boolean thirdEvaluation(RevaluationMarksUpdateForm form) throws Exception{
		boolean isThirdEvaluation=false;
		IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
		boolean isEvaluation=transaction.thirdEvaluation(form);
		if(isEvaluation)
			isThirdEvaluation=true;
		return isThirdEvaluation;
	}
	
	public void saveChangedMarks(RevaluationMarksUpdateForm form) throws Exception{
		List<MarksEntryCorrectionDetails> boList=RevaluationMarksUpdateHelper.getInstance().convertTotoBoListForRetotaling(form);
		IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
		boolean isUpdated=transaction.updateModifiedMarksForRetotaling(form);
		if(isUpdated)
		 transaction.saveMarksEntryCorrection(boList);
		 
	}
	public void saveChangedMarksforRevaluation(RevaluationMarksUpdateForm form) throws Exception{
		List<MarksEntryCorrectionDetails> boList=RevaluationMarksUpdateHelper.getInstance().convertTotoBoListForRevaluation(form);
		IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
		boolean isUpdated=transaction.updateModifiedMarksForRevaluation(form);
		if(isUpdated)
		 transaction.saveMarksEntryCorrection(boList);
	}
	public Double getMaxMarkOfSubject(RevaluationMarksUpdateForm form ) throws Exception {
		IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
			return transaction.getMaxMarkOfSubject(form);
	}
	public boolean saveChangedMarksForUpdateAll(RevaluationMarksUpdateForm form) throws Exception{
		List<MarksEntryCorrectionDetails> boList=RevaluationMarksUpdateHelper.getInstance().convertTotoBoListForUpdateAll(form);
		IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
		boolean isUpdated=transaction.updateModifiedMarksForRetotalingForUpdateAll(form);
		if(isUpdated)
		 transaction.saveMarksEntryCorrection(boList);
		return  isUpdated;
	}

	/**
	 * @param revaluationMarksUpdateForm
	 * @throws Exception
	 */
	public void updateRegularOverAllProcess(RevaluationMarksUpdateForm revaluationMarksUpdateForm) throws Exception{
		if(revaluationMarksUpdateForm.getAvgMarks() != null && revaluationMarksUpdateForm.getStudentid() != 0
				&& revaluationMarksUpdateForm.getSubjectid() !=0 && revaluationMarksUpdateForm.getExamId() != null && revaluationMarksUpdateForm.getClassid() != 0){
			IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
			if(revaluationMarksUpdateForm.getOldMarks() != null && !revaluationMarksUpdateForm.getOldMarks().isEmpty()
					&& revaluationMarksUpdateForm.getAvgMarks() != null && !revaluationMarksUpdateForm.getAvgMarks().isEmpty()){
				if(Integer.parseInt(revaluationMarksUpdateForm.getOldMarks())!=Integer.parseInt(revaluationMarksUpdateForm.getAvgMarks())){
					String query = RevaluationMarksUpdateHelper.getInstance().getQueryForStudentClassDetails(revaluationMarksUpdateForm);
					List list=transaction.getDataByQuery(query);
					List<ClassesTO> classList = RevaluationMarksUpdateHelper.getInstance().convertBoListToTOList(list);
					RevaluationMarksUpdateHelper.getInstance().calculateRegularOverAllAndSaveData(revaluationMarksUpdateForm, classList);
					if(revaluationMarksUpdateForm.getErrorList()==null || revaluationMarksUpdateForm.getErrorList().isEmpty()){
						if(revaluationMarksUpdateForm.getErrorMessage() == null || revaluationMarksUpdateForm.getErrorMessage().isEmpty()){
							RevaluationMarksUpdateHelper.getInstance().updatePassOrFail(revaluationMarksUpdateForm,classList);
						}
					}
				}
			}
			RevaluationMarksUpdateHelper.getInstance().updateRevaluationApllicationStatus(revaluationMarksUpdateForm,transaction);
		}
	}

	/**
	 * @param revaluationMarksUpdateForm
	 * @throws Exception
	 */
	public void updateRegularOverAllProcessRetoaling(
			RevaluationMarksUpdateForm revaluationMarksUpdateForm) throws Exception{
		if(revaluationMarksUpdateForm.getStudentid() != 0 && revaluationMarksUpdateForm.getSubjectid() !=0 
				&& revaluationMarksUpdateForm.getExamId() != null && revaluationMarksUpdateForm.getClassid() != 0){
			IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
			boolean runProcess = false;
			if(revaluationMarksUpdateForm.getOldMarks() != null && !revaluationMarksUpdateForm.getOldMarks().isEmpty()
					&& revaluationMarksUpdateForm.getNewMarks() != null && !revaluationMarksUpdateForm.getNewMarks().isEmpty()){
				if(Integer.parseInt(revaluationMarksUpdateForm.getOldMarks())!=Integer.parseInt(revaluationMarksUpdateForm.getNewMarks())){
					runProcess = true;
				}
			}
			if(!runProcess && revaluationMarksUpdateForm.getOldMark1() != null && !revaluationMarksUpdateForm.getOldMark1().isEmpty()
					&& revaluationMarksUpdateForm.getNewMark1() != null && !revaluationMarksUpdateForm.getNewMark1().isEmpty()){
				if(Integer.parseInt(revaluationMarksUpdateForm.getOldMark1())!=Integer.parseInt(revaluationMarksUpdateForm.getNewMark1())){
					runProcess = true;
				}
			}
			if(!runProcess && revaluationMarksUpdateForm.getOldMark2() != null && !revaluationMarksUpdateForm.getOldMark2().isEmpty()
					&& revaluationMarksUpdateForm.getNewMark2() != null && !revaluationMarksUpdateForm.getNewMark2().isEmpty()){
				if(Integer.parseInt(revaluationMarksUpdateForm.getOldMark2())!=Integer.parseInt(revaluationMarksUpdateForm.getNewMark2())){
					runProcess = true;
				}
			}
			//new
			if(!runProcess && revaluationMarksUpdateForm.getOldMark3() != null && !revaluationMarksUpdateForm.getOldMark3().isEmpty()
					&& revaluationMarksUpdateForm.getNewMark3() != null && !revaluationMarksUpdateForm.getNewMark3().isEmpty()){
				if(Integer.parseInt(revaluationMarksUpdateForm.getOldMark3())!=Integer.parseInt(revaluationMarksUpdateForm.getNewMark3())){
					runProcess = true;
				}
			}
			if(runProcess){
				String query = RevaluationMarksUpdateHelper.getInstance().getQueryForStudentClassDetails(revaluationMarksUpdateForm);
				List list=transaction.getDataByQuery(query);
				List<ClassesTO> classList = RevaluationMarksUpdateHelper.getInstance().convertBoListToTOList(list);
				RevaluationMarksUpdateHelper.getInstance().calculateRegularOverAllAndSaveData(revaluationMarksUpdateForm, classList);
				if(revaluationMarksUpdateForm.getErrorList()==null || revaluationMarksUpdateForm.getErrorList().isEmpty()){
					if(revaluationMarksUpdateForm.getErrorMessage() == null || revaluationMarksUpdateForm.getErrorMessage().isEmpty()){
						RevaluationMarksUpdateHelper.getInstance().updatePassOrFail(revaluationMarksUpdateForm,classList);
					}
				}
			}
			RevaluationMarksUpdateHelper.getInstance().updateRevaluationApllicationStatusRetoaling(revaluationMarksUpdateForm,transaction);
			
		}
	}

	/**
	 * @param revaluationMarksUpdateForm
	 * @throws Exception
	 */
	public void updateRegularOverAllProcessRetoalingForAll(RevaluationMarksUpdateForm revaluationMarksUpdateForm) throws Exception{
		if(revaluationMarksUpdateForm.getClassList() != null && !revaluationMarksUpdateForm.getClassList().isEmpty()
				&& revaluationMarksUpdateForm.getStudentListClassWise() != null && !revaluationMarksUpdateForm.getStudentListClassWise().isEmpty()){
			IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
			String query = RevaluationMarksUpdateHelper.getInstance().getQueryForStudentClassDetailsForAllClasses(revaluationMarksUpdateForm);
			List list=transaction.getDataByQueryForAllClasses(query,revaluationMarksUpdateForm.getClassList());
			List<ClassesTO> classList = RevaluationMarksUpdateHelper.getInstance().convertBoListToTOList(list);
			RevaluationMarksUpdateHelper.getInstance().calculateRegularOverAllAndSaveDataForAllClasses(revaluationMarksUpdateForm, classList);
			if(revaluationMarksUpdateForm.getErrorList()==null || revaluationMarksUpdateForm.getErrorList().isEmpty()){
				if(revaluationMarksUpdateForm.getErrorMessage() == null || revaluationMarksUpdateForm.getErrorMessage().isEmpty()){
					RevaluationMarksUpdateHelper.getInstance().updatePassOrFail(revaluationMarksUpdateForm,classList);
				}
			}
		}
	}
	
	public Double getSubjectMaxMarks(RevaluationMarksUpdateForm form,int courseId,int schemeNo,int subjectId ) throws Exception {
		IRevaluationMarksUpdateTransaction transaction=RevaluationMarksUpdateTransactionImpl.getInstance();
			return transaction.getSubjectMaxMark(form,courseId,schemeNo,subjectId);
	}
}
