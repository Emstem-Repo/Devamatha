package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.bo.admin.OnlineExamSupplementaryApplication;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.forms.admin.OnlineExamSuppApplicationForm;

public interface IOnlineExamSuppApplicationTransaction {
	
	void updateAndGenerateRecieptNoOnlinePaymentReciept(OnlinePaymentReciepts onlinePaymentReciepts) throws Exception;
	boolean saveCertificate( List<OnlineExamSupplementaryApplication> boList) throws Exception;
	public void getStudentId(int userId,OnlineExamSuppApplicationForm crForm) throws Exception;
	public boolean CheckStudentAlreadyApplied(OnlineExamSuppApplicationForm crForm) throws Exception;
	public int GetOnlineExamSuppCount(String year, String venue, String Date)throws Exception;
	public OnlineExamSupplementaryApplication getOnlineExamSupplementaryApplication (OnlinePaymentReciepts os)throws Exception;
	ConvocationSession getConvocationDetails(String courseId) throws Exception;
	boolean checkMaxGuestAvailable(int sessionId, int maxGuest, OnlineExamSuppApplicationForm crForm) throws Exception;
}
