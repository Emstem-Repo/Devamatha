package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.bo.employee.EmpResearchPublicMaster;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.employee.GuestImages;
import com.kp.cms.bo.employee.HodApprover;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
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
import com.kp.cms.to.employee.EmpResPublicMasterTO;
import com.kp.cms.to.employee.EmpSeminarsOrganizedTO;
import com.kp.cms.to.employee.EmpWorkshopsFdpTrainingTO;
import com.kp.cms.transactions.employee.IEmpResPubDetailsTransaction;
import com.kp.cms.transactionsimpl.employee.EmpResPubDetailsImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class EmpResPubDetailsHelper {
	
private static volatile EmpResPubDetailsHelper instance=null;
	/**
	 * 
	 */
	private EmpResPubDetailsHelper(){
	}
	/**
	 * @return
	 */
	public static EmpResPubDetailsHelper getInstance(){
		if(instance==null){
			instance=new EmpResPubDetailsHelper();
		}
		return instance;
	}
	IEmpResPubDetailsTransaction empTransaction=EmpResPubDetailsImpl.getInstance();
	
	public void convertBoToForm(List<EmpResearchPublicDetails> empApplicantDetails,
			EmpResPubDetailsForm empResPubForm) throws Exception {
		    int year = CurrentAcademicYear.getInstance().getAcademicyear();
		
			List<EmpArticlInPeriodicalsTO> empArticlInPeriodicalsTO = new ArrayList<EmpArticlInPeriodicalsTO>();
			List<EmpArticleJournalsTO> empArticleJournalsTO = new ArrayList<EmpArticleJournalsTO>();
			List<EmpBlogTO> empBlogTO= new ArrayList<EmpBlogTO>();
			List<EmpBooksMonographsTO> empBooksMonographsTO= new ArrayList<EmpBooksMonographsTO>();
			List<EmpCasesNotesWorkingTO> empCasesNotesWorkingTO= new ArrayList<EmpCasesNotesWorkingTO>();
			List<EmpChapterArticlBookTO> empChapterArticlBookTO= new ArrayList<EmpChapterArticlBookTO>();
			List<EmpConferencePresentationTO> empConferencePresentationTO= new ArrayList<EmpConferencePresentationTO>();
			List<EmpInvitedTalkTO> empInvitedTalkTO= new ArrayList<EmpInvitedTalkTO>();
			List<EmpFilmVideosDocTO> empFilmVideosDocTO= new ArrayList<EmpFilmVideosDocTO>();
			List<EmpOwnPhdMPilThesisTO> empOwnPhdMPilThesisTO= new ArrayList<EmpOwnPhdMPilThesisTO>();
			List<EmpPhdMPhilThesisGuidedTO> empPhdMPhilThesisGuidedTO= new ArrayList<EmpPhdMPhilThesisGuidedTO>();
			List<EmpSeminarsOrganizedTO> empSeminarsOrganizedTO= new ArrayList<EmpSeminarsOrganizedTO>();
			List<EmpResProjectTO> empResProjectTO= new ArrayList<EmpResProjectTO>();
			List<EmpConferenceSeminarsAttendedTO> empSeminarsAttendedTO= new ArrayList<EmpConferenceSeminarsAttendedTO>();
			List<EmpWorkshopsFdpTrainingTO> empWorkshopsTrainingTOs= new ArrayList<EmpWorkshopsFdpTrainingTO>();
			List<EmpAwardsAchievementsOthersTO> empAwardsAchievTOs= new ArrayList<EmpAwardsAchievementsOthersTO>();
			
			if(empApplicantDetails!=null && !empApplicantDetails.isEmpty())
			{
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
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
							empto.setApproverComment(empAcheiv.getApproverComment());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
							empto.setAcademicYear(empAcheiv.getAcademicYear());
						}
						else
						{
							empto.setAcademicYear("2012");
						}
						if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
							empto.setType(empAcheiv.getType());
							if(empAcheiv.getType().equalsIgnoreCase("other") &&(empAcheiv.getOtherText()!=null && ! empAcheiv.getOtherText().isEmpty())){
								empto.setOtherTextArticle(empAcheiv.getOtherText());
							}
						}
						
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if (empAcheiv.getApproverId()!=null)
							{
							if(empAcheiv.getApproverId().getId()> 0) {
							
							empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
						}
						}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsArticleInPeriodicals(empAcheiv.getIsArticleInPeriodicals());
						
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
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
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
							}
						}
						if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
							empto.setRejectReason(empAcheiv.getRejectReason());
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							empPreviousOrgTo.setIsRejected(false);
							
							empPreviousOrgTo.setIsArticleInPeriodicals(false);
							
							
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
							if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
								empto.setDatePublished(empAcheiv.getMonthYear());
							}
							/*if (empAcheiv.getDateSent()!=null && StringUtils.isNotEmpty(empAcheiv.getDateSent().toString())) {
								empto.setDateSent(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateSent().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}*/

							if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
								empto.setTitle(empAcheiv.getTitle());
							}
							if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage()) ) {
								empto.setLanguage(empAcheiv.getLanguage());
							}
							if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
								empto.setMonthYear(empAcheiv.getMonthYear());
							}
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
							if (empAcheiv.getApproverComment()!=null &&StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
								empto.setApprovedDate(empAcheiv.getApprovedDate());
								empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							}
							if (empAcheiv.getApproverId()!=null)
								{
								if(empAcheiv.getApproverId().getId()> 0) {
								
								empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
							}
							}
							if (empAcheiv.getIsActive()!=null) {
								empto.setIsActive(empAcheiv.getIsActive());
							}
							empto.setIsArticleJournal(empAcheiv.getIsArticleJournal());
							if (empAcheiv.getIsApproved()!=null) {
								empto.setIsApproved(empAcheiv.getIsApproved());
							}
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
							empPreviousOrgTo.setDepartmentInstitution("");
							empPreviousOrgTo.setCompanyInstitution("");
							empPreviousOrgTo.setPlacePublication("");
							empPreviousOrgTo.setUrl("");
							empPreviousOrgTo.setImpactFactor("");
							//empPreviousOrgTo.setDateAccepted("");
						//	empPreviousOrgTo.setDateSent("");
							empPreviousOrgTo.setDatePublished("");
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							empPreviousOrgTo.setIsArticleJournal(false);
							empPreviousOrgTo.setIsRejected(false);
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
						if ( empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment()) ) {
							empto.setApproverComment(empAcheiv.getApproverComment());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
							empto.setAcademicYear(empAcheiv.getAcademicYear());
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if (empAcheiv.getApproverId()!=null)
							{
							if(empAcheiv.getApproverId().getId()> 0) {
							
							empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
						}
						}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsBlog(empAcheiv.getIsBlog());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							empPreviousOrgTo.setIsBlog(false);
							empPreviousOrgTo.setIsRejected(false);
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
						int a=empAcheiv.getTitle().length();
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
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment()) ) {
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						}
						if (empAcheiv.getApproverId()!=null)
							{
							if(empAcheiv.getApproverId().getId()> 0) {
							
							empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
						}
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
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							empPreviousOrgTo.setIsBookMonographs(false);
							empPreviousOrgTo.setIsRejected(false);
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
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
							empto.setApproverComment(empAcheiv.getApproverComment());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
							empto.setAcademicYear(empAcheiv.getAcademicYear());
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsCasesNoteWorking(empAcheiv.getIsCasesNoteWorking());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							empPreviousOrgTo.setIsCasesNoteWorking(false);
							empPreviousOrgTo.setIsRejected(false);
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsChapterArticleBook(empAcheiv.getIsChapterArticleBook());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							empPreviousOrgTo.setIsChapterArticleBook(false);
							empPreviousOrgTo.setIsRejected(false);
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
					if (empAcheiv.getDateMonthYear()!=null &&  StringUtils.isNotEmpty(empAcheiv.getDateMonthYear().toString()) ) {
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
					if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment()) ) {
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
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsConferencePresentation(empAcheiv.getIsConferencePresentation());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
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
						empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						empPreviousOrgTo.setIsConferencePresentation(false);
						empPreviousOrgTo.setIsRejected(false);
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
					/*if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
						empto.setMonthYear(empAcheiv.getMonthYear());
					}*/
					if (empAcheiv.getDateMonthYear()!=null &&  StringUtils.isNotEmpty(empAcheiv.getDateMonthYear().toString()) ) {
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
					if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment()) ) {
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
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsInvitedTalk(empAcheiv.getIsInvitedTalk());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
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
						empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						empPreviousOrgTo.setIsInvitedTalk(false);
						empPreviousOrgTo.setIsRejected(false);
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
					if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
						empto.setApproverComment(empAcheiv.getApproverComment());
					}
					if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
						empto.setAcademicYear(empAcheiv.getAcademicYear());
					}
					if (empAcheiv.getAspectRatio()!=null && StringUtils.isNotEmpty(empAcheiv.getAspectRatio())) {
						empto.setAspectRatio(empAcheiv.getAspectRatio());
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsFilmVideoDoc(empAcheiv.getIsFilmVideoDoc());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
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
						empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						empPreviousOrgTo.setIsFilmVideoDoc(false);
						empPreviousOrgTo.setIsRejected(false);
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
					if (empAcheiv.getSubmissionDate()!=null &&  StringUtils.isNotEmpty(empAcheiv.getSubmissionDate().toString()) ) {
						empto.setSubmissionDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getSubmissionDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if(empAcheiv.getApprovedDate()!=null)
					{
					if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsOwnPhdMphilThesis(empAcheiv.getIsOwnPhdMphilThesis());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}	if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
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
						empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						empPreviousOrgTo.setIsOwnPhdMphilThesis(false);
						empPreviousOrgTo.setIsRejected(false);
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
					if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsPhdMPhilThesisGuided(empAcheiv.getIsPhdMPhilThesisGuided());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
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
						empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						empPreviousOrgTo.setIsPhdMPhilThesisGuided(false);
						empPreviousOrgTo.setIsRejected(false);
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
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsSeminarOrganized(empAcheiv.getIsSeminarOrganized());
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
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
						empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						empPreviousOrgTo.setIsSeminarOrganized(false);
						empPreviousOrgTo.setIsRejected(false);
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
					if (empAcheiv.getInvestigators()!=null && StringUtils.isNotEmpty(empAcheiv.getInvestigators())) {
						empto.setInvestigators(empAcheiv.getInvestigators());
					}
					if (empAcheiv.getSponsors()!=null && StringUtils.isNotEmpty(empAcheiv.getSponsors())) {
						empto.setSponsors(empAcheiv.getSponsors());
					}
					if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle()) ) {
						int a=empAcheiv.getTitle().length();
						empto.setTitle(empAcheiv.getTitle());
					}
					if (empAcheiv.getAbstractObjectives()!=null && StringUtils.isNotEmpty(empAcheiv.getAbstractObjectives())) {
						empto.setAbstractObjectives(empAcheiv.getAbstractObjectives());
					}
					if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsResearchProject(empAcheiv.getIsResearchProject());
					
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					
					empResProjectTO.add(empto);
					if(empResProjectTO==null || empResProjectTO.isEmpty())
					{
						EmpResProjectTO empPreviousOrgTo=new EmpResProjectTO();
						empPreviousOrgTo.setAbstractObjectives("");
						empPreviousOrgTo.setInvestigators("");
						empPreviousOrgTo.setSponsors("");
						empPreviousOrgTo.setTitle("");
						empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						empPreviousOrgTo.setIsResearchProject(false);
						empPreviousOrgTo.setIsRejected(false);
						empResProjectTO.add(empPreviousOrgTo);
					}
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
					if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsSeminarAttended(empAcheiv.getIsSeminarAttended());
					
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
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
						empPreviousOrgTo.setEndOfPgm("");
						empPreviousOrgTo.setDuration("");
						empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						empPreviousOrgTo.setIsSeminarAttended(false);
						empPreviousOrgTo.setIsRejected(false);
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
					if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsWorkshopTraining(empAcheiv.getIsWorkshopTraining());
					
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
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
						empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						empPreviousOrgTo.setIsWorkshopTraining(false);
						empPreviousOrgTo.setIsRejected(false);
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
					if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
						empto.setMonthYear(empAcheiv.getMonthYear());
					}
					if (empAcheiv.getPlace()!=null && StringUtils.isNotEmpty(empAcheiv.getPlace()) ) {
						
						empto.setPlace(empAcheiv.getPlace());
					}
					
					if (empAcheiv.getName()!=null && StringUtils.isNotEmpty(empAcheiv.getName())) {
						empto.setName(empAcheiv.getName());
					}
					if (empAcheiv.getDescription()!=null && StringUtils.isNotEmpty(empAcheiv.getDescription())) {
						empto.setDescription(empAcheiv.getDescription());
					}
					if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
						empto.setApprovedDate(empAcheiv.getApprovedDate());
						empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
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
					if (empAcheiv.getIsActive()!=null) {
						empto.setIsActive(empAcheiv.getIsActive());
					}
					empto.setIsAwardsAchievementsOthers(empAcheiv.getIsAwardsAchievementsOthers());
					
					if (empAcheiv.getIsApproved()!=null) {
						empto.setIsApproved(empAcheiv.getIsApproved());
					}
					if(empAcheiv.getIsRejected()!=null){
						empto.setIsRejected(empAcheiv.getIsRejected());
					}
					if(empAcheiv.getRejectDate()!=null){
						if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
							empto.setRejectDate(empAcheiv.getRejectDate());
						}
					}
					if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
						empto.setRejectReason(empAcheiv.getRejectReason());
					}
					
					empAwardsAchievTOs.add(empto);
					if(empAwardsAchievTOs==null || empAwardsAchievTOs.isEmpty())
					{
					    EmpAwardsAchievementsOthersTO empAwardsTo=new EmpAwardsAchievementsOthersTO();
					    empAwardsTo.setName("");
					    empAwardsTo.setPlace("");
					    empAwardsTo.setDescription("");
					    empAwardsTo.setMonthYear("");
					    empAwardsTo.setAcademicYear(String.valueOf(year));
					    empAwardsTo.setIsAwardsAchievementsOthers(false);
					    empAwardsTo.setIsRejected(false);
					    empAwardsAchievTOs.add(empAwardsTo);
					}
					empResPubForm.setEmpAwardsAchievementsOthersTO(empAwardsAchievTOs);
			}
			}
		}
		
		
	}
	}
	@SuppressWarnings("deprecation")
	public List<EmpResearchPublicDetails> convertFormToBo(EmpResPubDetailsForm empResPubForm,ActionErrors errors) throws Exception {
		 boolean splChar=false;
		 boolean dateValid=false;
		 String errMsg="";
		 boolean err=false;
		 List<EmpResearchPublicDetails> empResPubDetails = new ArrayList<EmpResearchPublicDetails>();
		 Employee emp=empTransaction.getEmployeeIdFromUserId(empResPubForm);
		 emp.setId(emp.getId());
		 empResPubForm.setEmployeeEmailId(emp.getWorkEmail());
		 HodApprover approverId=empTransaction.getApproverIdFromEmpId(emp.getId());
		 Employee emp1=new Employee();
		 if(approverId!=null)
		{
		 emp1.setId(approverId.getApprover().getId());
		 String approverEmail=empTransaction.getApproverEmailId(emp1.getId());
		 empResPubForm.setApproverEmailId(approverEmail);
		 empResPubForm.setFingerPrintId(emp.getFingerPrintId());
		/* errMsg=empResPubForm.getErrMsg();
		 if(errMsg==null || errMsg.isEmpty()){
       		errMsg = "Special characters allowed are ([].\\-#,@():;&!?\'\"). Special characters typed in following Fields:-";
*/       		if (empResPubForm.getSubmitName().equalsIgnoreCase("ArticleJournals")) {
		 if (empResPubForm.getEmpArticleJournalsTO() != null			
				&& !empResPubForm.getEmpArticleJournalsTO().isEmpty()) {
			Iterator<EmpArticleJournalsTO> itr = empResPubForm
					.getEmpArticleJournalsTO().iterator();
			while (itr.hasNext()) {
				
				EmpArticleJournalsTO to = (EmpArticleJournalsTO) itr.next();
				EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
				if(to!=null)
				{
					if(to.getAuthorName()!=null && !to.getAuthorName().isEmpty()|| to.getIsbn()!=null && !to.getIsbn().isEmpty()|| 
					  to.getIssueNumber()!=null && !to.getIssueNumber().isEmpty()|| to.getLanguage()!=null && !to.getLanguage().isEmpty()||
					  to.getNameJournal()!=null && !to.getNameJournal().isEmpty()|| to.getTitle()!=null && !to.getTitle().isEmpty()||
					  to.getDoi()!=null && !to.getDoi().isEmpty() || to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty() ||
					  to.getDepartmentInstitution()!=null && !to.getDepartmentInstitution().isEmpty() || 
					  to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty() || to.getUrl()!=null && !to.getUrl().isEmpty()|| 
					  to.getImpactFactor()!=null && !to.getImpactFactor().isEmpty() ||
				 to.getDatePublished()!=null && !to.getDatePublished().isEmpty())
					{
						if (to.getId() > 0) {
							dep.setId(to.getId());
						}
						
					/*splChar=splCharValidationNew(to.getAuthorName());
					if(!splChar){
						errMsg = errMsg + "Author name";
					err=true;
					}
					else*/
					dep.setAuthorName(to.getAuthorName());
					/*if(to.getDoi()!=null && to.getDoi()!="" && !to.getDoi().isEmpty()){
					dateValid=DateValidation(to.getDoi());
					if(!dateValid){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.doi1"," is not valid format.Please Enter DD/MM/YYY"));
					}
					else*/
					dep.setDoi(to.getDoi());
				//	}
					
					/*splChar=splCharValidationNew(to.getIsbn());
					if(!splChar){
						errMsg = errMsg + ", " +"ISBN";
						err=true;
					}
					else*/
					dep.setIsbn(to.getIsbn());
					
					/*splChar=splCharValidationNew(to.getIssueNumber());
					if(!splChar){
						errMsg = errMsg + ", " +"Issue Number";
					err=true;
					}
					else*/
					dep.setIssueNumber(to.getIssueNumber());
					
					/*splChar=splCharValidationNew(to.getLanguage());
					if(!splChar){
						errMsg = errMsg + ", " +"Language";
						err=true;
					}
					else*/
					dep.setLanguage(to.getLanguage());
					
					/*splChar=splCharValidationNew(to.getDepartmentInstitution());
					if(!splChar){
						errMsg = errMsg + ", " +"Department Institution";
					err=true;
					}
					else*/
					dep.setDepartmentInstitution(to.getDepartmentInstitution());
					
					/*splChar=splCharValidationNew(to.getPlacePublication());
					if(!splChar){
						errMsg = errMsg + ", " +"Publisher Address";
					err=true;
					}
					else*/
					dep.setPlacePublication(to.getPlacePublication());
					
					/*splChar=splCharValidationNew(to.getCompanyInstitution());
					if(!splChar){
						errMsg = errMsg + ", " +"Publisher Name";
					err=true;
					}
					else*/
					dep.setCompanyInstitution(to.getCompanyInstitution());
					dep.setUrl(to.getUrl());
					
					/*splChar=splCharValidationNew(to.getImpactFactor());
					if(!splChar){
						errMsg = errMsg + ", " +"Impact Factor";
					err=true;
					}
					else*/
					dep.setImpactFactor(to.getImpactFactor());
					
				/*	if(to.getDateAccepted()!=null && to.getDateAccepted()!="" && !to.getDateAccepted().isEmpty()){
					dateValid=DateValidation(to.getDateAccepted());
					if(!dateValid){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.dateaccepted1"," is not valid format.Please Enter DD/MM/YYY)"));
					}
					else
					dep.setDateAccepted(CommonUtil.ConvertStringToDate(to.getDateAccepted()));
					}*/
					
				/*	if(to.getDatePublished()!=null && to.getDatePublished()!="" && !to.getDatePublished().isEmpty()){
					dateValid=DateValidation(to.getDatePublished());
					if(!dateValid){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.datesent1"," is not valid format.Please Enter DD/MM/YYY)"));
						
					}
					else*/
					dep.setMonthYear(to.getDatePublished());
					/*}*/
				/*	if(to.getDateSent()!=null && to.getDateSent()!="" && !to.getDateSent().isEmpty()){
					dateValid=DateValidation(to.getDateSent());
					if(!dateValid){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.datesent1"," is not valid format.Please Enter DD/MM/YYY)"));
						
					}
					else
					dep.setDateSent(CommonUtil.ConvertStringToDate(to.getDateSent()));
					}*/
					
				//dep.setMonthYear(to.getMonthYear());
					/*splChar=splCharValidationNew(to.getNameJournal());
					if(!splChar){
						errMsg = errMsg + ", " + "Journal Name";
					err=true;
					}
					else*/
					dep.setNameJournal(to.getNameJournal());
					dep.setPagesFrom(to.getPagesFrom());
					dep.setPagesTo(to.getPagesTo());
					
					/*splChar=splCharValidationNew(to.getTitle());
					if(!splChar){
						errMsg = errMsg + ", " + "Title";
					err=true;
					}
					else*/
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
					/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
						Employee emp1=new Employee();
						emp1.setId(Integer.parseInt(to.getApproverId()));
						dep.setApproverId(emp1);
						}*/
					dep.setApproverId(emp1);
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
					dep.setIsWorkshopTraining(false);
					dep.setIsSeminarAttended(false);
					dep.setIsAwardsAchievementsOthers(false);
					dep.setIsRejected(false);
					dep.setIsEmployee(true);
					dep.setRejectDate(to.getRejectDate());
					dep.setRejectReason(to.getRejectReason());
					if(errors==null || errors.isEmpty())
						empResPubDetails.add(dep);
					else{
						throw  new ApplicationException(); 
					}
					}
					}
				}
			  }
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
								to.getTitle()!=null && !to.getTitle().isEmpty()|| to.getVolumeNumber()!=null && !to.getVolumeNumber().isEmpty()||
								to.getPagesFrom()!=null && !to.getPagesFrom().isEmpty() || to.getPagesTo()!=null && !to.getPagesTo().isEmpty())
						{
							if (to.getId() > 0) {
								dep.setId(to.getId());
							}
						/*splChar=splCharValidationNew(to.getAuthorName());
							if(!splChar){
								errMsg = errMsg + "Author name";
							err=true;
							}
							else*/
						dep.setAuthorName(to.getAuthorName());
							
						/*splChar=splCharValidationNew(to.getNamePeriodical());
							if(!splChar){
								errMsg = errMsg + ", " +"Periodical Name";
							err=true;
							}
							else*/
						dep.setNamePeriodical(to.getNamePeriodical());
							
						/*splChar=splCharValidationNew(to.getIsbn());
							if(!splChar){
								errMsg = errMsg + ", " +"Isbn";
							err=true;
							}
							else*/
						dep.setIsbn(to.getIsbn());
						dep.setIssueNumber(to.getIssueNumber());
						
						/*splChar=splCharValidationNew(to.getLanguage());
						if(!splChar){
							errMsg = errMsg + ", " +"Language";
						err=true;
						}
						else*/
						dep.setLanguage(to.getLanguage());
						
						if(to.getDateMonthYear()!=null && !to.getDateMonthYear().isEmpty()){
						dateValid=DateValidation(to.getDateMonthYear());
						if(!dateValid){
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.date.monthYear.publication1"," is not valid format.Please Enter DD/MM/YYY)"));
						}
						else
						dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
						}
						dep.setPagesFrom(to.getPagesFrom());
						dep.setPagesTo(to.getPagesTo());
						
						/*splChar=splCharValidationNew(to.getTitle());
						if(!splChar){
							errMsg = errMsg + ", " +"Title";
						err=true;
						}
						else*/
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
						/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
							Employee emp1=new Employee();
							emp1.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp1);
							}*/
						dep.setApproverId(emp1);
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
						dep.setIsWorkshopTraining(false);
						dep.setIsSeminarAttended(false);
						dep.setIsAwardsAchievementsOthers(false);
						dep.setIsRejected(false);
						dep.setIsEmployee(true);
						dep.setRejectDate(to.getRejectDate());
						dep.setRejectReason(to.getRejectReason());
						
						if(errors==null || errors.isEmpty())
							empResPubDetails.add(dep);
						else
							throw  new ApplicationException(); 
							
						}
					}
					}
				}
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
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							/*splChar=splCharValidationNew(to.getLanguage());
								if(!splChar){
									errMsg = errMsg + "Language";
								err=true;
								}
								else*/
							dep.setLanguage(to.getLanguage());
							dep.setNumberOfComments(to.getNumberOfComments());
							
							/*splChar=splCharValidationNew(to.getSubject());
							if(!splChar){
								errMsg = errMsg + ", " +"Subject";
							err=true;
							}
							else*/
							dep.setSubject(to.getSubject());
							
							dep.setUrl(to.getUrl());
							/*splChar=splCharValidationNew(to.getMonthYear());
							if(!splChar){
								errMsg = errMsg + ", " +"Month year";
							err=true;
							}
							else*/
							dep.setMonthYear(to.getMonthYear());
							
							/*splChar=splCharValidationNew(to.getTitle());
							if(!splChar){
								errMsg = errMsg + ", " +"Title";
							err=true;
							}
							else*/
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
							Employee emp1=new Employee();
							emp1.setId(Integer.parseInt(to.getApproverId()));
							dep.setApproverId(emp1);
							}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
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
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							/*splChar=splCharValidationNew(to.getLanguage());
								if(!splChar){
									errMsg = errMsg + "Language";
								err=true;
								}
								else*/
							dep.setLanguage(to.getLanguage());
							
							/*splChar=splCharValidationNew(to.getAuthorName());
								if(!splChar){
									errMsg = errMsg + ", " +"Author Name";
								err=true;
								}
								else*/
							dep.setAuthorName(to.getAuthorName());
							
							/*splChar=splCharValidationNew(to.getCompanyInstitution());
								if(!splChar){
									errMsg = errMsg + ", " +"Publishing Company /Institution";
								err=true;
								}
								else*/
							dep.setCompanyInstitution(to.getCompanyInstitution());
							
							/*splChar=splCharValidationNew(to.getIsbn());
								if(!splChar){
									errMsg = errMsg + ", " +"Isbn";
								err=true;
								}
								else*/
							dep.setIsbn(to.getIsbn());
							
							/*splChar=splCharValidationNew(to.getPlacePublication());
								if(!splChar){
									errMsg = errMsg + ", " +"Place of Publication";
								err=true;
								}
								else*/
							dep.setPlacePublication(to.getPlacePublication());
							dep.setMonthYear(to.getMonthYear());
							
							/*splChar=splCharValidationNew(to.getTitle());
							if(!splChar){
								errMsg = errMsg + ", " +"Title";
							err=true;
							}
							else*/
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
						/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
							}
						 }
					}
			  }
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
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							/*splChar=splCharValidationNew(to.getAbstractObjectives());
								if(!splChar){
									errMsg = errMsg + " Abstract and Objectives";
								err=true;
								}
								else*/
							dep.setAbstractObjectives(to.getAbstractObjectives());
								
							/*splChar=splCharValidationNew(to.getAuthorName());
								if(!splChar){
									errMsg = errMsg +" "+ " Author Name";
								err=true;
								}
								else*/
							dep.setAuthorName(to.getAuthorName());
							dep.setCaseNoteWorkPaper(to.getCaseNoteWorkPaper());
							/*splChar=splCharValidationNew(to.getSponsors());
							if(!splChar){
								errMsg = errMsg +" "+ " Institution /Department /Funding Agency /Sponsors";
							err=true;
							}
							else*/
							dep.setSponsors(to.getSponsors());
							/*splChar=splCharValidationNew(to.getTitle());
							if(!splChar){
								errMsg = errMsg +" "+ " Title of Article";
							err=true;
							}
							else*/
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
							}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if (errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
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
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							/*splChar=splCharValidationNew(to.getCompanyInstitution());
								if(!splChar){
									errMsg = errMsg + " Publishing Company /Institution";
								err=true;
								}
								else*/
							dep.setCompanyInstitution(to.getCompanyInstitution());
								
							/*splChar=splCharValidationNew(to.getAuthorName());
								if(!splChar){
									errMsg = errMsg +"," +" Author Names(s)";
								err=true;
								}
								else*/
							dep.setAuthorName(to.getAuthorName());
							
							/*	if(to.getDoi()!=null && to.getDoi()!="" && !to.getDoi().isEmpty()){
								dateValid=DateValidation(to.getDoi());
								if(!dateValid){
									errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.doi1"," is not valid format.Please Enter DD/MM/YYY)"));
								}
								else*/
							dep.setDoi(to.getDoi());
							//}
							/*splChar=splCharValidationNew(to.getEditorsName());
							if(!splChar){
								errMsg = errMsg +"," +" Editor Name(s)";
							err=true;
							}
							else*/
							dep.setEditorsName(to.getEditorsName());
							
							/*splChar=splCharValidationNew(to.getIsbn());
							if(!splChar){
								errMsg = errMsg +"," +"ISBN";
							err=true;
							}
							else*/
							dep.setIsbn(to.getIsbn());
							
							/*splChar=splCharValidationNew(to.getLanguage());
							if(!splChar){
								errMsg = errMsg +"," +"Language";
							err=true;
							}
							else*/
							dep.setLanguage(to.getLanguage());
							
							/*splChar=splCharValidationNew(to.getMonthYear());
							if(!splChar){
								errMsg = errMsg +"," +"Month and Year";
							err=true;
							}
							else*/
							dep.setMonthYear(to.getMonthYear());
							dep.setPagesFrom(to.getPagesFrom());
							dep.setPagesTo(to.getPagesTo());
							
							/*splChar=splCharValidationNew(to.getPlacePublication());
							if(!splChar){
								errMsg = errMsg +"," +" Place of Publication";
							err=true;
							}
							else*/
							dep.setPlacePublication(to.getPlacePublication());
							
							/*splChar=splCharValidationNew(to.getTitle());
							if(!splChar){
								errMsg = errMsg +"," +" Title of Book";
							err=true;
							}
							else*/
							dep.setTitle(to.getTitle());
							
							/*splChar=splCharValidationNew(to.getTitleChapterArticle());
							if(!splChar){
								errMsg = errMsg +"," +" Title of the Chapter /Article";
							err=true;
							}
							else*/
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
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
							  to.getTitle()!=null && !to.getTitle().isEmpty() || to.getAbstractDetails()!=null && !to.getAbstractDetails().isEmpty())
							{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							//dep.setCompanyInstitution(to.getCompanyInstitution());
							/*splChar=splCharValidationNew(to.getLanguage());
								if(!splChar){
									errMsg = errMsg + " Language";
								err=true;
								}
								else*/
							dep.setLanguage(to.getLanguage());
							
							/*splChar=splCharValidationNew(to.getMonthYear());
								if(!splChar){
									errMsg = errMsg +"," +" Month and Year";
								err=true;
								}
								else*/
						//	dep.setMonthYear(to.getMonthYear());
							if(to.getMonthYear()!=null && !to.getMonthYear().isEmpty()){	
								dateValid=DateValidation(to.getMonthYear());
									if(!dateValid){
										errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.date.monthYear.conference"," is not valid format.Please Enter DD/MM/YYY)"));
									}
									else
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
								}
							
							/*splChar=splCharValidationNew(to.getNameConferencesSeminar());
								if(!splChar){
									errMsg = errMsg +"," +" Name of Conference /Seminar /Symposium";
								err=true;
								}
								else*/
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
								
							/*splChar=splCharValidationNew(to.getNameTalksPresentation());
								if(!splChar){
									errMsg = errMsg +"," +" Name(s)";
								err=true;
								}
								else*/
							dep.setNameTalksPresentation(to.getNameTalksPresentation());
							
							/*splChar=splCharValidationNew(to.getPlacePublication());
								if(!splChar){
									errMsg = errMsg +"," +" Place";
								err=true;
								}
								else*/
							dep.setPlacePublication(to.getPlacePublication());
								
							/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg +"," +" Title of Article";
								err=true;
								}
								else*/
							dep.setTitle(to.getTitle());
								
							/*splChar=splCharValidationNew(to.getAbstractDetails());
								if(!splChar){
									errMsg = errMsg + " "+" Abstract";
								err=true;
								}
								else*/
							dep.setAbstracts(to.getAbstractDetails());
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
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
							  to.getTitle()!=null && !to.getTitle().isEmpty() || to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty())
							{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							/*splChar=splCharValidationNew(to.getCompanyInstitution());
								if(!splChar){
									errMsg = errMsg + " Name of the Institution /Radio /TV Channel";
								err=true;
								}
								else*/
							dep.setCompanyInstitution(to.getCompanyInstitution());
								
							/*splChar=splCharValidationNew(to.getLanguage());
								if(!splChar){
									errMsg = errMsg +"," +" Language";
								err=true;
								}
								else*/
							dep.setLanguage(to.getLanguage());
							dep.setNameOfPgm(to.getNameOfPgm());
							
							/*splChar=splCharValidationNew(to.getMonthYear());
								if(!splChar){
									errMsg = errMsg +"," +" Month and Year";
								err=true;
								}
								else*/
						//	dep.setMonthYear(to.getMonthYear());
							if(to.getMonthYear()!=null && !to.getMonthYear().isEmpty()){	
								dateValid=DateValidation(to.getMonthYear());
									if(!dateValid){
										errors.add(CMSConstants.ERROR, new ActionError("Date"," is not valid format.Please Enter DD/MM/YYY)"));
									}
									else
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
								}
							
							//dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							
							/*splChar=splCharValidationNew(to.getNameTalksPresentation());
								if(!splChar){
									errMsg = errMsg +"," +" Name";
								err=true;
								}
								else*/
							dep.setNameTalksPresentation(to.getNameTalksPresentation());
								
							/*splChar=splCharValidationNew(to.getPlacePublication());
								if(!splChar){
									errMsg = errMsg +"," +" Place";
								err=true;
								}
								else*/
							dep.setPlacePublication(to.getPlacePublication());
								
							/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg +"," +" Title of Article";
								err=true;
								}
								else*/
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
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
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							
							/*splChar=splCharValidationNew(to.getAspectRatio());
								if(!splChar){
									errMsg = errMsg + " Aspect Ratio";
								err=true;
								}
								else*/
							dep.setAspectRatio(to.getAspectRatio());
								
							/*splChar=splCharValidationNew(to.getAudioFormat());
								if(!splChar){
									errMsg = errMsg +"," +" Audio Format";
								err=true;
								}
								else*/
							dep.setAudioFormat(to.getAudioFormat());
								
							/*splChar=splCharValidationNew(to.getCopyrights());
								if(!splChar){
									errMsg = errMsg +"," +" Copyright";
								err=true;
								}
								else*/
							dep.setCopyrights(to.getCopyrights());
								
							/*splChar=splCharValidationNew(to.getCredits());
								if(!splChar){
									errMsg = errMsg +"," +" Credits";
								err=true;
								}
								else*/
							dep.setCredits(to.getCredits());
							
							/*splChar=splCharValidationNew(to.getDiscFormat());
								if(!splChar){
									errMsg = errMsg +"," +" Disc Format";
								err=true;
								}
								else*/
							dep.setDiscFormat(to.getDiscFormat());
								
							/*splChar=splCharValidationNew(to.getGenre());
								if(!splChar){
									errMsg = errMsg +"," +" Genre";
								err=true;
								}
								else*/
							dep.setGenre(to.getGenre());
								
							/*splChar=splCharValidationNew(to.getLanguage());
								if(!splChar){
									errMsg = errMsg +"," +" Language";
								err=true;
								}
								else*/
							dep.setLanguage(to.getLanguage());
								
							/*splChar=splCharValidationNew(to.getProducer());
								if(!splChar){
									errMsg = errMsg +"," +" Producer";
								err=true;
								}
								else*/
							dep.setProducer(to.getProducer());
							
							/*splChar=splCharValidationNew(to.getRunningTime());
								if(!splChar){
									errMsg = errMsg +"," +" Running Time";
								err=true;
								}
								else*/
							dep.setRunningTime(to.getRunningTime());
								
							/*splChar=splCharValidationNew(to.getSubtitles());
								if(!splChar){
									errMsg = errMsg +"," +" Subtitles";
								err=true;
								}
								else*/
							dep.setSubtitles(to.getSubtitles());
							
							/*splChar=splCharValidationNew(to.getTechnicalFormat());
								if(!splChar){
									errMsg = errMsg +"," +" Technical Format";
								err=true;
								}
								else*/
							dep.setTechnicalFormat(to.getTechnicalFormat());
								
							/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg +"," +" Title";
								err=true;
								}
								else*/
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
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
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							/*splChar=splCharValidationNew(to.getAbstractObjectives());
								if(!splChar){
									errMsg = errMsg +" Abstract and Objectives";
								err=true;
								}
								else*/
							dep.setAbstractObjectives(to.getAbstractObjectives());
								
							/*splChar=splCharValidationNew(to.getInvestigators());
								if(!splChar){
									errMsg = errMsg +","+" Investigator(s)";
								err=true;
								}
								else*/
							dep.setInvestigators(to.getInvestigators());
								
							/*splChar=splCharValidationNew(to.getSponsors());
								if(!splChar){
									errMsg = errMsg +","+" Sponsors(s)";
								err=true;
								}
								else*/
							dep.setSponsors(to.getSponsors());
								
							/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg +","+" Title";
								err=true;
								}
								else*/
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
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
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							/*splChar=splCharValidationNew(to.getCompanyInstitution());
								if(!splChar){
									errMsg = errMsg +" Institution/University";
								err=true;
								}
								else*/
							dep.setCompanyInstitution(to.getCompanyInstitution());
								
							/*splChar=splCharValidationNew(to.getMonthYear());
								if(!splChar){
									errMsg = errMsg +","+" Month and Year of Defense";
								err=true;
								}
								else*/
							dep.setMonthYear(to.getMonthYear());
								
							/*splChar=splCharValidationNew(to.getNameGuide());
								if(!splChar){
									errMsg = errMsg +","+" Name of the Guide";
								err=true;
								}
								else*/
							dep.setNameGuide(to.getNameGuide());
								
							/*splChar=splCharValidationNew(to.getPlace());
								if(!splChar){
									errMsg = errMsg +","+" Place";
								err=true;
								}
								else*/
							dep.setPlacePublication(to.getPlace());
							
							/*splChar=splCharValidationNew(to.getSubject());
								if(!splChar){
									errMsg = errMsg +","+" Subject";
								err=true;
								}
								else*/
							dep.setSubject(to.getSubject());
								
							/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg +","+" Title of Article";
								err=true;
								}
								else*/
							dep.setTitle(to.getTitle());
								
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							if(to.getSubmissionDate()!=null && !to.getSubmissionDate().isEmpty()){
								dateValid=DateValidation(to.getSubmissionDate());
								if(!dateValid){
									errors.add(CMSConstants.ERROR, new ActionError("Date of Submission is not valid format.Please Enter DD/MM/YYY)"));
								}
								else
							dep.setSubmissionDate(CommonUtil.ConvertStringToDate(to.getSubmissionDate()));
							}
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
					}
					}
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
								if (to.getId() > 0) {
									dep.setId(to.getId());
							}
							
							/*splChar=splCharValidationNew(to.getCompanyInstitution());
								if(!splChar){
									errMsg = errMsg +" Institution/University";
								err=true;
								}
								else*/
							dep.setCompanyInstitution(to.getCompanyInstitution());
								
								/*splChar=splCharValidationNew(to.getMonthYear());
								if(!splChar){
									errMsg = errMsg +","+" Month and Year";
								err=true;
								}
								else*/
							dep.setMonthYear(to.getMonthYear());
								
							/*splChar=splCharValidationNew(to.getNameStudent());
								if(!splChar){
									errMsg = errMsg +","+" Name of the Student";
								err=true;
								}
								else*/
							dep.setNameStudent(to.getNameStudent());
								
							/*splChar=splCharValidationNew(to.getPlace());
								if(!splChar){
									errMsg = errMsg +","+" Place";
								err=true;
								}
								else*/
							dep.setPlacePublication(to.getPlace());
								
							/*splChar=splCharValidationNew(to.getSubject());
								if(!splChar){
									errMsg = errMsg +","+" Subject";
								err=true;
								}
								else*/
							dep.setSubject(to.getSubject());
							
							/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg +","+" Title of Article";
								err=true;
								}
								else*/
							dep.setTitle(to.getTitle());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							/*code added by sudhir*/
							//dep.setGuidedAdjudicated(to.getGuidedAbudjicated());
							dep.setGuidedAdjudicated("Guided");
							/*code added by sudhir*/
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
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
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							/*splChar=splCharValidationNew(to.getLanguage());
								if(!splChar){
									errMsg = errMsg +","+" Language";
								err=true;
								}
								else*/
							dep.setLanguage(to.getLanguage());
							if(to.getDateMonthYear()!=null && !to.getDateMonthYear().isEmpty()){	
							dateValid=DateValidation(to.getDateMonthYear());
								if(!dateValid){
									errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.date.monthYear.publication1"," is not valid format.Please Enter DD/MM/YYY)"));
								}
								else
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
							}
							/*splChar=splCharValidationNew(to.getNameConferencesSeminar());
							if(!splChar){
								errMsg = errMsg +","+"Name of Conference /Seminar /Symposium";
							err=true;
							}
							else*/
							dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
							
							/*splChar=splCharValidationNew(to.getPlace());
							if(!splChar){
								errMsg = errMsg +","+"Place";
							err=true;
							}
							else*/
							dep.setPlacePublication(to.getPlace());
							
							/*splChar=splCharValidationNew(to.getNameOrganisers());
							if(!splChar){
								errMsg = errMsg +","+"Name of the Organizer(s)";
							err=true;
							}
							else*/
							dep.setNameOrganisers(to.getNameOrganisers());
							
							/*splChar=splCharValidationNew(to.getResoursePerson());
							if(!splChar){
								errMsg = errMsg +","+"Resourse Person(s) and Details";
							err=true;
							}
							else*/
							dep.setResoursePerson(to.getResoursePerson());
							
							/*splChar=splCharValidationNew(to.getSponsors());
							if(!splChar){
								errMsg = errMsg +","+"Institution /Department /Funding Agency /Sponsors";
							err=true;
							}
							else*/
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								 
							}
						}
						}
					}
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
							if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()||to.getEndOfPgm()!=null && !to.getEndOfPgm().isEmpty()|| to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty() || 
							to.getDuration()!=null && !to.getDuration().isEmpty() || to.getOrganisedBy()!=null && !to.getOrganisedBy().isEmpty())
							{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()){		
							dateValid=DateValidation(to.getDateOfPgm());
								if(!dateValid){
									errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.date.pgm1"," is not valid format.Please Enter DD/MM/YYY)"));
								}
								else
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
							}
							if(to.getEndOfPgm()!=null && !to.getEndOfPgm().isEmpty()){		
							dateValid=DateValidation(to.getEndOfPgm());
								if(!dateValid){
									errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.date.pgm2"," is not valid format.Please Enter DD/MM/YYY)"));
								}
								else
							dep.setEndOfPgm(CommonUtil.ConvertStringToDate(to.getEndOfPgm()));
							}
							/*splChar=splCharValidationNew(to.getNameOfPgm());
							if(!splChar){
								errMsg = errMsg +","+" Name of program";
							err=true;
							}
							else*/
							dep.setNameConferencesSeminar(to.getNameOfPgm());
							
							/*splChar=splCharValidationNew(to.getOrganisedBy());
							if(!splChar){
								errMsg = errMsg +","+" Organized By";
							err=true;
							}
							else*/
							dep.setNameOrganisers(to.getOrganisedBy());
							
							/*splChar=splCharValidationNew(to.getDuration());
							if(!splChar){
								errMsg = errMsg +","+" Duration";
							err=true;
							}
							else*/
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(true);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else						
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
		 }else if (empResPubForm.getSubmitName().equalsIgnoreCase("WorkshopsAttended")) {
				if (empResPubForm.getEmpWorkshopsTO() != null			
						&& !empResPubForm.getEmpWorkshopsTO().isEmpty()) {
					Iterator<EmpWorkshopsFdpTrainingTO> itr = empResPubForm.getEmpWorkshopsTO().iterator();
					while (itr.hasNext()) {
						EmpWorkshopsFdpTrainingTO to = (EmpWorkshopsFdpTrainingTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()|| to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty() || 
									to.getDuration()!=null && !to.getDuration().isEmpty() || to.getOrganisedBy()!=null && !to.getOrganisedBy().isEmpty())
									{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()){
							dateValid=DateValidation(to.getDateOfPgm());
								if(!dateValid){
									errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.date.pgm1"," is not valid format.Please Enter DD/MM/YYY)"));
								}
								else
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
							}
							
							/*splChar=splCharValidationNew(to.getNameOfPgm());
							if(!splChar){
								errMsg = errMsg +","+" Name of Program";
							err=true;
							}
							else*/
							dep.setNameConferencesSeminar(to.getNameOfPgm());
							
							/*splChar=splCharValidationNew(to.getOrganisedBy());
							if(!splChar){
								errMsg = errMsg +","+" Name of Organisers";
							err=true;
							}
							else*/
							dep.setNameOrganisers(to.getOrganisedBy());
							
							/*splChar=splCharValidationNew(to.getDuration());
							if(!splChar){
								errMsg = errMsg +","+" Duration";
							err=true;
							}
							else*/
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
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(true);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
		 }else if (empResPubForm.getSubmitName().equalsIgnoreCase("AwardsAchievements")) {
				if (empResPubForm.getEmpAwardsAchievementsOthersTO() != null			
						&& !empResPubForm.getEmpAwardsAchievementsOthersTO().isEmpty()) {
					Iterator<EmpAwardsAchievementsOthersTO> itr = empResPubForm.getEmpAwardsAchievementsOthersTO().iterator();
					while (itr.hasNext()) {
						EmpAwardsAchievementsOthersTO to = (EmpAwardsAchievementsOthersTO) itr.next();
						EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
						if(to!=null)
						{
							if(to.getName()!=null && !to.getName().isEmpty()|| to.getPlace()!=null && !to.getPlace().isEmpty() || 
									to.getDescription()!=null && !to.getDescription().isEmpty() || to.getMonthYear()!=null && !to.getMonthYear().isEmpty())
									{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setMonthYear(to.getMonthYear());
							
							/*splChar=splCharValidationNew(to.getName());
							if(!splChar){
								errMsg = errMsg +" Name";
							err=true;
							}
							else*/
							dep.setName(to.getName());
							
							/*splChar=splCharValidationNew(to.getPlace());
							if(!splChar){
								errMsg = errMsg +","+" Place";
							err=true;
							}
							else*/
							dep.setPlace(to.getPlace());
							
							/*splChar=splCharValidationNew(to.getDescription());
							if(!splChar){
								errMsg = errMsg +","+" Description";
							err=true;
							}
							else*/
							dep.setDescription(to.getDescription());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setOrganisationAwarded(to.getOrganisationAwarded());
							dep.setIsActive(true);
							dep.setEmployeeId(emp);
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							/*if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp1=new Employee();
								emp1.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp1);
								}*/
							dep.setApproverId(emp1);
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(true);
							dep.setIsRejected(false);
							dep.setIsEmployee(true);
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
							}
						}
						}
					}
		 }
		}
		 else
		 {
			 throw  new BusinessException(); 
		 }
		return empResPubDetails;
	}
	
	 public List<EmpResPublicMasterTO> convertBOtoTO(List<EmpResearchPublicMaster> allowance){
	    	List<EmpResPublicMasterTO> allowanceTO=new ArrayList<EmpResPublicMasterTO>();
	    	Iterator<EmpResearchPublicMaster> itr=allowance.iterator();
	    	while(itr.hasNext()){
	    		EmpResearchPublicMaster alowance=itr.next();
	    		EmpResPublicMasterTO alowanceTO=new EmpResPublicMasterTO();
	    		if(alowance.getName()!=null)
	    			alowanceTO.setName(alowance.getName());
	    		alowanceTO.setId(alowance.getId());
	    		allowanceTO.add(alowanceTO);
	    	}
	    	return allowanceTO;
	    }
	 
	    public EmpResearchPublicMaster convertFormTOBO(EmpResPubDetailsForm empResPubDetailsForm,String mode){
	    	EmpResearchPublicMaster allowance=new EmpResearchPublicMaster();
			if(mode.equalsIgnoreCase("add")){
				allowance.setCreatedBy(empResPubDetailsForm.getUserId());
				allowance.setCreatedDate(new Date());
			}else
				allowance.setId(Integer.parseInt(empResPubDetailsForm.getEmpResPubMasterId()));
			if(empResPubDetailsForm.getEmpResPubName()!=null)
				allowance.setName(empResPubDetailsForm.getEmpResPubName());
			allowance.setIsActive(true);
			allowance.setModifiedBy(empResPubDetailsForm.getUserId());
			allowance.setLastModifiedDate(new Date());
			return allowance;
		}
	    
	    public void setBotoForm(EmpResPubDetailsForm empResPubDetailsForm,EmpResearchPublicMaster allowance){
	    	if(allowance!=null){
	    		empResPubDetailsForm.setEmpResPubName(allowance.getName());
	    		empResPubDetailsForm.setOrigEmpResPubName(allowance.getName());
	    	}
	    }
	    
	    private boolean splCharValidationNew(String name) {
	    	boolean haveSplChar = false;
	    	//Pattern pattern = Pattern.compile("[^A-Za-z0-9{!#$%&'()*+,-./:;<=>?@[]^_`{|}~}\\\\b  \t  \n  \f  \r  \"  \'  \\]+");
	    	Pattern pattern = Pattern.compile("^[A-Za-z0-9!@#$%^&()*+,-:;<=>?_\"\'`{|}~\\\\b  \t  \n  \f  \r ]*$");
	    	Matcher matcher = pattern.matcher(name);
	    	haveSplChar = matcher.find();
	    	return haveSplChar;

	    }
	    private boolean DateValidation(String date) {
	    	boolean validDate = false;
	    	if(date!=null && !date.isEmpty()){
				if (CommonUtil.isValidDate(date)) 
					validDate=true;
			}
			
	    	return validDate;
	    }
	    
	    public List<EmployeeTO> convertEmployeeTOtoBO(List<Employee> employeelist,
				int departmentId, int designationId) throws Exception {

			List<Department> departmentList = empTransaction.getEmployeeDepartment();
			List<Designation> designationList = empTransaction.getEmployeeDesignation();

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

					if (emp.getDesignation() != null
							&& emp.getDesignation().getId() > 0) {
						int DesigId = emp.getDesignation().getId();
						String DesigName = null;
						if (designationList != null) {
							Iterator<Designation> desItr = designationList
									.iterator();
							while (desItr.hasNext()) {
								Designation des = (Designation) desItr.next();
								int desigId = des.getId();
								if (desigId == DesigId) {
									DesigName = des.getName();
								}
							}
						}
						empTo.setDummyDesignationName(DesigName);
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
				if (emp.getEmpImages() != null && emp.getEmpImages().size() > 0) {
						Iterator<EmpImages> itr=emp.getEmpImages().iterator();
						while (itr.hasNext()) {
							EmpImages bo =itr.next();
							if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
								empTo.setIsPhoto(true);
								break;
							}
						}
					}
					employeeTos.add(empTo);
				}
			}
			return employeeTos;
		}
	    
	    //For Guest Faculty.................
	    public List<EmployeeTO> convertGuestTOtoBO(List<GuestFaculty> employeelist,
				int departmentId, int designationId) throws Exception {

			List<Department> departmentList = empTransaction.getEmployeeDepartment();
			List<Designation> designationList = empTransaction.getEmployeeDesignation();

			List<EmployeeTO> employeeTos = new ArrayList<EmployeeTO>();
			String name = "";
			if (employeelist != null) {
				Iterator<GuestFaculty> stItr = employeelist.iterator();
				while (stItr.hasNext()) {
					name = "";
					GuestFaculty emp = (GuestFaculty) stItr.next();
					EmployeeTO empTo = new EmployeeTO();
					if (emp.getId() > 0) {
						empTo.setId(emp.getId());
					}

					empTo.setDummyFingerPrintId(" ");
					
					if (emp.getFirstName() != null) {

						empTo.setDummyFirstName(emp.getFirstName().toUpperCase());
					}

					if (emp.getDesignation() != null
							&& emp.getDesignation().getId() > 0) {
						int DesigId = emp.getDesignation().getId();
						String DesigName = null;
						if (designationList != null) {
							Iterator<Designation> desItr = designationList
									.iterator();
							while (desItr.hasNext()) {
								Designation des = (Designation) desItr.next();
								int desigId = des.getId();
								if (desigId == DesigId) {
									DesigName = des.getName();
								}
							}
						}
						empTo.setDummyDesignationName(DesigName);
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
				if (emp.getEmpImages() != null && emp.getEmpImages().size() > 0) {
						Iterator<GuestImages> itr=emp.getEmpImages().iterator();
						while (itr.hasNext()) {
							GuestImages bo =itr.next();
							if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
								empTo.setIsPhoto(true);
								break;
							}
						}
					}
					employeeTos.add(empTo);
				}
			}
			return employeeTos;
		}
	    
	    public void convertBoToFormAdmin(List<EmpResearchPublicDetails> empApplicantDetails,
				EmpResPubDetailsForm empResPubForm) throws Exception {
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				List<EmpArticlInPeriodicalsTO> empArticlInPeriodicalsTO = new ArrayList<EmpArticlInPeriodicalsTO>();
				List<EmpArticleJournalsTO> empArticleJournalsTO = new ArrayList<EmpArticleJournalsTO>();
				List<EmpBlogTO> empBlogTO= new ArrayList<EmpBlogTO>();
				List<EmpBooksMonographsTO> empBooksMonographsTO= new ArrayList<EmpBooksMonographsTO>();
				List<EmpCasesNotesWorkingTO> empCasesNotesWorkingTO= new ArrayList<EmpCasesNotesWorkingTO>();
				List<EmpChapterArticlBookTO> empChapterArticlBookTO= new ArrayList<EmpChapterArticlBookTO>();
				List<EmpConferencePresentationTO> empConferencePresentationTO= new ArrayList<EmpConferencePresentationTO>();
				List<EmpInvitedTalkTO> empInvitedTalkTO= new ArrayList<EmpInvitedTalkTO>();
				List<EmpFilmVideosDocTO> empFilmVideosDocTO= new ArrayList<EmpFilmVideosDocTO>();
				List<EmpOwnPhdMPilThesisTO> empOwnPhdMPilThesisTO= new ArrayList<EmpOwnPhdMPilThesisTO>();
				List<EmpPhdMPhilThesisGuidedTO> empPhdMPhilThesisGuidedTO= new ArrayList<EmpPhdMPhilThesisGuidedTO>();
				List<EmpSeminarsOrganizedTO> empSeminarsOrganizedTO= new ArrayList<EmpSeminarsOrganizedTO>();
				List<EmpResProjectTO> empResProjectTO= new ArrayList<EmpResProjectTO>();
				List<EmpConferenceSeminarsAttendedTO> empSeminarsAttendedTO= new ArrayList<EmpConferenceSeminarsAttendedTO>();
				List<EmpWorkshopsFdpTrainingTO> empWorkshopsTrainingTOs= new ArrayList<EmpWorkshopsFdpTrainingTO>();
				List<EmpAwardsAchievementsOthersTO> empAwardsAchievTOs= new ArrayList<EmpAwardsAchievementsOthersTO>();
				
				if(empApplicantDetails!=null && !empApplicantDetails.isEmpty())
				{
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
							if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
								empto.setApproverComment(empAcheiv.getApproverComment());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
								empto.setAcademicYear(empAcheiv.getAcademicYear());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getType()) && empAcheiv.getType()!=null) {
								empto.setType(empAcheiv.getType());
								if(empAcheiv.getType().equalsIgnoreCase("other") &&(empAcheiv.getOtherText()!=null && ! empAcheiv.getOtherText().isEmpty())){
									empto.setOtherTextArticle(empAcheiv.getOtherText());
								}
							}
							
							
							if(empAcheiv.getApprovedDate()!=null)
							{
							if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
								empto.setApprovedDate(empAcheiv.getApprovedDate());
								empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							}
							if (empAcheiv.getApproverId()!=null)
								{
								if(empAcheiv.getApproverId().getId()> 0) {
								
								empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
							}
							}
							if (empAcheiv.getEmployeeId()!=null)
							{
							if(empAcheiv.getEmployeeId().getId()> 0) {
							
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						}else if (empAcheiv.getGuestId()!=null)
							{
							if(empAcheiv.getGuestId().getId()> 0) {
							
							empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
						}
						}
							if (empAcheiv.getIsActive()!=null) {
								empto.setIsActive(empAcheiv.getIsActive());
							}
							empto.setIsArticleInPeriodicals(empAcheiv.getIsArticleInPeriodicals());
							
							if (empAcheiv.getIsApproved()!=null) 
								empto.setIsApproved(empAcheiv.getIsApproved());
							
							if (empAcheiv.getIsApproved())	
									empto.setAutoApprove("1");
							else
									empto.setAutoApprove("0");
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
							if(empAcheiv.getRejectDate()!=null){
								if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
									empto.setRejectDate(empAcheiv.getRejectDate());
									empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
								}
							}
							if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
								empto.setRejectReason(empAcheiv.getRejectReason());
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
								empPreviousOrgTo.setAcademicYear(String.valueOf(year));
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
								if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
									empto.setDatePublished(empAcheiv.getMonthYear());
								}
								/*if (empAcheiv.getDateSent()!=null && StringUtils.isNotEmpty(empAcheiv.getDateSent().toString())) {
									empto.setDateSent(CommonUtil.ConvertStringToDateFormat(empAcheiv.getDateSent().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
								}*/

								if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle())) {
									empto.setTitle(empAcheiv.getTitle());
								}
								if (empAcheiv.getLanguage()!=null && StringUtils.isNotEmpty(empAcheiv.getLanguage()) ) {
									empto.setLanguage(empAcheiv.getLanguage());
								}
								if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
									empto.setMonthYear(empAcheiv.getMonthYear());
								}
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
								if (empAcheiv.getApproverComment()!=null &&StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
									empto.setApprovedDate(empAcheiv.getApprovedDate());
									empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
								}
								}
								if (empAcheiv.getApproverId()!=null)
									{
									if(empAcheiv.getApproverId().getId()> 0) {
									
									empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
								}
								}
								if (empAcheiv.getEmployeeId()!=null)
								{
								if(empAcheiv.getEmployeeId().getId()> 0) {
								
								empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
							}
							}else if (empAcheiv.getGuestId()!=null)
							{
								if(empAcheiv.getGuestId().getId()> 0) {
								
								empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
							}
							}
								if (empAcheiv.getIsActive()!=null) {
									empto.setIsActive(empAcheiv.getIsActive());
								}
								empto.setIsArticleJournal(empAcheiv.getIsArticleJournal());
								
								if (empAcheiv.getIsApproved()!=null) 
									empto.setIsApproved(empAcheiv.getIsApproved());
								
								if(empAcheiv.getIsApproved())
									empto.setAutoApprove("1");
								else
									empto.setAutoApprove("0");
								
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
								if(empAcheiv.getRejectDate()!=null){
									if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
										empto.setRejectDate(empAcheiv.getRejectDate());
										empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
												"yyyy-mm-dd", "dd/mm/yyyy"));
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
								empPreviousOrgTo.setDepartmentInstitution("");
								empPreviousOrgTo.setCompanyInstitution("");
								empPreviousOrgTo.setPlacePublication("");
								empPreviousOrgTo.setUrl("");
								empPreviousOrgTo.setImpactFactor("");
							//	empPreviousOrgTo.setDateAccepted("");
							//	empPreviousOrgTo.setDateSent("");
								empPreviousOrgTo.setDatePublished("");
								empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							//	empPreviousOrgTo.setIsArticleJournal(false);
							//	empPreviousOrgTo.setIsRejected(false);
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
							if ( empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment()) ) {
								empto.setApproverComment(empAcheiv.getApproverComment());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
								empto.setAcademicYear(empAcheiv.getAcademicYear());
							}
							if(empAcheiv.getApprovedDate()!=null)
							{
							if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
								empto.setApprovedDate(empAcheiv.getApprovedDate());
								empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							}
							if (empAcheiv.getApproverId()!=null)
								{
								if(empAcheiv.getApproverId().getId()> 0) {
								
								empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
							}
							}
							if (empAcheiv.getEmployeeId()!=null)
							{
							if(empAcheiv.getEmployeeId().getId()> 0) {
							
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						}else if (empAcheiv.getGuestId()!=null)
							{
							if(empAcheiv.getGuestId().getId()> 0) {
							
							empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
						}
						}
							if (empAcheiv.getIsActive()!=null) {
								empto.setIsActive(empAcheiv.getIsActive());
							}
							empto.setIsBlog(empAcheiv.getIsBlog());
							if (empAcheiv.getIsApproved()!=null) {
								empto.setIsApproved(empAcheiv.getIsApproved());
							}
							
							if (empAcheiv.getIsApproved())	
								empto.setAutoApprove("1");
							else
								empto.setAutoApprove("0");
							
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
							if(empAcheiv.getRejectDate()!=null){
								if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
									empto.setRejectDate(empAcheiv.getRejectDate());
									empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
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
								empPreviousOrgTo.setAcademicYear(String.valueOf(year));
								empPreviousOrgTo.setIsBlog(false);
								empPreviousOrgTo.setIsRejected(false);
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
							int a=empAcheiv.getTitle().length();
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
							if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment()) ) {
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
								empto.setApprovedDate(empAcheiv.getApprovedDate());
								empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							}
							if (empAcheiv.getApproverId()!=null)
								{
								if(empAcheiv.getApproverId().getId()> 0) {
								
								empto.setApproverId(String.valueOf(empAcheiv.getApproverId().getId()));
							}
							}
							if (empAcheiv.getEmployeeId()!=null)
							{
							if(empAcheiv.getEmployeeId().getId()> 0) {
							
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						}else if (empAcheiv.getGuestId()!=null)
							{
							if(empAcheiv.getGuestId().getId()> 0) {
							
							empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
						}
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
							
							if (empAcheiv.getIsApproved())	
								empto.setAutoApprove("1");
							else
								empto.setAutoApprove("0");
							
							if(empAcheiv.getIsRejected()!=null){
								empto.setIsRejected(empAcheiv.getIsRejected());
							}
							if(empAcheiv.getRejectDate()!=null){
								if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
									empto.setRejectDate(empAcheiv.getRejectDate());
									empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
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
								empPreviousOrgTo.setAcademicYear(String.valueOf(year));
								//empPreviousOrgTo.setIsBookMonographs(false);
								//empPreviousOrgTo.setIsRejected(false);
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
							if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
								empto.setApproverComment(empAcheiv.getApproverComment());
							}
							if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
								empto.setAcademicYear(empAcheiv.getAcademicYear());
							}
							if(empAcheiv.getApprovedDate()!=null)
							{
							if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
								empto.setApprovedDate(empAcheiv.getApprovedDate());
								empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
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
							if (empAcheiv.getEmployeeId()!=null)
							{
							if(empAcheiv.getEmployeeId().getId()> 0) {
							
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						}else if (empAcheiv.getGuestId()!=null)
							{
							if(empAcheiv.getGuestId().getId()> 0) {
							
							empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
						}
						}
							if (empAcheiv.getIsActive()!=null) {
								empto.setIsActive(empAcheiv.getIsActive());
							}
							empto.setIsCasesNoteWorking(empAcheiv.getIsCasesNoteWorking());
							if (empAcheiv.getIsApproved()!=null) {
								empto.setIsApproved(empAcheiv.getIsApproved());
							}
							
							if (empAcheiv.getIsApproved())	
								empto.setAutoApprove("1");
							else
								empto.setAutoApprove("0");
							
							if(empAcheiv.getIsRejected()!=null){
								empto.setIsRejected(empAcheiv.getIsRejected());
							}
							if(empAcheiv.getRejectDate()!=null){
								if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
									empto.setRejectDate(empAcheiv.getRejectDate());
									empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
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
								empPreviousOrgTo.setAcademicYear(String.valueOf(year));
								//empPreviousOrgTo.setIsCasesNoteWorking(false);
							//	empPreviousOrgTo.setIsRejected(false);
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
								empto.setApprovedDate(empAcheiv.getApprovedDate());
								empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
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
							if (empAcheiv.getEmployeeId()!=null)
							{
							if(empAcheiv.getEmployeeId().getId()> 0) {
							
							empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
							}
							}else if (empAcheiv.getGuestId()!=null)
							{
							if(empAcheiv.getGuestId().getId()> 0) {
							
							empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
						}
						}
							if (empAcheiv.getIsActive()!=null) {
								empto.setIsActive(empAcheiv.getIsActive());
							}
							empto.setIsChapterArticleBook(empAcheiv.getIsChapterArticleBook());
							if (empAcheiv.getIsApproved()!=null) {
								empto.setIsApproved(empAcheiv.getIsApproved());
							}
							if (empAcheiv.getIsApproved())	
								empto.setAutoApprove("1");
							else
								empto.setAutoApprove("0");
							
							if(empAcheiv.getIsRejected()!=null){
								empto.setIsRejected(empAcheiv.getIsRejected());
							}
							if(empAcheiv.getRejectDate()!=null){
								if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
									empto.setRejectDate(empAcheiv.getRejectDate());
									empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
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
								empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							//	empPreviousOrgTo.setIsChapterArticleBook(false);
							//	empPreviousOrgTo.setIsRejected(false);
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
						if (empAcheiv.getDateMonthYear()!=null &&  StringUtils.isNotEmpty(empAcheiv.getDateMonthYear().toString()) ) {
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
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment()) ) {
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					}else if (empAcheiv.getGuestId()!=null)
					{
						if(empAcheiv.getGuestId().getId()> 0) {
						
						empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
					}
					}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsConferencePresentation(empAcheiv.getIsConferencePresentation());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
						else
							empto.setAutoApprove("0");
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						//	empPreviousOrgTo.setIsConferencePresentation(false);
						//	empPreviousOrgTo.setIsRejected(false);
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
						/*if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
							empto.setMonthYear(empAcheiv.getMonthYear());
						}*/
						if (empAcheiv.getDateMonthYear()!=null &&  StringUtils.isNotEmpty(empAcheiv.getDateMonthYear().toString()) ) {
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
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment()) ) {
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					}else if (empAcheiv.getGuestId()!=null)
					{
						if(empAcheiv.getGuestId().getId()> 0) {
						
						empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
					}
					}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsInvitedTalk(empAcheiv.getIsInvitedTalk());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
						else
							empto.setAutoApprove("0");
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						//	empPreviousOrgTo.setIsInvitedTalk(false);
						//	empPreviousOrgTo.setIsRejected(false);
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
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
							empto.setApproverComment(empAcheiv.getApproverComment());
						}
						if (StringUtils.isNotEmpty(empAcheiv.getAcademicYear()) && empAcheiv.getAcademicYear()!=null) {
							empto.setAcademicYear(empAcheiv.getAcademicYear());
						}
						if (empAcheiv.getAspectRatio()!=null && StringUtils.isNotEmpty(empAcheiv.getAspectRatio())) {
							empto.setAspectRatio(empAcheiv.getAspectRatio());
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					}else if (empAcheiv.getGuestId()!=null)
					{
						if(empAcheiv.getGuestId().getId()> 0) {
						
						empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
					}
					}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsFilmVideoDoc(empAcheiv.getIsFilmVideoDoc());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
						else
							empto.setAutoApprove("0");
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						//	empPreviousOrgTo.setIsFilmVideoDoc(false);
						//	empPreviousOrgTo.setIsRejected(false);
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
						if (empAcheiv.getSubmissionDate()!=null &&  StringUtils.isNotEmpty(empAcheiv.getSubmissionDate().toString()) ) {
							empto.setSubmissionDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getSubmissionDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(empAcheiv.getApprovedDate()!=null)
						{
						if (StringUtils.isNotEmpty(empAcheiv.getApprovedDate().toString())) {
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					}else if (empAcheiv.getGuestId()!=null)
					{
						if(empAcheiv.getGuestId().getId()> 0) {
						
						empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
					}
					}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsOwnPhdMphilThesis(empAcheiv.getIsOwnPhdMphilThesis());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
						else
							empto.setAutoApprove("0");
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
						//	empPreviousOrgTo.setIsOwnPhdMphilThesis(false);
						//	empPreviousOrgTo.setIsRejected(false);
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
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
						}
						}else if (empAcheiv.getGuestId()!=null)
							{
							if(empAcheiv.getGuestId().getId()> 0) {
							
							empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
						}
						}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsPhdMPhilThesisGuided(empAcheiv.getIsPhdMPhilThesisGuided());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
						else
							empto.setAutoApprove("0");
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
					//		empPreviousOrgTo.setIsPhdMPhilThesisGuided(false);
					//		empPreviousOrgTo.setIsRejected(false);
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
							}
					}else if (empAcheiv.getGuestId()!=null)
					{
						if(empAcheiv.getGuestId().getId()> 0) {
						
						empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
					}
					}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsSeminarOrganized(empAcheiv.getIsSeminarOrganized());
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
						else
							empto.setAutoApprove("0");
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
					//		empPreviousOrgTo.setIsSeminarOrganized(false);
					//		empPreviousOrgTo.setIsRejected(false);
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
						if (empAcheiv.getInvestigators()!=null && StringUtils.isNotEmpty(empAcheiv.getInvestigators())) {
							empto.setInvestigators(empAcheiv.getInvestigators());
						}
						if (empAcheiv.getSponsors()!=null && StringUtils.isNotEmpty(empAcheiv.getSponsors())) {
							empto.setSponsors(empAcheiv.getSponsors());
						}
						if (empAcheiv.getTitle()!=null && StringUtils.isNotEmpty(empAcheiv.getTitle()) ) {
							int a=empAcheiv.getTitle().length();
							empto.setTitle(empAcheiv.getTitle());
						}
						if (empAcheiv.getAbstractObjectives()!=null && StringUtils.isNotEmpty(empAcheiv.getAbstractObjectives())) {
							empto.setAbstractObjectives(empAcheiv.getAbstractObjectives());
						}
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					}else if (empAcheiv.getGuestId()!=null)
					{
						if(empAcheiv.getGuestId().getId()> 0) {
						
						empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
					}
					}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsResearchProject(empAcheiv.getIsResearchProject());
						
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
						else
							empto.setAutoApprove("0");
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
						}
						if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
							empto.setRejectReason(empAcheiv.getRejectReason());
						}
						
						empResProjectTO.add(empto);
						if(empResProjectTO==null || empResProjectTO.isEmpty())
						{
							EmpResProjectTO empPreviousOrgTo=new EmpResProjectTO();
							empPreviousOrgTo.setAbstractObjectives("");
							empPreviousOrgTo.setInvestigators("");
							empPreviousOrgTo.setSponsors("");
							empPreviousOrgTo.setTitle("");
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
				//			empPreviousOrgTo.setIsResearchProject(false);
				//			empPreviousOrgTo.setIsRejected(false);
							empResProjectTO.add(empPreviousOrgTo);
						}
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
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					}else if (empAcheiv.getGuestId()!=null)
					{
						if(empAcheiv.getGuestId().getId()> 0) {
						
						empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
					}
					}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsSeminarAttended(empAcheiv.getIsSeminarAttended());
						
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
						else
							empto.setAutoApprove("0");
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
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
							empPreviousOrgTo.setEndOfPgm("");
							empPreviousOrgTo.setDuration("");
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							empPreviousOrgTo.setIsSeminarAttended(false);
							empPreviousOrgTo.setIsRejected(false);
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
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					}else if (empAcheiv.getGuestId()!=null)
					{
						if(empAcheiv.getGuestId().getId()> 0) {
						
						empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
					}
					}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsWorkshopTraining(empAcheiv.getIsWorkshopTraining());
						
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
						else
							empto.setAutoApprove("0");
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
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
							empPreviousOrgTo.setAcademicYear(String.valueOf(year));
							empPreviousOrgTo.setIsWorkshopTraining(false);
							empPreviousOrgTo.setIsRejected(false);
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
						if (empAcheiv.getMonthYear()!=null && StringUtils.isNotEmpty(empAcheiv.getMonthYear())) {
							empto.setMonthYear(empAcheiv.getMonthYear());
						}
						if (empAcheiv.getPlace()!=null && StringUtils.isNotEmpty(empAcheiv.getPlace()) ) {
							
							empto.setPlace(empAcheiv.getPlace());
						}
						
						if (empAcheiv.getName()!=null && StringUtils.isNotEmpty(empAcheiv.getName())) {
							empto.setName(empAcheiv.getName());
						}
						if (empAcheiv.getDescription()!=null && StringUtils.isNotEmpty(empAcheiv.getDescription())) {
							empto.setDescription(empAcheiv.getDescription());
						}
						if (empAcheiv.getApproverComment()!=null && StringUtils.isNotEmpty(empAcheiv.getApproverComment())) {
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
							empto.setApprovedDate(empAcheiv.getApprovedDate());
							empto.setEntryApprovedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getApprovedDate().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
						}
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
						if (empAcheiv.getEmployeeId()!=null)
						{
						if(empAcheiv.getEmployeeId().getId()> 0) {
						
						empto.setEmployeeId(String.valueOf(empAcheiv.getEmployeeId().getId()));
					}
					}else if (empAcheiv.getGuestId()!=null)
					{
						if(empAcheiv.getGuestId().getId()> 0) {
						
						empto.setGuestId(String.valueOf(empAcheiv.getGuestId().getId()));
					}
					}
						if (empAcheiv.getIsActive()!=null) {
							empto.setIsActive(empAcheiv.getIsActive());
						}
						empto.setIsAwardsAchievementsOthers(empAcheiv.getIsAwardsAchievementsOthers());
						
						if (empAcheiv.getIsApproved()!=null) {
							empto.setIsApproved(empAcheiv.getIsApproved());
						}
						if (empAcheiv.getIsApproved())	
							empto.setAutoApprove("1");
					    else
						    empto.setAutoApprove("0"); 
						
						if(empAcheiv.getIsRejected()!=null){
							empto.setIsRejected(empAcheiv.getIsRejected());
						}
						if(empAcheiv.getRejectDate()!=null){
							if (StringUtils.isNotEmpty(empAcheiv.getRejectDate().toString())) {
								empto.setRejectDate(empAcheiv.getRejectDate());
								empto.setEntryRejectedDate(CommonUtil.ConvertStringToDateFormat(empAcheiv.getRejectDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
							}
						}
						if(empAcheiv.getRejectReason()!=null && !empAcheiv.getRejectReason().isEmpty()){
							empto.setRejectReason(empAcheiv.getRejectReason());
						}
						
						empAwardsAchievTOs.add(empto);
						if(empAwardsAchievTOs==null || empAwardsAchievTOs.isEmpty())
						{
						    EmpAwardsAchievementsOthersTO empAwardsTo=new EmpAwardsAchievementsOthersTO();
						    empAwardsTo.setName("");
						    empAwardsTo.setPlace("");
						    empAwardsTo.setDescription("");
						    empAwardsTo.setMonthYear("");
						    empAwardsTo.setAcademicYear(String.valueOf(year));
						    empAwardsTo.setIsAwardsAchievementsOthers(false);
						    empAwardsTo.setIsRejected(false);
						    empAwardsAchievTOs.add(empAwardsTo);
						}
						empResPubForm.setEmpAwardsAchievementsOthersTO(empAwardsAchievTOs);
				}
				}
			}
		}
		}
		@SuppressWarnings("deprecation")
		public List<EmpResearchPublicDetails> convertFormToBoAdmin(EmpResPubDetailsForm empResPubForm,ActionErrors errors) throws Exception {
			 boolean splChar=false;
			 boolean dateValid=false;
			 String errMsg="";
			 boolean err=false;
			 List<EmpResearchPublicDetails> empResPubDetails = new ArrayList<EmpResearchPublicDetails>();
			 Employee adminId=empTransaction.getEmployeeIdFromUserId(empResPubForm);
			 //adminId.setId(adminId.getId());
			/* empResPubForm.setEmployeeEmailId(emp.getWorkEmail());*/
			 Employee app=new Employee();
			
			 HodApprover approverId=empTransaction.getApproverIdFromEmpId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
			
			 if(approverId!=null)
			{
			  app.setId(approverId.getApprover().getId());
			  
			  String approverEmail=empTransaction.getApproverEmailId(app.getId());
			}
			 
			// empResPubForm.setApproverEmailId(approverEmail);
			// empResPubForm.setFingerPrintId(emp.getFingerPrintId());
			/* errMsg=empResPubForm.getErrMsg();
			 if(errMsg==null || errMsg.isEmpty()){
	       		errMsg = "Special characters allowed are ([].\\-#,@():;&!?\'\"). Special characters typed in following Fields:-";
	*/       if (empResPubForm.getSubmitName().equalsIgnoreCase("ArticleJournals")) {
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
						  to.getDoi()!=null && !to.getDoi().isEmpty() || to.getCompanyInstitution()!=null && !to.getCompanyInstitution().isEmpty() ||
						  to.getDepartmentInstitution()!=null && !to.getDepartmentInstitution().isEmpty() || 
						  to.getPlacePublication()!=null && !to.getPlacePublication().isEmpty() || to.getUrl()!=null && !to.getUrl().isEmpty()|| 
						  to.getImpactFactor()!=null && !to.getImpactFactor().isEmpty() || 
					 to.getDatePublished()!=null && !to.getDatePublished().isEmpty())
						{
							if (to.getId() > 0) {
								dep.setId(to.getId());
							}
						dep.setAuthorName(to.getAuthorName());
						/*if(to.getDoi()!=null && to.getDoi()!="" && !to.getDoi().isEmpty()){
						dateValid=DateValidation(to.getDoi());
						if(!dateValid){
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.doi1"," is not valid format.Please Enter DD/MM/YYY)"));
						}
						else*/
						dep.setDoi(to.getDoi());
			//			}
						dep.setIsbn(to.getIsbn());
						dep.setIssueNumber(to.getIssueNumber());
						dep.setLanguage(to.getLanguage());
						dep.setDepartmentInstitution(to.getDepartmentInstitution());
						dep.setPlacePublication(to.getPlacePublication());
						dep.setCompanyInstitution(to.getCompanyInstitution());
						dep.setUrl(to.getUrl());
						dep.setImpactFactor(to.getImpactFactor());
						
						/*if(to.getDateAccepted()!=null && to.getDateAccepted()!="" && !to.getDateAccepted().isEmpty()){
						dateValid=DateValidation(to.getDateAccepted());
						if(!dateValid){
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.dateaccepted1"," is not valid format.Please Enter DD/MM/YYY)"));
						}
						else
						dep.setDateAccepted(CommonUtil.ConvertStringToDate(to.getDateAccepted()));
						}*/
						dep.setMonthYear(to.getDatePublished());
						/*if(to.getDateSent()!=null && to.getDateSent()!="" && !to.getDateSent().isEmpty()){
						dateValid=DateValidation(to.getDateSent());
						if(!dateValid){
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.datesent1"," is not valid format.Please Enter DD/MM/YYY)"));
							
						}
						else
						dep.setDateSent(CommonUtil.ConvertStringToDate(to.getDateSent()));
						}*/
						dep.setNameJournal(to.getNameJournal());
						dep.setPagesFrom(to.getPagesFrom());
						dep.setPagesTo(to.getPagesTo());
						dep.setTitle(to.getTitle());
						dep.setVolumeNumber(to.getVolumeNumber());
						dep.setAcademicYear(to.getAcademicYear());
						dep.setType(to.getType());
						
						if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
							
							dep.setIsActive(false);
						else
							dep.setIsActive(true);
					if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
						if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
							Employee emp1=new Employee();
							emp1.setId(Integer.parseInt(to.getEmployeeId()));
							dep.setEmployeeId(emp1);
						}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
						{
							Employee emp1=new Employee();
							emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
							dep.setEmployeeId(emp1);
						}
					}else
					{
						if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
							GuestFaculty gf=new GuestFaculty();
							gf.setId(Integer.parseInt(to.getGuestId()));
							dep.setGuestId(gf);
						}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
						{
							GuestFaculty emp2=new GuestFaculty();
							emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
							dep.setGuestId(emp2);
						}	
					}
						dep.setCreatedBy(empResPubForm.getUserId());
						dep.setCreatedDate(new Date());
						dep.setModifiedBy(empResPubForm.getUserId());
						dep.setLastModifiedDate(new Date());
						dep.setApprovedDate(to.getApprovedDate());
						dep.setApproverComment(to.getApproverComment());
						if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp2=new Employee();
								emp2.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp2);
								dep.setIsEmployee(true);
							}else if(app!=null)
							{
								dep.setApproverId(app);
								dep.setIsEmployee(true);
							}
						}								
						else
						{
							if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
								Employee emp2=new Employee();
								emp2.setId(Integer.parseInt(to.getApproverId()));
								dep.setApproverId(emp2);
								dep.setIsEmployee(false);
							}else if(adminId!=null)
							{
								dep.setApproverId(adminId);
								dep.setIsEmployee(false);
							}
						}
						if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
							dep.setIsApproved(true);
							if(to.getApprovedDate()==null)
								dep.setApprovedDate(new Date());
						}
						else
						{
							dep.setIsApproved(to.getIsApproved());
						}
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
						dep.setIsWorkshopTraining(false);
						dep.setIsSeminarAttended(false);
						dep.setIsAwardsAchievementsOthers(false);
						if(to.getAutoApprove()!=null &&  to.getAutoApprove().equalsIgnoreCase("1")){
							dep.setIsRejected(false);
						}else
						{
							dep.setIsRejected(to.getIsRejected());
						}
						dep.setRejectDate(to.getRejectDate());
						dep.setRejectReason(to.getRejectReason());
						if(errors==null || errors.isEmpty())
							empResPubDetails.add(dep);
						else{
							throw  new ApplicationException(); 
						}
						}
						}
					}
				  }
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
									to.getTitle()!=null && !to.getTitle().isEmpty()|| to.getVolumeNumber()!=null && !to.getVolumeNumber().isEmpty()||
									to.getPagesFrom()!=null && !to.getPagesFrom().isEmpty() || to.getPagesTo()!=null && !to.getPagesTo().isEmpty())
							{
								if (to.getId() > 0) {
									dep.setId(to.getId());
								}
							dep.setAuthorName(to.getAuthorName());
							dep.setNamePeriodical(to.getNamePeriodical());
							dep.setIsbn(to.getIsbn());
							dep.setIssueNumber(to.getIssueNumber());
							dep.setLanguage(to.getLanguage());
							if(to.getDateMonthYear()!=null && !to.getDateMonthYear().isEmpty()){
							dateValid=DateValidation(to.getDateMonthYear());
							if(!dateValid){
								errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.date.monthYear.publication1"," is not valid format.Please Enter DD/MM/YYY)"));
							}
							else
							dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
							}
							dep.setPagesFrom(to.getPagesFrom());
							dep.setPagesTo(to.getPagesTo());
							dep.setTitle(to.getTitle());
							dep.setVolumeNumber(to.getVolumeNumber());
							dep.setAcademicYear(to.getAcademicYear());
							dep.setType(to.getType());
							if((to.getType()!=null && !to.getType().isEmpty() && to.getType().equalsIgnoreCase("other")) && (to.getOtherTextArticle()!=null && !to.getOtherTextArticle().isEmpty())){
								dep.setOtherText(to.getOtherTextArticle());
							}
							
							if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
								
								dep.setIsActive(false);
							else
								dep.setIsActive(true);
							
							if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
								if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
									Employee emp1=new Employee();
									emp1.setId(Integer.parseInt(to.getEmployeeId()));
									dep.setEmployeeId(emp1);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
								{
									Employee emp1=new Employee();
									emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
									dep.setEmployeeId(emp1);
								}
							}else
							{
							if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
								GuestFaculty gf=new GuestFaculty();
								gf.setId(Integer.parseInt(to.getGuestId()));
								dep.setGuestId(gf);
							}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
								{
									GuestFaculty emp2=new GuestFaculty();
									emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
									dep.setGuestId(emp2);
								}	
							}
							dep.setCreatedBy(empResPubForm.getUserId());
							dep.setCreatedDate(new Date());
							dep.setModifiedBy(empResPubForm.getUserId());
							dep.setLastModifiedDate(new Date());
							dep.setApprovedDate(to.getApprovedDate());
							dep.setApproverComment(to.getApproverComment());
							if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
								if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
									Employee emp2=new Employee();
									emp2.setId(Integer.parseInt(to.getApproverId()));
									dep.setApproverId(emp2);
									dep.setIsEmployee(true);
								}else if(app!=null)
								{
									dep.setApproverId(app);
									dep.setIsEmployee(true);
								}
							}								
							else
							{
								if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
									Employee emp2=new Employee();
									emp2.setId(Integer.parseInt(to.getApproverId()));
									dep.setApproverId(emp2);
									dep.setIsEmployee(false);
								}else if(adminId!=null)
								{
									dep.setApproverId(adminId);
									dep.setIsEmployee(false);
								}
							}
							if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
								dep.setIsApproved(true);
								if(to.getApprovedDate()==null)
									dep.setApprovedDate(new Date());
							}else
							{
								dep.setIsApproved(to.getIsApproved());
							}
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
							dep.setIsWorkshopTraining(false);
							dep.setIsSeminarAttended(false);
							dep.setIsAwardsAchievementsOthers(false);
							if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
							dep.setIsRejected(false);
							}
							else
							{
								dep.setIsRejected(to.getIsRejected());
							}
							dep.setRejectDate(to.getRejectDate());
							dep.setRejectReason(to.getRejectReason());
							
							if(errors==null || errors.isEmpty())
								empResPubDetails.add(dep);
							else
								throw  new ApplicationException(); 
								
							}
						}
						}
					}
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
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								/*splChar=splCharValidationNew(to.getLanguage());
									if(!splChar){
										errMsg = errMsg + "Language";
									err=true;
									}
									else*/
								dep.setLanguage(to.getLanguage());
								dep.setNumberOfComments(to.getNumberOfComments());
								
								/*splChar=splCharValidationNew(to.getSubject());
								if(!splChar){
									errMsg = errMsg + ", " +"Subject";
								err=true;
								}
								else*/
								dep.setSubject(to.getSubject());
								
								dep.setUrl(to.getUrl());
								/*splChar=splCharValidationNew(to.getMonthYear());
								if(!splChar){
									errMsg = errMsg + ", " +"Month year";
								err=true;
								}
								else*/
								dep.setMonthYear(to.getMonthYear());
								
								/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg + ", " +"Title";
								err=true;
								}
								else*/
								dep.setTitle(to.getTitle());
								dep.setAcademicYear(to.getAcademicYear());
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{
									if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
										GuestFaculty gf=new GuestFaculty();
										gf.setId(Integer.parseInt(to.getGuestId()));
										dep.setGuestId(gf);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsRejected(false);
								}else
								{
								dep.setIsRejected(to.getIsRejected());
								}
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
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
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								/*splChar=splCharValidationNew(to.getLanguage());
									if(!splChar){
										errMsg = errMsg + "Language";
									err=true;
									}
									else*/
								dep.setLanguage(to.getLanguage());
								
								/*splChar=splCharValidationNew(to.getAuthorName());
									if(!splChar){
										errMsg = errMsg + ", " +"Author Name";
									err=true;
									}
									else*/
								dep.setAuthorName(to.getAuthorName());
								
								/*splChar=splCharValidationNew(to.getCompanyInstitution());
									if(!splChar){
										errMsg = errMsg + ", " +"Publishing Company /Institution";
									err=true;
									}
									else*/
								dep.setCompanyInstitution(to.getCompanyInstitution());
								
								/*splChar=splCharValidationNew(to.getIsbn());
									if(!splChar){
										errMsg = errMsg + ", " +"Isbn";
									err=true;
									}
									else*/
								dep.setIsbn(to.getIsbn());
								
								/*splChar=splCharValidationNew(to.getPlacePublication());
									if(!splChar){
										errMsg = errMsg + ", " +"Place of Publication";
									err=true;
									}
									else*/
								dep.setPlacePublication(to.getPlacePublication());
								dep.setMonthYear(to.getMonthYear());
								
								/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg + ", " +"Title";
								err=true;
								}
								else*/
								dep.setTitle(to.getTitle());
								dep.setTotalPages(to.getTotalPages());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setCoAuthored(to.getCoAuthored());
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{
									if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
										GuestFaculty gf=new GuestFaculty();
										gf.setId(Integer.parseInt(to.getGuestId()));
										dep.setGuestId(gf);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							  }
							}
						}
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
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
									int a= to.getAbstractObjectives().length();
								/*splChar=splCharValidationNew(to.getAbstractObjectives());
									if(!splChar){
										errMsg = errMsg + " Abstract and Objectives";
									err=true;
									}
									else*/
								dep.setAbstractObjectives(to.getAbstractObjectives());
									
								/*splChar=splCharValidationNew(to.getAuthorName());
									if(!splChar){
										errMsg = errMsg +" "+ " Author Name";
									err=true;
									}
									else*/
								dep.setAuthorName(to.getAuthorName());
								dep.setCaseNoteWorkPaper(to.getCaseNoteWorkPaper());
								
								/*splChar=splCharValidationNew(to.getSponsors());
								if(!splChar){
									errMsg = errMsg +" "+ " Institution /Department /Funding Agency /Sponsors";
								err=true;
								}
								else*/
								dep.setSponsors(to.getSponsors());
								int b=to.getTitle().length();
								
								/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg +" "+ " Title of Article";
								err=true;
								}
								else*/
								dep.setTitle(to.getTitle());
								dep.setAcademicYear(to.getAcademicYear());
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{
									if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
										GuestFaculty gf=new GuestFaculty();
										gf.setId(Integer.parseInt(to.getGuestId()));
										dep.setGuestId(gf);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if (errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
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
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								/*splChar=splCharValidationNew(to.getCompanyInstitution());
									if(!splChar){
										errMsg = errMsg + " Publishing Company /Institution";
									err=true;
									}
									else*/
								dep.setCompanyInstitution(to.getCompanyInstitution());
									
								/*splChar=splCharValidationNew(to.getAuthorName());
									if(!splChar){
										errMsg = errMsg +"," +" Author Names(s)";
									err=true;
									}
									else*/
								dep.setAuthorName(to.getAuthorName());
								
									/*if(to.getDoi()!=null && to.getDoi()!="" && !to.getDoi().isEmpty()){
									dateValid=DateValidation(to.getDoi());
									if(!dateValid){
										errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.doi1"," is not valid format.Please Enter DD/MM/YYY)"));
									}
									else*/
								dep.setDoi(to.getDoi());
							//.	}
								/*splChar=splCharValidationNew(to.getEditorsName());
								if(!splChar){
									errMsg = errMsg +"," +" Editor Name(s)";
								err=true;
								}
								else*/
								dep.setEditorsName(to.getEditorsName());
								
								/*splChar=splCharValidationNew(to.getIsbn());
								if(!splChar){
									errMsg = errMsg +"," +"ISBN";
								err=true;
								}
								else*/
								dep.setIsbn(to.getIsbn());
								
								/*splChar=splCharValidationNew(to.getLanguage());
								if(!splChar){
									errMsg = errMsg +"," +"Language";
								err=true;
								}
								else*/
								dep.setLanguage(to.getLanguage());
								
								/*splChar=splCharValidationNew(to.getMonthYear());
								if(!splChar){
									errMsg = errMsg +"," +"Month and Year";
								err=true;
								}
								else*/
								dep.setMonthYear(to.getMonthYear());
								dep.setPagesFrom(to.getPagesFrom());
								dep.setPagesTo(to.getPagesTo());
								
								/*splChar=splCharValidationNew(to.getPlacePublication());
								if(!splChar){
									errMsg = errMsg +"," +" Place of Publication";
								err=true;
								}
								else*/
								dep.setPlacePublication(to.getPlacePublication());
								
								/*splChar=splCharValidationNew(to.getTitle());
								if(!splChar){
									errMsg = errMsg +"," +" Title of Book";
								err=true;
								}
								else*/
								dep.setTitle(to.getTitle());
								int a = to.getTitleChapterArticle().length();
								
								/*splChar=splCharValidationNew(to.getTitleChapterArticle());
								if(!splChar){
									errMsg = errMsg +"," +" Title of the Chapter /Article";
								err=true;
								}
								else*/
								dep.setTitleChapterArticle(to.getTitleChapterArticle());
								dep.setTotalPages(to.getTotalPages());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setCoAuthored(to.getCoAuthored());
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{ if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
									 dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
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
								  to.getTitle()!=null && !to.getTitle().isEmpty() || to.getAbstractDetails()!=null && !to.getAbstractDetails().isEmpty() 
								  || to.getType()!=null && !to.getType().isEmpty() || to.getTypeOfPgm()!=null && !to.getTypeOfPgm().isEmpty())
								{
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								//dep.setCompanyInstitution(to.getCompanyInstitution());
								/*splChar=splCharValidationNew(to.getLanguage());
									if(!splChar){
										errMsg = errMsg + " Language";
									err=true;
									}
									else*/
								dep.setLanguage(to.getLanguage());
								
								/*splChar=splCharValidationNew(to.getMonthYear());
									if(!splChar){
										errMsg = errMsg +"," +" Month and Year";
									err=true;
									}
									else*/
								//dep.setMonthYear(to.getMonthYear());
								if(to.getMonthYear()!=null && !to.getMonthYear().isEmpty()){	
									dateValid=DateValidation(to.getMonthYear());
										if(!dateValid){
											errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.date.monthYear.conference"," is not valid format.Please Enter DD/MM/YYY)"));
										}
										else
									dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
									}
								/*splChar=splCharValidationNew(to.getNameConferencesSeminar());
									if(!splChar){
										errMsg = errMsg +"," +" Name of Conference /Seminar /Symposium";
									err=true;
									}
									else*/
								dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
									
								/*splChar=splCharValidationNew(to.getNameTalksPresentation());
									if(!splChar){
										errMsg = errMsg +"," +" Name(s)";
									err=true;
									}
									else*/
								dep.setNameTalksPresentation(to.getNameTalksPresentation());
								
								/*splChar=splCharValidationNew(to.getPlacePublication());
									if(!splChar){
										errMsg = errMsg +"," +" Place";
									err=true;
									}
									else*/
								dep.setPlacePublication(to.getPlacePublication());
									
								/*splChar=splCharValidationNew(to.getTitle());
									if(!splChar){
										errMsg = errMsg +"," +" Title of Article";
									err=true;
									}
									else*/
								dep.setTitle(to.getTitle());
									
								/*splChar=splCharValidationNew(to.getAbstractDetails());
									if(!splChar){
										errMsg = errMsg + " "+" Abstract";
									err=true;
									}
									else*/
								dep.setAbstracts(to.getAbstractDetails());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								dep.setTypeOfPgm(to.getTypeOfPgm());
								if((to.getTypeOfPgm()!=null && !to.getTypeOfPgm().isEmpty() && to.getTypeOfPgm().equalsIgnoreCase("other")) && (to.getOtherTextConf()!=null && !to.getOtherTextConf().isEmpty())){
									dep.setOtherText(to.getOtherTextConf());
								}
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{
									if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
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
								  to.getTitle()!=null && !to.getTitle().isEmpty()|| to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty())
								{
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								/*splChar=splCharValidationNew(to.getCompanyInstitution());
									if(!splChar){
										errMsg = errMsg + " Name of the Institution /Radio /TV Channel";
									err=true;
									}
									else*/
								dep.setNameOfPgm(to.getNameOfPgm());
								dep.setCompanyInstitution(to.getCompanyInstitution());
									
								/*splChar=splCharValidationNew(to.getLanguage());
									if(!splChar){
										errMsg = errMsg +"," +" Language";
									err=true;
									}
									else*/
								dep.setLanguage(to.getLanguage());
								
								/*splChar=splCharValidationNew(to.getMonthYear());
									if(!splChar){
										errMsg = errMsg +"," +" Month and Year";
									err=true;
									}
									else*/
								//dep.setMonthYear(to.getMonthYear());
								if(to.getMonthYear()!=null && !to.getMonthYear().isEmpty()){	
									dateValid=DateValidation(to.getMonthYear());
										if(!dateValid){
											errors.add(CMSConstants.ERROR, new ActionError("Date"," is not valid format.Please Enter DD/MM/YYY)"));
										}
										else
									dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getMonthYear()));
									}
								
								//dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
								
								/*splChar=splCharValidationNew(to.getNameTalksPresentation());
									if(!splChar){
										errMsg = errMsg +"," +" Name";
									err=true;
									}
									else*/
								dep.setNameTalksPresentation(to.getNameTalksPresentation());
									
								/*splChar=splCharValidationNew(to.getPlacePublication());
									if(!splChar){
										errMsg = errMsg +"," +" Place";
									err=true;
									}
									else*/
								dep.setPlacePublication(to.getPlacePublication());
									
								/*splChar=splCharValidationNew(to.getTitle());
									if(!splChar){
										errMsg = errMsg +"," +" Title of Article";
									err=true;
									}
									else*/
								dep.setTitle(to.getTitle());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{ if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
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
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								
								/*splChar=splCharValidationNew(to.getAspectRatio());
									if(!splChar){
										errMsg = errMsg + " Aspect Ratio";
									err=true;
									}
									else*/
								dep.setAspectRatio(to.getAspectRatio());
									
								/*splChar=splCharValidationNew(to.getAudioFormat());
									if(!splChar){
										errMsg = errMsg +"," +" Audio Format";
									err=true;
									}
									else*/
								dep.setAudioFormat(to.getAudioFormat());
									
								/*splChar=splCharValidationNew(to.getCopyrights());
									if(!splChar){
										errMsg = errMsg +"," +" Copyright";
									err=true;
									}
									else*/
								dep.setCopyrights(to.getCopyrights());
									
								/*splChar=splCharValidationNew(to.getCredits());
									if(!splChar){
										errMsg = errMsg +"," +" Credits";
									err=true;
									}
									else*/
								dep.setCredits(to.getCredits());
								
								/*splChar=splCharValidationNew(to.getDiscFormat());
									if(!splChar){
										errMsg = errMsg +"," +" Disc Format";
									err=true;
									}
									else*/
								dep.setDiscFormat(to.getDiscFormat());
									
								/*splChar=splCharValidationNew(to.getGenre());
									if(!splChar){
										errMsg = errMsg +"," +" Genre";
									err=true;
									}
									else*/
								dep.setGenre(to.getGenre());
									
								/*splChar=splCharValidationNew(to.getLanguage());
									if(!splChar){
										errMsg = errMsg +"," +" Language";
									err=true;
									}
									else*/
								dep.setLanguage(to.getLanguage());
									
								/*splChar=splCharValidationNew(to.getProducer());
									if(!splChar){
										errMsg = errMsg +"," +" Producer";
									err=true;
									}
									else*/
								dep.setProducer(to.getProducer());
								
								/*splChar=splCharValidationNew(to.getRunningTime());
									if(!splChar){
										errMsg = errMsg +"," +" Running Time";
									err=true;
									}
									else*/
								dep.setRunningTime(to.getRunningTime());
									
								/*splChar=splCharValidationNew(to.getSubtitles());
									if(!splChar){
										errMsg = errMsg +"," +" Subtitles";
									err=true;
									}
									else*/
								dep.setSubtitles(to.getSubtitles());
								
								/*splChar=splCharValidationNew(to.getTechnicalFormat());
									if(!splChar){
										errMsg = errMsg +"," +" Technical Format";
									err=true;
									}
									else*/
								dep.setTechnicalFormat(to.getTechnicalFormat());
									
								/*splChar=splCharValidationNew(to.getTitle());
									if(!splChar){
										errMsg = errMsg +"," +" Title";
									err=true;
									}
									else*/
								dep.setTitle(to.getTitle());
								dep.setAcademicYear(to.getAcademicYear());
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{ if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
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
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								/*splChar=splCharValidationNew(to.getAbstractObjectives());
									if(!splChar){
										errMsg = errMsg +" Abstract and Objectives";
									err=true;
									}
									else*/
								dep.setAbstractObjectives(to.getAbstractObjectives());
									
								/*splChar=splCharValidationNew(to.getInvestigators());
									if(!splChar){
										errMsg = errMsg +","+" Investigator(s)";
									err=true;
									}
									else*/
								dep.setInvestigators(to.getInvestigators());
									
								/*splChar=splCharValidationNew(to.getSponsors());
									if(!splChar){
										errMsg = errMsg +","+" Sponsors(s)";
									err=true;
									}
									else*/
								dep.setSponsors(to.getSponsors());
									
								/*splChar=splCharValidationNew(to.getTitle());
									if(!splChar){
										errMsg = errMsg +","+" Title";
									err=true;
									}
									else*/
								dep.setTitle(to.getTitle());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setInternalExternal(to.getInternalExternal());
								dep.setAmountGranted(to.getAmountGranted());
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{ if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
									
									
									
									
									
									
								/*	if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}else{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}*/
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
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
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								/*splChar=splCharValidationNew(to.getCompanyInstitution());
									if(!splChar){
										errMsg = errMsg +" Institution/University";
									err=true;
									}
									else*/
								dep.setCompanyInstitution(to.getCompanyInstitution());
									
								/*splChar=splCharValidationNew(to.getMonthYear());
									if(!splChar){
										errMsg = errMsg +","+" Month and Year of Defense";
									err=true;
									}
									else*/
								dep.setMonthYear(to.getMonthYear());
									
								/*splChar=splCharValidationNew(to.getNameGuide());
									if(!splChar){
										errMsg = errMsg +","+" Name of the Guide";
									err=true;
									}
									else*/
								dep.setNameGuide(to.getNameGuide());
									
								/*splChar=splCharValidationNew(to.getPlace());
									if(!splChar){
										errMsg = errMsg +","+" Place";
									err=true;
									}
									else*/
								dep.setPlacePublication(to.getPlace());
								
								/*splChar=splCharValidationNew(to.getSubject());
									if(!splChar){
										errMsg = errMsg +","+" Subject";
									err=true;
									}
									else*/
								dep.setSubject(to.getSubject());
									
								/*splChar=splCharValidationNew(to.getTitle());
									if(!splChar){
										errMsg = errMsg +","+" Title of Article";
									err=true;
									}
									else*/
								dep.setTitle(to.getTitle());
									
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								if(to.getSubmissionDate()!=null && !to.getSubmissionDate().isEmpty()){
									dateValid=DateValidation(to.getSubmissionDate());
									if(!dateValid){
										errors.add(CMSConstants.ERROR, new ActionError("Date of Submission is not valid format.Please Enter DD/MM/YYY)"));
									}
									else
								dep.setSubmissionDate(CommonUtil.ConvertStringToDate(to.getSubmissionDate()));
								}
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{   if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
								   dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
						}
						}
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
									if (to.getId() > 0) {
										dep.setId(to.getId());
								}
								
								/*splChar=splCharValidationNew(to.getCompanyInstitution());
									if(!splChar){
										errMsg = errMsg +" Institution/University";
									err=true;
									}
									else*/
								dep.setCompanyInstitution(to.getCompanyInstitution());
									
									/*splChar=splCharValidationNew(to.getMonthYear());
									if(!splChar){
										errMsg = errMsg +","+" Month and Year";
									err=true;
									}
									else*/
								dep.setMonthYear(to.getMonthYear());
									
								/*splChar=splCharValidationNew(to.getNameStudent());
									if(!splChar){
										errMsg = errMsg +","+" Name of the Student";
									err=true;
									}
									else*/
								dep.setNameStudent(to.getNameStudent());
									
								/*splChar=splCharValidationNew(to.getPlace());
									if(!splChar){
										errMsg = errMsg +","+" Place";
									err=true;
									}
									else*/
								dep.setPlacePublication(to.getPlace());
									
								/*splChar=splCharValidationNew(to.getSubject());
									if(!splChar){
										errMsg = errMsg +","+" Subject";
									err=true;
									}
									else*/
								dep.setSubject(to.getSubject());
								
								/*splChar=splCharValidationNew(to.getTitle());
									if(!splChar){
										errMsg = errMsg +","+" Title of Article";
									err=true;
									}
									else*/
								dep.setTitle(to.getTitle());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								/*code modified by sudhir*/
								//dep.setGuidedAdjudicated(to.getGuidedAbudjicated());
								dep.setGuidedAdjudicated("Guided");
								/*---------------*/
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{   if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
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
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								/*splChar=splCharValidationNew(to.getLanguage());
									if(!splChar){
										errMsg = errMsg +","+" Language";
									err=true;
									}
									else*/
								dep.setLanguage(to.getLanguage());
								if(to.getDateMonthYear()!=null && !to.getDateMonthYear().isEmpty()){	
								dateValid=DateValidation(to.getDateMonthYear());
									if(!dateValid){
										errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.date.monthYear.publication1"," is not valid format.Please Enter DD/MM/YYY)"));
									}
									else
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateMonthYear()));
								}
								/*splChar=splCharValidationNew(to.getNameConferencesSeminar());
								if(!splChar){
									errMsg = errMsg +","+"Name of Conference /Seminar /Symposium";
								err=true;
								}
								else*/
								dep.setNameConferencesSeminar(to.getNameConferencesSeminar());
								
								/*splChar=splCharValidationNew(to.getPlace());
								if(!splChar){
									errMsg = errMsg +","+"Place";
								err=true;
								}
								else*/
								dep.setPlacePublication(to.getPlace());
								
								/*splChar=splCharValidationNew(to.getNameOrganisers());
								if(!splChar){
									errMsg = errMsg +","+"Name of the Organizer(s)";
								err=true;
								}
								else*/
								dep.setNameOrganisers(to.getNameOrganisers());
								
								/*splChar=splCharValidationNew(to.getResoursePerson());
								if(!splChar){
									errMsg = errMsg +","+"Resourse Person(s) and Details";
								err=true;
								}
								else*/
								dep.setResoursePerson(to.getResoursePerson());
								
								/*splChar=splCharValidationNew(to.getSponsors());
								if(!splChar){
									errMsg = errMsg +","+"Institution /Department /Funding Agency /Sponsors";
								err=true;
								}
								else*/
								dep.setSponsors(to.getSponsors());
								dep.setAcademicYear(to.getAcademicYear());
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{   if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									 
								}
							}
							}
						}
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
								if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()|| to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty() || 
								to.getDuration()!=null && !to.getDuration().isEmpty() || to.getOrganisedBy()!=null && !to.getOrganisedBy().isEmpty())
								{
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()){		
								dateValid=DateValidation(to.getDateOfPgm());
									if(!dateValid){
										errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.date.pgm1"," is not valid format.Please Enter DD/MM/YYY)"));
									}
									else
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
								}
								if(to.getEndOfPgm()!=null && !to.getEndOfPgm().isEmpty()){		
									dateValid=DateValidation(to.getEndOfPgm());
										if(!dateValid){
											errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.date.pgm2"," is not valid format.Please Enter DD/MM/YYY)"));
										}
										else
									dep.setEndOfPgm(CommonUtil.ConvertStringToDate(to.getEndOfPgm()));
									}
								/*splChar=splCharValidationNew(to.getNameOfPgm());
								if(!splChar){
									errMsg = errMsg +","+" Name of program";
								err=true;
								}
								else*/
								dep.setNameConferencesSeminar(to.getNameOfPgm());
								
								/*splChar=splCharValidationNew(to.getOrganisedBy());
								if(!splChar){
									errMsg = errMsg +","+" Organized By";
								err=true;
								}
								else*/
								dep.setNameOrganisers(to.getOrganisedBy());
								
								/*splChar=splCharValidationNew(to.getDuration());
								if(!splChar){
									errMsg = errMsg +","+" Duration";
								err=true;
								}
								else*/
								dep.setRunningTime(to.getDuration());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								
								
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{
									if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
										GuestFaculty gf=new GuestFaculty();
										gf.setId(Integer.parseInt(to.getGuestId()));
										dep.setGuestId(gf);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(true);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else						
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
			 }else if (empResPubForm.getSubmitName().equalsIgnoreCase("WorkshopsAttended")) {
					if (empResPubForm.getEmpWorkshopsTO() != null			
							&& !empResPubForm.getEmpWorkshopsTO().isEmpty()) {
						Iterator<EmpWorkshopsFdpTrainingTO> itr = empResPubForm.getEmpWorkshopsTO().iterator();
						while (itr.hasNext()) {
							EmpWorkshopsFdpTrainingTO to = (EmpWorkshopsFdpTrainingTO) itr.next();
							EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
							if(to!=null)
							{
								if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()|| to.getNameOfPgm()!=null && !to.getNameOfPgm().isEmpty() || 
										to.getDuration()!=null && !to.getDuration().isEmpty() || to.getOrganisedBy()!=null && !to.getOrganisedBy().isEmpty())
										{
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								if(to.getDateOfPgm()!=null && !to.getDateOfPgm().isEmpty()){
								dateValid=DateValidation(to.getDateOfPgm());
									if(!dateValid){
										errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.date.pgm1"," is not valid format.Please Enter DD/MM/YYY)"));
									}
									else
								dep.setDateMonthYear(CommonUtil.ConvertStringToDate(to.getDateOfPgm()));
								}
								
								/*splChar=splCharValidationNew(to.getNameOfPgm());
								if(!splChar){
									errMsg = errMsg +","+" Name of Program";
								err=true;
								}
								else*/
								dep.setNameConferencesSeminar(to.getNameOfPgm());
								
								/*splChar=splCharValidationNew(to.getOrganisedBy());
								if(!splChar){
									errMsg = errMsg +","+" Name of Organisers";
								err=true;
								}
								else*/
								dep.setNameOrganisers(to.getOrganisedBy());
								
								/*splChar=splCharValidationNew(to.getDuration());
								if(!splChar){
									errMsg = errMsg +","+" Duration";
								err=true;
								}
								else*/
								dep.setRunningTime(to.getDuration());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setType(to.getType());
								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
									
									dep.setIsActive(false);
								else
									dep.setIsActive(true);
								
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{ if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
									dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(true);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(false);
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
									
								}
							}
							}
						}
			 }else if (empResPubForm.getSubmitName().equalsIgnoreCase("AwardsAchievements")) {
					if (empResPubForm.getEmpAwardsAchievementsOthersTO() != null			
							&& !empResPubForm.getEmpAwardsAchievementsOthersTO().isEmpty()) {
						Iterator<EmpAwardsAchievementsOthersTO> itr = empResPubForm.getEmpAwardsAchievementsOthersTO().iterator();
						while (itr.hasNext()) {
							EmpAwardsAchievementsOthersTO to = (EmpAwardsAchievementsOthersTO) itr.next();
							EmpResearchPublicDetails dep = new EmpResearchPublicDetails();
							if(to!=null)
							{
								if(to.getName()!=null && !to.getName().isEmpty()|| to.getPlace()!=null && !to.getPlace().isEmpty() || 
										to.getDescription()!=null && !to.getDescription().isEmpty() || to.getMonthYear()!=null && !to.getMonthYear().isEmpty())
										{
									if (to.getId() > 0) {
										dep.setId(to.getId());
									}
								dep.setMonthYear(to.getMonthYear());
								
								/*splChar=splCharValidationNew(to.getName());
								if(!splChar){
									errMsg = errMsg +" Name";
								err=true;
								}
								else*/
								dep.setName(to.getName());
								
								/*splChar=splCharValidationNew(to.getPlace());
								if(!splChar){
									errMsg = errMsg +","+" Place";
								err=true;
								}
								else*/
								dep.setPlace(to.getPlace());
								
								/*splChar=splCharValidationNew(to.getDescription());
								if(!splChar){
									errMsg = errMsg +","+" Description";
								err=true;
								}
								else*/
								dep.setDescription(to.getDescription());
								dep.setAcademicYear(to.getAcademicYear());
								dep.setOrganisationAwarded(to.getOrganisationAwarded());

								if((empResPubForm.getIsDelete()!=null && empResPubForm.getIsDelete().equalsIgnoreCase("true")) && (empResPubForm.getSelectedResId()!=null && !empResPubForm.getSelectedResId().isEmpty() && Integer.parseInt(empResPubForm.getSelectedResId())==to.getId()))
								
									dep.setIsActive(false);
								else
									dep.setIsActive(true);

								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getEmployeeId()!=null && !to.getEmployeeId().isEmpty()){
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(to.getEmployeeId()));
										dep.setEmployeeId(emp1);
									}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										Employee emp1=new Employee();
										emp1.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setEmployeeId(emp1);
									}
								}else
								{ if(to.getGuestId()!=null && !to.getGuestId().isEmpty()){
									GuestFaculty gf=new GuestFaculty();
									gf.setId(Integer.parseInt(to.getGuestId()));
									dep.setGuestId(gf);
								}else if(empResPubForm.getSelectedEmployeeId()!=null && !empResPubForm.getSelectedEmployeeId().isEmpty())
									{
										GuestFaculty emp2=new GuestFaculty();
										emp2.setId(Integer.parseInt(empResPubForm.getSelectedEmployeeId()));
										dep.setGuestId(emp2);
									}	
								}
								dep.setCreatedBy(empResPubForm.getUserId());
								dep.setCreatedDate(new Date());
								dep.setModifiedBy(empResPubForm.getUserId());
								dep.setLastModifiedDate(new Date());
								dep.setApprovedDate(to.getApprovedDate());
								dep.setApproverComment(to.getApproverComment());
								if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(true);
									}else if(app!=null)
									{
										dep.setApproverId(app);
										dep.setIsEmployee(true);
									}
								}								
								else
								{
									if(to.getApproverId()!=null && !to.getApproverId().isEmpty()){
										Employee emp2=new Employee();
										emp2.setId(Integer.parseInt(to.getApproverId()));
										dep.setApproverId(emp2);
										dep.setIsEmployee(false);
									}else if(adminId!=null)
									{
										dep.setApproverId(adminId);
										dep.setIsEmployee(false);
									}
								}
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1")){
									dep.setIsApproved(true);
									if(to.getApprovedDate()==null)
										dep.setApprovedDate(new Date());
								}else
								{
									dep.setIsApproved(to.getIsApproved());
								}
								
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
								dep.setIsWorkshopTraining(false);
								dep.setIsSeminarAttended(false);
								dep.setIsAwardsAchievementsOthers(true);
								
								if(to.getAutoApprove()!=null && to.getAutoApprove().equalsIgnoreCase("1"))
									dep.setIsRejected(false);
								else
									dep.setIsRejected(to.getIsRejected());
								dep.setRejectDate(to.getRejectDate());
								dep.setRejectReason(to.getRejectReason());
								if(errors==null || errors.isEmpty())
									empResPubDetails.add(dep);
								else
									throw  new ApplicationException(); 
								}
							}
							}
						}
			 }
			return empResPubDetails;
		}
	    
		public void convertBoToFormEmployee(Employee emp,
				EmpResPubDetailsForm  empResPubForm) throws Exception {
			
			if(emp!=null)
			{
				if (emp.getEmpImages() != null && !emp.getEmpImages().isEmpty()) {
					Iterator<EmpImages> itr=emp.getEmpImages().iterator();
					while (itr.hasNext()) {
						EmpImages bo =itr.next();
						
						if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
							empResPubForm.setResearchPhotoBytes(bo.getEmpPhoto());
							break;
						}else
						{
							
							empResPubForm.setResearchPhotoBytes(null);
						}
					}
					
				}
				else
				{		empResPubForm.setResearchPhotoBytes(null);
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
		
		public void convertBoToFormGuest(GuestFaculty emp,
				EmpResPubDetailsForm  empResPubForm) throws Exception {
			
			if(emp!=null)
			{
				if (emp.getEmpImages() != null && !emp.getEmpImages().isEmpty()) {
					Iterator<GuestImages> itr=emp.getEmpImages().iterator();
					while (itr.hasNext()) {
						GuestImages bo =itr.next();
						
						if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
							empResPubForm.setResearchPhotoBytes(bo.getEmpPhoto());
							break;
						}
						else
						{
							
							empResPubForm.setResearchPhotoBytes(null);
						}
					}
					
				}else
				{
					empResPubForm.setResearchPhotoBytes(null);
				}
					empResPubForm.setFingerprintId("");
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