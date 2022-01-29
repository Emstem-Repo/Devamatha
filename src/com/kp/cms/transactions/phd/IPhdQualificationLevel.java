package com.kp.cms.transactions.phd;

import java.util.List;

import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.phd.PhdQualificationLevelBo;
import com.kp.cms.forms.phd.PhdQualificationLevelForm;

public interface IPhdQualificationLevel {
	public List<PhdQualificationLevelBo> getQualificationLevel() throws Exception;
	public PhdQualificationLevelBo isDuplicated(PhdQualificationLevelForm qualificationLevelForm) throws Exception;
	public PhdQualificationLevelBo isDuplicateDisplayOrder(PhdQualificationLevelForm qualificationLevelForm)throws Exception;
	public PhdQualificationLevelBo isReactivate(PhdQualificationLevelForm qualificationLevelForm)throws Exception;
	public boolean addQualificationLevel(PhdQualificationLevelBo qualificationLevelBO, String mode)throws Exception;
	public boolean deleteQualificationLevel(int id, boolean activate,PhdQualificationLevelForm qualificationLevelForm) throws Exception;
	public PhdQualificationLevelBo editQualificationLevel(int id)throws Exception;
}
