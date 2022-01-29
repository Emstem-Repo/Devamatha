package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.bo.admin.SyllabusEntryHeadingDesc;
import com.kp.cms.bo.admin.SyllabusEntryProgramDetails;
import com.kp.cms.bo.admin.SyllabusEntryUnitsHours;
import com.kp.cms.forms.admin.SyllabusEntryHodApprovalForm;
import com.kp.cms.to.admin.SyllabusEntryHeadingDescTo;
import com.kp.cms.to.admin.SyllabusEntryPreviewTo;
import com.kp.cms.to.admin.SyllabusEntryProgramDetailsTo;
import com.kp.cms.to.admin.SyllabusEntryUnitsHoursTo;
import com.kp.cms.transactions.admin.ISyllabusEntryTrans;
import com.kp.cms.transactionsimpl.admin.SyllabusEntryTransImpl;

public class SyllabusEntryHodHelper {
	ISyllabusEntryTrans transaction=SyllabusEntryTransImpl.getInstance();
	public static volatile SyllabusEntryHodHelper syllabusEntryHelper=null;
	//private constructor
	private SyllabusEntryHodHelper(){
		
	}
	//singleton object
	public static SyllabusEntryHodHelper getInstance(){
		if(syllabusEntryHelper==null){
			syllabusEntryHelper=new SyllabusEntryHodHelper();
			return syllabusEntryHelper;
		}
		return syllabusEntryHelper;
	}
	public void setToFormWhichIsInProgress(SyllabusEntry syllabusEntry,
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		syllabusEntryForHodApprovalForm.setId(syllabusEntry.getId());
		syllabusEntryForHodApprovalForm.setBatchYear(String.valueOf(syllabusEntry.getBatchYear()));
		syllabusEntryForHodApprovalForm.setSubjectCode(syllabusEntry.getSubject().getCode());
		syllabusEntryForHodApprovalForm.setSubjectName(syllabusEntry.getSubject().getName());
		syllabusEntryForHodApprovalForm.setParentDepartment(syllabusEntry.getSubject().getDepartment().getName());
		if(syllabusEntry.getSubject().getIsSecondLanguage() && syllabusEntry.getSubject().getIsSecondLanguage()!=null){
			syllabusEntryForHodApprovalForm.setSecondLanguage("Yes");
		}else{
			syllabusEntryForHodApprovalForm.setSecondLanguage("No");
		}
		if(syllabusEntry.getSubject().getIsTheoryPractical()!=null){
			if(syllabusEntry.getSubject().getIsTheoryPractical().equalsIgnoreCase("T")){
				syllabusEntryForHodApprovalForm.setTheoryOrPractical("Theory");
			}else if(syllabusEntry.getSubject().getIsTheoryPractical().equalsIgnoreCase("P")){
				syllabusEntryForHodApprovalForm.setTheoryOrPractical("Practical");
			}else{
				syllabusEntryForHodApprovalForm.setTheoryOrPractical("Theory and Practical");
			}
		}
		if(syllabusEntry.getSubject().getQuestionbyrequired()!=null && syllabusEntry.getSubject().getQuestionbyrequired()){
			syllabusEntryForHodApprovalForm.setQuestionBankRequired("Yes");
		}else{
			syllabusEntryForHodApprovalForm.setQuestionBankRequired("No");
		}
		if(syllabusEntry.getTotTeachingHrsPerSem()!=null){
			syllabusEntryForHodApprovalForm.setTotTeachHrsPerSem(String.valueOf(syllabusEntry.getTotTeachingHrsPerSem()));
		}
		if(syllabusEntry.getNoOfLectureHrsPerWeek()!=null){
			syllabusEntryForHodApprovalForm.setNoOfLectureHrsPerWeek(String.valueOf(syllabusEntry.getNoOfLectureHrsPerWeek()));
		}
		if(syllabusEntry.getCredits()!=null){
			syllabusEntryForHodApprovalForm.setCredits(syllabusEntry.getCredits());
		}
		if(syllabusEntry.getMaxMarks()!=null){
			syllabusEntryForHodApprovalForm.setMaxMarks(String.valueOf(syllabusEntry.getMaxMarks()));
		}
		if(syllabusEntry.getCourseObjective()!=null){
			syllabusEntryForHodApprovalForm.setCourseObjective(syllabusEntry.getCourseObjective());
		}
		if(syllabusEntry.getLearningOutcome()!=null){
			syllabusEntryForHodApprovalForm.setLerningOutcome(syllabusEntry.getLearningOutcome());
		}
		if(syllabusEntry.getTextBooksAndRefBooks()!=null){
			syllabusEntryForHodApprovalForm.setTextBooksAndRefBooks(syllabusEntry.getTextBooksAndRefBooks());
		}
		if(syllabusEntry.getFreeText()!=null){
			syllabusEntryForHodApprovalForm.setFreeText(syllabusEntry.getFreeText());
		}
		if(syllabusEntry.getRecommendedReading()!=null){
			syllabusEntryForHodApprovalForm.setRecommendedReading(syllabusEntry.getRecommendedReading());
		}
		if(syllabusEntry.getChangeInSyllabus()!=null && syllabusEntry.getChangeInSyllabus().equalsIgnoreCase("Y")){
			syllabusEntryForHodApprovalForm.setChangeInSyllabus("yes");
			syllabusEntryForHodApprovalForm.setTempChangeInSyllabus("yes");
			
		}else{
			syllabusEntryForHodApprovalForm.setChangeInSyllabus("no");
			syllabusEntryForHodApprovalForm.setTempChangeInSyllabus("no");
		}
		if(syllabusEntry.getBriefDetailsExistingSyllabus()!=null){
			syllabusEntryForHodApprovalForm.setBriefDetailsExistingSyllabus(syllabusEntry.getBriefDetailsExistingSyllabus());
		}
		if(syllabusEntry.getBriefDetalsAboutChange()!=null){
			syllabusEntryForHodApprovalForm.setBriefDetalsAboutChange(syllabusEntry.getBriefDetalsAboutChange());
		}
		if(syllabusEntry.getChangeInSyllabus()!=null){
			syllabusEntryForHodApprovalForm.setChangeReason(syllabusEntry.getChangeReason());
		}
		if(syllabusEntry.getRemarks()!=null){
			syllabusEntryForHodApprovalForm.setRemarks(syllabusEntry.getRemarks());
		}
		if((syllabusEntry.getHodReject()!=null && syllabusEntry.getHodReject())
				|| (syllabusEntry.getFinalReject()!=null && syllabusEntry.getFinalReject())){
			syllabusEntryForHodApprovalForm.setRejectReason(syllabusEntry.getRejectReason());
		}
		//units
		if(syllabusEntry.getSyllabusEntryUnitsHours()!=null && !syllabusEntry.getSyllabusEntryUnitsHours().isEmpty()){
			Set<SyllabusEntryUnitsHours> unitsSet=syllabusEntry.getSyllabusEntryUnitsHours();
			List<SyllabusEntryUnitsHoursTo> unitsList=new ArrayList<SyllabusEntryUnitsHoursTo>();
			SyllabusEntryUnitsHoursTo syllEntryUnitsHoursTo=null;
			for (SyllabusEntryUnitsHours syllabusEntryUnitsHours : unitsSet) {
				syllEntryUnitsHoursTo=new SyllabusEntryUnitsHoursTo();
				if(syllabusEntryUnitsHours.getIsActive()!=null && syllabusEntryUnitsHours.getIsActive()){
					syllEntryUnitsHoursTo.setId(syllabusEntryUnitsHours.getId());
					syllEntryUnitsHoursTo.setTeachingHoursTemplate("Teaching Hours");
					syllEntryUnitsHoursTo.setPosition(unitsList.size()+1);
					if(syllabusEntryUnitsHours.getUnitNo()!=null){
						syllEntryUnitsHoursTo.setUnitNo(syllabusEntryUnitsHours.getUnitNo());
					}
					if(syllabusEntryUnitsHours.getTeachingHours()!=null){
						syllEntryUnitsHoursTo.setTeachingHours(String.valueOf(syllabusEntryUnitsHours.getTeachingHours()));
					}
					if(syllabusEntryUnitsHours.getUnits()!=null){
						syllEntryUnitsHoursTo.setUnits(syllabusEntryUnitsHours.getUnits());
					}
					//headings
					if(syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs()!=null && !syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs().isEmpty()){
						Set<SyllabusEntryHeadingDesc> headingsSet=syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs();
						List<SyllabusEntryHeadingDescTo> headingsList=new ArrayList<SyllabusEntryHeadingDescTo>();
						SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo=null;
						for (SyllabusEntryHeadingDesc syllabusEntryHeadingDesc : headingsSet) {
							syllabusEntryHeadingDescTo=new SyllabusEntryHeadingDescTo();
							syllabusEntryHeadingDescTo.setId(syllabusEntryHeadingDesc.getId());
							if(syllabusEntryHeadingDesc.getHeading()!=null && !syllabusEntryHeadingDesc.getHeading().isEmpty()){
								syllabusEntryHeadingDescTo.setTempHead(syllabusEntryHeadingDesc.getHeading());
							}else {
								syllabusEntryHeadingDescTo.setHeadingTemplate("Heading");
							}
							syllabusEntryHeadingDescTo.setDescriptionTemplate("Description");
							if(syllabusEntryHeadingDesc.getHeadingNo()!=null){
								syllabusEntryHeadingDescTo.setHeadingNO(syllabusEntryHeadingDesc.getHeadingNo());
							}
							if(syllabusEntryHeadingDesc.getDescription()!=null){
								syllabusEntryHeadingDescTo.setDescription(syllabusEntryHeadingDesc.getDescription());
							}
							if(syllabusEntryHeadingDesc.getHeading()!=null){
								syllabusEntryHeadingDescTo.setHeading(syllabusEntryHeadingDesc.getHeading());
							}
							headingsList.add(syllabusEntryHeadingDescTo);
						}
						Collections.sort(headingsList);
						syllEntryUnitsHoursTo.setSyllabusEntryHeadingDescTos(headingsList);
					}
					//end headings
					if(syllEntryUnitsHoursTo.getSyllabusEntryHeadingDescTos().size()>1){
						syllEntryUnitsHoursTo.setHeadingsFlag(true);
						
					}
					unitsList.add(syllEntryUnitsHoursTo);
				}
			}
			Collections.sort(unitsList);
			//end units
			syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(unitsList);
		}
		if(syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos().size()>1){
			syllabusEntryForHodApprovalForm.setUnitsFocus("unit_"+(syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos().size()-1));
			syllabusEntryForHodApprovalForm.setUnitsFlag(true);
		}
	}
	/**
	 * 
	 * @param syllabusEntryForHodApprovalForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public SyllabusEntry convertFormToAdminSaveDraftBo(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm,
			HttpServletRequest request) throws Exception{
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForHodApprovalForm.getBatchYear()),Integer.parseInt(syllabusEntryForHodApprovalForm.getSubjectId()));
		if(syllabusEntry==null){
			syllabusEntry=new SyllabusEntry();
		}
	//start syllabus entry table
		if(syllabusEntryForHodApprovalForm.getMaxMarks()!=null && !syllabusEntryForHodApprovalForm.getMaxMarks().isEmpty()){
			syllabusEntry.setMaxMarks(Integer.parseInt(syllabusEntryForHodApprovalForm.getMaxMarks()));
		}
		if(syllabusEntryForHodApprovalForm.getTotTeachHrsPerSem()!=null && !syllabusEntryForHodApprovalForm.getTotTeachHrsPerSem().isEmpty()){
			syllabusEntry.setTotTeachingHrsPerSem(Integer.parseInt(syllabusEntryForHodApprovalForm.getTotTeachHrsPerSem()));
		}
		if(syllabusEntryForHodApprovalForm.getNoOfLectureHrsPerWeek()!=null && !syllabusEntryForHodApprovalForm.getNoOfLectureHrsPerWeek().isEmpty()){
			syllabusEntry.setNoOfLectureHrsPerWeek(Integer.parseInt(syllabusEntryForHodApprovalForm.getNoOfLectureHrsPerWeek()));
		}
		if(syllabusEntryForHodApprovalForm.getCredits().trim()!=null && !syllabusEntryForHodApprovalForm.getCredits().trim().isEmpty()){
			syllabusEntry.setCredits(syllabusEntryForHodApprovalForm.getCredits());
		}	
		if(syllabusEntryForHodApprovalForm.getCourseObjective().trim()!=null && !syllabusEntryForHodApprovalForm.getCourseObjective().trim().isEmpty()){
			syllabusEntry.setCourseObjective(syllabusEntryForHodApprovalForm.getCourseObjective());
		}	
		if(syllabusEntryForHodApprovalForm.getLerningOutcome()!=null && !syllabusEntryForHodApprovalForm.getLerningOutcome().trim().isEmpty()){
			syllabusEntry.setLearningOutcome(syllabusEntryForHodApprovalForm.getLerningOutcome());
		}	
		if(syllabusEntryForHodApprovalForm.getTextBooksAndRefBooks().trim()!=null && !syllabusEntryForHodApprovalForm.getTextBooksAndRefBooks().trim().isEmpty()){
			syllabusEntry.setTextBooksAndRefBooks(syllabusEntryForHodApprovalForm.getTextBooksAndRefBooks());
		}	
		if(syllabusEntryForHodApprovalForm.getFreeText().trim()!=null && !syllabusEntryForHodApprovalForm.getFreeText().trim().isEmpty()){
			syllabusEntry.setFreeText(syllabusEntryForHodApprovalForm.getFreeText());
		}	
		if(syllabusEntryForHodApprovalForm.getBatchYear()!=null){
			syllabusEntry.setBatchYear(Integer.parseInt(syllabusEntryForHodApprovalForm.getBatchYear()));
		}
		if(syllabusEntryForHodApprovalForm.getRecommendedReading().trim()!=null && !syllabusEntryForHodApprovalForm.getRecommendedReading().trim().isEmpty()){
			syllabusEntry.setRecommendedReading(syllabusEntryForHodApprovalForm.getRecommendedReading().trim());
		}
		if(syllabusEntryForHodApprovalForm.getChangeInSyllabus()!=null && !syllabusEntryForHodApprovalForm.getChangeInSyllabus().isEmpty()){
			if(syllabusEntryForHodApprovalForm.getChangeInSyllabus().equalsIgnoreCase("yes")){
				syllabusEntry.setChangeInSyllabus("Y");
			}else {
				syllabusEntry.setChangeInSyllabus("N");
			}
		}
		if(syllabusEntryForHodApprovalForm.getBriefDetailsExistingSyllabus()!=null && !syllabusEntryForHodApprovalForm.getBriefDetailsExistingSyllabus().isEmpty()){
			syllabusEntry.setBriefDetailsExistingSyllabus(syllabusEntryForHodApprovalForm.getBriefDetailsExistingSyllabus().trim());
		}
		if(syllabusEntryForHodApprovalForm.getBriefDetalsAboutChange()!=null && !syllabusEntryForHodApprovalForm.getBriefDetalsAboutChange().isEmpty()){
			syllabusEntry.setBriefDetalsAboutChange(syllabusEntryForHodApprovalForm.getBriefDetalsAboutChange().trim());
		}
		if(syllabusEntryForHodApprovalForm.getChangeReason()!=null && !syllabusEntryForHodApprovalForm.getChangeReason().isEmpty()){
			syllabusEntry.setChangeReason(syllabusEntryForHodApprovalForm.getChangeReason().trim());
		}
		if(syllabusEntryForHodApprovalForm.getRemarks()!=null && !syllabusEntryForHodApprovalForm.getRemarks().isEmpty()){
			syllabusEntry.setRemarks(syllabusEntryForHodApprovalForm.getRemarks().trim());
		}
		/*syllabusEntry.setSendForApproval(false);
		syllabusEntry.setApproved(false);
		syllabusEntry.setHodReject(false);
		syllabusEntry.setFinalReject(false);
		syllabusEntry.setFinalApproval(false);*/
		if(syllabusEntryForHodApprovalForm.getSubjectId()!=null){
			Subject subject=new Subject();
			subject.setId(Integer.parseInt(syllabusEntryForHodApprovalForm.getSubjectId()));
			syllabusEntry.setSubject(subject);
		}
		if(syllabusEntry.getId()<=0){
			syllabusEntry.setCreatedBy(syllabusEntryForHodApprovalForm.getUserId());
			syllabusEntry.setCreatedDate(new Date());
		}
		syllabusEntry.setModifiedBy(syllabusEntryForHodApprovalForm.getUserId());
		syllabusEntry.setLastModifiedDate(new Date());
		syllabusEntry.setIsActive(true);
		//start syllabus entry units
		List<Integer> unitsWhichAlreadyExits=new ArrayList<Integer>();
		List<SyllabusEntryUnitsHoursTo> unitsList1=syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos();
		//get units which are already exits to know that that unit have id if already exits
		for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : unitsList1) {
			if(syllabusEntryUnitsHoursTo.getId()>0){
				unitsWhichAlreadyExits.add(syllabusEntryUnitsHoursTo.getId());
			}
		}
		//make isActive false remaining units not in units which is in form
		Set<SyllabusEntryUnitsHours> isActiveFalseUnits=makeIsActiveFalse(syllabusEntry.getSyllabusEntryUnitsHours(),unitsWhichAlreadyExits);
		//start syllabus entry units
		List<SyllabusEntryUnitsHoursTo> unitsList=syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos();
		if(unitsList!=null && !unitsList.isEmpty()){
			Set<SyllabusEntryUnitsHours> unitsSet=new HashSet<SyllabusEntryUnitsHours>();
			SyllabusEntryUnitsHours syllabusEntryUnitsHours=null;
			for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : unitsList) {
				syllabusEntryUnitsHours=new SyllabusEntryUnitsHours();
				if(syllabusEntryUnitsHoursTo.getUnits()!=null && !syllabusEntryUnitsHoursTo.getUnits().isEmpty()){
					syllabusEntryUnitsHours.setUnits(syllabusEntryUnitsHoursTo.getUnits());
				}
				if(syllabusEntryUnitsHoursTo.getTeachingHours()!=null && !syllabusEntryUnitsHoursTo.getTeachingHours().isEmpty()){
					syllabusEntryUnitsHours.setTeachingHours(Integer.parseInt(syllabusEntryUnitsHoursTo.getTeachingHours()));
				}
				if(syllabusEntryUnitsHoursTo.getId()>0){
					syllabusEntryUnitsHours.setId(syllabusEntryUnitsHoursTo.getId());
				}
				if(syllabusEntryUnitsHoursTo.getId()<=0){
					syllabusEntryUnitsHours.setCreatedBy(syllabusEntryForHodApprovalForm.getUserId());
					syllabusEntryUnitsHours.setCreatedDate(new Date());
				}
				syllabusEntryUnitsHours.setUnitNo(syllabusEntryUnitsHoursTo.getUnitNo());
				syllabusEntryUnitsHours.setModifiedBy(syllabusEntryForHodApprovalForm.getUserId());
				syllabusEntryUnitsHours.setLastModifiedDate(new Date());
				syllabusEntryUnitsHours.setIsActive(true);
				//start syllabus headings
				List<SyllabusEntryHeadingDescTo> headingsList=syllabusEntryUnitsHoursTo.getSyllabusEntryHeadingDescTos();
				if(headingsList!=null && !headingsList.isEmpty()){
					Set<SyllabusEntryHeadingDesc> headingsSet=new HashSet<SyllabusEntryHeadingDesc>();
					SyllabusEntryHeadingDesc syllabusEntryHeadingDesc=null;
					for (SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo : headingsList) {
						syllabusEntryHeadingDesc=new SyllabusEntryHeadingDesc();
						if(syllabusEntryHeadingDescTo.getHeading()!=null && !syllabusEntryHeadingDescTo.getHeading().isEmpty()){
							syllabusEntryHeadingDesc.setHeading(syllabusEntryHeadingDescTo.getHeading());
						}
						if(syllabusEntryHeadingDescTo.getDescription()!=null && !syllabusEntryHeadingDescTo.getDescription().isEmpty()){
							if(syllabusEntryHeadingDescTo.getDescription().length()<5000){
								syllabusEntryHeadingDesc.setDescription(syllabusEntryHeadingDescTo.getDescription());
							}else{
								request.setAttribute("field","Description");
								throw new Exception();
							}
						}
						if(syllabusEntryHeadingDescTo.getId()>0){
							syllabusEntryHeadingDesc.setId(syllabusEntryHeadingDescTo.getId());
						}
						if(syllabusEntryHeadingDescTo.getId()<=0){
							syllabusEntryHeadingDesc.setCreatedBy(syllabusEntryForHodApprovalForm.getUserId());
							syllabusEntryHeadingDesc.setCreatedDate(new Date());
						}
						syllabusEntryHeadingDesc.setHeadingNo(syllabusEntryHeadingDescTo.getHeadingNO());
						syllabusEntryHeadingDesc.setModifiedBy(syllabusEntryForHodApprovalForm.getUserId());
						syllabusEntryHeadingDesc.setLastModifiedDate(new Date());
						syllabusEntryHeadingDesc.setIsActive(true);
						syllabusEntryHeadingDesc.setSyllabusEntryUnitsHours(syllabusEntryUnitsHours);
						headingsSet.add(syllabusEntryHeadingDesc);
					}
					syllabusEntryUnitsHours.setSyllabusEntryHeadingDescs(headingsSet);
				}
				//end syllabus headings
				syllabusEntryUnitsHours.setSyllabusEntry(syllabusEntry);
				unitsSet.add(syllabusEntryUnitsHours);
			}
			if(isActiveFalseUnits!=null && !isActiveFalseUnits.isEmpty()){
				unitsSet.addAll(isActiveFalseUnits);
			}
			syllabusEntry.setSyllabusEntryUnitsHours(unitsSet);
		}
		//end syllabus entry units
	//end syllabus entry table
	return syllabusEntry;
}
public SyllabusEntry convertFormToAdminSaveBo(
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm,
		HttpServletRequest request) throws Exception{
	boolean flag=false;
	SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForHodApprovalForm.getBatchYear()),Integer.parseInt(syllabusEntryForHodApprovalForm.getSubjectId()));
	if(syllabusEntry==null){
		syllabusEntry=new SyllabusEntry();
	}else{
		flag=true;
	}
	//start syllabus entry table
		syllabusEntry.setMaxMarks(Integer.parseInt(syllabusEntryForHodApprovalForm.getMaxMarks()));
		syllabusEntry.setTotTeachingHrsPerSem(Integer.parseInt(syllabusEntryForHodApprovalForm.getTotTeachHrsPerSem()));
		syllabusEntry.setNoOfLectureHrsPerWeek(Integer.parseInt(syllabusEntryForHodApprovalForm.getNoOfLectureHrsPerWeek()));
		syllabusEntry.setCredits(syllabusEntryForHodApprovalForm.getCredits());
		syllabusEntry.setCourseObjective(syllabusEntryForHodApprovalForm.getCourseObjective());
		syllabusEntry.setLearningOutcome(syllabusEntryForHodApprovalForm.getLerningOutcome());
		if(syllabusEntryForHodApprovalForm.getTextBooksAndRefBooks().trim()!=null && !syllabusEntryForHodApprovalForm.getTextBooksAndRefBooks().trim().isEmpty()){
			syllabusEntry.setTextBooksAndRefBooks(syllabusEntryForHodApprovalForm.getTextBooksAndRefBooks());
		}
		if(syllabusEntryForHodApprovalForm.getRecommendedReading().trim()!=null && !syllabusEntryForHodApprovalForm.getRecommendedReading().trim().isEmpty()){
			syllabusEntry.setRecommendedReading(syllabusEntryForHodApprovalForm.getRecommendedReading());
		}
		if(syllabusEntryForHodApprovalForm.getFreeText()!=null && !syllabusEntryForHodApprovalForm.getFreeText().isEmpty()){
			syllabusEntry.setFreeText(syllabusEntryForHodApprovalForm.getFreeText());
		}
		if(syllabusEntryForHodApprovalForm.getChangeInSyllabus()!=null && !syllabusEntryForHodApprovalForm.getChangeInSyllabus().isEmpty()){
			if(syllabusEntryForHodApprovalForm.getChangeInSyllabus().equalsIgnoreCase("yes")){
				syllabusEntry.setChangeInSyllabus("Y");
				if(syllabusEntryForHodApprovalForm.getBriefDetailsExistingSyllabus().trim().length()<=500){
					syllabusEntry.setBriefDetailsExistingSyllabus(syllabusEntryForHodApprovalForm.getBriefDetailsExistingSyllabus());
				}else{
					request.setAttribute("field1","brief");
					throw new Exception();
				}
				if(syllabusEntryForHodApprovalForm.getBriefDetalsAboutChange().trim().length()<=500){
					syllabusEntry.setBriefDetalsAboutChange(syllabusEntryForHodApprovalForm.getBriefDetalsAboutChange());
				}else{
					request.setAttribute("field1","brief1");
					throw new Exception();
				}
				if(syllabusEntryForHodApprovalForm.getChangeReason().trim().length()<=500){
					syllabusEntry.setChangeReason(syllabusEntryForHodApprovalForm.getChangeReason());
				}else{
					request.setAttribute("field1","brief2");
					throw new Exception();
				}
				if(syllabusEntryForHodApprovalForm.getRemarks().trim().length()<=500){
					syllabusEntry.setRemarks(syllabusEntryForHodApprovalForm.getRemarks());
				}else{
					request.setAttribute("field1","brief3");
					throw new Exception();
				}
				
			}else{
				syllabusEntry.setChangeInSyllabus("N");
			}
		}
		syllabusEntry.setBatchYear(Integer.parseInt(syllabusEntryForHodApprovalForm.getBatchYear()));
		syllabusEntry.setSendForApproval(false);
		syllabusEntry.setApproved(true);
		syllabusEntry.setHodReject(false);
		syllabusEntry.setFinalReject(false);
		syllabusEntry.setFinalApproval(false);
		Subject subject=new Subject();
		subject.setId(Integer.parseInt(syllabusEntryForHodApprovalForm.getSubjectId()));
		syllabusEntry.setSubject(subject);
		if(syllabusEntry.getId()<=0){
			syllabusEntry.setCreatedBy(syllabusEntryForHodApprovalForm.getUserId());
			syllabusEntry.setCreatedDate(new Date());
		}
		syllabusEntry.setModifiedBy(syllabusEntryForHodApprovalForm.getUserId());
		syllabusEntry.setLastModifiedDate(new Date());
		syllabusEntry.setIsActive(true);
		Set<SyllabusEntryUnitsHours> isActiveFalseUnits=null;
		if(flag){
			//start syllabus entry units
			List<Integer> unitsWhichAlreadyExits=new ArrayList<Integer>();
			List<SyllabusEntryUnitsHoursTo> unitsList1=syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos();
			//get units which are already exits to know that that unit have id if already exits
			for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : unitsList1) {
				if(syllabusEntryUnitsHoursTo.getId()>0){
					unitsWhichAlreadyExits.add(syllabusEntryUnitsHoursTo.getId());
				}
			}
			//make isActive false remaining units not in units which is in form
			isActiveFalseUnits=makeIsActiveFalse(syllabusEntry.getSyllabusEntryUnitsHours(),unitsWhichAlreadyExits);
		}
		//start syllabus entry units
		Set<SyllabusEntryUnitsHours> unitsSet=new HashSet<SyllabusEntryUnitsHours>();
		SyllabusEntryUnitsHours syllabusEntryUnitsHours=null;
		List<SyllabusEntryUnitsHoursTo> unitsList=syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos();
		for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : unitsList) {
			syllabusEntryUnitsHours=new SyllabusEntryUnitsHours();
				syllabusEntryUnitsHours.setUnits(syllabusEntryUnitsHoursTo.getUnits());
				if(syllabusEntryUnitsHoursTo.getTeachingHours()!=null && !syllabusEntryUnitsHoursTo.getTeachingHours().isEmpty()){
					syllabusEntryUnitsHours.setTeachingHours(Integer.parseInt(syllabusEntryUnitsHoursTo.getTeachingHours()));
					if(syllabusEntryUnitsHoursTo.getId()>0){
						syllabusEntryUnitsHours.setId(syllabusEntryUnitsHoursTo.getId());
					}
					if(syllabusEntryUnitsHoursTo.getId()<=0){
						syllabusEntryUnitsHours.setCreatedBy(syllabusEntryForHodApprovalForm.getUserId());
						syllabusEntryUnitsHours.setCreatedDate(new Date());
					}
					syllabusEntryUnitsHours.setUnitNo(syllabusEntryUnitsHoursTo.getUnitNo());
					syllabusEntryUnitsHours.setModifiedBy(syllabusEntryForHodApprovalForm.getUserId());
					syllabusEntryUnitsHours.setLastModifiedDate(new Date());
					syllabusEntryUnitsHours.setIsActive(true);
					//start syllabus headings
					Set<SyllabusEntryHeadingDesc> headingsSet=new HashSet<SyllabusEntryHeadingDesc>();
					List<SyllabusEntryHeadingDescTo> headingsList=syllabusEntryUnitsHoursTo.getSyllabusEntryHeadingDescTos();
					SyllabusEntryHeadingDesc syllabusEntryHeadingDesc=null;
					for (SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo : headingsList) {
						syllabusEntryHeadingDesc=new SyllabusEntryHeadingDesc();
						if (syllabusEntryHeadingDescTo.getHeading()!=null && !syllabusEntryHeadingDescTo.getHeading().isEmpty()) {
							syllabusEntryHeadingDesc.setHeading(syllabusEntryHeadingDescTo.getHeading());
						}
						if(syllabusEntryHeadingDescTo.getDescription()!=null && !syllabusEntryHeadingDescTo.getDescription().isEmpty()){
							if(syllabusEntryHeadingDescTo.getDescription().length()<=5000){
								syllabusEntryHeadingDesc.setDescription(syllabusEntryHeadingDescTo.getDescription());
								if(syllabusEntryHeadingDescTo.getId()>0){
									syllabusEntryHeadingDesc.setId(syllabusEntryHeadingDescTo.getId());
								}
								if(syllabusEntryHeadingDescTo.getId()<=0){
									syllabusEntryHeadingDesc.setCreatedBy(syllabusEntryForHodApprovalForm.getUserId());
									syllabusEntryHeadingDesc.setCreatedDate(new Date());
								}
								syllabusEntryHeadingDesc.setHeadingNo(syllabusEntryHeadingDescTo.getHeadingNO());
								syllabusEntryHeadingDesc.setModifiedBy(syllabusEntryForHodApprovalForm.getUserId());
								syllabusEntryHeadingDesc.setLastModifiedDate(new Date());
								syllabusEntryHeadingDesc.setIsActive(true);
								syllabusEntryHeadingDesc.setSyllabusEntryUnitsHours(syllabusEntryUnitsHours);
								headingsSet.add(syllabusEntryHeadingDesc);
							}else{
								request.setAttribute("description","Description");
								throw new Exception();
							}
							
						}else {
							request.setAttribute("field","Unit Details");
							throw new Exception();
						}
					}
					syllabusEntryUnitsHours.setSyllabusEntryHeadingDescs(headingsSet);
					//end syllabus headings
					syllabusEntryUnitsHours.setSyllabusEntry(syllabusEntry);
					unitsSet.add(syllabusEntryUnitsHours);
				}else{
					request.setAttribute("field","Unit Details");
					throw new Exception();
				}
		}
		if(isActiveFalseUnits!=null && !isActiveFalseUnits.isEmpty()){
			unitsSet.addAll(isActiveFalseUnits);
		}
		syllabusEntry.setSyllabusEntryUnitsHours(unitsSet);
		//end syllabus entry units
	//end syllabus entry table
	return syllabusEntry;
}

