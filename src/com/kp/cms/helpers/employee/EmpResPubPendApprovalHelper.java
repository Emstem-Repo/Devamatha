package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.forms.employee.EmpResPubPendApprovalForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpArticlInPeriodicalsTO;
import com.kp.cms.to.employee.EmpArticleJournalsTO;
import com.kp.cms.to.employee.EmpAwardsAchievementsOthersTO;
import com.kp.cms.to.employee.EmpBlogTO;
import com.kp.cms.to.employee.EmpBooksMonographsTO;
import com.kp.cms.to.employee.EmpCasesNotesWorkingTO;
import com.kp.cms.to.employee.EmpChapterArticlBookTO;
import com.kp.cms.to.employee.EmpConferencePresentationTO;
import com.kp.cms.to.employee.EmpConferenceSeminarsAttendedTO;
import com.kp.cms.to.employee.EmpFilmVideosDocTO;
import com.kp.cms.to.employee.EmpInvitedTalkTO;
import com.kp.cms.to.employee.EmpOwnPhdMPilThesisTO;
import com.kp.cms.to.employee.EmpPhdMPhilThesisGuidedTO;
import com.kp.cms.to.employee.EmpResProjectTO;
import com.kp.cms.to.employee.EmpSeminarsOrganizedTO;
import com.kp.cms.to.employee.EmpWorkshopsFdpTrainingTO;
import com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction;
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.transactionsimpl.employee.EmpResPubPendApprovalImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoEditTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class EmpResPubPendApprovalHelper {
	
	
	private static volatile EmpResPubPendApprovalHelper instance = null;
	IEmpResPubPendApprovalTransaction empTransaction=new EmpResPubPendApprovalImpl();
	/**
		 * 
		 */
	private EmpResPubPendApprovalHelper() {	}

	/**
	 * @return
	 */
	public static EmpResPubPendApprovalHelper getInstance() {
		if (instance == null) {
			instance = new EmpResPubPendApprovalHelper();
		}
		return instance;
	}
	
	public List<EmployeeTO> convertEmployeeTOtoBO(List<Employee> employeelist) throws Exception {

		List<EmployeeTO> employeeTos = new ArrayList<EmployeeTO>();
		String name = "";
		if (employeelist != null) {
			Iterator<Employee> stItr = employeelist.iterator();
			while (stItr.hasNext()) {
				name = "";
				Employee emp = (Employee) stItr.next();
				EmployeeTO empTo = new EmployeeTO();
				if (emp.getId() > 0) {
					empTo.setId(emp.getId());
				}
				
				if (emp.getFingerPrintId() != null) {

					empTo.setDummyFingerPrintId(emp.getFingerPrintId());
				}
				if (emp.getFirstName() != null) {

					empTo.setDummyFirstName(emp.getFirstName());
				}
				if (emp.getDepartment()!= null && emp.getDepartment().getId()>0) {

					empTo.setDummyDepartmentName(emp.getDepartment().getName());
				}
				employeeTos.add(empTo);
			}
	}

		return employeeTos;
	}

	
	public List<EmployeeTO> convertEmployeeTOtoBO(List<Employee> employeelist,
			int departmentId) throws Exception {

		List<Department> departmentList = empTransaction.getEmployeeDepartment();

		List<EmployeeTO> employeeTos = new ArrayList<EmployeeTO>();
		String name = "";
		if (employeelist != null) {
			Iterator<Employee> stItr = employeelist.iterator();
			while (stItr.hasNext()) {
				name = "";
				Employee emp = (Employee) stItr.next();
				EmployeeTO empTo = new EmployeeTO();
				if (emp.getId() > 0) {
					empTo.setId(emp.getId());
				}
				if (emp.getFingerPrintId() != null) {

					empTo.setDummyFingerPrintId(emp.getFingerPrintId());
				}
				if (emp.getFirstName() != null) {

					empTo.setDummyFirstName(emp.getFirstName().toUpperCase());
				}

				
				if (emp.getDepartment() != null
						&& emp.getDepartment().getId() > 0) {
					int DepId = emp.getDepartment().getId();
					String DepName = null;
					if (departmentList != null) {
						Iterator<Department> depItr = departmentList.iterator();
						while (depItr.hasNext()) {
							Department dep = (Department) depItr.next();
							int depId = dep.getId();
							if (depId == DepId) {
								DepName = dep.getName();
							}
						}
					}
					empTo.setDummyDepartmentName(DepName);
				}
			
				employeeTos.add(empTo);
			}
		}
		return employeeTos;
	}
	
	
	public void convertBoToForm(List<EmpResearchPublicDetails> empApplicantDetails,
			EmpResPubPendApprovalForm  empResPubForm) throws Exception {
		
		if(empApplicantDetails!=null && !empApplicantDetails.isEmpty())
		{
			List<EmpArticlInPeriodicalsTO> empArticlInPeriodicalsTO = new ArrayList<EmpArticlInPeriodicalsTO>();
			List<EmpArticleJournalsTO> empArticleJournalsTO = new ArrayList<EmpArticleJournalsTO>();
			List<EmpBlogTO> empBlogTO= new ArrayList<EmpBlogTO>();
			List<EmpBooksMonographsTO> empBooksMonographsTO= new ArrayList<EmpBooksMonographsTO>();
			List<EmpCasesNotesWorkingTO> empCasesNotesWorkingTO= new ArrayList<EmpCasesNotesWorkingTO>();
			List<EmpChapterArticlBookTO> empChapterArticlBookTO= new ArrayList<EmpChapterArticlBookTO>();
			List<EmpConferencePresentationTO> empConferencePresentationTO= new ArrayList<EmpConferencePresentationTO>();
			List<EmpFilmVideosDocTO> empFilmVideosDocTO= new ArrayList<EmpFilmVideosDocTO>();
			List<EmpOwnPhdMPilThesisTO> empOwnPhdMPilThesisTO= new ArrayList<EmpOwnPhdMPilThesisTO>();
			List<EmpPhdMPhilThesisGuidedTO> empPhdMPhilThesisGuidedTO= new ArrayList<EmpPhdMPhilThesisGuidedTO>();
			List<EmpSeminarsOrganizedTO> empSeminarsOrganizedTO= new ArrayList<EmpSeminarsOrganizedTO>();
			List<EmpResProjectTO> empResProjectTO= new ArrayList<EmpResProjectTO>();
			List<EmpInvitedTalkTO> empInvitedTalkTO= new ArrayList<EmpInvitedTalkTO>();
			List<EmpConferenceSeminarsAttendedTO> empSeminarsAttendedTO= new ArrayList<EmpConferenceSeminarsAttendedTO>();
			List<EmpWorkshopsFdpTrainingTO> empWorkshopsTrainingTOs= new ArrayList<EmpWorkshopsFdpTrainingTO>();
			List<EmpAwardsAchievementsOthersTO> empAwardsAchievementsTOs= new ArrayList<EmpAwardsAchievementsOthersTO>();
			Iterator<EmpResearchPublicDetails> iterator = empApplicantDetails.iterator();
			while (iterator.hasNext()) {
				EmpResearchPublicDetails empAcheiv = iterator.next();
				
				if (empAcheiv != null) {
					
			
					if(empAcheiv.getIsArticleInPeriodicals()!=null && empAcheiv.getIsArticleInPeriodicals())
					{						
						EmpArticlInPeriodicalsTO empto= new EmpArticlInPeriodicalsTO();
						if (empAcheiv.getId() > 0) {
							empto.setId(empAcheiv.getId());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAuthorName())) {
							empto.setAuthorName(empAcheiv.getAuthorName());
						}
						if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
							empto.setTitle(empAcheiv.getTitle());
						}
						if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage())) {
							empto.setLanguage(empAcheiv.getLanguage());
						}
						if (empAcheiv.getDateMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getDateMonthYear().toString())) {
							empto.setDateMonthYear(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateMonthYear().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if (empAcheiv.getNamePeriodical()!=null && StringUtils.isNotEmpty(empAcheiv.getNamePeriodical())) {
							empto.setNamePeriodical(empAcheiv.getNamePeriodical());
						}
						if (empAcheiv.getVolumeNumber()!=null && StringUtils.isNotEmpty(empAcheiv.getVolumeNumber())) {
							empto.setVolumeNumber(empAcheiv.getVolumeNumber());
						}
						if (empAcheiv.getIssueNumber()!=null && StringUtils.isNotEmpty(empAcheiv.getIssueNumber())) {
							empto.setIssueNumber(empAcheiv.getIssueNumber());
						}
						if (empAcheiv.getPagesFrom()!=null && StringUtils.isNotEmpty(empAcheiv.getPagesFrom())) {
							empto.setPagesFrom(empAcheiv.getPagesFrom());
						}
						if (empAcheiv.getPagesTo()!=null && StringUtils.isNotEmpty(empAcheiv.getPagesTo()) ) {
							empto.setPagesTo(empAcheiv.getPagesTo());
						}
						if (empAcheiv.getIsbn()!=null && StringUtils.isNotEmpty(empAcheiv.getIsbn()) ) {
							empto.setIsbn(empAcheiv.getIsbn());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
							empto.setApproverComment(empAcheiv.getApproverComment());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
							empto.setAcademicYear(empAcheiv.getAcademicYear());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
							empto.setType(empAcheiv.getType());
							if(empAcheiv.getType().equalsIgnoreCase("other") &&(empAcheiv.getOtherText()!=null && !empAcheiv.getOtherText().isEmpty())){
								empto.setOtherTextArticle(empAcheiv.getOtherText());
							}
						}
												
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
							empto.setApprovedDate(empAcheiv.getApprovedDate());
						}
						if (empAcheiv.getApproverId()!=null)
							{
							if(empAcheiv.getApproverId().getId()> 0) {
							
							empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
						}
						}
						if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
						{
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsArticleInPeriodicals(empAcheiv.getIsArticleInPeriodicals());
						
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}else
							empto.setIsApproved(false);
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						else
							empto.setIsRejected(false);
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
							}
						}
						if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
							empto.setRejectReason(empAcheiv.getRejectReason());
						}
						if(empAcheiv.getCreatedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
							empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						empArticlInPeriodicalsTO.add(empto);
						
						if(empArticlInPeriodicalsTO==null || empArticlInPeriodicalsTO.isEmpty())
						{
							EmpArticlInPeriodicalsTO empPreviousOrgTo=new EmpArticlInPeriodicalsTO();
							empPreviousOrgTo.setTitle("");
							empPreviousOrgTo.setAuthorName("");
							empPreviousOrgTo.setTitle("");
							empPreviousOrgTo.setLanguage("");
							empPreviousOrgTo.setDateMonthYear("");
							empPreviousOrgTo.setNamePeriodical("");
							empPreviousOrgTo.setVolumeNumber("");
							empPreviousOrgTo.setIssueNumber("");
							empPreviousOrgTo.setPagesFrom("");
							empPreviousOrgTo.setPagesTo("");
							empPreviousOrgTo.setIsbn("");
							
							
							empArticlInPeriodicalsTO.add(empPreviousOrgTo);
						}
						
						empResPubForm.setEmpArticlInPeriodicalsTO(empArticlInPeriodicalsTO);
					}
					else if(empAcheiv.getIsArticleJournal()!=null && empAcheiv.getIsArticleJournal())
					{
							EmpArticleJournalsTO empto= new EmpArticleJournalsTO();
							if (empAcheiv.getId() > 0) {
								empto.setId(empAcheiv.getId());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getAuthorName())) {
								empto.setAuthorName(empAcheiv.getAuthorName());
							}

							if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
								empto.setTitle(empAcheiv.getTitle());
							}
							if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage()) ) {
								empto.setLanguage(empAcheiv.getLanguage());
							}
							if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
								empto.setMonthYear(empAcheiv.getMonthYear());
							}
							
							if (StringUtils.isNotEmpty(empAcheiv.getUrl())) {
								empto.setUrl(empAcheiv.getUrl());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getDepartmentInstitution())) {
								empto.setDepartmentInstitution(empAcheiv.getDepartmentInstitution());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getImpactFactor())) {
								empto.setImpactFactor(empAcheiv.getImpactFactor());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getCompanyInstitution())) {
								empto.setCompanyInstitution(empAcheiv.getCompanyInstitution());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getPlacePublication())) {
								empto.setPlacePublication(empAcheiv.getPlacePublication());
							}
							/*if (empAcheiv.getDateAccepted()!=null && StringUtils.isNotEmpty(empAcheiv.getDateAccepted().toString())) {
								empto.setDateAccepted(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateAccepted().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}*/
							if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())){
								empto.setDatePublished(empAcheiv.getMonthYear());
							}
							/*if (empAcheiv.getDateSent()!=null && StringUtils.isNotEmpty(empAcheiv.getDateSent().toString())){
								empto.setDateSent(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateSent().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}*/
							
							
							
							if (empAcheiv.getNameJournal()!=null && StringUtils.isNotEmpty(empAcheiv.getNameJournal()) ) {
							empto.setNameJournal(empAcheiv.getNameJournal());
							}
							if (empAcheiv.getVolumeNumber()!=null && StringUtils.isNotEmpty(empAcheiv.getVolumeNumber())) {
								empto.setVolumeNumber(empAcheiv.getVolumeNumber());
							}
							if (empAcheiv.getIssueNumber()!=null && StringUtils.isNotEmpty(empAcheiv.getIssueNumber())) {
								empto.setIssueNumber(empAcheiv.getIssueNumber());
							}
							if (empAcheiv.getPagesFrom()!=null && StringUtils.isNotEmpty(empAcheiv.getPagesFrom())) {
								empto.setPagesFrom(empAcheiv.getPagesFrom());
							}
							if (empAcheiv.getPagesTo()!=null && StringUtils.isNotEmpty(empAcheiv.getPagesTo())) {
								empto.setPagesTo(empAcheiv.getPagesTo());
							}
							if (empAcheiv.getIsbn()!=null && StringUtils.isNotEmpty(empAcheiv.getIsbn())) {
								empto.setIsbn(empAcheiv.getIsbn());
							}
							if (empAcheiv.getDoi()!=null && StringUtils.isNotEmpty(empAcheiv.getDoi())) {
								empto.setDoi(empAcheiv.getDoi());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
								empto.setApproverComment(empAcheiv.getApproverComment());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
								empto.setAcademicYear(empAcheiv.getAcademicYear());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
								empto.setType(empAcheiv.getType());
							}
							if(empAcheiv.getApprovedDate()!=null)
							{
							if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
								empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							}
							if(empAcheiv.getApprovedDate()!=null)
							{
								empto.setApprovedDate(empAcheiv.getApprovedDate());
							}
							if (empAcheiv.getApproverId()!=null)
								{
								if(empAcheiv.getApproverId().getId()> 0) {
								
								empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
							}
							}
							if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
							{
								empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
							}
							if (empAcheiv.getIsActive()!=null) {
								empto.setIsActive(empAcheiv.getIsActive());
							}
							empto.setIsArticleJournal(empAcheiv.getIsArticleJournal());
							if (empAcheiv.getIsApproved()!=null) {
								empto.setIsApproved(empAcheiv.getIsApproved());
							}
							else
								empto.setIsApproved(false);
								
							if(empAcheiv.getCreatedDate()!=null)
							{
							if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
								empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							}
							if(empAcheiv.getIsRejected()!=null){
								empto.setIsRejected(empAcheiv.getIsRejected());
							}
							else
								empto.setIsRejected(false);
							if(empAcheiv.getRejectDate()!=null){
								if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
									empto.setRejectDate(empAcheiv.getRejectDate());
								}
							}
							if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
								empto.setRejectReason(empAcheiv.getRejectReason());
							}
						empArticleJournalsTO.add(empto);
						
						if(empArticleJournalsTO==null || empArticleJournalsTO.isEmpty())
						{
							EmpArticleJournalsTO empPreviousOrgTo=new EmpArticleJournalsTO();
							empPreviousOrgTo.setNameJournal("");
							empPreviousOrgTo.setAuthorName("");
							empPreviousOrgTo.setDoi("");
							empPreviousOrgTo.setIsbn("");
							empPreviousOrgTo.setLanguage("");
							empPreviousOrgTo.setMonthYear("");
							empPreviousOrgTo.setIssueNumber("");
							empPreviousOrgTo.setTitle("");
							empPreviousOrgTo.setPagesFrom("");
							empPreviousOrgTo.setPagesTo("");
							empPreviousOrgTo.setVolumeNumber("");
							empPreviousOrgTo.setCompanyInstitution("");
							empArticleJournalsTO.add(empPreviousOrgTo);
						}
						empResPubForm.setEmpArticleJournalsTO(empArticleJournalsTO);
						
					}else if(empAcheiv.getIsBlog()!=null && empAcheiv.getIsBlog())
					{
						EmpBlogTO empto= new EmpBlogTO();
						if (empAcheiv.getId() > 0) {
							empto.setId(empAcheiv.getId());
						}
						if (empAcheiv.getSubject()!=null && StringUtils.isNotEmpty(empAcheiv.getSubject())) {
							empto.setSubject(empAcheiv.getSubject());
						}

						if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle()) ) {
							empto.setTitle(empAcheiv.getTitle());
						}
						if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage())) {
							empto.setLanguage(empAcheiv.getLanguage());
						}
						if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
							empto.setMonthYear(empAcheiv.getMonthYear());
						}
						if (empAcheiv.getUrl()!=null && StringUtils.isNotEmpty(empAcheiv.getUrl())) {
							empto.setUrl(empAcheiv.getUrl());
						}
						if (empAcheiv.getNumberOfComments()!=null && StringUtils.isNotEmpty(empAcheiv.getNumberOfComments()) ) {
							empto.setNumberOfComments(empAcheiv.getNumberOfComments());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
							empto.setApproverComment(empAcheiv.getApproverComment());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
							empto.setAcademicYear(empAcheiv.getAcademicYear());
						}
						
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
							empto.setApprovedDate(empAcheiv.getApprovedDate());
						}
						if (empAcheiv.getApproverId()!=null)
							{
							if(empAcheiv.getApproverId().getId()> 0) {
							
							empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
						}
						}
						if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
						{
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsBlog(empAcheiv.getIsBlog());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						else
							empto.setIsApproved(false);
						if(empAcheiv.getCreatedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
							empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						else
							empto.setIsRejected(false);
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
							}
						}
						if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
							empto.setRejectReason(empAcheiv.getRejectReason());
						}
						empBlogTO.add(empto);
						if(empBlogTO==null || empBlogTO.isEmpty())
						{
							EmpBlogTO empPreviousOrgTo=new EmpBlogTO();
							empPreviousOrgTo.setLanguage("");
							empPreviousOrgTo.setMonthYear("");
							empPreviousOrgTo.setNumberOfComments("");
							empPreviousOrgTo.setSubject("");
							empPreviousOrgTo.setUrl("");
							empPreviousOrgTo.setTitle("");
							empBlogTO.add(empPreviousOrgTo);
						}
						empResPubForm.setEmpBlogTO(empBlogTO);
						
					}else if(empAcheiv.getIsBookMonographs()!=null && empAcheiv.getIsBookMonographs())
					{
						EmpBooksMonographsTO empto= new EmpBooksMonographsTO();
						if (empAcheiv.getId() > 0) {
							empto.setId(empAcheiv.getId());
						}
						if (empAcheiv.getAuthorName()!=null && StringUtils.isNotEmpty(empAcheiv.getAuthorName())) {
							empto.setAuthorName(empAcheiv.getAuthorName());
						}
						if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle()) ) {
							empto.setTitle(empAcheiv.getTitle());
						}
						if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage())) {
							empto.setLanguage(empAcheiv.getLanguage());
						}
						if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
							empto.setMonthYear(empAcheiv.getMonthYear());
						}
						if (empAcheiv.getPlacePublication()!=null && StringUtils.isNotEmpty(empAcheiv.getPlacePublication())) {
							empto.setPlacePublication(empAcheiv.getPlacePublication());
						}
						if (empAcheiv.getCompanyInstitution()!=null && StringUtils.isNotEmpty(empAcheiv.getCompanyInstitution())) {
							empto.setCompanyInstitution(empAcheiv.getCompanyInstitution());
						}
						if (empAcheiv.getTotalPages()!=null && StringUtils.isNotEmpty(empAcheiv.getTotalPages())) {
							empto.setTotalPages(empAcheiv.getTotalPages());
						}
						if (empAcheiv.getIsbn()!=null && StringUtils.isNotEmpty(empAcheiv.getIsbn()) ) {
							empto.setIsbn(empAcheiv.getIsbn());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
							empto.setApproverComment(empAcheiv.getApproverComment());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
							empto.setAcademicYear(empAcheiv.getAcademicYear());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getCoAuthored()) && empAcheiv.getCoAuthored()!=null) {
							empto.setCoAuthored(empAcheiv.getCoAuthored());
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
							empto.setApprovedDate(empAcheiv.getApprovedDate());
						}
						if (empAcheiv.getApproverId()!=null)
							{
							if(empAcheiv.getApproverId().getId()> 0) {
							
							empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
						}
						}
						if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
						{
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						if(empAcheiv.getCreatedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
							empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsBookMonographs(empAcheiv.getIsBookMonographs());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						else
							empto.setIsApproved(false);
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}else
							empto.setIsRejected(false);
						
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
							}
						}
						if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
							empto.setRejectReason(empAcheiv.getRejectReason());
						}
						empBooksMonographsTO.add(empto);
						if(empBooksMonographsTO==null || empBooksMonographsTO.isEmpty())
						
						{
							EmpBooksMonographsTO empPreviousOrgTo=new EmpBooksMonographsTO();
							empPreviousOrgTo.setCompanyInstitution("");
							empPreviousOrgTo.setAuthorName("");
							empPreviousOrgTo.setIsbn("");
							empPreviousOrgTo.setLanguage("");
							empPreviousOrgTo.setMonthYear("");
							empPreviousOrgTo.setPlacePublication("");
							empPreviousOrgTo.setTitle("");
							empPreviousOrgTo.setTotalPages("");
							empBooksMonographsTO.add(empPreviousOrgTo);
						}
						empResPubForm.setEmpBooksMonographsTO(empBooksMonographsTO);
					}else if(empAcheiv.getIsCasesNoteWorking()!=null && empAcheiv.getIsCasesNoteWorking())
					{
						
						EmpCasesNotesWorkingTO empto= new EmpCasesNotesWorkingTO();
						if (empAcheiv.getId() > 0) {
							empto.setId(empAcheiv.getId());
						}
						if (empAcheiv.getAuthorName()!=null && StringUtils.isNotEmpty(empAcheiv.getAuthorName()) ) {
							empto.setAuthorName(empAcheiv.getAuthorName());
						}

						if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
							empto.setTitle(empAcheiv.getTitle());
						}
						if (empAcheiv.getAbstractObjectives()!=null && StringUtils.isNotEmpty(empAcheiv.getAbstractObjectives())) {
							empto.setAbstractObjectives(empAcheiv.getAbstractObjectives());
						}
						if (empAcheiv.getSponsors()!=null && StringUtils.isNotEmpty(empAcheiv.getSponsors())) {
							empto.setSponsors(empAcheiv.getSponsors());
						}
						if (empAcheiv.getCaseNoteWorkPaper()!=null && StringUtils.isNotEmpty(empAcheiv.getCaseNoteWorkPaper())) {
							empto.setCaseNoteWorkPaper(empAcheiv.getCaseNoteWorkPaper());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
							empto.setApproverComment(empAcheiv.getApproverComment());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
							empto.setAcademicYear(empAcheiv.getAcademicYear());
						}
						
						
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
							empto.setApprovedDate(empAcheiv.getApprovedDate());
						}
						
						if(empAcheiv.getCreatedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
							empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if (empAcheiv.getApproverId()!=null)
							{
							if(empAcheiv.getApproverId().getId()> 0) {
							
							empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
						}
						}
						if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
						{
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsCasesNoteWorking(empAcheiv.getIsCasesNoteWorking());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}else
							empto.setIsApproved(false);
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}else
							empto.setIsRejected(false);
						
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
							}
						}
						if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
							empto.setRejectReason(empAcheiv.getRejectReason());
						}
						empCasesNotesWorkingTO.add(empto);
						if(empCasesNotesWorkingTO==null || empCasesNotesWorkingTO.isEmpty())
						{
							EmpCasesNotesWorkingTO empPreviousOrgTo=new EmpCasesNotesWorkingTO();
							empPreviousOrgTo.setAuthorName("");
							empPreviousOrgTo.setTitle("");
							empPreviousOrgTo.setAbstractObjectives("");
							empPreviousOrgTo.setSponsors("");
							empPreviousOrgTo.setCaseNoteWorkPaper("");
							empCasesNotesWorkingTO.add(empPreviousOrgTo);
						}
						empResPubForm.setEmpCasesNotesWorkingTO(empCasesNotesWorkingTO);
					}
					else if(empAcheiv.getIsChapterArticleBook()!=null && empAcheiv.getIsChapterArticleBook())

					{
						EmpChapterArticlBookTO empto= new EmpChapterArticlBookTO();
						if (empAcheiv.getId() > 0) {
							empto.setId(empAcheiv.getId());
						}
						if (empAcheiv.getAuthorName()!=null && StringUtils.isNotEmpty(empAcheiv.getAuthorName()) ) {
							empto.setAuthorName(empAcheiv.getAuthorName());
						}
						if ( empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
							empto.setTitle(empAcheiv.getTitle());
						}
						if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage())) {
							empto.setLanguage(empAcheiv.getLanguage());
						}
						if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
							empto.setMonthYear(empAcheiv.getMonthYear());
						}
						if (empAcheiv.getEditorsName()!=null && StringUtils.isNotEmpty(empAcheiv.getEditorsName())) { 
							empto.setEditorsName(empAcheiv.getEditorsName());
						}
						if (empAcheiv.getPlacePublication()!=null && StringUtils.isNotEmpty(empAcheiv.getPlacePublication())) {
							empto.setPlacePublication(empAcheiv.getPlacePublication());
						}
						if (empAcheiv.getCompanyInstitution()!=null && StringUtils.isNotEmpty(empAcheiv.getCompanyInstitution())) {
							empto.setCompanyInstitution(empAcheiv.getCompanyInstitution());
						}
						if (empAcheiv.getPagesFrom()!=null && StringUtils.isNotEmpty(empAcheiv.getPagesFrom())) {
							empto.setPagesFrom(empAcheiv.getPagesFrom());
						}
						if (empAcheiv.getPagesTo()!=null && StringUtils.isNotEmpty(empAcheiv.getPagesTo()) ) {
							empto.setPagesTo(empAcheiv.getPagesTo());
						}
						if (empAcheiv.getIsbn()!=null && StringUtils.isNotEmpty(empAcheiv.getIsbn())) {
							empto.setIsbn(empAcheiv.getIsbn());
						}
						if (empAcheiv.getTitleChapterArticle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitleChapterArticle())) {
							empto.setTitleChapterArticle(empAcheiv.getTitleChapterArticle());	
						}
						if (empAcheiv.getTotalPages()!=null && StringUtils.isNotEmpty(empAcheiv.getTotalPages())) {
							empto.setTotalPages(empAcheiv.getTotalPages());
						}
						if (empAcheiv.getDoi()!=null && StringUtils.isNotEmpty(empAcheiv.getDoi())) {
							empto.setDoi(empAcheiv.getDoi());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
							empto.setApproverComment(empAcheiv.getApproverComment());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
							empto.setAcademicYear(empAcheiv.getAcademicYear());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getCoAuthored()) && empAcheiv.getCoAuthored()!=null) {
							empto.setCoAuthored(empAcheiv.getCoAuthored());
						}
						
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
							empto.setApprovedDate(empAcheiv.getApprovedDate());
						}
						if(empAcheiv.getCreatedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
							empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if (empAcheiv.getApproverId()!=null)
							{
							if(empAcheiv.getApproverId().getId()> 0) {
							
							empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
						}
						}
						if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
						{
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsChapterArticleBook(empAcheiv.getIsChapterArticleBook());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						else
							empto.setIsApproved(false);
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}else
							empto.setIsRejected(false);
						
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
							}
						}
						if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
							empto.setRejectReason(empAcheiv.getRejectReason());
						}
						empChapterArticlBookTO.add(empto);
						if(empChapterArticlBookTO==null || empChapterArticlBookTO.isEmpty())
						{
							EmpChapterArticlBookTO empPreviousOrgTo=new EmpChapterArticlBookTO();
							empPreviousOrgTo.setEditorsName("");
							empPreviousOrgTo.setAuthorName("");
							empPreviousOrgTo.setDoi("");
							empPreviousOrgTo.setIsbn("");
							empPreviousOrgTo.setLanguage("");
							empPreviousOrgTo.setMonthYear("");
							empPreviousOrgTo.setPlacePublication("");
							empPreviousOrgTo.setTitle("");
							empPreviousOrgTo.setPagesFrom("");
							empPreviousOrgTo.setPagesTo("");
							empPreviousOrgTo.setCompanyInstitution("");
							empPreviousOrgTo.setTotalPages("");
							empPreviousOrgTo.setTitleChapterArticle("");
							empChapterArticlBookTO.add(empPreviousOrgTo);
						}
						empResPubForm.setEmpChapterArticlBookTO(empChapterArticlBookTO);
				}
				else if(empAcheiv.getIsConferencePresentation()!=null && empAcheiv.getIsConferencePresentation())
				{
					EmpConferencePresentationTO empto= new EmpConferencePresentationTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					if (empAcheiv.getNameTalksPresentation()!=null && StringUtils.isNotEmpty(empAcheiv.getNameTalksPresentation()) ) {
						empto.setNameTalksPresentation(empAcheiv.getNameTalksPresentation());
					}
					if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
						empto.setTitle(empAcheiv.getTitle());
					}
					if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage()) ) {
						empto.setLanguage(empAcheiv.getLanguage());
					}
					/*if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
						empto.setMonthYear(empAcheiv.getMonthYear());
					}*/
					if (empAcheiv.getDateMonthYear()!=null) {
						empto.setMonthYear(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateMonthYear().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if (empAcheiv.getNameConferencesSeminar()!=null && StringUtils.isNotEmpty(empAcheiv.getNameConferencesSeminar()) ) {
						empto.setNameConferencesSeminar(empAcheiv.getNameConferencesSeminar());
					}
					
					if (empAcheiv.getCompanyInstitution()!=null && StringUtils.isNotEmpty(empAcheiv.getCompanyInstitution())) {
						empto.setCompanyInstitution(empAcheiv.getCompanyInstitution());
					}
					if (empAcheiv.getPlacePublication()!=null && StringUtils.isNotEmpty(empAcheiv.getPlacePublication()) ) {
						empto.setPlacePublication(empAcheiv.getPlacePublication());
					}
					if(empAcheiv.getAbstracts()!=null && StringUtils.isNotEmpty(empAcheiv.getAbstracts()))
					{
						empto.setAbstractDetails(empAcheiv.getAbstracts());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
						empto.setType(empAcheiv.getType());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getTypeOfPgm()) && empAcheiv.getTypeOfPgm()!=null) {
						empto.setTypeOfPgm(empAcheiv.getTypeOfPgm());
						if(empAcheiv.getTypeOfPgm().equalsIgnoreCase("other") &&(empAcheiv.getOtherText()!=null && ! empAcheiv.getOtherText().isEmpty())){
							empto.setOtherTextConf(empAcheiv.getOtherText());
						}
					}
					
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsConferencePresentation(empAcheiv.getIsConferencePresentation());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					else
						empto.setIsApproved(false);
					
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}else
						empto.setIsRejected(false);
					
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					
					empConferencePresentationTO.add(empto);
					if(empConferencePresentationTO==null || empConferencePresentationTO.isEmpty())
					{
						EmpConferencePresentationTO empPreviousOrgTo=new EmpConferencePresentationTO();
						empPreviousOrgTo.setNameTalksPresentation("");
						empPreviousOrgTo.setTitle("");
						empPreviousOrgTo.setLanguage("");
						empPreviousOrgTo.setNameConferencesSeminar("");
						empPreviousOrgTo.setMonthYear("");
						empPreviousOrgTo.setCompanyInstitution("");
						empPreviousOrgTo.setPlacePublication("");
						empPreviousOrgTo.setAbstractDetails("");
						empPreviousOrgTo.setTypeOfPgm("");
						empConferencePresentationTO.add(empPreviousOrgTo);
					}
					empResPubForm.setEmpConferencePresentationTO(empConferencePresentationTO);
				}
				else if(empAcheiv.getIsInvitedTalk()!=null && empAcheiv.getIsInvitedTalk())
				{
					EmpInvitedTalkTO empto= new EmpInvitedTalkTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					if (empAcheiv.getNameOfPgm()!=null && StringUtils.isNotEmpty(empAcheiv.getNameOfPgm()) ) {
						empto.setNameOfPgm(empAcheiv.getNameOfPgm());
					}
					if (empAcheiv.getNameTalksPresentation()!=null && StringUtils.isNotEmpty(empAcheiv.getNameTalksPresentation()) ) {
						empto.setNameTalksPresentation(empAcheiv.getNameTalksPresentation());
					}
					if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
						empto.setTitle(empAcheiv.getTitle());
					}
					if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage()) ) {
						empto.setLanguage(empAcheiv.getLanguage());
					}
					if (empAcheiv.getDateMonthYear()!=null) {
						empto.setMonthYear(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateMonthYear().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if (empAcheiv.getNameConferencesSeminar()!=null && StringUtils.isNotEmpty(empAcheiv.getNameConferencesSeminar()) ) {
						empto.setNameConferencesSeminar(empAcheiv.getNameConferencesSeminar());
					}
					
					if (empAcheiv.getCompanyInstitution()!=null && StringUtils.isNotEmpty(empAcheiv.getCompanyInstitution())) {
						empto.setCompanyInstitution(empAcheiv.getCompanyInstitution());
					}
					if (empAcheiv.getPlacePublication()!=null && StringUtils.isNotEmpty(empAcheiv.getPlacePublication()) ) {
						empto.setPlacePublication(empAcheiv.getPlacePublication());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
						empto.setType(empAcheiv.getType());
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsInvitedTalk(empAcheiv.getIsInvitedTalk());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}else
						empto.setIsApproved(false);
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
					else
						empto.setIsRejected(false);
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					
					empInvitedTalkTO.add(empto);
					if(empInvitedTalkTO==null || empInvitedTalkTO.isEmpty())
					{
						EmpInvitedTalkTO empPreviousOrgTo=new EmpInvitedTalkTO();
						empPreviousOrgTo.setNameOfPgm("");
						empPreviousOrgTo.setNameTalksPresentation("");
						empPreviousOrgTo.setTitle("");
						empPreviousOrgTo.setLanguage("");
						empPreviousOrgTo.setNameConferencesSeminar("");
						empPreviousOrgTo.setMonthYear("");
						empPreviousOrgTo.setCompanyInstitution("");
						empPreviousOrgTo.setPlacePublication("");
						empInvitedTalkTO.add(empPreviousOrgTo);
					}
					empResPubForm.setEmpInvitedTalkTO(empInvitedTalkTO);
				}
				else if(empAcheiv.getIsFilmVideoDoc()!=null && empAcheiv.getIsFilmVideoDoc())
				{					
					EmpFilmVideosDocTO empto= new EmpFilmVideosDocTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					if (empAcheiv.getSubtitles()!=null && StringUtils.isNotEmpty(empAcheiv.getSubtitles())) {
						empto.setSubtitles(empAcheiv.getSubtitles());
					}
					if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
						empto.setTitle(empAcheiv.getTitle());
					}
					if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage()) ) {
						empto.setLanguage(empAcheiv.getLanguage());
					}
					if (empAcheiv.getGenre()!=null && StringUtils.isNotEmpty(empAcheiv.getGenre())) {
						empto.setGenre(empAcheiv.getGenre());
					}
					if (empAcheiv.getCredits()!=null && StringUtils.isNotEmpty(empAcheiv.getCredits())) {
						empto.setCredits(empAcheiv.getCredits());
					}
					if (empAcheiv.getRunningTime()!=null && StringUtils.isNotEmpty(empAcheiv.getRunningTime())) {
						empto.setRunningTime(empAcheiv.getRunningTime());
					}
					if (empAcheiv.getDiscFormat()!=null && StringUtils.isNotEmpty(empAcheiv.getDiscFormat())) {
						empto.setDiscFormat(empAcheiv.getDiscFormat());
					}
					if (empAcheiv.getTechnicalFormat()!=null && StringUtils.isNotEmpty(empAcheiv.getTechnicalFormat())) {
						empto.setTechnicalFormat(empAcheiv.getTechnicalFormat());
					}
					if (empAcheiv.getAudioFormat()!=null && StringUtils.isNotEmpty(empAcheiv.getAudioFormat())) {
						empto.setAudioFormat(empAcheiv.getAudioFormat());
					}
					if (empAcheiv.getProducer()!=null && StringUtils.isNotEmpty(empAcheiv.getProducer()) ) {
						empto.setProducer(empAcheiv.getProducer());
					}
					if (empAcheiv.getCopyrights()!=null && StringUtils.isNotEmpty(empAcheiv.getCopyrights())) {
						empto.setCopyrights(empAcheiv.getCopyrights());
					}
					if (empAcheiv.getAspectRatio()!=null && StringUtils.isNotEmpty(empAcheiv.getAspectRatio())) {
						empto.setAspectRatio(empAcheiv.getAspectRatio());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsFilmVideoDoc(empAcheiv.getIsFilmVideoDoc());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}else
						empto.setIsApproved(false);
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
					else
						empto.setIsRejected(false);
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					
					empFilmVideosDocTO.add(empto);
					if(empFilmVideosDocTO==null || empFilmVideosDocTO.isEmpty())
					{
						EmpFilmVideosDocTO empPreviousOrgTo=new EmpFilmVideosDocTO();
						empPreviousOrgTo.setTitle("");
						empPreviousOrgTo.setSubtitles("");
						empPreviousOrgTo.setGenre("");
						empPreviousOrgTo.setCredits("");
						empPreviousOrgTo.setRunningTime("");
						empPreviousOrgTo.setAspectRatio("");
						empPreviousOrgTo.setLanguage("");
						empPreviousOrgTo.setDiscFormat("");
						empPreviousOrgTo.setTechnicalFormat("");
						empPreviousOrgTo.setAudioFormat("");
						empPreviousOrgTo.setProducer("");
						empPreviousOrgTo.setCopyrights("");
						empFilmVideosDocTO.add(empPreviousOrgTo);
					}
					empResPubForm.setEmpFilmVideosDocTO(empFilmVideosDocTO);
			}
				else if(empAcheiv.getIsOwnPhdMphilThesis()!=null && empAcheiv.getIsOwnPhdMphilThesis())
				{	
					EmpOwnPhdMPilThesisTO empto= new EmpOwnPhdMPilThesisTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					if (empAcheiv.getSubject()!=null && StringUtils.isNotEmpty(empAcheiv.getSubject()) ) {
						empto.setSubject(empAcheiv.getSubject());
					}
					if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
						empto.setTitle(empAcheiv.getTitle());
					}
					if (empAcheiv.getNameGuide()!=null && StringUtils.isNotEmpty(empAcheiv.getNameGuide()) ) {
						empto.setNameGuide(empAcheiv.getNameGuide());
					}
					if (empAcheiv.getCompanyInstitution()!=null && StringUtils.isNotEmpty(empAcheiv.getCompanyInstitution())) {
						empto.setCompanyInstitution(empAcheiv.getCompanyInstitution());
					}
					if (empAcheiv.getPlacePublication()!=null && StringUtils.isNotEmpty(empAcheiv.getPlacePublication()) ) {
						empto.setPlace(empAcheiv.getPlacePublication());
					}
					if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
						empto.setMonthYear(empAcheiv.getMonthYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
						empto.setType(empAcheiv.getType());
					}
					
					if(empAcheiv.getSubmissionDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getSubmissionDate().toString())) {
						empto.setSubmissionDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getSubmissionDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsOwnPhdMphilThesis(empAcheiv.getIsOwnPhdMphilThesis());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}else
						empto.setIsApproved(false);
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}else
						empto.setIsRejected(false);
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					
					empOwnPhdMPilThesisTO.add(empto);
					if(empOwnPhdMPilThesisTO==null ||empOwnPhdMPilThesisTO.isEmpty())
					{
					
						EmpOwnPhdMPilThesisTO empPreviousOrgTo=new EmpOwnPhdMPilThesisTO();
						empPreviousOrgTo.setTitle("");
						empPreviousOrgTo.setSubject("");
						empPreviousOrgTo.setNameGuide("");
						empPreviousOrgTo.setCompanyInstitution("");
						empPreviousOrgTo.setPlace("");
						empPreviousOrgTo.setMonthYear("");
						empOwnPhdMPilThesisTO.add(empPreviousOrgTo);
					}
					empResPubForm.setEmpOwnPhdMPilThesisTO(empOwnPhdMPilThesisTO);
			}
				else if(empAcheiv.getIsPhdMPhilThesisGuided()!=null && empAcheiv.getIsPhdMPhilThesisGuided())
				{
					
					EmpPhdMPhilThesisGuidedTO empto= new EmpPhdMPhilThesisGuidedTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					if (empAcheiv.getSubject()!=null && StringUtils.isNotEmpty(empAcheiv.getSubject())) {
						empto.setSubject(empAcheiv.getSubject());
					}
					if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
						empto.setTitle(empAcheiv.getTitle());
					}
					if (empAcheiv.getNameStudent()!=null && StringUtils.isNotEmpty(empAcheiv.getNameStudent())) {
						empto.setNameStudent(empAcheiv.getNameStudent());
					}
					if (empAcheiv.getCompanyInstitution()!=null && StringUtils.isNotEmpty(empAcheiv.getCompanyInstitution())) {
						empto.setCompanyInstitution(empAcheiv.getCompanyInstitution());
					}
					if (empAcheiv.getPlacePublication()!=null && StringUtils.isNotEmpty(empAcheiv.getPlacePublication())) {
						empto.setPlace(empAcheiv.getPlacePublication());
					}
					if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
						empto.setMonthYear(empAcheiv.getMonthYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
						empto.setType(empAcheiv.getType());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getGuidedAdjudicated()) && empAcheiv.getGuidedAdjudicated()!=null) {
						empto.setGuidedAbudjicated(empAcheiv.getGuidedAdjudicated());
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsPhdMPhilThesisGuided(empAcheiv.getIsPhdMPhilThesisGuided());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}else
						empto.setIsApproved(false);
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}else
						empto.setIsRejected(false);
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					
					
					empPhdMPhilThesisGuidedTO.add(empto);
					if(empPhdMPhilThesisGuidedTO==null || empPhdMPhilThesisGuidedTO.isEmpty())
					{
						EmpPhdMPhilThesisGuidedTO empPreviousOrgTo=new EmpPhdMPhilThesisGuidedTO();
						empPreviousOrgTo.setTitle("");
						empPreviousOrgTo.setSubject("");
						empPreviousOrgTo.setNameStudent("");
						empPreviousOrgTo.setCompanyInstitution("");
						empPreviousOrgTo.setPlace("");
						empPreviousOrgTo.setMonthYear("");
						empPhdMPhilThesisGuidedTO.add(empPreviousOrgTo);
					}
					empResPubForm.setEmpPhdMPhilThesisGuidedTO(empPhdMPhilThesisGuidedTO);
			}
				else if(empAcheiv.getIsSeminarOrganized()!=null && empAcheiv.getIsSeminarOrganized())
				{
					
					EmpSeminarsOrganizedTO empto= new EmpSeminarsOrganizedTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					if (empAcheiv.getNameOrganisers()!=null && StringUtils.isNotEmpty(empAcheiv.getNameOrganisers())) {
						empto.setNameOrganisers(empAcheiv.getNameOrganisers());
					}
					if (empAcheiv.getNameConferencesSeminar()!=null && StringUtils.isNotEmpty(empAcheiv.getNameConferencesSeminar())) {
						empto.setNameConferencesSeminar(empAcheiv.getNameConferencesSeminar());
					}
					if (empAcheiv.getResoursePerson()!=null && StringUtils.isNotEmpty(empAcheiv.getResoursePerson())) {
						empto.setResoursePerson(empAcheiv.getResoursePerson());
					}
					if (empAcheiv.getPlacePublication()!=null && StringUtils.isNotEmpty(empAcheiv.getPlacePublication())) {
						empto.setPlace(empAcheiv.getPlacePublication());
					}
					if (empAcheiv.getDateMonthYear()!=null &&  StringUtils.isNotEmpty(empAcheiv.getDateMonthYear().toString()) ) {
						empto.setDateMonthYear(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateMonthYear().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage()) ) {
						empto.setLanguage(empAcheiv.getLanguage());
					}
					if (empAcheiv.getSponsors()!=null && StringUtils.isNotEmpty(empAcheiv.getSponsors())) {
						empto.setSponsors(empAcheiv.getSponsors());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsSeminarOrganized(empAcheiv.getIsSeminarOrganized());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}else
						empto.setIsApproved(false);
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}else
						empto.setIsRejected(false);
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}                  
					
					
					empSeminarsOrganizedTO.add(empto);
					if(empSeminarsOrganizedTO==null || empSeminarsOrganizedTO.isEmpty())
					{
						EmpSeminarsOrganizedTO empPreviousOrgTo=new EmpSeminarsOrganizedTO();
						empPreviousOrgTo.setNameOrganisers("");
						empPreviousOrgTo.setNameConferencesSeminar("");
						empPreviousOrgTo.setResoursePerson("");
						empPreviousOrgTo.setPlace("");
						empPreviousOrgTo.setDateMonthYear("");
						empPreviousOrgTo.setLanguage("");
						empPreviousOrgTo.setSponsors("");
						empSeminarsOrganizedTO.add(empPreviousOrgTo);
					}
					empResPubForm.setEmpSeminarsOrganizedTO(empSeminarsOrganizedTO);
			}
				else if(empAcheiv.getIsResearchProject()!=null && empAcheiv.getIsResearchProject())
				{
					
					EmpResProjectTO empto= new EmpResProjectTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getInvestigators()) && empAcheiv.getInvestigators()!=null) {
						empto.setInvestigators(empAcheiv.getInvestigators());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getSponsors()) && empAcheiv.getSponsors()!=null) {
						empto.setSponsors(empAcheiv.getSponsors());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getTitle()) && empAcheiv.getTitle()!=null) {
						empto.setTitle(empAcheiv.getTitle());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAbstractObjectives()) && empAcheiv.getAbstractObjectives()!=null) {
						empto.setAbstractObjectives(empAcheiv.getAbstractObjectives());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAmountGranted()) && empAcheiv.getAmountGranted()!=null) {
						empto.setAmountGranted(empAcheiv.getAmountGranted());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getInternalExternal()) && empAcheiv.getInternalExternal()!=null) {
						empto.setInternalExternal(empAcheiv.getInternalExternal());
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsResearchProject(empAcheiv.getIsResearchProject());
					
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}else
						empto.setIsApproved(false);
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}else
						empto.setIsRejected(false);
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					empResProjectTO.add(empto);
					/*if(empResProjectTO==null || empResProjectTO.isEmpty())
					{
						EmpResProjectTO empPreviousOrgTo=new EmpResProjectTO();
						empPreviousOrgTo.setAbstractObjectives("");
						empPreviousOrgTo.setInvestigators("");
						empPreviousOrgTo.setSponsors("");
						empPreviousOrgTo.setTitle("");
						empResProjectTO.add(empPreviousOrgTo);
					}*/
					empResPubForm.setEmpResearchProjectTO(empResProjectTO);
			}
				else if(empAcheiv.getIsSeminarAttended()!=null && empAcheiv.getIsSeminarAttended())
				{
					
					EmpConferenceSeminarsAttendedTO empto= new EmpConferenceSeminarsAttendedTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					
					if (empAcheiv.getNameConferencesSeminar()!=null && StringUtils.isNotEmpty(empAcheiv.getNameConferencesSeminar())) {
						empto.setNameOfPgm(empAcheiv.getNameConferencesSeminar());
					}
					if (empAcheiv.getNameOrganisers()!=null && StringUtils.isNotEmpty(empAcheiv.getNameOrganisers()) ) {
						
						empto.setOrganisedBy(empAcheiv.getNameOrganisers());
					}
					if (empAcheiv.getDateMonthYear()!=null &&  StringUtils.isNotEmpty(empAcheiv.getDateMonthYear().toString()) ) {
						empto.setDateOfPgm(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateMonthYear().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if (empAcheiv.getEndOfPgm()!=null &&  StringUtils.isNotEmpty(empAcheiv.getEndOfPgm().toString()) ) {
						empto.setEndOfPgm(CommonUtil.ConvertStringToDateFormat(empAcheiv.getEndOfPgm().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if (empAcheiv.getRunningTime()!=null && StringUtils.isNotEmpty(empAcheiv.getRunningTime())) {
						empto.setDuration(empAcheiv.getRunningTime());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
						empto.setType(empAcheiv.getType());
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsSeminarAttended(empAcheiv.getIsSeminarAttended());
					
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}else
						empto.setIsApproved(false);
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}else
						empto.setIsRejected(false);
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					empSeminarsAttendedTO.add(empto);
					if(empSeminarsAttendedTO==null || empSeminarsAttendedTO.isEmpty())
					{
						EmpConferenceSeminarsAttendedTO empPreviousOrgTo=new EmpConferenceSeminarsAttendedTO();
						empPreviousOrgTo.setNameOfPgm("");
						empPreviousOrgTo.setOrganisedBy("");
						empPreviousOrgTo.setDateOfPgm("");
						empPreviousOrgTo.setDuration("");
						empSeminarsAttendedTO.add(empPreviousOrgTo);
					}
					empResPubForm.setEmpConferenceSeminarsAttendedTO(empSeminarsAttendedTO);
			}
				else if(empAcheiv.getIsWorkshopTraining()!=null && empAcheiv.getIsWorkshopTraining())
				{
					
					EmpWorkshopsFdpTrainingTO empto= new EmpWorkshopsFdpTrainingTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					if (empAcheiv.getNameConferencesSeminar()!=null && StringUtils.isNotEmpty(empAcheiv.getNameConferencesSeminar())) {
						empto.setNameOfPgm(empAcheiv.getNameConferencesSeminar());
					}
					if (empAcheiv.getNameOrganisers()!=null && StringUtils.isNotEmpty(empAcheiv.getNameOrganisers()) ) {
						
						empto.setOrganisedBy(empAcheiv.getNameOrganisers());
					}
					if (empAcheiv.getDateMonthYear()!=null &&  StringUtils.isNotEmpty(empAcheiv.getDateMonthYear().toString()) ) {
						empto.setDateOfPgm(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateMonthYear().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if (empAcheiv.getRunningTime()!=null && StringUtils.isNotEmpty(empAcheiv.getRunningTime())) {
						empto.setDuration(empAcheiv.getRunningTime());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
						empto.setType(empAcheiv.getType());
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsWorkshopTraining(empAcheiv.getIsWorkshopTraining());
					
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}else
						empto.setIsApproved(false);
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}else
						empto.setIsRejected(false);
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					
					empWorkshopsTrainingTOs.add(empto);
					if(empWorkshopsTrainingTOs==null || empWorkshopsTrainingTOs.isEmpty())
					{
					    EmpWorkshopsFdpTrainingTO empPreviousOrgTo=new EmpWorkshopsFdpTrainingTO();
						empPreviousOrgTo.setNameOfPgm("");
						empPreviousOrgTo.setOrganisedBy("");
						empPreviousOrgTo.setDateOfPgm("");
						empPreviousOrgTo.setDuration("");
						empWorkshopsTrainingTOs.add(empPreviousOrgTo);
					}
					empResPubForm.setEmpWorkshopsTO(empWorkshopsTrainingTOs);
			}
				else if(empAcheiv.getIsAwardsAchievementsOthers()!=null && empAcheiv.getIsAwardsAchievementsOthers())
				{
					
					EmpAwardsAchievementsOthersTO empto= new EmpAwardsAchievementsOthersTO();
					if (empAcheiv.getId() > 0) {
						empto.setId(empAcheiv.getId());
					}
					if (empAcheiv.getName()!=null && StringUtils.isNotEmpty(empAcheiv.getName())) {
						empto.setName(empAcheiv.getName());
					}
					if (empAcheiv.getDescription()!=null && StringUtils.isNotEmpty(empAcheiv.getDescription()) ) {
						
						empto.setDescription(empAcheiv.getDescription());
					}
					if (empAcheiv.getMonthYear()!=null &&  StringUtils.isNotEmpty(empAcheiv.getMonthYear()) ) {
						empto.setMonthYear(empAcheiv.getMonthYear());
					}
					if (empAcheiv.getPlace()!=null && StringUtils.isNotEmpty(empAcheiv.getPlace())) {
						empto.setPlace(empAcheiv.getPlace());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getApproverComment()) && empAcheiv.getApproverComment()!=null) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getOrganisationAwarded()) && empAcheiv.getOrganisationAwarded()!=null) {
						empto.setOrganisationAwarded(empAcheiv.getOrganisationAwarded());
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
						empto.setApprovedDate(empAcheiv.getApprovedDate());
					}
					if(empAcheiv.getCreatedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getCreatedDate().toString())) {
						empto.setEntryCreatedate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getCreatedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					}
					if (empAcheiv.getApproverId()!=null)
						{
						if(empAcheiv.getApproverId().getId()> 0) {
						
						empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
					}
					}
					if (empAcheiv.getEmployeeId()!=null && empAcheiv.getEmployeeId().getId()>0)
					{
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsAwardsAchievementsOthers(empAcheiv.getIsAwardsAchievementsOthers());
					
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}else
						empto.setIsApproved(false);
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}else
						empto.setIsRejected(false);
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					
					empAwardsAchievementsTOs.add(empto);
					if(empAwardsAchievementsTOs==null || empAwardsAchievementsTOs.isEmpty())
					{
					    EmpAwardsAchievementsOthersTO empAwardsTo=new EmpAwardsAchievementsOthersTO();
					    empAwardsTo.setName("");
					    empAwardsTo.setPlace("");
					    empAwardsTo.setDescription("");
					    empAwardsTo.setMonthYear("");
					    empAwardsTo.setOrganisationAwarded("");
					    empAwardsAchievementsTOs.add(empAwardsTo);
					}
					empResPubForm.setEmpAwardsAchievementsOthersTO(empAwardsAchievementsTOs);
			}
					
			}
		}
		}	
		/*	} else {

				List<EmpAcheivementTO> flist = new ArrayList<EmpAcheivementTO>();
				EmpAcheivementTO empAcheivementTO = new EmpAcheivementTO();
				empAcheivementTO.setAcheivementName("");
				empAcheivementTO.setDetails("");
				objform.setAchievementListSize(String.valueOf(flist.size()));
				flist.add(empAcheivementTO);
				objform.getEmployeeInfoTONew().setEmpAcheivements(flist);*/
	}
	
	public List<EmpResearchPublicDetails> convertFormToBo(EmpResPubPendApprovalForm empResPubForm) throws Exception {
		 List<EmpResearchPublicDetails> empResPubDetails = new ArrayList<EmpResearchPublicDetails>();
		 int empId=Integer.parseInt(empResPubForm.getSelectedEmployeeId());
		 Employee emp=new Employee();
		 emp.setId(empId);
		 Employee empnew=empTransaction.getEmailfromEmployeeId(empId);
		 empResPubForm.setEmployeeEmailId(empnew.getWorkEmail());
		 empResPubForm.setEmployeeName(empnew.getFirstName());
		 int approverId=empTransaction.getEmployeeIdFromUserId(empResPubForm);
		 Employee emp1=new Employee();
		 emp1.setId(approverId);
		 empResPubForm.setApproveFlag(false);
		 
		 if (empResPubForm.getSubmitName().equalsIgnoreCase("ArticleJournals")) {
		 if (empResPubForm.getEmpArticleJournalsTO() != null			
				&& !empResPubForm.getEmpArticleJournalsTO().isEmpty()) {
			Iterator<EmpArticleJournalsTO> itr = empResPubForm
					.getEmpArticleJournalsTO().iterator();
			while (itr.hasNext()) {
				EmpArticleJournalsTO to = (EmpArticleJournalsTO) itr.next();
				EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
				if(to!=null )
				{
					if(to.getAuthorName()!=null && !to.getAuthorName().isEmpty()|| to.getIsbn()!=null && !to.getIsbn().isEmpty()|| 
					  to.getIssueNumber()!=null && !to.getIssueNumber().isEmpty()|| to.getLanguage()!=null && !to.getLanguage().isEmpty()||
					  to.getNameJournal()!=null && !to.getNameJournal().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty()||
					  to.getDoi()!=null && !to.getDoi().isEmpty() || to.getMonthYear()!=null && !to.getMonthYear().isEmpty()||
					   to.getDepartmentInstitution()!=null && !to.getDepartmentInstitution().isEmpty()||
					   to.getDatePublished()!=null && !to.getDatePublished().isEmpty()||
					   to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()||
					  to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()|| to.getUrl()!=null && !to.getUrl().isEmpty()||
					  to.getImpactFactor()!=null && !to.getImpactFactor().isEmpty()|| to.getPagesFrom()!=null && !to.getPagesFrom().isEmpty()||
					  to.getPagesTo()!=null && !to.getPagesTo().isEmpty())
					{
						if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
						{
					
						if (to.getId() > 0) {
							dep.setId(to.getId());
						}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
					dep.setAuthorName(to.getAuthorName());
					dep.setDoi(to.getDoi());
					dep.setIsbn(to.getIsbn());
					dep.setIssueNumber(to.getIssueNumber());
					dep.setLanguage(to.getLanguage());
					//dep.setMonthYear(to.getMonthYear());
					dep.setDepartmentInstitution(to.getDepartmentInstitution());
					dep.setPlacePublication(to.getPlacePublication());
					dep.setCompanyInstitution(to.getCompanyInstitution());
					dep.setUrl(to.getUrl());
					dep.setImpactFactor(to.getImpactFactor());
					//dep.setDateAccepted(CommonUtil.ConvertStringToDate(to.getDateAccepted()));
					dep.setMonthYear(to.getDatePublished());
					//dep.setDateSent(CommonUtil.ConvertStringToDate(to.getDateSent()));
					dep.setNameJournal(to.getNameJournal());
					dep.setPagesFrom(to.getPagesFrom());
					dep.setPagesTo(to.getPagesTo());
					dep.setTitle(to.getTitle());
					dep.setVolumeNumber(to.getVolumeNumber());
					dep.setAcademicYear(to.getAcademicYear());
					dep.setType(to.getType());
					dep.setIsActive(true);
					dep.setEmployeeId(emp);
					dep.setCreatedBy(empResPubForm.getUserId());
					dep.setCreatedDate(new Date());
					dep.setModifiedBy(empResPubForm.getUserId());
					dep.setLastModifiedDate(new Date());
					dep.setApprovedDate(new Date());
					dep.setApproverComment(to.getApproverComment());
					//dep.setApproverId(emp1);
					Employee emp2 =new Employee();
					emp2.setId(Integer.parseInt(to.getApproverId()));
					dep.setApproverId(emp2);
					dep.setIsApproved(true);
					dep.setIsArticleJournal(true);
					dep.setIsArticleInPeriodicals(false);
					dep.setIsBlog(false);
					dep.setIsBookMonographs(false);
					dep.setIsCasesNoteWorking(false);
					dep.setIsChapterArticleBook(false);
					dep.setIsConferencePresentation(false);
					dep.setIsFilmVideoDoc(false);
					dep.setIsOwnPhdMphilThesis(false);
					dep.setIsPhdMPhilThesisGuided(false);
					dep.setIsResearchProject(false);
					dep.setIsSeminarOrganized(false);
					dep.setIsInvitedTalk(false);
					dep.setIsSeminarAttended(false);
					dep.setIsWorkshopTraining(false);
					dep.setIsAwardsAchievementsOthers(false);
					dep.setIsRejected(false);
					dep.setIsEmployee(true);
					dep.setRejectReason(to.getRejectReason());
					dep.setRejectDate(to.getRejectDate());
					empResPubDetails.add(dep);
					}
					}
					else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
					{
						if (to.getId() > 0) {
							dep.setId(to.getId());
						}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
					dep.setAuthorName(to.getAuthorName());
					dep.setDoi(to.getDoi());
					dep.setIsbn(to.getIsbn());
					dep.setIssueNumber(to.getIssueNumber());
					dep.setLanguage(to.getLanguage());
					//dep.setMonthYear(to.getMonthYear());
					dep.setDepartmentInstitution(to.getDepartmentInstitution());
					dep.setPlacePublication(to.getPlacePublication());
					dep.setCompanyInstitution(to.getCompanyInstitution());
					dep.setUrl(to.getUrl());
					dep.setImpactFactor(to.getImpactFactor());
					//dep.setDateAccepted(CommonUtil.ConvertStringToDate(to.getDateAccepted()));
					dep.setMonthYear(to.getDatePublished());
					//dep.setDateSent(CommonUtil.ConvertStringToDate(to.getDateSent()));
					dep.setNameJournal(to.getNameJournal());
					dep.setPagesFrom(to.getPagesFrom());
					dep.setPagesTo(to.getPagesTo());
					dep.setTitle(to.getTitle());
					dep.setVolumeNumber(to.getVolumeNumber());
					dep.setAcademicYear(to.getAcademicYear());
					dep.setType(to.getType());
					dep.setIsActive(true);
					dep.setEmployeeId(emp);
					dep.setCreatedBy(empResPubForm.getUserId());
					dep.setCreatedDate(new Date());
					dep.setModifiedBy(empResPubForm.getUserId());
					dep.setLastModifiedDate(new Date());
					dep.setApprovedDate(to.getApprovedDate());
					dep.setApproverComment(to.getApproverComment());
					//dep.setApproverId(emp1);
					Employee emp2 =new Employee();
					emp2.setId(Integer.parseInt(to.getApproverId()));
					dep.setApproverId(emp2);
					dep.setIsApproved(false);
					dep.setIsRejected(true);
					dep.setIsArticleJournal(true);
					dep.setIsArticleInPeriodicals(false);
					dep.setIsBlog(false);
					dep.setIsBookMonographs(false);
					dep.setIsCasesNoteWorking(false);
					dep.setIsChapterArticleBook(false);
					dep.setIsConferencePresentation(false);
					dep.setIsFilmVideoDoc(false);
					dep.setIsOwnPhdMphilThesis(false);
					dep.setIsPhdMPhilThesisGuided(false);
					dep.setIsResearchProject(false);
					dep.setIsSeminarOrganized(false);
					dep.setIsInvitedTalk(false);
					dep.setIsSeminarAttended(false);
					dep.setIsWorkshopTraining(false);
					dep.setIsAwardsAchievementsOthers(false);
					dep.setIsRejected(true);
					dep.setIsEmployee(true);
					dep.setRejectReason(empResPubForm.getRejectReason());
					dep.setRejectDate(new Date());
					empResPubDetails.add(dep);
					}
					}
				}
				}
			  }
			
			}
		 empResPubForm.setApproveFlag(true);
		 }
		 else if (empResPubForm.getSubmitName().equalsIgnoreCase("ArticlInPeriodicals")) {
			if (empResPubForm.getEmpArticlInPeriodicalsTO() != null			
					&& !empResPubForm.getEmpArticlInPeriodicalsTO().isEmpty()) {
				Iterator<EmpArticlInPeriodicalsTO> itr = empResPubForm
						.getEmpArticlInPeriodicalsTO().iterator();
				while (itr.hasNext()) {
					EmpArticlInPeriodicalsTO to = (EmpArticlInPeriodicalsTO) itr.next();
					EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
					if(to!=null)
					{
						if(to.getAuthorName()!=null && !to.getAuthorName().isEmpty()|| to.getDateMonthYear()!=null && !to.getDateMonthYear().isEmpty()||
								to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getIsbn()!=null && !to.getIsbn().isEmpty()|| to.getIssueNumber()!=null && !to.getIssueNumber().isEmpty()||
								to.getLanguage()!=null && !to.getLanguage().isEmpty()|| to.getNamePeriodical()!=null && !to.getNamePeriodical().isEmpty()||
								to.getTitle()!=null && !to.getTitle().isEmpty()|| to.getVolumeNumber()!=null && !to.getVolumeNumber().isEmpty() || to.getPagesFrom()!=null && !to.getPagesFrom().isEmpty()||
								to.getPagesTo()!=null && !to.getPagesTo().isEmpty())
						{
							if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
							{
							if (to.getId() > 0) {
								dep.setId(to.getId());
							}
						if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
						dep.setAuthorName(to.getAuthorName());
						dep.setNamePeriodical(to.getNamePeriodical());
						dep.setIsbn(to.getIsbn());
						dep.setIssueNumber(to.getIssueNumber());
						dep.setLanguage(to.getLanguage());
						dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
						dep.setPagesFrom(to.getPagesFrom());
						dep.setPagesTo(to.getPagesTo());
						dep.setTitle(to.getTitle());
						dep.setVolumeNumber(to.getVolumeNumber());
						dep.setAcademicYear(to.getAcademicYear());
						dep.setType(to.getType());
						if((to.getType()!=null && !to.getType().isEmpty() && to.getType().equalsIgnoreCase("other")) && (to.getOtherTextArticle()!=null && !to.getOtherTextArticle().isEmpty())){
							dep.setOtherText(to.getOtherTextArticle());
						}
						dep.setIsActive(true);
						dep.setEmployeeId(emp);
						dep.setCreatedBy(empResPubForm.getUserId());
						dep.setCreatedDate(new Date());
						dep.setModifiedBy(empResPubForm.getUserId());
						dep.setLastModifiedDate(new Date());
						dep.setApprovedDate(new Date());
						dep.setApproverComment(to.getApproverComment());
						//dep.setApproverId(emp1);
						Employee emp2 =new Employee();
						emp2.setId(Integer.parseInt(to.getApproverId()));
						dep.setApproverId(emp2);
						dep.setIsApproved(true);
						dep.setIsArticleJournal(false);
						dep.setIsArticleInPeriodicals(true);
						dep.setIsBlog(false);
						dep.setIsBookMonographs(false);
						dep.setIsCasesNoteWorking(false);
						dep.setIsChapterArticleBook(false);
						dep.setIsConferencePresentation(false);
						dep.setIsFilmVideoDoc(false);
						dep.setIsOwnPhdMphilThesis(false);
						dep.setIsPhdMPhilThesisGuided(false);
						dep.setIsResearchProject(false);
						dep.setIsSeminarOrganized(false);
						dep.setIsInvitedTalk(false);
						dep.setIsSeminarAttended(false);
						dep.setIsWorkshopTraining(false);
						dep.setIsAwardsAchievementsOthers(false);
						dep.setIsRejected(false);
						dep.setIsEmployee(true);
					    dep.setRejectReason(to.getRejectReason());
					    dep.setRejectDate(to.getRejectDate());
						empResPubDetails.add(dep);
						}
						}
						else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
						{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
								if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
								{
								dep.setAuthorName(to.getAuthorName());
								dep.setNamePeriodical(to.getNamePeriodical());
								dep.setIsbn(to.getIsbn());
								dep.setIssueNumber(to.getIssueNumber());
								dep.setLanguage(to.getLanguage());
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
								dep.setPagesFrom(to.getPagesFrom());
								dep.setPagesTo(to.getPagesTo());
								dep.setTitle(to.getTitle());
								dep.setVolumeNumber(to.getVolumeNumber());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								if((to.getType()!=null && !to.getType().isEmpty() && to.getType().equalsIgnoreCase("other")) && (to.getOtherTextArticle()!=null && !to.getOtherTextArticle().isEmpty())){
									dep.setOtherText(to.getOtherTextArticle());
								}
								dep.setIsActive(true);
								dep.setEmployeeId(emp);
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								//dep.setApproverId(emp1);
								Employee emp2 =new Employee();
								emp2.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp2);
								dep.setIsApproved(false);
								dep.setIsArticleJournal(false);
								dep.setIsArticleInPeriodicals(true);
								dep.setIsBlog(false);
								dep.setIsBookMonographs(false);
								dep.setIsCasesNoteWorking(false);
								dep.setIsChapterArticleBook(false);
								dep.setIsConferencePresentation(false);
								dep.setIsFilmVideoDoc(false);
								dep.setIsOwnPhdMphilThesis(false);
								dep.setIsPhdMPhilThesisGuided(false);
								dep.setIsResearchProject(false);
								dep.setIsSeminarOrganized(false);
								dep.setIsInvitedTalk(false);
								dep.setIsSeminarAttended(false);
								dep.setIsWorkshopTraining(false);
								dep.setIsAwardsAchievementsOthers(false);
								dep.setIsRejected(true);
								dep.setIsEmployee(true);
							    dep.setRejectReason(empResPubForm.getRejectReason());
							    dep.setRejectDate(new Date());
							 empResPubDetails.add(dep);
							 
								}
							}
					}}
					}
				}empResPubForm.setApproveFlag(true);
		 }
			else if (empResPubForm.getSubmitName().equalsIgnoreCase("Blog")) {
				if (empResPubForm.getEmpBlogTO() != null			
						&& !empResPubForm.getEmpBlogTO().isEmpty()) {
					Iterator<EmpBlogTO> itr = empResPubForm
							.getEmpBlogTO().iterator();
					while (itr.hasNext()) {
						EmpBlogTO to = (EmpBlogTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getLanguage()!=null && !to.getLanguage().isEmpty()||
							to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| to.getNumberOfComments()!=null && !to.getNumberOfComments().isEmpty()||
							to.getSubject()!=null && !to.getSubject().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty()||
							to.getUrl()!=null && !to.getUrl().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
						if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
							dep.setLanguage(to.getLanguage());
							dep.setNumberOfComments(to.getNumberOfComments());
							dep.setSubject(to.getSubject());
							dep.setUrl(to.getUrl());
							dep.setMonthYear(to.getMonthYear());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(true);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
							}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setLanguage(to.getLanguage());
							dep.setNumberOfComments(to.getNumberOfComments());
							dep.setSubject(to.getSubject());
							dep.setUrl(to.getUrl());
							dep.setMonthYear(to.getMonthYear());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(true);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
							
							}
							}
							
						}
						}
						}
					}
				empResPubForm.setApproveFlag(true);
			}
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("BooksMonographs")) {
				if (empResPubForm.getEmpBooksMonographsTO() != null			
						&& !empResPubForm.getEmpBooksMonographsTO().isEmpty()) {
					Iterator<EmpBooksMonographsTO> itr = empResPubForm
							.getEmpBooksMonographsTO().iterator();
					while (itr.hasNext()) {
						EmpBooksMonographsTO to = (EmpBooksMonographsTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getAuthorName()!=null && !to.getAuthorName().isEmpty()|| to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()||
							 to.getIsbn()!=null && !to.getIsbn().isEmpty()|| to.getLanguage()!=null && !to.getLanguage().isEmpty()||
							to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| 
							to.getTitle()!=null && !to.getTitle().isEmpty() || to.getTotalPages()!=null && !to.getTotalPages().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
						if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
							dep.setLanguage(to.getLanguage());
							dep.setAuthorName(to.getAuthorName());
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setIsbn(to.getIsbn());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setMonthYear(to.getMonthYear());
							dep.setTitle(to.getTitle());
							dep.setTotalPages(to.getTotalPages());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setCoAuthored(to.getCoAuthored());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(true);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
						}
							}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					   {
							dep.setLanguage(to.getLanguage());
							dep.setAuthorName(to.getAuthorName());
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setIsbn(to.getIsbn());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setMonthYear(to.getMonthYear());
							dep.setTitle(to.getTitle());
							dep.setTotalPages(to.getTotalPages());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setCoAuthored(to.getCoAuthored());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(true);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
					   }	
							}
						}
						  }
						}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("CasesNotesWorking")) {
				if (empResPubForm.getEmpCasesNotesWorkingTO() != null			
						&& !empResPubForm.getEmpCasesNotesWorkingTO().isEmpty()) {
					Iterator<EmpCasesNotesWorkingTO> itr = empResPubForm
							.getEmpCasesNotesWorkingTO().iterator();
					while (itr.hasNext()) {
						EmpCasesNotesWorkingTO to = (EmpCasesNotesWorkingTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getAbstractObjectives()!=null && !to.getAbstractObjectives().isEmpty()|| to.getAuthorName()!=null && !to.getAuthorName().isEmpty()||
							  to.getCaseNoteWorkPaper()!=null && !to.getCaseNoteWorkPaper().isEmpty()|| to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getSponsors()!=null && !to.getSponsors().isEmpty()||
							  to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
							dep.setAbstractObjectives(to.getAbstractObjectives());
							dep.setAuthorName(to.getAuthorName());
							dep.setCaseNoteWorkPaper(to.getCaseNoteWorkPaper());
							dep.setSponsors(to.getSponsors());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(true);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
							}
					}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
				if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
							dep.setAbstractObjectives(to.getAbstractObjectives());
							dep.setAuthorName(to.getAuthorName());
							dep.setCaseNoteWorkPaper(to.getCaseNoteWorkPaper());
							dep.setSponsors(to.getSponsors());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(true);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
							
					}	
							}
						}
						}
						}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("ChapterArticlBook")) {
				if (empResPubForm.getEmpChapterArticlBookTO() != null			
						&& !empResPubForm.getEmpChapterArticlBookTO().isEmpty()) {
					Iterator<EmpChapterArticlBookTO> itr = empResPubForm
							.getEmpChapterArticlBookTO().iterator();
					while (itr.hasNext()) {
						EmpChapterArticlBookTO to = (EmpChapterArticlBookTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getAuthorName()!=null && !to.getAuthorName().isEmpty()|| to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()||
							   to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getEditorsName()!=null && !to.getEditorsName().isEmpty()|| to.getIsbn()!=null && !to.getIsbn().isEmpty()||
							   to.getLanguage()!=null && !to.getLanguage().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()||
							   to.getTitle()!=null && !to.getTitle().isEmpty()|| to.getTitleChapterArticle()!=null && !to.getTitleChapterArticle().isEmpty()|| to.getTotalPages()!=null && !to.getTotalPages().isEmpty()||
							   to.getPagesFrom()!=null && !to.getPagesFrom().isEmpty() || to.getPagesTo()!=null && !to.getPagesTo().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setAuthorName(to.getAuthorName());
							dep.setDoi(to.getDoi());
							dep.setEditorsName(to.getEditorsName());
							dep.setIsbn(to.getIsbn());
							dep.setLanguage(to.getLanguage());
							dep.setMonthYear(to.getMonthYear());
							dep.setPagesFrom(to.getPagesFrom());
							dep.setPagesTo(to.getPagesTo());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setTitle(to.getTitle());
							dep.setTitleChapterArticle(to.getTitleChapterArticle());
							dep.setTotalPages(to.getTotalPages());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setCoAuthored(to.getCoAuthored());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(true);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
							}
								}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
						if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setAuthorName(to.getAuthorName());
							dep.setDoi(to.getDoi());
							dep.setEditorsName(to.getEditorsName());
							dep.setIsbn(to.getIsbn());
							dep.setLanguage(to.getLanguage());
							dep.setMonthYear(to.getMonthYear());
							dep.setPagesFrom(to.getPagesFrom());
							dep.setPagesTo(to.getPagesTo());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setTitle(to.getTitle());
							dep.setTitleChapterArticle(to.getTitleChapterArticle());
							dep.setTotalPages(to.getTotalPages());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setCoAuthored(to.getCoAuthored());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(true);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
							}
							}
						}
						}
						}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("ConferencePresentation")) {
				if (empResPubForm.getEmpConferencePresentationTO() != null			
						&& !empResPubForm.getEmpConferencePresentationTO().isEmpty()) {
					Iterator<EmpConferencePresentationTO> itr = empResPubForm
							.getEmpConferencePresentationTO().iterator();
					while (itr.hasNext()) {
						EmpConferencePresentationTO to = (EmpConferencePresentationTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()|| to.getDoi()!=null && !to.getDoi().isEmpty()||
							  to.getLanguage()!=null && !to.getLanguage().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| to.getNameConferencesSeminar()!=null && !to.getNameConferencesSeminar().isEmpty()||
							  to.getNameTalksPresentation()!=null && !to.getNameTalksPresentation().isEmpty()|| to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()||
							  to.getTitle()!=null && !to.getTitle().isEmpty() || to.getAbstractDetails()!=null && !to.getAbstractDetails().isEmpty() || to.getTypeOfPgm()!=null && !to.getTypeOfPgm().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
						if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
							{
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setLanguage(to.getLanguage());
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
							//dep.setMonthYear(to.getMonthYear());
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							dep.setNameTalksPresentation(to.getNameTalksPresentation());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setAbstracts(to.getAbstractDetails());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setTypeOfPgm(to.getTypeOfPgm());
							if((to.getTypeOfPgm()!=null && !to.getTypeOfPgm().isEmpty() && to.getTypeOfPgm().equalsIgnoreCase("other")) && (to.getOtherTextConf()!=null && !to.getOtherTextConf().isEmpty())){
								dep.setOtherText(to.getOtherTextConf());
							}
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(true);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
							}
								}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
						if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
							{
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setLanguage(to.getLanguage());
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
							//dep.setMonthYear(to.getMonthYear());
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							dep.setNameTalksPresentation(to.getNameTalksPresentation());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setAbstracts(to.getAbstractDetails());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setTypeOfPgm(to.getTypeOfPgm());
							if((to.getTypeOfPgm()!=null && !to.getTypeOfPgm().isEmpty() && to.getTypeOfPgm().equalsIgnoreCase("other")) && (to.getOtherTextConf()!=null && !to.getOtherTextConf().isEmpty())){
								dep.setOtherText(to.getOtherTextConf());
							}
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(true);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
							
							}
							}
						}
						}
						}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("InvitedTalk")) { 
				if (empResPubForm.getEmpInvitedTalkTO() != null			
						&& !empResPubForm.getEmpInvitedTalkTO().isEmpty()) {
					Iterator<EmpInvitedTalkTO> itr = empResPubForm
							.getEmpInvitedTalkTO().iterator();
					while (itr.hasNext()) {
						EmpInvitedTalkTO to = (EmpInvitedTalkTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()|| to.getDoi()!=null && !to.getDoi().isEmpty()||
							  to.getLanguage()!=null && !to.getLanguage().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| to.getNameConferencesSeminar()!=null && !to.getNameConferencesSeminar().isEmpty()||
							  to.getNameTalksPresentation()!=null && !to.getNameTalksPresentation().isEmpty()|| to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()||
							  to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
						if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
								{
							dep.setNameOfPgm(to.getNameOfPgm());
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setLanguage(to.getLanguage());
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							dep.setNameTalksPresentation(to.getNameTalksPresentation());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(true);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
							}
						}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
							dep.setNameOfPgm(to.getNameOfPgm());
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setLanguage(to.getLanguage());
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
							//dep.setMonthYear(to.getMonthYear());
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							dep.setNameTalksPresentation(to.getNameTalksPresentation());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(true);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
							
					}
							}
						}
						}
						}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("FilmVideosDoc")) {
				if (empResPubForm.getEmpFilmVideosDocTO() != null			
						&& !empResPubForm.getEmpFilmVideosDocTO().isEmpty()) {
					Iterator<EmpFilmVideosDocTO> itr = empResPubForm
							.getEmpFilmVideosDocTO().iterator();
					while (itr.hasNext()) {
						EmpFilmVideosDocTO to = (EmpFilmVideosDocTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getAspectRatio()!=null && !to.getAspectRatio().isEmpty()|| to.getAudioFormat()!=null && !to.getAudioFormat().isEmpty()||
							   to.getCopyrights()!=null && !to.getCopyrights().isEmpty()|| to.getCredits()!=null && !to.getCredits().isEmpty()|| to.getDiscFormat()!=null && !to.getDiscFormat().isEmpty()
							   ||to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getGenre()!=null && !to.getGenre().isEmpty() || to.getLanguage()!=null && !to.getLanguage().isEmpty()||
							   to.getProducer()!=null && !to.getProducer().isEmpty()|| to.getRunningTime()!=null && !to.getRunningTime().isEmpty()|| to.getSubtitles()!=null && !to.getSubtitles().isEmpty()||
							   to.getTechnicalFormat()!=null && !to.getTechnicalFormat().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
							dep.setAspectRatio(to.getAspectRatio());
							dep.setAudioFormat(to.getAudioFormat());
							dep.setCopyrights(to.getCopyrights());
							dep.setCredits(to.getCredits());
							dep.setDiscFormat(to.getDiscFormat());
							dep.setGenre(to.getGenre());
							dep.setLanguage(to.getLanguage());
							dep.setProducer(to.getProducer());
							dep.setRunningTime(to.getRunningTime());
							dep.setSubtitles(to.getSubtitles());
							dep.setTechnicalFormat(to.getTechnicalFormat());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(true);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
							}
						}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
				if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
							dep.setAspectRatio(to.getAspectRatio());
							dep.setAudioFormat(to.getAudioFormat());
							dep.setCopyrights(to.getCopyrights());
							dep.setCredits(to.getCredits());
							dep.setDiscFormat(to.getDiscFormat());
							dep.setGenre(to.getGenre());
							dep.setLanguage(to.getLanguage());
							dep.setProducer(to.getProducer());
							dep.setRunningTime(to.getRunningTime());
							dep.setSubtitles(to.getSubtitles());
							dep.setTechnicalFormat(to.getTechnicalFormat());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(true);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
							}
						}}
						}
						}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("ResearchProject")) {
				if (empResPubForm.getEmpResearchProjectTO() != null			
						&& !empResPubForm.getEmpResearchProjectTO().isEmpty()) {
					Iterator<EmpResProjectTO> itr = empResPubForm
							.getEmpResearchProjectTO().iterator();
					while (itr.hasNext()) {
						EmpResProjectTO to = (EmpResProjectTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{	
							if(to.getAbstractObjectives()!=null && !to.getAbstractObjectives().isEmpty()|| to.getInvestigators()!=null && !to.getInvestigators().isEmpty()||
								to.getSponsors()!=null && !to.getSponsors().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
			if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
							dep.setAbstractObjectives(to.getAbstractObjectives());
							dep.setInvestigators(to.getInvestigators());
							dep.setSponsors(to.getSponsors());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setInternalExternal(to.getInternalExternal());
							dep.setAmountGranted(to.getAmountGranted());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(true);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
					}
							}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
		if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
				{
							dep.setAbstractObjectives(to.getAbstractObjectives());
							dep.setInvestigators(to.getInvestigators());
							dep.setSponsors(to.getSponsors());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setInternalExternal(to.getInternalExternal());
							dep.setAmountGranted(to.getAmountGranted());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(true);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
							
				}	
							}
						}}
						}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("OwnPhdMPilThesis")) {
				if (empResPubForm.getEmpOwnPhdMPilThesisTO() != null			
						&& !empResPubForm.getEmpOwnPhdMPilThesisTO().isEmpty()) {
					Iterator<EmpOwnPhdMPilThesisTO> itr = empResPubForm
							.getEmpOwnPhdMPilThesisTO().iterator();
					while (itr.hasNext()) {
						EmpOwnPhdMPilThesisTO to = (EmpOwnPhdMPilThesisTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()||
							  to.getNameGuide()!=null && !to.getNameGuide().isEmpty() || to.getPlace()!=null && !to.getPlace().isEmpty()|| to.getSubject()!=null && !to.getSubject().isEmpty()||
							  to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null ||empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
			if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
								{
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setMonthYear(to.getMonthYear());
							dep.setNameGuide(to.getNameGuide());
							dep.setPlacePublication(to.getPlace());
							dep.setSubject(to.getSubject());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setSubmissionDate(CommonUtil.ConvertStringToDate(to.getSubmissionDate()));
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(true);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
							}
					}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
			if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
				{
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setMonthYear(to.getMonthYear());
							dep.setNameGuide(to.getNameGuide());
							dep.setPlacePublication(to.getPlace());
							dep.setSubject(to.getSubject());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setSubmissionDate(CommonUtil.ConvertStringToDate(to.getSubmissionDate()));
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(true);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
				}
							
							}
							}
						}
					}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("PhdMPhilThesisGuided")) {
				if (empResPubForm.getEmpPhdMPhilThesisGuidedTO() != null			
						&& !empResPubForm.getEmpPhdMPhilThesisGuidedTO().isEmpty()) {
					Iterator<EmpPhdMPhilThesisGuidedTO> itr = empResPubForm
							.getEmpPhdMPhilThesisGuidedTO().iterator();
					while (itr.hasNext()) {
						EmpPhdMPhilThesisGuidedTO to = (EmpPhdMPhilThesisGuidedTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty() || to.getMonthYear()!=null && !to.getMonthYear().isEmpty()
							|| to.getNameStudent()!=null && !to.getNameStudent().isEmpty()||to.getPlace()!=null && !to.getPlace().isEmpty()||
							to.getSubject()!=null && !to.getSubject().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null ||empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
			if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setMonthYear(to.getMonthYear());
							dep.setNameStudent(to.getNameStudent());
							dep.setPlacePublication(to.getPlace());
							dep.setSubject(to.getSubject());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							/*code added by sudhir*/
							//dep.setGuidedAdjudicated(to.getGuidedAbudjicated());
							dep.setGuidedAdjudicated("Guided");
							/*--------------------*/
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(true);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
							}
						}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
			if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setMonthYear(to.getMonthYear());
							dep.setNameStudent(to.getNameStudent());
							dep.setPlacePublication(to.getPlace());
							dep.setSubject(to.getSubject());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setGuidedAdjudicated(to.getGuidedAbudjicated());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(true);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
					}
							
							}
							}
						}
						}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("SeminarsOrganized")) {
				if (empResPubForm.getEmpSeminarsOrganizedTO() != null			
						&& !empResPubForm.getEmpSeminarsOrganizedTO().isEmpty()) {
					Iterator<EmpSeminarsOrganizedTO> itr = empResPubForm
							.getEmpSeminarsOrganizedTO().iterator();
					while (itr.hasNext()) {
						EmpSeminarsOrganizedTO to = (EmpSeminarsOrganizedTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getLanguage()!=null && !to.getLanguage().isEmpty() || 
							to.getDateMonthYear()!=null && !to.getDateMonthYear().isEmpty() || to.getNameConferencesSeminar()!=null && !to.getNameConferencesSeminar().isEmpty()||
							to.getNameOrganisers()!=null && !to.getNameOrganisers().isEmpty()|| to.getPlace()!=null && !to.getPlace().isEmpty()
							|| to.getResoursePerson()!=null && !to.getResoursePerson().isEmpty()|| to.getSponsors()!=null && !to.getSponsors().isEmpty())
							{
								if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
				if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
					{
							dep.setLanguage(to.getLanguage());
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							dep.setPlacePublication(to.getPlace());
							dep.setNameOrganisers(to.getNameOrganisers());
							dep.setResoursePerson(to.getResoursePerson());
							dep.setSponsors(to.getSponsors());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(new Date());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(true);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(true);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
						    dep.setRejectReason(to.getRejectReason());
						    dep.setRejectDate(to.getRejectDate());
							empResPubDetails.add(dep);
							}
								}
							else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
							{

								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
								{
							dep.setLanguage(to.getLanguage());
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							dep.setPlacePublication(to.getPlace());
							dep.setNameOrganisers(to.getNameOrganisers());
							dep.setResoursePerson(to.getResoursePerson());
							dep.setSponsors(to.getSponsors());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							//dep.setApproverId(emp1);
							Employee emp2 =new Employee();
							emp2.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp2);
							dep.setIsApproved(false);
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(true);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(true);
							dep.setIsEmployee(true);
						    dep.setRejectReason(empResPubForm.getRejectReason());
						    dep.setRejectDate(new Date());
							empResPubDetails.add(dep);
								}
							
							}
							}
						}
						}
					}
				empResPubForm.setApproveFlag(true);
			 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("SeminarsAttended")) {
					if (empResPubForm.getEmpConferenceSeminarsAttendedTO() != null			
							&& !empResPubForm.getEmpConferenceSeminarsAttendedTO().isEmpty()) {
						Iterator<EmpConferenceSeminarsAttendedTO> itr = empResPubForm
								.getEmpConferenceSeminarsAttendedTO().iterator();
						while (itr.hasNext()) {
							EmpConferenceSeminarsAttendedTO to = (EmpConferenceSeminarsAttendedTO) itr.next();
							EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
							if(to!=null)
							{
								if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()|| to.getEndOfPgm()!=null && !to.getEndOfPgm().isEmpty()|| to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty() || 
										to.getDuration()!=null && !to.getDuration().isEmpty() || to.getOrganisedBy()!=null && !to.getOrganisedBy().isEmpty())
								{
									if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
									{
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
									{
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
								dep.setEndOfPgm(CommonUtil.ConvertStringToDate(to.getEndOfPgm()));
								dep.setNameConferencesSeminar(to.getNameOfPgm());
								dep.setNameOrganisers(to.getOrganisedBy());
								dep.setRunningTime(to.getDuration());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								dep.setIsActive(true);
								dep.setEmployeeId(emp);
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(new Date());
								dep.setApproverComment(to.getApproverComment());
								//dep.setApproverId(emp1);
								Employee emp2 =new Employee();
								emp2.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp2);
								dep.setIsApproved(true);
								dep.setIsArticleJournal(false);
								dep.setIsArticleInPeriodicals(false);
								dep.setIsBlog(false);
								dep.setIsBookMonographs(false);
								dep.setIsCasesNoteWorking(false);
								dep.setIsChapterArticleBook(false);
								dep.setIsConferencePresentation(false);
								dep.setIsFilmVideoDoc(false);
								dep.setIsOwnPhdMphilThesis(false);
								dep.setIsPhdMPhilThesisGuided(false);
								dep.setIsResearchProject(false);
								dep.setIsSeminarOrganized(false);
								dep.setIsInvitedTalk(false);
								dep.setIsSeminarAttended(true);
								dep.setIsWorkshopTraining(false);
								dep.setIsAwardsAchievementsOthers(false);
								dep.setIsRejected(false);
								dep.setIsEmployee(true);
							    dep.setRejectReason(to.getRejectReason());
							    dep.setRejectDate(to.getRejectDate());
								empResPubDetails.add(dep);
								}
							}
								else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
								{

									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
				if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
								dep.setEndOfPgm(CommonUtil.ConvertStringToDate(to.getEndOfPgm()));
								dep.setNameConferencesSeminar(to.getNameOfPgm());
								dep.setNameOrganisers(to.getOrganisedBy());
								dep.setRunningTime(to.getDuration());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								dep.setIsActive(true);
								dep.setEmployeeId(emp);
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								//dep.setApproverId(emp1);
								Employee emp2 =new Employee();
								emp2.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp2);
								dep.setIsApproved(false);
								dep.setIsArticleJournal(false);
								dep.setIsArticleInPeriodicals(false);
								dep.setIsBlog(false);
								dep.setIsBookMonographs(false);
								dep.setIsCasesNoteWorking(false);
								dep.setIsChapterArticleBook(false);
								dep.setIsConferencePresentation(false);
								dep.setIsFilmVideoDoc(false);
								dep.setIsOwnPhdMphilThesis(false);
								dep.setIsPhdMPhilThesisGuided(false);
								dep.setIsResearchProject(false);
								dep.setIsSeminarOrganized(false);
								dep.setIsInvitedTalk(false);
								dep.setIsSeminarAttended(true);
								dep.setIsWorkshopTraining(false);
								dep.setIsAwardsAchievementsOthers(false);
								dep.setIsRejected(true);
								dep.setIsEmployee(true);
							    dep.setRejectReason(empResPubForm.getRejectReason());
							    dep.setRejectDate(new Date());
								empResPubDetails.add(dep);
								
						}	
								}
								}
							}
							}
						}
					empResPubForm.setApproveFlag(true);
				 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("WorkshopsAttended")) {
					if (empResPubForm.getEmpWorkshopsTO() != null			
							&& !empResPubForm.getEmpWorkshopsTO().isEmpty()) {
						Iterator<EmpWorkshopsFdpTrainingTO> itr = empResPubForm
								.getEmpWorkshopsTO().iterator();
						while (itr.hasNext()) {
							EmpWorkshopsFdpTrainingTO to = (EmpWorkshopsFdpTrainingTO) itr.next();
							EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
							if(to!=null)
							{if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()|| to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty() || 
									to.getDuration()!=null && !to.getDuration().isEmpty() || to.getOrganisedBy()!=null && !to.getOrganisedBy().isEmpty())
								{
									if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
									{
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
								dep.setNameConferencesSeminar(to.getNameOfPgm());
								dep.setNameOrganisers(to.getOrganisedBy());
								dep.setRunningTime(to.getDuration());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								dep.setIsActive(true);
								dep.setEmployeeId(emp);
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(new Date());
								dep.setApproverComment(to.getApproverComment());
								//dep.setApproverId(emp1);
								Employee emp2 =new Employee();
								emp2.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp2);
								dep.setIsApproved(true);
								dep.setIsArticleJournal(false);
								dep.setIsArticleInPeriodicals(false);
								dep.setIsBlog(false);
								dep.setIsBookMonographs(false);
								dep.setIsCasesNoteWorking(false);
								dep.setIsChapterArticleBook(false);
								dep.setIsConferencePresentation(false);
								dep.setIsFilmVideoDoc(false);
								dep.setIsOwnPhdMphilThesis(false);
								dep.setIsPhdMPhilThesisGuided(false);
								dep.setIsResearchProject(false);
								dep.setIsSeminarOrganized(false);
								dep.setIsInvitedTalk(false);
								dep.setIsSeminarAttended(false);
								dep.setIsWorkshopTraining(true);
								dep.setIsAwardsAchievementsOthers(false);
								dep.setIsRejected(false);
								dep.setIsEmployee(true);
							    dep.setRejectReason(to.getRejectReason());
							    dep.setRejectDate(to.getRejectDate());
								empResPubDetails.add(dep);
								}
									}
								else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
								{

									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
						if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						 {
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
								dep.setNameConferencesSeminar(to.getNameOfPgm());
								dep.setNameOrganisers(to.getOrganisedBy());
								dep.setRunningTime(to.getDuration());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								dep.setIsActive(true);
								dep.setEmployeeId(emp);
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								//dep.setApproverId(emp1);
								Employee emp2 =new Employee();
								emp2.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp2);
								dep.setIsApproved(false);
								dep.setIsArticleJournal(false);
								dep.setIsArticleInPeriodicals(false);
								dep.setIsBlog(false);
								dep.setIsBookMonographs(false);
								dep.setIsCasesNoteWorking(false);
								dep.setIsChapterArticleBook(false);
								dep.setIsConferencePresentation(false);
								dep.setIsFilmVideoDoc(false);
								dep.setIsOwnPhdMphilThesis(false);
								dep.setIsPhdMPhilThesisGuided(false);
								dep.setIsResearchProject(false);
								dep.setIsSeminarOrganized(false);
								dep.setIsInvitedTalk(false);
								dep.setIsSeminarAttended(false);
								dep.setIsWorkshopTraining(true);
								dep.setIsAwardsAchievementsOthers(false);
								dep.setIsRejected(true);
								dep.setIsEmployee(true);
							    dep.setRejectReason(empResPubForm.getRejectReason());
							    dep.setRejectDate(new Date());
								empResPubDetails.add(dep);
						        }
								}
								}
							}
							}
						}
					empResPubForm.setApproveFlag(true);
				 }
			 else if (empResPubForm.getSubmitName().equalsIgnoreCase("AwardsAchievements")) {
					if (empResPubForm.getEmpAwardsAchievementsOthersTO() != null			
							&& !empResPubForm.getEmpAwardsAchievementsOthersTO().isEmpty()) {
						Iterator<EmpAwardsAchievementsOthersTO> itr = empResPubForm
								.getEmpAwardsAchievementsOthersTO().iterator();
						while (itr.hasNext()) {
							EmpAwardsAchievementsOthersTO to = (EmpAwardsAchievementsOthersTO) itr.next();
							EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
							if(to!=null)
							{	
								if(to.getName()!=null && !to.getName().isEmpty()|| to.getPlace()!=null && !to.getPlace().isEmpty() || 
										to.getDescription()!=null && !to.getDescription().isEmpty() || to.getMonthYear()!=null && !to.getMonthYear().isEmpty())
										{
									if(!to.getIsApproved() && (empResPubForm.getIsReject().isEmpty() || empResPubForm.getIsReject()==null || empResPubForm.getIsReject().equalsIgnoreCase("false")))
									{
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
						if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
									dep.setMonthYear(to.getMonthYear());
									dep.setName(to.getName());
									dep.setPlace(to.getPlace());
									dep.setDescription(to.getDescription());
									dep.setAcademicYear(to.getAcademicYear());
									dep.setOrganisationAwarded(to.getOrganisationAwarded());
								dep.setIsActive(true);
								dep.setEmployeeId(emp);
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(new Date());
								dep.setApproverComment(to.getApproverComment());
								//dep.setApproverId(emp1);
								Employee emp2 =new Employee();
								emp2.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp2);
								dep.setIsApproved(true);
								dep.setIsArticleJournal(false);
								dep.setIsArticleInPeriodicals(false);
								dep.setIsBlog(false);
								dep.setIsBookMonographs(false);
								dep.setIsCasesNoteWorking(false);
								dep.setIsChapterArticleBook(false);
								dep.setIsConferencePresentation(false);
								dep.setIsFilmVideoDoc(false);
								dep.setIsOwnPhdMphilThesis(false);
								dep.setIsPhdMPhilThesisGuided(false);
								dep.setIsResearchProject(false);
								dep.setIsSeminarOrganized(false);
								dep.setIsInvitedTalk(false);
								dep.setIsSeminarAttended(false);
								dep.setIsWorkshopTraining(false);
								dep.setIsAwardsAchievementsOthers(true);
								dep.setIsRejected(false);
								dep.setIsEmployee(true);
							    dep.setRejectReason(to.getRejectReason());
							    dep.setRejectDate(to.getRejectDate());
								empResPubDetails.add(dep);
								}
						}
								else if(!to.getIsApproved() && empResPubForm.getIsReject().equalsIgnoreCase("true"))
								{

									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
					if(empResPubForm.getSelectedId()!=null && !empResPubForm.getSelectedId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedId())==to.getId())
						{
									dep.setMonthYear(to.getMonthYear());
									dep.setName(to.getName());
									dep.setPlace(to.getPlace());
									dep.setDescription(to.getDescription());
									dep.setAcademicYear(to.getAcademicYear());
								dep.setIsActive(true);
								dep.setEmployeeId(emp);
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								//dep.setApproverId(emp1);
								Employee emp2 =new Employee();
								emp2.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp2);
								dep.setIsApproved(false);
								dep.setIsArticleJournal(false);
								dep.setIsArticleInPeriodicals(false);
								dep.setIsBlog(false);
								dep.setIsBookMonographs(false);
								dep.setIsCasesNoteWorking(false);
								dep.setIsChapterArticleBook(false);
								dep.setIsConferencePresentation(false);
								dep.setIsFilmVideoDoc(false);
								dep.setIsOwnPhdMphilThesis(false);
								dep.setIsPhdMPhilThesisGuided(false);
								dep.setIsResearchProject(false);
								dep.setIsSeminarOrganized(false);
								dep.setIsInvitedTalk(false);
								dep.setIsSeminarAttended(false);
								dep.setIsWorkshopTraining(false);
								dep.setIsAwardsAchievementsOthers(true);
								dep.setIsRejected(true);
								dep.setIsEmployee(true);
							    dep.setRejectReason(empResPubForm.getRejectReason());
							    dep.setRejectDate(new Date());
								empResPubDetails.add(dep);
						}
								
								}
							}}
							}
						}empResPubForm.setApproveFlag(true);
				 }
		return empResPubDetails;
	}
	
	public List<EmpResearchPublicDetails> convertFormToBoApproved(EmpResPubPendApprovalForm empResPubForm) throws Exception {
		 List<EmpResearchPublicDetails> empResPubDetails = new ArrayList<EmpResearchPublicDetails>();
		
		 if (empResPubForm.getEmpArticleJournalsTO() != null			
				&& !empResPubForm.getEmpArticleJournalsTO().isEmpty()) {
			Iterator<EmpArticleJournalsTO> itr = empResPubForm
					.getEmpArticleJournalsTO().iterator();
			while (itr.hasNext()) {
				EmpArticleJournalsTO to = (EmpArticleJournalsTO) itr.next();
				EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
				if(to!=null )
				{
					if(to.getAuthorName()!=null && !to.getAuthorName().isEmpty()|| to.getIsbn()!=null && !to.getIsbn().isEmpty()|| 
					  to.getIssueNumber()!=null && !to.getIssueNumber().isEmpty()|| to.getLanguage()!=null && !to.getLanguage().isEmpty()||
					  to.getNameJournal()!=null && !to.getNameJournal().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty()||
					  to.getDoi()!=null && !to.getDoi().isEmpty() || to.getMonthYear()!=null && !to.getMonthYear().isEmpty()||to.getDepartmentInstitution()!=null && !to.getDepartmentInstitution().isEmpty()||
					   to.getDatePublished()!=null && !to.getDatePublished().isEmpty() ||
					  to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()|| to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()||
					  to.getUrl()!=null && !to.getUrl().isEmpty()|| to.getImpactFactor()!=null && !to.getImpactFactor().isEmpty())
					{
						if(to.getIsApproved())
						{
						if (to.getId() > 0) {
							dep.setId(to.getId());
						}
					dep.setAuthorName(to.getAuthorName());
					dep.setDoi(to.getDoi());
					dep.setIsbn(to.getIsbn());
					dep.setIssueNumber(to.getIssueNumber());
					dep.setLanguage(to.getLanguage());
					//dep.setMonthYear(to.getMonthYear());
					dep.setDepartmentInstitution(to.getDepartmentInstitution());
					dep.setPlacePublication(to.getPlacePublication());
					dep.setCompanyInstitution(to.getCompanyInstitution());
					dep.setUrl(to.getUrl());
					dep.setImpactFactor(to.getImpactFactor());
					//dep.setDateAccepted(CommonUtil.ConvertStringToDate(to.getDateAccepted()));
					dep.setMonthYear(to.getDatePublished());
					//dep.setDateSent(CommonUtil.ConvertStringToDate(to.getDateSent()));
					dep.setNameJournal(to.getNameJournal());
					dep.setPagesFrom(to.getPagesFrom());
					dep.setPagesTo(to.getPagesTo());
					dep.setTitle(to.getTitle());
					dep.setVolumeNumber(to.getVolumeNumber());
					dep.setAcademicYear(to.getAcademicYear());
					dep.setType(to.getType());
					dep.setIsActive(true);
					if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
						Employee emp=new Employee();
						emp.setId(Integer.parseInt(to.getEmployeeId()));
						dep.setEmployeeId(emp);
						}
					dep.setCreatedBy(empResPubForm.getUserId());
					dep.setCreatedDate(new Date());
					dep.setModifiedBy(empResPubForm.getUserId());
					dep.setLastModifiedDate(new Date());
					dep.setApprovedDate(to.getApprovedDate());
					dep.setApproverComment(to.getApproverComment());
					if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
						Employee emp1=new Employee();
						emp1.setId(Integer.parseInt(to.getApproverId()));
						dep.setApproverId(emp1);
						}
					dep.setIsApproved(to.getIsApproved());
					dep.setIsArticleJournal(true);
					dep.setIsArticleInPeriodicals(false);
					dep.setIsBlog(false);
					dep.setIsBookMonographs(false);
					dep.setIsCasesNoteWorking(false);
					dep.setIsChapterArticleBook(false);
					dep.setIsConferencePresentation(false);
					dep.setIsFilmVideoDoc(false);
					dep.setIsOwnPhdMphilThesis(false);
					dep.setIsPhdMPhilThesisGuided(false);
					dep.setIsResearchProject(false);
					dep.setIsSeminarOrganized(false);
					dep.setIsInvitedTalk(false);
					dep.setIsSeminarAttended(false);
					dep.setIsWorkshopTraining(false);
					dep.setIsAwardsAchievementsOthers(false);
					dep.setIsRejected(to.getIsRejected());
					dep.setRejectDate(to.getRejectDate());
					dep.setRejectReason(to.getRejectReason());
					dep.setIsEmployee(true);
					empResPubDetails.add(dep);
					}
					}
				}
			  }
			}
		
			if (empResPubForm.getEmpArticlInPeriodicalsTO() != null			
					&& !empResPubForm.getEmpArticlInPeriodicalsTO().isEmpty()) {
				Iterator<EmpArticlInPeriodicalsTO> itr = empResPubForm
						.getEmpArticlInPeriodicalsTO().iterator();
				while (itr.hasNext()) {
					EmpArticlInPeriodicalsTO to = (EmpArticlInPeriodicalsTO) itr.next();
					EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
					if(to!=null)
					{
						if(to.getAuthorName()!=null && !to.getAuthorName().isEmpty()|| to.getDateMonthYear()!=null && !to.getDateMonthYear().isEmpty()||
								to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getIsbn()!=null && !to.getIsbn().isEmpty()|| to.getIssueNumber()!=null && !to.getIssueNumber().isEmpty()||
								to.getLanguage()!=null && !to.getLanguage().isEmpty()|| to.getNamePeriodical()!=null && !to.getNamePeriodical().isEmpty()||
								to.getTitle()!=null && !to.getTitle().isEmpty()|| to.getVolumeNumber()!=null && !to.getVolumeNumber().isEmpty())
						{
						if(to.getIsApproved())
						{
							if (to.getId() > 0) {
								dep.setId(to.getId());
							}
						dep.setAuthorName(to.getAuthorName());
						dep.setNamePeriodical(to.getNamePeriodical());
						dep.setIsbn(to.getIsbn());
						dep.setIssueNumber(to.getIssueNumber());
						dep.setLanguage(to.getLanguage());
						dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
						dep.setPagesFrom(to.getPagesFrom());
						dep.setPagesTo(to.getPagesTo());
						dep.setTitle(to.getTitle());
						dep.setVolumeNumber(to.getVolumeNumber());
						dep.setAcademicYear(to.getAcademicYear());
						dep.setType(to.getType());
						if((to.getType()!=null && !to.getType().isEmpty() && to.getType().equalsIgnoreCase("other")) && (to.getOtherTextArticle()!=null && !to.getOtherTextArticle().isEmpty())){
							dep.setOtherText(to.getOtherTextArticle());
						}
						dep.setIsActive(true);
						if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
							Employee emp=new Employee();
							emp.setId(Integer.parseInt(to.getEmployeeId()));
							dep.setEmployeeId(emp);
							}
						dep.setCreatedBy(empResPubForm.getUserId());
						dep.setCreatedDate(new Date());
						dep.setModifiedBy(empResPubForm.getUserId());
						dep.setLastModifiedDate(new Date());
						dep.setApprovedDate(to.getApprovedDate());
						dep.setApproverComment(to.getApproverComment());
						if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
							Employee emp1=new Employee();
							emp1.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp1);
							}
						dep.setIsApproved(to.getIsApproved());
						dep.setIsArticleJournal(false);
						dep.setIsArticleInPeriodicals(true);
						dep.setIsBlog(false);
						dep.setIsBookMonographs(false);
						dep.setIsCasesNoteWorking(false);
						dep.setIsChapterArticleBook(false);
						dep.setIsConferencePresentation(false);
						dep.setIsFilmVideoDoc(false);
						dep.setIsOwnPhdMphilThesis(false);
						dep.setIsPhdMPhilThesisGuided(false);
						dep.setIsResearchProject(false);
						dep.setIsSeminarOrganized(false);
						dep.setIsInvitedTalk(false);
						dep.setIsSeminarAttended(false);
						dep.setIsWorkshopTraining(false);
						dep.setIsAwardsAchievementsOthers(false);
						dep.setIsRejected(to.getIsRejected());
						dep.setRejectDate(to.getRejectDate());
						dep.setRejectReason(to.getRejectReason());
						dep.setIsEmployee(true);
						empResPubDetails.add(dep);
						}
						}
					}
					}
				}
				if (empResPubForm.getEmpBlogTO() != null			
						&& !empResPubForm.getEmpBlogTO().isEmpty()) {
					Iterator<EmpBlogTO> itr = empResPubForm
							.getEmpBlogTO().iterator();
					while (itr.hasNext()) {
						EmpBlogTO to = (EmpBlogTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getLanguage()!=null && !to.getLanguage().isEmpty()||
							to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| to.getNumberOfComments()!=null && !to.getNumberOfComments().isEmpty()||
							to.getSubject()!=null && !to.getSubject().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty()||
							to.getUrl()!=null && !to.getUrl().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setLanguage(to.getLanguage());
							dep.setNumberOfComments(to.getNumberOfComments());
							dep.setSubject(to.getSubject());
							dep.setUrl(to.getUrl());
							dep.setMonthYear(to.getMonthYear());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
							Employee emp1=new Employee();
							emp1.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp1);
							}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(true);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
						}
					}
				if (empResPubForm.getEmpBooksMonographsTO() != null			
						&& !empResPubForm.getEmpBooksMonographsTO().isEmpty()) {
					Iterator<EmpBooksMonographsTO> itr = empResPubForm
							.getEmpBooksMonographsTO().iterator();
					while (itr.hasNext()) {
						EmpBooksMonographsTO to = (EmpBooksMonographsTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getAuthorName()!=null && !to.getAuthorName().isEmpty()|| to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()||
							 to.getIsbn()!=null && !to.getIsbn().isEmpty()|| to.getLanguage()!=null && !to.getLanguage().isEmpty()||
							to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| 
							to.getTitle()!=null && !to.getTitle().isEmpty() || to.getTotalPages()!=null && !to.getTotalPages().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setLanguage(to.getLanguage());
							dep.setAuthorName(to.getAuthorName());
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setIsbn(to.getIsbn());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setMonthYear(to.getMonthYear());
							dep.setTitle(to.getTitle());
							dep.setTotalPages(to.getTotalPages());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setCoAuthored(to.getCoAuthored());
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(true);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}}
						  }
						}
					}
				if (empResPubForm.getEmpCasesNotesWorkingTO() != null			
						&& !empResPubForm.getEmpCasesNotesWorkingTO().isEmpty()) {
					Iterator<EmpCasesNotesWorkingTO> itr = empResPubForm
							.getEmpCasesNotesWorkingTO().iterator();
					while (itr.hasNext()) {
						EmpCasesNotesWorkingTO to = (EmpCasesNotesWorkingTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getAbstractObjectives()!=null && !to.getAbstractObjectives().isEmpty()|| to.getAuthorName()!=null && !to.getAuthorName().isEmpty()||
							  to.getCaseNoteWorkPaper()!=null && !to.getCaseNoteWorkPaper().isEmpty()|| to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getSponsors()!=null && !to.getSponsors().isEmpty()||
							  to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setAbstractObjectives(to.getAbstractObjectives());
							dep.setAuthorName(to.getAuthorName());
							dep.setCaseNoteWorkPaper(to.getCaseNoteWorkPaper());
							dep.setSponsors(to.getSponsors());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(true);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
						}
					}

				if (empResPubForm.getEmpChapterArticlBookTO() != null			
						&& !empResPubForm.getEmpChapterArticlBookTO().isEmpty()) {
					Iterator<EmpChapterArticlBookTO> itr = empResPubForm
							.getEmpChapterArticlBookTO().iterator();
					while (itr.hasNext()) {
						EmpChapterArticlBookTO to = (EmpChapterArticlBookTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getAuthorName()!=null && !to.getAuthorName().isEmpty()|| to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()||
							   to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getEditorsName()!=null && !to.getEditorsName().isEmpty()|| to.getIsbn()!=null && !to.getIsbn().isEmpty()||
							   to.getLanguage()!=null && !to.getLanguage().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()||
							   to.getTitle()!=null && !to.getTitle().isEmpty()|| to.getTitleChapterArticle()!=null && !to.getTitleChapterArticle().isEmpty()|| to.getTotalPages()!=null && !to.getTotalPages().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setAuthorName(to.getAuthorName());
							dep.setDoi(to.getDoi());
							dep.setEditorsName(to.getEditorsName());
							dep.setIsbn(to.getIsbn());
							dep.setLanguage(to.getLanguage());
							dep.setMonthYear(to.getMonthYear());
							dep.setPagesFrom(to.getPagesFrom());
							dep.setPagesTo(to.getPagesTo());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setTitle(to.getTitle());
							dep.setTitleChapterArticle(to.getTitleChapterArticle());
							dep.setTotalPages(to.getTotalPages());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setCoAuthored(to.getCoAuthored());
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(true);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
						}
					}
				if (empResPubForm.getEmpConferencePresentationTO() != null			
						&& !empResPubForm.getEmpConferencePresentationTO().isEmpty()) {
					Iterator<EmpConferencePresentationTO> itr = empResPubForm
							.getEmpConferencePresentationTO().iterator();
					while (itr.hasNext()) {
						EmpConferencePresentationTO to = (EmpConferencePresentationTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()|| to.getDoi()!=null && !to.getDoi().isEmpty()||
							  to.getLanguage()!=null && !to.getLanguage().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| to.getNameConferencesSeminar()!=null && !to.getNameConferencesSeminar().isEmpty()||
							  to.getNameTalksPresentation()!=null && !to.getNameTalksPresentation().isEmpty()|| to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()||
							  to.getTitle()!=null && !to.getTitle().isEmpty()|| to.getTypeOfPgm()!=null && !to.getTypeOfPgm().isEmpty() || to.getAbstractDetails()!=null && !to.getAbstractDetails().isEmpty() 
							  || to.getType()!=null && !to.getType().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setLanguage(to.getLanguage());
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
							//dep.setMonthYear(to.getMonthYear());
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							dep.setNameTalksPresentation(to.getNameTalksPresentation());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setTitle(to.getTitle());
							dep.setAbstracts(to.getAbstractDetails());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setTypeOfPgm(to.getTypeOfPgm());
							if((to.getTypeOfPgm()!=null && !to.getTypeOfPgm().isEmpty() && to.getTypeOfPgm().equalsIgnoreCase("other")) && (to.getOtherTextConf()!=null && !to.getOtherTextConf().isEmpty())){
								dep.setOtherText(to.getOtherTextConf());
							}
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(true);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
						}
					}
				if (empResPubForm.getEmpInvitedTalkTO() != null			
						&& !empResPubForm.getEmpInvitedTalkTO().isEmpty()) {
					Iterator<EmpInvitedTalkTO> itr = empResPubForm
							.getEmpInvitedTalkTO().iterator();
					while (itr.hasNext()) {
						EmpInvitedTalkTO to = (EmpInvitedTalkTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()|| to.getDoi()!=null && !to.getDoi().isEmpty()||
							  to.getLanguage()!=null && !to.getLanguage().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()|| to.getNameConferencesSeminar()!=null && !to.getNameConferencesSeminar().isEmpty()||
							  to.getNameTalksPresentation()!=null && !to.getNameTalksPresentation().isEmpty()|| to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty()||
							  to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setNameOfPgm(to.getNameOfPgm());
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setLanguage(to.getLanguage());
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
						//	dep.setMonthYear(to.getMonthYear());
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							dep.setNameTalksPresentation(to.getNameTalksPresentation());
							dep.setPlacePublication(to.getPlacePublication());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(true);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
						}
					}
				if (empResPubForm.getEmpFilmVideosDocTO() != null			
						&& !empResPubForm.getEmpFilmVideosDocTO().isEmpty()) {
					Iterator<EmpFilmVideosDocTO> itr = empResPubForm
							.getEmpFilmVideosDocTO().iterator();
					while (itr.hasNext()) {
						EmpFilmVideosDocTO to = (EmpFilmVideosDocTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getAspectRatio()!=null && !to.getAspectRatio().isEmpty()|| to.getAudioFormat()!=null && !to.getAudioFormat().isEmpty()||
							   to.getCopyrights()!=null && !to.getCopyrights().isEmpty()|| to.getCredits()!=null && !to.getCredits().isEmpty()|| to.getDiscFormat()!=null && !to.getDiscFormat().isEmpty()
							   ||to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getGenre()!=null && !to.getGenre().isEmpty() || to.getLanguage()!=null && !to.getLanguage().isEmpty()||
							   to.getProducer()!=null && !to.getProducer().isEmpty()|| to.getRunningTime()!=null && !to.getRunningTime().isEmpty()|| to.getSubtitles()!=null && !to.getSubtitles().isEmpty()||
							   to.getTechnicalFormat()!=null && !to.getTechnicalFormat().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setAspectRatio(to.getAspectRatio());
							dep.setAudioFormat(to.getAudioFormat());
							dep.setCopyrights(to.getCopyrights());
							dep.setCredits(to.getCredits());
							dep.setDiscFormat(to.getDiscFormat());
							dep.setGenre(to.getGenre());
							dep.setLanguage(to.getLanguage());
							dep.setProducer(to.getProducer());
							dep.setRunningTime(to.getRunningTime());
							dep.setSubtitles(to.getSubtitles());
							dep.setTechnicalFormat(to.getTechnicalFormat());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(true);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
						}
					}
				if (empResPubForm.getEmpResearchProjectTO() != null			
						&& !empResPubForm.getEmpResearchProjectTO().isEmpty()) {
					Iterator<EmpResProjectTO> itr = empResPubForm
							.getEmpResearchProjectTO().iterator();
					while (itr.hasNext()) {
						EmpResProjectTO to = (EmpResProjectTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{	
							if(to.getAbstractObjectives()!=null && !to.getAbstractObjectives().isEmpty()|| to.getInvestigators()!=null && !to.getInvestigators().isEmpty()||
								to.getSponsors()!=null && !to.getSponsors().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setAbstractObjectives(to.getAbstractObjectives());
							dep.setInvestigators(to.getInvestigators());
							dep.setSponsors(to.getSponsors());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setInternalExternal(to.getInternalExternal());
							dep.setAmountGranted(to.getAmountGranted());
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(true);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
						}
					}
				
				if (empResPubForm.getEmpOwnPhdMPilThesisTO() != null			
						&& !empResPubForm.getEmpOwnPhdMPilThesisTO().isEmpty()) {
					Iterator<EmpOwnPhdMPilThesisTO> itr = empResPubForm
							.getEmpOwnPhdMPilThesisTO().iterator();
					while (itr.hasNext()) {
						EmpOwnPhdMPilThesisTO to = (EmpOwnPhdMPilThesisTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty()|| to.getMonthYear()!=null && !to.getMonthYear().isEmpty()||
							  to.getNameGuide()!=null && !to.getNameGuide().isEmpty() || to.getPlace()!=null && !to.getPlace().isEmpty()|| to.getSubject()!=null && !to.getSubject().isEmpty()||
							  to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setMonthYear(to.getMonthYear());
							dep.setNameGuide(to.getNameGuide());
							dep.setPlacePublication(to.getPlace());
							dep.setSubject(to.getSubject());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							dep.setSubmissionDate(CommonUtil.ConvertStringToDate(to.getSubmissionDate()));
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(true);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
					}
					}
				
				if (empResPubForm.getEmpPhdMPhilThesisGuidedTO() != null			
						&& !empResPubForm.getEmpPhdMPhilThesisGuidedTO().isEmpty()) {
					Iterator<EmpPhdMPhilThesisGuidedTO> itr = empResPubForm
							.getEmpPhdMPhilThesisGuidedTO().iterator();
					while (itr.hasNext()) {
						EmpPhdMPhilThesisGuidedTO to = (EmpPhdMPhilThesisGuidedTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty() || to.getMonthYear()!=null && !to.getMonthYear().isEmpty()
							|| to.getNameStudent()!=null && !to.getNameStudent().isEmpty()||to.getPlace()!=null && !to.getPlace().isEmpty()||
							to.getSubject()!=null && !to.getSubject().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setCompanyInstitution(to.getCompanyInstitution());
							dep.setMonthYear(to.getMonthYear());
							dep.setNameStudent(to.getNameStudent());
							dep.setPlacePublication(to.getPlace());
							dep.setSubject(to.getSubject());
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							/*code modified by sudhir */
							//dep.setGuidedAdjudicated(to.getGuidedAbudjicated());
							dep.setGuidedAdjudicated("Guided");
							/*-----------------*/
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(true);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(false);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
						}
					}
				if (empResPubForm.getEmpSeminarsOrganizedTO() != null			
						&& !empResPubForm.getEmpSeminarsOrganizedTO().isEmpty()) {
					Iterator<EmpSeminarsOrganizedTO> itr = empResPubForm
							.getEmpSeminarsOrganizedTO().iterator();
					while (itr.hasNext()) {
						EmpSeminarsOrganizedTO to = (EmpSeminarsOrganizedTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getDoi()!=null && !to.getDoi().isEmpty()|| to.getLanguage()!=null && !to.getLanguage().isEmpty() || 
							to.getDateMonthYear()!=null && !to.getDateMonthYear().isEmpty() || to.getNameConferencesSeminar()!=null && !to.getNameConferencesSeminar().isEmpty()||
							to.getNameOrganisers()!=null && !to.getNameOrganisers().isEmpty()|| to.getPlace()!=null && !to.getPlace().isEmpty()
							|| to.getResoursePerson()!=null && !to.getResoursePerson().isEmpty()|| to.getSponsors()!=null && !to.getSponsors().isEmpty())
							{
								if(to.getIsApproved())
								{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setLanguage(to.getLanguage());
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							dep.setPlacePublication(to.getPlace());
							dep.setNameOrganisers(to.getNameOrganisers());
							dep.setResoursePerson(to.getResoursePerson());
							dep.setSponsors(to.getSponsors());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setIsActive(true);
							if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
								Employee emp=new Employee();
								emp.setId(Integer.parseInt(to.getEmployeeId()));
								dep.setEmployeeId(emp);
								}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}
							dep.setIsApproved(to.getIsApproved());
							dep.setIsArticleJournal(false);
							dep.setIsArticleInPeriodicals(false);
							dep.setIsBlog(false);
							dep.setIsBookMonographs(false);
							dep.setIsCasesNoteWorking(false);
							dep.setIsChapterArticleBook(false);
							dep.setIsConferencePresentation(false);
							dep.setIsFilmVideoDoc(false);
							dep.setIsOwnPhdMphilThesis(false);
							dep.setIsPhdMPhilThesisGuided(false);
							dep.setIsResearchProject(false);
							dep.setIsSeminarOrganized(true);
							dep.setIsInvitedTalk(false);
							dep.setIsSeminarAttended(false);
							dep.setIsWorkshopTraining(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(to.getIsRejected());
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							dep.setIsEmployee(true);
							empResPubDetails.add(dep);
							}
							}
						}
						}
					}
				 
						if (empResPubForm.getEmpConferenceSeminarsAttendedTO() != null			
								&& !empResPubForm.getEmpConferenceSeminarsAttendedTO().isEmpty()) {
							Iterator<EmpConferenceSeminarsAttendedTO> itr = empResPubForm
									.getEmpConferenceSeminarsAttendedTO().iterator();
							while (itr.hasNext()) {
								EmpConferenceSeminarsAttendedTO to = (EmpConferenceSeminarsAttendedTO) itr.next();
								EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
								if(to!=null)
								{
									if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()|| to.getEndOfPgm()!=null && !to.getEndOfPgm().isEmpty()|| to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty() || 
											to.getDuration()!=null && !to.getDuration().isEmpty() || to.getOrganisedBy()!=null && !to.getOrganisedBy().isEmpty())
									{
										if(to.getIsApproved())
										{
										if (to.getId() > 0) {
											dep.setId(to.getId());
										}
									dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
									dep.setEndOfPgm(CommonUtil.ConvertStringToDate(to.getEndOfPgm()));
									dep.setNameConferencesSeminar(to.getNameOfPgm());
									dep.setNameOrganisers(to.getOrganisedBy());
									dep.setRunningTime(to.getDuration());
									dep.setAcademicYear(to.getAcademicYear());
									dep.setType(to.getType());
									dep.setIsActive(true);
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp=new Employee();
										emp.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp);
										}
									dep.setCreatedBy(empResPubForm.getUserId());
									dep.setCreatedDate(new Date());
									dep.setModifiedBy(empResPubForm.getUserId());
									dep.setLastModifiedDate(new Date());
									dep.setApprovedDate(to.getApprovedDate());
									dep.setApproverComment(to.getApproverComment());
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp1);
										}
									dep.setIsApproved(to.getIsApproved());
									dep.setIsArticleJournal(false);
									dep.setIsArticleInPeriodicals(false);
									dep.setIsBlog(false);
									dep.setIsBookMonographs(false);
									dep.setIsCasesNoteWorking(false);
									dep.setIsChapterArticleBook(false);
									dep.setIsConferencePresentation(false);
									dep.setIsFilmVideoDoc(false);
									dep.setIsOwnPhdMphilThesis(false);
									dep.setIsPhdMPhilThesisGuided(false);
									dep.setIsResearchProject(false);
									dep.setIsSeminarOrganized(false);
									dep.setIsInvitedTalk(false);
									dep.setIsSeminarAttended(true);
									dep.setIsWorkshopTraining(false);
									dep.setIsAwardsAchievementsOthers(false);
									dep.setIsRejected(to.getIsRejected());
									dep.setRejectDate(to.getRejectDate());
									dep.setRejectReason(to.getRejectReason());
									dep.setIsEmployee(true);
									empResPubDetails.add(dep);
									}
									}
								}
								}
							}
									
						if (empResPubForm.getEmpWorkshopsTO() != null			
								&& !empResPubForm.getEmpWorkshopsTO().isEmpty()) {
							Iterator<EmpWorkshopsFdpTrainingTO> itr = empResPubForm
									.getEmpWorkshopsTO().iterator();
							while (itr.hasNext()) {
								EmpWorkshopsFdpTrainingTO to = (EmpWorkshopsFdpTrainingTO) itr.next();
								EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
								if(to!=null)
								{if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()|| to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty() || 
										to.getDuration()!=null && !to.getDuration().isEmpty() || to.getOrganisedBy()!=null && !to.getOrganisedBy().isEmpty())
									{
										if(to.getIsApproved())
										{
										if (to.getId() > 0) {
											dep.setId(to.getId());
										}
									dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
									dep.setNameConferencesSeminar(to.getNameOfPgm());
									dep.setNameOrganisers(to.getOrganisedBy());
									dep.setRunningTime(to.getDuration());
									dep.setAcademicYear(to.getAcademicYear());
									dep.setType(to.getType());
									dep.setIsActive(true);
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp=new Employee();
										emp.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp);
										}
									dep.setCreatedBy(empResPubForm.getUserId());
									dep.setCreatedDate(new Date());
									dep.setModifiedBy(empResPubForm.getUserId());
									dep.setLastModifiedDate(new Date());
									dep.setApprovedDate(to.getApprovedDate());
									dep.setApproverComment(to.getApproverComment());
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp1);
										}
									dep.setIsApproved(to.getIsApproved());
									dep.setIsArticleJournal(false);
									dep.setIsArticleInPeriodicals(false);
									dep.setIsBlog(false);
									dep.setIsBookMonographs(false);
									dep.setIsCasesNoteWorking(false);
									dep.setIsChapterArticleBook(false);
									dep.setIsConferencePresentation(false);
									dep.setIsFilmVideoDoc(false);
									dep.setIsOwnPhdMphilThesis(false);
									dep.setIsPhdMPhilThesisGuided(false);
									dep.setIsResearchProject(false);
									dep.setIsSeminarOrganized(false);
									dep.setIsInvitedTalk(false);
									dep.setIsSeminarAttended(false);
									dep.setIsWorkshopTraining(true);
									dep.setIsAwardsAchievementsOthers(false);
									dep.setIsRejected(to.getIsRejected());
									dep.setRejectDate(to.getRejectDate());
									dep.setRejectReason(to.getRejectReason());
									dep.setIsEmployee(true);
									empResPubDetails.add(dep);
									}
									}
								}
								}
							}
						
						if (empResPubForm.getEmpAwardsAchievementsOthersTO() != null			
								&& !empResPubForm.getEmpAwardsAchievementsOthersTO().isEmpty()) {
							Iterator<EmpAwardsAchievementsOthersTO> itr = empResPubForm
									.getEmpAwardsAchievementsOthersTO().iterator();
							while (itr.hasNext()) {
								EmpAwardsAchievementsOthersTO to = (EmpAwardsAchievementsOthersTO) itr.next();
								EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
								if(to!=null)
								{if(to.getName()!=null && !to.getName().isEmpty()|| to.getDescription()!=null && !to.getDescription().isEmpty() || 
										to.getPlace()!=null && !to.getPlace().isEmpty() || to.getMonthYear()!=null && !to.getMonthYear().isEmpty())
									{
										if(to.getIsApproved())
										{
										if (to.getId() > 0) {
											dep.setId(to.getId());
										}
									dep.setMonthYear(to.getMonthYear());
									dep.setName(to.getName());
									dep.setDescription(to.getDescription());
									dep.setPlace(to.getPlace());
									dep.setAcademicYear(to.getAcademicYear());
									dep.setOrganisationAwarded(to.getOrganisationAwarded());
									dep.setIsActive(true);
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp=new Employee();
										emp.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp);
										}
									dep.setCreatedBy(empResPubForm.getUserId());
									dep.setCreatedDate(new Date());
									dep.setModifiedBy(empResPubForm.getUserId());
									dep.setLastModifiedDate(new Date());
									dep.setApprovedDate(to.getApprovedDate());
									dep.setApproverComment(to.getApproverComment());
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp1);
										}
									dep.setIsApproved(to.getIsApproved());
									dep.setIsArticleJournal(false);
									dep.setIsArticleInPeriodicals(false);
									dep.setIsBlog(false);
									dep.setIsBookMonographs(false);
									dep.setIsCasesNoteWorking(false);
									dep.setIsChapterArticleBook(false);
									dep.setIsConferencePresentation(false);
									dep.setIsFilmVideoDoc(false);
									dep.setIsOwnPhdMphilThesis(false);
									dep.setIsPhdMPhilThesisGuided(false);
									dep.setIsResearchProject(false);
									dep.setIsSeminarOrganized(false);
									dep.setIsInvitedTalk(false);
									dep.setIsSeminarAttended(false);
									dep.setIsWorkshopTraining(false);
									dep.setIsAwardsAchievementsOthers(true);
									dep.setIsRejected(to.getIsRejected());
									dep.setRejectDate(to.getRejectDate());
									dep.setRejectReason(to.getRejectReason());
									dep.setIsEmployee(true);
									empResPubDetails.add(dep);
									}
									}
								}
								}
							}
						
					
		return empResPubDetails;
	}
	
	
	public void convertBoToFormEmployee(Employee emp,
			EmpResPubPendApprovalForm  empResPubForm) throws Exception {
		
		if(emp!=null)
		{
			if (emp.getEmpImages() != null && !emp.getEmpImages().isEmpty()) {
				Iterator<EmpImages> itr=emp.getEmpImages().iterator();
				while (itr.hasNext()) {
					EmpImages bo =itr.next();
					
					if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
						empResPubForm.setResearchPhotoBytes(bo.getEmpPhoto());
						break;
					}
				}
				
			}else
			{
				empResPubForm.setResearchPhotoBytes(null);
			}
			if(emp.getFingerPrintId()!=null && !emp.getFingerPrintId().isEmpty()){
				empResPubForm.setFingerprintId(emp.getFingerPrintId());
			}
			if(emp.getFirstName()!=null && !emp.getFirstName().isEmpty()){
				empResPubForm.setEmpName(emp.getFirstName());
			}
			if (emp.getDepartment() != null
					&& emp.getDepartment().getId() > 0) {
				empResPubForm.setEmpDepartment(emp.getDepartment().getName());
			}
		}
	}
	

}
