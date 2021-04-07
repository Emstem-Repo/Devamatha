package com.kp.cms.transactions.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidsemExemption;
import com.kp.cms.forms.exam.ExamMidsemExemptionForm;
import com.kp.cms.to.exam.KeyValueTO;

public interface IExamMidsemExemptionTransaction {
	
	public ArrayList<KeyValueTO> getExamByYear(int academicYear) throws Exception;
	
	public ArrayList<KeyValueTO> getclassByRegNo(int regNo) throws Exception;
	
	public ExamDefinitionBO getExamName(ExamMidsemExemptionForm exemptionForm) throws Exception;

	Student getStudentBO(ExamMidsemExemptionForm exemptionForm) throws Exception;
	
	boolean save(ExamMidsemExemption exemptionBO)throws Exception;
	
	public ExamMidsemExemption getPreviousSelected(ExamMidsemExemptionForm exemptionForm, int studentId) throws Exception;
	
	public ArrayList<KeyValueTO> getExamByYearOnly(int academicYear) throws Exception;
	
}
