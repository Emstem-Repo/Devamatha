package com.kp.cms.transactions.sap;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.bo.sap.ExamRegistrationDetails;
import com.kp.cms.bo.sap.ExamRegistrationFeeAmount;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.forms.sap.ExamRegistrationDetailsForm;

public interface IExamRegistrationDetailsTransaction {

	public Map<Integer,String> getWorkLocationDetailsList(ExamRegistrationDetailsForm objForm)throws Exception;

	public List<Object[]> getDateSessionDetailsOfWorkLocation(int workLocationId)throws Exception;

	public List<Object[]> getPreviousDetailsOfWorkLocation( int workLocationId)throws Exception;

	public List<ExamScheduleVenue> getVenueDetails(int workLocationId, int examSessionId)throws Exception;

	public  Map<Integer, Integer> getAllotedVenuesForSession( int workLocationId, int examSessionId)throws Exception;

//	public SapVenue getWorkLocationAndVenueByIds(int venueId)throws Exception;

	public int saveRegistrationDetails( ExamRegistrationDetails registrationDetails)throws Exception;

	public int saveOnlinePaymentReciepts( OnlinePaymentReciepts onlinePaymentReciepts)throws Exception;

	public void updateAndGenerateRecieptNoOnlinePaymentReciept( OnlinePaymentReciepts paymentReciepts)throws Exception;

	public Double getFeeAmount(String examType)throws Exception;

	public ExamRegistrationDetails getCheckIsAlreadyRegisteredForExam( int studentId)throws Exception;

	public ExamRegistrationDetails checkIsAlreadyCancelledRegistration( int studentId)throws Exception;

	public int getReceiptNumber(ExamRegistrationDetailsForm objForm)throws Exception;

	public int getSAPMarksOfStudent(String query)throws Exception;

}
