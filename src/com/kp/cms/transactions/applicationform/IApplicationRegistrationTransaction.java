package com.kp.cms.transactions.applicationform;

import java.util.List;

import org.apache.poi.hslf.record.ExVideoContainer;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.applicationform.ProfessorPGIDetails;
import com.kp.cms.bo.applicationform.ProfessorPersonalData;
import com.kp.cms.bo.applicationform.ProfessorQualificationBO;
import com.kp.cms.forms.applicationform.ApplicationRegistrationForm;

public interface IApplicationRegistrationTransaction {

	List<ProfessorQualificationBO> getDocumentList()throws Exception;

	boolean saveProfesserInformation(ProfessorPersonalData toQuaList) throws Exception;

	ProfessorPersonalData getApplicationDetails(ApplicationRegistrationForm registrationForm)throws Exception;

	String generateApplicantRefNoOnline(ProfessorPGIDetails bo,ApplicationRegistrationForm registrationForm)throws Exception;

	boolean updateReceivedStatusProfessort(ProfessorPGIDetails bo,ApplicationRegistrationForm registrationForm,ProfessorPersonalData personalData)throws Exception;

	Department getDepartmentNameThroughId(int id) throws Exception;

	Religion getReligionNameThroughId(int id)throws Exception;

	Caste getCasteNameThroughId(int id)throws Exception;

	ProfessorPersonalData getDetailsForApplicationPrint(ApplicationRegistrationForm registrationForm) throws Exception;

}
