package com.kp.cms.helpers.admission;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.CurriculumSchemeForm;
import com.kp.cms.handlers.admin.SubjectGroupHandler;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admission.CurriculumSchemeDurationTO;
import com.kp.cms.to.admission.CurriculumSchemeSubjectTO;
import com.kp.cms.to.admission.CurriculumSchemeTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurriculumSchemeDurationComparator;
import com.kp.cms.utilities.CurriculumSchemeSubjectComparator;

public class CurriculumSchemeHelper {
	public static volatile CurriculumSchemeHelper curriculumSchemeHelper = null;
	private static final Log log = LogFactory.getLog(CurriculumSchemeHelper.class);
	
	private CurriculumSchemeHelper()
	{
		
	}
	
	/**
	 * 
	 * @return Returns a single instance every time
	 */

	public static CurriculumSchemeHelper getInstance() {
		if (curriculumSchemeHelper == null) {
			curriculumSchemeHelper = new CurriculumSchemeHelper();
			return curriculumSchemeHelper;
		}
		return curriculumSchemeHelper;
	}
/**
 * 
 * @param  ReturnsPopulates CourseBO to CurriculumSchemeTO
 */
	public CurriculumSchemeTO populateCourseBOtoTo(Course course)throws Exception {
		log.info("Entering into populateCourseBOtoTo of CurriculumSchemeHelper");
		CurriculumSchemeTO curriculumSchemeTO = new CurriculumSchemeTO();
		curriculumSchemeTO.setCourseName(course.getName());
		curriculumSchemeTO.setProgramName(course.getProgram().getName());
		curriculumSchemeTO.setProgramTypeName(course.getProgram().getProgramType().getName());
		log.info("Leaving into populateCourseBOtoTo of CurriculumSchemeHelper");
		return curriculumSchemeTO;
	}
	
