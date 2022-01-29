package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationProcess;
import com.kp.cms.bo.exam.ExamValuationRemuneration;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.bo.exam.ValuatorMeetingChargesBo;
import com.kp.cms.forms.exam.ExamValuationStatusForm;
import com.kp.cms.forms.exam.ValuationChallanForm;


public interface IValuationChallanTransaction {


	public Map<Integer, String> getEmployeeMap(HttpSession s) throws Exception;

	public List<ExamValidationDetails> getValuatorDetails(ValuationChallanForm valuationChallanForm) throws Exception;

	public boolean saveDetails(ExamValuationRemuneration remuneration, ValuationChallanForm valuationChallanForm) throws Exception;

	public Integer getSubjectGroupForSubject(int id) throws Exception;

	public ValuatorChargesBo getValuatorChargerPerScript(int id) throws Exception;

	public ValuatorMeetingChargesBo getValuatorMeetingCharges() throws Exception;

	public ExamValuationRemuneration getRemunerationDetails(ValuationChallanForm valuationChallanForm) throws Exception;

	public boolean saveChallanDetails(ValuationChallanForm valuationChallanForm) throws Exception;

	public ExamValuators getExamValuator(String otherEmpId) throws Exception;

	public Users getUser(int userId) throws Exception;
	
	public boolean getGuestId(String userId)throws Exception;
	
	public List<Integer> getSemisternumbers(int examId)throws Exception;
	
	public String getExamTypeByExamId(int examId)throws Exception;

}
