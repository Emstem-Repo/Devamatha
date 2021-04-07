package com.kp.cms.transactions.teacherfeedback;

import java.util.List;

import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackGroup;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedBackGroupForm;

public interface IEvaTeacherFeedBackGroupTransaction {

    public abstract List<EvaTeacherFeedbackGroup> getTeacherFeedBackGroup()throws Exception;

    public abstract EvaTeacherFeedbackGroup isNameExist(String s, String s1, EvaTeacherFeedBackGroupForm evaTeacherFeedBackGroupForm)throws Exception;

    public abstract boolean addFeedbackGroup(EvaTeacherFeedbackGroup evaTeacherFeedBackGroupForm)throws Exception;

    public abstract EvaTeacherFeedbackGroup editFeedBackGroup(int i)throws Exception;

    public abstract List<EvaTeacherFeedbackGroup> getStudentFeedBackGroup()throws Exception;

    public abstract boolean updateFeedbackGroup(EvaTeacherFeedbackGroup evaTeacherfeedbackgroup)throws Exception;

    public abstract boolean deleteFeedBackGroup(int i, String s)throws Exception;

    public abstract boolean reActivateFeedBackGroup(String s, String s1, int i)throws Exception;

}
