package com.kp.cms.handlers.teacherfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackGroup;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedBackGroupForm;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackGroupTo;
import com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedBackGroupTransaction;
import com.kp.cms.transactionsimpl.teacherfeedback.EvaTeacherFeedBackGroupImpl;

public class EvaTeacherFeedBackGroupHandler {
	private static final Log log = LogFactory.getLog(EvaTeacherFeedBackGroupHandler.class);
	public static volatile EvaTeacherFeedBackGroupHandler evaTeacherFeedBackGroupHandler = null;

	public static EvaTeacherFeedBackGroupHandler getInstance()
	{
		if(evaTeacherFeedBackGroupHandler == null)
		{
			evaTeacherFeedBackGroupHandler = new EvaTeacherFeedBackGroupHandler();
			return evaTeacherFeedBackGroupHandler;
		} else
		{
			return evaTeacherFeedBackGroupHandler;
		}
	}
	IEvaTeacherFeedBackGroupTransaction transaction= EvaTeacherFeedBackGroupImpl.getInstance();


	public List<EvaTeacherFeedBackGroupTo> getTeacherFeedBackEntry() throws Exception
	{
		log.info("call of getTeacherFeedBackEntry method in EvaTeacherFeedBackGroupHandler class.");
		List<EvaTeacherFeedBackGroupTo> studentList = new ArrayList<EvaTeacherFeedBackGroupTo>();
		List<EvaTeacherFeedbackGroup> list = transaction.getTeacherFeedBackGroup();
		EvaTeacherFeedBackGroupTo teacherFeedBackGroupTo;
		EvaTeacherFeedbackGroup teacherFeedbackGroup;
		Iterator<EvaTeacherFeedbackGroup> itr = list.iterator();
		while (itr.hasNext()) {
			teacherFeedbackGroup = (EvaTeacherFeedbackGroup)itr.next();
			teacherFeedBackGroupTo = new EvaTeacherFeedBackGroupTo();
			teacherFeedBackGroupTo.setId(teacherFeedbackGroup.getId());
			teacherFeedBackGroupTo.setName(teacherFeedbackGroup.getName());
			teacherFeedBackGroupTo.setDisOrder(String.valueOf(teacherFeedbackGroup.getDisOrder()));
			studentList.add(teacherFeedBackGroupTo);
		}
		log.info("end of getTeacherFeedBackEntry method in EvaTeacherFeedBackGroupHandler class.");
		return studentList;
	}


	public EvaTeacherFeedbackGroup isNameExist(String name, String order, EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm)
	throws Exception{
		log.info("call of isNameExist method in EvaTeacherFeedBackGroupHandler class.");
		EvaTeacherFeedbackGroup FeedbackGroup = transaction.isNameExist(name, order, teacherFeedBackGroupForm);
		log.info("end of isNameExist method in EvaTeacherFeedBackGroupHandler class.");
		return FeedbackGroup;
	}


	public boolean addFeedBackGroup(EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm)
	throws Exception {
		log.info("call of addFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		EvaTeacherFeedbackGroup FeedbackGroup = new EvaTeacherFeedbackGroup();
		FeedbackGroup.setDisOrder(Integer.parseInt(teacherFeedBackGroupForm.getDisOrder()));
		FeedbackGroup.setName(teacherFeedBackGroupForm.getName());
		FeedbackGroup.setCreatedBy(teacherFeedBackGroupForm.getUserId());
		FeedbackGroup.setCreatedDate(new Date());
		FeedbackGroup.setModifiedBy(teacherFeedBackGroupForm.getUserId());
		FeedbackGroup.setLastModifiedDate(new Date());
		FeedbackGroup.setIsActive(Boolean.TRUE);
		boolean isAdded = transaction.addFeedbackGroup(FeedbackGroup);
		log.info("end of addFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		return isAdded;
	}

	public EvaTeacherFeedBackGroupTo editFeedBackGroup(int id)
	throws Exception
	{
		log.info("call of editFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		EvaTeacherFeedBackGroupTo evaTeacherFeedBackGroupTo = new EvaTeacherFeedBackGroupTo();
		EvaTeacherFeedbackGroup FeedbackGroup = transaction.editFeedBackGroup(id);
		evaTeacherFeedBackGroupTo.setId(FeedbackGroup.getId());
		evaTeacherFeedBackGroupTo.setName(FeedbackGroup.getName());
		if(FeedbackGroup.getDisOrder() != null)
		{
			evaTeacherFeedBackGroupTo.setDisOrder(String.valueOf(FeedbackGroup.getDisOrder()));
		}
		log.info("end of editFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		return evaTeacherFeedBackGroupTo;
	}

	public boolean updateTeacherFeedBackGroup(EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm)
	throws Exception
	{
		log.info("call of updateTeacherFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		EvaTeacherFeedbackGroup teacherFeedbackGroup = new EvaTeacherFeedbackGroup();
		teacherFeedbackGroup.setId(teacherFeedBackGroupForm.getId());
		teacherFeedbackGroup.setName(teacherFeedBackGroupForm.getName());
		teacherFeedbackGroup.setDisOrder(Integer.parseInt(teacherFeedBackGroupForm.getDisOrder()));
		teacherFeedbackGroup.setModifiedBy(teacherFeedBackGroupForm.getUserId());
		teacherFeedbackGroup.setLastModifiedDate(new Date());
		teacherFeedbackGroup.setIsActive(Boolean.TRUE);
		boolean isUpdated = transaction.updateFeedbackGroup(teacherFeedbackGroup);
		log.info("end of updateTeacherFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		return isUpdated;
	}

	public boolean deleteFeedBackGroup(int id, String userId)
	throws Exception
	{
		log.info("call of deleteFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		boolean isDeleted = transaction.deleteFeedBackGroup(id, userId);
		log.info("end of deleteFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		return isDeleted;
	}

	public boolean reActivateFeedBackGroup(String name, String userId, int id)
	throws Exception
	{
		log.info("call of reActivateFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		boolean isReactivated = transaction.reActivateFeedBackGroup(name, userId, id);
		log.info("end of reActivateFeedBackGroup method in EvaTeacherFeedBackGroupHandler class.");
		return isReactivated;
	}
}
