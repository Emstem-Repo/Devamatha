package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.InternalMarkSupplementaryDetails;
import com.kp.cms.forms.exam.NewInternalMarksSupplementaryForm;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;

public interface INewInternalMarksSupplementaryTransaction {

	boolean checkValidStudentRegNo(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception;

	List getDataForQuery(String intQuery) throws Exception;

	boolean saveInternalMarkSupplementaryDetails(List<InternalMarkSupplementaryDetails> bos, String userId) throws Exception;

	List<Object[]> getMaxMarkOfSubject(ExamInternalMarksSupplementaryTO to, NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception;

}
