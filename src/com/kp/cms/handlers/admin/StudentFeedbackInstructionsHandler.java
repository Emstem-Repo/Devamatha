package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.StudentFeedbackInstructions;
import com.kp.cms.forms.admin.StudentFeedbackInstructionsForm;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;
import com.kp.cms.transactions.admin.IStudentFeedbackInstructionsTransaction;
import com.kp.cms.transactionsimpl.admin.StudentFeedbackInstructionsTrasImpl;

public class StudentFeedbackInstructionsHandler {
	private static final Logger log = Logger.getLogger(StudentFeedbackInstructionsHandler.class);
	public static volatile StudentFeedbackInstructionsHandler feedbackInstructionsHandler = null;
	public static StudentFeedbackInstructionsHandler getInstance(){
		if(feedbackInstructionsHandler ==null){
			feedbackInstructionsHandler = new StudentFeedbackInstructionsHandler();
			return feedbackInstructionsHandler;
		}
		return feedbackInstructionsHandler;
	}
	IStudentFeedbackInstructionsTransaction transaction = StudentFeedbackInstructionsTrasImpl.getInstance();
	/**
	 * @param stuFeedbackInstructionsForm
	 * @return
	 */
	public List<StudentFeedbackInstructionsTO> getInstructions() throws Exception{
		List<StudentFeedbackInstructionsTO> instructionsTOList = new ArrayList<StudentFeedbackInstructionsTO>();
		List<StudentFeedbackInstructions> instructions = transaction.getInstructions();
		StudentFeedbackInstructionsTO to = null;
		if(instructions!=null){
			Iterator<StudentFeedbackInstructions> iterator = instructions.iterator();
			while (iterator.hasNext()) {
				StudentFeedbackInstructions studentFeedbackInstructions = (StudentFeedbackInstructions) iterator .next();
				to = new StudentFeedbackInstructionsTO();
				to.setId(studentFeedbackInstructions.getId());
				to.setDescription(studentFeedbackInstructions.getDescription());
				instructionsTOList.add(to);
			}
		}
		return instructionsTOList;
	}
	/**
	 * @param stuFeedbackInstructionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicateInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm)throws Exception {
		boolean isExist = transaction.checkDuplicateInstructions(stuFeedbackInstructionsForm);
		return isExist;
	}
	/**
	 * @param stuFeedbackInstructionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean addStuFeedbackInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm ,String mode)throws Exception {
		StudentFeedbackInstructions feedbackInstructions = null;
		if(stuFeedbackInstructionsForm.getDescription()!=null && !stuFeedbackInstructionsForm.getDescription().isEmpty()){
			if(mode.equalsIgnoreCase("add")){
				feedbackInstructions = new StudentFeedbackInstructions();
				feedbackInstructions.setDescription(stuFeedbackInstructionsForm.getDescription());
				feedbackInstructions.setCreatedBy(stuFeedbackInstructionsForm.getUserId());
				feedbackInstructions.setCreatedDate(new Date());
			}else if(mode.equalsIgnoreCase("edit")){
				feedbackInstructions = new StudentFeedbackInstructions();
				feedbackInstructions.setId(stuFeedbackInstructionsForm.getId());
				feedbackInstructions.setDescription(stuFeedbackInstructionsForm.getDescription());
				feedbackInstructions.setModifiedBy(stuFeedbackInstructionsForm.getUserId());
				feedbackInstructions.setLastModifiedDate(new Date());
			}
		}
		boolean isAdded = transaction.addStuFeedbackInstructions(feedbackInstructions,mode);
		return isAdded;
	}
	/**
	 * @param stuFeedbackInstructionsForm
	 */
	public void editFeedbackInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm) throws Exception{
		StudentFeedbackInstructions feedbackInstructions = transaction.editFeedbackInstructions(stuFeedbackInstructionsForm);
		if(feedbackInstructions!=null && !feedbackInstructions.toString().isEmpty()){
			stuFeedbackInstructionsForm.setId(feedbackInstructions.getId());
			stuFeedbackInstructionsForm.setDescription(feedbackInstructions.getDescription());
		}
	}
	/**
	 * @param stuFeedbackInstructionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm) throws Exception{
		boolean isDeleted = transaction.deleteInstructions(stuFeedbackInstructionsForm);
		return isDeleted;
	}
}
