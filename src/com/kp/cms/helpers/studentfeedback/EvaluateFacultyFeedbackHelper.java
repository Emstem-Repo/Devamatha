package com.kp.cms.helpers.studentfeedback;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackAverage;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackOverallAverage;
import com.kp.cms.forms.studentfeedback.EvaluateFacultyFeedbackForm;
import com.kp.cms.transactions.studentfeedback.IEvaluateFacultyFeedback;
import com.kp.cms.transactionsimpl.studentfeedback.EvaluateFacultyFeedbackImpl;

public class EvaluateFacultyFeedbackHelper {
	public static volatile EvaluateFacultyFeedbackHelper evaluateFacultyFeedbackHelper = null;
	public static EvaluateFacultyFeedbackHelper getInstance()
    {
        if(evaluateFacultyFeedbackHelper == null)
        {
        	evaluateFacultyFeedbackHelper = new EvaluateFacultyFeedbackHelper();
            return evaluateFacultyFeedbackHelper;
        } else
        {
            return evaluateFacultyFeedbackHelper;
        }
    }
}
