package com.kp.cms.transactions.exam;

import com.kp.cms.forms.exam.NewSecuredMarksVerficationForm;

public interface INewSecuredMarksVerficationTransaction {

	Double getMaxMarkOfSubject(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm) throws Exception;

	boolean saveMarks(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm) throws Exception;

}
