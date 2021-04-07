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
import com.kp.cms.bo.admin.SyllabusEntryUnitsHours;
import com.kp.cms.forms.admin.SyllabusEntryForm;
import com.kp.cms.to.admin.SyllabusEntryDupTo;
import com.kp.cms.to.admin.SyllabusEntryGeneralTo;
import com.kp.cms.to.admin.SyllabusEntryHeadingDescTo;
import com.kp.cms.to.admin.SyllabusEntryTo;
import com.kp.cms.to.admin.SyllabusEntryUnitsHoursTo;
import com.kp.cms.transactions.admin.ISyllabusEntryTrans;
import com.kp.cms.transactionsimpl.admin.SyllabusEntryTransImpl;


public class SyllabusEntryHelper {
	ISyllabusEntryTrans transaction=SyllabusEntryTransImpl.getInstance();
	public static volatile SyllabusEntryHelper syllabusEntryHelper=null;
	//private constructor
	private SyllabusEntryHelper(){
		
	}
	//singleton object
	public static SyllabusEntryHelper getInstance(){
		if(syllabusEntryHelper==null){
			syllabusEntryHelper=new SyllabusEntryHelper();
			return syllabusEntryHelper;
		}
		return syllabusEntryHelper;
	}
	public SyllabusEntry convertFormToAdminSaveBo(SyllabusEntryForm syllabusEntryForm, HttpServletRequest request) throws Exception{
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForm.getBatchYear()),Integer.parseInt(syllabusEntryForm.getSubjectId()));
		boolean flag=false;
		if(syllabusEntry==null){
			syllabusEntry=new SyllabusEntry();
		}else{
			flag=true;
		}
		//start syllabus entry table
			syllabusEntry.setMaxMarks(Integer.parseInt(syllabusEntryForm.getMaxMarks()));
			syllabusEntry.setTotTeachingHrsPerSem(Integer.parseInt(syllabusEntryForm.getTotTeachHrsPerSem()));
			syllabusEntry.setNoOfLectureHrsPerWeek(Integer.parseInt(syllabusEntryForm.getNoOfLectureHrsPerWeek()));
			syllabusEntry.setCredits(syllabusEntryForm.getCredits());
			syllabusEntry.setCourseObjective(syllabusEntryForm.getCourseObjective());
			syllabusEntry.setLearningOutcome(syllabusEntryForm.getLerningOutcome());
			if(syllabusEntryForm.getTextBooksAndRefBooks().trim()!=null && !syllabusEntryForm.getTextBooksAndRefBooks().trim().isEmpty()){
				syllabusEntry.setTextBooksAndRefBooks(syllabusEntryForm.getTextBooksAndRefBooks());
			}
			if(syllabusEntryForm.getRecommendedReading().trim()!=null && !syllabusEntryForm.getRecommendedReading().trim().isEmpty()){
				syllabusEntry.setRecommendedReading(syllabusEntryForm.getRecommendedReading());
			}
			if(syllabusEntryForm.getFreeText()!=null && !syllabusEntryForm.getFreeText().isEmpty()){
				syllabusEntry.setFreeText(syllabusEntryForm.getFreeText());
			}
			if(syllabusEntryForm.getChangeInSyllabus()!=null && !syllabusEntryForm.getChangeInSyllabus().isEmpty()){
				if(syllabusEntryForm.getChangeInSyllabus().equalsIgnoreCase("yes")){
					syllabusEntry.setChangeInSyllabus("Y");
					if(syllabusEntryForm.getBriefDetailsExistingSyllabus().trim().length()<=500){
						syllabusEntry.setBriefDetailsExistingSyllabus(syllabusEntryForm.getBriefDetailsExistingSyllabus());
					}else{
						request.setAttribute("field1","brief");
						throw new Exception();
					}
					if(syllabusEntryForm.getBriefDetalsAboutChange().trim().length()<=500){
						syllabusEntry.setBriefDetalsAboutChange(syllabusEntryForm.getBriefDetalsAboutChange());
					}else{
						request.setAttribute("field1","brief1");
						throw new Exception();
					}
					if(syllabusEntryForm.getChangeReason().trim().length()<=500){
						syllabusEntry.setChangeReason(syllabusEntryForm.getChangeReason());
					}else{
						request.setAttribute("field1","brief2");
						throw new Exception();
					}
					if(syllabusEntryForm.getRemarks().trim().length()<=500){
						syllabusEntry.setRemarks(syllabusEntryForm.getRemarks());
					}else{
						request.setAttribute("field1","brief3");
						throw new Exception();
					}
					
				}else{
					syllabusEntry.setChangeInSyllabus("N");
				}
			}
			syllabusEntry.setBatchYear(Integer.parseInt(syllabusEntryForm.getBatchYear()));
			syllabusEntry.setSendForApproval(true);
			syllabusEntry.setApproved(false);
			syllabusEntry.setHodReject(false);
			syllabusEntry.setFinalReject(false);
			syllabusEntry.setFinalApproval(false);
			
			Subject subject=new Subject();
			subject.setId(Integer.parseInt(syllabusEntryForm.getSubjectId()));
			syllabusEntry.setSubject(subject);
			if(syllabusEntry.getId()<=0){
				syllabusEntry.setCreatedBy(syllabusEntryForm.getUserId());
				syllabusEntry.setCreatedDate(new Date());
			}
			syllabusEntry.setModifiedBy(syllabusEntryForm.getUserId());
			syllabusEntry.setLastModifiedDate(new Date());
			syllabusEntry.setIsActive(true);
			Set<SyllabusEntryUnitsHours> isActiveFalseUnits=null;
			if(flag){
				//start syllabus entry units
				List<Integer> unitsWhichAlreadyExits=new ArrayList<Integer>();
				List<SyllabusEntryUnitsHoursTo> unitsList1=syllabusEntryForm.getSyllabusEntryUnitsHoursTos();
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
			List<SyllabusEntryUnitsHoursTo> unitsList=syllabusEntryForm.getSyllabusEntryUnitsHoursTos();
			for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : unitsList) {
				syllabusEntryUnitsHours=new SyllabusEntryUnitsHours();
					syllabusEntryUnitsHours.setUnits(syllabusEntryUnitsHoursTo.getUnits());
					if(syllabusEntryUnitsHoursTo.getTeachingHours()!=null && !syllabusEntryUnitsHoursTo.getTeachingHours().isEmpty()){
						syllabusEntryUnitsHours.setTeachingHours(Integer.parseInt(syllabusEntryUnitsHoursTo.getTeachingHours()));
						if(syllabusEntryUnitsHoursTo.getId()>0){
							syllabusEntryUnitsHours.setId(syllabusEntryUnitsHoursTo.getId());
						}
						if(syllabusEntryUnitsHoursTo.getId()<=0){
							syllabusEntryUnitsHours.setCreatedBy(syllabusEntryForm.getUserId());
							syllabusEntryUnitsHours.setCreatedDate(new Date());
						}
						syllabusEntryUnitsHours.setUnitNo(syllabusEntryUnitsHoursTo.getUnitNo());
						syllabusEntryUnitsHours.setModifiedBy(syllabusEntryForm.getUserId());
						syllabusEntryUnitsHours.setLastModifiedDate(new Date());
						syllabusEntryUnitsHours.setIsActive(true);
						//start syllabus headings
						Set<SyllabusEntryHeadingDesc> headingsSet=new HashSet<SyllabusEntryHeadingDesc>();
						List<SyllabusEntryHeadingDescTo> headingsList=syllabusEntryUnitsHoursTo.getSyllabusEntryHeadingDescTos();
						SyllabusEntryHeadingDesc syllabusEntryHeadingDesc=null;
						for (SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo : headingsList) {
							syllabusEntryHeadingDesc=new SyllabusEntryHeadingDesc();
							if(syllabusEntryHeadingDescTo.getHeading()!=null && !syllabusEntryHeadingDescTo.getHeading().isEmpty()){
								syllabusEntryHeadingDesc.setHeading(syllabusEntryHeadingDescTo.getHeading());
							}
							if(syllabusEntryHeadingDescTo.getDescription()!=null && !syllabusEntryHeadingDescTo.getDescription().trim().isEmpty()){
								if(syllabusEntryHeadingDescTo.getDescription().trim().length()<=5000){
									syllabusEntryHeadingDesc.setDescription(syllabusEntryHeadingDescTo.getDescription().trim());
									if(syllabusEntryHeadingDescTo.getId()>0){
										syllabusEntryHeadingDesc.setId(syllabusEntryHeadingDescTo.getId());
									}
									if(syllabusEntryHeadingDescTo.getId()<=0){
										syllabusEntryHeadingDesc.setCreatedBy(syllabusEntryForm.getUserId());
										syllabusEntryHeadingDesc.setCreatedDate(new Date());
									}
									syllabusEntryHeadingDesc.setHeadingNo(syllabusEntryHeadingDescTo.getHeadingNO());
									syllabusEntryHeadingDesc.setModifiedBy(syllabusEntryForm.getUserId());
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
	public SyllabusEntry convertFormToAdminSaveDraftBo(
			SyllabusEntryForm syllabusEntryForm, HttpServletRequest request) throws Exception{
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForm.getBatchYear()),Integer.parseInt(syllabusEntryForm.getSubjectId()));
			if(syllabusEntry==null){
				syllabusEntry=new SyllabusEntry();
			}
		//start syllabus entry table
			if(syllabusEntryForm.getMaxMarks()!=null && !syllabusEntryForm.getMaxMarks().isEmpty()){
				syllabusEntry.setMaxMarks(Integer.parseInt(syllabusEntryForm.getMaxMarks()));
			}
			if(syllabusEntryForm.getTotTeachHrsPerSem()!=null && !syllabusEntryForm.getTotTeachHrsPerSem().isEmpty()){
				syllabusEntry.setTotTeachingHrsPerSem(Integer.parseInt(syllabusEntryForm.getTotTeachHrsPerSem()));
			}
			if(syllabusEntryForm.getNoOfLectureHrsPerWeek()!=null && !syllabusEntryForm.getNoOfLectureHrsPerWeek().isEmpty()){
				syllabusEntry.setNoOfLectureHrsPerWeek(Integer.parseInt(syllabusEntryForm.getNoOfLectureHrsPerWeek()));
			}
			if(syllabusEntryForm.getCredits().trim()!=null && !syllabusEntryForm.getCredits().trim().isEmpty()){
				syllabusEntry.setCredits(syllabusEntryForm.getCredits());
			}	
			if(syllabusEntryForm.getCourseObjective().trim()!=null && !syllabusEntryForm.getCourseObjective().trim().isEmpty()){
				syllabusEntry.setCourseObjective(syllabusEntryForm.getCourseObjective());
			}	
			if(syllabusEntryForm.getLerningOutcome()!=null && !syllabusEntryForm.getLerningOutcome().trim().isEmpty()){
				syllabusEntry.setLearningOutcome(syllabusEntryForm.getLerningOutcome());
			}	
			if(syllabusEntryForm.getTextBooksAndRefBooks().trim()!=null && !syllabusEntryForm.getTextBooksAndRefBooks().trim().isEmpty()){
				syllabusEntry.setTextBooksAndRefBooks(syllabusEntryForm.getTextBooksAndRefBooks());
			}	
			if(syllabusEntryForm.getFreeText().trim()!=null && !syllabusEntryForm.getFreeText().trim().isEmpty()){
				syllabusEntry.setFreeText(syllabusEntryForm.getFreeText());
			}	
			if(syllabusEntryForm.getBatchYear()!=null){
				syllabusEntry.setBatchYear(Integer.parseInt(syllabusEntryForm.getBatchYear()));
			}
			if(syllabusEntryForm.getRecommendedReading().trim()!=null && !syllabusEntryForm.getRecommendedReading().trim().isEmpty()){
				syllabusEntry.setRecommendedReading(syllabusEntryForm.getRecommendedReading().trim());
			}
			if(syllabusEntryForm.getChangeInSyllabus()!=null && !syllabusEntryForm.getChangeInSyllabus().isEmpty()){
				if(syllabusEntryForm.getChangeInSyllabus().equalsIgnoreCase("yes")){
					syllabusEntry.setChangeInSyllabus("Y");
				}else {
					syllabusEntry.setChangeInSyllabus("N");
				}
			}
			if(syllabusEntryForm.getBriefDetailsExistingSyllabus()!=null && !syllabusEntryForm.getBriefDetailsExistingSyllabus().isEmpty()){
				syllabusEntry.setBriefDetailsExistingSyllabus(syllabusEntryForm.getBriefDetailsExistingSyllabus().trim());
			}
			if(syllabusEntryForm.getBriefDetalsAboutChange()!=null && !syllabusEntryForm.getBriefDetalsAboutChange().isEmpty()){
				syllabusEntry.setBriefDetalsAboutChange(syllabusEntryForm.getBriefDetalsAboutChange().trim());
			}
			if(syllabusEntryForm.getChangeReason()!=null && !syllabusEntryForm.getChangeReason().isEmpty()){
				syllabusEntry.setChangeReason(syllabusEntryForm.getChangeReason().trim());
			}
			if(syllabusEntryForm.getRemarks()!=null && !syllabusEntryForm.getRemarks().isEmpty()){
				syllabusEntry.setRemarks(syllabusEntryForm.getRemarks().trim());
			}
			syllabusEntry.setSendForApproval(false);
			syllabusEntry.setApproved(false);
			syllabusEntry.setHodReject(false);
			syllabusEntry.setFinalReject(false);
			syllabusEntry.setFinalApproval(false);
			if(syllabusEntryForm.getSubjectId()!=null){
				Subject subject=new Subject();
				subject.setId(Integer.parseInt(syllabusEntryForm.getSubjectId()));
				syllabusEntry.setSubject(subject);
			}
			if(syllabusEntry.getId()<=0){
				syllabusEntry.setCreatedBy(syllabusEntryForm.getUserId());
				syllabusEntry.setCreatedDate(new Date());
			}
			syllabusEntry.setModifiedBy(syllabusEntryForm.getUserId());
			syllabusEntry.setLastModifiedDate(new Date());
			syllabusEntry.setIsActive(true);
			//start syllabus entry units
			List<Integer> unitsWhichAlreadyExits=new ArrayList<Integer>();
			List<SyllabusEntryUnitsHoursTo> unitsList1=syllabusEntryForm.getSyllabusEntryUnitsHoursTos();
			//get units which are already exits to know that that unit have id if already exits
			for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : unitsList1) {
				if(syllabusEntryUnitsHoursTo.getId()>0){
					unitsWhichAlreadyExits.add(syllabusEntryUnitsHoursTo.getId());
				}
			}
			//make isActive false remaining units not in units which is in form
			Set<SyllabusEntryUnitsHours> isActiveFalseUnits=makeIsActiveFalse(syllabusEntry.getSyllabusEntryUnitsHours(),unitsWhichAlreadyExits);
			List<SyllabusEntryUnitsHoursTo> unitsList=syllabusEntryForm.getSyllabusEntryUnitsHoursTos();
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
						syllabusEntryUnitsHours.setCreatedBy(syllabusEntryForm.getUserId());
						syllabusEntryUnitsHours.setCreatedDate(new Date());
					}
					syllabusEntryUnitsHours.setUnitNo(syllabusEntryUnitsHoursTo.getUnitNo());
					syllabusEntryUnitsHours.setModifiedBy(syllabusEntryForm.getUserId());
					syllabusEntryUnitsHours.setLastModifiedDate(new Date());
					syllabusEntryUnitsHours.setIsActive(true);
					
					//start syllabus headings
					List<SyllabusEntryHeadingDescTo> headingsList=syllabusEntryUnitsHoursTo.getSyllabusEntryHeadingDescTos();
					if(headingsList!=null && !headingsList.isEmpty()){
						Set<SyllabusEntryHeadingDesc> headingsSet=new HashSet<SyllabusEntryHeadingDesc>();
						SyllabusEntryHeadingDesc syllabusEntryHeadingDesc=null;
						for (SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo : headingsList) {
							syllabusEntryHeadingDesc=new SyllabusEntryHeadingDesc();
							if(syllabusEntryHeadingDescTo.getHeading()!=null && !syllabusEntryHeadingDescTo.getHeading().trim().isEmpty()){
								syllabusEntryHeadingDesc.setHeading(syllabusEntryHeadingDescTo.getHeading().trim());
							}
							if(syllabusEntryHeadingDescTo.getDescription()!=null && !syllabusEntryHeadingDescTo.getDescription().trim().isEmpty()){
								if(syllabusEntryHeadingDescTo.getDescription().trim().length()<5000){
									syllabusEntryHeadingDesc.setDescription(syllabusEntryHeadingDescTo.getDescription().trim());
								}else{
									request.setAttribute("field","Description");
									throw new Exception();
								}
							}
							if(syllabusEntryHeadingDescTo.getId()>0){
								syllabusEntryHeadingDesc.setId(syllabusEntryHeadingDescTo.getId());
							}
							if(syllabusEntryHeadingDescTo.getId()<=0){
								syllabusEntryHeadingDesc.setCreatedBy(syllabusEntryForm.getUserId());
								syllabusEntryHeadingDesc.setCreatedDate(new Date());
							}
							syllabusEntryHeadingDesc.setHeadingNo(syllabusEntryHeadingDescTo.getHeadingNO());
							syllabusEntryHeadingDesc.setModifiedBy(syllabusEntryForm.getUserId());
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
	/**
	 *  make isActive false
	 * @param syllabusEntryUnitsHours
	 * @param unitsWhichAlreadyExits
	 * @return
	 */
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
	public void setToFormWhichIsInProgress(SyllabusEntry syllabusEntry, SyllabusEntryForm syllabusEntryForm) throws Exception{
		syllabusEntryForm.setId(syllabusEntry.getId());
		syllabusEntryForm.setBatchYear(String.valueOf(syllabusEntry.getBatchYear()));
		syllabusEntryForm.setSubjectCode(syllabusEntry.getSubject().getCode());
		syllabusEntryForm.setSubjectName(syllabusEntry.getSubject().getName());
		syllabusEntryForm.setParentDepartment(syllabusEntry.getSubject().getDepartment().getName());
		if(syllabusEntry.getSubject().getIsSecondLanguage() && syllabusEntry.getSubject().getIsSecondLanguage()!=null){
			syllabusEntryForm.setSecondLanguage("Yes");
		}else{
			syllabusEntryForm.setSecondLanguage("No");
		}
		if(syllabusEntry.getSubject().getIsTheoryPractical()!=null){
			if(syllabusEntry.getSubject().getIsTheoryPractical().equalsIgnoreCase("T")){
				syllabusEntryForm.setTheoryOrPractical("Theory");
			}else if(syllabusEntry.getSubject().getIsTheoryPractical().equalsIgnoreCase("P")){
				syllabusEntryForm.setTheoryOrPractical("Practical");
			}else{
				syllabusEntryForm.setTheoryOrPractical("Theory and Practical");
			}
		}
		if(syllabusEntry.getSubject().getQuestionbyrequired()!=null && syllabusEntry.getSubject().getQuestionbyrequired()){
			syllabusEntryForm.setQuestionBankRequired("Yes");
		}else{
			syllabusEntryForm.setQuestionBankRequired("No");
		}
		if(syllabusEntry.getTotTeachingHrsPerSem()!=null){
			syllabusEntryForm.setTotTeachHrsPerSem(String.valueOf(syllabusEntry.getTotTeachingHrsPerSem()));
		}
		if(syllabusEntry.getNoOfLectureHrsPerWeek()!=null){
			syllabusEntryForm.setNoOfLectureHrsPerWeek(String.valueOf(syllabusEntry.getNoOfLectureHrsPerWeek()));
		}
		if(syllabusEntry.getCredits()!=null){
			syllabusEntryForm.setCredits(syllabusEntry.getCredits());
		}
		if(syllabusEntry.getMaxMarks()!=null){
			syllabusEntryForm.setMaxMarks(String.valueOf(syllabusEntry.getMaxMarks()));
		}
		if(syllabusEntry.getCourseObjective()!=null){
			syllabusEntryForm.setCourseObjective(syllabusEntry.getCourseObjective());
		}
		if(syllabusEntry.getLearningOutcome()!=null){
			syllabusEntryForm.setLerningOutcome(syllabusEntry.getLearningOutcome());
		}
		if(syllabusEntry.getTextBooksAndRefBooks()!=null){
			syllabusEntryForm.setTextBooksAndRefBooks(syllabusEntry.getTextBooksAndRefBooks());
		}
		if(syllabusEntry.getFreeText()!=null){
			syllabusEntryForm.setFreeText(syllabusEntry.getFreeText());
		}
		if(syllabusEntry.getRecommendedReading()!=null){
			syllabusEntryForm.setRecommendedReading(syllabusEntry.getRecommendedReading());
		}
		if(syllabusEntry.getChangeInSyllabus()!=null && syllabusEntry.getChangeInSyllabus().equalsIgnoreCase("Y")){
			syllabusEntryForm.setChangeInSyllabus("yes");
			syllabusEntryForm.setTempChangeInSyllabus("yes");
			
		}else{
			syllabusEntryForm.setChangeInSyllabus("no");
			syllabusEntryForm.setTempChangeInSyllabus("no");
		}
		if(syllabusEntry.getBriefDetailsExistingSyllabus()!=null){
			syllabusEntryForm.setBriefDetailsExistingSyllabus(syllabusEntry.getBriefDetailsExistingSyllabus());
		}
		if(syllabusEntry.getBriefDetalsAboutChange()!=null){
			syllabusEntryForm.setBriefDetalsAboutChange(syllabusEntry.getBriefDetalsAboutChange());
		}
		if(syllabusEntry.getChangeInSyllabus()!=null){
			syllabusEntryForm.setChangeReason(syllabusEntry.getChangeReason());
		}
		if(syllabusEntry.getRemarks()!=null){
			syllabusEntryForm.setRemarks(syllabusEntry.getRemarks());
		}
		if((syllabusEntry.getHodReject()!=null && syllabusEntry.getHodReject())
				|| (syllabusEntry.getFinalReject()!=null && syllabusEntry.getFinalReject())){
			syllabusEntryForm.setRejectReason(syllabusEntry.getRejectReason());
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
			syllabusEntryForm.setSyllabusEntryUnitsHoursTos(unitsList);
		}
		if(syllabusEntryForm.getSyllabusEntryUnitsHoursTos()!=null && !syllabusEntryForm.getSyllabusEntryUnitsHoursTos().isEmpty()
				&& syllabusEntryForm.getSyllabusEntryUnitsHoursTos().size()>1){
			syllabusEntryForm.setUnitsFocus("unit_"+(syllabusEntryForm.getSyllabusEntryUnitsHoursTos().size()-1));
			syllabusEntryForm.setUnitsFlag(true);
		}
	}
	public List<SyllabusEntryDupTo> setBosToTos(
			List<SyllabusEntry> syllabusEntries, Map<Integer, String> subjectMap, String semister) throws Exception{
		List<SyllabusEntryDupTo> list=new ArrayList<SyllabusEntryDupTo>();
		Map<Integer, String> map=subjectMap;
		SyllabusEntryDupTo syllabusEntryDupTo=null;
		if(syllabusEntries!=null && !syllabusEntries.isEmpty()){
			for (SyllabusEntry syllabusEntry : syllabusEntries) {
				syllabusEntryDupTo=new SyllabusEntryDupTo();
				syllabusEntryDupTo.setId(syllabusEntry.getId());
				if((syllabusEntry.getSendForApproval()==null || !syllabusEntry.getSendForApproval())
						&& (syllabusEntry.getApproved()==null || !syllabusEntry.getApproved())
						&& (syllabusEntry.getFinalApproval()==null || !syllabusEntry.getFinalApproval())
						&& (syllabusEntry.getHodReject()==null || !syllabusEntry.getHodReject())
						&& (syllabusEntry.getFinalReject()==null || !syllabusEntry.getFinalReject())){
					syllabusEntryDupTo.setStatus("In-progress");
				}else if(syllabusEntry.getApproved()!=null && syllabusEntry.getApproved()){
					syllabusEntryDupTo.setStatus("Approved By HOD");
				}else if(syllabusEntry.getSendForApproval()!=null && syllabusEntry.getSendForApproval()){
					syllabusEntryDupTo.setStatus("Completed");
				}else if(syllabusEntry.getFinalApproval()!=null && syllabusEntry.getFinalApproval()){
					syllabusEntryDupTo.setStatus("Approved");
				}else if(syllabusEntry.getFinalReject()!=null && syllabusEntry.getFinalReject()){
					syllabusEntryDupTo.setStatus("Rejected");
				}else if(syllabusEntry.getHodReject()!=null && syllabusEntry.getHodReject()){
					syllabusEntryDupTo.setStatus("HOD Rejected");
				}
				syllabusEntryDupTo.setSubjectName(syllabusEntry.getSubject().getName());
				syllabusEntryDupTo.setSubjectCode(syllabusEntry.getSubject().getCode());
				syllabusEntryDupTo.setSubjId(syllabusEntry.getSubject().getId());
				map.remove(syllabusEntry.getSubject().getId());
				list.add(syllabusEntryDupTo);
				 Collections.sort(list);
			}
		}
		if(map!=null && !map.isEmpty()){
			for (Map.Entry<Integer, String> entry : map.entrySet()){
				syllabusEntryDupTo=new SyllabusEntryDupTo();
				syllabusEntryDupTo.setSubjectName(entry.getValue().substring(0, entry.getValue().indexOf("(")));
				syllabusEntryDupTo.setSubjectCode(entry.getValue().substring( entry.getValue().indexOf("(")+1,  entry.getValue().indexOf(")")));
				syllabusEntryDupTo.setSubjId(entry.getKey());
				syllabusEntryDupTo.setStatus("Pending");
				list.add(syllabusEntryDupTo);
				 Collections.sort(list);
			}
		}
		return list;
	}
	public Map<String, List<SyllabusEntryTo>> setBosToMap(
			List<SyllabusEntry> syllabusEntries,
			Map<Integer, Map<String, SyllabusEntryGeneralTo>> subjectMap, int deptId) throws Exception{
		Map<String,List<SyllabusEntryTo>> mainMap=new HashMap<String, List<SyllabusEntryTo>>();
		Map<Integer, Map<String, SyllabusEntryGeneralTo>> map=subjectMap;
		Map<String, SyllabusEntryGeneralTo> schemeMap=null;
		SyllabusEntryTo syllabusEntryTo=null;
		SyllabusEntryGeneralTo syllabusEntryGeneralTo=null;
		List<SyllabusEntryTo> list=null;
		if(syllabusEntries!=null && !syllabusEntries.isEmpty()){
			for (SyllabusEntry syllabusEntry : syllabusEntries) {
				if(map.containsKey(syllabusEntry.getSubject().getId())){
					syllabusEntryTo=new SyllabusEntryTo();
					syllabusEntryTo.setId(syllabusEntry.getId());
					if((syllabusEntry.getSendForApproval()==null || !syllabusEntry.getSendForApproval())
							&& (syllabusEntry.getApproved()==null || !syllabusEntry.getApproved())
							&& (syllabusEntry.getFinalApproval()==null || !syllabusEntry.getFinalApproval())
							&& (syllabusEntry.getHodReject()==null || !syllabusEntry.getHodReject())
							&& (syllabusEntry.getFinalReject()==null || !syllabusEntry.getFinalReject())){
						syllabusEntryTo.setStatus("In-progress");
					}else if(syllabusEntry.getApproved()!=null && syllabusEntry.getApproved()){
						syllabusEntryTo.setStatus("Approved By HOD");
					}else if(syllabusEntry.getSendForApproval()!=null && syllabusEntry.getSendForApproval()){
						syllabusEntryTo.setStatus("Completed");
					}else if(syllabusEntry.getFinalApproval()!=null && syllabusEntry.getFinalApproval()){
						syllabusEntryTo.setStatus("Approved");
					}else if(syllabusEntry.getFinalReject()!=null && syllabusEntry.getFinalReject()){
						syllabusEntryTo.setStatus("Rejected");
						syllabusEntryTo.setRejectReason(syllabusEntry.getRejectReason());
					}else if(syllabusEntry.getHodReject()!=null && syllabusEntry.getHodReject()){
						syllabusEntryTo.setStatus("HOD Rejected");
						syllabusEntryTo.setRejectReason(syllabusEntry.getRejectReason());
					}
					syllabusEntryTo.setSubjectName(syllabusEntry.getSubject().getName());
					syllabusEntryTo.setSubjId(syllabusEntry.getSubject().getId());
					schemeMap=map.get(syllabusEntry.getSubject().getId());
					for (Map.Entry<String, SyllabusEntryGeneralTo> entry : schemeMap.entrySet()){
					    if(mainMap.containsKey(entry.getKey())){
					    	list=mainMap.get(entry.getKey());
					    }else{
					    	list=new ArrayList<SyllabusEntryTo>();
					    }
					    syllabusEntryGeneralTo=entry.getValue();
					    syllabusEntryTo.setSubjectName(syllabusEntryGeneralTo.getSubjectName());
					    syllabusEntryTo.setSemester(syllabusEntryGeneralTo.getSemester());
					    syllabusEntryTo.setSubjectCode(syllabusEntryGeneralTo.getSubjectCode());
					    if(deptId==syllabusEntryGeneralTo.getDeptId()){
					    	syllabusEntryTo.setDisplay("display");
					    }
					    syllabusEntryTo.setSubjId(syllabusEntry.getSubject().getId());
					    list.add(syllabusEntryTo);
					    Collections.sort(list);
					    mainMap.put(entry.getKey(), list);
					}
					map.remove(syllabusEntry.getSubject().getId());
				}
			}
		}
		if(map!=null && !map.isEmpty()){
			for (Map.Entry<Integer, Map<String,SyllabusEntryGeneralTo>> entry : map.entrySet()){
				schemeMap=entry.getValue();
				for (Map.Entry<String, SyllabusEntryGeneralTo> entry1 : schemeMap.entrySet()) {
					if(mainMap.containsKey(entry1.getKey())){
						list=mainMap.get(entry1.getKey());
					}else{
						list=new ArrayList<SyllabusEntryTo>();
					}
					syllabusEntryGeneralTo=entry1.getValue();
					syllabusEntryTo=new SyllabusEntryTo();
				    syllabusEntryTo.setSubjectName(syllabusEntryGeneralTo.getSubjectName());
				    syllabusEntryTo.setSemester(syllabusEntryGeneralTo.getSemester());
				    syllabusEntryTo.setSubjectCode(syllabusEntryGeneralTo.getSubjectCode());
				    if(deptId==syllabusEntryGeneralTo.getDeptId()){
				    	syllabusEntryTo.setDisplay("display");
				    }
					syllabusEntryTo.setSubjId(entry.getKey());
					syllabusEntryTo.setStatus("Pending");
					list.add(syllabusEntryTo);
					Collections.sort(list);
					mainMap.put(entry1.getKey(), list);
				}
			}
		}
		return mainMap;
	}
	public void setToFormPreviousSyllabusEntry(SyllabusEntry syllabusEntry,
			SyllabusEntryForm syllabusEntryForm) throws Exception{
		if(syllabusEntry.getTotTeachingHrsPerSem()!=null){
			syllabusEntryForm.setTotTeachHrsPerSem(String.valueOf(syllabusEntry.getTotTeachingHrsPerSem()));
		}
		if(syllabusEntry.getNoOfLectureHrsPerWeek()!=null){
			syllabusEntryForm.setNoOfLectureHrsPerWeek(String.valueOf(syllabusEntry.getNoOfLectureHrsPerWeek()));
		}
		if(syllabusEntry.getCredits()!=null){
			syllabusEntryForm.setCredits(syllabusEntry.getCredits());
		}
		if(syllabusEntry.getMaxMarks()!=null){
			syllabusEntryForm.setMaxMarks(String.valueOf(syllabusEntry.getMaxMarks()));
		}
		if(syllabusEntry.getCourseObjective()!=null){
			syllabusEntryForm.setCourseObjective(syllabusEntry.getCourseObjective());
		}
		if(syllabusEntry.getLearningOutcome()!=null){
			syllabusEntryForm.setLerningOutcome(syllabusEntry.getLearningOutcome());
		}
		if(syllabusEntry.getTextBooksAndRefBooks()!=null){
			syllabusEntryForm.setTextBooksAndRefBooks(syllabusEntry.getTextBooksAndRefBooks());
		}
		if(syllabusEntry.getFreeText()!=null){
			syllabusEntryForm.setFreeText(syllabusEntry.getFreeText());
		}
		if(syllabusEntry.getChangeInSyllabus()!=null){
			if(syllabusEntry.getChangeInSyllabus().equalsIgnoreCase("Y")){
				syllabusEntryForm.setChangeInSyllabus("yes");
				syllabusEntryForm.setTempChangeInSyllabus("yes");
			}else{
				syllabusEntryForm.setChangeInSyllabus("no");
				syllabusEntryForm.setTempChangeInSyllabus("no");
			}
			
		}
		if(syllabusEntry.getBriefDetailsExistingSyllabus()!=null){
			syllabusEntryForm.setBriefDetailsExistingSyllabus(syllabusEntry.getBriefDetailsExistingSyllabus());
		}
		if(syllabusEntry.getBriefDetalsAboutChange()!=null){
			syllabusEntryForm.setBriefDetalsAboutChange(syllabusEntry.getBriefDetalsAboutChange());
		}
		if(syllabusEntry.getChangeInSyllabus()!=null){
			syllabusEntryForm.setChangeReason(syllabusEntry.getChangeReason());
		}
		if(syllabusEntry.getRemarks()!=null){
			syllabusEntryForm.setRemarks(syllabusEntry.getRemarks());
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
			syllabusEntryForm.setSyllabusEntryUnitsHoursTos(unitsList);
		}
		
	
	}
	public void setToFormForPreview(SyllabusEntry syllabusEntry,
			SyllabusEntryForm syllabusEntryForm) throws Exception{

		syllabusEntryForm.setBatchYear(String.valueOf(syllabusEntry.getBatchYear()));
		syllabusEntryForm.setSubjectCode(syllabusEntry.getSubject().getCode());
		syllabusEntryForm.setSubjectName(syllabusEntry.getSubject().getName());
		if(syllabusEntry.getTotTeachingHrsPerSem()!=null){
			syllabusEntryForm.setTotTeachHrsPerSem(String.valueOf(syllabusEntry.getTotTeachingHrsPerSem()));
		}
		if(syllabusEntry.getNoOfLectureHrsPerWeek()!=null){
			syllabusEntryForm.setNoOfLectureHrsPerWeek(String.valueOf(syllabusEntry.getNoOfLectureHrsPerWeek()));
		}
		if(syllabusEntry.getCredits()!=null){
			syllabusEntryForm.setCredits(syllabusEntry.getCredits());
		}
		if(syllabusEntry.getMaxMarks()!=null){
			syllabusEntryForm.setMaxMarks(String.valueOf(syllabusEntry.getMaxMarks()));
		}
		if(syllabusEntry.getCourseObjective()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Course Objective</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getCourseObjective().length();i++){
				stringBuilder.append(syllabusEntry.getCourseObjective().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryForm.setCourseObjective(stringBuilder.toString());
		}
		if(syllabusEntry.getLearningOutcome()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Learning Outcome</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getLearningOutcome().length();i++){
				stringBuilder.append(syllabusEntry.getLearningOutcome().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryForm.setLerningOutcome(stringBuilder.toString());
		}
		if(syllabusEntry.getTextBooksAndRefBooks()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Essential Text Books</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getTextBooksAndRefBooks().length();i++){
				stringBuilder.append(syllabusEntry.getTextBooksAndRefBooks().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryForm.setTextBooksAndRefBooks(stringBuilder.toString());
		}
		if(syllabusEntry.getRecommendedReading()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Recommended Reading</font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getRecommendedReading().length();i++){
				stringBuilder.append(syllabusEntry.getRecommendedReading().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryForm.setRecommendedReading(stringBuilder.toString());
		}
		if(syllabusEntry.getFreeText()!=null){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<table width='100%'><tr><td><b><font size='3'>Additional Information </font></b></td></tr>"+
					"<tr><td><table width='100%'><tr><td width='1%'></td><td align='justify'><font size='2'>");
			for(int i=0;i<syllabusEntry.getFreeText().length();i++){
				stringBuilder.append(syllabusEntry.getFreeText().charAt(i));
			}
			stringBuilder.append("</font></td></tr></table></td></tr></table>");
			syllabusEntryForm.setFreeText(stringBuilder.toString());
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
			syllabusEntryForm.setSyllabusEntryUnitsHoursTos(unitsList);
		}
	}
	
}
