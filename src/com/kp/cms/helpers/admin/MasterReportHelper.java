package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.bo.admin.EligibleSubjects;
import com.kp.cms.bo.admin.EmployeeCategory;
import com.kp.cms.bo.admin.ExtracurricularActivity;
import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.Grade;
import com.kp.cms.bo.admin.GuidelinesDoc;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewStatus;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.LeaveType;
import com.kp.cms.bo.admin.Menus;
import com.kp.cms.bo.admin.Modules;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.NewsEvents;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.Preferences;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Recommendor;
import com.kp.cms.bo.admin.Region;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.RemarkType;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.TermsConditions;
import com.kp.cms.bo.admin.University;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.MasterReportForm;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CourseApplicationNoTO;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.to.admin.CourseSchemeTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.DocTypeTO;
import com.kp.cms.to.admin.EligibilityCriteriaTO;
import com.kp.cms.to.admin.EligibleSubjectsTO;
import com.kp.cms.to.admin.ExtracurricularActivityTO;
import com.kp.cms.to.admin.GradeTO;
import com.kp.cms.to.admin.GuidelinesEntryTO;
import com.kp.cms.to.admin.NewsEventsTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.PreferencesTO;
import com.kp.cms.to.admin.PrerequisiteTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.RecommendedByTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.RemarkTypeTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.SubjectGroupSubjectsTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.admin.TermsConditionTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admin.WeightageTO;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.to.admission.ApplicationNumberTO;
import com.kp.cms.to.admission.CurriculumSchemeDurationTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewSubroundsTO;
import com.kp.cms.to.attendance.ActivityTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.ClassSchemewiseTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.attendance.MasterReportTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.fee.FeeAccountTO;
import com.kp.cms.to.fee.FeeAdditionalTO;
import com.kp.cms.to.fee.FeeBillNumberTO;
import com.kp.cms.to.fee.FeeDivisionTO;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.fee.FeeHeadingTO;
import com.kp.cms.to.usermanagement.MenusTO;
import com.kp.cms.to.usermanagement.ModuleTO;
import com.kp.cms.utilities.CommonUtil;

