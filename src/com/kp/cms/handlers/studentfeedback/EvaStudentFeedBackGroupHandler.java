package com.kp.cms.handlers.studentfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackGroup;
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackGroupForm;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;
import com.kp.cms.transactions.studentfeedback.IEvaStudentFeedBackGroupTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.EvaStudentFeedBackGroupImpl;

public class EvaStudentFeedBackGroupHandler
{
	private static final Log log = LogFactory.getLog(EvaStudentFeedBackGroupHandler.class);
	public static volatile EvaStudentFeedBackGroupHandler evaStudentFeedBackGroupHandler = null;

    public static EvaStudentFeedBackGroupHandler getInstance()
    {
        if(evaStudentFeedBackGroupHandler == null)
        {
            evaStudentFeedBackGroupHandler = new EvaStudentFeedBackGroupHandler();
            return evaStudentFeedBackGroupHandler;
        } else
        {
            return evaStudentFeedBackGroupHandler;
        }
    }
    IEvaStudentFeedBackGroupTransaction transaction= EvaStudentFeedBackGroupImpl.getInstance();
    
     public List<EvaStudentFeedBackGroupTo> getStudentFeedBackEntry() throws Exception
    {
        log.info("call of getStudentFeedBackEntry method in EvaStudentFeedBackGroupHandler class.");
        List<EvaStudentFeedBackGroupTo> studentList = new ArrayList<EvaStudentFeedBackGroupTo>();
        List<EvaStudentFeedbackGroup> list = transaction.getStudentFeedBackGroup();
        EvaStudentFeedBackGroupTo studentFeedBackGroupTo;
        EvaStudentFeedbackGroup studentFeedbackGroup;
    	Iterator<EvaStudentFeedbackGroup> itr = list.iterator();
    	while (itr.hasNext()) {
    		studentFeedbackGroup = (EvaStudentFeedbackGroup)itr.next();
            studentFeedBackGroupTo = new EvaStudentFeedBackGroupTo();
            studentFeedBackGroupTo.setId(studentFeedbackGroup.getId());
            studentFeedBackGroupTo.setName(studentFeedbackGroup.getName());
            studentFeedBackGroupTo.setDisOrder(String.valueOf(studentFeedbackGroup.getDisOrder()));
            studentList.add(studentFeedBackGroupTo);
        }
        log.info("end of getStudentFeedBackEntry method in EvaStudentFeedBackGroupHandler class.");
        return studentList;
    }

    public EvaStudentFeedbackGroup isNameExist(String name, String order, EvaStudentFeedBackGroupForm studentFeedBackGroupForm)
        throws Exception{
        log.info("call of isNameExist method in EvaStudentFeedBackGroupHandler class.");
        EvaStudentFeedbackGroup FeedbackGroup = transaction.isNameExist(name, order, studentFeedBackGroupForm);
        log.info("end of isNameExist method in EvaStudentFeedBackGroupHandler class.");
        return FeedbackGroup;
    }

    public boolean addFeedBackGroup(EvaStudentFeedBackGroupForm studentFeedBackGroupForm)
        throws Exception {
        log.info("call of addDesignationEntry method in EvaStudentFeedBackGroupHandler class.");
        EvaStudentFeedbackGroup FeedbackGroup = new EvaStudentFeedbackGroup();
        FeedbackGroup.setDisOrder(Integer.parseInt(studentFeedBackGroupForm.getDisOrder()));
        FeedbackGroup.setName(studentFeedBackGroupForm.getName());
        FeedbackGroup.setCreatedBy(studentFeedBackGroupForm.getUserId());
        FeedbackGroup.setCreatedDate(new Date());
        FeedbackGroup.setModifiedBy(studentFeedBackGroupForm.getUserId());
        FeedbackGroup.setLastModifiedDate(new Date());
        FeedbackGroup.setIsActive(Boolean.TRUE);
        boolean isAdded = transaction.addFeedbackGroup(FeedbackGroup);
        log.info("end of addFeedBackGroup method in EvaStudentFeedBackGroupHandler class.");
        return isAdded;
    }

    public EvaStudentFeedBackGroupTo editFeedBackGroup(int id)
        throws Exception
    {
        log.info("call of editFeedBackGroup method in EvaStudentFeedBackGroupHandler class.");
        EvaStudentFeedBackGroupTo evaStudentFeedBackGroupTo = new EvaStudentFeedBackGroupTo();
        EvaStudentFeedbackGroup FeedbackGroup = transaction.editFeedBackGroup(id);
        evaStudentFeedBackGroupTo.setId(FeedbackGroup.getId());
        evaStudentFeedBackGroupTo.setName(FeedbackGroup.getName());
        if(FeedbackGroup.getDisOrder() != null)
        {
            evaStudentFeedBackGroupTo.setDisOrder(String.valueOf(FeedbackGroup.getDisOrder()));
        }
        log.info("end of editFeedBackGroup method in EvaStudentFeedBackGroupHandler class.");
        return evaStudentFeedBackGroupTo;
    }

    public boolean updateStudentFeedBackGroup(EvaStudentFeedBackGroupForm studentFeedBackGroupForm)
        throws Exception
    {
        log.info("call of updateStudentFeedBackGroup method in EvaStudentFeedBackGroupHandler clas" +
"s."
);
        EvaStudentFeedbackGroup studentFeedbackGroup = new EvaStudentFeedbackGroup();
        studentFeedbackGroup.setId(studentFeedBackGroupForm.getId());
        studentFeedbackGroup.setName(studentFeedBackGroupForm.getName());
        studentFeedbackGroup.setDisOrder(Integer.parseInt(studentFeedBackGroupForm.getDisOrder()));
        studentFeedbackGroup.setModifiedBy(studentFeedBackGroupForm.getUserId());
        studentFeedbackGroup.setLastModifiedDate(new Date());
        studentFeedbackGroup.setIsActive(Boolean.TRUE);
        boolean isUpdated = transaction.updateFeedbackGroup(studentFeedbackGroup);
        log.info("end of updateStudentFeedBackGroup method in EvaStudentFeedBackGroupHandler class" +
"."
);
        return isUpdated;
    }

    public boolean deleteFeedBackGroup(int id, String userId)
        throws Exception
    {
        log.info("call of deleteFeedBackGroup method in EvaStudentFeedBackGroupHandler class.");
        boolean isDeleted = transaction.deleteFeedBackGroup(id, userId);
        log.info("end of deleteFeedBackGroup method in EvaStudentFeedBackGroupHandler class.");
        return isDeleted;
    }

    public boolean reActivateFeedBackGroup(String name, String userId, int id)
        throws Exception
    {
        log.info("call of reActivateFeedBackGroup method in EvaStudentFeedBackGroupHandler class.");
        boolean isReactivated = transaction.reActivateFeedBackGroup(name, userId, id);
        log.info("end of reActivateFeedBackGroup method in EvaStudentFeedBackGroupHandler class.");
        return isReactivated;
    }

	}
