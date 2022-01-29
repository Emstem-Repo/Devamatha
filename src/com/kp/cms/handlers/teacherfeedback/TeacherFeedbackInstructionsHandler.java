package com.kp.cms.handlers.teacherfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.teacherfeedback.TeacherFeedbackInstructions;
import com.kp.cms.forms.teacherfeedback.TeacherFeedbackInstructionsForm;
import com.kp.cms.to.teacherfeedback.TeacherFeedbackInstructionsTO;
import com.kp.cms.transactions.teacherfeedback.ITeacherFeedbackInstructionsTransaction;
import com.kp.cms.transactionsimpl.teacherfeedback.TeacherFeedbackInstructionsTrasImpl;

public class TeacherFeedbackInstructionsHandler {
	private static final Logger log = Logger.getLogger(TeacherFeedbackInstructionsHandler.class);
	public static volatile TeacherFeedbackInstructionsHandler feedbackInstructionsHandler = null;
	public static TeacherFeedbackInstructionsHandler getInstance(){
		if(feedbackInstructionsHandler ==null){
			feedbackInstructionsHandler = new TeacherFeedbackInstructionsHandler();
			return feedbackInstructionsHandler;
		}
		return feedbackInstructionsHandler;
	}
	ITeacherFeedbackInstructionsTransaction transaction = TeacherFeedbackInstructionsTrasImpl.getInstance();
	/**
	 * @param teacherFeedbackInstructionsForm
	 * @return
	 */
	public List<TeacherFeedbackInstructionsTO> getInstructions() throws Exception{
		List<TeacherFeedbackInstructionsTO> instructionsTOList = new ArrayList<TeacherFeedbackInstructionsTO>();
		List<TeacherFeedbackInstructions> instructions = transaction.getInstructions();
		TeacherFeedbackInstructionsTO to = null;
		if(instructions!=null){
			Iterator<TeacherFeedbackInstructions> iterator = instructions.iterator();
			while (iterator.hasNext()) {
				TeacherFeedbackInstructions studentFeedbackInstructions = (TeacherFeedbackInstructions) iterator .next();
				to = new TeacherFeedbackInstructionsTO();
				to.setId(studentFeedbackInstructions.getId());
				to.setDescription(studentFeedbackInstructions.getDescription());
				instructionsTOList.add(to);
			}
		}
		return instructionsTOList;
	}
	/**
	 * @param teacherFeedbackInstructionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicateInstructions( TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm)throws Exception {
		boolean isExist = transaction.checkDuplicateInstructions(teacherFeedbackInstructionsForm);
		return isExist;
	}
	/**
	 * @param teacherFeedbackInstructionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean addTeacherFeedbackInstructions( TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm ,String mode)throws Exception {
		TeacherFeedbackInstructions feedbackInstructions = null;
		if(teacherFeedbackInstructionsForm.getDescription()!=null && !teacherFeedbackInstructionsForm.getDescription().isEmpty()){
			if(mode.equalsIgnoreCase("add")){
				feedbackInstructions = new TeacherFeedbackInstructions();
				feedbackInstructions.setDescription(teacherFeedbackInstructionsForm.getDescription());
				feedbackInstructions.setCreatedBy(teacherFeedbackInstructionsForm.getUserId());
				feedbackInstructions.setCreatedDate(new Date());
			}else if(mode.equalsIgnoreCase("edit")){
				feedbackInstructions = new TeacherFeedbackInstructions();
				feedbackInstructions.setId(teacherFeedbackInstructionsForm.getId());
				feedbackInstructions.setDescription(teacherFeedbackInstructionsForm.getDescription());
				feedbackInstructions.setModifiedBy(teacherFeedbackInstructionsForm.getUserId());
				feedbackInstructions.setLastModifiedDate(new Date());
			}
		}
		boolean isAdded = transaction.addTeacherFeedbackInstructions(feedbackInstructions,mode);
		return isAdded;
	}
	/**
	 * @param teacherFeedbackInstructionsForm
	 */
	public void editFeedbackInstructions( TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm) throws Exception{
		TeacherFeedbackInstructions feedbackInstructions = transaction.editFeedbackInstructions(teacherFeedbackInstructionsForm);
		if(feedbackInstructions!=null && !feedbackInstructions.toString().isEmpty()){
			teacherFeedbackInstructionsForm.setId(feedbackInstructions.getId());
			teacherFeedbackInstructionsForm.setDescription(feedbackInstructions.getDescription());
		}
	}
	/**
	 * @param teacherFeedbackInstructionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInstructions( TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm) throws Exception{
		boolean isDeleted = transaction.deleteInstructions(teacherFeedbackInstructionsForm);
		return isDeleted;
	}
	
}