	/*
	 * Populates the record retrieved from DB based on ID of Curriculumscheme Table converts Curriculumscheme BO to TO.
	 */
	public CurriculumSchemeTO populateCurriculumSchemeBOtoTOOnId(CurriculumScheme curriculumScheme, CurriculumSchemeForm curriculumSchemeForm, boolean edit)throws Exception
	{
		log.info("Entering into populateCurriculumSchemeBOtoTOOnId of CurriculumSchemeHelper");
		CurriculumSchemeTO curriculumSchemeTO=null;
			if (curriculumScheme!=null) {
				List<Integer> curriculumDurationIdList = new ArrayList<Integer>();
				Map<Integer, Integer> subjectGroupMap = new HashMap<Integer, Integer>();
			
				curriculumSchemeTO = new CurriculumSchemeTO();
				curriculumSchemeTO.setId(curriculumScheme.getId()!=0 ? curriculumScheme.getId():0);
				curriculumSchemeTO.setNoOfScheme(curriculumScheme.getNoScheme()!=0 ? curriculumScheme.getNoScheme():0);
				/**
				 * Calling the Subject Group Handler method and getting all the subject names  based on the courseId (If exists)
				 */						
				List<SubjectGroupTO> subjectGroupList = SubjectGroupHandler.getInstance().getSubjectGroupDetails(curriculumScheme.getCourse().getId());
				if(!subjectGroupList.isEmpty()){
//				Collections.sort(subjectGroupList, new SubjectGroupComparator());
				curriculumSchemeForm.setSubjectGroupList(subjectGroupList);
				}		
				
				//Set the available data to form as well as CurriculumschemeTO 
				if(curriculumScheme.getId()!=0){
					curriculumSchemeForm.setId(curriculumScheme.getId());
				}
				if(curriculumScheme.getCourse()!=null && curriculumScheme.getCourse().getId()!=0){
				curriculumSchemeTO.setCourseId(curriculumScheme.getCourse().getId());
				if(edit){
				curriculumSchemeForm.setCourse(String.valueOf(curriculumScheme.getCourse().getId()));
				}
				curriculumSchemeForm.setOldCourseId(curriculumScheme.getCourse().getId());
				}
				if(curriculumScheme.getCourse()!=null && curriculumScheme.getCourse().getProgram()!=null){
				curriculumSchemeTO.setProgramId(curriculumScheme.getCourse().getProgram().getId());
				curriculumSchemeForm.setProgram(String.valueOf(curriculumScheme.getCourse().getProgram().getId()));
				}
				if(curriculumScheme.getCourse()!=null && curriculumScheme.getCourse().getProgram()!=null && curriculumScheme.getCourse().getProgram().getProgramType()!=null){
				curriculumSchemeTO.setProgramTypeId(curriculumScheme.getCourse().getProgram().getProgramType().getId());
				curriculumSchemeForm.setProgramType(String.valueOf(curriculumScheme.getCourse().getProgram().getProgramType().getId()));
				}
				curriculumSchemeTO.setYear(curriculumScheme.getYear()!=null ? curriculumScheme.getYear():null);
				
				if(edit){
				curriculumSchemeForm.setYear(String.valueOf(curriculumScheme.getYear()!=null ? curriculumScheme.getYear():null));
				}
				curriculumSchemeForm.setOldYear(curriculumScheme.getYear());
				if(curriculumScheme.getCourseScheme()!=null && curriculumScheme.getCourseScheme().getId()!=0){
				curriculumSchemeTO.setCourseSchemeId(curriculumScheme.getCourseScheme().getId());
				if(edit){
				if(curriculumScheme.getCourseScheme()!=null && curriculumScheme.getCourseScheme().getIsActive()){
				curriculumSchemeForm.setSchemeId(String.valueOf(curriculumScheme.getCourseScheme().getId()));
				}
				}
				}
				if(curriculumScheme.getNoScheme()!=0){
					curriculumSchemeTO.setNoOfScheme(curriculumScheme.getNoScheme());
					if(edit){
					curriculumSchemeForm.setNoOfScheme(String.valueOf(curriculumScheme.getNoScheme()));
					curriculumSchemeForm.setOldNoOfScheme(curriculumScheme.getNoScheme());
					}
				}
				Set<CurriculumSchemeDuration> curriculumSchemeDurationSet = curriculumScheme.getCurriculumSchemeDurations();
				List<CurriculumSchemeDurationTO> durationList = new ArrayList<CurriculumSchemeDurationTO>();
				
				if(curriculumSchemeDurationSet!=null && !curriculumSchemeDurationSet.isEmpty()){
				//Used to sort the list in semesterwise
				List<CurriculumSchemeDuration> durationBOList = new ArrayList<CurriculumSchemeDuration>();
				durationBOList.addAll(curriculumSchemeDurationSet);
				Collections.sort(durationBOList, new CurriculumSchemeDurationComparator());
		
				Iterator<CurriculumSchemeDuration> iterator1 = durationBOList.iterator();
				while (iterator1.hasNext()) {
					CurriculumSchemeDuration curriculumSchemeDuration = iterator1.next();
					CurriculumSchemeDurationTO curriculumSchemeDurationTO = new CurriculumSchemeDurationTO();
					curriculumDurationIdList.add(curriculumSchemeDuration.getId());

					curriculumSchemeDurationTO.setStartDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(curriculumSchemeDuration.getStartDate()!=null ? curriculumSchemeDuration.getStartDate():null), "dd-MMM-yyyy","dd/MM/yyyy"));
					curriculumSchemeDurationTO.setEndDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((curriculumSchemeDuration.getEndDate()!=null) ? curriculumSchemeDuration.getEndDate():null), "dd-MMM-yyyy","dd/MM/yyyy"));
					curriculumSchemeDurationTO.setSemester(curriculumSchemeDuration.getSemesterYearNo()!=0 ? curriculumSchemeDuration.getSemesterYearNo():0);
					if(curriculumSchemeDuration.getAcademicYear()!=0){
					curriculumSchemeDurationTO.setAcademicYear(curriculumSchemeDuration.getAcademicYear());
					}else{
						curriculumSchemeDurationTO.setAcademicYear(0);
					}
					curriculumSchemeDurationTO.setSelectedIndex(0);
					curriculumSchemeDurationTO.setSemester(curriculumSchemeDuration.getSemesterYearNo()!=0 ? curriculumSchemeDuration.getSemesterYearNo():0);
					
