package com.kp.cms.actions.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.fee.FeeHeadingsAction;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.studentfeedback.ExitEvaluationQuestion;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.fee.FeeHeadingsForm;
import com.kp.cms.forms.studentfeedback.ExitEvaluationQuestionForm;
import com.kp.cms.handlers.fee.FeeGroupHandler;
import com.kp.cms.handlers.fee.FeeHeadingsHandler;
import com.kp.cms.handlers.studentfeedback.ExitEvaluationQuestionHandler;
import com.kp.cms.handlers.studentfeedback.ExitEvaluationTopicHandler;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.fee.FeeHeadingTO;
import com.kp.cms.to.studentfeedback.ExitEvaluationQuestionTo;
import com.kp.cms.to.studentfeedback.ExitEvaluationTopicTo;

public class ExitEvaluationQuestionAction extends BaseDispatchAction {
	
	private static final Logger log=Logger.getLogger(ExitEvaluationQuestionAction.class);
	
	public ActionForward initExitEvalQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of initExitEvalQues method in ExitEvaluationQuestionAction class.");
		ExitEvaluationQuestionForm admForm=(ExitEvaluationQuestionForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = admForm.validate(mapping, request);
	    if(errors.isEmpty()){
	    	//errors are empty and loading the fee heading details to form.
	    	getExitEvalQuesDetails(admForm);
	    }else{
	    	//errors are present and displays error messages.
	    	errors.add(messages);
			saveErrors(request, errors);
	    }
		
		log.info("end of initExitEvalQues method in ExitEvaluationQuestionAction class.");
		return mapping.findForward(CMSConstants.EXIT_EVAL_QUES);
	}
	
	public ActionForward addExitEvalQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of addFeeHeadings method in FeeHeadingsAction class.");
		ExitEvaluationQuestionForm admForm=(ExitEvaluationQuestionForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = admForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request, admForm); //setting of user id to form.
	    		int topicId=Integer.parseInt(admForm.getExitEvalTopic());
	    		String question=admForm.getQuestion();
	    		int exitEvalTopicId = Integer.valueOf(admForm.getExitEvalTopic());
	    		ExitEvaluationQuestion exitEvalQues=ExitEvaluationQuestionHandler.getInstance().isExitEvalQuesExist(topicId, question);
	    		if(exitEvalQues!=null && exitEvalQues.getIsActive() && exitEvalQues.getQuestion().equals(question)){
					errors.add(CMSConstants.EXIT_EVAL_QUES_NAME_EXIST,new ActionError(CMSConstants.EXIT_EVAL_QUES_NAME_EXIST));
					saveErrors(request, errors);
				}else if(exitEvalQues!=null && !exitEvalQues.getIsActive()){
					errors.add(CMSConstants.EXIT_EVAL_QUES_REACTIVATE,new ActionError(CMSConstants.EXIT_EVAL_QUES_REACTIVATE));
					saveErrors(request, errors);
				}
				else{
	    		boolean isadded=ExitEvaluationQuestionHandler.getInstance().addExitEvalQues(admForm);
				if(isadded) {
					//if adding is success.
					ActionMessage message = new ActionMessage(CMSConstants.EXIT_EVAL_QUES_ADDSUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					admForm.clearAll();
					admForm.reset(mapping, request);
				}if(!isadded) {
					//if adding is failure.
					ActionMessage message = new ActionMessage(CMSConstants.EXIT_EVAL_QUES_ADDFAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					admForm.clearAll();
					admForm.reset(mapping, request);
				}


    			}//end of if
	    	}
	    		else{
	    			errors.add(messages);
	    			saveErrors(request, errors);
	    		}
	    }catch (BusinessException businessException) {
			log.info("Exception addFeeHeadings");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    
		getExitEvalQuesDetails(admForm);
			
		log.info("end of addFeeHeadings method in FeeHeadingsAction class.");
		return mapping.findForward(CMSConstants.EXIT_EVAL_QUES);
	}
	
	public ActionForward editExitEvalQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("call of editFeeHeadings method in FeeHeadingsAction class.");
		ExitEvaluationQuestionForm admForm=(ExitEvaluationQuestionForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = admForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		ExitEvaluationQuestionTo exitEvalQuesTo =ExitEvaluationQuestionHandler.getInstance().editExitEvalQues(admForm.getExitEvalQuesId());
	    		admForm.setQuestion(exitEvalQuesTo.getQuestion());
	    		admForm.setExitEvalTopic(String.valueOf(exitEvalQuesTo.getExitEvalTopicTo().getId()));
	    		request.setAttribute("exitEvalQuesOperation","edit");
	    		HttpSession session=request.getSession(false);
	    		if(session == null){
	    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
	    		}else{
		    		session.setAttribute("question",admForm.getQuestion());
		    		session.setAttribute("topicid",admForm.getExitEvalTopic());
	    		}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception editFeeHeadings");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		getExitEvalQuesDetails(admForm);
			
		log.info("end of editFeeHeadings method in FeeHeadingsAction class.");
		return mapping.findForward(CMSConstants.EXIT_EVAL_QUES);
	}
	
