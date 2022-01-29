package com.kp.cms.helpers.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.bo.admin.EligibleSubjects;
import com.kp.cms.forms.admin.EligibilityCriteriaForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.EligibilityCriteriaTO;
import com.kp.cms.to.admin.EligibleSubjectsTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;

public class EligibilityCriteriaHelper {
	private static Log log = LogFactory	.getLog(EligibilityCriteriaHelper.class);
	public static volatile EligibilityCriteriaHelper eligibilityCriteriaHelper = null;
	public static EligibilityCriteriaHelper getInstance() {
		if (eligibilityCriteriaHelper == null) {
			eligibilityCriteriaHelper = new EligibilityCriteriaHelper();
			return eligibilityCriteriaHelper;
		}
		return eligibilityCriteriaHelper;
	}

	/**
	 * 
	 * @param eligibilityCriteriaForm
	 * @return EligibilityCriteria Bo object
	 * @throws Exception
	 */
	public EligibilityCriteria copyFromFormToBO(EligibilityCriteriaForm eligibilityCriteriaForm) throws Exception {
		EligibilityCriteria eligibilityCriteria = new EligibilityCriteria();
		String selectedSubjects[] = eligibilityCriteriaForm.getSelectedSubjects(); 
		Course course = new Course();
		Set<EligibleSubjects> eligibleSubjectsSet = new HashSet<EligibleSubjects>();
		if(selectedSubjects != null){
			for (int x = 0; x < selectedSubjects.length; x++){
				EligibleSubjects eligibleSubjects = new EligibleSubjects();
				
				DetailedSubjects detailedSubjects = new DetailedSubjects();
				detailedSubjects.setId(Integer.parseInt(selectedSubjects[x]));
				eligibleSubjects.setDetailedSubjects(detailedSubjects);
				eligibleSubjects.setIsActive(true);
				eligibleSubjectsSet.add(eligibleSubjects);
			}
		}
		
		if(eligibilityCriteriaForm.getCourseId() != null && !eligibilityCriteriaForm.getCourseId().equals("")){
			course.setId(Integer.parseInt(eligibilityCriteriaForm.getCourseId()));
		}
		eligibilityCriteria.setCourse(course);
		if(eligibilityCriteriaForm.getTotalPercentage() != null && !eligibilityCriteriaForm.getTotalPercentage().equals("")){
			eligibilityCriteria.setTotalPercentage(BigDecimal.valueOf(Double.parseDouble(eligibilityCriteriaForm.getTotalPercentage())));
		}
		
		if(eligibilityCriteriaForm.getPercentageWithoutLanguage() != null && !eligibilityCriteriaForm.getPercentageWithoutLanguage().equals("")){
			eligibilityCriteria.setPercentageWithoutLanguage(BigDecimal.valueOf(Double.parseDouble(eligibilityCriteriaForm.getPercentageWithoutLanguage())));
		}
		eligibilityCriteria.setYear(Integer.parseInt(eligibilityCriteriaForm.getYear()));
		eligibilityCriteria.setEligibleSubjectses(eligibleSubjectsSet);
		eligibilityCriteria.setIsActive(true);
		log.error("ending of copyFromFormToBO method in EligibilityCriteriaHelper");
		return eligibilityCriteria;
		
	}
	/**
	 * creating criteriaTOList for UI display
	 * @param criteriaList
	 * @param eligibilityCriteriaForm
	 * @param id
	 * @param toDisplay
	 * @return
	 */
	public List<EligibilityCriteriaTO> copyCriteriaBosToTos(List<EligibilityCriteria> criteriaList, EligibilityCriteriaForm eligibilityCriteriaForm, int id, Boolean toDisplay) {
		Iterator<EligibilityCriteria> i = criteriaList.iterator();
		EligibilityCriteria eligibilityCriteria;
		EligibilityCriteriaTO eligibilityCriteriaTO;
		CourseTO courseTo;
		ProgramTypeTO programTypeTO;
		ProgramTO programTO;
		List<EligibilityCriteriaTO> eligibilityCriteriaTOList = new ArrayList<EligibilityCriteriaTO>(); 
		List<EligibleSubjectsTO> tempList;
		
		while (i.hasNext()) {
			eligibilityCriteriaTO = new EligibilityCriteriaTO();
			courseTo = new CourseTO();
			programTypeTO = new ProgramTypeTO();
			programTO = new ProgramTO();
			
			eligibilityCriteria = i.next();

			programTypeTO.setProgramTypeId(eligibilityCriteria.getCourse().getProgram().getProgramType().getId());
			programTypeTO.setProgramTypeName(eligibilityCriteria.getCourse().getProgram().getProgramType().getName());
			programTO.setProgramTypeTo(programTypeTO);
			programTO.setId(eligibilityCriteria.getCourse().getProgram().getId());
			programTO.setName(eligibilityCriteria.getCourse().getProgram().getName());
			courseTo.setProgramTo(programTO);
			courseTo.setId(eligibilityCriteria.getCourse().getId());
			courseTo.setName(eligibilityCriteria.getCourse().getName());
			eligibilityCriteriaTO.setId(eligibilityCriteria.getId());
			eligibilityCriteriaTO.setCourseTO(courseTo);
			eligibilityCriteriaTO.setTotalPercentage(eligibilityCriteria.getTotalPercentage());
			eligibilityCriteriaTO.setPercentageWithoutLanguage(eligibilityCriteria.getPercentageWithoutLanguage());
			eligibilityCriteriaTO.setYear(eligibilityCriteria.getYear());
			
			Set<EligibleSubjects> eligibleSubjectSet = eligibilityCriteria.getEligibleSubjectses(); 
			Iterator<EligibleSubjects> iterator = eligibleSubjectSet.iterator();
			tempList = new ArrayList<EligibleSubjectsTO>();
			
			while (iterator.hasNext()){
				EligibleSubjectsTO eligibleSubjectsTO = new EligibleSubjectsTO();
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				EligibleSubjects eligibleSubjects = iterator.next();
				eligibleSubjectsTO.setId(eligibleSubjects.getId());
				if(eligibleSubjects.getDetailedSubjects()!= null){
					detailedSubjectsTO.setId(eligibleSubjects.getDetailedSubjects().getId());
					if(eligibleSubjects.getDetailedSubjects()!= null && eligibleSubjects.getDetailedSubjects().getSubjectName()!= null){
						detailedSubjectsTO.setSubjectName(eligibleSubjects.getDetailedSubjects().getSubjectName());
					}
					eligibleSubjectsTO.setDetailedSubjectsTO(detailedSubjectsTO);
				}
				eligibleSubjectsTO.setIsActive(eligibleSubjects.getIsActive());
				if(toDisplay){
					if(eligibleSubjects.getIsActive()){
						tempList.add(eligibleSubjectsTO);
					}
				}
				else
				{
					tempList.add(eligibleSubjectsTO);
				}
			}
			eligibilityCriteriaTO.setEligibleSubjectsTOList(tempList);
			eligibilityCriteriaTOList.add(eligibilityCriteriaTO);
		}
		log.error("ending of copyCriteriaBosToTos method in EligibilityCriteriaHelper");
		return eligibilityCriteriaTOList;
	
	}
	/**
	 * method for update criteria
	 * @param eligibilityCriteriaForm
	 * @return
	 * @throws Exception
	 */
	public EligibilityCriteria copyFormToBOForUpdate(EligibilityCriteriaForm eligibilityCriteriaForm)throws Exception{
		//Gets the updated values from formbean and setting those to BO object
		EligibilityCriteria eligibilityCriteria = new EligibilityCriteria();
		Course course = new Course();
		EligibleSubjects eligibleSubjects;
		DetailedSubjects detailedSubjects;
		EligibleSubjectsTO eligibleSubjectsTO;
		eligibilityCriteria.setId(eligibilityCriteriaForm.getId());
		course.setId(Integer.parseInt(eligibilityCriteriaForm.getCourseId()));
		eligibilityCriteria.setCourse(course);
		
		if(eligibilityCriteriaForm.getTotalPercentage() != null && !eligibilityCriteriaForm.getTotalPercentage().equals("")){
			eligibilityCriteria.setTotalPercentage(BigDecimal.valueOf(Double.parseDouble(eligibilityCriteriaForm.getTotalPercentage())));
		}
		if(eligibilityCriteriaForm.getPercentageWithoutLanguage() != null && !eligibilityCriteriaForm.getPercentageWithoutLanguage().equals("")){
			eligibilityCriteria.setPercentageWithoutLanguage(BigDecimal.valueOf(Double.parseDouble(eligibilityCriteriaForm.getPercentageWithoutLanguage())));
		}
		
		eligibilityCriteria.setIsActive(true);
		eligibilityCriteria.setYear(Integer.parseInt(eligibilityCriteriaForm.getYear()));
		
		Set<EligibleSubjects> eligibleSubjectSet= new HashSet<EligibleSubjects>();
		int subjectSize = 0;
		if(eligibilityCriteriaForm.getSelectedSubjects() != null ){
			subjectSize = eligibilityCriteriaForm.getSelectedSubjects().length;
		}
		
		String subjectArray[] = eligibilityCriteriaForm.getSelectedSubjects(); 
		EligibilityCriteriaTO eligibilityCriteriaTO = eligibilityCriteriaForm.getEligibilityCriteriaTO();
		
		List<EligibleSubjectsTO> eligibleSubjectsTOList= eligibilityCriteriaTO.getEligibleSubjectsTOList();
		boolean inserted = false;
		for (int i = 0; i < subjectSize; i++) {
			eligibleSubjects = new EligibleSubjects();
			detailedSubjects = new DetailedSubjects();
			Iterator<EligibleSubjectsTO> iterator = eligibleSubjectsTOList.iterator();
			while (iterator.hasNext()) {
				//eligibleSubjectsTO = new EligibleSubjectsTO();
				eligibleSubjectsTO = iterator.next();					
				// if true - will not created new record keeps the previous state
				if(eligibleSubjectsTO.getDetailedSubjectsTO().getId() == Integer.parseInt(subjectArray[i])) {
					eligibleSubjects.setId(eligibleSubjectsTO.getId());
					detailedSubjects.setId(eligibleSubjectsTO.getDetailedSubjectsTO().getId());
					eligibleSubjects.setDetailedSubjects(detailedSubjects);
					eligibleSubjects.setIsActive(true);
					inserted = true;	
				}
			}
			if(!inserted) {
				// this works when new subject added.
				detailedSubjects.setId(Integer.parseInt(subjectArray[i]));
				eligibleSubjects.setDetailedSubjects(detailedSubjects);
				eligibleSubjects.setIsActive(true);
			}
			eligibleSubjectSet.add(eligibleSubjects);
			inserted = false;
			}
		boolean deleted = true;
		Iterator<EligibleSubjectsTO> it1=eligibleSubjectsTOList.iterator();
		while (it1.hasNext()) {
			eligibleSubjectsTO = it1.next();
			deleted = true;
			for (int i = 0; i < subjectSize; i++) {
				//Checks the old and new subjects
				if(eligibleSubjectsTO.getDetailedSubjectsTO().getId() == Integer.parseInt(subjectArray[i])) {
					deleted = false;
					break;
				}
			}
			// below condition checks for deletion
			if(deleted) {
				eligibleSubjects = new EligibleSubjects();
				detailedSubjects = new DetailedSubjects();
				eligibleSubjects.setId(eligibleSubjectsTO.getId());
				detailedSubjects.setId(eligibleSubjectsTO.getDetailedSubjectsTO().getId());
				eligibleSubjects.setDetailedSubjects(detailedSubjects);
				eligibleSubjects.setIsActive(false);
				
				eligibleSubjectSet.add(eligibleSubjects);
			}
		}	
		eligibilityCriteria.setEligibleSubjectses(eligibleSubjectSet);
		log.error("ending of copyFormToBOForUpdate method EligibilityCriteriaHelper");
		return eligibilityCriteria;
	}
	
}
