package com.kp.cms.transactions.attandance;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;

public interface ICopyPeriodsTransaction {

	public List<ClassSchemewise> getClassesByYear(int fromYear)throws Exception;

	public Map<String, ClassSchemewise> getClassesByToYear(int toYear)throws Exception;

	public boolean savePeriods(List<Period> periods)throws Exception;

}