	public ActionForward updateExitEvalQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of updateFeeHeadings method in FeeHeadingsAction class.");
		ExitEvaluationQuestionForm admForm=(ExitEvaluationQuestionForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = admForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request, admForm);
	    		HttpSession session=request.getSession(false);
	    		int topicId=Integer.parseInt(admForm.getExitEvalTopic());
	    		String question=admForm.getQuestion();
	    		String fname = session.getAttribute("question").toString();
	    		String fgroup = session.getAttribute("topicid").toString();
	    		if(!fname.equals(fname) && !fgroup.equals(admForm.getExitEvalTopic())){
	    			ExitEvaluationQuestion exitEvalQues=ExitEvaluationQuestionHandler.getInstance().isExitEvalQuesExist(topicId, question);
				if(exitEvalQues!=null && exitEvalQues.getIsActive() && exitEvalQues.getQuestion().equals(question)){
					errors.add(CMSConstants.EXIT_EVAL_QUES_NAME_EXIST,new ActionError(CMSConstants.EXIT_EVAL_QUES_NAME_EXIST));
					saveErrors(request, errors);
				}else if(exitEvalQues!=null && !exitEvalQues.getIsActive()){
					errors.add(CMSConstants.EXIT_EVAL_QUES_REACTIVATE,new ActionError(CMSConstants.EXIT_EVAL_QUES_REACTIVATE));
					saveErrors(request, errors);
				}else{
	    		boolean isUpdated =ExitEvaluationQuestionHandler.getInstance().updateExitEvalQues(admForm);
	    		if(isUpdated){
	    			//if update is success.
	    			session.removeAttribute("question");
	    			session.removeAttribute("topicid");
	    			ActionMessage message = new ActionMessage(CMSConstants.EXIT_EVAL_QUES_UPDATESUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					admForm.clearAll();
					admForm.reset(mapping, request);
	    		}if(!isUpdated){
	    			//if update is failure.
	    			ActionMessage message = new ActionMessage(CMSConstants.EXIT_EVAL_QUES_UPDATEFAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					admForm.clearAll();
					admForm.reset(mapping, request);
	    		}
	    		}
	    		}else{
	    			boolean isUpdated =ExitEvaluationQuestionHandler.getInstance().updateExitEvalQues(admForm);
		    		if(isUpdated){
		    			//if update is success.
		    			session.removeAttribute("question");
		    			session.removeAttribute("topicid");
		    			ActionMessage message = new ActionMessage(CMSConstants.EXIT_EVAL_QUES_UPDATESUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						admForm.clearAll();
						admForm.reset(mapping, request);
		    		}if(!isUpdated){
		    			//if update is failure.
		    			ActionMessage message = new ActionMessage(CMSConstants.EXIT_EVAL_QUES_UPDATEFAILURE);
						messages.add("messages", message);
						saveMessages(request, messages);
						admForm.clearAll();
						admForm.reset(mapping, request);
		    		}
	    		}
	    		}
	    		else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception updateFeeHeadings");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    // call of getFeeHeadingDetails method and setting to form.
		getExitEvalQuesDetails(admForm);
			
		log.info("end of updateFeeHeadings method in FeeHeadingsAction class.");
		return mapping.findForward(CMSConstants.EXIT_EVAL_QUES);
	}
	
	public ActionForward deleteExitEvalQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of deleteFeeHeadings method in FeeHeadingsAction class.");
		ExitEvaluationQuestionForm admForm=(ExitEvaluationQuestionForm)form;
		ActionErrors errors = new ActionErrors();
	    ActionMessages messages = new ActionMessages();
	    errors = admForm.validate(mapping, request);
	    try{
	    	if(errors.isEmpty()){
	    		//errors are empty
	    		setUserId(request, admForm);
	    		boolean isDelete=ExitEvaluationQuestionHandler.getInstance().deleteExitEvalQues(admForm.getExitEvalQuesId(), admForm.getUserId());
				if(isDelete){
					//if delete is success.
					ActionMessage message = new ActionMessage(CMSConstants.EXIT_EVAL_QUES_DELETESUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					admForm.clearAll();
					admForm.reset(mapping, request);
				}if(!isDelete){	
					// if delete is failure.
					ActionMessage message = new ActionMessage(CMSConstants.EXIT_EVAL_QUES_DELETEFAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					admForm.clearAll();
					admForm.reset(mapping, request);
				}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception deleteFeeHeadings");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	    // 	call of getFeeHeadingDetails method and setting to form.
		getExitEvalQuesDetails(admForm);
			
		log.info("end of deleteFeeHeadings method in FeeHeadingsAction class.");
		return mapping.findForward(CMSConstants.EXIT_EVAL_QUES);
	}
	
	public void getExitEvalQuesDetails(ExitEvaluationQuestionForm admForm) throws Exception {
		log.info("call of getFeeHeadingDetails method in FeeHeadingsAction class.");
		//getting the data of fee group and setting to list of type FeeGroupTO.
		List<ExitEvaluationTopicTo> exitEvalTopicToList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
		admForm.setExitEvalTopicList(exitEvalTopicToList);
		//getting the data of fee heading and setting to list of type FeeHeadingTO.
		List<ExitEvaluationQuestionTo> exitEvalQuesToList = ExitEvaluationQuestionHandler.getInstance().getExitEvalQuesDetails();
		admForm.setExitEvalQuesToList(exitEvalQuesToList);
		log.info("end of getFeeHeadingDetails method in FeeHeadingsAction class.");
	}

}
