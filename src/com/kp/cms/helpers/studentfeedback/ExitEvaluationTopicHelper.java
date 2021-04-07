package com.kp.cms.helpers.studentfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.bo.studentfeedback.ExitEvaluationTopic;
import com.kp.cms.forms.admin.EducationStreamForm;
import com.kp.cms.forms.studentfeedback.ExitEvaluationTopicForm;
import com.kp.cms.to.admin.EducationStreamTO;
import com.kp.cms.to.studentfeedback.ExitEvaluationTopicTo;

public class ExitEvaluationTopicHelper {
	
	public static List<ExitEvaluationTopicTo> convertBOsToTos(List<ExitEvaluationTopic> exitEvalTopicList)
	{
		List<ExitEvaluationTopicTo> exitEvalTopicToList = new ArrayList<ExitEvaluationTopicTo>();
		if (exitEvalTopicList != null)
		{
			Iterator<ExitEvaluationTopic> iterator = exitEvalTopicList.iterator();
			while (iterator.hasNext())
			{
				ExitEvaluationTopic exitEvalTopic = (ExitEvaluationTopic) iterator.next();
				ExitEvaluationTopicTo exitEvalTopicTo = new ExitEvaluationTopicTo();
				exitEvalTopicTo.setId(exitEvalTopic.getId());
				exitEvalTopicTo.setName(exitEvalTopic.getName());
				exitEvalTopicToList.add(exitEvalTopicTo);
			}
		}
		return exitEvalTopicToList;
	}
	
	public static ExitEvaluationTopic convertTOtoBO(ExitEvaluationTopicForm exitEvalTopicForm,String mode) throws Exception
	{
		ExitEvaluationTopic exitEvalTopic=new ExitEvaluationTopic();
		if(mode.equals("Update"))
		{
			exitEvalTopic.setId(exitEvalTopicForm.getExitEvalTopicId());
			exitEvalTopic.setModifiedBy(exitEvalTopicForm.getUserId());
			exitEvalTopic.setLastModifiedDate(new Date());
		}
		else if(mode.equals("Add"))
		{
			exitEvalTopic.setModifiedBy(exitEvalTopicForm.getUserId());
			exitEvalTopic.setLastModifiedDate(new Date());
			exitEvalTopic.setCreatedBy(exitEvalTopicForm.getUserId());
			exitEvalTopic.setCreatedDate(new Date());
			//appFee.setIsActive(true);			
		}
		exitEvalTopic.setIsActive(true);
		exitEvalTopic.setName(exitEvalTopicForm.getTopicName());
		return exitEvalTopic;
	}

}
