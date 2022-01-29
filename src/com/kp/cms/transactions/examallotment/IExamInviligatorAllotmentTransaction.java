package com.kp.cms.transactions.examallotment;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.examallotment.ExamInvigilationDutySettings;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.forms.examallotment.ExamInviligatorAllotmentForm;


public interface IExamInviligatorAllotmentTransaction {
	
	Map<String, String> getExamMap(ExamInviligatorAllotmentForm InvForm)throws Exception;

	Map<String, String> getWorkLocationMap()throws Exception;
	
	List<Object[]> getTeacherList(ExamInviligatorAllotmentForm InvForm ,String query)throws Exception;
	
	List<Object[]> getStudentList(ExamInviligatorAllotmentForm InvForm,String query) throws Exception;
	
	List<Object[]> getPmSessionList(ExamInviligatorAllotmentForm InvForm,String query)throws Exception;
	
	List<Object[]> getSessionList(ExamInviligatorAllotmentForm InvForm,StringBuffer query)throws Exception;
	
    ExamInvigilationDutySettings getSettingsList(ExamInviligatorAllotmentForm InvForm)throws Exception;
    
    String getExamType(ExamInviligatorAllotmentForm InvForm)throws Exception;
    
    boolean update(List<ExamInviligatorDuties> InvDutyList)throws Exception;
    
    void removeTeacherAllotment (ExamInviligatorAllotmentForm InvForm) throws Exception;
    
    List<ExamInvigilatorAvailable> setAvailableTeacher(StringBuilder query)throws Exception;
    
    List<ExamInviligatorExemptionDatewise> setExemptedTeacher(StringBuilder query)throws Exception;
    
    void removeTeacherAllotmentRollback(ExamInviligatorAllotmentForm InvForm) throws Exception;
    
    


}
