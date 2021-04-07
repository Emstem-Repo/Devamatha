package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.forms.exam.PublishSupplementaryImpApplicationForm;

public interface IPublishSupplementaryImpApplicationTxn {
	
	List<StudentSupplementaryImprovementApplication> loadClassByExamNameAndYear(PublishSupplementaryImpApplicationForm actionForm) throws Exception;
    
	String isDuplicate(int id,int examId,int classId) throws Exception;
	
	List getClassNameAndYearByClassId(int id) throws Exception;
}
