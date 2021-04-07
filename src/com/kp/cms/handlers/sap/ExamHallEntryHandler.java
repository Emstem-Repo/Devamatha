package com.kp.cms.handlers.sap;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.sap.ExamRegistrationDetails;
import com.kp.cms.forms.sap.ExamHallEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.transactions.sap.ISapVenueTransactions;
import com.kp.cms.transactionsimpl.sap.SapVenueTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


public class ExamHallEntryHandler {
	ISapVenueTransactions transactions=SapVenueTransactionImpl.getInstance();
private static volatile ExamHallEntryHandler examHallEntryHandler = null;
	/*
	 * instance method by giri
	 */
	public static ExamHallEntryHandler getInsatnce() {
		if(examHallEntryHandler == null) {
			examHallEntryHandler = new ExamHallEntryHandler();
		}
		return examHallEntryHandler;
	}
	/*
	 * to get student details
	 */
	public void getStudentDetails(String regNo, HttpServletRequest request) throws Exception{
		ExamRegistrationDetails examRegistrationDetails=transactions.getstudentDetails(regNo);
		if(examRegistrationDetails!=null){
			String name="";
			if(examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null){
				name=name+examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getFirstName();
			}
			if(examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMiddleName()!=null){
				name=name+examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMiddleName();
			}
			if(examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getLastName()!=null){
				name=name+examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getLastName();
			}
			request.setAttribute("name", name);
			if(examRegistrationDetails.getStudentId().getAdmAppln().getCourse().getName()!=null){
				request.setAttribute("clas", examRegistrationDetails.getStudentId().getAdmAppln().getCourse().getName());
			}
			if(CommonUtil.formatDates(examRegistrationDetails.getExamScheduleDateId().getExamDate())!=null){
				request.setAttribute("date", CommonUtil.formatDates(examRegistrationDetails.getExamScheduleDateId().getExamDate()));
			}
			if(examRegistrationDetails.getExamScheduleDateId().getSession()!=null){
				request.setAttribute("session",examRegistrationDetails.getExamScheduleDateId().getSession());
			}
			if(examRegistrationDetails.getSapVenueId().getVenueName()!=null){
				request.setAttribute("venue", examRegistrationDetails.getSapVenueId().getVenueName());
			}
		}else{
			request.setAttribute("msg", "Register Number is not valid or not registered for SAP");
		}
		
	}
	public boolean updateExamDetails(ExamHallEntryForm examHallEntryForm) throws Exception{
		boolean present=false;
		if(examHallEntryForm.getPresent()!=null && examHallEntryForm.getPresent().equalsIgnoreCase("on")){
			present=true;
		}
		boolean flag=transactions.updateExamDetails(examHallEntryForm.getRegNo(),present);
		return flag;
	}
	public void getstudentData(ExamHallEntryForm examHallEntryForm) throws Exception{
		ExamRegistrationDetails examRegistrationDetails=transactions.getstudentDetails(examHallEntryForm.getRegNo());
		if(examRegistrationDetails!=null){
			String name="";
			if(examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null){
				name=name+examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getFirstName();
			}
			if(examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMiddleName()!=null){
				name=name+examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMiddleName();
			}
			if(examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getLastName()!=null){
				name=name+examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getLastName();
			}
			examHallEntryForm.setName(name);
			if(examRegistrationDetails.getStudentId().getAdmAppln().getCourse().getName()!=null){
				examHallEntryForm.setClas(examRegistrationDetails.getStudentId().getAdmAppln().getCourse().getName());
			}
			if(CommonUtil.formatDates(examRegistrationDetails.getExamScheduleDateId().getExamDate())!=null){
				examHallEntryForm.setDate(CommonUtil.formatDates(examRegistrationDetails.getExamScheduleDateId().getExamDate()));
			}
			if(examRegistrationDetails.getExamScheduleDateId().getSession()!=null){
				examHallEntryForm.setSession(examRegistrationDetails.getExamScheduleDateId().getSession());
			}
			if(examRegistrationDetails.getSapVenueId().getVenueName()!=null){
				examHallEntryForm.setVenue(examRegistrationDetails.getSapVenueId().getVenueName());
			}
		}
	}
}
