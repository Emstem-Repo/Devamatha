package com.kp.cms.handlers.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.kp.cms.bo.studentfeedback.ExitEvaluationTopic;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.studentfeedback.ExitEvaluationTopicForm;
import com.kp.cms.handlers.admin.EducationStreamHandler;
import com.kp.cms.helpers.studentfeedback.ExitEvaluationTopicHelper;
import com.kp.cms.to.studentfeedback.ExitEvaluationTopicTo;
import com.kp.cms.transactions.studentfeedback.IExitEvaluationTopicTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.ExitEvaluationTopicTransactionImpl;

public class ExitEvaluationTopicHandler {
	
	private static volatile ExitEvaluationTopicHandler exitEvalTopicHandler = null;
	private ExitEvaluationTopicHandler()
	{
		
	}
	
	public static ExitEvaluationTopicHandler getInstance() 
	{
	
		if (exitEvalTopicHandler == null) {
			exitEvalTopicHandler = new ExitEvaluationTopicHandler();
		}
		return exitEvalTopicHandler;
	}
	
	public List<ExitEvaluationTopicTo> getExitEvalTopics()
	{
		IExitEvaluationTopicTransaction transaction=new ExitEvaluationTopicTransactionImpl();
		List<ExitEvaluationTopic> exitEvalTop=transaction.getExitEvalTopics();
		List<ExitEvaluationTopicTo> exitEvalTopicList = ExitEvaluationTopicHelper.convertBOsToTos(exitEvalTop);
		return exitEvalTopicList;
	}
	
	public boolean addExitEvalTopic(ExitEvaluationTopicForm exitEvalTopicForm,HttpServletRequest request) throws Exception
	{
		IExitEvaluationTopicTransaction transaction=new ExitEvaluationTopicTransactionImpl();
		ExitEvaluationTopic exitEvalTopic=ExitEvaluationTopicHelper.convertTOtoBO(exitEvalTopicForm,"Add");
		ExitEvaluationTopic exitEvalTopic1=null;
		exitEvalTopic1=transaction.isExitEvalTopicDuplicated(exitEvalTopic);
		boolean isExitEvalTopicAdded=false;
		if(exitEvalTopic1!=null && exitEvalTopic1.getIsActive()==true)
		{
			throw new DuplicateException();
		}
		else if(exitEvalTopic1!=null && exitEvalTopic1.getIsActive()==false)
		{
			request.getSession().setAttribute("exitEvalTopic", exitEvalTopic1);
			exitEvalTopicForm.setReactivateid(exitEvalTopic.getId());
			throw new ReActivateException();
			
		}
		else if(transaction!=null)
		{
			isExitEvalTopicAdded=transaction.addExitEvalTopic(exitEvalTopic);
		}
		return isExitEvalTopicAdded;
		
	}
	
	public boolean updateExitEvalTopic(ExitEvaluationTopicForm exitEvalTopicForm,HttpServletRequest request) throws Exception
	{
		IExitEvaluationTopicTransaction transaction=new ExitEvaluationTopicTransactionImpl();
		boolean isExitEvalTopicEdited=false;
		ExitEvaluationTopic exitEvalTopic=ExitEvaluationTopicHelper.convertTOtoBO(exitEvalTopicForm,"Update");
		ExitEvaluationTopic exitEvalTopic1=ExitEvaluationTopicHelper.convertTOtoBO(exitEvalTopicForm,"Update");
		//EducationStream educstream2=educstreamImpl.getEducStreamList(educstreamForm.getEducstreamId());
		exitEvalTopic=transaction.isExitEvalTopicDuplicated(exitEvalTopic);
		if(!exitEvalTopicForm.getTopicName().equals(exitEvalTopicForm.getOrgName()))
		{
			if(exitEvalTopic!=null && exitEvalTopic.getIsActive())
			{
			throw new DuplicateException();
			}
			else if(exitEvalTopic!=null && !exitEvalTopic.getIsActive())
			{
				request.getSession().setAttribute("exitEvalTopic", exitEvalTopic);
				exitEvalTopicForm.setReactivateid(exitEvalTopic.getId());
				throw new ReActivateException();
			}
			else if(transaction!=null)
			{
				isExitEvalTopicEdited=transaction.updateExitEvalTopic(exitEvalTopic1);
			}
		}
		return isExitEvalTopicEdited;
		
	}
	
	public boolean deleteExitEvalTopic(int exitEvalTopicId,String userId) throws Exception
	{
		IExitEvaluationTopicTransaction transaction=new ExitEvaluationTopicTransactionImpl();
		boolean isExitEvalTopicDeleted=false;
		if(transaction!=null)
		{
			isExitEvalTopicDeleted=transaction.deleteExitEvalTopic(exitEvalTopicId,userId);
		}
		return isExitEvalTopicDeleted;
	}
	
	public boolean reActivateExitEvalTopic(ExitEvaluationTopic exitEvalTopic,String userId)throws Exception
	{
		IExitEvaluationTopicTransaction transaction=new ExitEvaluationTopicTransactionImpl();
		boolean isReActivateExitEvalTopic=transaction.reActivateExitEvalTopic(exitEvalTopic,userId);
		return isReActivateExitEvalTopic;
	}
	

}