public class MasterReportHelper {
	private static final Log log = LogFactory.getLog(MasterReportHelper.class);
	/**
	 * @param studentSearchBo
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will converty BO' to TO's
	 */
	public static List<MasterReportTO> convertBoToTo(List masterBO,MasterReportForm masterReportForm,String []fields,HttpServletRequest req) {
		log.info("entered convertBoToTo..");
		List<MasterReportTO> candidateSearchTOList = new ArrayList<MasterReportTO>();
		if(masterReportForm!=null && masterReportForm.getMasterTable()!=null){
			
			Iterator itr = masterBO.iterator(); 
			//Prepare data for Caste Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.CASTE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojb = (Object[]) itr.next();
					Caste cast = (Caste) ojb[0];
					String createdBy = (String) ojb[1];
					String modifiedBy = (String) ojb[2];
					CasteTO casteTO = new CasteTO();
					casteTO.setCasteName(cast.getName());
					if(createdBy!= null){
						casteTO.setCreatedBy(createdBy);
					}
					casteTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(cast.getCreatedDate()), CMSConstants.SOURCE_DATE ,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						casteTO.setModifiedBy(modifiedBy);
					}
					casteTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(cast.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(cast.getIsActive() ){
						casteTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						casteTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					masterTO.setCasteTO(casteTO);
					candidateSearchTOList.add(masterTO);
				}				
			}
			//Prepare data for Country Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.COUNTRY)){
				while(itr.hasNext()){
					Object[] countryitr = (Object[]) itr.next();
					Country country = (Country) countryitr[0];
					String createdBy = (String) countryitr[1];
					String modifiedBy = (String) countryitr[2];
					CountryTO countryTO = new CountryTO(); 
					MasterReportTO masterTO = new MasterReportTO();	
					countryTO.setName(country.getName());
					if(createdBy!= null){
						countryTO.setCreated(createdBy);
					}
					countryTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(country.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						countryTO.setModified(modifiedBy);
					}
					
					countryTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(country.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(country.getIsActive()){
						countryTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						countryTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					masterTO.setCountry(countryTO);
					candidateSearchTOList.add(masterTO);
					
				}				
			}
			//Prepare data for AdmissionStatus Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.INTERVIEW_STATUS)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					InterviewStatus interviewStatus = (InterviewStatus)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					AdmissionStatusTO admissionStatusTO = new AdmissionStatusTO();
					if(interviewStatus.getName()!=null){
					admissionStatusTO.setName(interviewStatus.getName());
					}
					if(createdBy!=null){
						admissionStatusTO.setCreatedBy(createdBy);
					}
					if(modifiedBy!=null){
						admissionStatusTO.setModifiedBy(modifiedBy);
					}
					if(interviewStatus.getCreatedDate()!=null){
						admissionStatusTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewStatus.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(interviewStatus.getLastModifiedDate()!=null){
						admissionStatusTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewStatus.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(interviewStatus.getIsActive() ){
						admissionStatusTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						admissionStatusTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					masterTO.setAdmissionStatusTO(admissionStatusTO);
					candidateSearchTOList.add(masterTO);
				}				
			}
			//Prepare data for CourseScheme Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.COURSE_SCHEME)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					CourseScheme courseScheme = (CourseScheme)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];					
					CourseSchemeTO courseSchemeTO = new CourseSchemeTO();
					courseSchemeTO.setCourseSchemeName(courseScheme.getName());
					if(courseScheme.getIsActive() ){
						courseSchemeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						courseSchemeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					if(createdBy!=null){
						courseSchemeTO.setCreatedBy(createdBy);
					}
					if(modifiedBy!=null){
						courseSchemeTO.setModifiedBy(modifiedBy);
					}
					if(courseScheme.getCreatedDate()!=null){
						courseSchemeTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(courseScheme.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(courseScheme.getLastModifiedDate()!=null){
						courseSchemeTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(courseScheme.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					
					
					masterTO.setCourseSchemeTO(courseSchemeTO);
					candidateSearchTOList.add(masterTO);
					
				}				
			}
			//Prepare data for Religion Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.RELIGION)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Religion religion = (Religion)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					masterTO.setName(religion.getName());
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(religion.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(religion.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(religion.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for Prerequisite Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.PREREQUISITE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Prerequisite prerequisite = (Prerequisite)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(prerequisite.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(prerequisite.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(prerequisite.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(prerequisite.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for AdmittedThrough Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.ADMITTED_THROUGH)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					AdmittedThrough admittedThrough = (AdmittedThrough)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(admittedThrough.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admittedThrough.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(createdBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admittedThrough.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(admittedThrough.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for ProgramType Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.PROGRAM_TYPE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					ProgramType programType = (ProgramType)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(programType.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(programType.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(programType.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(programType.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for Occupation Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.OCCUPATION)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Occupation occupation = (Occupation)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(occupation.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(occupation.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(occupation.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(occupation.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for ResidentCategory Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.RESIDENT_CATEGORY)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					ResidentCategory residentCategory = (ResidentCategory)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(residentCategory.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(residentCategory.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(residentCategory.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(residentCategory.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for Region Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.REGION)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Region region = (Region)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(region.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(region.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(region.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(region.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for University Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.UNIVERSITY)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					University university = (University)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(university.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(university.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(university.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(university.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for Department Report
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.DEPARTMENT)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Department department = (Department)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(department.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(department.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(department.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(department.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					candidateSearchTOList.add(masterTO);
				}				
			}
			//Prepare data for Roles
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.ROLES)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Roles roles = (Roles)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					masterTO.setName(roles.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(roles.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(roles.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(roles.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for EmployeeCategory
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.EMPLOYEE_CATEGORY)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					EmployeeCategory employeeCategory = (EmployeeCategory)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(employeeCategory.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(employeeCategory.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!=  null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(employeeCategory.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(employeeCategory.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for LeaveType
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.LEAVE_TYPE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					LeaveType leaveType = (LeaveType)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(leaveType.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(leaveType.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(leaveType.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(leaveType.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					candidateSearchTOList.add(masterTO);	
				}				
			}
			//Prepare data for FeeDivision
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.FEE_DIVISION)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					FeeDivision feeDivision = (FeeDivision)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					masterTO.setName(feeDivision.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeDivision.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeDivision.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(feeDivision.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for FeePaymentMode
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.FEE_PAYMENT_MODE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	 
					Object[] ojbit = (Object[]) itr.next();
					FeePaymentMode feePaymentMode = (FeePaymentMode)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(feePaymentMode.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!= null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feePaymentMode.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(modifiedBy!= null){
						masterTO.setModifiedBy(modifiedBy);
					}
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feePaymentMode.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(feePaymentMode.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for Designation
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.DESIGNATION)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Designation designation = (Designation)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					masterTO.setName(designation.getName());
					if(masterReportForm.getMasterTable() != null){
						masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
					}
					if(createdBy!=null){
						masterTO.setCreatedBy(createdBy);
					}
					masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(designation.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					masterTO.setModifiedBy(modifiedBy);
					masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(designation.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					if(designation.getIsActive() ){
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}					
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for Preferences
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.PREFERENCES)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	 
					Object[] ojbit = (Object[]) itr.next();
					Preferences preferences = (Preferences)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					PreferencesTO preferencesTO = new PreferencesTO();
					CourseTO courseTO = new CourseTO();
					CourseTO preCourseTO = new CourseTO();
					if(preferences.getCourseByCourseId()!=null && preferences.getCourseByCourseId().getName()!=null){
						courseTO.setName(preferences.getCourseByCourseId().getName());
						preCourseTO.setName(preferences.getCourseByPrefCourseId().getName());
						preferencesTO.setCourseTO(courseTO);
						preferencesTO.setPrefCourseTO(preCourseTO);
					}
					if(createdBy!=null){
						preferencesTO.setCreatedBy(createdBy);
					}
					if(preferences.getCreatedDate()!=null){
						preferencesTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(preferences.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						preferencesTO.setModifiedBy(modifiedBy);
					}
					if(preferences.getLastModifiedDate()!=null){
						preferencesTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(preferences.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(preferences.getIsActive()!=null){
						if(preferences.getIsActive() ){
							preferencesTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							preferencesTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					masterTO.setPreferencesTO(preferencesTO);
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for Currency
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.CURRENCY)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Currency currency = (Currency)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					CurrencyMasterTO currencyMasterTO = new CurrencyMasterTO();
					if(currency.getName()!=null){
						currencyMasterTO.setName(currency.getName());
					}
					if(currency.getCurrencySubdivision()!=null){
						currencyMasterTO.setCurrencySubdivision(currency.getCurrencySubdivision());
					}
					if(currency.getCreatedDate()!=null){
						currencyMasterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(currency.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(createdBy!=null){
						currencyMasterTO.setCreatedBy(createdBy);
					}
					if(modifiedBy!=null){
						currencyMasterTO.setModifiedBy(modifiedBy);
					}
					if(currency.getLastModifiedDate()!=null){
						currencyMasterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(currency.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(currency.getIsActive()!=null){
						if(currency.getIsActive() ){
							currencyMasterTO.setActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							currencyMasterTO.setActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(currency.getSymbol()!=null){
						currencyMasterTO.setSymbol(currency.getSymbol());						
					}
					if(currency.getCurrencyCode()!=null){
						currencyMasterTO.setCurrencyCode(currency.getCurrencyCode());						
					}					
					masterTO.setCurrencyMasterTO(currencyMasterTO);
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for ReligionSection
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.RELIGION_SECTION)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					ReligionSection religionSection = (ReligionSection)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					ReligionSectionTO religionSectionTO = new ReligionSectionTO();
					ReligionTO religionTO = new ReligionTO();
					
					if(religionSection.getName()!=null){
						religionSectionTO.setName(religionSection.getName());
					}
					if(religionSection.getReligion()!=null && religionSection.getReligion().getName()!=null){
						religionTO.setReligionName(religionSection.getReligion().getName());
						religionSectionTO.setReligionTO(religionTO);				
					}
					if(createdBy!=null){
						religionSectionTO.setCreated(createdBy);
					}
					if(religionSection.getCreatedDate()!=null){
						religionSectionTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(religionSection.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						religionSectionTO.setModified(modifiedBy);
					}
					if(religionSection.getLastModifiedDate()!=null){
						religionSectionTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(religionSection.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(religionSection.getIsActive()!=null){
						if(religionSection.getIsActive() ){
							religionSectionTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							religionSectionTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					masterTO.setReligionSectionTO(religionSectionTO);
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for State
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.STATE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					State state = (State)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					StateTO stateTO = new StateTO();
					CountryTO countryTO = new CountryTO();
					
					if(state.getName()!=null){
						stateTO.setName(state.getName());
					}
					if(state.getCountry()!=null && state.getCountry().getName()!=null){
						countryTO.setName(state.getCountry().getName());
						stateTO.setCountryTo(countryTO);				
					}
					if(createdBy!=null){
						stateTO.setCreatedBy(createdBy);
					}
					if(state.getCreatedDate()!=null){
						stateTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(state.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						stateTO.setModifiedBy(modifiedBy);
					}
					if(state.getLastModifiedDate()!=null){
						stateTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(state.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(state.getIsActive()!=null){
						if(state.getIsActive() ){
							stateTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							stateTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					masterTO.setStateTO(stateTO);
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for CoursePrerequisite
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.COURSE_PREREQUISITE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					CoursePrerequisite coursePrerequisite = (CoursePrerequisite)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					CoursePrerequisiteTO coursePrerequisiteTO = new CoursePrerequisiteTO();
					CourseTO courseTO = new CourseTO();
					PrerequisiteTO prerequisiteTO = new PrerequisiteTO();
					WeightageTO weightageTO = new WeightageTO();
					
					if(coursePrerequisite.getCourse()!=null && coursePrerequisite.getCourse().getName()!=null){
						courseTO.setName(coursePrerequisite.getCourse().getName());
						coursePrerequisiteTO.setCourseTO(courseTO);				
					}
					if(coursePrerequisite.getPrerequisite()!=null && coursePrerequisite.getPrerequisite().getName()!=null){
						prerequisiteTO.setName(coursePrerequisite.getPrerequisite().getName());
						coursePrerequisiteTO.setPrerequisiteTO(prerequisiteTO);				
					}
					if(coursePrerequisite.getWeightage()!=null && coursePrerequisite.getWeightage().getName()!=null){
						weightageTO.setName(coursePrerequisite.getWeightage().getName());
						coursePrerequisiteTO.setWeightageTO(weightageTO);				
					}
					if(coursePrerequisite.getPercentage()!=null){
						coursePrerequisiteTO.setPercentage1(coursePrerequisite.getPercentage().toString());
					}
					if(coursePrerequisite.getWeightagePercentage()!=null){
						coursePrerequisiteTO.setTotalMark1(coursePrerequisite.getWeightagePercentage().toString());
					}
					if(coursePrerequisite.getTotalMark()!=null){
						coursePrerequisiteTO.setTotalMark2(coursePrerequisite.getTotalMark().toString());
					}
					if(createdBy!=null){
						coursePrerequisiteTO.setCreatedBy(createdBy);
					}
					if(coursePrerequisite.getCreatedDate()!=null){
						coursePrerequisiteTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(coursePrerequisite.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						coursePrerequisiteTO.setModifiedBy(modifiedBy);
					}
					if(coursePrerequisite.getLastModifiedDate()!=null){
						coursePrerequisiteTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(coursePrerequisite.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(coursePrerequisite.getIsActive()!=null){
						if(coursePrerequisite.getIsActive() ){
							coursePrerequisiteTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							coursePrerequisiteTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					masterTO.setCoursePrerequisiteTO(coursePrerequisiteTO);
					candidateSearchTOList.add(masterTO);
				}				
			}
			//Prepare data for Program
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.PROGRAM)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Program program = (Program)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					ProgramTO programTO = new ProgramTO();
					ProgramTypeTO programTypeTO = new ProgramTypeTO();
					
					if(program.getProgramType()!=null && program.getProgramType().getName()!=null){
						programTypeTO.setProgramTypeName(program.getProgramType().getName());
						programTO.setProgramTypeTo(programTypeTO);	
					}
					if(program.getCode()!=null){
						programTO.setCode(program.getCode());
					}
					if(program.getName()!=null){
						programTO.setName(program.getName());
					}
					
					if(createdBy!=null){
						programTO.setCreated(createdBy);
					}
					if(program.getCreatedDate()!=null){
						programTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(program.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						programTO.setModified(modifiedBy);
					}
					if(program.getLastModifiedDate()!=null){
						programTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(program.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(program.getIsActive()!=null){
						if(program.getIsActive() ){
							programTO.setActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsMotherTongue()!=null){
						if(program.getIsMotherTongue() ){
							programTO.setMotherTongue(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setMotherTongue(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsSecondLanguage()!=null){
						if(program.getIsSecondLanguage() ){
							programTO.setSecondLanguage(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setSecondLanguage(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsDisplayLanguageKnown()!=null){
						if(program.getIsDisplayLanguageKnown() ){
							programTO.setDisplayLanguageKnown(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setDisplayLanguageKnown(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsFamilyBackground()!=null){
						if(program.getIsFamilyBackground() ){
							programTO.setFamilyBackground(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setFamilyBackground(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsHeightWeight()!=null){
						if(program.getIsHeightWeight() ){
							programTO.setHeightWeight(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setHeightWeight(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsEntranceDetails()!=null){
						if(program.getIsEntranceDetails() ){
							programTO.setEntranceDetails(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setEntranceDetails(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsLateralDetails()!=null){
						if(program.getIsLateralDetails() ){
							programTO.setLateralDetails(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setLateralDetails(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsDisplayTrainingCourse()!=null){
						if(program.getIsDisplayTrainingCourse() ){
							programTO.setDisplayTrainingCourse(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setDisplayTrainingCourse(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsTransferCourse()!=null){
						if(program.getIsTransferCourse() ){
							programTO.setTransferCourse(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setTransferCourse(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsAdditionalInfo()!=null){
						if(program.getIsAdditionalInfo() ){
							programTO.setAdditionalInfo(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setAdditionalInfo(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsExtraDetails()!=null){
						if(program.getIsExtraDetails() ){
							programTO.setExtraDetails(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setExtraDetails(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsTCDetails()!=null){
						if(program.getIsTCDetails() ){
							programTO.setTcDetails(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setTcDetails(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(program.getIsRegistrationNo()!=null){
						if(program.getIsRegistrationNo() ){
							programTO.setRegistrationNo(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							programTO.setRegistrationNo(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					masterTO.setProgramTO(programTO);
					candidateSearchTOList.add(masterTO);
				}				
			}
			//Prepare data for Course
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.COURSE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Course course = (Course)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					CourseTO courseTO = new CourseTO();
					ProgramTO programTO = new ProgramTO();
					
					if(course.getProgram()!=null && course.getProgram().getName()!=null){
						programTO.setName(course.getProgram().getName());
						courseTO.setProgramTo(programTO);	
					}
					if(course.getName()!=null){
						courseTO.setName(course.getName());	
					}
					if(course.getCode()!=null){
						courseTO.setCode(course.getCode());	
					}
					if(createdBy!=null){
						courseTO.setCreated(createdBy);
					}
					if(course.getCreatedDate()!=null){
						courseTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(course.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						courseTO.setModified(modifiedBy);
					}
					if(course.getLastModifiedDate()!=null){
						courseTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(course.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(course.getIsActive()!=null){
						if(course.getIsActive() ){
							courseTO.setActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							courseTO.setActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(course.getIsAutonomous()!=null){
						if(course.getIsAutonomous() ){
							courseTO.setAutonomous(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							courseTO.setAutonomous(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(course.getIsWorkExperienceRequired()!=null){
						if(course.getIsWorkExperienceRequired() ){
							courseTO.setIsWorkExperienceRequired(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							courseTO.setIsWorkExperienceRequired(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(course.getMaxIntake()!=null){
						courseTO.setMaxIntake(String.valueOf(course.getMaxIntake()));
					}
					if(course.getPayCode()!=null){
						courseTO.setPayCode(course.getPayCode());
					}
					if(course.getAmount()!=null){
						courseTO.setAmount(course.getAmount());
					}
					
					masterTO.setCourseTO(courseTO);
					candidateSearchTOList.add(masterTO);					
				}
			}	
			//Prepare data for GuidelinesDoc
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.GUIDELINESDOC)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					GuidelinesDoc guidelinesDoc = (GuidelinesDoc)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					GuidelinesEntryTO guidelinesEntryTO = new GuidelinesEntryTO();
					CourseTO courseTO = new CourseTO();
					
					if(guidelinesDoc.getCourse()!=null && guidelinesDoc.getCourse().getName()!=null){
						courseTO.setName(guidelinesDoc.getCourse().getName());
						guidelinesEntryTO.setCourseTO(courseTO);
					}
					int year = 0;
					int tempYear = 0;
					if(guidelinesDoc.getYear()!=null){
						year = guidelinesDoc.getYear();
						tempYear = year + 1;
						guidelinesEntryTO.setAcademicYear("" + year + "-" + tempYear);
					}	
					if(guidelinesDoc.getFileName()!=null){
						guidelinesEntryTO.setFileName(guidelinesDoc.getFileName());	
					}
					if(guidelinesDoc.getContentType()!=null){
						guidelinesEntryTO.setContentType(guidelinesDoc.getContentType());	
					}
					if(createdBy!=null){
						guidelinesEntryTO.setCreatedBy(createdBy);
					}
					if(guidelinesDoc.getCreatedDate()!=null){
						guidelinesEntryTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(guidelinesDoc.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						guidelinesEntryTO.setModifiedBy(modifiedBy);
					}
					if(guidelinesDoc.getLastModifiedDate()!=null){
						guidelinesEntryTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(guidelinesDoc.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(guidelinesDoc.getIsActive()!=null){
						if(guidelinesDoc.getIsActive() ){
							guidelinesEntryTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							guidelinesEntryTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}					
					masterTO.setGuidelinesEntryTO(guidelinesEntryTO);
					candidateSearchTOList.add(masterTO);					
				}
			}
			//Prepare data for TermsConditions
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.TERMS_CONDITIONS)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					TermsConditions termsConditions = (TermsConditions)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					TermsConditionTO termsConditionTO = new TermsConditionTO();
					CourseTO courseTO = new CourseTO();
					if(termsConditions.getCourse()!=null && termsConditions.getCourse().getName()!=null){
						courseTO.setName(termsConditions.getCourse().getName());
						termsConditionTO.setCourseTo(courseTO);
					} 
					int year = 0;
					int tempYear = 0;
					if(termsConditions.getYear()!=null){
						year = termsConditions.getYear();
						tempYear = year + 1;
						termsConditionTO.setAcademicYear(""+year+"-"+tempYear);
					}
					if(termsConditions.getDescription() !=null){
						termsConditionTO.setDescription(termsConditions.getDescription());
					}
					if(termsConditions.getIsActive()!=null){
						if(termsConditions.getIsActive() ){
							termsConditionTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							termsConditionTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}		
					if(createdBy!= null){
						termsConditionTO.setCreatedBy(createdBy);
					}
					if(modifiedBy!= null){
						termsConditionTO.setModifiedBy(modifiedBy);
					}
					if(termsConditions.getCreatedDate()!=null){
						termsConditionTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(termsConditions.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(termsConditions.getLastModifiedDate()!= null){
						termsConditionTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(termsConditions.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						
					}
					
					masterTO.setTermsConditionTO(termsConditionTO);
					candidateSearchTOList.add(masterTO);					
				}
			}	
			//Prepare data for Subject
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.SUBJECT)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Subject subject = (Subject)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					SubjectTO subjectTO = new SubjectTO();
					
					if(subject.getName()!=null){
						subjectTO.setName(subject.getName());
					}
					if(subject.getCode()!=null){
						subjectTO.setCode(subject.getCode());
					}
					if(createdBy!=null){
						subjectTO.setCreatedBy(createdBy);
					}
					if(subject.getCreatedDate()!=null){
						subjectTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(subject.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						subjectTO.setModifiedBy(modifiedBy);
					}
					if(subject.getLastModifiedDate()!=null){
						subjectTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(subject.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(subject.getIsActive()!=null){
						if(subject.getIsActive() ){
							subjectTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							subjectTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(subject.getTotalMarks()!=null){
						subjectTO.setTotalmarks(String.valueOf(subject.getTotalMarks()));
					}
					if(subject.getPassingMarks()!=null){
						subjectTO.setPassingmarks(String.valueOf(subject.getPassingMarks()));
					}
					if(subject.getIsSecondLanguage()!=null){
						if(subject.getIsSecondLanguage() ){
							subjectTO.setIsSecondLanguage(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							subjectTO.setIsSecondLanguage(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(subject.getIsOptionalSubject()!=null){
						if(subject.getIsActive() ){
							subjectTO.setIsOptionalSubject(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							subjectTO.setIsOptionalSubject(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}					
					masterTO.setSubjectTO(subjectTO);
					candidateSearchTOList.add(masterTO);
				}
			}	
			//Prepare data for Classes
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.CLASSES)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Classes classes = (Classes)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					ClassesTO classesTO = new ClassesTO();
					CourseTO courseTO = new CourseTO();
					
					if(classes.getName()!=null){
						classesTO.setClassName(classes.getName());
					}
					if(classes.getSectionName()!=null && !classes.getSectionName().isEmpty()){
						classesTO.setSectionName(classes.getSectionName());
					}
					if(classes.getCourse()!=null && classes.getCourse().getName()!=null){
						courseTO.setName(classes.getCourse().getName());
						classesTO.setCourseTo(courseTO);
					}
					if(createdBy!=null){
						classesTO.setCreatedBy(createdBy);
					}
					if(classes.getCreatedDate()!=null){
						classesTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(classes.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						classesTO.setModifiedBy(modifiedBy);
					}
					if(classes.getLastModifiedDate()!=null){
						classesTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(classes.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(classes.getIsActive()!=null){
						if(classes.getIsActive() ){
							classesTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							classesTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(classes.getTermNumber()!=null){
						classesTO.setTermNo(classes.getTermNumber());
					}	
					masterTO.setClassesTO(classesTO);
					candidateSearchTOList.add(masterTO);
				}
			}	
			//Prepare data for DocType
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.DOCTYPE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					DocType docType = (DocType)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					DocTypeTO docTypeTO = new DocTypeTO();

					if(docType.getName()!= null){
						docTypeTO.setName(docType.getName());
					}
					if(docType.getDocShortName()!= null){
						docTypeTO.setDocShortName(docType.getDocShortName());
					}
					if(docType.getPrintName()!= null){
						docTypeTO.setPrintName(docType.getPrintName());
					}
					
					if(createdBy!=null){
						docTypeTO.setCreatedBy(createdBy);
					}
					if(docType.getCreatedDate()!=null){
						docTypeTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(docType.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						docTypeTO.setModifiedBy(modifiedBy);
					}
					if(docType.getLastModifiedDate()!=null){
						docTypeTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(docType.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(docType.getIsActive()!=null){
						if(docType.getIsActive() ){
							docTypeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							docTypeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					masterTO.setDocTypeTO(docTypeTO);
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for College
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.COLLEGE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					College college = (College)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					CollegeTO collegeTO = new CollegeTO();
					UniversityTO universityTO = new UniversityTO();

					if(college.getName()!= null){
						collegeTO.setName(college.getName());
					}
					if(college.getUniversity()!= null && college.getUniversity().getName()!= null){
						universityTO.setName(college.getUniversity().getName());
						collegeTO.setUniversityTO(universityTO);
					}
					if(createdBy!=null){
						collegeTO.setCreatedBy(createdBy);
					}
					if(college.getCreatedDate()!=null){
						collegeTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(college.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						collegeTO.setModifiedBy(modifiedBy);
					}
					if(college.getLastModifiedDate()!=null){
						collegeTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(college.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(college.getIsActive()!=null){
						if(college.getIsActive() ){
							collegeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							collegeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					masterTO.setCollegeTO(collegeTO);
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for Grade
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.GRADE)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Grade grade = (Grade)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					GradeTO gradeTO = new GradeTO();

					if(grade.getGrade()!= null){
						gradeTO.setGrade(grade.getGrade());
					}
					if(grade.getMarks()!= null){
						gradeTO.setGrade(grade.getGrade());
					}
					
					gradeTO.setMark(grade.getMarks());
					if(createdBy!=null){
						gradeTO.setCreatedBy(createdBy);
					}
					if(grade.getCreatedDate()!=null){
						gradeTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(grade.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						gradeTO.setModifiedBy(modifiedBy);
					}
					if(grade.getLastModifiedDate()!=null){
						gradeTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(grade.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(grade.isIsActive() ){
						gradeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						gradeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					masterTO.setGradeTO(gradeTO);
					candidateSearchTOList.add(masterTO);					
				}				
			}
			//Prepare data for Recommendor
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.RECOMMENDOR)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					Recommendor recommendor = (Recommendor)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					RecommendedByTO recommendedByTO = new RecommendedByTO();
					StateTO stateTO = new StateTO();
					CountryTO countryTO = new CountryTO();

					if(recommendor.getCode()!= null){
						recommendedByTO.setCode(recommendor.getCode());
					}
					if(recommendor.getName()!= null){
						recommendedByTO.setName(recommendor.getName());
					}
					if(recommendor.getAddressLine1()!= null){
						recommendedByTO.setAddressLine1(recommendor.getAddressLine1());
					}
					if(recommendor.getAddressLine2()!= null){
						recommendedByTO.setAddressLine2(recommendor.getAddressLine1());
					}
					if(recommendor.getCity()!= null){
						recommendedByTO.setCity(recommendor.getCity());
					}
					if(recommendor.getState()!= null){
						stateTO.setName(recommendor.getState().getName());
						recommendedByTO.setStateTO(stateTO);
					}
					if(recommendor.getCountry()!= null){
						countryTO.setName(recommendor.getState().getCountry().getName());
						recommendedByTO.setCountryTO(countryTO);
					}
					if(recommendor.getPhone()!= null){
						recommendedByTO.setPhone(recommendor.getPhone());
					}
					if(recommendor.getComments()!= null){
						recommendedByTO.setComments(recommendor.getComments());
					}
					if(createdBy!=null){
						recommendedByTO.setCreatedBy(createdBy);
					}
					if(recommendor.getCreatedDate()!=null){
						recommendedByTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(recommendor.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						recommendedByTO.setModifiedBy(modifiedBy);
					}
					if(recommendor.getLastModifiedDate()!=null){
						recommendedByTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(recommendor.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(recommendor.getIsActive() ){
						recommendedByTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
					}else{
						recommendedByTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
					}
					masterTO.setRecommendedByTO(recommendedByTO);
					candidateSearchTOList.add(masterTO);
					
				}				
			}
			//Prepare data for SubjectGroup
			if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.SUBJECT_GROUP)){
				while(itr.hasNext()){
					MasterReportTO masterTO = new MasterReportTO();	
					Object[] ojbit = (Object[]) itr.next();
					SubjectGroup subjectGroup = (SubjectGroup)ojbit[0];
					String createdBy = (String) ojbit[1];
					String modifiedBy = (String) ojbit[2];
					
					SubjectGroupTO subjectGroupTO = new SubjectGroupTO();
					
					if(subjectGroup.getName()!=null){
						subjectGroupTO.setName(subjectGroup.getName());
					}
					if(createdBy!=null){
						subjectGroupTO.setCreatedBy(createdBy);
					}
					if(subjectGroup.getCreatedDate()!=null){
						subjectGroupTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(subjectGroup.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(modifiedBy!=null){
						subjectGroupTO.setModifiedBy(modifiedBy);
					}
					if(subjectGroup.getLastModifiedDate()!=null){
						subjectGroupTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(subjectGroup.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
					}
					if(subjectGroup.getIsActive()!=null){
						if(subjectGroup.getIsActive()){
							subjectGroupTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							subjectGroupTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
					}
					if(subjectGroup.getCourse()!=null && subjectGroup.getCourse().getId()!=0){
						if(subjectGroup.getCourse().getName()!=null){
							CourseTO courseTO = new CourseTO();
							courseTO.setName(subjectGroup.getCourse().getName());
							subjectGroupTO.setCourseTO(courseTO);
						}
					}
					if(subjectGroup.getSubjectGroupSubjectses()!=null && !subjectGroup.getSubjectGroupSubjectses().isEmpty()){
						Set<SubjectGroupSubjects> subjectGoupSubjectSet = subjectGroup.getSubjectGroupSubjectses();
						List<SubjectGroupSubjectsTO>subjectGoupSubjectTOList = new ArrayList<SubjectGroupSubjectsTO>();
						Iterator<SubjectGroupSubjects> iterator = subjectGoupSubjectSet.iterator();
						while (iterator.hasNext()) {
							SubjectGroupSubjects subjectGroupSubjects = iterator.next();
							SubjectGroupSubjectsTO subjectGroupSubjectsTO = new SubjectGroupSubjectsTO();
							if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getId()!=0){
								SubjectTO subjectTO = new SubjectTO();
								subjectTO.setName(subjectGroupSubjects.getSubject().getName()+"("+subjectGroupSubjects.getSubject().getCode()+")");
								subjectGroupSubjectsTO.setSubjectTo(subjectTO);
							}
							subjectGoupSubjectTOList.add(subjectGroupSubjectsTO);
						}
						subjectGroupTO.setSubjectGroupSubjectsTOList(subjectGoupSubjectTOList);
					}				
					masterTO.setSubjectGroupTO(subjectGroupTO);
					candidateSearchTOList.add(masterTO);
				}
			}
			//Prepare data for DetailedSubjects
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.DETAILED_SUBJECTS)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						DetailedSubjects detailedSubjects = (DetailedSubjects)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();						
						if(detailedSubjects.getSubjectName()!=null){
							detailedSubjectsTO.setSubjectName(detailedSubjects.getSubjectName());
						}
						if(detailedSubjects.getCourse()!=null && detailedSubjects.getCourse().getId()!=0){
							CourseTO courseTO = new CourseTO();
							courseTO.setName(detailedSubjects.getCourse().getName());
							detailedSubjectsTO.setCourseTo(courseTO);
						}
						
						if(createdBy!=null){
							detailedSubjectsTO.setCreatedBy(createdBy);
						}
						if(detailedSubjects.getCreatedDate()!=null){
							detailedSubjectsTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(detailedSubjects.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							detailedSubjectsTO.setModifiedBy(modifiedBy);
						}
						if(detailedSubjects.getLastModifiedDate()!=null){
							detailedSubjectsTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(detailedSubjects.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(detailedSubjects.getIsActive()!=null){
							if(detailedSubjects.getIsActive()){
								detailedSubjectsTO.setActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								detailedSubjectsTO.setActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}						
						masterTO.setDetailedSubjectsTO(detailedSubjectsTO);
						candidateSearchTOList.add(masterTO);
					}
			}
				//Prepare data for EligibilityCriteria
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.ELIGIBILITY)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						EligibilityCriteria eligibilityCriteria = (EligibilityCriteria)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						EligibilityCriteriaTO eligibilityCriteriaTO = new EligibilityCriteriaTO();	
						int year=0;
						int tempYear = 0;
						if(eligibilityCriteria.getYear()!=null){
							year = eligibilityCriteria.getYear();
							tempYear = year + 1;
							eligibilityCriteriaTO.setEligibleYear("" +year + "-" + tempYear);
						}
						if(eligibilityCriteria.getCourse()!=null && eligibilityCriteria.getCourse().getId()!=0){
							CourseTO courseTO = new CourseTO();
							courseTO.setName(eligibilityCriteria.getCourse().getName());
							eligibilityCriteriaTO.setCourseTO(courseTO);
						}
						
						if(createdBy!=null){
							eligibilityCriteriaTO.setCreatedBy(createdBy);
						}
						if(eligibilityCriteria.getCreatedDate()!=null){
							eligibilityCriteriaTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(eligibilityCriteria.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							eligibilityCriteriaTO.setModifiedBy(modifiedBy);
						}
						if(eligibilityCriteria.getLastModifiedDate()!=null){
							eligibilityCriteriaTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(eligibilityCriteria.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(eligibilityCriteria.getIsActive()!=null){
							if(eligibilityCriteria.getIsActive()){
								eligibilityCriteriaTO.setActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								eligibilityCriteriaTO.setActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						if(eligibilityCriteria.getTotalPercentage()!=null){
							eligibilityCriteriaTO.setTotalPercentage(eligibilityCriteria.getTotalPercentage());
						}
						if(eligibilityCriteria.getPercentageWithoutLanguage()!=null){
							eligibilityCriteriaTO.setPercentageWithoutLanguage(eligibilityCriteria.getPercentageWithoutLanguage());
						}
						if(eligibilityCriteria.getEligibleSubjectses()!=null && !eligibilityCriteria.getEligibleSubjectses().isEmpty()){
							List<EligibleSubjectsTO> elligibleSubjectList = new ArrayList<EligibleSubjectsTO>();
							Iterator<EligibleSubjects> iterator = eligibilityCriteria.getEligibleSubjectses().iterator();
							while (iterator.hasNext()) {
								EligibleSubjects eligibleSubjects = iterator.next();
								EligibleSubjectsTO eligibleSubjectsTO = new EligibleSubjectsTO();
								if(eligibleSubjects.getDetailedSubjects()!=null && eligibleSubjects.getDetailedSubjects().getSubjectName()!=null){
									DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
									detailedSubjectsTO.setSubjectName(eligibleSubjects.getDetailedSubjects().getSubjectName());
									eligibleSubjectsTO.setDetailedSubjectsTO(detailedSubjectsTO);
								}
								elligibleSubjectList.add(eligibleSubjectsTO);
							}
							eligibilityCriteriaTO.setEligibleSubjectsTOList(elligibleSubjectList);
						}
						masterTO.setEligibilityCriteriaTO(eligibilityCriteriaTO);
						candidateSearchTOList.add(masterTO);
					}
			}
				//Prepare data for NewsEvents
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.NEWS_EVENTS)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						NewsEvents newsEvents = (NewsEvents)ojbit[0];
						String createdBy = (String) ojbit[1];
						
						NewsEventsTO newsEventsTO = new NewsEventsTO();						
						if(newsEvents.getDescription()!=null){
							newsEventsTO.setName(newsEvents.getDescription());
						}
					if(createdBy!=null){
						newsEventsTO.setCreatedBy(createdBy);
						}
						if(newsEvents.getCreatedDate()!=null){
							newsEventsTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(newsEvents.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						masterTO.setNewsEventsTO(newsEventsTO);
						candidateSearchTOList.add(masterTO);
					}
			}
				//Prepare data for ExtracurricularActivity
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.EXTRA_CURRICULAR_ACTIVITY)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						ExtracurricularActivity extracurricularActivity = (ExtracurricularActivity)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];					
						ExtracurricularActivityTO extracurricularActivityTO = new ExtracurricularActivityTO();						
						if(extracurricularActivity.getName()!=null){
							extracurricularActivityTO.setName(extracurricularActivity.getName());
						}
						if(extracurricularActivity.getOrganisation()!=null && extracurricularActivity.getOrganisation().getId()!=0){
							OrganizationTO organizationTO = new OrganizationTO();
							organizationTO.setOrganizationName(extracurricularActivity.getOrganisation().getName());
							extracurricularActivityTO.setOrganizationTO(organizationTO);
						}
						
						if(createdBy!=null){
							extracurricularActivityTO.setCreatedBy(createdBy);
						}
						if(extracurricularActivity.getCreatedDate()!=null){
							extracurricularActivityTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(extracurricularActivity.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							extracurricularActivityTO.setModifiedBy(modifiedBy);
						}
						if(extracurricularActivity.getLastModifiedDate()!=null){
							extracurricularActivityTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(extracurricularActivity.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(extracurricularActivity.getIsActive()!=null){
							if(extracurricularActivity.getIsActive()){
								extracurricularActivityTO.setTempActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								extracurricularActivityTO.setTempActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setExtracurricularActivityTO(extracurricularActivityTO);
						candidateSearchTOList.add(masterTO);
					}
			}
				//Prepare data for FeeBillNumber
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.FEE_BILL_NUMBER)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						FeeBillNumber feeBillNumber = (FeeBillNumber)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						FeeBillNumberTO feeBillNumberTO = new FeeBillNumberTO();						
						if(feeBillNumber.getBillNoFrom()!=null){
							feeBillNumberTO.setBillNo(feeBillNumber.getBillNoFrom());
						}
						if(feeBillNumber.getFeeFinancialYear()!=null && feeBillNumber.getFeeFinancialYear().getYear()!=null){
							feeBillNumberTO.setAcademicYear(feeBillNumber.getFeeFinancialYear().getYear());
						}
						
						if(createdBy!=null){
							feeBillNumberTO.setCreatedBy(createdBy);
						}
						if(feeBillNumber.getCreatedDate()!=null){
							feeBillNumberTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeBillNumber.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							feeBillNumberTO.setModifiedBy(modifiedBy);
						}
						if(feeBillNumber.getLastModifiedDate()!=null){
							feeBillNumberTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeBillNumber.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(feeBillNumber.getIsActive()!=null){
							if(feeBillNumber.getIsActive()){
								feeBillNumberTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								feeBillNumberTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setFeeBillNumberTO(feeBillNumberTO);
						candidateSearchTOList.add(masterTO);
					}
			}
				//Prepare data for FeeGroup
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.FEE_GROUP)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						FeeGroup feeGroup = (FeeGroup)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						FeeGroupTO feeGroupTO = new FeeGroupTO();						
						if(feeGroup.getName()!=null){
							feeGroupTO.setName(feeGroup.getName());
						}
						if(feeGroup.getIsOptional()!=null){
							if(feeGroup.getIsOptional()){
								feeGroupTO.setOptional("Yes");
							}
							else{
								feeGroupTO.setOptional("No");
							}
						}
						
						if(createdBy!=null){
							feeGroupTO.setCreatedBy(createdBy);
						}
						if(feeGroup.getCreatedDate()!=null){
							feeGroupTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeGroup.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							feeGroupTO.setModifiedBy(modifiedBy);
						}
						if(feeGroup.getLastModifiedDate()!=null){
							feeGroupTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeGroup.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(feeGroup.getIsActive()!=null){
							if(feeGroup.getIsActive()){
								feeGroupTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								feeGroupTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setFeeGroupTO(feeGroupTO);
						candidateSearchTOList.add(masterTO);
					}
			}
				//Prepare data for FeeHeading
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.FEE_HEADING)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						FeeHeading feeHeading = (FeeHeading)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						FeeHeadingTO feeHeadingTO = new FeeHeadingTO();						
						if(feeHeading.getName()!=null){
							feeHeadingTO.setName(feeHeading.getName());
						}
						if(feeHeading.getFeeGroup()!=null && feeHeading.getFeeGroup().getId()!=0){
							FeeGroupTO feeGroupTO = new FeeGroupTO();
							if(feeHeading.getFeeGroup().getName()!=null){
							feeGroupTO.setName(feeHeading.getFeeGroup().getName());
							}
							feeHeadingTO.setFeeGroupTO(feeGroupTO);
						}
						
						if(createdBy!=null){
							feeHeadingTO.setCreatedBy(createdBy);
						}
						if(feeHeading.getCreatedDate()!=null){
							feeHeadingTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeHeading.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							feeHeadingTO.setModifiedBy(modifiedBy);
						}
						if(feeHeading.getLastModifiedDate()!=null){
							feeHeadingTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeHeading.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(feeHeading.getIsActive()!=null){
							if(feeHeading.getIsActive()){
								feeHeadingTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								feeHeadingTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setFeeHeadingTO(feeHeadingTO);
						candidateSearchTOList.add(masterTO);
					}
			}
				//Prepare data for FeeAdditional
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.FEE_ADDITIONAL)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						FeeAdditional feeAdditional = (FeeAdditional)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						FeeAdditionalTO feeAdditionalTO = new FeeAdditionalTO();						
						if(feeAdditional.getFeeDivision()!=null){
							FeeDivisionTO feeDivisionTO = new FeeDivisionTO();
							if(feeAdditional.getFeeDivision().getName()!=null){
							feeDivisionTO.setName(feeAdditional.getFeeDivision().getName());
							}
							//raghu
							//feeAdditionalTO.setFeeDivisionTO(feeDivisionTO);
						}
						if(feeAdditional.getFeeGroup()!=null){
							FeeGroupTO feeGroupTO = new FeeGroupTO();
							if(feeAdditional.getFeeGroup().getName()!=null){
								feeGroupTO.setName(feeAdditional.getFeeGroup().getName());
							}
							feeAdditionalTO.setFeeGroupTO(feeGroupTO);
						}
						
						if(createdBy!=null){
							feeAdditionalTO.setCreatedBy(createdBy);
						}
						if(feeAdditional.getCreatedDate()!=null){
							feeAdditionalTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeAdditional.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							feeAdditionalTO.setModifiedBy(modifiedBy);
						}
						if(feeAdditional.getLastModifiedDate()!=null){
							feeAdditionalTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeAdditional.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(feeAdditional.getIsActive()!=null){
							if(feeAdditional.getIsActive()){
								feeAdditionalTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								feeAdditionalTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setFeeAdditionalTO(feeAdditionalTO);
						candidateSearchTOList.add(masterTO);
					}	
			}
				//Prepare data for FeeAccount
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.FEE_ACCOUNT)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						FeeAccount feeAccount = (FeeAccount)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						FeeAccountTO feeAccountTO = new FeeAccountTO();						
						if(feeAccount.getFeeDivision()!=null && feeAccount.getFeeDivision().getId()!=0){
							FeeDivisionTO feeDivisionTO = new FeeDivisionTO();
							if(feeAccount.getFeeDivision().getName()!=null){
							feeDivisionTO.setName(feeAccount.getFeeDivision().getName());
							}
							feeAccountTO.setFeeDivisionTO(feeDivisionTO);
						}
						if(feeAccount.getName()!=null){
							feeAccountTO.setName(feeAccount.getName());
						}
						if(feeAccount.getCode()!=null){
							feeAccountTO.setCode(feeAccount.getCode());
						}
						if(createdBy!=null){
							feeAccountTO.setCreatedBy(createdBy);
						}
						if(feeAccount.getCreatedDate()!=null){
							feeAccountTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeAccount.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							feeAccountTO.setModifiedBy(modifiedBy);
						}
						if(feeAccount.getLastModifiedDate()!=null){
							feeAccountTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feeAccount.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(feeAccount.getIsActive()!=null){
							if(feeAccount.getIsActive()){
								feeAccountTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								feeAccountTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setFeeAccountTO(feeAccountTO);
						candidateSearchTOList.add(masterTO);
					}	
			}
				//Prepare data for Period
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.PERIOD)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();	
						Object[] ojbit = (Object[]) itr.next();
						Period period = (Period)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						PeriodTO periodTO = new PeriodTO();
						ClassesTO classesTO = new ClassesTO(); 
						
						ClassSchemewiseTO classSchemewiseTO = new ClassSchemewiseTO();
						CurriculumSchemeDurationTO curriculumSchemeDurationTO = new CurriculumSchemeDurationTO();

						if(period.getPeriodName()!= null){
							periodTO.setPeriodName(period.getPeriodName());
						}
						if(period.getStartTime()!= null){
							periodTO.setStartTime(period.getStartTime().substring(0, 5));
						}
						if(period.getEndTime()!= null){
							periodTO.setEndTime(period.getEndTime().substring(0, 5));
						}
						if(period.getClassSchemewise()!= null){
							classSchemewiseTO.setId(period.getClassSchemewise().getId());
						}
						if(period.getClassSchemewise()!= null && period.getClassSchemewise().getClasses()!= null){
							classesTO.setClassName(period.getClassSchemewise().getClasses().getName());
						}
						if(period.getClassSchemewise()!= null && period.getClassSchemewise().getCurriculumSchemeDuration()!= null && period.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()!= null){
							curriculumSchemeDurationTO.setTempYear( Integer.toString(period.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()) + "-" + Integer.toString((period.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() + 1)) );
						}
						classSchemewiseTO.setClassesTo(classesTO);
						classSchemewiseTO.setCurriculumSchemeDurationTO(curriculumSchemeDurationTO);
						periodTO.setClassSchemewiseTO(classSchemewiseTO);
						
						if(createdBy!=null){
							periodTO.setCreatedBy(createdBy);
						}
						if(period.getCreatedDate()!=null){
							periodTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(period.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							periodTO.setModifiedBy(modifiedBy);
						}
						if(period.getLastModifiedDate()!=null){
							periodTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(period.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(period.getIsActive() ){
							periodTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							periodTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
						masterTO.setPeriodTO(periodTO);
						candidateSearchTOList.add(masterTO);						
					}				
				}
				//Prepare data for InterviewSubRounds
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.INTERVIEW_SUB_ROUNDS)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						InterviewSubRounds interviewSubRounds = (InterviewSubRounds)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						InterviewSubroundsTO interviewSubroundsTO = new InterviewSubroundsTO();						
						if(interviewSubRounds.getInterviewProgramCourse()!=null && interviewSubRounds.getInterviewProgramCourse().getId()!=0){
							InterviewProgramCourseTO interviewProgramCourseTO = new InterviewProgramCourseTO();
							if(interviewSubRounds.getInterviewProgramCourse().getName()!=null){
								interviewProgramCourseTO.setName(interviewSubRounds.getInterviewProgramCourse().getName());
							}
							int academicYear = 0;
							int tempYear = 0;
							if(interviewSubRounds.getInterviewProgramCourse().getYear()!=null){
								academicYear = interviewSubRounds.getInterviewProgramCourse().getYear();
								tempYear = academicYear + 1;
								interviewProgramCourseTO.setAcademicYear("" + academicYear + "-" + tempYear);
							}
							if(interviewSubRounds.getInterviewProgramCourse().getCourse()!=null	&& interviewSubRounds.getInterviewProgramCourse().getCourse().getId()!=0 
							&& interviewSubRounds.getInterviewProgramCourse().getCourse().getName()!=null){		
								CourseTO courseTO = new CourseTO();
								courseTO.setName(interviewSubRounds.getInterviewProgramCourse().getCourse().getName());
								interviewProgramCourseTO.setCourse(courseTO);
							}
							interviewSubroundsTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
						}
						if(interviewSubRounds.getName()!=null){
							interviewSubroundsTO.setName(interviewSubRounds.getName());
						}
						
						if(createdBy!=null){
							interviewSubroundsTO.setCreatedBy(createdBy);
						}
						if(interviewSubRounds.getCreatedDate()!=null){
							interviewSubroundsTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewSubRounds.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							interviewSubroundsTO.setModifiedBy(modifiedBy);
						}
						if(interviewSubRounds.getLastModifiedDate()!=null){
							interviewSubroundsTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewSubRounds.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(interviewSubRounds.getIsActive()!=null){
							if(interviewSubRounds.getIsActive()){
								interviewSubroundsTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								interviewSubroundsTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setInterviewSubroundsTO(interviewSubroundsTO);
						candidateSearchTOList.add(masterTO);
					}	
			}
				//Prepare data for InterviewProgramCourse
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.INTERVIEW_PROGRAM_COURSE)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();
						ProgramTypeTO programTypeTO = new ProgramTypeTO();
						ProgramTO programTO = new ProgramTO();
						CourseTO courseTO = new CourseTO();
						InterviewProgramCourseTO interviewProgramCourseTO = new InterviewProgramCourseTO();
						Object[] ojbit = (Object[]) itr.next();
						InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];						
						if(interviewProgramCourse.getProgram().getProgramType()!= null && interviewProgramCourse.getProgram().getProgramType().getName()!= null){
							programTypeTO.setProgramTypeName(interviewProgramCourse.getProgram().getProgramType().getName());
							programTO.setProgramTypeTo(programTypeTO);
						}
						if(interviewProgramCourse.getProgram()!= null && interviewProgramCourse.getProgram().getName()!= null){
							programTO.setName(interviewProgramCourse.getProgram().getName());
						}
						interviewProgramCourseTO.setProgram(programTO);
						if(interviewProgramCourse.getCourse().getName()!= null){
							courseTO.setName(interviewProgramCourse.getCourse().getName());
							interviewProgramCourseTO.setCourse(courseTO);
						}
						if(interviewProgramCourse.getYear()!= null){
							interviewProgramCourseTO.setYear(interviewProgramCourse.getYear());
							interviewProgramCourseTO.setAcademicYear(interviewProgramCourse.getYear().toString() + "-" + (Integer.toString(interviewProgramCourse.getYear()+1)));							
						}
						if(interviewProgramCourse.getName()!= null){
							interviewProgramCourseTO.setName(interviewProgramCourse.getName());
						}
						if(interviewProgramCourse.getSequence()!= null){
							interviewProgramCourseTO.setSequence(interviewProgramCourse.getSequence());
						}
						
						if(createdBy!=null){
							interviewProgramCourseTO.setCreatedBy(createdBy);
						}
						if(interviewProgramCourse.getCreatedDate()!=null){
							interviewProgramCourseTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewProgramCourse.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							interviewProgramCourseTO.setModifiedBy(modifiedBy);
						}
						if(interviewProgramCourse.getLastModifiedDate()!=null){
							interviewProgramCourseTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewProgramCourse.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(interviewProgramCourse.getIsActive() ){
							interviewProgramCourseTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							interviewProgramCourseTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}
						masterTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
						candidateSearchTOList.add(masterTO);						
					}				
				}
				//Prepare data for ApplicationNumber
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.APPLICATION_NUMBER)){
					
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();		
						Object[] ojbit = (Object[]) itr.next();
						ApplicationNumber applicationNumber = (ApplicationNumber)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						ApplicationNumberTO applicationNumberTO = new ApplicationNumberTO();						
						int academicYear = 0;
						int tempYear = 0;
						if(applicationNumber.getYear()!=null){
							academicYear = applicationNumber.getYear();
							tempYear = academicYear + 1;
							applicationNumberTO.setAcademicYear("" + academicYear +"-"+ tempYear);
						}
						
						String onlineApplicationNo = "";
						String offlineApplicationNo = "";
						String online = "";
						String offline = "";
						if(applicationNumber.getOnlineApplnNoFrom()!=null){
							onlineApplicationNo = applicationNumber.getOnlineApplnNoFrom();
						}
						if(applicationNumber.getOnlineApplnNoTo()!=null){
							online = onlineApplicationNo + "-" + applicationNumber.getOnlineApplnNoTo();
						}
						applicationNumberTO.setOnlineAppNoFrom(online);
						
						if(applicationNumber.getOfflineApplnNoFrom()!=null){
							offlineApplicationNo = applicationNumber.getOfflineApplnNoFrom();
						}
						if(applicationNumber.getOfflineApplnNoTo()!=null){
							offline = offlineApplicationNo + "-" +applicationNumber.getOfflineApplnNoTo();
						}
						applicationNumberTO.setOfflineAppNoFrom(offline);
						
						if(applicationNumber.getIsActive()!=null){
							if(applicationNumber.getIsActive()){
								applicationNumberTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								applicationNumberTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						if(createdBy!= null){
							applicationNumberTO.setCreatedBy(createdBy);
						}
						if(modifiedBy!=  null){
							applicationNumberTO.setModifiedBy(modifiedBy);
						}
						if(applicationNumber.getCreatedDate()!= null){
							applicationNumberTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(applicationNumber.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(applicationNumber.getLastModifiedDate()!= null){
							applicationNumberTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(applicationNumber.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						
						if(applicationNumber.getCourseApplicationNumbers()!=null && !applicationNumber.getCourseApplicationNumbers().isEmpty()){
							 List<CourseApplicationNoTO> courseApplicationNoTOList = new ArrayList<CourseApplicationNoTO>();
							Iterator<CourseApplicationNumber> iterator = applicationNumber.getCourseApplicationNumbers().iterator();
							while (iterator.hasNext()) {
								CourseApplicationNumber courseApplicationNumber = iterator.next();
								CourseApplicationNoTO courseApplicationNoTO = new CourseApplicationNoTO();
								if(courseApplicationNumber.getCourse()!=null && courseApplicationNumber.getCourse().getName()!=null){
									CourseTO courseTO = new CourseTO();
									courseTO.setName(courseApplicationNumber.getCourse().getName());
									courseApplicationNoTO.setCourseTO(courseTO);
								}
								courseApplicationNoTOList.add(courseApplicationNoTO);
							}
							applicationNumberTO.setCourseApplicationNoTO(courseApplicationNoTOList);
						}
						masterTO.setApplicationNumberTO(applicationNumberTO);
						candidateSearchTOList.add(masterTO);
					}	
			}
				//Prepare data for Activity
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.ACTIVITY)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();	
						ActivityTO activityTO = new ActivityTO();
						AttendanceTypeTO attendanceTypeTO = new AttendanceTypeTO();

						Object[] ojbit = (Object[]) itr.next();
						Activity activity = (Activity)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						if(activity.getName()!=null){
							activityTO.setName(activity.getName());
						}
						if(activity.getAttendanceType()!= null && activity.getAttendanceType().getName()!= null){
							attendanceTypeTO.setAttendanceTypeName(activity.getAttendanceType().getName());
							activityTO.setAttendanceTypeTO(attendanceTypeTO);
						}
						if(createdBy!= null){
							activityTO.setCreatedBy(createdBy);
						}
						
						if(activity.getCreatedDate()!=null){
							activityTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(activity.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							activityTO.setModifiedBy(modifiedBy);
						}
						if(activity.getLastModifiedDate()!=null){
							activityTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(activity.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(activity.getIsActive()!=null){
							if(activity.getIsActive() ){
								activityTO.setActivityIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								activityTO.setActivityIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setActivityTO(activityTO);
						candidateSearchTOList.add(masterTO);						
					}				
				}
				//Prepares Data for AttendanceType
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.ATTENDANCE_TYPE)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();	
						Object[] ojbit = (Object[]) itr.next();
						AttendanceType attendanceType = (AttendanceType)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						AttendanceTypeTO attendanceTypeTO = new AttendanceTypeTO();
						
						if(attendanceType.getName()!=null){
							attendanceTypeTO.setAttendanceTypeName(attendanceType.getName());
						}
						if(attendanceType.getIsDefault()!=null){
							if(attendanceType.getIsDefault() ){
								attendanceTypeTO.setDefaultValue(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								attendanceTypeTO.setDefaultValue(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						if(createdBy!=null){
							attendanceTypeTO.setCreatedBy(createdBy);
						}
						
						if(attendanceType.getCreatedDate()!=null){
							attendanceTypeTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(attendanceType.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modifiedBy!=null){
							attendanceTypeTO.setModifiedBy(modifiedBy);
						}
						if(attendanceType.getLastModifiedDate()!=null){
							attendanceTypeTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(attendanceType.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(attendanceType.getIsActive()!=null){
							if(attendanceType.getIsActive() ){
								attendanceTypeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								attendanceTypeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setAttendanceTypeTO(attendanceTypeTO);
						candidateSearchTOList.add(masterTO);
						
					}				
				}
				//prepare data for Modules
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.MODULES)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();	
						Object[] ojbit = (Object[]) itr.next();
						Modules modules = (Modules)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];						
						ModuleTO moduleTO = new ModuleTO();
						if(modules.getDisplayName()!= null){
							moduleTO.setName(modules.getDisplayName());
						}
						if(modules.getPosition()!= null){
							moduleTO.setPosition(modules.getPosition());
						}
						if(createdBy!= null){
							moduleTO.setCreatedBy(createdBy);
						}
						if(modifiedBy!= null){
							moduleTO.setModifiedBy(modifiedBy);
						}
						if(modules.getCreatedDate()!= null){
							moduleTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(modules.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(modules.getLastModifiedDate()!= null){
							moduleTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(modules.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}						
						if(modules.getIsActive()!=null){
							if(modules.getIsActive() ){
								moduleTO.setTempIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								moduleTO.setTempIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setModuleTO(moduleTO);
						candidateSearchTOList.add(masterTO);					
					}				
				}
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.REMARK_TYPE)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();	
						Object[] ojbit = (Object[]) itr.next();
						RemarkType remarkType = (RemarkType)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];						
						RemarkTypeTO remarkTypeTO = new RemarkTypeTO();
						if(remarkType.getRemarkType()!= null){
							remarkTypeTO.setRemarkType(remarkType.getRemarkType());
						}
						remarkTypeTO.setMaxOccurrences(Integer.toString(remarkType.getMaxOccurrences()));
						
						if(createdBy!= null){
							remarkTypeTO.setCreatedBy(createdBy);
						}
						if(modifiedBy!= null){
							remarkTypeTO.setModifiedBy(modifiedBy);
						}
						if(remarkType.getCreatedDate()!= null){
							remarkTypeTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(remarkType.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(remarkType.getLastModifiedDate()!= null){
							remarkTypeTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(remarkType.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}						
						if(remarkType.getIsActive()!=null){
							if(remarkType.getIsActive() ){
								remarkTypeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								remarkTypeTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setRemarkTypeTO(remarkTypeTO);
						candidateSearchTOList.add(masterTO);					
					}				
				}
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.MENUS)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();	
						Object[] ojbit = (Object[]) itr.next();
						Menus menus = (Menus)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];						
						MenusTO menusTO = new MenusTO();
						ModuleTO moduleTO = new ModuleTO();
						if(menus.getDisplayName()!= null){
							menusTO.setName(menus.getDisplayName());
						}
						menusTO.setPosition(menus.getPosition());
						moduleTO.setName(menus.getModules().getDisplayName());
						menusTO.setModuleTO(moduleTO);
						if(menus.getUrl()!= null){
							menusTO.setUrl(menus.getUrl());
						}
						if(createdBy!= null){
							menusTO.setCreatedBy(createdBy);
						}
						if(modifiedBy!= null){
							menusTO.setModifiedBy(modifiedBy);
						}
						if(menus.getCreatedDate()!= null){
							menusTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(menus.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}
						if(menus.getLastModifiedDate()!= null){
							menusTO.setLastModifiedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(menus.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						}						
						if(menus.getIsActive()!=null){
							if(menus.getIsActive() ){
								menusTO.setTempIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
							}else{
								menusTO.setTempIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
							}
						}
						masterTO.setMenusTO(menusTO);
						candidateSearchTOList.add(masterTO);					
					}				
				}
				if(masterReportForm.getMasterTable().equalsIgnoreCase(CMSConstants.NATIONALITY)){
					while(itr.hasNext()){
						MasterReportTO masterTO = new MasterReportTO();	

						Object[] ojbit = (Object[]) itr.next();
						Nationality nationality = (Nationality)ojbit[0];
						String createdBy = (String) ojbit[1];
						String modifiedBy = (String) ojbit[2];
						
						masterTO.setName(nationality.getName());
						if(masterReportForm.getMasterTable() != null){
							masterTO.setFieldName(masterReportForm.getMasterTable()+"&nbsp;Name");
						}
						if(createdBy!= null){
							masterTO.setCreatedBy(createdBy);
						}
						masterTO.setCDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(nationality.getCreatedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						if(createdBy!= null){
							masterTO.setModifiedBy(modifiedBy);
						}
						masterTO.setLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(nationality.getLastModifiedDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
						if(nationality.getIsActive() ){
							masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_TRUE);
						}else{
							masterTO.setIsActive(CMSConstants.KNOWLEDGEPRO_FALSE);
						}					
						candidateSearchTOList.add(masterTO);					
					}				
				}				
		}
		return candidateSearchTOList;
	}
}
