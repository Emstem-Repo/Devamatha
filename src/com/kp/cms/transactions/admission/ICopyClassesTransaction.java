package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;

public interface ICopyClassesTransaction {

	public List<Classes> getClassesByYear(int year) throws Exception;
	public Map<String,Integer> getClassesByToYear(int toYear) throws Exception;
	public boolean saveClasses(List<Classes> classesBO) throws Exception;

}
