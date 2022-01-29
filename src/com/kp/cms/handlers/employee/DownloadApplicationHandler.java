package com.kp.cms.handlers.employee;

import java.util.*;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmpOnlineResumeUsers;
import com.kp.cms.forms.employee.DownloadApplicationForm;
import com.kp.cms.helpers.employee.DownloadApplicationHelper;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;
import com.kp.cms.to.admin.EmpOnlineEducationalDetailsTO;
import com.kp.cms.to.admin.EmpOnlinePreviousExperienceTO;
import com.kp.cms.transactions.employee.IDownloadApplicationTransaction;
import com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction;
import com.kp.cms.transactionsimpl.employee.DownloadApplicationTxnImpl;
import com.kp.cms.transactionsimpl.employee.DownloadEmployeeResumeTransactionImpl;

public class DownloadApplicationHandler {
	private static final Log log = LogFactory.getLog(DownloadApplicationHandler.class);
	public static volatile DownloadApplicationHandler handler = null;
	public static DownloadApplicationHandler getInstance(){
		if(handler == null){
			handler = new DownloadApplicationHandler();
			return handler;
		}
		return handler;
	}
	IDownloadApplicationTransaction transaction = DownloadApplicationTxnImpl.getInstance();
	/**
	 * @param downloadApplicationForm
	 * @throws Exception
	 */
	public void getEmployeeDetailsTOForm( DownloadApplicationForm downloadApplicationForm) throws Exception{
		List<Department> departmentList = transaction.getDepartmentList();
		//List<Designation> designationList = transaction.getDesignationList();
		List<EmpQualificationLevel> qualificationList = transaction.getEmpQualificationList();
		IDownloadEmployeeResumeTransaction downloadTransaction = DownloadEmployeeResumeTransactionImpl.getInstance();
		Map<Integer,String> empSubMap= downloadTransaction.getEmployeeSubjects();
		downloadApplicationForm.setDepartmentList(departmentList);
		//downloadApplicationForm.setDesignationList(designationList);
		downloadApplicationForm.setQualificationList(qualificationList);
		downloadApplicationForm.setEmpSubjects(empSubMap);
	}
	/**
	 * @param downloadApplicationForm
	 * @return
	 * @throws Exception
	 */
	public List<DownloadEmployeeResumeTO> getEmpDetails( DownloadApplicationForm downloadApplicationForm) throws Exception{
		List<EmpOnlineResumeUsers> empOnlineResumesUsers = transaction.getEmployeeDetails(downloadApplicationForm);
		List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs = DownloadApplicationHelper.getInstance().convertBoToTo(empOnlineResumesUsers);
		return downloadEmployeeResumeTOs;
	}
	/**
	 * @param downloadApplicationForm
	 * @param request 
	 * @param session 
	 * @throws Exception
	 */
	public void getResumeDetails(DownloadApplicationForm downloadApplicationForm, HttpSession session, HttpServletRequest request)throws Exception {
		String empId = downloadApplicationForm.getEmployeeId();
		EmpOnlineResume empOnlineResume = transaction.getDetailsForEmployee(empId);
		List<EmpOnlineEducationalDetails> eduDetails = transaction.getEmployeeEducationDetails(empId);
		List<EmpOnlinePreviousExperience> teachingExperience = transaction.getEmployeeExperienceDetails(empId);
		 List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs = DownloadApplicationHelper.getInstance().convertBoToToForPrint(empOnlineResume, teachingExperience, downloadApplicationForm,session);
		downloadApplicationForm.setTos(downloadEmployeeResumeTOs);
		 List<EmpOnlineEducationalDetailsTO> empEduDetails = DownloadApplicationHelper.getInstance().convertBoToToForEduDetails(eduDetails,downloadApplicationForm);
		List<EmpOnlinePreviousExperienceTO> tos = DownloadApplicationHelper.getInstance().convertBoToToForExpDetails(teachingExperience,empOnlineResume,downloadApplicationForm);
		downloadApplicationForm.setTeachingExperience(tos);
		downloadApplicationForm.setEmpEducationalDetails(empEduDetails);
		
	}
}
