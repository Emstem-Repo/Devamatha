package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.QualificationLevelForm;



public interface IQualificationLevelTransaction {

	public List<QualificationLevelBO> getQualificationLevel() throws Exception;

	public QualificationLevelBO isDuplicated(QualificationLevelForm qualificationLevelForm) throws Exception;

	public boolean addQualificationLevel(QualificationLevelBO qualificationLevelBO, String mode)throws Exception;

	public boolean deleteQualificationLevel(int id, boolean activate,QualificationLevelForm qualificationLevelForm) throws Exception;

	public QualificationLevelBO editQualificationLevel(int id)throws Exception;

	public QualificationLevelBO isDuplicateDisplayOrder(QualificationLevelForm qualificationLevelForm)throws Exception;

	public QualificationLevelBO isReactivate(QualificationLevelForm qualificationLevelForm)throws Exception;

	

	

}
