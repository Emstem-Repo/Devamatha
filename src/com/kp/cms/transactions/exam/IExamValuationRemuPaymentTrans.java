package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.exam.ExamValuationRemuneration;
import com.kp.cms.forms.exam.ExamValuationRemuPaymentForm;
import com.kp.cms.to.employee.GuestFacultyTO;

public interface IExamValuationRemuPaymentTrans {

	List<Object[]> getListOfSearchedValuators(String query)throws Exception;

	boolean update(List<ExamValuationRemuneration> examValuationRemunerations)throws Exception;

	List<ExamValuationRemuneration> getExamValuationRemunirations(
			List<Integer> integers) throws Exception;

	GuestFaculty getGuestFaculty(String name) throws Exception;

	List<Object[]> getBoardMeetingCharges(ExamValuationRemuPaymentForm examValuationRemuPaymentForm) throws Exception ;

}
