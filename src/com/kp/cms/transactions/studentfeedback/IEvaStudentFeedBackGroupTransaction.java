package com.kp.cms.transactions.studentfeedback;

import java.util.List;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackGroup;
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackGroupForm;

public interface IEvaStudentFeedBackGroupTransaction
{

    public abstract List<EvaStudentFeedbackGroup> getStudentFeedBackGroup()throws Exception;

    public abstract EvaStudentFeedbackGroup isNameExist(String s, String s1, EvaStudentFeedBackGroupForm evastudentfeedbackgroupform)throws Exception;

    public abstract boolean addFeedbackGroup(EvaStudentFeedbackGroup evastudentfeedbackgroup)throws Exception;

    public abstract EvaStudentFeedbackGroup editFeedBackGroup(int i)throws Exception;

    public abstract boolean updateFeedbackGroup(EvaStudentFeedbackGroup evastudentfeedbackgroup)throws Exception;

    public abstract boolean deleteFeedBackGroup(int i, String s)throws Exception;

    public abstract boolean reActivateFeedBackGroup(String s, String s1, int i)throws Exception;
}
