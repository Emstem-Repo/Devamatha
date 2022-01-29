package com.kp.cms.transactions.exam;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidSemRepeatExemption;
import com.kp.cms.bo.exam.ExamMidsemExemption;
import com.kp.cms.bo.exam.ExamMidsemExemptionDetails;
import com.kp.cms.bo.exam.ExamMidsemRepeat;
import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.forms.admin.RepeatMidSemAppForm;
import com.kp.cms.forms.exam.ExamMidsemRepeatForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.to.exam.KeyValueTO;

public interface IExamMidsemRepeatTransaction {
	
	ExamMidsemRepeat getDataForStudentloginForm = null;

	public ArrayList<KeyValueTO> getExamMidsemByYear(int year) throws Exception;
	
	public ArrayList<KeyValueTO> getExamByYear(int academicYear) throws Exception;
	
	public List<Object[]> getRunSetDataToTable(ExamMidsemRepeatForm exemptionForm) throws Exception;
	
	public boolean saveData(List<ExamMidsemRepeat> allData) throws Exception;
	
	public ExamDefinitionBO getExamName(ExamMidsemRepeatForm exemptionForm) throws Exception;

	public List<ExamMidsemRepeat> getpreviousData(ExamMidsemRepeatForm exemptionForm) throws Exception;

	public boolean deleteAllData(List<ExamMidsemRepeat> previousData, ExamMidsemRepeatForm exemptionForm) throws Exception;

	public ExamMidsemRepeat getDataForForm(LoginForm loginForm) throws Exception;

	public boolean updateOneData(ExamMidsemRepeat oneData)  throws Exception;

	public List<ExamMidsemRepeat> getCoeDataForForm(ExamMidsemRepeatForm loginForm) throws Exception;

	public ExamMidsemRepeat getApprovedDataForForm(ExamMidsemRepeatForm loginForm) throws Exception;

	public ExamMidsemRepeatSetting getValidExamId(LoginForm loginForm) throws Exception;

	public String checkForRepeatApplicationExam(int id, Integer examId, LoginForm loginForm) throws Exception;

	public String checkForRepeatFeesPaymentExam(int id, Integer examId, LoginForm loginForm) throws Exception;

	public ExamMidsemRepeatSetting getValidExamIdForFees(LoginForm loginForm) throws Exception;

	public int saveOnlinePaymentBo(OnlinePaymentReciepts onlinePaymentReciepts) throws Exception;

	public void updateAndGenerateRecieptNoOnlinePaymentReciept( OnlinePaymentReciepts paymentReciepts)throws Exception;

	public ExamMidsemRepeatSetting getValidExamIdForFeesPayment(LoginForm loginForm) throws Exception;

	public ExamMidsemRepeatSetting ReminderMailForFeePayment() throws Exception;

	public float getClassHeld(String midSemStudentId, int midSemClassId, int subId) throws Exception;

	public float getClassPresent(String midSemStudentId, int midSemClassId, int subId) throws Exception;

	List<ExamMidsemRepeat> repeatMidSemExamReminder(Integer id)throws Exception;
	
	public List<Object[]> getClassHeldandPresent(String midSemStudentId, int midSemClassId,List<Integer> subjectIdList)throws Exception;

	public Object[] getAggregatePercentage(String midSemStudentId, int midSemClassId)throws Exception;
	
	public Object[] getNoOfAttempts(String courseId, int studentId)throws Exception;
	
	public int getAttemptsByCourseId(String courseId)throws Exception;
	
	public boolean getStudentDataForExempt(RepeatMidSemAppForm repeatMidSemAppForm) throws Exception;
	
	public boolean updateRepeatMidExemption(ExamMidSemRepeatExemption oneData)  throws Exception;
	
	public boolean getStudentAlreadyExempted(RepeatMidSemAppForm repeatMidSemAppForm) throws Exception;
	
	public boolean getStudentAlreadyExempted(LoginForm loginForm) throws Exception;
	
	public List<ExamMidsemExemption> getStudentExemptedOrNot(LoginForm loginForm) throws Exception;

	public String checkForRepeatHallticket(int id, LoginForm loginForm) throws Exception;
	
	public ExamMidsemRepeatSetting GetExamRepeatSettings(ExamMidsemRepeatForm loginForm) throws Exception;
	
	public Object[] getNoOfAttemptsCOE(String registerNo)throws Exception;
	
	public int getAttemptsByRegisterNo(String registerNo)throws Exception;

}