					curriculumSchemeDurationTO.setId(curriculumSchemeDuration.getId());
					
					Set<CurriculumSchemeSubject> subjectset = curriculumSchemeDuration.getCurriculumSchemeSubjects();
					String[] selectedSubject = null;
					if(curriculumSchemeDuration.getCurriculumSchemeSubjects()!=null){
					int length = curriculumSchemeDuration.getCurriculumSchemeSubjects().size();
					
					selectedSubject = new String[length];
					}
					else{
						selectedSubject = new String[0];
					}
					int count =0;
					List<CurriculumSchemeSubjectTO> curriculumSubjectTOList = new ArrayList<CurriculumSchemeSubjectTO>();
					
					if(subjectset!=null && !subjectset.isEmpty()){	
					//Used to sort by curriculumsubjectIds
					List<CurriculumSchemeSubject> subjectBOList = new ArrayList<CurriculumSchemeSubject>();			
					subjectBOList.addAll(subjectset);
					Collections.sort(subjectBOList, new CurriculumSchemeSubjectComparator());
					
						Iterator<CurriculumSchemeSubject> iterator2 = subjectBOList.iterator();
							while (iterator2.hasNext()) {
								CurriculumSchemeSubject curriculumSchemeSubject = iterator2.next();
								CurriculumSchemeSubjectTO curriculumSchemeSubjectTO = new CurriculumSchemeSubjectTO();
									if(curriculumSchemeSubject.getId()!=0 && curriculumSchemeSubject.getSubjectGroup() != null &&
									curriculumSchemeSubject.getSubjectGroup().getId() !=0)
									{
									curriculumSchemeSubjectTO.setId(curriculumSchemeSubject.getId());
			
									SubjectGroupTO subjectGroupTO = new SubjectGroupTO();
									if(curriculumSchemeSubject.getSubjectGroup()!=null && curriculumSchemeSubject.getSubjectGroup().getIsActive()){
									subjectGroupTO.setId(curriculumSchemeSubject.getSubjectGroup().getId());
									subjectGroupTO.setName(curriculumSchemeSubject.getSubjectGroup().getName()!=null ? curriculumSchemeSubject.getSubjectGroup().getName():null);
									selectedSubject[count++] = String.valueOf(curriculumSchemeSubject.getSubjectGroup().getId());
									
									if(curriculumSchemeSubject.getSubjectGroup()!=null){
									subjectGroupMap.put(curriculumSchemeSubject.getSubjectGroup().getId(), curriculumSchemeSubject.getId());
									}
									}
									
									curriculumSchemeSubjectTO.setSubjectGroupTO(subjectGroupTO);
									curriculumSubjectTOList.add(curriculumSchemeSubjectTO);
									}
									curriculumSchemeForm.setSubjectGroupMap(subjectGroupMap);
							}
							
							curriculumSchemeDurationTO.setCurriculumSubjectTOList(curriculumSubjectTOList);
							
							curriculumSchemeDurationTO.setSubjectGroups(selectedSubject);
						}
					
