package com.kp.cms.transactions.studentfeedback;

import java.util.List;

import com.kp.cms.bo.studentfeedback.EvaFacultyFeedBackGroup;
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackGroupForm;

public interface IEvaFacultyFeedBackGroupTransaction
{

    public abstract List<EvaFacultyFeedBackGroup> getFacultyFeedBackGroup()throws Exception;

    public abstract EvaFacultyFeedBackGroup isNameExist(String s, String s1, EvaFacultyFeedBackGroupForm evastudentfeedbackgroupform)throws Exception;

    public abstract boolean addFeedbackGroup(EvaFacultyFeedBackGroup evastudentfeedbackgroup)throws Exception;

    public abstract EvaFacultyFeedBackGroup editFeedBackGroup(int i)throws Exception;

    public abstract boolean updateFeedbackGroup(EvaFacultyFeedBackGroup evastudentfeedbackgroup)throws Exception;

    public abstract boolean deleteFeedBackGroup(int i, String s)throws Exception;

    public abstract boolean reActivateFeedBackGroup(String s, String s1, int i)throws Exception;
}
