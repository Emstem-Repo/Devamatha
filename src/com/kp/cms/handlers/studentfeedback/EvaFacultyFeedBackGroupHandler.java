package com.kp.cms.handlers.studentfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.studentfeedback.EvaFacultyFeedBackGroup;
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackGroupForm;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackGroupTo;
import com.kp.cms.transactions.studentfeedback.IEvaFacultyFeedBackGroupTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.EvaFacultyFeedBackGroupImpl;

public class EvaFacultyFeedBackGroupHandler
{
	private static final Log log = LogFactory.getLog(EvaFacultyFeedBackGroupHandler.class);
	public static volatile EvaFacultyFeedBackGroupHandler evaFacultyFeedBackGroupHandler = null;

    public static EvaFacultyFeedBackGroupHandler getInstance()
    {
        if(evaFacultyFeedBackGroupHandler == null)
        {
            evaFacultyFeedBackGroupHandler = new EvaFacultyFeedBackGroupHandler();
            return evaFacultyFeedBackGroupHandler;
        } else
        {
            return evaFacultyFeedBackGroupHandler;
        }
    }
    IEvaFacultyFeedBackGroupTransaction transaction= EvaFacultyFeedBackGroupImpl.getInstance();
    
     public List<EvaFacultyFeedBackGroupTo> getFacultyFeedBackEntry() throws Exception
    {
        log.info("call of getFacultyFeedBackEntry method in EvaFacultyFeedBackGroupHandler class.");
        List<EvaFacultyFeedBackGroupTo> facultyList = new ArrayList<EvaFacultyFeedBackGroupTo>();
        List<EvaFacultyFeedBackGroup> list = transaction.getFacultyFeedBackGroup();
        EvaFacultyFeedBackGroupTo facultyFeedBackGroupTo;
        EvaFacultyFeedBackGroup facultyFeedbackGroup;
    	Iterator<EvaFacultyFeedBackGroup> itr = list.iterator();
    	while (itr.hasNext()) {
    		facultyFeedbackGroup = (EvaFacultyFeedBackGroup)itr.next();
            facultyFeedBackGroupTo = new EvaFacultyFeedBackGroupTo();
            facultyFeedBackGroupTo.setId(facultyFeedbackGroup.getId());
            facultyFeedBackGroupTo.setName(facultyFeedbackGroup.getName());
            facultyFeedBackGroupTo.setOrder(String.valueOf(facultyFeedbackGroup.getOrder()));
            facultyList.add(facultyFeedBackGroupTo);
        }
        log.info("end of getFacultyFeedBackEntry method in EvaFacultyFeedBackGroupHandler class.");
        return facultyList;
    }

    public EvaFacultyFeedBackGroup isNameExist(String name, String order, EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm)
        throws Exception{
        log.info("call of isNameExist method in EvaFacultyFeedBackGroupHandler class.");
        EvaFacultyFeedBackGroup FeedbackGroup = transaction.isNameExist(name, order, facultyFeedBackGroupForm);
        log.info("end of isNameExist method in EvaFacultyFeedBackGroupHandler class.");
        return FeedbackGroup;
    }

    public boolean addFeedBackGroup(EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm)
        throws Exception {
        log.info("call of addDesignationEntry method in EvaFacultyFeedBackGroupHandler class.");
        EvaFacultyFeedBackGroup FeedbackGroup = new EvaFacultyFeedBackGroup();
        FeedbackGroup.setOrder(Integer.parseInt(facultyFeedBackGroupForm.getOrder()));
        FeedbackGroup.setName(facultyFeedBackGroupForm.getName());
        FeedbackGroup.setCreatedBy(facultyFeedBackGroupForm.getUserId());
        FeedbackGroup.setCreatedDate(new Date());
        FeedbackGroup.setModifiedBy(facultyFeedBackGroupForm.getUserId());
        FeedbackGroup.setLastModifiedDate(new Date());
        FeedbackGroup.setIsActive(Boolean.TRUE);
        boolean isAdded = transaction.addFeedbackGroup(FeedbackGroup);
        log.info("end of addFeedBackGroup method in EvaFacultyFeedBackGroupHandler class.");
        return isAdded;
    }

    public EvaFacultyFeedBackGroupTo editFeedBackGroup(int id)
        throws Exception
    {
        log.info("call of editFeedBackGroup method in EvaFacultyFeedBackGroupHandler class.");
        EvaFacultyFeedBackGroupTo evaFacultyFeedBackGroupTo = new EvaFacultyFeedBackGroupTo();
        EvaFacultyFeedBackGroup FeedbackGroup = transaction.editFeedBackGroup(id);
        evaFacultyFeedBackGroupTo.setId(FeedbackGroup.getId());
        evaFacultyFeedBackGroupTo.setName(FeedbackGroup.getName());
        if(FeedbackGroup.getOrder() != null)
        {
            evaFacultyFeedBackGroupTo.setOrder(String.valueOf(FeedbackGroup.getOrder()));
        }
        log.info("end of editFeedBackGroup method in EvaFacultyFeedBackGroupHandler class.");
        return evaFacultyFeedBackGroupTo;
    }

    public boolean updateFacultyFeedBackGroup(EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm)
        throws Exception
    {
        log.info("call of updateFacultyFeedBackGroup method in EvaFacultyFeedBackGroupHandler clas" +
"s."
);
        EvaFacultyFeedBackGroup facultyFeedbackGroup = new EvaFacultyFeedBackGroup();
        facultyFeedbackGroup.setId(facultyFeedBackGroupForm.getId());
        facultyFeedbackGroup.setName(facultyFeedBackGroupForm.getName());
        facultyFeedbackGroup.setOrder(Integer.parseInt(facultyFeedBackGroupForm.getOrder()));
        facultyFeedbackGroup.setModifiedBy(facultyFeedBackGroupForm.getUserId());
        facultyFeedbackGroup.setLastModifiedDate(new Date());
        facultyFeedbackGroup.setIsActive(Boolean.TRUE);
        boolean isUpdated = transaction.updateFeedbackGroup(facultyFeedbackGroup);
        log.info("end of updateFacultyFeedBackGroup method in EvaFacultyFeedBackGroupHandler class" +
"."
);
        return isUpdated;
    }

    public boolean deleteFeedBackGroup(int id, String userId)
        throws Exception
    {
        log.info("call of deleteFeedBackGroup method in EvaFacultyFeedBackGroupHandler class.");
        boolean isDeleted = transaction.deleteFeedBackGroup(id, userId);
        log.info("end of deleteFeedBackGroup method in EvaFacultyFeedBackGroupHandler class.");
        return isDeleted;
    }

    public boolean reActivateFeedBackGroup(String name, String userId, int id)
        throws Exception
    {
        log.info("call of reActivateFeedBackGroup method in EvaFacultyFeedBackGroupHandler class.");
        boolean isReactivated = transaction.reActivateFeedBackGroup(name, userId, id);
        log.info("end of reActivateFeedBackGroup method in EvaFacultyFeedBackGroupHandler class.");
        return isReactivated;
    }

	}