					curriculumSchemeForm.setCurriculumDurationIdList(curriculumDurationIdList);
					durationList.add(curriculumSchemeDurationTO);
			}
				curriculumSchemeTO.setCurriculumDurationList(durationList);
			}
	}
			log.info("Leaving into populateCurriculumSchemeBOtoTOOnId of CurriculumSchemeHelper");
			return curriculumSchemeTO;
	}
	/**
	 * 
	 * @param Helper method converts To properties to BO for update operation
	 * @return
	 */
	public CurriculumScheme populateCurriculumSchemeTOtoBO(CurriculumSchemeForm curriculumSchemeForm)throws Exception
	{
		log.info("Entering into populateCurriculumSchemeBOtoTOOnId of CurriculumSchemeHelper");
		List<CurriculumSchemeDurationTO> durationList = curriculumSchemeForm.getDurationList();
		Set<CurriculumSchemeDuration> durationSet = new HashSet<CurriculumSchemeDuration>();
		List<Integer> curriculumDurationIdList = curriculumSchemeForm.getCurriculumDurationIdList();
		
		Set<CurriculumSchemeSubject> newcurriculumSubjectBOSet = new HashSet<CurriculumSchemeSubject>();
		CurriculumScheme curriculumScheme = null;
			
			try {
				CurriculumSchemeDurationTO curriculumSchemeDurationTO;
				CourseScheme courseScheme = null;
				Course course = null;
				CurriculumSchemeDuration curriculumSchemeDuration = null;
				CurriculumSchemeSubject curriculumSchemeSubject = null;
				SubjectGroup subjectGroup = null;
			
				if(durationList!=null && !durationList.isEmpty()){
					curriculumScheme = new CurriculumScheme();
					curriculumScheme.setId(curriculumSchemeForm.getId());
					curriculumScheme.setNoScheme(Integer.parseInt(curriculumSchemeForm.getNoOfScheme()));
					curriculumScheme.setYear(Integer.parseInt(curriculumSchemeForm.getYear()));
					curriculumScheme.setModifiedBy(curriculumSchemeForm.getUserId());
					curriculumScheme.setLastModifiedDate(new Date());
					
					course = new Course();
					course.setId(Integer.parseInt(curriculumSchemeForm.getCourse()));									
					curriculumScheme.setCourse(course);
					
					courseScheme = new CourseScheme();
					courseScheme.setId(Integer.parseInt(curriculumSchemeForm.getSchemeId()));					
					curriculumScheme.setCourseScheme(courseScheme);
					
					Iterator<CurriculumSchemeDurationTO> iterator = durationList.iterator();
					while (iterator.hasNext()) {
						curriculumSchemeDurationTO = (CurriculumSchemeDurationTO) iterator.next();
						
						//Works for old durations if that durations already present
						if(curriculumDurationIdList.contains(Integer.valueOf(curriculumSchemeDurationTO.getId()))){
						curriculumSchemeDuration = new CurriculumSchemeDuration();
						curriculumSchemeDuration.setId(curriculumSchemeDurationTO.getId());	
						curriculumSchemeDuration.setSemesterYearNo(curriculumSchemeDurationTO.getSemester());
						if(curriculumSchemeDurationTO.getStartDate()!=null || !curriculumSchemeDurationTO.getStartDate().isEmpty()){
						curriculumSchemeDuration.setStartDate(CommonUtil.ConvertStringToSQLDate(curriculumSchemeDurationTO.getStartDate()));
						}
						if(curriculumSchemeDurationTO.getEndDate()!=null || !curriculumSchemeDurationTO.getEndDate().isEmpty()){
						curriculumSchemeDuration.setEndDate(CommonUtil.ConvertStringToSQLDate(curriculumSchemeDurationTO.getEndDate()));
						}
						curriculumSchemeDuration.setAcademicYear(curriculumSchemeDurationTO.getAcademicYear());
						curriculumSchemeDuration.setModifiedBy(curriculumSchemeForm.getUserId());
						
						curriculumSchemeDuration.setLastModifiedDate(new Date());
						
						Map<Integer, Integer> subjectGroupMap = new HashMap<Integer, Integer>();
							List<CurriculumSchemeSubjectTO> curriculumSubjectTOList = curriculumSchemeDurationTO.getCurriculumSubjectTOList();
							
							if(curriculumSubjectTOList!=null && !curriculumSubjectTOList.isEmpty()) {
							subjectGroupMap = new HashMap<Integer, Integer>();
							Iterator<CurriculumSchemeSubjectTO> itr6 = curriculumSubjectTOList.iterator();
							CurriculumSchemeSubjectTO subjectTO;
							//Constructing map and putting subjectID as the key and CurriculumSchemeSubject id as the value
							while(itr6.hasNext()) {
								subjectTO = itr6.next();
								if(subjectTO.getSubjectGroupTO()!=null && subjectTO.getSubjectGroupTO().getId()!=0 & subjectTO.getId()!=0){
								subjectGroupMap.put(subjectTO.getSubjectGroupTO().getId(),subjectTO.getId());
								}
							} }
					
							Set<CurriculumSchemeSubject> curriculumSubjectBOSet = new HashSet<CurriculumSchemeSubject>();
									
										String selectedSubjectGroups[] = new String[0];
										if(curriculumSchemeDurationTO.getSelectedIndex() != -1){
											selectedSubjectGroups = curriculumSchemeDurationTO.getSubjectGroups();
										}
										
										if(selectedSubjectGroups!=null){
										
										for(String str : selectedSubjectGroups){
											subjectGroup = new SubjectGroup();
											curriculumSchemeSubject = new CurriculumSchemeSubject();
											//Checks if that subjectgroupId is already present. Then override that
											if(subjectGroupMap.containsKey(Integer.parseInt(str))){
												curriculumSchemeSubject.setId(subjectGroupMap.get(Integer.parseInt(str)));
												subjectGroup.setId(Integer.parseInt(str));
												curriculumSchemeSubject.setCreatedBy(curriculumSchemeForm.getUserId());
												curriculumSchemeSubject.setCreatedDate(new Date());
												curriculumSchemeSubject.setModifiedBy(curriculumSchemeForm.getUserId());
												curriculumSchemeSubject.setLastModifiedDate(new Date());
												curriculumSchemeSubject.setSubjectGroup(subjectGroup);
												curriculumSubjectBOSet.add(curriculumSchemeSubject);
										} else {
											//Else create new object and add subjectgroups to that
												subjectGroup.setId(Integer.parseInt(str));
												curriculumSchemeSubject.setCreatedBy(curriculumSchemeForm.getUserId());
												curriculumSchemeSubject.setCreatedDate(new Date());
												curriculumSchemeSubject.setModifiedBy(curriculumSchemeForm.getUserId());
												curriculumSchemeSubject.setLastModifiedDate(new Date());
												curriculumSchemeSubject.setSubjectGroup(subjectGroup);
												curriculumSubjectBOSet.add(curriculumSchemeSubject);
											}
										}
									}
										curriculumSchemeDuration.setCurriculumSchemeSubjects(curriculumSubjectBOSet);
						}
						//Works for new curriculumscheme durations
						else{
							curriculumSchemeDuration = new CurriculumSchemeDuration();
							curriculumSchemeDuration.setSemesterYearNo(curriculumSchemeDurationTO.getSemester());
							if(curriculumSchemeDurationTO.getStartDate()!=null || !curriculumSchemeDurationTO.getStartDate().isEmpty()){
							curriculumSchemeDuration.setStartDate(CommonUtil.ConvertStringToSQLDate(curriculumSchemeDurationTO.getStartDate()));
							}
							if(curriculumSchemeDurationTO.getEndDate()!=null || !curriculumSchemeDurationTO.getEndDate().isEmpty()){
							curriculumSchemeDuration.setEndDate(CommonUtil.ConvertStringToSQLDate(curriculumSchemeDurationTO.getEndDate()));
							}
							curriculumSchemeDuration.setAcademicYear(curriculumSchemeDurationTO.getAcademicYear());
							curriculumSchemeDuration.setCreatedBy(curriculumSchemeForm.getUserId());
							curriculumSchemeDuration.setCreatedDate(new Date());
							curriculumSchemeDuration.setModifiedBy(curriculumSchemeForm.getUserId());
							curriculumSchemeDuration.setLastModifiedDate(new Date());
							
							List<CurriculumSchemeSubjectTO> curriculumSubjectTOList = curriculumSchemeDurationTO.getCurriculumSubjectTOList();
							
							if(curriculumSubjectTOList==null && curriculumSchemeDurationTO.getSubjectGroups()!=null &&
									curriculumSchemeDurationTO.getSubjectGroups().length!=0){
								String selectedSubjectGroups[] = curriculumSchemeDurationTO.getSubjectGroups();
								int length = selectedSubjectGroups.length;
									for(int i =0 ; i<length ; i++){									
										subjectGroup = new SubjectGroup();
										curriculumSchemeSubject = new CurriculumSchemeSubject();										
										subjectGroup.setId(Integer.parseInt(selectedSubjectGroups[i]));
										curriculumSchemeSubject.setCreatedBy(curriculumSchemeForm.getUserId());
										curriculumSchemeSubject.setCreatedDate(new Date());
										curriculumSchemeSubject.setModifiedBy(curriculumSchemeForm.getUserId());
										curriculumSchemeSubject.setLastModifiedDate(new Date());
										curriculumSchemeSubject.setSubjectGroup(subjectGroup);
										newcurriculumSubjectBOSet.add(curriculumSchemeSubject);
									}
								}
							curriculumSchemeDuration.setCurriculumSchemeSubjects(newcurriculumSubjectBOSet);
						}
						durationSet.add(curriculumSchemeDuration);
							}
					
					curriculumScheme.setCurriculumSchemeDurations(durationSet);
						}					
				}
			catch (Exception e) {
				log.info("Error occured at populateCurriculumSchemeBOtoTOOnId in Helper",e);
				throw new ApplicationException(e);				
			}
			log.info("Leaving into populateCurriculumSchemeBOtoTOOnId of CurriculumSchemeHelper");
			return curriculumScheme;
	}
	

