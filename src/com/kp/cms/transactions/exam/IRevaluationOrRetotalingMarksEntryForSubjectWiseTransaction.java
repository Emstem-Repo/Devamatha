package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.forms.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;

public interface IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction {
	public List<Object> getStudentDetailsList(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form)throws Exception;
	public Double getMaxMarkOfSubject(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form)throws Exception;
	public boolean saveMarks(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception;
	public Double getMaxMarkOfSubjectValidation(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form,int courseId,int schemeNo)throws Exception;
	public boolean saveMarksThirdEvaluationMarks(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception;
	public boolean isStudentAppliedForThirdEvl(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form,int exanRevaluationAppId) throws Exception;
}
