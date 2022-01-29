package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admission.AdmSelectionSettings;
import com.kp.cms.forms.admission.AdmSelectionProcessCJCForm;

public interface IAdmSelectProcessCJCTransaction {
	
	public Map<String, String> getInstutionMap() throws Exception;
	
	public Map<String, String> getCasteMap() throws Exception;
	
	public Map<String, String> getReligionMap() throws Exception;
	
	public Map<String, String> getCourseMap() throws Exception;
	
	public Map<String, String> getUniversityMap() throws Exception;
	
	public List<AdmAppln> getpreviousData(AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception;

	public boolean deleteAllData(List<AdmAppln> previousData, AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception;

	public List<AdmAppln> getRunSetDataToTable(AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception;

	public boolean saveData(List<AdmAppln> allData,AdmSelectionProcessCJCForm admSelProcessCJCForm,int interPgmCourse) throws Exception;
	
	public List<AdmSelectionSettings> getAdmSelectionSettings(AdmSelectionProcessCJCForm admSelProcessCJCForm)throws Exception;

	public List<Integer> getCourseIdsFromAdmSettings(AdmSelectionProcessCJCForm admSelProcessCJCForm)throws Exception;
	
	public List<InterviewProgramCourse> getInterviewPrgCourse(AdmSelectionProcessCJCForm admForm)throws Exception;

	public int getInterviewProgCourse(AdmSelectionProcessCJCForm admSelProcessCJCForm)throws Exception;
		
	}