/**
 * 
 * @param Populates all the records of Curriculumscheme Table from Curriculumscheme BO to TO
 * @return
 */

	public List<CurriculumSchemeTO> populateCurriculumSchemeBOtoTO(List<CurriculumScheme> list1)throws Exception
	{
		log.info("Entering into populateCurriculumSchemeBOtoTO of CurriculumSchemeHelper");
		List<CurriculumSchemeTO> curriculumSchemeDetailsList=new ArrayList<CurriculumSchemeTO>();		
		Iterator<CurriculumScheme> iterator=list1.iterator();
		while (iterator.hasNext()) {
			CurriculumScheme curriculumScheme = iterator.next();
			CurriculumSchemeTO curriculumSchemeTO=new CurriculumSchemeTO();
			if(curriculumScheme.getCourseScheme().getId()!=0 && curriculumScheme.getNoScheme()!=0 && curriculumScheme.getCourse().getCode()!=null
			&& curriculumScheme.getCourse().getId()!=0 && curriculumScheme.getCourse().getProgram().getId()!=0 && curriculumScheme.getCourse().getProgram().getProgramType().getId() !=0
			&& curriculumScheme.getCourse().getProgram().getName()!=null && curriculumScheme.getCourse().getProgram().getProgramType().getName()!=null && 
			curriculumScheme.getYear()!=0 && curriculumScheme.getId()!=0)
			{
			curriculumSchemeTO.setCourseSchemeId(curriculumScheme.getCourseScheme().getId());
			curriculumSchemeTO.setNoOfScheme(curriculumScheme.getNoScheme());
			curriculumSchemeTO.setCourseName(curriculumScheme.getCourse().getName());
			curriculumSchemeTO.setCourseId(curriculumScheme.getCourse().getId());
			
			curriculumSchemeTO.setProgramId(curriculumScheme.getCourse().getProgram().getId());
			curriculumSchemeTO.setProgramTypeId(curriculumScheme.getCourse().getProgram().getProgramType().getId());
			
			curriculumSchemeTO.setProgramName(curriculumScheme.getCourse().getProgram().getName());
			curriculumSchemeTO.setProgramTypeName(curriculumScheme.getCourse().getProgram().getProgramType().getName());
			curriculumSchemeTO.setYear(curriculumScheme.getYear());
			curriculumSchemeTO.setId(curriculumScheme.getId());
			}
			Set<CurriculumSchemeDuration> durationSet=curriculumScheme.getCurriculumSchemeDurations();
			Set<CurriculumSchemeDurationTO> durationSetTO=new HashSet<CurriculumSchemeDurationTO>();
			Iterator<CurriculumSchemeDuration> it=durationSet.iterator();
			while (it.hasNext()) {
				CurriculumSchemeDuration duration = it.next();
				CurriculumSchemeDurationTO curriculumSchemeDurationTO=new CurriculumSchemeDurationTO();
				if(duration.getId()!=0 && duration.getStartDate()!=null && duration.getEndDate() !=null && duration.getSemesterYearNo()!=0 )
				{	
				curriculumSchemeDurationTO.setId(duration.getId());
				curriculumSchemeDurationTO.setStartDate(CommonUtil.getStringDate(duration.getStartDate()));
				curriculumSchemeDurationTO.setEndDate(CommonUtil.getStringDate(duration.getEndDate()));
				curriculumSchemeDurationTO.setSemester(duration.getSemesterYearNo());
				}
				Set<CurriculumSchemeSubject>subjectSet=duration.getCurriculumSchemeSubjects();
				Set<CurriculumSchemeSubjectTO> subjectSetTO=new HashSet<CurriculumSchemeSubjectTO>();
				Iterator<CurriculumSchemeSubject> itr=subjectSet.iterator();
				while (itr.hasNext()) {
					CurriculumSchemeSubject curriculumSchemeSubject = itr.next();
					CurriculumSchemeSubjectTO curriculumSchemeSubjectTO=new CurriculumSchemeSubjectTO();
					if(curriculumSchemeSubject.getId()!=0 && duration.getId()!=0){
					curriculumSchemeSubjectTO.setId(curriculumSchemeSubject.getId());
					curriculumSchemeSubjectTO.setCurriculumSchemeDurationId(duration.getId());
					}
					SubjectGroup subjectGroup=new SubjectGroup();
					SubjectGroupTO subjectGroupTO=new SubjectGroupTO();
					subjectGroupTO.setId(subjectGroup.getId());
					curriculumSchemeSubjectTO.setSubjectGroupTO(subjectGroupTO);
					subjectSetTO.add(curriculumSchemeSubjectTO);
					curriculumSchemeDurationTO.setCurriculumSchemeSubjectTO(subjectSetTO);
					}
				durationSetTO.add(curriculumSchemeDurationTO);
			}
			curriculumSchemeTO.setCurriculumSchemeDurationTO(durationSetTO);
			curriculumSchemeDetailsList.add(curriculumSchemeTO);
		}	
		log.info("Leaving into populateCurriculumSchemeBOtoTO of CurriculumSchemeHelper");
		return curriculumSchemeDetailsList;
	}
	


