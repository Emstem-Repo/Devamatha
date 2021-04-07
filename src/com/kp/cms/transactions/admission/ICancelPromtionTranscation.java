package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.forms.admission.CancelPromotionForm;
import com.kp.cms.to.admission.CancelPromotionTo;


public interface ICancelPromtionTranscation {

	public Student getSearchCancelPromotionDetails( CancelPromotionForm cancelPromotionForm) throws Exception;

	public ExamStudentPreviousClassDetailsBO getPreviousClassDetails(int stuId, int semNo)throws Exception;

	public ClassSchemewise getClassSchemeWiseDetails( CancelPromotionTo promotionTo)throws Exception;

	public boolean saveClassSchemeWise(CancelPromotionTo promotionTo, int stuId)throws Exception;

	public List<ExamStudentSubGrpHistoryBO> getSubjectGroupHistory(int stuId, int semNo)throws Exception;

	public void deleteApplicantSubjectGroup(int admappln)throws Exception;

	public boolean saveAppSubGrp(List<CancelPromotionTo> promotionToList, int admappln)throws Exception;

	public void deletePreviousSubGrp(List<CancelPromotionTo> promotionToList)throws Exception;

	public void deletePreviousClasses(CancelPromotionTo canProTo)throws Exception;

	

	
}