private Set<SyllabusEntryUnitsHours> makeIsActiveFalse(
		Set<SyllabusEntryUnitsHours> syllabusEntryUnitsHours,
		List<Integer> unitsWhichAlreadyExits) throws Exception{
	Set<SyllabusEntryUnitsHours> set=new HashSet<SyllabusEntryUnitsHours>();
	for (SyllabusEntryUnitsHours syllabusEntryUnitsHours2 : syllabusEntryUnitsHours) {
		if(unitsWhichAlreadyExits!=null && unitsWhichAlreadyExits.contains(syllabusEntryUnitsHours2.getId())){
			continue;
		}else{
			syllabusEntryUnitsHours2.setIsActive(false);
			set.add(syllabusEntryUnitsHours2);
		}
	}
	return set;
}
public Map<Integer, SyllabusEntryProgramDetailsTo> getProgramDetailsMap(
		List<SyllabusEntryProgramDetails> programDetails) throws Exception{
	Map<Integer, SyllabusEntryProgramDetailsTo> map=new HashMap<Integer, SyllabusEntryProgramDetailsTo>();
	SyllabusEntryProgramDetailsTo syllabusEntryProgramDetailsTo=null;
	if(programDetails!=null && !programDetails.isEmpty()){
		for (SyllabusEntryProgramDetails syllabusEntryProgramDetails : programDetails) {
			syllabusEntryProgramDetailsTo=new SyllabusEntryProgramDetailsTo();
			syllabusEntryProgramDetailsTo.setAssesmentPattern(syllabusEntryProgramDetails.getAssesmentPattern());
			syllabusEntryProgramDetailsTo.setDepartmentOverview(syllabusEntryProgramDetails.getDepartmentOverview());
			syllabusEntryProgramDetailsTo.setExaminationAndAssesments(syllabusEntryProgramDetails.getExaminationAndAssesments());
			syllabusEntryProgramDetailsTo.setIntroductionProgramme(syllabusEntryProgramDetails.getIntroductionProgramme());
			syllabusEntryProgramDetailsTo.setMissionStatement(syllabusEntryProgramDetails.getMissionStatement());
			syllabusEntryProgramDetailsTo.setProgramObjective(syllabusEntryProgramDetails.getProgramObjective());
			syllabusEntryProgramDetailsTo.setId(syllabusEntryProgramDetails.getId());
			syllabusEntryProgramDetailsTo.setCourseId(syllabusEntryProgramDetails.getCourseId().getId());
			syllabusEntryProgramDetailsTo.setBatchYear(syllabusEntryProgramDetails.getBatchYear());
			map.put(syllabusEntryProgramDetails.getCourseId().getId(), syllabusEntryProgramDetailsTo);
		}
	}
	return map;
}
public void convertProgramDetailsToForm(
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm,
		SyllabusEntryProgramDetails syllabusEntryProgramDetails) throws Exception{
	if(syllabusEntryProgramDetails!=null){
		if(syllabusEntryProgramDetails.getAssesmentPattern()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Assesment Pattern</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntryProgramDetails.getAssesmentPattern().length();i++){
				stringBuilder.append(syllabusEntryProgramDetails.getAssesmentPattern().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryForHodApprovalForm.setAssesmentPattern(stringBuilder.toString());
		}
		if(syllabusEntryProgramDetails.getExaminationAndAssesments()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Examination And Assesments</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntryProgramDetails.getExaminationAndAssesments().length();i++){
				stringBuilder.append(syllabusEntryProgramDetails.getExaminationAndAssesments().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryForHodApprovalForm.setExaminationAndAssesments(stringBuilder.toString());
		}
		syllabusEntryForHodApprovalForm.setProgramObjective(syllabusEntryProgramDetails.getProgramObjective());
		syllabusEntryForHodApprovalForm.setIntroductionProgramme(syllabusEntryProgramDetails.getIntroductionProgramme());
		syllabusEntryForHodApprovalForm.setDepartmentOverview(syllabusEntryProgramDetails.getDepartmentOverview());
		syllabusEntryForHodApprovalForm.setMissionStatement(syllabusEntryProgramDetails.getMissionStatement());
	}
	
}
public void convertBosToTos(
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm,
		List<SyllabusEntry> list) throws Exception{
	List<SyllabusEntryPreviewTo> toList=new ArrayList<SyllabusEntryPreviewTo>();
	SyllabusEntryPreviewTo syllabusEntryPreviewTo=null;
	for (SyllabusEntry syllabusEntry : list) {
		syllabusEntryPreviewTo=new SyllabusEntryPreviewTo();
		syllabusEntryPreviewTo.setBatchYear(String.valueOf(syllabusEntry.getBatchYear()));
		syllabusEntryPreviewTo.setSubjectCode(syllabusEntry.getSubject().getCode());
		syllabusEntryPreviewTo.setSubjectName(syllabusEntry.getSubject().getName());
		if(syllabusEntry.getTotTeachingHrsPerSem()!=null){
			syllabusEntryPreviewTo.setTotTeachHrsPerSem(String.valueOf(syllabusEntry.getTotTeachingHrsPerSem()));
		}
		if(syllabusEntry.getNoOfLectureHrsPerWeek()!=null){
			syllabusEntryPreviewTo.setNoOfLectureHrsPerWeek(String.valueOf(syllabusEntry.getNoOfLectureHrsPerWeek()));
		}
		if(syllabusEntry.getCredits()!=null){
			syllabusEntryPreviewTo.setCredits(syllabusEntry.getCredits());
		}
		if(syllabusEntry.getMaxMarks()!=null){
			syllabusEntryPreviewTo.setMaxMarks(String.valueOf(syllabusEntry.getMaxMarks()));
		}
		if(syllabusEntry.getCourseObjective()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Course Objective</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getCourseObjective().length();i++){
				stringBuilder.append(syllabusEntry.getCourseObjective().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryPreviewTo.setCourseObjective(stringBuilder.toString());
		}
		if(syllabusEntry.getLearningOutcome()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Learning Outcome</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getLearningOutcome().length();i++){
				stringBuilder.append(syllabusEntry.getLearningOutcome().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryPreviewTo.setLerningOutcome(stringBuilder.toString());
		}
		if(syllabusEntry.getTextBooksAndRefBooks()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Essential Text Books</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getTextBooksAndRefBooks().length();i++){
				stringBuilder.append(syllabusEntry.getTextBooksAndRefBooks().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryPreviewTo.setTextBooksAndRefBooks(stringBuilder.toString());
		}
		if(syllabusEntry.getRecommendedReading()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Recommended Reading</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getRecommendedReading().length();i++){
				stringBuilder.append(syllabusEntry.getRecommendedReading().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryPreviewTo.setRecommendedReading(stringBuilder.toString());
		}
		if(syllabusEntry.getFreeText()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Subject Additional Information (This will appear end of each subject)</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getFreeText().length();i++){
				stringBuilder.append(syllabusEntry.getFreeText().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryPreviewTo.setFreeText(stringBuilder.toString());
		}
		//units
		if(syllabusEntry.getSyllabusEntryUnitsHours()!=null && !syllabusEntry.getSyllabusEntryUnitsHours().isEmpty()){
			Set<SyllabusEntryUnitsHours> unitsSet=syllabusEntry.getSyllabusEntryUnitsHours();
			List<SyllabusEntryUnitsHoursTo> unitsList=new ArrayList<SyllabusEntryUnitsHoursTo>();
			SyllabusEntryUnitsHoursTo syllEntryUnitsHoursTo=null;
			for (SyllabusEntryUnitsHours syllabusEntryUnitsHours : unitsSet) {
				syllEntryUnitsHoursTo=new SyllabusEntryUnitsHoursTo();
				if(syllabusEntryUnitsHours.getIsActive()!=null && syllabusEntryUnitsHours.getIsActive()){
					syllEntryUnitsHoursTo.setId(syllabusEntryUnitsHours.getId());
					syllEntryUnitsHoursTo.setTeachingHoursTemplate("Teaching Hours");
					syllEntryUnitsHoursTo.setPosition(unitsList.size()+1);
					syllEntryUnitsHoursTo.setUnitNo(syllabusEntryUnitsHours.getUnitNo());
					if(syllabusEntryUnitsHours.getTeachingHours()!=null){
						syllEntryUnitsHoursTo.setTeachingHours(String.valueOf(syllabusEntryUnitsHours.getTeachingHours()));
					}
					if(syllabusEntryUnitsHours.getUnits()!=null){
						syllEntryUnitsHoursTo.setUnits(syllabusEntryUnitsHours.getUnits());
					}
					//headings
					if(syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs()!=null && !syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs().isEmpty()){
						Set<SyllabusEntryHeadingDesc> headingsSet=syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs();
						List<SyllabusEntryHeadingDescTo> headingsList=new ArrayList<SyllabusEntryHeadingDescTo>();
						SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo=null;
						for (SyllabusEntryHeadingDesc syllabusEntryHeadingDesc : headingsSet) {
							syllabusEntryHeadingDescTo=new SyllabusEntryHeadingDescTo();
							syllabusEntryHeadingDescTo.setId(syllabusEntryHeadingDesc.getId());
							if(syllabusEntryHeadingDesc.getHeading()!=null && !syllabusEntryHeadingDesc.getHeading().isEmpty()){
								syllabusEntryHeadingDescTo.setTempHead(syllabusEntryHeadingDesc.getHeading());
							}else {
								syllabusEntryHeadingDescTo.setHeadingTemplate("Heading");
							}
							syllabusEntryHeadingDescTo.setDescriptionTemplate("Description");
							syllabusEntryHeadingDescTo.setHeadingNO(syllabusEntryHeadingDesc.getHeadingNo());
							if(syllabusEntryHeadingDesc.getDescription()!=null){
								syllabusEntryHeadingDescTo.setDescription(syllabusEntryHeadingDesc.getDescription().trim());
							}
							if(syllabusEntryHeadingDesc.getHeading()!=null){
								syllabusEntryHeadingDescTo.setHeading(syllabusEntryHeadingDesc.getHeading().trim());
							}
							headingsList.add(syllabusEntryHeadingDescTo);
						}
						Collections.sort(headingsList);
						syllEntryUnitsHoursTo.setSyllabusEntryHeadingDescTos(headingsList);
					}
					//end headings
					unitsList.add(syllEntryUnitsHoursTo);
				}
			}
			Collections.sort(unitsList);
			//end units
			syllabusEntryPreviewTo.setSyllabusEntryUnitsHoursTos(unitsList);
		}
		toList.add(syllabusEntryPreviewTo);
	}
	syllabusEntryForHodApprovalForm.setHodPreviewList(toList);
}
public void setToFormForPreview(SyllabusEntry syllabusEntry,
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{


	syllabusEntryForHodApprovalForm.setBatchYear(String.valueOf(syllabusEntry.getBatchYear()));
	syllabusEntryForHodApprovalForm.setSubjectCode(syllabusEntry.getSubject().getCode());
	syllabusEntryForHodApprovalForm.setSubjectName(syllabusEntry.getSubject().getName());
	if(syllabusEntry.getTotTeachingHrsPerSem()!=null){
		syllabusEntryForHodApprovalForm.setTotTeachHrsPerSem(String.valueOf(syllabusEntry.getTotTeachingHrsPerSem()));
	}
	if(syllabusEntry.getNoOfLectureHrsPerWeek()!=null){
		syllabusEntryForHodApprovalForm.setNoOfLectureHrsPerWeek(String.valueOf(syllabusEntry.getNoOfLectureHrsPerWeek()));
	}
	if(syllabusEntry.getCredits()!=null){
		syllabusEntryForHodApprovalForm.setCredits(syllabusEntry.getCredits());
	}
	if(syllabusEntry.getMaxMarks()!=null){
		syllabusEntryForHodApprovalForm.setMaxMarks(String.valueOf(syllabusEntry.getMaxMarks()));
	}
	if(syllabusEntry.getCourseObjective()!=null){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Course Objective</font></b></td></tr>"+
				"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
		for(int i=0;i<syllabusEntry.getCourseObjective().length();i++){
			stringBuilder.append(syllabusEntry.getCourseObjective().charAt(i));
		}
		stringBuilder.append("</font></td></tr></table></td></tr></table>");
		syllabusEntryForHodApprovalForm.setCourseObjective(stringBuilder.toString());
	}
	if(syllabusEntry.getLearningOutcome()!=null){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Learning Outcome</font></b></td></tr>"+
				"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
		for(int i=0;i<syllabusEntry.getLearningOutcome().length();i++){
			stringBuilder.append(syllabusEntry.getLearningOutcome().charAt(i));
		}
		stringBuilder.append("</font></td></tr></table></td></tr></table>");
		syllabusEntryForHodApprovalForm.setLerningOutcome(stringBuilder.toString());
	}
	if(syllabusEntry.getTextBooksAndRefBooks()!=null){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Essential Text Books</font></b></td></tr>"+
				"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
		for(int i=0;i<syllabusEntry.getTextBooksAndRefBooks().length();i++){
			stringBuilder.append(syllabusEntry.getTextBooksAndRefBooks().charAt(i));
		}
		stringBuilder.append("</font></td></tr></table></td></tr></table>");
		syllabusEntryForHodApprovalForm.setTextBooksAndRefBooks(stringBuilder.toString());
	}
	if(syllabusEntry.getRecommendedReading()!=null){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Recommended Reading</font></b></td></tr>"+
				"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
		for(int i=0;i<syllabusEntry.getRecommendedReading().length();i++){
			stringBuilder.append(syllabusEntry.getRecommendedReading().charAt(i));
		}
		stringBuilder.append("</font></td></tr></table></td></tr></table>");
		syllabusEntryForHodApprovalForm.setRecommendedReading(stringBuilder.toString());
	}
	if(syllabusEntry.getFreeText()!=null){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Subject Additional Information (This will appear end of each subject)</font></b></td></tr>"+
				"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
		for(int i=0;i<syllabusEntry.getFreeText().length();i++){
			stringBuilder.append(syllabusEntry.getFreeText().charAt(i));
		}
		stringBuilder.append("</font></td></tr></table></td></tr></table>");
		syllabusEntryForHodApprovalForm.setFreeText(stringBuilder.toString());
	}
	//units
	if(syllabusEntry.getSyllabusEntryUnitsHours()!=null && !syllabusEntry.getSyllabusEntryUnitsHours().isEmpty()){
		Set<SyllabusEntryUnitsHours> unitsSet=syllabusEntry.getSyllabusEntryUnitsHours();
		List<SyllabusEntryUnitsHoursTo> unitsList=new ArrayList<SyllabusEntryUnitsHoursTo>();
		SyllabusEntryUnitsHoursTo syllEntryUnitsHoursTo=null;
		for (SyllabusEntryUnitsHours syllabusEntryUnitsHours : unitsSet) {
			syllEntryUnitsHoursTo=new SyllabusEntryUnitsHoursTo();
			if(syllabusEntryUnitsHours.getIsActive()!=null && syllabusEntryUnitsHours.getIsActive()){
				syllEntryUnitsHoursTo.setId(syllabusEntryUnitsHours.getId());
				syllEntryUnitsHoursTo.setTeachingHoursTemplate("Teaching Hours");
				syllEntryUnitsHoursTo.setPosition(unitsList.size()+1);
				syllEntryUnitsHoursTo.setUnitNo(syllabusEntryUnitsHours.getUnitNo());
				if(syllabusEntryUnitsHours.getTeachingHours()!=null){
					syllEntryUnitsHoursTo.setTeachingHours(String.valueOf(syllabusEntryUnitsHours.getTeachingHours()));
				}
				if(syllabusEntryUnitsHours.getUnits()!=null){
					syllEntryUnitsHoursTo.setUnits(syllabusEntryUnitsHours.getUnits());
				}
				//headings
				if(syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs()!=null && !syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs().isEmpty()){
					Set<SyllabusEntryHeadingDesc> headingsSet=syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs();
					List<SyllabusEntryHeadingDescTo> headingsList=new ArrayList<SyllabusEntryHeadingDescTo>();
					SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo=null;
					for (SyllabusEntryHeadingDesc syllabusEntryHeadingDesc : headingsSet) {
						syllabusEntryHeadingDescTo=new SyllabusEntryHeadingDescTo();
						syllabusEntryHeadingDescTo.setId(syllabusEntryHeadingDesc.getId());
						if(syllabusEntryHeadingDesc.getHeading()!=null && !syllabusEntryHeadingDesc.getHeading().isEmpty()){
							syllabusEntryHeadingDescTo.setTempHead(syllabusEntryHeadingDesc.getHeading());
						}else {
							syllabusEntryHeadingDescTo.setHeadingTemplate("Heading");
						}
						syllabusEntryHeadingDescTo.setDescriptionTemplate("Description");
						syllabusEntryHeadingDescTo.setHeadingNO(syllabusEntryHeadingDesc.getHeadingNo());
						if(syllabusEntryHeadingDesc.getDescription()!=null){
							syllabusEntryHeadingDescTo.setDescription(syllabusEntryHeadingDesc.getDescription().trim());
						}
						if(syllabusEntryHeadingDesc.getHeading()!=null){
							syllabusEntryHeadingDescTo.setHeading(syllabusEntryHeadingDesc.getHeading().trim());
						}
						headingsList.add(syllabusEntryHeadingDescTo);
					}
					Collections.sort(headingsList);
					syllEntryUnitsHoursTo.setSyllabusEntryHeadingDescTos(headingsList);
				}
				//end headings
				unitsList.add(syllEntryUnitsHoursTo);
			}
		}
		Collections.sort(unitsList);
		//end units
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(unitsList);
	}

}
public void setToFormPreviousSyllabusEntry(SyllabusEntry syllabusEntry,
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{

	if(syllabusEntry.getTotTeachingHrsPerSem()!=null){
		syllabusEntryForHodApprovalForm.setTotTeachHrsPerSem(String.valueOf(syllabusEntry.getTotTeachingHrsPerSem()));
	}
	if(syllabusEntry.getNoOfLectureHrsPerWeek()!=null){
		syllabusEntryForHodApprovalForm.setNoOfLectureHrsPerWeek(String.valueOf(syllabusEntry.getNoOfLectureHrsPerWeek()));
	}
	if(syllabusEntry.getCredits()!=null){
		syllabusEntryForHodApprovalForm.setCredits(syllabusEntry.getCredits());
	}
	if(syllabusEntry.getMaxMarks()!=null){
		syllabusEntryForHodApprovalForm.setMaxMarks(String.valueOf(syllabusEntry.getMaxMarks()));
	}
	if(syllabusEntry.getCourseObjective()!=null){
		syllabusEntryForHodApprovalForm.setCourseObjective(syllabusEntry.getCourseObjective());
	}
	if(syllabusEntry.getLearningOutcome()!=null){
		syllabusEntryForHodApprovalForm.setLerningOutcome(syllabusEntry.getLearningOutcome());
	}
	if(syllabusEntry.getTextBooksAndRefBooks()!=null){
		syllabusEntryForHodApprovalForm.setTextBooksAndRefBooks(syllabusEntry.getTextBooksAndRefBooks());
	}
	if(syllabusEntry.getFreeText()!=null){
		syllabusEntryForHodApprovalForm.setFreeText(syllabusEntry.getFreeText());
	}
	if(syllabusEntry.getChangeInSyllabus()!=null){
		if(syllabusEntry.getChangeInSyllabus().equalsIgnoreCase("Y")){
			syllabusEntryForHodApprovalForm.setChangeInSyllabus("yes");
			syllabusEntryForHodApprovalForm.setTempChangeInSyllabus("yes");
		}else{
			syllabusEntryForHodApprovalForm.setChangeInSyllabus("no");
			syllabusEntryForHodApprovalForm.setTempChangeInSyllabus("no");
		}
		
	}
	if(syllabusEntry.getBriefDetailsExistingSyllabus()!=null){
		syllabusEntryForHodApprovalForm.setBriefDetailsExistingSyllabus(syllabusEntry.getBriefDetailsExistingSyllabus());
	}
	if(syllabusEntry.getBriefDetalsAboutChange()!=null){
		syllabusEntryForHodApprovalForm.setBriefDetalsAboutChange(syllabusEntry.getBriefDetalsAboutChange());
	}
	if(syllabusEntry.getChangeInSyllabus()!=null){
		syllabusEntryForHodApprovalForm.setChangeReason(syllabusEntry.getChangeReason());
	}
	if(syllabusEntry.getRemarks()!=null){
		syllabusEntryForHodApprovalForm.setRemarks(syllabusEntry.getRemarks());
	}
	//units
	if(syllabusEntry.getSyllabusEntryUnitsHours()!=null && !syllabusEntry.getSyllabusEntryUnitsHours().isEmpty()){
		Set<SyllabusEntryUnitsHours> unitsSet=syllabusEntry.getSyllabusEntryUnitsHours();
		List<SyllabusEntryUnitsHoursTo> unitsList=new ArrayList<SyllabusEntryUnitsHoursTo>();
		SyllabusEntryUnitsHoursTo syllEntryUnitsHoursTo=null;
		for (SyllabusEntryUnitsHours syllabusEntryUnitsHours : unitsSet) {
			syllEntryUnitsHoursTo=new SyllabusEntryUnitsHoursTo();
			syllEntryUnitsHoursTo.setId(syllabusEntryUnitsHours.getId());
			syllEntryUnitsHoursTo.setTeachingHoursTemplate("Teaching Hours");
			syllEntryUnitsHoursTo.setPosition(unitsList.size()+1);
			if(syllabusEntryUnitsHours.getTeachingHours()!=null){
				syllEntryUnitsHoursTo.setTeachingHours(String.valueOf(syllabusEntryUnitsHours.getTeachingHours()));
			}
			if(syllabusEntryUnitsHours.getUnits()!=null){
				syllEntryUnitsHoursTo.setUnits(syllabusEntryUnitsHours.getUnits());
			}
			//headings
			if(syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs()!=null && !syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs().isEmpty()){
				Set<SyllabusEntryHeadingDesc> headingsSet=syllabusEntryUnitsHours.getSyllabusEntryHeadingDescs();
				List<SyllabusEntryHeadingDescTo> headingsList=new ArrayList<SyllabusEntryHeadingDescTo>();
				SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo=null;
				for (SyllabusEntryHeadingDesc syllabusEntryHeadingDesc : headingsSet) {
					syllabusEntryHeadingDescTo=new SyllabusEntryHeadingDescTo();
					syllabusEntryHeadingDescTo.setId(syllabusEntryHeadingDesc.getId());
					syllabusEntryHeadingDescTo.setHeadingTemplate("Heading");
					syllabusEntryHeadingDescTo.setDescriptionTemplate("Description");
					if(syllabusEntryHeadingDesc.getDescription()!=null){
						syllabusEntryHeadingDescTo.setDescription(syllabusEntryHeadingDesc.getDescription());
					}
					if(syllabusEntryHeadingDesc.getHeading()!=null){
						syllabusEntryHeadingDescTo.setHeading(syllabusEntryHeadingDesc.getHeading());
					}
					headingsList.add(syllabusEntryHeadingDescTo);
				}
				syllEntryUnitsHoursTo.setSyllabusEntryHeadingDescTos(headingsList);
			}
			//end headings
			unitsList.add(syllEntryUnitsHoursTo);
		}
		Collections.sort(unitsList);
		//end units
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(unitsList);
	}
	}
}