public List<CurriculumSchemeTO> populateCurriculumSchemeYearwiseBOtoTO(List<CurriculumScheme> list1)throws Exception
{
	log.info("Entering into populateCurriculumSchemeBOtoTO of CurriculumSchemeHelper");
	List<CurriculumSchemeTO> curriculumSchemeDetailsList=new ArrayList<CurriculumSchemeTO>();		
	Iterator<CurriculumScheme> iterator=list1.iterator();
	while (iterator.hasNext()) {
		CurriculumScheme curriculumScheme = iterator.next();
		CurriculumSchemeTO curriculumSchemeTO=new CurriculumSchemeTO();
		if(curriculumScheme.getCourseScheme().getId()!=0 && curriculumScheme.getNoScheme()!=0 && curriculumScheme.getCourse().getCode()!=null
		&& curriculumScheme.getCourse().getId()!=0 && curriculumScheme.getCourse().getProgram().getId()!=0 && curriculumScheme.getCourse().getProgram().getProgramType().getId() !=0
		&& curriculumScheme.getCourse().getProgram().getName()!=null && curriculumScheme.getCourse().getProgram().getProgramType().getName()!=null && 
		curriculumScheme.getYear()!=0 && curriculumScheme.getId()!=0)
		{
		curriculumSchemeTO.setCourseSchemeId(curriculumScheme.getCourseScheme().getId());
		curriculumSchemeTO.setNoOfScheme(curriculumScheme.getNoScheme());
		curriculumSchemeTO.setCourseName(curriculumScheme.getCourse().getName());
		curriculumSchemeTO.setCourseId(curriculumScheme.getCourse().getId());
		
		curriculumSchemeTO.setProgramId(curriculumScheme.getCourse().getProgram().getId());
		curriculumSchemeTO.setProgramTypeId(curriculumScheme.getCourse().getProgram().getProgramType().getId());
		
		curriculumSchemeTO.setProgramName(curriculumScheme.getCourse().getProgram().getName());
		curriculumSchemeTO.setProgramTypeName(curriculumScheme.getCourse().getProgram().getProgramType().getName());
		curriculumSchemeTO.setYear(curriculumScheme.getYear());
		curriculumSchemeTO.setId(curriculumScheme.getId());
		}
		Set<CurriculumSchemeDuration> durationSet=curriculumScheme.getCurriculumSchemeDurations();
		Set<CurriculumSchemeDurationTO> durationSetTO=new HashSet<CurriculumSchemeDurationTO>();
		Iterator<CurriculumSchemeDuration> it=durationSet.iterator();
		while (it.hasNext()) {
			CurriculumSchemeDuration duration = it.next();
			CurriculumSchemeDurationTO curriculumSchemeDurationTO=new CurriculumSchemeDurationTO();
			if(duration.getId()!=0 && duration.getStartDate()!=null && duration.getEndDate() !=null && duration.getSemesterYearNo()!=0 )
			{	
			curriculumSchemeDurationTO.setId(duration.getId());
			curriculumSchemeDurationTO.setStartDate(CommonUtil.getStringDate(duration.getStartDate()));
			curriculumSchemeDurationTO.setEndDate(CommonUtil.getStringDate(duration.getEndDate()));
			curriculumSchemeDurationTO.setSemester(duration.getSemesterYearNo());
			}
			Set<CurriculumSchemeSubject>subjectSet=duration.getCurriculumSchemeSubjects();
			Set<CurriculumSchemeSubjectTO> subjectSetTO=new HashSet<CurriculumSchemeSubjectTO>();
			Iterator<CurriculumSchemeSubject> itr=subjectSet.iterator();
			while (itr.hasNext()) {
				CurriculumSchemeSubject curriculumSchemeSubject = itr.next();
				CurriculumSchemeSubjectTO curriculumSchemeSubjectTO=new CurriculumSchemeSubjectTO();
				if(curriculumSchemeSubject.getId()!=0 && duration.getId()!=0){
				curriculumSchemeSubjectTO.setId(curriculumSchemeSubject.getId());
				curriculumSchemeSubjectTO.setCurriculumSchemeDurationId(duration.getId());
				}
				SubjectGroup subjectGroup=new SubjectGroup();
				SubjectGroupTO subjectGroupTO=new SubjectGroupTO();
				subjectGroupTO.setId(subjectGroup.getId());
				curriculumSchemeSubjectTO.setSubjectGroupTO(subjectGroupTO);
				subjectSetTO.add(curriculumSchemeSubjectTO);
				curriculumSchemeDurationTO.setCurriculumSchemeSubjectTO(subjectSetTO);
				}
			durationSetTO.add(curriculumSchemeDurationTO);
		}
		curriculumSchemeTO.setCurriculumSchemeDurationTO(durationSetTO);
		curriculumSchemeDetailsList.add(curriculumSchemeTO);
	}	
	log.info("Leaving into populateCurriculumSchemeBOtoTO of CurriculumSchemeHelper");
	return curriculumSchemeDetailsList;
}

}







