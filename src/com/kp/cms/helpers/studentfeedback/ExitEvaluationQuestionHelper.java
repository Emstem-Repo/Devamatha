package com.kp.cms.helpers.studentfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.studentfeedback.ExitEvaluationQuestion;
import com.kp.cms.bo.studentfeedback.ExitEvaluationTopic;
import com.kp.cms.forms.fee.FeeHeadingsForm;
import com.kp.cms.forms.studentfeedback.ExitEvaluationQuestionForm;
import com.kp.cms.helpers.fee.FeeHeadingsHelper;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.fee.FeeHeadingTO;
import com.kp.cms.to.studentfeedback.ExitEvaluationQuestionTo;
import com.kp.cms.to.studentfeedback.ExitEvaluationTopicTo;

public class ExitEvaluationQuestionHelper {
	
	private static final Logger log=Logger.getLogger(ExitEvaluationQuestionHelper.class);
	private static ExitEvaluationQuestionHelper exitEvalHelper= null;
	public static ExitEvaluationQuestionHelper getInstance() {
	      if(exitEvalHelper == null) {
	    	  exitEvalHelper = new ExitEvaluationQuestionHelper();
	    	  return exitEvalHelper;
	      }
	      return exitEvalHelper;
	}
	
	public List<ExitEvaluationQuestionTo> convertBOstoTOs(List<ExitEvaluationQuestion> exitEvalQuestionList) throws Exception {
		log.info("call of convertBOstoTOs in ExitEvaluationQuestionHelper class.");
		List<ExitEvaluationQuestionTo> list=new ArrayList<ExitEvaluationQuestionTo>();
		ExitEvaluationQuestionTo exitEvalQuesTo;
		ExitEvaluationTopicTo exitEvalTopicTo;
		ExitEvaluationQuestion exitEvalQues;
			Iterator<ExitEvaluationQuestion> iterator = exitEvalQuestionList.iterator();
			while (iterator.hasNext()) {
				exitEvalQues = (ExitEvaluationQuestion) iterator.next();
				exitEvalQuesTo = new ExitEvaluationQuestionTo();
				exitEvalTopicTo = new ExitEvaluationTopicTo();
				exitEvalQuesTo.setId(exitEvalQues.getId());
				exitEvalQuesTo.setQuestion(exitEvalQues.getQuestion());
				exitEvalTopicTo.setId(exitEvalQues.getExitEvalTopic().getId());
				exitEvalTopicTo.setName(exitEvalQues.getExitEvalTopic().getName());
				exitEvalQuesTo.setExitEvalTopicTo(exitEvalTopicTo);
				exitEvalQuesTo.setOrder(String.valueOf(exitEvalQues.getOrder()));
				list.add(exitEvalQuesTo);
			}
			log.info("end of convertBOstoTOs in ExitEvaluationQuestionHelper class.");
		return list;
	}
	
	public ExitEvaluationQuestion convertTOstoBOs(ExitEvaluationQuestionForm exitEvalQuesForm) throws Exception {
		log.info("call of convertTOstoBOs in ExitEvaluationQuestionHelper class.");
		ExitEvaluationQuestion exitEvalQues = new ExitEvaluationQuestion();
		exitEvalQues.setCreatedDate(new Date());
		exitEvalQues.setCreatedBy(exitEvalQuesForm.getUserId());
		exitEvalQues.setModifiedBy(exitEvalQuesForm.getUserId());
		exitEvalQues.setIsActive(Boolean.TRUE);
		exitEvalQues.setQuestion(exitEvalQuesForm.getQuestion());
		ExitEvaluationTopic exitEvalTopic= new ExitEvaluationTopic();
		exitEvalTopic.setId(Integer.valueOf(exitEvalQuesForm.getExitEvalTopic()));
		
		exitEvalQues.setExitEvalTopic(exitEvalTopic);
		exitEvalQues.setOrder(Integer.parseInt(exitEvalQuesForm.getOrder()));
		log.info("end of convertTOstoBOs in FeeHeadingHelper class.");
		return exitEvalQues;
	}
	
	public ExitEvaluationQuestionTo convertBOstoTOsForEdit(List<ExitEvaluationQuestion> exitEvalQuesList) throws Exception {
		log.info("call of convertBOstoTOsForEdit in ExitEvaluationQuestionHelper class.");
		ExitEvaluationQuestionTo exitEvalQuesTo = null; 
		ExitEvaluationTopicTo exitEvalTopicTo;
		Iterator<ExitEvaluationQuestion> iterator = exitEvalQuesList.iterator();
		ExitEvaluationQuestion exitEvalQuesBo;
		while (iterator.hasNext()) {
			exitEvalQuesBo = (ExitEvaluationQuestion) iterator.next();
			exitEvalQuesTo = new ExitEvaluationQuestionTo();
			exitEvalQuesTo.setId(exitEvalQuesBo.getId());
			exitEvalQuesTo.setQuestion(exitEvalQuesBo.getQuestion());
			exitEvalTopicTo = new ExitEvaluationTopicTo();
			exitEvalTopicTo.setId(exitEvalQuesBo.getExitEvalTopic().getId());
			exitEvalTopicTo.setName(exitEvalQuesBo.getExitEvalTopic().getName());
			exitEvalQuesTo.setExitEvalTopicTo(exitEvalTopicTo);
			exitEvalQuesTo.setOrder(String.valueOf(exitEvalQuesBo.getOrder()));
		}
		log.info("end of convertBOstoTOsForEdit in ExitEvaluationQuestionHelper class.");
		return exitEvalQuesTo;
	}

}
