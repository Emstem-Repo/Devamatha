package com.kp.cms.handlers.studentfeedback;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.studentfeedback.ExitEvaluationQuestion;
import com.kp.cms.bo.studentfeedback.ExitEvaluationTopic;
import com.kp.cms.forms.studentfeedback.ExitEvaluationQuestionForm;
import com.kp.cms.helpers.studentfeedback.ExitEvaluationQuestionHelper;
import com.kp.cms.to.studentfeedback.ExitEvaluationQuestionTo;
import com.kp.cms.transactions.fee.IFeeHeadingTransaction;
import com.kp.cms.transactions.studentfeedback.IExitEvaluationQuestionTransaction;
import com.kp.cms.transactionsimpl.fee.FeeHeadingTransactionImpl;
import com.kp.cms.transactionsimpl.studentfeedback.ExitEvaluationQuestionTransactionImpl;

public class ExitEvaluationQuestionHandler {
	private static final Logger log=Logger.getLogger(ExitEvaluationQuestionHandler.class);
	
	private static ExitEvaluationQuestionHandler exitEvalQuesHandler= null;
	public static ExitEvaluationQuestionHandler getInstance() {
	      if(exitEvalQuesHandler == null) {
	    	  exitEvalQuesHandler = new ExitEvaluationQuestionHandler();
	    	  return exitEvalQuesHandler;
	      }
	      return exitEvalQuesHandler;
	}
	
	public List<ExitEvaluationQuestionTo> getExitEvalQuesDetails() throws Exception {
		log.info("call of getExitEvalQuesDetails in ExitEvaluationQuestionHandler class.");
		IExitEvaluationQuestionTransaction transaction= new ExitEvaluationQuestionTransactionImpl();
		//getting the FeeHeading details as a list from Impl.
		List<ExitEvaluationQuestion> list = transaction.getAllExitEvalQues();
		//sending the list of BO to helper for converting to TO.
		List<ExitEvaluationQuestionTo> exitEvalQuesTolist = ExitEvaluationQuestionHelper.getInstance().convertBOstoTOs(list);
		log.info("end of getExitEvalQuesDetails in ExitEvaluationQuestionHandler class.");
		return exitEvalQuesTolist;
	}
	
	public boolean addExitEvalQues(ExitEvaluationQuestionForm exitEvalQuestForm) throws Exception {
		log.info("call of addExitEvalQues in ExitEvaluationQuestionHandler class.");
		//sending feeHeadingForm to helper to convertTostoBOs.
		ExitEvaluationQuestion exitEvalQues = ExitEvaluationQuestionHelper.getInstance().convertTOstoBOs(exitEvalQuestForm);
		IExitEvaluationQuestionTransaction transaction= new ExitEvaluationQuestionTransactionImpl();
		//sending BO instance to FeeHeadingTransactionImpl for saving a record to database.
		boolean isAdded = transaction.addExitEvalQuestion(exitEvalQues);
		log.info("end of addExitEvalQues in ExitEvaluationQuestionHandler class.");
		return isAdded;
	}
	
	public ExitEvaluationQuestionTo editExitEvalQues(int id) throws Exception {
		log.info("call of editFeeHeadings in ExitEvaluationQuestionHandler class.");
		IExitEvaluationQuestionTransaction transaction= new ExitEvaluationQuestionTransactionImpl();
		//getting a list of type FeeHeading BO from FeeHeadingTransactionImpl based on id.
		List<ExitEvaluationQuestion> exitEvalQuesList = transaction.editExitEvalQues(id);
		//converting list of type FeeHeading BO to FeeHeading TO.
		ExitEvaluationQuestionTo exitEvalQuesTo = ExitEvaluationQuestionHelper.getInstance().convertBOstoTOsForEdit(exitEvalQuesList);
		log.info("end of editFeeHeadings in ExitEvaluationQuestionHandler class.");
		return exitEvalQuesTo;
	}
	
	public boolean updateExitEvalQues(ExitEvaluationQuestionForm exitEvalQuestForm) throws Exception {
		log.info("call of updateExitEvalQues in ExitEvaluationQuestionHandler class.");
		IExitEvaluationQuestionTransaction transaction= new ExitEvaluationQuestionTransactionImpl();
		//setting the data form feeHeadingForm to BO for updating.
		ExitEvaluationQuestion exitEvalQues = new ExitEvaluationQuestion();
		ExitEvaluationTopic exitEvalTopic = new ExitEvaluationTopic();
		exitEvalTopic.setId(Integer.valueOf(exitEvalQuestForm.getExitEvalTopic()));
		exitEvalQues.setExitEvalTopic(exitEvalTopic);
		exitEvalQues.setId(exitEvalQuestForm.getExitEvalQuesId());
		exitEvalQues.setQuestion(exitEvalQuestForm.getQuestion());
		exitEvalQues.setIsActive(new Boolean(true));
		exitEvalQues.setModifiedBy(exitEvalQuestForm.getUserId());
		exitEvalQues.setLastModifiedDate(new Date());
			//sending the BO instance to FeeHeadingTransactionImpl for updating a record in database.
		boolean isUpdated = transaction.updateExitEvalQues(exitEvalQues);
		log.info("end of updateExitEvalQues in ExitEvaluationQuestionHandler class.");
		return isUpdated;
	}
	
	public boolean deleteExitEvalQues(int id, String userId) throws Exception {
		log.info("call of deleteExitEvalQues in ExitEvaluationQuestionHandler class.");
		IExitEvaluationQuestionTransaction transaction= new ExitEvaluationQuestionTransactionImpl();
		//sending id to FeeHeadingTransactionImpl for deleting a record from database.
		boolean isDeleted = transaction.deleteExitEvalQues(id,userId);
		log.info("end of deleteExitEvalQues in ExitEvaluationQuestionHandler class.");
		return isDeleted;
	}
	
	public void reActivateExitEvalQues(String question, String userId)throws Exception {
		log.info("call of reActivateExitEvalQues in ExitEvaluationQuestionHandler class.");
		IExitEvaluationQuestionTransaction transaction= new ExitEvaluationQuestionTransactionImpl();
		//restoring a record from database based on feesName through ExitEvaluationQuestionHandler.
		transaction.reActivateExitEvalQues(question,userId);
		log.info("end of reActivateExitEvalQues in ExitEvaluationQuestionHandler class.");
	}
	
	public ExitEvaluationQuestion isExitEvalQuesExist(int topicId, String question) throws Exception {
		log.info("call of FeeHeadingNameExist in FeeHeadingsHandler class.");
		log.info("call of reActivateExitEvalQues in ExitEvaluationQuestionHandler class.");
		IExitEvaluationQuestionTransaction transaction= new ExitEvaluationQuestionTransactionImpl();
		//restoring a record from database based on feesName through ExitEvaluationQuestionHandler.
		ExitEvaluationQuestion exitEvalQuestion=transaction.isExitEvalQuesExist(topicId, question);
		log.info("end of reActivateExitEvalQues in ExitEvaluationQuestionHandler class.");
		return exitEvalQuestion;
	}

}
