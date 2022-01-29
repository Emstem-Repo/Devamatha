package com.kp.cms.helpers.reports;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.CategoryWiseIntakeForm;
import com.kp.cms.to.reports.CategoryWiseMapTO;
import com.kp.cms.to.reports.CategoryWiseTO;

public class CategoryWiseIntakeHelper {
	
private static final Log log = LogFactory.getLog(CategoryWiseIntakeHelper.class);
	
	/**
	 * @param studentsAttendanceReportForm
	 * @return
	 */
	private static String commonSearch(CategoryWiseIntakeForm intakeForm) {
		log.info("entered commonSearch..");
		String searchCriteria = "";
		
		if (intakeForm.getProgramId() != null && intakeForm.getProgramId().trim().length() > 0 && !StringUtils.isEmpty(intakeForm.getProgramId())) {
			String program = "and stu.admAppln.courseBySelectedCourseId.program.id = "
				+ intakeForm.getProgramId();
			searchCriteria = searchCriteria + program;
		}else if (intakeForm.getProgramTypeId() != null && intakeForm.getProgramTypeId().trim().length() > 0 && !StringUtils.isEmpty(intakeForm.getProgramTypeId())) {
			String programType = "and stu.admAppln.courseBySelectedCourseId.program.programType.id = "
				+ intakeForm.getProgramTypeId();
			searchCriteria = searchCriteria + programType;
		}

		if (intakeForm.getSemister() != null && intakeForm.getSemister().trim().length() > 0 && !StringUtils.isEmpty(intakeForm.getSemister())) {
			String semister = " and stu.classSchemewise.classes.termNumber = "
					+ intakeForm.getSemister();
			searchCriteria = searchCriteria + semister;
		}		
		
		searchCriteria = searchCriteria + " order by stu.admAppln.courseBySelectedCourseId.id";
		log.info("exit commonSearch..");
		return searchCriteria;
	}
	
	/**
	 * @param performaIIIForm
	 * @return
	 */
	public static String getSelectionCriteria(CategoryWiseIntakeForm intakeForm) {
		
		log.info("Entered getSelectionCriteria..");
		String statusCriteria = commonSearch(intakeForm);
		
		String searchCriteria= "";
		
		searchCriteria = " select stu.admAppln.courseBySelectedCourseId.id, " +
				"stu.admAppln.courseBySelectedCourseId.name, " +
				"stu.admAppln.courseBySelectedCourseId.maxIntake, " +
				"c.id, " +
				"c.name " +
				"from Student stu left join  stu.admAppln.personalData.caste c " +
				"where  stu.isAdmitted = 1 and stu.isActive=1 and stu.admAppln.isCancelled=0 and stu.admAppln.isSelected=1 " + statusCriteria;
		
		log.info("Exit getSelectionCriteria..");
		return searchCriteria;
	}
	
	/**
	 * @param performIIIBOList
	 * @return
	 */
	public static Map<Integer, CategoryWiseTO> convertBOstoTOs(List<Student> categoryWiseList) {
			
		log.info("entered convertBOstoTOs..");
		Map<Integer, CategoryWiseTO> categoryIntakeDetailsMap = new HashMap<Integer, CategoryWiseTO>();
		Map<Integer, CategoryWiseMapTO> categoryWiseMap = null;
		CategoryWiseTO CategoryWiseTO = null;
		CategoryWiseMapTO categoryWiseMapTO = null;
		int casteId=0;
		String casteName="";
		if(categoryWiseList !=null){
			Iterator heldItr = categoryWiseList.iterator();
			while (heldItr.hasNext()) {
				Object[] courseIntakeDetails = (Object[]) heldItr.next();
				if(courseIntakeDetails[3]==null){
					casteId=0;
					casteName="Other";
				}else{
					casteId=(Integer) courseIntakeDetails[3];
					casteName=(String)courseIntakeDetails[4];
				}
				if (categoryIntakeDetailsMap.containsKey((Integer) courseIntakeDetails[0])) {
					CategoryWiseTO = categoryIntakeDetailsMap.get((Integer) courseIntakeDetails[0]);
					categoryWiseMap = CategoryWiseTO.getCategoryMap();
					
					if(categoryWiseMap.containsKey(casteId)){
						categoryWiseMapTO = categoryWiseMap.get(casteId);
						
						int count = categoryWiseMapTO.getIntakeValue();
						count++;
						categoryWiseMapTO.setIntakeValue(count);
						categoryWiseMapTO.setCasteName(casteName);
						categoryWiseMap.put(casteId, categoryWiseMapTO);
					}else{
						categoryWiseMapTO = new CategoryWiseMapTO();
						
						categoryWiseMapTO.setCasteName(casteName);
						categoryWiseMapTO.setIntakeValue(1);
						categoryWiseMap.put(casteId, categoryWiseMapTO);
						CategoryWiseTO.setCategoryMap(categoryWiseMap);
					}
				}else{
					CategoryWiseTO = new CategoryWiseTO();
					if(courseIntakeDetails[1] != null){
						CategoryWiseTO.setCourseName((String) courseIntakeDetails[1]);
					}
					if(courseIntakeDetails[2] != null){
						CategoryWiseTO.setCourseIntake(courseIntakeDetails[2].toString());
					}
					if(casteId>=0){
						categoryWiseMapTO = new CategoryWiseMapTO();
						categoryWiseMap = new HashMap<Integer, CategoryWiseMapTO>();
						
						categoryWiseMapTO.setCasteName(casteName);
						categoryWiseMapTO.setIntakeValue(1);
						categoryWiseMap.put(casteId, categoryWiseMapTO);
						CategoryWiseTO.setCategoryMap(categoryWiseMap);
					}
					categoryIntakeDetailsMap.put((Integer) courseIntakeDetails[0], CategoryWiseTO);
				}
			}
		}	
		log.info("exit convertBotoTo..");
		return categoryIntakeDetailsMap;
	}
}